package tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseTest;
import listeners.TestListener;
import pages.LoginPage;
import pages.ProductPage;
import utils.DriverFactory;
import utils.ScreenshotUtil;

@Listeners(TestListener.class)
public class ProductTest extends BaseTest {
    WebDriver driver;
    ProductPage productPage;
    LoginPage loginPage;


    @BeforeClass
    public void setUp() {
        driver = DriverFactory.initDriver();

        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
    }

    @AfterMethod
	 public void resetAppState() {
	     driver.navigate().refresh(); 
	 }
	 
    @Test(priority = 1)
    public void verifyProductListDisplay() {
        Assert.assertTrue(productPage.allProductsHaveDetails(), "Not all product elements are properly displayed.");
    }

    @Test(priority = 2)
    public void sortByNameAToZ() {
        productPage.selectSortOption("Name (A to Z)");
        List<String> actual = productPage.getAllProductNames();
        List<String> expected = new ArrayList<>(actual);
        Collections.sort(expected);
        Assert.assertEquals(actual, expected, "Products are not sorted A to Z");
        
    }

    @Test(priority = 3)
    public void sortByNameZToA() {
        productPage.selectSortOption("Name (Z to A)");
        List<String> actual = productPage.getAllProductNames();
        List<String> expected = new ArrayList<>(actual);
        Collections.sort(expected, Collections.reverseOrder());
        Assert.assertEquals(actual, expected, "Products are not sorted Z to A");

    }

    @Test(priority = 4)
    public void sortByPriceLowToHigh() {
        productPage.selectSortOption("Price (low to high)");
        List<Double> actual = productPage.getAllProductPrices();
        List<Double> expected = new ArrayList<>(actual);
        Collections.sort(expected);
        Assert.assertEquals(actual, expected, "Products are not sorted Low to High");

    }

    @Test(priority = 5)
    public void sortByPriceHighToLow() {
        productPage.selectSortOption("Price (high to low)");
        List<Double> actual = productPage.getAllProductPrices();
        List<Double> expected = new ArrayList<>(actual);
        Collections.sort(expected, Collections.reverseOrder());
        Assert.assertEquals(actual, expected, "Products are not sorted High to Low");

    }

    @Test(priority = 6)
    public void addProductToCart() throws InterruptedException {
        productPage.addProductToCart("Sauce Labs Backpack");
        Assert.assertEquals(productPage.getAddToCartButtonText("Sauce Labs Backpack"), "Remove");
    }



    @Test(priority = 9)
    public void clickOnProductDetailsPage() {
        productPage.clickOnProduct("Sauce Labs Backpack");
        Assert.assertEquals(productPage.getProductDetailName(), "Sauce Labs Backpack", "Incorrect product detail page");

    }

    @Test(priority = 10, expectedExceptions = RuntimeException.class)
    public void addInvalidProduct() throws InterruptedException {
        productPage.addProductToCart("Non Existent Product");

    }

}
