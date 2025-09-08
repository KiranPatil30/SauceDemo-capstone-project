
package utils;

import java.time.Duration;
import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();


    public static WebDriver initDriver() {
        if (driver.get() == null) {
            System.out.println("[DriverFactory] Initializing ChromeDriver for thread: ");

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--incognito");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-save-password-bubble");
            options.addArguments("--disable-infobars");

            options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
            options.setExperimentalOption("useAutomationExtension", false);

            WebDriver chromeDriver = new ChromeDriver(options);
            chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            chromeDriver.manage().window().maximize();

            driver.set(chromeDriver);
        } else {
            System.out.println("[DriverFactory] Reusing existing driver for thread: ");
        }

        return driver.get();
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver != null) {
            System.out.println("[DriverFactory] Quitting driver for thread: " );
            webDriver.quit();
            driver.remove(); 
        } else {
            System.out.println("[DriverFactory] No driver found for thread: ");
        }
    }
}
