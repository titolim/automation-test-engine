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

import org.bigtester.ate.model.IATEException;
import org.eclipse.jdt.annotation.Nullable;

import ch.qos.logback.classic.Level;

// TODO: Auto-generated Javadoc
/**
 * This class IATECaseExecProblem defines ....
 * @author Peidong Hu
 *
 */
public interface IATEProblem {
	
	/**
	 * Gets the problem message.
	 *
	 * @return the problem message
	 */
	@Nullable
	String getProblemMessage();
	
	/**
	 * Checks if is fatal problem.
	 *
	 * @return true, if is fatal problem
	 */
	boolean isFatalProblem();
	
	/**
	 * Gets the logging level.
	 *
	 * @return the logging level
	 */
	Level getLoggingLevel();
	
	/**
	 * Sets the logging level.
	 *
	 * @param loggingLevel the new logging level
	 */
	void setLoggingLevel(Level loggingLevel);
	
	/**
	 * Sets the fatal problem.
	 *
	 * @param fatal the new fatal problem
	 */
	void setFatalProblem(boolean fatal);
	
	/**
	 * Gets the ate exception.
	 *
	 * @return the ate exception
	 */
	@Nullable
	IATEException getAteException();
	
	/**
	 * Sets the ate exception.
	 *
	 * @param ateE the new ate exception
	 */
	void setAteException(IATEException ateE);
	
	/**
	 * Gets the step index jump to.
	 *
	 * @return the step index jump to
	 */
	int getStepIndexSkipTo();
	
	/**
	 * Sets the step index jump to.
	 *
	 * @param stepIndex the new step index jump to
	 */
	void setStepIndexSkipTo(int stepIndex);
}
