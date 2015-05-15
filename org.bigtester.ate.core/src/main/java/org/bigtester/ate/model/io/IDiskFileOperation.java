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
package org.bigtester.ate.model.io;
 
/**
 * The Interface IDiskFileOperation.
 *
 * @author Peidong Hu
 */
public interface IDiskFileOperation {
	
	/**
	 * Save to single file.
	 */
	void saveToSingleFile();
	
	/**
	 * Save to multiple files.
	 */
	void saveToMultipleFiles();
	
	/**
	 * Import from single file.
	 */
	void importFromSingleFile();
	
	/**
	 * Import from multiple files.
	 */
	void importFromMultipleFiles();
	
	/**
	 * Sets the import file name with absolute path.
	 *
	 * @param fileNameWithAbsolutePath the new import file name with absolute path
	 */
	void setImportFileNameWithAbsolutePath(String fileNameWithAbsolutePath);
	
	/**
	 * Sets the export file name with absolute path.
	 *
	 * @param fileNameWithAbsolutePath the new export file name with absolute path
	 */
	void setExportFileNameWithAbsolutePath(String fileNameWithAbsolutePath);
}
