package com.binhkt.ec_auth.response;

public enum ResponseCode {
    USER_REGISTERED_SUCCESS("User registered successfully"),
    LOGIN_SUCCESS("Login successful"),
    GENERIC_ERROR("An error occurred"),
    ACCESS_DENIED("Access Denied"),
    AUTHENTICATION_FAILED("Authentication Failed"),
    PASSWORD_REFRESH_SUCCESS("Password refreshed successfully"),
    PASSWORD_REFRESH_FAILED("Password refresh failed"),
    PASSWORD_FORGOT_SUCCESS("Password reset link sent successfully"),
    PASSWORD_FORGOT_FAILED("Failed to send password reset link"),
    TOKEN_REFRESH_SUCCESS("Token refreshed successfully"),
    TOKEN_REFRESH_FAILED("Token refresh failed");

    private final String message;

    ResponseCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
