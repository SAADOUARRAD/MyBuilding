package com.mybuilding.residents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ResidentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResidentsApplication.class, args);
    }
}
