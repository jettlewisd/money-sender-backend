package com.jettdrafahl.money_sender.jdbc;

import com.jettdrafahl.money_sender.dao.AccountDao;
import com.jettdrafahl.money_sender.model.Account;
import com.jettdrafahl.money_sender.rowmapper.AccountRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcAccountDao implements AccountDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account getAccountById(Long id) {
        String sql = "SELECT * FROM accounts WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new AccountRowMapper(), id);
    }

    @Override
    public List<Account> getAllAccounts() {
        String sql = "SELECT * FROM accounts";
        return jdbcTemplate.query(sql, new AccountRowMapper());
    }

    @Override
    public List<Account> getAccountsByUserId(Long userId) {
        String sql = "SELECT * FROM accounts WHERE user_id = ?";
        return jdbcTemplate.query(sql, new AccountRowMapper(), userId);
    }

    @Override
    public Long createAccount(Account account) {
        String sql = "INSERT INTO accounts (user_id, balance, account_type) VALUES (?, ?, ?) RETURNING id";
        return jdbcTemplate.queryForObject(sql, Long.class, account.getUserId(), account.getBalance(), account.getAccountType());
    }

    @Override
    public boolean updateAccount(Account account) {
        String sql = "UPDATE accounts SET user_id = ?, balance = ?, account_type = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, account.getUserId(), account.getBalance(), account.getAccountType(), account.getId());
        return rowsAffected > 0;
    }

    @Override
    public boolean deleteAccount(Long id) {
        String sql = "DELETE FROM accounts WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }
}
