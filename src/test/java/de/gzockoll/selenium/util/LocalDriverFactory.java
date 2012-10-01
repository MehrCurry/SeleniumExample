package de.gzockoll.selenium.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class LocalDriverFactory implements DriverFactory {

	@Override
	public WebDriver getDriver(String browser, DesiredCapabilities cap) {
		if ("firefox".equalsIgnoreCase(browser))
			return new FirefoxDriver(cap);
		throw new IllegalArgumentException("Unsupported driver name: "
				+ browser);
	}

}
