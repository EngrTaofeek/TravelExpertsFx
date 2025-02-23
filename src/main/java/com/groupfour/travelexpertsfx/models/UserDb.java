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
    public static String authenticateUser(String email, String password) {
        Connection conn = getConnection();
        if (conn == null) {
            System.out.println("❌ Database connection failed!");
            return null;
        }

        String query = "SELECT password_hash, role FROM Users WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                String role = rs.getString("role");

                System.out.println("🔍 Stored Hash: " + storedHash);
                boolean passwordMatch = PasswordUtils.verifyPassword(password, storedHash);

                if (passwordMatch) {
                    System.out.println("✅ Login successful for: " + email);
                    return role; // Return role (either 'agent' or 'manager')
                }
            } else {
                System.out.println("❌ No user found with this email!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Check if email is already registered
    public static boolean isEmailTaken(String email) {
        Connection conn = getConnection();
        if (conn == null) {
            System.out.println("⚠ Warning: Could not connect to DB.");
            return false; // Allow registration instead of blocking it
        }

        String query = "SELECT COUNT(*) FROM Users WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if email exists
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Default to false in case of an error
    }

    // Register a new user
    public static boolean registerUser(String agentEmail, String password, String role) {
        int agentId = getAgentIdByEmail(agentEmail);
        if (agentId == -1) {
            System.out.println("❌ Registration failed: No agent found with this email.");
            return false;
        }

        if (isEmailTaken(agentEmail)) {
            System.out.println("❌ Registration failed: Email is already in use.");
            return false;
        }

        Connection conn = getConnection();
        if (conn == null) {
            System.out.println("❌ Registration failed: Database connection error.");
            return false;
        }

        String query = "INSERT INTO Users (agentid, email, password_hash, role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, agentId);
            stmt.setString(2, agentEmail);
            stmt.setString(3, PasswordUtils.hashPassword(password)); // Hash password
            stmt.setString(4, role);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("✅ Registration successful for: " + agentEmail);
                return true;
            } else {
                System.out.println("❌ Registration failed: No rows inserted.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("❌ Registration failed due to SQL error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }




    // Retrieve agent ID by email
    public static int getAgentIdByEmail(String email) {
        Connection conn = getConnection();
        if (conn == null) {
            return -1;
        }

        String query = "SELECT agentid FROM Agents WHERE agtemail = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("agentid"); // Return agent ID if found
            } else {
                System.out.println("❌ No agent found with this email: " + email);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if no agent found
    }
}
