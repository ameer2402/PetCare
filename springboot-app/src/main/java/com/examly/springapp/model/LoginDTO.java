package com.examly.springapp.model;

/**
 * Data Transfer Object (DTO) class representing a login response.
 * Contains user details and JWT token.
 */
public class LoginDTO {

    private String jwtToken;

    public LoginDTO() {
        // TODO: Implement the constructor logic if needed
        // throw new UnsupportedOperationException("Default constructor is not
        // supported.");
    }

    public String getJwtToken() {
        return jwtToken;
    }

    /**
     * Sets the JWT token.
     * 
     * @param jwtToken the JWT token to set
     */
    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

}
