package com.example.strongteamtesttask.controller;

import com.example.strongteamtesttask.dto.AuthRequest;
import com.example.strongteamtesttask.exceptionHandler.ErrorResponse;
import com.example.strongteamtesttask.jwt.JwtService;
import com.example.strongteamtesttask.model.Users;
import com.example.strongteamtesttask.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody AuthRequest authRequest) {

        if (!userService.existByUsername(authRequest.getUsername())) {
            if (isValidPassword(authRequest.getPassword())) {
                userService.addUser(authRequest);
                Map<String, Object> response = new HashMap<>();
                response.put("messages", "User has been registered successfully.");
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(), "Password is too short and easy to guess."));
            }
        } else {
            return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(), String.format("User with username %s already exist.", authRequest.getUsername())));
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody AuthRequest authRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword()));

        Users user = userService.findByUserName(authRequest.getUsername());
        String token = jwtService.generateToken(authRequest.getUsername());

        Map<String, Object> response = new HashMap<>();
        response.put("user", user);
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[0-9]).{8,}$");
    }

}
