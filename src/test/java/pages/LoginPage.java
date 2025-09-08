package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By usernameInput = By.id("user-name");
    private By passwordInput = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorMessage = By.xpath("//h3[contains(text(),'Epic sadface')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Actions
    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
        driver.findElement(usernameInput).clear();
        driver.findElement(usernameInput).sendKeys(username);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        driver.findElement(loginButton).click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public String getErrorMessage() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
            return driver.findElement(errorMessage).getText();
        } catch (TimeoutException e) {
            return "";  
        }
    }

    public boolean isInventoryPageDisplayed() {
        return driver.getCurrentUrl().contains("/inventory.html");
    }
    

    public boolean isErrorMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
            return driver.findElement(errorMessage).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}
