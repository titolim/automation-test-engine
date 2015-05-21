package org.bigtester.ate.test.experimentals;

import org.apache.commons.lang3.StringUtils;
import org.bigtester.ate.model.page.atewebdriver.MyChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class SendTextSlashHandling {
	/** An empty string constant */
	  public static final String EMPTY = "";
  @Test
  public void f() throws InterruptedException {
	  MyChromeDriver.setChromeDriverSystemEnv();
	  WebDriver driver = new ChromeDriver();
	  driver.get("http://www.w3schools.com/tags/tryit.asp?filename=tryhtml_textarea");
	  driver.switchTo().frame(driver.findElement(new By.ById("iframeResult")));
	  WebElement textbox = driver.findElement(new By.ByTagName("textarea"));
	  //textbox.clear();
	  String text = "abcdikdkdkdkdkkdkdkdkdkdkkdkdkdkkdkdkdkdkdkdk https://github.com/bigtester/automation-test-engine";
	  JavascriptExecutor js = (JavascriptExecutor) driver;
	  js.executeScript("return (function() {document.getElementsByTagName('textarea')[0].value='"+text+"'})();" );
	  //js.executeScript("return (function() {alert(arguments);})();", text, textbox);
	  
	  Thread.sleep(6000);
	  //driver.quit();
  }
  
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

		     String strings[] = new String[count];
		     int begin = 0;

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
		           strings[i] = EMPTY;
		        else
		           strings[i] = string.substring(begin, end);

		        // update the begining index
		        begin = end + 1;
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
     int count = 0;
     int idx = 0;

     while ((idx = string.indexOf(substring, idx)) != -1)
     {
        idx++;
        count++;
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
  public static int count(final String string, final char c)
  {
     return count(string, String.valueOf(c));
  }
}
