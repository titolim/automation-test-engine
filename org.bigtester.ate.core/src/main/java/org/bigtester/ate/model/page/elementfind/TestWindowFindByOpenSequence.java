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
package org.bigtester.ate.model.page.elementfind;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

// TODO: Auto-generated Javadoc
/**
 * This class TestWindowFindByTitle defines ....
 * @author Peidong Hu
 *
 */
public class TestWindowFindByOpenSequence extends BaseTestWindowFinderImpl implements ITestWindowFinder, ITestObjectFinderImpl{

	/** The title. */
	final private String title;
	
	/**
	 * Instantiates a new test window find by title.
	 *
	 * @param title the title
	 */
	public TestWindowFindByOpenSequence(String title) {
		super();
		this.title = title;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String doFind(IMyWebDriver myWebDriver) throws NoSuchElementException {
		WebDriver webD = myWebDriver.getWebDriver();
		if (null == webD) {
			throw GlobalUtils.createNotInitializedException("web driver");
		} else {
			MultiWindowsHandler winHandler = new MultiWindowsHandler(webD);
			String retVal = winHandler.retriveWindowHandleUsingTitle(getTitle());
			if (null == retVal) {
				throw new NoSuchElementException("test window" + getTitle());
			} else {
				return retVal;
			}
		}
	}

}
