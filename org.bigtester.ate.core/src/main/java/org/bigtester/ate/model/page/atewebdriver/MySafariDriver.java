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
import org.openqa.selenium.safari.SafariDriver;
import org.eclipse.jdt.annotation.Nullable;

// TODO: Auto-generated Javadoc
/**
 * The Class MysafariDriver defines ....
 * 
 * @author Jun Yang
 */
public class MySafariDriver extends AbstractWebDriverBase implements IMyWebDriver {
	
	/** The Constant BROWSERTYPENAME. */
	final public static String BROWSERTYPENAME = "Safari"; 
	/** The browser profile. */
	@Nullable
	final private SafariFeatureProfile browserProfile;

	// /** The Constant BROWSERNAME. */
	// final static private String BROWSERNAME = "webdriver.safari.driver";



	/**
	 * Instantiates a new my Safari driver.
	 *
	 * @param profileName
	 *            the profile name
	 */
	public MySafariDriver() {
		super();
		browserProfile = new SafariFeatureProfile (  );
	}

	/**
	 * @return the browserProfile
	 */

	public SafariFeatureProfile getBrowserProfile() {

		final  SafariFeatureProfile retVal = browserProfile;
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
			retVal = new SafariDriver();
			setWebDriver(retVal);
		}
		
		return retVal;
		/*
		 * if ( null == retVal) { if (null == getBrowserProfile().getProfile())
		 * { retVal = new SafariDriver(); } else { retVal = new
		 * SafariDriver(getBrowserProfile().getProfile()); }
		 * setWebDriver(retVal);
		 * 
		 * } return retVal;
		 */
	}

}
