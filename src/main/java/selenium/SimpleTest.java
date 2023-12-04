package selenium;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SimpleTest extends Parent {

    @BeforeMethod
    public void setUp() {
    	super.setUp();
        driver.get("http://the-internet.herokuapp.com");
    }

    @Test
    public void checkPageTitle() {
        String title = driver.getTitle();
        Assert.assertEquals(title, "The Internet");
    }

    @AfterMethod
    public void closePage() {
        if (driver != null) {
        	driver.close();
        }
    }
}
