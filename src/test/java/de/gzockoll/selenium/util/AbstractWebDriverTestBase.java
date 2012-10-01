package de.gzockoll.selenium.util;

import org.openqa.selenium.WebDriver;

public abstract class AbstractWebDriverTestBase extends
		AbstractSeleniumTestBase {

	public AbstractWebDriverTestBase(WebDriver driver, Environment environment) {
		super(driver, environment);
	}
}
