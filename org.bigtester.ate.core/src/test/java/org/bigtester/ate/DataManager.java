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

import java.io.File;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;


import org.bigtester.ate.model.casestep.TestCase;
import org.bigtester.ate.model.data.dao.ElementInputDataDaoImpl;
import org.bigtester.ate.model.data.dbtable.ElementInputData;
import org.bigtester.ate.model.page.exception.PageValidationException2;
import org.bigtester.ate.model.page.exception.StepExecutionException2;
import org.bigtester.ate.systemlogger.LogbackWriter;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class DataManager is to test the data model
 * 
 * @author Peidong Hu
 */
public class DataManager {

	
	private String locked = ""; //NOPMD

//	/**
//	 * Test first data class.
//	 * 
//	 * @throws InterruptedException
//	 *             the interrupted exception
//	 * @throws StepExecutionException
//	 *             the step execution exception
//	 * @throws PageValidationException 
//	 */
//	@Test
//	public void testDataClassTestCase() throws InterruptedException,
//			StepExecutionException2, PageValidationException2 {
//		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
//				"Test-applicationContext.xml");
//		/** The my tc. */
//		TestCase myTestCase;
//		myTestCase = GlobalUtils.findTestCaseBean(context);
//		myTestCase.goSteps();
//		context.close();
//	}
//
//	/**
//	 * Test first data class.
//	 * 
//	 * @throws InterruptedException
//	 *             the interrupted exception
//	 */
//	@Test
//	public void testDataClassWithoutTestCase() throws InterruptedException {
//		//Thread.sleep(10000);
//		LogbackWriter.writeAppInfo("testng-msg: "
//				+ "lock on and 2nd thread running through" + locked);
//		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
//				"Test-dbContext.xml");
//		ElementInputDataDaoImpl dao = (ElementInputDataDaoImpl) context
//				.getBean("elementInputDataDao");
//
//		ElementInputData eid = new ElementInputData("login Name", "peidonghu1");
//		ElementInputData eid2 = new ElementInputData("login Name", "peidonghu3");
//
//		dao.save(eid);
//		dao.save(eid2);
//
//		List<ElementInputData> persons = dao.getAll();
//		for (ElementInputData person : persons) {
//			LogbackWriter.writeAppInfo("testng-msg: " + person.toString());
//		}
//		context.close();
//		LogbackWriter.writeAppInfo("testng-msg: "
//				+ "lock on and 2nd thread running through" + locked);
//	}
//
//	/**
//	 * Test first data class.
//	 * 
//	 * @throws InterruptedException
//	 *             the interrupted exception
//	 * @throws SQLException 
//	 * @throws DatabaseUnitException 
//	 * @throws DataSetException 
//	 * @throws MalformedURLException 
//	 */
//	@Test
//	public void testAutomaticallyCreateTable() throws InterruptedException, SQLException, MalformedURLException, DataSetException, DatabaseUnitException {
//
//		LogbackWriter.writeUnitTestInfo(ATETestUtil.getMethodName(0));
//		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
//				"Test-dbContext.xml");
//		DataSource datas = (DataSource)context.getBean("dataSource");
//		IDatabaseConnection con = new DatabaseConnection(datas.getConnection()); //Create DBUnit Database connection 
//		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
//		builder.setColumnSensing(true);
//		DatabaseOperation.CLEAN_INSERT.execute(con, builder.build(new File("src/main/resources/META-INF/data.xml"))); //Import your data
//		con.close();
//			
//		context.close();
//		LogbackWriter.writeUnitTestInfo(ATETestUtil.getMethodName(0) + "ended");
//	}
//	
//	/**
//	 * Test first data class.
//	 * 
//	 * @throws InterruptedException
//	 *             the interrupted exception
//	 * @throws SQLException 
//	 * @throws DatabaseUnitException 
//	 * @throws DataSetException 
//	 * @throws MalformedURLException 
//	 */
//	@Test
//	public void testEnumConstantTable() throws InterruptedException, SQLException, MalformedURLException, DataSetException, DatabaseUnitException {
//
//		LogbackWriter.writeUnitTestInfo(ATETestUtil.getMethodName(0));
//		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
//				"Test-dbContext.xml");
//		DataSource datas = (DataSource)context.getBean("dataSource");
//		IDatabaseConnection con = new DatabaseConnection(datas.getConnection()); //Create DBUnit Database connection 
//		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
//		builder.setColumnSensing(true);
//		DatabaseOperation.REFRESH.execute(con, builder.build(new File("src/main/resources/META-INF/data.xml"))); //Import your data
//		con.close();
//			
//		context.close();
//		LogbackWriter.writeUnitTestInfo(ATETestUtil.getMethodName(0) + "ended");
//	}
}
