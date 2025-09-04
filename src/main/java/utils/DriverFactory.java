package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static WebDriver initDriver() {
        WebDriver webDriver = new ChromeDriver(); // Can add ChromeOptions if needed
        webDriver.manage().window().maximize();
        driver.set(webDriver);
        return getDriver();  // ✅ return the driver
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
