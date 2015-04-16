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

//	@Nullable
//	protected WebElement findThroughFrames(BrowserWindow win,
//			WindowFrame winFrame, Wait<WebDriver> wait, final By findByValue) {
//		win.getCurrentElementFindFrameChain().add(winFrame);
//		winFrame.obtainFocus();
//		try {
//			WebElement retValWE = wait.until( // NOPMD
//					new Function<WebDriver, WebElement>() {
//						public @Nullable WebElement apply( // NOPMD
//								@Nullable WebDriver driver) {
//							if (null == driver) {
//								throw new IllegalStateException(
//										"webdriver is not correctly populated.");
//							} else {
//								return driver.findElement(findByValue);
//							}
//						}
//					});
//			if (null != retValWE) {
//				if (!win.getLastSuccessElementFindFrameChain().equals(
//						win.getCurrentElementFindFrameChain())) {
//					win.getLastSuccessElementFindFrameChain().clear();
//					win.getLastSuccessElementFindFrameChain().addAll(
//							win.getCurrentElementFindFrameChain());
//				}
//				return retValWE;
//			}
//		} catch (NoSuchElementException | TimeoutException error) {
//			List<WindowFrame> childFrames = winFrame.getChildFrames();
//			for (WindowFrame gChildF : childFrames) {
//				if (null == gChildF)
//					throw GlobalUtils.createInternalError("java arraylist");
//				WebElement retVal = findThroughFrames(win, gChildF, wait,
//						findByValue);
//				if (null != retVal) {
//
//					return retVal;
//				}
//			}
//		}
//		if (winFrame.getParentFrame() == null) {
//			winFrame.focusDefautContent();
//		} else {
//			winFrame.focusParent();
//		}
//		return null;
//	}
//
//	protected WebElement findElement(final By findBy, IMyWebDriver myWebDriver) {
//		WebDriver webD = myWebDriver.getWebDriver();
//		if (null == webD) {
//			throw new IllegalStateException(
//					"web driver is not correctly populated.");
//		} else {
//			createWait(webD);
//
//			BrowserWindow winOnFocus = myWebDriver.getMultiWindowsHandler()
//					.getBrowserWindowOnFocus();
//			winOnFocus.switchToDefaultContent();
//			if (!winOnFocus.getLastSuccessElementFindFrameChain().isEmpty()) {
//				for (WindowFrame lastSuccessWFrame : winOnFocus
//						.getLastSuccessElementFindFrameChain()) {
//					lastSuccessWFrame.obtainFocus();
//				}
//			}
//			try {
//				WebElement retValWE = getWait().until( // NOPMD
//						new Function<WebDriver, WebElement>() {
//							public @Nullable WebElement apply( // NOPMD
//									@Nullable WebDriver driver) {
//								if (null == driver) {
//									throw new IllegalStateException(
//											"webdriver is not correctly populated.");
//								} else {
//									return driver.findElement(findBy);
//								}
//							}
//						});
//				if (null != retValWE) {
//					return retValWE;
//				}
//			} catch (NoSuchElementException | TimeoutException error) {
//				winOnFocus.getCurrentElementFindFrameChain().clear();
//				for (WindowFrame winfr : winOnFocus.getFrames()) {
//
//					try {
//						if (null == winfr)
//							throw GlobalUtils
//									.createInternalError("arraylist error");
//						winfr.focusDefautContent();
//						WebElement retValWE = findThroughFrames(winOnFocus,
//								winfr, getWait(), findBy);
//
//						if (null != retValWE)
//							return retValWE;
//					} catch (NoSuchElementException | TimeoutException error1) {
//
//						continue;
//					}
//
//				}
//			}
//
//			throw new NoSuchElementException(findBy.toString());
//		}
//	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebElement doFind(IMyWebDriver myWebDriver, final String findByValue) {
		final By findBy = By.id(findByValue);
		if (null == findBy)
			throw GlobalUtils.createInternalError("selenium By creation");
		return findElement(findBy, myWebDriver);
//		WebDriver webD = myWebDriver.getWebDriver();
//		if (null == webD) {
//			throw new IllegalStateException(
//					"web driver is not correctly populated.");
//		} else {
//			
//			if (null == findBy)
//				throw GlobalUtils.createInternalError("selenium By creation");
//			createWait(webD);
//
//			BrowserWindow winOnFocus = myWebDriver.getMultiWindowsHandler()
//					.getBrowserWindowOnFocus();
//			winOnFocus.switchToDefaultContent();
//			if (!winOnFocus.getLastSuccessElementFindFrameChain().isEmpty()) {
//				for (WindowFrame lastSuccessWFrame : winOnFocus
//						.getLastSuccessElementFindFrameChain()) {
//					lastSuccessWFrame.obtainFocus();
//				}
//			}
//			try {
//				WebElement retValWE = getWait().until( // NOPMD
//						new Function<WebDriver, WebElement>() {
//							public @Nullable WebElement apply( // NOPMD
//									@Nullable WebDriver driver) {
//								if (null == driver) {
//									throw new IllegalStateException(
//											"webdriver is not correctly populated.");
//								} else {
//									return driver.findElement(findBy);
//								}
//							}
//						});
//				if (null != retValWE) {
//					return retValWE;
//				}
//			} catch (NoSuchElementException | TimeoutException error) {
//				winOnFocus.getCurrentElementFindFrameChain().clear();
//				for (WindowFrame winfr : winOnFocus.getFrames()) {
//
//					try {
//						if (null == winfr)
//							throw GlobalUtils
//									.createInternalError("arraylist error");
//						winfr.focusDefautContent();
//						WebElement retValWE = findThroughFrames(winOnFocus,
//								winfr, getWait(), findBy);
//
//						if (null != retValWE)
//							return retValWE;
//					} catch (NoSuchElementException | TimeoutException error1) {
//
//						continue;
//					}
//
//				}
//			}
//
//			throw new NoSuchElementException(findByValue);
//		}
	}

}
