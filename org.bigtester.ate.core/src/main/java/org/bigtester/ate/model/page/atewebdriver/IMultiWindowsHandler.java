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
public interface IMultiWindowsHandler {

	/**
	 * @return the mainWindowTitle
	 */
	String getMainWindowTitle();
	
	/**
	 * Gets the window on focus handle.
	 *
	 * @return the window on focus handle
	 */
	@Nullable
	String getWindowOnFocusHandle();
	
	@Nullable
	BrowserWindow getWindowByHandle(String winHandle);
	/**
	 * Close window.
	 *
	 * @param winHandle
	 *            the win handle
	 */
	void closeWindow(String winHandle);
	
	/**
	 * Close all windows except main window.
	 */
	void closeAllWindowsExceptMainWindow();


	/**
	 * Retrieve current window title.
	 *
	 * @return the string
	 */
	String retrieveCurrentWindowTitle();
	
	// To close all the other windows except the main window.
	/**
	 * Close all other windows.
	 *
	 * @param openWindowHandle
	 *            the open window handle
	 * @return true, if successful
	 */
	boolean closeAllOtherWindows(String openWindowHandle);
		/**
	 * Switch to window.
	 *
	 * @param title
	 *            the title
	 * @return true, if successful
	 */
	boolean switchToWindowUsingTitle(String title);
	
	/**
	 * Retrive window handle using title.
	 *
	 * @param title
	 *            the title
	 * @return the string
	 */
	@Nullable
	String retriveWindowHandleUsingTitle(String title);
		/**
	 * Focus on latest window.
	 */
	void focusOnLatestWindow();
	
	/**
	 * Obtain window handle.
	 *
	 * @param openSequence
	 *            the open sequence
	 * @return the string
	 */
	@Nullable
	String obtainWindowHandle(int openSequence);
	
	/**
	 * Obtain focus on alert dialog.
	 *
	 * @param openSequence
	 *            the open sequence, indexed from 0
	 * @return the abstract alert dialog
	 */
	@Nullable
	AbstractAlertDialog obtainFocusOnAlertDialog(int openSequence);
	
	/**
	 * Obtain focus on latest alert dialog.
	 *
	 * @return the abstract alert dialog
	 */
	@Nullable
	AbstractAlertDialog obtainFocusOnLatestAlertDialog();
	
//	/**
//	 * Accept alert dialog on focus.
//	 */
//	void acceptAlertDialogOnFocus();
//	
	/**
	 * Focus on open sequence number.
	 *
	 * @param openSequence
	 *            the open sequence
	 */
	void focusOnOpenSequenceNumber(int openSequence);
		/**
	 * Gets the browser window on focus.
	 *
	 * @return the browser window on focus
	 */
	BrowserWindow getBrowserWindowOnFocus();
	
	/**
	 * Refresh windows list.
	 *
	 * @param webD
	 *            the web d
	 */
	void refreshWindowsList(@Nullable WebDriver webD, boolean refreshFrameFlag);

	/**
	 * @return the windows
	 */
	List<BrowserWindow> getWindows();

	/**
	 * @return the myWebD
	 */
	WebDriver getDriver();

	/**
	 * @return the mainWindowHandler
	 */
	String getMainWindowHandler();

	/**
	 * @param mainWindowHandler
	 *            the mainWindowHandler to set
	 */
	void setMainWindowHandler(String mainWindowHandler);

	/**
	 * @param mainWindowTitle
	 *            the mainWindowTitle to set
	 */
	void setMainWindowTitle(String mainWindowTitle);

	/**
	 * @return the alerts
	 */
	List<AbstractAlertDialog> getAlerts();

	/**
	 * @return the myWd
	 */
	IMyWebDriver getMyWd();

	/**
	 * @param myWd the myWd to set
	 */
	void setMyWd(IMyWebDriver myWd);


}
