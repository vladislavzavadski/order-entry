package com.netcracker.orderentry.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
