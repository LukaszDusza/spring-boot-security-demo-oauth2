package com.example.demo;

import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {

    @Test
    public void getHash() {
        getPasswordHash();
    }

    @Bean
    public String getPasswordHash() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("user"));
        return bCryptPasswordEncoder.encode("user");
    }
}
