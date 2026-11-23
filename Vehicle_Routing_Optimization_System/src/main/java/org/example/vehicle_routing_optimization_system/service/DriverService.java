package org.example.vehicle_routing_optimization_system.service;

import org.example.vehicle_routing_optimization_system.model.Driver;
import org.example.vehicle_routing_optimization_system.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverService {

    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public Optional<Driver> getDriverById(Long id) {
        return driverRepository.findById(id);
    }

    public Driver saveDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    public Driver createProfile(String name, String email, String phone) {
        return driverRepository.save(new Driver(name, email, phone));
    }

    public Optional<Driver> findByEmail(String email) {
        return driverRepository.findByEmail(email);
    }

    public void deleteDriver(Long id) {
        driverRepository.deleteById(id);
    }
}