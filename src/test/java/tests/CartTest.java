package tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseTest;
import listeners.TestListener;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductPage;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.ScreenshotUtil;

@Listeners(TestListener.class)
public class CartTest extends BaseTest {

    private LoginPage loginPage;
    private ProductPage productPage;
    private CartPage cartPage;

    @BeforeClass
    public void setUpPages() {
        driver = driver != null ? driver : utils.DriverFactory.initDriver();
        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);

        String baseUrl = ConfigReader.get("baseUrl");
        String username = ConfigReader.get("username");
        String password = ConfigReader.get("password");

   
        driver.get(baseUrl);
        loginPage.login(username, password);
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "Login failed!");
        
        ScreenshotUtil.takeScreenshot(driver, "LoginSuccessful");
    }

    @Test(priority = 1)
    public void testAddProductToCart() throws InterruptedException {
        productPage.addProductToCart("Sauce Labs Backpack");
        productPage.openCart();
        Assert.assertTrue(cartPage.isProductInCart("Sauce Labs Backpack"), "Product not found in cart!");
        Thread.sleep(500); 
        ScreenshotUtil.takeScreenshot(driver, "ProductAddedToCart");
    }

    @Test(priority = 2)
    public void testRemoveProductFromCart() throws InterruptedException {
        productPage.openCart();
        Assert.assertTrue(cartPage.isOnCartPage(), "Not on Cart Page!");
        cartPage.removeProduct("Sauce Labs Backpack");
        Assert.assertFalse(cartPage.isProductInCart("Sauce Labs Backpack"), "Product not removed!");
        Thread.sleep(500); 
        ScreenshotUtil.takeScreenshot(driver, "ProductRemovedFromCart");
    }

    @Test(priority = 4) 
    public void testContinueShopping() 
    { 
    	cartPage.clickContinueShopping();
    	Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "Did not return to Product Page!");
    	ScreenshotUtil.takeScreenshot(driver, "testContinueShopping");
    	} 
    
    @Test(priority = 5)
    public void testProceedToCheckout() throws InterruptedException { 
    	productPage.openCart(); cartPage.clickCheckout(); 
    	System.out.println("Current URL before checkout: " + driver.getCurrentUrl());
    	Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one"), "Did not navigate to Checkout Page!");
    	ScreenshotUtil.takeScreenshot(driver, "testProceedToCheckout"); 
    	}
   

}
