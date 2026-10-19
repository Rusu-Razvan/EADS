package org.example.lab3;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Car {

    //2. Field Injection
    @Autowired
    private Engine fieldInjectedEngine;

    private final Engine constructorInjectedEngine;
    private Engine setterInjectedEngine;
    private Engine methodInjectedEngine;

    //1. Constructor Injection
    public Car(Engine constructorInjectedEngine) {
        this.constructorInjectedEngine = constructorInjectedEngine;

        System.out.println("1 - Constructor Injection executat. Implementare injectată: "
                + constructorInjectedEngine.getClass().getSimpleName());
        System.out.println("    - Status Field Injection în constructor: "
                + (this.fieldInjectedEngine != null ? "Injected" : "Null (Injecția pe câmp urmează)"));
    }

    // 3. Setter Injection
    @Autowired
    public void setSetterInjectedEngine(Engine setterInjectedEngine) {
        this.setterInjectedEngine = setterInjectedEngine;

        System.out.println("-> [3] Setter Injection executat.");
        System.out.println("    - Status Field Injection în setter: "
                + (this.fieldInjectedEngine != null ? "A fost deja injectat!" : "Null"));
    }

    // 4. Method Injection
    @Autowired
    public void customConfigurationMethod(Engine methodInjectedEngine) {
        this.methodInjectedEngine = methodInjectedEngine;

        System.out.println("-> [4] General Method Injection executat.");
    }

    @PostConstruct
    public void init() {
        System.out.println("-> [5] @PostConstruct: Toate injecțiile s-au încheiat. Tip motor: "
                + fieldInjectedEngine.getType());
    }
}
