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

import java.util.List;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.constant.XsdElementConstants;
import org.bigtester.ate.model.casestep.StepTypeService;
import org.bigtester.ate.model.casestep.ICucumberTestStep.CucumberStepType;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;


// TODO: Auto-generated Javadoc
/**
 * This class SimpleDateFormatBeanDefinitionParser defines ....
 * 
 * @author Peidong Hu
 *
 */
public class StepTypeServiceBeanDefinitionParser extends
BaseTestStepBeanDefinitionParser {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected AbstractBeanDefinition parseInternal(@Nullable Element element,
			@Nullable ParserContext parserContext) {
		
		
				if (parserContext==null || element == null ) throw GlobalUtils.createNotInitializedException("element and parserContext");
		
				BeanDefinition bDef = super.parseInternal(element, parserContext);
				bDef.setBeanClassName(StepTypeService.class.getName());
				String cucumberStepType = element.getAttribute(XsdElementConstants.ATTR_CUCUMBER_STEP_TYPE);
		        
		        if (StringUtils.hasText(cucumberStepType)) {
		        	bDef.getPropertyValues().addPropertyValue(XsdElementConstants.ATTR_CUCUMBER_STEP_TYPE, CucumberStepType.valueOf(cucumberStepType));
				}	

				List<Element> testStepElements = (List<Element>) DomUtils
						.getChildElements(element);

				if (testStepElements != null && !testStepElements.isEmpty()) {
					parseTestStepComponents(testStepElements, bDef, parserContext);
				}

				
				
				parserContext.getRegistry().registerBeanDefinition(
						element.getAttribute("id"), bDef);
				return (AbstractBeanDefinition) bDef;
		 
	}


	private static void parseTestStepComponents(List<Element> childElements,
			BeanDefinition beanDef, ParserContext parserContext) {
		ManagedList<BeanDefinition> children = new ManagedList<BeanDefinition>(//NOPMD
				childElements.size());
		for (Element element : childElements) {
			BeanDefinitionParser bep = XsdNameSpaceParserRegistry.getNameSpaceHandlerRegistry().get(element.getLocalName());
			if (null == bep) throw GlobalUtils.createNotInitializedException("Name space handler not registered for this element: " + element.getTagName());
			BeanDefinition tmpBDef = bep.parse(element, parserContext);
			tmpBDef.getPropertyValues().removePropertyValue(tmpBDef.getPropertyValues().getPropertyValue(XsdElementConstants.MEMBER_BASETESTSTEP_TESTCASE));
			children.add(tmpBDef);
			
//			if (element.getTagName() == "ate:" //NOPMD
//					+ XsdElementConstants.ELEMENT_HOMESTEP) {
//				HomeStepBeanDefinitionParser homeStep = new HomeStepBeanDefinitionParser();
//				BeanDefinition tmpBDef = homeStep.parse(element, parserContext);
//				tmpBDef.getPropertyValues().removePropertyValue(tmpBDef.getPropertyValues().getPropertyValue(XsdElementConstants.MEMBER_BASETESTSTEP_TESTCASE));
//				
//				children.add(tmpBDef);
//			} else if (element.getTagName() == "ate:"
//					+ XsdElementConstants.ELEMENT_ELEMENTSTEP) {
//				ElementStepBeanDefinitionParser elementStep = new ElementStepBeanDefinitionParser();
//				BeanDefinition tmpBDef = elementStep.parse(element, parserContext);
//				tmpBDef.getPropertyValues().removePropertyValue(tmpBDef.getPropertyValues().getPropertyValue(XsdElementConstants.MEMBER_BASETESTSTEP_TESTCASE));
//				
//				children.add(tmpBDef);
//			} else if (element.getTagName() == "ate:"
//					+ XsdElementConstants.ELEMENT_LASTSTEP) {
//				LastStepBeanDefinitionParser lastStep = new LastStepBeanDefinitionParser();
//				BeanDefinition tmpBDef = lastStep.parse(element, parserContext);
//				tmpBDef.getPropertyValues().removePropertyValue(tmpBDef.getPropertyValues().getPropertyValue(XsdElementConstants.MEMBER_BASETESTSTEP_TESTCASE));
//				
//				children.add(tmpBDef);
//		
//			}
		}
		beanDef.getPropertyValues().addPropertyValue(
				XsdElementConstants.MEMBER_STEPTYPESERVICE_STEPSET, children);
		
	}
}
