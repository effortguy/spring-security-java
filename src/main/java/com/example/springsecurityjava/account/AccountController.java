package com.example.springsecurityjava.account;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/account")
    public void createAccount() {
        Account user = new Account("keesun", "123", "USER");
        Account admin = new Account("admin", "123", "ADMIN");

        accountService.createNew(user);
        accountService.createNew(admin);
    }
}
