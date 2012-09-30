package de.gzockoll.selenium.demo;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.gzockoll.selenium.util.AbstractWebDriverTestBase;
import de.gzockoll.selenium.util.Environment;

@SuppressWarnings("javadoc")
public class WwwHeiseDeIT extends AbstractWebDriverTestBase {

	public WwwHeiseDeIT(String browser, Environment environment) {
		super(browser, environment);
	}

	/**
	 * @return the logger
	 */
	@Override
	protected Logger getLogger() {
		return logger;
	}

	private static Logger logger = LoggerFactory.getLogger(WwwHeiseDeIT.class);

	@Test
	public void testImpressum() throws Exception {
		driver.get(getBaseUrl() + "/");
		assertEquals(
				"IT-News, c't, iX, Technology Review, Telepolis | heise online",
				driver.getTitle());
	}

	@Override
	protected String getBaseUrl() {
		return environment.getAdminUrl();
	}
}
