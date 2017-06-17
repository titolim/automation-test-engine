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

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.ProxySelector;
import java.util.Calendar;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.bigtester.ate.GlobalUtils;
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

// TODO: Auto-generated Javadoc
/**
 * The Class WebDriverBase defines ....
 * 
 * @author Peidong Hu
 */
abstract public class AbstractWebDriverBase implements IMyWebDriver{

	/** The web driver. */
	@Nullable
	@XStreamOmitField
	protected WebDriver webDriver;
	
	/** The browser windows monitor. */
	@Nullable
	@Autowired
	private IMultiWindowsHandler multiWindowsHandler;
	
	
	/**
	 * Gets the web driver.
	 *
	 * @return the webDriver
	 */
	@Nullable
	public  WebDriver getWebDriver() {
		return webDriver;
	}
	/**
	 * Creates the driver.
	 *
	 * @return the web driver
	 */
	abstract public WebDriver getWebDriverInstance();
	/**
	 * Sets the web driver.
	 *
	 * @param webDriver
	 *            the webDriver to set
	 */
	public final void setWebDriver(final WebDriver webDriver) {
		
		EventFiringWebDriver eDriver  = new EventFiringWebDriver(webDriver);
//		multiWindowsHandler = new MultiWindowsHandler(this);
		eDriver.register((MultiWindowsHandler)GlobalUtils.getTargetObject(multiWindowsHandler));
		this.webDriver = eDriver;
	}

	/**
	 * Instantiates a new web driver base.
	 */
	public AbstractWebDriverBase() {
		// Following code is to defensively make sure that default proxySelector
		// is not null.
		// Selenium driver-binary-downloader-maven-plugin incorrectly set
		// defaultProxySelector to null which causes error when creating
		// httpclient.
		// Issue has been opened for Selenium
		// driver-binary-downloader-maven-plugin at
		// https://github.com/Ardesco/selenium-standalone-server-plugin/issues/23
		ProxySelector pSel = ProxySelector.getDefault();
		if (null == pSel) {
			try {
				Class<?> cls = Class.forName("sun.net.spi.DefaultProxySelector");
				if (cls != null && ProxySelector.class.isAssignableFrom(cls)) {
					ProxySelector.setDefault((ProxySelector) cls.newInstance());
				}
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException err) {
				throw GlobalUtils.createInternalError("creating default proxy selector"); //NOPMD
			}
		}

	}

	/**
	 * @return the browserWindowsMonitor
	 */
	public IMultiWindowsHandler getMultiWindowsHandler() {
		final IMultiWindowsHandler browserWindowsMonitor2 = multiWindowsHandler;
		if (browserWindowsMonitor2 == null) {
			throw GlobalUtils.createNotInitializedException("browser Windows monitor");
		} else {
			return browserWindowsMonitor2;
		}
	}

	/**
	 * @param browserWindowsMonitor the browserWindowsMonitor to set
	 */
	public void setMultiWindowsHandler(IMultiWindowsHandler multiWindowsHandler) {
		this.multiWindowsHandler = multiWindowsHandler;
	}
	
	@SuppressWarnings("null")//NOPMD
	private String generateRandomFilename(String filename) {
		Calendar cld = Calendar.getInstance();
		filename=filename.trim();
		int indexOfEnter = filename.indexOf('\n');
		if (indexOfEnter>0)filename = filename.substring(0, indexOfEnter); 
		filename = filename.replaceAll("\\s", "_")
				.replaceAll(":", "").replaceAll("/", ".")
				+ ".jpg";
		filename = "logs/images/" + cld.get(Calendar.YEAR) + "-" + cld.get(Calendar.MONTH)
				+ "-" + cld.get(Calendar.DAY_OF_MONTH) + "-"
				+ cld.get(Calendar.HOUR_OF_DAY) + "-" + cld.get(Calendar.MINUTE)
				+ "-" + cld.get(Calendar.SECOND) + "-" + filename;
		return filename;
	}

	private boolean createScreenCaptureJPEG(String filename) {
		boolean retVal = false;
		try {
			BufferedImage img = getScreenAsBufferedImage();
			File output = new File(filename);
			ImageIO.write(img, "jpg", output);
			retVal = true;
		} catch (IOException | AWTException e) {
			//e.printStackTrace();
			retVal= false;
		}
		return retVal;
	}

	@SuppressWarnings("null")
	private BufferedImage getScreenAsBufferedImage() throws AWTException {
		BufferedImage img = null;

		Robot rob;
		rob = new Robot();
		Toolkit tlk = Toolkit.getDefaultToolkit();
		Rectangle rect = new Rectangle(tlk.getScreenSize());
		img = rob.createScreenCapture(rect);

		return img;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("null")
	public Optional<String> saveScreenShot(Optional<String> pathFileName) {
		String filename = generateRandomFilename(pathFileName
				.orElse(getWebDriverInstance().getCurrentUrl()));
		Optional<String> retVal = Optional.empty();
		if (createScreenCaptureJPEG(filename)) {
			retVal =  Optional.of(filename);
		}
		return retVal;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("null")
	public Optional<String> saveScreenShot() {
		return saveScreenShot(Optional.ofNullable(getWebDriverInstance().getCurrentUrl()));
	}
	
	

}
