package com.binhkt.ec_auth.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.binhkt.ec_auth.dto.AuthRequest;
import com.binhkt.ec_auth.dto.AuthResponse;
import com.binhkt.ec_auth.entity.User;

import lombok.*;

@Data
@Service
@AllArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final PasswordEncoder encoder;
    private final UserService userService;
    private final JavaMailSender mailSender;
    private final PasswordResetService passwordResetService;

    public AuthResponse authenticate(AuthRequest request) {
        // Validate input
        String identifier = request.getUsername();
        String password = request.getPassword();
        
        if (identifier == null || identifier.trim().isEmpty()) {
            throw new RuntimeException("Username or email is required");
        }
        
        if (password == null || password.trim().isEmpty()) {
            throw new RuntimeException("Password is required");
        }
        
        // Support login with either username or email
        String encodedPassword = userService.getPasswordByUsernameOrEmail(identifier);
        
        if (encodedPassword == null) {
            throw new RuntimeException("User not found");
        }
        
        if (!encoder.matches(password, encodedPassword)) {
            throw new RuntimeException("Invalid password");
        }

        // Get the actual user to extract username for token
        User user = userService.findByUsernameOrEmail(identifier);
        
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        
        if (!user.isEnabled()) {
            throw new RuntimeException("Account is disabled");
        }
        
        String actualUsername = user.getUsername();

        // Generate access token using actual username
        String accessToken = jwtService.generateToken(actualUsername);

        // Generate refresh token using actual username
        String refreshToken = jwtService.generateRefreshToken(actualUsername);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .username(actualUsername)
                .role(user.getRole())
                .build();
    }

    public boolean sendForgotPasswordEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        try {
            // Generate secure password reset token
            String resetToken = passwordResetService.generatePasswordResetToken(email);
            
            SimpleMailMessage message = new SimpleMailMessage();

            // Gmail yêu cầu phải gửi từ đúng tài khoản đã đăng nhập SMTP
            message.setFrom("ktbinh2206@gmail.com"); 

            message.setTo(email);
            message.setSubject("Password Reset Request");
            message.setText("Click the link below to reset your password:\n" +
                    "https://yourdomain.com/auth/reset-password/key/" + resetToken + "/\n\n" +
                    "This link will expire in 24 hours.\n" +
                    "If you didn't request this password reset, please ignore this email.");

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