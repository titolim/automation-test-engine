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
package org.bigtester.ate.model.page.elementfind;

import org.eclipse.jdt.annotation.Nullable;

// TODO: Auto-generated Javadoc
/**
 * This class BaseTestWindowFinderImpl defines ....
 * @author Peidong Hu
 *
 */
public class BaseCookieFinderImpl extends AbstractTestObjectFinderImpl {

	/** The title. */
	private String domain = "";
	
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getDomain() {
		return domain;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Nullable
	public <T> T getCapability(Class<T> type) {
		if (this instanceof ICookieFinder) {
			return (T) this; //NOPMD
		} else {
			return null;
		}
	}

}
