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

import org.bigtester.ate.GlobalUtils;
import org.bigtester.problomatic2.problems.RawProblem;
import org.testng.internal.Utils;

import ch.qos.logback.classic.Level;

// TODO: Auto-generated Javadoc
/**
 * This class GenericATEProblem defines ....
 * 
 * @author Peidong Hu
 *
 */
public class GenericATEProblem extends RawProblem {
	/** The full stack trace. */
	private final String fullStackTrace;

	/** The short stack trace. */
	private final String shortStackTrace;

	/** The log level. */
	private Level logLevel;
	
	/** The fatal problem. */
	private boolean fatalProblem;
	
	private int stepIndexJumpTo = -1;
	/**
	 * Instantiates a new generic ate problem.
	 *
	 * @param source
	 *            the source
	 * @param see
	 *            the see
	 */
	public GenericATEProblem(Object source, Exception exception) {
		super(source, exception);
		String[] stackTraces = Utils.stackTrace(exception, false);
		String tmp1 = stackTraces[1];
		if (tmp1 == null)
			fullStackTrace = "fullstacktrace Internal error.";

		else
			fullStackTrace = tmp1;
		String tmp0 = stackTraces[0];
		if (tmp0 == null)
			shortStackTrace = "shortstacktrace Internal error.";
		else
			shortStackTrace = tmp0;
		final Level warn2 = Level.WARN;
		if (warn2 == null) {
			throw GlobalUtils.createInternalError("jvm");
		} else {
			this.logLevel = warn2;
		}
		this.fatalProblem = false;
	}

	/**
	 * Instantiates a new generic ate problem.
	 *
	 * @param source
	 *            the source
	 * @param exception
	 *            the exception
	 */
	public GenericATEProblem(Object source, Throwable exception) {
		super(source, exception);
		String[] stackTraces = Utils.stackTrace(exception, false);
		String tmp1 = stackTraces[1];
		if (tmp1 == null)
			fullStackTrace = "fullstacktrace Internal error.";

		else
			fullStackTrace = tmp1;
		String tmp0 = stackTraces[0];
		if (tmp0 == null)
			shortStackTrace = "shortstacktrace Internal error.";
		else
			shortStackTrace = tmp0;
		final Level warn2 = Level.WARN;
		if (warn2 == null) {
			throw GlobalUtils.createInternalError("jvm");
		} else {
			this.logLevel = warn2;
		}
		this.fatalProblem = false;
	}

	/**
	 * Gets the short stack trace.
	 *
	 * @return the shortStackTrace
	 */
	public String getShortStackTrace() {
		return shortStackTrace;
	}

	/**
	 * Gets the full stack trace.
	 *
	 * @return the fullStackTrace
	 */
	public String getFullStackTrace() {
		return fullStackTrace;
	}

	/**
	 * @return the logLevel
	 */
	public Level getLogLevel() {
		return logLevel;
	}

	/**
	 * @param logLevel the logLevel to set
	 */
	public void setLogLevel(Level logLevel) {
		this.logLevel = logLevel;
	}

	/**
	 * @return the fatalProblem
	 */
	public boolean isFatalProblem() {
		return fatalProblem;
	}

	/**
	 * @param fatalProblem the fatalProblem to set
	 */
	public void setFatalProblem(boolean fatalProblem) {
		this.fatalProblem = fatalProblem;
	}

	/**
	 * @return the stepIndexJumpTo
	 */
	public int getStepIndexJumpTo() {
		return stepIndexJumpTo;
	}

	/**
	 * @param stepIndexJumpTo the stepIndexJumpTo to set
	 */
	public void setStepIndexJumpTo(int stepIndexJumpTo) {
		this.stepIndexJumpTo = stepIndexJumpTo;
	}
}
