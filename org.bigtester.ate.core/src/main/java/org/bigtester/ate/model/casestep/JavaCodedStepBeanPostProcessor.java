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
 * This class JavaCodeStep defines ....
 * @author Peidong Hu
 *
 */

public class JavaCodedStepBeanPostProcessor implements BeanDefinitionRegistryPostProcessor{
	
	/** The bd reg. */
	@Nullable
	transient private BeanDefinitionRegistry bdReg;
	
	/**
	* {@inheritDoc}
	*/
	@Override
	public void postProcessBeanFactory(@Nullable ConfigurableListableBeanFactory beanFactory)
			throws BeansException {
		if (beanFactory == null)
			throw new IllegalStateException(
					"Spring Container initialization error");
		String[] javaSteps = beanFactory.getBeanNamesForType(//NOPMD
				JavaCodedStep.class, true, false);
		
		for (int index = 0; index < javaSteps.length; index++) {
			BeanDefinition javaStep = beanFactory
					.getBeanDefinition(javaSteps[index]);
			String userJavaClassName = ((RuntimeBeanReference) javaStep.getConstructorArgumentValues().getGenericArgumentValue(RuntimeBeanReference.class).getValue()).getBeanName();
					//.getAttribute(XsdElementConstants.ATTR_SENDKEYSACTION_DATAVALUE);
			if (null == userJavaClassName) {
				throw new IllegalStateException(
						"Spring Container user java code class name initialization error");
			} else {
				try {
					beanFactory.getBeanDefinition(userJavaClassName);
				} catch (NoSuchBeanDefinitionException NoBeanDef) {

					String idstr;
					BeanDefinitionBuilder definitionBuilder;

						try {
							Class<?> userClass = Class.forName(userJavaClassName);
						definitionBuilder = BeanDefinitionBuilder
									.genericBeanDefinition(userClass);
						idstr = (String) javaStep.getAttribute("id")
								+ "_UserJavaCodedStepBean_ID";
						//TODO current version javacodedstep doesn't support constructor arguments.
						//definitionBuilder.addConstructorArgValue(idstr);
						//definitionBuilder.addConstructorArgValue(10);

						
						getBdReg().registerBeanDefinition(idstr,
								definitionBuilder.getBeanDefinition());
						javaStep.setAttribute(
								JavaCodedStep.XSD_ELEMENT_JAVACODEDSTEP,
								idstr);
						javaStep.getConstructorArgumentValues()
								.getGenericArgumentValue(RuntimeBeanReference.class).setValue( 
										new RuntimeBeanReference(idstr));

						} catch (ClassNotFoundException e) {
							throw GlobalUtils.createNotInitializedException("user java step class: " + userJavaClassName, e);//NOPMD
						}
				}
			}
		}

		
	}
	/**
	* {@inheritDoc}
	*/
	@Override
	public void postProcessBeanDefinitionRegistry(@Nullable BeanDefinitionRegistry arg0)
			throws BeansException {
		if (arg0 == null)
			throw new IllegalStateException(
					"Spring Container initialization error");

		this.setBdReg(arg0);		
	}
	
	/**
	 * @param bdReg the bdReg to set
	 */
	public void setBdReg(BeanDefinitionRegistry bdReg) {
		this.bdReg = bdReg;
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
}
