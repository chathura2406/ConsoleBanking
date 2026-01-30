package com.banking.controller;

import com.banking.entity.User;
import com.banking.repository.UserRepository;
import com.banking.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    // --- REGISTER API ---
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }
        // Password eka encrypt karala save karanawa
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER"); // Default role
        return ResponseEntity.ok(userRepository.save(user));
    }

    // --- LOGIN API ---
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        // 1. Username/Password hari da balanawa (Spring Security walin)
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        // 2. Hari nam, Token eka hadala denawa
        String token = jwtUtils.generateToken(username);
        return ResponseEntity.ok(Map.of("token", token));
    }
}