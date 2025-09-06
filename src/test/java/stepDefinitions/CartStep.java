package stepDefinitions;

import static utils.DriverFactory.getDriver;

import org.testng.Assert;
import io.cucumber.java.en.*;

import pages.CartPage;
import pages.LoginPage;
import pages.ProductPage;

public class CartStep {

    LoginPage loginPage;
    ProductPage productPage;
    CartPage cartPage;

    String baseUrl = "https://www.saucedemo.com";

    @Given("the user is on the login page")
    public void user_is_on_login_page() {
        getDriver().get(baseUrl);
        loginPage = new LoginPage(getDriver());
    }

    @When("the user logs in with username {string} and password {string}")
    public void user_logs_in(String username, String password) {
        loginPage.login(username, password);
        productPage = new ProductPage(getDriver());
    }

    @Then("the user should be redirected to the products page")
    public void user_redirected_to_products_page() {
        Assert.assertTrue(getDriver().getCurrentUrl().contains("inventory"));
    }

    @Given("the user is logged in")
    public void user_is_logged_in() {
        getDriver().get(baseUrl);
        loginPage = new LoginPage(getDriver());
        loginPage.login("standard_user", "secret_sauce");
        productPage = new ProductPage(getDriver());
    }

    @When("the user adds {string} to the cart")
    public void user_adds_product_to_cart(String productName) {
        productPage.addProductToCart(productName);
    }

    @When("the user navigates to the cart")
    public void user_navigates_to_cart() {
        if (productPage == null) {
            productPage = new ProductPage(getDriver());
        }
        productPage.openCart();
        cartPage = new CartPage(getDriver());
    }


    @Then("{string} should be present in the cart")
    public void product_should_be_present_in_cart(String productName) {
        Assert.assertTrue(cartPage.isProductInCart(productName));
    }

    @Given("the user has {string} in the cart")
    public void user_has_product_in_cart(String productName) {
        user_is_logged_in();
        user_adds_product_to_cart(productName);
        user_navigates_to_cart();
    }

    @When("the user removes {string} from the cart")
    public void user_removes_product_from_cart(String productName) {
        cartPage.removeProduct(productName);
    }

    @Then("{string} should not be present in the cart")
    public void product_should_not_be_in_cart(String productName) {
        Assert.assertFalse(cartPage.isProductInCart(productName));
    }

    @Given("the user is on the cart page")
    public void user_is_on_cart_page() {
        user_is_logged_in(); // ✅ ensures productPage is initialized
        user_navigates_to_cart();
        Assert.assertTrue(cartPage.isOnCartPage());
    }


    @When("the user clicks on Continue Shopping")
    public void user_clicks_continue_shopping() {
        cartPage.clickContinueShopping();
    }
//
//    @Then("the user should be redirected to the products page")
//    public void user_should_be_redirected_to_products_page() {
//        Assert.assertTrue(getDriver().getCurrentUrl().contains("inventory"));
//    }

    @When("the user proceeds to checkout")
    public void user_proceeds_to_checkout() {
        cartPage.clickCheckout();
    }

    @Then("the user should be redirected to the checkout page")
    public void user_should_be_redirected_to_checkout_page() {
        Assert.assertTrue(getDriver().getCurrentUrl().contains("checkout-step-one"));
    }
}
