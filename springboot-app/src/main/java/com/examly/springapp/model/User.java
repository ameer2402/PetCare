package com.examly.springapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Entity annotation indicates that this class is mapped to a database table.
 */
@Entity
public class User {

    /**
     * Primary key for the User entity.
     * Annotated with `@Id` to specify the primary key.
     * `@GeneratedValue` annotation is used for auto-generating the primary key
     * value.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    /**
     * Fields representing user information.
     */
    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.com$", message = "Invalid email format, must end with .com")
    private String email;

    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must be at least 8 characters long and contain at least one digit, one lowercase letter, one uppercase letter, one special character, and no whitespace")
    private String password;

    @NotBlank(message = "Username is required")
    @Size(max = 100, message = "Username must be less than 100 characters")
    private String username;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid mobile number")
    private String mobileNumber;

    @NotBlank(message = "User role is required")
    @Pattern(regexp = "^(PETOWNER|ADMIN)$", message = "User role must be either PETOWNER or ADMIN")
    private String userRole = "PETOWNER"; // Default value

    /**
     * Default constructor for User class.
     */
    public User() {
    }

    /**
     * Parameterized constructor for User class.
     *
     * @param userId       The ID of the user.
     * @param email        The email of the user.
     * @param password     The password of the user.
     * @param username     The username of the user.
     * @param mobileNumber The mobile number of the user.
     * @param userRole     The role of the user.
     */
    public User(int userId, String email, String password, String username, String mobileNumber, String userRole) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.username = username;
        this.mobileNumber = mobileNumber;
        this.userRole = userRole;
    }

    /**
     * Getter and setter methods for userId.
     */
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Getter and setter methods for email.
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter and setter methods for password.
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter and setter methods for username.
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter and setter methods for mobileNumber.
     */
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    /**
     * Getter and setter methods for userRole.
     */
    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}