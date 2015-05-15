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

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

// TODO: Auto-generated Javadoc
/**
 * This class BigtesterTest defines ....
 * @author Peidong Hu
 *
 */
@ContextConfiguration(locations = {"classpath:bigtesterTestNG/testproject.xml"})
public class BigtesterProjectTest extends BaseATETest {
	
	/** The my driver. */
	@Nullable
	private IMyWebDriver myDriver;

	/**
	 * @param myDriver the myDriver to set
	 */
	public final void setMyDriver(IMyWebDriver myDriver) {
		this.myDriver = myDriver;
	}
	
	/**
	 * @return the myDriver
	 */
	public final IMyWebDriver getMyDriver() {
		final IMyWebDriver myDriver2 = myDriver;
		if (myDriver2 == null) {
			throw GlobalUtils.createNotInitializedException("web driver");
		} else {
			return myDriver2;
			
		}
	}
	
	/**
	 * Inits the test objects.
	 */
	@BeforeClass
	public void initTestObjects() {
		Object obj = applicationContext.getBean(IMyWebDriver.class);
		if (obj == null) {
			throw GlobalUtils.createInternalError("app ctx web driver");
		}
		myDriver = (IMyWebDriver) obj;
	}
	
	/**
	 * Tear down.
	 */
	@AfterClass
	public void tearDown() {
		if (getMyDriver().getWebDriver() != null)
			getMyDriver().getWebDriverInstance().quit();
		
	}
}
