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
package org.bigtester.ate.test.data.cookies;

import java.util.HashSet;
import java.util.Set;

import org.openqa.selenium.Cookie;

// TODO: Auto-generated Javadoc
/**
 * This class Cookies defines ....
 * @author Peidong Hu
 *
 */
public class CookiesData {//NOPMD
	/** The cookies. */

	private Set<Cookie> cookies = new HashSet<Cookie>();

	/**
	 * @return the cookies
	 */
	public final Set<Cookie> getCookies() {
		return cookies;
	}

	/**
	 * @param cookies the cookies to set
	 */
	public final void setCookies(Set<Cookie> cookies) {
		this.cookies = cookies;
	}

	/**
	 * @return the cookie1
	 */
	public final Cookie getCookie1() {
		return cookie1;
	}

	/**
	 * @param cookie1 the cookie1 to set
	 */
	public final void setCookie1(Cookie cookie1) {
		this.cookie1 = cookie1;
	}

	/**
	 * @return the cookie2
	 */
	public final Cookie getCookie2() {
		return cookie2;
	}

	/**
	 * @param cookie2 the cookie2 to set
	 */
	public final void setCookie2(Cookie cookie2) {
		this.cookie2 = cookie2;
	}

	/**
	 * @return the cookie3
	 */
	public final Cookie getCookie3() {
		return cookie3;
	}

	/**
	 * @param cookie3 the cookie3 to set
	 */
	public final void setCookie3(Cookie cookie3) {
		this.cookie3 = cookie3;
	}

	/**
	 * @return the cookie4
	 */
	public final Cookie getCookie4() {
		return cookie4;
	}

	/**
	 * @param cookie4 the cookie4 to set
	 */
	public final void setCookie4(Cookie cookie4) {
		this.cookie4 = cookie4;
	}

	/** The cookie1. */
	private Cookie cookie1 = new Cookie("cookie1", "cookie1value");

	/** The cookie2. */
	private Cookie cookie2 = new Cookie("cookie2", "cookie2value");

	/** The cookie3. */
	private Cookie cookie3 = new Cookie("cookie3", "cookie3value");
	
	/** The cookie4. */
	private Cookie cookie4 = new Cookie("cookie3", "cookie4value");
	
	/**
	 * Instantiates a new cookies data.
	 */
	public CookiesData() {
		cookies.add(cookie1);
		cookies.add(cookie2);
		cookies.add(cookie3);
	}
}
