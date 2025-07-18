package com.binhkt.ec_auth.service;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.binhkt.ec_auth.entity.PasswordResetToken;
import com.binhkt.ec_auth.entity.User;
import com.binhkt.ec_auth.repository.PasswordResetTokenRepository;
import com.binhkt.ec_auth.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PasswordResetService {
    
    private final PasswordResetTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecureRandom secureRandom = new SecureRandom();
    
    /**
     * Generates a secure password reset token in the format: {shortId}-{userHash}-{cryptoHash}
     * Similar to: 5o0h8-crx5in-838cc17bd19139b95d8b394031e2cca1
     */
    public String generatePasswordResetToken(String email) {
        try {
            // Check if user exists
            Optional<User> userOpt = userRepository.findByEmail(email);
            if (userOpt.isEmpty()) {
                throw new RuntimeException("User with email not found");
            }
            
            User user = userOpt.get();
            
            // Invalidate any existing tokens for this email
            invalidateExistingTokens(email);
            
            // Generate token parts
            String shortId = generateShortId(); // 5-6 characters
            String userHash = generateUserHash(user); // 6 characters based on user
            String cryptoHash = generateCryptoHash(email, shortId, userHash); // 32 characters
            
            String token = shortId + "-" + userHash + "-" + cryptoHash;
            
            // Save token to database
            PasswordResetToken resetToken = new PasswordResetToken();
            resetToken.setToken(token);
            resetToken.setEmail(email);
            resetToken.setExpiryDate(LocalDateTime.now().plusHours(24)); // 24 hours expiry
            resetToken.setUsed(false);
            
            tokenRepository.save(resetToken);
            
            return token;
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate password reset token", e);
        }
    }
    
    /**
     * Validates a password reset token
     */
    public boolean isValidToken(String token) {
        Optional<PasswordResetToken> resetToken = tokenRepository.findByTokenAndUsedFalse(token);
        
        if (resetToken.isEmpty()) {
            return false;
        }
        
        PasswordResetToken tokenEntity = resetToken.get();
        
        // Check if token is expired
        if (tokenEntity.getExpiryDate().isBefore(LocalDateTime.now())) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Reset password using token
     */
    @Transactional
    public boolean resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> resetTokenOpt = tokenRepository.findByTokenAndUsedFalse(token);
        
        if (resetTokenOpt.isEmpty()) {
            return false;
        }
        
        PasswordResetToken resetToken = resetTokenOpt.get();
        
        // Check if token is expired
        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return false;
        }
        
        // Find user by email
        Optional<User> userOpt = userRepository.findByEmail(resetToken.getEmail());
        if (userOpt.isEmpty()) {
            return false;
        }
        
        User user = userOpt.get();
        
        // Update password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        
        // Mark token as used
        tokenRepository.markTokenAsUsed(token);
        
        return true;
    }
    
    /**
     * Get email from valid token
     */
    public String getEmailFromToken(String token) {
        Optional<PasswordResetToken> resetToken = tokenRepository.findByTokenAndUsedFalse(token);
        
        if (resetToken.isEmpty()) {
            return null;
        }
        
        PasswordResetToken tokenEntity = resetToken.get();
        
        // Check if token is expired
        if (tokenEntity.getExpiryDate().isBefore(LocalDateTime.now())) {
            return null;
        }
        
        return tokenEntity.getEmail();
    }
    
    /**
     * Generate a short ID (5-6 characters)
     */
    private String generateShortId() {
        String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append(chars.charAt(secureRandom.nextInt(chars.length())));
        }
        return sb.toString();
    }
    
    /**
     * Generate user-specific hash (6 characters)
     */
    private String generateUserHash(User user) {
        try {
            String input = user.getUsername() + user.getId() + user.getEmail();
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(input.getBytes());
            
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 3; i++) { // Take first 3 bytes = 6 hex chars
                sb.append(String.format("%02x", hash[i]));
            }
            return sb.toString();
        } catch (Exception e) {
            // Fallback to random string
            return generateShortId().substring(0, 6);
        }
    }
    
    /**
     * Generate cryptographic hash (32 characters)
     */
    private String generateCryptoHash(String email, String shortId, String userHash) {
        try {
            String timestamp = String.valueOf(System.currentTimeMillis());
            String randomBytes = generateRandomBytes(16);
            String input = email + shortId + userHash + timestamp + randomBytes;
            
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(input.getBytes());
            
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate crypto hash", e);
        }
    }
    
    /**
     * Generate random bytes for additional entropy
     */
    private String generateRandomBytes(int length) {
        byte[] bytes = new byte[length];
        secureRandom.nextBytes(bytes);
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    
    /**
     * Invalidate existing tokens for email
     */
    @Transactional
    private void invalidateExistingTokens(String email) {
        Optional<PasswordResetToken> existingToken = tokenRepository.findByEmailAndUsedFalse(email);
        if (existingToken.isPresent()) {
            tokenRepository.markTokenAsUsed(existingToken.get().getToken());
        }
    }
    
    /**
     * Clean up expired tokens (can be called by scheduled task)
     */
    @Transactional
    public void cleanupExpiredTokens() {
        tokenRepository.deleteExpiredTokens(LocalDateTime.now());
    }
}
