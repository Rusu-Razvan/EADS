package org.example.vehicle_routing_optimization_system.controller;

import jakarta.validation.Valid;
import org.example.vehicle_routing_optimization_system.dto.VehicleDTO;
import org.example.vehicle_routing_optimization_system.model.Vehicle;
import org.example.vehicle_routing_optimization_system.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    private final VehicleService service;
    public VehicleController(VehicleService service) { this.service = service; }

    @GetMapping public List<VehicleDTO> all() { return service.getAllVehicles().stream().map(this::dto).collect(Collectors.toList()); }
    @GetMapping("/{id}") public VehicleDTO one(@PathVariable Long id) { return dto(service.getRequired(id)); }
    @PostMapping public ResponseEntity<VehicleDTO> create(@Valid @RequestBody VehicleDTO input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dto(save(input, new Vehicle())));
    }
    @PutMapping("/{id}") public VehicleDTO update(@PathVariable Long id, @Valid @RequestBody VehicleDTO input) {
        return dto(save(input, service.getRequired(id)));
    }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.deleteVehicle(id); }

    private Vehicle save(VehicleDTO input, Vehicle vehicle) {
        vehicle.setName(input.getName()); vehicle.setCapacity(input.getCapacity());
        vehicle.setAvailable(input.isAvailable()); vehicle.setDepot(service.getDepot(input.getDepotId()));
        return service.saveVehicle(vehicle);
    }
    private VehicleDTO dto(Vehicle v) {
        VehicleDTO d = new VehicleDTO(); d.setId(v.getId()); d.setName(v.getName()); d.setCapacity(v.getCapacity());
        d.setAvailable(v.isAvailable()); d.setDepotId(v.getDepot().getId()); return d;
    }
}
