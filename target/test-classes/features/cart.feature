Feature: Shopping Cart Functionality

  Scenario: User logs in successfully
    Given the user is on the login page
    When the user logs in with username "standard_user" and password "secret_sauce"
    Then the user should be redirected to the products page

  Scenario: User adds a product to the cart
    Given the user is logged in
    When the user adds "Sauce Labs Backpack" to the cart
    And the user navigates to the cart
    Then "Sauce Labs Backpack" should be present in the cart

  Scenario: User removes an item from the cart
    Given the user has "Sauce Labs Backpack" in the cart
    When the user removes "Sauce Labs Backpack" from the cart
    Then "Sauce Labs Backpack" should not be present in the cart

  Scenario: User continues shopping from the cart
    Given the user is on the cart page
    When the user clicks on Continue Shopping
    Then the user should be redirected to the products page

  Scenario: User proceeds to checkout
    Given the user has "Sauce Labs Backpack" in the cart
    When the user proceeds to checkout
    Then the user should be redirected to the checkout page
