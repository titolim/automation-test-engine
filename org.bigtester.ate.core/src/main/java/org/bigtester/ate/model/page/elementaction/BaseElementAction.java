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
package org.bigtester.ate.model.page.elementaction;


import org.bigtester.ate.model.data.IStepInputData;
import org.bigtester.ate.model.page.PageModelBase;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.eclipse.jdt.annotation.Nullable;


// TODO: Auto-generated Javadoc
/**
 * The Class _ElementAction defines ....
 * 
 * @author Peidong Hu
 */
public class BaseElementAction extends PageModelBase{
	/** The data valued action flag. */
	private transient boolean dataValuedActionFlag; 
	
	/** The data value. */
	@Nullable
	private IStepInputData dataValue;
	
	
	/**
	 * @param myWd
	 */
	public BaseElementAction(IMyWebDriver myWd) {
		super(myWd);
		// TODO Auto-generated constructor stub
	}
	public String getActionParametersLoggingValue() {
		String retVal;
		
		final IStepInputData dataValue2 = dataValue;
		if (dataValue2 == null) {
			retVal = "action with no parameter";
		} else {
			retVal = "dataValue = " + dataValue2.getStrDataValue();
		}
		return retVal;
	}
	
	/**
	 * Gets the data value.
	 * 
	 * @return the data value
	 */
	@Nullable
	public IStepInputData getDataValue() {
		return dataValue;
	}

	/**
	 * Sets the data value.
	 * 
	 * @param dataValue
	 *            the new data value
	 */
	public void setDataValue(final IStepInputData dataValue) {
		this.dataValuedActionFlag = true;
		this.dataValue = dataValue;
	}

	
	
	/**
	 * Checks if is data value action flag.
	 *
	 * @return the dataValueFlag
	 */
	public boolean isDataValuedActionFlag() {
		return dataValuedActionFlag;
	}

	/**
	 * {@inheritDoc}
	 */
	public @Nullable <T> T getCapability(Class<T> type) {
		if (this instanceof IElementAction) {
			return (T) this; //NOPMD
		} else {
			return null;
		}
	}
	
}
