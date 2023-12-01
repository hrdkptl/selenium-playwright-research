package playwright;

import java.util.Arrays;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.deque.html.axecore.playwright.AxeBuilder;
import com.deque.html.axecore.results.AxeResults;
import com.deque.html.axecore.results.Check;
import com.deque.html.axecore.results.CheckedNode;
import com.deque.html.axecore.results.Rule;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class GoogleSearchTest extends Parent {

    @BeforeTest
    public void setUp() {
    	super.setUp();
        page = browser.newPage();
        page.navigate("https://www.google.com");
    }

    @Test
    public void performSearch() {
    	
    	checkAccessibility(page);

        Locator inputField =  page.locator("//textarea[@title='Search']");
        inputField.fill("OpenAI");
        inputField.press("Enter");
        
        //page.fill("//textarea[@title='Search']", "OpenAI");
        //page.click("(//input[@value='Google Search' and @role='button' and @type='submit'])[2]");
        
        System.out.println("################# NEW PAGE #######################");
        // Wait for the results stats to be visible
        ElementHandle resultsStats = page.waitForSelector("#result-stats");
        String resultsText = resultsStats.innerText();

        // Extract the number of results from the text
        String resultsNumberString = resultsText.split(" ")[1].replace(",", "").replace(".", ""); // Removing commas and dots for locales that use them

        // Parse the extracted string to a long
        long numberOfResults = Long.parseLong(resultsNumberString);

        // Check if the number of results is greater than 100,000,000
        Assert.assertTrue(numberOfResults > 100000000, "Number of results is not greater than 100,000,000");
        
        checkAccessibility(page);

    }

    @AfterMethod
    public void closePage() {
        if (page != null) {
            page.close();
        }
    }
    
    public void checkAccessibility(Page page) {
    	
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
