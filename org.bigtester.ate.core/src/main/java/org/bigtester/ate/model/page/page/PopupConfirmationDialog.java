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

import org.openqa.selenium.WebDriver;

// TODO: Auto-generated Javadoc
/**
 * This class PopupConfirmationDialog defines ....
 * @author Peidong Hu
 *
 */
public class PopupConfirmationDialog extends PopupAlertDialog {

	/**
	 * @param winHandler
	 * @param webD
	 */
	public PopupConfirmationDialog(String winHandler, WebDriver webD) {
		super(winHandler, webD);
	}

	/**
	 * Reject.
	 */
	public void reject() {
		obtainAlert();
		getAlertDialog().dismiss();
	}

}
