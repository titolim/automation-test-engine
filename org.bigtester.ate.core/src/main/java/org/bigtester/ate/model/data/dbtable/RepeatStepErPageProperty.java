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
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.bigtester.ate.GlobalUtils;
import org.eclipse.jdt.annotation.Nullable;



// TODO: Auto-generated Javadoc
/**
 * This class StepResult defines ....
 * 
 * @author Peidong Hu
 *
 */
@Entity
@DiscriminatorValue(value="No")  
public class RepeatStepErPageProperty extends StepErPageProperty {


	/** The repeat step bean id. */
	@Column
	@Nullable
	private String repeatStepName;
	
	@Column
	private int iteration; // NOPMD
	/**
	 * @return the repeatStepBeanId
	 */
	public String getRepeatStepName() {
		final String repeatStepBeanId2 = repeatStepName;
		if (null == repeatStepBeanId2) {
			throw GlobalUtils.createNotInitializedException("repeat step bean id");
			
		} else {
			return repeatStepBeanId2;
		}
	}

	/**
	 * @return the iterationIndex
	 */
	public int getIteration() {
		return iteration;
	}

	/**
	 * @param iteration the iterationIndex to set
	 */
	public void setIteration(int iteration) {
		this.iteration = iteration;
	}
	/**
	 * @param repeatStepName the repeatStepBeanId to set
	 */
	public void setRepeatStepName(String repeatStepName) {
		this.repeatStepName = repeatStepName;
	}
	
}
