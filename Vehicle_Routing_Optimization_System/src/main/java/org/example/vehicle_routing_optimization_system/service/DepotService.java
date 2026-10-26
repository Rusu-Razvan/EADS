package org.example.vehicle_routing_optimization_system.service;

import org.example.vehicle_routing_optimization_system.model.Depot;
import org.example.vehicle_routing_optimization_system.repository.DepotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepotService {

    private final DepotRepository depotRepository;

    public DepotService(DepotRepository depotRepository) {
        this.depotRepository = depotRepository;
    }

    public List<Depot> getAllDepots() {
        return depotRepository.findAll();
    }

    public Optional<Depot> getDepotById(Long id) {
        return depotRepository.findById(id);
    }

    public Depot saveDepot(Depot depot) {
        return depotRepository.save(depot);
    }

    public void deleteDepot(Long id) {
        depotRepository.deleteById(id);
    }
}