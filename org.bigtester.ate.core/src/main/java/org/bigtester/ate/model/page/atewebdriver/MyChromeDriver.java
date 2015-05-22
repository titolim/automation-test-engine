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
package org.bigtester.ate.model.page.atewebdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver; 
import org.openqa.selenium.chrome.ChromeOptions;
import org.bigtester.ate.GlobalUtils;
import org.eclipse.jdt.annotation.Nullable;

// TODO: Auto-generated Javadoc
/**
 * The Class MyChromeDriver defines ....
 * 
 * @author Jun Yang
 */
public class MyChromeDriver extends AbstractWebDriverBase implements IMyWebDriver {

	/** The browser profile. */
	@Nullable
	final private ChromeFeatureProfile browserProfile = new ChromeFeatureProfile(); //NOPMD
	
	/** The browsertypename. */
	final public static String BROWSERTYPENAME = "Chrome"; 
	/** The Constant BROWSERNAME. */
/*	private static final String BROWSERNAME = "googlechrome";*/
	/** The Constant BROWSERDRVNAME. */
	private static final String BROWSERDRVNAME = "webdriver.chrome.driver";
	/** The Constant BROWSERWIN32PATH. */
	private static final String BROWSERWIN32PATH = "windows/googlechrome/32bit/";
	/** The Constant BROWSERWIN64PATH. */
	private static final String BROWSERWIN64PATH = "windows/googlechrome/64bit/";
	/** The Constant BROWSERLINUX32PATH. */
	private static final String BROWSERLINUX32PATH = "linux/googlechrome/32bit/";
	/** The Constant BROWSERLINUX64PATH. */
	private static final String BROWSERLINUX64PATH = "linux/googlechrome/64bit/";
	/** The Constant BROWSEROSX32PATH. */
	private static final String BROWSEROSX32PATH = "osx/googlechrome/32bit/";
	/** The Constant BROWSEROSX32PATH. */
	private static final String BROWSEROSX64PATH = "osx/googlechrome/64bit/";
	/** The Constant BROWSERWINFILENAME. */
	private static final String BROWSERWINFILENAME = "chromedriver.exe";
	/** The Constant BROWSERLINUXFILENAME. */
	private static final String BROWSERLINUXFILENAME = "chromedriver";
	/** The Constant BROWSERMACFILENAME. */
	private static final String BROWSER0SXFILENAME = "chromedriver";

	
	/**
	 * Instantiates a new my Chrome driver.
	 */
	public MyChromeDriver(boolean preserveCookiesOnExecutions) {
		
		super();
		getBrowserProfile().setPreserveCookiesOnExecutions(preserveCookiesOnExecutions);
		
	}
	
	/**
	 * Instantiates a new my Chrome driver.
	 */
	public MyChromeDriver() {
		
		super();
		 
		 
	}

	/**
	 * @return the browserProfile
	 */

	public   ChromeFeatureProfile  getBrowserProfile() {
		final ChromeFeatureProfile  retVal = browserProfile;
		if (null == retVal) {
			throw new IllegalStateException(
					"browserProfile is not correctly populated");

		} else {
			return retVal;
		}
	}

	/**
	 * Sets the chrome driver system env.
	 */
	public static void setChromeDriverSystemEnv() {
		OSinfo osinfo = new OSinfo();
		EPlatform platform = osinfo.getOSname();
		String driverPath = GlobalUtils.getDriverPath(); //NOPMD
		
		switch (platform) {
		case Windows_32:
			/*versionNum = ReadXmlFile.parserXml(ReadXmlFile.REPOFILENAME, "windows", BROWSERNAME, ReadXmlFile.VERSION);*/
			if (driverPath == null)
				System.setProperty(BROWSERDRVNAME, GlobalUtils.DEFAULT_DRIVER_PATH + GlobalUtils.PATH_DELIMITER 
						           + BROWSERWIN32PATH + BROWSERWINFILENAME);
			else
				System.setProperty(BROWSERDRVNAME, driverPath + GlobalUtils.PATH_DELIMITER 
						           + BROWSERWIN32PATH + BROWSERWINFILENAME);
			break;
		case Windows_64:
			/*versionNum = ReadXmlFile.parserXml(ReadXmlFile.REPOFILENAME, "windows", BROWSERNAME, ReadXmlFile.VERSION);*/
			if (driverPath == null)
				System.setProperty(BROWSERDRVNAME, GlobalUtils.DEFAULT_DRIVER_PATH + GlobalUtils.PATH_DELIMITER 
						           + BROWSERWIN64PATH + BROWSERWINFILENAME);	
			else
				System.setProperty(BROWSERDRVNAME, driverPath + GlobalUtils.PATH_DELIMITER 
						           + BROWSERWIN64PATH + BROWSERWINFILENAME);
			break;
		case Linux_32:
			/*versionNum = ReadXmlFile.parserXml(ReadXmlFile.REPOFILENAME, "linux", BROWSERNAME, ReadXmlFile.VERSION);*/
			if (driverPath == null)
				System.setProperty(BROWSERDRVNAME, GlobalUtils.DEFAULT_DRIVER_PATH + GlobalUtils.PATH_DELIMITER 
						           + BROWSERLINUX32PATH + BROWSERLINUXFILENAME);	
			else
				System.setProperty(BROWSERDRVNAME, driverPath + GlobalUtils.PATH_DELIMITER 
						           + BROWSERLINUX32PATH + BROWSERLINUXFILENAME);
			break;
		case Linux_64:
			/*versionNum = ReadXmlFile.parserXml(ReadXmlFile.REPOFILENAME, "linux", BROWSERNAME, ReadXmlFile.VERSION);*/
			if (driverPath == null)
				System.setProperty(BROWSERDRVNAME, GlobalUtils.DEFAULT_DRIVER_PATH + GlobalUtils.PATH_DELIMITER 
						           + BROWSERLINUX64PATH + BROWSERLINUXFILENAME);	
			else
				System.setProperty(BROWSERDRVNAME, driverPath + GlobalUtils.PATH_DELIMITER 
						           + BROWSERLINUX64PATH + BROWSERLINUXFILENAME);
			break;
		case Mac_OS_X_32:
			/*versionNum = ReadXmlFile.parserXml(ReadXmlFile.REPOFILENAME, "osx", BROWSERNAME, ReadXmlFile.VERSION);*/
			if (driverPath == null)
				System.setProperty(BROWSERDRVNAME, GlobalUtils.DEFAULT_DRIVER_PATH + GlobalUtils.PATH_DELIMITER 
						           + BROWSEROSX32PATH + BROWSER0SXFILENAME);
			else 
				System.setProperty(BROWSERDRVNAME, driverPath + GlobalUtils.PATH_DELIMITER 
						           + BROWSEROSX32PATH + BROWSER0SXFILENAME);				
			break;
		case Mac_OS_X_64:
			/*versionNum = ReadXmlFile.parserXml(ReadXmlFile.REPOFILENAME, "osx", BROWSERNAME, ReadXmlFile.VERSION);*/
			if (driverPath == null)
				System.setProperty(BROWSERDRVNAME, GlobalUtils.DEFAULT_DRIVER_PATH + GlobalUtils.PATH_DELIMITER 
						           + BROWSEROSX64PATH + BROWSER0SXFILENAME);	
			else
				System.setProperty(BROWSERDRVNAME, driverPath + GlobalUtils.PATH_DELIMITER 
						           + BROWSEROSX64PATH + BROWSER0SXFILENAME);
			break;
		default:
			throw GlobalUtils.createNotInitializedException("operating system is not supported ");
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Nullable
	public WebDriver getWebDriver() {
		return super.getWebDriver();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebDriver getWebDriverInstance() {
		WebDriver retVal = getWebDriver();
		if (null == retVal) {
			setChromeDriverSystemEnv();
			if (getBrowserProfile().isPreserveCookiesOnExecutions()) {
				ChromeOptions ops = new ChromeOptions();
				
				ops.addArguments("--user-data-dir=" + getBrowserProfile().getTestCaseChromeUserDataDir());
				retVal = new ChromeDriver(ops);
			} else {
				retVal = new ChromeDriver();
			}
		}
		setWebDriver(retVal);
		return retVal;

	}

}
