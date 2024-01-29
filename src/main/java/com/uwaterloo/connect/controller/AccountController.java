package com.uwaterloo.connect.controller;

import com.uwaterloo.connect.model.Account;
import com.uwaterloo.connect.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")//TODO: Change to something meaningful
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @GetMapping("/accounts/{email}")
    public ResponseEntity<Account> getAccountById(@PathVariable(value = "email") String email)
            throws RuntimeException {
        Account account = accountRepository.findById(email)
                .orElseThrow(() -> new RuntimeException("Account not found for: " + email));
        return ResponseEntity.ok().body(account);
    }

}
