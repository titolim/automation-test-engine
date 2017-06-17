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
package org.bigtester.ate.resultmaker;


import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.model.casestep.BaseTestStep;
import org.bigtester.ate.model.casestep.ITestStep;
import org.bigtester.ate.model.testresult.TestStepResult;
import org.testng.ITestResult;
import org.testng.Reporter;

//import com.rits.cloning.Cloner;

// TODO: Auto-generated Javadoc
/*
 * @Aspect tells the Spring framework that this class contains advice that should
 * be applied to one or more specified Pointcuts at runtime
 */
/**
 * The Class TestStepResult defines ....
 * 
 * @author Peidong Hu
 */
@Aspect
public class StepResultMaker {

	/** The Step result list constant. */
	public static final String STEPRESULTLIST = "StepResultList";

	/**
	 * Log.
	 * 
	 * @param joinPoint_p
	 *            the join point_p
	 */

	@SuppressWarnings("unchecked")
	@After("@annotation(org.bigtester.ate.annotation.StepLoggable)")
	public void reportStepResult(final JoinPoint joinPoint_p) {
		
//		XStream xstream = new XStream();
//		xstream.autodetectAnnotations(true);
//		String xml = xstream.toXML((BaseTestStep) joinPoint_p
//				.getTarget());
//		Object bts = xstream.fromXML(xml);
		Object bts = joinPoint_p
				.getTarget();
		 if (bts == null) throw
				 GlobalUtils.createInternalError("stepresultmaker log function.");
		TestStepResult tsr = new TestStepResult(
				((BaseTestStep) joinPoint_p.getTarget()).getStepName(), (ITestStep) bts);

		ITestResult testResult = Reporter.getCurrentTestResult();
		List<TestStepResult> stepResultList;
		try {
			if (testResult.getAttribute(TestStepResult.STEPRESULTLIST) instanceof List<?>) {
				stepResultList = (List<TestStepResult>) testResult // NOPMD
						.getAttribute(TestStepResult.STEPRESULTLIST);
			} else {
				stepResultList = new ArrayList<TestStepResult>();// NOPMD
			}
		} catch (final ClassCastException e) {

			stepResultList = new ArrayList<TestStepResult>();
		}

		stepResultList.add(tsr);
		testResult.setAttribute(TestStepResult.STEPRESULTLIST, stepResultList);
		
	}

}