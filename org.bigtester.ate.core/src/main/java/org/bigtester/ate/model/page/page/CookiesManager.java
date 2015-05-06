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
package org.bigtester.ate.model.page.page;

import java.io.File;
import java.io.FileInputStream; 
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream; 
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.model.io.IDiskFileOperation;
import org.bigtester.ate.model.page.PageModelBase;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.eclipse.aether.util.StringUtils;
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.Cookie;

// TODO: Auto-generated Javadoc
/**
 * The Class CookiesManager.
 *
 * @author Peidong Hu
 */
public class CookiesManager extends PageModelBase implements IDiskFileOperation {
	
	/** The import file name with absolute path. */
	@Nullable
	private String importFileNameWithAbsolutePath;
	
	/** The export file name with absolute path. */
	@Nullable
	private String exportFileNameWithAbsolutePath;
	
	/** The import folder name with absolute path. */
	@Nullable
	private String importFolderNameWithAbsolutePath;
	
	/** The export folder name with absoluate path. */
	@Nullable
	private String exportFolderNameWithAbsoluatePath;

	/** The cookies. */
	private Map<String, Cookie> cookies = new ConcurrentHashMap<String, Cookie>();//NOPMD

	// if domain is "", it means all cookies in the browser storing in manager.
	/** The domain. */
	private String domain;

	/**
	 * Instantiates a new cookies manager.
	 *
	 * @param myWd the my wd
	 * @param domain the domain
	 * @param cookies the cookies
	 */
	public CookiesManager(IMyWebDriver myWd, String domain, Set<Cookie> cookies) {
		super(myWd);
		this.domain = domain;
		for (Cookie coo : cookies) {
			if (this.domain.equalsIgnoreCase(domain)) {
				this.cookies.put(coo.getName(), coo);
			}
		}
	}

	/**
	 * Instantiates a new cookies manager.
	 *
	 * @param myWd the my wd
	 * @param cookies the cookies
	 */
	public CookiesManager(IMyWebDriver myWd, Set<Cookie> cookies) {
		super(myWd);
		this.domain = "";
		for (Cookie coo : cookies) {

			this.cookies.put(coo.getName(), coo);

		}
	}

	/**
	 * Gets the domain.
	 *
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * Sets the domain.
	 *
	 * @param domain the new domain
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * Gets the cookies.
	 *
	 * @return the cookies
	 */
	public Map<String, Cookie> getCookies() {
		return cookies;
	}

	/**
	 * Sets the cookies.
	 *
	 * @param cookies the cookies
	 */
	public void setCookies(Map<String, Cookie> cookies) {
		this.cookies = cookies;
	}

	/**
	 * Gets the import file name with absolute path.
	 *
	 * @return the import file name with absolute path
	 */
	public String getImportFileNameWithAbsolutePath() {

		final String importFileNameWithAbsolutePath2 = importFileNameWithAbsolutePath;
		String retVal;
		if (importFileNameWithAbsolutePath2 == null) {
			retVal =  "";
		} else {
			retVal = importFileNameWithAbsolutePath2;
		}
		return retVal;
	}

	
	/**
	 * {@inheritDoc}
	 */
	public void setImportFileNameWithAbsolutePath(
			String importFileNameWithAbsolutePath) {
		this.importFileNameWithAbsolutePath = importFileNameWithAbsolutePath;
	}

	/**
	 * Gets the export file name with absolute path.
	 *
	 * @return the export file name with absolute path
	 */
	public String getExportFileNameWithAbsolutePath() {
		final String exportFileNameWithAbsolutePath2 = exportFileNameWithAbsolutePath;
		String retVal;
		
		if (exportFileNameWithAbsolutePath2 == null) {
			retVal = "";
		} else {

			retVal = exportFileNameWithAbsolutePath2;
		}
		return retVal;
	}

	
	/**
	 * {@inheritDoc}
	 */
	public void setExportFileNameWithAbsolutePath(
			String exportFileNameWithAbsolutePath) {
		this.exportFileNameWithAbsolutePath = exportFileNameWithAbsolutePath;
	}

	/**
	 * Gets the import folder name with absolute path.
	 *
	 * @return the import folder name with absolute path
	 */
	public String getImportFolderNameWithAbsolutePath() {
		final String importFolderNameWithAbsolutePath2 = importFolderNameWithAbsolutePath;
		String retVal;
		if (importFolderNameWithAbsolutePath2 == null) {
			retVal = "";
		} else {

			retVal = importFolderNameWithAbsolutePath2;
		}
		return retVal;
	}

	/**
	 * Sets the import folder name with absolute path.
	 *
	 * @param importFolderNameWithAbsolutePath the new import folder name with absolute path
	 */
	public void setImportFolderNameWithAbsolutePath(
			String importFolderNameWithAbsolutePath) {
		this.importFolderNameWithAbsolutePath = importFolderNameWithAbsolutePath;
	}

	/**
	 * Gets the export folder name with absoluate path.
	 *
	 * @return the export folder name with absoluate path
	 */
	public String getExportFolderNameWithAbsoluatePath() {
		final String exportFolderNameWithAbsoluatePath2 = exportFolderNameWithAbsoluatePath;
		
		String retVal;
		if (exportFolderNameWithAbsoluatePath2 == null) {
			retVal = "";
		} else {

			retVal = exportFolderNameWithAbsoluatePath2;
		}
		
		return retVal;
	}

	/**
	 * Sets the export folder name with absoluate path.
	 *
	 * @param exportFolderNameWithAbsoluatePath the new export folder name with absoluate path
	 */
	public void setExportFolderNameWithAbsoluatePath(
			String exportFolderNameWithAbsoluatePath) {
		this.exportFolderNameWithAbsoluatePath = exportFolderNameWithAbsoluatePath;
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveToSingleFile() {

		try {
			ObjectOutput out;
			out = new ObjectOutputStream(new FileOutputStream(
					this.getExportFileNameWithAbsolutePath()));
			out.writeObject(getCookies());
			out.close();
		} catch (IOException e) {
			throw GlobalUtils.createInternalError("File saving operation", e);
		}

	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveToMultipleFiles() {
		if (StringUtils.isEmpty(this.getExportFolderNameWithAbsoluatePath())) {
			throw GlobalUtils
					.createNotInitializedException("export foldername with full path");
		} else {
			// TODO
		}
	}

	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void importFromSingleFile() {
		if (StringUtils.isEmpty(this.getImportFileNameWithAbsolutePath())) {
			throw GlobalUtils
					.createNotInitializedException("import filename with full path");
		} else {
			try {
				File file = new File(this.getExportFileNameWithAbsolutePath());
				ObjectInputStream ins = new ObjectInputStream(
						new FileInputStream(file));
				final Map<String, Cookie> temp = (Map<String, Cookie>) ins.readObject();
				if (null == temp) {
					ins.close();
					throw GlobalUtils.createNotInitializedException("cookie loading initial condition");
				}
				this.cookies = temp;
				ins.close();
			} catch (IOException | ClassNotFoundException e) {
				throw GlobalUtils.createInternalError("File loading operation", e);
			}
			for (Map.Entry<String, Cookie> entry : cookies.entrySet())
				super.getMyWd().getWebDriverInstance().manage()
						.addCookie(entry.getValue());
		}
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void importFromMultipleFiles() {
		if (StringUtils.isEmpty(this.getExportFileNameWithAbsolutePath())) {
			throw GlobalUtils
					.createNotInitializedException("import foldername with full path");
		} else {
			// TODO
		}

	}

}
