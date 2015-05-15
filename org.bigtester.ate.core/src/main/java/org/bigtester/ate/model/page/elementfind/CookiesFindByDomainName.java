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

import java.util.HashSet;
import java.util.Set;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.page.page.CookiesManager;
import org.eclipse.aether.util.StringUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

// TODO: Auto-generated Javadoc
/**
 * This class TestWindowFindByTitle defines ....
 * @author Peidong Hu
 *
 */
public class CookiesFindByDomainName extends BaseCookieFinderImpl implements ICookieFinder, ITestObjectFinderImpl{

	/**
	 * Instantiates a new test window find by title.
	 *
	 * @param title the title
	 */
	public CookiesFindByDomainName(String domain) {
		super();
		if (StringUtils.isEmpty(domain)) throw GlobalUtils.createNotInitializedException("domain name of the cookies");
		setDomain(domain); 
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CookiesManager doFind(IMyWebDriver myWebDriver) throws NoSuchElementException {
		WebDriver webD = myWebDriver.getWebDriver();
		if (null == webD) {
			throw GlobalUtils.createNotInitializedException("web driver");
		} else {
			Set<Cookie> coos = webD.manage().getCookies();
			if (null == coos) coos = new HashSet<Cookie>();
			return new CookiesManager(myWebDriver, getDomain(), coos);
		
		}
	}

}
