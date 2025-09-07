package base;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import utils.DriverFactory;

public class BaseTest {
    protected static WebDriver driver;

    @BeforeSuite
    public void suiteSetup() {
  

         driver = DriverFactory.initDriver();
        driver.get("https://www.saucedemo.com/"); // ✅ Make sure this is correct login URL

    }

    @AfterSuite
    public void suiteTearDown() {
        if (driver != null) {
            DriverFactory.quitDriver();
        }
    }
}

