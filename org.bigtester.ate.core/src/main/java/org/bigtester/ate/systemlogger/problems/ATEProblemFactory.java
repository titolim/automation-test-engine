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
package org.bigtester.ate.systemlogger.problems;

import org.bigtester.ate.model.AbstractATEException;
import org.bigtester.ate.model.page.exception.PageValidationException2;
import org.bigtester.ate.model.page.exception.StepExecutionException;
import org.bigtester.problomatic2.Problem;
import org.eclipse.jdt.annotation.Nullable;

// TODO: Auto-generated Javadoc
/**
 * This class ATEPageFactory defines ....
 * 
 * @author Peidong Hu
 * 
 */
public final class ATEProblemFactory implements IATEProblemFactory {

	//private IPageObject iPageOject;
	//private MyWebElement myWebElement;
	//private IElementFind iElementFind;
	//private IElementAction iElementAction;
	/** The instance. */
	@Nullable
	private static IATEProblemFactory instance;

	private ATEProblemFactory() {
	}

	/**
	 * Gets the single instance of ATEPageFactory.
	 * 
	 * @return single instance of ATEPageFactory
	 */
	public static synchronized IATEProblemFactory getInstance() { //NOPMD

		 
		IATEProblemFactory instance2 = instance;
		if (instance2 == null) {
			instance = new ATEProblemFactory();
			instance2 = instance;
			return instance2; //NOPMD
		} else {
			return instance2;
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Problem getATEProblem(Object source,
			AbstractATEException ateException) {
		//TODO add more exception and problem type
		synchronized (this) {
			Problem retVal;
			 if (ateException instanceof PageValidationException2) {
				retVal = new PageValidationProblem2(source,
						(PageValidationException2) ateException);
			} else {
				retVal = new GenericATEProblem(source, ateException);
			}
			return retVal;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IATECaseExecProblem getATECaseExecProblem(Object source,
			AbstractATEException ateException) {
		//TODO add more exception and problem type
		synchronized (this) {
			IATECaseExecProblem retVal;
			  if (ateException instanceof PageValidationException2) {
				retVal = (IATECaseExecProblem) new PageValidationProblem2(source,
						(PageValidationException2) ateException);
			} else {
				retVal = (IATECaseExecProblem) new GenericATEProblem(source, ateException);
			}
			return retVal;
		}
	}

}
