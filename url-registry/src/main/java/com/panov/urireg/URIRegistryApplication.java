package com.panov.urireg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class URIRegistryApplication {
    public static void main(String[] args) {
        SpringApplication.run(URIRegistryApplication.class, args);
    }
}
