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

import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractAlertDialog.
 *
 * @author Peidong Hu
 */
public abstract class AbstractAlertDialog {
	
	/** The alert dialog. */
	@XStreamOmitField
	private Alert alertDialog;
	/** The my wd. */
	@XStreamOmitField
	final private WebDriver myWd;

	/** The popup index. starting with 0*/
	final private int popupSequence;
	
	/** The closed. */
	private boolean closed;
	
	/** The closing window handle. */
	@Nullable
	private String closingWindowHandle;
	
	/**
	 * Gets the my wd.
	 *
	 * @return the my wd
	 */
	public WebDriver getMyWd() {
		return myWd;
	}

	
	/**
	 * Instantiates a new abstract alert dialog.
	 *
	 * @param winHandler the win handler
	 * @param webD the web d
	 */
	public AbstractAlertDialog(WebDriver webD, Alert alt, int popupSeq){
		myWd = webD;
		this.alertDialog = alt;
		this.popupSequence = popupSeq;
	}

	
	/**
	 * Accept.
	 */
	abstract public void accept();


	/**
	 * Gets the alert dialog.
	 *
	 * @return the alert dialog
	 */
	public Alert getAlertDialog() {
		return alertDialog;
	}

	/**
	 * Sets the alert dialog.
	 *
	 * @param alertDialog the new alert dialog
	 */
	public void setAlertDialog(Alert alertDialog) {
		this.alertDialog = alertDialog;
	}


	/**
	 * @return the popupSequence
	 */
	public int getPopupSequence() {
		return popupSequence;
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


	


	/**
	 * @return the closingWindowHandle
	 */
	@Nullable
	public String getClosingWindowHandle() {
		return closingWindowHandle;
	}


	/**
	 * @param closingWindowHandle the closingWindowHandle to set
	 */
	public void setClosingWindowHandle(String closingWindowHandle) {
		this.closingWindowHandle = closingWindowHandle;
	}


}
