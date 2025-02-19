package com.jettdrafahl.money_sender.service;

import com.jettdrafahl.money_sender.dao.TransactionDao;
import com.jettdrafahl.money_sender.model.Transaction;
import com.jettdrafahl.money_sender.service.TransactionService;
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
        return transactionDao.getTransactionById(id);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionDao.getAllTransactions();
    }

    @Override
    public List<Transaction> getTransactionsBySender(Long senderAccountId) {
        return transactionDao.getTransactionsBySender(senderAccountId);
    }

    @Override
    public List<Transaction> getTransactionsByReceiver(Long receiverAccountId) {
        return transactionDao.getTransactionsByReceiver(receiverAccountId);
    }

    @Override
    public Long createTransaction(Transaction transaction) {
        return transactionDao.createTransaction(transaction);
    }

    @Override
    public boolean updateTransaction(Transaction transaction) {
        return transactionDao.updateTransaction(transaction);
    }

    @Override
    public boolean deleteTransaction(Long id) {
        return transactionDao.deleteTransaction(id);
    }
}
