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
package org.bigtester.ate.model.data.dbtable;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.eclipse.jdt.annotation.Nullable;


// TODO: Auto-generated Javadoc
/**
 * This class AbstractTestDataTable defines ....
 * @author Peidong Hu
 *
 */
@MappedSuperclass
public class BaseTestDataTable extends BaseDBTable{

	@ManyToOne
	@JoinColumn(name = "testDataContext_idColumn", referencedColumnName = "idColumn")
	@Nullable
	private TestDataContext testDataContext; //NOPMD

//	@Column
//	private String firstTimeExecution;
	/**
	 * @return the testDataContext
	 */
	public TestDataContext getTestDataContext() {
		final TestDataContext testDataContext2 = testDataContext;
		if (null == testDataContext2 ) {
			throw new IllegalStateException("test data context is not correctly populated in xml db file");
		} else {
			return testDataContext2;
		}
	}

	/**
	 * @param testDataContext the testDataContext to set
	 */
	public void setTestDataContext(TestDataContext testDataContext) {
		this.testDataContext = testDataContext;
	}

//	/**
//	 * @return the firstTimeExecution
//	 */
//	
//	public String getFirstTimeExecution() {
//		return firstTimeExecution;
//	}
//
//	/**
//	 * @param firstTimeExecution the firstTimeExecution to set
//	 */
//	
//	public void setFirstTimeExecution(String firstTimeExecution) {
//		this.firstTimeExecution = firstTimeExecution;
//	}
}
