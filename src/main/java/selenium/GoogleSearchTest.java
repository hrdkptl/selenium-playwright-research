package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GoogleSearchTest extends Parent {

    @BeforeTest
    public void setUp() {
    	super.setUp();
        driver.get("https://www.google.com");
    }

    @Test
    public void performSearch() {
        WebElement searchBox = driver.findElement(By.xpath("//textarea[@title='Search']"));
        //searchBox.sendKeys("OpenAI" + Keys.ENTER);
        searchBox.sendKeys("OpenAI");
        
        WebElement searchButton = driver.findElement(By.xpath("(//input[@value='Google Search' and @role='button' and @type='submit'])[2]"));
        searchButton.click();
        
        // Wait for the results to be displayed
        WebElement resultsStats = driver.findElement(By.id("result-stats"));
        String resultsText = resultsStats.getText();
        
        // Extract the number of results from the text
        String resultsNumberString = resultsText.split(" ")[1].replace(",", "");
        
        // Parse the extracted string to a long
        long numberOfResults = Long.parseLong(resultsNumberString);
        
        // Check if the number of results is greater than 100,000,000
        Assert.assertTrue(numberOfResults > 100000000, "Number of results is not greater than 100,000,000");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.close();
        }
    }
}
