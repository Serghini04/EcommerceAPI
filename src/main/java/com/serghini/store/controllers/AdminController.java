package com.serghini.store.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @PostMapping("admin/hello")
    public ResponseEntity<String> sayHello() {
            return ResponseEntity.ok("Hey Bro!");
    }
    
}
