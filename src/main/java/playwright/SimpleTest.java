package playwright;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import utils.AccessibilityTester;

public class SimpleTest extends Parent {

    @BeforeTest
    public void setUp() {	
    	super.setUp();
        page = browser.newPage();
        page.navigate("http://the-internet.herokuapp.com");
    }

    @Test
    public void checkPageTitle() {
    	AccessibilityTester.checkAccessibility(page, this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName());
    	
        String title = page.title();
        Assert.assertEquals(title, "The Internet");
    }
    
    @AfterMethod
    public void closePage() {
        if (page != null) {
            page.close();
        }
    }
}
