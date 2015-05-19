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
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

// TODO: Add wait
/**
 * This class BrowserWindow defines ....
 * 
 * @author Peidong Hu
 *
 */
public class WindowFrame {

	/** The window handle. */
	private int frameIndex;

	/** The my wd. */
	@XStreamOmitField
	final private WebDriver myWd;

	/** The frame. */
	@XStreamOmitField
	private WebElement frame;

	/** The frames. */
	final private List<WindowFrame> childFrames = new ArrayList<WindowFrame>();
	
	/** The parent frame. */
	@Nullable
	private WindowFrame parentFrame;

	/**
	 * Instantiates a new browser window.
	 *
	 * @param winHandle
	 *            the win handle
	 * @param myWd
	 *            the my wd
	 */
	public WindowFrame(int frameIndex, WebDriver myWd, WebElement frame) {
		this.frameIndex = frameIndex;
		this.frame = frame;
		this.myWd = myWd;
	}

	/**
	 * Instantiates a new browser window.
	 *
	 * @param winHandle
	 *            the win handle
	 * @param myWd
	 *            the my wd
	 */
	public WindowFrame(int frameIndex, WebDriver myWd, WebElement frame,
			WindowFrame parentFrame) {
		this.frameIndex = frameIndex;
		this.frame = frame;
		this.myWd = myWd;
		this.parentFrame = parentFrame;
	}

	/**
	 * Obtain focus.
	 */
	public void obtainFrameFocus() {
		// WindowFrame parentFrameTmp = getParentFrame();
		// if (parentFrameTmp == null) {
		// myWd.switchTo().defaultContent();
		// } else {
		// myWd.switchTo().frame(parentFrameTmp.getFrame());
		// }
		try {
			myWd.switchTo().frame(this.getFrame());
		} catch (Throwable thr) {
			System.out.println("exception place marker: " + thr.getStackTrace());
		}

	}

	/**
	 * Focus parent frame.
	 */
	public void focusParentFrame() {
		myWd.switchTo().parentFrame();
	}

	/**
	 * Focus defaut content.
	 */
	public void focusDefautContent() {
		myWd.switchTo().defaultContent();
	}

	/**
	 * @return the windowHandle
	 */
	public int getFrameIndex() {
		return frameIndex;
	}

	/**
	 * @return the myWd
	 */
	public WebDriver getMyWd() {
		return myWd;
	}

	/**
	 * @return the frame
	 */
	public WebElement getFrame() {
		return frame;
	}

	/**
	 * Refresh child frames.
	 */
	public void refreshChildFrames() {
		obtainFrameFocus();
		List<WebElement> iframes = myWd.findElements(By.tagName("iframe"));
		int index;
		this.childFrames.clear();
		for (index = 0; index < iframes.size(); index++) {
			WebElement iframe = iframes.get(index);
			if (null == iframe)
				throw GlobalUtils.createInternalError("web driver");
			WindowFrame childWinF = new WindowFrame(index, this.myWd, iframe,
					this);
			this.childFrames.add(childWinF);
			childWinF.refreshChildFrames();
		}
		List<WebElement> frames = myWd.findElements(By.tagName("frame"));
		for (int indexj = 0; indexj < frames.size(); indexj++) {
			WebElement frame = frames.get(indexj);
			if (null == frame)
				throw GlobalUtils.createInternalError("web driver");
			WindowFrame childWinF = new WindowFrame(indexj + index, this.myWd,
					frame, this);
			this.childFrames.add(childWinF);
			childWinF.refreshChildFrames();
		}
		if (null == parentFrame) {
			focusDefautContent();
		} else {
			focusParentFrame();
		}
	}

	/**
	 * @return the frames
	 */
	public List<WindowFrame> getChildFrames() {
		return childFrames;
	}

	/**
	 * @return the parentFrame
	 */
	@Nullable
	public WindowFrame getParentFrame() {
		return parentFrame;
	}

	/**
	 * @param parentFrame
	 *            the parentFrame to set
	 */
	public void setParentFrame(WindowFrame parentFrame) {
		this.parentFrame = parentFrame;
	}

	/**
	 * @param frameIndex
	 *            the frameIndex to set
	 */
	public void setFrameIndex(int frameIndex) {
		this.frameIndex = frameIndex;
	}

	/**
	 * @param frame
	 *            the frame to set
	 */
	public void setFrame(WebElement frame) {
		this.frame = frame;
	}
}
