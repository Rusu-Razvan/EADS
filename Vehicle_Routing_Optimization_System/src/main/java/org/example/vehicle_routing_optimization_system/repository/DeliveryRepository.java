package org.example.vehicle_routing_optimization_system.repository;

import org.example.vehicle_routing_optimization_system.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findByCustomerId(Long customerId);
}