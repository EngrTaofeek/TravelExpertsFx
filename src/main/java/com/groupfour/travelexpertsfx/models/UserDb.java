package com.groupfour.travelexpertsfx.models;

import com.groupfour.travelexpertsfx.database.DbConfig;
import com.groupfour.travelexpertsfx.utils.PasswordUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDb {

    // Establishes a database connection
    private static Connection getConnection() {
        try {
            String url = DbConfig.getProperty("url");
            String user = DbConfig.getProperty("user");
            String password = DbConfig.getProperty("password");
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Authenticate user by checking email and password hash
    public static boolean authenticateUser(String email, String password) {
        Connection conn = getConnection();
        if (conn == null) {
            System.out.println("❌ Database connection failed!");
            return false;
        }

        String query = "SELECT password_hash FROM Users WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                System.out.println("🔍 Stored Hash: " + storedHash);
                System.out.println("🔍 Comparing with entered password: " + password);

                boolean passwordMatch = PasswordUtils.verifyPassword(password, storedHash);
                System.out.println("✅ Password Match: " + passwordMatch);
                return passwordMatch;
            } else {
                System.out.println("❌ No user found with this email!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
