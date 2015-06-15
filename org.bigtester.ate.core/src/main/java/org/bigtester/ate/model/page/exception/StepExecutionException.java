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
package org.bigtester.ate.model.page.exception;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.model.BaseATECaseExecE;
import org.bigtester.ate.model.IATECaseExecException;
import org.bigtester.ate.model.casestep.ITestCase;
import org.bigtester.ate.model.casestep.ITestStep; 
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.systemlogger.IATEProblemCreator;
import org.bigtester.ate.systemlogger.LogMessage;
import org.bigtester.ate.systemlogger.problemhandler.IProblemLogMessenger;
import org.bigtester.ate.systemlogger.problems.GenericATEProblem;
import org.bigtester.ate.systemlogger.problems.IATECaseExecProblem;
import org.eclipse.jdt.annotation.Nullable;


// TODO: Auto-generated Javadoc
/**
 * This class StepExecutionException defines ....
 * 
 * @author Peidong Hu
 * 
 */
public class StepExecutionException extends BaseATECaseExecE implements IATEProblemCreator{

	/** The Constant MSG. */
	public static final String MSG = "Step execution throwable";
	
	/** The Constant CODE. */
	public static final String CODE = "6019919237360483689L";
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6019919237360483689L;

	
	


	

	
	/**
	 * Instantiates a new step execution exception.
	 *
	 * @param message the message
	 * @param errorCode the error code
	 * @param myWebDriver the my web driver
	 * @param currentTestCase the current test case
	 */
	public StepExecutionException(String message, String errorCode,
			IMyWebDriver myWebDriver, ITestCase currentTestCase) {
		super(message, errorCode, currentTestCase, myWebDriver);
		setMyWebDriver(myWebDriver);
		setCurrentTestCase(currentTestCase);
	}
	
	/**
	 * Instantiates a new step execution exception.
	 *
	 * @param message the message
	 * @param errorCode the error code
	 * @param myWebDriver the my web driver
	 * @param currentTestCase the current test case
	 * @param cause the cause
	 */
	public StepExecutionException(String message, String errorCode,
			IMyWebDriver myWebDriver, ITestCase currentTestCase, Throwable cause) {
		super(message, errorCode, currentTestCase, myWebDriver, cause);
		setMyWebDriver(myWebDriver);
		setCurrentTestCase(currentTestCase);
	}
	
	/**
	 * Instantiates a new step execution exception2.
	 *
	 * @param message the message
	 * @param errorCode the error code
	 * @param myWebElement the my web element
	 * @param myWebDriver the my web driver
	 * @param currentTestCase the current test case
	 */
	public StepExecutionException(String message, String errorCode,
			IMyWebDriver myWebDriver, ITestCase currentTestCase, int stepIndexJumpTo) {
		super(message, errorCode, currentTestCase, myWebDriver);
		setMyWebDriver(myWebDriver);
		setCurrentTestCase(currentTestCase);
		super.setStepIndexJumpTo(stepIndexJumpTo);
	}
	
	/**
	 * Instantiates a new step execution exception.
	 *
	 * @param message the message
	 * @param errorCode the error code
	 * @param myWebElement the my web element
	 * @param myWebDriver the my web driver
	 * @param currentTestCase the current test case
	 * @param stepIndexJumpTo the step index jump to
	 * @param cause the cause
	 */
	public StepExecutionException(String message, String errorCode,
			IMyWebDriver myWebDriver, ITestCase currentTestCase, int stepIndexJumpTo, Throwable cause) {
		super(message, errorCode, currentTestCase, myWebDriver, cause);
		setMyWebDriver(myWebDriver);
		setCurrentTestCase(currentTestCase);
		super.setStepIndexJumpTo(stepIndexJumpTo);
	}

	/**
	 * Gets the my web element.
	 * 
	 * @return the myWebElement
	 */
//	public MyWebElement<?> getMyWebElement() {
//		return myWebElement;
//	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public IATECaseExecProblem initAteProblemInstance(Object ateProblemLocatin) {
		StepExecutionProblem retVal = (StepExecutionProblem) ateProblem;
		if (null == retVal) {
			retVal = new StepExecutionProblem(ateProblemLocatin, this);
			ateProblem = retVal;
		}
		return retVal;
	}
	
	

	/**
	 * This class StepExecutionProblem defines ....
	 * 
	 * @author Peidong Hu
	 * 
	 */
	public class StepExecutionProblem extends GenericATEProblem implements IATECaseExecProblem,  IProblemLogMessenger{
		
		
		
		/** The problem test case. */
		private final ITestCase problemTestCase;	
		
		
		/**
		 * Instantiates a new page validation problem.
		 *
		 * @param source the source
		 * @param see the see
		 * @param pTc the tc
		 */
		public StepExecutionProblem(Object source, StepExecutionException see) {
			super(source, see);
			problemTestCase = see.getCurrentTestCase();
		}
		
		

		/**
		 * Gets the step exec exception.
		 *
		 * @return the stepExecException
		 */
		public IATECaseExecException getStepExecException() {
			IATECaseExecException retVal;
			retVal = (IATECaseExecException) getAteException(); 
			if (null == retVal) throw GlobalUtils.createNotInitializedException("case exec exception");
			return retVal;
		}

		/**
		 * Gets the problem test case.
		 *
		 * @return the problemTestCase
		 */
		public ITestCase getProblemTestCase() {
			return problemTestCase;
		}



		/**
		 * {@inheritDoc}
		 */
		@Override
		public ITestCase getCurrentTestCase() {
			IATECaseExecException retVal;
			retVal = (IATECaseExecException) getAteException();
			if (null == retVal) throw GlobalUtils.createNotInitializedException("case exec exception");
			return ((IATECaseExecException) retVal).getCurrentTestCase();
		}



		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public ITestStep getCurrentTestStep() {
			return this.getCurrentTestCase().getCurrentTestStep();
		}



		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getProblemMessage() {
			String tmpStr =  this.getATECaseExecException().getMessage();
			if (null == tmpStr) return "exception error meesage is not populated."; //NOPMD
			else return tmpStr;
		}



		/**
		 * {@inheritDoc}
		 */
		@Override
		public IATECaseExecException getATECaseExecException() {
			return this.getStepExecException();
		}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getErrorCode() {
			return CODE;
		}



		/**
		 * {@inheritDoc}
		 */
		@Override
		public LogMessage getLogMessage() {
			String errorMsg = "";//NOPMD
			String warnMsg = "";//NOPMD
			if (isFatalProblem()) {
				errorMsg = "This throwable " + this.getClass().getCanonicalName() +"  is fatal for test case: " 
						+ this.getCurrentTestCase().getTestCaseName() 
						+ " in step: " + this.getCurrentTestStep().getStepName();
				
			} else {
				warnMsg = "This step producing throwable  " + this.getClass().getCanonicalName() +"  is optional for test case: " 
						+ this.getCurrentTestCase().getTestCaseName() 
						+ " in step: " + this.getCurrentTestStep().getStepName();
			}
			
			return new LogMessage(errorMsg, warnMsg);
		}


	}



	/**
	* {@inheritDoc}
	*/
	@Override
	@Nullable
	public StepExecutionProblem getAteProblem() {
		return (StepExecutionProblem) ateProblem;
	}


}
