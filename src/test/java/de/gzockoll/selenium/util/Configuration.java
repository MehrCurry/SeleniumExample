package de.gzockoll.selenium.util;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Configuration {
	public static final String HUB_URL = System.getProperty("HUB",
			"http://localhost:4444/wd/hub");
	public static final boolean SCREENSHOT_ON_SUCCESS = Boolean
			.parseBoolean(System.getProperty("SCREENSHOT_ON_SUCCESS", "false"));
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

	public static Collection browsersStrings() {
		return Arrays.asList(new Object[][] { { "firefox", getEnvironment() },
				{ "chrome", getEnvironment() } });
	}

	public static Environment getEnvironment() {
		String sut = System.getProperty("TEST_ENVIRONMENT", "qs");
		assertThat(environments.containsKey(sut), is(true));
		return environments.get(sut);
	}
}
