package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Constructor
    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    // Locators
    private By productList = By.className("inventory_item");
    private By productName = By.className("inventory_item_name");
    private By productPrice = By.className("inventory_item_price");
    private By productImage = By.cssSelector(".inventory_item img");
    private By addToCartButton = By.xpath(".//button[contains(text(),'Add to cart')]");
    private By removeFromCartButton = By.xpath(".//button[contains(text(),'Remove')]");
    private By productDetailName = By.cssSelector(".inventory_details_name");
    private By sortDropdown = By.cssSelector(".product_sort_container");
//    private By cartIcon = By.id("shopping_cart_container");   // ✅ Added

    // Actions

    public void selectSortOption(String sortOption) {
        new Select(driver.findElement(sortDropdown)).selectByVisibleText(sortOption);
    }

    public List<String> getAllProductNames() {
        List<WebElement> elements = driver.findElements(productName);
        List<String> names = new ArrayList<>();
        for (WebElement el : elements) {
            names.add(el.getText().trim());
        }
        return names;
    }
    public List<WebElement> getProductNameElements() {
        // Wait for at least one product name to be visible before returning
        wait.until(ExpectedConditions.visibilityOfElementLocated(productName));
        return driver.findElements(productName);
    }

    public List<WebElement> getProductPriceElements() {
        // Wait for at least one product price to be visible before returning
        wait.until(ExpectedConditions.visibilityOfElementLocated(productPrice));
        return driver.findElements(productPrice);
    }


    public List<Double> getAllProductPrices() {
        List<WebElement> elements = driver.findElements(productPrice);
        List<Double> prices = new ArrayList<>();
        for (WebElement el : elements) {
            prices.add(Double.parseDouble(el.getText().replace("$", "").trim()));
        }
        return prices;
    }
    public void addProductToCart(String productName) {
        // Construct a product-specific button locator
        By productButtonLocator = By.xpath("//div[@class='inventory_item']//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//button");

        // Click the product's "Add to cart" button
        WebElement addButton = driver.findElement(productButtonLocator);
        addButton.click();

        // Wait for the button text to change to "Remove"
        wait.until(ExpectedConditions.textToBePresentInElementLocated(productButtonLocator, "Remove"));
        
    }

//    public void removeProductFromCart(String productName) throws InterruptedException {
//        // Dynamic locator for the product's button (either "Add to cart" or "Remove")
//        By productButtonLocator = By.xpath("//div[@class='inventory_item']//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//button");
//        Thread.sleep(2000);
//        WebElement removeButton = wait.until(ExpectedConditions.elementToBeClickable(productButtonLocator));
//        removeButton.click();
//
//        // Now wait until the button text becomes "Add to cart" (after removal)
//        wait.until(ExpectedConditions.textToBePresentInElementLocated(productButtonLocator, "Add to cart"));
//    }
    
    public void removeProductFromCart(String productName) {
        try {
            By productButtonLocator = By.xpath("//div[@class='inventory_item']//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//button[text()='Remove']");
            
            WebElement removeButton = wait.until(ExpectedConditions.elementToBeClickable(productButtonLocator));
            removeButton.click();
            
            By addToCartButtonLocator = By.xpath("//div[@class='inventory_item']//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//button[text()='Add to cart']");
            wait.until(ExpectedConditions.elementToBeClickable(addToCartButtonLocator));
            
        } catch (TimeoutException e) {
            throw new RuntimeException("Failed to remove product '" + productName + "' from cart within timeout period", e);
        }
    }


    

//    public String getAddToCartButtonText(String productName) {
//        WebElement product = getProductElementByName(productName);
//        if (product != null) {
//            return product.findElement(By.tagName("button")).getText().trim();
//        }
//        throw new RuntimeException("Product not found: " + productName);
//    }

    public String getAddToCartButtonText(String productName) {
        By productButtonLocator = By.xpath("//div[@class='inventory_item']//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//button");
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(productButtonLocator));
        return button.getText().trim();
    }
    public void clickOnProduct(String productName) throws InterruptedException {
    	Thread.sleep(2000);
        WebElement product = getProductElementByName(productName);
        if (product != null) {
            product.findElement(By.className("inventory_item_name")).click();
        } else {
            throw new RuntimeException("Product not found to click: " + productName);
        }
    }

    public String getProductDetailName() throws InterruptedException {
    	Thread.sleep(2000);
        return driver.findElement(productDetailName).getText().trim();
    }

    public boolean allProductsHaveDetails() throws InterruptedException {
    	Thread.sleep(2000);
        List<WebElement> products = driver.findElements(productList);
        for (WebElement product : products) {
            if (!(product.findElement(this.productName).isDisplayed()
                    && product.findElement(this.productPrice).isDisplayed()
                    && product.findElement(productImage).isDisplayed()
                    && product.findElement(By.tagName("button")).isDisplayed())) {
                return false;
            }
        }
        return true;
    }

    private WebElement getProductElementByName(String name) {
        List<WebElement> products = driver.findElements(productList);
        for (WebElement product : products) {
            String productName = product.findElement(this.productName).getText().trim();
            if (productName.equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }
    // ✅ Missing Method Added: Open Cart
//    public void openCart() {
//        driver.findElement(cartIcon).click();
//    }
    
 // In ProductPage class
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
