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
package org.bigtester.ate.test.model.page.page;

import java.io.File;

import org.bigtester.ate.model.page.page.CookiesManager;
import org.bigtester.ate.test.BigtesterProjectTest;
import org.bigtester.ate.test.data.cookies.CookiesData;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;

import static org.mockito.Mockito.when;

/**
 * The Class CookiesManagerTest.
 *
 * @author Peidong Hu
 */

@ContextConfiguration(locations = { "classpath:bigtesterTestNG/testSuite01/loginSuccess.xml" })
public class CookiesManagerTest extends BigtesterProjectTest {
	
	/** The cookies data. */
	final private CookiesData cookiesData = new CookiesData();
	
	/** The cookies mng. */
	final private transient CookiesManager cookiesMng;

	/** The file name with absolute path. */
	private static String fileNameWithAbsolutePath = "logs/testfile";

	/**
	 * Instantiates a new cookies manager test.
	 */
	public CookiesManagerTest() {
		super();
		cookiesMng = new CookiesManager(getMyMockedDriver(), cookiesData.getCookies());
	}
	
	/**
	 * Setup.
	 */
	@BeforeClass
	public void setup() {
		cookiesMng.setExportFileNameWithAbsolutePath(fileNameWithAbsolutePath);
		when(getMyMockedDriver().getWebDriverInstance()).thenReturn(getMockedDriver());
		when(getMockedDriver().manage()).thenReturn(getOptions());
//		Mockito.doThrow(GlobalUtils.createInternalError("errora")).when(getOptions()).addCookie(Mockito.refEq(cookie4));
//		Mockito.doNothing().when(getOptions()).addCookie(Mockito.refEq(cookie2));
//		Mockito.doNothing().when(getOptions()).addCookie(Mockito.eq(cookie4));
	}

	/**
	 * Hello world test
	 * 
	 * @throws InterruptedException
	 */
	@Test (priority = 1)
	public void exportToSingleFileTest() throws InterruptedException {
		cookiesMng.saveToSingleFile();
		File newFile = new File(cookiesMng.getExportFileNameWithAbsolutePath());
		Assert.assertTrue(newFile.exists());
	}
	
	/**
	 * Import file test.
	 */
	@Test (priority = 2)
	public void importFromSingleFileTest() {
		cookiesMng.setImportFileNameWithAbsolutePath(fileNameWithAbsolutePath);
		cookiesMng.importFromSingleFile();
		Mockito.verify(getOptions()).addCookie(cookiesData.getCookie1());
		Mockito.verify(getOptions()).addCookie(cookiesData.getCookie2());
		Mockito.verify(getOptions()).addCookie(cookiesData.getCookie3());
	}
	
	/**
	* {@inheritDoc}
	*/
	@AfterClass
	public void tearDown() {
		File newFile = new File(cookiesMng.getExportFileNameWithAbsolutePath());
		if (newFile.exists()) newFile.delete();
	}

	/**
	 * @return the cookiesData
	 */
	public CookiesData getCookiesData() {
		return cookiesData;
	}

}
