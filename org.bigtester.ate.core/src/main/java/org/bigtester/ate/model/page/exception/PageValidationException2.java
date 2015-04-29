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

import java.util.List;

import org.bigtester.ate.model.BaseATECaseExecE;
import org.bigtester.ate.model.asserter.IExpectedResultAsserter;
import org.bigtester.ate.model.casestep.TestCase;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.page.elementfind.IElementFind;
import org.eclipse.jdt.annotation.Nullable;

// TODO: Auto-generated Javadoc
/**
 * This class PageValidationException defines ....
 * 
 * @author Peidong Hu
 *
 */
public class PageValidationException2 extends BaseATECaseExecE {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7144577815429959503L;

	/** The page property. */
	@Nullable
	private transient String pageProperty;
	/** The element find. */
	@Nullable
	private transient IElementFind elementFind;

	/** The list asserters. */
	@Nullable
	private List<IExpectedResultAsserter> listAsserters;

		
	/**
	 * @return the listAsserters
	 */
	@Nullable
	public List<IExpectedResultAsserter> getListAsserters() {
		return listAsserters;
	}

	/**
	 * @param listAsserters
	 *            the listAsserters to set
	 */
	public void setListAsserters(List<IExpectedResultAsserter> listAsserters) {
		this.listAsserters = listAsserters;
	}

	/**
	 * Gets the page property.
	 *
	 * @return the pageProperty
	 */
	@Nullable
	public String getPageProperty() {
		return pageProperty;
	}

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
	public PageValidationException2(String message, String errorCode,
			String pageProperty, IMyWebDriver myWebDriver,
			TestCase currentTestCase) {
		super(message, errorCode, currentTestCase, myWebDriver);
		this.pageProperty = pageProperty;

	}

	/**
	 * Instantiates a new page validation exception2.
	 *
	 * @param message
	 *            the message
	 * @param errorCode
	 *            the error code
	 * @param eFind
	 *            the e find
	 * @param myWebDriver
	 *            the my web driver
	 * @param currentTestCase
	 *            the current test case
	 */
	public PageValidationException2(String message, String errorCode,
			IElementFind eFind, IMyWebDriver myWebDriver,
			TestCase currentTestCase) {
		super(message, errorCode, currentTestCase, myWebDriver);
		elementFind = eFind;

	}

	/**
	 * Instantiates a new page validation exception2.
	 *
	 * @param message
	 *            the message
	 * @param errorCode
	 *            the error code
	 * @param listAsserters
	 *            the list asserters
	 * @param myWebDriver
	 *            the my web driver
	 * @param currentTestCase
	 *            the current test case
	 */
	public PageValidationException2(String message, String errorCode,
			List<IExpectedResultAsserter> listAsserters,
			IMyWebDriver myWebDriver, TestCase currentTestCase) {
		super(message, errorCode, currentTestCase, myWebDriver);
		this.listAsserters = listAsserters;

	}

	

	/**
	 * Gets the element find.
	 *
	 * @return the elementFind
	 */
	@Nullable
	public IElementFind getElementFind() {
		return elementFind;
	}

}
