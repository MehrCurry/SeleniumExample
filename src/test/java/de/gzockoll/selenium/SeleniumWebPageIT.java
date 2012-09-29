package de.gzockoll.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("javadoc")
public class SeleniumWebPageIT extends AbstractWebDriverTestBase {
	private String baseUrl = "http://code.google.com/";

	/**
	 * Create a new SomeTestsIT.
	 * 
	 * @param browser
	 */
	public SeleniumWebPageIT(String browser) {
		super(browser);
	}

	/**
	 * @return the logger
	 */
	@Override
	protected Logger getLogger() {
		return logger;
	}

	private static Logger logger = LoggerFactory
			.getLogger(SeleniumWebPageIT.class);

	@Test
	public void testNormalFlow() throws Exception {
		driver.get(baseUrl + "/p/selenium/wiki/SeIDEReleaseNotes");
		driver.findElement(By.linkText("Project Home")).click();
		driver.findElement(By.cssSelector("#wikicontent > ul > li > a"))
				.click();
		driver.findElement(By.linkText("Documentation")).click();
		driver.findElement(
				By.xpath("(//a[contains(text(),'Introduction')])[2]")).click();
		assertTrue(isTextPresent("download the IDE from the SeleniumHQ"));
	}
}
