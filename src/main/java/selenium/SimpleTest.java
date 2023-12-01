package selenium;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SimpleTest extends Parent {

    @BeforeTest
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
    public void tearDown() {
        if (driver != null) {
            driver.close();
        }
    }
}
