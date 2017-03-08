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
import org.bigtester.ate.model.casestep.ITestCase; 
import org.bigtester.ate.model.page.atewebdriver.exception.BrowserUnexpectedException;
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.util.StringUtils;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

// TODO: Auto-generated Javadoc
/**
 * This class BrowserWindowsMonitor defines ....
 * 
 * @author Peidong Hu
 *
 */
public class MultiWindowsHandler extends AbstractLockProtectedMultiWindowsHandler implements IMultiWindowsHandler,
		WebDriverEventListener, ApplicationListener<AlertDialogAcceptedEvent> {
	
	/** The test case. */
	@Nullable
	@Autowired
	private ITestCase testCase;
	
	



	/**
	 * Gets the window on focus handle.
	 *
	 * @return the window on focus handle
	 */
	@Nullable
	public String getWindowOnFocusHandle() {
		String retVal;
		if (getWindows().isEmpty()) {
			retVal = null; // NOPMD
		} else {
			retVal = getDriver().getWindowHandle();
		}
		return retVal;
	}

	/**
	* {@inheritDoc}
	*/
	@Nullable
	public BrowserWindow getWindowByHandle(String winHandle) {
		BrowserWindow retVal = null;//NOPMD
		for (BrowserWindow win : this.getWindows()) {
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
	 * @throws BrowserUnexpectedException 
	 */
	public void closeWindow(String winHandle) throws BrowserUnexpectedException {
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
		int retVal = 0;//NOPMD
		for (BrowserWindow win : getWindows()) {
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
						.createInternalError("ATE multi windows handler", error);
		} catch (UnsupportedOperationException e) {
			
				throw GlobalUtils
						.createInternalError("Driver doesn't support alert handling yet", e);
		} catch (NoSuchWindowException windClosedAlready) {
			//do nothing if window closed without alert dialog intervention. for example in Chrome.
			throw new NoAlertPresentException(windClosedAlready);
		}
	}

	/**
	 * Close all windows except main window.
	 * @throws BrowserUnexpectedException 
	 */
	public void closeAllWindowsExceptMainWindow() throws BrowserUnexpectedException {

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
	 * @throws BrowserUnexpectedException 
	 */
	public boolean closeAllOtherWindows(String openWindowHandle) throws BrowserUnexpectedException {
		Iterator<BrowserWindow> itr = this.getWindows().iterator();
		while(itr.hasNext()) {
			
			BrowserWindow win = itr.next();
		//for (BrowserWindow win : this.getWindows()) {
			if (!openWindowHandle.equalsIgnoreCase(win.getWindowHandle()) && !win.isClosed()) {
				win.close();
				refreshWindowsList(getDriver(), false);
				try {
					
					checkCloseWindowAlert(win.getWindowHandle());// test if there is alert. if no, refresh windows list
				} catch (NoAlertPresentException noAlert) {
					refreshWindowsList(getDriver(), false);
					if (this.getWindows().size()>1)
						itr = this.getWindows().iterator();
					else
						break;
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
		if (getWindows().isEmpty())
			return;
		getWindows().get(getWindows().size() - 1).obtainWindowFocus();
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
		try {
			this.refreshWindowsList(getDriver(), false);
		} catch (BrowserUnexpectedException e) {
			this.retryRefreshWindows(getDriver(), false);
		}
		String retVal = null; // NOPMD
		if (getWindows().size() >= openSequence + 1) {
			retVal = getWindows().get(openSequence).getWindowHandle();
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
			retVal = null;//NOPMD
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
			retVal = null;//NOPMD
		else {
			retVal = alerts.get(alerts.size() - 1);
			if (null == retVal)
				throw GlobalUtils.createInternalError("java");
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
		if (getWindows().size() < openSequence + 1) {
			return;
		} else {
			getWindows().get(openSequence).obtainWindowFocus();
		}

	}

	/**
	 * Gets the browser window on focus.
	 *
	 * @return the browser window on focus
	 */
	public BrowserWindow getBrowserWindowOnFocus() {
		String winHandle = this.getDriver().getWindowHandle(); // NOPMD
		for (BrowserWindow bwd : getWindows()) {
			if (bwd.getWindowHandle().equals(winHandle)) {
				return bwd;
			}
		}
		throw GlobalUtils.createInternalError("web driver wrong state");
	}





	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterChangeValueOf(@Nullable WebElement arg0,
			@Nullable WebDriver arg1, CharSequence[] arg2) {
		try {
			refreshWindowsList(arg1, false);
		} catch (BrowserUnexpectedException e) {
			retryRefreshWindows(getMyWd().getWebDriverInstance(), false);
		}


	}

	public void retryRefreshWindows(WebDriver driver, boolean refreshFrames) {
		this.resetWindows();
		try {
			refreshWindowsList(driver, refreshFrames);
		} catch (BrowserUnexpectedException e1) {
			throw GlobalUtils.createInternalError("browser fatal error.", e1);//NOPMD
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterClickOn(@Nullable WebElement arg0, @Nullable WebDriver arg1) {
		try {
			refreshWindowsList(arg1, false);
		} catch (BrowserUnexpectedException e) {
			retryRefreshWindows(getMyWd().getWebDriverInstance(), false);
		}

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
		try {
			refreshWindowsList(arg0, false);
		} catch (BrowserUnexpectedException e) {
			retryRefreshWindows(getMyWd().getWebDriverInstance(), false);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterNavigateForward(@Nullable WebDriver arg0) {
		try {
			refreshWindowsList(arg0, false);
		} catch (BrowserUnexpectedException e) {
			retryRefreshWindows(getMyWd().getWebDriverInstance(), false);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterNavigateTo(@Nullable String arg0, @Nullable WebDriver arg1) {
		try {
			refreshWindowsList(arg1, false);
		} catch (BrowserUnexpectedException e) {
			retryRefreshWindows(getMyWd().getWebDriverInstance(), false);
		}


	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterScript(@Nullable String arg0, @Nullable WebDriver arg1) {
		try {
			refreshWindowsList(arg1, false);
		} catch (BrowserUnexpectedException e) {
			retryRefreshWindows(getMyWd().getWebDriverInstance(), false);
		}


	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeChangeValueOf(@Nullable WebElement arg0,
			@Nullable WebDriver arg1, CharSequence[] arg2) {
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
//		try {
//			refreshWindowsList(arg2, true);
//		} catch (BrowserUnexpectedException e) {
//			retryRefreshWindows(getMyWd().getWebDriverInstance());
//		}

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
	 * {@inheritDoc}
	 */
	@Override
	public void onApplicationEvent(@Nullable AlertDialogAcceptedEvent event) {

		if (null == event)
			throw GlobalUtils.createInternalError("spring event");
		AbstractAlertDialog alertD = (AbstractAlertDialog) event.getSource();//NOPMD
		for (int i = 0; i < getWindows().size(); i++) {
			if (getWindows().get(i).getWindowHandle()
					.equalsIgnoreCase(alertD.getClosingWindowHandle())) {
				getWindows().get(i).setClosed(true);
				try {
					refreshWindowsList(this.getDriver(), false);
				} catch (BrowserUnexpectedException e) {
					retryRefreshWindows(getMyWd().getWebDriverInstance(), false);
				}
			}
		}

	}





	/**
	 * @return the testCase
	 */
	public ITestCase getTestCase() {
		final ITestCase testCase2 = testCase;
		if (testCase2 == null) {
			throw GlobalUtils.createNotInitializedException("test case");
		} else {
			return testCase2;
		}
	}

	/**
	 * @param testCase the testCase to set
	 */
	public void setTestCase(ITestCase testCase) {
		this.testCase = testCase;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterNavigateRefresh(WebDriver arg0) {
		// TODO Auto-generated method stub
		
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeNavigateRefresh(WebDriver arg0) {
		// TODO Auto-generated method stub
		
	}

}
