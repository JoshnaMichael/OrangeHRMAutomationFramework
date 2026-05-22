package com.orangehrm.utils;

import org.testng.annotations.DataProvider;

/**
 * Centralized TestNG DataProviders for the framework.
 */
public class DataProviderUtils {

    @DataProvider(name = "loginData")
    public static Object[][] loginData() {
        // Reads from testdata/LoginData.xlsx Sheet1 by default
        return ExcelUtils.getSheetData("Sheet1");
    }
}
