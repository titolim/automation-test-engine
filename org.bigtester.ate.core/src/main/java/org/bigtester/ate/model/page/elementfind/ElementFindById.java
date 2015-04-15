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

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.model.page.atewebdriver.BrowserWindow;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.page.atewebdriver.WindowFrame;
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;

// TODO: Auto-generated Javadoc
/**
 * The Class ElementFindById defines the way to find element by id
 * 
 * @author Peidong Hu
 */
public class ElementFindById extends AbstractElementFind implements
		IElementFind, ITestObjectFinderImpl {

	/**
	 * @param findByValue
	 */
	public ElementFindById(String findByValue) {
		super(findByValue);
		// TODO Auto-generated constructor stub
	}

	@Nullable
	private WebElement findThroughFrames(WindowFrame winFrame,
			Wait<WebDriver> wait, final String findByValue) {

		winFrame.obtainFocus();
		try {
			WebElement retValWE = wait.until( // NOPMD
					new Function<WebDriver, WebElement>() {
						public @Nullable WebElement apply( // NOPMD
								@Nullable WebDriver driver) {
							if (null == driver) {
								throw new IllegalStateException(
										"webdriver is not correctly populated.");
							} else {
								return driver.findElement(By.id(findByValue));
							}
						}
					});
			if (null != retValWE)
				return retValWE;
		} catch (NoSuchElementException | TimeoutException error) {
			List<WindowFrame> childFrames = winFrame.getChildFrames();
			for (WindowFrame gChildF : childFrames) {
				if (null == gChildF)
					throw GlobalUtils.createInternalError("java arraylist");
				WebElement retVal = findThroughFrames(gChildF, wait,
						findByValue);
				if (null != retVal) {
					return retVal;
				}
			}
		}
		return null;
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
			BrowserWindow winOnFocus = myWebDriver.getMultiWindowsHandler()
					.getBrowserWindowOnFocus();
			if (winOnFocus.getFrames().isEmpty()) {

				WebElement retValWE = getWait().until( // NOPMD
						new Function<WebDriver, WebElement>() {
							public @Nullable WebElement apply( // NOPMD
									@Nullable WebDriver driver) {
								if (null == driver) {
									throw new IllegalStateException(
											"webdriver is not correctly populated.");
								} else {
									return driver.findElement(By
											.id(findByValue));
								}
							}
						});
				if (null == retValWE) {
					throw new NoSuchElementException(findByValue);
				} else {
					return retValWE;
				}
			} else {
				for (WindowFrame _wf : winOnFocus.getFrames()) {
					// _wf.obtainFocus();
					// webD.switchTo().frame(_wf.getFrame());
					// webD.findElement(By.id(findByValue));
					try {
						if (null == _wf)
							throw GlobalUtils
									.createInternalError("arraylist error");
						WebElement retValWE = findThroughFrames(_wf, getWait(),
								findByValue);
						// WebElement retValWE = getWait().until( // NOPMD
						// new Function<WebDriver, WebElement>() {
						// public @Nullable WebElement apply( // NOPMD
						// @Nullable WebDriver driver) {
						// if (null == driver) {
						// throw new IllegalStateException(
						// "webdriver is not correctly populated.");
						// } else {
						// return driver.findElement(By
						// .id(findByValue));
						// }
						// }
						// });
						if (null != retValWE)
							return retValWE;
					} catch (NoSuchElementException | TimeoutException error) {

						continue;
					}

				}
				// search in main frame
				winOnFocus.switchToMainFrame();
				WebElement retValWE = getWait().until( // NOPMD
						new Function<WebDriver, WebElement>() {
							public @Nullable WebElement apply( // NOPMD
									@Nullable WebDriver driver) {
								if (null == driver) {
									throw new IllegalStateException(
											"webdriver is not correctly populated.");
								} else {
									return driver.findElement(By
											.id(findByValue));
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

}
