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
package org.bigtester.ate.model.page.atewebdriver; //NOPMD

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bigtester.ate.GlobalUtils;
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

// TODO: Auto-generated Javadoc
/**
 * This class BrowserWindowsMonitor defines ....
 * 
 * @author Peidong Hu
 *
 */
public class MultiWindowsHandler implements WebDriverEventListener {

	/** The my web d. */
	final private WebDriver driver;

	/** The windows. */
	final private List<BrowserWindow> windows = new ArrayList<BrowserWindow>();

	/** The main window handler. */
	@Nullable
	private String mainWindowHandler;

	/** The main window title. */
	@Nullable
	private String mainWindowTitle;

	/**
	 * Instantiates a new multi windows handler.
	 *
	 * @param myWebD
	 *            the my web d
	 */
	public MultiWindowsHandler(WebDriver myWebD) {
		this.driver = myWebD;
	}

	/**
	 * @return the mainWindowTitle
	 */
	public String getMainWindowTitle() {
		final String mainWindowTitle2 = mainWindowTitle;
		if (mainWindowTitle2 == null) {
			throw GlobalUtils
					.createNotInitializedException("main window title");
		} else {
			return mainWindowTitle2;
		}
	}

	/**
	 * Close all windows except main window.
	 */
	public void closeAllWindowsExceptMainWindow() {

		closeAllOtherWindows(getMainWindowHandler());

	}

	/**
	 * Retrieve current window title.
	 *
	 * @return the string
	 */
	final public String retrieveCurrentWindowTitle() {
		String windowTitle = driver.getTitle();
		if (null == windowTitle) {
			throw GlobalUtils.createInternalError("web driver internal error!");
		} else {
			return windowTitle;
		}
	}

	// To close all the other windows except the main window.
	/**
	 * Close all other windows.
	 *
	 * @param openWindowHandle
	 *            the open window handle
	 * @return true, if successful
	 */
	public boolean closeAllOtherWindows(String openWindowHandle) {

		Set<String> allWindowHandles = driver.getWindowHandles();
		for (String currentWindowHandle : allWindowHandles) {
			if (!currentWindowHandle.equals(openWindowHandle)) {
				driver.switchTo().window(currentWindowHandle);
				driver.close();
			}
		}

		driver.switchTo().window(openWindowHandle);
		boolean retVal;
		if (driver.getWindowHandles().size() == 1) { // NOPMD
			retVal = true;
		} else {
			retVal = false;
		}
		return retVal;
	}

	/**
	 * Switch to window.
	 *
	 * @param title
	 *            the title
	 * @return true, if successful
	 */
	public boolean switchToWindowUsingTitle(String title) {
		String currentWindow = driver.getWindowHandle(); // NOPMD
		Set<String> availableWindows = driver.getWindowHandles();
		boolean switchSuccess;
		if (availableWindows.isEmpty()) {
			switchSuccess = false;
		} else {
			switchSuccess = false; // NOPMD
			for (String windowId : availableWindows) {
				if (driver.switchTo().window(windowId).getTitle().equals(title)) {
					switchSuccess = true;
					break;
				} else {
					driver.switchTo().window(currentWindow);
				}
			}

		}
		return switchSuccess;
	}

	/**
	 * Retrive window handle using title.
	 *
	 * @param title
	 *            the title
	 * @return the string
	 */
	@Nullable
	public String retriveWindowHandleUsingTitle(String title) {
		String currentWindow = driver.getWindowHandle(); // NOPMD
		Set<String> availableWindows = driver.getWindowHandles();
		String retVal = null; // NOPMD
		if (!availableWindows.isEmpty()) {
			for (String windowId : availableWindows) {
				if (driver.switchTo().window(windowId).getTitle().equals(title)) {
					retVal = driver.getWindowHandle();
					break;
				} else {
					driver.switchTo().window(currentWindow);
				}
			}

		}
		return retVal;
	}

	/**
	 * Focus on latest window.
	 */
	public void focusOnLatestWindow() {
		if (windows.isEmpty())
			return;
		windows.get(windows.size() - 1).obtainFocus();
	}

	/**
	 * Obtain window handle.
	 *
	 * @param openSequence
	 *            the open sequence
	 * @return the string
	 */
	@Nullable
	public String obtainWindowHandle(int openSequence) {
		String retVal = null; // NOPMD
		if (windows.size() >= openSequence + 1) {
			retVal = windows.get(openSequence).getWindowHandle();
		}
		return retVal;
	}

	/**
	 * Focus on open sequence number.
	 *
	 * @param openSequence
	 *            the open sequence
	 */
	public void focusOnOpenSequenceNumber(int openSequence) {
		if (windows.size() < openSequence + 1) {
			return;
		} else {
			windows.get(openSequence).obtainFocus();
		}

	}

	/**
	 * Refresh windows list.
	 *
	 * @param webD
	 *            the web d
	 */
	private void refreshWindowsList(@Nullable WebDriver webD) {
		if (null == webD)
			throw GlobalUtils.createNotInitializedException("Web Driver");
		Set<String> allWinHandles = webD.getWindowHandles();
		for (String winH : allWinHandles) {
			if (null == winH)
				throw GlobalUtils
						.createInternalError("web driver get all windows handles error.");
			boolean winAlreadyStored = false; //NOPMD
			for (BrowserWindow bWin : windows) {
				if (bWin.getWindowHandle().equals(winH)) {
					winAlreadyStored = true;
					break;
				} 
			}
			if (!winAlreadyStored) {
				BrowserWindow temp = new BrowserWindow(winH, driver);
				windows.add(temp);
			}
		}
		for (BrowserWindow winH2 : windows) {
			if (allWinHandles.contains(winH2.getWindowHandle())) {
				continue;
			} else {
				windows.remove(winH2);
			}
		}
		String currentWinH = webD.getWindowHandle();
		this.mainWindowHandler = windows.get(0).getWindowHandle();
		this.mainWindowTitle = windows.get(0).getMyWd().getTitle();
		webD.switchTo().window(currentWinH);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterChangeValueOf(@Nullable WebElement arg0,
			@Nullable WebDriver arg1) {
		refreshWindowsList(arg1);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterClickOn(@Nullable WebElement arg0, @Nullable WebDriver arg1) {
		refreshWindowsList(arg1);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterFindBy(@Nullable By arg0, @Nullable WebElement arg1,
			@Nullable WebDriver arg2) {
		refreshWindowsList(arg2);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterNavigateBack(@Nullable WebDriver arg0) {
		refreshWindowsList(arg0);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterNavigateForward(@Nullable WebDriver arg0) {
		refreshWindowsList(arg0);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterNavigateTo(@Nullable String arg0, @Nullable WebDriver arg1) {
		refreshWindowsList(arg1);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterScript(@Nullable String arg0, @Nullable WebDriver arg1) {
		refreshWindowsList(arg1);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeChangeValueOf(@Nullable WebElement arg0,
			@Nullable WebDriver arg1) {
		refreshWindowsList(arg1);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeClickOn(@Nullable WebElement arg0,
			@Nullable WebDriver arg1) {
		refreshWindowsList(arg1);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeFindBy(@Nullable By arg0, @Nullable WebElement arg1,
			@Nullable WebDriver arg2) {
		refreshWindowsList(arg2);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeNavigateBack(@Nullable WebDriver arg0) {
		refreshWindowsList(arg0);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeNavigateForward(@Nullable WebDriver arg0) {
		refreshWindowsList(arg0);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeNavigateTo(@Nullable String arg0, @Nullable WebDriver arg1) {
		refreshWindowsList(arg1);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeScript(@Nullable String arg0, @Nullable WebDriver arg1) {
		refreshWindowsList(arg1);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onException(@Nullable Throwable arg0, @Nullable WebDriver arg1) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the windows
	 */
	public List<BrowserWindow> getWindows() {
		return windows;
	}

	/**
	 * @return the myWebD
	 */
	public WebDriver getDriver() {
		return driver;
	}

	/**
	 * @return the mainWindowHandler
	 */
	public String getMainWindowHandler() {
		final String mainWindowHandler2 = mainWindowHandler;
		if (mainWindowHandler2 == null) {
			throw GlobalUtils
					.createNotInitializedException("main window handle");
		} else {
			return mainWindowHandler2;
		}
	}

	/**
	 * @param mainWindowHandler
	 *            the mainWindowHandler to set
	 */
	public void setMainWindowHandler(String mainWindowHandler) {
		this.mainWindowHandler = mainWindowHandler;
	}

	/**
	 * @param mainWindowTitle
	 *            the mainWindowTitle to set
	 */
	public void setMainWindowTitle(String mainWindowTitle) {
		this.mainWindowTitle = mainWindowTitle;
	}

}
