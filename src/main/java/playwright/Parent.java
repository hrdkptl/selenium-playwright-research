package playwright;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class Parent {
	
    protected Playwright playwright;
    protected Browser browser;
    protected Page page;

    @BeforeTest
    public void setUp() {
        playwright = Playwright.create();
        
        LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setHeadless(false);
        
        launchOptions.setChannel("msedge");
        
        browser = playwright.chromium().launch(launchOptions);
    }
    
    @AfterTest
    public void tearDown() {
        try {
            if (browser != null) {
                browser.close();
            }
        } finally {
            if (playwright != null) {
                playwright.close();
            }
        }
    }

}
