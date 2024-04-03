package com.example.guardianmessenger.kdc;

import com.example.guardianmessenger.utils.UserModel;
import com.example.guardianmessenger.utils.ChatModel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.crypto.SecretKey;

public class KeyDB {
    private Set<SecretKey> keys;
    private Map<UserModel, SecretKey> keyAssignments;
    private Map<ChatModel, SecretKey> sessionKeys;

    public KeyDB() {
        this.keys = new HashSet<>();
        this.keyAssignments = new HashMap<>();
        this.sessionKeys = new HashMap<>();
    }

    public SecretKey getUserKey(UserModel employee) {
        return keyAssignments.getOrDefault(employee, null);
    }

    public void updateUserKey(UserModel employee, SecretKey newKey) {
        keyAssignments.put(employee, newKey);
    }

    public void updateSessionKey(ChatModel session, SecretKey sessionKey) {
        sessionKeys.put(session, sessionKey);
    }

    public SecretKey getSessionKey(ChatModel session) {
        return sessionKeys.getOrDefault(session, null);
    }

    public Set<UserModel> getEmployees() {
        return keyAssignments.keySet();
    }

    public Set<ChatModel> getSessions() { return sessionKeys.keySet(); }

    public boolean canStoreKey(SecretKey key) {
        return keys.add(key);
    }
}