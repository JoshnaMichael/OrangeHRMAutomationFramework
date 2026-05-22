package com.orangehrm.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static ExtentReports getInstance() {
        if (extent == null) {
            new File("reports").mkdirs();
            ExtentSparkReporter spark = new ExtentSparkReporter("reports/ExtentReport.html");
            spark.config().setTheme(Theme.DARK);
            spark.config().setDocumentTitle("OrangeHRM Automation Report");
            spark.config().setReportName("Test Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Tester", "Josh");
            extent.setSystemInfo("Application", "OrangeHRM");
            extent.setSystemInfo("Browser", "Chrome");
        }
        return extent;
    }

    public static ExtentTest createTest(String testName, String description) {
        ExtentTest extentTest = getInstance().createTest(testName, description);
        test.set(extentTest);
        return extentTest;
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void logInfo(String message) {
        if (test.get() != null) {
            test.get().info(message);
        }
    }

    public static void logPass(String message) {
        if (test.get() != null) {
            test.get().pass(message);
        }
    }

    public static void logFail(String message) {
        if (test.get() != null) {
            test.get().fail(message);
        }
    }

    public static void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}