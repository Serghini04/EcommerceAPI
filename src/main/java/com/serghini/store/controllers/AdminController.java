package com.serghini.store.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@Tag(name="Admin", description="Administrative operations")
public class AdminController {
    @PostMapping("admin/hello")
    @Operation(summary = "Say hello")
    public ResponseEntity<String> sayHello() {
            return ResponseEntity.ok("Hey Bro!");
    }
    
}
