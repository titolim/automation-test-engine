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
import org.bigtester.ate.model.casestep.HomeStep;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
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
public class HomeStepBeanDefinitionParser extends
	BaseTestStepBeanDefinitionParser {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected AbstractBeanDefinition parseInternal(@Nullable Element element,
			@Nullable ParserContext parserContext) {
		// Here we parse the Spring elements such as < property>
		if (parserContext==null || element == null ) throw GlobalUtils.createNotInitializedException("element and parserContext");
		// Here we parse the Spring elements such as < property>
//        BeanDefinitionHolder holder = parserContext.getDelegate().parseBeanDefinitionElement(element);
//        BeanDefinition bDef = holder.getBeanDefinition();
        BeanDefinition bDef = super.parseInternal(element, parserContext);
        bDef.setBeanClassName(HomeStep.class.getName());
        String pageObject = element
				.getAttribute(XsdElementConstants.ATTR_BASETESTSTEP_PAGEOBJECT);
		if (StringUtils.hasText(pageObject)) {
			bDef.getConstructorArgumentValues().addGenericArgumentValue(
					new RuntimeBeanReference(pageObject));
		}
//		String myWE = element
//				.getAttribute(XsdElementConstants.ATTR_ELEMENTSTEP_MYWEBELEMENT);
//		if (StringUtils.hasText(myWE)) {
//			bDef.getConstructorArgumentValues().addGenericArgumentValue(
//					new RuntimeBeanReference(myWE));
//		}
//        boolean target = Boolean.parseBoolean(element.getAttribute(XsdElementConstants.ATTR_TESTSTEP_TARGETSTEP));
//        bDef.getPropertyValues().addPropertyValue(XsdElementConstants.ATTR_TESTSTEP_TARGETSTEP, target);
//        
//        String stepName = element.getAttribute(XsdElementConstants.ATTR_TESTSTEP_STEPNAME);
//        bDef.getPropertyValues().addPropertyValue(XsdElementConstants.ATTR_TESTSTEP_STEPNAME, stepName);
//        
//        String stepDesc = element.getAttribute(XsdElementConstants.ATTR_TESTSTEP_STEPDESCRIPTION);
//        bDef.getPropertyValues().addPropertyValue(XsdElementConstants.ATTR_TESTSTEP_STEPDESCRIPTION, stepDesc);
//        
        parserContext.getRegistry().registerBeanDefinition(element.getAttribute("id"), bDef);
        return (AbstractBeanDefinition) bDef;
        
	}
	

}
