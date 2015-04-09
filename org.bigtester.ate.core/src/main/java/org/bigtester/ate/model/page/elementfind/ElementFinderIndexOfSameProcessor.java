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
package org.bigtester.ate.model.page.elementfind;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.constant.XsdElementConstants;
import org.bigtester.ate.model.data.AutoIncrementalDataHolder;
import org.eclipse.aether.util.StringUtils;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

// TODO: Auto-generated Javadoc
/**
 * This class CaseDataProcessor defines ....
 * 
 * @author Peidong Hu
 *
 */

public class ElementFinderIndexOfSameProcessor implements
		BeanDefinitionRegistryPostProcessor {

	/** The bd reg. */
	@Nullable
	transient private BeanDefinitionRegistry bdReg;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void postProcessBeanDefinitionRegistry(
			@Nullable BeanDefinitionRegistry bdReg) throws BeansException {
		if (bdReg == null)
			throw new IllegalStateException(
					"Spring Container initialization error");

		this.setBdReg(bdReg);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void postProcessBeanFactory(
			@Nullable ConfigurableListableBeanFactory beanFactory)
			throws BeansException {
		if (beanFactory == null)
			throw new IllegalStateException(
					"Spring Container initialization error");
		// String[] homePageNames =
		// beanFactory.getBeanNamesForType(IStepInputData.class, true, false);
		String[] elementFinders = beanFactory.getBeanNamesForType(
				AbstractElementFind.class, true, false);
		for (int index = 0; index < elementFinders.length; index++) {
			BeanDefinition elementFinderDef = beanFactory
					.getBeanDefinition(elementFinders[index]);

			Object indexOfSameElementsRef = null; // NOPMD
			if (elementFinderDef
					.getPropertyValues()
					.getPropertyValue(
							XsdElementConstants.ATTR_GENERICELEMENTFIND_INDEXOFSAMEELEMENTS) != null) {
				indexOfSameElementsRef = elementFinderDef
						.getPropertyValues()
						.getPropertyValue(
								XsdElementConstants.ATTR_GENERICELEMENTFIND_INDEXOFSAMEELEMENTS)
						.getValue();
			}
			if (indexOfSameElementsRef == null)
				continue;
			String dataValue = ((RuntimeBeanReference) indexOfSameElementsRef)
					.getBeanName();

			if (null == dataValue) {
				throw new IllegalStateException(
						"Spring Container sendKeyActionValue initialization error");
			} else {
				try {
					beanFactory.getBeanDefinition(dataValue);
				} catch (NoSuchBeanDefinitionException NoBeanDef) {

					String idstr;
					int intDataValue;
					BeanDefinitionBuilder definitionBuilder;

					if (StringUtils.isEmpty(dataValue)) {// assign 0
						intDataValue = 0;
					} else {
						try {
							intDataValue = Integer.parseInt(dataValue);//NOPMD
						} catch (NumberFormatException nonNumeric) {
							intDataValue = 0;
						}
					}
					definitionBuilder = BeanDefinitionBuilder
							.genericBeanDefinition(AutoIncrementalDataHolder.class);

					definitionBuilder.addConstructorArgValue(intDataValue);
					definitionBuilder.addConstructorArgValue(1);

					idstr = (String) elementFinderDef.getAttribute("id")
							+ "_elementfinderIndexOfSameElements_ID";

					getBdReg().registerBeanDefinition(idstr,
							definitionBuilder.getBeanDefinition());

					elementFinderDef
							.getPropertyValues()
							.removePropertyValue(
									XsdElementConstants.ATTR_GENERICELEMENTFIND_INDEXOFSAMEELEMENTS);
					elementFinderDef
							.getPropertyValues()
							.addPropertyValue(
									XsdElementConstants.ATTR_GENERICELEMENTFIND_INDEXOFSAMEELEMENTS,
									new RuntimeBeanReference(idstr));

				}
			}
		}

	}

	/**
	 * @return the bdReg
	 */

	public BeanDefinitionRegistry getBdReg() {

		final BeanDefinitionRegistry bdReg2 = bdReg;
		if (bdReg2 == null) {
			throw GlobalUtils.createNotInitializedException("beaddefregistry");
		} else {
			return bdReg2;
		}
	}

	/**
	 * @param bdReg
	 *            the bdReg to set
	 */
	public void setBdReg(BeanDefinitionRegistry bdReg) {
		this.bdReg = bdReg;
	}

}
