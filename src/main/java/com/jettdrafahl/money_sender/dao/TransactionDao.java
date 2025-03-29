package com.jettdrafahl.money_sender.dao;

import com.jettdrafahl.money_sender.model.Transaction;
import java.util.List;

public interface TransactionDao {
    Transaction getTransactionById(Long id);
    List<Transaction> getAllTransactions();
    List<Transaction> getTransactionsBySender(Long senderAccountId);
    List<Transaction> getTransactionsByReceiver(Long receiverAccountId);
    Long createTransaction(Transaction transaction);
    boolean updateTransaction(Transaction transaction);
    boolean deleteTransaction(Long id);

    // Non CRUD methods:
}
