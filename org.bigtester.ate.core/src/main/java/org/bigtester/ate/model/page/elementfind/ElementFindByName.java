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
package org.bigtester.ate.model.page.elementfind;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


// TODO: Auto-generated Javadoc
/**
 * The Class ElementFindByName defines the way to find element by name
 * 
 * @author Peidong Hu
 */
public class ElementFindByName extends AbstractElementFind implements IElementFind, ITestObjectFinderImpl {
	
	/**
	 * @param findByValue
	 */
	public ElementFindByName(String findByValue) {
		super(findByValue);
		// TODO Auto-generated constructor stub
	}

	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebElement doFind(IMyWebDriver myWebDriver, final String findByValue) {
		final By findBy = By.name(findByValue);
		if (null == findBy)
			throw GlobalUtils.createInternalError("selenium By creation");
		return findElement(findBy, myWebDriver);
//		
//		WebDriver webD = myWebDriver.getWebDriver();
//		if (null == webD) {
//			throw new IllegalStateException(
//					"web driver is not correctly populated.");
//		} else {
//			createWait(webD);
//
//			WebElement retValWE = getWait().until( //NOPMD
//					new Function<WebDriver, WebElement>() {
//						public @Nullable WebElement apply( //NOPMD
//								@Nullable WebDriver driver) {
//							if (null == driver) {
//								throw new IllegalStateException(
//										"webdriver is not correctly populated.");
//							} else {
//								return driver.findElement(By.name(findByValue));
//							}
//						}
//					});
//			if (null == retValWE) {
//				throw new NoSuchElementException(findByValue);
//			} else {
//				return retValWE;
//			}
//		}
	}


	
	
}
