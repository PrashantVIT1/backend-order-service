package com.prashant.backendorderservice.auth.service;

import com.prashant.backendorderservice.auth.dto.request.LoginRequest;
import com.prashant.backendorderservice.auth.dto.response.LoginResponse;
import com.prashant.backendorderservice.auth.dto.response.SignupResponse;
import com.prashant.backendorderservice.auth.entity.User;
import com.prashant.backendorderservice.auth.entity.type.AuthProviderType;
import com.prashant.backendorderservice.auth.entity.type.RoleType;
import com.prashant.backendorderservice.auth.exception.InvalidCredentialsException;
import com.prashant.backendorderservice.auth.exception.UserAlreadyExistsException;
import com.prashant.backendorderservice.auth.repository.UserRepository;
import com.prashant.backendorderservice.auth.util.AuthUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Set;


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

    public User signupInternal(LoginRequest signupRequest, AuthProviderType authProviderType, String providerId) {
        User user = userRepository.findByUsername(signupRequest.getUsername()).orElse(null);

        if (user != null) throw new UserAlreadyExistsException("User already exists: " + signupRequest.getUsername());

        user = User.builder()
                .username(signupRequest.getUsername())
                .providerId(providerId)
                .providerType(authProviderType)
                .roles(Set.of(RoleType.USER))
                .build();

        if(authProviderType == AuthProviderType.EMAIL){
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        }

        return userRepository.save(user);

    }

    public SignupResponse signup(LoginRequest signupRequest) {

        User user = signupInternal(signupRequest, AuthProviderType.EMAIL, null);
        return  new SignupResponse(user.getId(), user.getUsername());
    }

    @Transactional
    public ResponseEntity<LoginResponse> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId) {

        AuthProviderType providerType = authUtil.getProviderTypeFromRegistrationId(registrationId);
        String providerId = authUtil.determineProviderIdFromOAuth2User(oAuth2User, registrationId);

        User user = userRepository.findByProviderIdAndProviderType(providerId, providerType).orElse(null);
        String email = oAuth2User.getAttribute("email");

        User emailUser = userRepository.findByUsername(email).orElse(null);

        if(user == null &&  emailUser == null){
            //signup flow:
            String username = authUtil.determineUsernameFromOAuth2User(oAuth2User, registrationId, providerId);
            user = signupInternal(new LoginRequest(username, null), providerType, providerId);

        } else if(user !=null){
            if(email!= null && !email.isBlank() && !email.equals(user.getUsername())){
                user.setUsername(email);
                userRepository.save(user);
            }
        } else{
            throw new BadCredentialsException("This email is already registered with provider "+email);
        }

        LoginResponse loginResponse = new LoginResponse(authUtil.generateAccessToken(user));
        return ResponseEntity.ok(loginResponse);
    }
}
