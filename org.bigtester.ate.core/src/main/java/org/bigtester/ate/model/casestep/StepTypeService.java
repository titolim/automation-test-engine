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

import org.bigtester.ate.annotation.StepLoggable;
import org.bigtester.ate.constant.StepResultStatus;
import org.bigtester.ate.model.AbstractATEException;
import org.bigtester.ate.model.data.exception.RuntimeDataException;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.page.exception.PageValidationException;
import org.bigtester.ate.model.page.exception.StepExecutionException;
import org.bigtester.ate.model.utils.ThinkTime;
import org.bigtester.ate.systemlogger.IATEProblemCreator;
import org.bigtester.ate.systemlogger.problems.IATEProblem;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.aop.support.AopUtils;

// TODO: Auto-generated Javadoc
/**
 * This class RepeatStep defines ....
 * 
 * @author Peidong Hu
 *
 */
public class StepTypeService extends BaseTestStep implements ITestStep, IStepJumpingEnclosedContainer, ICucumberTestStep {
	
	
	/** The step i ds. */
	private List<ITestStep> stepSet = new ArrayList<ITestStep>();

	
	/**
	 * {@inheritDoc}
	 */
	@StepLoggable(level = org.bigtester.ate.annotation.ATELogLevel.INFO)
	@Override
	public void doStep(@Nullable IStepJumpingEnclosedContainer jumpingContainer) throws StepExecutionException,
			PageValidationException, RuntimeDataException {
		
		runSteps();

	}
	
	/**
	 * run steps.
	 * 
	 * @throws RuntimeDataException
	 * @throws StepExecutionException
	 * @throws PageValidationException
	 */
	private void runSteps() throws StepExecutionException,
			PageValidationException, RuntimeDataException {
		
		for (int i = 0; i < getStepSet().size(); i++) {
			ITestStep currentTestStepTmp =  getStepSet().get(i);

			if (null == currentTestStepTmp) {
				throw new IllegalStateException(
						"Test Step List was not successfully initialized by ApplicationContext at list index"
								+ i);
			} else {
				getTestCase().setCurrentTestStep(currentTestStepTmp);
			}
			try {
				currentTestStepTmp.doStep(this);// NOPMD
				currentTestStepTmp.setStepResultStatus(StepResultStatus.PASS);
			} catch (Exception e) { //NOPMD
				//StepTypeService is considered as atomic step. We don't do step jump to step out of the steptypeservice
				//jump only happens in side the steps of stepTypeService (similar with test case step jump
				
				getTestCase().setCurrentTestStep(currentTestStepTmp);
				
				if (currentTestStepTmp.isCorrectedOnTheFly()) 
					currentTestStepTmp.setStepResultStatus(StepResultStatus.PASS);
				IATEProblem prob;
				if (e instanceof IATEProblemCreator) {//NOPMD
					prob = ((IATEProblemCreator) e).getAteProblem();
					ITestStep exceptionRaisingStep = ((AbstractATEException) e).getOriginalStepRaisingException();
					if (prob == null) {
						if (null == exceptionRaisingStep)
							prob = ((IATEProblemCreator) e)
								.initAteProblemInstance(currentTestStepTmp);
						else
							prob = ((IATEProblemCreator) e)
							.initAteProblemInstance(exceptionRaisingStep);
					} 
					boolean optionalStepRaisingException = false;//NOPMD
					if (exceptionRaisingStep != null)
						optionalStepRaisingException = exceptionRaisingStep.isOptionalStep(); //NOPMD
					if (!prob.isFatalProblem() && prob.getStepIndexSkipTo() > -1) { // NOPMD
						i = prob.getStepIndexSkipTo(); // NOPMD
						if (AopUtils.getTargetClass(currentTestStepTmp) == RepeatStep.class)
							currentTestStepTmp
									.setStepResultStatus(StepResultStatus.NEUTRAL);
						else
							currentTestStepTmp
									.setStepResultStatus(StepResultStatus.SKIP);
					} else if (!prob.isFatalProblem() && optionalStepRaisingException) {
						int correlatedOptionalStepsUtilInclusiveIndex = -1;//NOPMD
						if (exceptionRaisingStep != null)
							correlatedOptionalStepsUtilInclusiveIndex = exceptionRaisingStep.getCorrelatedOptionalStepsUtilInclusiveIndex(this); //NOPMD
						if (AopUtils.getTargetClass(currentTestStepTmp) == RepeatStep.class)
							currentTestStepTmp
									.setStepResultStatus(StepResultStatus.NEUTRAL);
						else
							currentTestStepTmp
									.setStepResultStatus(StepResultStatus.SKIP);
						if (correlatedOptionalStepsUtilInclusiveIndex > i) {
							i = correlatedOptionalStepsUtilInclusiveIndex;// NOPMD
						}
					} else {
						throw e;
					}
				} else {
					throw e;
				}
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
	public ITestCase getServingCase() {
		return getTestCase();
	}

	/**
	 * @param stepSet the stepSet to set
	 */
	public void setStepSet(List<ITestStep> stepSet) {
		this.stepSet = stepSet;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ITestStep> getContainerStepList() {
		return getStepSet();
	}

	
	

}
