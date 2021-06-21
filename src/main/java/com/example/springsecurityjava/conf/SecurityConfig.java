package com.example.springsecurityjava.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                    .mvcMatchers("/", "/info").permitAll()
                    .mvcMatchers("/admin").hasRole("ADMIN")
                    .anyRequest().authenticated();
        httpSecurity.formLogin();
        httpSecurity.httpBasic();
    }
}
