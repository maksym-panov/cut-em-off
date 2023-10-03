package com.panov.resolver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ShortcutResolverApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShortcutResolverApplication.class, args);
    }
}
