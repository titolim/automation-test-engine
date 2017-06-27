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

import java.util.ArrayList;
import java.util.List;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.constant.ExceptionMessage;
import org.bigtester.ate.constant.StepResultStatus;
import org.bigtester.ate.model.asserter.IExpectedResultAsserter;
import org.bigtester.ate.model.casestep.ICucumberTestStep.CucumberStepType;
import org.bigtester.ate.model.data.ICaseServiceParsedDataParser;
import org.bigtester.ate.model.data.IDataParser; 
import org.bigtester.ate.model.data.IStepInputData;
import org.bigtester.ate.model.data.exception.RuntimeDataException;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.page.exception.PageValidationException;
import org.bigtester.ate.model.page.exception.StepExecutionException;
import org.bigtester.ate.model.page.page.IPageObject; 
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.WebDriver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

// TODO: Auto-generated Javadoc
/**
 * This class CaseTypeService defines ....
 * 
 * @author Peidong Hu
 *
 */
public class CaseTypeService extends TestCase implements ITestStep, ICucumberTestStep { // NOPMD
	/** The cucumber step type. */
	final private CucumberStepType cucumberStepType;
	/** The step result status. */
	private StepResultStatus stepResultStatus = StepResultStatus.FAIL;
	
	/** The test case file name. */
	final private String testCaseFileName;

	/** The parent test case. */
	// TODO current version needs user to manually set the parentTestCase in xml
	// file
	final private ITestCase parentTestCase;

	/** The data holders. */
	@XStreamOmitField
	private List<IDataParser> dataHolders = new ArrayList<IDataParser>();

//	/** The on the fly data holders. */
//	private List<IOnTheFlyData<?>> onTheFlyDataHolders = new ArrayList<IOnTheFlyData<?>>();
//	
	/**
	 * @param testCaseName
	 */
	public CaseTypeService(String testCaseName, String testCaseFileName,
			ITestCase parentTestCase) {
		super(testCaseName);
		this.cucumberStepType = null;//NOPMD
		this.testCaseFileName = testCaseFileName;
		this.parentTestCase = parentTestCase;
	}
	
	/**
	 * Instantiates a new case type service.
	 *
	 * @param cucumberStepType the cucumber step type
	 * @param testCaseName the test case name
	 * @param testCaseFileName the test case file name
	 * @param parentTestCase the parent test case
	 */
	public CaseTypeService(CucumberStepType cucumberStepType, String testCaseName, String testCaseFileName,
			ITestCase parentTestCase) {
		super(testCaseName);
		this.cucumberStepType = cucumberStepType;
		this.testCaseFileName = testCaseFileName;
		this.parentTestCase = parentTestCase;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isTargetStep() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPageValidation() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Nullable
	public IPageObject getPageObject() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMyWebDriver getMyWebDriver() {
		return super.getCurrentWebDriver();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getStepName() {
		return getTestCaseName();
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	@Nullable
	public String getStepDescription() {
		return getTestCaseName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isElementStepFlag() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doStep(@Nullable IStepJumpingEnclosedContainer jumpingContainer) throws StepExecutionException,
			PageValidationException, RuntimeDataException {
		//if (null == jumpingContainer) jumpingContainer = (IStepJumpingEnclosedContainer) this.getParentTestCase();

		String testCaseFileName = getTestCaseFileName();
		WebDriver mainDriver;
		ApplicationContext context;
		context = new FileSystemXmlApplicationContext(testCaseFileName);
		IMyWebDriver myWebD = (IMyWebDriver) GlobalUtils
				.findMyWebDriver(context);
		mainDriver = myWebD.getWebDriverInstance();
		try {
			super.setCurrentWebDriver(myWebD);
			setStepThinkTime(getParentTestCase().getStepThinkTime());
			/* remove all the asserters */
			TestCase newTestcase = (TestCase) GlobalUtils
					.findTestCaseBean(context);
			newTestcase.cleanUpAsserters();
			newTestcase.goSteps();

			/* transfer all the dataholders value to parent test case */
			for (int j = 0; j < getDataHolders().size(); j++) {
				IDataParser parentDataHolder = getDataHolders().get(j);
				if (parentDataHolder instanceof ICaseServiceParsedDataParser) {
					ICaseServiceParsedDataParser parentDataHolderTemp = (ICaseServiceParsedDataParser) parentDataHolder;
					parentDataHolderTemp
							.setStrDataValue(((IStepInputData) context
									.getBean(parentDataHolderTemp
											.getSubCaseMappedDataHolderID()))
									.getStrDataValue());
				}
			}

		} catch (Exception t) { // NOPMD
			//CaseTypeservice is considered as atomic step. We don't do step jump inside of the casetypeservice
			//if error appears, directly, we exit the service and throw error.
			mainDriver.quit();
			StepExecutionException pve = new StepExecutionException(
					StepExecutionException.MSG
							+ ExceptionMessage.MSG_SEPERATOR + t.getMessage(),
							StepExecutionException.CODE,
					this.getMyWebDriver(),
					getParentTestCase(), t);
			pve.initAteProblemInstance(this).setFatalProblem(true);//Any exception thrown out of testcase is fatal for this step.
			
			throw pve;
		}

	}

	/**
	 * @return the testCaseFileName
	 */
	public String getTestCaseFileName() {
		return testCaseFileName;
	}

	/**
	 * @return the parentTestCase
	 */
	public ITestCase getParentTestCase() {
		return parentTestCase;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IExpectedResultAsserter> getExpectedResultAsserter() {

		return new ArrayList<IExpectedResultAsserter>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IDataParser> getDataHolders() {

		return dataHolders;
	}

	/**
	 * @param dataHolders
	 *            the dataHolders to set
	 */
	public void setDataHolders(List<IDataParser> dataHolders) {
		this.dataHolders = dataHolders;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isOptionalStep() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setStepResultStatus(StepResultStatus stepResultStatus) {
		this.stepResultStatus = stepResultStatus;
		
	}

	/**
	 * @return the stepResultStatus
	 */
	public StepResultStatus getStepResultStatus() {
		return stepResultStatus;
	}
	
	/**
	 * Sets the step description, 
	 *
	 * @param desc the new step description
	 */
	public void setStepDescription(String desc) {
		//TODO future to add step desc for special step type
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCorrelatedOptionalStepsUtilInclusiveName() {
		// TODO add optionalStepUtil parameter for caseTypeService
		return "";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setOptionalStep(boolean optionalStep) {
		// TODO add optionalStepUtil parameter for caseTypeService
		
	}

//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public List<IOnTheFlyData<?>> getOnTheFlyDataHolders() {
//		return onTheFlyDataHolders;
//	}

//	/**
//	 * @param onTheFlyDataHolders the onTheFlyDataHolders to set
//	 */
//	public void setOnTheFlyDataHolders(List<IOnTheFlyData<?>> onTheFlyDataHolders) {
//		this.onTheFlyDataHolders = onTheFlyDataHolders;
//	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getCorrelatedOptionalStepsUtilInclusiveIndex(IStepJumpingEnclosedContainer jumpingContainer) {
		return -1;
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public boolean isCorrectedOnTheFly() {
		
		return false;
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public void setCorrectedOnTheFly(boolean correctedOnTheFly) {
		//for future.
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CucumberStepType getCucumberStepType() {
		return this.cucumberStepType;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long getTimeOutMillis() {
		return 10000l;
	}

	

}
