package playwright;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;

import utils.AccessibilityTester;

public class GoogleSearchTest extends Parent {

    @BeforeMethod
    public void setUp() {
    	super.setUp();
    	page = browser.newPage();
    	page.navigate("https://www.google.com");
    }

    @Test
    public void performSearchWithClickButton() {
    	AccessibilityTester.checkAccessibility(page, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
    	
    	//page.fill("//textarea[@title='Search']", "OpenAI");
    	Locator searchInputField = page.locator("//textarea[@title='Search']");
    	searchInputField.fill("OpenAI");
    	
        //page.click("(//input[@value='Google Search' and @role='button' and @type='submit'])[2]");
    	Locator searchButton = page.locator("(//input[@value='Google Search' and @role='button' and @type='submit'])[2]");
    	searchButton.click();
        
        // ----- Another Page
        AccessibilityTester.checkAccessibility(page, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
        
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

    @Test
    public void performSearchWithEnterKey() {
    	AccessibilityTester.checkAccessibility(page, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
    	
    	Locator searchInputField = page.locator("//textarea[@title='Search']");
    	searchInputField.fill("OpenAI");
    	searchInputField.press("Enter");
        
        // ----- Another Page
        AccessibilityTester.checkAccessibility(page, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
        
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
    
    @Test
    public void performSearchWithJSscript() {
    	AccessibilityTester.checkAccessibility(page, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
    	
    	//page.fill("//textarea[@title='Search']", "OpenAI");
    	Locator searchInputField = page.locator("//textarea[@title='Search']");
    	searchInputField.fill("OpenAI");
    	
        //page.click("(//input[@value='Google Search' and @role='button' and @type='submit'])[2]");
    	Locator searchButton = page.locator("(//input[@value='Google Search' and @role='button' and @type='submit'])[2]");
    	page.evaluate("element => element.click()", searchButton.first().elementHandle());

        // ----- Another Page
        AccessibilityTester.checkAccessibility(page, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
        
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
