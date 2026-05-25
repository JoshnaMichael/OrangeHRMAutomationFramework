package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class PIMPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Employee List Page Locators
    private By pimMenu           = By.xpath("//span[text()='PIM']");
    private By addEmployeeBtn    = By.xpath("//a[normalize-space()='Add Employee']");
    private By employeeNameInput = By.xpath("//input[@placeholder='Type for hints...']");
    private By searchBtn         = By.cssSelector("button[type='submit']");
    private By recordsFound      = By.xpath("//span[@class='oxd-text oxd-text--span']");
    private By employeeListRows  = By.cssSelector(".oxd-table-body .oxd-table-row");

    // Add Employee Page Locators
    private By firstNameField    = By.name("firstName");
    private By middleNameField   = By.name("middleName");
    private By lastNameField     = By.name("lastName");
    private By employeeIdField   = By.xpath("//label[text()='Employee Id']/following::input[1]");
    private By saveBtn           = By.cssSelector("button[type='submit']");
    private By cancelBtn         = By.xpath("//button[normalize-space()='Cancel']");
    private By pageHeader        = By.xpath("//h6[normalize-space()='Add Employee']");

    public PIMPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Navigate to PIM module
    public void navigateToPIM() {
        wait.until(ExpectedConditions.elementToBeClickable(pimMenu)).click();
        wait.until(ExpectedConditions.urlContains("pim"));
    }

    // Click Add Employee button
    public void clickAddEmployee() {
        wait.until(ExpectedConditions.elementToBeClickable(addEmployeeBtn)).click();
        wait.until(ExpectedConditions.urlContains("addEmployee"));
    }

    // Fill Add Employee form
    public void enterFirstName(String firstName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField))
            .clear();
        driver.findElement(firstNameField).sendKeys(firstName);
    }

    public void enterMiddleName(String middleName) {
        driver.findElement(middleNameField).clear();
        driver.findElement(middleNameField).sendKeys(middleName);
    }

    public void enterLastName(String lastName) {
        driver.findElement(lastNameField).clear();
        driver.findElement(lastNameField).sendKeys(lastName);
    }

    public void addEmployee(String firstName, String middleName, String lastName) {
        enterFirstName(firstName);
        enterMiddleName(middleName);
        enterLastName(lastName);
    }

    // Click Save button
    public void clickSave() {
        wait.until(ExpectedConditions.elementToBeClickable(saveBtn)).click();
    }

    // Click Cancel button
    public void clickCancel() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelBtn)).click();
    }

    // Get Employee ID (auto generated)
    public String getEmployeeId() {
        return wait.until(ExpectedConditions
            .visibilityOfElementLocated(employeeIdField)).getAttribute("value");
    }

    // Search employee by name
    public void searchEmployee(String name) {
        wait.until(ExpectedConditions
            .visibilityOfElementLocated(employeeNameInput)).clear();
        driver.findElement(employeeNameInput).sendKeys(name);

        // Wait for autocomplete suggestion and click it
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }

        wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    // Get records found text
    public String getRecordsFoundText() {
        return wait.until(ExpectedConditions
            .visibilityOfElementLocated(recordsFound)).getText();
    }

    // Check if Add Employee page is displayed
    public boolean isAddEmployeePageDisplayed() {
        return wait.until(ExpectedConditions
            .visibilityOfElementLocated(pageHeader)).isDisplayed();
    }

    // Get current URL
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}