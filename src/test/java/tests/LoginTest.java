package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseTest;
import listeners.TestListener;
import pages.LoginPage;
import utils.ScreenshotUtil;

@Listeners(TestListener.class)
public class LoginTest extends BaseTest {

//	WebDriver driver;
    LoginPage loginPage;

    @BeforeClass
    public void setupTest() {
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver);
        ScreenshotUtil.takeScreenshot(driver, "LoginPage_Loaded");
    }

    @Test(priority = 8,groups = "login")
    public void testValidLogin() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        Assert.assertEquals(loginPage.isInventoryPageDisplayed(), "https://www.saucedemo.com/inventory.html");
        ScreenshotUtil.takeScreenshot(driver, "testValidLogin");
    }
    
    @Test(priority = 3)
    public void testEmptyUsername() {
        driver.get("https://www.saucedemo.com/");
        loginPage.enterUsername("");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        String actualError = loginPage.getErrorMessage();
        Assert.assertEquals(actualError, "Epic sadface: Username is required");
        ScreenshotUtil.takeScreenshot(driver, "testEmptyUsername");
    }

    
    @Test(priority = 4)
    public void testInvalidUsername() {
        driver.get("https://www.saucedemo.com/");
        loginPage.enterUsername("invalid-username");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        String actualError = loginPage.getErrorMessage();
        Assert.assertEquals(actualError, "Epic sadface: Username and password do not match any user in this service");

        ScreenshotUtil.takeScreenshot(driver, "testEmptyUsername");
    }
    
    @Test(priority = 5)
    public void testEmptyPassword() {
        driver.get("https://www.saucedemo.com/");
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("");
        loginPage.clickLogin();

        String actualError = loginPage.getErrorMessage();
        Assert.assertEquals(actualError, "Epic sadface: Password is required");
        ScreenshotUtil.takeScreenshot(driver, "testEmptyPassword");
    }
    @Test(priority = 6)
    public void testInvalidPassword() {
        driver.get("https://www.saucedemo.com/"); // Reset page
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("wrong_password");
        loginPage.clickLogin();

        String actualError = loginPage.getErrorMessage();
        System.out.println("Actusl error of invalid passw"+actualError);
        Assert.assertEquals(loginPage.getErrorMessage().trim(),"Epic sadface: Username and password do not match any user in this service");
        ScreenshotUtil.takeScreenshot(driver, "testInvalidPassword");
    }


    @Test(priority = 7)
    public void testEmptyUsernameAndPassword() {
        driver.get("https://www.saucedemo.com/");
        loginPage.enterUsername("");
        loginPage.enterPassword("");
        loginPage.clickLogin();

        String actualError = loginPage.getErrorMessage();
        Assert.assertEquals(actualError, "Epic sadface: Username is required");
        ScreenshotUtil.takeScreenshot(driver, "testEmptyPassword");
    }
   

   
    @Test(priority = 8)
    public void testLockedOutUser() {
        driver.get("https://www.saucedemo.com/");
        loginPage.enterUsername("locked_out_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        String actualError = loginPage.getErrorMessage();
        Assert.assertEquals(actualError, "Epic sadface: Sorry, this user has been locked out.");
        ScreenshotUtil.takeScreenshot(driver, "testLockedOutUser");
    }
    
    @Test(priority = 2 )
    public void testProblemUser() {
        driver.get("https://www.saucedemo.com/");
        loginPage.enterUsername("problem_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        Assert.assertEquals(loginPage.isInventoryPageDisplayed(), "https://www.saucedemo.com/inventory.html");

        ScreenshotUtil.takeScreenshot(driver, "testProblemUser");
    }
 
   
   
}
