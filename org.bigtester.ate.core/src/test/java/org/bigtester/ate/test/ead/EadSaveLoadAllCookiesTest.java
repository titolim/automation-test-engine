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

// TODO: Auto-generated Javadoc
/**
 * This class EadSaveAllCookiesTest defines ....
 * @author Peidong Hu
 *
 */
@ContextConfiguration(locations = { "classpath:bigtesterTestNG/testSuite01/homePageValidation.xml" })
public class EadSaveLoadAllCookiesTest extends BigtesterProjectTest{
	
	/** The Constant FILETOSAVE. */
	final public static String FILETOSAVE = "myFile"; 
	
	/** The original cookies. */
	private transient Set<Cookie> originalCookies = new HashSet<Cookie>();
	
	
	/**
	 * Ead test.
	 * @throws RuntimeDataException 
	 * @throws PageValidationException2 
	 */
	@Test (priority = 1)
	public void eadSaveCookiesTest() throws PageValidationException2, RuntimeDataException{
		
		WebDriver webDriver = getMyDriver().getWebDriverInstance();
		
		webDriver.manage().deleteAllCookies();
		Set<Cookie> emptyCookies = webDriver.manage().getCookies();
		Assert.assertEquals (emptyCookies.size(), 0);
		
		HomeStep homeStep = (HomeStep) GlobalUtils.getTargetObject( getApplicationContext().getBean("stepOpenBigtesterHomePage"));
		homeStep.doStep();

		Set<Cookie> homepageCookies = webDriver.manage().getCookies();
		Assert.assertTrue(!homepageCookies.isEmpty()); 	
		
		originalCookies = homepageCookies;
		
		MyWebElement<?> testEad = (MyWebElement<?>) getApplicationContext().getBean("eadSaveCookies");
		testEad.doAction();
		File fileSaved = new File(EadSaveLoadAllCookiesTest.FILETOSAVE);
		Assert.assertTrue(fileSaved.exists());
		
		
	}
	
	/**
	 * Ead load cookies test.
	 * @throws RuntimeDataException 
	 * @throws PageValidationException2 
	 * @throws StepExecutionException2 
	 */
	@Test (priority = 2)
	public void eadLoadCookiesTest() throws PageValidationException2, RuntimeDataException, StepExecutionException2 {

		WebDriver webDriver = getMyDriver().getWebDriverInstance();
		webDriver.manage().deleteAllCookies();
		Assert.assertTrue(webDriver.manage().getCookies().isEmpty());
		
		MyWebElement<?> testEad = (MyWebElement<?>) getApplicationContext().getBean("eadLoadCookies");
		testEad.doAction();

		Set<Cookie> loadedCookies = webDriver.manage().getCookies();
		Assert.assertTrue(!originalCookies.isEmpty());
		Assert.assertEquals(originalCookies, loadedCookies);
		
		
		
		
			
	}
	
	/**
	* {@inheritDoc}
	*/
	@AfterClass
	public void tearDown() {
		File fileSaved = new File(EadSaveLoadAllCookiesTest.FILETOSAVE);
		if (fileSaved.exists()) fileSaved.delete();
	}

	/**
	 * @return the originalCookies
	 */
	public Set<Cookie> getOriginalCookies() {
		return originalCookies;
	}
}
