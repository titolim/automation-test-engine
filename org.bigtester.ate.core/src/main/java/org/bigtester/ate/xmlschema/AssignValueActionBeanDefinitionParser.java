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
import org.bigtester.ate.model.page.elementaction.AssignValueAction;
import org.bigtester.ate.model.page.elementaction.AssignValueAction.ValueAssignmentMethod;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
//import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
//import org.springframework.beans.factory.support.ChildBeanDefinition;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
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
public class AssignValueActionBeanDefinitionParser extends
		AbstractBeanDefinitionParser {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected @Nullable AbstractBeanDefinition parseInternal(@Nullable Element element,
			@Nullable ParserContext parserContext) {
		// Here we parse the Spring elements such as < property>
				if (parserContext == null || element == null)
					throw GlobalUtils
							.createNotInitializedException("element and parserContext");
				// Here we parse the Spring elements such as < property>
				BeanDefinitionHolder holder = parserContext.getDelegate()
						.parseBeanDefinitionElement(element);
				BeanDefinition bDef = holder.getBeanDefinition();
				bDef.setBeanClassName(AssignValueAction.class.getName());

				String data = element
						.getAttribute(XsdElementConstants.ATTR_SENDKEYSACTION_DATAVALUE);
				if (StringUtils.hasText(data)) {
					bDef.setAttribute(XsdElementConstants.ATTR_SENDKEYSACTION_DATAVALUE, data);
					bDef.getConstructorArgumentValues().addGenericArgumentValue( new RuntimeBeanReference(data));
				} else {
					bDef.setAttribute(XsdElementConstants.ATTR_SENDKEYSACTION_DATAVALUE, "");
				}
				
				String replace = element
						.getAttribute(XsdElementConstants.ATTR_ASSIGNVALUEACTION_ASSIGNMETHOD);
				if (replace != null && StringUtils.hasText(replace)) {
					ValueAssignmentMethod rep = ValueAssignmentMethod.valueOf(replace);
					bDef.setAttribute(XsdElementConstants.ATTR_ASSIGNVALUEACTION_ASSIGNMETHOD, rep);
					bDef.getPropertyValues().addPropertyValue(XsdElementConstants.ATTR_ASSIGNVALUEACTION_ASSIGNMETHOD, rep );
				} else {
					bDef.setAttribute(XsdElementConstants.ATTR_ASSIGNVALUEACTION_ASSIGNMETHOD, ValueAssignmentMethod.REPLACE);
				}

				bDef.setParentName(XsdElementConstants.ELEMENT_ID_BASEELEMENTACTION);
				
				bDef.setAttribute("id", element.getAttribute("id"));
				parserContext.getRegistry().registerBeanDefinition(
						element.getAttribute("id"), bDef);
				return (AbstractBeanDefinition) bDef;
	}
	
	
}
