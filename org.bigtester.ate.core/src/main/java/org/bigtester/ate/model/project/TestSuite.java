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

import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

// TODO: Auto-generated Javadoc
/**
 * The Class TestSuite defines ....
 * 
 * @author Peidong Hu
 */
public class TestSuite {

	/** The test case list. */
	@Nullable
	private List<XmlTestCase> testCaseList;

	/** The suite name. */
	private String suiteName;

	/**
	* {@inheritDoc}
	*/
	@Override
	public String toString() {
		String retVal =  "Suite: " + suiteName + " with following test cases, ";//NOPMD
		final List<XmlTestCase> testCaseList2 = testCaseList;
		if (testCaseList2 != null) {
			for (XmlTestCase testCase: testCaseList2) {
				retVal = retVal + "\r\n" + testCase.getTestCaseFilePathName() ;//NOPMD
			}
		} 
		
		return retVal;
	}
	/**
	 * Instantiates a new test suite.
	 *
	 * @param suiteName the suite name
	 */
	public TestSuite(String suiteName) {
		this.suiteName = suiteName;
	}
	/**
	 * Gets the test case list.
	 * 
	 * @return the testCaseList
	 */
	public List<XmlTestCase> getTestCaseList() {
		final List<XmlTestCase> retVal = testCaseList;
		if (null == retVal) {
			throw new IllegalStateException("testCaseList is not correctly populated");
			
		} else {
			return retVal;
		}
	}

	/**
	 * Sets the test case list.
	 * 
	 * @param testCaseList
	 *            the testCaseList to set
	 */
	public void setTestCaseList(final List<XmlTestCase> testCaseList) {
		this.testCaseList = testCaseList;
	}

	/**
	 * Gets the suite name.
	 * 
	 * @return the suite name
	 */
	public String getSuiteName() {
		return suiteName;
	}

	/**
	 * Sets the suite name.
	 * 
	 * @param suiteName
	 *            the new suite name
	 */
	public void setSuiteName(final String suiteName) {
		this.suiteName = suiteName;
	}

	
}
