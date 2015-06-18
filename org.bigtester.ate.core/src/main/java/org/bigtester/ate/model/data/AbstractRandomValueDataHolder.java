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
package org.bigtester.ate.model.data;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.constant.EnumRunTimeDataType;

// TODO: Auto-generated Javadoc
/**
 * This class ManualAssignedValueDataHolder defines ....
 * 
 * @author Peidong Hu
 *
 */
abstract public class AbstractRandomValueDataHolder extends
		AbstractRunTimeInputDataHolder {

	/**
	 * The Enum Mode.
	 *
	 * @author Peidong Hu
	 */
	protected static enum Mode {

		/** The alpha. */
		ALPHA,
		/** The alphanumeric. */
		ALPHANUMERIC,
		/** The numeric. */
		NUMERIC
	}

	/**
	 * Instantiates a new random value data holder.
	 *
	 * @param dataType
	 *            the data type
	 * @param strRandomValue
	 *            the str random value
	 * @param springBeanID
	 *            the spring bean id
	 */
	public AbstractRandomValueDataHolder(EnumRunTimeDataType dataType,
			String strRandomValue, String springBeanID) {
		super(dataType, strRandomValue, springBeanID);
	}

	/**
	 * Generate random string.
	 *
	 * @param length
	 *            the length
	 * @param mode
	 *            the mode
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	protected static String generateRandomString(int length, Mode mode) {
		if (1 > length) { // NOPMD
			throw GlobalUtils
					.createNotInitializedException("randomString length");
		}

		StringBuffer buffer = new StringBuffer();
		String characters;

		switch (mode) {

		case ALPHA:
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";// NOPMD
			break;

		case ALPHANUMERIC:
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";// NOPMD
			break;

		case NUMERIC:
			characters = "1234567890";// NOPMD
			break;

		default:
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";// NOPMD
			break;
		}

		for (int i = 0; i < length; i++) {
			double index = Math.random() * characters.length();
			buffer.append(characters.charAt((int) index));
		}
		String retVal = buffer.toString();
		if (null == retVal) {
			throw GlobalUtils.createInternalError("buffe.toString");

		} else {
			return retVal;
		}
	}
	
	/**
	 * Generate random email.
	 *
	 * @return the string
	 */
	protected static String generateRandomEmail() {
		//TODO research to use online service to get this email address from site http://www.yopmail.com/en/email-generator.php
		return generateRandomString(15, Mode.ALPHA) + "@hotmail.com";
		
	}
}
