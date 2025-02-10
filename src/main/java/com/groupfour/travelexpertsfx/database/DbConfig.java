package com.groupfour.travelexpertsfx.database;

import java.io.InputStream;
import java.util.Properties;

public class DbConfig {
    // Object to store database properties
    private static final Properties prop = new Properties();

    // Static block to load properties from file
    static {
        try (InputStream in = DbConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) {
                throw new RuntimeException("⚠ ERROR: db.properties file not found in resources folder!");
            }
            prop.load(in);
        } catch (Exception e) {
            throw new RuntimeException("⚠ ERROR: Failed to load db.properties", e);
        }
    }

    // Read property value from properties file
    public static String getProperty(String key) {
        String value = prop.getProperty(key);
        if (value == null) {
            throw new RuntimeException("⚠ ERROR: Property '" + key + "' not found in db.properties!");
        }
        return value;
    }
}
