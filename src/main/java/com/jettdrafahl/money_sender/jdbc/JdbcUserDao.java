package com.jettdrafahl.money_sender.jdbc;

import com.jettdrafahl.money_sender.dao.UserDao;
import com.jettdrafahl.money_sender.model.User;
import com.jettdrafahl.money_sender.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcUserDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getUserById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), username);
    }

    @Override
    public Long createUser(User user) {
        String sql = "INSERT INTO users (username, password_hash, email) VALUES (?, ?, ?) RETURNING id";
        return jdbcTemplate.queryForObject(sql, Long.class, user.getUsername(), user.getPasswordHash(), user.getEmail());
    }

    @Override
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET username = ?, password_hash = ?, email = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, user.getUsername(), user.getPasswordHash(), user.getEmail(), user.getId());
        return rowsAffected > 0;
    }

    @Override
    public boolean deleteUser(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }
}
