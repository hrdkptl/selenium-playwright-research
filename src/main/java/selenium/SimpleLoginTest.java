package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.AccessibilityTester;

public class SimpleLoginTest extends Parent {

    @BeforeMethod
    public void setUp() {
    	super.setUp();
        driver.get("http://the-internet.herokuapp.com/login");
    }

    @Test
    public void Login() {
    	AccessibilityTester.checkAccessibility(driver, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
    	
        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));

        usernameField.sendKeys("tomsmith");
        passwordField.sendKeys("SuperSecretPassword!");
        loginButton.click();

        WebElement successMessage = driver.findElement(By.cssSelector(".flash.success"));
        Assert.assertTrue(successMessage.isDisplayed());

        WebElement logoutButton = driver.findElement(By.cssSelector("a[href='/logout']"));
        logoutButton.click();

        WebElement loginPageHeader = driver.findElement(By.cssSelector("h2"));
        Assert.assertEquals(loginPageHeader.getText(), "Login Page");
    }
    
    @AfterMethod
    public void closePage() {
        if (driver != null) {
        	driver.close();
        }
    }
}
