package stepDefinitions;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import io.cucumber.java.en.*;
import pages.LoginPage;
import utils.DriverFactory;
import utils.ScreenshotUtil;

public class LoginSteps {

    WebDriver driver;
    LoginPage loginPage;

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        driver = DriverFactory.getDriver(); 
        loginPage = new LoginPage(driver);
        driver.get("https://www.saucedemo.com/");
        ScreenshotUtil.takeScreenshot(driver, "LoginPage");
    }

    @When("I enter username {string} and password {string}")
    public void i_enter_username_and_password(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @And("I click the login button")
    public void i_click_the_login_button() {
        loginPage.clickLogin();
    }

    @Then("I should be redirected to the inventory page")
    public void i_should_be_redirected_to_the_inventory_page() {
        String currentUrl = loginPage.isInventoryPageDisplayed();
        Assert.assertEquals(currentUrl, "https://www.saucedemo.com/inventory.html");
        ScreenshotUtil.takeScreenshot(driver, "InventoryPage");
    }

    @Then("I should see the error message {string}")
    public void i_should_see_the_error_message(String expectedError) {
        String actualError = loginPage.getErrorMessage().trim();
        Assert.assertEquals(actualError, expectedError);
        ScreenshotUtil.takeScreenshot(driver, "LoginError");
    }
}
