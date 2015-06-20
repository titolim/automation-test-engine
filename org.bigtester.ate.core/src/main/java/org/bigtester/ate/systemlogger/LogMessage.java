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
package org.bigtester.ate.systemlogger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bigtester.ate.annotation.ATELogLevel;

import ch.qos.logback.classic.Level;

/**
 * The Class LogbackLogMessenger.
 *
 * @author Peidong Hu
 */
public class LogMessage {

	/** The messages. */
	final private Map<Level, String> messages = new ConcurrentHashMap<Level, String>(); // NOPMD

	/**
	 * Gets the error msg.
	 *
	 * @return the error msg
	 */
	public String getErrorMsg() {
		String retVal = messages.get(Level.ERROR);
		if (null == retVal)
			retVal = "";
		return retVal;
	}

	/**
	 * Gets the warning msg.
	 *
	 * @return the warning msg
	 */
	public String getWarningMsg() {
		String retVal = messages.get(Level.WARN);
		if (null == retVal)
			retVal = "";
		return retVal;
	}

	/**
	 * Gets the info msg.
	 *
	 * @return the info msg
	 */
	public String getInfoMsg() {
		String retVal = messages.get(Level.INFO);
		if (null == retVal)
			retVal = "";
		return retVal;
	}

	/**
	 * Gets the debug msg.
	 *
	 * @return the debug msg
	 */
	public String getDebugMsg() {
		String retVal = messages.get(Level.DEBUG);
		if (null == retVal)
			retVal = "";
		return retVal;
	}

	/**
	 * Gets the trace msg.
	 *
	 * @return the trace msg
	 */
	public String getTraceMsg() {
		String retVal = messages.get(Level.TRACE);
		if (null == retVal)
			retVal = "";
		return retVal;
	}

	/**
	 * Instantiates a new log message.
	 *
	 * @param errorMsg the error msg
	 */
	public LogMessage(String errorMsg) {
		messages.put(Level.ERROR, errorMsg);

	}

	/**
	 * Instantiates a new log message.
	 *
	 * @param errorMsg the error msg
	 * @param warnMsg the warn msg
	 */
	public LogMessage(String errorMsg, String warnMsg) {
		messages.put(Level.ERROR, errorMsg);
		messages.put(Level.WARN, warnMsg);

	}

	/**
	 * Instantiates a new log message.
	 *
	 * @param errorMsg the error msg
	 * @param warnMsg the warn msg
	 * @param infoMsg the info msg
	 */
	public LogMessage(String errorMsg, String warnMsg, String infoMsg) {
		messages.put(Level.ERROR, errorMsg);
		messages.put(Level.WARN, warnMsg);
		messages.put(Level.INFO, infoMsg);

	}

	/**
	 * Instantiates a new log message.
	 *
	 * @param errorMsg the error msg
	 * @param warnMsg the warn msg
	 * @param infoMsg the info msg
	 * @param debugMsg the debug msg
	 */
	public LogMessage(String errorMsg, String warnMsg, String infoMsg,
			String debugMsg) {
		messages.put(Level.ERROR, errorMsg);
		messages.put(Level.WARN, warnMsg);
		messages.put(Level.INFO, infoMsg);
		messages.put(Level.DEBUG, debugMsg);
	}

	/**
	 * Instantiates a new log message.
	 *
	 * @param errorMsg the error msg
	 * @param warnMsg the warn msg
	 * @param infoMsg the info msg
	 * @param debugMsg the debug msg
	 * @param traceMsg the trace msg
	 */
	public LogMessage(String errorMsg, String warnMsg, String infoMsg,
			String debugMsg, String traceMsg) {
		messages.put(Level.ERROR, errorMsg);
		messages.put(Level.WARN, warnMsg);
		messages.put(Level.INFO, infoMsg);
		messages.put(Level.DEBUG, debugMsg);
		messages.put(Level.TRACE, traceMsg);
	}

	/**
	 * Instantiates a new log message.
	 *
	 * @param msg the msg
	 * @param level the level
	 */
	public LogMessage(String msg, ATELogLevel level) {
		if (level == ATELogLevel.INFO)
			messages.put(Level.INFO, msg);
		if (level == ATELogLevel.DEBUG)
			messages.put(Level.DEBUG, msg);
		if (level == ATELogLevel.TRACE)
			messages.put(Level.TRACE, msg);
	}
}
