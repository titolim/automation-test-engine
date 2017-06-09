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
import java.sql.SQLException;

import org.bigtester.ate.constant.GlobalConstants;
import org.bigtester.ate.model.data.TestDatabaseInitializer;
import org.bigtester.ate.model.project.TestProject;
import org.dbunit.DatabaseUnitException;
import org.eclipse.jdt.annotation.Nullable;
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
	static { //runs when the main class is loaded.
	    System.setProperty("logback-access.debug", "true");
//	    System.setProperty("org.jboss.logging.provider", "slf4j");
	    System.setProperty("org.jboss.logging.provider", "log4j");
	    System.setProperty("hsqldb.reconfig_logging", "false");
	    
	}
	
	 
	private ApplicationContext testProjectContext;
	
	public abstract Scenario getScenario();
	
	public abstract String getAteGlueTestProjectXmlFilePath();
	
		 
	public AbstractCucumberTestStepDefs() {
		TestProjectRunner.registerXsdNameSpaceParsers();
		TestProjectRunner.registerProblemHandlers();
		
	}
	/**
	 * Run test.
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws ParseException 
	 */
	private void runStep(ApplicationContext context, final String testCaseName, final String testCaseId, @Nullable final String stepTypeServiceName) throws ClassNotFoundException, ParseException, IOException {
		TestProject testProj = GlobalUtils.findTestProjectBean(context);
		testProj.setFilteringTestCaseName(testCaseName);
		testProj.setFilteringStepName(stepTypeServiceName);
		testProj.runSuites();
		
	}
	
	protected void runCucumberStep(String ateStepName) {
		String testProjectXml = this.getAteGlueTestProjectXmlFilePath();
		try {
			String testCaseName = getScenario().getName();
			String testSuiteName = getScenario().getId().substring(0, getScenario().getId().indexOf(";"));
			String stepName = ateStepName;
			
			runStep(testCaseName, testSuiteName,  testProjectXml, stepName);
		} catch (ClassNotFoundException | DatabaseUnitException | SQLException
				| IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};
	
	protected void runCucumberStep(String ateStepName, String testCaseName, String testSuiteName) {
		String testProjectXml = this.getAteGlueTestProjectXmlFilePath();
		try {
			String stepName = ateStepName;
			
			runStep(testCaseName, testSuiteName,  testProjectXml, stepName);
		} catch (ClassNotFoundException | DatabaseUnitException | SQLException
				| IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};
	
	/**
	 * Run test.
	 *
	 * @param testProjectXml the test project xml
	 * @throws DatabaseUnitException the database unit exception
	 * @throws SQLException the SQL exception
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws ParseException 
	 */
	private void runStep(final String testCaseName, final String testCaseId, final String testProjectXml, @Nullable final String stepTypeServiceName) throws DatabaseUnitException, SQLException, IOException, ClassNotFoundException, ParseException  {
		
		
		if (StringUtils.isEmpty(testProjectXml) && testProjectContext == null) {
			testProjectContext = new ClassPathXmlApplicationContext(
					"testproject.xml");
		} else if (testProjectContext == null ){
			testProjectContext = new FileSystemXmlApplicationContext(testProjectXml);
		}
		
		TestProject testplan = GlobalUtils.findTestProjectBean(testProjectContext);
		testplan.setAppCtx(testProjectContext);
		
		TestDatabaseInitializer dbinit = (TestDatabaseInitializer) testProjectContext.getBean(GlobalConstants.BEAN_ID_GLOBAL_DBINITIALIZER);
		
		if (dbinit.getSingleInitXmlFile() == null)
			dbinit.setSingleInitXmlFile(testplan.getGlobalInitXmlFile());
		
		//TODO add db initialization handler
		if (dbinit.getDatasets()==null)
			dbinit.initializeGlobalDataFile(testProjectContext);
		
		runStep(testProjectContext, testCaseName, testCaseId,stepTypeServiceName);
		

	}
	
	protected void closeAteExecutionContext() {
		if (testProjectContext!=null) {
			((ConfigurableApplicationContext) testProjectContext).close();
			testProjectContext = null;
		}
	}

}
