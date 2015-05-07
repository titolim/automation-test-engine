package org.bigtester.ate.test.model.page.page;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
@ContextConfiguration(locations = {"classpath:bigtesterTestNG/testproject.xml", "classpath:bigtesterTestNG/testSuite01/homePageValidation.xml" })

public class NewTest extends AbstractTestNGSpringContextTests {
  @Test
  public void f() {
	  
	  System.out.println("hell");
  }
}
