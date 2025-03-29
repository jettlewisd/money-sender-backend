package com.jettdrafahl.money_sender.jdbc;

import com.jettdrafahl.money_sender.dao.TransactionDao;
import com.jettdrafahl.money_sender.model.Transaction;
import com.jettdrafahl.money_sender.rowmapper.TransactionRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcTransactionDao implements TransactionDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTransactionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Transaction getTransactionById(Long id) {
        String sql = "SELECT * FROM transactions WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new TransactionRowMapper(), id);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        String sql = "SELECT * FROM transactions";
        return jdbcTemplate.query(sql, new TransactionRowMapper());
    }

    @Override
    public List<Transaction> getTransactionsBySender(Long senderAccountId) {
        String sql = "SELECT * FROM transactions WHERE sender_account_id = ?";
        return jdbcTemplate.query(sql, new TransactionRowMapper(), senderAccountId);
    }

    @Override
    public List<Transaction> getTransactionsByReceiver(Long receiverAccountId) {
        String sql = "SELECT * FROM transactions WHERE receiver_account_id = ?";
        return jdbcTemplate.query(sql, new TransactionRowMapper(), receiverAccountId);
    }

    @Override
    public Long createTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (sender_account_id, receiver_account_id, amount, timestamp) VALUES (?, ?, ?, ?) RETURNING id";
        return jdbcTemplate.queryForObject(sql, Long.class, transaction.getSenderAccountId(), transaction.getReceiverAccountId(), transaction.getAmount(), transaction.getTimestamp());
    }

    @Override
    public boolean updateTransaction(Transaction transaction) {
        String sql = "UPDATE transactions SET sender_account_id = ?, receiver_account_id = ?, amount = ?, timestamp = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, transaction.getSenderAccountId(), transaction.getReceiverAccountId(), transaction.getAmount(), transaction.getTimestamp(), transaction.getId());
        return rowsAffected > 0;
    }

    @Override
    public boolean deleteTransaction(Long id) {
        String sql = "DELETE FROM transactions WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

}
