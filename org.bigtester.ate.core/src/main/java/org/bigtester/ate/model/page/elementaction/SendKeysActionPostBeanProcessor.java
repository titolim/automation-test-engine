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
package org.bigtester.ate.model.page.elementaction;


import jodd.util.ArraysUtil;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.constant.EnumRunTimeDataType;
import org.bigtester.ate.constant.XsdElementConstants;
import org.bigtester.ate.model.data.ManualAssignedValueDataHolder;
import org.bigtester.ate.model.data.RandomAlphaTextValueDataHolder;
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

import com.jcabi.aspects.Immutable.Array;

// TODO: Auto-generated Javadoc
/**
 * This class CaseDataProcessor defines ....
 * 
 * @author Peidong Hu
 *
 */

public class SendKeysActionPostBeanProcessor implements
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
		String[] allSendKeysActions = beanFactory.getBeanNamesForType(
				SendKeysAction.class, true, false);
		String[] allAssignValueActs = beanFactory.getBeanNamesForType(
				AssignValueAction.class, true, false);
		allSendKeysActions = ArraysUtil.join(allSendKeysActions, allAssignValueActs);
		
		for (int index = 0; index < allSendKeysActions.length; index++) {
			BeanDefinition sendKeyActDef = beanFactory
					.getBeanDefinition(allSendKeysActions[index]);
			String dataValue = ((RuntimeBeanReference) sendKeyActDef.getConstructorArgumentValues().getGenericArgumentValue(RuntimeBeanReference.class).getValue()).getBeanName();
					//.getAttribute(XsdElementConstants.ATTR_SENDKEYSACTION_DATAVALUE);
			if (null == dataValue) {
				throw new IllegalStateException(
						"Spring Container sendKeyActionValue initialization error");
			} else {
				try {
					beanFactory.getBeanDefinition(dataValue);
				} catch (NoSuchBeanDefinitionException NoBeanDef) {

					String idstr;
					BeanDefinitionBuilder definitionBuilder;

					if (StringUtils.isEmpty(dataValue)) {
						 definitionBuilder = BeanDefinitionBuilder
									.genericBeanDefinition(RandomAlphaTextValueDataHolder.class);
						idstr = (String) sendKeyActDef.getAttribute("id")
								+ "_SendKeysRandomAlphaTextDataValueBean_ID";
						definitionBuilder.addConstructorArgValue(idstr);
						definitionBuilder.addConstructorArgValue(10);

					} else {
						 definitionBuilder = BeanDefinitionBuilder
									.genericBeanDefinition(ManualAssignedValueDataHolder.class);
						
						definitionBuilder
								.addConstructorArgValue(EnumRunTimeDataType.TEXT);
						definitionBuilder.addConstructorArgValue(dataValue);

						idstr = (String) sendKeyActDef.getAttribute("id")
								+ "_SendKeysDataValueBean_ID";
						definitionBuilder.addConstructorArgValue(idstr);

					}

					getBdReg().registerBeanDefinition(idstr,
							definitionBuilder.getBeanDefinition());
					sendKeyActDef.setAttribute(
							XsdElementConstants.ATTR_SENDKEYSACTION_DATAVALUE,
							idstr);
					sendKeyActDef.getConstructorArgumentValues()
							.getGenericArgumentValue(RuntimeBeanReference.class).setValue( 
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
