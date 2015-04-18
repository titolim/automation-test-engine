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
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.thoughtworks.xstream.annotations.XStreamOmitField;


// TODO: Auto-generated Javadoc
/**
 * This class BrowserWindow defines ....
 * @author Peidong Hu
 *
 */
public class BrowserWindow {
	
	/** The window handle. */
	final private String windowHandle;
	
	/** The frames. */
	final private List<WindowFrame> visibleFrames = new ArrayList<WindowFrame>();
	
	/** The last success element find frame chain. */
	final private List<WindowFrame> lastSuccessElementFindFrameChain =  new ArrayList<WindowFrame>();
	
	/** The current element find frame chain. */
	final private List<WindowFrame> currentElementFindFrameChain =  new ArrayList<WindowFrame>();
	
	/** The my wd. */
	@XStreamOmitField
	final private WebDriver myWd;
	
	/**
	 * Instantiates a new browser window.
	 *
	 * @param winHandle the win handle
	 * @param myWd the my wd
	 */
	public BrowserWindow(String winHandle, WebDriver myWd) {
		this.windowHandle = winHandle;
		this.myWd = myWd;
	}
	
	/**
	 * Switch to main frame.
	 */
	public void switchToDefaultContent () {
		//if (!frames.isEmpty()) {
			myWd.switchTo().defaultContent();
		//}
	}
	
	/**
	 * Maximize.
	 */
	public void maximize() {
		obtainWindowFocus();
		myWd.manage().window().maximize();
	}
	
	/**
	 * Close.
	 */
	public void close() {
		obtainWindowFocus();
		myWd.close();
	}
	
	/**
	 * Refresh frames.
	 */
	public void refreshFrames() {
		obtainWindowFocus();
		switchToDefaultContent ();
		List<WebElement> iframes = myWd.findElements(By.tagName("iframe"));
		int index;
		this.visibleFrames.clear();
		for (index=0; index<iframes.size(); index++) {
			WebElement iframe = iframes.get(index);
			if (null == iframe) throw GlobalUtils.createInternalError("web driver");
			if (!iframe.isDisplayed()) { 
				continue;
			}
			WindowFrame winF = new WindowFrame(index, this.myWd, iframe);
			this.visibleFrames.add(winF);
			switchToDefaultContent ();
			winF.refreshChildFrames();
		}
		
		List<WebElement> frames = myWd.findElements(By.tagName("frame"));
		for (int indexj = 0; indexj<frames.size(); indexj++) {
			WebElement frame = frames.get(indexj);
			if (null == frame) throw GlobalUtils.createInternalError("web driver");
			if (!frame.isDisplayed()) { 
				continue;
			}
			WindowFrame winF = new WindowFrame(indexj + index, this.myWd, frame);
			this.visibleFrames.add(winF);
			switchToDefaultContent ();
			winF.refreshChildFrames();
		}
		switchToDefaultContent ();
		//obtainFocus();
	}
	/**
	 * Obtain focus.
	 */
	public void obtainWindowFocus() {
		myWd.switchTo().window(getWindowHandle());
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
		return myWd;
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
}
