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

import org.bigtester.ate.GlobalUtils;
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
	@Nullable
	private Alert alertDialog;
	/** The my wd. */
	@XStreamOmitField
	final private WebDriver myWd;
	
	/**
	 * Gets the my wd.
	 *
	 * @return the my wd
	 */
	public WebDriver getMyWd() {
		return myWd;
	}

	/** The owner handler. */
	final private String ownerHandler;
	
	/**
	 * Instantiates a new abstract alert dialog.
	 *
	 * @param winHandler the win handler
	 * @param webD the web d
	 */
	public AbstractAlertDialog(String winHandler, WebDriver webD){
		myWd = webD;
		ownerHandler = winHandler;
	}

	/**
	 * Obtain focus.
	 */
	public void obtainFocus() {
		myWd.switchTo().alert();
	}
	
	/**
	 * Accept.
	 */
	abstract void accept();

	/**
	 * Gets the owner handler.
	 *
	 * @return the owner handler
	 */
	public String getOwnerHandler() {
		return ownerHandler;
	}

	public Alert getAlertDialog() {
		final Alert alertDialog2 = alertDialog;
		if (alertDialog2 == null) {
			throw GlobalUtils.createNotInitializedException("alertdialog");
		} else {
			return alertDialog2;
		}
	}

	public void setAlertDialog(Alert alertDialog) {
		this.alertDialog = alertDialog;
	}
}
