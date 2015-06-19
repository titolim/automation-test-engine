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
package org.bigtester.ate.reporter;

import java.util.List;
import java.util.Properties;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.constant.ReportMessage;
import org.bigtester.ate.model.asserter.IExpectedResultAsserter;
import org.bigtester.ate.model.casestep.IElementStep;
import org.bigtester.ate.model.page.elementaction.ITestObjectAction;
import org.bigtester.ate.model.page.elementfind.ITestObjectFinder;
import org.bigtester.ate.model.page.page.MyWebElement;
import org.bigtester.ate.model.testresult.TestStepResult;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.reporters.XMLStringBuffer;

// TODO: Auto-generated Javadoc
/**
 * This class TestStepsXMLReporter defines ....
 * 
 * @author Peidong Hu
 * 
 */
public final class TestStepsXMLReporterUtils {

	private TestStepsXMLReporterUtils() {

	}

	/**
	 * Adds the test steps.
	 * 
	 * @param xmlBuffer
	 *            the xml buffer
	 * @param testResult
	 *            the test result
	 */
	public static void addTestSteps(XMLStringBuffer xmlBuffer,
			ITestResult testResult) {

		if (testResult.getAttribute(TestStepResult.STEPRESULTLIST) instanceof List<?>) {
			@SuppressWarnings("unchecked")
			List<TestStepResult> stepResults = (List<TestStepResult>) testResult
					.getAttribute(TestStepResult.STEPRESULTLIST);
			if (!stepResults.isEmpty()) {
				xmlBuffer.push(ATEXMLReporterConfig.TAG_STEPS);
				for (int i = 0; i < stepResults.size(); i++) {
					addStep(xmlBuffer, stepResults.get(i), i);
				}
				xmlBuffer.pop();
			}
		}
	}

	/**
	 * Adds the step.
	 * 
	 * @param xmlBuffer
	 *            the xml buffer
	 * @param tsr
	 *            the parameter
	 * @param iCount
	 *            the i
	 */
	private static void addStep(XMLStringBuffer xmlBuffer, TestStepResult tsr,
			int iCount) {
		Properties attrs = new Properties();
		attrs.setProperty(ATEXMLReporterConfig.ATTR_INDEX,
				String.valueOf(iCount));
		attrs.setProperty(ATEXMLReporterConfig.ATTR_NAME, tsr.getStepName());
		xmlBuffer.push(ATEXMLReporterConfig.TAG_STEP, attrs);
		xmlBuffer.push(ATEXMLReporterConfig.TAG_STEP_DESC);
		
		String stepReportMSG;
		if (tsr.getThisStep() instanceof IElementStep) {
			
			@SuppressWarnings("unchecked")
			MyWebElement<WebElement> mwe = (MyWebElement<WebElement>) ((IElementStep) tsr.getThisStep()).getMyWebElement();
			ITestObjectAction<?> myEA = (ITestObjectAction<?>) mwe.getTestObjectAction();
			if (null == myEA) {
				throw GlobalUtils
						.createNotInitializedException("test object action in test step.");
			} else {
				ITestObjectFinder<?> myEF = (ITestObjectFinder<?>) mwe.getTestObjectFinder();
				
				stepReportMSG = tsr.getThisStep().getStepDescription()
						+ ReportMessage.MSG_SEPERATOR
						+ myEA.getClass().getName() + " : \""
						+ myEA.getActionParametersLoggingValue()
						+ "\" action on test object:"
						+ myEF.getClass().getName() + ": \""
						+ myEF.getFindingParametersLoggingValue() + "\"";
			}

		} else {
			stepReportMSG = tsr.getThisStep().getStepDescription();
		}
		xmlBuffer.addCDATA(stepReportMSG);
		xmlBuffer.pop();
		xmlBuffer.push(ATEXMLReporterConfig.TAG_STEP_RESULT);
		List<IExpectedResultAsserter> asserters = tsr.getThisStep().getExpectedResultAsserter();
		if (!asserters.isEmpty()) {
			StringBuffer stepResultMSG = new StringBuffer("");
			for (int i = 0; i < asserters
					.size(); i++) {
				stepResultMSG.append(asserters.get(i).getAssertReportMSG());
				stepResultMSG.append("</br>");
//				StepExecutionResult ser = tsr.getThisStep()
//						.getExpectedResultAsserter().get(i).getExecResult();
//				StepExpectedResultValue serv = ser.getStepExpectedResultValue();
//				if (serv != null) {
//					for (int index = 0; index < serv.getValue().size(); index++) {
//						if (ser.getActualResult().getResultSet()
//								.get(serv.getValue().get(index).getIdColumn()) != null) {
//							stepResultMSG.append(serv.getValue().get(index)
//									.getTestDataContext()
//									.getContextFieldValue());
//							stepResultMSG.append(ReportMessage.MSG_SEPERATOR
//									+ serv.getValue().get(index)
//											.getAssertPriority());
//							stepResultMSG.append(ReportMessage.MSG_SEPERATOR
//									+ serv.getValue().get(index)
//											.getElementFindBy());
//							stepResultMSG.append(ReportMessage.MSG_SEPERATOR
//									+ serv.getValue().get(index)
//											.getElementFindByValue());
//							stepResultMSG.append(ReportMessage.MSG_SEPERATOR
//									+ serv.getValue().get(index)
//											.getAssertProperty());
//							stepResultMSG.append(ReportMessage.MSG_SEPERATOR
//									+ serv.getValue().get(index)
//											.getAssertValue());
//							stepResultMSG.append(ReportMessage.MSG_SEPERATOR
//									+ ReportMessage.MSG_SEPERATOR
//									+ ser.getActualResult()
//											.getResultSet()
//											.get(serv.getValue().get(index)
//													.getIdColumn()));
//							stepResultMSG.append(ReportMessage.MSG_SEPERATOR
//									+ ReportMessage.MSG_SEPERATOR
//									+ ser.getComparedResult()
//											.get(serv.getValue().get(index)
//													.getIdColumn()).toString());
//							stepResultMSG.append('\n');
//						}
//					}
//					stepResultMSG.append('\n');
//				}
			}
			xmlBuffer.addCDATA(stepResultMSG.toString());
		}
		xmlBuffer.pop();
		xmlBuffer.pop();
	}

}
