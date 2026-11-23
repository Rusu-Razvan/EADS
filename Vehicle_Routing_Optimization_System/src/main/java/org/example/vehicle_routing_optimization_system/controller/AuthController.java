package org.example.vehicle_routing_optimization_system.controller;

import org.example.vehicle_routing_optimization_system.security.JwtUtils;
import org.example.vehicle_routing_optimization_system.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, AuthService authService,
                          JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping({"/register", "/api/auth/register"})
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> request) {
        try {
            return ResponseEntity.ok(authService.register(request));
        } catch (AuthService.RegistrationForbiddenException exception) {
            return ResponseEntity.status(403).body(Map.of("error", exception.getMessage()));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(Map.of("error", exception.getMessage()));
        }
    }

    @PostMapping({"/login", "/api/auth/login"})
    public ResponseEntity<?> authenticateUser(@RequestBody Map<String, String> request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.get("email"), request.get("password"))
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken((UserDetails) authentication.getPrincipal());

        return ResponseEntity.ok(Map.of("token", jwt));
    }
}
