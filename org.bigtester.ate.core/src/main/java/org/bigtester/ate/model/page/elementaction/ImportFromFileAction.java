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

import org.apache.commons.lang3.StringUtils;
import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.model.io.IDiskFileOperation;
import org.bigtester.ate.model.page.PageModelBase;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.WebDriver;

// TODO: Auto-generated Javadoc
/**
 * The Class ClickAction defines ....
 * 
 * @author Peidong Hu
 */
public class ImportFromFileAction extends PageModelBase implements
		IFileAction, ITestObjectActionImpl {

	private String fileNameWithAbsolutePath;
	public String getFileNameWithAbsolutePath() {
		return fileNameWithAbsolutePath;
	}


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
	public @Nullable <T> T getCapability(Class<T> type) {
		if (this instanceof IFileAction) {
			return (T) this; //NOPMD
		} else {
			return null;
		}
	}


	

	@Override
	public void doAction(IDiskFileOperation fileOpr) {
		fileOpr.setImportFileNameWithAbsolutePath(getFileNameWithAbsolutePath());
		fileOpr.importFromSingleFile();
		
	}

	

}
