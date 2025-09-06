package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.CheckOutPage;

public class CheckOutTest extends BaseTest {

    CheckOutPage checkoutPage;

    @BeforeClass
    public void setUpTest() {
        driver = driver != null ? driver : utils.DriverFactory.initDriver(); // Ensure driver is ready

        checkoutPage = new CheckOutPage(driver);
        checkoutPage.loginAndAddToCartToCheckout("standard_user", "secret_sauce");

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one"),
                "Not redirected to checkout step one after clicking checkout");
    }

    @Test(priority = 1)
    public void TC020_navigateToCheckoutPage() {
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one"), "User is not on checkout page");
    }

    @Test(priority = 3)
    public void TC021_checkoutWithValidInformation() {
        checkoutPage.enterFirstName("John");
        checkoutPage.enterLastName("Doe");
        checkoutPage.enterPostalCode("12345");
        checkoutPage.clickContinue();

        Assert.assertTrue(checkoutPage.isOnCheckoutOverview(), "User is not on checkout overview page");

        checkoutPage.clickFinish();

        Assert.assertTrue(checkoutPage.isCheckoutComplete(), "Checkout was not completed successfully");
    }
    @BeforeMethod
    public void resetToCheckoutForm() {
        driver.get("https://www.saucedemo.com/checkout-step-one.html");
    }

    @Test(priority = 2)
    public void TC022_checkoutWithMissingRequiredFields() {
//        driver.navigate().back(); // Go back to checkout-step-one
//        driver.navigate().refresh(); // Reset form

        checkoutPage.enterFirstName(""); // Empty first name
        checkoutPage.enterLastName("Doe");
        checkoutPage.enterPostalCode("12345");
        checkoutPage.clickContinue();

        String error = checkoutPage.getErrorMessage();
        Assert.assertTrue(error.contains("Error") || error.length() > 0, "Expected error message not shown");
    }
}
