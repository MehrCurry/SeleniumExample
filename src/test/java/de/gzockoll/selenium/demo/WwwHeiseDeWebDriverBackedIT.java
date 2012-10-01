package de.gzockoll.selenium.demo;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.gzockoll.selenium.util.AbstractWebDriverBackedTestBase;
import de.gzockoll.selenium.util.Environment;

@SuppressWarnings("javadoc")
public class WwwHeiseDeWebDriverBackedIT extends
		AbstractWebDriverBackedTestBase {

	public WwwHeiseDeWebDriverBackedIT(WebDriver driver, Environment environment) {
		super(driver, environment);
	}

	/**
	 * @return the logger
	 */
	@Override
	protected Logger getLogger() {
		return logger;
	}

	private static Logger logger = LoggerFactory
			.getLogger(WwwHeiseDeWebDriverBackedIT.class);

	@Test
	public void testImpressum() throws Exception {
		selenium.open("/");
		verifyEquals(
				"IT-News, c't, iX, Technology Review, Telepolis | heise online",
				selenium.getTitle());
	}

	@Override
	protected String getBaseUrl() {
		return environment.getAdminUrl();
	}
}
