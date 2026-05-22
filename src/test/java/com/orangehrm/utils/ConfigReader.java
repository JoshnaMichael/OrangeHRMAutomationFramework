package com.orangehrm.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties = new Properties();

    static {
        try {
            FileInputStream fis = new FileInputStream(
                System.getProperty("user.dir") + "/src/main/resources/config.properties");
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("config.properties file not found!");
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getUrl() {
        return properties.getProperty("url");
    }

    public static String getUsername() {
        return properties.getProperty("username");
    }

    public static String getPassword() {
        return properties.getProperty("password");
    }

    public static String getBrowser() {
        return properties.getProperty("browser");
    }
}