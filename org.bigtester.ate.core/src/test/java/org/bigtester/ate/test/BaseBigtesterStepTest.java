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
import org.bigtester.ate.model.casestep.ElementTestStep;
import org.bigtester.ate.model.casestep.IStepJumpingEnclosedContainer;
import org.bigtester.ate.model.casestep.ITestCase;
import org.bigtester.ate.model.casestep.TestCase;
import org.bigtester.ate.model.data.exception.RuntimeDataException;
import org.bigtester.ate.model.page.exception.PageValidationException;
import org.bigtester.ate.model.page.exception.StepExecutionException;
import org.bigtester.ate.model.page.page.MyWebElement;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

// TODO: Auto-generated Javadoc
/**
 * This class BaseBigtesterStepTest defines ....
 * @author Peidong Hu
 *
 */
abstract public class BaseBigtesterStepTest extends BigtesterProjectTest {
	
	/**
	 * Run element test step.
	 *
	 * @throws NoSuchBeanDefinitionException the no such bean definition exception
	 * @throws StepExecutionException the step execution exception
	 * @throws PageValidationException the page validation exception
	 * @throws RuntimeDataException the runtime data exception
	 */
	public void runElementTestStep() throws NoSuchBeanDefinitionException, StepExecutionException, PageValidationException, RuntimeDataException {
		MyWebElement<?> myWebE = getMyWebElement();
		ElementTestStep ets = new ElementTestStep(myWebE);
		ets.setApplicationContext(getApplicationContext());
		ets.doStep((IStepJumpingEnclosedContainer)GlobalUtils.findTestCaseBean());
	}
	
	/**
	 * Run element test step.
	 *
	 * @throws NoSuchBeanDefinitionException the no such bean definition exception
	 * @throws StepExecutionException the step execution exception
	 * @throws PageValidationException the page validation exception
	 * @throws RuntimeDataException the runtime data exception
	 */
	public void runElementTestStep(@Nullable MyWebElement<?> myWebE) throws NoSuchBeanDefinitionException, StepExecutionException, PageValidationException, RuntimeDataException {
		if (myWebE == null) myWebE = getMyWebElement();
		ElementTestStep ets = new ElementTestStep(myWebE);
		ets.setApplicationContext(getApplicationContext());
		((ITestCase)GlobalUtils.findTestCaseBean(getApplicationContext())).setCurrentTestStep(ets);
		ets.doStep((IStepJumpingEnclosedContainer)GlobalUtils.findTestCaseBean(getApplicationContext()));
	}
	
	/**
	 * Gets the my web element.
	 *
	 * @return the my web element
	 */
	abstract public MyWebElement<?> getMyWebElement();
}
