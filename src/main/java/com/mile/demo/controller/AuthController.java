package com.mile.demo.controller;

import com.mile.demo.entity.User;
import com.mile.demo.repository.UserRepository;
import com.mile.demo.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
//@CrossOrigin(origins = "http://localhost:5173")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepository;
    @Autowired
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            UserRepository userRepository,
            JwtUtil jwtUtil,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {

        var user = userRepository.findByUsername(request.get("username"))
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.get("password"), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return Map.of("token", token);
    }
    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Map<String, String> request) {

        String username = request.get("username");
        String password = request.get("password");

        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("ROLE_USER");

        userRepository.save(user);

        return Map.of("message", "User registered successfully");
    }
}
