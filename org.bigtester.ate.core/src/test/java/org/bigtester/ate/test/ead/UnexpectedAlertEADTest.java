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

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.model.casestep.HomeStep;

import org.bigtester.ate.model.casestep.ElementTestStep;

import org.bigtester.ate.model.data.exception.RuntimeDataException;
import org.bigtester.ate.model.page.exception.PageValidationException2;
import org.bigtester.ate.model.page.exception.StepExecutionException2;
import org.bigtester.ate.model.page.page.MyWebElement;
import org.bigtester.ate.test.BigtesterProjectTest;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;


/**
 * This class EadSaveAllCookiesTest testing the save and load cookies
 * elementaction definition. It is a good example of how to use real spring bean
 * to unit test or integration test.
 * 
 * To run this test, the web site bigtester.com has to be alive and Internet connection is required.
 * 
 * @author Peidong Hu
 *
 */
@ContextConfiguration(locations = { "classpath:bigtesterTestNG/testSuite01/unexpectedAlertHandling.xml" })
public class UnexpectedAlertEADTest extends BigtesterProjectTest {

	/**
	 * Ead test.
	 * 
	 * @throws RuntimeDataException
	 * @throws PageValidationException2
	 * @throws StepExecutionException2 
	 */
	@Test(priority = 1)
	public void eadHandleUnexpectedAlertTest() throws PageValidationException2,
			RuntimeDataException, StepExecutionException2 {

		WebDriver webDriver = getMyDriver().getWebDriverInstance();

		HomeStep homeStep = (HomeStep) GlobalUtils
				.getTargetObject(getApplicationContext().getBean(
						"stepOpenAlertHomePage"));
		homeStep.doStep();


		MyWebElement<?> clickAlertWinLink = (MyWebElement<?>) getApplicationContext()
				.getBean("eadClickAlertWinLink");
		clickAlertWinLink.doAction();
		
		ElementTestStep closeStep = (ElementTestStep) GlobalUtils
				.getTargetObject(getApplicationContext().getBean(
						"stepCloseAlertWindow"));
		
		closeStep.doStep();
		

	}

	
	
	/**
	 * {@inheritDoc}
	 */
	@AfterClass
	public void tearDown() {
	
	}

	
}
