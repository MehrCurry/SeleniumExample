package de.gzockoll.selenium.demo;

import org.junit.Test;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.gzockoll.selenium.util.AbstractWebDriverTestBase;
import de.gzockoll.selenium.util.Environment;

@SuppressWarnings("javadoc")
public class SeleniumWebPageIT extends AbstractWebDriverTestBase {

	public SeleniumWebPageIT(String browser, Environment environment) {
		super(browser, environment);
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
		driver.get(getBaseUrl() + "/p/selenium/wiki/SeIDEReleaseNotes");
		driver.findElement(By.linkText("Project Home")).click();
		driver.findElement(By.cssSelector("#wikicontent > ul > li > a"))
				.click();
		driver.findElement(By.linkText("Documentation")).click();
		driver.findElement(
				By.xpath("(//a[contains(text(),'Introduction')])[2]")).click();
		assertTrue(isTextPresent("download the IDE from the SeleniumHQ"));
	}

	@Override
	protected String getBaseUrl() {
		return environment.getPmiUrl();
	}
}
