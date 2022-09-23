package com.devfortech.crudservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@OpenAPIDefinition
public class CrudServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudServiceApplication.class, args);
    }
    
}
