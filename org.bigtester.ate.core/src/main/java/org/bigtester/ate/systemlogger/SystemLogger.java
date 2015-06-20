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
import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.constant.ExceptionMessage;
import org.bigtester.ate.model.data.exception.TestDataException;
import org.testng.ITestResult;
import org.testng.Reporter;


// TODO: Auto-generated Javadoc
/**
 * This class GenericSystemLogger handles the error happened out of test case
 * 
 * @author Peidong Hu
 * 
 */
@Aspect
public class SystemLogger {
	
	
	@Pointcut("within(org.bigtester.ate..*)")
	private void selectAll() {} //NOPMD

	private boolean isAlreadySysPointCut(Throwable error) {
		boolean retVal = false; //NOPMD
		for (int i =0; i <error.getSuppressed().length; i++) {
			if (error.getSuppressed()[i].getMessage().equalsIgnoreCase(ExceptionMessage.MSG_ALREADY_SYSPOINTCUT)) {
				retVal = true; //NOPMD
			}
		}
		return retVal;
	}
	
	/**
	 * Sets the already case point cut.
	 *
	 * @param error the new already case point cut
	 */
	private void setAlreadySysPointCut(Throwable error) {
		Throwable flagT = new Exception(ExceptionMessage.MSG_ALREADY_SYSPOINTCUT);
		error.addSuppressed(flagT);
	}
	/**
	 * After throwing advice.
	 *
	 * @param joinPoint the join point
	 * @param error the error
	 */
	@AfterThrowing(pointcut = "selectAll()", throwing = "error")
	public void afterThrowingAdvice(JoinPoint joinPoint, Throwable error) {
		if (isAlreadySysPointCut(error)) {
			return;
		}
		setAlreadySysPointCut(error);
		if (error instanceof TestDataException) {
			ITestResult itr = Reporter.getCurrentTestResult();
			itr.setThrowable(error);
		}
//		String[] fullST = Utils.stackTrace(error, false);
//		LogbackWriter.writeSysError(joinPoint.getTarget().toString()+ error.hashCode()  + fullST[1] );
		Class<?> cls = joinPoint.getTarget().getClass();
		if (null == cls) throw GlobalUtils.createInternalError("jvm");
		LogbackWriter.writeSysError(cls,  error);
		
	}

}
