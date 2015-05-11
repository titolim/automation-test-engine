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
package org.bigtester.ate.test;

import java.io.IOException;

import static org.mockito.Mockito.mock;

import java.sql.SQLException;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.constant.GlobalConstants;
import org.bigtester.ate.model.data.TestDatabaseInitializer;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.project.TestProject;
import org.dbunit.DatabaseUnitException;
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * The Class CookiesManagerTest.
 *
 * @author Peidong Hu
 */

public class BaseATETest extends AbstractTestNGSpringContextTests implements
		ApplicationContextAware, BeanFactoryPostProcessor, PriorityOrdered {

	
	/** The mocked driver. */
	final private WebDriver mockedDriver;
	/** The my mocked driver. */
	final private IMyWebDriver myMockedDriver;

	/** The options. */
	final private Options options;
	/**
	 * @return the mockedDriver
	 */
	public final WebDriver getMockedDriver() {
		return mockedDriver;
	}

	/**
	 * ATE mock. Use ate mock to avoid null pointer check warning in your code.
	 *
	 * @param <T> the generic type
	 * @param classToMock the class to mock
	 * @return the t
	 */
	public final <T> T ateMock(Class<?> classToMock) {
		@SuppressWarnings("unchecked")
		T retVal = (T) mock(classToMock);
		if (null == retVal) throw GlobalUtils.createInternalError("mock");
		return retVal;
	}
	
	/**
	 * @return the myMockedDriver
	 */
	public final IMyWebDriver getMyMockedDriver() {
		return myMockedDriver;
	}

	/**
	 * Instantiates a new base ate test.
	 */
	public BaseATETest() {
		super();
		mockedDriver = ateMock(WebDriver.class);
		myMockedDriver = ateMock(IMyWebDriver.class);
		options = ateMock(Options.class);
	}
	
	/**
	 * @return the applicationContext
	 */
	public ApplicationContext getApplicationContext() {
		final ApplicationContext applicationContext2 = applicationContext;
		if (applicationContext2 == null) {
			throw GlobalUtils.createNotInitializedException("app context");
		} else {
			return applicationContext2;

		}

	}

	private void initDB() throws IOException, DatabaseUnitException,
			SQLException {
		TestProject testplan = GlobalUtils
				.findTestProjectBean(getApplicationContext());
		testplan.setAppCtx(getApplicationContext());

		TestDatabaseInitializer dbinit = (TestDatabaseInitializer) getApplicationContext()
				.getBean(GlobalConstants.BEAN_ID_GLOBAL_DBINITIALIZER);

		dbinit.setSingleInitXmlFile(testplan.getGlobalInitXmlFile());

		dbinit.initializeGlobalDataFile(getApplicationContext());

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void postProcessBeanFactory(
			@Nullable ConfigurableListableBeanFactory arg0)
			throws BeansException {
		try {
			initDB();
		} catch (IOException | DatabaseUnitException | SQLException e) {
			throw GlobalUtils.createInternalError("initDB", e);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getOrder() {
		return 0;
	}

	/**
	 * @return the options
	 */
	public Options getOptions() {
		return options;
	}

	/**
	 * Gets the web driver manage.
	 *
	 * @return the web driver manage
	 */
	public Options getWebDriverManage() {
		return getOptions();
	}
}
