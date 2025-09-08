package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import utils.DriverFactory;

public class BaseTest {
    protected static WebDriver driver;

    @BeforeClass
    public void suiteSetup() {
  

        driver = DriverFactory.initDriver();
        driver.get("https://www.saucedemo.com/"); 

    }

    @AfterClass
    public void suiteTearDown() {
        if (driver != null) {
            DriverFactory.quitDriver();
        }
    }
}

