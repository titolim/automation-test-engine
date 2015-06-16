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

import org.bigtester.ate.constant.EnumRunTimeDataType;
import org.bigtester.ate.model.data.exception.RuntimeDataException;
import org.bigtester.ate.model.page.page.IPageObject;

// TODO: Auto-generated Javadoc
/**
 * This class PageParsedDataHolder defines ....
 * @author Peidong Hu
 *
 */
public class PageParsedDataHolder extends AbstractRunTimeInputDataHolder implements IStepInputData, IDataParser{
	
	/** The index of same type of data on page. */
	final private int indexOfSameTypeOfDataOnPage;
	/**
	 * @param dataType
	 * @param pageHtmlLeftBoundry
	 * @param pageHtmlRightBoundry
	 * @param page
	 * @param springBeanID
	 */
	public PageParsedDataHolder(EnumRunTimeDataType dataType,
			String pageHtmlLeftBoundry, String pageHtmlRightBoundry,
			IPageObject page, String springBeanID) {
		super(dataType, pageHtmlLeftBoundry, pageHtmlRightBoundry, page,
				springBeanID);
		this.indexOfSameTypeOfDataOnPage = 0;
	}
	
	/**
	 * Instantiates a new page parsed data holder.
	 *
	 * @param dataType the data type
	 * @param pageHtmlLeftBoundry the page html left boundry
	 * @param pageHtmlRightBoundry the page html right boundry
	 * @param page the page
	 * @param springBeanID the spring bean id
	 * @param indexOfSameTypeOfDataOnPage the index of same type of data on page
	 */
	public PageParsedDataHolder(EnumRunTimeDataType dataType,
			String pageHtmlLeftBoundry, String pageHtmlRightBoundry,
			IPageObject page, String springBeanID, int indexOfSameTypeOfDataOnPage) {
		super(dataType, pageHtmlLeftBoundry, pageHtmlRightBoundry, page,
				springBeanID);
		this.indexOfSameTypeOfDataOnPage = indexOfSameTypeOfDataOnPage;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void parseData() throws RuntimeDataException {
		
			parseLeftRightBoundryData(indexOfSameTypeOfDataOnPage);
		
	}

	/**
	 * @return the indexOfSameTypeOfDataOnPage
	 */
	public int getIndexOfSameTypeOfDataOnPage() {
		return indexOfSameTypeOfDataOnPage;
	}

	

}
