package com.parking_system.io.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthApi {
    @GetMapping("/ping")
    public String healthCheck() {
        return "pong";
    }
}
