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

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.bigtester.ate.model.AbstractATECaseExecE;
import org.bigtester.ate.model.AbstractATEException;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.systemlogger.problemhandler.ProblemBrowserHandler;
import org.bigtester.ate.systemlogger.problemhandler.ProblemLogbackHandler;
import org.bigtester.ate.systemlogger.problems.ATEProblemFactory;
import org.bigtester.ate.systemlogger.problems.GenericATEProblem;
import org.bigtester.ate.systemlogger.problems.IATEProblemFactory;
import org.bigtester.problomatic2.Problem;
import org.bigtester.problomatic2.Problomatic;

// TODO: Auto-generated Javadoc
/**
 * This class GenericTestCaseLogger will handle errors happened inside of a
 * specific test case execution
 * 
 * @author Peidong Hu
 * 
 */
@Aspect
public class GenericTestCaseLogger {

	/**
	 * Select all method as pointcuts.
	 */
	@Pointcut("within(org.bigtester.ate..*)")
	private void selectAll() { //NOPMD
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
		if (error instanceof AbstractATEException
				&& ((AbstractATEException) error).isAlreadyCasePointCut()) {
			return;
		}

		ProblemLogbackHandler plbh = new ProblemLogbackHandler();

		IMyWebDriver myWebDriver;
		Problem prb;
		ProblemBrowserHandler pbh;

		if (error instanceof AbstractATEException
				&& error instanceof AbstractATECaseExecE) {
			IATEProblemFactory ipf = ATEProblemFactory.getInstance();
			prb = ipf.getATEProblem(joinPoint.getTarget(),
					(AbstractATECaseExecE) error);

			myWebDriver = ((AbstractATECaseExecE) error).getMyWebDriver();
			pbh = new ProblemBrowserHandler(myWebDriver);
			Problomatic.addProblemHandlerForProblem(prb, pbh);
			((AbstractATECaseExecE) error).setAlreadyCasePointCut(true);
		} else {
			prb = new GenericATEProblem(joinPoint.getTarget(),
					(Exception) error);

		}
		Problomatic.addProblemHandlerForProblem(prb, plbh);
		Problomatic.handleProblem(prb);
	}

}
