package com.example.guardianmessenger.utils;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Blob;

/**
 * This class represents an individual message
 */
public class MessageModel {
    private String senderId;
    private Blob message;
    private Timestamp timestamp;

    /**
     * Empty Contructor for Firebase
     */
    public MessageModel() {
    }

    /**
     * Contructor creates the Message Model with given message, senderId and timestamp
     * @param message Blob that has the encrypted message
     * @param senderId userId of the sender
     * @param timestamp time stamp that the message was sent
     */
    public MessageModel(Blob message, String senderId, Timestamp timestamp) {
        this.message = message;
        this.senderId = senderId;
        this.timestamp = timestamp;
    }

    /**
     * Accessor For Blob that contains message
     * @return Blob that contains message
     */
    public Blob getMessage() {
        return message;
    }

    /**
     * Setter for Message
     * @param message Blob that contains the message
     */
    public void setMessage(Blob message) {
        this.message = message;
    }

    /**
     * Accessor for senderId
     * @return senderId
     */
    public String getSenderId() {
        return senderId;
    }

    /**
     * Setter for senderId
     * @param senderId senderId  of type String
     */
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    /**
     * Accessor for Timestamp
     * @return the timestamp of type Firebase.Timestamp
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * Setter for Timestamp
     * @param timestamp timestamp of type Firebase.Timestamp
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
