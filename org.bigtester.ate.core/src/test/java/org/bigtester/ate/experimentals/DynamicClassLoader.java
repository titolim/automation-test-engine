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
package org.bigtester.ate.experimentals;

import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.model.Dependency;
import org.eclipse.aether.resolution.ArtifactDescriptorException;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.project.MavenProject;
import org.bigtester.ate.model.project.IRunTestCase;

import com.jcabi.aether.Classpath;

/**
 * The Class DynamicClassLoader.
 *
 * @author Peidong Hu
 */
public class DynamicClassLoader extends UnitTestBase {

	/**
	 * F test.
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws ArtifactDescriptorException
	 * @throws ArtifactResolutionException
	 * @throws XmlPullParserException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@Test
  public void fTest() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, ArtifactDescriptorException, ArtifactResolutionException, FileNotFoundException, IOException, XmlPullParserException {
		System.out.println( "------------------------------------------------------------" );
       
        RepositorySystem system = Booter.newRepositorySystem();
        List<String> jars = new ArrayList<String>();
        RepositorySystemSession session = Booter.newRepositorySystemSession( system );

        File pomFile = new File (getTestResourcesDirectory(), "pom.xml");
        Model modelPom = readPom(pomFile);
        System.out.println("--------- Model -------------");
        for (Dependency depe : modelPom.getDependencies()) {
           
        Artifact artifact = new DefaultArtifact( depe.getGroupId() + ":" +depe.getArtifactId() +":" + depe.getVersion());
        
        ArtifactRequest artifactRequest = new ArtifactRequest();
        artifactRequest.setArtifact( artifact );
        artifactRequest.setRepositories( Booter.newRepositories( system, session ) );

        ArtifactResult artifactResult = system.resolveArtifact( session, artifactRequest );

        artifact = artifactResult.getArtifact();
        jars.add(artifact.getFile().getCanonicalPath());
        
        }
        System.out.println(StringUtils.join(jars,":") );
//        for ( Dependency dependency : descriptorResult.getDependencies() )
//        {
//            System.out.println( dependency );
//        }
//		
  }

	public Model readPom(File pomFile) throws FileNotFoundException,
			IOException, XmlPullParserException {
		MavenXpp3Reader mavenReader = new MavenXpp3Reader();
		Model pom = mavenReader.read(new FileReader(pomFile));
		return pom;
	}

	@Test
	public void test3() throws FileNotFoundException, IOException,
			XmlPullParserException, DependencyResolutionRequiredException {
		MavenXpp3Reader reader = new MavenXpp3Reader(); 
		
		File pomFile = new File("pom.xml");
		Model model = reader.read(new FileReader(pomFile)); 
		MavenProject project = new MavenProject(model); 
//		Collection<File> jars = new Classpath(
//				  project,
//				  new File("/home/peidong/.m2/respository"),
//				  "test" // the scope you're interested in
//				);
		List<Dependency> runtimeClasspathElements = project.getDependencies();
		for (Dependency runtimeClasspathElement : runtimeClasspathElements) {
			System.out.println(runtimeClasspathElement);
		}
//		System.out.println("--------- Model -------------");
//		for (Dependency dependency : modelPom.getDependencies()) {
//			System.out.println("Model Dependency:" + dependency);
//		}
	}

	@Test
	public void test5() throws Exception {
		Class cls = Class.forName("org.testng.TestNG");

        // returns the ClassLoader object associated with this Class
        ClassLoader cLoader = cls.getClassLoader();
        
        ((URLClassLoader) cLoader).getURLs();
        
        System.out.println(Arrays.toString(((URLClassLoader) cLoader).getURLs()));
	}
	
	@Test
	public void test4() {
		System.out.println(System.getProperty("user.home"));
		//String classPath = "/home/peidong/git/ate/automation-test-engine/org.bigtester.ate.core/target/classes:/home/peidong/git/ate/automation-test-engine/org.bigtester.ate.core/target/org.bigtester.ate.core-0.0.3-SNAPSHOT.jar:/home/peidong/.m2/repository/org/hibernate/hibernate-entitymanager/4.3.6.Final/hibernate-entitymanager-4.3.6.Final.jar:/home/peidong/.m2/repository/org/jboss/logging/jboss-logging/3.1.3.GA/jboss-logging-3.1.3.GA.jar:/home/peidong/.m2/repository/org/jboss/logging/jboss-logging-annotations/1.2.0.Beta1/jboss-logging-annotations-1.2.0.Beta1.jar:/home/peidong/.m2/repository/org/hibernate/hibernate-core/4.3.6.Final/hibernate-core-4.3.6.Final.jar:/home/peidong/.m2/repository/antlr/antlr/2.7.7/antlr-2.7.7.jar:/home/peidong/.m2/repository/org/jboss/jandex/1.1.0.Final/jandex-1.1.0.Final.jar:/home/peidong/.m2/repository/dom4j/dom4j/1.6.1/dom4j-1.6.1.jar:/home/peidong/.m2/repository/org/hibernate/common/hibernate-commons-annotations/4.0.5.Final/hibernate-commons-annotations-4.0.5.Final.jar:/home/peidong/.m2/repository/org/hibernate/javax/persistence/hibernate-jpa-2.1-api/1.0.0.Final/hibernate-jpa-2.1-api-1.0.0.Final.jar:/home/peidong/.m2/repository/org/jboss/spec/javax/transaction/jboss-transaction-api_1.2_spec/1.0.0.Final/jboss-transaction-api_1.2_spec-1.0.0.Final.jar:/home/peidong/.m2/repository/org/javassist/javassist/3.18.1-GA/javassist-3.18.1-GA.jar:/home/peidong/.m2/repository/org/springframework/spring-orm/4.0.5.RELEASE/spring-orm-4.0.5.RELEASE.jar:/home/peidong/.m2/repository/org/springframework/spring-beans/4.0.5.RELEASE/spring-beans-4.0.5.RELEASE.jar:/home/peidong/.m2/repository/org/springframework/spring-core/4.0.5.RELEASE/spring-core-4.0.5.RELEASE.jar:/home/peidong/.m2/repository/commons-logging/commons-logging/1.1.3/commons-logging-1.1.3.jar:/home/peidong/.m2/repository/org/springframework/spring-jdbc/4.0.5.RELEASE/spring-jdbc-4.0.5.RELEASE.jar:/home/peidong/.m2/repository/org/springframework/spring-tx/4.0.5.RELEASE/spring-tx-4.0.5.RELEASE.jar:/home/peidong/.m2/repository/org/hsqldb/hsqldb/2.3.2/hsqldb-2.3.2.jar:/home/peidong/.m2/repository/org/testng/testng/6.8.8/testng-6.8.8.jar:/home/peidong/.m2/repository/org/beanshell/bsh/2.0b4/bsh-2.0b4.jar:/home/peidong/.m2/repository/com/beust/jcommander/1.27/jcommander-1.27.jar:/home/peidong/.m2/repository/org/seleniumhq/selenium/selenium-java/2.43.1/selenium-java-2.43.1.jar:/home/peidong/.m2/repository/org/seleniumhq/selenium/selenium-chrome-driver/2.43.1/selenium-chrome-driver-2.43.1.jar:/home/peidong/.m2/repository/org/seleniumhq/selenium/selenium-remote-driver/2.43.1/selenium-remote-driver-2.43.1.jar:/home/peidong/.m2/repository/cglib/cglib-nodep/2.1_3/cglib-nodep-2.1_3.jar:/home/peidong/.m2/repository/org/json/json/20080701/json-20080701.jar:/home/peidong/.m2/repository/org/seleniumhq/selenium/selenium-api/2.43.1/selenium-api-2.43.1.jar:/home/peidong/.m2/repository/org/seleniumhq/selenium/selenium-htmlunit-driver/2.43.1/selenium-htmlunit-driver-2.43.1.jar:/home/peidong/.m2/repository/net/sourceforge/htmlunit/htmlunit/2.15/htmlunit-2.15.jar:/home/peidong/.m2/repository/org/apache/commons/commons-lang3/3.3.2/commons-lang3-3.3.2.jar:/home/peidong/.m2/repository/org/apache/httpcomponents/httpmime/4.3.3/httpmime-4.3.3.jar:/home/peidong/.m2/repository/net/sourceforge/htmlunit/htmlunit-core-js/2.15/htmlunit-core-js-2.15.jar:/home/peidong/.m2/repository/xerces/xercesImpl/2.11.0/xercesImpl-2.11.0.jar:/home/peidong/.m2/repository/net/sourceforge/nekohtml/nekohtml/1.9.21/nekohtml-1.9.21.jar:/home/peidong/.m2/repository/net/sourceforge/cssparser/cssparser/0.9.14/cssparser-0.9.14.jar:/home/peidong/.m2/repository/org/w3c/css/sac/1.3/sac-1.3.jar:/home/peidong/.m2/repository/org/eclipse/jetty/jetty-websocket/8.1.15.v20140411/jetty-websocket-8.1.15.v20140411.jar:/home/peidong/.m2/repository/org/eclipse/jetty/jetty-util/8.1.15.v20140411/jetty-util-8.1.15.v20140411.jar:/home/peidong/.m2/repository/org/eclipse/jetty/jetty-io/8.1.15.v20140411/jetty-io-8.1.15.v20140411.jar:/home/peidong/.m2/repository/org/eclipse/jetty/jetty-http/8.1.15.v20140411/jetty-http-8.1.15.v20140411.jar:/home/peidong/.m2/repository/org/seleniumhq/selenium/selenium-firefox-driver/2.43.1/selenium-firefox-driver-2.43.1.jar:/home/peidong/.m2/repository/commons-io/commons-io/2.4/commons-io-2.4.jar:/home/peidong/.m2/repository/org/apache/commons/commons-exec/1.1/commons-exec-1.1.jar:/home/peidong/.m2/repository/org/seleniumhq/selenium/selenium-ie-driver/2.43.1/selenium-ie-driver-2.43.1.jar:/home/peidong/.m2/repository/net/java/dev/jna/jna/3.4.0/jna-3.4.0.jar:/home/peidong/.m2/repository/net/java/dev/jna/platform/3.4.0/platform-3.4.0.jar:/home/peidong/.m2/repository/org/seleniumhq/selenium/selenium-safari-driver/2.43.1/selenium-safari-driver-2.43.1.jar:/home/peidong/.m2/repository/org/seleniumhq/selenium/selenium-support/2.43.1/selenium-support-2.43.1.jar:/home/peidong/.m2/repository/org/webbitserver/webbit/0.4.15/webbit-0.4.15.jar:/home/peidong/.m2/repository/io/netty/netty/3.5.5.Final/netty-3.5.5.Final.jar:/home/peidong/.m2/repository/org/springframework/spring-test/4.0.5.RELEASE/spring-test-4.0.5.RELEASE.jar:/home/peidong/.m2/repository/org/springframework/spring-context/4.0.5.RELEASE/spring-context-4.0.5.RELEASE.jar:/home/peidong/.m2/repository/org/springframework/spring-expression/4.0.5.RELEASE/spring-expression-4.0.5.RELEASE.jar:/home/peidong/.m2/repository/org/slf4j/jcl-over-slf4j/1.7.7/jcl-over-slf4j-1.7.7.jar:/home/peidong/.m2/repository/org/slf4j/slf4j-api/1.7.7/slf4j-api-1.7.7.jar:/home/peidong/.m2/repository/ch/qos/logback/logback-classic/1.1.2/logback-classic-1.1.2.jar:/home/peidong/.m2/repository/ch/qos/logback/logback-core/1.1.2/logback-core-1.1.2.jar:/home/peidong/.m2/repository/ch/qos/logback/logback-access/1.1.2/logback-access-1.1.2.jar:/home/peidong/.m2/repository/log4j/log4j/1.2.14/log4j-1.2.14.jar:/home/peidong/.m2/repository/org/springframework/spring-aop/4.0.5.RELEASE/spring-aop-4.0.5.RELEASE.jar:/home/peidong/.m2/repository/aopalliance/aopalliance/1.0/aopalliance-1.0.jar:/home/peidong/.m2/repository/com/jamonapi/jamon/2.78/jamon-2.78.jar:/home/peidong/.m2/repository/com/hazelcast/hazelcast-all/3.2.3/hazelcast-all-3.2.3.jar:/home/peidong/.m2/repository/net/sourceforge/findbugs/annotations/1.3.2/annotations-1.3.2.jar:/home/peidong/.m2/repository/org/springframework/spring-aspects/4.0.5.RELEASE/spring-aspects-4.0.5.RELEASE.jar:/home/peidong/.m2/repository/cglib/cglib/2.2/cglib-2.2.jar:/home/peidong/.m2/repository/asm/asm/3.1/asm-3.1.jar:/home/peidong/.m2/repository/org/aspectj/aspectjrt/1.7.3/aspectjrt-1.7.3.jar:/home/peidong/.m2/repository/org/aspectj/aspectjweaver/1.7.3/aspectjweaver-1.7.3.jar:/home/peidong/git/problomatic2/problomatic2/problomatic2/target/classes:/home/peidong/Downloads/sts/sts-bundle/sts-3.6.2.RELEASE/plugins/org.junit_4.11.0.v201303080030/junit.jar:/home/peidong/Downloads/sts/sts-bundle/sts-3.6.2.RELEASE/plugins/org.hamcrest.core_1.3.0.v201303031735.jar:/home/peidong/.m2/repository/org/apache/xmlbeans/xmlbeans/2.4.0/xmlbeans-2.4.0.jar:/home/peidong/.m2/repository/stax/stax-api/1.0.1/stax-api-1.0.1.jar:/home/peidong/.m2/repository/xalan/xalan/2.7.1/xalan-2.7.1.jar:/home/peidong/.m2/repository/xalan/serializer/2.7.1/serializer-2.7.1.jar:/home/peidong/.m2/repository/com/sun/mail/smtp/1.4.5/smtp-1.4.5.jar:/home/peidong/.m2/repository/com/sun/mail/pop3/1.4.5/pop3-1.4.5.jar:/home/peidong/.m2/repository/com/sun/mail/mailapi/1.4.5/mailapi-1.4.5.jar:/home/peidong/.m2/repository/javax/xml/jsr173/1.0/jsr173-1.0.jar:/home/peidong/.m2/repository/org/apache/bcel/bcel/5.2/bcel-5.2.jar:/home/peidong/.m2/repository/jakarta-regexp/jakarta-regexp/1.4/jakarta-regexp-1.4.jar:/home/peidong/.m2/repository/javax/activation/activation/1.1.1/activation-1.1.1.jar:/home/peidong/.m2/repository/org/apache/ant/ant/1.8.4/ant-1.8.4.jar:/home/peidong/.m2/repository/org/apache/ant/ant-launcher/1.8.4/ant-launcher-1.8.4.jar:/home/peidong/.m2/repository/junit/junit/4.11/junit-4.11.jar:/home/peidong/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar:/home/peidong/.m2/repository/org/springframework/plugin/spring-plugin-core/1.1.0.RELEASE/spring-plugin-core-1.1.0.RELEASE.jar:/home/peidong/.m2/repository/org/springframework/plugin/spring-plugin-integration/1.0.0.RELEASE/spring-plugin-integration-1.0.0.RELEASE.jar:/home/peidong/.m2/repository/org/springframework/integration/spring-integration-core/2.1.4.RELEASE/spring-integration-core-2.1.4.RELEASE.jar:/home/peidong/.m2/repository/org/springframework/plugin/spring-plugin-metadata/1.1.0.RELEASE/spring-plugin-metadata-1.1.0.RELEASE.jar:/home/peidong/.m2/repository/xml-apis/xml-apis/1.4.01/xml-apis-1.4.01.jar:/home/peidong/.m2/repository/org/codehaus/janino/janino/2.5.16/janino-2.5.16.jar:/home/peidong/.m2/repository/org/projectlombok/lombok/1.14.8/lombok-1.14.8.jar:/home/peidong/.m2/repository/org/dbunit/dbunit/2.5.0/dbunit-2.5.0.jar:/home/peidong/.m2/repository/commons-collections/commons-collections/3.2.1/commons-collections-3.2.1.jar:/home/peidong/.m2/repository/org/eclipse/jdt/org.eclipse.jdt.annotation/1.1.0/org.eclipse.jdt.annotation-1.1.0.jar:/home/peidong/.m2/repository/com/google/inject/guice/4.0-beta5/guice-4.0-beta5.jar:/home/peidong/.m2/repository/javax/inject/javax.inject/1/javax.inject-1.jar:/home/peidong/.m2/repository/com/google/guava/guava/16.0.1/guava-16.0.1.jar:/home/peidong/.m2/repository/net/jodah/typetools/0.4.0/typetools-0.4.0.jar:/home/peidong/.m2/repository/com/github/javaparser/javaparser-core/2.0.0/javaparser-core-2.0.0.jar:/home/peidong/.m2/repository/org/jodd/jodd/3.3.8/jodd-3.3.8.jar";
		//System.out.println(StringUtils.replace(classPath, "/home/peidong/.m2/", System.getProperty("user.home")+System.getProperty("file.separator") +".m2" + System.getProperty("file.separator")));
		//System.out.println(StringUtils.replace(classPath, "/home/peidong/.m2/", System.getProperty("user.home")+System.getProperty("file.separator") +".m2" + System.getProperty("file.separator")));
	}
	
//	@Test
//	public void test5() throws DependencyResolutionRequiredException {
//		File pomFile = new File(getTestResourcesDirectory(), "pom.xml");
//		MavenProject project = createProjectFromPom(pomFile);
//		List<String> runtimeClasspathElements = project.getRuntimeClasspathElements();
//		for (String runtimeClasspathElement : runtimeClasspathElements) {
//			System.out.println(runtimeClasspathElement);
//		}
//		
//	}
//	
//	private MavenProject createProjectFromPom(File pom) {
//        MavenEmbedder maven = new MavenEmbedder();
//        maven.setOffline(true);
//        maven.setClassLoader(Thread.currentThread().getContextClassLoader());
//        maven.setLogger(new MavenEmbedderConsoleLogger());
//
//        MavenProject p = null;
//
//        try {
//            maven.setAlignWithUserInstallation(true);
//            maven.start();
//            p = maven.readProjectWithDependencies(pom);
//            maven.stop();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return p;
//    }
	
	/**
	 * F2 test.
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws MalformedURLException
	 */
	@Test
	public void f2Test() throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, MalformedURLException {
		/** Compilation Requirements *********************************************************************************************/
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(
				diagnostics, null, null);

		// This sets up the class path that the compiler will use.
		// I've added the .jar file that contains the DoStuff interface within
		// in it...
		List<String> optionList = new ArrayList<String>();
		optionList.add("-classpath");
		optionList.add(System.getProperty("java.class.path")
				+ ";dist/InlineCompiler.jar");

		File helloWorldJava = new File(
				System.getProperty("user.dir")
						+ "/generated-code/caserunners/org/bigtester/ate/model/project/CaseRunner8187856223134148550.java");

		Iterable<? extends JavaFileObject> compilationUnit = fileManager
				.getJavaFileObjectsFromFiles(Arrays.asList(helloWorldJava));
		JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager,
				diagnostics, optionList, null, compilationUnit);
		/********************************************************************************************* Compilation Requirements **/
		if (task.call()) {
			/** Load and execute *************************************************************************************************/
			System.out.println("Yipe");
			// Create a new custom class loader, pointing to the directory that
			// contains the compiled
			// classes, this should point to the top of the package structure!
			URLClassLoader classLoader = new URLClassLoader(
					new URL[] { new File(System.getProperty("user.dir")
							+ "/generated-code/caserunners/").toURI().toURL() });
			// Load the class from the classloader by name....
			Class<?> loadedClass = classLoader
					.loadClass("org.bigtester.ate.model.project.CaseRunner8187856223134148550");
			// Create a new instance...
			Object obj = loadedClass.newInstance();
			// Santity check
			if (obj instanceof IRunTestCase) {
				((IRunTestCase) obj)
						.setCurrentExecutingTCName("test case name example");
				Assert.assertEquals(
						((IRunTestCase) obj).getCurrentExecutingTCName(),
						"test case name example");
				System.out.println("pass");
			}
			/************************************************************************************************* Load and execute **/
		} else {
			for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics
					.getDiagnostics()) {
				System.out.format("Error on line %d in %s%n", diagnostic
						.getLineNumber(), diagnostic.getSource().toUri());
			}
		}
	}
}
