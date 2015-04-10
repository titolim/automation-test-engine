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

import org.bigtester.ate.browser.BrowserProfile;
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

// TODO: Auto-generated Javadoc
/**
 * The Class MyFirefoxDriver defines ....
 * 
 * @author Peidong Hu
 */
public class MyFirefoxDriver extends AbstractWebDriverBase implements IMyWebDriver{
	
	/** The browser profile. */
	@Nullable
	final private BrowserProfile<FirefoxProfile> browserProfile;
	
	/**
	 * Instantiates a new my firefox driver.
	 */
	public MyFirefoxDriver() {
		//TODO create multi browsers and remote web driver handler
		super();
		
		//setWebDriver(new FirefoxDriver());
		//TODO need to re-code to use null pattern object.
		browserProfile = null;
	}

	/**
	 * Instantiates a new my firefox driver.
	 *
	 * @param profileName the profile name
	 */
	public MyFirefoxDriver(String profileName) {
		super();
		browserProfile = new BrowserProfile<FirefoxProfile>(FirefoxProfile.class, profileName);
		//setWebDriver(new FirefoxDriver(browserProfile.getProfile()));
	}
	
	
	/**
	 * @return the browserProfile
	 */
	@Nullable
	public BrowserProfile<FirefoxProfile> getBrowserProfile() {
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
	 * {@inheritDoc}
	 */
	@Override
	public WebDriver getWebDriverInstance() {
		WebDriver retVal = super.getWebDriver();
		if ( null == retVal) {
			BrowserProfile<FirefoxProfile> bPro = getBrowserProfile();
			if (null == bPro) {
				retVal = new FirefoxDriver();
			} else {
				FirefoxBinary binary=new FirefoxBinary();
				binary.addCommandLineOptions("-no-remote");
				retVal = new FirefoxDriver(binary, bPro.getProfile());
			}
			setWebDriver(retVal);
			
		}
		return retVal;
	}
	
}
