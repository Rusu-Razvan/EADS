package org.example.vehicle_routing_optimization_system.service;

import org.example.vehicle_routing_optimization_system.exception.ResourceNotFoundException;
import org.example.vehicle_routing_optimization_system.model.Scenario;
import org.example.vehicle_routing_optimization_system.dto.ScenarioDTO;
import org.example.vehicle_routing_optimization_system.model.Vehicle;
import org.example.vehicle_routing_optimization_system.model.Delivery;
import org.example.vehicle_routing_optimization_system.repository.VehicleRepository;
import org.example.vehicle_routing_optimization_system.repository.DeliveryRepository;
import org.example.vehicle_routing_optimization_system.repository.ScenarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScenarioService {

    private final ScenarioRepository scenarioRepository;
    private final VehicleRepository vehicleRepository;
    private final DeliveryRepository deliveryRepository;

    public ScenarioService(ScenarioRepository scenarioRepository, VehicleRepository vehicleRepository,
                           DeliveryRepository deliveryRepository) {
        this.scenarioRepository = scenarioRepository;
        this.vehicleRepository = vehicleRepository;
        this.deliveryRepository = deliveryRepository;
    }

    public List<Scenario> getAllScenarios() {
        return scenarioRepository.findAll();
    }

    public Scenario getScenarioById(Long id) {
        return scenarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Scenariul cu ID-ul " + id + " nu a fost găsit."));
    }

    public void saveScenario(Scenario scenario) {
        scenarioRepository.save(scenario);
    }

    public ScenarioDTO toDto(Scenario scenario) {
        ScenarioDTO dto = new ScenarioDTO();
        dto.setId(scenario.getId());
        dto.setName(scenario.getName());
        dto.setVehicleIds(scenario.getVehicles().stream().map(Vehicle::getId).collect(java.util.stream.Collectors.toSet()));
        dto.setDeliveryIds(scenario.getDeliveries().stream().map(Delivery::getId).collect(java.util.stream.Collectors.toSet()));
        return dto;
    }

    public Scenario fromDto(ScenarioDTO dto) {
        Scenario scenario = new Scenario();
        scenario.setName(dto.getName());
        scenario.setVehicles(dto.getVehicleIds() == null ? new java.util.HashSet<>() :
                new java.util.HashSet<>(vehicleRepository.findAllById(dto.getVehicleIds())));
        scenario.setDeliveries(dto.getDeliveryIds() == null ? new java.util.HashSet<>() :
                new java.util.HashSet<>(deliveryRepository.findAllById(dto.getDeliveryIds())));
        if (dto.getVehicleIds() != null && scenario.getVehicles().size() != dto.getVehicleIds().size())
            throw new ResourceNotFoundException("Unul dintre vehiculele scenariului nu există.");
        if (dto.getDeliveryIds() != null && scenario.getDeliveries().size() != dto.getDeliveryIds().size())
            throw new ResourceNotFoundException("Una dintre livrările scenariului nu există.");
        return scenario;
    }

    public void deleteScenario(Long id) {
        if (!scenarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Scenariul cu ID-ul " + id + " nu a fost găsit pentru ștergere.");
        }
        scenarioRepository.deleteById(id);
    }
}