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

import org.bigtester.ate.model.AbstractATEException;
import org.bigtester.ate.model.page.atewebdriver.WindowFrame;

// TODO: Auto-generated Javadoc
/**
 * This class PageValidationException defines ....
 * 
 * @author Peidong Hu
 *
 */
public class PageFrameRefreshException extends AbstractATEException {

	/** The frame. */
	final private WindowFrame frame;
	/**
	 * 
	 */
	private static final long serialVersionUID = -1261126553233428668L;


	/**
	 * Instantiates a new page validation exception2.
	 *
	 * @param message
	 *            the message
	 * @param errorCode
	 *            the error code
	 * @param pageProperty
	 *            the page property
	 * @param myWebDriver
	 *            the my web driver
	 * @param currentTestCase
	 *            the current test case
	 */
	public PageFrameRefreshException(String message, String errorCode, WindowFrame frame) {
		super(message, errorCode);
		this.frame = frame;

	}


	/**
	 * @return the frame
	 */
	public WindowFrame getFrame() {
		return frame;
	}


}
