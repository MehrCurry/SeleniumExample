package de.gzockoll.selenium;

import static de.gzockoll.selenium.Configuration.HUB_URL;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.After;
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

@SuppressWarnings("javadoc")
@RunWith(Parallelized.class)
public abstract class AbstractSeleniumTest {
    protected static final String SCREEN_SHOTS_RESULTS_PATH = "./screenshots";
    protected static final DateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");
    static {
        new File(SCREEN_SHOTS_RESULTS_PATH).mkdirs();
    }
    private String browser;
    protected WebDriver driver;

    protected abstract Logger getLogger();

    @Rule
    public TestRule rule = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            getLogger().debug("Creating screenshot from: " + description);
            if (e != null)
                getLogger().debug("Caught", e);
            if (driver != null) {
                String scrFilename = getScreenshotBaseFilename(description) + ".png";
                createScreenshot(scrFilename);
            }
        }

        /**
         * @param description
         * @return
         */
        private String getScreenshotBaseFilename(Description description) {
            String scrFilename = df.format(new Date()) + "-" + browser + "-" + description.getClassName() + "-"
                    + description.getMethodName() + ".png";
            return scrFilename;
        }

        /**
         * @param scrFilename
         */
        private void createScreenshot(String scrFilename) {
            try {
                File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                File outputFile = new File(SCREEN_SHOTS_RESULTS_PATH, scrFilename);
                getLogger().info(scrFilename + " screenshot created.");
                FileUtils.copyFile(scrFile, outputFile);
            } catch (Exception ex) {
                getLogger().error("Error writing screenshot " + scrFilename, ex);
            }
        }

        protected void succeeded(Description description) {
            getLogger().debug("Creating screenshot from: " + description);
            if (driver != null) {
                String scrFilename = getScreenshotBaseFilename(description) + "-final.png";
                createScreenshot(scrFilename);
            }

        };
    };

    @Parameters
    public static Collection browsersStrings() {
        return Configuration.browsersStrings();
    }

    public AbstractSeleniumTest(String browser) {
        super();
        this.browser = browser;
    }

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setBrowserName(browser);
        driver = new Augmenter().augment(new RemoteWebDriver(new URL(HUB_URL), cap));
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        assertNotNull(driver);
    }

    @After
    public void tearDown() throws Exception {
        // if (driver != null)
        // driver.close();
    }

    protected boolean isTextPresent(String text) {
        try {
            boolean result = driver.findElement(By.xpath("//*[contains(.,'" + text + "')]")) != null;
            return result;
        } catch (Exception e) {
            getLogger().warn("Exception: " + e);
            return false;
        }

    }
}
