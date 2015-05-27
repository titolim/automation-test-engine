/*******************************************************************************
 * ATE, Automation Test Engine
 *
 * Copyright 2015, Montreal PROT, or individual contributors as
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
package org.bigtester.ate.test.ead;

import java.util.ArrayList;

import java.util.List;

import org.bigtester.ate.constant.StepResultStatus;
import org.bigtester.ate.model.asserter.IExpectedResultAsserter;
import org.bigtester.ate.model.casestep.IJavaCodedStep;
import org.bigtester.ate.model.data.IDataParser;
import org.bigtester.ate.model.data.exception.RuntimeDataException;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.page.exception.PageValidationException2;
import org.bigtester.ate.model.page.exception.StepExecutionException2;
import org.bigtester.ate.model.page.page.IPageObject;
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

// TODO: Auto-generated Javadoc
/**
 * This class JavaCodedStepTest defines ....
 * @author Peidong Hu
 *
 */
public class JavaCodedStepFilloutTextArea implements IJavaCodedStep {

	/** The testvalue. */
	final public static String TESTVALUE = "ABCD";
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isTargetStep() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCorrelatedOptionalStepsUtilInclusiveName() {
		// TODO Auto-generated method stub
		return "";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getCorrelatedOptionalStepsUtilInclusiveIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isOptionalStep() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setOptionalStep(boolean optionalStep) {
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPageValidation() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Nullable
	public IPageObject getPageObject() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Nullable
	public IMyWebDriver getMyWebDriver() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getStepName() {
		return "unitTestingJavaStep";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Nullable
	public String getStepDescription() {
		
		return "unit testing java step";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setStepDescription(String stepDescription) {
		

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IExpectedResultAsserter> getExpectedResultAsserter() {
		return  new ArrayList<IExpectedResultAsserter>();
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Nullable
	public List<IDataParser> getDataHolders() {
		
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isElementStepFlag() {
		
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doStep() throws StepExecutionException2,
			PageValidationException2, RuntimeDataException {
		

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setStepResultStatus(StepResultStatus stepResultStatus) {
		

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StepResultStatus getStepResultStatus() {

		return StepResultStatus.PASS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isCorrectedOnTheFly() {

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCorrectedOnTheFly(boolean correctedOnTheFly) {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doStep(IMyWebDriver myWebDriver)
			throws StepExecutionException2, PageValidationException2,
			RuntimeDataException {
		WebElement webE = myWebDriver.getWebDriverInstance().findElement(By.tagName("textarea"));
		webE.clear();
		webE.sendKeys(TESTVALUE);
	}
}
