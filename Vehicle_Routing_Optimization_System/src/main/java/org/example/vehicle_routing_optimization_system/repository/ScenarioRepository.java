package org.example.vehicle_routing_optimization_system.repository;

import org.example.vehicle_routing_optimization_system.model.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScenarioRepository extends JpaRepository<Scenario, Long> {
}