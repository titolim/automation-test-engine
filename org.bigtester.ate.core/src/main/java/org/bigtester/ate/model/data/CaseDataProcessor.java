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
package org.bigtester.ate.model.data;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.constant.XsdElementConstants;
import org.bigtester.ate.model.page.page.Homepage;
import org.bigtester.ate.model.page.page.Lastpage;
import org.bigtester.ate.model.page.page.RegularPage;
import org.bigtester.ate.model.project.TestProject;
import org.dbunit.DatabaseUnitException;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionValidationException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

// TODO: Auto-generated Javadoc
/**
 * This class CaseDataProcessor defines ....
 * @author Peidong Hu
 *
 */

public class CaseDataProcessor implements BeanFactoryPostProcessor {

	/** The all page names. */
	@Nullable
	transient private String[] allPageNames;
	
	/** The page bean defs. */
	final transient private List<BeanDefinition> pageBeanDefs = new ArrayList<BeanDefinition>();
	
	/** The case data files. */
	final transient private List<Resource> caseDataFiles = new ArrayList<Resource>();
	
	/** The db init. */
	@Nullable
	transient private TestDatabaseInitializer dbInit;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void postProcessBeanFactory(
			@Nullable ConfigurableListableBeanFactory beanFactory) throws BeansException {
		if (beanFactory == null) throw new IllegalStateException("Spring Container initialization error");
		String[] homePageNames = beanFactory.getBeanNamesForType(Homepage.class, true, false);
		String[] lastPageNames = beanFactory.getBeanNamesForType(Lastpage.class, true, false);
		String[] regularPageNames = beanFactory.getBeanNamesForType(RegularPage.class, true, false);
		if (allPageNames != null)
			ArrayUtils.removeAll(allPageNames);
		
		allPageNames = ArrayUtils.addAll(homePageNames, lastPageNames);
		allPageNames = ArrayUtils.addAll(allPageNames, regularPageNames);
		
		pageBeanDefs.clear();
		for (int i=0; i<getAllPageNames().length; i++) {
			pageBeanDefs.add(beanFactory.getBeanDefinition(getAllPageNames()[i]));
		}
		caseDataFiles.clear();
		for (int j=0; j<pageBeanDefs.size(); j++) {
			if (null != pageBeanDefs.get(j).getPropertyValues().getPropertyValue(XsdElementConstants.ATTR_BASEPAGEOBJECT_DATAFILE)) {
				caseDataFiles.add(new FileSystemResource((String) pageBeanDefs.get(j).getPropertyValues().getPropertyValue(XsdElementConstants.ATTR_BASEPAGEOBJECT_DATAFILE).getValue()));
			}
		}
		
		if (!caseDataFiles.isEmpty()) {
			dbInit = GlobalUtils.findDBInitializer(beanFactory);
			try {
				getDbInit().setInitXmlFiles(caseDataFiles);
			} catch (IOException e) {
				throw new BeanDefinitionValidationException("Page data file in page attribute can't be read!", e);
			}
			try {
				getDbInit().initialize(beanFactory);
				TestProject tpj = GlobalUtils.findTestProjectBean(beanFactory.getParentBeanFactory());
				if (tpj.getCucumberDataTable()!=null && tpj.getFilteringStepName()!=null)
					tpj.getCucumberDataInjector().inject(tpj.getCucumberDataTable(), tpj.getFilteringStepName());
			} catch (MalformedURLException | DatabaseUnitException
					| SQLException e) {
				throw new FatalBeanException("Case database creation error!", e);
			}
		}
	}


	/**
	 * @return the allPageNames
	 */
	public String[] getAllPageNames() {

		final String[] allPageNames2 = allPageNames;
		if (null == allPageNames2) {
			throw new IllegalStateException(
					"allPageNames field is not correctly populated");
		} else {
			String[] retVal = new String[allPageNames2.length];
			System.arraycopy(allPageNames2, 0, retVal, 0, allPageNames2.length);
			return retVal;
		}

	}

	/**
	 * Gets the page bean defs.
	 *
	 * @return the page bean defs
	 */
	public List<BeanDefinition> getPageBeanDefs() {
		return pageBeanDefs;
	}


	/**
	 * @return the dbInit
	 */
	public TestDatabaseInitializer getDbInit() {
		final TestDatabaseInitializer dbInit2 = dbInit;
		if (null == dbInit2 ) {
			throw new IllegalStateException(
					"dbinit field is not correctly populated");
		} else {
			return dbInit2;
		}
	}

}
