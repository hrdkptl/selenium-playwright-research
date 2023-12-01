package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SimpleLoginTest extends Parent {

    @BeforeTest
    public void setUp() {
    	super.setUp();
        driver.get("http://the-internet.herokuapp.com/login");
    }

    @Test
    public void Login() {
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
    public void tearDown() {
        if (driver != null) {
            driver.close();
        }
    }
}
