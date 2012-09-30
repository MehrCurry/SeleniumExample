package de.gzockoll.selenium.util;

import static de.gzockoll.selenium.util.Configuration.*;
import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
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

import com.thoughtworks.selenium.SeleneseTestBase;

@SuppressWarnings("javadoc")
@RunWith(Parallelized.class)
public abstract class AbstractSeleniumTestBase extends SeleneseTestBase {

	private static final Logger logger = LoggerFactory
			.getLogger(AbstractSeleniumTestBase.class);
	protected static final String SCREEN_SHOTS_RESULTS_PATH = "./target/site/images";
	protected static final DateFormat df = new SimpleDateFormat(
			"yyyyMMdd-HHmmss");
	static {
		new File(SCREEN_SHOTS_RESULTS_PATH).mkdirs();
	}
	private static Queue<WebDriver> tasks = new LinkedList<WebDriver>();
	private String browser;
	protected Environment environment;
	protected WebDriver driver;

	protected abstract Logger getLogger();

	public AbstractSeleniumTestBase(String browser, Environment environment) {
		super();
		this.browser = browser;
		this.environment = environment;
	}

	@Rule
	public TestRule rule = new TestWatcher() {
		@Override
		protected void succeeded(Description description) {
			if (Configuration.SCREENSHOT_ON_SUCCESS) {
				getLogger().debug("Creating screenshot from: " + description);
				if (driver != null) {
					String scrFilename = getScreenshotBaseFilename(description)
							+ "-final.png";
					createScreenshot(scrFilename);
				}
			}
		}

		@Override
		protected void failed(Throwable e, Description description) {
			getLogger().debug("Creating screenshot from: " + description);
			if (e != null)
				getLogger().debug("Caught", e);
			if (driver != null) {
				String scrFilename = getScreenshotBaseFilename(description)
						+ "-failed" + ".png";
				createScreenshot(scrFilename);
			}
		}

		/**
		 * @param description
		 * @return
		 */
		private String getScreenshotBaseFilename(Description description) {
			String scrFilename = description.getClassName().replace('.', '/')
					+ "/" + df.format(new Date()) + "-" + browser + "-"
					+ description.getMethodName();
			return scrFilename;
		}

		/**
		 * @param scrFilename
		 */
		private void createScreenshot(String scrFilename) {
			try {
				File scrFile = ((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.FILE);
				File outputFile = new File(SCREEN_SHOTS_RESULTS_PATH,
						scrFilename);
				FileUtils.copyFile(scrFile, outputFile);
				getLogger()
						.info(outputFile.toString() + " screenshot created.");
			} catch (Exception ex) {
				getLogger()
						.error("Error writing screenshot " + scrFilename, ex);
			}
		};
	};

	@Parameters
	public static Collection browsersStrings() {
		return Configuration.browsersStrings();
	}

	@Override
	@Before
	public void setUp() throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setBrowserName(browser);
		cap.setJavascriptEnabled(true);
		driver = new Augmenter().augment(new RemoteWebDriver(new URL(HUB_URL),
				cap));
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		assertNotNull(driver);
	}

	@Override
	@After
	public void tearDown() throws Exception {
		tasks.add(driver);
		getLogger().debug("Preparing to close driver: " + driver);
	}

	@AfterClass
	public static void quitDrivers() throws InterruptedException {
		while (!tasks.isEmpty()) {
			WebDriver d = tasks.remove();
			logger.debug("Closing driver: " + d);
			d.quit();
		}
	}

	protected boolean isTextPresent(String text) {
		try {
			boolean result = driver.findElement(By.xpath("//*[contains(.,'"
					+ text + "')]")) != null;
			return result;
		} catch (Exception e) {
			getLogger().warn("Exception: " + e);
			return false;
		}

	}

	protected abstract String getBaseUrl();
}
