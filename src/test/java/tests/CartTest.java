package tests;

import org.testng.Assert;
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

@Listeners(TestListener.class)
public class CartTest extends BaseTest {

    private LoginPage loginPage;
    private ProductPage productPage;
    private CartPage cartPage;

    @BeforeClass
    public void setUpPages() {
        if (driver == null) {
            driver = DriverFactory.initDriver();
            driver.get(ConfigReader.get("baseUrl"));
        }

        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);

        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "Login failed!");
    }


    @Test(priority = 1)
    public void testAddProductToCart() throws InterruptedException {
        productPage.addProductToCart("Sauce Labs Backpack");
        productPage.openCart();
        Assert.assertTrue(cartPage.isProductInCart("Sauce Labs Backpack"), "Product not found in cart!");
        Thread.sleep(500); 
    }

    @Test(priority = 2)
    public void testRemoveProductFromCart() throws InterruptedException {
        productPage.openCart();
        Assert.assertTrue(cartPage.isOnCartPage(), "Not on Cart Page!");
        cartPage.removeProduct("Sauce Labs Backpack");
        Assert.assertFalse(cartPage.isProductInCart("Sauce Labs Backpack"), "Product not removed!");
        Thread.sleep(500); 
    }

    @Test(priority = 4) 
    public void testContinueShopping() 
    { 
    	cartPage.clickContinueShopping();
    	Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "Did not return to Product Page!");
    } 
    
    @Test(priority = 5)
    public void testProceedToCheckout() throws InterruptedException { 
    	productPage.openCart(); cartPage.clickCheckout(); 
    	System.out.println("Current URL before checkout: " + driver.getCurrentUrl());
    	Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one"), "Did not navigate to Checkout Page!");
    	}
    

}