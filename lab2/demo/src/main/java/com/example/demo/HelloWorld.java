package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

    @Value("${greeting.message:Salut implicit!}")
    private String greetingMessage;

    @GetMapping("/hello-world")
    public String sayHello() {
        return greetingMessage;
    }
}
