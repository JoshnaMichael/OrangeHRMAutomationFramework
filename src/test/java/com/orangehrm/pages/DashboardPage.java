package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class DashboardPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By welcomeMessage = By.cssSelector(".oxd-userdropdown-name");
    private By dashboardTitle = By.cssSelector(".oxd-topbar-header-breadcrumb h6");
    private By adminMenu      = By.xpath("//ul[contains(@class,'oxd-main-menu')]//span[text()='Admin']");
    private By logoutDropdown = By.cssSelector(".oxd-userdropdown-name");
    private By logoutOption   = By.xpath("//a[normalize-space()='Logout']");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public String getWelcomeMessage() {
        return wait.until(ExpectedConditions
            .visibilityOfElementLocated(welcomeMessage)).getText();
    }

    public String getDashboardTitle() {
        return wait.until(ExpectedConditions
            .visibilityOfElementLocated(dashboardTitle)).getText();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void clickAdminMenu() {
        wait.until(ExpectedConditions
            .elementToBeClickable(adminMenu)).click();
    }

    public boolean isAdminMenuVisible() {
        return driver.findElement(adminMenu).isDisplayed();
    }

    public void logout() {
        wait.until(ExpectedConditions
            .elementToBeClickable(logoutDropdown)).click();
        wait.until(ExpectedConditions
            .elementToBeClickable(logoutOption)).click();
    }
}