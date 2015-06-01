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
import org.eclipse.jdt.annotation.Nullable;

// TODO: Auto-generated Javadoc
/**
 * This class StepExecutionException defines ....
 * 
 * @author Peidong Hu
 * 
 */
public class TestDataException extends AbstractATEException {

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
	
}
