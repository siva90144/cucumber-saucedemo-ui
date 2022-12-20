@loginAccount
Feature: Login Features

  @loginIntoAccount
  Scenario Outline: To login into an account with user <userName>
    Given I am on the application login page
    #When I navigate to Login Page by clicking on Sign In button
    When I login into application with "<userName>" and "<password>"
    And I click on "Sign in" button
    Then I verify home page
   # And I logout of application

    Examples:
      | userName      | password     |
      | standard_user | secret_sauce |
      | test          | test         |