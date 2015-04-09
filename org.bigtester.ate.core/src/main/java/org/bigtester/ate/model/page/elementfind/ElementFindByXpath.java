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

import java.util.List;

import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.google.common.base.Function;

// TODO: Auto-generated Javadoc
/**
 * This class ElementFindByXpath defines ....
 * @author Grace Hu
 *
 */
public class ElementFindByXpath extends AbstractElementFind implements IElementFind, ITestObjectFinderImpl {
	/**
	 * @param findByValue
	 */
	public ElementFindByXpath(String findByValue) {
		super(findByValue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebElement doFind(IMyWebDriver myWebDriver, final String findByValue) {
		WebDriver webD = myWebDriver.getWebDriver();
		if (null == webD) {
			throw new IllegalStateException(
					"web driver is not correctly populated.");
		} else {
			createWait(webD);

			WebElement retValWE = getWait().until( //NOPMD
					new Function<WebDriver, WebElement>() {
						public @Nullable WebElement apply( //NOPMD
								@Nullable WebDriver driver) {
							if (null == driver) {
								throw new IllegalStateException(
										"webdriver is not correctly populated.");
							} else {
								List<WebElement> allElements = driver.findElements(By.xpath(findByValue));
								if (allElements.size() == 0) throw new NoSuchElementException(findByValue);
								WebElement retVal;
								if (getIndexOfSameElementsInt() < -1) {
									retVal = allElements.get(0);
								} else if (getIndexOfSameElementsInt() == -1) {
									retVal = allElements.get(allElements.size() - 1);
								} else {
									retVal = allElements.get(getIndexOfSameElementsInt());
								}
								return retVal;
								//return driver.findElement(By.xpath(findByValue));
							}
						}
					});
			if (null == retValWE) {
				throw new NoSuchElementException(findByValue);
			} else {
				return retValWE;
			}
		}
	}


	
}
