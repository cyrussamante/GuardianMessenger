package com.example.guardianmessenger.utils;

import com.example.guardianmessenger.kdc.KDCController;
import com.google.firebase.Timestamp;

import java.util.List;

import javax.crypto.SecretKey;

/**
 * This class represents a Chat
 */
public class ChatModel {
    String chatId;
    List<String> userIds;
    Timestamp lastMsgTime;
    String lastMsg;
    private SecretKey key;
    KDCController kdc;

    /**
     * Empty Contructor for firebase
     */
    public ChatModel() {
    }

    /**
     * Contructor for chat
     * @param chatId unique chatId
     * @param userIds List of userIds of participating users
     * @param lastMsgTime Timestamp of last message
     * @param lastMsg Unencrypted string of last message
     */
    public ChatModel(String chatId, List<String> userIds, Timestamp lastMsgTime, String lastMsg) {
        this.chatId = chatId;
        this.userIds = userIds;
        this.lastMsgTime = lastMsgTime;
        this.lastMsg = lastMsg;
    }

    /**
     * Accessor for chatId
     * @return the chatId
     */
    public String getChatId() {
        return chatId;
    }

    /**
     * Setter for chatid
     * @param chatId chatId of type string
     */
    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    /**
     * Getter for userIds
     * @return a List of userids
     */
    public List<String> getUserIds() {
        return userIds;
    }

    /**
     * Setter for userIds
     * @param userIds A list of userids
     */
    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    /**
     * Getter for last message time
     * @return A timestamp of the time the last message was sent
     */
    public Timestamp getLastMsgTime() {
        return lastMsgTime;
    }

    /**
     * Setter for last message time
     * @param lastMsgTime A timestamp of the time the last message was sent
     */
    public void setLastMsgTime(Timestamp lastMsgTime) {
        this.lastMsgTime = lastMsgTime;
    }

    /**
     * Getter for last message
     * @return the message of type string
     */
    public String getLastMsg() {
        return lastMsg;
    }

    /**
     * Setter for last message
     * @param lastMsg the message of type string
     */
    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    /**
     * Setter for chatKey
     */
    public void setChatKey() { this.key = kdc.createKey();}
}
