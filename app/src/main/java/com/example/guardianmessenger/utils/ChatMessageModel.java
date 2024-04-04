package com.example.guardianmessenger.utils;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Blob;

public class ChatMessageModel {
    private String senderId;
    private Blob message;
    private Timestamp timestamp;

    public ChatMessageModel() {
    }

    public ChatMessageModel(Blob message, String senderId, Timestamp timestamp) {
        this.message = message;
        this.senderId = senderId;
        this.timestamp = timestamp;
    }

    public Blob getMessage() {
        return message;
    }

    public void setMessage(Blob message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
