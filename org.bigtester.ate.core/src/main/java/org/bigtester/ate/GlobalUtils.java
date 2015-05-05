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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.aop.framework.Advised;
import org.bigtester.ate.model.casestep.StepDataLogger;
import org.bigtester.ate.model.casestep.TestCase;
import org.bigtester.ate.model.data.AbstractRunTimeDataHolder;
import org.bigtester.ate.model.data.TestDatabaseInitializer;
import org.bigtester.ate.model.page.atewebdriver.BrowserWindow;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.page.page.Homepage;
import org.bigtester.ate.model.page.page.Lastpage;
import org.bigtester.ate.model.page.page.RegularPage;
import org.bigtester.ate.model.project.TestProject;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

// TODO: Auto-generated Javadoc
/**
 * This class GlobalUtils defines ....
 * 
 * @author Peidong Hu
 *
 */
public class GlobalUtils implements ApplicationContextAware {

	/** The apx. */
	@Nullable
	private static ApplicationContext apx;//NOPMD
	
	/** The Constant - path delimiter */
	public static final String PATH_DELIMITER = "/" ;
	
	/** The Constant - default driver path */
	public static final String DEFAULT_DRIVER_PATH = "browserdriver" ;
	
	/** The browser driver path*/
	@Nullable
	@XStreamOmitField
	private static String driverPath; //NOPMD
	
	/**
	 * @return the browser driver path
	 */
	@Nullable public static String getDriverPath() {
		return driverPath;
	}

	/**
	 * @set the browser driver path
	 */
	public static void setDriverPath(@Nullable String driverPath) {
		GlobalUtils.driverPath = driverPath;
	}
	
	/**
	 * Gets the target object.
	 *
	 * @param <T>
	 *            the generic type
	 * @param proxy
	 *            the proxy
	 * @return the target object
	 * @throws Exception 
	 */
	
	@SuppressWarnings("unchecked")
	public static <T> T getTargetObject(@Nullable Object proxy) { //NOPMD
		if (proxy == null ) throw GlobalUtils.createNotInitializedException("proxy");
		while (AopUtils.isJdkDynamicProxy(proxy)) {
			try {
				return (T) getTargetObject(((Advised) proxy).getTargetSource()//NOPMD
						.getTarget());
			} catch (Exception e) {//NOPMD
				throw GlobalUtils.createInternalError("proxied object error", e);
			}
		}
		return (T) proxy; // expected to be cglib proxy then, which is simply a
							// specialized class
	}

	/**
	 * Find test case bean.
	 *
	 * @param appCtx
	 *            the app ctx
	 * @return the xml test case
	 * @throws NoSuchBeanDefinitionException
	 *             the no such bean definition exception
	 */
	public static TestCase findTestCaseBean(ApplicationContext appCtx)
			throws NoSuchBeanDefinitionException {
		Map<String, TestCase> testcases = appCtx.getBeansOfType(TestCase.class);

		if (testcases.isEmpty()) {
			throw new NoSuchBeanDefinitionException(TestCase.class);
		} else {
			TestCase retVal = testcases.values().iterator().next();
			if (null == retVal) {
				throw new NoSuchBeanDefinitionException(TestCase.class);
			} else {
				return retVal;
			}
		}
	}
	
	/**
	 * Find test case bean.
	 *
	 * @param appCtx
	 *            the app ctx
	 * @return the xml test case
	 * @throws NoSuchBeanDefinitionException
	 *             the no such bean definition exception
	 */
	public static TestCase findTestCaseBean()
			throws NoSuchBeanDefinitionException {
		ApplicationContext appCtx = getApx();
		Map<String, TestCase> testcases = appCtx.getBeansOfType(TestCase.class);

		if (testcases.isEmpty()) {
			throw new NoSuchBeanDefinitionException(TestCase.class);
		} else {
			TestCase retVal = testcases.values().iterator().next();
			if (null == retVal) {
				throw new NoSuchBeanDefinitionException(TestCase.class);
			} else {
				return retVal;
			}
		}
	}
	
	/**
	 * Find step data logger bean.
	 *
	 * @param appCtx the app ctx
	 * @return the step data logger
	 * @throws NoSuchBeanDefinitionException the no such bean definition exception
	 */
	public static StepDataLogger findStepDataLoggerBean(ApplicationContext appCtx)
			throws NoSuchBeanDefinitionException {
		Map<String, StepDataLogger> loggers = appCtx.getBeansOfType(StepDataLogger.class);

		if (loggers.isEmpty()) {
			throw new NoSuchBeanDefinitionException(TestCase.class);
		} else {
			StepDataLogger retVal = loggers.values().iterator().next();
			if (null == retVal) {
				throw new NoSuchBeanDefinitionException(TestCase.class);
			} else {
				return retVal;
			}
		}
	}

	/**
	 * Find run time data holder beans.
	 *
	 * @param appCtx
	 *            the app ctx
	 * @return the map
	 * @throws NoSuchBeanDefinitionException
	 *             the no such bean definition exception
	 */
	public static @Nullable Map<String, AbstractRunTimeDataHolder> findRunTimeDataHolderBeans(
			ApplicationContext appCtx) throws NoSuchBeanDefinitionException {
		return appCtx.getBeansOfType(AbstractRunTimeDataHolder.class);

	}

	/**
	 * Find db initializer.
	 *
	 * @param appCtx
	 *            the app ctx
	 * @return the test database initializer
	 */
	public static TestDatabaseInitializer findDBInitializer(
			ApplicationContext appCtx) throws NoSuchBeanDefinitionException {
		Map<String, TestDatabaseInitializer> dbInit = appCtx
				.getBeansOfType(TestDatabaseInitializer.class);

		if (dbInit.isEmpty()) {
			throw new NoSuchBeanDefinitionException(
					TestDatabaseInitializer.class);
		} else {
			TestDatabaseInitializer retVal = dbInit.values().iterator().next();
			if (null == retVal) {
				throw new NoSuchBeanDefinitionException(
						TestDatabaseInitializer.class);
			} else {
				return retVal;
			}
		}
	}

	/**
	 * Find db initializer.
	 *
	 * @param beanFac
	 *            the bean factory
	 * @return the test database initializer
	 */
	public static TestDatabaseInitializer findDBInitializer(BeanFactory beanFac)
			throws NoSuchBeanDefinitionException {

		TestDatabaseInitializer dbInit = beanFac
				.getBean(TestDatabaseInitializer.class);
		if (null == dbInit) {
			throw new NoSuchBeanDefinitionException(
					TestDatabaseInitializer.class);
		} else {
			return dbInit;
		}
	}

	/**
	 * Gets the case data files.
	 *
	 * @param appCtx
	 *            the app ctx
	 * @return the case data files
	 */
	@Nullable
	public static List<Resource> getCaseDataFiles(ApplicationContext appCtx) {
		Map<String, Homepage> homepages = appCtx.getBeansOfType(Homepage.class,
				true, true);
		Map<String, Lastpage> lastpages = appCtx.getBeansOfType(Lastpage.class);
		Map<String, RegularPage> regularpages = appCtx
				.getBeansOfType(RegularPage.class);

		List<Resource> dataFiles = new ArrayList<Resource>();
		for (int i = 0; i < homepages.size(); i++) {
			if (null != homepages.values().iterator().next().getDataFile())
				dataFiles.add(homepages.values().iterator().next()
						.getDataFile());
		}
		for (int i = 0; i < lastpages.size(); i++) {
			if (null != lastpages.values().iterator().next().getDataFile())
				dataFiles.add(lastpages.values().iterator().next()
						.getDataFile());
		}
		for (int i = 0; i < regularpages.size(); i++) {
			if (null != regularpages.values().iterator().next().getDataFile())
				dataFiles.add(regularpages.values().iterator().next()
						.getDataFile());
		}
		return dataFiles;

	}

	/**
	 * Find test project bean.
	 *
	 * @param appCtx
	 *            the app ctx
	 * @return the test project
	 * @throws NoSuchBeanDefinitionException
	 *             the no such bean definition exception
	 */
	public static TestProject findTestProjectBean(ApplicationContext appCtx)
			throws NoSuchBeanDefinitionException {
		Map<String, TestProject> testProjects = appCtx
				.getBeansOfType(TestProject.class);

		if (testProjects.isEmpty()) {
			throw new NoSuchBeanDefinitionException(TestProject.class);
		} else {
			TestProject testProject = testProjects.values().iterator().next();
			if (null == testProject) {
				throw new NoSuchBeanDefinitionException(TestProject.class);
			} else {
				return testProject;
			}
		}

	}

	/**
	 * Find data source bean.
	 *
	 * @param appCtx
	 *            the app ctx
	 * @return the data source
	 * @throws NoSuchBeanDefinitionException
	 *             the no such bean definition exception
	 */
	public static DataSource findDataSourceBean(ApplicationContext appCtx)
			throws NoSuchBeanDefinitionException {
		Map<String, DataSource> testcases = appCtx
				.getBeansOfType(DataSource.class);

		if (testcases.isEmpty()) {
			throw new NoSuchBeanDefinitionException(DataSource.class);
		} else {
			DataSource dataSource = testcases.values().iterator().next();
			if (null == dataSource) {
				throw new NoSuchBeanDefinitionException(DataSource.class);
			} else {
				return dataSource;
			}
		}

	}

	/**
	 * Find data source bean.
	 *
	 * @param appCtx
	 *            the app ctx
	 * @return the data source
	 * @throws NoSuchBeanDefinitionException
	 *             the no such bean definition exception
	 */
	public static IMyWebDriver findMyWebDriver(ApplicationContext appCtx)
			throws NoSuchBeanDefinitionException {
		Map<String, IMyWebDriver> drivers = appCtx
				.getBeansOfType(IMyWebDriver.class);

		if (drivers.isEmpty()) {
			throw new NoSuchBeanDefinitionException(DataSource.class);
		} else {
			IMyWebDriver retDriver = drivers.values().iterator().next();
			if (null == retDriver) {
				throw new NoSuchBeanDefinitionException(DataSource.class);
			} else {
				return retDriver;
			}
		}

	}

	/**
	 * Find data source bean.
	 *
	 * @param beanFac
	 *            the bean factory
	 * @return the data source
	 * @throws NoSuchBeanDefinitionException
	 *             the no such bean definition exception
	 */
	public static DataSource findDataSourceBean(BeanFactory beanFac) {
		DataSource dataSrc = beanFac.getBean(DataSource.class);

		if (null == dataSrc) {
			throw new NoSuchBeanDefinitionException(DataSource.class);
		} else {
			return dataSrc;
		}

	}

	/**
	 * Throw not initialized exception.
	 *
	 * @param variableName
	 *            the variable name
	 */
	public static IllegalStateException createNotInitializedException(
			String variableName) {
		return new IllegalStateException(variableName
				+ " not correctly populated.");
	}

	/**
	 * Throw not initialized exception.
	 *
	 * @param variableName
	 *            the variable name
	 */
	public static IllegalStateException createNotInitializedException(
			String variableName, Throwable cause) {
		return new IllegalStateException(variableName
				+ " not correctly populated.", cause);
	}

	/**
	 * Creates the internal error.
	 *
	 * @param errorPlace
	 *            the error place
	 * @return the illegal state exception
	 */
	public static IllegalStateException createInternalError(String errorPlace) {
		return new IllegalStateException("internal error at: " + errorPlace);
	}

	/**
	 * Creates the internal error.
	 *
	 * @param errorPlace
	 *            the error place
	 * @return the illegal state exception
	 */
	public static IllegalStateException createInternalError(String errorPlace,
			Throwable cause) {
		return new IllegalStateException("internal error at: " + errorPlace,
				cause);
	}

	// TODO use generic Type <T> to reduce the number of duplicated findNNNBean
	// functions.

	/**
	 * Instantiates a new global utils.
	 */
	public GlobalUtils() {
		GlobalUtils.apx = null;//NOPMD
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setApplicationContext(@Nullable ApplicationContext arg0)
			throws BeansException {
		if (null == arg0) throw GlobalUtils.createInternalError("Application Context Aware");
		GlobalUtils.apx = arg0;
	}

	/**
	 * @return the apx
	 */
	public static ApplicationContext getApx() {
		final ApplicationContext apx2 = apx;
		if (apx2 == null) {
			throw GlobalUtils.createInternalError("application context aware");
		} else {
			return apx2;
		}
	}

}
