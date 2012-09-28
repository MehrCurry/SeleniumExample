package de.gzockoll.selenium;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("javadoc")
public class SomeTestsIT extends AbstractSeleniumTest {
    /**
     * Create a new SomeTestsIT.
     * 
     * @param browser
     */
    public SomeTestsIT(String browser) {
        super(browser);
    }

    /**
     * @return the logger
     */
    protected Logger getLogger() {
        return logger;
    }

    private static Logger logger = LoggerFactory.getLogger(SomeTestsIT.class);

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

}
