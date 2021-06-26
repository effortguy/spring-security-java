package com.example.springsecurityjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringSecurityJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityJavaApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        // 기존 전략이 noop에서 bcrypt로 변경
        // return NoOpPasswordEncoder.getInstance();

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
