package com.binhkt.ec_auth.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.binhkt.ec_auth.dto.AuthRequest;
import com.binhkt.ec_auth.dto.AuthResponse;
import com.binhkt.ec_auth.entity.User;
import com.binhkt.ec_auth.response.ApiResponse;
import com.binhkt.ec_auth.response.ResponseCode;
import com.binhkt.ec_auth.service.AuthService;
import com.binhkt.ec_auth.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private UserService userService;
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody Map<String, String> body) {
        User user = userService.register(body.get("username"), body.get("password"));
        AuthResponse response = AuthResponse.builder()
                .username(user.getUsername())
                .role(user.getRole())
                .build();
        return ResponseEntity.ok(new ApiResponse(true, ResponseCode.USER_REGISTERED_SUCCESS.getMessage(), response, null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody AuthRequest request) {
        AuthResponse authResponse = authService.authenticate(request);
        return ResponseEntity.ok(new ApiResponse(true, ResponseCode.LOGIN_SUCCESS.getMessage(), authResponse, null));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse> refreshToken(@RequestBody String refreshToken) {
        try {
            String newToken = authService.createNewTokenFromRefreshToken(refreshToken);
            return ResponseEntity.ok(new ApiResponse(true, ResponseCode.TOKEN_REFRESH_SUCCESS.getMessage(), newToken, null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok(new ApiResponse(false, ResponseCode.TOKEN_REFRESH_FAILED.getMessage(), null, Map.of("details", e.getMessage())));
        }
    }

    @PostMapping("/forgot")
    public ResponseEntity<ApiResponse> forgotPassword(@RequestBody Map<String, String> body) {
        boolean success = authService.sendForgotPasswordEmail(body.get("email"));
        return ResponseEntity.ok(new ApiResponse(success, success ? "Email sent" : ResponseCode.PASSWORD_FORGOT_FAILED.getMessage(), null, null));
    }
}
