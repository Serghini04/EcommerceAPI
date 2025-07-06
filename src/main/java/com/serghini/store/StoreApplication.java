package com.serghini.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StoreApplication {

    //   docker run -d \           
    //   --name mysql-store \
    //   -e MYSQL_ROOT_PASSWORD=MyPassword! \
    //   -e MYSQL_DATABASE=store_api \
    //   -v mysql_store_data:/var/lib/mysql \
    //   -p 3306:3306 \
    //   mysql:8
    public static void main(String[] args) {
        System.out.println("Starting StoreApplication ...");
        SpringApplication.run(StoreApplication.class, args);
    }
}
