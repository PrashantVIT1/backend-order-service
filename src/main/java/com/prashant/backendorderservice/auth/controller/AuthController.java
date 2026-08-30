package com.prashant.backendorderservice.auth.controller;

import com.prashant.backendorderservice.auth.config.swagger.AuthControllerDocs;
import com.prashant.backendorderservice.auth.dto.request.LoginRequest;
import com.prashant.backendorderservice.auth.dto.response.LoginResponse;
import com.prashant.backendorderservice.auth.dto.response.SignupResponse;
import com.prashant.backendorderservice.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDocs {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody LoginRequest signupRequest) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.signup(signupRequest));
    }

    @GetMapping("/whoami")
    public Object whoami(Authentication authentication) {
        return authentication;
    }
}
