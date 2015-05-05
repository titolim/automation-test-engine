package org.bigtester.ate.model.page.page;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.model.io.IDiskFileOperation;
import org.eclipse.aether.util.StringUtils;
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.Cookie;

public class CookiesManager implements IDiskFileOperation{
	@Nullable
	private String importFileNameWithAbsolutePath;
	@Nullable
	private String exportFileNameWithAbsolutePath;
	@Nullable
	private String importFolderNameWithAbsolutePath;
	@Nullable
	private String exportFolderNameWithAbsoluatePath;
	
	private Map<String, Cookie> cookies = new ConcurrentHashMap<String, Cookie>();
	
	//if domain is "", it means all cookies in the browser storing in manager.
	private String domain;
	
	public CookiesManager(String domain, Set<Cookie> cookies) {
		this.domain = domain;
		for (Cookie coo: cookies) {
			if (this.domain.equalsIgnoreCase(domain)) {
				this.cookies.put(coo.getName(), coo);
			}
		}
	}
	
	public CookiesManager(Set<Cookie> cookies) {
		this.domain = "";
		for (Cookie coo: cookies) {
			
				this.cookies.put(coo.getName(), coo);
			
		}
	}
	
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public Map<String, Cookie> getCookies() {
		return cookies;
	}
	public void setCookies(Map<String, Cookie> cookies) {
		this.cookies = cookies;
	}

	public String getImportFileNameWithAbsolutePath() {
		
		final String importFileNameWithAbsolutePath2 = importFileNameWithAbsolutePath;
		if (importFileNameWithAbsolutePath2 == null) {
			return "";
		} else {
			return importFileNameWithAbsolutePath2;
		}
	}

	public void setImportFileNameWithAbsolutePath(
			String importFileNameWithAbsolutePath) {
		this.importFileNameWithAbsolutePath = importFileNameWithAbsolutePath;
	}

	public String getExportFileNameWithAbsolutePath() {
		final String exportFileNameWithAbsolutePath2 = exportFileNameWithAbsolutePath;
		if (exportFileNameWithAbsolutePath2 == null) {
			return "";
		} else {
			
			return exportFileNameWithAbsolutePath2;
		}
	}

	public void setExportFileNameWithAbsolutePath(
			String exportFileNameWithAbsolutePath) {
		this.exportFileNameWithAbsolutePath = exportFileNameWithAbsolutePath;
	}

	public String getImportFolderNameWithAbsolutePath() {
		final String importFolderNameWithAbsolutePath2 = importFolderNameWithAbsolutePath;
		if (importFolderNameWithAbsolutePath2 == null) {
			return "";
		} else {
			
			return importFolderNameWithAbsolutePath2;
		}
	}

	public void setImportFolderNameWithAbsolutePath(
			String importFolderNameWithAbsolutePath) {
		this.importFolderNameWithAbsolutePath = importFolderNameWithAbsolutePath;
	}

	public String getExportFolderNameWithAbsoluatePath() {
		final String exportFolderNameWithAbsoluatePath2 = exportFolderNameWithAbsoluatePath;
		if (exportFolderNameWithAbsoluatePath2 == null) {
			return "";
		} else {
			
			return exportFolderNameWithAbsoluatePath2;
		}
	}

	public void setExportFolderNameWithAbsoluatePath(
			String exportFolderNameWithAbsoluatePath) {
		this.exportFolderNameWithAbsoluatePath = exportFolderNameWithAbsoluatePath;
	}

	@Override
	public void saveToSingleFile() {
		if (StringUtils.isEmpty(this.getExportFileNameWithAbsolutePath())) {
			throw GlobalUtils.createNotInitializedException("import filename with full path");
		} else {
			//TODO 
		}
		
	}

	@Override
	public void saveToMultipleFiles() {
		if (StringUtils.isEmpty(this.getExportFolderNameWithAbsoluatePath())) {
			throw GlobalUtils.createNotInitializedException("export foldername with full path");
		} else {
			//TODO 
		}
	}

	@Override
	public void importFromSingleFile() {
		if (StringUtils.isEmpty(this.getImportFileNameWithAbsolutePath())) {
			throw GlobalUtils.createNotInitializedException("import filename with full path");
		} else {
			//TODO 
		}
		
	}

	@Override
	public void importFromMultipleFiles() {
		if (StringUtils.isEmpty(this.getExportFileNameWithAbsolutePath())) {
			throw GlobalUtils.createNotInitializedException("import foldername with full path");
		} else {
			//TODO 
		}
		
	}

	
}
