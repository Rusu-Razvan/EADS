package org.example.vehicle_routing_optimization_system.controller;

import jakarta.validation.Valid;
import org.example.vehicle_routing_optimization_system.dto.DeliveryDTO;
import org.example.vehicle_routing_optimization_system.model.Delivery;
import org.example.vehicle_routing_optimization_system.service.DeliveryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {
    private final DeliveryService service;
    public DeliveryController(DeliveryService service) { this.service = service; }
    @GetMapping public List<DeliveryDTO> all() { return service.getAllDeliveries().stream().map(this::dto).collect(Collectors.toList()); }
    @GetMapping("/{id}") public DeliveryDTO one(@PathVariable Long id) { return dto(service.getRequired(id)); }
    @PostMapping public ResponseEntity<DeliveryDTO> create(@Valid @RequestBody DeliveryDTO input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dto(save(input, new Delivery())));
    }
    @PutMapping("/{id}") public DeliveryDTO update(@PathVariable Long id, @Valid @RequestBody DeliveryDTO input) {
        return dto(save(input, service.getRequired(id)));
    }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.deleteDelivery(id); }
    private Delivery save(DeliveryDTO input, Delivery d) {
        d.setCustomer(service.getCustomer(input.getCustomerId())); d.setQuantity(input.getQuantity());
        d.setNotes(input.getNotes()); d.setTimestamp(input.getTimestamp()); return service.saveDelivery(d);
    }
    private DeliveryDTO dto(Delivery d) {
        DeliveryDTO x = new DeliveryDTO(); x.setId(d.getId()); x.setCustomerId(d.getCustomer().getId());
        x.setQuantity(d.getQuantity()); x.setNotes(d.getNotes()); x.setTimestamp(d.getTimestamp()); return x;
    }
}
