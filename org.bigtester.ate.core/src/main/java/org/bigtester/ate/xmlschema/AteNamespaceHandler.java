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

import org.bigtester.ate.constant.XsdElementConstants;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

// TODO: Auto-generated Javadoc
/**
 * This class AteNamespaceHandler defines ....
 * @author Peidong Hu
 *
 */
public class AteNamespaceHandler extends NamespaceHandlerSupport {

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() {
		 
		/******************************* following for Test Project ******************************/
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_TESTPROJECT, new TestProjectBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_TESTSUITE, new TestSuiteBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_XMLTESTCASE, new XmlTestCaseBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_CASEDEPENDENCY, new CaseDependencyBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_GENERICSYSTEMLOGGER, new GenericSystemLoggerBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_TESTDATABASEINITIALIZER, new TestDatabaseInitializerBeanDefinitionParser());
		
		/******************************* following for Test Case ******************************/
        registerBeanDefinitionParser(XsdElementConstants.ELEMENT_TESTCASE, new TestCaseBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_CASETYPESERVICE, new CaseTypeServiceBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_STEPTYPESERVICEDEFINITION, new StepTypeServiceBeanDefinitionParser());
		
		/******************************* following for Test Step ******************************/
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_ELEMENTSTEP, new ElementStepBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_HOMESTEP, new HomeStepBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_REPEATSTEP, new RepeatStepBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_LASTSTEP, new LastStepBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_BASEERVALUE, new BaseERValueBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_STEPEXPECTEDRESULTVALUE, new StepERValueBeanDefinitionParser());
		
		/******************************* following for Test Page ******************************/
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_BASEPAGEOBJECT, new BasePageObjectBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_BASEPAGEMODEL, new BasePageModelBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_MYWEBELEMENT, new MyWebElementBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_HOMEPAGE, new HomepageBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_LASTPAGE, new LastPageBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_REGULARPAGE, new RegularPageBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_BASEELEMENTACTION, new BaseElementActionBeanDefinitionParser());
						
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_PAGEELEMENTEXISTENCE, new PageElementExistBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_PAGEPROPERTYCORRECTNESS, new PagePropertyCorrectBeanDefinitionParser());
		
		/******************************* following for Test Data ******************************/
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_BASEINPUTDATAVALUE, new BaseInputDataValueBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_STEPINPUTDATAVALUE, new StepInputDataValueBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_INPUTDATAVALUEPARENT, new InputDataValueParentBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_RUNTIMEDATAHOLDER, new RunTimeDataHolderBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_AUTOINCREMENTALDATAHOLDSER, new AutoIncrementalDataHolderBeanDefinitionParser());
		
		/******************************* following for Element Find ******************************/
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_ELEMENTFINDBYXPATH, new FindByXpathBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_ELEMENTFINDBYID, new FindByIdBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_BROWSERWINDOWFINDBYTITLE, new WindowFindByTitleBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_BROWSERWINDOWFINDBYOPENSEQUENCE, new WindowFindByOpenSequenceBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_ELEMENTFINDBYNAME, new FindByNameBeanDefinitionParser());	

		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_ELEMENTFINDBYCLASSNAME, new FindByClassBeanDefinitionParser());
	
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_ELEMENTFINDBYCSS, new FindByCssBeanDefinitionParser());
	
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_ELEMENTFINDBYLINKTEXT, new FindByLinkTextBeanDefinitionParser());
		
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_ELEMENTFINDBYPLINKTEXT, new FindByPartialLinkTextBeanDefinitionParser());

		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_ELEMENTFINDBYTAGNAME, new FindByTagNameBeanDefinitionParser());		
		/******************************* following for Element Action ******************************/
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_ELEMENTACTIONDEF, new ElementActionDefBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_CLICKACTION, new ClickActionBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_CLEARTEXTACTION, new ClearTextActionBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_MOUSEMOVETOACTION, new CursorMoveActionBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_SENDKEYSACTION, new SendKeysActionBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_UPLOADFILEACTION, new UploadFileActionBeanDefinitionParser());
				
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_BROWSERWINDOWSWITCH, new WindowSwitchActionBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_BROWSERWINDOWCLOSE, new WindowCloseActionBeanDefinitionParser());
	
		/******************************* following for Webdriver ******************************/
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_FIREFOXDRIVER, new FirefoxDriverBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_CHROMEDRIVER, new ChromeDriverBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_IEDRIVER, new IEDriverBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_SAFARIDRIVER, new SafariDriverBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_OPERADRIVER, new OperaDriverBeanDefinitionParser());
		registerBeanDefinitionParser(XsdElementConstants.ELEMENT_HTMLUNITDRIVER, new HtmlUnitDriverBeanDefinitionParser());
		
	}

}
