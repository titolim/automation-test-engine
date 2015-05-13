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
package org.bigtester.ate.model.page.page;

import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.WebDriver;

// TODO: Auto-generated Javadoc
/**
 * This class PopupPromptDialog defines ....
 * @author Peidong Hu
 *
 */
public class PopupPromptDialog extends PopupConfirmationDialog {

	/** The text. */
	@Nullable
	private transient String text;
	/**
	 * @param winHandler
	 * @param webD
	 */
	public PopupPromptDialog(WebDriver webD) {
		super(webD);
	}
	/**
	 * @return the text
	 */
	public String getText() {
		final String text2 = text;
		String retVal;
		if (text2 == null) {
			retVal = "";
		} else {
			retVal = text2;
		}
		return retVal;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Receive text.
	 *
	 * @param text the text
	 */
	public void receiveText(String text) {
		setText(text);
		getAlertDialog().sendKeys(text);
	}
}
