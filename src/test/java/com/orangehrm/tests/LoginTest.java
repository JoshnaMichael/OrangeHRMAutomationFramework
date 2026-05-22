package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utils.ConfigReader;
import com.orangehrm.utils.ExtentReportManager;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(priority = 1, description = "Valid login with correct credentials")
    public void validLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());

        // Wait until URL changes to dashboard
        new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(ExpectedConditions.urlContains("dashboard"));

        String url = driver.getCurrentUrl();
        ExtentReportManager.logInfo("URL after login: " + url);
        Assert.assertTrue(url.contains("dashboard"),
            "Dashboard URL not found! Actual: " + url);
    }

    @Test(priority = 2, description = "Invalid login with wrong credentials")
    public void invalidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("wrongUser", "wrongPass");

        String error = loginPage.getErrorMessage();
        ExtentReportManager.logInfo("Error: " + error);
        Assert.assertTrue(error.contains("Invalid credentials"),
            "Expected error not shown! Got: " + error);
    }

    @Test(priority = 3, description = "Login with empty credentials")
    public void emptyLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickLogin();

        String error = loginPage.getErrorMessage();
        ExtentReportManager.logInfo("Empty login error: " + error);
        Assert.assertFalse(error.isEmpty(),
            "No error shown for empty login!");
    }

    @Test(priority = 4, description = "Logout after valid login")
    public void logoutTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());

        new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(ExpectedConditions.urlContains("dashboard"));

        DashboardPage dashboard = new DashboardPage(driver);
        dashboard.logout();

        new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(ExpectedConditions.urlContains("login"));

        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
            "Login page not shown after logout!");
        ExtentReportManager.logInfo("Logout successful");
    }
}