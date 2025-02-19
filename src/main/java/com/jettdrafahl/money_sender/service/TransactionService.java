package com.jettdrafahl.money_sender.service;

import com.jettdrafahl.money_sender.model.Transaction;
import java.util.List;

public interface TransactionService {
    Transaction getTransactionById(Long id);
    List<Transaction> getAllTransactions();
    List<Transaction> getTransactionsBySender(Long senderAccountId);
    List<Transaction> getTransactionsByReceiver(Long receiverAccountId);
    Long createTransaction(Transaction transaction);
    boolean updateTransaction(Transaction transaction);
    boolean deleteTransaction(Long id);
}
