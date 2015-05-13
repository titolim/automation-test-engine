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
package org.bigtester.ate.model.page.page;

import org.bigtester.ate.constant.EnumElementFindType;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.page.elementfind.ElementFindById;
import org.bigtester.ate.model.page.elementfind.ElementFindByLinkText;
import org.bigtester.ate.model.page.elementfind.ElementFindByName;
import org.bigtester.ate.model.page.elementfind.ElementFindByXpath;
import org.bigtester.ate.model.page.elementfind.ITestObjectFinderImpl;
import org.eclipse.jdt.annotation.Nullable;

// TODO: Auto-generated Javadoc
/**
 * This class ATEPageFactory defines ....
 * 
 * @author Peidong Hu
 * 
 */
public final class ATEPageFactory implements IATEPageFactory {

	// private IPageObject iPageOject;
	// private MyWebElement myWebElement;
	// private IElementFind iElementFind;
	// private IElementAction iElementAction;
	/** The instance. */
	@Nullable
	private static IATEPageFactory instance;

	private ATEPageFactory() {
	}

	/**
	 * Gets the single instance of ATEPageFactory.
	 * 
	 * @return single instance of ATEPageFactory
	 */
	public static synchronized IATEPageFactory getInstance() { //NOPMD
		IATEPageFactory retVal = instance;
		if (null == retVal) {
			instance = new ATEPageFactory();
			retVal = instance;
		} 
		return retVal;
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public MyWebElement<?> getMyWebElement(EnumElementFindType elementFindType,
			String findByValue, IMyWebDriver myWd) {
		synchronized (this) {
			return new MyWebElement(getIElementFind(elementFindType,
					findByValue), null, myWd);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ITestObjectFinderImpl getIElementFind(EnumElementFindType elementFindType,
			String findByValue) {
		// TODO add more findby type
		synchronized (this) {
			ITestObjectFinderImpl retVal;
			switch (elementFindType) {
			case ID:
				ElementFindById efbID = new ElementFindById(findByValue);
				retVal = (ITestObjectFinderImpl) efbID;
				break;
			case XPATH:
				ElementFindByXpath efbXpath = new ElementFindByXpath(
						findByValue);

				retVal = (ITestObjectFinderImpl) efbXpath;
				break;
			case NAME:
				ElementFindByName efbName = new ElementFindByName(findByValue);

				retVal = (ITestObjectFinderImpl) efbName;
				break;
			case LINKTEXT:
				ElementFindByLinkText efbLinkText = new ElementFindByLinkText(
						findByValue);

				retVal = (ITestObjectFinderImpl) efbLinkText;
				break;
			default:
				ElementFindById efbIDd = new ElementFindById(findByValue);

				retVal = (ITestObjectFinderImpl) efbIDd;
				break;
			}
			return retVal;
		}
	}

	/**
	 * {@inheritDoc}
	 */
//	@Override
//	public IElementAction getIElementAction() {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
