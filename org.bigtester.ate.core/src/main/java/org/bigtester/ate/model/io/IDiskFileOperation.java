package org.bigtester.ate.model.io;

public interface IDiskFileOperation {
	void saveToSingleFile();
	void saveToMultipleFiles();
	void importFromSingleFile();
	void importFromMultipleFiles();
	void setImportFileNameWithAbsolutePath(String fileNameWithAbsolutePath);
	void setExportFileNameWithAbsolutePath(String fileNameWithAbsolutePath);
}
