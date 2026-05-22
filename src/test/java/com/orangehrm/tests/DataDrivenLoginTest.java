package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utils.DataProviderUtils;
import com.orangehrm.utils.ExtentReportManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

/**
 * Data driven login test using TestNG DataProvider and Excel (Apache POI).
 */
public class DataDrivenLoginTest extends BaseTest {

    @Test(dataProvider = "loginData", dataProviderClass = DataProviderUtils.class)
    public void dataDrivenLogin(String username, String password, String testcase, String expectedResult) {

        // Create a friendly test entry in extent (per data row)
        ExtentReportManager.createTest(testcase == null || testcase.isEmpty() ? "DataDrivenLogin" : testcase,
            "Data driven login test");

        // Normalize null to empty strings to avoid sendKeys(null)
        username = username == null ? "" : username;
        password = password == null ? "" : password;

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        if (expectedResult == null) {
            expectedResult = "";
        }

        // If expected result indicates success, assert dashboard URL
        if (expectedResult.equalsIgnoreCase("success")) {
            new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains("dashboard"));
            String url = driver.getCurrentUrl();
            ExtentReportManager.logInfo("URL after login: " + url);
            Assert.assertTrue(url.contains("dashboard"), "Expected dashboard URL for valid login. Got: " + url);
        } else {
            // For invalid / empty login, validate the error message contains expected text
            String error = loginPage.getErrorMessage();
            ExtentReportManager.logInfo("Error message displayed: " + error);
            Assert.assertTrue(error.toLowerCase().contains(expectedResult.toLowerCase()),
                "Expected error to contain '" + expectedResult + "' but got: '" + error + "'");
        }
    }
}
