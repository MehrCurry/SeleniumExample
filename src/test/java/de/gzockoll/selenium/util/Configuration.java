package de.gzockoll.selenium.util;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Configuration {
	public static final boolean SCREENSHOT_ON_SUCCESS = Boolean
			.parseBoolean(System.getProperty("SCREENSHOT_ON_SUCCESS", "false"));
	private static final String browsers = System.getProperty("Browsers",
			"firefox,chrome");
	private static final Mode mode = Mode.valueOf(System.getProperty(
			"Browsers", "local").toUpperCase());

	public static Map<String, Environment> environments = new HashMap<String, Environment>();

	static {
		System.setProperty("webdriver.chrome.driver",
				"/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");

		environments.put(
				"qs",
				EnvironmentBuilder.environment()
						.withAdminUrl("http://www.heise.de")
						.withPmiUrl("http://code.google.com").build());
	}

	public static Collection<Object[]> driverConfigurations() {
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setJavascriptEnabled(true);
		Collection<Object[]> results = new ArrayList<Object[]>();
		for (String browser : browsers.split(",")) {
			WebDriver driver = null;
			cap.setBrowserName(browser);
			switch (mode) {
			case LOCAL:
				driver = new LocalDriverFactory().getDriver(browser, cap);
				break;
			case REMOTE:
				driver = new RemoteDriverFactory().getDriver(browser, cap);
				break;
			}
			results.add(new Object[] { driver, getEnvironment() });
		}
		return results;
	}

	public static Environment getEnvironment() {
		String sut = System.getProperty("TEST_ENVIRONMENT", "qs");
		assertThat(environments.containsKey(sut), is(true));
		return environments.get(sut);
	}
}
