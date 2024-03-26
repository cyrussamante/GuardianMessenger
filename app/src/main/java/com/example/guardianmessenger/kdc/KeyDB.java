package com.example.guardianmessenger.kdc;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import javax.crypto.SecretKey;

public class KeyDB {
    private Set<SecretKey> keys;
    private Map<Integer, SecretKey> keyAssignments;

    public KeyDB() {
        this.keys = new HashSet<>();
        this.keyAssignments = new HashMap<>();
    }

    public SecretKey getKey(int employeeID) {
        return keyAssignments.getOrDefault(employeeID, null);
    }

    public void updateKey(int employeeID, SecretKey key) {
        keyAssignments.put(employeeID, key);
    }
}