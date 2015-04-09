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
 * @author Peidong Hu
 *
 */
public class AutoIncrementalDataHolder extends AbstractOnTheFlyDataHolder<Integer> implements ApplicationListener<RepeatDataRefreshEvent>{
	final private int startValue;
	final private int pacing;
	private int endValue = Integer.MAX_VALUE;
	public AutoIncrementalDataHolder(int startValue, int pacing) {
		this.startValue = startValue;
		this.pacing = pacing;
		Integer tmp = Integer.valueOf(startValue);
		if (null == tmp) throw GlobalUtils.createInternalError("java vm integer conversion");
		else
			setOnTheFlyData(tmp);
	}
	
	public AutoIncrementalDataHolder(int startValue, int pacing, int endValue) {
		this.startValue = startValue;
		this.pacing = pacing;
		Integer tmp = Integer.valueOf(startValue);
		if (null == tmp) throw GlobalUtils.createInternalError("java vm integer conversion");
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
			return;//NOPMD
		if (!((RepeatStep) arg0.getSource()).getOnTheFlyDataHolders().contains(this)) return;
				
		if (arg0.getIteration() == 0) {
			Integer tmp = Integer.valueOf(startValue);
			if (null == tmp) throw GlobalUtils.createInternalError("java vm integer conversion");
			else setOnTheFlyData(tmp);
		} else {
			Integer valueTmp = getOnTheFlyData();
			setOnTheFlyData(valueTmp + pacing);
		}
		
	}
}
