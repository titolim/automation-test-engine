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

import org.springframework.util.StringUtils;
import org.bigtester.ate.GlobalUtils; 
import org.bigtester.ate.constant.XsdElementConstants;
import org.bigtester.ate.model.data.AutoIncrementalDataHolder;  
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder; 
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
public class AutoIncrementalDataHolderBeanDefinitionParser extends
		AbstractBeanDefinitionParser {

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

		BeanDefinitionHolder holder = parserContext.getDelegate()
				.parseBeanDefinitionElement(element);
		BeanDefinition bDef = holder.getBeanDefinition();

		bDef.setBeanClassName(AutoIncrementalDataHolder.class.getName());

		String startValue = element.getAttribute(
				XsdElementConstants.ATTR_AUTOINCREMENTALDATAHOLDSER_STARTVALUE);
		if (StringUtils.hasText(startValue)) {
			try {
			int intStartValue = Integer.parseInt(startValue);
			bDef.getConstructorArgumentValues().addGenericArgumentValue(
					intStartValue);
			} catch (NumberFormatException nfe){
				bDef.getConstructorArgumentValues().addGenericArgumentValue(
						0);
			}
		} else {
			bDef.getConstructorArgumentValues().addGenericArgumentValue(
					0);
		}
		
		String pacing = element.getAttribute(
				XsdElementConstants.ATTR_AUTOINCREMENTALDATAHOLDSER_PACING);
		if (StringUtils.hasText(pacing)) {
			try {
			int intPacing = Integer.parseInt(pacing);
			bDef.getConstructorArgumentValues().addGenericArgumentValue(
					intPacing);
			} catch (NumberFormatException nfe){
				bDef.getConstructorArgumentValues().addGenericArgumentValue(
						1);
			}
		} else {
			bDef.getConstructorArgumentValues().addGenericArgumentValue(
					1);
		}
		String endValue = element.getAttribute(
				XsdElementConstants.ATTR_AUTOINCREMENTALDATAHOLDSER_ENDVALUE);
		if (StringUtils.hasText(endValue)) {
			try {
			int intEndValue = Integer.parseInt(endValue);
			bDef.getConstructorArgumentValues().addGenericArgumentValue(
					intEndValue);
			} catch (NumberFormatException nfe){
				bDef.getConstructorArgumentValues().addGenericArgumentValue(
						Integer.MAX_VALUE);
			}
		} else {
			bDef.getConstructorArgumentValues().addGenericArgumentValue(
					Integer.MAX_VALUE);
		}

		parserContext.getRegistry().registerBeanDefinition(
				element.getAttribute("id"), bDef);
		return (AbstractBeanDefinition) bDef;

	}

}
