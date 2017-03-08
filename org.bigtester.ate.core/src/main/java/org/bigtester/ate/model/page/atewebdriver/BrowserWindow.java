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
package org.bigtester.ate.model.page.atewebdriver;

import java.util.ArrayList;
import java.util.List;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.model.page.atewebdriver.exception.BrowserUnexpectedException;
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

// TODO: Auto-generated Javadoc
/**
 * This class BrowserWindow defines ....
 * 
 * @author Peidong Hu
 *
 */
public class BrowserWindow {

	/** The window handle. */
	final private String windowHandle;
	
	/** The my wd. */
	@XStreamOmitField
	final private IMyWebDriver myWd;  //NOPMD

	/** The browser driver path */
	@Nullable
	@XStreamOmitField
	private static String driverPath; // NOPMD

	/** The frames. */
	final private List<WindowFrame> visibleFrames = new ArrayList<WindowFrame>();

	/** The last success element find frame chain. */
	final private List<WindowFrame> lastSuccessElementFindFrameChain = new ArrayList<WindowFrame>();

	/** The current element find frame chain. */
	final private List<WindowFrame> currentElementFindFrameChain = new ArrayList<WindowFrame>();

	// /** The frame refresh try counting. */
	// private transient int frameRefreshTryCounting = 0;

	/** The Constant maxFrameRefreshTryCount. */
	final static private int MAXFRAMEREFRESHTRYCOUNT = 2;

	/** The closed. */
	private boolean closed;

	/**
	 * Instantiates a new browser window.
	 *
	 * @param winHandle
	 *            the win handle
	 * @param myWd
	 *            the my wd
	 */
	public BrowserWindow(String winHandle, IMyWebDriver myWd) {
		this.windowHandle = winHandle;
		this.myWd = myWd;
		this.closed = false;
	}

	/**
	 * Switch to main frame.
	 * @throws BrowserUnexpectedException 
	 */
	public void switchToDefaultContent() throws BrowserUnexpectedException {
		try {
			myWd.getWebDriverInstance().switchTo().defaultContent();
		}
		catch (Exception thr) {//NOPMD
			
			String msg = thr.getMessage();
			if (null == msg) msg = "Can't switch to DefaultContent";
			throw new BrowserUnexpectedException(thr,msg);
		}
		// }
	}

	/**
	 * Maximize.
	 */
	public void maximize() {
		obtainWindowFocus();
		myWd.getWebDriverInstance().manage().window().maximize();
	}

	/**
	 * Close.
	 */
	public void close() {
		try {
			obtainWindowFocus();
			myWd.getWebDriverInstance().close();
			this.setClosed(true);
		} catch (NoSuchWindowException noWinE) {
			this.setClosed(true);
		}
	}

	/**
	 * Refresh frames.
	 * @throws BrowserUnexpectedException 
	 */
	public void refreshFrames() throws BrowserUnexpectedException {
		obtainWindowFocus();

		for (int i = 0; i < BrowserWindow.MAXFRAMEREFRESHTRYCOUNT; i++) {
			try {
				switchToDefaultContent();
				List<WebElement> iframes = myWd.getWebDriverInstance().findElements(By
						.tagName("iframe"));
				int index;
				this.visibleFrames.clear();
				for (index = 0; index < iframes.size(); index++) {
					WebElement iframe = iframes.get(index);
					if (null == iframe)
						throw GlobalUtils.createInternalError("web driver");
					if (!iframe.isDisplayed()) {
						continue;
					}
					WindowFrame winF = new WindowFrame(index, myWd.getWebDriverInstance(), iframe);
					this.visibleFrames.add(winF);
					switchToDefaultContent();
					winF.refreshChildFrames();
				}

				List<WebElement> frames = myWd.getWebDriverInstance()
						.findElements(By.tagName("frame"));
				for (int indexj = 0; indexj < frames.size(); indexj++) {
					WebElement frame = frames.get(indexj);
					if (null == frame)
						throw GlobalUtils.createInternalError("web driver");
					if (!frame.isDisplayed()) {
						continue;
					}
					WindowFrame winF = new WindowFrame(indexj + index,
							myWd.getWebDriverInstance(), frame);
					this.visibleFrames.add(winF);
					switchToDefaultContent();
					winF.refreshChildFrames();
				}
				switchToDefaultContent();
				break;
			} catch (StaleElementReferenceException frameDeletedError) {
				continue;
			}
		}
		// obtainFocus();
	}

	/**
	 * Obtain focus.
	 */
	public void obtainWindowFocus() {
		myWd.getWebDriverInstance().switchTo().window(getWindowHandle());
	}

	/**
	 * @return the windowHandle
	 */
	public String getWindowHandle() {
		return windowHandle;
	}

	/**
	 * @return the myWd
	 */
	public WebDriver getMyWd() {
		return myWd.getWebDriverInstance();
	}

	/**
	 * @return the frames
	 */
	public List<WindowFrame> getVisibleFrames() {
		return visibleFrames;
	}

	/**
	 * @return the lastSuccessElementFindFrameChain
	 */
	public List<WindowFrame> getLastSuccessElementFindFrameChain() {
		return lastSuccessElementFindFrameChain;
	}

	/**
	 * @return the currentElementFindFrameChain
	 */
	public List<WindowFrame> getCurrentElementFindFrameChain() {
		return currentElementFindFrameChain;
	}


	/**
	 * @return the browser driver path
	 */
	@Nullable
	public static String getDriverPath() {
		return driverPath;
	}

	/**
	 * @set the browser driver path
	 */
	public static void setDriverPath(@Nullable String driverPath) {
		BrowserWindow.driverPath = driverPath;
	}

	/**
	 * @return the closed
	 */
	public boolean isClosed() {
		return closed;
	}

	/**
	 * @param closed the closed to set
	 */
	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	

	
}
