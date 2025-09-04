package tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import utils.DriverFactory;

public class ProductTest {
	 WebDriver driver;

	    @BeforeClass
	    public void setUp() {
	        driver = DriverFactory.getDriver();
	        driver.get("https://www.saucedemo.com/inventory.html");  // Assume user is already logged in for simplicity
	    }

	    @Test
	    public void verifyProductListDisplay() {
	        List<WebElement> products = driver.findElements(By.className("inventory_item"));
	        Assert.assertTrue(products.size() > 0, "Product list should not be empty");

	        for (WebElement product : products) {
	            // Verify product name displayed
	            Assert.assertTrue(product.findElement(By.className("inventory_item_name")).isDisplayed(), "Product name is not displayed");

	            // Verify product price displayed
	            Assert.assertTrue(product.findElement(By.className("inventory_item_price")).isDisplayed(), "Product price is not displayed");

	            // Verify product image displayed
	            Assert.assertTrue(product.findElement(By.tagName("img")).isDisplayed(), "Product image is not displayed");

	            // Verify Add to Cart button displayed
	            Assert.assertTrue(product.findElement(By.tagName("button")).isDisplayed(), "Add to Cart button is not displayed");
	        }
	    }
}
