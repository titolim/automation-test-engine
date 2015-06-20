/*******************************************************************************
 * ATE, Automation Test Engine
 *
 * Copyright 2014, Montreal PROT, or individual contributors as
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
package org.bigtester.ate.model.page.elementaction;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.annotation.ATELogLevel;
import org.bigtester.ate.annotation.ActionLoggable;
import org.bigtester.ate.model.page.PageModelBase;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.WebDriver;

// TODO: Auto-generated Javadoc
/**
 * The Class ClickAction defines ....
 * 
 * @author Peidong Hu
 */
public class TestWindowsSwitchAction extends PageModelBase implements
		ITestWindowAction, ITestObjectActionImpl {

	/**
	 * @param myWd
	 */
	public TestWindowsSwitchAction(IMyWebDriver myWd) {
		super(myWd);
	}

	
	/**
	 * {@inheritDoc}
	 */
	public @Nullable <T> T getCapability(Class<T> type) {
		if (this instanceof ITestWindowAction) {
			return (T) this; //NOPMD
		} else {
			return null;
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	@ActionLoggable (level=ATELogLevel.INFO)
	public void doAction(String winHandle) {
		WebDriver webD = super.getMyWd().getWebDriver();
		if (null == webD) {
			throw GlobalUtils.createNotInitializedException("web driver");
		} else {
			webD.switchTo().window(winHandle);
		}
		
	}

	/**
	* {@inheritDoc}
	*/
	public String getActionParametersLoggingValue() {
		return "window switch action with no parameter";
	}
	

}
