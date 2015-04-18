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
package org.bigtester.ate.model.casestep;

import javax.swing.tree.DefaultMutableTreeNode;

import com.thoughtworks.xstream.annotations.XStreamOmitField;


// TODO: Auto-generated Javadoc
/**
 * This class RepeatStepExecutionLoggerNode defines ....
 * @author Peidong Hu
 *
 */
public class RepeatStepExecutionLoggerNode extends DefaultMutableTreeNode {
	
	/** The repeat step with initial values. */
	@XStreamOmitField
	final private RepeatStep repeatStepWithInitialValues;
	
	/** The live repeat step. */
	@XStreamOmitField
	final private RepeatStep liveRepeatStep;
	/**
	 * 
	 */
	private static final long serialVersionUID = -7048151244571042316L;

	/**
	 * Instantiates a new repeat step execution logger node.
	 *
	 * @param repeatStepName the repeat step name
	 */
	public RepeatStepExecutionLoggerNode(String repeatStepName, RepeatStep repeatStepWithInitialValues, RepeatStep liveRepeatStep) {
		super(repeatStepName);
		this.repeatStepWithInitialValues = repeatStepWithInitialValues;
		this.liveRepeatStep = liveRepeatStep;
	}

	/**
	 * @return the repeatStep
	 */
	public RepeatStep getRepeatStepWithInitialValues() {
		return repeatStepWithInitialValues;
	}

	/**
	 * @return the liveRepeatStep
	 */
	public RepeatStep getLiveRepeatStep() {
		return liveRepeatStep;
	}

}
