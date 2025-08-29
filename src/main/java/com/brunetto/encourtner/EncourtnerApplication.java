package com.brunetto.encourtner;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@OpenAPIDefinition(info = @Info(title = "Encourtner", version = "1.0", description = "API to create and manage shortened URLs"))
public class EncourtnerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EncourtnerApplication.class, args);
    }

}
