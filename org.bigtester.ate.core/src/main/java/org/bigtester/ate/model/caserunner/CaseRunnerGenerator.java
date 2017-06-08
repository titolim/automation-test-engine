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
package org.bigtester.ate.model.caserunner; //NOPMD

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import jodd.util.ClassLoaderUtil;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.constant.EnumCaseDependencyType;
import org.bigtester.ate.model.page.atewebdriver.EPlatform;
import org.bigtester.ate.model.page.atewebdriver.OSinfo;
import org.bigtester.ate.model.project.CaseDependency;
import org.bigtester.ate.model.project.TestSuite;
import org.bigtester.ate.model.project.XmlTestCase;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.github.javaparser.ASTHelper;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * This class CaseRunnerGenerator dynamically generate caserunner classes ....
 * 
 * @author Peidong Hu
 *
 */
public class CaseRunnerGenerator {

	/** The number of test cases. */
	transient private long numberOfTestCases;

	/** The case runner java file path names. */
	transient private Map<String, String> caseRunnerJavaFileNames = new HashMap<String, String>();// NOPMD

	/** The package name. */
	@Nullable
	private String basePackageName;

	/** The case runner template. */
	public final static String caseRunnerTemplateFileName = "CaseRunner";// NOPMD

	/** The case runner cache folder. */
	final private String caseRunnerCacheAbsoluteFolder;

	/** The suites. */
	final private List<TestSuite> suites;
	
	final private String testCaseNameToGenerate;

	/** The compiler. */
	@Nullable
	final private JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

	/**
	 * @return the caseRunnerCacheAbsoluteFolder
	 */
	public String getCaseRunnerCacheAbsoluteFolder() {
		return caseRunnerCacheAbsoluteFolder;
	}

	/**
	 * Instantiates a new case runner generator.
	 *
	 * @param numberOfTestCases
	 *            the number of test cases
	 * @throws IOException
	 */
	public CaseRunnerGenerator(List<TestSuite> suites) throws IOException {
		this.suites = suites;
		testCaseNameToGenerate = null;
		for (TestSuite tSuite : suites) {
			this.numberOfTestCases = this.numberOfTestCases
					+ tSuite.getTestCaseList().size();
		}
		this.caseRunnerCacheAbsoluteFolder = System.getProperty("user.dir")
				+ "/generated-code/caserunners/org/bigtester/ate/model/caserunner/";
		File deleteOldCaseRunners = new File(caseRunnerCacheAbsoluteFolder);
		if (deleteOldCaseRunners.exists()) {
			FileUtils.deleteDirectory(deleteOldCaseRunners);
		}

	}
	
	public CaseRunnerGenerator(List<TestSuite> suites, String testCaseNameToGenerate) throws IOException {
		this.testCaseNameToGenerate = testCaseNameToGenerate;
		
		this.suites = suites.stream().filter(suite->suite.getTestCaseList().stream().filter(tcase->tcase.getTestCaseFilePathName().contains(testCaseNameToGenerate)).count()>0).collect(Collectors.toList());
		
		
		for (TestSuite tSuite : suites) {
			tSuite.setTestCaseList(tSuite.getTestCaseList()
			.stream()
			.filter(tcase -> tcase.getTestCaseFilePathName()
					.contains(testCaseNameToGenerate))
			.collect(Collectors.toList()));
		}
		
		this.caseRunnerCacheAbsoluteFolder = System.getProperty("user.dir")
				+ "/generated-code/caserunners/org/bigtester/ate/model/caserunner/";
		File deleteOldCaseRunners = new File(caseRunnerCacheAbsoluteFolder);
		if (deleteOldCaseRunners.exists()) {
			FileUtils.deleteDirectory(deleteOldCaseRunners);
		}

	}

	/**
	 * Creates the case runners.
	 *
	 * @throws ParseException the parse exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void createCaseRunners() throws ParseException, IOException {
		createCaseRunners("runTest");
	}
	
	/**
	 * Creates the case runners.
	 *
	 * @param methodName the method name
	 * @throws ParseException the parse exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void createCaseRunners(String methodName) throws ParseException, IOException {
		// creates an input stream for the file to be parsed
		for (TestSuite tSuite : suites) {
			File suiteCacheDir = new File(caseRunnerCacheAbsoluteFolder
					+ tSuite.getSuiteName() + "/");
			if (!suiteCacheDir.exists()) {
				suiteCacheDir.mkdirs();
			}
			for (int index = 0; index < tSuite.getTestCaseList().size(); index++) {
				Resource fTemp = new ClassPathResource(
						caseRunnerTemplateFileName);
				CompilationUnit caseRunnerCU;
				caseRunnerCU = JavaParser.parse(fTemp.getInputStream());
				this.basePackageName = caseRunnerCU.getPackage().getName()
						.toStringWithoutComments();
				try {
					File newCaseRunner = File.createTempFile("CaseRunner",
							".java", suiteCacheDir);
					this.caseRunnerJavaFileNames.put(tSuite.getTestCaseList()
							.get(index).getTestCaseFilePathName(),
							newCaseRunner.getCanonicalPath());
					new TestCaseNameFieldVisitor().visit(caseRunnerCU, tSuite
							.getTestCaseList().get(index)
							.getTestCaseFilePathName());
					new ClassNameVisitor().visit(caseRunnerCU, FilenameUtils
							.removeExtension(newCaseRunner.getName()));

					new PackageNameVisitor().visit(caseRunnerCU,
							this.basePackageName + "." + tSuite.getSuiteName());

					new MethodTestAnnotationVisitor(methodName).visit(caseRunnerCU,
							tSuite.getTestCaseList().get(index));

					// prints the changed compilation unit
					PrintWriter out = new PrintWriter(newCaseRunner);
					out.println(caseRunnerCU.toString());
					out.close();
				} catch (IOException ioE) {

					continue;
				}
				fTemp.getInputStream().close();
			}
		}

	}

	private String parsePackageName(String caseRunnerJavaFilePathName) {

		String testSuiteName = new File(caseRunnerJavaFilePathName)
				.getParentFile().getName();
		return this.basePackageName + "." + testSuiteName;
	}

	private String parseClassFullName(String caseRunnerJavaFilePathName) {
		String packageName = parsePackageName(caseRunnerJavaFilePathName);
		String caseRunnerFileName = new File(caseRunnerJavaFilePathName)
				.getName();
		return packageName + "."
				+ FilenameUtils.removeExtension(caseRunnerFileName);
	}

	/**
	 * Load case runner classes.
	 *
	 * @return the int
	 * @throws Exception
	 */
	public int loadCaseRunnerClasses() {

		Iterator<Entry<String, String>> iter = caseRunnerJavaFileNames
				.entrySet().iterator();
		int retVal = 0;// NOPMD
		while (iter.hasNext()) {

			Map.Entry<String, String> pairs = (Map.Entry<String, String>) iter
					.next();
			String caseRunnerJavaFilePath = pairs.getValue();
			if (caseRunnerJavaFilePath == null)
				continue;
			else {
				try {
					loadClass(caseRunnerJavaFilePath,
							parseClassFullName(caseRunnerJavaFilePath));
				} catch (MalformedURLException | ClassNotFoundException e) {
					continue;
				} catch (IOException e) {
					continue;
				}
				retVal = retVal + 1;
			}
		}
		return retVal;
	}

	/**
	 * Change test annotation groups.
	 *
	 * @param caseRunnerRelativeFilePathName
	 *            the case runner relative file path name
	 * @param groups
	 *            the groups
	 * @param testMethodName
	 *            the test method name
	 * @throws ParseException
	 *             the parse exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void changeTestMethodName(String caseRunnerRelativeFilePathName,
			String testMethodName) throws ParseException, IOException {
		// creates an input stream for the file to be parsed
		FileInputStream fTemp = new FileInputStream(
				caseRunnerRelativeFilePathName);
		CompilationUnit caseRunnerCU;
		try {
			// parse the file
			caseRunnerCU = JavaParser.parse(fTemp);
		} finally {
			fTemp.close();

		}
		// visit and print the methods names
		new MethodNameVisitor().visit(caseRunnerCU, testMethodName);
		String newClass = caseRunnerCU.toString();
		fTemp.close();
		// prints the changed compilation unit
		FileWriter out = new FileWriter(caseRunnerRelativeFilePathName, false);
		out.write(newClass);
		out.close();
	}

	/**
	 * Change test annotation groups.
	 *
	 * @param caseRunnerAbsoluteFilePathName
	 *            the case runner relative file path name
	 * @param groups
	 *            the groups
	 * @param testMethodName
	 *            the test method name
	 * @throws ParseException
	 *             the parse exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void changeTestAnnotationGroups(
			String caseRunnerAbsoluteFilePathName, List<String> groups,
			String testMethodName) throws ParseException, IOException {
		// creates an input stream for the file to be parsed
		FileInputStream fTemp = new FileInputStream(
				caseRunnerAbsoluteFilePathName);
		CompilationUnit caseRunnerCU;
		try {
			// parse the file
			caseRunnerCU = JavaParser.parse(fTemp);
		} finally {
			fTemp.close();

		}

		groups.add(0, testMethodName);
		new MethodGroupAnnotationVisitor().visit(caseRunnerCU, groups);
		String newClass = caseRunnerCU.toString();
		fTemp.close();
		// prints the changed compilation unit
		FileWriter out = new FileWriter(caseRunnerAbsoluteFilePathName, false);
		out.write(newClass);
		out.close();
	}

	/**
	 * @return the numberOfTestCases
	 */
	public long getNumberOfTestCases() {
		return numberOfTestCases;
	}

	/**
	 * @return the caseRunnerJavaFilePathNames
	 */
	public Map<String, String> getCaseRunnerJavaFileNames() {
		return caseRunnerJavaFileNames;
	}

	/**
	 * @param caseRunnerJavaFileNames
	 *            the caseRunnerJavaFilePathNames to set
	 */
	public void setCaseRunnerJavaFileNames(
			Map<String, String> caseRunnerJavaFileNames) {
		this.caseRunnerJavaFileNames = caseRunnerJavaFileNames;
	}

	/**
	 * @return the packageName
	 */
	public String getBasePackageName() {

		final String packageName2 = basePackageName;
		if (null == packageName2) {
			throw GlobalUtils.createNotInitializedException("pageckage name");
		} else {
			return packageName2;
		}
	}

	/**
	 * @param packageName
	 *            the packageName to set
	 */
	public void setBasePackageName(String packageName) {
		this.basePackageName = packageName;
	}

	/**
	 * @return the suites
	 */
	public List<TestSuite> getSuites() {
		return suites;
	}

	/**
	 * The Class ClassVisitor.
	 *
	 * @author Peidong Hu
	 */
	private static class ClassNameVisitor extends VoidVisitorAdapter<String> {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visit(
				@Nullable ClassOrInterfaceDeclaration classInterfaceDec,
				@Nullable String newClassName) {
			if (null == classInterfaceDec)
				throw GlobalUtils
						.createNotInitializedException("classorinterfacedeclaration"); // NOPMD
			else {
				if (classInterfaceDec.getName().equals("CaseRunner")) {
					classInterfaceDec.setName(newClassName);
				}
			}
		}
	}

	/**
	 * The Class ClassVisitor.
	 *
	 * @author Peidong Hu
	 */
	private static class TestCaseNameFieldVisitor extends
			VoidVisitorAdapter<String> {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visit(@Nullable FieldDeclaration fieldInterfaceDec,
				@Nullable String testCaseFilePath) {
			if (null == fieldInterfaceDec) {
				throw GlobalUtils
						.createNotInitializedException("classorinterfacedeclaration");
			} else {
				for (VariableDeclarator var : fieldInterfaceDec.getVariables()) {
					if (var.getId().getName().equals("currentExecutingTCName")) {
						var.setInit(new StringLiteralExpr(testCaseFilePath));
					}
				}
			}
		}
	}

	/**
	 * The Class PackageNameVisitor.
	 *
	 * @author Peidong Hu
	 */
	private static class PackageNameVisitor extends VoidVisitorAdapter<String> {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visit(@Nullable PackageDeclaration packageInterfaceDec,
				@Nullable String newPackageName) {
			if (null == packageInterfaceDec)
				throw GlobalUtils
						.createNotInitializedException("classorinterfacedeclaration");
			else {
				packageInterfaceDec.setName(new NameExpr(newPackageName));
			}
		}
	}

	/**
	 * Simple visitor implementation for visiting MethodDeclaration nodes.
	 *
	 * @author Peidong Hu
	 */
	private static class MethodNameVisitor extends VoidVisitorAdapter<String> {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visit(@Nullable MethodDeclaration methodDec,
				@Nullable String newMethodName) {
			if (null == methodDec)
				throw GlobalUtils
						.createNotInitializedException("classorinterfacedeclaration");
			else {
				if (methodDec.getName().equals("runTest")) {
					methodDec.setName(newMethodName);
				}
			}
		}
	}

	/**
	 * Simple visitor implementation for visiting MethodDeclaration nodes.
	 *
	 * @author Peidong Hu
	 */
	private static class MethodGroupAnnotationVisitor extends
			VoidVisitorAdapter<List<String>> {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visit(@Nullable MethodDeclaration methodDec,
				@Nullable List<String> groupNames) {
			if (null == methodDec || null == groupNames)
				throw GlobalUtils
						.createNotInitializedException("classorinterfacedeclaration");
			else {
				if (methodDec.getName().equals("runTest")) {
					List<Expression> groups = new ArrayList<Expression>();
					for (int i = 1; i < groupNames.size(); i++) {
						groups.add(new StringLiteralExpr(groupNames.get(i)));
					}
					List<MemberValuePair> testAnnoParams = new ArrayList<MemberValuePair>();
					testAnnoParams.add(new MemberValuePair("groups",
							new ArrayInitializerExpr(groups)));
					NormalAnnotationExpr testAnno = new NormalAnnotationExpr(
							ASTHelper.createNameExpr("Test"), testAnnoParams);

					methodDec.getAnnotations().add(testAnno);
				}
			}
		}
	}

	/**
	 * Simple visitor implementation for visiting MethodDeclaration nodes.
	 *
	 * @author Peidong Hu
	 */
	private static class MethodTestAnnotationVisitor extends
			VoidVisitorAdapter<XmlTestCase> {

		private final String methodName;
		
		public MethodTestAnnotationVisitor(String methodName) {
			this.methodName = methodName;
		}
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visit(@Nullable MethodDeclaration methodDec,
				@Nullable XmlTestCase testCase) {
			if (null == methodDec || null == testCase)
				throw GlobalUtils
						.createNotInitializedException("classorinterfacedeclaration");
			else {
				if (methodDec.getName().equals(methodName)) {
					List<Expression> groups = new ArrayList<Expression>();
					groups.add(new StringLiteralExpr(testCase
							.getTestCaseFilePathName()));

					List<MemberValuePair> testAnnoParams = new ArrayList<MemberValuePair>();
					testAnnoParams.add(new MemberValuePair("groups",
							new ArrayInitializerExpr(groups)));
					testAnnoParams.add(new MemberValuePair("dataProvider",
							new StringLiteralExpr("dp")));

					List<CaseDependency> caseDeps = testCase
							.getDependOnTestCases();

					if (null != caseDeps) {
						List<Expression> dependsOnGroups = new ArrayList<Expression>();
						boolean alwaysRun = true;// NOPMD
						for (CaseDependency cDep : caseDeps) {
							dependsOnGroups.add(new StringLiteralExpr(cDep
									.getDependOnTestCaseID()));
							if (cDep.getDependencyType() == EnumCaseDependencyType.TESTCASESTEPDEPENDENCY) {
								alwaysRun = false;// NOPMD
							}
						}
						testAnnoParams.add(new MemberValuePair(
								"dependsOnGroups", new ArrayInitializerExpr(
										dependsOnGroups)));
						testAnnoParams.add(new MemberValuePair("alwaysRun",
								new BooleanLiteralExpr(alwaysRun)));
					}
					NormalAnnotationExpr testAnno = new NormalAnnotationExpr(
							ASTHelper.createNameExpr("Test"), testAnnoParams);

					methodDec.getAnnotations().add(testAnno);
				}
			}
		}
	}

	private void loadClass(String classFilePathName, String className)
			throws ClassNotFoundException, IOException {
		/** Compilation Requirements *********************************************************************************************/
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		StandardJavaFileManager fileManager = getCompiler()
				.getStandardFileManager(diagnostics, null, null);

		// This sets up the class path that the compiler will use.
		// I've added the .jar file that contains the DoStuff interface within
		// in it...
		List<String> optionList = new ArrayList<String>();
		optionList.add("-classpath");
		optionList.add(getAllJarsClassPathInMavenLocalRepo());
		//optionList.add("-verbose");

		File helloWorldJava = new File(classFilePathName);

		Iterable<? extends JavaFileObject> compilationUnit = fileManager
				.getJavaFileObjectsFromFiles(Arrays.asList(helloWorldJava));
		JavaCompiler.CompilationTask task = getCompiler().getTask(null,
				fileManager, diagnostics, optionList, null, compilationUnit);

		/********************************************************************************************* Compilation Requirements **/
		if (task.call()) {
			/** Load and execute *************************************************************************************************/
			// Create a new custom class loader, pointing to the directory that
			// contains the compiled
			// classes, this should point to the top of the package structure!
			// TODO the / separator needs to be revised to platform awared
			URLClassLoader classLoader = new URLClassLoader(
					new URL[] { new File(System.getProperty("user.dir")
							+ "/generated-code/caserunners/").toURI().toURL() },
					Thread.currentThread().getContextClassLoader());
			String addonClasspath = System.getProperty("user.dir")
					+ "/generated-code/caserunners/";
			ClassLoaderUtil.addFileToClassPath(addonClasspath,
					classLoader.getParent());
			classLoader.loadClass(className);
			classLoader.close();
			/************************************************************************************************* Load and execute **/
		} else {
			for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics
					.getDiagnostics()) {
				System.out.format("Error on line %d in %s%n with error %s",
						diagnostic.getLineNumber(), diagnostic.getSource(),
						diagnostic.getMessage(new Locale("en")));
			}
		}
	}

	private static @Nullable URI fixFileURL(URL url) {
		if (!"file".equals(url.getProtocol()))//NOPMD
			throw new IllegalArgumentException();// NOPMD
		File tmp ;
		try {
			tmp = new File(url.toURI());//NOPMD
		} catch (URISyntaxException e) {
			tmp = new File(url.getFile());
		}
		return tmp.toURI();
	}

	private String getAllJarsClassPathInMavenLocalRepo() {

		Class<?> cls;
		String retVal;
		try {
			cls = Class.forName("org.bigtester.ate.TestProjectRunner");// NOPMD
		} catch (ClassNotFoundException e) {
			retVal = System.getProperty("java.class.path")
					+ ":dist/InlineCompiler.jar:target/*.jar";
			return retVal;// NOPMD
		}

		// returns the ClassLoader object associated with this Class
		ClassLoader cLoader = cls.getClassLoader();

		URL[] paths = ((URLClassLoader) cLoader).getURLs();
		OSinfo osinfo = new OSinfo();
		EPlatform platform = osinfo.getOSname();
		String pathSep;
		if (platform == EPlatform.Windows_64
				|| platform == EPlatform.Windows_32) {
			pathSep = ";";
		} else {
			pathSep = ":";
		}

		retVal = "target" + File.separator + "classes"
				+ pathSep + "target" + System.getProperty("file.separator")
				+ "*.jar" + pathSep + "dist"
				+ System.getProperty("file.separator") + "InlineCompiler.jar";
		for (URL path : paths) {
			// try {
			if (null == path)
				throw GlobalUtils.createInternalError("classloader url wrong");
			URI fileURI = fixFileURL(path);
			retVal = retVal + pathSep + Paths.get(fileURI).toString();// NOPMD
			// } catch (URISyntaxException e) {
			// throw
			// GlobalUtils.createInternalError("class path resolving error", e);
			// }//NOPMD
		}

		return retVal;
		// String classPath =
		// "target/classes:target/*.jar:/home/peidong/.m2/repository/org/hibernate/hibernate-entitymanager/4.3.6.Final/hibernate-entitymanager-4.3.6.Final.jar:/home/peidong/.m2/repository/org/jboss/logging/jboss-logging/3.1.3.GA/jboss-logging-3.1.3.GA.jar:/home/peidong/.m2/repository/org/jboss/logging/jboss-logging-annotations/1.2.0.Beta1/jboss-logging-annotations-1.2.0.Beta1.jar:/home/peidong/.m2/repository/org/hibernate/hibernate-core/4.3.6.Final/hibernate-core-4.3.6.Final.jar:/home/peidong/.m2/repository/antlr/antlr/2.7.7/antlr-2.7.7.jar:/home/peidong/.m2/repository/org/jboss/jandex/1.1.0.Final/jandex-1.1.0.Final.jar:/home/peidong/.m2/repository/dom4j/dom4j/1.6.1/dom4j-1.6.1.jar:/home/peidong/.m2/repository/org/hibernate/common/hibernate-commons-annotations/4.0.5.Final/hibernate-commons-annotations-4.0.5.Final.jar:/home/peidong/.m2/repository/org/hibernate/javax/persistence/hibernate-jpa-2.1-api/1.0.0.Final/hibernate-jpa-2.1-api-1.0.0.Final.jar:/home/peidong/.m2/repository/org/jboss/spec/javax/transaction/jboss-transaction-api_1.2_spec/1.0.0.Final/jboss-transaction-api_1.2_spec-1.0.0.Final.jar:/home/peidong/.m2/repository/org/javassist/javassist/3.18.1-GA/javassist-3.18.1-GA.jar:/home/peidong/.m2/repository/org/springframework/spring-orm/4.0.5.RELEASE/spring-orm-4.0.5.RELEASE.jar:/home/peidong/.m2/repository/org/springframework/spring-beans/4.0.5.RELEASE/spring-beans-4.0.5.RELEASE.jar:/home/peidong/.m2/repository/org/springframework/spring-core/4.0.5.RELEASE/spring-core-4.0.5.RELEASE.jar:/home/peidong/.m2/repository/commons-logging/commons-logging/1.1.3/commons-logging-1.1.3.jar:/home/peidong/.m2/repository/org/springframework/spring-jdbc/4.0.5.RELEASE/spring-jdbc-4.0.5.RELEASE.jar:/home/peidong/.m2/repository/org/springframework/spring-tx/4.0.5.RELEASE/spring-tx-4.0.5.RELEASE.jar:/home/peidong/.m2/repository/org/hsqldb/hsqldb/2.3.2/hsqldb-2.3.2.jar:/home/peidong/.m2/repository/org/testng/testng/6.8.8/testng-6.8.8.jar:/home/peidong/.m2/repository/org/beanshell/bsh/2.0b4/bsh-2.0b4.jar:/home/peidong/.m2/repository/com/beust/jcommander/1.27/jcommander-1.27.jar:/home/peidong/.m2/repository/org/seleniumhq/selenium/selenium-java/2.43.1/selenium-java-2.43.1.jar:/home/peidong/.m2/repository/org/seleniumhq/selenium/selenium-chrome-driver/2.43.1/selenium-chrome-driver-2.43.1.jar:/home/peidong/.m2/repository/org/seleniumhq/selenium/selenium-remote-driver/2.43.1/selenium-remote-driver-2.43.1.jar:/home/peidong/.m2/repository/cglib/cglib-nodep/2.1_3/cglib-nodep-2.1_3.jar:/home/peidong/.m2/repository/org/json/json/20080701/json-20080701.jar:/home/peidong/.m2/repository/org/seleniumhq/selenium/selenium-api/2.43.1/selenium-api-2.43.1.jar:/home/peidong/.m2/repository/org/seleniumhq/selenium/selenium-htmlunit-driver/2.43.1/selenium-htmlunit-driver-2.43.1.jar:/home/peidong/.m2/repository/net/sourceforge/htmlunit/htmlunit/2.15/htmlunit-2.15.jar:/home/peidong/.m2/repository/org/apache/commons/commons-lang3/3.3.2/commons-lang3-3.3.2.jar:/home/peidong/.m2/repository/org/apache/httpcomponents/httpmime/4.3.3/httpmime-4.3.3.jar:/home/peidong/.m2/repository/net/sourceforge/htmlunit/htmlunit-core-js/2.15/htmlunit-core-js-2.15.jar:/home/peidong/.m2/repository/xerces/xercesImpl/2.11.0/xercesImpl-2.11.0.jar:/home/peidong/.m2/repository/net/sourceforge/nekohtml/nekohtml/1.9.21/nekohtml-1.9.21.jar:/home/peidong/.m2/repository/net/sourceforge/cssparser/cssparser/0.9.14/cssparser-0.9.14.jar:/home/peidong/.m2/repository/org/w3c/css/sac/1.3/sac-1.3.jar:/home/peidong/.m2/repository/org/eclipse/jetty/jetty-websocket/8.1.15.v20140411/jetty-websocket-8.1.15.v20140411.jar:/home/peidong/.m2/repository/org/eclipse/jetty/jetty-util/8.1.15.v20140411/jetty-util-8.1.15.v20140411.jar:/home/peidong/.m2/repository/org/eclipse/jetty/jetty-io/8.1.15.v20140411/jetty-io-8.1.15.v20140411.jar:/home/peidong/.m2/repository/org/eclipse/jetty/jetty-http/8.1.15.v20140411/jetty-http-8.1.15.v20140411.jar:/home/peidong/.m2/repository/org/seleniumhq/selenium/selenium-firefox-driver/2.43.1/selenium-firefox-driver-2.43.1.jar:/home/peidong/.m2/repository/commons-io/commons-io/2.4/commons-io-2.4.jar:/home/peidong/.m2/repository/org/apache/commons/commons-exec/1.1/commons-exec-1.1.jar:/home/peidong/.m2/repository/org/seleniumhq/selenium/selenium-ie-driver/2.43.1/selenium-ie-driver-2.43.1.jar:/home/peidong/.m2/repository/net/java/dev/jna/jna/3.4.0/jna-3.4.0.jar:/home/peidong/.m2/repository/net/java/dev/jna/platform/3.4.0/platform-3.4.0.jar:/home/peidong/.m2/repository/org/seleniumhq/selenium/selenium-safari-driver/2.43.1/selenium-safari-driver-2.43.1.jar:/home/peidong/.m2/repository/org/seleniumhq/selenium/selenium-support/2.43.1/selenium-support-2.43.1.jar:/home/peidong/.m2/repository/org/webbitserver/webbit/0.4.15/webbit-0.4.15.jar:/home/peidong/.m2/repository/io/netty/netty/3.5.5.Final/netty-3.5.5.Final.jar:/home/peidong/.m2/repository/org/springframework/spring-test/4.0.5.RELEASE/spring-test-4.0.5.RELEASE.jar:/home/peidong/.m2/repository/org/springframework/spring-context/4.0.5.RELEASE/spring-context-4.0.5.RELEASE.jar:/home/peidong/.m2/repository/org/springframework/spring-expression/4.0.5.RELEASE/spring-expression-4.0.5.RELEASE.jar:/home/peidong/.m2/repository/org/slf4j/jcl-over-slf4j/1.7.7/jcl-over-slf4j-1.7.7.jar:/home/peidong/.m2/repository/org/slf4j/slf4j-api/1.7.7/slf4j-api-1.7.7.jar:/home/peidong/.m2/repository/ch/qos/logback/logback-classic/1.1.2/logback-classic-1.1.2.jar:/home/peidong/.m2/repository/ch/qos/logback/logback-core/1.1.2/logback-core-1.1.2.jar:/home/peidong/.m2/repository/ch/qos/logback/logback-access/1.1.2/logback-access-1.1.2.jar:/home/peidong/.m2/repository/log4j/log4j/1.2.14/log4j-1.2.14.jar:/home/peidong/.m2/repository/org/springframework/spring-aop/4.0.5.RELEASE/spring-aop-4.0.5.RELEASE.jar:/home/peidong/.m2/repository/aopalliance/aopalliance/1.0/aopalliance-1.0.jar:/home/peidong/.m2/repository/com/jamonapi/jamon/2.78/jamon-2.78.jar:/home/peidong/.m2/repository/com/hazelcast/hazelcast-all/3.2.3/hazelcast-all-3.2.3.jar:/home/peidong/.m2/repository/net/sourceforge/findbugs/annotations/1.3.2/annotations-1.3.2.jar:/home/peidong/.m2/repository/org/springframework/spring-aspects/4.0.5.RELEASE/spring-aspects-4.0.5.RELEASE.jar:/home/peidong/.m2/repository/cglib/cglib/2.2/cglib-2.2.jar:/home/peidong/.m2/repository/asm/asm/3.1/asm-3.1.jar:/home/peidong/.m2/repository/org/aspectj/aspectjrt/1.7.3/aspectjrt-1.7.3.jar:/home/peidong/.m2/repository/org/aspectj/aspectjweaver/1.7.3/aspectjweaver-1.7.3.jar:/home/peidong/git/problomatic2/problomatic2/problomatic2/target/classes:/home/peidong/Downloads/sts/sts-bundle/sts-3.6.2.RELEASE/plugins/org.junit_4.11.0.v201303080030/junit.jar:/home/peidong/Downloads/sts/sts-bundle/sts-3.6.2.RELEASE/plugins/org.hamcrest.core_1.3.0.v201303031735.jar:/home/peidong/.m2/repository/org/apache/xmlbeans/xmlbeans/2.4.0/xmlbeans-2.4.0.jar:/home/peidong/.m2/repository/stax/stax-api/1.0.1/stax-api-1.0.1.jar:/home/peidong/.m2/repository/xalan/xalan/2.7.1/xalan-2.7.1.jar:/home/peidong/.m2/repository/xalan/serializer/2.7.1/serializer-2.7.1.jar:/home/peidong/.m2/repository/com/sun/mail/smtp/1.4.5/smtp-1.4.5.jar:/home/peidong/.m2/repository/com/sun/mail/pop3/1.4.5/pop3-1.4.5.jar:/home/peidong/.m2/repository/com/sun/mail/mailapi/1.4.5/mailapi-1.4.5.jar:/home/peidong/.m2/repository/javax/xml/jsr173/1.0/jsr173-1.0.jar:/home/peidong/.m2/repository/org/apache/bcel/bcel/5.2/bcel-5.2.jar:/home/peidong/.m2/repository/jakarta-regexp/jakarta-regexp/1.4/jakarta-regexp-1.4.jar:/home/peidong/.m2/repository/javax/activation/activation/1.1.1/activation-1.1.1.jar:/home/peidong/.m2/repository/org/apache/ant/ant/1.8.4/ant-1.8.4.jar:/home/peidong/.m2/repository/org/apache/ant/ant-launcher/1.8.4/ant-launcher-1.8.4.jar:/home/peidong/.m2/repository/junit/junit/4.11/junit-4.11.jar:/home/peidong/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar:/home/peidong/.m2/repository/org/springframework/plugin/spring-plugin-core/1.1.0.RELEASE/spring-plugin-core-1.1.0.RELEASE.jar:/home/peidong/.m2/repository/org/springframework/plugin/spring-plugin-integration/1.0.0.RELEASE/spring-plugin-integration-1.0.0.RELEASE.jar:/home/peidong/.m2/repository/org/springframework/integration/spring-integration-core/2.1.4.RELEASE/spring-integration-core-2.1.4.RELEASE.jar:/home/peidong/.m2/repository/org/springframework/plugin/spring-plugin-metadata/1.1.0.RELEASE/spring-plugin-metadata-1.1.0.RELEASE.jar:/home/peidong/.m2/repository/xml-apis/xml-apis/1.4.01/xml-apis-1.4.01.jar:/home/peidong/.m2/repository/org/codehaus/janino/janino/2.5.16/janino-2.5.16.jar:/home/peidong/.m2/repository/org/projectlombok/lombok/1.14.8/lombok-1.14.8.jar:/home/peidong/.m2/repository/org/dbunit/dbunit/2.5.0/dbunit-2.5.0.jar:/home/peidong/.m2/repository/commons-collections/commons-collections/3.2.1/commons-collections-3.2.1.jar:/home/peidong/.m2/repository/org/eclipse/jdt/org.eclipse.jdt.annotation/1.1.0/org.eclipse.jdt.annotation-1.1.0.jar:/home/peidong/.m2/repository/com/google/inject/guice/4.0-beta5/guice-4.0-beta5.jar:/home/peidong/.m2/repository/javax/inject/javax.inject/1/javax.inject-1.jar:/home/peidong/.m2/repository/com/google/guava/guava/16.0.1/guava-16.0.1.jar:/home/peidong/.m2/repository/net/jodah/typetools/0.4.0/typetools-0.4.0.jar:/home/peidong/.m2/repository/com/github/javaparser/javaparser-core/2.0.0/javaparser-core-2.0.0.jar:/home/peidong/.m2/repository/org/jodd/jodd/3.3.8/jodd-3.3.8.jar:/home/peidong/.m2/repository/org/eclipse/aether/aether-api/1.0.2.v20150114/aether-api-1.0.2.v20150114.jar:/home/peidong/.m2/repository/org/apache/maven/maven-aether-provider/3.2.5/maven-aether-provider-3.2.5.jar:/home/peidong/.m2/repository/org/apache/maven/maven-model/3.2.5/maven-model-3.2.5.jar:/home/peidong/.m2/repository/org/apache/maven/maven-model-builder/3.2.5/maven-model-builder-3.2.5.jar:/home/peidong/.m2/repository/org/apache/maven/maven-repository-metadata/3.2.5/maven-repository-metadata-3.2.5.jar:/home/peidong/.m2/repository/org/eclipse/aether/aether-spi/1.0.0.v20140518/aether-spi-1.0.0.v20140518.jar:/home/peidong/.m2/repository/org/eclipse/aether/aether-util/1.0.0.v20140518/aether-util-1.0.0.v20140518.jar:/home/peidong/.m2/repository/org/eclipse/aether/aether-impl/1.0.0.v20140518/aether-impl-1.0.0.v20140518.jar:/home/peidong/.m2/repository/org/codehaus/plexus/plexus-component-annotations/1.5.5/plexus-component-annotations-1.5.5.jar:/home/peidong/.m2/repository/org/eclipse/aether/aether-connector-basic/1.0.2.v20150114/aether-connector-basic-1.0.2.v20150114.jar:/home/peidong/.m2/repository/org/eclipse/aether/aether-transport-http/1.0.2.v20150114/aether-transport-http-1.0.2.v20150114.jar:/home/peidong/.m2/repository/org/eclipse/aether/aether-transport-file/1.0.2.v20150114/aether-transport-file-1.0.2.v20150114.jar:/home/peidong/.m2/repository/org/codehaus/plexus/plexus-utils/3.0.21/plexus-utils-3.0.21.jar:/home/peidong/.m2/repository/org/apache/maven/maven-compat/3.2.5/maven-compat-3.2.5.jar:/home/peidong/.m2/repository/org/apache/maven/maven-settings/3.2.5/maven-settings-3.2.5.jar:/home/peidong/.m2/repository/org/apache/maven/maven-artifact/3.2.5/maven-artifact-3.2.5.jar:/home/peidong/.m2/repository/org/apache/maven/maven-core/3.2.5/maven-core-3.2.5.jar:/home/peidong/.m2/repository/org/apache/maven/maven-settings-builder/3.2.5/maven-settings-builder-3.2.5.jar:/home/peidong/.m2/repository/org/sonatype/sisu/sisu-guice/3.2.3/sisu-guice-3.2.3-no_aop.jar:/home/peidong/.m2/repository/org/codehaus/plexus/plexus-classworlds/2.5.2/plexus-classworlds-2.5.2.jar:/home/peidong/.m2/repository/org/sonatype/plexus/plexus-sec-dispatcher/1.3/plexus-sec-dispatcher-1.3.jar:/home/peidong/.m2/repository/org/sonatype/plexus/plexus-cipher/1.4/plexus-cipher-1.4.jar:/home/peidong/.m2/repository/org/codehaus/plexus/plexus-interpolation/1.21/plexus-interpolation-1.21.jar:/home/peidong/.m2/repository/org/eclipse/sisu/org.eclipse.sisu.plexus/0.3.0.M1/org.eclipse.sisu.plexus-0.3.0.M1.jar:/home/peidong/.m2/repository/javax/enterprise/cdi-api/1.0/cdi-api-1.0.jar:/home/peidong/.m2/repository/javax/annotation/jsr250-api/1.0/jsr250-api-1.0.jar:/home/peidong/.m2/repository/org/eclipse/sisu/org.eclipse.sisu.inject/0.3.0.M1/org.eclipse.sisu.inject-0.3.0.M1.jar:/home/peidong/.m2/repository/org/apache/maven/wagon/wagon-provider-api/2.8/wagon-provider-api-2.8.jar:/home/peidong/.m2/repository/org/apache/maven/wagon/wagon-http-lightweight/2.8/wagon-http-lightweight-2.8.jar:/home/peidong/.m2/repository/org/apache/maven/wagon/wagon-http-shared/2.8/wagon-http-shared-2.8.jar:/home/peidong/.m2/repository/org/jsoup/jsoup/1.7.2/jsoup-1.7.2.jar:/home/peidong/.m2/repository/commons-lang/commons-lang/2.6/commons-lang-2.6.jar:/home/peidong/.m2/repository/org/apache/httpcomponents/httpclient/4.3.6/httpclient-4.3.6.jar:/home/peidong/.m2/repository/org/apache/httpcomponents/httpcore/4.3.3/httpcore-4.3.3.jar:/home/peidong/.m2/repository/commons-logging/commons-logging/1.1.3/commons-logging-1.1.3.jar:/home/peidong/.m2/repository/org/bigtester/org.bigtester.ate.core/0.0.4-SNAPSHOT/org.bigtester.ate.core-0.0.4-SNAPSHOT.jar";//NOPMD
		// classPath = StringUtils.replace(classPath, "/home/peidong/.m2/",
		// System.getProperty("user.home")+System.getProperty("file.separator")
		// +".m2" + System.getProperty("file.separator"));
		// classPath = StringUtils.replace(classPath, "/",
		// System.getProperty("file.separator"));
		//
		// OSinfo osinfo = new OSinfo();
		// EPlatform platform = osinfo.getOSname();
		// if (platform == EPlatform.Windows_64 ||platform ==
		// EPlatform.Windows_32)
		// {
		// classPath = StringUtils.replace(classPath, ":", ";");
		// }
		//
		// if (classPath == null) classPath = "";
		// return classPath;
		// File localRepoDir = new File( System.getProperty("user.home") +
		// System.getProperty("file.separator") + ".m2" +
		// System.getProperty("file.separator"));
		// Collection<File> files = FileUtils.listFiles(
		// localRepoDir,
		// new RegexFileFilter(".*.(jar)"),
		// DirectoryFileFilter.DIRECTORY
		// );
		// String retVal = "";
		// for (File jar : files) {
		// retVal = retVal + jar.getCanonicalPath() + ":";
		// }
		// return retVal;
	}

	/**
	 * @return the compiler
	 */
	public JavaCompiler getCompiler() {
		final JavaCompiler compiler2 = compiler;
		if (compiler2 == null) {
			throw GlobalUtils
					.createInternalError("JDK tools.jar is not correctly set in test environment.");
		} else {
			return compiler2;
		}
	}

	/**
	 * @return the testCaseNameToGenerate
	 */
	public String getTestCaseNameToGenerate() {
		return testCaseNameToGenerate;
	}
}
