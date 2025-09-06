package utils;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static WebDriver initDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        WebDriver webDriver = new ChromeDriver(options);
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        webDriver.manage().window().maximize();

        driver.set(webDriver);
        return webDriver;
    }

    public static void quitDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver != null) {
            webDriver.quit();
            driver.remove();
        }
    }
}
