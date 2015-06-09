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
package org.bigtester.ate.model.data.exception;

import org.bigtester.ate.model.AbstractATEException;
import org.bigtester.ate.systemlogger.IATEProblemCreator;
import org.bigtester.ate.systemlogger.LogbackWriter;
import org.bigtester.ate.systemlogger.problemhandler.IProblemLogPrinter;
import org.bigtester.ate.systemlogger.problems.GenericATEProblem;
import org.bigtester.ate.systemlogger.problems.IATEProblem;
import org.eclipse.jdt.annotation.Nullable;

import ch.qos.logback.classic.Level;

// TODO: Auto-generated Javadoc
/**
 * This class StepExecutionException defines ....
 * 
 * @author Peidong Hu
 * 
 */
public class TestDataException extends AbstractATEException implements IATEProblemCreator{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2675548817712757408L;
	
	
	/** The message. */
	private String message; //NOPMD
	
	
	
	/** The test step name. */
	@Nullable
	private String testStepName; //NOPMD
	
	
	/** The test case name. */
	@Nullable
	private String testCaseName; //NOPMD

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the testStepName
	 */
	public String getTestStepName() {
		final String retVal = testStepName;
		if (null == retVal) {
			throw new IllegalStateException("teststepName is not correctly populated");
			
		} else {
			return retVal;
		}
	}

	/**
	 * @param testStepName the testStepName to set
	 */
	public void setTestStepName(@Nullable String testStepName) {
		this.testStepName = testStepName;
	}

	/**
	 * @return the testCaseName
	 */
	public String getTestCaseName() {
		final String retVal = testCaseName;
		if (null == retVal) {
			throw new IllegalStateException("testcasename is not correctly populated");
			
		} else {
			return retVal;
		}
	}

	/**
	 * @param testCaseName the testCaseName to set
	 */
	public void setTestCaseName(@Nullable String testCaseName) {
		this.testCaseName = testCaseName;
	}

	/**
	 * Instantiates a new step execution exception.
	 *
	 * @param message            the message
	 * @param errorCode            the error code
	 */
	public TestDataException(String message, String errorCode) {
		super(message, errorCode);
		this.message = message;
		
	}
	
	/**
	 * Instantiates a new test data exception.
	 *
	 * @param message the message
	 * @param errorCode the error code
	 * @param cause the cause
	 */
	public TestDataException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
		this.message = message;
		
	}
	
	/**
	 * This class StepExecutionProblem defines ....
	 * 
	 * @author Peidong Hu
	 * 
	 */
	public class TestDataProblem extends GenericATEProblem implements IATEProblem, IProblemLogPrinter{

		/** The test data exception. */
		private final transient TestDataException testDataException;

		/**
		 * Instantiates a new page validation problem.
		 * 
		 * @param source
		 *            the source
		 * @param tde
		 *            the see
		 */
		public TestDataProblem(Object source, TestDataException tde) {
			super(source, tde);
			testDataException = tde;
		}

		/**
		 * Gets the step exec exception.
		 * 
		 * @return the step exec exception
		 */
		public TestDataException getStepExecException() {
			return testDataException;
		}

		/**
		* {@inheritDoc}
		*/
		@Override
		public void logging(Level logLevel) {
			
			LogbackWriter.writeAppError("test data test printer interface.");
		}

	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public IATEProblem initAteProblemInstance(Object ateProblemLocatin) {
		TestDataProblem retVal = (TestDataProblem) ateProblem;
		if (null == retVal) {
			retVal = new TestDataProblem(ateProblemLocatin, this);
			ateProblem = retVal;
		}
		return retVal;
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	@Nullable
	public IATEProblem getAteProblem() {
		return (TestDataProblem) ateProblem;
	}


	
}
