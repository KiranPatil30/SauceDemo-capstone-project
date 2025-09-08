package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import utils.ConfigReader;
import utils.DriverFactory;

public class BaseTest {
    protected static WebDriver driver;

    @BeforeClass
    public void suiteSetup() {
  

        driver = DriverFactory.initDriver();
        driver.get(ConfigReader.get("baseUrl")); 

    }

    @AfterClass
    public void suiteTearDown() {
        if (driver != null) {
            DriverFactory.quitDriver();
        }
    }
}

