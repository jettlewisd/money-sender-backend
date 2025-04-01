package com.jettdrafahl.money_sender.dao;

import com.jettdrafahl.money_sender.model.Account;
import java.util.List;

public interface AccountDao {
    Account getAccountById(Long id);
    List<Account> getAllAccounts();
    List<Account> getAccountsByUserId(Long userId);
    Long createAccount(Account account);
    boolean updateAccount(Account account);
    boolean deleteAccount(Long id);


    // Non-core-CRUD methods
    Double getBalance(Long id);
    void updateBalance(Long userId, double amount);
}
