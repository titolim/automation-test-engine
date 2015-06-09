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
package org.bigtester.ate.systemlogger.problemhandler;

import java.util.Properties;
import org.bigtester.ate.GlobalUtils;
import org.bigtester.problomatic2.InitException;
import org.bigtester.problomatic2.Problem;
import org.bigtester.problomatic2.handlers.AbstractProblemHandler;
import org.eclipse.jdt.annotation.Nullable;

import ch.qos.logback.classic.Level;

// TODO: Auto-generated Javadoc
/**
 * This class StepExecutionProblemHandler defines ....
 * 
 * @author Peidong Hu
 * 
 */
public class ProblemLogbackHandler extends AbstractProblemHandler implements
		IATEProblemHandler {

	/** The Constant slf4jLogger. */

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleProblem(@Nullable Problem aProblem) {

		// TODO add code to handle the other problems
		// problems are clasified as the following categories,
		// 1) target Step element not found: test error; (failed test)
		// 2) non-target Step exeception or error: test dependency error; (test
		// dependency error)
		// 3) page validation error : test error; (failed test)
		// 4) page validation exception: test pass with bug; (passed test with
		// bug)
		

		if (aProblem instanceof IProblemLogPrinter) {
			final Level warn2 = Level.WARN;
			if (warn2 == null) {
				GlobalUtils.createInternalError("jvm logback level enum.");
			} else {
				((IProblemLogPrinter) aProblem).logging(warn2);
			}
		}
		//non application level error is not logged in application.log
//		else {
//			if (null == aProblem)
//				LogbackWriter
//						.writeAppInfo(ExceptionMessage.MSG_UNCAUGHT_APP_ERRORS
//								+ LogbackTag.TAG_SEPERATOR
//								+ getClass().toString()
//								+ "aProblem parameter error.");
//			else
//				LogbackWriter
//						.writeAppInfo(ExceptionMessage.MSG_UNCAUGHT_APP_ERRORS
//								+ LogbackTag.TAG_SEPERATOR
//								+ aProblem.getSource().toString()
//								+ aProblem.getMessages().toString());
//		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(@Nullable Properties properties) throws InitException {
		// TODO Auto-generated method stub

	}

	/**
	* {@inheritDoc}
	*/
	@Override
	@Nullable
	public Class<?> getAttachedClass() {
		return null;
	}

}
