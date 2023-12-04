package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import utils.Browser;

class Parent {

    protected WebDriver driver;

    @BeforeTest
    public void setUp() {
    	
    	switch (Browser.getCurrentBrowser()) {
        case CHROME:
            System.setProperty("webdriver.chrome.driver", "C:\\selenium-automation\\drivers\\chrome\\chromedriver119.exe");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--remote-allow-origins=*");
            chromeOptions.addArguments("--start-maximized");
            chromeOptions.addArguments("-inprivate");

            driver = new ChromeDriver(chromeOptions);
            break;
        case MSEDGE:
            System.setProperty("webdriver.edge.driver", "C:\\selenium-automation\\drivers\\edge\\msedgedriver119.0.2151.44.exe");

            EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions.addArguments("--remote-allow-origins=*");
            edgeOptions.addArguments("--start-maximized");
            edgeOptions.addArguments("-inprivate");

            driver = new EdgeDriver(edgeOptions);
            break;
        case FIREFOX:
            System.setProperty("webdriver.gecko.driver", "C:\\selenium-automation\\drivers\\firefox\\geckodriver0.33.0.exe");
            // Firefox does not use a binary system property like Chrome so does not need browserPath

            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--start-maximized");
            options.addArguments("-inprivate");

            driver = new FirefoxDriver(options);
            break;
        default:
            throw new IllegalArgumentException("Unsupported browser type.");
    	}
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
