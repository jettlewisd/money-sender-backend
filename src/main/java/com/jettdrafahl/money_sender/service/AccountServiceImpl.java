package com.jettdrafahl.money_sender.service;


import com.jettdrafahl.money_sender.dao.AccountDao;
import com.jettdrafahl.money_sender.model.Account;
import com.jettdrafahl.money_sender.service.AccountService;
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
        return accountDao.getAccountById(id);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountDao.getAllAccounts();
    }

    @Override
    public List<Account> getAccountsByUserId(Long userId) {
        return accountDao.getAccountsByUserId(userId);
    }

    @Override
    public Long createAccount(Account account) {
        return accountDao.createAccount(account);
    }

    @Override
    public boolean updateAccount(Account account) {
        return accountDao.updateAccount(account);
    }

    @Override
    public boolean deleteAccount(Long id) {
        return accountDao.deleteAccount(id);
    }
}
