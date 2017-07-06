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

import org.openqa.selenium.Platform;

// TODO: Auto-generated Javadoc
/**
 * The Class MyChromeDriver defines ....
 * 
 * @author Jun Yang
 */
public class MySauceLabDriver extends MyRemoteDriver implements IMyWebDriver {

	/** The caps. */
	private String userName;
	
	/** The url. */
	private String accesskey;
	/**
	 * Instantiates a new my Chrome driver.
	 */
	public MySauceLabDriver(String userName, String accesskey) {
		
		super("chrome", "", Platform.ANY.toString(), "https://" + userName + ":" + accesskey + "@ondemand.saucelabs.com:443/wd/hub");
		this.setUserName(userName);
		this.setAccesskey(accesskey);
	}
		
	/**
	 * Instantiates a new my Chrome driver.
	 */
	public MySauceLabDriver() {
		
		super();
		 
		 
	}

	
		
	

	

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the accesskey
	 */
	public String getAccesskey() {
		return accesskey;
	}

	/**
	 * @param accesskey the accesskey to set
	 */
	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}

	
}
