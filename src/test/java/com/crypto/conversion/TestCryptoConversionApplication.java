package com.crypto.conversion;

import org.springframework.boot.SpringApplication;

public class TestCryptoConversionApplication {

    public static void main(String[] args) {
        SpringApplication.from(CryptoConversionApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
