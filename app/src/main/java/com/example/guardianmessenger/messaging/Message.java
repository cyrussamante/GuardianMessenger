package com.example.guardianmessenger.messaging;

import com.google.firebase.Timestamp;

import java.util.HashSet;

public class Message {
    private final String message;
    private final String senderID;
    private final Timestamp datetime;
    private HashSet<String> receivers;
    private MessageStatus status = MessageStatus.PENDING;

    public Message(String message, String senderID, HashSet<String> receivers, Timestamp datetime) {
        this.message = message;
        this.senderID = senderID;
        this.receivers = receivers;
        this.datetime = datetime;
    }

    public String getMessage() {
        return message;
    }

    public String getSenderID() {
        return senderID;
    }

    public HashSet<String> getReceivers() {
        return receivers;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public void setReceivers(HashSet<String> receivers) {
        this.receivers = receivers;
    }
}
