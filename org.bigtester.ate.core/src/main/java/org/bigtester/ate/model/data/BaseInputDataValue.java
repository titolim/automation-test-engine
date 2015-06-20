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
package org.bigtester.ate.model.data;

import org.bigtester.ate.annotation.RepeatStepRefreshable;
import org.bigtester.ate.annotation.RepeatStepRefreshable.RefreshDataType;
import org.eclipse.jdt.annotation.Nullable;

// TODO: Auto-generated Javadoc
/**
 * This class AbstractDataValue defines ....
 * @author Peidong Hu
 *
 */
public class BaseInputDataValue {//NOPMD
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	@Nullable
	private String strDataValue; // NOPMD

	/**
	 * Instantiates a new base input data value.
	 *
	 * @param strDataValue the str data value
	 */
	public BaseInputDataValue(String strDataValue) {
		this.strDataValue = strDataValue;
	}
	
	/**
	 * @return the value
	 */
	@RepeatStepRefreshable (dataType=RefreshDataType.PAGEINPUTDATA)
	public String getStrDataValue() {
		final String value2 = strDataValue;
		if (null == value2) {
			throw new IllegalStateException(
					"Input Data value is not correctly populated.");
		} else {
			return value2;
		}
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setStrDataValue(String value) {
		this.strDataValue = value;
	}
	
	
}	
