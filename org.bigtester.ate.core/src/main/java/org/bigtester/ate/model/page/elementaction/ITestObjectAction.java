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
package org.bigtester.ate.model.page.elementaction;

import org.bigtester.ate.model.page.atewebdriver.exception.BrowserUnexpectedException;

/**
 * This class ITestObjectFinder defines ....
 * @author Peidong Hu
 *
 */
public interface ITestObjectAction<T> {
	/**
	 * Do find of the element.
	 * 
	 * @param findByValue
	 *            the find by value
	 * @return the web element
	 * @throws BrowserUnexpectedException 
	 */
	void doAction(T testObject) throws BrowserUnexpectedException;
	
}
