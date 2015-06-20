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
package org.bigtester.ate.model.project;

import org.bigtester.ate.model.data.TestParameters;
import org.bigtester.ate.model.data.exception.TestDataException;
import org.bigtester.ate.model.page.exception.StepExecutionException;
import org.eclipse.jdt.annotation.Nullable;
import org.testng.ITest;

// TODO: Auto-generated Javadoc
/**
 * The Interface IRunSuites defines ....
 *
 * @author Peidong Hu
 */
public interface IRunTestCase extends ITest {
	
	/**
	 * Run suites.
	 * @throws TestDataException 
	 * @throws Throwable 
	 */
	void runTest(TestParameters testParams) throws StepExecutionException, TestDataException, Throwable;
	
	/**
	 * Sets the current executing tc name.
	 *
	 * @param cetc the new current executing tc name
	 */
	void setCurrentExecutingTCName(String cetc);
	
	/**
	 * Gets the current executing tc name.
	 *
	 * @return the current executing tc name
	 */
	@Nullable
	String getCurrentExecutingTCName();
}
