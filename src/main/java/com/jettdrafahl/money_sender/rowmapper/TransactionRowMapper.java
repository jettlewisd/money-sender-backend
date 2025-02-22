package com.jettdrafahl.money_sender.rowmapper;

import com.jettdrafahl.money_sender.model.Transaction;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionRowMapper implements RowMapper<Transaction> {
    @Override
    public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Transaction(
                rs.getLong("id"),
                rs.getLong("sender_account_id"),
                rs.getLong("receiver_account_id"),
                rs.getDouble("amount"),
                rs.getTimestamp("timestamp") // Updated mapping
        );
    }
}
