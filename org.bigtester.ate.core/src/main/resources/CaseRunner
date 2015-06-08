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
package org.bigtester.ate.model.caserunner; //NOPMD

import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.constant.LogbackTag;
import org.bigtester.ate.model.casestep.TestCase;
import org.bigtester.ate.model.data.TestParameters;
import org.bigtester.ate.model.data.exception.TestDataException;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.project.IRunTestCase;
import org.bigtester.ate.model.project.TestProjectListener;
import org.bigtester.ate.systemlogger.LogbackWriter;
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestRunner;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;//NOPMD, can't be deleted since this file will be used as a template to create runners
import org.testng.internal.Utils;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

// TODO: Auto-generated Javadoc
/**
 * The Class CaseRunner runs a single test case
 * 
 * @author Peidong Hu
 */
@XmlRootElement
public class CaseRunner implements IRunTestCase {

	/** The context. */
	@Nullable
	@XStreamOmitField
	private ApplicationContext context;

	/** The main driver. */
	@Nullable
	private WebDriver mainDriver;

	/** The my tc. */
	@Nullable
	private TestCase myTestCase;

	/** The current executing tc name. */
	@Nullable
	protected String currentExecutingTCName; // must not be null

	/** The page object data files. */
	private List<Resource> pageObjectDataFiles = new ArrayList<Resource>();

	/**
	 * @return the pageObjectDataFiles
	 */
	public List<Resource> getPageObjectDataFiles() {
		return pageObjectDataFiles;
	}

	/**
	 * @param pageObjectDataFiles
	 *            the pageObjectDataFiles to set
	 */
	public void setPageObjectDataFiles(List<Resource> pageObjectDataFiles) {
		this.pageObjectDataFiles = pageObjectDataFiles;
	}

	/**
	 * @return the currentExecutingTCName
	 */
	public String getCurrentExecutingTCName() {
		final String currentExecutingTCName2 = currentExecutingTCName;
		if (null == currentExecutingTCName2 ) {
			throw GlobalUtils.createNotInitializedException("current tc name");
		} else {
			return currentExecutingTCName2;
		}

	}

	/**
	 * @param currentExecutingTCName
	 *            the currentExecutingTCName to set
	 */
	public void setCurrentExecutingTCName(String currentExecutingTCName) {
		this.currentExecutingTCName = currentExecutingTCName;
	}

	/**
	 * @return the myTestCase
	 */
	public TestCase getMyTestCase() {
		final TestCase retVal = myTestCase;
		if (null == retVal) {
			throw new IllegalStateException(
					"myTestCase is not correctly populated");

		} else {
			return retVal;
		}
	}

	/**
	 * @param myTestCase
	 *            the myTestCase to set
	 */
	public void setMyTestCase(final TestCase myTestCase) {
		this.myTestCase = myTestCase;
	}

	/**
	 * Gets the test data.
	 * 
	 * @param ctx
	 *            the ctx
	 * @return the test data
	 */
	@DataProvider(name = "dp")
	public Object[][] getTestData(ITestContext ctx) {
		if (null == currentExecutingTCName) {
			throw new IllegalStateException(
					"xml test case name is not correctly populated.");
		} else {
			TestParameters params = new TestParameters(getCurrentExecutingTCName(),
					getCurrentExecutingTCName());

			for (int index = 0; index < ((TestRunner) ctx).getTestListeners()
					.size(); index++) {
				if (((TestRunner) ctx).getTestListeners().get(index) instanceof TestProjectListener) {
					int thinkT = ((TestProjectListener) ((TestRunner) ctx)
							.getTestListeners().get(index)).getMytp()
							.getStepThinkTime();
					params.setStepThinkTime(thinkT);
					params.setGlobalAppCtx(((TestProjectListener) ((TestRunner) ctx)
							.getTestListeners().get(index)).getMytp()
							.getAppCtx());
					params.setTestProject(((TestProjectListener) ((TestRunner) ctx)
							.getTestListeners().get(index)).getMytp());
					break;
				}
			}

			return new Object[][] { { params } };
		}

	}

	/**
	 * Test data.
	 * 
	 * @param method
	 *            the method
	 * @param testData
	 *            the test data
	 */
	@BeforeMethod(alwaysRun = true)
	public void testData(Method method, Object[] testData) { // NOPMD
		String testCase;
		if (testData != null && testData.length > 0) {
			TestParameters testParams;
			// Check if test method has actually received required parameters
			for (Object testParameter : testData) {
				if (testParameter instanceof TestParameters) {
					testParams = (TestParameters) testParameter;
					testCase = testParams.getTestName();
					this.currentExecutingTCName = String.format("%s", testCase);
					break;
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Nullable
	public String getTestName() {
		return this.getCurrentExecutingTCName();
	}

	/**
	 * Test runner1.
	 * 
	 * @param ctx
	 *            the ctx
	 * @throws Throwable
	 */
	// @Test(dataProvider = "dp")
	public void runTest(TestParameters testParams) throws Throwable {
		String testname = testParams.getTestFilename();

		// ApplicationContext context;
		try {
			context = new FileSystemXmlApplicationContext(testname);
			IMyWebDriver myWebD = (IMyWebDriver) GlobalUtils
					.findMyWebDriver(context);
			mainDriver = myWebD.getWebDriverInstance();
			myTestCase = GlobalUtils.findTestCaseBean(getContext());
			getMyTestCase().setStepThinkTime(testParams.getStepThinkTime());
			getMyTestCase().setCurrentWebDriver(myWebD);
			getMyTestCase().setParentTestProject(testParams.getTestProject());
			getMyTestCase().goSteps();

		} catch (FatalBeanException fbe) {
			if (fbe.getCause() instanceof FileNotFoundException) {
				context = new ClassPathXmlApplicationContext(testname);
				mainDriver = ((IMyWebDriver) GlobalUtils
						.findMyWebDriver(context)).getWebDriverInstance();
				myTestCase = GlobalUtils.findTestCaseBean(getContext());
				myTestCase.setStepThinkTime(testParams.getStepThinkTime());
				getMyTestCase().goSteps();

			} else if (fbe instanceof BeanCreationException) { // NOPMD
				ITestResult itr = Reporter.getCurrentTestResult();

				if (itr.getThrowable() != null
						&& itr.getThrowable() instanceof TestDataException) {
					TestDataException tde = (TestDataException) itr
							.getThrowable();
					tde.setTestStepName(((BeanCreationException) fbe.getCause())
							.getBeanName());
					tde.setTestCaseName(((BeanCreationException) fbe)
							.getResourceDescription());
					tde.setMessage(tde.getMessage() + LogbackTag.TAG_SEPERATOR
							+ tde.getTestCaseName() + LogbackTag.TAG_SEPERATOR
							+ tde.getTestStepName());
					throw (TestDataException) itr.getThrowable();
				} else { // other test case bean creation errors. need to create
					// another exception to handle it.
					String[] fullST = Utils.stackTrace(fbe, false);
					int TWO = 2; // NOPMD
					if (fullST.length < TWO) {
						LogbackWriter
								.writeSysError("java internal error in stack trace.");
					} else {
						String errLog = fullST[1];
						if (null == errLog)
							LogbackWriter
									.writeSysError("java internal error in stack trace.");
						else
							LogbackWriter.writeSysError(errLog);
					}
					throw fbe;

				}
			} else {
				throw fbe;
			}
		}
//		catch (Throwable th) {//NOPMD
//			LogbackWriter.printStackTrace(th);
//			throw th;
//		}
	}

	/**
	 * Tear down.
	 */
	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		try {
			if (null != mainDriver) {
				mainDriver.quit();
			}
			if (null != context) {

				Map<String, IMyWebDriver> myWebDrivers = context
						.getBeansOfType(IMyWebDriver.class);
				for (IMyWebDriver myWebDriver2 : myWebDrivers.values()) {
					WebDriver weD = myWebDriver2.getWebDriver();
					if (null != weD)
						weD.quit();
				}
			}
		} catch (UnreachableBrowserException | NoSuchWindowException e) { // NOPMD
			// browser has been closed, no action needs to be done here.
		}
		if (null != context) {
			((ConfigurableApplicationContext) context).close();
		}

	}

	/**
	 * @return the context
	 */
	public ApplicationContext getContext() {
		final ApplicationContext retVal = context;
		if (null == retVal) {
			throw new IllegalStateException(
					"context is not correctly populated");

		} else {
			return retVal;
		}
	}

	/**
	 * @param context
	 *            the context to set
	 */
	public void setContext(ApplicationContext context) {
		this.context = context;
	}

	/**
	 * @return the mainDriver
	 */
	public WebDriver getMainDriver() {
		final WebDriver retVal = mainDriver;
		if (null == retVal) {
			throw new IllegalStateException(
					"mainDriver is not correctly populated");

		} else {
			return retVal;
		}
	}

	/**
	 * @param mainDriver
	 *            the mainDriver to set
	 */
	public void setMainDriver(WebDriver mainDriver) {
		this.mainDriver = mainDriver;
	}

}
