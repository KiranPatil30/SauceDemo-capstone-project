Feature: Login Functionality


  Scenario: Valid login
    Given I am on the login page
    When I enter username "standard_user" and password "secret_sauce"
    And I click the login button
    Then I should be redirected to the inventory page

  Scenario: Empty username
    Given I am on the login page
    When I enter username "" and password "secret_sauce"
    And I click the login button
    Then I should see the error message "Epic sadface: Username is required"

  Scenario: Invalid username
    Given I am on the login page
    When I enter username "invalid-username" and password "secret_sauce"
    And I click the login button
    Then I should see the error message "Epic sadface: Username and password do not match any user in this service"

  Scenario: Empty password
    Given I am on the login page
    When I enter username "standard_user" and password ""
    And I click the login button
    Then I should see the error message "Epic sadface: Password is required"

  Scenario: Invalid password
    Given I am on the login page
    When I enter username "standard_user" and password "wrong_password"
    And I click the login button
    Then I should see the error message "Epic sadface: Username and password do not match any user in this service"

  Scenario: Empty username and password
    Given I am on the login page
    When I enter username "" and password ""
    And I click the login button
    Then I should see the error message "Epic sadface: Username is required"

  Scenario: Locked out user
    Given I am on the login page
    When I enter username "locked_out_user" and password "secret_sauce"
    And I click the login button
    Then I should see the error message "Epic sadface: Sorry, this user has been locked out."

  Scenario: Problem user login
    Given I am on the login page
    When I enter username "problem_user" and password "secret_sauce"
    And I click the login button
    Then I should be redirected to the inventory page
