package de.gzockoll.selenium;

import java.util.Arrays;
import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

public class Configuration {
	public static final String HUB_URL = "http://localhost:4444/wd/hub";
	static {
		System.setProperty("webdriver.chrome.driver",
				"/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");
	}

	@Parameters
	public static Collection browsersStrings() {
		return Arrays.asList(new Object[][] { { "firefox" }, { "chrome" } });
	}

}
