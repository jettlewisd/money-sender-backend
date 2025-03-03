package com.jettdrafahl.money_sender.service;

import com.jettdrafahl.money_sender.dao.UserDao;
import com.jettdrafahl.money_sender.exception.ResourceNotFoundException;
import com.jettdrafahl.money_sender.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUserById(Long id) {
        User user = userDao.getUserById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User with ID " + id + " not found.");
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User findByUsername(String username) {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException("User with username '" + username + "' not found.");
        }
        return user;
    }

    @Override
    public Long createUser(User user) {
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }
        return userDao.createUser(user);
    }

    @Override
    public boolean updateUser(User user) {
        if (userDao.getUserById(user.getId()) == null) {
            throw new ResourceNotFoundException("Cannot update. User with ID " + user.getId() + " not found.");
        }
        return userDao.updateUser(user);
    }

    @Override
    public boolean deleteUser(Long id) {
        if (userDao.getUserById(id) == null) {
            throw new ResourceNotFoundException("Cannot delete. User with ID " + id + " not found.");
        }
        return userDao.deleteUser(id);
    }
}
