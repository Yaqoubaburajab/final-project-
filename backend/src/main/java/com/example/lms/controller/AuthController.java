package com.example.lms.controller;

import com.example.lms.model.User;
import com.example.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth" )
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Default role to student if not provided
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("ROLE_STUDENT");
        }
        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
    
    // A simple login endpoint (full JWT implementation would be more complex)
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        return userRepository.findByUsername(loginRequest.getUsername())
            .map(user -> {
                if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                    // In a real app, you would generate and return a JWT here
                    return ResponseEntity.ok("{\"token\": \"dummy-jwt-for-" + user.getUsername() + "\"}");
                } else {
                    return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
                }
            })
            .orElse(new ResponseEntity<>("User not found", HttpStatus.UNAUTHORIZED));
    }
}
