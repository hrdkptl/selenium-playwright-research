package playwright;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.microsoft.playwright.ElementHandle;

public class GoogleSearchTest extends Parent {

    @BeforeTest
    public void setUp() {
    	super.setUp();
        page = browser.newPage();
        page.navigate("https://www.google.com");
    }

    @Test
    public void performSearch() {
        //page.fill("//textarea[@title='Search']", "OpenAI" + Keys.ENTER);

        page.fill("//textarea[@title='Search']", "OpenAI");
        page.click("(//input[@value='Google Search' and @role='button' and @type='submit'])[2]");

        // Wait for the results stats to be visible
        ElementHandle resultsStats = page.waitForSelector("#result-stats");
        String resultsText = resultsStats.innerText();

        // Extract the number of results from the text
        String resultsNumberString = resultsText.split(" ")[1].replace(",", "").replace(".", ""); // Removing commas and dots for locales that use them

        // Parse the extracted string to a long
        long numberOfResults = Long.parseLong(resultsNumberString);

        // Check if the number of results is greater than 100,000,000
        Assert.assertTrue(numberOfResults > 100000000, "Number of results is not greater than 100,000,000");
    }

    @AfterMethod
    public void closePage() {
        if (page != null) {
            page.close();
        }
    }
}
