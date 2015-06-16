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
import org.bigtester.ate.constant.GlobalConstants;
import org.bigtester.ate.constant.XsdElementConstants;
import org.bigtester.ate.model.casestep.BaseTestStep;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
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
public class BaseTestStepBeanDefinitionParser extends
		AbstractBeanDefinitionParser {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected AbstractBeanDefinition parseInternal(@Nullable Element element,
			@Nullable ParserContext parserContext) {
		// Here we parse the Spring elements such as < property>
		if (parserContext==null || element == null ) throw GlobalUtils.createNotInitializedException("element and parserContext");
        BeanDefinitionHolder holder = parserContext.getDelegate().parseBeanDefinitionElement(element);
        BeanDefinition bDef = holder.getBeanDefinition();
        bDef.setBeanClassName(BaseTestStep.class.getName());
        String pageObject = element
				.getAttribute(XsdElementConstants.ATTR_BASETESTSTEP_PAGEOBJECT);
		if (StringUtils.hasText(pageObject)) {
			bDef.getPropertyValues().addPropertyValue(
					XsdElementConstants.ATTR_TESTSTEP_PAGEOBJECT, new RuntimeBeanReference(pageObject));
		}
		
				
		boolean target = Boolean.parseBoolean(element
				.getAttribute(XsdElementConstants.ATTR_TESTSTEP_TARGETSTEP));
		bDef.getPropertyValues().addPropertyValue(
				XsdElementConstants.ATTR_TESTSTEP_TARGETSTEP, target);
		
		boolean optional;
		
		if (target) {
			optional = false;
		} else {
			optional = Boolean.parseBoolean(element
					.getAttribute(XsdElementConstants.ATTR_TESTSTEP_OPTIONALSTEP));
			String optionalStepUntilInclusive = element
					.getAttribute(XsdElementConstants.ATTR_TESTSTEP_CORRELATEDOPTIONALSTEPSUTILINCLUSIVE);
			bDef.getPropertyValues().addPropertyValue(
					XsdElementConstants.ATTR_TESTSTEP_CORRELATEDOPTIONALSTEPSUTILINCLUSIVE, optionalStepUntilInclusive);
		}
		bDef.getPropertyValues().addPropertyValue(
				XsdElementConstants.ATTR_TESTSTEP_OPTIONALSTEP, optional);
		
		

		String stepName = element
				.getAttribute(XsdElementConstants.ATTR_TESTSTEP_STEPNAME);
		bDef.getPropertyValues().addPropertyValue(
				XsdElementConstants.ATTR_TESTSTEP_STEPNAME, stepName);

		String stepDesc = element
				.getAttribute(XsdElementConstants.ATTR_TESTSTEP_STEPDESCRIPTION);
		bDef.getPropertyValues().addPropertyValue(
				XsdElementConstants.ATTR_TESTSTEP_STEPDESCRIPTION, stepDesc);
		
		bDef.getPropertyValues().addPropertyValue("repeatStepLogger",
				new RuntimeBeanReference(GlobalConstants.BEAN_ID_REPEATSTEPEXECUTIONLOGGER));
		if (!"beans".equals(element.getParentNode().getLocalName())) {//NOPMD
			String testcaseParentName = element.getParentNode().getAttributes().getNamedItem("id").getNodeValue();
			bDef.getPropertyValues().addPropertyValue(XsdElementConstants.MEMBER_BASETESTSTEP_TESTCASE,
					new RuntimeBeanReference(testcaseParentName));
		}
		
        List<Element> asserterReferences = (List<Element>) DomUtils.getChildElementsByTagName(element, "asserterReferences");
        if (null != asserterReferences && asserterReferences.size() == 1) {
	        List<Element> allReferences = (List<Element>) DomUtils.getChildElementsByTagName(asserterReferences.get(0), "ateXmlElementReference");
	        
	        if (allReferences != null && !allReferences.isEmpty()) {
	        	parseAsserterComponents(allReferences , bDef, parserContext);
	        }
		}
        
        parserContext.getRegistry().registerBeanDefinition(element.getAttribute("id"), bDef);
        return (AbstractBeanDefinition) bDef;
      
	}
	private static void parseAsserterComponents(List<Element> childElements,
			BeanDefinition beanDef, ParserContext parserContext) {
		ManagedList<BeanDefinition> children = new ManagedList<BeanDefinition>(//NOPMD
				childElements.size());
		for (Element element : childElements) {
			BeanDefinitionParser bep = XsdNameSpaceParserRegistry.getNameSpaceHandlerRegistry().get(element.getLocalName());
			if (null == bep) throw GlobalUtils.createNotInitializedException("Name space handler not registered for this element: " + element.getTagName());
			BeanDefinition tmpBDef = bep.parse(element, parserContext);
			children.add(tmpBDef);
			
		}
		beanDef.getPropertyValues().addPropertyValue(
				"expectedResultAsserter", children);
	}
	
}
