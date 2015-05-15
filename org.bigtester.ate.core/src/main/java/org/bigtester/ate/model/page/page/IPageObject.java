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

import java.util.List;
import java.util.Map;

import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.openqa.selenium.Cookie;


// TODO: Auto-generated Javadoc
/**
 * The Interface IPageObjectAction defines ....
 * 
 * @author Peidong Hu
 */
public interface IPageObject {
	
	/**
	 * Gets the my wd.
	 *
	 * @return the my wd
	 */
	IMyWebDriver getMyWd();
	
	/**
	 * Gets the page name.
	 *
	 * @return the page name
	 */
	String getPageName();
	/**
	 * Gets the web element list.
	 *
	 * @return the web element list
	 */
	Map<Long, MyWebElement<?>> getMyWebElementList();
	
	/**
	 * Gets the cookies.
	 *
	 * @return the cookies
	 */
	List<Cookie> getCookies();	
	
	/**
	 * Gets the page title.
	 *
	 * @return the page title
	 */
	String getPageTitle();
	
	/**
	 * Sets the page title.
	 *
	 * @param pageTitle the new page title
	 */
	void setPageTitle(String pageTitle);
	
	/**
	 * Gets the html page source.
	 *
	 * @return the html page source
	 */
	String getPageHtmlSource();
}
