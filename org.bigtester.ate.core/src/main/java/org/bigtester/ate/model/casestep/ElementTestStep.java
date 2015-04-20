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
package org.bigtester.ate.model.casestep;

import java.util.ArrayList;
import java.util.List;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.annotation.StepLoggable;
import org.bigtester.ate.constant.ExceptionErrorCode;
import org.bigtester.ate.constant.ExceptionMessage;
import org.bigtester.ate.model.asserter.IExpectedResultAsserter;
import org.bigtester.ate.model.data.exception.RuntimeDataException;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.page.exception.PageValidationException2;
import org.bigtester.ate.model.page.exception.StepExecutionException2;
import org.bigtester.ate.model.page.page.IPageObject;
import org.bigtester.ate.model.page.page.MyWebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

// TODO: Auto-generated Javadoc
/**
 * The Class TestStep defines ....
 * 
 * @author Peidong Hu
 */
public class ElementTestStep extends BaseTestStep implements ITestStep {

	/**
	 * Gets the my web element.
	 * 
	 * @return the myWebElement
	 */
	@Override
	public final MyWebElement<?> getMyWebElement() throws IllegalStateException{
		final MyWebElement<?> myWebElement2 = myWebElement;
		if ( null == myWebElement2 ) {
			throw new IllegalStateException("Element Test Step MyWebElement can't be null.");
		} else {
			return myWebElement2;
		}
	}
	/**
	 * @param pageObject
	 * @param myWebElement
	 */
	public ElementTestStep(IPageObject pageObject, MyWebElement<?> myWebElement, TestCase testCase) {
		super(pageObject, myWebElement, testCase);
	}

	/**
	 * @param myWebElement
	 */
	public ElementTestStep( MyWebElement<?> myWebElement, TestCase testCase) {
		super( myWebElement, testCase);
	}
	/**
	 * {@inheritDoc}
	 * @throws RuntimeDataException 
	 * @throws PageValidationException 
	 */
	@StepLoggable
	public void doStep() throws StepExecutionException2, PageValidationException2, RuntimeDataException {
		try {
			getMyWebElement().doAction();
//			if (getExpectedResultAsserter() != null) {
//				for (int i=0; i < getExpectedResultAsserter().size(); i++) {
//					getExpectedResultAsserter().get(i).assertER();
//				}
//			}
			super.parseDataHolder();
		} catch (NoSuchElementException e) {
			StepExecutionException2 pve = new StepExecutionException2(
					ExceptionMessage.MSG_WEBELEMENT_NOTFOUND
							+ ExceptionMessage.MSG_SEPERATOR + e.getMessage(),
					ExceptionErrorCode.WEBELEMENT_NOTFOUND,
					this.getMyWebElement(),
					this.getMyWebDriver(),
					GlobalUtils.findTestCaseBean(getApplicationContext()));
			pve.initCause(e);
			throw pve;
		} catch (TimeoutException et) {
			StepExecutionException2 pve = new StepExecutionException2(
					ExceptionMessage.MSG_WEBELEMENT_NOTFOUND
							+ ExceptionMessage.MSG_SEPERATOR + et.getMessage(),
					ExceptionErrorCode.WEBELEMENT_NOTFOUND,
					this.getMyWebElement(),
					this.getMyWebDriver(),
					GlobalUtils.findTestCaseBean(getApplicationContext()));
			pve.initCause(et);
			throw pve;
		}
		
		List<IExpectedResultAsserter> asserterList = getExpectedResultAsserter();
		if ( null != asserterList) {
			boolean flagThrowE = false;
			List<IExpectedResultAsserter> listAsserters = new ArrayList<IExpectedResultAsserter>();
			for (int i=0; i < asserterList.size(); i++) {
				asserterList.get(i).assertER();
				if (asserterList.get(i).getExecResult().isFlagFailCase()) {
					flagThrowE = true;
				}
				listAsserters.add(asserterList.get(i));
			}
			if (flagThrowE && isTargetStep()) {
				PageValidationException2 pve = new PageValidationException2(
						ExceptionMessage.MSG_PAGE_VALIDATION_ERROR_HIGH,
						ExceptionErrorCode.PAGEVALIDATION_HIGH,
						listAsserters, asserterList.get(0).getResultPage().getMyWd(),
						GlobalUtils.findTestCaseBean(getApplicationContext()));
				throw pve;
			}
			
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMyWebDriver getMyWebDriver() {
		return getMyWebElement().getMyWd();
	}
	
}
