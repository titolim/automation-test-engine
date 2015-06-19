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
package org.bigtester.ate.model.asserter;


import org.bigtester.ate.annotation.RepeatStepRefreshable;
import org.bigtester.ate.model.data.IStepERValue;
import org.bigtester.ate.model.page.page.IPageObject;

// TODO: Auto-generated Javadoc
/**
 * This class IExpectedResultAsserter defines ....
 * @author Peidong Hu
 *
 */
public interface IExpectedResultAsserter {
	
	/**
	 * Assert e r.
	 */
	void assertER();
	
	/**
	 * Gets the result page.
	 *
	 * @return the result page
	 */
	IPageObject getResultPage();
	
	/**
	 * Gets the step er value.
	 *
	 * @return the step er value
	 */
	@RepeatStepRefreshable
	IStepERValue getStepERValue();
	
	/**
	 * Gets the exec result.
	 *
	 * @return the exec result
	 */
	IStepExecutionResult getExecResult();
	
	/**
	 * Gets the assert report msg.
	 *
	 * @return the assert report msg
	 */
	String getAssertReportMSG();
}
