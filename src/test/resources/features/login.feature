@loginAccount
Feature: Login Features

  @loginIntoAccount1
#  Scenario Outline: To login into an account
#    Given I am on the application login page
#    When I navigate to Login Page by clicking on Sign In button
#    And I enter valid email id to create an account
#    And I enter valid details <content_firstName> and register the user
#    Then I should be navigated to My account screen
#    And I logout of application
#    And I should be navigated to Authentication screen
#
#    Examples:
#      | content_firstName |
#      | Jon               |

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