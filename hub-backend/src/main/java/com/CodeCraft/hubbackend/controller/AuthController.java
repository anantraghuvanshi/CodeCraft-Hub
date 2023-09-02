package com.CodeCraft.hubbackend.controller;

import com.CodeCraft.hubbackend.model.User;
import com.CodeCraft.hubbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//
//    @PostMapping("/signup")
//    public ResponseEntity<?> registerUser(@RequestBody User user) {
//        try {
//            if (userService.existsByEmail(user.getEmail())) {
//                return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
//            }
//
//            userService.createUser(user);
//            return ResponseEntity.ok(new MessageResponse("User registered successfully."));
//
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(new MessageResponse("Error registering user: " + e.getMessage()));
//        }
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> loginUser(@RequestBody User user) {
//        try {
//            Optional<User> foundUser = userService.findByUsername(user.getUserName());
//
//            if (foundUser.isPresent()) {
//                if (passwordEncoder.matches(user.getPassword(), foundUser.get().getPassword())) {
//                    String jwtToken = jwtUtil.generateToken(foundUser.get().getUserName());
//                    Map<String, String> response = new HashMap<>();
//                    response.put("jwt", jwtToken);
//                    return ResponseEntity.ok(response);
//                } else {
//                    return ResponseEntity.badRequest().body(new MessageResponse("Invalid password."));
//                }
//            } else {
//                return ResponseEntity.badRequest().body(new MessageResponse("User not found."));
//            }
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Error logging in user: " + e.getMessage());
//        }
//    }

    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
        @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticationService.login(request));

    }
}
