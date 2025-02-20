package com.jettdrafahl.money_sender.service;

import com.jettdrafahl.money_sender.dao.AccountDao;
import com.jettdrafahl.money_sender.exception.ResourceNotFoundException;
import com.jettdrafahl.money_sender.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao;

    @Autowired
    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public Account getAccountById(Long id) {
        Account account = accountDao.getAccountById(id);
        if (account == null) {
            throw new ResourceNotFoundException("Account with ID " + id + " not found.");
        }
        return account;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountDao.getAllAccounts();
    }

    @Override
    public List<Account> getAccountsByUserId(Long userId) {
        List<Account> accounts = accountDao.getAccountsByUserId(userId);
        if (accounts.isEmpty()) {
            throw new ResourceNotFoundException("No accounts found for user ID " + userId);
        }
        return accounts;
    }

    @Override
    public Long createAccount(Account account) {
        return accountDao.createAccount(account);
    }

    @Override
    public boolean updateAccount(Account account) {
        if (accountDao.getAccountById(account.getId()) == null) {
            throw new ResourceNotFoundException("Cannot update. Account with ID " + account.getId() + " not found.");
        }
        return accountDao.updateAccount(account);
    }

    @Override
    public boolean deleteAccount(Long id) {
        if (accountDao.getAccountById(id) == null) {
            throw new ResourceNotFoundException("Cannot delete. Account with ID " + id + " not found.");
        }
        return accountDao.deleteAccount(id);
    }
}
