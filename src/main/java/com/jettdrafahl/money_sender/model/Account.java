package com.jettdrafahl.money_sender.model;

public class Account {
    private Long id;
    private Long userId;
    private Double balance;
    private String accountType;

    // Constructor
    public Account(Long id, Long userId, Double balance, String accountType) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
        this.accountType = accountType;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Double getBalance() {
        return balance;
    }

    public String getAccountType() {
        return accountType;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    // toString method
    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userId=" + userId +
                ", balance=" + balance +
                ", accountType='" + accountType + '\'' +
                '}';
    }
}
