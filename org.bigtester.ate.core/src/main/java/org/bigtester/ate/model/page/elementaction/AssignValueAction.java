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
package org.bigtester.ate.model.page.elementaction;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.annotation.ATELogLevel;
import org.bigtester.ate.annotation.ActionLoggable;
import org.bigtester.ate.model.data.IStepInputData;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

// TODO: Auto-generated Javadoc
/**
 * The Class SendKeysAction defines ....
 * 
 * @author Peidong Hu
 */
public class AssignValueAction extends BaseElementAction implements
		IElementAction, ITestObjectActionImpl {

	/**
	 * The Enum ValueAssignmentMethod.
	 *
	 * @author Peidong Hu
	 */
	public enum ValueAssignmentMethod {

		/** The append. */
		APPEND,
		/** The replace. */
		REPLACE,
		/** The prepend. */
		PREPEND
	}

	/** The append. */
	@Nullable
	private ValueAssignmentMethod assignMethod = ValueAssignmentMethod.REPLACE;

	/**
	 * Instantiates a new assign value action.
	 *
	 * @param myWd
	 *            the my wd
	 * @param dataValue
	 *            the data value
	 */
	public AssignValueAction(IMyWebDriver myWd, IStepInputData dataValue) {
		super(myWd);
		setDataValue(dataValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@ActionLoggable (level=ATELogLevel.INFO)
	public void doAction(final WebElement webElm) {
		IStepInputData inputData = getDataValue();
		if (null == inputData) {
			throw new IllegalStateException(
					"inputDatavalue is not correctly populated.");
		} else {
			// workaround for issue, chrome can't correctly handle slash in
			// send keys
			if (getMyWd().getWebDriverInstance() instanceof JavascriptExecutor) {

				JavascriptExecutor jst = (JavascriptExecutor) getMyWd()// NOPMD
						.getWebDriverInstance();
				if (getAssignMethod() == ValueAssignmentMethod.REPLACE)
					jst.executeScript("arguments[1].value = arguments[0]; ",
							inputData.getStrDataValue(), webElm);
				else if (getAssignMethod() == ValueAssignmentMethod.APPEND)
					jst.executeScript(
							"arguments[1].value = arguments[1].value + arguments[0]; ",
							inputData.getStrDataValue(), webElm);
				else if (getAssignMethod() == ValueAssignmentMethod.PREPEND)
					jst.executeScript(
							"arguments[1].value = arguments[0] + arguments[1].value; ",
							inputData.getStrDataValue(), webElm);

			} else {
				if (getAssignMethod() == ValueAssignmentMethod.REPLACE)
					webElm.clear();
				else if (getAssignMethod() == ValueAssignmentMethod.APPEND)
					webElm.sendKeys(Keys.CONTROL, Keys.END);
				else if (getAssignMethod() == ValueAssignmentMethod.PREPEND)
					webElm.sendKeys(Keys.CONTROL, Keys.HOME);
				webElm.sendKeys(inputData.getStrDataValue());
			}
		}

	}

	/**
	 * Gets the assign method.
	 *
	 * @return the assignMethod
	 */
	public ValueAssignmentMethod getAssignMethod() {
		final ValueAssignmentMethod assignMethod2 = assignMethod;
		if (assignMethod2 == null) {
			throw GlobalUtils.createNotInitializedException("assign method");
		} else {
			return assignMethod2;
		}
	}

	/**
	 * Sets the assign method.
	 *
	 * @param assignMethod
	 *            the assignMethod to set
	 */
	public void setAssignMethod(ValueAssignmentMethod assignMethod) {
		this.assignMethod = assignMethod;
	}

}
