Feature: Product page functionality

  Background:
    Given I am logged in as a valid user

  Scenario: TC009 Verify product listing page after login
    Then the product listing should display names and prices

  Scenario: TC010 Verify product names and prices are displayed
    Then the product listing should display names and prices

  Scenario Outline: TC011 Verify sorting options
    When I sort products by "<sortOption>"
    Then products should be sorted by "<sortOption>"
    Examples:
      | sortOption            |
      | Name (A to Z)         |
      | Name (Z to A)         |
      | Price (low to high)   |
      | Price (high to low)   |

  Scenario: TC012 Add a product to the cart
    When I add "Sauce Labs Backpack" to the cart
    Then "Sauce Labs Backpack" should be in the cart

  Scenario: TC013 Remove a product from the cart
    When I add "Sauce Labs Backpack" to the cart
    And I remove "Sauce Labs Backpack" from the cart
    Then "Sauce Labs Backpack" should not be in the cart

  Scenario Outline: TC014 Add multiple products to the cart
    When I add "<productName>" to the cart
    Then "<productName>" should be in the cart
    Examples:
      | productName            |
      | Sauce Labs Backpack    |
      | Sauce Labs Bike Light  |
      | Sauce Labs Bolt T-Shirt|

  Scenario: TC015 Click on a product to go to its details page
    When I click on the "Sauce Labs Backpack" product
    Then I should see the details page for "Sauce Labs Backpack"
