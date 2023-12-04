package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import utils.AccessibilityTester;

public class CRSScoreCalculator extends Parent {

    @BeforeTest
    public void setUp() {
    	super.setUp();
        driver.get("https://ircc.canada.ca/english/immigrate/skilled/crs-tool.asp");
    }

    @Test
    public void calculateCRSScore() throws InterruptedException {
    	AccessibilityTester.checkAccessibility(driver, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
    	
        // Fill out the form with example data
        // Marital Status
        new Select(driver.findElement(By.id("q1"))).selectByValue("F"); // Single

        // Age
        new Select(driver.findElement(By.id("q3"))).selectByValue("M"); // 29 years of age

        // Education Level
        new Select(driver.findElement(By.id("q4"))).selectByValue("E"); // Bachelor's degree

        // Canadian degree, diploma or certificate
        new Select(driver.findElement(By.id("q4b"))).selectByValue("A"); // No

        // Language test results
        new Select(driver.findElement(By.id("q5i"))).selectByValue("A"); // Yes, less than two years old

        // Language test taken
        new Select(driver.findElement(By.id("q5i-a"))).selectByValue("B"); // IELTS

        // Enter language test scores (example scores)
        new Select(driver.findElement(By.id("q5i-b-speaking"))).selectByValue("F"); // Speaking
        new Select(driver.findElement(By.id("q5i-b-listening"))).selectByValue("F"); // Listening
        new Select(driver.findElement(By.id("q5i-b-reading"))).selectByValue("F"); // Reading
        new Select(driver.findElement(By.id("q5i-b-writing"))).selectByValue("F"); // Writing

        // Do you have other language results?
        new Select(driver.findElement(By.id("q5ii"))).selectByValue("C"); // not applicable

        // Work Experience in Canada
        new Select(driver.findElement(By.id("q6i"))).selectByValue("A"); // None or less than a year
        // Foreign Work Experience
        new Select(driver.findElement(By.id("q6ii"))).selectByValue("A"); // None or less than a year
        
        // Certificate of qualification from a Canadian province
        new Select(driver.findElement(By.id("q7"))).selectByValue("A"); // No

        // Valid job offer
        new Select(driver.findElement(By.id("q8"))).selectByValue("A"); // No

        // Provincial nomination
        new Select(driver.findElement(By.id("q9"))).selectByValue("A"); // No

        // Sibling in Canada
        new Select(driver.findElement(By.id("q10i"))).selectByValue("A"); // No

        // Submit the form
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();
        
        // Locate the element containing the score
        WebElement scoreElement = driver.findElement(By.xpath("//p[strong[contains(text(), 'Comprehensive Ranking System formula grand total')]]"));
        // Extract the text from the element
        String number = scoreElement.getText().replaceAll("[^0-9]", "");
  
        // Check if the score is greater than 500
        if (Integer.parseInt(number) > 500) {
            System.out.println("PR is possible");
        } else {
            System.out.println("PR not possible");
        }
    }
    
    @AfterMethod
    public void closePage() {
        if (driver != null) {
        	driver.close();
        }
    }
}
