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
package org.bigtester.ate.constant;

// TODO: Auto-generated Javadoc
/**
 * This class TestCase defines ....
 * @author Peidong Hu
 *
 */
public final class XsdElementConstants {
	
	/******************************* following for Test Project ******************************/
	
	/** The Constant ELEMENT_TESTPROJECT. */
	public static final String ELEMENT_TESTPROJECT = "TestProject";
	
	/** The Constant ELEMENT_TESTSUITE. */
	public static final String ELEMENT_TESTSUITE = "TestSuite";
	
	/** The Constant PROP_TESTSUITE_SUITENAME. */
	public static final String ATTR_TESTSUITE_SUITENAME = "suiteName";
	
	/** The Constant ELEMENT_XMLTESTCASE. */
	public static final String ELEMENT_XMLTESTCASE = "XmlTestCase";
	
	/** The Constant ELEMENT_CASEDEPENDENCIES. */
	public static final String ELEMENT_CASEDEPENDENCIES = "CaseDependencies";
	
	/** The Constant ELEMENT_CASEDEPENDENCY. */
	public static final String ELEMENT_CASEDEPENDENCY = "CaseDependency";
	
	/** The Constant ATTR_CASEDEPENDENCY_DEPENDONTESTCASEID. */
	public static final String ATTR_CASEDEPENDENCY_DEPENDONTESTCASEID = "dependOnTestCaseID"; //NOPMD
	
	/** The Constant ATTR_CASEDEPENDENCY_DEPENDENCYTYPE. */
	public static final String ATTR_CASEDEPENDENCY_DEPENDENCYTYPE = "dependencyType";
	
	/** The Constant ELEMENT_TESTSTEPRESULT. */
	public static final String ELEMENT_TESTSTEPRESULT = "TestStepResult";
	
	/** The Constant ELEMENT_GENERICSYSTEMLOGGER. */
	public static final String ELEMENT_GENERICSYSTEMLOGGER = "GenericSystemLogger";
	
	/** The Constant ELEMENT_TESTDATABASEINITIALIZER. */
	public static final String ELEMENT_TESTDATABASEINITIALIZER = "TestDatabaseInitializer";
	
	/** The Constant ATTR_INITXMLFILE. */
	public static final String ATTR_TESTDBINITIALIZER_INITXMLFILE = "initXmlFile";

	/** The Constant ATTR_STEPTHINKTIME. */
	public static final String ATTR_TESTPROJECT_STEPTHINKTIME = "stepThinkTime";
	
	/** The Constant ATTR_TESTPROJECT_GLOBALINITXMLFILE. */
	public static final String ATTR_TESTPROJECT_GLOBALINITXMLFILE = "globalInitXmlFile";

	/** The Constant PROP_TESTPROJECT_SUITELIST. */
	public static final String PROP_TESTPROJECT_SUITELIST = "suiteList";
	
	/** The Constant PROP_TESTSUITE_TESTCASELIST. */
	public static final String PROP_TESTSUITE_TESTCASELIST = "testCaseList";

	/******************************* following for Test Case ******************************/
	
    /** The Constant ELEMENT_TESTCASE. */
    public static final String ELEMENT_TESTCASE = "testCase";

    /** The Constant ELEMENT_CASETYPESERVICE. */ 
    public static final String ELEMENT_CASETYPESERVICE = "caseTypeService";
    
	/** The Constant ATTR_XMLTESTCASE_TESTCASENAME. */
    public static final String ATTR_XMLTESTCASE_TESTCASEFILEPATHNAME = "testCaseFilePathName";//NOPMD

    /** The Constant ATTR_TESTCASE_TESTCASENAME. */
    public static final String ATTR_TESTCASE_TESTCASENAME = "testCaseName";

    /** The Constant ATTR_TESTCASE_TESTCASEFILENAME. */
    public static final String ATTR_TESTCASE_TESTCASEFILENAME = "testCaseFileName";

    /** The Constant ATTR_TESTCASE_PARENTTESTCASE. */
    public static final String ATTR_TESTCASE_PARENTTESTCASE = "parentTestCase";

    /** The Constant PROP_XMLTESTCASE_DEPENDONTESTCASES. */
    public static final String PROP_XMLTESTCASE_DEPENDONTESTCASES = "dependOnTestCases";
    
    /** The Constant PROP_TESTCASE_TESTSTEPLIST. */
	public static final String PROP_TESTCASE_TESTSTEPLIST = "testStepList";
	
    /******************************* following for Test Step ******************************/
	
	/** The Constant ELEMENT_MYWEBELEMENT. */
	public static final String ELEMENT_MYWEBELEMENT = "myWebElement";
	
	/** The Constant ELEMENT_ELEMENTSTEP. */
	public static final String ELEMENT_ELEMENTSTEP = "elementStep";
	
	/** The Constant ELEMENT_BASETESTSTEP. */
	public static final String ELEMENT_BASETESTSTEP = "baseTestStep";
	
	/** The Constant ATTR_BASETESTSTEP_PAGEOBJECT. */
	public static final String ATTR_BASETESTSTEP_PAGEOBJECT = "pageObject";
	
	/** The Constant ELEMENT_STEPEXPECTEDRESULTVALUE. */
	public static final String ELEMENT_STEPEXPECTEDRESULTVALUE = "stepExpectedResultValue";
	
	/** The Constant ATTR_STEPERVALUE_DATAVALUEID. */
	public static final String ATTR_STEPERVALUE_DATAVALUEID = "dataValueID";
	
	/** The Constant ELEMENT_ID_baseERValue. */
	public static final String ELEMENT_ID_BASEERVALUE = "baseERValue";
	
	/** The Constant ELEMENT_BASEERVALUE. */
	public static final String ELEMENT_BASEERVALUE = "baseERValue";
	
	/** The Constant ELEMENT_BASEERVALUE_STEPERDAO. */
	public static final String ELEMENT_BASEERVALUE_STEPERDAO = "stepERDao";
	
	/** The Constant ELEMENT_HOMESTEP. */
	public static final String ELEMENT_HOMESTEP = "homeStep";
	
	/** The Constant ELEMENT_REPEATSTEP. */
	public static final String ELEMENT_REPEATSTEP = "repeatStep";
	
	/** The Constant ELEMENT_LASTSTEP. */
	public static final String ELEMENT_LASTSTEP = "lastStep";
	
	/** The Constant ELEMENT_STEPTYPESERVICE Definition. */
	public static final String ELEMENT_STEPTYPESERVICEDEFINITION = "stepTypeServiceDefinition";
	
	/** The Constant ELEMENT_STEPTYPESERVICEREFERENCE. */
	public static final String ELEMENT_STEPTYPESERVICEREFERENCE = "stepTypeServiceReference";
	
	/** The Constant ATTR_STEPTYPESERVICEREFERENCE_STEPTYPESERVICEDEFINITIONID. */
	public static final String ATTR_STEPTYPESERVICEREFERENCE_STEPTYPESERVICEDEFINITIONID = "stepTypeServiceDefinitionID";//NOPMD
	
	/** The Constant ATTR_BASETESTSTEP_MYWEBELEMENT. */
	public static final String ATTR_BASETESTSTEP_MYWEBELEMENT = "myWebElement";
	
	/** The Constant ATTR_TESTSTEP_TARGETSTEP. */
	public static final String ATTR_TESTSTEP_TARGETSTEP = "targetStep";
	
	/** The Constant ATTR_TESTSTEP_PAGEOBJECT. */
	public static final String ATTR_TESTSTEP_PAGEOBJECT = "pageObject";
	
	/** The Constant ATTR_REPEATSTEP_STARTSTEPNAME. */
	public static final String ATTR_REPEATSTEP_STARTSTEPNAME = "startStepName";
	
	/** The Constant ATTR_REPEATSTEP_ENDSTEPNAME. */
	public static final String ATTR_REPEATSTEP_ENDSTEPNAME = "endStepName";
	
	/** The Constant ATTR_REPEATSTEP_CONTINUEONFAILURE. */
	public static final String ATTR_REPEATSTEP_CONTINUEONFAILURE = "continueOnFailure";
	
	/** The Constant ATTR_REPEATSTEP_NUMBEROFITERATIONS. */
	public static final String ATTR_REPEATSTEP_NUMBEROFITERATIONS = "numberOfIterations";
	
	/** The Constant ATTR_REPEATSTEP_ASSERTERVALUESREMAINSAME. */
	public static final String ATTR_REPEATSTEP_ASSERTERVALUESREMAINSAME = "asserterValuesRemainSame";//NOPMD
		
	/** The Constant ATTR_TESTSTEP_OPTIONALSTEP. */
	public static final String ATTR_TESTSTEP_OPTIONALSTEP = "optionalStep";
	
	/** The Constant ATTR_TESTSTEP_OPTIONALSTEPUTILINCLUSIVE. */
	public static final String ATTR_TESTSTEP_CORRELATEDOPTIONALSTEPSUTILINCLUSIVE = "correlatedOptionalStepsUtilInclusive";//NOPMD
	
	/** The Constant ATTR_TESTSTEP_STEPNAME. */
	public static final String ATTR_TESTSTEP_STEPNAME = "stepName";
	
	/** The Constant ATTR_TESTSTEP_STEPDESCRIPTION. */
	public static final String ATTR_TESTSTEP_STEPDESCRIPTION = "stepDescription";
		
	/** The Constant PROP_STEPTYPESERVICE_STEPSET. */
	public static final String MEMBER_STEPTYPESERVICE_STEPSET = "stepSet";
	
	/** The Constant MEMBER_BASETESTSTEP_TESTCASE. */
	public static final String MEMBER_BASETESTSTEP_TESTCASE = "testCase";
	
	/** The Constant ATTR_ELEMENTSTEP_MYWEBELEMENT. */
	public static final String ATTR_ELEMENTSTEP_MYWEBELEMENT = "myWebElement";
	
	/******************************* following for Test Page ******************************/
	
	/** The Constant ELEMENT_ID_MYBASEPAGEMODEL. */
	public static final String ELEMENT_ID_MYBASEPAGEMODEL = "myBasePageModel";
	
	/** The Constant ELEMENT_ID_MYBASEPAGEOBJECT. */
	public static final String ELEMENT_ID_MYBASEPAGEOBJECT = "myBasePageObject";
	
	/** The Constant ELEMENT_ID_BASEELEMENTACTION. */
	public static final String ELEMENT_ID_BASEELEMENTACTION = "baseElementAction";
	
	/** The Constant ELEMENT_BASEPAGEOBJECT. */
	public static final String ELEMENT_BASEPAGEOBJECT = "basePageObject";

	/** The Constant ATTR_BASEPAGEOBJECT_DATAFILE. */
	public static final String ATTR_BASEPAGEOBJECT_DATAFILE = "dataFile";

	/** The Constant ELEMENT_BASEPAGEMODEL. */
	public static final String ELEMENT_BASEPAGEMODEL = "basePageModel";
	
	/** The Constant ATTR_BASEPAGEMODEL_MYWD. */
	public static final String ATTR_BASEPAGEMODEL_MYWD = "myWebDriver";
	
	/** The Constant ELEMENT_LASTPAGE. */
	public static final String ELEMENT_LASTPAGE = "lastPage";
	
	/** The Constant ELEMENT_HOMEPAGE. */
	public static final String ELEMENT_HOMEPAGE = "homePage";
	
	/** The Constant ATTR_HOMEPAGE_HOMEURL. */
	public static final String ATTR_HOMEPAGE_HOMEURL = "homeUrl";
	
	/** The Constant ELEMENT_REGULARPAGE. */
	public static final String ELEMENT_REGULARPAGE = "regularPage";
	
	/** The Constant ATTR_ABSTRACTEXPECTEDRESULTASSERTER_RESULTPAGE. */
	public static final String ATTR_ABSTRACTEXPECTEDRESULTASSERTER_RESULTPAGE = "resultPage"; //NOPMD
		
	/** The Constant ELEMENT_BASEELEMENTACTION. */
	public static final String ELEMENT_BASEELEMENTACTION = "baseElementAction";
	
	/** The Constant ATTR_BASEELEMENTACTION_DATAVALUE. */
	public static final String ATTR_BASEELEMENTACTION_DATAVALUE = "dataValue";
		
	/** The Constant ELEMENT_PAGEPROPERTYCORRECTNESS. */
	public static final String ELEMENT_PAGEPROPERTYCORRECTNESS = "pagePropertyCorrectnessAsserter";
	
	/** The Constant ELEMENT_PAGEPROPERTYCORRECTNESS. */
	public static final String ATTR_PAGEPROPERTYCORRECTNESS = "pagePropertyCorrectnessAsserter";
	
	/** The Constant ELEMENT_PAGEELEMENTEXISTENCE. */
	public static final String ELEMENT_PAGEELEMENTEXISTENCE = "pageElementExistenceAsserter";
	
    /******************************* following for Test Data ****************************/
	
	/** The Constant ELEMENT_ID_BASEINPUTDATAVALUE. */
	public static final String ELEMENT_ID_BASEINPUTDATAVALUE = "baseInputDataValue";
	
	/** The Constant ELEMENT_BASEDATAVALUE. */
	public static final String ELEMENT_BASEINPUTDATAVALUE = "baseInputDataValue";
	
	/** The Constant ATTR_BASEDATAVALUE_ELEMENTDATADAO. */
	public static final String ATTR_BASEINPUTDATAVALUE_ELEMENTDATADAO = "elementDataDao"; //NOPMD
	
	/** The Constant ELEMENT_INPUTDATAVALUEPARENT. */
	public static final String ELEMENT_INPUTDATAVALUEPARENT = "stepInputDataValueParent";
				
	/** The Constant ELEMENT_STEPDATAVALUE. */
	public static final String ELEMENT_STEPINPUTDATAVALUE = "fileDataHolder";
			
	/** The Constant ATTR_STEPDATAVALUE_DATAVALUEID. */
	public static final String ATTR_STEPDATAVALUE_DATAVALUEID = "dataValueID";
	
	/** The Constant ATTR_ABSTRACTEXPECTEDRESULTASSERTER_STEPERVALUE. */
	public static final String ATTR_ABSTRACTEXPECTEDRESULTASSERTER_STEPERVALUE = "stepERValue";//NOPMD
	
	/** The Constant ELEMENT_autoIncrementalDataHolder. */
	public static final String ELEMENT_AUTOINCREMENTALDATAHOLDSER = "autoIncrementalDataHolder";
	
	/** The Constant ATTR_AUTOINCREMENTALDATAHOLDSER_STARTVALUE. */
	public static final String ATTR_AUTOINCREMENTALDATAHOLDSER_STARTVALUE = "startValue";//NOPMD
	
	/** The Constant ATTR_AUTOINCREMENTALDATAHOLDSER_ENDVALUE. */
	public static final String ATTR_AUTOINCREMENTALDATAHOLDSER_ENDVALUE = "endValue";//NOPMD

	/** The Constant ATTR_AUTOINCREMENTALDATAHOLDSER_PACING. */
	public static final String ATTR_AUTOINCREMENTALDATAHOLDSER_PACING = "pacing";//NOPMD
	
	/** The Constant ELEMENT_RUNTIMEDATAHOLDER. */
	public static final String ELEMENT_RUNTIMEDATAHOLDER = "runTimeDataHolder";
	
	/** The Constant ATTR_RUNTIMEDATAHOLDER_DATATYPE. */
	public static final String ATTR_RUNTIMEDATAHOLDER_DATATYPE = "dataType";
	
	/** The Constant ATTR_RUNTIMEDATAHOLDER_DATAHOLDERTYPE. */
	public static final String ATTR_RUNTIMEDATAHOLDER_DATAHOLDERTYPE = "dataHolderType"; //NOPMD
	
	/** The Constant ATTR_RUNTIMEDATAHOLDER_SUBCASEMAPPEDDATAHOLDERID. */
	public static final String ATTR_RUNTIMEDATAHOLDER_SUBCASEMAPPEDDATAHOLDERID = "subCaseMappedDataHolderID"; //NOPMD
	
	/** The Constant ATTR_RUNTIMEDATAHOLDER_INDEXOFSAMETYPEDATAONPAGE. */
	public static final String ATTR_RUNTIMEDATAHOLDER_INDEXOFSAMETYPEDATAONPAGE = "indexOfSameTypeDataOnPage"; //NOPMD
	
	/** The Constant ATTR_RUNTIMEDATAHOLDER_RANDOMINPUTLENGTH. */
	public static final String ATTR_RUNTIMEDATAHOLDER_RANDOMINPUTLENGTH = "randomInputLength"; //NOPMD
	
	/** The Constant ATTR_RUNTIMEDATAHOLDER_DATAVALUE. */
	public static final String ATTR_RUNTIMEDATAHOLDER_DATAVALUE = "dataValue";
	
	/** The Constant ATTR_RUNTIMEDATAHOLDER_LEFTBOUNDRY. */
	public static final String ATTR_RUNTIMEDATAHOLDER_LEFTBOUNDRY = "pageHtmlLeftBoundry";
	
	/** The Constant ATTR_RUNTIMEDATAHOLDER_RIGHTBOUNDRY. */
	public static final String ATTR_RUNTIMEDATAHOLDER_RIGHTBOUNDRY = "pageHtmlRightBoundry"; //NOPMD
	
	/** The Constant ATTR_RUNTIMEDATAHOLDER_PAGE. */
	public static final String ATTR_RUNTIMEDATAHOLDER_PAGE = "page"; //NOPMD
			
	/******************************* following for Element Find ******************************/	
	
	/** The Constant ELEMENT_BASETESTSTEP. */
	public static final String ELEMENT_ELEMENTFINDBYID = "elementFindById";
	
	/** The Constant ELEMENT_ELEMENTFINDBYXPATH. */
	public static final String ELEMENT_ELEMENTFINDBYXPATH = "elementFindByXpath";
	
	/** The Constant ELEMENT_BROWSERWINDOWFINDBYTITLE. */
	public static final String ELEMENT_BROWSERWINDOWFINDBYTITLE = "browserWindowFindByTitle";
	
	/** The Constant ELEMENT_BROWSERWINDOWFINDBYOPENSEQUENCE. */
	public static final String ELEMENT_BROWSERWINDOWFINDBYOPENSEQUENCE = "browserWindowFindByOpenSequence";//NOPMD
	
	/** The Constant ATTR_BROWSERWINDOWFINDBYOPENSEQUENCE_OPENSEQUENCE. */
	public static final String ATTR_BROWSERWINDOWFINDBYOPENSEQUENCE_OPENSEQUENCE = "openSequence";//NOPMD
	
	/** The Constant ATTR_BROWSERWINDOWFINDBYTITLE_TITLE. */
	public static final String ATTR_BROWSERWINDOWFINDBYTITLE_TITLE = "title"; //NOPMD
	
	/** The Constant ATTR_MYWEBELEMENT_ELEMENTFIND. */
	public static final String ATTR_MYWEBELEMENT_ELEMENTFIND = "elementFind";
	
	/** The Constant ATTR_ELEMENTACTIONDEF_ELEMENTFIND. */
	public static final String ATTR_ELEMENTACTIONDEF_ELEMENTFIND = "elementFind";
	
	/** The Constant ATTR_ELEMENTFINDBYID_FINDBYVALUE. */
	public static final String ATTR_ELEMENTFINDBYID_FINDBYVALUE = "findByValue";
	
	/** The Constant ATTR_GENERICELEMENTFIND_FINDBYVALUE. */
	public static final String ATTR_GENERICELEMENTFIND_FINDBYVALUE = "findByValue"; //NOPMD
	
	/** The Constant ATTR_GENERICELEMENTFIND_INDEXOFSAMEELEMENTS. */
	public static final String ATTR_GENERICELEMENTFIND_INDEXOFSAMEELEMENTS = "indexOfSameElements"; //NOPMD

	/******************************* following for Element Action ******************************/
	
	/** The Constant ELEMENT_MYWEBELEMENT. */
	public static final String ELEMENT_ELEMENTACTIONDEF = "elementActionDef";
		
	/** The Constant ELEMENT_BROWSERWINDOWSWITCH. */
	public static final String ELEMENT_BROWSERWINDOWSWITCH = "browserWindowSwitch";
	
	/** The Constant ELEMENT_BROWSERWINDOWCLOSE. */
	public static final String ELEMENT_BROWSERWINDOWCLOSE = "browserWindowClose";
	
	/** The Constant ELEMENT_CLICKACTION. */
	public static final String ELEMENT_CLICKACTION = "clickAction";
	
	/** The Constant ELEMENT_CLEARTEXTACTION. */
	public static final String ELEMENT_CLEARTEXTACTION = "clearTextAction";
	
	/** The Constant ELEMENT_MOUSEMOVETOACTION. */
	public static final String ELEMENT_MOUSEMOVETOACTION = "mouseMoveToAction";
	
	/** The Constant ELEMENT_SENDKEYSACTION. */
	public static final String ELEMENT_SENDKEYSACTION = "sendKeysAction";
	
	/** The Constant ATTR_SENDKEYSACTION_DATAVALUE. */
	public static final String ATTR_SENDKEYSACTION_DATAVALUE = "dataValue";
	
	/** The Constant ELEMENT_UPLOADFILEACTION. */
	public static final String ELEMENT_UPLOADFILEACTION = "uploadFileAction";
	
	/** The Constant ATTR_UPLOADFILEACTION_DATAVALUE. */
	public static final String ATTR_UPLOADFILEACTION_DATAVALUE = "fileNameWithAbsolutePath";
	
	/** The Constant ATTR_ELEMENTSTEP_ELEMENTACTIONDEF. */
	public static final String ATTR_ELEMENTSTEP_ELEMENTACTIONDEF = "elementActionDef";
	
	/** The Constant ATTR_MYWEBELEMENT_ELEMENTACTION. */
	public static final String ATTR_MYWEBELEMENT_ELEMENTACTION = "elementAction";

	/** The Constant ATTR_ELEMENTACTIONDEF_ELEMENTACTION. */
	public static final String ATTR_ELEMENTACTIONDEF_ELEMENTACTION = "elementAction"; //NOPMD
	
	/******************************* following for Webdriver ******************************/
	
	/** The Constant ELEMENT_FIREFOXDRIVER. */
	public static final String ELEMENT_FIREFOXDRIVER = "firefoxDriver";
	
	/** The Constant ELEMENT_CHROMEDRIVER. */
	public static final String ELEMENT_CHROMEDRIVER = "chromeDriver";
	
	/** The Constant ELEMENT_IEDRIVER. */
	public static final String ELEMENT_IEDRIVER = "IEDriver";
	
	/** The Constant ELEMENT_SAFARIDRIVER. */
	public static final String ELEMENT_SAFARIDRIVER = "safariDriver";
	
	/** The Constant ELEMENT_OPERADRIVER. */
	public static final String ELEMENT_OPERADRIVER = "operaDriver";
	
	/** The Constant ELEMENT_HTMLUNITDRIVER. */
	public static final String ELEMENT_HTMLUNITDRIVER = "htmlUnitDriver";
	
	/**
	 * Instantiates a new xsd element constants.
	 */
	private XsdElementConstants () {
		throw new AssertionError();
	}
	
}

