-- 1. Tabela Depots
CREATE TABLE depots (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        coordinates VARCHAR(100) NOT NULL
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

-- 4. Tabela Customers
CREATE TABLE customers (
                           id BIGSERIAL PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           email VARCHAR(100) UNIQUE NOT NULL,
                           phone VARCHAR(20),
                           address VARCHAR(255) NOT NULL,
                           coordinates VARCHAR(100) NOT NULL
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