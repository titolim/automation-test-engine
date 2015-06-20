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

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.model.page.PageModelBase;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.page.atewebdriver.exception.BrowserUnexpectedException;
import org.bigtester.ate.model.page.elementaction.ITestObjectAction;
import org.bigtester.ate.model.page.elementaction.ITestObjectActionImpl;
import org.bigtester.ate.model.page.elementaction.TestObjectAction;
import org.bigtester.ate.model.page.elementfind.ITestObjectFinder;
import org.bigtester.ate.model.page.elementfind.ITestObjectFinderImpl;
import org.bigtester.ate.model.page.elementfind.TestObjectFinder;
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;

// TODO: Auto-generated Javadoc
/**
 * The Class MyWebElement defines ....
 * 
 * @author Peidong Hu
 */
public class MyWebElement<T> extends PageModelBase {

	/** The test object finder. */
	final private ITestObjectFinder<T> testObjectFinder;
	
	/** The i elm action. */
	@Nullable
	final private ITestObjectAction<T> testObjectAction;

	/**
	 * Instantiates a new my web element.
	 * 
	 * @param iElmFind
	 *            the ef
	 * @param iElmAction
	 *            the ea
	 */
	public MyWebElement(final ITestObjectFinderImpl iElmFind,
			@Nullable final ITestObjectActionImpl iElmAction, IMyWebDriver myWd) {
		super(myWd);
		if (null == iElmAction) {
			testObjectAction = null; //NOPMD
		} else {
			ITestObjectAction<T> testObjectActionTmp = new TestObjectAction(iElmAction).getCapability(ITestObjectAction.class); 
			if (null == testObjectActionTmp) {
				throw GlobalUtils
						.createNotInitializedException("test object finder");
			} else {
				testObjectAction = testObjectActionTmp;
			}
		} 
		ITestObjectFinder<T> testObjectFinderTmp = new TestObjectFinder(iElmFind).getCapability(ITestObjectFinder.class); 
		if (null == testObjectFinderTmp) {
			throw GlobalUtils.createNotInitializedException("test object finder");
		} else {
			testObjectFinder = testObjectFinderTmp;
		}
	}

	/**
	 * @return the elementAction
	 */
	@Nullable
	public ITestObjectAction<T> getTestObjectAction() {
		return testObjectAction;
	}

	
	/**
	 * Do action.
	 * @throws BrowserUnexpectedException 
	 * @throws NoSuchElementException 
	 */
	public void doAction() throws NoSuchElementException, BrowserUnexpectedException {

		final ITestObjectAction<T> testObjectAction2 = testObjectAction;
		if (testObjectAction2 == null) {
			throw GlobalUtils.createNotInitializedException("test object action.");
		} else {
			try {
				testObjectAction2.doAction(testObjectFinder.doFind(getMyWd()));
			} catch (UnexpectedTagNameException ute) {
				throw new NoSuchElementException("Html element found by ate has different tag name.", ute);
			}
		}

	}

		
	/**
	 * @return the testObjectFinder
	 */
	public ITestObjectFinder<T> getTestObjectFinder() {
		return testObjectFinder;
	}
}
