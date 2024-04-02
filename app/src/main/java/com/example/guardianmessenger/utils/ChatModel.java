package com.example.guardianmessenger.utils;

import com.example.guardianmessenger.kdc.KDCController;
import com.google.firebase.Timestamp;

import java.util.List;

import javax.crypto.SecretKey;

public class ChatModel {
    String chatId;
    List<String> userIds;
    Timestamp lastMsgTime;
    String lastMsg;
    private SecretKey key;
    KDCController kdc;

    public ChatModel() {
    }

    public ChatModel(String chatId, List<String> userIds, Timestamp lastMsgTime, String lastMsg) {
        this.chatId = chatId;
        this.userIds = userIds;
        this.lastMsgTime = lastMsgTime;
        this.lastMsg = lastMsg;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public Timestamp getLastMsgTime() {
        return lastMsgTime;
    }

    public void setLastMsgTime(Timestamp lastMsgTime) {
        this.lastMsgTime = lastMsgTime;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public void setChatKey() { this.key = kdc.createKey();}
}
