package com.binhkt.ec_auth.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username; // Can be either username or email for login
    private String password;
    private String email;
    private String confirmPassword;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String role;
    private String avatar;

}
