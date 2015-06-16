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
package org.bigtester.ate.model.data;

import org.apache.commons.lang3.StringUtils;
import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.constant.EnumRunTimeDataType;
import org.bigtester.ate.constant.ExceptionErrorCode;
import org.bigtester.ate.constant.ExceptionMessage;
import org.bigtester.ate.model.data.exception.RuntimeDataException;
import org.bigtester.ate.model.page.page.IPageObject;
import org.eclipse.jdt.annotation.Nullable;

/**
 * This class is the abstract class of any input data created in script (Non
 * File db generated input data).
 * 
 * @author Peidong Hu
 *
 */
public abstract class AbstractRunTimeInputDataHolder extends BaseInputDataValue{

	/** The page that has been used to parse the data. */
	final private String springBeanID;
	
	/** The page. */
	@Nullable
	private IPageObject page;
	

	/** The data type. */
	final private EnumRunTimeDataType dataType;

	/** The page html left boundry. */
	@Nullable
	private String pageHtmlLeftBoundry;

	/** The oage html right boundry. */
	@Nullable
	private String pageHtmlRightBoundry;

	
	/**
	 * @return the dataType
	 */
	public EnumRunTimeDataType getDataType() {
		return dataType;
	}

	/**
	 * Instantiates a new run time data holder.
	 *
	 * @param dataType
	 *            the data type
	 * @param pageHtmlRightBoundry
	 */
	public AbstractRunTimeInputDataHolder(EnumRunTimeDataType dataType,
			String pageHtmlLeftBoundry, String pageHtmlRightBoundry,
			IPageObject page, String springBeanID) {
		super("");
		this.dataType = dataType;
		this.pageHtmlLeftBoundry = pageHtmlLeftBoundry;
		this.pageHtmlRightBoundry = pageHtmlRightBoundry;
		this.page = page;
		this.springBeanID = springBeanID;
	}

	/**
	 * Instantiates a new run time data holder.
	 *
	 * @param dataType
	 *            the data type
	 * @param strDataValue
	 *            the str data value
	 * @param springBeanID 
	 */
	public AbstractRunTimeInputDataHolder(EnumRunTimeDataType dataType, String strDataValue, String springBeanID) {
		super(strDataValue);
		this.dataType = dataType;
		this.springBeanID = springBeanID;
	}
	
	/**
	 * Instantiates a new run time data holder.
	 *
	 * @param dataType
	 *            the data type
	 * @param strDataValue
	 *            the str data value
	 * @param springBeanID 
	 */
	public AbstractRunTimeInputDataHolder(EnumRunTimeDataType dataType, String springBeanID) {
		super("");
		this.dataType = dataType;
		this.springBeanID = springBeanID;
	}

	/**
	 * @return the pageHtmlLeftBoundry
	 */
	public String getPageHtmlLeftBoundry() {
		final String pageHtmlLeftBoundry2 = pageHtmlLeftBoundry;
		if (null == pageHtmlLeftBoundry2 ) {
			throw GlobalUtils.createNotInitializedException("pageHtmlLeftBoundry");
		} else {
			return pageHtmlLeftBoundry2;
		}
	}

	/**
	 * @return the oageHtmlRightBoundry
	 */
	public String getPageHtmlRightBoundry() {
		final String pageHtmlLeftBoundry2 = pageHtmlRightBoundry;
		if (null == pageHtmlLeftBoundry2 ) {
			throw GlobalUtils.createNotInitializedException("pageHtmlRightBoundry");
		} else {
			return pageHtmlLeftBoundry2;
		}
	}

	/**
	 * @return the page
	 */
	public IPageObject getPage() {
		final IPageObject page2 = page;
		if (null == page2 ) {
			throw GlobalUtils.createNotInitializedException("page object");
		} else {
			return page2;
		}
	}
	/**
	 * @param page the page to set
	 */
	public void setPage(IPageObject page) {
		this.page = page;
	}

	/**
	 * @param pageHtmlLeftBoundry the pageHtmlLeftBoundry to set
	 */
	public void setPageHtmlLeftBoundry(String pageHtmlLeftBoundry) {
		this.pageHtmlLeftBoundry = pageHtmlLeftBoundry;
	}

	/**
	 * @param pageHtmlRightBoundry the pageHtmlRightBoundry to set
	 */
	public void setPageHtmlRightBoundry(String pageHtmlRightBoundry) {
		this.pageHtmlRightBoundry = pageHtmlRightBoundry;
	}
	
	
	/**
	 * Parses the left right boundry data.
	 *
	 * @throws RuntimeDataException the runtime data exception
	 */
	protected void parseLeftRightBoundryData(int index) throws RuntimeDataException {
		
		String str = getPage().getPageHtmlSource();
		String[] values = StringUtils.substringsBetween(str, getPageHtmlLeftBoundry(), getPageHtmlRightBoundry());
		if (null == values ) {
			throw new RuntimeDataException(ExceptionMessage.MSG_RUNTIMEDATA_NOTFOUND, ExceptionErrorCode.RUNTIMEDATA_NOTFOUND);
		}
		if (values.length < index+1 || -1 == index) {
			index = values.length -1; 
		}
		String setVal = values[index];
		if (null == setVal) {
			throw GlobalUtils.createInternalError("parseLeftRightBoundryData");
		} else {
			setStrDataValue( setVal );
		}
		
	}

	/**
	 * @return the springBeanID
	 */
	public String getSpringBeanID() {
		return springBeanID;
	}

	
}
