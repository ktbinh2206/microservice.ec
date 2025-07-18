package com.binhkt.ec_auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Login request supporting both username and email authentication.
 * 
 * Examples:
 * 1. Login with username:
 *    {
 *      "username": "johndoe",
 *      "password": "secretpassword"
 *    }
 * 
 * 2. Login with email:
 *    {
 *      "username": "john@example.com",
 *      "password": "secretpassword"
 *    }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String username; // Can be either username or email
    private String password;
}
