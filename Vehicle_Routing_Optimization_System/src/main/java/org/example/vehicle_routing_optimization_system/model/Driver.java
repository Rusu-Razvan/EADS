package org.example.vehicle_routing_optimization_system.model;

import jakarta.persistence.*;

@Entity
@Table(name = "drivers")
public class Driver extends Person {

    @OneToOne(mappedBy = "driver")
    private Vehicle vehicle;

    public Driver() {}

    public Driver(String name, String email, String phone) {
        super(name, email, phone);
    }

    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
}