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


import org.bigtester.ate.model.page.elementaction.IElementAction;
import org.bigtester.ate.model.page.elementaction.ITestObjectAction;
import org.bigtester.ate.model.data.IStepInputData;
import org.bigtester.ate.model.data.exception.RuntimeDataException;
import org.bigtester.ate.model.page.exception.PageValidationException2;
import org.bigtester.ate.model.page.exception.StepExecutionException2;
import org.bigtester.ate.model.page.page.MyWebElement;
import org.bigtester.ate.test.BigtesterProjectTest;
import org.openqa.selenium.By;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * 
 * @author Peidong Hu
 *
 */
@ContextConfiguration(locations = { "classpath:bigtesterTestNG/testSuite01/assignValueAction.xml" })
public class AssignValueEADTest extends BigtesterProjectTest {

	/**
	 * Ead test.
	 * 
	 * @throws RuntimeDataException
	 * @throws PageValidationException2
	 * @throws StepExecutionException2 
	 * @throws InterruptedException 
	 */
	@Test(priority = 1)
	public void assignValueEADTest() throws PageValidationException2,
			RuntimeDataException, StepExecutionException2, InterruptedException {
//here, we need portable file path handling for different system
		//getTestPage("file:///c:/index.html");
		getTestPage("bigtesterTestNG/aut/textarea.html");

		MyWebElement<?> assignV = (MyWebElement<?>) getApplicationContext()
				.getBean("eadAssignValue");
		assignV.doAction();
		
		MyWebElement<?> prependAssignV = (MyWebElement<?>) getApplicationContext()
				.getBean("eadAssignValuePrepend");
		prependAssignV.doAction();
		
		MyWebElement<?> appendAssignV = (MyWebElement<?>) getApplicationContext()
				.getBean("eadAssignValueAppend");
		appendAssignV.doAction();
		
		ITestObjectAction<?> prependActObj = (ITestObjectAction<?>) prependAssignV.getTestObjectAction();
		
		ITestObjectAction<?> appendActObj = (ITestObjectAction<?>) appendAssignV.getTestObjectAction();
		
		ITestObjectAction<?> replaceActObj = (ITestObjectAction<?>) assignV.getTestObjectAction();
		if (prependActObj == null || appendActObj == null || replaceActObj == null) {
			Assert.assertTrue(false);
		} else {
			IStepInputData prependInp = ((IElementAction) prependActObj).getDataValue();
			IStepInputData appendInp = ((IElementAction) appendActObj).getDataValue();
			IStepInputData replaceInp = ((IElementAction) replaceActObj).getDataValue();
			
			 if (prependInp == null || appendInp == null || replaceInp == null) {
				 Assert.assertTrue(false);
			 } else {
				 String expectedVal = prependInp.getStrDataValue()  + replaceInp.getStrDataValue() + appendInp.getStrDataValue();
				 String actualVal = getMyDriver().getWebDriverInstance().findElements(new By.ByTagName("textarea")).get(0).getAttribute("value");
				 Assert.assertTrue(expectedVal.equals(actualVal));
			 }
		
		}
		
		
	}

}
