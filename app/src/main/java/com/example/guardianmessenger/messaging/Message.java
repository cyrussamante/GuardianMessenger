package com.example.guardianmessenger.messaging;

import java.time.LocalDateTime;
import java.util.HashSet;

public class Message {
    private final String message;
    private final String senderID;
    private final LocalDateTime datetime;
    private HashSet<String> receivers;
    private MessageStatus status = MessageStatus.PENDING;

    public Message(String message, String senderID, HashSet<String> receivers, LocalDateTime datetime) {
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

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }
    public MessageStatus getStatus() {
        return status;
    }
}
