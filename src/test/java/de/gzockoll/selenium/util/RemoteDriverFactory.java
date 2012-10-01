package de.gzockoll.selenium.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class RemoteDriverFactory implements DriverFactory {
	public static final String HUB_URL = System.getProperty("HUB",
			"http://localhost:4444/wd/hub");

	@Override
	public WebDriver getDriver(String browser, DesiredCapabilities cap) {
		try {
			WebDriver driver = new Augmenter().augment(new RemoteWebDriver(
					new URL(HUB_URL), cap));
			return driver;
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
}
