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

import org.bigtester.ate.annotation.ATELogLevel;
import org.bigtester.ate.annotation.ActionLoggable;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

// TODO: Auto-generated Javadoc
/**
 * The Class ClickAction defines ....
 * 
 * @author Peidong Hu
 */
public class CursorMoveAction extends BaseElementAction implements
		IElementAction, ITestObjectActionImpl  {

	/**
	 * @param myWd
	 */
	public CursorMoveAction(IMyWebDriver myWd) {
		super(myWd);
		// TODO Auto-generated constructor stub
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@ActionLoggable (level=ATELogLevel.INFO)
	public void doAction(final WebElement webElm) {
		Actions act = new Actions(getMyWd().getWebDriver());
		act.moveToElement(webElm).build().perform();
	}

	

}
