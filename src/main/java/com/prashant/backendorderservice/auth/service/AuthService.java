package com.prashant.backendorderservice.auth.service;

import com.prashant.backendorderservice.auth.dto.request.LoginRequest;
import com.prashant.backendorderservice.auth.dto.response.LoginResponse;
import com.prashant.backendorderservice.auth.dto.response.SignupResponse;
import com.prashant.backendorderservice.auth.entity.User;
import com.prashant.backendorderservice.auth.exception.InvalidCredentialsException;
import com.prashant.backendorderservice.auth.exception.UserAlreadyExistsException;
import com.prashant.backendorderservice.auth.repository.UserRepository;
import com.prashant.backendorderservice.auth.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(), loginRequest.getPassword())
            );

            User user = (User) authentication.getPrincipal();

            String token = authUtil.generateAccessToken(user);

            return new LoginResponse(token);
        } catch (AuthenticationException ex) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

    }

    public SignupResponse signup(LoginRequest signupRequest) {
        User user = userRepository.findByUsername(signupRequest.getUsername()).orElse(null);

        if (user != null) throw new UserAlreadyExistsException("User already exists: " + signupRequest.getUsername());


        user = userRepository.save(User.builder()
                .username(signupRequest.getUsername())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .build()
        );

        return  new SignupResponse(user.getId(), user.getUsername());
    }
}
