package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class ProductPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By productList = By.className("inventory_item");
    private By productName = By.className("inventory_item_name");
    private By productPrice = By.className("inventory_item_price");
    private By productImage = By.cssSelector(".inventory_item img");
    private By sortDropdown = By.cssSelector(".product_sort_container");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void selectSortOption(String sortOption) {
        Select dropdown = new Select(wait.until(ExpectedConditions.elementToBeClickable(sortDropdown)));
        dropdown.selectByVisibleText(sortOption);
    }

    public List<WebElement> getProductNameElements() { 
    	wait.until(ExpectedConditions.visibilityOfElementLocated(productName)); 
    	return driver.findElements(productName); 
    	
    }
    public List<WebElement> getProductPriceElements() { 
    	wait.until(ExpectedConditions.visibilityOfElementLocated(productPrice));
    	return driver.findElements(productPrice);
    }
    public List<String> getAllProductNames() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productName));
        List<WebElement> elements = driver.findElements(productName);
        List<String> names = new ArrayList<>();
        for (WebElement el : elements) {
            names.add(el.getText().trim());
        }
        return names;
    }

    public List<Double> getAllProductPrices() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productPrice));
        List<WebElement> elements = driver.findElements(productPrice);
        List<Double> prices = new ArrayList<>();
        for (WebElement el : elements) {
            prices.add(Double.parseDouble(el.getText().replace("$", "").trim()));
        }
        return prices;
    }

    public void addProductToCart(String productName) {
        By buttonLocator = By.xpath(
            "//div[@class='inventory_item']//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//button"
        );
        try {
            WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(buttonLocator));
            addButton.click();
            wait.until(ExpectedConditions.textToBePresentInElementLocated(buttonLocator, "Remove"));
        } catch (TimeoutException e) {
            throw new RuntimeException("Product '" + productName + "' not found on product page.", e);
        }
    }


    public void removeProductFromCart(String productName) {
        try {
            By removeButtonLocator = By.xpath(
                "//div[@class='inventory_item']//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//button[text()='Remove']"
            );
            WebElement removeButton = wait.until(ExpectedConditions.elementToBeClickable(removeButtonLocator));
            removeButton.click();

            By addToCartButtonLocator = By.xpath(
                "//div[@class='inventory_item']//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//button[text()='Add to cart']"
            );
            wait.until(ExpectedConditions.elementToBeClickable(addToCartButtonLocator));
        } catch (TimeoutException e) {
            throw new RuntimeException("Failed to remove product '" + productName + "' from cart within timeout period", e);
        }
    }

    public String getAddToCartButtonText(String productName) {
        By buttonLocator = By.xpath(
            "//div[@class='inventory_item']//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//button"
        );
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(buttonLocator));
        return button.getText().trim();
    }

    public void clickOnProduct(String productName) {
        WebElement product = getProductElementByName(productName);
        if (product != null) {
            product.findElement(By.className("inventory_item_name")).click();
        } else {
            throw new RuntimeException("Product not found to click: " + productName);
        }
    }

    public String getProductDetailName() {
        WebElement detailName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".inventory_details_name")));
        return detailName.getText().trim();
    }

    public boolean allProductsHaveDetails() {
        List<WebElement> products = driver.findElements(productList);
        for (WebElement product : products) {
            boolean displayed = product.findElement(productName).isDisplayed()
                    && product.findElement(productPrice).isDisplayed()
                    && product.findElement(productImage).isDisplayed()
                    && product.findElement(By.tagName("button")).isDisplayed();
            if (!displayed) {
                return false;
            }
        }
        return true;
    }

    private WebElement getProductElementByName(String name) {
        List<WebElement> products = driver.findElements(productList);
        for (WebElement product : products) {
            String pName = product.findElement(productName).getText().trim();
            if (pName.equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }

    public void openCart() {
        try {
            WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(By.id("shopping_cart_container")));
            cartIcon.click();
            wait.until(ExpectedConditions.urlContains("cart"));
        } catch (Exception e) {
            driver.get("https://www.saucedemo.com/cart.html");
            wait.until(ExpectedConditions.urlContains("cart"));
        }
    }
}
