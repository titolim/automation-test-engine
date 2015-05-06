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
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.model.page.atewebdriver.EPlatform;
import org.bigtester.ate.model.page.atewebdriver.OSinfo;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * The Class CookiesManagerTest.
 *
 * @author Peidong Hu
 */
//@ContextConfiguration(locations = { "classpath:org/bigtester/ate/test/model/page/page/AppContext.xml" })
//@ContextConfiguration(locations = {"classpath:bigtesterTest/testproject.xml", "classpath:bigtesterTest/testSuite01/homePageValidation.xml" })
public class CookiesManagerTest{
  final private static Map<String, String> WinPathURLSpecialEncoding = new ConcurrentHashMap<String, String>();
  //
  
  
  /**
   * First test.
   */
//  @Test
//  public void firstTest() {
//	  System.out.println(getAllJarsClassPathInMavenLocalRepoTest());
//  }
  
  private @Nullable URI fixFileURL(URL url) {
		if (!"file".equals(url.getProtocol()))//NOPMD
			throw new IllegalArgumentException();// NOPMD
		File tmp = new File(url.getFile());
		if 
		for (Map.Entry<String, String> entry : WinPathURLSpecialEncoding.entrySet()  ) {
			
		}
		return tmp.toURI();
	}
  
  @Test
  public void getAllJarsClassPathInMavenLocalRepoTest() {
	  	
		Class<?> cls;
		String retVal;
		try {
			cls = Class.forName("org.bigtester.ate.TestProjectRunner");// NOPMD
		} catch (ClassNotFoundException e) {
			retVal = System.getProperty("java.class.path")
					+ ":dist/InlineCompiler.jar:target/*.jar";
			System.out.println( retVal);// NOPMD
			return;
		}

		// returns the ClassLoader object associated with this Class
		ClassLoader cLoader = cls.getClassLoader();

		URL[] paths = ((URLClassLoader) cLoader).getURLs();
		OSinfo osinfo = new OSinfo();
		EPlatform platform = osinfo.getOSname();
		String pathSep;
		if (platform == EPlatform.Windows_64
				|| platform == EPlatform.Windows_32) {
			pathSep = ";";
		} else {
			pathSep = ":";
		}

		retVal = "target" + System.getProperty("file.separator") + "classes"
				+ pathSep + "target" + System.getProperty("file.separator")
				+ "*.jar" + pathSep + "dist"
				+ System.getProperty("file.separator") + "InlineCompiler.jar";
		for (URL path : paths) {
			
			if (null == path)
				throw GlobalUtils.createInternalError("classloader url wrong");
			URI fileURI = fixFileURL(path);
			retVal = retVal + pathSep + Paths.get(fileURI).toString();// NOPMD
			
		}

		System.out.println( retVal );
		
	}

/**
 * @return the winPathURLSpecialEncoding
 */
public Map<String, String> getWinPathURLSpecialEncoding() {
	return WinPathURLSpecialEncoding;
}
}
