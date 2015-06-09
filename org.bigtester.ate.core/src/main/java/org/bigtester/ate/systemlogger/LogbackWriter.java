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
package org.bigtester.ate.systemlogger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.constant.ExceptionMessage;
import org.bigtester.ate.constant.LogbackTag;
import org.eclipse.jdt.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;

// TODO: Auto-generated Javadoc
/**
 * The Class LogbackWriter defines ....
 *
 * @author Peidong Hu
 */
public final class LogbackWriter {

	/** The Constant MYLOGGER. */
	@Nullable
	private static Logger myLogger = LoggerFactory
			.getLogger(LogbackWriter.class);

	/**
	 * Prints the stack trace.
	 *
	 * @param thr the th
	 */
	public static void printStackTrace(Throwable thr) {
		final Logger mylogger2 = myLogger;
		if (mylogger2 == null) {
			throw GlobalUtils.createNotInitializedException("MYLOGGER"); //NOPMD
		} else {
			if (mylogger2.isErrorEnabled()) {
				mylogger2.error("Uncaught error!", thr);
			} else {
				throw new UnsupportedOperationException(
						ExceptionMessage.MSG_UNSUPPORTED_LOGBACK_LEVEL
								+ "MYLOGGER.isErrorEnabled()");
			}
			
		}
	}
	
	
	
	public static void writeLogbackAppLog(LogMessage logMessenger, Class<?> classProducingError) {
		final Logger logger = LoggerFactory.getLogger(classProducingError);
		if (null == logger) {
			throw GlobalUtils.createNotInitializedException("logback logger");
		}
		if (!logMessenger.getErrorMsg().equals(""))
			logger.error(LogbackTag.TAG_APP_LOG
					+ LogbackTag.TAG_TEST_ERROR +logMessenger.getErrorMsg());
		if (!logMessenger.getWarningMsg().equals(""))
			logger.warn(LogbackTag.TAG_APP_LOG + LogbackTag.TAG_TEST_WARNING + logMessenger.getWarningMsg());
		if (!logMessenger.getInfoMsg().equals(""))
			logger.info(LogbackTag.TAG_APP_LOG + LogbackTag.TAG_TEST_INFO +logMessenger.getInfoMsg());
		if (!logMessenger.getDebugMsg().equals(""))
			logger.debug(logMessenger.getDebugMsg());
		if (!logMessenger.getTraceMsg().equals(""))
			logger.trace(logMessenger.getTraceMsg());
	}
	
	public static void writeLogbackAppLog(LogMessage logMessenger, Class<?> classProducingError, Throwable error) {
		final Logger logger = LoggerFactory.getLogger(classProducingError);
		if (null == logger) {
			throw GlobalUtils.createNotInitializedException("logback logger");
		}
		if (!logMessenger.getErrorMsg().equals(""))
			logger.error(LogbackTag.TAG_APP_LOG
					+ LogbackTag.TAG_TEST_ERROR +logMessenger.getErrorMsg(), error);
		if (!logMessenger.getWarningMsg().equals(""))
			logger.warn(LogbackTag.TAG_APP_LOG + LogbackTag.TAG_TEST_WARNING + logMessenger.getWarningMsg(), error);
		if (!logMessenger.getInfoMsg().equals(""))
			logger.info(LogbackTag.TAG_APP_LOG + LogbackTag.TAG_TEST_INFO +logMessenger.getInfoMsg(), error);
		if (!logMessenger.getDebugMsg().equals(""))
			logger.debug(logMessenger.getDebugMsg(), error);
		if (!logMessenger.getTraceMsg().equals(""))
			logger.trace(logMessenger.getTraceMsg(), error);
	}
	
	/**
	 * Write app error.
	 *
	 * @param msg the msg
	 * @param classProducingError the class producing error
	 */
	public static void writeAppError(String msg, Class<?> classProducingError) {
		final Logger logger = LoggerFactory.getLogger(classProducingError);
		if (null == logger) {
			throw GlobalUtils.createNotInitializedException("logback logger");
		}
		if (logger.isErrorEnabled()) {
			logger.error(msg);
		}
	}
	
	
	
	/**
	 * Write app error.
	 *
	 * @param msg
	 *            the msg
	 */
	public static void writeAppError(String msg) {

		final Logger mylogger2 = myLogger;
		if (mylogger2 == null) {
			throw GlobalUtils.createNotInitializedException("MYLOGGER"); //NOPMD
		} else {
			if (mylogger2.isErrorEnabled()) {
				mylogger2.error(LogbackTag.TAG_APP_LOG
						+ LogbackTag.TAG_TEST_ERROR + msg);
			} else {
				throw new UnsupportedOperationException(
						ExceptionMessage.MSG_UNSUPPORTED_LOGBACK_LEVEL
								+ "MYLOGGER.isErrorEnabled()");
			}
			
		}

	}

	/**
	 * Write app error.
	 *
	 * @param msg
	 *            the msg
	 */
	public static void writeSysError(String msg) {
		final Logger mylogger2 = myLogger;
		if (mylogger2 == null) {
			throw GlobalUtils.createNotInitializedException("MYLOGGER");
		} else {
			if (mylogger2.isErrorEnabled()) {
				mylogger2.error(LogbackTag.TAG_SYS_LOG + LogbackTag.TAG_SYS_ERROR
						+ msg);
			} else {
				throw new UnsupportedOperationException(
						ExceptionMessage.MSG_UNSUPPORTED_LOGBACK_LEVEL
								+ "MYLOGGER.isErrorEnabled()");
			}
		}
	}

	/**
	 * Write app warning.
	 *
	 * @param msg
	 *            the msg
	 */
	public static void writeAppWarning(String msg) {
		final Logger mylogger2 = myLogger;
		if (mylogger2 == null) {
			throw GlobalUtils.createNotInitializedException("MYLOGGER");
		} else {
			if (mylogger2.isWarnEnabled()) {
				mylogger2.warn(LogbackTag.TAG_APP_LOG + LogbackTag.TAG_TEST_WARNING
						+ msg);
			} else {
				throw new UnsupportedOperationException(
						ExceptionMessage.MSG_UNSUPPORTED_LOGBACK_LEVEL
								+ "MYLOGGER.isWarnEnabled()");
			}
		}
	}

	/**
	 * Write app info.
	 *
	 * @param msg
	 *            the msg
	 */
	public static void writeAppInfo(String msg) {
		final Logger mylogger2 = myLogger;
		if (mylogger2 == null) {
			throw GlobalUtils.createNotInitializedException("MYLOGGER");
		} else {
			if (mylogger2.isInfoEnabled()) {
				mylogger2.info(LogbackTag.TAG_APP_LOG + LogbackTag.TAG_TEST_INFO + msg); // NOPMD
			} else {
				throw new UnsupportedOperationException(
						ExceptionMessage.MSG_UNSUPPORTED_LOGBACK_LEVEL
								+ "MYLOGGER.isInfoEnabled()");
			}
		}
	}

	/**
	 * Write test info.
	 *
	 * @param msg
	 *            the msg
	 */
	public static void writeUnitTestInfo(String msg) {
		final Logger mylogger2 = myLogger;
		if (mylogger2 == null) {
			throw GlobalUtils.createNotInitializedException("MYLOGGER");
		} else {
			
			if (mylogger2.isInfoEnabled()) {
				mylogger2.info(LogbackTag.TAG_APP_LOG + LogbackTag.TAG_UNITTEST_INFO + msg); // NOPMD
			} else {
				throw new UnsupportedOperationException(
						ExceptionMessage.MSG_UNSUPPORTED_LOGBACK_LEVEL
								+ "MYLOGGER.isInfoEnabled()");
			}
		}
	}

	/**
	 * Instantiates a new logback writer.
	 */
	private LogbackWriter() {
		throw new AssertionError();
	}
}
