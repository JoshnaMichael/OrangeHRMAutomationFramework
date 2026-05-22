package com.orangehrm.base;

import com.orangehrm.utils.ConfigReader;
import com.orangehrm.utils.ExtentReportManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class BaseTest {

    // Use ThreadLocal to allow parallel test execution without driver collisions
    private static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    // Instance field that test classes use (keeps existing test code unchanged)
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        String browser = ConfigReader.getBrowser();

        WebDriver wd = null;
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            wd = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            wd = new FirefoxDriver();
        }

        // Store WebDriver in ThreadLocal and also set the instance field for existing tests
        driverThread.set(wd);
        this.driver = driverThread.get();

        this.driver.manage().window().maximize();
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        this.driver.get(ConfigReader.getUrl());
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = captureScreenshot(result.getName());
            try {
                ExtentReportManager.getTest()
                    .addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Quit and remove thread-local driver
        try {
            WebDriver wd = driverThread.get();
            if (wd != null) {
                wd.quit();
            }
        } finally {
            driverThread.remove();
            this.driver = null;
        }
    }

    public String captureScreenshot(String testName) {
        new File("screenshots").mkdirs();
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        long threadId = Thread.currentThread().getId();
        String path = System.getProperty("user.dir") + "/screenshots/"
                      + testName + "_t" + threadId + "_" + timestamp + ".png";
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}