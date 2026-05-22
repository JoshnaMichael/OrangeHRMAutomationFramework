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

public class DashboardTest extends BaseTest {

    private DashboardPage loginAndGetDashboard() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());

        // Wait for dashboard URL
        new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(ExpectedConditions.urlContains("dashboard"));

        return new DashboardPage(driver);
    }

    @Test(priority = 1, description = "Verify dashboard title after login")
    public void verifyDashboardTitle() {
        DashboardPage dashboard = loginAndGetDashboard();
        String title = dashboard.getDashboardTitle();
        ExtentReportManager.logInfo("Dashboard title: " + title);
        Assert.assertEquals(title, "Dashboard", "Title mismatch!");
    }

    @Test(priority = 2, description = "Verify welcome message is displayed")
    public void verifyWelcomeMessage() {
        DashboardPage dashboard = loginAndGetDashboard();
        String welcome = dashboard.getWelcomeMessage();
        ExtentReportManager.logInfo("Welcome message: " + welcome);

        // OrangeHRM shows full name (e.g. "Paul Collings"), not "Admin"
        Assert.assertNotNull(welcome, "Welcome message is null!");
        Assert.assertFalse(welcome.trim().isEmpty(), "Welcome message is empty!");
    }

    @Test(priority = 3, description = "Verify dashboard URL after login")
    public void verifyDashboardUrl() {
        DashboardPage dashboard = loginAndGetDashboard();
        String url = dashboard.getCurrentUrl();
        ExtentReportManager.logInfo("Dashboard URL: " + url);
        Assert.assertTrue(url.contains("dashboard"),
            "URL missing 'dashboard'! Actual: " + url);
    }

    @Test(priority = 4, description = "Verify Admin menu navigation")
    public void verifyAdminMenuNavigation() {
        DashboardPage dashboard = loginAndGetDashboard();
        dashboard.clickAdminMenu();

        new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(ExpectedConditions.urlContains("admin"));

        String url = dashboard.getCurrentUrl();
        ExtentReportManager.logInfo("Admin URL: " + url);
        Assert.assertTrue(url.contains("admin"), "Admin URL not loaded! Actual: " + url);
    }
}