package org.example.lab3;


import org.springframework.stereotype.Component;

@Component
public class V6Engine implements Engine {
    @Override
    public String getType() {
        return "V6 Turbo Engine";
    }
}

