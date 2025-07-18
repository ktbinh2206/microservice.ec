package com.binhkt.ec_auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.binhkt.ec_auth.entity.User;
import com.binhkt.ec_auth.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(String username, String email, String rawPassword) {
        // Validate input
        if (username == null || username.trim().isEmpty()) {
            throw new RuntimeException("Username is required");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new RuntimeException("Email is required");
        }
        if (rawPassword == null || rawPassword.trim().isEmpty()) {
            throw new RuntimeException("Password is required");
        }
        
        // Check for existing username
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        
        // Check for existing email
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }
        
        // Additional validation: ensure username is not an email format to avoid confusion
        if (isEmail(username)) {
            throw new RuntimeException("Username cannot be in email format. Please use a different username.");
        }
        
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole("USER");
        user.setEnabled(true);
        return userRepository.save(user);
    }

    public String getPasswordByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(User::getPassword)
                .orElse(null);
    }

    public String getPasswordByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::getPassword)
                .orElse(null);
    }

    public User findByUsernameOrEmail(String identifier) {
        if (identifier == null || identifier.trim().isEmpty()) {
            return null;
        }
        
        // Check if the identifier looks like an email
        if (isEmail(identifier)) {
            return userRepository.findByEmail(identifier).orElse(null);
        } else {
            // Try username first, then fallback to email in case username contains @
            return userRepository.findByUsername(identifier)
                    .orElseGet(() -> userRepository.findByEmail(identifier).orElse(null));
        }
    }

    public String getPasswordByUsernameOrEmail(String identifier) {
        User user = findByUsernameOrEmail(identifier);
        return user != null ? user.getPassword() : null;
    }

    /**
     * Simple email validation - checks if string contains @ and has basic email structure
     */
    private boolean isEmail(String identifier) {
        return identifier != null && 
               identifier.contains("@") && 
               identifier.indexOf("@") > 0 && 
               identifier.indexOf("@") < identifier.length() - 1 &&
               identifier.indexOf(".") > identifier.indexOf("@");
    }
}
