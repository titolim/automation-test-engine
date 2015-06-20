/*******************************************************************************
 * ATE, Automation Test Engine
 *
 * Copyright 2014, Montreal PROT, or individual contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Montreal PROT.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.bigtester.ate.systemlogger;

import java.util.Set;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.annotation.ActionLoggable;
import org.bigtester.ate.annotation.StepLoggable;
import org.bigtester.ate.annotation.TestCaseLoggable;
import org.bigtester.ate.annotation.TestObjectFinderLoggable;
import org.bigtester.ate.annotation.TestProjectLoggable;
import org.bigtester.ate.constant.ExceptionMessage;
import org.bigtester.ate.model.casestep.ITestCase;
import org.bigtester.ate.model.casestep.ITestStep; 
import org.bigtester.ate.model.page.elementaction.ITestObjectAction;
import org.bigtester.ate.model.page.elementfind.ITestObjectFinder;
import org.bigtester.ate.model.project.TestProject;
import org.bigtester.ate.systemlogger.problemhandler.ProblemHandlerRegistry;
import org.bigtester.ate.systemlogger.problems.IATEProblem;
import org.bigtester.problomatic2.Problem;
import org.bigtester.problomatic2.ProblemHandler;
import org.bigtester.problomatic2.Problomatic;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

// TODO: Auto-generated Javadoc
/**
 * This class GenericTestCaseLogger will handle throwables created by ATE
 * application These throwables are considered as application level throwables
 * which is understoodable by ate The name GenericTestCaseLogger is a bad name.
 * Need to be changed
 * 
 * @author Peidong Hu
 * 
 */
@Aspect
public class ApplicationLogger implements ApplicationContextAware {
	// TODO change the name to AteApplicationLevelLogger
	/** The app context. */
	@Nullable
	@XStreamOmitField
	private transient ApplicationContext appContext;

	/** The errorous info log of null aop point cut class. */
	private static String nullAOPPointCutClass = "incorrect system aop pointcut for test logging: pointcut class is null.";
	
	/** The errorous incorrect aop point cut object. */
	private static String incorrectAOPPointCutObject  = "incorrect object type for test step logging: ";

	/**
	 * Gets the app context.
	 *
	 * @return the appContext
	 */
	public ApplicationContext getAppContext() {
		final ApplicationContext appContext2 = appContext;
		if (null == appContext2) {
			throw GlobalUtils.createNotInitializedException("appContext");
		} else {
			return appContext2;
		}
	}

	/**
	 * Select all method as pointcuts.
	 */
	@Pointcut("within(org.bigtester.ate..*)")
	private void selectAll() { // NOPMD
	}

	/**
	 * Checks if is already case point cut.
	 *
	 * @param error the error
	 * @return true, if is already case point cut
	 */
	private boolean isAlreadyCasePointCut(Throwable error) {
		boolean retVal = false; // NOPMD
		for (int i = 0; i < error.getSuppressed().length; i++) {
			if (error.getSuppressed()[i].getMessage().equalsIgnoreCase(
					ExceptionMessage.MSG_ALREADY_CASEPOINTCUT)) {
				retVal = true; // NOPMD
			}
		}
		return retVal;
	}

	/**
	 * Sets the already case point cut.
	 *
	 * @param error
	 *            the new already case point cut
	 */
	private void setAlreadyCasePointCut(Throwable error) {
		Throwable flagT = new Exception(
				ExceptionMessage.MSG_ALREADY_CASEPOINTCUT);
		error.addSuppressed(flagT);
	}

	/**
	 * After throwing advice.
	 *
	 * @param joinPoint            the join point
	 * @param error            the error
	 */
	@AfterThrowing(pointcut = "selectAll()", throwing = "error")
	public void afterThrowingAdvice(JoinPoint joinPoint, Throwable error) {
		if (isAlreadyCasePointCut(error)) {
			return;
		}

		setAlreadyCasePointCut(error);
		// Only handle application logs
		if (error instanceof IATEProblemCreator) {

			Object obj = joinPoint.getTarget();
			if (obj == null)
				throw GlobalUtils.createInternalError("GenericTestCaseLogger");
			IATEProblem iPrb = ((IATEProblemCreator) error).getAteProblem();
			Problem prb;
			if (null == iPrb) {
				prb = (Problem) GlobalUtils
						.getTargetObject(((IATEProblemCreator) error)
								.initAteProblemInstance(obj));
			} else {
				prb = (Problem) GlobalUtils.getTargetObject(iPrb);
			}
			Set<ProblemHandler> genericBlbh = ProblemHandlerRegistry
					.getGenericProblemHandlers();
			for (ProblemHandler hlr : genericBlbh)
				Problomatic.addProblemHandlerForProblem(prb, hlr);

			Set<ProblemHandler> problemSpecificBlbh = ProblemHandlerRegistry
					.getProblemAttachedProblemHandlers(prb);
			for (ProblemHandler hlr : problemSpecificBlbh)
				Problomatic.addProblemHandlerForProblem(prb, hlr);
			// Application logger doesn't handle the
			// throwableAttachedProblemHandlers,
			// since only IATEProblemCreator is considered as application level
			// throwable
			Problomatic.handleProblem(prb);
		}

	}

	
	/**
	 * Log before test project.
	 *
	 * @param joinPoint_p the join point_p
	 * @param loggable the loggable
	 */
	@Before(value = "@annotation(loggable)", argNames = "joinPoint_p, loggable")//NOPMD
	public void logBeforeTestProject(final JoinPoint joinPoint_p,
			TestProjectLoggable loggable) {
		Object obj = joinPoint_p.getTarget();
		Class<?> cls = obj.getClass();
		if (null == cls) {
			LogMessage lmsg = new LogMessage("",
					nullAOPPointCutClass );
			LogbackWriter.writeLogbackAppLog(lmsg);
		} else if ( obj instanceof TestProject) {
			TestProject testProj = (TestProject) obj;
			LogMessage lmsg = new LogMessage(
					"\r\n\r\n=============================================================\r\n       *****===TestProject execution starts.===*****\r\n"
							+ testProj.toString(), loggable.level());
			LogbackWriter.writeLogbackAppLog(lmsg, cls);
		} else {
			LogMessage lmsg = new LogMessage("",
					incorrectAOPPointCutObject  
							+ cls.getCanonicalName());
			LogbackWriter.writeLogbackAppLog(lmsg, cls);
		} 

	}

	/**
	 * Log after test project.
	 *
	 * @param joinPoint_p the join point_p
	 * @param loggable the loggable
	 */
	@AfterReturning(value = "@annotation(loggable)", argNames = "joinPoint_p, loggable")
	public void logAfterTestProject(final JoinPoint joinPoint_p,
			TestProjectLoggable loggable) {
		Object obj = joinPoint_p.getTarget();
		Class<?> cls = obj.getClass();
		if (null == cls) {
			LogMessage lmsg = new LogMessage("",
					nullAOPPointCutClass );
			LogbackWriter.writeLogbackAppLog(lmsg);
		} else	if (obj instanceof TestProject) {
			LogMessage lmsg = new LogMessage(
					"  *****===TestProject execution ends.===*****",
					loggable.level());
			LogbackWriter.writeLogbackAppLog(lmsg, cls);
		} else {
			LogMessage lmsg = new LogMessage("",
					incorrectAOPPointCutObject  
							+ cls.getCanonicalName());
			LogbackWriter.writeLogbackAppLog(lmsg, cls);
		} 

	}

	/**
	 * Log before test case.
	 *
	 * @param joinPoint_p the join point_p
	 * @param loggable the loggable
	 */
	@Before(value = "@annotation(loggable)", argNames = "joinPoint_p, loggable")
	public void logBeforeTestCase(final JoinPoint joinPoint_p,
			TestCaseLoggable loggable) {
		Object obj = joinPoint_p.getTarget();
		Class<?> cls = obj.getClass();
		if (null == cls) {
			LogMessage lmsg = new LogMessage("",
					nullAOPPointCutClass );
			LogbackWriter.writeLogbackAppLog(lmsg);
		} else if (obj instanceof ITestCase) {
			ITestCase testCase = (ITestCase) obj;
			LogMessage lmsg = new LogMessage("\r\n *****TestCase: "
					+ testCase.getTestCaseName() + " execution starts.*****",
					loggable.level());
			LogbackWriter.writeLogbackAppLog(lmsg, cls);
		} else {
			LogMessage lmsg = new LogMessage("",
					incorrectAOPPointCutObject  
							+ cls.getCanonicalName());
			LogbackWriter.writeLogbackAppLog(lmsg, cls);
		} 

	}

	/**
	 * Log before test step.
	 *
	 * @param joinPoint_p the join point_p
	 * @param loggable the loggable
	 */
	@Before(value = "@annotation(loggable)", argNames = "joinPoint_p, loggable")
	public void logBeforeTestStep(final JoinPoint joinPoint_p,
			StepLoggable loggable) {
		Object obj = joinPoint_p.getTarget();
		Class<?> cls = obj.getClass();
		if (null == cls) {
			LogMessage lmsg = new LogMessage("",
					nullAOPPointCutClass );
			LogbackWriter.writeLogbackAppLog(lmsg);
		} else if (obj instanceof ITestStep) {
			ITestStep step = (ITestStep) obj;
			LogMessage lmsg = new LogMessage(" ===Step: " + step.getStepName()
					+ " execution starts.===", loggable.level());
			LogbackWriter.writeLogbackAppLog(lmsg, cls);
		} else {
			LogMessage lmsg = new LogMessage("",
					incorrectAOPPointCutObject
							+ cls.getCanonicalName());
			LogbackWriter.writeLogbackAppLog(lmsg, cls);
		} 

	}

	/**
	 * Log before finder.
	 *
	 * @param joinPoint_p the join point_p
	 * @param finderloggable the finderloggable
	 */
	@Before(value = "@annotation(finderloggable)", argNames = "joinPoint_p, finderloggable")
	public void logBeforeFinder(final JoinPoint joinPoint_p,
			TestObjectFinderLoggable finderloggable) {
		Object obj = joinPoint_p.getTarget();
		Class<?> cls = obj.getClass();
		if (null == cls) {
			LogMessage lmsg = new LogMessage("",
					nullAOPPointCutClass );
			LogbackWriter.writeLogbackAppLog(lmsg);
		} else if (obj instanceof ITestObjectFinder<?>) {
			ITestObjectFinder<?> finder = (ITestObjectFinder<?>) obj;
			LogMessage lmsg = new LogMessage(finder.getClass().getName()
					+ " : \"" + finder.getFindingParametersLoggingValue()//NOPMD
					+ "\" searching test object starts.",
					finderloggable.level());
			LogbackWriter.writeLogbackAppLog(lmsg, cls);
		} else {
			LogMessage lmsg = new LogMessage("",
					incorrectAOPPointCutObject
							+ cls.getCanonicalName());
			LogbackWriter.writeLogbackAppLog(lmsg, cls);
		} 

	}

	/**
	 * Log after finder.
	 *
	 * @param joinPoint_p the join point_p
	 * @param finderloggable the finderloggable
	 * @param retVal the ret val
	 */
	@AfterReturning(value = "@annotation(finderloggable)", returning = "retVal", argNames = "joinPoint_p, finderloggable, retVal")
	public void logAfterFinder(final JoinPoint joinPoint_p,
			TestObjectFinderLoggable finderloggable, Object retVal) {
		Object obj = joinPoint_p.getTarget();
		Class<?> cls = obj.getClass();
		if (null == cls) {
			LogMessage lmsg = new LogMessage("",
					nullAOPPointCutClass );
			LogbackWriter.writeLogbackAppLog(lmsg);
		} else if (obj instanceof ITestObjectFinder<?>) {
			ITestObjectFinder<?> finder = (ITestObjectFinder<?>) obj;
			LogMessage lmsg = new LogMessage(finder.getClass().getName()
					+ " : \"" + finder.getFindingParametersLoggingValue()
					+ "\" has been found. searching test object ends.",
					finderloggable.level());
			LogbackWriter.writeLogbackAppLog(lmsg, cls);
		} else {
			LogMessage lmsg = new LogMessage("",
					incorrectAOPPointCutObject
							+ cls.getCanonicalName());
			LogbackWriter.writeLogbackAppLog(lmsg, cls);
		} 

	}

	/**
	 * Log before action.
	 *
	 * @param joinPoint_p the join point_p
	 * @param actionloggable the actionloggable
	 */
	@Before(value = "@annotation(actionloggable)", argNames = "joinPoint_p, actionloggable")
	public void logBeforeAction(final JoinPoint joinPoint_p,
			ActionLoggable actionloggable) {
		Object obj = joinPoint_p.getTarget();
		Class<?> cls = obj.getClass();
		if (null == cls) {
			LogMessage lmsg = new LogMessage("",
					nullAOPPointCutClass );
			LogbackWriter.writeLogbackAppLog(lmsg);
		} else if (obj instanceof ITestObjectAction<?>) {
			ITestObjectAction<?> action = (ITestObjectAction<?>) obj;
			LogMessage lmsg = new LogMessage(action.getClass().getName()
					+ " : \"" + action.getActionParametersLoggingValue()
					+ "\" action starts.", actionloggable.level());
			LogbackWriter.writeLogbackAppLog(lmsg, cls);
		} else  {
			LogMessage lmsg = new LogMessage("",
					incorrectAOPPointCutObject
							+ cls.getCanonicalName());
			LogbackWriter.writeLogbackAppLog(lmsg, cls);
		} 

	}

	/**
	 * Log after action.
	 *
	 * @param joinPoint_p the join point_p
	 * @param actionloggable the actionloggable
	 */
	@AfterReturning(value = "@annotation(actionloggable)", argNames = "joinPoint_p, actionloggable")
	public void logAfterAction(final JoinPoint joinPoint_p,
			ActionLoggable actionloggable) {
		Object obj = joinPoint_p.getTarget();
		Class<?> cls = obj.getClass();
		if (null == cls) {
			LogMessage lmsg = new LogMessage("",
					nullAOPPointCutClass );
			LogbackWriter.writeLogbackAppLog(lmsg);
		} else if (obj instanceof ITestObjectAction<?>) {
			ITestObjectAction<?> action = (ITestObjectAction<?>) obj;
			LogMessage lmsg = new LogMessage(action.getClass().getName()
					+ " : \"" + action.getActionParametersLoggingValue()
					+ "\" action ends.", actionloggable.level());
			LogbackWriter.writeLogbackAppLog(lmsg, cls);
		} else {
			LogMessage lmsg = new LogMessage("",
					incorrectAOPPointCutObject
							+ cls.getCanonicalName());
			LogbackWriter.writeLogbackAppLog(lmsg, cls);
		} 

	}

	/**
	 * Log after test step.
	 *
	 * @param joinPoint the join point
	 * @param loggable the loggable
	 */
	@AfterReturning(value = "@annotation(loggable)", argNames = "joinPoint, loggable")
	public void logAfterTestStep(final JoinPoint joinPoint,
			StepLoggable loggable) {
		Object obj = joinPoint.getTarget();
		Class<?> cls = obj.getClass();
		if (null == cls) {
			LogMessage lmsg = new LogMessage("",
					nullAOPPointCutClass );
			LogbackWriter.writeLogbackAppLog(lmsg);
		} else if (obj instanceof ITestStep) {
			ITestStep step = (ITestStep) obj;
			LogMessage lmsg = new LogMessage(" Step: " + step.getStepName()
					+ " execution ends.", loggable.level());
			LogbackWriter.writeLogbackAppLog(lmsg, cls);
		} else {
			LogMessage lmsg = new LogMessage("",
					incorrectAOPPointCutObject
							+ cls.getCanonicalName());
			LogbackWriter.writeLogbackAppLog(lmsg, cls);
		} 

	}

	/**
	 * Log after test case.
	 *
	 * @param joinPoint_p the join point_p
	 * @param loggable the loggable
	 */
	@AfterReturning(value = "@annotation(loggable)", argNames = "joinPoint_p, loggable")
	public void logAfterTestCase(final JoinPoint joinPoint_p,
			TestCaseLoggable loggable) {
		Object obj = joinPoint_p.getTarget();
		Class<?> cls = obj.getClass();
		if (null == cls) {
			LogMessage lmsg = new LogMessage("",
					nullAOPPointCutClass );
			LogbackWriter.writeLogbackAppLog(lmsg);
		} else if (obj instanceof ITestCase) {
			ITestCase testCase = (ITestCase) obj;
			LogMessage lmsg = new LogMessage(" TestCase: "
					+ testCase.getTestCaseName() + " execution ends.",
					loggable.level());
			LogbackWriter.writeLogbackAppLog(lmsg, cls);
		} else {
			LogMessage lmsg = new LogMessage("",
					incorrectAOPPointCutObject
							+ cls.getCanonicalName());
			LogbackWriter.writeLogbackAppLog(lmsg, cls);
		} 

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setApplicationContext(@Nullable ApplicationContext arg0)
			throws BeansException {
		this.appContext = arg0;

	}

}
