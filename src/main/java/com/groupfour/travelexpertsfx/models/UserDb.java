package com.groupfour.travelexpertsfx.models;

import com.groupfour.travelexpertsfx.database.DbConfig;
import com.groupfour.travelexpertsfx.utils.PasswordUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Database utility class for handling user authentication, registration,
 * and retrieving user-related information from the database.
 */
public class UserDb {

    /**
     * Establishes a database connection.
     *
     * @return Connection object if successful, otherwise null.
     */
    private static Connection getConnection() {
        try {
            String url = DbConfig.getProperty("url");
            String user = DbConfig.getProperty("user");
            String password = DbConfig.getProperty("password");
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("❌ Error establishing database connection: " + e.getMessage());
            return null;
        }
    }

    /**
     * Authenticates a user by verifying their email and password hash.
     *
     * @param email    The email address of the user.
     * @param password The entered password to be checked against the stored hash.
     * @return The user's role ("agent" or "manager") if authentication succeeds.
     *         Returns "email-not-found" if the email does not exist.
     *         Returns "wrong-password" if the password is incorrect.
     *         Returns "error" if there is an internal database failure.
     */
    public static String authenticateUser(String email, String password) {
        Connection conn = getConnection();
        if (conn == null) {
            return "error";
        }

        String query = "SELECT password_hash, role FROM Users WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password_hash");
                    String role = rs.getString("role");

                    // Verify password
                    boolean passwordMatch = PasswordUtils.verifyPassword(password, storedHash);

                    if (passwordMatch) {
                        return role;
                    } else {
                        return "wrong-password";
                    }
                } else {
                    return "email-not-found";
                }
            }
        } catch (Exception e) {
            return "error";
        }
    }

    /**
     * Checks if the given email is already registered in the database.
     *
     * @param email The email to check.
     * @return true if the email exists, false otherwise.
     */
    public static boolean isEmailTaken(String email) {
        Connection conn = getConnection();
        if (conn == null) {
            return false;
        }

        String query = "SELECT COUNT(*) FROM Users WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Registers a new user by inserting their details into the database.
     *
     * @param agentEmail The agent's email (used to fetch agent ID).
     * @param password   The raw password (to be hashed).
     * @param role       The role assigned to the user.
     * @return true if registration was successful, false otherwise.
     */
    public static boolean registerUser(String agentEmail, String password, String role) {
        int agentId = getAgentIdByEmail(agentEmail);
        if (agentId == -1 || isEmailTaken(agentEmail)) {
            return false;
        }

        Connection conn = getConnection();
        if (conn == null) {
            return false;
        }

        String query = "INSERT INTO Users (agentid, email, password_hash, role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, agentId);
            stmt.setString(2, agentEmail);

            // Hash the password
            String hashedPassword = PasswordUtils.hashPassword(password);

            stmt.setString(3, hashedPassword);
            stmt.setString(4, role);

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Retrieves an agent's ID based on their email address.
     *
     * @param email The email to look up.
     * @return The agent ID if found, or -1 if not found.
     */
    public static int getAgentIdByEmail(String email) {
        Connection conn = getConnection();
        if (conn == null) {
            return -1;
        }

        String query = "SELECT agentid FROM Agents WHERE agtemail = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt("agentid") : -1;
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Retrieves the role of an agent based on their email address.
     *
     * @param email The email to look up.
     * @return The agent's role if found, or null if not found.
     */
    public static String getAgentRoleByEmail(String email) {
        Connection conn = getConnection();
        if (conn == null) {
            return null;
        }

        String query = "SELECT role FROM agents WHERE agtemail = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getString("role") : null;
        } catch (Exception e) {
            return null;
        }
    }
}
