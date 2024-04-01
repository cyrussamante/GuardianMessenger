package com.example.guardianmessenger.kdc;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import javax.crypto.SecretKey;
import com.example.guardianmessenger.accounts.Employee;

public class KeyDB {
    private Set<SecretKey> keys;
    private Map<Employee, SecretKey> keyAssignments;
    private Map<Employee, SecretKey> sessionKeys;

    public KeyDB() {
        this.keys = new HashSet<>();
        this.keyAssignments = new HashMap<>();
        this.sessionKeys = new HashMap<>();
    }

    public SecretKey getUserKey(Employee employee) {
        return keyAssignments.getOrDefault(employee, null);
    }

    public void updateUserKey(Employee employee, SecretKey newKey) {
        keyAssignments.put(employee, newKey);
    }

    public void updateSessionKey(Employee employee, SecretKey sessionKey) {
        sessionKeys.put(employee, sessionKey);
    }

    public SecretKey getSessionKey(Employee employee) {
        return sessionKeys.getOrDefault(employee, null);
    }

    public Set<Employee> getEmployees() {
        return keyAssignments.keySet();
    }

    public boolean canStoreKey(SecretKey key) {
        return keys.add(key);
    }
}