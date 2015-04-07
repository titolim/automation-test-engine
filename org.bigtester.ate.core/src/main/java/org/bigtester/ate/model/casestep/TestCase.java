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
package org.bigtester.ate.model.casestep;

import java.util.List;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.constant.ExceptionErrorCode;
import org.bigtester.ate.constant.StepResultStatus;
import org.bigtester.ate.model.data.exception.RuntimeDataException;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.page.exception.PageValidationException2;
import org.bigtester.ate.model.page.exception.StepExecutionException2;
import org.bigtester.ate.model.utils.ThinkTime;
import org.codehaus.plexus.util.StringUtils;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.aop.support.AopUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class TestCase defines ....
 * 
 * @author Peidong Hu
 */
public class TestCase {

	/** The current web driver. */
	@Nullable
	private IMyWebDriver currentWebDriver;
	/** The test case name. */
	private String testCaseName;

	/** The step think time. */
	private int stepThinkTime;

	/** The current test step. */
	@Nullable
	private ITestStep currentTestStep;

	/** The test step list. */
	@Nullable
	private List<ITestStep> testStepList;

	/**
	 * Instantiates a new test case.
	 *
	 * @param testCaseName
	 *            the test case name
	 */
	public TestCase(String testCaseName) {
		this.testCaseName = testCaseName;

	}

	/**
	 * @return the stepThinkTime
	 */
	public int getStepThinkTime() {
		return stepThinkTime;
	}

	/**
	 * @param stepThinkTime
	 *            the stepThinkTime to set
	 */
	public void setStepThinkTime(int stepThinkTime) {
		this.stepThinkTime = stepThinkTime;
	}

	/**
	 * Clean up asserters.
	 */
	protected void cleanUpAsserters() {
		for (int i = 0; i < getTestStepList().size(); i++) {
			if (!getTestStepList().get(i).getExpectedResultAsserter().isEmpty()) {
				getTestStepList()
						.get(i)
						.getExpectedResultAsserter()
						.removeAll(
								getTestStepList().get(i)
										.getExpectedResultAsserter());
			}
		}
	}

	/**
	 * Gets the test step list.
	 * 
	 * @return the testStepList
	 */
	public List<ITestStep> getTestStepList() throws IllegalStateException {
		final List<ITestStep> testStepList2 = testStepList;
		if (null == testStepList2) {
			throw new IllegalStateException(
					"Test Step List was not successfully initialized by ApplicationContext");
		} else {
			return testStepList2;
		}
	}

	/**
	 * Sets the test step list.
	 * 
	 * @param testStepList
	 *            the testStepList to set
	 */
	public final void setTestStepList(final List<ITestStep> testStepList) {
		this.testStepList = testStepList;
	}

	/**
	 * Optional step population.
	 * return the endindex;
	 */
	private int optionalStepPopulation(@Nullable ITestStep currentStep) {
		if (null == currentStep)
			throw GlobalUtils.createNotInitializedException("currentStep");
		int retVal = -1;
		if (!StringUtils.isEmpty(currentStep.getCorrelatedOptionalStepsUtilInclusive())) {
			currentStep.setOptionalStep(true);
			int startIndex = -1;
			int endIndex = -1;
			for (int index = 0; index < getTestStepList().size(); index++) {
				if (getTestStepList().get(index).getStepName() == currentStep
						.getStepName()) {
					startIndex = index;
				}
				if (getTestStepList().get(index).getStepName() == currentStep
						.getCorrelatedOptionalStepsUtilInclusive()) {
					endIndex = index;
					break;
				}
			}
			if (startIndex == -1 || endIndex == -1 || endIndex < startIndex)
				throw GlobalUtils
						.createInternalError("Optional Step util inclusive");
			for (int index2 = startIndex; index2 <= endIndex; index2++) {
				getTestStepList().get(index2).setOptionalStep(true);
			}
			retVal = endIndex;
		}
		return retVal;
		
	}

	/**
	 * run steps.
	 * 
	 * @throws RuntimeDataException
	 * @throws StepExecutionException
	 * @throws PageValidationException
	 */
	public void goSteps() throws StepExecutionException2,
			PageValidationException2, IllegalStateException,
			RuntimeDataException {
		int correlatedOptionlStepsEndIndex = -1;
		for (int i = 0; i < getTestStepList().size(); i++) {

			ITestStep currentTestStepTmp = getTestStepList().get(i);
			
			if (null == currentTestStepTmp) {
				throw new IllegalStateException(
						"Test Step List was not successfully initialized by ApplicationContext at list index"
								+ i);
			} else {
				setCurrentTestStep(currentTestStepTmp);
			}
			if (correlatedOptionlStepsEndIndex == -1)
				correlatedOptionlStepsEndIndex = optionalStepPopulation(currentTestStepTmp);
			// setCurrentWebDriver(getCurrentTestStep().getMyWebDriver());
			try {
				getCurrentTestStep().doStep();// NOPMD
				getCurrentTestStep().setStepResultStatus(StepResultStatus.PASS);
				if (i == correlatedOptionlStepsEndIndex) correlatedOptionlStepsEndIndex = -1;
			} catch (Exception e) { //NOPMD
				if (getCurrentTestStep().isOptionalStep()) {
					getCurrentTestStep().setStepResultStatus(
							StepResultStatus.SKIP);
					if (correlatedOptionlStepsEndIndex >= i) {
						i = correlatedOptionlStepsEndIndex;
						correlatedOptionlStepsEndIndex = -1;
					}
				} else {
					throw GlobalUtils.createInternalError("Error not handled", e);
				}
			}
			// } catch (StepExecutionException2 stepE) {
			//
			// if (stepE.getErrorCode() ==
			// ExceptionErrorCode.WEBELEMENT_NOTFOUND
			// && getCurrentTestStep().isOptionalStep()) {
			// getCurrentTestStep().setStepResultStatus(
			// StepResultStatus.SKIP);
			// } else if (AopUtils.getTargetClass(getCurrentTestStep()) ==
			// RepeatStep.class && getCurrentTestStep().isOptionalStep()) {
			// getCurrentTestStep().setStepResultStatus(
			// StepResultStatus.SKIP);
			// } else {
			// throw stepE;
			// }
			// }
			if (stepThinkTime > 0) {
				ThinkTime thinkTimer = new ThinkTime(stepThinkTime);
				thinkTimer.setTimer();
			}

		}
	}

	/**
	 * Gets the test case name.
	 *
	 * @return the testCaseName
	 */
	public String getTestCaseName() {
		return testCaseName;
	}

	/**
	 * Sets the test case name.
	 *
	 * @param testCaseName
	 *            the testCaseName to set
	 */
	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}

	/**
	 * Gets the current test step.
	 *
	 * @return the currentTestStep
	 */
	public ITestStep getCurrentTestStep() throws IllegalStateException {
		final ITestStep currentTestStep2 = currentTestStep;
		if (null == currentTestStep2) {
			throw new IllegalStateException(
					"Current Test Step was not successfully initialized by ate");
		} else {
			return currentTestStep2;
		}
	}

	/**
	 * Sets the current test step.
	 *
	 * @param currentTestStep
	 *            the currentTestStep to set
	 */
	public void setCurrentTestStep(ITestStep currentTestStep) {
		this.currentTestStep = currentTestStep;
	}

	/**
	 * Gets the current web driver.
	 *
	 * @return the currentWebDriver
	 */
	public IMyWebDriver getCurrentWebDriver() throws IllegalStateException {
		final IMyWebDriver currentWebDriver2 = currentWebDriver;
		if (null == currentWebDriver2) {
			throw new IllegalStateException(
					"Current web driver was not successfully initialized by ate");
		} else {
			return currentWebDriver2;
		}
	}

	/**
	 * Sets the current web driver.
	 *
	 * @param currentWebDriver
	 *            the currentWebDriver to set
	 */
	public void setCurrentWebDriver(IMyWebDriver currentWebDriver) {
		this.currentWebDriver = currentWebDriver;
	}

}
