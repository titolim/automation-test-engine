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
import org.bigtester.ate.browser.BrowserProfile;
import org.eclipse.jdt.annotation.Nullable;

// TODO: Auto-generated Javadoc
/**
 * The Class MyChromeDriver defines ....
 * 
 * @author Jun Yang
 */
public class MyOperaDriver extends AbstractWebDriverBase implements IMyWebDriver {

	/** The browser profile. */
	@Nullable
	final private BrowserProfile<ChromeOptions> browserProfile; //NOPMD
	/** The Constant BROWSERNAME. */
	/*private static final String BROWSERNAME = "operachromium";*/
	/** The Constant BROWSERDRVNAME. */
	private static final String BROWSERDRVNAME = "webdriver.chrome.driver";
	/** The Constant BROWSERWIN32PATH. */
	private static final String BROWSERWIN32PATH = "browserdriver/windows/operachromium/32bit/";
	/** The Constant BROWSERWIN64PATH. */
	private static final String BROWSERWIN64PATH = "browserdriver/windows/operachromium/64bit/";
	/** The Constant BROWSERLINUX32PATH. */
	private static final String BROWSERLINUX32PATH = "browserdriver/linux/operachromium/32bit/";
	/** The Constant BROWSERLINUX64PATH. */
	private static final String BROWSERLINUX64PATH = "browserdriver/linux/operachromium/64bit/";
	/** The Constant BROWSEROSX32PATH. */
	private static final String BROWSEROSX32PATH = "browserdriver/osx/operachromium/32bit/";
	/** The Constant BROWSEROSX32PATH. */
	private static final String BROWSEROSX64PATH = "browserdriver/osx/operachromium/64bit/";
	/** The Constant BROWSERWINFILENAME. */
	private static final String BROWSERWINFILENAME = "operadriver.exe";
	/** The Constant BROWSERLINUXFILENAME. */
	private static final String BROWSERLINUXFILENAME = "operadriver";
	/** The Constant BROWSERMACFILENAME. */
	private static final String BROWSEROSXFILENAME = "operadriver";

	/**
	 * Instantiates a new my Opera driver.
	 */
	public MyOperaDriver() {
		// TODO create Opera browsers and remote web driver handler
		super();
		browserProfile = null;
	}

	/**
	 * @return the browserProfile
	 */

	public BrowserProfile<ChromeOptions> getBrowserProfile() {
		final BrowserProfile<ChromeOptions> retVal = browserProfile;
		if (null == retVal) {
			throw new IllegalStateException(
					"browserProfile is not correctly populated");
		} else {
			return retVal;
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
			OSinfo osinfo = new OSinfo();
			EPlatform platform = osinfo.getOSname();
			String driverPath = BrowserWindow.getDriverPath(); //NOPMD
			
			switch (platform) {
			case Windows_32:
				/*versionNum = ReadXmlFile.parserXml(ReadXmlFile.REPOFILENAME, "windows", BROWSERNAME, ReadXmlFile.VERSION);*/
				if (driverPath == null)
					System.setProperty(BROWSERDRVNAME, BROWSERWIN32PATH + BROWSERWINFILENAME);
				else
					System.setProperty(BROWSERDRVNAME, driverPath + "/" + BROWSERWINFILENAME);
				break;
			case Windows_64:
				/*versionNum = ReadXmlFile.parserXml(ReadXmlFile.REPOFILENAME, "windows", BROWSERNAME, ReadXmlFile.VERSION);*/
				if (driverPath == null)
					System.setProperty(BROWSERDRVNAME, BROWSERWIN64PATH + BROWSERWINFILENAME);
				else
					System.setProperty(BROWSERDRVNAME, driverPath + "/" + BROWSERWINFILENAME);
				break;
			case Linux_32:
				/*versionNum = ReadXmlFile.parserXml(ReadXmlFile.REPOFILENAME, "linux", BROWSERNAME, ReadXmlFile.VERSION);*/
				if (driverPath == null)
					System.setProperty(BROWSERDRVNAME, BROWSERLINUX32PATH + BROWSERLINUXFILENAME);
				else
					System.setProperty(BROWSERDRVNAME, driverPath + "/" + BROWSERLINUXFILENAME);
				break;
			case Linux_64:
				/*versionNum = ReadXmlFile.parserXml(ReadXmlFile.REPOFILENAME, "linux", BROWSERNAME, ReadXmlFile.VERSION);*/
				if (driverPath == null)
				System.setProperty(BROWSERDRVNAME, BROWSERLINUX64PATH + BROWSERLINUXFILENAME);
			else
				System.setProperty(BROWSERDRVNAME, driverPath + "/" + BROWSERLINUXFILENAME);
				break;
			case Mac_OS_X_32:
				/*versionNum = ReadXmlFile.parserXml(ReadXmlFile.REPOFILENAME, "osx", BROWSERNAME, ReadXmlFile.VERSION);*/
				if (driverPath == null)
				System.setProperty(BROWSERDRVNAME, BROWSEROSX32PATH + BROWSEROSXFILENAME);
			else
				System.setProperty(BROWSERDRVNAME, driverPath + "/" + BROWSEROSXFILENAME);
				break;
			case Mac_OS_X_64:
				/*versionNum = ReadXmlFile.parserXml(ReadXmlFile.REPOFILENAME, "osx", BROWSERNAME, ReadXmlFile.VERSION);*/
				if (driverPath == null)
					System.setProperty(BROWSERDRVNAME, BROWSEROSX64PATH + BROWSEROSXFILENAME);
				else
					System.setProperty(BROWSERDRVNAME, driverPath + "/" + BROWSEROSXFILENAME);
				break;
			default:
				throw GlobalUtils.createNotInitializedException("operating system is not supported ");
			}
			retVal = new ChromeDriver();
		}
		setWebDriver(retVal);
		return retVal;
		/*
		 * if ( null == retVal) { if (null == getBrowserProfile().getProfile())
		 * { retVal = new ChromeDriver(); } else { retVal = new
		 * ChromeDriver(getBrowserProfile().getProfile()); }
		 * setWebDriver(retVal); } return retVal;
		 */
	}

}
