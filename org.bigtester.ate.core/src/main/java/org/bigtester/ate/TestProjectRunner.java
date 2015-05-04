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

package org.bigtester.ate;

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

// TODO: Auto-generated Javadoc
/**
 * The Class TestProjectRunner defines ....
 * 
 * @author Peidong Hu
 */
public final class TestProjectRunner {
	
	private TestProjectRunner() {
		
	}
	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws SQLException 
	 * @throws DatabaseUnitException 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws ParseException 
	 */
	public static void main(final String... args) throws DatabaseUnitException, SQLException, IOException, ClassNotFoundException, ParseException {
		
		if (args.length > 2 )                          //NOPMD
			throw GlobalUtils.createNotInitializedException("Only support two arguments");
		if (args.length > 1 ) {                        //NOPMD                       
			GlobalUtils.setDriverPath (args[1]);       //NOPMD
		}
		if (args.length > 0 ) {
		   	runTest(args[0]);
		} 
		else {
			runTest("");
		}
	}
	
	/**
	 * Run test.
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws ParseException 
	 */
	private static void runTest(ApplicationContext context) throws ClassNotFoundException, ParseException, IOException {
		TestProject testProj = GlobalUtils.findTestProjectBean(context);
		testProj.runSuites();
		
	}
	
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
	public static void runTest(@Nullable final String testProjectXml) throws DatabaseUnitException, SQLException, IOException, ClassNotFoundException, ParseException  {
		ApplicationContext context;
		if (StringUtils.isEmpty(testProjectXml)) {
			context = new ClassPathXmlApplicationContext(
					"testproject.xml");
		} else {
			context = new FileSystemXmlApplicationContext(testProjectXml);
		}
		
		TestProject testplan = GlobalUtils.findTestProjectBean(context);
		testplan.setAppCtx(context);
		
		TestDatabaseInitializer dbinit = (TestDatabaseInitializer) context.getBean(GlobalConstants.BEAN_ID_GLOBAL_DBINITIALIZER);
		
		dbinit.setSingleInitXmlFile(testplan.getGlobalInitXmlFile());
		
		//TODO add db initialization handler
		dbinit.initializeGlobalDataFile(context);
		
		runTest(context);
		
	  	((ConfigurableApplicationContext)context).close();
	}

}
