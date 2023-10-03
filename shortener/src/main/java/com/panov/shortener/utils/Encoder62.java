package com.panov.shortener.utils;

import org.springframework.stereotype.Component;

@Component
public class Encoder62 implements Encoder {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int BASE = 62;

    @Override
    public String encode(long num) {
        num = Math.abs(num);
        StringBuilder res = new StringBuilder();
        do {
            res.append(ALPHABET.charAt((int) (num % BASE)));
            num /= BASE;
        } while (num > 0);
        return res.toString();
    }
}
