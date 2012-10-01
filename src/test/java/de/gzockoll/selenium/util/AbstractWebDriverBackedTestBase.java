package de.gzockoll.selenium.util;

import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;

public abstract class AbstractWebDriverBackedTestBase extends
		AbstractSeleniumTestBase {

	public AbstractWebDriverBackedTestBase(WebDriver driver,
			Environment environment) {
		super(driver, environment);
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		selenium = new WebDriverBackedSelenium(driver, getBaseUrl());
	}
}
