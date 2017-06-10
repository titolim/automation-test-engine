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
package org.bigtester.ate.xmlschema;

import org.apache.commons.lang3.StringUtils;
import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.constant.XsdElementConstants;
import org.bigtester.ate.model.page.atewebdriver.MyChromeDriver;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

// TODO: Auto-generated Javadoc
/**
 * This class SimpleDateFormatBeanDefinitionParser defines ....
 * 
 * @author Jun Yang
 *
 */
public class ChromeDriverBeanDefinitionParser extends
		AbstractSingleBeanDefinitionParser {

	/**
	 * {@inheritDoc}
	 */
	protected Class<MyChromeDriver> getBeanClass(@Nullable Element element) {
		return MyChromeDriver.class;
	}

	/**
	 * {@inheritDoc}
	 */
	protected void doParse(@Nullable Element element,
			@Nullable BeanDefinitionBuilder bean) {
		// this will never be null since the schema explicitly requires that a
		// value be supplied
		if (bean == null || element == null)
			throw GlobalUtils.createNotInitializedException("element and bean");
		String preserveCookies = element
				.getAttribute(XsdElementConstants.ATTR_CHROMEDRIVER_PRESERVECOOKIES);
		if (!StringUtils.isEmpty(preserveCookies)) {
			bean.addConstructorArgValue(Boolean.parseBoolean(preserveCookies));
		}
		String startArguments = element
				.getAttribute(XsdElementConstants.ATTR_CHROMEDRIVER_START_ARGUMENTS);
		if (!StringUtils.isEmpty(startArguments)) {
			bean.addConstructorArgValue(startArguments);
		}
		

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory
	 *      .xml.AbstractBeanDefinitionParser#resolveId(org.w3c.dom.Element,
	 *      org.springframework.beans.factory.support.AbstractBeanDefinition,
	 *      org.springframework.beans.factory.xml.ParserContext)
	 */
	protected String resolveId(@Nullable Element element,
			@Nullable AbstractBeanDefinition definition,
			@Nullable ParserContext parserContext)
			throws BeanDefinitionStoreException {

		return "MyWebDriver2";
	}
}
