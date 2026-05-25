package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By usernameField    = By.name("username");
    private By passwordField    = By.name("password");
    private By loginButton      = By.cssSelector("button[type='submit']");
    private By alertError       = By.cssSelector(".oxd-alert-content-text");
    private By requiredError    = By.cssSelector(".oxd-input-field-error-message");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void enterUsername(String username) {
        // Use an extended explicit wait (30 seconds) for the username field to be visible
        WebDriverWait wait30 = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait30.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public String getErrorMessage() {
        try {
            // Invalid credentials error (alert box)
            return wait.until(ExpectedConditions
                .visibilityOfElementLocated(alertError)).getText();
        } catch (Exception e) {
            // Empty field error (required message under field)
            return wait.until(ExpectedConditions
                .visibilityOfElementLocated(requiredError)).getText();
        }
    }

    public boolean isLoginPageDisplayed() {
        return driver.findElement(usernameField).isDisplayed();
    }
}