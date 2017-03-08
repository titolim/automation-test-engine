/*******************************************************************************
 * ATE, Automation Test Engine
 *
 * Copyright 2016, Montreal PROT, or individual contributors as
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
package org.bigtester.ate.model.page.atewebdriver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.model.page.atewebdriver.exception.BrowserUnexpectedException;
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.sun.jna.platform.unix.X11.Window;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

// TODO: Auto-generated Javadoc
/**
 * This class AbstractLockProtectedMultiWindowsHandler defines ....
 * @author Peidong Hu
 *
 */
public class AbstractLockProtectedMultiWindowsHandler {
	/** The windows. */
	final private List<BrowserWindow> windows = new ArrayList<BrowserWindow>();
	
	/** The lock. */
	private final Object lock = new Object();
	

	/** The alerts. */
	final protected List<AbstractAlertDialog> alerts = new ArrayList<AbstractAlertDialog>();


	/** The my wd. */
	@XStreamOmitField
	@Nullable
	@Autowired
	protected IMyWebDriver myWd;

	/** The main window handler. */
	@Nullable
	protected String mainWindowHandler;

	/** The main window title. */
	@Nullable
	protected String mainWindowTitle;

	/**
	 * Instantiates a new abstract lock protected multi windows handler.
	 */
	public AbstractLockProtectedMultiWindowsHandler() {
		// TODO Auto-generated constructor stub
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
	private void removeClosedWindows() {
		
		boolean winRemoved = false;// NOPMD
		String currentWinHandle = "";
		try {
			currentWinHandle = this.getDriver().getWindowHandle();
		} catch (NoSuchWindowException noWinEx) {
			winRemoved = true;
		}
//		for (int i = 0; i < windows.size(); i++) {
//			if (windows.get(i).isClosed()) {
//				windows.remove(i);
//				winRemoved = true;// NOPMD
//			}
//		}
		for (Iterator<BrowserWindow> winItr=windows.iterator(); winItr.hasNext();) {
			BrowserWindow bw = winItr.next();
			if (bw.isClosed()) {
				winItr.remove();
				winRemoved = true;
			} else {
				try {
					this.getDriver().switchTo().window(bw.getWindowHandle());
				} catch (NoSuchWindowException noWinE) {
					winItr.remove();
					winRemoved = true;
				}
			}
		}
		if (winRemoved) {
			this.getDriver().switchTo()
					.window(windows.get(windows.size() - 1).getWindowHandle());
		} else {
			this.getDriver().switchTo().window(currentWinHandle);
		}
	}
	private void refreshAlerts() {
		for (int i = 0; i < alerts.size(); i++) {
			if (alerts.get(i).isClosed())
				alerts.remove(alerts.get(i));
		}
	}

	/**
	 * Refresh windows list.
	 *
	 * @param webD
	 *            the web d
	 * @throws BrowserUnexpectedException 
	 */
	public void refreshWindowsList(@Nullable WebDriver webD,
			boolean refreshFrameFlag) throws BrowserUnexpectedException {
		synchronized (lock) {
//			try {
//				//Thread.sleep(2);
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
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
					BrowserWindow temp = new BrowserWindow(winH, getMyWd());
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

	}
	/**
	 * @param myWd
	 *            the myWd to set
	 */
	public void setMyWd(IMyWebDriver myWd) {
		this.myWd = myWd;
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
	

	protected void resetWindows() {
		synchronized(lock) {
			windows.clear();
			alerts.clear();
			this.mainWindowHandler = null;//NOPMD
			this.mainWindowTitle = null;//NOPMD
		}
	}
	/**
	 * @return the myWebD
	 */
	public WebDriver getDriver() {
		return getMyWd().getWebDriverInstance();
	}
	/**
	 * @return the windows
	 */
	public List<BrowserWindow> getWindows() {
		synchronized (lock) {
			return windows;
		}
	}

}
