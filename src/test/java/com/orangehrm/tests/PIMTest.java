package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.pages.PIMPage;
import com.orangehrm.utils.ConfigReader;
import com.orangehrm.utils.ExtentReportManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PIMTest extends BaseTest {

    // Helper method — login and go to PIM
    private PIMPage loginAndGoToPIM() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getUsername(), ConfigReader.getPassword());

        new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(ExpectedConditions.urlContains("dashboard"));

        PIMPage pimPage = new PIMPage(driver);
        pimPage.navigateToPIM();
        return pimPage;
    }

    @Test(priority = 1, description = "Verify PIM Employee List page loads")
    public void verifyEmployeeListPage() {
        PIMPage pimPage = loginAndGoToPIM();

        String url = pimPage.getCurrentUrl();
        ExtentReportManager.logInfo("PIM URL: " + url);

        Assert.assertTrue(url.contains("pim"),
            "PIM page not loaded! URL: " + url);
    }

    @Test(priority = 2, description = "Verify Add Employee page loads")
    public void verifyAddEmployeePageLoads() {
        PIMPage pimPage = loginAndGoToPIM();
        pimPage.clickAddEmployee();

        boolean isDisplayed = pimPage.isAddEmployeePageDisplayed();
        ExtentReportManager.logInfo("Add Employee page displayed: " + isDisplayed);

        Assert.assertTrue(isDisplayed, "Add Employee page not displayed!");
    }

    @Test(priority = 3, description = "Add new employee successfully")
    public void addNewEmployee() {
        PIMPage pimPage = loginAndGoToPIM();
        pimPage.clickAddEmployee();

        // Fill employee details
        pimPage.addEmployee("Test", "Auto", "Josh");
        ExtentReportManager.logInfo("Filled employee: Test Auto Josh");

        // Get the auto generated employee ID before saving
        String empId = pimPage.getEmployeeId();
        ExtentReportManager.logInfo("Auto generated Employee ID: " + empId);

        // Save the employee
        pimPage.clickSave();

        // After save, URL changes to employee profile page
        new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(ExpectedConditions.urlContains("viewPersonalDetails"));

        String url = pimPage.getCurrentUrl();
        ExtentReportManager.logInfo("URL after save: " + url);

        Assert.assertTrue(url.contains("viewPersonalDetails"),
            "Employee not saved! URL: " + url);
        ExtentReportManager.logPass("✅ Employee added successfully!");
    }

    @Test(priority = 4, description = "Verify Cancel button on Add Employee page")
    public void verifyCancelButton() {
        PIMPage pimPage = loginAndGoToPIM();
        pimPage.clickAddEmployee();

        // Fill some data
        pimPage.enterFirstName("CancelTest");

        // Click cancel
        pimPage.clickCancel();

        // Should go back to Employee List
        new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(ExpectedConditions.urlContains("viewEmployeeList"));

        String url = pimPage.getCurrentUrl();
        ExtentReportManager.logInfo("URL after cancel: " + url);

        Assert.assertTrue(url.contains("viewEmployeeList"),
            "Cancel did not go back to Employee List!");
        ExtentReportManager.logPass("✅ Cancel button works!");
    }
}