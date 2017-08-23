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

import org.bigtester.ate.GlobalUtils;
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

// TODO: Auto-generated Javadoc
/**
 * The Class MyFirefoxDriver defines ....
 * 
 * @author Peidong Hu
 */
public class MyFirefoxDriver extends AbstractWebDriverBase implements
		IMyWebDriver {
	
	/** The GECKON Driver Name and Path. */	 
	/** The Constant BROWSERNAME. */
	private static final String MARIONETTENAME = "marionette";
	/** The Constant BROWSERDRVNAME. */
	private static final String BROWSERDRVNAME = "webdriver.gecko.driver";
	/** The Constant BROWSERWIN32PATH. */
	private static final String BROWSERWIN32PATH = "windows/marionette/32bit/";
	/** The Constant BROWSERWIN64PATH. */
	private static final String BROWSERWIN64PATH = "windows/marionette/64bit/";
	/** The Constant BROWSERLINUX32PATH. */
	private static final String BROWSERLINUX32PATH = "linux/marionette/32bit/";
	/** The Constant BROWSERLINUX64PATH. */
	private static final String BROWSERLINUX64PATH = "linux/marionette/64bit/";
	/** The Constant BROWSEROSX32PATH. */
	private static final String BROWSEROSX32PATH = "osx/marionette/32bit/";
	/** The Constant BROWSEROSX32PATH. */
	private static final String BROWSEROSX64PATH = "osx/marionette/64bit/";
	/** The Constant BROWSERWINFILENAME. */
	private static final String BROWSERWINFILENAME = "geckodriver.exe";
	/** The Constant BROWSERLINUXFILENAME. */
	private static final String BROWSERLINUXFILENAME = "geckodriver";
	/** The Constant BROWSERMACFILENAME. */
	private static final String BROWSER0SXFILENAME = "geckodriver";


	/** The browser profile. */
	@Nullable
	final private FirefoxFeatureProfile browserProfile;
	
	/** The Constant BROWSERTYPENAME. */
	final public static String BROWSERTYPENAME = "firefox"; 
	/**
	 * Instantiates a new my firefox driver.
	 */
	public MyFirefoxDriver() {
		super();

		browserProfile = null;
	}

	/**
	 * Instantiates a new my firefox driver.
	 *
	 * @param profileName
	 *            the profile name
	 */
	public MyFirefoxDriver(String profileName) {
		super();
		browserProfile = new FirefoxFeatureProfile(profileName);
	}

	/**
	 * @return the browserProfile
	 */
	@Nullable
	public FirefoxFeatureProfile getBrowserProfile() {
		return browserProfile;
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
	 * Sets the firefox driver system env.
	 */
	public static void setFirefoxDriverSystemEnv() {
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
				System.setProperty(BROWSERDRVNAME, "E:\\geckodriver.exe");
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
	public WebDriver getWebDriverInstance() {
		WebDriver retVal = super.getWebDriver();
		if (null == retVal) {
			FirefoxFeatureProfile bPro = getBrowserProfile();			
            setFirefoxDriverSystemEnv(); 
            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
    		capabilities.setCapability(MARIONETTENAME, true);
    		capabilities.setBrowserName(BROWSERTYPENAME);
    		capabilities.setVersion("");
    		capabilities.setPlatform(Platform.ANY);
			if (null == bPro) {
				retVal = new FirefoxDriver(capabilities);
			} else {
				FirefoxBinary binary = new FirefoxBinary();
				binary.addCommandLineOptions("-no-remote");
				retVal = new FirefoxDriver(binary, bPro.getProfile());
			}
			setWebDriver(retVal);

		}
		return retVal;
	}

}
