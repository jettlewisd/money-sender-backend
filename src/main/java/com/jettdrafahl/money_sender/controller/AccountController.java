package com.jettdrafahl.money_sender.controller;

import com.jettdrafahl.money_sender.model.Account;
import com.jettdrafahl.money_sender.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        Account account = accountService.getAccountById(id);
        return ResponseEntity.ok(account);
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Account>> getAccountsByUserId(@PathVariable Long userId) {
        List<Account> accounts = accountService.getAccountsByUserId(userId);
        return ResponseEntity.ok(accounts);
    }

    @PostMapping
    public ResponseEntity<Long> createAccount(@RequestBody Account account) {
        Long accountId = accountService.createAccount(account);
        return ResponseEntity.ok(accountId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateAccount(@PathVariable Long id, @RequestBody Account account) {
        account.setId(id);
        boolean updated = accountService.updateAccount(account);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAccount(@PathVariable Long id) {
        boolean deleted = accountService.deleteAccount(id);
        return ResponseEntity.ok(deleted);
    }
}
