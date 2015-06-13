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
package org.bigtester.ate.model;

import org.bigtester.ate.model.casestep.TestCase;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;

// TODO: Auto-generated Javadoc
/**
 * This class AbstractATECEException defines ....
 * @author Peidong Hu
 *
 */
public class BaseATECaseExecE extends AbstractATEException implements IATECaseExecException{


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7308181663474968945L;

	/** The current test case. */
	private TestCase currentTestCase;
	
	/** The my web driver. */
	private IMyWebDriver myWebDriver;
	
	/** The step index jump to. */
	private int stepIndexJumpTo = -1;
	
	/**
	 * Instantiates a new abstract ate case exec e.
	 *
	 * @param message the message
	 * @param errorCode the error code
	 */
	public BaseATECaseExecE(final String message, final String errorCode, final TestCase currentTestCase, final IMyWebDriver myWebDriver) {
		super(message, errorCode);
		this.currentTestCase = currentTestCase;
		this.myWebDriver = myWebDriver;
		this.setOriginalStepRaisingException(currentTestCase.getCurrentTestStep());
	}
	
	/**
	 * Instantiates a new base ate case exec e.
	 *
	 * @param message the message
	 * @param errorCode the error code
	 * @param currentTestCase the current test case
	 * @param myWebDriver the my web driver
	 * @param cause the cause
	 */
	public BaseATECaseExecE(final String message, final String errorCode, final TestCase currentTestCase, final IMyWebDriver myWebDriver, Throwable cause) {
		super(message, errorCode, cause);
		this.currentTestCase = currentTestCase;
		this.myWebDriver = myWebDriver;
		this.setOriginalStepRaisingException(currentTestCase.getCurrentTestStep());
	}
	/**
	 * Gets the current test case.
	 *
	 * @return the currentTestCase
	 */
	public TestCase getCurrentTestCase() {
		return currentTestCase;
	}
	
	/**
	 * Sets the current test case.
	 *
	 * @param currentTestCase the currentTestCase to set
	 */
	public void setCurrentTestCase(TestCase currentTestCase) {
		this.currentTestCase = currentTestCase;
	}

	/**
	 * @return the myWebDriver
	 */
	public IMyWebDriver getMyWebDriver() {
		return myWebDriver;
	}

	/**
	 * @param myWebDriver the myWebDriver to set
	 */
	public void setMyWebDriver(IMyWebDriver myWebDriver) {
		this.myWebDriver = myWebDriver;
	}
	/**
	 * @return the stepIndexJumpTo
	 */
	public int getStepIndexJumpTo() {
		return stepIndexJumpTo;
	}
	/**
	 * @param stepIndexJumpTo the stepIndexJumpTo to set
	 */
	public void setStepIndexJumpTo(int stepIndexJumpTo) {
		this.stepIndexJumpTo = stepIndexJumpTo;
	}
}
