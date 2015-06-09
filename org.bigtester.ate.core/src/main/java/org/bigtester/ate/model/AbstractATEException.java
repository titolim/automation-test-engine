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
package org.bigtester.ate.model;

import org.bigtester.ate.systemlogger.problems.GenericATEProblem;
import org.eclipse.jdt.annotation.Nullable;

// TODO: Auto-generated Javadoc
/**
 * This class Abstract defines ....
 * 
 * @author Peidong Hu
 * 
 */
public abstract class AbstractATEException extends Exception {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5513011387447019438L;
	/** The error code. */
	private final String errorCode;

	/** The ate problem. */
	@Nullable
	protected GenericATEProblem ateProblem; 
		
	/**
	 * Gets the error code.
	 * 
	 * @return the error code
	 */
	public String getErrorCode() {
		return this.errorCode;
	}

	/**
	 * Instantiates a new abstract ate exception.
	 *
	 * @param message the message
	 * @param errorCode the error code
	 */
	public AbstractATEException(final String message, final String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	
	/**
	 * Instantiates a new abstract ate exception.
	 *
	 * @param message the message
	 * @param errorCode the error code
	 */
	public AbstractATEException(final String message, final String errorCode, Throwable thr) {
		super(message, thr);
		this.errorCode = errorCode;
	}
	
}
