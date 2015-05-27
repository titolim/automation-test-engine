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
package org.bigtester.ate.model.casestep;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.annotation.XsdBeanDefinitionParser;
import org.bigtester.ate.constant.XsdElementConstants;
import org.bigtester.ate.model.data.exception.RuntimeDataException;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.page.elementaction.AssignValueAction;
import org.bigtester.ate.model.page.elementaction.AssignValueAction.ValueAssignmentMethod;
import org.bigtester.ate.model.page.exception.PageValidationException2;
import org.bigtester.ate.model.page.exception.StepExecutionException2;
import org.bigtester.ate.xmlschema.IXsdBeanDefinitionParser;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.beans.factory.xml.NamespaceHandlerResolver;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

// TODO: Auto-generated Javadoc
/**
 * This class JavaCodeStep defines ....
 * @author Peidong Hu
 *
 */
@XsdBeanDefinitionParser
public class JavaCodedStep extends BaseTestStep implements IJavaCodedStep, IXsdBeanDefinitionParser{
	
	@Nullable
	private static JavaCodedStepNameSpaceParser nameSpaceParser;

	private class JavaCodedStepNameSpaceParser extends AbstractBeanDefinitionParser {
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
	/**
	* {@inheritDoc}
	*/
	@Override
	@Nullable
	public IMyWebDriver getMyWebDriver() {
		return null;
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public void doStep() throws StepExecutionException2,
			PageValidationException2, RuntimeDataException {
		
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public String getXsdElementTag() {
		return "javaCodedStep";
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public BeanDefinitionParser getParser() {
			final JavaCodedStepNameSpaceParser nameSpaceParser2 = nameSpaceParser;
			if (nameSpaceParser2 != null) {
				return nameSpaceParser2;
			} else {
				return new JavaCodedStepNameSpaceParser();
			}
	}

}
