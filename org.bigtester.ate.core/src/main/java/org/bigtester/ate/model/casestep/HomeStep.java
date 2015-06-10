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
import org.bigtester.ate.annotation.StepLoggable.ATELogLevel;
import org.bigtester.ate.constant.ExceptionErrorCode;
import org.bigtester.ate.constant.ExceptionMessage;
import org.bigtester.ate.model.asserter.IExpectedResultAsserter;
import org.bigtester.ate.model.data.exception.RuntimeDataException;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.page.exception.PageValidationException2;
import org.bigtester.ate.model.page.page.IHomepage;

// TODO: Auto-generated Javadoc
/**
 * The Class HomeStep defines ....
 * 
 * @author Peidong Hu
 */
public class HomeStep extends BaseTestStep implements ITestStep {

	/** The homepg. */
	private IHomepage homePage;

	/**
	 * Instantiates a new home step.
	 *
	 * @param pageObject
	 *            the page object
	 * @param myWebElement
	 *            the my web element
	 */
	public HomeStep(IHomepage pageObject) {
		super();
		this.homePage = pageObject;
	}

	/**
	 * Gets the homepg.
	 * 
	 * @return the homepg
	 */
	public IHomepage getHomePage() {
		return homePage;
	}

	/**
	 * Sets the homepg.
	 * 
	 * @param homepg
	 *            the new homepg
	 */
	public void setHomePage(final IHomepage homepg) {
		this.homePage = homepg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bigtester.ate.model.casestep.ITestStep#doStep()
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @throws RuntimeDataException
	 * @throws PageValidationException
	 */
	@StepLoggable(level = ATELogLevel.INFO)
	@Override
	public void doStep() throws PageValidationException2, RuntimeDataException {
		homePage.startHomepage();
		super.parseDataHolder();
		List<IExpectedResultAsserter> asserters = getExpectedResultAsserter();

		boolean flagThrowE = false;// NOPMD
		List<IExpectedResultAsserter> listAsserters = new ArrayList<IExpectedResultAsserter>();// NOPMD
		for (int i = 0; i < asserters.size(); i++) {
			listAsserters.add(asserters.get(i));
			asserters.get(i).assertER();
			if (asserters.get(i).getExecResult().isFlagFailCase()) {
				flagThrowE = true;// NOPMD
			}

		}
		if (flagThrowE && isTargetStep()) {
			PageValidationException2 pve = new PageValidationException2(
					ExceptionMessage.MSG_PAGE_VALIDATION_ERROR_HIGH,
					ExceptionErrorCode.PAGEVALIDATION_HIGH, listAsserters,
					asserters.get(0).getResultPage().getMyWd(),
					GlobalUtils.findTestCaseBean(getApplicationContext()));
			throw pve;
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMyWebDriver getMyWebDriver() {
		// TODO Auto-generated method stub
		return homePage.getMyWd();
	}

}
