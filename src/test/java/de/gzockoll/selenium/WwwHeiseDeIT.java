package de.gzockoll.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("javadoc")
public class WwwHeiseDeIT extends AbstractWebDriverTestBase {
	private String baseUrl = "http://www.heise.de/";

	/**
	 * Create a new SomeTestsIT.
	 * 
	 * @param browser
	 */
	public WwwHeiseDeIT(String browser) {
		super(browser);
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
		driver.get(baseUrl + "/");
		driver.findElement(By.linkText("Impressum")).click();
		assertEquals("Amtsgericht Hannover HRA 26709\nUST-Id.: DE 813 501 887",
				driver.findElement(By.xpath("//div[@id='mitte_text']/p[21]"))
						.getText());
	}
}
