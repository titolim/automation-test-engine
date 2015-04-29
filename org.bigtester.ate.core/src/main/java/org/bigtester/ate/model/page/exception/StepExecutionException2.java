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
import org.bigtester.ate.model.casestep.TestCase;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.page.page.MyWebElement;

// TODO: Auto-generated Javadoc
/**
 * This class StepExecutionException defines ....
 * 
 * @author Peidong Hu
 * 
 */
public class StepExecutionException2 extends BaseATECaseExecE {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6019919237360483689L;

	/** The my web element. */
	private final MyWebElement<?> myWebElement;
	


	
	/**
	 * Instantiates a new step execution exception2.
	 *
	 * @param message the message
	 * @param errorCode the error code
	 * @param myWebElement the my web element
	 * @param myWebDriver the my web driver
	 * @param currentTestCase the current test case
	 */
	public StepExecutionException2(String message, String errorCode,
			MyWebElement<?> myWebElement, IMyWebDriver myWebDriver, TestCase currentTestCase) {
		super(message, errorCode, currentTestCase, myWebDriver);
		this.myWebElement = myWebElement;
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
	public StepExecutionException2(String message, String errorCode,
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
	public MyWebElement<?> getMyWebElement() {
		return myWebElement;
	}

}
