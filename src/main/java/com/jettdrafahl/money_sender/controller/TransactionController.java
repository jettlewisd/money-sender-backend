package com.jettdrafahl.money_sender.controller;

import com.jettdrafahl.money_sender.model.Transaction;
import com.jettdrafahl.money_sender.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/sender/{senderId}")
    public ResponseEntity<List<Transaction>> getTransactionsBySender(@PathVariable Long senderId) {
        List<Transaction> transactions = transactionService.getTransactionsBySender(senderId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/receiver/{receiverId}")
    public ResponseEntity<List<Transaction>> getTransactionsByReceiver(@PathVariable Long receiverId) {
        List<Transaction> transactions = transactionService.getTransactionsByReceiver(receiverId);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping
    public ResponseEntity<Long> createTransaction(@RequestBody Transaction transaction) {
        Long transactionId = transactionService.createTransaction(transaction);
        return ResponseEntity.ok(transactionId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
        transaction.setId(id);
        boolean updated = transactionService.updateTransaction(transaction);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteTransaction(@PathVariable Long id) {
        boolean deleted = transactionService.deleteTransaction(id);
        return ResponseEntity.ok(deleted);
    }
}

