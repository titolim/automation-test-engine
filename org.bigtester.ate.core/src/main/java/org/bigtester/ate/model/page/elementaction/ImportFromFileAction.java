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
package org.bigtester.ate.model.page.elementaction;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.annotation.ATELogLevel;
import org.bigtester.ate.annotation.ActionLoggable;
import org.bigtester.ate.model.io.IDiskFileOperation;
import org.bigtester.ate.model.page.PageModelBase;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.eclipse.jdt.annotation.Nullable;

// TODO: Auto-generated Javadoc
/**
 * The Class ClickAction defines ....
 * 
 * @author Peidong Hu
 */
public class ImportFromFileAction extends PageModelBase implements
		IFileAction, ITestObjectActionImpl {

	/** The file name with absolute path. */
	private String fileNameWithAbsolutePath;
	
	/** The file not found raise error. */
	private boolean fileNotFoundRaiseError = true;
	/**
	 * Gets the file name with absolute path.
	 *
	 * @return the file name with absolute path
	 */
	public String getFileNameWithAbsolutePath() {
		return fileNameWithAbsolutePath;
	}


	/**
	 * Sets the file name with absolute path.
	 *
	 * @param fileNameWithAbsolutePath the new file name with absolute path
	 */
	public void setFileNameWithAbsolutePath(String fileNameWithAbsolutePath) {
		this.fileNameWithAbsolutePath = fileNameWithAbsolutePath;
	}


	/**
	 * @param myWd
	 */
	public ImportFromFileAction(IMyWebDriver myWd, String fileName) {
		super(myWd);
		if (StringUtils.isEmpty(fileName)) throw GlobalUtils.createNotInitializedException("file Name");
		this.fileNameWithAbsolutePath = fileName;
	}

	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public @Nullable <T> T getCapability(Class<T> type) {
		if (this instanceof IFileAction) {
			return (T) this; //NOPMD
		} else {
			return null;
		}
	}


	

	/**
	 * {@inheritDoc}
	 */
	@Override
	@ActionLoggable (level=ATELogLevel.INFO)
	public void doAction(IDiskFileOperation fileOpr) {
		File temp = new File(getFileNameWithAbsolutePath());
		if (temp.exists() ) {
			
				fileOpr.setImportFileNameWithAbsolutePath(getFileNameWithAbsolutePath());
				fileOpr.importFromSingleFile();
			
		} else {
			if (this.fileNotFoundRaiseError) throw GlobalUtils.createNotInitializedException("import file name");
		}
		
		
	}
	
	/**
	* {@inheritDoc}
	*/
	public String getActionParametersLoggingValue() {
		return "fileNameWithAbsolutePath = " + fileNameWithAbsolutePath;
	}

	/**
	 * @return the fileNotFoundRaiseError
	 */
	public boolean isFileNotFoundRaiseError() {
		return fileNotFoundRaiseError;
	}


	/**
	 * @param fileNotFoundRaiseError the fileNotFoundRaiseError to set
	 */
	public void setFileNotFoundRaiseError(boolean fileNotFoundRaiseError) {
		this.fileNotFoundRaiseError = fileNotFoundRaiseError;
	}

	

}
