package org.bigtester.ate.model.page.page;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Iterator;
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

public class CookiesManager extends PageModelBase implements IDiskFileOperation {
	@Nullable
	private String importFileNameWithAbsolutePath;
	@Nullable
	private String exportFileNameWithAbsolutePath;
	@Nullable
	private String importFolderNameWithAbsolutePath;
	@Nullable
	private String exportFolderNameWithAbsoluatePath;

	private Map<String, Cookie> cookies = new ConcurrentHashMap<String, Cookie>();

	// if domain is "", it means all cookies in the browser storing in manager.
	private String domain;

	public CookiesManager(IMyWebDriver myWd, String domain, Set<Cookie> cookies) {
		super(myWd);
		this.domain = domain;
		for (Cookie coo : cookies) {
			if (this.domain.equalsIgnoreCase(domain)) {
				this.cookies.put(coo.getName(), coo);
			}
		}
	}

	public CookiesManager(IMyWebDriver myWd, Set<Cookie> cookies) {
		super(myWd);
		this.domain = "";
		for (Cookie coo : cookies) {

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

	@Override
	public void saveToMultipleFiles() {
		if (StringUtils.isEmpty(this.getExportFolderNameWithAbsoluatePath())) {
			throw GlobalUtils
					.createNotInitializedException("export foldername with full path");
		} else {
			// TODO
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void importFromSingleFile() {
		if (StringUtils.isEmpty(this.getImportFileNameWithAbsolutePath())) {
			throw GlobalUtils
					.createNotInitializedException("import filename with full path");
		} else {
			try {
				File file = new File(this.getExportFileNameWithAbsolutePath());
				ObjectInputStream in = new ObjectInputStream(
						new FileInputStream(file));
				final Map<String, Cookie> temp = (Map<String, Cookie>) in.readObject();
				if (null == temp) {
					in.close();
					throw GlobalUtils.createNotInitializedException("cookie loading initial condition");
				}
				this.cookies = temp;
				in.close();
			} catch (IOException | ClassNotFoundException e) {
				throw GlobalUtils.createInternalError("File loading operation", e);
			}
			for (Map.Entry<String, Cookie> entry : cookies.entrySet())
				super.getMyWd().getWebDriverInstance().manage()
						.addCookie(entry.getValue());
		}
	}

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
