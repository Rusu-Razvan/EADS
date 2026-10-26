package org.example.vehicle_routing_optimization_system;

import org.example.vehicle_routing_optimization_system.model.*;
import org.example.vehicle_routing_optimization_system.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class VrosRunner implements CommandLineRunner {

    private final CustomerService customerService;
    private final DepotService depotService;
    private final DriverService driverService;
    private final VehicleService vehicleService;
    private final DeliveryService deliveryService;

    public VrosRunner(CustomerService customerService,
                      DepotService depotService,
                      DriverService driverService,
                      VehicleService vehicleService,
                      DeliveryService deliveryService) {
        this.customerService = customerService;
        this.depotService = depotService;
        this.driverService = driverService;
        this.vehicleService = vehicleService;
        this.deliveryService = deliveryService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>> Pornire VROS Test Runner (Homework) <<<");

        // Curățăm datele existente pentru a porni de la zero (opțional, respectând ordinea cheilor străine)
        // Notă: În aplicații reale ștergerea se face prin metode dedicate de delete,
        // aici doar adăugăm date noi dacă baza e goală.

        if (customerService.getAllCustomers().isEmpty()) {
            // 1. Creare și salvare Clienți
            Customer c1 = new Customer("Alexandru Popescu", "alex.popescu@gmail.com", "0721112233", "Str. Libertății 15", "44.4322, 26.1062");
            Customer c2 = new Customer("Maria Ionescu", "maria.ionescu@gmail.com", "0733445566", "Bd. Unirii 40", "44.4211, 26.1145");

            customerService.saveCustomer(c1);
            customerService.saveCustomer(c2);

            // 2. Creare și salvare Depozit (Depot)
            Depot depot = new Depot("Depozitul Central București", "44.4400, 26.0900");
            depotService.saveDepot(depot);

            // 3. Creare și salvare Șofer (Driver)
            Driver driver = new Driver("Ion Șofer", "ion.sofer@vros.com", "0755667788");
            driverService.saveDriver(driver);

            // 4. Creare și salvare Vehicul (Vehicle legat de Depot și Driver)
            Vehicle vehicle = new Vehicle("Camioneta 1.5T", 1500.0, depot);
            vehicle.setDriver(driver);
            vehicleService.saveVehicle(vehicle);

            // 5. Creare și salvare Livrare (Delivery legată de Customer)
            Delivery delivery1 = new Delivery(250.0, "Livrare fragilă - etajul 2", LocalDateTime.now().plusHours(2), c1);
            Delivery delivery2 = new Delivery(500.0, "Livrare paletizată", LocalDateTime.now().plusHours(4), c2);

            deliveryService.saveDelivery(delivery1);
            deliveryService.saveDelivery(delivery2);

            System.out.println(">>> Datele de test au fost inserate cu succes! <<<");
        } else {
            System.out.println(">>> Baza de date conține deja date. Număr clienți: " + customerService.getAllCustomers().size() + " <<<");
        }
    }
}