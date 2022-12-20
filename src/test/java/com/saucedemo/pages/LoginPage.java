package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginPage {
    private WebDriver driver;
    @FindBy(id = "user-name")
    WebElement userName;
    @FindBy(id = "password")
    WebElement password;

    @FindBy(id = "login-button")
    WebElement loginBtn;

    @FindBy(xpath = "//span[text()='Products']")
    WebElement homePage;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void enterSigninDetails(String userID, String pwd) {
        userName.sendKeys(userID);
        password.sendKeys(pwd);
    }

    public void clickLink(String linkName) {
        loginBtn.click();
    }

    public void verifyHomePage() {
        assertThat(homePage.getText()).isEqualTo("PRODUCTS");
    }
}
