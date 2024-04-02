package com.example.guardianmessenger.kdc;

import com.example.guardianmessenger.utils.UserModel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.crypto.SecretKey;

public class KeyDB {
    private Set<SecretKey> keys;
    private Map<UserModel, SecretKey> keyAssignments;
    private Map<UserModel, SecretKey> sessionKeys;

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

    public void updateSessionKey(UserModel employee, SecretKey sessionKey) {
        sessionKeys.put(employee, sessionKey);
    }

    public SecretKey getSessionKey(UserModel employee) {
        return sessionKeys.getOrDefault(employee, null);
    }

    public Set<UserModel> getEmployees() {
        return keyAssignments.keySet();
    }

    public boolean canStoreKey(SecretKey key) {
        return keys.add(key);
    }
}