package playwright;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

class Parent {

	protected Playwright playwright;
	protected Browser browser;
	protected Page page;

	@BeforeTest
	public void setUp() {
		playwright = Playwright.create();

		LaunchOptions launchOptions = new BrowserType.LaunchOptions();
		launchOptions.setHeadless(false);
		
		/*
		 * Supported values are "chrome", "chrome-beta", "chrome-dev", "chrome-canary",
		 * "msedge", "msedge-beta", "msedge-dev", "msedge-canary"
		 */
		launchOptions.setChannel(utils.Browser.getCurrentBrowser().getValue());

		browser = playwright.chromium().launch(launchOptions);
	}

	@AfterTest
	public void tearDown() {
		try {
			if (browser != null) {
				browser.close();
			}
		} finally {
			if (playwright != null) {
				playwright.close();
			}
		}
	}
}
