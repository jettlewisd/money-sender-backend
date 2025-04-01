package com.jettdrafahl.money_sender.service;

import com.jettdrafahl.money_sender.dao.AccountDao;
import com.jettdrafahl.money_sender.dao.TransactionDao;
import com.jettdrafahl.money_sender.exception.ResourceNotFoundException;
import com.jettdrafahl.money_sender.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionDao transactionDao;
    private final AccountDao accountDao;

    @Autowired
    public TransactionServiceImpl(TransactionDao transactionDao, AccountDao accountDao) {
        this.transactionDao = transactionDao;
        this.accountDao = accountDao;
    }

    @Override
    public Transaction getTransactionById(Long id) {
        Transaction transaction = transactionDao.getTransactionById(id);
        if (transaction == null) {
            throw new ResourceNotFoundException("Transaction with ID " + id + " not found.");
        }
        return transaction;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionDao.getAllTransactions();
    }

    @Override
    public List<Transaction> getTransactionsBySender(Long senderAccountId) {
        List<Transaction> transactions = transactionDao.getTransactionsBySender(senderAccountId);
        if (transactions.isEmpty()) {
            throw new ResourceNotFoundException("No transactions found for sender account ID " + senderAccountId);
        }
        return transactions;
    }

    @Override
    public List<Transaction> getTransactionsByReceiver(Long receiverAccountId) {
        List<Transaction> transactions = transactionDao.getTransactionsByReceiver(receiverAccountId);
        if (transactions.isEmpty()) {
            throw new ResourceNotFoundException("No transactions found for receiver account ID " + receiverAccountId);
        }
        return transactions;
    }

    @Override
    public Long createTransaction(Transaction transaction) {
        return transactionDao.createTransaction(transaction);
    }

    @Override
    public boolean updateTransaction(Transaction transaction) {
        if (transactionDao.getTransactionById(transaction.getId()) == null) {
            throw new ResourceNotFoundException("Cannot update. Transaction with ID " + transaction.getId() + " not found.");
        }
        return transactionDao.updateTransaction(transaction);
    }

    @Override
    public boolean deleteTransaction(Long id) {
        if (transactionDao.getTransactionById(id) == null) {
            throw new ResourceNotFoundException("Cannot delete. Transaction with ID " + id + " not found.");
        }
        return transactionDao.deleteTransaction(id);
    }

    // Non CRUD methods:

    @Override
    @Transactional
    public boolean transferMoney(Long senderAccountId, Long receiverAccountId, Double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than zero.");
        }

        Double senderBalance = accountDao.getBalance(senderAccountId);
        if (senderBalance == null) {
            throw new ResourceNotFoundException("Sender account with ID " + senderAccountId + " not found.");
        }
        Double receiverBalance = accountDao.getBalance(receiverAccountId);
        if (receiverBalance == null) {
            throw new ResourceNotFoundException("Receiver account with ID " + receiverAccountId + " not found.");
        }

        if (senderBalance < amount) {
            throw new IllegalArgumentException("Insufficient funds in sender's account.");
        }


        accountDao.updateBalance(senderAccountId, -amount);
        accountDao.updateBalance(receiverAccountId, amount);


        Transaction transaction = new Transaction(null, senderAccountId, receiverAccountId, amount, new Timestamp(System.currentTimeMillis()));
        Long transactionId = transactionDao.createTransaction(transaction);

        return transactionId != null;
    }


}
