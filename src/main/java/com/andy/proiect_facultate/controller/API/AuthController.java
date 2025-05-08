package com.andy.proiect_facultate.controller.API;

import com.andy.proiect_facultate.model.dto.request.AuthRequest;
import com.andy.proiect_facultate.model.dto.request.RegisterRequest;
import com.andy.proiect_facultate.model.entity.User;
import com.andy.proiect_facultate.security.JwtUtil;
import com.andy.proiect_facultate.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenUtil;
    private final UserServiceImpl userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

            String token = jwtTokenUtil.generateToken(authentication.getName(), authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
            return ResponseEntity.ok().body(token);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        User newUser = userService.registerUser(registerRequest);
        return ResponseEntity.status(201).body("User registered successfully: " + newUser.getEmail());
    }

}