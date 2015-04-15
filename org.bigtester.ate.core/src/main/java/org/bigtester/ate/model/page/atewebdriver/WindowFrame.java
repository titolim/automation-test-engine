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


// TODO: Add wait
/**
 * This class BrowserWindow defines ....
 * @author Peidong Hu
 *
 */
public class WindowFrame {
	
	/** The window handle. */
	final private int frameIndex;
	
	/** The my wd. */
	final private WebDriver myWd;
	
	/** The frame. */
	final private WebElement frame;
	
	/** The frames. */
	final private List<WindowFrame> childFrames = new ArrayList<WindowFrame>();
	
	/**
	 * Instantiates a new browser window.
	 *
	 * @param winHandle the win handle
	 * @param myWd the my wd
	 */
	public WindowFrame(int frameIndex, WebDriver myWd, WebElement frame) {
		this.frameIndex = frameIndex;
		this.frame = frame;
		this.myWd = myWd;
	}
	
	
	
	/**
	 * Obtain focus.
	 */
	public void obtainFocus() {
		
		myWd.switchTo().frame(this.getFrame());
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
		obtainFocus();
		List<WebElement> iframes = myWd.findElements(By.tagName("iframe"));
		int index;
		this.childFrames.clear();
		for (index=0; index<iframes.size(); index++) {
			WebElement iframe = iframes.get(index);
			if (null == iframe) throw GlobalUtils.createInternalError("web driver");
			WindowFrame childWinF = new WindowFrame(index, this.myWd, iframe);
			this.childFrames.add(childWinF);
			childWinF.refreshChildFrames();
		}
		List<WebElement> frames = myWd.findElements(By.tagName("frame"));
		for (int indexj = 0; indexj<frames.size(); indexj++) {
			WebElement frame = frames.get(indexj);
			if (null == frame) throw GlobalUtils.createInternalError("web driver");
			WindowFrame childWinF = new WindowFrame(indexj + index, this.myWd, frame);
			this.childFrames.add(childWinF);
			childWinF.refreshChildFrames();
		}
	}

	/**
	 * @return the frames
	 */
	public List<WindowFrame> getChildFrames() {
		return childFrames;
	}
}
