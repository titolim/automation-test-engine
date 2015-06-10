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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.annotation.StepLoggable;
import org.bigtester.ate.annotation.TestObjectFinderLoggable;
import org.bigtester.ate.constant.ExceptionMessage;
import org.bigtester.ate.model.casestep.ITestStep;
import org.bigtester.ate.model.page.elementfind.ITestObjectFinder;
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
 * which is understoodable by ate 
 * The name GenericTestCaseLogger is a bad name. Need to be changed
 * 
 * @author Peidong Hu
 * 
 */
@Aspect
public class ApplicationLogger implements ApplicationContextAware {
//TODO change the name to AteApplicationLevelLogger
	/** The app context. */
	@Nullable
	@XStreamOmitField
	private transient ApplicationContext appContext;

	/**
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
	 * @param joinPoint
	 *            the join point
	 * @param error
	 *            the error
	 * @throws InstantiationException
	 *             the instantiation exception
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
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
				prb = (Problem) GlobalUtils.getTargetObject(((IATEProblemCreator) error).initAteProblemInstance(obj));
			} else {
				prb = (Problem) GlobalUtils.getTargetObject(iPrb);
			}
			Set<ProblemHandler> genericBlbh = ProblemHandlerRegistry.getGenericProblemHandlers();
			for (ProblemHandler hlr : genericBlbh)
				Problomatic.addProblemHandlerForProblem(prb, hlr);
			
			Set<ProblemHandler> problemSpecificBlbh = ProblemHandlerRegistry.getProblemAttachedProblemHandlers(prb);
			for (ProblemHandler hlr : problemSpecificBlbh)
				Problomatic.addProblemHandlerForProblem(prb, hlr);
			//Application logger doesn't handle the throwableAttachedProblemHandlers, 
			//since only IATEProblemCreator is considered as application level throwable 
			Problomatic.handleProblem(prb);
		}

	}

	@Before(value = "@annotation(loggable)", argNames="joinPoint_p, loggable")
	public void logBeforeTestStep(final JoinPoint joinPoint_p, StepLoggable loggable) {
		Object obj = joinPoint_p.getTarget();
		Class<?> cls = obj.getClass();
		if (null != cls && obj instanceof ITestStep) {
			ITestStep step = (ITestStep) obj;
			LogMessage lmsg = new LogMessage(LogbackWriter.APPLOG_INFOHEADER + step.getStepName() + " execution starts.", loggable.level());
			LogbackWriter.writeLogbackAppLog(lmsg, cls);
		} else if (null != cls){
			LogMessage lmsg = new LogMessage("", LogbackWriter.APPLOG_WARNHEADER + "incorrect object type for test step logging: " + cls.getCanonicalName());
			LogbackWriter.writeLogbackAppLog(lmsg, cls);
		} else {
			LogMessage lmsg = new LogMessage("", LogbackWriter.APPLOG_WARNHEADER + "incorrect system aop pointcut for test step logging: class is null.");
			LogbackWriter.writeLogbackAppLog(lmsg);
		}
		
		
	}
	
	@Before(value = "@annotation(finderloggable)", argNames="joinPoint_p, finderloggable")
	public void logBeforeFinder(final JoinPoint joinPoint_p, TestObjectFinderLoggable finderloggable) {
		Object obj = joinPoint_p.getTarget();
		Class<?> cls = obj.getClass();
		if (null != cls && obj instanceof ITestObjectFinder<?>) {
			ITestObjectFinder<?> finder = (ITestObjectFinder<?>) obj;
			LogMessage lmsg = new LogMessage(LogbackWriter.APPLOG_INFOHEADER + finder.getClass().getName() + " : \"" + finder.getFindingParametersLoggingValue()+ "\" searching test object starts.", finderloggable.level());
			LogbackWriter.writeLogbackAppLog(lmsg, cls);
		} else if (null != cls){
			LogMessage lmsg = new LogMessage("", LogbackWriter.APPLOG_WARNHEADER + "incorrect object type for test step logging: " + cls.getCanonicalName());
			LogbackWriter.writeLogbackAppLog(lmsg, cls);
		} else {
			LogMessage lmsg = new LogMessage("", LogbackWriter.APPLOG_WARNHEADER + "incorrect system aop pointcut for test step logging: class is null.");
			LogbackWriter.writeLogbackAppLog(lmsg);
		}
		
		
	}
	
	@AfterReturning(value = "@annotation(finderloggable)", returning="retVal", argNames="joinPoint_p, finderloggable, retVal")
	public void logAfterFinder(final JoinPoint joinPoint_p, TestObjectFinderLoggable finderloggable, Object retVal) {
		Object obj = joinPoint_p.getTarget();
		Class<?> cls = obj.getClass();
		if (null != cls && obj instanceof ITestObjectFinder<?>) {
			ITestObjectFinder<?> finder = (ITestObjectFinder<?>) obj;
			LogMessage lmsg = new LogMessage(LogbackWriter.APPLOG_INFOHEADER + finder.getClass().getName() + " : \"" + finder.getFindingParametersLoggingValue()+ "\" has been found. searching test object ends.", finderloggable.level());
			LogbackWriter.writeLogbackAppLog(lmsg, cls);
		} else if (null != cls){
			LogMessage lmsg = new LogMessage("", LogbackWriter.APPLOG_WARNHEADER + "incorrect object type for test step logging: " + cls.getCanonicalName());
			LogbackWriter.writeLogbackAppLog(lmsg, cls);
		} else {
			LogMessage lmsg = new LogMessage("", LogbackWriter.APPLOG_WARNHEADER + "incorrect system aop pointcut for test step logging: class is null.");
			LogbackWriter.writeLogbackAppLog(lmsg);
		}
		
		
	}
	
	@AfterReturning(value = "@annotation(loggable)", argNames="joinPoint, loggable")
	public void logAfterTestStep(final JoinPoint joinPoint, StepLoggable loggable) {
		Object obj = joinPoint.getTarget();
		Class<?> cls = obj.getClass();
		if (null != cls && obj instanceof ITestStep) {
			ITestStep step = (ITestStep) obj;
			LogMessage lmsg = new LogMessage(LogbackWriter.APPLOG_INFOHEADER + step.getStepName() + " execution ends.", loggable.level());
			LogbackWriter.writeLogbackAppLog(lmsg, cls);
		} else if (null != cls){
			LogMessage lmsg = new LogMessage("", LogbackWriter.APPLOG_WARNHEADER + "incorrect object type for test step logging: " + cls.getCanonicalName());
			LogbackWriter.writeLogbackAppLog(lmsg, cls);
		} else {
			LogMessage lmsg = new LogMessage("", LogbackWriter.APPLOG_WARNHEADER + "incorrect system aop pointcut for test step logging: class is null.");
			LogbackWriter.writeLogbackAppLog(lmsg);
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
