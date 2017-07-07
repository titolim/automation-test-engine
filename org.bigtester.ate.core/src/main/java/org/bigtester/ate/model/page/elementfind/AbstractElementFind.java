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
package org.bigtester.ate.model.page.elementfind;//NOPMD

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.annotation.ATELogLevel;
import org.bigtester.ate.annotation.TestObjectFinderLoggable;
import org.bigtester.ate.model.data.IOnTheFlyData;
import org.bigtester.ate.model.page.atewebdriver.BrowserWindow;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.page.atewebdriver.WindowFrame;
import org.bigtester.ate.model.page.atewebdriver.exception.BrowserUnexpectedException;
import org.bigtester.ate.model.page.exception.PageFrameRefreshException;
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractElementFind defines ....
 * 
 * @author Peidong Hu
 */
public abstract class AbstractElementFind extends AbstractTestObjectFinderImpl {

	/** The find by value. */
	private String findByValue;

	/** The index of same elements. */
	@Nullable
	private IOnTheFlyData<Integer> indexOfSameElements;

	/** The wait. */
	@Nullable
	transient protected Wait<WebDriver> wait;

	

	/**
	 * Gets the finding parameters logging value.
	 *
	 * @return the finding parameters logging value
	 */
	public String getFindingParametersLoggingValue() {
		return "findByValue = " + findByValue;
	}
	
	/**
	 * @return the wait
	 */
	public Wait<WebDriver> getWait() {
		final Wait<WebDriver> retVal = wait;
		if (null == retVal) {
			throw new IllegalStateException("wait is not correctly populated");

		} else {
			return retVal;
		}
	}

	/**
	 * Do find.
	 *
	 * @param myWebDriver
	 *            the my web driver
	 * @param findByValue
	 *            the find by value
	 * @return the web element
	 * @throws BrowserUnexpectedException 
	 * @throws NoSuchElementException 
	 */
	public abstract WebElement doFind(IMyWebDriver myWebDriver,
			String findByValue) throws NoSuchElementException, BrowserUnexpectedException;

	/**
	 * Instantiates a new abstract element find.
	 *
	 * @param findByValue
	 *            the find by value
	 */
	public AbstractElementFind(String findByValue) {
		super();
		this.findByValue = findByValue;
	}

	/**
	 * Gets the find by value.
	 * 
	 * @return the find by value
	 */
	public String getFindByValue() {
		return findByValue;
	}

	/**
	 * Sets the find by value.
	 * 
	 * @param findByValue
	 *            the new find by value
	 */
	public void setFindByValue(final String findByValue) {
		this.findByValue = findByValue;
	}

	/**
	 * Supports plugin = false.
	 *
	 * @param arg0
	 *            the arg0
	 * @return true, if successful
	 */
	public boolean supports(final String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Creates the wait.
	 *
	 * @param driver
	 *            the driver
	 */
	public void createWait(WebDriver driver) {
		wait = new FluentWait<WebDriver>(driver)
				.withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public @Nullable <T> T getCapability(Class<T> type) {
		if (this instanceof IElementFind) {
			return (T) this; // NOPMD
		} else {
			return null;
		}
	}

	/**
	 * Do find.
	 *
	 * @param myWebDriver
	 *            the my web driver
	 * @return the web element
	 * @throws NoSuchElementException
	 *             the no such element exception
	 * @throws BrowserUnexpectedException 
	 */
	@TestObjectFinderLoggable (level=ATELogLevel.INFO)
	public WebElement doFind(IMyWebDriver myWebDriver)
			throws NoSuchElementException, BrowserUnexpectedException {

		return doFind(myWebDriver, findByValue);
	}

	/**
	 * @return the indexOfSameElements
	 */
	public int getIndexOfSameElementsInt() {
		final IOnTheFlyData<Integer> indexOfSameElements2 = indexOfSameElements;
		if (null == indexOfSameElements2) {
			return 0;// NOPMD
		} else {
			return indexOfSameElements2.getOnTheFlyData().intValue();
		}
	}

	/**
	 * @return the indexOfSameElements
	 */
	public IOnTheFlyData<Integer> getIndexOfSameElements() {
		final IOnTheFlyData<Integer> indexOfSameElements2 = indexOfSameElements;
		if (null == indexOfSameElements2) {
			throw GlobalUtils
					.createNotInitializedException("indexOfSameElements");
		} else {
			return indexOfSameElements2;
		}
	}

	/**
	 * @param indexOfSameElements
	 *            the indexOfSameElements to set
	 */
	public void setIndexOfSameElements(
			IOnTheFlyData<Integer> indexOfSameElements) {
		this.indexOfSameElements = indexOfSameElements;
	}

	/**
	 * Find through frames.
	 *
	 * @param win
	 *            the win
	 * @param winFrame
	 *            the win frame
	 * @param wait
	 *            the wait
	 * @param findByValue
	 *            the find by value
	 * @return the web element
	 */
	@Nullable
	protected WebElement findThroughFrames(BrowserWindow win,
			WindowFrame winFrame, Wait<WebDriver> wait, final By findByValue) {
		win.getCurrentElementFindFrameChain().add(winFrame);
		try {
			winFrame.obtainFrameFocus();
		} catch (PageFrameRefreshException e) {
			throw GlobalUtils.createNotInitializedException("web driver frame", e);
		}
		WebElement retValWE = null;// NOPMD
		try {
			retValWE = wait.until( // NOPMD
					new Function<WebDriver, WebElement>() {
						public @Nullable WebElement apply( // NOPMD
								@Nullable WebDriver driver) {
							if (null == driver) {
								throw new IllegalStateException(
										"webdriver is not correctly populated.");
							} else {
								List<WebElement> allElements = driver
										.findElements(findByValue);
								if (allElements.size() == 0)
									throw new NoSuchElementException(
											findByValue.toString());
								WebElement retVal;
								int intIndex = getIndexOfSameElementsInt();
								if (intIndex < -1) {
									retVal = allElements.get(0);
								} else if (intIndex == -1) {
									retVal = allElements.get(allElements.size() - 1);
								} else if (intIndex < allElements.size()){
									retVal = allElements.get(intIndex);
								} else {
									throw new NoSuchElementException(findByValue.toString()); 
								}
								return retVal;
								// return driver.findElement(findByValue);
							}
						}
					});
			
		} catch (NoSuchElementException | TimeoutException error) {
			List<WindowFrame> childFrames = winFrame.getChildFrames();
			for (WindowFrame gChildF : childFrames) {
				if (null == gChildF)
					throw GlobalUtils.createInternalError("java arraylist",
							error);
				retValWE = findThroughFrames(win, gChildF, wait, findByValue);
				if (null != retValWE) {

					break;
				}
			}
		}
		if (null == retValWE) {
			if (winFrame.getParentFrame() == null) {
				winFrame.focusDefautContent();
			} else {
				winFrame.focusParentFrame();
			}
		}

		return retValWE;
	}

	/**
	 * Find element.
	 *
	 * @param findBy
	 *            the find by
	 * @param myWebDriver
	 *            the my web driver
	 * @return the web element
	 * @throws BrowserUnexpectedException 
	 */
	protected WebElement findElement(final By findBy, IMyWebDriver myWebDriver) throws BrowserUnexpectedException {
		WebDriver webD = myWebDriver.getWebDriver();
		if (null == webD) {
			throw new IllegalStateException(
					"web driver is not correctly populated.");
		} else {
			createWait(webD);

			BrowserWindow winOnFocus = myWebDriver.getMultiWindowsHandler()
					.getBrowserWindowOnFocus();
			winOnFocus.switchToDefaultContent();
			if (!winOnFocus.getLastSuccessElementFindFrameChain().isEmpty()) {
				for (WindowFrame lastSuccessWFrame : winOnFocus
						.getLastSuccessElementFindFrameChain()) {
					try {
						lastSuccessWFrame.obtainFrameFocus();
					} catch (PageFrameRefreshException e) {
						//throw GlobalUtils.createNotInitializedException("frame chain");
						break;
					}
				}
			}
			WebElement retValWE = null;// NOPMD
			try {
				retValWE = getWait().until( // NOPMD
						new Function<WebDriver, WebElement>() {
							public @Nullable WebElement apply( // NOPMD
									@Nullable WebDriver driver) {
								if (null == driver) {
									throw new IllegalStateException(
											"webdriver is not correctly populated.");
								} else {
									List<WebElement> allElements = driver
											.findElements(findBy);
									if (allElements.size() == 0)
										throw new NoSuchElementException(
												findByValue);
									WebElement retVal;
									int intIndex = getIndexOfSameElementsInt();
									if (intIndex < -1) {
										retVal = allElements.get(0);
									} else if (intIndex == -1) {
										retVal = allElements.get(allElements
												.size() - 1);
									} else if (intIndex < allElements.size()){
										retVal = allElements.get(intIndex);
									} else {
										throw new NoSuchElementException(findBy.toString()); 
									}
									return retVal;
								}
							}
						});

			} catch (NoSuchElementException | TimeoutException error) {
				winOnFocus.getCurrentElementFindFrameChain().clear();
				try {
					myWebDriver.getMultiWindowsHandler().refreshWindowsList(myWebDriver.getWebDriverInstance(), true);
				} catch (BrowserUnexpectedException e) {
					myWebDriver.getMultiWindowsHandler().retryRefreshWindows(myWebDriver.getWebDriverInstance(), true);
				}
				
				for (WindowFrame winfr : winOnFocus.getVisibleFrames()) {

					try {
						if (null == winfr)
							throw GlobalUtils.createInternalError(
									"arraylist error", error);
						winfr.focusDefautContent();
						winOnFocus.getCurrentElementFindFrameChain().clear();
						retValWE = findThroughFrames(winOnFocus, winfr,
								getWait(), findBy);

						if (null == retValWE)
							winfr.focusDefautContent();
						else {
							
								if (!winOnFocus.getLastSuccessElementFindFrameChain().equals(
										winOnFocus.getCurrentElementFindFrameChain())) {
									winOnFocus.getLastSuccessElementFindFrameChain().clear();
									winOnFocus.getLastSuccessElementFindFrameChain().addAll(
											winOnFocus.getCurrentElementFindFrameChain());
								}

							
							break;
						}
					} catch (NoSuchElementException | TimeoutException error1) {

						continue;
					}

				}
			}
			if (null != retValWE) {
				return retValWE;
			}
			throw new NoSuchElementException(findBy.toString());
		}
	}

}
