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

import org.bigtester.ate.constant.EnumAssertPriority;
import org.bigtester.ate.constant.EnumAssertResult;
import org.bigtester.ate.model.data.IStepERValue;
import org.bigtester.ate.model.data.ItemCompareResult;
import org.bigtester.ate.model.data.StepErElementExistenceValue;
import org.bigtester.ate.model.data.dbtable.StepErElementExistence;
import org.bigtester.ate.model.page.atewebdriver.exception.BrowserUnexpectedException;
import org.bigtester.ate.model.page.elementfind.IElementFind;
import org.bigtester.ate.model.page.page.ATEPageFactory;
import org.bigtester.ate.model.page.page.IATEPageFactory;
import org.bigtester.ate.model.page.page.IPageObject;
import org.bigtester.ate.model.page.page.MyWebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

// TODO: Auto-generated Javadoc
/**
 * This class PageElementExistenceAsserter defines ....
 * 
 * @author Peidong Hu
 * 
 */
public class PageElementExistenceAsserter extends
		AbstractExpectedResultAsserter implements IExpectedResultAsserter,
		IStepExecutionResult {

	/** The step er value. */
	private StepErElementExistenceValue stepERValue;

	/**
	 * @param pageObj
	 */
	public PageElementExistenceAsserter(final IPageObject pageObj,
			IStepERValue stepERValue) {
		super();
		setResultPage(pageObj);
		this.stepERValue = (StepErElementExistenceValue) stepERValue.getERValue();
		setExecResult(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void assertER() {

		for (int index = 0; index < stepERValue.getValue().size(); index++) {
			IATEPageFactory ipf = ATEPageFactory.getInstance();
			StepErElementExistence sErEE = stepERValue.getValue().get(index);
			MyWebElement webelement = ipf.getMyWebElement(
					sErEE.getElementFindBy(), sErEE.getElementFindByValue(),
					getResultPage().getMyWd());
			try {
				((IElementFind) webelement.getTestObjectFinder()).doFind(
						getResultPage().getMyWd(), ((IElementFind) webelement
								.getTestObjectFinder()).getFindByValue());
				ItemCompareResult icr = new ItemCompareResult(sErEE
						.getElementFindBy().toString(),
						sErEE.getElementFindByValue(),
						EnumAssertResult.PAGEELEMENTEXIST.toString(),
						sErEE.getAssertPriority(),
						EnumAssertResult.PAGEELEMENTEXIST);
				getExecResult().getComparedItemResults().put(
						sErEE.getIdColumn(), icr);
				super.appendAssertReportMSG(icr);
			} catch (NoSuchElementException | TimeoutException |BrowserUnexpectedException et) {
				ItemCompareResult icr = new ItemCompareResult(sErEE
						.getElementFindBy().toString(),
						sErEE.getElementFindByValue(),
						EnumAssertResult.PAGEELEMENTNOTEXIST.toString(),
						sErEE.getAssertPriority(),
						EnumAssertResult.PAGEELEMENTNOTEXIST);
				getExecResult().getComparedItemResults().put(
						sErEE.getIdColumn(), icr);
				getExecResult().getFailedItemResults().put(sErEE.getIdColumn(),
						icr);
				EnumAssertPriority failedPriority = getStepERValue().getValue()
						.get(index).getAssertPriority();
				if (failedPriority.equals(EnumAssertPriority.HIGH)) {
					setFlagFailCase(true);
				}
				super.appendAssertReportMSG(icr);

			}
		}

	}

	/**
	 * @return the stepERValue
	 */
	public StepErElementExistenceValue getStepERValue() {
		return stepERValue;
	}

	/**
	 * @param stepERValue
	 *            the stepERValue to set
	 */
	public void setStepERValue(StepErElementExistenceValue stepERValue) {
		this.stepERValue = stepERValue;
	}

	

}
