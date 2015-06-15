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

import java.util.List;

import org.bigtester.ate.annotation.ATELogLevel;
import org.bigtester.ate.annotation.TestCaseLoggable; 
import org.bigtester.ate.model.data.exception.RuntimeDataException;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.page.exception.PageValidationException;
import org.bigtester.ate.model.page.exception.StepExecutionException;
import org.bigtester.ate.model.project.TestProject; 

// TODO: Auto-generated Javadoc
/**
 * This class ITestCase defines ....
 * 
 * @author Peidong Hu
 *
 */
public interface ITestCase {

	/**
	 * @return the stepThinkTime
	 */
	int getStepThinkTime();

	/**
	 * @param stepThinkTime
	 *            the stepThinkTime to set
	 */
	void setStepThinkTime(int stepThinkTime);

	/**
	 * Gets the test step list.
	 * 
	 * @return the testStepList
	 */
	List<ITestStep> getTestStepList();

	/**
	 * Sets the test step list.
	 * 
	 * @param testStepList
	 *            the testStepList to set
	 */
	  void setTestStepList(final List<ITestStep> testStepList);

	/**
	 * run steps.
	 * 
	 * @throws RuntimeDataException
	 * @throws StepExecutionException
	 * @throws PageValidationException
	 */
	@TestCaseLoggable(level = ATELogLevel.INFO)
	void goSteps() throws StepExecutionException,
	PageValidationException, IllegalStateException,
	RuntimeDataException;

	/**
	 * Gets the test case name.
	 *
	 * @return the testCaseName
	 */
	String getTestCaseName();

	/**
	 * Sets the test case name.
	 *
	 * @param testCaseName
	 *            the testCaseName to set
	 */
	void setTestCaseName(String testCaseName);

	/**
	 * Gets the current test step.
	 *
	 * @return the currentTestStep
	 */
	ITestStep getCurrentTestStep();

	/**
	 * Sets the current test step.
	 *
	 * @param currentTestStep
	 *            the currentTestStep to set
	 */
	void setCurrentTestStep(ITestStep currentTestStep);

	/**
	 * Gets the current web driver.
	 *
	 * @return the currentWebDriver
	 */
	IMyWebDriver getCurrentWebDriver();

	/**
	 * Sets the current web driver.
	 *
	 * @param currentWebDriver
	 *            the currentWebDriver to set
	 */
	void setCurrentWebDriver(IMyWebDriver currentWebDriver);

	/**
	 * @return the parentTestProject
	 */
	TestProject getParentTestProject();

	/**
	 * @param parentTestProject
	 *            the parentTestProject to set
	 */
	void setParentTestProject(TestProject parentTestProject);

	/**
	 * {@inheritDoc}
	 */
	List<ITestStep> getContainerStepList();

}
