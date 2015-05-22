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
package org.bigtester.ate.test.experimentals;

import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;

import org.bigtester.ate.GlobalUtils;

/**
 * The Class GetCurrentExecutionPath.
 *
 * @author Peidong Hu
 */
public class GetCurrentExecutionPath {
	
  /**
   * F.
   */
  public void foo() {
	  URL thisPath = ClassLoader.getSystemClassLoader().getResource(".");
	  if (thisPath == null) throw GlobalUtils.createInternalError("jvm");
	  URI fileURI = GlobalUtils.fixFileURL(thisPath);
		String retVal = Paths.get(fileURI).toString();// NOPMD
	  System.out.println(retVal);//NOPMD
  }
}
