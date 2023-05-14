package com.example.strongteamtesttask.controller;

import com.example.strongteamtesttask.dto.AuthRequest;
import com.example.strongteamtesttask.exceptionHandler.ErrorResponse;
import com.example.strongteamtesttask.model.Users;
import com.example.strongteamtesttask.security.JwtTokenProvider;
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
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody AuthRequest authRequest) {

        if (isValidPassword(authRequest.getPassword())) {
            userService.addUser(authRequest);
            return ResponseEntity.ok("User has been registered successfully.");
        } else {
            return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(), "Password is too short and easy to guess."));
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody AuthRequest authRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword()));

        Users user = userService.findByUserName(authRequest.getUsername());
        String token = jwtTokenProvider.createToken(authRequest.getUsername());

        Map<String, Object> response = new HashMap<>();
        response.put("user", user);
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[0-9]).{8,}$");
    }

}
