package de.gzockoll.selenium;

import org.junit.Before;
import org.openqa.selenium.WebDriverBackedSelenium;

public abstract class AbstractWebDriverBackedTest extends
		AbstractSeleniumTestBase {

	public AbstractWebDriverBackedTest(String browser, Environment environment) {
		super(browser, environment);
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		selenium = new WebDriverBackedSelenium(driver, getBaseUrl());
	}
}
