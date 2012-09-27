package de.gzockoll.selenium;

import static de.gzockoll.selenium.Configuration.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Parallelized.class)
public class SomeTestsIT {
	protected static final String SCREEN_SHOTS_RESULTS_PATH = "./screenshots";
	static {
		new File(SCREEN_SHOTS_RESULTS_PATH).mkdirs();
	}
	private static Logger logger = LoggerFactory.getLogger(SomeTestsIT.class);
	private String browser;
	private WebDriver driver;

	@Rule
	public TestRule rule = new TestWatcher() {
		@Override
		protected void failed(Throwable e, Description description) {
			logger.debug("Creating screenshot...");
			if (driver != null) {
				String scrFilename = browser + "-" + description.getClassName()
						+ "-" + description.getMethodName() + ".png";
				File scrFile = ((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.FILE);
				File outputFile = new File(SCREEN_SHOTS_RESULTS_PATH,
						scrFilename);
				logger.info(scrFilename + " screenshot created.");
				try {
					FileUtils.copyFile(scrFile, outputFile);
				} catch (IOException ioe) {
					logger.error("Error copying screenshot after exception.",
							ioe);
				}
			}
		}
	};

	@Parameters
	public static Collection browsersStrings() {
		return Configuration.browsersStrings();
	}

	public SomeTestsIT(String browser) {
		super();
		this.browser = browser;
	}

	@Before
	public void setUp() throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setBrowserName(browser);
		driver = new Augmenter().augment(new RemoteWebDriver(new URL(HUB_URL),
				cap));
		assertNotNull(driver);
	}

	@Test
	@Ignore
	public void testHeise() throws Exception {
		driver.get("http://www.heise.de");
		assertTrue(isTextPresent("heise"));
	}

	@Test
	public void testScreenshot() {
		driver.get("http://www.heise.de");
		fail("Test screenshot");
	}

	@After
	public void tearDown() throws Exception {
		if (driver != null)
			driver.close();
	}

	private boolean isTextPresent(String text) {
		try {
			boolean result = driver.findElement(By.xpath("//*[contains(.,'"
					+ text + "')]")) != null;
			return result;
		} catch (Exception e) {
			logger.warn("Exception: " + e);
			return false;
		}

	}
}
