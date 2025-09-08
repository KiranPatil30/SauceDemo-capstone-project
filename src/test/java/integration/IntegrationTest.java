package integration;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.CartPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.ProductPage;

public class IntegrationTest extends BaseTest{
	 @Test
	    public void testCompleteUserJourney() {
	        LoginPage loginPage = new LoginPage(driver);
	        ProductPage productPage = new ProductPage(driver);
	        CartPage cartPage = new CartPage(driver);
	        CheckoutPage checkoutPage = new CheckoutPage(driver);

	        loginPage.login("standard_user", "secret_sauce");
	        Assert.assertTrue(loginPage.isInventoryPageDisplayed(), "Login failed or inventory page not displayed.");

	        productPage.addProductToCart("Sauce Labs Backpack");
	        productPage.openCart();

	        cartPage.clickCheckout();

	        checkoutPage.fillDetails("Kiran", "Patil", "416107"); 
	        checkoutPage.clickContinue();
	        checkoutPage.clickFinish();   

	        Assert.assertTrue( checkoutPage.isCheckoutComplete(), "Order was not successful.");
	    }
}
