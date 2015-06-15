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
package org.bigtester.ate.model.data;

import java.util.List;

import org.bigtester.ate.model.casestep.ITestCase; 
import org.eclipse.jdt.annotation.Nullable;

// TODO: Auto-generated Javadoc
/**
 * This class CaseConfigData defines two methods of system configuration
 * 1) by changing the aut's configuration database or config files
 * 2) by running other test cases.
 * @author Peidong Hu
 *
 */

public class CaseConfigData {
	
	/** The config sql. */
	//TODO for v2.0
	@Nullable
	private List<String> configSQLs;
	
	/** The change file command. */
	//TODO for v2.0
	@Nullable
	private List<String> changeFileCommands;
	
	/** The config test cases. */
	@Nullable
	private List<ITestCase> configTestCases;
	
	/**
	 * Gets the config test cases.
	 *
	 * @return the configTestCases
	 */
	@Nullable
	public List<ITestCase> getConfigTestCases() {
		return configTestCases;
	}
	
	/**
	 * Sets the config test cases.
	 *
	 * @param configTestCases the configTestCases to set
	 */
	public void setConfigTestCases(List<ITestCase> configTestCases) {
		this.configTestCases = configTestCases;
	}

	/**
	 * @return the configSQLs
	 */
	@Nullable
	public List<String> getConfigSQLs() {
		return configSQLs;
	}

	/**
	 * @param configSQLs the configSQLs to set
	 */
	public void setConfigSQLs(List<String> configSQLs) {
		this.configSQLs = configSQLs;
	}

	/**
	 * @return the changeFileCommands
	 */
	@Nullable
	public List<String> getChangeFileCommands() {
		return changeFileCommands;
	}

	/**
	 * @param changeFileCommands the changeFileCommands to set
	 */
	public void setChangeFileCommands(List<String> changeFileCommands) {
		this.changeFileCommands = changeFileCommands;
	}

}
