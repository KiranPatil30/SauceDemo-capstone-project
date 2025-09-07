package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {
    WebDriver driver;

    // Locators
    By firstName = By.id("first-name");
    By lastName = By.id("last-name");
    By postalCode = By.id("postal-code");
    By continueButton = By.id("continue");
    By errorMessage = By.cssSelector(".error-message-container");

    By cancelButton = By.id("cancel");
    By finishButton = By.id("finish");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public void loginAndAddToCartToCheckout(String username, String password) {
        driver.get("https://www.saucedemo.com");
//        driver.findElement(By.id("user-name")).sendKeys(username);
//        driver.findElement(By.id("password")).sendKeys(password);
//        driver.findElement(By.id("login-button")).click();
        new LoginPage(driver).login(username, password);

        // Add product to cart
        ProductPage productPage = new ProductPage(driver);
        productPage.addProductToCart("Sauce Labs Backpack");
        productPage.openCart();

        // Click checkout from cart
        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckout();
    }

    public void enterFirstName(String fname) {
        driver.findElement(firstName).clear();
        driver.findElement(firstName).sendKeys(fname);
    }

    public void enterLastName(String lname) {
        driver.findElement(lastName).clear();
        driver.findElement(lastName).sendKeys(lname);
    }

    public void enterPostalCode(String zip) {
        driver.findElement(postalCode).clear();
        driver.findElement(postalCode).sendKeys(zip);
    }

    public void clickContinue() {
        driver.findElement(continueButton).click();
    }

    public void clickFinish() {
        driver.findElement(finishButton).click();
    }

    public void clickCancel() {
        driver.findElement(cancelButton).click();
    }

    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }

    public boolean isOnCheckoutOverview() {
        return driver.getCurrentUrl().contains("checkout-step-two");
    }

    public boolean isCheckoutComplete() {
        return driver.getCurrentUrl().contains("checkout-complete");
    }
}
