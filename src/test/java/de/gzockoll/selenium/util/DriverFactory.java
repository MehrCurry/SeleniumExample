package de.gzockoll.selenium.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public interface DriverFactory {
	WebDriver getDriver(String browser, DesiredCapabilities cap);
}
