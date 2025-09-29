package com.hostel.mess.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@Tag(name = "Home", description = "Home and general APIs")
public class HomeController {
    
    @GetMapping
    @Operation(summary = "Welcome message", description = "Get welcome message for the application")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome to Smart Hostel Mess Management System! " +
                "Visit /swagger-ui.html for API documentation.");
    }
    
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Check if the application is running")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Application is running!");
    }
}

