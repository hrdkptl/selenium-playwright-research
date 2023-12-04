package playwright;

import java.util.Arrays;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.deque.html.axecore.playwright.AxeBuilder;
import com.deque.html.axecore.results.AxeResults;
import com.deque.html.axecore.results.Check;
import com.deque.html.axecore.results.CheckedNode;
import com.deque.html.axecore.results.Rule;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class Parent {

	protected Playwright playwright;
	protected Browser browser;
	protected Page page;

	@BeforeTest
	public void setUp() {
		playwright = Playwright.create();

		LaunchOptions launchOptions = new BrowserType.LaunchOptions();
		launchOptions.setHeadless(false);

		launchOptions.setChannel("msedge");

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

	public void checkAccessibility(Page page) {

		Boolean accessibilityTesting = Boolean.FALSE;
		
		if(accessibilityTesting) {
			AxeResults accessibilityScanResults = new AxeBuilder(page)
					.withTags(Arrays.asList("wcag2a", "wcag2aa", "wcag21a", "wcag21aa"))
					.analyze();

			if (!accessibilityScanResults.getViolations().isEmpty()) {
				System.out.println("Accessibility Violations:");
				for (Rule violation : accessibilityScanResults.getViolations()) {
					System.out.println("Violation Description: " + violation.getDescription());
					System.out.println("Impact: " + violation.getImpact());
					System.out.println("Help URL: " + violation.getHelpUrl());
					System.out.println("Affected Nodes:");
					for (CheckedNode node : violation.getNodes()) {
						System.out.println("  HTML Element: " + node.getHtml());
						System.out.println("  Fix all of the following:");
						for (Check check : node.getAll()) {
							System.out.println("    " + check.getMessage());
						}
						System.out.println("  Fix any of the following:");
						for (Check check : node.getAny()) {
							System.out.println("    " + check.getMessage());
						}
					}
					System.out.println("-----------------------------------------");
				}
			}

			//Assert.assertEquals(accessibilityScanResults.getViolations(), Collections.emptyList());
		}
	}
}
