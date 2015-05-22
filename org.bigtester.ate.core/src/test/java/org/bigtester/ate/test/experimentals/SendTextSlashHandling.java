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

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.model.page.atewebdriver.MyChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

// TODO: Auto-generated Javadoc
/**
 * The Class SendTextSlashHandling.
 *
 * @author Peidong Hu
 */
public class SendTextSlashHandling {
	
	/**  An empty string constant. */
	  public static final String EMPTY = "";
  
  /**
   * F.
   *
   * @throws InterruptedException the interrupted exception
   */
  //@Test
  public void func() throws InterruptedException {
	  MyChromeDriver.setChromeDriverSystemEnv();
	  WebDriver driver = new ChromeDriver();
	  driver.get("file:///C:/index.html");
	  //driver.switchTo().frame(driver.findElement(new By.ById("iframeResult")));
	  WebElement textbox = driver.findElement(new By.ByTagName("textarea"));
	  //textbox.clear();
	  textbox.getAttribute("name");
	  
	  String text = "abcdikdkdkdkdkkdkdkdkdkdkkdkdkdkkdkdkdkdkdkdk https://github.com/bigtester/automation-test-engine";
	  JavascriptExecutor jst = (JavascriptExecutor) driver;
	  jst.executeScript("arguments[1].value = arguments[0]; ", text, textbox );
	  
	  Thread.sleep(6000);
	  driver.quit();
  }
  
  /**
   * Split.
   *
   * @param string the string
   * @param delim the delim
   * @param limit the limit
   * @return the string[]
   */
  public static String[] split(final String string, final String delim,
		     final int limit)
		  {
		     // get the count of delim in string, if count is > limit 
		     // then use limit for count.  The number of delimiters is less by one
		     // than the number of elements, so add one to count.
		     int count = count(string, delim) + 1;
		     if (limit > 0 && count > limit)
		     {
		        count = limit;
		     }

		     String strings[] = new String[count];//NOPMD
		     int begin = 0;//NOPMD

		     for (int i = 0; i < count; i++)
		     {
		        // get the next index of delim
		        int end = string.indexOf(delim, begin);
		        
		        // if the end index is -1 or if this is the last element
		        // then use the string's length for the end index
		        if (end == -1 || i + 1 == count)
		           end = string.length();

		        // if end is 0, then the first element is empty
		        if (end == 0)
		           strings[i] = EMPTY;//NOPMD
		        else
		           strings[i] = string.substring(begin, end);//NOPMD

		        // update the begining index
		        begin = end + 1;//NOPMD
		     }

		     return strings;
		  }
  /**
   * Split up a string into multiple strings based on a delimiter.
   *
   * @param string  String to split up.
   * @param delim   Delimiter.
   * @return        Array of strings.
   */
  public static String[] split(final String string, final String delim)
  {
     return split(string, delim, -1);
  }

  /**
   * Count the number of instances of substring within a string.
   *
   * @param string     String to look for substring in.
   * @param substring  Sub-string to look for.
   * @return           Count of substrings in string.
   */
  public static int count(final String string, final String substring)
  {
     int count = 0;//NOPMD
     int idx = 0;//NOPMD

     while ((idx = string.indexOf(substring, idx)) != -1)//NOPMD
     {
        idx++;//NOPMD
        count++;//NOPMD
     }

     return count;
  }

  /**
   * Count the number of instances of character within a string.
   *
   * @param string     String to look for substring in.
   * @param c          Character to look for.
   * @return           Count of substrings in string.
   */
  public static int count(final String string, final char c)//NOPMD
  {
	 String ccc = String.valueOf(c);
	 if (null == ccc) throw GlobalUtils.createInternalError("jvm");
     return count(string, ccc);
  }
}
