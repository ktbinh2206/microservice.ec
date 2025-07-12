package com.binhkt.ec_auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String username;
    private String role;
    private String accessToken;
    private String refreshToken;

    private AuthResponse() {
        // Private constructor to enforce usage of builder methods
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    // Static method to start building an AuthResponse instance
    public static AuthResponse builder() {
        return new AuthResponse();
    }

    public AuthResponse token(String token) {
        this.token = token;
        return this;
    }

    public AuthResponse username(String username) {
        this.username = username;
        return this;
    }

    public AuthResponse role(String role) {
        this.role = role;
        return this;
    }

    public AuthResponse accessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public AuthResponse refreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public AuthResponse build() {
        return this;
    }
}