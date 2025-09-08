package stepDefinitions;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import pages.CheckoutPage;
import pages.ProductPage;
import pages.CartPage;
import utils.DriverFactory;

public class CheckoutSteps {

    WebDriver driver;
    CheckoutPage checkoutPage;
    ProductPage productPage;
    CartPage cartPage;

    @Before
    public void setUp() {
        driver = DriverFactory.getDriver(); 
        checkoutPage = new CheckoutPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
    }

    @Given("adds {string} to the cart")
    public void add_product_to_cart(String productName) {
        productPage.addProductToCart(productName);
        productPage.openCart();
    }

    @Given("navigates to the checkout page")
    public void navigate_to_checkout_page() {
        cartPage.clickCheckout();
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one"),
                "User is not on checkout page");
    }

    @Then("the user should be on the {string} page")
    public void verify_user_is_on_page(String expectedPage) {
        Assert.assertTrue(driver.getCurrentUrl().contains(expectedPage),
                "User is not on expected page: " + expectedPage);
    }

    @When("the user enters first name {string}, last name {string}, and postal code {string}")
    public void user_enters_checkout_details(String fname, String lname, String zip) {
        checkoutPage.enterFirstName(fname);
        checkoutPage.enterLastName(lname);
        checkoutPage.enterPostalCode(zip);
    }

    @When("clicks the continue button")
    public void click_continue_button() {
        checkoutPage.clickContinue();
    }

    @Then("the user should be on the checkout overview page")
    public void verify_checkout_overview() {
        Assert.assertTrue(checkoutPage.isOnCheckoutOverview(),
                "User is not on checkout overview page");
    }

    @When("the user clicks the finish button")
    public void click_finish_button() {
        checkoutPage.clickFinish();
    }

    @Then("the checkout should be completed successfully")
    public void verify_checkout_complete() {
        Assert.assertTrue(checkoutPage.isCheckoutComplete(),
                "Checkout was not completed successfully");
    }

    @Then("an error message should be displayed")
    public void verify_error_message_displayed() {
        String error = checkoutPage.getErrorMessage();
        Assert.assertTrue(error != null && !error.isEmpty(),
                "Expected error message not shown");
    }
}
