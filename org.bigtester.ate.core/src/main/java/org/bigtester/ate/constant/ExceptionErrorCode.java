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
package org.bigtester.ate.constant;

// TODO: Auto-generated Javadoc
/**
 * This class ExceptionErrorCode defines ....
 * @author Peidong Hu
 *
 */
public final class ExceptionErrorCode {
	/** The Constant UNKNOWN_ERROR. */
	public static final String UNKNOWN_ERROR = "0000";
	
	/** The Constant WEBELEMENT_NOTFOUND. */
	public static final String WEBELEMENT_NOTFOUND = "0001";
	
	/** The Constant TESTDATA_NOTFOUND. */
	public static final String TESTDATA_NOTFOUND = "0002";
	
	/** The Constant PAGEPROPERTY_INCORRECT. */
	public static final String PAGEPROPERTY_INCORRECT = "0003";
	
	/** The Constant PAGEVALIDATION_HIGH. */
	public static final String PAGEVALIDATION_HIGH = "0004";
	
	/** The Constant RUNTIMEDATA_NOTFOUND. */
	public static final String RUNTIMEDATA_NOTFOUND = "0005";
	
	/** The Constant Repeat TESTDATA_NOTFOUND. */
	public static final String REPEATTESTDATA_NOTFOUND = "0006";
	
	/** The Constant REPEATTESTDATA_DUPLICATED. */
	public static final String REPEATTESTDATA_DUPLICATED = "0007";
	
	/** The Constant WINDOWFRAME_REFRESH. */
	public static final String WINDOWFRAME_REFRESH = "0008";
	
	/**
	 * Instantiates a new exception error code.
	 */
	private ExceptionErrorCode(){
	    throw new AssertionError();
	  }
}
