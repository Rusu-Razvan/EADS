package org.example.vehicle_routing_optimization_system.security;

import org.example.vehicle_routing_optimization_system.model.Delivery;
import org.example.vehicle_routing_optimization_system.repository.DeliveryRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("deliveryAuthorization")
public class DeliveryAuthorization {

    private final DeliveryRepository deliveryRepository;
    private final CustomerAuthorization customerAuthorization;

    public DeliveryAuthorization(DeliveryRepository deliveryRepository,
                                  CustomerAuthorization customerAuthorization) {
        this.deliveryRepository = deliveryRepository;
        this.customerAuthorization = customerAuthorization;
    }

    public boolean isOwner(Long deliveryId, Authentication authentication) {
        return deliveryRepository.findById(deliveryId)
                .map(Delivery::getCustomer)
                .map(customer -> customerAuthorization.isOwner(customer.getId(), authentication))
                .orElse(false);
    }
}
