package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.AccessibilityTester;

public class GoogleSearchTest extends Parent {

    @BeforeMethod
    public void setUp() {
    	super.setUp();
        driver.get("https://www.google.com");
    }
    
    @Test
    public void performSearchWithClickButton() {
    	AccessibilityTester.checkAccessibility(driver, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
    	
        WebElement searchBox = driver.findElement(By.xpath("//textarea[@title='Search']"));
        searchBox.sendKeys("OpenAI");
        WebElement searchButton = driver.findElement(By.xpath("(//input[@value='Google Search' and @role='button' and @type='submit'])[2]"));
        searchButton.click();
        
        //---- Results Page
        AccessibilityTester.checkAccessibility(driver, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
        
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

    @Test
    public void performSearchWithEnterKey() {
    	AccessibilityTester.checkAccessibility(driver, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
    	
        WebElement searchBox = driver.findElement(By.xpath("//textarea[@title='Search']"));
        searchBox.sendKeys("OpenAI" + Keys.ENTER);
        
        //---- Results Page
        AccessibilityTester.checkAccessibility(driver, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
        
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
    
    @Test
    public void performSearchWithJSscript() {
    	AccessibilityTester.checkAccessibility(driver, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
    	
        WebElement searchBox = driver.findElement(By.xpath("//textarea[@title='Search']"));
        searchBox.sendKeys("OpenAI");
        WebElement searchButton = driver.findElement(By.xpath("(//input[@value='Google Search' and @role='button' and @type='submit'])[2]"));
        
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchButton); // bypass dynamic content (like suggestion lists)
        
        //---- Results Page
        AccessibilityTester.checkAccessibility(driver, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
        
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
    public void closePage() {
        if (driver != null) {
        	driver.close();
        }
    }
}
