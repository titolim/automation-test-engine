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

import java.util.ArrayList;
import java.util.List;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.constant.StepResultStatus;
import org.bigtester.ate.model.asserter.IExpectedResultAsserter;
import org.bigtester.ate.model.data.IDataParser;
import org.bigtester.ate.model.data.exception.RuntimeDataException;
import org.bigtester.ate.model.page.page.IPageObject;
import org.codehaus.plexus.util.StringUtils;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

// TODO: Auto-generated Javadoc
/**
 * This class AbstractTestStep defines ....
 * 
 * @author Peidong Hu
 * 
 */
abstract public class BaseTestStep implements ApplicationContextAware {// NOPMD

	/** The test case. */
	@Nullable
	private ITestCase testCase;

//	@Nullable
//	private IStepJumpingEnclosedContainer stepJumpingEnclosedContainer;
	
	/** The current iteration. */
	private int currentIteration;

	/** The current repeat step name. */
	private String currentRepeatStepName = "";

	/** The application context. */
	@Nullable
	@XStreamOmitField
	private ApplicationContext applicationContext;

	/** The step logger. */
	@Nullable
	@XStreamOmitField
	private IRepeatStepExecutionLogger repeatStepLogger;

	/** The data holders. */
	@XStreamOmitField
	private List<IDataParser> dataHolders = new ArrayList<IDataParser>();

	// /** The on the fly data holders. */
	// private List<IOnTheFlyData<?>> onTheFlyDataHolders = new
	// ArrayList<IOnTheFlyData<?>>();
	//
	/** The element step flag. */

	private transient boolean elementStepFlag;

	/** The i expected result asserter. */
	@XStreamOmitField
	private List<IExpectedResultAsserter> expectedResultAsserter = new ArrayList<IExpectedResultAsserter>();

	/** The forced page validation. */
	private boolean forcedPageValidation;

	
	/** The optional step. default is false */
	private boolean optionalStep;

	/** The optional step inclusive. */
	private String correlatedOptionalStepsUtilInclusive = "";// NOPMD

	/** The correlated optional steps util inclusive index. */
	private int correlatedOptionalStepsUtilInclusiveIndex = -1;// NOPMD
	/** The page object. */
	@Nullable
	@XStreamOmitField
	private IPageObject pageObject;

	/** The step description. */
	@Nullable
	private String stepDescription = "";

	/** The step name. */
	private String stepName = "";

	/** The passed. */
	private StepResultStatus stepResultStatus = StepResultStatus.FAIL;

	/** The target step. */
	private boolean targetStep;

	/** The corrected on the fly. */
	private boolean correctedOnTheFly;
	
	
	/**
	 * Gets the test case.
	 *
	 * @return the testCase
	 */
	public ITestCase getTestCase() {
		
		if (null == testCase) 
			testCase = GlobalUtils.findTestCaseBean();
		final ITestCase testCase2 = testCase;
		if (testCase2 == null) {
			throw GlobalUtils.createInternalError("test case bean couldn't be found");
			
		} else {
			return testCase2;
		}
	}
//	@StepLoggable(level = ATELogLevel.INFO)
//	abstract public void doStep () throws StepExecutionException, PageValidationException2, RuntimeDataException;

	/**
	 * Gets the application context.
	 *
	 * @return the application context
	 * @throws IllegalStateException
	 *             the illegal state exception
	 */
	public ApplicationContext getApplicationContext()
			throws IllegalStateException {

		final ApplicationContext applicationContext2 = applicationContext;
		if (null == applicationContext2) {
			throw new IllegalStateException(
					"applicationContext is not correctly initialized in test step");
		} else {
			return applicationContext2;
		}
	}

	/**
	 * Gets the correlated optional steps util inclusive index.
	 *
	 * @return the correlatedOptionalStepsUtilInclusiveIndex
	 */

	public int getCorrelatedOptionalStepsUtilInclusiveIndex(IStepJumpingEnclosedContainer stepJumpingEnclosedContainer) {
		if (-1 == correlatedOptionalStepsUtilInclusiveIndex
				&& !StringUtils
						.isEmpty(getCorrelatedOptionalStepsUtilInclusiveName())) {

			setOptionalStep(true);
			int startIndex = -1;// NOPMD
			int endIndex = -1;// NOPMD
			for (int index = 0; index < stepJumpingEnclosedContainer.getContainerStepList().size(); index++) {
				if (startIndex == -1
						&& stepJumpingEnclosedContainer.getContainerStepList().get(index)
								.getStepName() == getStepName()) {
					startIndex = index;// NOPMD
				}
				if (stepJumpingEnclosedContainer.getContainerStepList().get(index).getStepName() == getCorrelatedOptionalStepsUtilInclusiveName()) {
					endIndex = index;
					break;
				}
			}
			if (startIndex == -1 || endIndex == -1 || endIndex < startIndex)
				throw GlobalUtils
						.createInternalError("parameter optional Step util inclusive not correctly populated");
			for (int index2 = startIndex; index2 <= endIndex; index2++) {
				stepJumpingEnclosedContainer.getContainerStepList().get(index2)
						.setOptionalStep(true);
			}
			correlatedOptionalStepsUtilInclusiveIndex = endIndex;

		}

		return correlatedOptionalStepsUtilInclusiveIndex;

	}

	/**
	 * Gets the data holders.
	 *
	 * @return the dataHolders
	 */
	public List<IDataParser> getDataHolders() {
		return dataHolders;

	}

	/**
	 * Gets the expected result asserter.
	 *
	 * @return the iExpectedResultAsserter
	 */
	public List<IExpectedResultAsserter> getExpectedResultAsserter() {
		return expectedResultAsserter;
	}

	

	/**
	 * Gets the page object.
	 * 
	 * @return the pageObject
	 */
	@Nullable
	public IPageObject getPageObject() {
		return pageObject;
	}

	/**
	 * Gets the step description.
	 * 
	 * @return the stepDescription
	 */
	@Nullable
	public String getStepDescription() {
		return stepDescription;
	}

	/**
	 * Gets the step name.
	 * 
	 * @return the stepName
	 */
	public String getStepName() {
		return stepName;
	}

	/**
	 * Gets the step result status.
	 *
	 * @return the stepResultStatus
	 */
	public StepResultStatus getStepResultStatus() {
		return stepResultStatus;
	}

	/**
	 * Checks if is element step flag.
	 *
	 * @return the elementStepFlag
	 */
	public boolean isElementStepFlag() {
		return elementStepFlag;
	}

	/**
	 * Checks if is forced page validation.
	 *
	 * @return the forcedPageValidation
	 */
	public boolean isForcedPageValidation() {
		return forcedPageValidation;
	}

	/**
	 * Checks if is optional step.
	 *
	 * @return the optionalStep
	 */
	public boolean isOptionalStep() {
		return optionalStep;
	}

	/**
	 * Checks if is page validation.
	 *
	 * @return true, if is page validation
	 */
	public boolean isPageValidation() {

		return forcedPageValidation ? true : targetStep;

	}

	/**
	 * Checks if is target step.
	 * 
	 * @return the targetStep
	 */
	public boolean isTargetStep() {
		return targetStep;
	}

	/**
	 * Parses the data holder.
	 *
	 * @throws RuntimeDataException
	 *             the runtime data exception
	 */
	protected void parseDataHolder() throws RuntimeDataException {
		for (int i = 0; i < getDataHolders().size(); i++) {
			getDataHolders().get(i).parseData();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setApplicationContext(
			@Nullable ApplicationContext applicationContext)
			throws BeansException {
		if (null == applicationContext) {
			throw new NoSuchBeanDefinitionException(ApplicationContext.class);
		} else {
			this.applicationContext = applicationContext;
		}

	}

	/**
	 * Sets the data holders.
	 *
	 * @param dataHolders
	 *            the dataHolders to set
	 */
	public void setDataHolders(List<IDataParser> dataHolders) {
		this.dataHolders = dataHolders;
	}

	/**
	 * Sets the expected result asserter.
	 *
	 * @param iExpectedResultAsserter
	 *            the iExpectedResultAsserter to set
	 */
	public void setExpectedResultAsserter(
			List<IExpectedResultAsserter> iExpectedResultAsserter) {
		this.expectedResultAsserter = iExpectedResultAsserter;
	}

	/**
	 * Sets the forced page validation.
	 *
	 * @param forcedPageValidation
	 *            the forcedPageValidation to set
	 */
	public void setForcedPageValidation(boolean forcedPageValidation) {
		this.forcedPageValidation = forcedPageValidation;
	}

	

	/**
	 * Sets the optional step.
	 *
	 * @param optionalStep
	 *            the optionalStep to set
	 */
	public void setOptionalStep(boolean optionalStep) {
		this.optionalStep = optionalStep;
	}

	/**
	 * Sets the page object.
	 * 
	 * @param pageObject
	 *            the pageObject to set
	 */
	public void setPageObject(IPageObject pageObject) {
		this.pageObject = pageObject;
	}

	/**
	 * Sets the step description.
	 * 
	 * @param stepDescription
	 *            the stepDescription to set
	 */
	public void setStepDescription(String stepDescription) {
		this.stepDescription = stepDescription;
	}

	/**
	 * Sets the step name.
	 * 
	 * @param stepName
	 *            the stepName to set
	 */
	public void setStepName(final String stepName) {
		this.stepName = stepName;
	}

	/**
	 * Sets the step result status.
	 *
	 * @param stepResultStatus
	 *            the stepResultStatus to set
	 */
	public void setStepResultStatus(StepResultStatus stepResultStatus) {
		this.stepResultStatus = stepResultStatus;
	}

	/**
	 * Sets the target step.
	 * 
	 * @param targetStep
	 *            the targetStep to set
	 */
	public void setTargetStep(boolean targetStep) {
		this.targetStep = targetStep;
	}

	/**
	 * Gets the current iteration.
	 *
	 * @return the currentIteration
	 */
	public int getCurrentIteration() {
		return currentIteration;
	}

	/**
	 * Sets the current iteration.
	 *
	 * @param currentIteration
	 *            the currentIteration to set
	 */
	public void setCurrentIteration(int currentIteration) {
		this.currentIteration = currentIteration;
	}

	/**
	 * Gets the current repeat step name.
	 *
	 * @return the currentRepeatStepName
	 */
	public String getCurrentRepeatStepName() {
		return currentRepeatStepName;
	}

	/**
	 * Sets the current repeat step name.
	 *
	 * @param currentRepeatStepName
	 *            the currentRepeatStepName to set
	 */
	public void setCurrentRepeatStepName(String currentRepeatStepName) {
		this.currentRepeatStepName = currentRepeatStepName;
	}

	/**
	 * Sets the element step flag.
	 *
	 * @param elementStepFlag
	 *            the elementStepFlag to set
	 */
	public void setElementStepFlag(boolean elementStepFlag) {
		this.elementStepFlag = elementStepFlag;
	}

	/**
	 * Gets the repeat step logger.
	 *
	 * @return the stepLogger
	 */
	public IRepeatStepExecutionLogger getRepeatStepLogger() {
		final IRepeatStepExecutionLogger repeatStepLogger2 = repeatStepLogger;
		if (repeatStepLogger2 == null) {
			throw GlobalUtils.createNotInitializedException("repeatStepLogger");
		} else {
			return repeatStepLogger2;
		}
	}

	/**
	 * Sets the repeat step logger.
	 *
	 * @param repeatStepLogger
	 *            the repeatStepLogger to set
	 */
	public void setRepeatStepLogger(IRepeatStepExecutionLogger repeatStepLogger) {
		this.repeatStepLogger = repeatStepLogger;
	}

	/**
	 * Sets the correlated optional steps util inclusive.
	 *
	 * @param correlatedOptionalStepsUtilInclusive
	 *            the new correlated optional steps util inclusive
	 */
	public void setCorrelatedOptionalStepsUtilInclusive(
			String correlatedOptionalStepsUtilInclusive) {// NOPMD
		this.correlatedOptionalStepsUtilInclusive = correlatedOptionalStepsUtilInclusive;
	}

	/**
	 * Gets the correlated optional steps util inclusive.
	 *
	 * @return the optionalStepUtilInclusive
	 */
	public String getCorrelatedOptionalStepsUtilInclusiveName() {
		return correlatedOptionalStepsUtilInclusive;
	}

	/**
	 * Sets the correlated optional steps util inclusive index.
	 *
	 * @param correlatedOptionalStepsUtilInclusiveIndex            the correlatedOptionalStepsUtilInclusiveIndex to set
	 */
	public void setCorrelatedOptionalStepsUtilInclusiveIndex(
			int correlatedOptionalStepsUtilInclusiveIndex) {//NOPMD
		this.correlatedOptionalStepsUtilInclusiveIndex = correlatedOptionalStepsUtilInclusiveIndex;
	}

	/**
	 * @param testCase the testCase to set
	 */
	public void setTestCase(ITestCase testCase) {
		this.testCase = testCase;
	}

	/**
	 * @return the correlatedOptionalStepsUtilInclusive
	 */
	public String getCorrelatedOptionalStepsUtilInclusive() {
		return correlatedOptionalStepsUtilInclusive;
	}



	/**
	 * @return the correctedOnTheFly
	 */
	public boolean isCorrectedOnTheFly() {
		return correctedOnTheFly;
	}



	/**
	 * @param correctedOnTheFly the correctedOnTheFly to set
	 */
	public void setCorrectedOnTheFly(boolean correctedOnTheFly) {
		this.correctedOnTheFly = correctedOnTheFly;
	}

//	/**
//	 * @return the stepJumpingEnclosedContainer
//	 */
//	@Nullable
//	public IStepJumpingEnclosedContainer getStepJumpingEnclosedContainer() {
//		return stepJumpingEnclosedContainer;
//	}
//
//	/**
//	 * @param stepJumpingEnclosedContainer the stepJumpingEnclosedContainer to set
//	 */
//	public void setStepJumpingEnclosedContainer(
//			IStepJumpingEnclosedContainer stepJumpingEnclosedContainer) {
//		this.stepJumpingEnclosedContainer = stepJumpingEnclosedContainer;
//	}

	
}
