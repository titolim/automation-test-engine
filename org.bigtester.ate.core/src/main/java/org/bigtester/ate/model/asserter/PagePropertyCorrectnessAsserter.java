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
package org.bigtester.ate.model.asserter;


import org.bigtester.ate.constant.AssertType;
import org.bigtester.ate.constant.EnumAssertPriority;
import org.bigtester.ate.constant.EnumAssertResult;
import org.bigtester.ate.constant.PagePropertyType;
import org.bigtester.ate.model.data.IStepERValue;
import org.bigtester.ate.model.data.ItemCompareResult;
import org.bigtester.ate.model.data.StepErPagePropertyValue;
import org.bigtester.ate.model.data.dbtable.StepErPageProperty;
import org.bigtester.ate.model.page.page.IPageObject;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

// TODO: Auto-generated Javadoc
/**
 * This class PagePropertyCorrectnessAsserter validates non web element property
 * value including 1) cookie; 2) page title
 * 
 * @author Peidong Hu
 *
 */
public class PagePropertyCorrectnessAsserter extends
		AbstractExpectedResultAsserter implements IExpectedResultAsserter, IStepExecutionResult {
	
	/** The step er value. */
	private StepErPagePropertyValue stepERValue;
	/**
	 * @param pageObj
	 */
	public PagePropertyCorrectnessAsserter(final IPageObject pageObj, IStepERValue stepERValue) {
		super();
		setResultPage(pageObj);
		this.stepERValue = (StepErPagePropertyValue) stepERValue.getERValue();
		setExecResult(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void assertER() {
	
		WebDriver webDriver = getResultPage().getMyWd().getWebDriver();// NOPMD
		if (null == webDriver) {
			throw new IllegalStateException("webDriver is not correctly populated.");
		}
		for (int i = 0; i < stepERValue.getValue().size(); i++) {
			StepErPageProperty sErPP = stepERValue.getValue().get(i);
			if (sErPP.getTestDataContext()
					.getContextFieldValue()
					.equalsIgnoreCase(AssertType.PAGE_PROPERTY_CORRECTNESS)) {
				String assertProperty = sErPP
						.getAssertProperty();
				boolean correctFlag;
				if (PagePropertyType.COOKIE.equalsIgnoreCase(assertProperty)) {

					Cookie cki = new Cookie(sErPP
							.getAssertValue(), sErPP.getAssertValue());
					if (webDriver.manage().getCookies().contains(cki)) {
						correctFlag = true;
						
					} else {
						correctFlag = false;
					}
				} else if (PagePropertyType.PAGE_TITLE
						.equalsIgnoreCase(assertProperty)) {
					if (webDriver.getTitle()
							.equals(sErPP
									.getAssertValue())) {
						correctFlag = true;
						
					} else {
						correctFlag = false;
						
					}
				} else {
					correctFlag = true;
				}
				if (correctFlag) {
					ItemCompareResult icr = new ItemCompareResult(sErPP.getAssertProperty()
							, sErPP.getAssertValue(), EnumAssertResult.PAGEPROPERTYCORRECT.toString(),
							sErPP.getAssertPriority(), EnumAssertResult.PAGEPROPERTYCORRECT);
					getExecResult().getComparedItemResults().put(
							sErPP.getIdColumn(),
							icr);
					super.appendAssertReportMSG(icr);
				} else {
					ItemCompareResult icr = new ItemCompareResult(sErPP.getAssertProperty()
							, sErPP.getAssertValue(), EnumAssertResult.PAGEPROPERTYNOTCORRECT.toString(),
							sErPP.getAssertPriority(), EnumAssertResult.PAGEPROPERTYNOTCORRECT);
					getExecResult().getComparedItemResults().put(
							sErPP.getIdColumn(),
							icr);
					getExecResult().getFailedItemResults().put(sErPP.getIdColumn(),
							icr);
					EnumAssertPriority failPriority = getStepERValue().getValue().get(i).getAssertPriority();
					if (failPriority.equals(EnumAssertPriority.HIGH)) {
						setFlagFailCase(true);
					}
					super.appendAssertReportMSG(icr);
				}
			}
		}
	}

	/**
	 * @return the stepERValue
	 */
	
	public StepErPagePropertyValue getStepERValue() {
		return stepERValue;
	}

	/**
	 * @param stepERValue the stepERValue to set
	 */
	public void setStepERValue(StepErPagePropertyValue stepERValue) {
		this.stepERValue = stepERValue;
	}
}
