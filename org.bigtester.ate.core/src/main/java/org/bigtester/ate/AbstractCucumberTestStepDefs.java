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

package org.bigtester.ate;//NOPMD

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.testng.ITestResult;
import org.bigtester.ate.constant.GlobalConstants;
import org.bigtester.ate.constant.StepResultStatus;
import org.bigtester.ate.model.cucumber.ActionNameValuePair;
import org.bigtester.ate.model.data.TestDatabaseInitializer;
import org.bigtester.ate.model.project.TestProject;
import org.bigtester.ate.model.testresult.TestStepResult;
import org.bigtester.ate.reporter.ATEXMLReporter;
import org.bigtester.ate.reporter.ATEXMLSuiteResultWriter;
import org.dbunit.DatabaseUnitException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.StringUtils;

import com.github.javaparser.ParseException;

import cucumber.api.Scenario;

// TODO: Auto-generated Javadoc
/**
 * The Class TestProjectRunner defines ....
 * 
 * @author Peidong Hu
 */

abstract public class AbstractCucumberTestStepDefs {
	/** The test project context. */
	private ApplicationContext testProjectContext;
	/**
	 * The Class AteProjectFilter.
	 *
	 * @author Peidong Hu
	 */
	public class AteProjectFilter{
		
		/** The suite name. */
		final private String suiteName;
		
		/** The case name. */
		final private String caseName;
		
		/** The step name. */
		final private String stepName;
		
		/**
		 * Instantiates a new ate project filter.
		 *
		 * @param suiteName the suite name
		 * @param caseName the case name
		 * @param stepName the step name
		 */
		public AteProjectFilter(String suiteName, String caseName, String stepName) {
			this.suiteName = suiteName;
			this.caseName = caseName;
			this.stepName = stepName;
		}
		/**
		 * @return the suiteName
		 */
		public String getSuiteName() {
			return suiteName;
		}
		/**
		 * @return the caseName
		 */
		public String getCaseName() {
			return caseName;
		}
		/**
		 * @return the stepName
		 */
		public String getStepName() {
			return stepName;
		}
	}
	

	
	static { // runs when the main class is loaded.
		System.setProperty("logback-access.debug", "true");
		// System.setProperty("org.jboss.logging.provider", "slf4j");
		System.setProperty("org.jboss.logging.provider", "log4j");
		System.setProperty("hsqldb.reconfig_logging", "false");

	}
	/**
	 * Gets the scenario.
	 *
	 * @return the scenario
	 */
	public abstract Scenario getScenario();

	/**
	 * Gets the ate glue test project xml file path.
	 *
	 * @return the ate glue test project xml file path
	 */
	public abstract String getAteGlueTestProjectXmlFilePath();

	/**
	 * Instantiates a new abstract cucumber test step defs.
	 */
	public AbstractCucumberTestStepDefs() {
		TestProjectRunner.registerXsdNameSpaceParsers();
		TestProjectRunner.registerProblemHandlers();

	}

	/**
	 * Run test.
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	private StepResultStatus runStep(ApplicationContext context, AteProjectFilter executionFilter, 
			List<Map<String, String>> featureDataTable,
			ActionNameValuePair... actionNameValuePairs)
			 {
		StepResultStatus retVal = StepResultStatus.FAIL;
		TestProject testProj = GlobalUtils.findTestProjectBean(context);
		testProj.setFilteringTestCaseName(executionFilter.getCaseName());
		testProj.setFilteringStepName(executionFilter.stepName);
		testProj.setFilteringTestSuiteName(executionFilter.suiteName);
		testProj.setCucumberDataTable(featureDataTable);
		if (actionNameValuePairs.length > 0)
			testProj.setCucumberActionNameValuePairs(new ArrayList<ActionNameValuePair>(
					Arrays.asList(actionNameValuePairs)));
		try {
			testProj.runSuites();
		} catch (ClassNotFoundException | ParseException | IOException e) {
			retVal = StepResultStatus.FAIL;
		}
		ATEXMLReporter ateReporter = (ATEXMLReporter) testProj.getTestng()
				.getReporters().stream()
				.filter(reporter -> reporter instanceof ATEXMLReporter)
				.collect(Collectors.toList()).get(0);
		Set<ITestResult> testResults = ateReporter
				.getSuiteResults()
				.values()
				.stream()
				.map(tmp -> tmp.entrySet())
				.flatMap(lem -> lem.stream())
				.map(entry -> entry.getValue())
				.map(suiteResult -> ATEXMLSuiteResultWriter
						.convertSuiteResultToTestResults(suiteResult))
				.flatMap(mem -> mem.stream()).collect(Collectors.toSet());

		List<TestStepResult> stepResults = testResults
				.stream()
				.map(testR -> ATEXMLSuiteResultWriter
						.retrieveTestStepResults(testR))
				.flatMap(mem -> mem.stream()).collect(Collectors.toList());
		
		retVal = stepResults.iterator().next().getThisStep().getStepResultStatus();
		return retVal;

	}

	/**
	 * Run cucumber step.
	 *
	 * @param ateStepName the ate step name
	 * @return the string
	 */
	protected StepResultStatus runCucumberStep(String ateStepName) {
		String testProjectXml = this.getAteGlueTestProjectXmlFilePath();
		StepResultStatus retVal = StepResultStatus.FAIL;
		
			String testCaseName = getScenario().getName();
			String testSuiteName = getScenario().getId().substring(0,
					getScenario().getId().indexOf(";"));
			

			retVal = runStep(new AteProjectFilter(testSuiteName, testCaseName, ateStepName), testProjectXml, 
					new ArrayList<Map<String, String>>());
		
		return retVal;
	};

	/**
	 * Run cucumber step.
	 *
	 * @param executionFilter the execution filter
	 * @param featureDataTable the feature data table
	 * @param actionNameValuePairs the action name value pairs
	 * @return the step result status
	 */
	protected StepResultStatus runCucumberStep(AteProjectFilter executionFilter, List<Map<String, String>> featureDataTable,
			ActionNameValuePair... actionNameValuePairs) {
		String testProjectXml = this.getAteGlueTestProjectXmlFilePath();
		
		

		return runStep(executionFilter, testProjectXml, 
					featureDataTable, actionNameValuePairs);
		
	};
	
	/**
	 * Run cucumber step.
	 *
	 * @param executionFilter the execution filter
	 * @param actionNameValuePairs the action name value pairs
	 * @return the step result status
	 */
	protected StepResultStatus runCucumberStep(AteProjectFilter executionFilter,
			ActionNameValuePair... actionNameValuePairs) {
		String testProjectXml = this.getAteGlueTestProjectXmlFilePath();
		
			
		return runStep(executionFilter, testProjectXml, 
					null, actionNameValuePairs);
		
	};

	/**
	 * Run cucumber step.
	 *
	 * @param executionFilter the execution filter
	 * @return the step result status
	 */
	protected StepResultStatus runCucumberStep(AteProjectFilter executionFilter) {
		String testProjectXml = this.getAteGlueTestProjectXmlFilePath();
	

		return runStep(executionFilter, testProjectXml, 
					null);
		
		
	};

	/**
	 * Run test.
	 *
	 * @param testProjectXml
	 *            the test project xml
	 * @throws DatabaseUnitException
	 *             the database unit exception
	 * @throws SQLException
	 *             the SQL exception
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws ParseException
	 */
	private StepResultStatus runStep(AteProjectFilter executionFilter,
			final String testProjectXml,
			List<Map<String, String>> featureDataTable,
			ActionNameValuePair... actionNameValuePairs)
			 {
		StepResultStatus retVal = StepResultStatus.FAIL;
		if (StringUtils.isEmpty(testProjectXml) && testProjectContext == null) {
			testProjectContext = new ClassPathXmlApplicationContext(
					"testproject.xml");
		} else if (testProjectContext == null) {
			testProjectContext = new FileSystemXmlApplicationContext(
					testProjectXml);
		}

		TestProject testplan = GlobalUtils
				.findTestProjectBean(testProjectContext);
		testplan.setAppCtx(testProjectContext);

		TestDatabaseInitializer dbinit = (TestDatabaseInitializer) testProjectContext
				.getBean(GlobalConstants.BEAN_ID_GLOBAL_DBINITIALIZER);
		try {
		if (dbinit.getSingleInitXmlFile() == null)
			
				dbinit.setSingleInitXmlFile(testplan.getGlobalInitXmlFile());
			

		// TODO add db initialization handler
		if (dbinit.getDatasets() == null)
			
				dbinit.initializeGlobalDataFile(testProjectContext);
			

		retVal = runStep(testProjectContext, executionFilter, featureDataTable, actionNameValuePairs);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			retVal = StepResultStatus.FAIL;
		} catch (DatabaseUnitException e) {
			// TODO Auto-generated catch block
			retVal = StepResultStatus.FAIL;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			retVal = StepResultStatus.FAIL;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			retVal = StepResultStatus.FAIL;
		}
		return retVal;

	}

	/**
	 * Close ate execution context.
	 */
	protected void closeAteExecutionContext() {
		if (testProjectContext != null) {
			((ConfigurableApplicationContext) testProjectContext).close();
			testProjectContext = null;//NOPMD
		}
	}

	/**
	 * @return the testProjectContext
	 */
	public ApplicationContext getTestProjectContext() {
		return testProjectContext;
	}

	/**
	 * @param testProjectContext the testProjectContext to set
	 */
	public void setTestProjectContext(ApplicationContext testProjectContext) {
		this.testProjectContext = testProjectContext;
	}

}
