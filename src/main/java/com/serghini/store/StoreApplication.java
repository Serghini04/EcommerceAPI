package com.serghini.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StoreApplication {

    public static void main(String[] args) {
        System.out.println("Starting StoreApplication ...");
        SpringApplication.run(StoreApplication.class, args);
    }
}
