DROP TABLE IF EXISTS scenario_deliveries CASCADE;
DROP TABLE IF EXISTS scenario_vehicles CASCADE;
DROP TABLE IF EXISTS scenarios CASCADE;
DROP TABLE IF EXISTS deliveries CASCADE;
DROP TABLE IF EXISTS vehicles CASCADE;
DROP TABLE IF EXISTS drivers CASCADE;
DROP TABLE IF EXISTS depots CASCADE;
DROP TABLE IF EXISTS customers CASCADE;

-- 1. Tabela Depots (cu x și y numerice)
CREATE TABLE depots (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        x INTEGER NOT NULL,
                        y INTEGER NOT NULL
);

-- 2. Tabela Drivers
CREATE TABLE drivers (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         email VARCHAR(100) UNIQUE NOT NULL,
                         phone VARCHAR(20)
);

-- 3. Tabela Vehicles
CREATE TABLE vehicles (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(50) NOT NULL,
                          capacity DOUBLE PRECISION NOT NULL,
                          available BOOLEAN NOT NULL DEFAULT TRUE,
                          depot_id BIGINT NOT NULL,
                          driver_id BIGINT UNIQUE,
                          CONSTRAINT fk_vehicle_depot FOREIGN KEY (depot_id) REFERENCES depots(id) ON DELETE CASCADE,
                          CONSTRAINT fk_vehicle_driver FOREIGN KEY (driver_id) REFERENCES drivers(id) ON DELETE SET NULL
);

-- 4. Tabela Customers (cu x și y numerice)
CREATE TABLE customers (
                           id BIGSERIAL PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           email VARCHAR(100) UNIQUE NOT NULL,
                           phone VARCHAR(20),
                           address VARCHAR(255) NOT NULL,
                           x INTEGER NOT NULL,
                           y INTEGER NOT NULL
);

-- 5. Tabela Deliveries
CREATE TABLE deliveries (
                            id BIGSERIAL PRIMARY KEY,
                            customer_id BIGINT NOT NULL,
                            quantity DOUBLE PRECISION NOT NULL,
                            notes TEXT,
                            timestamp TIMESTAMP NOT NULL,
                            CONSTRAINT fk_delivery_customer FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
);

-- 6. Tabela Scenarios (Homework)
CREATE TABLE scenarios (
                           id BIGSERIAL PRIMARY KEY,
                           name VARCHAR(100) NOT NULL UNIQUE
);

-- 7. Tabela de joncțiune Many-to-Many: Scenario <-> Vehicles
CREATE TABLE scenario_vehicles (
                                   scenario_id BIGINT NOT NULL,
                                   vehicle_id BIGINT NOT NULL,
                                   PRIMARY KEY (scenario_id, vehicle_id),
                                   CONSTRAINT fk_sc_veh_scenario FOREIGN KEY (scenario_id) REFERENCES scenarios(id) ON DELETE CASCADE,
                                   CONSTRAINT fk_sc_veh_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicles(id) ON DELETE CASCADE
);

-- 8. Tabela de joncțiune Many-to-Many: Scenario <-> Deliveries
CREATE TABLE scenario_deliveries (
                                     scenario_id BIGINT NOT NULL,
                                     delivery_id BIGINT NOT NULL,
                                     PRIMARY KEY (scenario_id, delivery_id),
                                     CONSTRAINT fk_sc_del_scenario FOREIGN KEY (scenario_id) REFERENCES scenarios(id) ON DELETE CASCADE,
                                     CONSTRAINT fk_sc_del_delivery FOREIGN KEY (delivery_id) REFERENCES deliveries(id) ON DELETE CASCADE
);