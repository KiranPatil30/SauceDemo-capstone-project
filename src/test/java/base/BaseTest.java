package base;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
    protected static WebDriver driver;

    @BeforeSuite
    public void suiteSetup() {
    	 ChromeOptions options = new ChromeOptions();

         options.addArguments("--disable-features=AutofillServerCommunication");
         options.addArguments("--disable-popup-blocking");
         options.addArguments("--disable-notifications");
         options.addArguments("--incognito"); // optional
         options.setExperimentalOption("prefs", Map.of(
             "credentials_enable_service", false,
             "profile.password_manager_enabled", false
         ));

        driver = new ChromeDriver(options);
        driver.get("https://www.saucedemo.com/"); // ✅ Make sure this is correct login URL

    }

    @AfterSuite
    public void suiteTearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

