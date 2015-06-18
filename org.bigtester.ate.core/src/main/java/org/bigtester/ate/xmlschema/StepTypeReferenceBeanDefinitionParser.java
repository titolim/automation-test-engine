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

import org.apache.commons.lang.StringUtils;
import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.constant.XsdElementConstants; 
import org.eclipse.jdt.annotation.Nullable; 
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;


// TODO: Auto-generated Javadoc
/**
 * This class SimpleDateFormatBeanDefinitionParser defines ....
 * 
 * @author Peidong Hu
 *
 */
public class StepTypeReferenceBeanDefinitionParser extends
		AbstractBeanDefinitionParser {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected AbstractBeanDefinition parseInternal(@Nullable Element element,
			@Nullable ParserContext parserContext) {
		// Here we parse the Spring elements such as < property>
		if (parserContext==null || element == null ) throw GlobalUtils.createNotInitializedException("element and parserContext");
		String stepServiceDefRef = element
			.getAttribute(XsdElementConstants.ATTR_STEPTYPESERVICEREFERENCE_STEPTYPESERVICEDEFINITIONID);
	if (StringUtils.isEmpty(stepServiceDefRef)) {
		throw GlobalUtils
				.createNotInitializedException("STEPTYPESERVICEDEFINITIONID");
	} else {
		AbstractBeanDefinition retVal = (AbstractBeanDefinition) parserContext.getRegistry()
				.getBeanDefinition(stepServiceDefRef);
		if (null == retVal) throw GlobalUtils.createInternalError("spring beandefinition registry");
		return retVal;
	}

        
	}
	

}
