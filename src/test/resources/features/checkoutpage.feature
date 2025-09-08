Feature: Checkout process on SauceDemo

  Background:
    Given the user logs in with username "standard_user" and password "secret_sauce"
    And adds "Sauce Labs Backpack" to the cart
    And navigates to the checkout page

  Scenario: TC020 - Navigate to Checkout Page
    Then the user should be on the "checkout-step-one" page

  Scenario: TC021 - Successful checkout with valid information
    When the user enters first name "John", last name "Doe", and postal code "12345"
    And clicks the continue button
    Then the user should be on the checkout overview page
    When the user clicks the finish button
    Then the checkout should be completed successfully

  Scenario: TC022 - Checkout with missing required fields
    When the user enters first name "", last name "Doe", and postal code "12345"
    And clicks the continue button
    Then an error message should be displayed
