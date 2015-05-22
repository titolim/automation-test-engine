package org.bigtester.ate.test.experimentals;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import org.bigtester.ate.GlobalUtils;
import org.eclipse.jdt.annotation.Nullable;
import org.testng.annotations.Test;

public class GetCurrentExecutionPath {
	
  @Test
  public void f() {
	  URL thisPath = ClassLoader.getSystemClassLoader().getResource(".");
	  if (thisPath == null) throw GlobalUtils.createInternalError("jvm");
	  URI fileURI = GlobalUtils.fixFileURL(thisPath);
		String retVal = Paths.get(fileURI).toString();// NOPMD
	  System.out.println(retVal);
  }
}
