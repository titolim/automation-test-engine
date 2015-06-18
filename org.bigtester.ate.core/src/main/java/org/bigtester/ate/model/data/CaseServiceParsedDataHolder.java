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

// TODO: Auto-generated Javadoc
/**
 * This class CaseServiceParsedDataHolder defines ....
 * @author Peidong Hu
 *
 */
public class CaseServiceParsedDataHolder extends AbstractRunTimeInputDataHolder  implements IStepInputData, ICaseServiceParsedDataParser{
	
	/** The sub case mapped data holder id. */
	final private String subCaseMappedDataHolderID;
	/**
	 * @param dataType
	 * @param strDataValue
	 * @param springBeanID
	 */
	public CaseServiceParsedDataHolder(EnumRunTimeDataType dataType,
			 String springBeanID, String subCaseMappedDataHolderID) {
		super(dataType, springBeanID);
		this.subCaseMappedDataHolderID = subCaseMappedDataHolderID;
	}

	/**
	 * {@inheritDoc}
	 * Case service data will be transferred from sub test case. No page parsing work to do.
	 */
	@Override
	public void parseData() throws RuntimeDataException {//NOPMD
		
		
	}

	/**
	 * @return the subCaseMappedDataHolderID
	 */
	public String getSubCaseMappedDataHolderID() {
		return subCaseMappedDataHolderID;
	}

	
}
