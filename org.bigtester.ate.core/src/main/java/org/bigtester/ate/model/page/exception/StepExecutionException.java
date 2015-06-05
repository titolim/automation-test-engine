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

import org.bigtester.ate.model.BaseATECaseExecE;
import org.bigtester.ate.model.IATECaseExecException;
import org.bigtester.ate.model.casestep.ITestStep;
import org.bigtester.ate.model.casestep.TestCase;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.page.page.MyWebElement;
import org.bigtester.ate.systemlogger.IATEProblemCreator;
import org.bigtester.ate.systemlogger.LogbackWriter;
import org.bigtester.ate.systemlogger.problemhandler.IProblemLogPrinter;
import org.bigtester.ate.systemlogger.problems.GenericATEProblem;
import org.bigtester.ate.systemlogger.problems.IATECaseExecProblem;
import org.bigtester.ate.systemlogger.problems.IAteProblemImpl;
import org.eclipse.jdt.annotation.Nullable;

import ch.qos.logback.classic.Level;

// TODO: Auto-generated Javadoc
/**
 * This class StepExecutionException defines ....
 * 
 * @author Peidong Hu
 * 
 */
public class StepExecutionException extends BaseATECaseExecE implements IATEProblemCreator{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6019919237360483689L;

	/** The my web element. */
	@Nullable
	private MyWebElement<?> myWebElement;
	
	/** The ate problem. */
	@Nullable
	private StepExecutionProblem ateProblem; 


	
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
			MyWebElement<?> myWebElement, IMyWebDriver myWebDriver, TestCase currentTestCase) {
		super(message, errorCode, currentTestCase, myWebDriver);
		this.myWebElement = myWebElement;
		setMyWebDriver(myWebDriver);
		setCurrentTestCase(currentTestCase);
	}
	
	public StepExecutionException(String message, String errorCode,
			IMyWebDriver myWebDriver, TestCase currentTestCase) {
		super(message, errorCode, currentTestCase, myWebDriver);
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
			MyWebElement<?> myWebElement, IMyWebDriver myWebDriver, TestCase currentTestCase, int stepIndexJumpTo) {
		super(message, errorCode, currentTestCase, myWebDriver);
		this.myWebElement = myWebElement;
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
		StepExecutionProblem retVal = ateProblem;
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
	public class StepExecutionProblem extends GenericATEProblem implements IATECaseExecProblem, IAteProblemImpl, IProblemLogPrinter{
		
		
		
		/** The problem test case. */
		private final TestCase problemTestCase;	
		/** The step exec exception. */
		private final StepExecutionException stepExecException;
		
		/**
		 * Instantiates a new page validation problem.
		 *
		 * @param source the source
		 * @param see the see
		 * @param pTc the tc
		 */
		public StepExecutionProblem(Object source, StepExecutionException see) {
			super(source, see);
			stepExecException = see;
			problemTestCase = see.getCurrentTestCase();
		}
		
		

		/**
		 * Gets the step exec exception.
		 *
		 * @return the stepExecException
		 */
		public StepExecutionException getStepExecException() {
			return stepExecException;
		}

		/**
		 * Gets the problem test case.
		 *
		 * @return the problemTestCase
		 */
		public TestCase getProblemTestCase() {
			return problemTestCase;
		}



		/**
		 * {@inheritDoc}
		 */
		@Override
		public TestCase getCurrentTestCase() {
			return this.stepExecException.getCurrentTestCase();
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
			return this.getStepExecException().getErrorCode();
		}



		/**
		* {@inheritDoc}
		*/
		@Override
		@Nullable
		public <T> T getCapability(Class<T> type) {
			if (this instanceof IATECaseExecProblem) {
				return (T) this; // NOPMD
			} else {
				return null;
			}

		}



		/**
		* {@inheritDoc}
		*/
		@Override
		public void logging(Level logLevel) {
			LogbackWriter.writeAppError("test printer interface.");
			
		}


	}



	/**
	* {@inheritDoc}
	*/
	@Override
	@Nullable
	public StepExecutionProblem getAteProblem() {
		return ateProblem;
	}


}
