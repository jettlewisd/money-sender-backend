package com.jettdrafahl.money_sender.rowmapper;

import com.jettdrafahl.money_sender.model.Account;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRowMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Account(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getDouble("balance")
        );
    }
}
