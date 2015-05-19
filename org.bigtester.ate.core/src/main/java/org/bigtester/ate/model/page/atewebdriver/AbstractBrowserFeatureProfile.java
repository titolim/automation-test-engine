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
package org.bigtester.ate.model.page.atewebdriver;

import java.io.File;

import org.bigtester.ate.constant.GlobalConstants;

// TODO: Auto-generated Javadoc
/**
 * This class ProfileSelector defines ....
 *
 * @author Peidong Hu
 * @param <T>
 *            the generic type
 */
abstract public class AbstractBrowserFeatureProfile {

	/** The preserve cookies on executions. */
	private boolean preserveCookiesOnExecutions;

	/** The Constant webDriverProfileBasePath. */
	final static public String WEBDRIVERFEATUREPROFILEBASEPATH = GlobalConstants.ATE_EXECUTION_TEMP_DATA_ROOTFOLDER
			+ File.separator + "webDriverProfile";

	/** The web driver feature profile path. */
	private String webDriverFeatureProfilePath;

	/**
	 * Instantiates a new browser feature profile.
	 *
	 * @param webDriverFeatureProfilePath
	 *            the web driver feature profile path
	 */
	public AbstractBrowserFeatureProfile() {

		webDriverFeatureProfilePath = AbstractBrowserFeatureProfile.WEBDRIVERFEATUREPROFILEBASEPATH
				+ File.separator
				+ OSinfo.getInstance().getOSname().toString()
				+ File.separator + getBrowserTypeString() + File.separator;
	}

	/**
	 * @return the preserveCookiesOnExecutions
	 */
	public boolean isPreserveCookiesOnExecutions() {
		return preserveCookiesOnExecutions;
	}

	/**
	 * @param preserveCookiesOnExecutions
	 *            the preserveCookiesOnExecutions to set
	 */
	public void setPreserveCookiesOnExecutions(
			boolean preserveCookiesOnExecutions) {
		this.preserveCookiesOnExecutions = preserveCookiesOnExecutions;
	}

	/**
	 * @return the webDriverProfilePath
	 */
	public String getWebDriverFeatureProfilePath() {

		return webDriverFeatureProfilePath;

	}

	/**
	 * @param webDriverProfilePath
	 *            the webDriverProfilePath to set
	 */
	public void setWebDriverFeatureProfilePath(
			String webDriverFeatureProfilePath) {
		this.webDriverFeatureProfilePath = webDriverFeatureProfilePath;
	}

	/**
	 * Gets the browser type string.
	 *
	 * @return the browser type string
	 */
	abstract public String getBrowserTypeString();

}
