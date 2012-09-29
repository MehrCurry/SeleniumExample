package de.gzockoll.selenium;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("javadoc")
public class WwwHeiseDeWebDriverBackedIT extends AbstractWebDriverBackedTest {

	public WwwHeiseDeWebDriverBackedIT(String browser, Environment environment) {
		super(browser, environment);
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
