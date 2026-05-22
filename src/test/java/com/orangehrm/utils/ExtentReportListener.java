package com.orangehrm.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentReportListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        ExtentReportManager.getInstance();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentReportManager.createTest(
            result.getMethod().getMethodName(),
            result.getMethod().getDescription()
        );
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportManager.logPass("✅ Test Passed");
        ExtentReportManager.flush();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentReportManager.logFail("❌ Test Failed: " + result.getThrowable());
        ExtentReportManager.flush();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportManager.logInfo("⚠️ Test Skipped");
        ExtentReportManager.flush();
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReportManager.flush();
    }
}