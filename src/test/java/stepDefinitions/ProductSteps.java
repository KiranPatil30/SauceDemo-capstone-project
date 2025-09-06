package stepDefinitions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.ProductPage;
import utils.DriverFactory;

public class ProductSteps {
	WebDriver driver = DriverFactory.getDriver(); 
	ProductPage productPage;
	
	
	@Given("I am logged in as a valid user")
	public void i_am_on_the_product_page() {
	    driver.get("https://www.saucedemo.com/inventory.html");
	    productPage = new ProductPage(driver);
	    driver.findElement(By.id("user-name")).sendKeys("standard_user");
	    driver.findElement(By.id("password")).sendKeys("secret_sauce");
	    driver.findElement(By.id("login-button")).click();

	    // Optional assertion
	    Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "Login failed or did not redirect to inventory page");
	}

	@Then("the product listing should display names and prices")
	public void the_product_listing_should_display_names_and_prices() {
		
	    List<WebElement> productsName = productPage.getProductNameElements();
	    List<WebElement> productsPrice = productPage.getProductPriceElements();

	    
	    Assert.assertTrue(productsName.size() > 0, "Product list is empty");
        Assert.assertTrue(productsPrice.size() > 0, "Product price list is empty");

        for (WebElement product : productsName) {
            Assert.assertTrue(product.isDisplayed(), "Product name is not displayed");
        }

        for (WebElement price : productsPrice) {
            Assert.assertTrue(price.isDisplayed(), "Product price is not displayed");
        }
	}
	
	@When("I sort products by {string}")
	public void I_sort_products_by(String sort) {
		productPage.selectSortOption(sort);
	}
	
	@Then("products should be sorted by {string}")
	public void products_should_be_sorted_by(String sortOption) {
		if (sortOption.equals("Name (A to Z)")) {
	        List<String> actual = productPage.getAllProductNames();
	        List<String> expected = new ArrayList<>(actual);
	        Collections.sort(expected);
	        Assert.assertEquals(expected, actual);

	    } else if (sortOption.equals("Name (Z to A)")) {
	        List<String> actual = productPage.getAllProductNames();
	        List<String> expected = new ArrayList<>(actual);
	        Collections.sort(expected, Collections.reverseOrder());
	        Assert.assertEquals(expected, actual);

	    } else if (sortOption.equals("Price (low to high)")) {
	        List<Double> actual = productPage.getAllProductPrices();
	        List<Double> expected = new ArrayList<>(actual);
	        Collections.sort(expected);
	        Assert.assertEquals(expected, actual);

	    } else if (sortOption.equals("Price (high to low)")) {
	        List<Double> actual = productPage.getAllProductPrices();
	        List<Double> expected = new ArrayList<>(actual);
	        Collections.sort(expected, Collections.reverseOrder());
	        Assert.assertEquals(expected, actual);

	    } else {
	        Assert.fail("Unsupported sort option: " + sortOption);
	    }
	}
	
	
		  @When("I add {string} to the cart")
		    public void i_add_to_the_cart(String productName) throws InterruptedException {
		        productPage.addProductToCart(productName);
		    }
	
		  @Then("{string} should be in the cart")
		    public void product_should_be_in_the_cart(String productName) {
		        String buttonText = productPage.getAddToCartButtonText(productName);
		        Assert.assertEquals(buttonText, "Remove", productName + " was not added successfully");
		    }
	   

		  @And("I remove {string} from the cart")
		    public void I_remove_from_the_cart(String productName) throws InterruptedException {
		        productPage.removeProductFromCart(productName);
		   }


		  @Then("{string} should not be in the cart")
		    public void product_should_not_be_in_the_cart(String productName) {
		        String buttonText = productPage.getAddToCartButtonText(productName);
		        Assert.assertEquals(buttonText, "Add to cart", productName + " was not removed successfully");
		    }
		  @When("I click on the {string} product")
		    public void iClickOnTheProduct(String productName) throws InterruptedException {
		        productPage.clickOnProduct(productName);
		    }

		    @Then("I should see the details page for {string}")
		   public void i_should_see_the_details_page(String productName) throws InterruptedException {
		        String actualName = productPage.getProductDetailName();
		        Assert.assertEquals(actualName, productName, "Product detail page not displayed correctly");
		  }
}
