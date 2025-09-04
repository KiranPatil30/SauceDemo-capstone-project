package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductPage {

    private WebDriver driver;

    // Constructor
    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    private By productList = By.className("inventory_item");
    private By productName = By.className("inventory_item_name");
    private By productPrice = By.className("inventory_item_price");
    private By productImage = By.cssSelector(".inventory_item img");
    private By addToCartButtons = By.xpath("//button[contains(text(),'Add to cart')]");

    // Action

    // Returns all products
    public List<WebElement> getAllProducts() {
        return driver.findElements(productList);
    }

    // Checks if all products are displayed
    public boolean isProductListDisplayed() {
        return getAllProducts().size() > 0;
    }

    // Check if all product names are displayed
    public boolean areProductNamesDisplayed() {
        List<WebElement> names = driver.findElements(productName);
        for (WebElement name : names) {
            if (!name.isDisplayed()) return false;
        }
        return true;
    }

    // Check if all product prices are displayed
    public boolean areProductPricesDisplayed() {
        List<WebElement> prices = driver.findElements(productPrice);
        for (WebElement price : prices) {
            if (!price.isDisplayed()) return false;
        }
        return true;
    }

    // Check if all product images are displayed
    public boolean areProductImagesDisplayed() {
        List<WebElement> images = driver.findElements(productImage);
        for (WebElement image : images) {
            if (!image.isDisplayed()) return false;
        }
        return true;
    }

    // Check if all Add to Cart buttons are displayed
    public boolean areAddToCartButtonsDisplayed() {
        List<WebElement> buttons = driver.findElements(addToCartButtons);
        for (WebElement button : buttons) {
            if (!button.isDisplayed()) return false;
        }
        return true;
    }
}
