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

package org.bigtester.ate;//NOPMD

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import org.bigtester.ate.constant.GlobalConstants;
import org.bigtester.ate.constant.XsdElementConstants;
import org.bigtester.ate.model.data.TestDatabaseInitializer;
import org.bigtester.ate.model.project.TestProject;
import org.bigtester.ate.systemlogger.problemhandler.IATEProblemHandler;
import org.bigtester.ate.systemlogger.problemhandler.ProblemHandlerRegistry;
import org.bigtester.ate.xmlschema.AlertDialogAcceptActionBeanDefinitionParser;
import org.bigtester.ate.xmlschema.AlertDialogFindInFocusBeanDefinitionParser;
import org.bigtester.ate.xmlschema.AssignValueActionBeanDefinitionParser;
import org.bigtester.ate.xmlschema.AteReferenceBeanDefinitionParser;
import org.bigtester.ate.xmlschema.AutoIncrementalDataHolderBeanDefinitionParser;
import org.bigtester.ate.xmlschema.BaseERValueBeanDefinitionParser;
import org.bigtester.ate.xmlschema.BaseElementActionBeanDefinitionParser;
import org.bigtester.ate.xmlschema.BaseInputDataValueBeanDefinitionParser;
import org.bigtester.ate.xmlschema.BasePageModelBeanDefinitionParser;
import org.bigtester.ate.xmlschema.BasePageObjectBeanDefinitionParser;
import org.bigtester.ate.xmlschema.CaseDependencyBeanDefinitionParser;
import org.bigtester.ate.xmlschema.CaseTypeServiceBeanDefinitionParser;
import org.bigtester.ate.xmlschema.ChromeDriverBeanDefinitionParser;
import org.bigtester.ate.xmlschema.ClearTextActionBeanDefinitionParser;
import org.bigtester.ate.xmlschema.ClickActionBeanDefinitionParser;
import org.bigtester.ate.xmlschema.CookiesFindAllBeanDefinitionParser;
import org.bigtester.ate.xmlschema.CookiesFindByDomainNameBeanDefinitionParser;
import org.bigtester.ate.xmlschema.CursorMoveActionBeanDefinitionParser;
import org.bigtester.ate.xmlschema.DropdownListSelectActionBeanDefinitionParser;
import org.bigtester.ate.xmlschema.ElementActionDefBeanDefinitionParser;
import org.bigtester.ate.xmlschema.ElementStepBeanDefinitionParser;
import org.bigtester.ate.xmlschema.FileExportActionBeanDefinitionParser;
import org.bigtester.ate.xmlschema.FileImportActionBeanDefinitionParser;
import org.bigtester.ate.xmlschema.FilesExportActionBeanDefinitionParser;
import org.bigtester.ate.xmlschema.FilesImportActionBeanDefinitionParser;
import org.bigtester.ate.xmlschema.FindByClassBeanDefinitionParser;
import org.bigtester.ate.xmlschema.FindByCssBeanDefinitionParser;
import org.bigtester.ate.xmlschema.FindByIdBeanDefinitionParser;
import org.bigtester.ate.xmlschema.FindByLinkTextBeanDefinitionParser;
import org.bigtester.ate.xmlschema.FindByNameBeanDefinitionParser;
import org.bigtester.ate.xmlschema.FindByPartialLinkTextBeanDefinitionParser;
import org.bigtester.ate.xmlschema.FindByTagNameBeanDefinitionParser;
import org.bigtester.ate.xmlschema.FindByXpathBeanDefinitionParser;
import org.bigtester.ate.xmlschema.FirefoxDriverBeanDefinitionParser;
import org.bigtester.ate.xmlschema.GenericSystemLoggerBeanDefinitionParser;
import org.bigtester.ate.xmlschema.HomeStepBeanDefinitionParser;
import org.bigtester.ate.xmlschema.HomepageBeanDefinitionParser;
import org.bigtester.ate.xmlschema.HtmlUnitDriverBeanDefinitionParser;
import org.bigtester.ate.xmlschema.IEDriverBeanDefinitionParser;
import org.bigtester.ate.xmlschema.IXsdBeanDefinitionParser;
import org.bigtester.ate.xmlschema.InputDataValueParentBeanDefinitionParser;
import org.bigtester.ate.xmlschema.LastPageBeanDefinitionParser;
import org.bigtester.ate.xmlschema.LastStepBeanDefinitionParser;
import org.bigtester.ate.xmlschema.MyWebElementBeanDefinitionParser;
import org.bigtester.ate.xmlschema.OperaDriverBeanDefinitionParser;
import org.bigtester.ate.xmlschema.PageElementExistBeanDefinitionParser;
import org.bigtester.ate.xmlschema.PagePropertyCorrectBeanDefinitionParser;
import org.bigtester.ate.xmlschema.RegularPageBeanDefinitionParser;
import org.bigtester.ate.xmlschema.RemoteDriverBeanDefinitionParser;
import org.bigtester.ate.xmlschema.RepeatStepBeanDefinitionParser;
import org.bigtester.ate.xmlschema.RunTimeDataHolderBeanDefinitionParser;
import org.bigtester.ate.xmlschema.SafariDriverBeanDefinitionParser;
import org.bigtester.ate.xmlschema.SauceLabDriverBeanDefinitionParser;
import org.bigtester.ate.xmlschema.SendKeysActionBeanDefinitionParser;
import org.bigtester.ate.xmlschema.StepERValueBeanDefinitionParser;
import org.bigtester.ate.xmlschema.StepInputDataValueBeanDefinitionParser;
import org.bigtester.ate.xmlschema.StepTypeReferenceBeanDefinitionParser;
import org.bigtester.ate.xmlschema.StepTypeServiceBeanDefinitionParser;
import org.bigtester.ate.xmlschema.TestCaseBeanDefinitionParser;
import org.bigtester.ate.xmlschema.TestDatabaseInitializerBeanDefinitionParser;
import org.bigtester.ate.xmlschema.TestProjectBeanDefinitionParser;
import org.bigtester.ate.xmlschema.TestSuiteBeanDefinitionParser;
import org.bigtester.ate.xmlschema.UploadFileActionBeanDefinitionParser;
import org.bigtester.ate.xmlschema.WindowCloseActionBeanDefinitionParser;
import org.bigtester.ate.xmlschema.WindowFindByOpenSequenceBeanDefinitionParser;
import org.bigtester.ate.xmlschema.WindowFindByTitleBeanDefinitionParser;
import org.bigtester.ate.xmlschema.WindowSwitchActionBeanDefinitionParser;
import org.bigtester.ate.xmlschema.XmlTestCaseBeanDefinitionParser;
import org.bigtester.ate.xmlschema.XsdNameSpaceParserRegistry;
import org.bigtester.problomatic2.ProblemHandler;
import org.dbunit.DatabaseUnitException;
import org.eclipse.jdt.annotation.Nullable;
import org.reflections.Reflections;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.StringUtils;

import com.github.javaparser.ParseException;

// TODO: Auto-generated Javadoc
/**
 * The Class TestProjectRunner defines ....
 * 
 * @author Peidong Hu & Jun Yang
 */
public final class TestProjectRunner {
	
	private TestProjectRunner() {
		
	}
	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws SQLException 
	 * @throws DatabaseUnitException 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws ParseException 
	 */
	public static void main(final String... args) throws DatabaseUnitException, SQLException, IOException, ClassNotFoundException, ParseException {
				
		if (args.length > 2 )                          //NOPMD
			throw GlobalUtils.createNotInitializedException("Only support two arguments");
		if (args.length > 1 ) {                        //NOPMD                       
			GlobalUtils.setDriverPath (args[1]);       //NOPMD
		}
		if (args.length > 0 ) {
		   	runTest(args[0]);
		} 
		else {
			runTest("");
		}
	}
	
	/**
	 * Run test.
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws ParseException 
	 */
	private static void runTest(ApplicationContext context) throws ClassNotFoundException, ParseException, IOException {
		TestProject testProj = GlobalUtils.findTestProjectBean(context);
		testProj.runSuites();
		
	}
	
	/**
	 * Run test.
	 *
	 * @param testProjectXml the test project xml
	 * @throws DatabaseUnitException the database unit exception
	 * @throws SQLException the SQL exception
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws ParseException 
	 */
	public static void runTest(@Nullable final String testProjectXml) throws DatabaseUnitException, SQLException, IOException, ClassNotFoundException, ParseException  {
		registerXsdNameSpaceParsers();
		registerProblemHandlers();
		ApplicationContext context;
		if (StringUtils.isEmpty(testProjectXml)) {
			context = new ClassPathXmlApplicationContext(
					"testproject.xml");
		} else {
			context = new FileSystemXmlApplicationContext(testProjectXml);
		}
		
		TestProject testplan = GlobalUtils.findTestProjectBean(context);
		testplan.setAppCtx(context);
		
		TestDatabaseInitializer dbinit = (TestDatabaseInitializer) context.getBean(GlobalConstants.BEAN_ID_GLOBAL_DBINITIALIZER);
		
		dbinit.setSingleInitXmlFile(testplan.getGlobalInitXmlFile());
		
		//TODO add db initialization handler
		dbinit.initializeGlobalDataFile(context);
		
		runTest(context);
		
	  	((ConfigurableApplicationContext)context).close();
	}
	
	private static void registerLegacyXsdNameSpaceParsers() {
		/******************************* following for Test Project ******************************/
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_TESTPROJECT, new TestProjectBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_TESTSUITE, new TestSuiteBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_XMLTESTCASE, new XmlTestCaseBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_CASEDEPENDENCY, new CaseDependencyBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_GENERICSYSTEMLOGGER, new GenericSystemLoggerBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_TESTDATABASEINITIALIZER, new TestDatabaseInitializerBeanDefinitionParser());
		
		/******************************* following for Test Case ******************************/
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_TESTCASE, new TestCaseBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_CASETYPESERVICE, new CaseTypeServiceBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_STEPTYPESERVICEDEFINITION, new StepTypeServiceBeanDefinitionParser());
		
		/******************************* following for Test Step ******************************/
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_ELEMENTSTEP, new ElementStepBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_HOMESTEP, new HomeStepBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_REPEATSTEP, new RepeatStepBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_LASTSTEP, new LastStepBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_BASEERVALUE, new BaseERValueBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_STEPEXPECTEDRESULTVALUE, new StepERValueBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_STEPTYPESERVICEREFERENCE, new StepTypeReferenceBeanDefinitionParser());
		
		
		/******************************* following for Test Page ******************************/
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_BASEPAGEOBJECT, new BasePageObjectBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_BASEPAGEMODEL, new BasePageModelBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_MYWEBELEMENT, new MyWebElementBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_HOMEPAGE, new HomepageBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_LASTPAGE, new LastPageBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_REGULARPAGE, new RegularPageBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_BASEELEMENTACTION, new BaseElementActionBeanDefinitionParser());
						
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_PAGEELEMENTEXISTENCE, new PageElementExistBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_PAGEPROPERTYCORRECTNESS, new PagePropertyCorrectBeanDefinitionParser());
		
		XsdNameSpaceParserRegistry.registerNameSpaceHandler("ateXmlElementReference", new AteReferenceBeanDefinitionParser());
		
		/******************************* following for Test Data ******************************/
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_BASEINPUTDATAVALUE, new BaseInputDataValueBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_STEPINPUTDATAVALUE, new StepInputDataValueBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_INPUTDATAVALUEPARENT, new InputDataValueParentBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_RUNTIMEDATAHOLDER, new RunTimeDataHolderBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_AUTOINCREMENTALDATAHOLDSER, new AutoIncrementalDataHolderBeanDefinitionParser());
		
		/******************************* following for Element Find ******************************/
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_ELEMENTFINDBYXPATH, new FindByXpathBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_ELEMENTFINDBYID, new FindByIdBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_BROWSERWINDOWFINDBYTITLE, new WindowFindByTitleBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_BROWSERWINDOWFINDBYOPENSEQUENCE, new WindowFindByOpenSequenceBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_ELEMENTFINDBYNAME, new FindByNameBeanDefinitionParser());	

		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_ELEMENTFINDBYCLASSNAME, new FindByClassBeanDefinitionParser());
	
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_ELEMENTFINDBYCSS, new FindByCssBeanDefinitionParser());
	
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_ELEMENTFINDBYLINKTEXT, new FindByLinkTextBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_ALERTDIALOGFINDINCURRENTFOCUS, new AlertDialogFindInFocusBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_COOKIESFINDALL, new CookiesFindAllBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_COOKIESFINDBYDOMAINNAME, new CookiesFindByDomainNameBeanDefinitionParser());
		
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_ELEMENTFINDBYPLINKTEXT, new FindByPartialLinkTextBeanDefinitionParser());

		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_ELEMENTFINDBYTAGNAME, new FindByTagNameBeanDefinitionParser());		
		/******************************* following for Element Action ******************************/
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_ELEMENTACTIONDEF, new ElementActionDefBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_CLICKACTION, new ClickActionBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_CLEARTEXTACTION, new ClearTextActionBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_MOUSEMOVETOACTION, new CursorMoveActionBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_SENDKEYSACTION, new SendKeysActionBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_DROPDOWNLISTSELECTACTION, new DropdownListSelectActionBeanDefinitionParser());
		
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_ASSIGNVALUEACTION, new AssignValueActionBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_UPLOADFILEACTION, new UploadFileActionBeanDefinitionParser());
				
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_BROWSERWINDOWSWITCH, new WindowSwitchActionBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_BROWSERWINDOWCLOSE, new WindowCloseActionBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_ALERTDIALOGACCEPT, new AlertDialogAcceptActionBeanDefinitionParser());
		
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_FILEIMPORTACTION, new FileImportActionBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_FILESIMPORTACTION, new FilesImportActionBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_FILEEXPORTACTION, new FileExportActionBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_FILESEXPORTACTION, new FilesExportActionBeanDefinitionParser());
		
		
		
		/******************************* following for Webdriver ******************************/
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_FIREFOXDRIVER, new FirefoxDriverBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_CHROMEDRIVER, new ChromeDriverBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_REMOTEDRIVER, new RemoteDriverBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_SAUCELABDRIVER, new SauceLabDriverBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_IEDRIVER, new IEDriverBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_SAFARIDRIVER, new SafariDriverBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_OPERADRIVER, new OperaDriverBeanDefinitionParser());
		XsdNameSpaceParserRegistry.registerNameSpaceHandler(XsdElementConstants.ELEMENT_HTMLUNITDRIVER, new HtmlUnitDriverBeanDefinitionParser());
		
		Map<String, BeanDefinitionParser> userParsers = XsdNameSpaceParserRegistry.getNameSpaceHandlerRegistry();
		for (Map.Entry<String, BeanDefinitionParser> parser : userParsers.entrySet()) {
			String elmName = parser.getKey();
			BeanDefinitionParser bdp = parser.getValue();
			if (null == elmName || bdp == null) throw GlobalUtils.createNotInitializedException("jvm map iterator");
			XsdNameSpaceParserRegistry.registerNameSpaceHandler(elmName, bdp);
		}
	}
	
	/**
	 * Register xsd name space parsers.
	 */
	public static void registerXsdNameSpaceParsers() {
		registerLegacyXsdNameSpaceParsers();
		Reflections reflections = new Reflections("org.bigtester.ate");
		Set<Class<? extends IXsdBeanDefinitionParser>> subTypes = reflections.getSubTypesOf(IXsdBeanDefinitionParser.class);
		for (Class<? extends IXsdBeanDefinitionParser> parser:subTypes) {
			try {
				Object ins = parser.newInstance();
				
				Method getParser = parser.getDeclaredMethod("getParser");
				Method getElementName = parser.getDeclaredMethod("getXsdElementTag");
				
				BeanDefinitionParser bDef =  (BeanDefinitionParser) getParser.invoke(ins,(Object[]) null);
				String elementName = (String) getElementName.invoke(ins, (Object[])  null);
				if (elementName == null || null == bDef) throw GlobalUtils.createNotInitializedException("elementname or beandefinition parser");
				XsdNameSpaceParserRegistry.registerNameSpaceHandler(elementName, bDef);
				
			} catch (NoSuchMethodException | SecurityException e) {
				throw GlobalUtils.createNotInitializedException("xsd name space parser", e);//NOPMD
			} catch (IllegalAccessException e) {
				throw GlobalUtils.createNotInitializedException("xsd name space parser", e);
			} catch (IllegalArgumentException e) {
				throw GlobalUtils.createNotInitializedException("xsd name space parser", e);
			} catch (InvocationTargetException e) {
				throw GlobalUtils.createNotInitializedException("xsd name space parser", e);
			} catch (InstantiationException e) {
				throw GlobalUtils.createNotInitializedException("class needs to provide a no argument constructor.", e);
			}
		}
		
		
		
	}

	/**
	 * Register problem handlers.
	 */
	public static void registerProblemHandlers() {
		Reflections reflections = new Reflections("org.bigtester.ate");
		Set<Class<? extends IATEProblemHandler>> handlers = reflections.getSubTypesOf(IATEProblemHandler.class);
		for (Class<? extends IATEProblemHandler> handler:handlers) {
			try {
				Object ins = handler.newInstance();
				
				Method getAttachedClassMethod = handler.getDeclaredMethod("getAttachedClass");
				
				Class<?> cls =  (Class<?>) getAttachedClassMethod.invoke(ins,(Object[]) null);
				if (null == cls) {
					ProblemHandler hlr = (ProblemHandler) ins;
					if (hlr == null) throw GlobalUtils.createInternalError("object conversion");
					ProblemHandlerRegistry.registerGenericProblemHandler(hlr);
				} else {
					ProblemHandler hlr = (ProblemHandler) ins;
					if (hlr == null) throw GlobalUtils.createInternalError("object conversion");
					ProblemHandlerRegistry.registerAttachedProblemHandler(cls, hlr);
				}
				
			} catch (NoSuchMethodException | SecurityException e) {
				throw GlobalUtils.createNotInitializedException("xsd name space parser", e);//NOPMD
			} catch (IllegalAccessException e) {
				throw GlobalUtils.createNotInitializedException("xsd name space parser", e);
			} catch (IllegalArgumentException e) {
				throw GlobalUtils.createNotInitializedException("xsd name space parser", e);
			} catch (InvocationTargetException e) {
				throw GlobalUtils.createNotInitializedException("xsd name space parser", e);
			} catch (InstantiationException e) {
				throw GlobalUtils.createNotInitializedException("class needs to provide a no argument constructor.", e);
			}
		}
		
		
		
	}

}
