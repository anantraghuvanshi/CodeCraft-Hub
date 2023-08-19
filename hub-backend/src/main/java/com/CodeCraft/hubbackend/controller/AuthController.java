package com.CodeCraft.hubbackend.controller;

import com.CodeCraft.hubbackend.model.User;
import com.CodeCraft.hubbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            if (userService.existsByEmail(user.getEmail())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
            }

            userService.createUser(user);
            return ResponseEntity.ok(new MessageResponse("User registered successfully."));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error registering user: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        try {
            Optional<User> foundUser = userService.findByUsername(user.getUserName());

            if (foundUser.isPresent()) {
                if (passwordEncoder.matches(user.getPassword(), foundUser.get().getPassword())) {
                    return ResponseEntity.ok("User logged in successfully.");
                } else {
                    return ResponseEntity.badRequest().body("Invalid password.");
                }
            } else {
                return ResponseEntity.badRequest().body("User not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error logging in user: " + e.getMessage());
        }
    }


}
