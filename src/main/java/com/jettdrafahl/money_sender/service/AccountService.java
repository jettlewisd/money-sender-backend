package com.jettdrafahl.money_sender.service;

import com.jettdrafahl.money_sender.model.Account;
import java.util.List;

public interface AccountService {
    Account getAccountById(Long id);
    List<Account> getAllAccounts();
    List<Account> getAccountsByUserId(Long userId);
    Long createAccount(Account account);
    boolean updateAccount(Account account);
    boolean deleteAccount(Long id);

    // Non-core-CRUD methods




}
