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
package org.bigtester.ate.test.model.page.elementaction;

import org.bigtester.ate.GlobalUtils;

import org.bigtester.ate.model.page.atewebdriver.exception.BrowserUnexpectedException;
import org.bigtester.ate.model.page.elementaction.IElementAction;
import org.bigtester.ate.model.page.elementaction.ITestObjectAction;
import org.bigtester.ate.model.data.IStepInputData;
import org.bigtester.ate.model.data.exception.RuntimeDataException;
import org.bigtester.ate.model.page.exception.PageValidationException;
import org.bigtester.ate.model.page.exception.StepExecutionException;
import org.bigtester.ate.model.page.page.MyWebElement;
import org.bigtester.ate.test.AbstractBigtesterStepTest;
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.By;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 
 * @author Chen Chen
 *
 */
@ContextConfiguration(locations = { "classpath:bigtesterTestNG/testSuite01/buttonClick.xml" })
public class ClickActionTest extends AbstractBigtesterStepTest {

	/** The my web e. */
	@Nullable
	private transient MyWebElement<?> myWebE;
	/**
	 * Ead test.
	 * 
	 * @throws RuntimeDataException
	 * @throws PageValidationException
	 * @throws StepExecutionException 
	 * @throws InterruptedException 
	 * @throws BrowserUnexpectedException 
	 */
	@Test(priority = 1)
	public void buttonClickEADTest() throws PageValidationException,
			RuntimeDataException, StepExecutionException, InterruptedException, BrowserUnexpectedException {
		getTestPage("bigtesterTestNG/aut/button.html");

		MyWebElement<?> buttonC = (MyWebElement<?>) getApplicationContext()
				.getBean("eadTestButtonClick");
		//buttonClick.doAction();
		this.runElementTestStep(buttonC);		
				
		ITestObjectAction<?> moveActObj = (ITestObjectAction<?>) buttonC.getTestObjectAction();
		if  (moveActObj == null) {
			Assert.assertTrue(false);
		} else {			
			IStepInputData moveInp = ((IElementAction) moveActObj).getDataValue();
			
			 if (moveInp == null) {
				 Assert.assertTrue(false);
			 } else {
				 String expectedVal = moveInp.getStrDataValue();
				 String actualVal = getMyDriver().getWebDriverInstance().findElements(new By.ByTagName("button")).get(0).getAttribute("value");
				 Assert.assertTrue(expectedVal.equals(actualVal));
			 }		
		}				
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public MyWebElement<?> getMyWebElement() {
			final MyWebElement<?> myWebE2 = myWebE;
			if (myWebE2 == null) {
				throw GlobalUtils.createNotInitializedException("myWebe");
			} else {
				return myWebE2;
			}
		}
}
