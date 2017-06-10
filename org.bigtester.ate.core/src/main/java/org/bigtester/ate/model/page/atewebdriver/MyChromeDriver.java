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
package org.bigtester.ate.model.page.atewebdriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional; 

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver; 
import org.openqa.selenium.chrome.ChromeOptions;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option; 
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.bigtester.ate.GlobalUtils;
import org.eclipse.jdt.annotation.Nullable;

// TODO: Auto-generated Javadoc
/**
 * The Class MyChromeDriver defines ....
 * 
 * @author Jun Yang
 */
public class MyChromeDriver extends AbstractWebDriverBase implements IMyWebDriver {

	/** The browser profile. */
	@Nullable
	final private ChromeFeatureProfile browserProfile = new ChromeFeatureProfile(); //NOPMD
	
	/** The browsertypename. */
	final public static String BROWSERTYPENAME = "Chrome"; 
	/** The Constant BROWSERNAME. */
/*	private static final String BROWSERNAME = "googlechrome";*/
	/** The Constant BROWSERDRVNAME. */
	private static final String BROWSERDRVNAME = "webdriver.chrome.driver";
	/** The Constant BROWSERWIN32PATH. */
	private static final String BROWSERWIN32PATH = "windows/googlechrome/32bit/";
	/** The Constant BROWSERWIN64PATH. */
	private static final String BROWSERWIN64PATH = "windows/googlechrome/64bit/";
	/** The Constant BROWSERLINUX32PATH. */
	private static final String BROWSERLINUX32PATH = "linux/googlechrome/32bit/";
	/** The Constant BROWSERLINUX64PATH. */
	private static final String BROWSERLINUX64PATH = "linux/googlechrome/64bit/";
	/** The Constant BROWSEROSX32PATH. */
	private static final String BROWSEROSX32PATH = "osx/googlechrome/32bit/";
	/** The Constant BROWSEROSX32PATH. */
	private static final String BROWSEROSX64PATH = "osx/googlechrome/64bit/";
	/** The Constant BROWSERWINFILENAME. */
	private static final String BROWSERWINFILENAME = "chromedriver.exe";
	/** The Constant BROWSERLINUXFILENAME. */
	private static final String BROWSERLINUXFILENAME = "chromedriver";
	/** The Constant BROWSERMACFILENAME. */
	private static final String BROWSER0SXFILENAME = "chromedriver";

	
	/**
	 * Instantiates a new my Chrome driver.
	 */
	public MyChromeDriver(boolean preserveCookiesOnExecutions) {
		
		super();
		getBrowserProfile().setPreserveCookiesOnExecutions(preserveCookiesOnExecutions);
		
	}
	
	@SuppressWarnings({ "null", "unused" })
	private CommandLine parseArgs (String argsStr){
		// create the command line parser
		CommandLineParser parser = new DefaultParser();

		// create the Options
		Options options = new Options();
		options.addOption( "a", "all", false, "do not hide entries starting with ." );
		options.addOption( "A", "almost-all", false, "do not list implied . and .." );
		options.addOption( "b", "escape", false, "print octal escapes for nongraphic "
		                                         + "characters" );
		Option sizeOption = Option.builder()
			    .longOpt( "block-size" )
			    .desc( "use SIZE-byte blocks"  )
			    .hasArg()
			    .argName( "SIZE" )
			    .build();
		options.addOption( sizeOption );
		options.addOption( "B", "ignore-backups", false, "do not list implied entried "
		                                                 + "ending with ~");
		options.addOption( "c", false, "with -lt: sort by, and show, ctime (time of last " 
		                               + "modification of file status information) with "
		                               + "-l:show ctime and sort by name otherwise: sort "
		                               + "by ctime" );
		options.addOption( "C", false, "list entries by columns" );

		

		
			String[] args = argsStr.split("\\s+");
			
		    // parse the command line arguments
		    Optional<CommandLine> line = Optional.empty();
		    CommandLine retVal = line.get();
		    try {
				line = Optional.of(parser.parse( options, args ));
			} catch (ParseException e) {
				retVal = line.get();
			}
		    
		    return retVal;
		
	
	}
	
	@SuppressWarnings("null")
	private List<String> parseArgsIntoArray(String argsStr) {
		List<String> retVal = new ArrayList<String>();
		String[] args = argsStr.split("\\s+");
		retVal = Arrays.asList(args);
		return retVal;
		
	}
	
	/**
	 * Instantiates a new my chrome driver.
	 *
	 * @param preserveCookiesOnExecutions the preserve cookies on executions
	 * @param startArguments the start arguments
	 */

	public MyChromeDriver(boolean preserveCookiesOnExecutions, String startArguments) {
		
		super();
		getBrowserProfile().setPreserveCookiesOnExecutions(preserveCookiesOnExecutions);
		
		getBrowserProfile().setStartArguments(parseArgsIntoArray(startArguments));
		
	}
	
	/**
	 * Instantiates a new my Chrome driver.
	 */
	public MyChromeDriver() {
		
		super();
		 
		 
	}

	/**
	 * @return the browserProfile
	 */

	public   ChromeFeatureProfile  getBrowserProfile() {
		final ChromeFeatureProfile  retVal = browserProfile;
		if (null == retVal) {
			throw new IllegalStateException(
					"browserProfile is not correctly populated");

		} else {
			return retVal;
		}
	}

	/**
	 * Sets the chrome driver system env.
	 */
	public static void setChromeDriverSystemEnv() {
		OSinfo osinfo = new OSinfo();
		EPlatform platform = osinfo.getOSname();
		String driverPath = GlobalUtils.getDriverPath(); //NOPMD
		
		switch (platform) {
		case Windows_32:
			/*versionNum = ReadXmlFile.parserXml(ReadXmlFile.REPOFILENAME, "windows", BROWSERNAME, ReadXmlFile.VERSION);*/
			if (driverPath == null)
				System.setProperty(BROWSERDRVNAME, GlobalUtils.DEFAULT_DRIVER_PATH + GlobalUtils.PATH_DELIMITER 
						           + BROWSERWIN32PATH + BROWSERWINFILENAME);
			else
				System.setProperty(BROWSERDRVNAME, driverPath + GlobalUtils.PATH_DELIMITER 
						           + BROWSERWIN32PATH + BROWSERWINFILENAME);
			break;
		case Windows_64:
			/*versionNum = ReadXmlFile.parserXml(ReadXmlFile.REPOFILENAME, "windows", BROWSERNAME, ReadXmlFile.VERSION);*/
			if (driverPath == null)
				System.setProperty(BROWSERDRVNAME, GlobalUtils.DEFAULT_DRIVER_PATH + GlobalUtils.PATH_DELIMITER 
						           + BROWSERWIN64PATH + BROWSERWINFILENAME);	
			else
				System.setProperty(BROWSERDRVNAME, driverPath + GlobalUtils.PATH_DELIMITER 
						           + BROWSERWIN64PATH + BROWSERWINFILENAME);
			break;
		case Linux_32:
			/*versionNum = ReadXmlFile.parserXml(ReadXmlFile.REPOFILENAME, "linux", BROWSERNAME, ReadXmlFile.VERSION);*/
			if (driverPath == null)
				System.setProperty(BROWSERDRVNAME, GlobalUtils.DEFAULT_DRIVER_PATH + GlobalUtils.PATH_DELIMITER 
						           + BROWSERLINUX32PATH + BROWSERLINUXFILENAME);	
			else
				System.setProperty(BROWSERDRVNAME, driverPath + GlobalUtils.PATH_DELIMITER 
						           + BROWSERLINUX32PATH + BROWSERLINUXFILENAME);
			break;
		case Linux_64:
			/*versionNum = ReadXmlFile.parserXml(ReadXmlFile.REPOFILENAME, "linux", BROWSERNAME, ReadXmlFile.VERSION);*/
			if (driverPath == null)
				System.setProperty(BROWSERDRVNAME, GlobalUtils.DEFAULT_DRIVER_PATH + GlobalUtils.PATH_DELIMITER 
						           + BROWSERLINUX64PATH + BROWSERLINUXFILENAME);	
			else
				System.setProperty(BROWSERDRVNAME, driverPath + GlobalUtils.PATH_DELIMITER 
						           + BROWSERLINUX64PATH + BROWSERLINUXFILENAME);
			break;
		case Mac_OS_X_32:
			/*versionNum = ReadXmlFile.parserXml(ReadXmlFile.REPOFILENAME, "osx", BROWSERNAME, ReadXmlFile.VERSION);*/
			if (driverPath == null)
				System.setProperty(BROWSERDRVNAME, GlobalUtils.DEFAULT_DRIVER_PATH + GlobalUtils.PATH_DELIMITER 
						           + BROWSEROSX32PATH + BROWSER0SXFILENAME);
			else 
				System.setProperty(BROWSERDRVNAME, driverPath + GlobalUtils.PATH_DELIMITER 
						           + BROWSEROSX32PATH + BROWSER0SXFILENAME);				
			break;
		case Mac_OS_X_64:
			/*versionNum = ReadXmlFile.parserXml(ReadXmlFile.REPOFILENAME, "osx", BROWSERNAME, ReadXmlFile.VERSION);*/
			if (driverPath == null)
				System.setProperty(BROWSERDRVNAME, GlobalUtils.DEFAULT_DRIVER_PATH + GlobalUtils.PATH_DELIMITER 
						           + BROWSEROSX64PATH + BROWSER0SXFILENAME);	
			else
				System.setProperty(BROWSERDRVNAME, driverPath + GlobalUtils.PATH_DELIMITER 
						           + BROWSEROSX64PATH + BROWSER0SXFILENAME);
			break;
		default:
			throw GlobalUtils.createNotInitializedException("operating system is not supported ");
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Nullable
	public WebDriver getWebDriver() {
		return super.getWebDriver();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebDriver getWebDriverInstance() {
		WebDriver retVal = getWebDriver();
		if (null == retVal) {
			setChromeDriverSystemEnv();
			ChromeOptions ops = new ChromeOptions();
			boolean opsAdded = false;
			if (getBrowserProfile().getStartArguments().size()>0) {
				ops.addArguments(getBrowserProfile().getStartArguments());
				opsAdded = true;
			}
			if (getBrowserProfile().isPreserveCookiesOnExecutions()) {

				ops.addArguments("--user-data-dir="
						+ getBrowserProfile().getTestCaseChromeUserDataDir());
				opsAdded = true;

			}

			if (opsAdded)
				retVal = new ChromeDriver(ops);
			else
				retVal = new ChromeDriver();
			setWebDriver(retVal);
		}
		return retVal;

	}

}
