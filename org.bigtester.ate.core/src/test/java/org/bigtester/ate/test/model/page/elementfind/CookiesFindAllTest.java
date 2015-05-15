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
package org.bigtester.ate.test.model.page.elementfind;

import static org.mockito.Mockito.when;

import org.bigtester.ate.model.page.elementfind.CookiesFindAll;
import org.bigtester.ate.model.page.page.CookiesManager;
import org.bigtester.ate.test.BigtesterProjectTest;
import org.bigtester.ate.test.data.cookies.CookiesData;
import org.openqa.selenium.Cookie;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

// TODO: Auto-generated Javadoc
/**
 * This class CookiesFindAllTest defines ....
 * @author Peidong Hu
 *
 */
@ContextConfiguration(locations = { "classpath:bigtesterTestNG/testSuite01/loginSuccess.xml" })

public class CookiesFindAllTest extends BigtesterProjectTest{
	/** The cookies data. */
	final private CookiesData cookiesData = new CookiesData();
	
	/** The cookies mng. */
	final private transient CookiesFindAll cookiesFindAll;
	
	/**
	 * Instantiates a new cookies find all test.
	 */
	public CookiesFindAllTest() {
		super();
		cookiesFindAll = new CookiesFindAll();
	}

	/**
	 * @return the cookiesFindAll
	 */
	public CookiesFindAll getCookiesFindAll() {
		return cookiesFindAll;
	}

	/**
	 * @return the cookiesData
	 */
	public CookiesData getCookiesData() {
		return cookiesData;
	}
	
	/**
	 * Setup.
	 */
	@BeforeClass
	public void setup() {
		when(getMyMockedDriver().getWebDriverInstance()).thenReturn(getMockedDriver());
		when(getMockedDriver().manage()).thenReturn(getOptions());
		when(getOptions().getCookies()).thenReturn(cookiesData.getCookies());
	}
	
	/**
	 * Do find test.
	 */
	@Test
	public void doFindTest() {
		CookiesManager cookiesMng = getCookiesFindAll().doFind(getMyMockedDriver());
		
		Assert.assertEquals(cookiesData.getCookies().size(), cookiesMng.getCookies().size());
		for (Cookie cookie : cookiesData.getCookies()) {
			Assert.assertTrue(cookiesMng.getCookies().containsValue(cookie));
		}
	}
}
