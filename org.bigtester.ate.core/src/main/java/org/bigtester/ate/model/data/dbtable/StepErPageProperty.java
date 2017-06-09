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
package org.bigtester.ate.model.data.dbtable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.eclipse.jdt.annotation.Nullable;


// TODO: Auto-generated Javadoc
/**
 * This class StepResult defines ....
 * 
 * @author Peidong Hu
 *
 */
@Entity
@Table
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="firstTimeExecution",discriminatorType=DiscriminatorType.STRING)  
@DiscriminatorValue(value="Yes")  
public class StepErPageProperty extends BaseStepExpectedResult {

	
	@Column
	/** The Assert property. */
	@Nullable
	private String AssertProperty; // NOPMD

	@Column
	/** The Assert value. */
	@Nullable
	private String AssertValue; // NOPMD

	
	/**
	 * @return the assertProperty
	 */
	public String getAssertProperty() {
		final String retVal = AssertProperty;
		if (null == retVal) {
			throw new IllegalStateException("asertproperty collumn is not correctly populated");
			
		} else {
			return retVal;
		}
	}

	/**
	 * @return the assertValue
	 */
	public String getAssertValue() {
		final String retVal = AssertValue;
		if (null == retVal) {
			throw new IllegalStateException("assertvalue collumn is not correctly populated");
			
		} else {
			return retVal;
		}
	}

	
	/**
	 * @param assertProperty the assertProperty to set
	 */
	public void setAssertProperty(String assertProperty) {
		AssertProperty = assertProperty;
	}

	/**
	 * @param assertValue the assertValue to set
	 */
	public void setAssertValue(String assertValue) {
		AssertValue = assertValue;
	}

	
}
