package org.example.vehicle_routing_optimization_system.service;

import org.example.vehicle_routing_optimization_system.model.Role;
import org.example.vehicle_routing_optimization_system.model.User;
import org.example.vehicle_routing_optimization_system.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final CustomerService customerService;
    private final DriverService driverService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, CustomerService customerService,
                       DriverService driverService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.customerService = customerService;
        this.driverService = driverService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Map<String, Object> register(Map<String, String> request) {
        String email = required(request, "email");
        String password = required(request, "password");

        if (userRepository.findByEmail(email).isPresent()
                || customerService.findByEmail(email).isPresent()
                || driverService.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Emailul este deja folosit!");
        }

        Role role = parseRole(request.getOrDefault("role", "ROLE_CUSTOMER"));
        if (role != Role.ROLE_CUSTOMER && role != Role.ROLE_DRIVER) {
            throw new RegistrationForbiddenException(
                    "Prin auto-inregistrare sunt permise doar rolurile CUSTOMER si DRIVER.");
        }

        User user = new User();
        user.setEmail(email);
        user.setUsername(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        Long profileId;
        if (role == Role.ROLE_CUSTOMER) {
            user.setCustomer(customerService.createProfile(
                    required(request, "name"),
                    email,
                    request.get("phone"),
                    required(request, "address"),
                    requiredInteger(request, "x"),
                    requiredInteger(request, "y")));
            profileId = user.getCustomer().getId();
        } else {
            user.setDriver(driverService.createProfile(
                    required(request, "name"), email, request.get("phone")));
            profileId = user.getDriver().getId();
        }

        userRepository.save(user);
        return Map.of("message", "Utilizator inregistrat cu succes!",
                "role", role.name(), "profileId", profileId);
    }

    private Role parseRole(String requestedRole) {
        try {
            if (requestedRole == null || requestedRole.isBlank()) {
                throw new IllegalArgumentException("Rol invalid.");
            }
            String normalizedRole = requestedRole.toUpperCase();
            return Role.valueOf(normalizedRole.startsWith("ROLE_")
                    ? normalizedRole : "ROLE_" + normalizedRole);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Rol invalid.");
        }
    }

    private String required(Map<String, String> request, String field) {
        String value = request.get(field);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Campul '" + field + "' este obligatoriu.");
        }
        return value;
    }

    private Integer requiredInteger(Map<String, String> request, String field) {
        try {
            return Integer.valueOf(required(request, field));
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Campul '" + field + "' trebuie sa fie numar intreg.");
        }
    }

    public static class RegistrationForbiddenException extends RuntimeException {
        public RegistrationForbiddenException(String message) {
            super(message);
        }
    }
}
