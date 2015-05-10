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
import java.util.HashSet;
import java.util.Set;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.model.page.page.CookiesManager;
import org.bigtester.ate.test.BigtesterProjectTest;
import org.eclipse.jdt.annotation.Nullable;

import static org.mockito.Mockito.mock;

import org.openqa.selenium.Cookie;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;

/**
 * The Class CookiesManagerTest.
 *
 * @author Peidong Hu
 */

@ContextConfiguration(locations = { "classpath:bigtesterTestNG/testSuite01/loginSuccess.xml" })
public class CookiesManagerTest extends BigtesterProjectTest {

	/** The cookies. */

	private static Set<Cookie> cookies;

	/** The cookie1. */
	private static Cookie cookie1 = new Cookie("cookie1", "cookie1value");

	/** The cookie2. */
	private static Cookie cookie2 = new Cookie("cookie2", "cookie2value");

	/** The cookie3. */
	private static Cookie cookie3 = new Cookie("cookie3", "cookie3value");

	/** The cookies mng. */
	private transient CookiesManager cookiesMng;

	/** The file name with absolute path. */
	private static String fileNameWithAbsolutePath = "~/testfile";
	/**
	 * Setup.
	 */
	@BeforeClass
	public void setup() {
		cookies = new HashSet<Cookie>();
		cookies.add(cookie1);
		cookies.add(cookie2);
		cookies.add(cookie3);

		final Set<Cookie> cookies2 = cookies;
		if (cookies2 == null) {
			
		} else {
			cookiesMng = new CookiesManager(getMyDriver(), cookies2);
			final String fileNameWithAbsolutePath2 = fileNameWithAbsolutePath;
			if (fileNameWithAbsolutePath2 != null) {//NOPMD
				cookiesMng.setExportFileNameWithAbsolutePath(fileNameWithAbsolutePath2);
			} else {
				// TODO handle null value
			}
		}

	}

	/**
	 * Hello world test
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void exportFileTest() throws InterruptedException {
		cookiesMng.saveToSingleFile();
		File newFile = new File(cookiesMng.getExportFileNameWithAbsolutePath());
		Assert.assertTrue(newFile.exists());
	}

}
