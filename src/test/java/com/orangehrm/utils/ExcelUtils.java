package com.orangehrm.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to read Excel files (Apache POI) for data driven tests.
 */
public class ExcelUtils {

    private static final String DEFAULT_DATA_PATH = System.getProperty("user.dir") + "/testdata/LoginData.xlsx";

    /**
     * Reads sheet and returns data as Object[][] suitable for TestNG DataProvider.
     * Expected sheet layout (first row = header): username, password, testcase, expectedResult
     */
    public static Object[][] getSheetData(String sheetName) {
        return getSheetData(DEFAULT_DATA_PATH, sheetName);
    }

    public static Object[][] getSheetData(String filePath, String sheetName) {
        List<Object[]> rows = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet '" + sheetName + "' not found in " + filePath);
            }

            // Assume first row is header
            int firstDataRowIndex = sheet.getFirstRowNum() + 1;
            int lastRow = sheet.getLastRowNum();

            for (int r = firstDataRowIndex; r <= lastRow; r++) {
                Row row = sheet.getRow(r);
                if (row == null) {
                    // skip empty row
                    continue;
                }

                String username = getCellString(row, 0);
                String password = getCellString(row, 1);
                String testcase = getCellString(row, 2);
                String expected = getCellString(row, 3);

                rows.add(new Object[]{username, password, testcase, expected});
            }

        } catch (IOException e) {
            throw new RuntimeException("Unable to read Excel file: " + filePath, e);
        }

        Object[][] data = new Object[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            data[i] = rows.get(i);
        }
        return data;
    }

    private static String getCellString(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                // Avoid scientific notation for numeric values
                double d = cell.getNumericCellValue();
                long longVal = (long) d;
                if (longVal == d) {
                    return String.valueOf(longVal);
                } else {
                    return String.valueOf(d);
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case BLANK:
                return "";
            default:
                return cell.toString().trim();
        }
    }
}
