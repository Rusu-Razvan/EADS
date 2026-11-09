package org.example.vehicle_routing_optimization_system.controller;

import org.example.vehicle_routing_optimization_system.model.Role;
import org.example.vehicle_routing_optimization_system.model.User;
import org.example.vehicle_routing_optimization_system.repository.UserRepository;
import org.example.vehicle_routing_optimization_system.security.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping({"/register", "/api/auth/register"})
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Emailul si parola sunt obligatorii."));
        }
        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Emailul este deja folosit!"));
        }

        User user = new User();
        user.setEmail(email);
        user.setUsername(email);
        user.setPassword(passwordEncoder.encode(password));
        String requestedRole = request.getOrDefault("role", "ROLE_CUSTOMER");
        Role role;
        try {
            role = Role.valueOf(requestedRole.toUpperCase());
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(Map.of("error", "Rol invalid."));
        }
        if (role == Role.ROLE_ADMIN) {
            return ResponseEntity.status(403).body(Map.of("error", "ADMIN nu poate fi creat prin auto-inregistrare."));
        }
        user.setRole(role);

        userRepository.save(user);
        return ResponseEntity.ok(Map.of("message", "Utilizator Ã®nregistrat cu succes!"));
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
