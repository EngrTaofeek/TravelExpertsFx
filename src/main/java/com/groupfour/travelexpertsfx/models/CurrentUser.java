package com.groupfour.travelexpertsfx.models;

/**
 * Stores details of the currently logged-in user for use across the application.
 */
public class CurrentUser {
    private static String email;
    private static String role;

    /**
     * Sets the current user details after successful login.
     * @param userEmail The email of the logged-in user.
     * @param userRole The role of the logged-in user.
     */
    public static void setUser(String userEmail, String userRole) {
        email = userEmail;
        role = userRole;
    }

    /**
     * Retrieves the current user's email.
     * @return The email of the logged-in user.
     */
    public static String getEmail() {
        return email;
    }

    /**
     * Retrieves the current user's role.
     * @return The role of the logged-in user.
     */
    public static String getRole() {
        return role;
    }

    /**
     * Clears the stored user details (for logout functionality).
     */
    public static void clearUser() {
        email = null;
        role = null;
    }
}
