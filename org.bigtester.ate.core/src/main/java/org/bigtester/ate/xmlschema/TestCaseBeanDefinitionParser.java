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
import org.bigtester.ate.model.casestep.JavaCodedStep;
import org.bigtester.ate.model.casestep.JavaCodedStep.JavaCodedStepNameSpaceParser;
import org.bigtester.ate.model.casestep.TestCase;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
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
public class TestCaseBeanDefinitionParser extends AbstractBeanDefinitionParser {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected @Nullable AbstractBeanDefinition parseInternal(
			@Nullable Element element, @Nullable ParserContext parserContext) {
		// Here we parse the Spring elements such as < property>
		if (parserContext == null || element == null)
			throw GlobalUtils
					.createNotInitializedException("element and parserContext");
		// this will never be null since the schema explicitly requires that a
		// value be supplied
		String testCaseName = element
				.getAttribute(XsdElementConstants.ATTR_TESTCASE_TESTCASENAME);
		BeanDefinitionBuilder factory = BeanDefinitionBuilder
				.rootBeanDefinition(TestCase.class);
		if (StringUtils.hasText(testCaseName))
			factory.addConstructorArgValue(testCaseName);

		List<Element> testStepElements = (List<Element>) DomUtils
				.getChildElements(element);

		if (testStepElements != null && !testStepElements.isEmpty()) {
			if (null == factory)
				throw GlobalUtils.createNotInitializedException("factory");
			parseTestStepComponents(testStepElements, factory, parserContext);
		}

		return factory.getBeanDefinition();
	}

	private static void parseTestStepComponents(List<Element> childElements,
			BeanDefinitionBuilder factory, ParserContext parserContext) {
		if (!childElements.isEmpty()) {
			ManagedList<BeanDefinition> children = new ManagedList<BeanDefinition>(//NOPMD
					childElements.size());
			for (Element element : childElements) {
				if (element.getTagName().equalsIgnoreCase("ate:" // NOPMD
						+ XsdElementConstants.ELEMENT_HOMESTEP)) {
					HomeStepBeanDefinitionParser homeStep = new HomeStepBeanDefinitionParser();
					children.add(homeStep.parse(element, parserContext));
				} else if (element.getTagName().equalsIgnoreCase("ate:"
						+ XsdElementConstants.ELEMENT_ELEMENTSTEP)) {
					ElementStepBeanDefinitionParser elementStep = new ElementStepBeanDefinitionParser();
					children.add(elementStep.parse(element, parserContext));
				} else if (element.getTagName().equalsIgnoreCase("ate:"
						+ JavaCodedStep.XSD_ELEMENT_JAVACODEDSTEP)) {
					BeanDefinitionParser javastep = (new JavaCodedStep()).getParser();
					children.add(javastep.parse(element, parserContext));
				} else if (element.getTagName().equalsIgnoreCase("ate:"
						+ XsdElementConstants.ELEMENT_REPEATSTEP)) {
					RepeatStepBeanDefinitionParser repeatStep = new RepeatStepBeanDefinitionParser();
					children.add(repeatStep.parse(element, parserContext));
				} else if (element.getTagName().equalsIgnoreCase("ate:"
						+ XsdElementConstants.ELEMENT_LASTSTEP)) {
					LastStepBeanDefinitionParser lastStep = new LastStepBeanDefinitionParser();
					children.add(lastStep.parse(element, parserContext));
				} else if (element.getTagName().equalsIgnoreCase("ate:"
						+ XsdElementConstants.ELEMENT_CASETYPESERVICE)) {
					CaseTypeServiceBeanDefinitionParser caseService = new CaseTypeServiceBeanDefinitionParser();
					children.add(caseService.parse(element, parserContext));
				} else if (element
						.getTagName()
						.equalsIgnoreCase("ate:"
								+ XsdElementConstants.ELEMENT_STEPTYPESERVICEREFERENCE)) {
					String stepServiceDefRef = element
							.getAttribute(XsdElementConstants.ATTR_STEPTYPESERVICEREFERENCE_STEPTYPESERVICEDEFINITIONID);
					if (StringUtils.isEmpty(stepServiceDefRef)) {
						throw GlobalUtils
								.createNotInitializedException("STEPTYPESERVICEDEFINITIONID");
					} else {
						children.add(parserContext.getRegistry()
								.getBeanDefinition(stepServiceDefRef));
					}

				}
			}
			factory.addPropertyValue(
					XsdElementConstants.PROP_TESTCASE_TESTSTEPLIST, children);
		}
	}

}
