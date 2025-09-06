package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {

    private WebDriver driver;
    private WebDriverWait wait;

    
    private By cartTitle = By.className("title");
    private By checkoutButton = By.id("checkout");
    private By continueShoppingButton = By.id("continue-shopping");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isOnCartPage() {
        WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(cartTitle));
        return title.getText().equalsIgnoreCase("Your Cart");
    }

    public void removeProduct(String productName) {
        By removeButton = By.xpath("//div[text()='" + productName + "']/ancestor::div[@class='cart_item']//button");
        WebElement removeBtn = wait.until(ExpectedConditions.elementToBeClickable(removeButton));
        removeBtn.click();
    }

    public boolean isProductInCart(String productName) {
        By productLocator = By.xpath("//div[@class='inventory_item_name' and text()='" + productName + "']");
        return !driver.findElements(productLocator).isEmpty();
    }

    public void clickContinueShopping() {
        WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(continueShoppingButton));
        continueBtn.click();
    }

    public void clickCheckout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout")));
        checkoutBtn.click();

        // Wait for navigation to checkout page
        wait.until(ExpectedConditions.urlContains("checkout-step-one"));
    }

}

