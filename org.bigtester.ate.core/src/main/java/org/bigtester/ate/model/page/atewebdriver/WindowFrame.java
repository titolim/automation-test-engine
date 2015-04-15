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

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


// TODO: Auto-generated Javadoc
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
}
