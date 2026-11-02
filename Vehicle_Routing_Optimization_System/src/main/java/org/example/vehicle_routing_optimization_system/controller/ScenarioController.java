package org.example.vehicle_routing_optimization_system.controller;

import jakarta.validation.Valid;
import org.example.vehicle_routing_optimization_system.dto.ScenarioDTO;
import org.example.vehicle_routing_optimization_system.model.Scenario;
import org.example.vehicle_routing_optimization_system.service.ScenarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/scenarios")
public class ScenarioController {
    private final ScenarioService service;
    public ScenarioController(ScenarioService service) { this.service = service; }
    @GetMapping public List<ScenarioDTO> all() { return service.getAllScenarios().stream().map(service::toDto).collect(Collectors.toList()); }
    @GetMapping("/{id}") public ScenarioDTO one(@PathVariable Long id) { return service.toDto(service.getScenarioById(id)); }
    @PostMapping public ResponseEntity<ScenarioDTO> create(@Valid @RequestBody ScenarioDTO input) {
        Scenario saved = service.fromDto(input); service.saveScenario(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.toDto(saved));
    }
    @PutMapping("/{id}") public ScenarioDTO update(@PathVariable Long id, @Valid @RequestBody ScenarioDTO input) {
        Scenario existing = service.getScenarioById(id); Scenario replacement = service.fromDto(input);
        existing.setName(replacement.getName()); existing.setVehicles(replacement.getVehicles()); existing.setDeliveries(replacement.getDeliveries());
        service.saveScenario(existing); return service.toDto(existing);
    }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.deleteScenario(id); }
}
