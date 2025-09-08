package tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseTest;
import listeners.TestListener;
import pages.LoginPage;
import utils.DriverFactory;
import utils.ScreenshotUtil;

@Listeners(TestListener.class)
public class LoginTest extends BaseTest {

    private static final String BASE_URL = "https://www.saucedemo.com/";
    private static final String INVENTORY_URL_FRAGMENT = "/inventory.html";

    private LoginPage loginPage;

    @BeforeClass
    public void setupTest() {
        driver = DriverFactory.initDriver();
        loginPage = new LoginPage(driver);
    }

    @BeforeMethod
    public void loadLoginPage() {
        driver.get(BASE_URL);
        ScreenshotUtil.takeScreenshot(driver, "LoginPage_Loaded");
    }

    private void login(String username, String password) {
        loginPage.login(username, password);
    }

    private void verifyErrorMessage(String expectedErrorMessage, String screenshotName) {
        String actualError = loginPage.getErrorMessage();
        Assert.assertEquals(actualError, expectedErrorMessage);
        ScreenshotUtil.takeScreenshot(driver, screenshotName);
    }

    @Test(priority = 8, groups = "login")
    public void testValidLogin() {
        login("standard_user", "secret_sauce");
        Assert.assertTrue(loginPage.isInventoryPageDisplayed(), "Inventory page not displayed!");
        ScreenshotUtil.takeScreenshot(driver, "testValidLogin");
    }

    @Test(priority = 3)
    public void testEmptyUsername() {
        login("", "secret_sauce");
        verifyErrorMessage("Epic sadface: Username is required", "testEmptyUsername");
    }

    @Test(priority = 4)
    public void testInvalidUsername() {
        login("invalid-username", "secret_sauce");
        verifyErrorMessage("Epic sadface: Username and password do not match any user in this service", "testInvalidUsername");
    }

    @Test(priority = 5)
    public void testEmptyPassword() {
        login("standard_user", "");
        verifyErrorMessage("Epic sadface: Password is required", "testEmptyPassword");
    }

    @Test(priority = 6)
    public void testInvalidPassword() {
        login("standard_user", "wrong_password");
        verifyErrorMessage("Epic sadface: Username and password do not match any user in this service", "testInvalidPassword");
    }

    @Test(priority = 7)
    public void testEmptyUsernameAndPassword() {
        login("", "");
        verifyErrorMessage("Epic sadface: Username is required", "testEmptyUsernameAndPassword");
    }

    @Test(priority = 8)
    public void testLockedOutUser() {
        login("locked_out_user", "secret_sauce");
        verifyErrorMessage("Epic sadface: Sorry, this user has been locked out.", "testLockedOutUser");
    }

    @Test(priority = 2)
    public void testProblemUser() {
        login("problem_user", "secret_sauce");
        Assert.assertTrue(loginPage.isInventoryPageDisplayed(), "Inventory page not displayed!");
        ScreenshotUtil.takeScreenshot(driver, "testProblemUser");
    }
    
}