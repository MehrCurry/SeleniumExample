package de.gzockoll.selenium;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("javadoc")
public class WwwHeiseDeWebDriverBackedIT extends AbstractWebDriverBackedTest {
	private String baseUrl = "http://www.heise.de";

	/**
	 * Create a new SomeTestsIT.
	 * 
	 * @param browser
	 */
	public WwwHeiseDeWebDriverBackedIT(String browser) {
		super(browser);
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

	@Override
	public String getBaseUrl() {
		return baseUrl;
	}

	@Test
	public void testImpressum() throws Exception {
		selenium.open("/");
		selenium.click("link=Impressum");
		selenium.waitForPageToLoad("30000");
		verifyEquals("Amtsgericht Hannover HRA 26709\nUST-Id.: DE 813 501 887",
				selenium.getText("//div[@id='mitte_text']/p[21]"));
	}
}
