package com.saucedemo.steps;


import com.saucedemo.util.DriverManager;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.util.GetConfig;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoginAccoutSteps {
    private static final Logger log = LoggerFactory.getLogger(LoginAccoutSteps.class);
    private WebDriver driver;

    private LoginPage loginPage;


    public LoginAccoutSteps() {
        driver = DriverManager.getInstance().getDriver();
        loginPage = new LoginPage(driver);
    }

    /*@And("I logout of application")
    public void iLogoutOfApplication() {
    }*/




    @Given("I am on the application login page")
    public void i_am_on_the_application_login_page() {
        driver.get(GetConfig.getConfigProperty("base.url"));
    }

    @When("I login into application with {string} and {string}")
    public void i_login_into_application_with_and(String userName, String password) {
        loginPage.enterSigninDetails(userName, password);
    }

    @When("I click on {string} button")
    public void i_click_on_button(String type) {
        loginPage.clickLink(type);
    }

    @Then("I verify home page")
    public void i_verify_home_page() {
        loginPage.verifyHomePage();
    }
}
