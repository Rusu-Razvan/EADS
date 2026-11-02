package org.example.vehicle_routing_optimization_system;

import org.example.vehicle_routing_optimization_system.model.*;
import org.example.vehicle_routing_optimization_system.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class VrosRunner implements CommandLineRunner {

    private final CustomerService customerService;
    private final DepotService depotService;
    private final VehicleService vehicleService;
    private final DeliveryService deliveryService;
    private final ScenarioService scenarioService;

    public VrosRunner(CustomerService customerService,
                      DepotService depotService,
                      VehicleService vehicleService,
                      DeliveryService deliveryService,
                      ScenarioService scenarioService) {
        this.customerService = customerService;
        this.depotService = depotService;
        this.vehicleService = vehicleService;
        this.deliveryService = deliveryService;
        this.scenarioService = scenarioService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>> Pornire VROS Test Runner (Homework Nou) <<<");

        if (customerService.getAllCustomers().isEmpty()) {
            // 1. Clienți cu structura de Coordinates (x, y)
            Customer c1 = new Customer();
            c1.setName("Alexandru Popescu");
            c1.setEmail("alex.popescu@gmail.com");
            c1.setPhone("0721112233");
            c1.setAddress("Str. Principală 15");
            c1.setCoordinates(new Coordinates(10, 20));

            Customer c2 = new Customer();
            c2.setName("Maria Ionescu");
            c2.setEmail("maria.ionescu@gmail.com");
            c2.setPhone("0733445566");
            c2.setAddress("Bd. Unirii 40");
            c2.setCoordinates(new Coordinates(15, 25));

            customerService.saveCustomer(c1);
            customerService.saveCustomer(c2);

            // 2. Depozit
            Depot depot = new Depot();
            depot.setName("Depozitul Central București");
            depot.setCoordinates(new Coordinates(0, 0));
            depotService.saveDepot(depot);

            // 3. Vehicule
            Vehicle v1 = new Vehicle();
            v1.setName("Camionetă 1.5T");
            v1.setCapacity(1500.0);
            v1.setDepot(depot);
            vehicleService.saveVehicle(v1);

            Vehicle v2 = new Vehicle();
            v2.setName("Dubă 3.5T");
            v2.setCapacity(3500.0);
            v2.setDepot(depot);
            vehicleService.saveVehicle(v2);

            // 4. Livrări
            Delivery d1 = new Delivery();
            d1.setCustomer(c1);
            d1.setQuantity(250.0);
            d1.setNotes("Livrare fragilă");
            d1.setTimestamp(LocalDateTime.now().plusHours(2));
            deliveryService.saveDelivery(d1);

            Delivery d2 = new Delivery();
            d2.setCustomer(c2);
            d2.setQuantity(500.0);
            d2.setNotes("Livrare paletizată");
            d2.setTimestamp(LocalDateTime.now().plusHours(4));
            deliveryService.saveDelivery(d2);

            // 5. Scenariu (Relația ManyToMany cu Vehicule și Livrări)
            Scenario scenario = new Scenario();
            scenario.setName("Scenariul 1 - București Nord");
            scenario.setVehicles(new HashSet<>(Set.of(v1, v2)));
            scenario.setDeliveries(new HashSet<>(Set.of(d1, d2)));
            scenarioService.saveScenario(scenario);

            System.out.println(">>> Datele de test cu noul model au fost inserate cu succes! <<<");
        } else {
            System.out.println(">>> Baza de date conține deja date. Număr scenarii: " + scenarioService.getAllScenarios().size() + " <<<");
        }
    }
}