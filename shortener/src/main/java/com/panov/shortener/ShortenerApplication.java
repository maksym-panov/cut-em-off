package com.panov.shortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ShortenerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShortenerApplication.class, args);
    }
}
