package com.binhkt.ec_auth.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.binhkt.ec_auth.dto.AuthRequest;
import com.binhkt.ec_auth.dto.AuthResponse;

import lombok.*;

@Data
@Service
@AllArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final PasswordEncoder encoder;
    private final UserService userService;
    private final JavaMailSender mailSender;

    public AuthResponse authenticate(AuthRequest request) {
        String encodedPassword = userService.getPasswordByUsername(request.getUsername());
        if (encodedPassword == null || !encoder.matches(request.getPassword(), encodedPassword)) {
            throw new RuntimeException("Invalid credentials");
        }

        // Generate access token using JwtService
        String accessToken = jwtService.generateToken(request.getUsername());

        // Generate refresh token using JwtService
        String refreshToken = jwtService.generateRefreshToken(request.getUsername());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .username(request.getUsername())
                .build();
    }

    public boolean sendForgotPasswordEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();

            // Gmail yêu cầu phải gửi từ đúng tài khoản đã đăng nhập SMTP
            message.setFrom("ktbinh2206@gmail.com"); 

            message.setTo(email);
            message.setSubject("Password Reset Request");
            message.setText("Click the link below to reset your password:\n" +
                    "https://yourdomain.com/reset-password?email=" + email);

            mailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send password reset email", e);
        }
    }

    public String createNewTokenFromRefreshToken(String refreshToken) {
        return jwtService.generateRefreshToken(refreshToken);
    }
}