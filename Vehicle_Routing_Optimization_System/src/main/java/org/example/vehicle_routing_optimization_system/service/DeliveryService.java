package org.example.vehicle_routing_optimization_system.service;

import org.example.vehicle_routing_optimization_system.model.Delivery;
import org.example.vehicle_routing_optimization_system.model.Customer;
import org.example.vehicle_routing_optimization_system.exception.ResourceNotFoundException;
import org.example.vehicle_routing_optimization_system.repository.DeliveryRepository;
import org.example.vehicle_routing_optimization_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final org.example.vehicle_routing_optimization_system.repository.CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public DeliveryService(DeliveryRepository deliveryRepository,
                           org.example.vehicle_routing_optimization_system.repository.CustomerRepository customerRepository,
                           UserRepository userRepository) {
        this.deliveryRepository = deliveryRepository;
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public List<Delivery> getDeliveriesForUser(String email) {
        Long customerId = userRepository.findByEmail(email)
                .map(user -> user.getCustomer() == null ? null : user.getCustomer().getId())
                .orElse(null);
        return customerId == null ? List.of() : deliveryRepository.findByCustomerId(customerId);
    }

    public Optional<Delivery> getDeliveryById(Long id) {
        return deliveryRepository.findById(id);
    }

    public Delivery saveDelivery(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    public void deleteDelivery(Long id) {
        if (!deliveryRepository.existsById(id)) throw new ResourceNotFoundException("Livrarea nu a fost găsită.");
        deliveryRepository.deleteById(id);
    }

    public Delivery getRequired(Long id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livrarea cu ID-ul " + id + " nu a fost găsită."));
    }

    public Customer getCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clientul cu ID-ul " + id + " nu a fost găsit."));
    }
}