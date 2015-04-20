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

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.constant.XsdElementConstants;
import org.bigtester.ate.model.casestep.RepeatStep;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

// TODO: Auto-generated Javadoc
/**
 * This class SimpleDateFormatBeanDefinitionParser defines ....
 * 
 * @author Peidong Hu
 *
 */
public class RepeatStepBeanDefinitionParser extends
		BaseTestStepBeanDefinitionParser {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected AbstractBeanDefinition parseInternal(@Nullable Element element,
			@Nullable ParserContext parserContext) {
		// Here we parse the Spring elements such as < property>
		if (parserContext == null || element == null)
			throw GlobalUtils
					.createNotInitializedException("element and parserContext");
		BeanDefinition bDef = super.parseInternal(element, parserContext);
		bDef.setBeanClassName(RepeatStep.class.getName());
		String startStepname = element
				.getAttribute(XsdElementConstants.ATTR_REPEATSTEP_STARTSTEPNAME);
		if (StringUtils.hasText(startStepname)) {
			bDef.getConstructorArgumentValues().addGenericArgumentValue(
					startStepname);
		}
		String endStepname = element
				.getAttribute(XsdElementConstants.ATTR_REPEATSTEP_ENDSTEPNAME);
		if (StringUtils.hasText(endStepname)) {
			bDef.getConstructorArgumentValues().addGenericArgumentValue(
					endStepname);
		}
		
		
		
		boolean continuef = Boolean
				.parseBoolean(element
						.getAttribute(XsdElementConstants.ATTR_REPEATSTEP_CONTINUEONFAILURE));
		bDef.getPropertyValues().addPropertyValue(
				XsdElementConstants.ATTR_REPEATSTEP_CONTINUEONFAILURE,
				continuef);

		int iter = Integer
				.parseInt(element
						.getAttribute(XsdElementConstants.ATTR_REPEATSTEP_NUMBEROFITERATIONS));
		bDef.getPropertyValues().addPropertyValue(
				XsdElementConstants.ATTR_REPEATSTEP_NUMBEROFITERATIONS, iter);

		boolean asserterSame = Boolean
				.parseBoolean(element
						.getAttribute(XsdElementConstants.ATTR_REPEATSTEP_ASSERTERVALUESREMAINSAME));
		bDef.getPropertyValues().addPropertyValue(
				XsdElementConstants.ATTR_REPEATSTEP_ASSERTERVALUESREMAINSAME,
				asserterSame);
		
		parserContext.getRegistry().registerBeanDefinition(
				element.getAttribute("id"), bDef);
		return (AbstractBeanDefinition) bDef;

	}

}
