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

import ch.qos.logback.classic.Level;

/**
 * The Class LogbackLogMessenger.
 *
 * @author Peidong Hu
 */
public class LogMessage {
	
	/** The messages. */
	final private Map<Level, String> messages = new ConcurrentHashMap<Level, String>(); //NOPMD
	public String getErrorMsg() {
		String retVal = messages.get(Level.ERROR);
		if (null == retVal) retVal = "";
		return retVal;
	}
	public String getWarningMsg() {
		String retVal = messages.get(Level.WARN);
		if (null == retVal) retVal = "";
		return retVal;
	}
	public String getInfoMsg() {
		String retVal = messages.get(Level.INFO);
		if (null == retVal) retVal = "";
		return retVal;
	}
	public String getDebugMsg() {
		String retVal = messages.get(Level.DEBUG);
		if (null == retVal) retVal = "";
		return retVal;
	}
	
	public String getTraceMsg() {
		String retVal = messages.get(Level.TRACE);
		if (null == retVal) retVal = "";
		return retVal;
	}
	public LogMessage(String errorMsg) {
		messages.put(Level.ERROR, errorMsg);
		
	}
	public LogMessage(String errorMsg, String warnMsg) {
		messages.put(Level.ERROR, errorMsg);
		messages.put(Level.WARN, warnMsg);
		
	}
	public LogMessage(String errorMsg, String warnMsg, String infoMsg) {
		messages.put(Level.ERROR, errorMsg);
		messages.put(Level.WARN, warnMsg);
		messages.put(Level.INFO, infoMsg);
		
	}
	public LogMessage(String errorMsg, String warnMsg, String infoMsg, String debugMsg) {
		messages.put(Level.ERROR, errorMsg);
		messages.put(Level.WARN, warnMsg);
		messages.put(Level.INFO, infoMsg);
		messages.put(Level.DEBUG, debugMsg);
	}
	public LogMessage(String errorMsg, String warnMsg, String infoMsg, String debugMsg, String traceMsg) {
		messages.put(Level.ERROR, errorMsg);
		messages.put(Level.WARN, warnMsg);
		messages.put(Level.INFO, infoMsg);
		messages.put(Level.DEBUG, debugMsg);
		messages.put(Level.TRACE, traceMsg);
	}
}
