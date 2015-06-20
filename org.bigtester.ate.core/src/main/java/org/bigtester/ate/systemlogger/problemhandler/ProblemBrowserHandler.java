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
package org.bigtester.ate.systemlogger.problemhandler;

import java.util.Properties;

import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.problomatic2.InitException;
import org.bigtester.problomatic2.Problem;
import org.bigtester.problomatic2.ProblemHandler;
import org.bigtester.problomatic2.handlers.AbstractProblemHandler;
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.WebDriver;



// TODO: Auto-generated Javadoc
/**
 * This handler is not used by ate because testng teardown has cover the browser quit action.
 * 
 * @author Peidong Hu
 * 
 */
public class ProblemBrowserHandler extends AbstractProblemHandler implements
		ProblemHandler {
	
	/** The my web driver. */
	private final transient  IMyWebDriver myWebDriver;
	
	/**
	 * Instantiates a new problem browser handler.
	 *
	 * @param myWebDriver the my web driver
	 */
	public ProblemBrowserHandler(IMyWebDriver myWebDriver) {
		super();
		this.myWebDriver = myWebDriver;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleProblem(@Nullable Problem aProblem) {
		WebDriver webD = myWebDriver.getWebDriver();
		if ( null != webD)
			webD.quit();
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(@Nullable Properties properties) throws InitException {
		// TODO Auto-generated method stub

	}
}
