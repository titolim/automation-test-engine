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

import org.bigtester.ate.annotation.StepLoggable;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.page.page.ILastpage;
import org.bigtester.ate.model.page.page.MyWebElement;
import org.eclipse.jdt.annotation.Nullable;

// TODO: Auto-generated Javadoc
/**
 * The Class LastStep defines ....
 * 
 * @author Peidong Hu
 */
public class LastStep extends BaseTestStep implements ITestStep {

	/** The last page. */

	private ILastpage lastPage;

	/**
	 * @param pageObject
	 * @param myWebElement
	 */
	public LastStep(ILastpage pageObject, @Nullable MyWebElement<?> myWebElement, TestCase testcase) {
		super(pageObject, myWebElement, testcase);
		this.lastPage = pageObject;
	}
	/**
	 * @param pageObject
	 * @param myWebElement
	 */
	public LastStep(ILastpage pageObject, TestCase testCase) {
		super(pageObject, testCase);
		this.lastPage = pageObject;
	}
	/**
	 * Gets the last page.
	 *
	 * @return the lastPage
	 */

	public ILastpage getLastPage() {
		return lastPage;
	}

	/**
	 * Sets the last page.
	 *
	 * @param lastPage
	 *            the lastPage to set
	 */
	public void setLastPage(final ILastpage lastPage) {
		this.lastPage = lastPage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bigtester.ate.model.casestep.ITestStep#doStep()
	 */
	/**
	 * {@inheritDoc}
	 */
	@StepLoggable
	public void doStep() {
		lastPage.closeLastpage();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMyWebDriver getMyWebDriver() {
		// TODO Auto-generated method stub
		return lastPage.getMyWd();
	}

}
