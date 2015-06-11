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
package org.bigtester.ate.model.page.exception;

import java.util.List;

import org.bigtester.ate.model.BaseATECaseExecE;
import org.bigtester.ate.model.IATECaseExecException;
import org.bigtester.ate.model.asserter.IExpectedResultAsserter;
import org.bigtester.ate.model.casestep.ITestStep;
import org.bigtester.ate.model.casestep.TestCase;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.page.elementfind.IElementFind;
import org.bigtester.ate.systemlogger.IATEProblemCreator;
import org.bigtester.ate.systemlogger.LogMessage;
import org.bigtester.ate.systemlogger.problemhandler.IProblemLogMessenger;
import org.bigtester.ate.systemlogger.problems.GenericATEProblem;
import org.bigtester.ate.systemlogger.problems.IATECaseExecProblem;
import org.bigtester.ate.systemlogger.problems.IATEProblem;
import org.eclipse.jdt.annotation.Nullable;

// TODO: Auto-generated Javadoc
/**
 * This class PageValidationException defines ....
 * 
 * @author Peidong Hu
 *
 */
public class PageValidationException extends BaseATECaseExecE implements IATEProblemCreator{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7144577815429959503L;

	/** The page property. */
	@Nullable
	private transient String pageProperty;
	/** The element find. */
	@Nullable
	private transient IElementFind elementFind;

	/** The list asserters. */
	@Nullable
	private List<IExpectedResultAsserter> listAsserters;

		
	/**
	 * Gets the list asserters.
	 *
	 * @return the listAsserters
	 */
	@Nullable
	public List<IExpectedResultAsserter> getListAsserters() {
		return listAsserters;
	}

	/**
	 * Sets the list asserters.
	 *
	 * @param listAsserters            the listAsserters to set
	 */
	public void setListAsserters(List<IExpectedResultAsserter> listAsserters) {
		this.listAsserters = listAsserters;
	}

	/**
	 * Gets the page property.
	 *
	 * @return the pageProperty
	 */
	@Nullable
	public String getPageProperty() {
		return pageProperty;
	}

	/**
	 * Instantiates a new page validation exception2.
	 *
	 * @param message
	 *            the message
	 * @param errorCode
	 *            the error code
	 * @param pageProperty
	 *            the page property
	 * @param myWebDriver
	 *            the my web driver
	 * @param currentTestCase
	 *            the current test case
	 */
	public PageValidationException(String message, String errorCode,
			String pageProperty, IMyWebDriver myWebDriver,
			TestCase currentTestCase) {
		super(message, errorCode, currentTestCase, myWebDriver);
		this.pageProperty = pageProperty;

	}

	/**
	 * Instantiates a new page validation exception2.
	 *
	 * @param message
	 *            the message
	 * @param errorCode
	 *            the error code
	 * @param eFind
	 *            the e find
	 * @param myWebDriver
	 *            the my web driver
	 * @param currentTestCase
	 *            the current test case
	 */
	public PageValidationException(String message, String errorCode,
			IElementFind eFind, IMyWebDriver myWebDriver,
			TestCase currentTestCase) {
		super(message, errorCode, currentTestCase, myWebDriver);
		elementFind = eFind;

	}

	/**
	 * Instantiates a new page validation exception2.
	 *
	 * @param message
	 *            the message
	 * @param errorCode
	 *            the error code
	 * @param listAsserters
	 *            the list asserters
	 * @param myWebDriver
	 *            the my web driver
	 * @param currentTestCase
	 *            the current test case
	 */
	public PageValidationException(String message, String errorCode,
			List<IExpectedResultAsserter> listAsserters,
			IMyWebDriver myWebDriver, TestCase currentTestCase) {
		super(message, errorCode, currentTestCase, myWebDriver);
		this.listAsserters = listAsserters;

	}

	

	/**
	 * The Class PageValidationProblem.
	 *
	 * @author Peidong Hu
	 */
	public class PageValidationProblem extends GenericATEProblem implements IATECaseExecProblem, IProblemLogMessenger {

		/** The test data exception. */
		private final transient PageValidationException pageValException;

		/**
		 * Instantiates a new page validation problem.
		 *
		 * @param source            the source
		 * @param pageValException the page val exception
		 */
		public PageValidationProblem(Object source, PageValidationException pageValException) {
			super(source, pageValException);
			this.pageValException = pageValException;
		}

		/**
		 * Gets the step exec exception.
		 * 
		 * @return the step exec exception
		 */
		public PageValidationException getStepExecException() {
			return pageValException;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public TestCase getCurrentTestCase() {
			return this.pageValException.getCurrentTestCase();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public ITestStep getCurrentTestStep() {
			return this.pageValException.getCurrentTestCase().getCurrentTestStep();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getProblemMessage() {
			String tmpStr = getATECaseExecException().getMessage();
			if (null == tmpStr) return "exception message is not populated."; //NOPMD
			else return tmpStr;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public IATECaseExecException getATECaseExecException() {
			return this.getStepExecException();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getErrorCode() {
			return this.getStepExecException().getErrorCode();
		}

		/**
		* {@inheritDoc}
		*/
		@Override
		public LogMessage getLogMessage() {
			String errorMsg = "";//NOPMD
			String warnMsg = "";//NOPMD
			if (isFatalProblem()) {
				errorMsg = "This throwable " + this.getClass().getCanonicalName() +"  is fatal for test case: " 
						+ this.getCurrentTestCase().getTestCaseName() 
						+ " in step: " + this.getCurrentTestStep().getStepName();
				
			} else {
				warnMsg = "This step producing throwable  " + this.getClass().getCanonicalName() +"  is optional for test case: " 
						+ this.getCurrentTestCase().getTestCaseName() 
						+ " in step: " + this.getCurrentTestStep().getStepName();
			}
			
			return new LogMessage(errorMsg, warnMsg);
		}
	}

	
	/**
	 * Gets the element find.
	 *
	 * @return the elementFind
	 */
	@Nullable
	public IElementFind getElementFind() {
		return elementFind;
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public IATEProblem initAteProblemInstance(Object ateProblemLocatin) {
		PageValidationProblem retVal = (PageValidationProblem) ateProblem;
		if (null == retVal) {
			retVal = new PageValidationProblem(ateProblemLocatin, this);
			ateProblem = retVal;
		}
		return retVal;
	}

}
