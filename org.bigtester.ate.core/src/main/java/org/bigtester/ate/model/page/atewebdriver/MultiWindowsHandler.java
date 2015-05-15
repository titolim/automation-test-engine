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
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.model.casestep.StepUnexpectedAlertEvent;
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.util.StringUtils;
import org.springframework.context.annotation.Bean;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

// TODO: Auto-generated Javadoc
/**
 * This class BrowserWindowsMonitor defines ....
 * 
 * @author Peidong Hu
 *
 */
public class MultiWindowsHandler implements IMultiWindowsHandler,
		WebDriverEventListener, ApplicationListener<AlertDialogAcceptedEvent> {

	/** The my web d. */
	@XStreamOmitField
	@Nullable
	private WebDriver driver;

	@XStreamOmitField
	@Nullable
	@Autowired
	private IMyWebDriver myWd;

	/** The windows. */
	final private List<BrowserWindow> windows = new ArrayList<BrowserWindow>();

	/** The alerts. */
	final private List<AbstractAlertDialog> alerts = new ArrayList<AbstractAlertDialog>();

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
	public MultiWindowsHandler() {
		// this.driver = myWebD.getWebDriverInstance();
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
	 * Gets the window on focus handle.
	 *
	 * @return the window on focus handle
	 */
	@Nullable
	public String getWindowOnFocusHandle() {
		String retVal;
		if (windows.isEmpty()) {
			retVal = null; // NOPMD
		} else {
			retVal = getDriver().getWindowHandle();
		}
		return retVal;
	}

	@Nullable
	public BrowserWindow getWindowByHandle(String winHandle) {
		BrowserWindow retVal = null;
		for (BrowserWindow win : this.windows) {
			if (winHandle.equalsIgnoreCase(win.getWindowHandle())) {
				retVal = win;
				break;
			}
		}
		return retVal;
	}

	/**
	 * Close window.
	 *
	 * @param winHandle
	 *            the win handle
	 */
	public void closeWindow(String winHandle) {
		BrowserWindow closingWindow = getWindowByHandle(winHandle);
		if (null == closingWindow) {
			// if (winHandle.equals(getWindowOnFocusHandle())) {
			// getDriver().close();
			// } else {
			// getDriver().switchTo().window(winHandle);
			// getDriver().close();
			// }
			throw GlobalUtils.createNotInitializedException("windows");
		} else {
			closingWindow.close();
			try {
				// test if there is alert. if no, refresh windows list
				checkCloseWindowAlert(closingWindow.getWindowHandle());

				closingWindow.setClosed(false);
			} catch (NoAlertPresentException noAlert) {
				if (getNumberOfOpenWindows() > 0)
					refreshWindowsList(getDriver(), false);
			}
		}

	}

	private int getNumberOfOpenWindows() {
		int retVal = 0;
		for (BrowserWindow win : windows) {
			if (!win.isClosed())
				retVal = retVal + 1;
		}
		return retVal;
	}

	private void checkCloseWindowAlert(String winh)
			throws NoAlertPresentException {
		try {
			Alert alt = getDriver().switchTo().alert();
			if (alt == null)
				throw GlobalUtils.createInternalError("web driver");
			PopupPromptDialog aDialog = new PopupPromptDialog(getDriver(), alt,
					alerts.size());
			aDialog.setClosingWindowHandle(winh);
			alerts.add(aDialog);
		} catch (UnreachableBrowserException error) {
			if (getNumberOfOpenWindows() > 0)
				throw GlobalUtils
						.createInternalError("ATE multi windows handler");
		} catch (UnsupportedOperationException e) {
			
				throw GlobalUtils
						.createInternalError("Driver doesn't support alert handling yet");
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
		String windowTitle = getDriver().getTitle();
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
		for (BrowserWindow win : this.windows) {
			if (!openWindowHandle.equalsIgnoreCase(win.getWindowHandle())) {
				win.close();
				try {
					// test if there is alert. if no, refresh windows list
					checkCloseWindowAlert(win.getWindowHandle());
				} catch (NoAlertPresentException noAlert) {
					refreshWindowsList(getDriver(), false);
				}
			}
		}
		// deal with the windows not in the windows list
		Set<String> allWindowHandles = getDriver().getWindowHandles();
		for (String currentWindowHandle : allWindowHandles) {
			if (!currentWindowHandle.equals(openWindowHandle)) {
				getDriver().switchTo().window(currentWindowHandle);
				getDriver().close();
				try {
					// test if there is alert. if no, refresh windows list
					checkCloseWindowAlert(currentWindowHandle);
				} catch (NoAlertPresentException noAlert) {
					refreshWindowsList(getDriver(), false);
				}
			}
		}

		getDriver().switchTo().window(openWindowHandle);
		boolean retVal;
		if (getDriver().getWindowHandles().size() == 1) { // NOPMD
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
		String currentWindow = getDriver().getWindowHandle(); // NOPMD
		Set<String> availableWindows = getDriver().getWindowHandles();
		boolean switchSuccess;
		if (availableWindows.isEmpty()) {
			switchSuccess = false;
		} else {
			switchSuccess = false; // NOPMD
			for (String windowId : availableWindows) {
				if (getDriver().switchTo().window(windowId).getTitle()
						.equals(title)) {
					switchSuccess = true;
					break;
				} else {
					getDriver().switchTo().window(currentWindow);
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
		String currentWindow = getDriver().getWindowHandle(); // NOPMD
		Set<String> availableWindows = getDriver().getWindowHandles();
		String retVal = null; // NOPMD
		if (!availableWindows.isEmpty()) {
			for (String windowId : availableWindows) {
				if (getDriver().switchTo().window(windowId).getTitle()
						.equals(title)) {
					retVal = getDriver().getWindowHandle();
					break;
				} else {
					getDriver().switchTo().window(currentWindow);
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
		windows.get(windows.size() - 1).obtainWindowFocus();
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
	 * Obtain focus on alert dialog.
	 *
	 * @param openSequence
	 *            the open sequence, indexed from 0
	 * @return the abstract alert dialog
	 */
	@Nullable
	public AbstractAlertDialog obtainFocusOnAlertDialog(int openSequence) {
		AbstractAlertDialog retVal;
		if (alerts.isEmpty())
			retVal = null;
		else {
			if (openSequence >= alerts.size())
				throw GlobalUtils
						.createNotInitializedException("this alert, opensequence is too large");
			retVal = alerts.get(openSequence);
			if (null == retVal)
				throw GlobalUtils.createInternalError("java");
		}
		return retVal;
	}

	/**
	 * Obtain focus on latest alert dialog.
	 *
	 * @return the abstract alert dialog
	 */
	@Nullable
	public AbstractAlertDialog obtainFocusOnLatestAlertDialog() {
		AbstractAlertDialog retVal;
		if (alerts.isEmpty())
			retVal = null;
		else {
			retVal = alerts.get(alerts.size() - 1);
			if (null == retVal)
				throw GlobalUtils.createInternalError("java");
		}
		return retVal;
	}

	/**
	 * Accept alert dialog on focus.
	 */
	public void acceptAlertDialogOnFocus() {
		AbstractAlertDialog alert = obtainFocusOnLatestAlertDialog();
		if (null == alert)
			throw GlobalUtils
					.createNotInitializedException("default alert dialog");
		alert.accept();
		GlobalUtils.getApx().publishEvent(new AlertDialogAcceptedEvent(alert));
		this.refreshWindowsList(getDriver(), false);
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
			windows.get(openSequence).obtainWindowFocus();
		}

	}

	/**
	 * Gets the browser window on focus.
	 *
	 * @return the browser window on focus
	 */
	public BrowserWindow getBrowserWindowOnFocus() {
		String winHandle = this.getDriver().getWindowHandle(); // NOPMD
		for (BrowserWindow bwd : windows) {
			if (bwd.getWindowHandle().equals(winHandle)) {
				return bwd;
			}
		}
		throw GlobalUtils.createInternalError("web driver wrong state");
	}

	private void refreshAlerts() {
		for (int i = 0; i < alerts.size(); i++) {
			if (alerts.get(i).isClosed())
				alerts.remove(alerts.get(i));
		}
	}

	private void removeClosedWindows() {
		boolean winRemoved = false;// NOPMD
		for (int i = 0; i < windows.size(); i++) {
			if (windows.get(i).isClosed()) {
				windows.remove(i);
				winRemoved = true;// NOPMD
			}
		}
		if (winRemoved) {
			this.getDriver().switchTo()
					.window(windows.get(windows.size() - 1).getWindowHandle());
		}
	}

	/**
	 * Refresh windows list.
	 *
	 * @param webD
	 *            the web d
	 */
	public void refreshWindowsList(@Nullable WebDriver webD,
			boolean refreshFrameFlag) {
		if (null == webD)
			throw GlobalUtils.createNotInitializedException("Web Driver");
		List<String> newAddedWinHandles = new ArrayList<String>();// NOPMD
		Alert winAlert = null; // NOPMD
		String winHandlePreserved = null;// NOPMD
		removeClosedWindows();
		refreshAlerts();
		try {
			winAlert = webD.switchTo().alert(); // NOPMD
			if (!alerts.contains(winAlert)) {
				if (null == winAlert)
					throw GlobalUtils.createInternalError("java");
				PopupPromptDialog alertNew = new PopupPromptDialog(webD,
						winAlert, alerts.size());
				alertNew.setClosed(false);
				alerts.add(alertNew);
			}
		} catch (NoAlertPresentException noAlert) {
			winHandlePreserved = webD.getWindowHandle(); // NOPMD
		} catch (UnsupportedOperationException e) {
			winHandlePreserved = webD.getWindowHandle(); // NOPMD
		}

		Set<String> allWinHandles = webD.getWindowHandles();
		for (String winH : allWinHandles) {
			if (null == winH)
				throw GlobalUtils
						.createInternalError("web driver get all windows handles error.");
			boolean winAlreadyStored = false; // NOPMD
			for (BrowserWindow bWin : windows) {
				if (bWin.getWindowHandle().equals(winH)) {
					winAlreadyStored = true;
					break;
				}
			}
			if (!winAlreadyStored) {
				BrowserWindow temp = new BrowserWindow(winH, getDriver());
				windows.add(temp);
				newAddedWinHandles.add(winH);
			}
		}
		for (Iterator<BrowserWindow> iter = windows.iterator(); iter.hasNext();) {
			BrowserWindow winH2 = iter.next();
			if (allWinHandles.contains(winH2.getWindowHandle())) {
				continue;
			} else {
				iter.remove();
			}
		}
		if (refreshFrameFlag) {
			for (Iterator<BrowserWindow> iter = windows.iterator(); iter
					.hasNext();) {
				BrowserWindow winH2 = iter.next();
				winH2.refreshFrames();
			}
		}

		if (StringUtils.isEmpty(this.mainWindowHandler)
				|| StringUtils.isEmpty(this.mainWindowTitle)) {
			this.mainWindowHandler = windows.get(0).getWindowHandle();
			String currentWinHandle = webD.getWindowHandle();
			webD.switchTo().window(this.mainWindowHandler);
			this.mainWindowTitle = windows.get(0).getMyWd().getTitle();
			webD.switchTo().window(currentWinHandle);
		}
		if (winAlert == null) {
			if (newAddedWinHandles.isEmpty()) {
				webD.switchTo().window(winHandlePreserved);
			} else {
				webD.switchTo().window(
						newAddedWinHandles.get(newAddedWinHandles.size() - 1));
			}
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterChangeValueOf(@Nullable WebElement arg0,
			@Nullable WebDriver arg1) {
		refreshWindowsList(arg1, true);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterClickOn(@Nullable WebElement arg0, @Nullable WebDriver arg1) {
		refreshWindowsList(arg1, true);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterFindBy(@Nullable By arg0, @Nullable WebElement arg1,
			@Nullable WebDriver arg2) {
		// refreshWindowsList(arg2);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterNavigateBack(@Nullable WebDriver arg0) {
		refreshWindowsList(arg0, true);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterNavigateForward(@Nullable WebDriver arg0) {
		refreshWindowsList(arg0, true);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterNavigateTo(@Nullable String arg0, @Nullable WebDriver arg1) {
		refreshWindowsList(arg1, true);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterScript(@Nullable String arg0, @Nullable WebDriver arg1) {
		refreshWindowsList(arg1, true);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeChangeValueOf(@Nullable WebElement arg0,
			@Nullable WebDriver arg1) {
		// refreshWindowsList(arg1);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeClickOn(@Nullable WebElement arg0,
			@Nullable WebDriver arg1) {
		// refreshWindowsList(arg1);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeFindBy(@Nullable By arg0, @Nullable WebElement arg1,
			@Nullable WebDriver arg2) {
		// refreshWindowsList(arg2);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeNavigateBack(@Nullable WebDriver arg0) {
		// refreshWindowsList(arg0);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeNavigateForward(@Nullable WebDriver arg0) {
		// refreshWindowsList(arg0);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeNavigateTo(@Nullable String arg0, @Nullable WebDriver arg1) {
		// refreshWindowsList(arg1);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeScript(@Nullable String arg0, @Nullable WebDriver arg1) {
		// refreshWindowsList(arg1);

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
		return getMyWd().getWebDriverInstance();
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

	/**
	 * @return the alerts
	 */
	public List<AbstractAlertDialog> getAlerts() {
		return alerts;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onApplicationEvent(@Nullable AlertDialogAcceptedEvent event) {

		if (null == event)
			throw GlobalUtils.createInternalError("spring event");
		AbstractAlertDialog alertD = (AbstractAlertDialog) event.getSource();
		for (int i = 0; i < windows.size(); i++) {
			if (windows.get(i).getWindowHandle()
					.equalsIgnoreCase(alertD.getClosingWindowHandle())) {
				windows.get(i).setClosed(true);
				refreshWindowsList(this.getDriver(), false);
			}
		}

	}

	/**
	 * @return the myWd
	 */
	public IMyWebDriver getMyWd() {
		final IMyWebDriver myWd2 = myWd;
		if (myWd2 == null) {
			throw GlobalUtils.createInternalError("spring wiring");
		} else {
			return myWd2;
		}
	}

	/**
	 * @param myWd
	 *            the myWd to set
	 */
	public void setMyWd(IMyWebDriver myWd) {
		this.myWd = myWd;
		driver = myWd.getWebDriverInstance();
	}

}
