package playwright;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import utils.AccessibilityTester;

public class SimpleLoginTest extends Parent {

    @BeforeTest
    public void setUp() {
    	super.setUp();
        page = browser.newPage();
        page.navigate("http://the-internet.herokuapp.com/login");
    }

    @Test
    public void Login() {
    	AccessibilityTester.checkAccessibility(page, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());;
    	
    	page.fill("#username", "tomsmith");
        page.fill("#password", "SuperSecretPassword!");
        page.click("button[type='submit']");

        boolean isSuccessMessageDisplayed = page.isVisible(".flash.success");
        Assert.assertTrue(isSuccessMessageDisplayed, "Success message not displayed");

        page.click("a[href='/logout']");

        String loginPageHeaderText = page.innerText("h2");
        Assert.assertEquals(loginPageHeaderText, "Login Page", "Login page header text mismatch");

    }
    
    @AfterMethod
    public void closePage() {
        if (page != null) {
            page.close();
        }
    }
}
