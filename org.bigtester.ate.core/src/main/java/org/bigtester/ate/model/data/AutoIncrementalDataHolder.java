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

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.model.casestep.RepeatDataRefreshEvent;
import org.bigtester.ate.model.casestep.RepeatStep;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.context.ApplicationListener;

// TODO: Auto-generated Javadoc
/**
 * This class AutoIncrementalDataHolder defines ....
 * 
 * @author Peidong Hu
 *
 */
public class AutoIncrementalDataHolder extends
		AbstractOnTheFlyDataHolder<Integer> implements IRepeatIncrementalIndex,
		ApplicationListener<RepeatDataRefreshEvent> {
	
	/** The start value. */
	final private int startValue;
	
	/** The pacing. */
	final private int pacing;
	
	/** The end value. */
	private int endValue = Integer.MAX_VALUE;

	/**
	 * Instantiates a new auto incremental data holder.
	 *
	 * @param startValue the start value
	 * @param pacing the pacing
	 */
	public AutoIncrementalDataHolder(int startValue, int pacing) {
		super();
		this.startValue = startValue;
		this.pacing = pacing;
		Integer tmp = Integer.valueOf(startValue);
		if (null == tmp)
			throw GlobalUtils.createInternalError("java vm integer conversion");
		else
			setOnTheFlyData(tmp);
	}

	/**
	 * Instantiates a new auto incremental data holder.
	 *
	 * @param startValue the start value
	 * @param pacing the pacing
	 * @param endValue the end value
	 */
	public AutoIncrementalDataHolder(int startValue, int pacing, int endValue) {
		super();
		this.startValue = startValue;
		this.pacing = pacing;
		Integer tmp = Integer.valueOf(startValue);
		if (null == tmp)
			throw GlobalUtils.createInternalError("java vm integer conversion");
		else
			setOnTheFlyData(tmp);
		this.endValue = endValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onApplicationEvent(@Nullable RepeatDataRefreshEvent arg0) {

		if (arg0 == null)
			return;// NOPMD
		RepeatStep currentRepeatStep = ((RepeatStep) arg0.getSource());
		if (!(currentRepeatStep).getRepeatIndexValuesNeedRefresh().contains(this))
			return;

		Integer tmp = Integer
				.valueOf(startValue + arg0.getIteration() * pacing);
		if (null == tmp)
			throw GlobalUtils.createInternalError("java vm integer conversion");
		else
			setOnTheFlyData(tmp);

	}

	/**
	 * @return the endValue
	 */
	public int getEndValue() {
		return endValue;
	}

	/**
	 * @param endValue the endValue to set
	 */
	public void setEndValue(int endValue) {
		this.endValue = endValue;
	}

	/**
	 * @return the startValue
	 */
	public int getStartValue() {
		return startValue;
	}

	/**
	 * @return the pacing
	 */
	public int getPacing() {
		return pacing;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getIndex() {
		return getOnTheFlyData();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resetIndex() {
		setOnTheFlyData(0);
		
	}
	
	
}
