package com.jettdrafahl.money_sender.model;

import java.sql.Timestamp;

public class Transaction {
    private Long id;
    private Long senderAccountId;
    private Long receiverAccountId;
    private Double amount;
    private Timestamp timestamp;

    // Constructor
    public Transaction(Long id, Long senderAccountId, Long receiverAccountId, Double amount, Timestamp timestamp) {
        this.id = id;
        this.senderAccountId = senderAccountId;
        this.receiverAccountId = receiverAccountId;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    // Getters
    public Long getId() { return id; }
    public Long getSenderAccountId() { return senderAccountId; }
    public Long getReceiverAccountId() { return receiverAccountId; }
    public Double getAmount() { return amount; }
    public Timestamp getTimestamp() { return timestamp; } // Updated getter

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setSenderAccountId(Long senderAccountId) { this.senderAccountId = senderAccountId; }
    public void setReceiverAccountId(Long receiverAccountId) { this.receiverAccountId = receiverAccountId; }
    public void setAmount(Double amount) { this.amount = amount; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; } // Updated setter

    // toString method
    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", senderAccountId=" + senderAccountId +
                ", receiverAccountId=" + receiverAccountId +
                ", amount=" + amount +
                ", timestamp=" + timestamp + // Updated field
                '}';
    }
}
