package org.example.vehicle_routing_optimization_system.security;

import org.example.vehicle_routing_optimization_system.model.User;
import org.example.vehicle_routing_optimization_system.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("customerAuthorization")
public class CustomerAuthorization {

    private final UserRepository userRepository;

    public CustomerAuthorization(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isOwner(Long customerId, Authentication authentication) {
        if (customerId == null || authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        return userRepository.findByEmail(authentication.getName())
                .map(User::getCustomer)
                .map(customer -> customerId.equals(customer.getId()))
                .orElse(false);
    }
}
