package com.jettdrafahl.money_sender.service;

import com.jettdrafahl.money_sender.dao.TransactionDao;
import com.jettdrafahl.money_sender.exception.ResourceNotFoundException;
import com.jettdrafahl.money_sender.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionDao transactionDao;

    @Autowired
    public TransactionServiceImpl(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
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
}
