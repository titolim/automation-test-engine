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

import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;

// TODO: Auto-generated Javadoc
/**
 * The Class MyFirefoxDriver defines ....
 * 
 * @author Peidong Hu
 */
public class MyFirefoxDriver extends AbstractWebDriverBase implements
		IMyWebDriver {

	/** The browser profile. */
	@Nullable
	final private FirefoxFeatureProfile browserProfile;
	
	/** The Constant BROWSERTYPENAME. */
	final public static String BROWSERTYPENAME = "Firefox"; 
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
		browserProfile = new FirefoxFeatureProfile(  profileName);
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
	 * {@inheritDoc}
	 */
	@Override
	public WebDriver getWebDriverInstance() {
		WebDriver retVal = super.getWebDriver();
		if (null == retVal) {
			FirefoxFeatureProfile bPro = getBrowserProfile();
			if (null == bPro) {
				retVal = new FirefoxDriver();
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
