package org.bigtester.ate.experimentals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class frameHandling {
  @Test
  public void f() {
	  	WebDriver ffd = new FirefoxDriver();
	  	ffd.get("https://hire.jobvite.com/CompanyJobs/Careers.aspx?k=Job&c=qtX9Vfwe&j=o5GT0fwo&s=Indeed");
		List<WebElement> iframes = ffd.findElements(By.tagName("iframe"));
		
		WebDriver ffd2 = new FirefoxDriver();
	  	ffd2.get("http://www.indeed.ca/viewjob?jk=c7a97fc38b31f5cb&q=qa&l=Montr%C3%A9al%2C+QC&tk=19jav19pfaf8nfh5");
		List<WebElement> iframes2 = ffd.findElements(By.tagName("iframe"));
		return;
  }
}
