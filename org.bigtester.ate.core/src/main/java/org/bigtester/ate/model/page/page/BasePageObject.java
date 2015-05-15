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
package org.bigtester.ate.model.page.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.model.page.PageModelBase;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.utils.ThinkTime;
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;


// TODO: Auto-generated Javadoc
/**
 * This class AbstractPageObject defines ....
 * 
 * @author Peidong Hu
 * 
 */
public class BasePageObject extends PageModelBase {

	/** The page name. */
	@Nullable
	private String pageName;
	
	/** The data file. */
	@Nullable
	private Resource dataFile;
	
	/** The web element list. */
	@Nullable
	private Map<Long, MyWebElement<?>> myWebElementList = new HashMap<Long, MyWebElement<?>>();//NOPMD

	/** The cookies. */
	@Nullable
	private List<Cookie> cookies = new ArrayList<Cookie>();
	
	/** The page title. */
	@Nullable
	private String pageTitle;
	
	/**
	 * @param myWd
	 */
	public BasePageObject(IMyWebDriver myWd) {
		super(myWd);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Gets the page title.
	 *
	 * @return the pageTitle
	 */
	public String getPageTitle() {
		final String retVal = pageTitle;
		if (null == retVal) {
			throw new IllegalStateException("pageTitle is not correctly populated");
			
		} else {
			return retVal;
		}
	}

	/**
	 * Sets the page title.
	 *
	 * @param pageTitle the pageTitle to set
	 */
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	/**
	 * Gets the cookies.
	 *
	 * @return the cookies
	 */
	public List<Cookie> getCookies() {
		final List<Cookie> retVal = cookies;
		if (null == retVal) {
			throw new IllegalStateException("cookies is not correctly populated");
			
		} else {
			return retVal;
		}
	}

	/**
	 * Sets the cookies.
	 *
	 * @param cookies the cookies to set
	 */
	public void setCookies(List<Cookie> cookies) {
		this.cookies = cookies;
	}

	/**
	 * Sets the my web element list.
	 * 
	 * @param myWebElementList
	 *            the myWebElementList to set
	 */
	public void setMyWebElementList(Map<Long, MyWebElement<?>> myWebElementList) {
		this.myWebElementList = myWebElementList;
	}

	/**
	 * Gets the web element list.
	 * 
	 * @return the web element list
	 */
	public Map<Long, MyWebElement<?>> getMyWebElementList() {
		final Map<Long, MyWebElement<?>> retVal = myWebElementList;
		if (null == retVal) {
			throw new IllegalStateException("mywebelementlist is not correctly populated");
			
		} else {
			return retVal;
		}
	}

	
	/**
	 * Gets the page name.
	 *
	 * @return the pageName
	 */
	public String getPageName() {
		final String retVal = pageName;
		if (null == retVal) {
			throw new IllegalStateException("pageName is not correctly populated");
			
		} else {
			return retVal;
		}
	}

	/**
	 * Sets the page name.
	 *
	 * @param pageName            the pageName to set
	 */
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	/**
	 * Gets the data file.
	 *
	 * @return the dataFile
	 */
	@Nullable
	public Resource getDataFile() {
		return dataFile;
	}

	/**
	 * Sets the data file.
	 *
	 * @param dataFile the dataFile to set
	 */
	public void setDataFile(Resource dataFile) {
		this.dataFile = dataFile;
	}
	/**
	 * @return the pageHtmlSource
	 * We don't use member to store page source,
	 * This function will make sure that the pageSource is always reflecting the current status of the page source.
	 */
	public String getPageHtmlSource() {
		//TODO to make sure that the events on page all finished before get source.
		ThinkTime newTT = new ThinkTime(10);
		newTT.setTimer();
		WebDriver webD = getMyWd().getWebDriver();
		if (null == webD) {
			throw GlobalUtils.createNotInitializedException("web driver");
		}
		else {
			String retVal = webD.getPageSource();
			if (null == retVal || !StringUtils.hasText(retVal)) {
				throw GlobalUtils
						.createInternalError("Web Driver internal error.!");
			}
			return retVal;
		}
				
	}
	
}
