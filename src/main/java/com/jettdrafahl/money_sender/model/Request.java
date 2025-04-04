package com.jettdrafahl.money_sender.model;

import java.sql.Timestamp;

public class Request {

    Long requestID;
    Long requestSenderId;
    Long requestReceiverId;
    Double amount;
    Timestamp timestamp;


    //Constructor
    public Request(Long requestID, Long requestSenderId, Long requestReceiverId, Double amount, Timestamp timestamp) {
        this.requestID = requestID;
        this.requestSenderId = requestSenderId;
        this.requestReceiverId = requestReceiverId;
        this.amount = amount;
        this.timestamp = timestamp;
    }


    // Getters and setters
    public Long getRequestID() {
        return requestID;
    }
    public Long getRequestSenderId() {
        return requestSenderId;
    }
    public Long getRequestReceiverId() {
        return requestReceiverId;
    }
    public Double getAmount() {
        return amount;
    }
    public Timestamp getTimestamp() {
        return timestamp;
    }
    public void setRequestID(Long requestID) {
        this.requestID = requestID;
    }
    public void setRequestSenderId(Long requestSenderId) {
        this.requestSenderId = requestSenderId;
    }
    public void setRequestReceiverId(Long requestReceiverId) {
        this.requestReceiverId = requestReceiverId;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestID=" + requestID +
                ", requestSenderId=" + requestSenderId +
                ", requestReceiverId=" + requestReceiverId +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }
}
