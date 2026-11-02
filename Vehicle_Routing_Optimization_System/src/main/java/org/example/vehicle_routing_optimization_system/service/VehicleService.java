package org.example.vehicle_routing_optimization_system.service;

import org.example.vehicle_routing_optimization_system.model.Vehicle;
import org.example.vehicle_routing_optimization_system.model.Depot;
import org.example.vehicle_routing_optimization_system.repository.DepotRepository;
import org.example.vehicle_routing_optimization_system.exception.ResourceNotFoundException;
import org.example.vehicle_routing_optimization_system.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final DepotRepository depotRepository;

    public VehicleService(VehicleRepository vehicleRepository, DepotRepository depotRepository) {
        this.vehicleRepository = vehicleRepository;
        this.depotRepository = depotRepository;
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Optional<Vehicle> getVehicleById(Long id) {
        return vehicleRepository.findById(id);
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(Long id) {
        if (!vehicleRepository.existsById(id)) throw new ResourceNotFoundException("Vehiculul nu a fost găsit.");
        vehicleRepository.deleteById(id);
    }

    public Vehicle getRequired(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehiculul cu ID-ul " + id + " nu a fost găsit."));
    }

    public Depot getDepot(Long id) {
        return depotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Depozitul cu ID-ul " + id + " nu a fost găsit."));
    }
}