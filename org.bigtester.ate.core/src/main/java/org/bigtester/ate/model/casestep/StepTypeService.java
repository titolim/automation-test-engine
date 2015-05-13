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
package org.bigtester.ate.model.casestep;//NOPMD

import java.util.ArrayList;
import java.util.List;

import org.bigtester.ate.constant.StepResultStatus;
import org.bigtester.ate.model.data.exception.RuntimeDataException;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.page.exception.PageValidationException2;
import org.bigtester.ate.model.page.exception.StepExecutionException2;
import org.bigtester.ate.model.utils.ThinkTime;
import org.eclipse.jdt.annotation.Nullable;

// TODO: Auto-generated Javadoc
/**
 * This class RepeatStep defines ....
 * 
 * @author Peidong Hu
 *
 */
public class StepTypeService extends BaseTestStep implements ITestStep {
	
	/** The step i ds. */
	private List<ITestStep> stepSet = new ArrayList<ITestStep>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doStep() throws StepExecutionException2,
			PageValidationException2, RuntimeDataException {

		runSteps();

	}
	
	/**
	 * run steps.
	 * 
	 * @throws RuntimeDataException
	 * @throws StepExecutionException
	 * @throws PageValidationException
	 */
	private void runSteps() throws StepExecutionException2,
			PageValidationException2, RuntimeDataException {
		
		for (int i = 0; i < getStepSet().size(); i++) {

			try {
				
				getStepSet().get(i).doStep();// NOPMD
				getStepSet().get(i).setStepResultStatus(StepResultStatus.PASS);
			} catch (Throwable e) { //NOPMD
				//StepTypeService is considered as atomic step. We don't do step jump inside of the steptypeservice
				//if error appears, directly, we exit the service and throw error.
				if (!getStepSet().get(i).isCorrectedOnTheFly()) 
					getStepSet().get(i).setStepResultStatus(StepResultStatus.FAIL);
				throw e;
			}
			if (getServingCase().getStepThinkTime() > 0) {
				ThinkTime thinkTimer = new ThinkTime(getServingCase().getStepThinkTime());
				thinkTimer.setTimer();
			}
		}
	}


	/**
	 * @return the stepSet
	 */
	public List<ITestStep> getStepSet() {
		return stepSet;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Nullable
	public IMyWebDriver getMyWebDriver() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the servingCase
	 */
	public TestCase getServingCase() {
		return getTestCase();
	}

	/**
	 * @param stepSet the stepSet to set
	 */
	public void setStepSet(List<ITestStep> stepSet) {
		this.stepSet = stepSet;
	}

	

}
