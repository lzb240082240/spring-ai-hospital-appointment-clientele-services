package com.example.springai.hospitalappointment.clienteleservices;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.springai.hospitalappointment.clienteleservices.mapper")
@SpringBootApplication
public class SpringAiClienteleServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAiClienteleServicesApplication.class, args);
    }

}
