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
import org.bigtester.ate.model.page.exception.StepExecutionException;
import org.bigtester.ate.model.page.page.MyWebElement;
import org.bigtester.ate.systemlogger.problems.IATECaseExecProblem;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

// TODO: Auto-generated Javadoc
/**
 * The Class TestStep defines ....
 * 
 * @author Peidong Hu
 */
public class ElementTestStep extends BaseTestStep implements IElementStep {

	/** The my web element. */

	protected MyWebElement<?> myWebElement;

	/**
	 * @param myWebElement
	 */
	public ElementTestStep(MyWebElement<?> myWebElement) {
		super();
		this.myWebElement = myWebElement;
		setElementStepFlag(true);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws RuntimeDataException
	 * @throws PageValidationException
	 */
	@StepLoggable
	public void doStep() throws StepExecutionException,
			PageValidationException2, RuntimeDataException {
		try {
			getMyWebElement().doAction();
			super.parseDataHolder();
		} catch (NoSuchElementException | TimeoutException e) {
			StepExecutionException pve = new StepExecutionException(
					ExceptionMessage.MSG_WEBELEMENT_NOTFOUND
							+ ExceptionMessage.MSG_SEPERATOR + e.getMessage(),
					ExceptionErrorCode.WEBELEMENT_NOTFOUND,
					this.getMyWebElement(), this.getMyWebDriver(),
					GlobalUtils.findTestCaseBean(getApplicationContext()), e);
			IATECaseExecProblem prb =pve.initAteProblemInstance(this);
			prb.setFatalProblem(false);
			throw pve;
		} catch (Throwable otherE) {//NOPMD
			getApplicationContext().publishEvent(
					new StepUnexpectedAlertEvent(this, otherE));
			StepExecutionException pve = new StepExecutionException(
					StepExecutionException.MSG
							+ ExceptionMessage.MSG_SEPERATOR + otherE.getMessage(),
							StepExecutionException.CODE,
					this.getMyWebElement(), this.getMyWebDriver(),
					GlobalUtils.findTestCaseBean(getApplicationContext()), otherE);
			IATECaseExecProblem prb = pve.initAteProblemInstance(this);
			prb.setFatalProblem(true);
			throw pve;
		}

		List<IExpectedResultAsserter> asserterList = getExpectedResultAsserter();

		boolean flagThrowE = false;
		List<IExpectedResultAsserter> listAsserters = new ArrayList<IExpectedResultAsserter>();
		for (int i = 0; i < asserterList.size(); i++) {
			asserterList.get(i).assertER();
			if (asserterList.get(i).getExecResult().isFlagFailCase()) {
				flagThrowE = true;
			}
			listAsserters.add(asserterList.get(i));
		}
		if (flagThrowE && isTargetStep()) {
			PageValidationException2 pve = new PageValidationException2(
					ExceptionMessage.MSG_PAGE_VALIDATION_ERROR_HIGH,
					ExceptionErrorCode.PAGEVALIDATION_HIGH, listAsserters,
					asserterList.get(0).getResultPage().getMyWd(),
					GlobalUtils.findTestCaseBean(getApplicationContext()));
			throw pve;
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMyWebDriver getMyWebDriver() {
		return getMyWebElement().getMyWd();
	}

	/**
	 * Gets the my web element.
	 * 
	 * @return the myWebElement
	 */

	public MyWebElement<?> getMyWebElement() {
		return myWebElement;
	}

	/**
	 * Sets the my web element.
	 * 
	 * @param myWebElement
	 *            the myWebElement to set
	 */
	public void setMyWebElement(final MyWebElement<?> myWebElement) {
		this.myWebElement = myWebElement;
	}
}
