package de.gzockoll.selenium;

import static de.gzockoll.selenium.Configuration.HUB_URL;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

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

@SuppressWarnings("javadoc")
@RunWith(Parallelized.class)
public class SomeTestsIT {
    protected static final String SCREEN_SHOTS_RESULTS_PATH = "./screenshots";
    protected static final DateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");
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
            logger.debug("Creating screenshot from: " + description);
            if (e != null)
                logger.debug("Caught", e);
            if (driver != null) {
                String scrFilename = df.format(new Date()) + "-" + browser + "-" + description.getClassName() + "-"
                        + description.getMethodName() + ".png";
                try {
                    File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                    File outputFile = new File(SCREEN_SHOTS_RESULTS_PATH, scrFilename);
                    logger.info(scrFilename + " screenshot created.");
                    FileUtils.copyFile(scrFile, outputFile);
                } catch (Exception ex) {
                    logger.error("Error writing screenshot " + scrFilename, ex);
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
        driver = new Augmenter().augment(new RemoteWebDriver(new URL(HUB_URL), cap));
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
            boolean result = driver.findElement(By.xpath("//*[contains(.,'" + text + "')]")) != null;
            return result;
        } catch (Exception e) {
            logger.warn("Exception: " + e);
            return false;
        }

    }
}
