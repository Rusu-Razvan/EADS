package org.example.vehicle_routing_optimization_system.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
@Embeddable
public class Coordinates {
    @NotNull(message = "Coordonata X este obligatorie")
    private Integer x;

    @NotNull(message = "Coordonata Y este obligatorie")
    private Integer y;

    public Coordinates() {}
    public Coordinates(Integer x, Integer y) { this.x = x; this.y = y; }
    public Integer getX() { return x; }
    public void setX(Integer x) { this.x = x; }
    public Integer getY() { return y; }
    public void setY(Integer y) { this.y = y; }

}