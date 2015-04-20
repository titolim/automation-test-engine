package org.bigtester.ate.experimentals;

import java.util.List;

import org.bigtester.ate.model.page.atewebdriver.BrowserWindow;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class frameHandling {
	@Test
	public void f() {
		WebDriver ffd = new FirefoxDriver();
		ffd.get("http://www.jobillico.com/en/job-offer/serti-informatique-inc/junior-.net-developer/542283?ji_visitsrc=118");
		// List<WebElement> iframes =
		// ffd.findElements(By.xpath("//iframe[contains(@title,'Facebook Cross')]"));
		for (int i = 0; i < 2; i++) {
			try {
				List<WebElement> iframes = ffd.findElements(By
						.tagName("iframe"));
				for (WebElement frame1 : iframes) {

					ffd.switchTo().defaultContent();
					// ffd.switchTo().frame(frame1);
					String abc = frame1.getAttribute("name");
					System.out.println(abc);
				}
			} catch (StaleElementReferenceException frameDeletedError) {
				continue;
			}
		}
		ffd.quit();

		WebDriver ffd2 = new FirefoxDriver();
		ffd2.get("http://www.indeed.ca/viewjob?jk=c7a97fc38b31f5cb&q=qa&l=Montr%C3%A9al%2C+QC&tk=19jav19pfaf8nfh5");
		List<WebElement> iframes2 = ffd.findElements(By.tagName("iframe"));
		return;
	}
}
