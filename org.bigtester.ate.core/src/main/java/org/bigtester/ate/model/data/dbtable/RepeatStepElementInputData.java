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
package org.bigtester.ate.model.data.dbtable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.bigtester.ate.GlobalUtils;
import org.eclipse.jdt.annotation.Nullable;

// TODO: Auto-generated Javadoc
/**
 * This class RepeatStepElementInputData defines ....
 * @author Peidong Hu
 *
 */
@Entity

@DiscriminatorValue(value="No") 
public class RepeatStepElementInputData extends ElementInputData implements Cloneable{

	@Column
	private int iteration; // NOPMD
	
	/** The step er set id. */
	@Column
	@Nullable
	private String repeatStepName;

	/** The repeat step loop path. */
	@Column
	@Nullable
	private String repeatStepExternalLoopPath;
	
	/**
	 * @return the iterationIndex
	 */
	public int getIteration() {
		return iteration;
	}

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
	 * @param iterationIndex the iterationIndex to set
	 */
	public void setIteration(int iterationIndex) {
		this.iteration = iterationIndex;
	}

	/**
	 * @param repeatStepName the repeatStepBeanId to set
	 */
	public void setRepeatStepName(String repeatStepName) {
		this.repeatStepName = repeatStepName;
	}

	/**
	 * @return the repeatStepExternalLoopPath
	 */
	public String getRepeatStepExternalLoopPath() {
		final String repeatStepExternalLoopPath2 = repeatStepExternalLoopPath;
		if (repeatStepExternalLoopPath2 == null) {
			throw GlobalUtils.createNotInitializedException("repeatStepExternalLoopPath");
		} else {
			return repeatStepExternalLoopPath2;
		}
	}

	/**
	 * @param repeatStepExternalLoopPath the repeatStepExternalLoopPath to set
	 */
	public void setRepeatStepExternalLoopPath(String repeatStepExternalLoopPath) {
		this.repeatStepExternalLoopPath = repeatStepExternalLoopPath;
	}
	
    /**
     * {@inheritDoc}
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
