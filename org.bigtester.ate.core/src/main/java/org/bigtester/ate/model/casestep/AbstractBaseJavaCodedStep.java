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


import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.model.data.exception.RuntimeDataException;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.page.exception.PageValidationException;
import org.bigtester.ate.model.page.exception.StepExecutionException; 
import org.eclipse.jdt.annotation.Nullable; 
import org.springframework.beans.factory.annotation.Autowired;   

// TODO: Auto-generated Javadoc
/**
 * This class JavaCodeStep defines ....
 * @author Peidong Hu
 *
 */

abstract public class AbstractBaseJavaCodedStep extends BaseTestStep{
	
	/** The my web driver. */
	@Autowired
	@Nullable
	private IMyWebDriver myWebDriver;
	
	
	/**
	 * Gets the my web driver.
	 *
	 * @return the my web driver
	 */
	public IMyWebDriver getMyWebDriver() {
		final IMyWebDriver myWebDriver2 = myWebDriver;
		if (myWebDriver2== null) {
			throw GlobalUtils.createNotInitializedException("my web driver");
		} else {
			return myWebDriver2;
		}
	}
		
	
	/**
	 * Sets the my web driver.
	 *
	 * @param myD the new my web driver
	 */
	public void setMyWebDriver(IMyWebDriver myD) {
		myWebDriver = myD;
	}
	
	/**
	 * {@inheritDoc}
	 * if user wants to use different webdriver than the default system one, please override this method.
	 */
	public void doStep(IMyWebDriver myWebDriver)
			throws StepExecutionException, PageValidationException,
			RuntimeDataException {
		
		return;//NOPMD

	}
	
}
