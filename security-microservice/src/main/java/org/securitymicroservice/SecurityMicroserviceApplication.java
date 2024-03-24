package org.securitymicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients

public class SecurityMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityMicroserviceApplication.class, args);
    }

}
