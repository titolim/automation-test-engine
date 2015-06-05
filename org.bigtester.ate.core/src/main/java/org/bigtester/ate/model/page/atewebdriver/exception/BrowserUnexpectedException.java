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
package org.bigtester.ate.model.page.atewebdriver.exception;

import org.bigtester.ate.model.AbstractATEException;
import org.eclipse.jdt.annotation.Nullable;

// TODO: Auto-generated Javadoc
/**
 * This class StepExecutionException defines ....
 * 
 * @author Peidong Hu
 * 
 */
public class BrowserUnexpectedException extends AbstractATEException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6192054488554647486L;
	
	/** The error code. */
	public static String errorCode = "6192054488554647486L";
	
	/** The super cause. */
	@Nullable
	private Throwable superCause;

	
	/**
	 * Instantiates a new step execution exception2.
	 *
	 * @param message the message
	 * @param errorCode the error code
	 * @param myWebElement the my web element
	 * @param myWebDriver the my web driver
	 * @param currentTestCase the current test case
	 */
	public BrowserUnexpectedException(Throwable cause, String message) {
		super(message, errorCode);
		setSuperCause(cause);
	}
	
	
	
	/**
	 * @return the superCause
	 */
	@Nullable
	public Throwable getSuperCause() {
		return superCause;
	}

	/**
	 * @param superCause the superCause to set
	 */
	public void setSuperCause(Throwable superCause) {
		this.superCause = superCause;
	}


}
