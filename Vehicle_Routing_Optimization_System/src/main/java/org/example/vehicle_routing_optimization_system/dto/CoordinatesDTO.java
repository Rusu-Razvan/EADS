package org.example.vehicle_routing_optimization_system.dto;

import jakarta.validation.constraints.NotNull;
public class CoordinatesDTO {
    @NotNull(message = "Coordonata X este obligatorie")
    private Integer x;

    @NotNull(message = "Coordonata Y este obligatorie")
    private Integer y;

    public Integer getX() { return x; }
    public void setX(Integer x) { this.x = x; }
    public Integer getY() { return y; }
    public void setY(Integer y) { this.y = y; }
}