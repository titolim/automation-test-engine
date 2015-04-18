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

import org.springframework.context.ApplicationEvent;

// TODO: Auto-generated Javadoc
/**
 * This class RepeatDataRefreshEvent defines ....
 * 
 * @author Peidong Hu
 *
 */
public class RepeatStepInOutEvent extends ApplicationEvent {

	/**
	 * The Enum RepeatStepInOut.
	 *
	 * @author Peidong Hu
	 */
	public enum RepeatStepInOut {
		IN, OUT
	}
	
	/** The in out flag. */
	final private RepeatStepInOut inOutFlag;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4904647971676073348L;
	/** The iteration. */
	final private int iteration;

	

	/**
	 * Instantiates a new repeat data refresh event.
	 *
	 * @param source
	 *            the source
	 * @param repeatStepInvokePathNodes
	 *            the repeat step invoke path nodes
	 * @param iteration
	 *            the iteration
	 */
	public RepeatStepInOutEvent(RepeatStep source, RepeatStepInOut inOut,
			int iteration) {
		super(source);
		this.inOutFlag = inOut;
		this.iteration = iteration;
	}

	/**
	 * @param repeatStep
	 * @param i
	 */
	public RepeatStepInOutEvent(Object source, RepeatStepInOut inOut) {
		super(source);
		this.inOutFlag = inOut;
		this.iteration = 0;
		
	}

	/**
	 * Gets the repeat step name.
	 *
	 * @return the repeat step name
	 */
	public String getRepeatStepName() {
		return ((RepeatStep) getSource()).getStepName();
	}
	
	

	/**
	 * @return the iteration
	 */
	public int getIteration() {
		return iteration;
	}

	/**
	 * @return the inOutFlag
	 */
	public RepeatStepInOut getInOutFlag() {
		return inOutFlag;
	}


}
