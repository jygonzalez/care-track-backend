package com.team5.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team5.backend.request.AuthRequest;
import com.team5.backend.request.RegisterRequest;
import com.team5.backend.response.AuthResponse;
import com.team5.backend.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
        @Valid @RequestBody RegisterRequest request) {
        
        return ResponseEntity.ok(authService.register(request));
    }
    
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(
        @RequestBody AuthRequest request) {
        
        return ResponseEntity.ok(authService.authenticate(request));
    }

}
