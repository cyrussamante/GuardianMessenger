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

    public KeyDB() {
        this.keys = new HashSet<>();
        this.keyAssignments = new HashMap<>();
    }

    public SecretKey getKey(Employee employee) {
        return keyAssignments.getOrDefault(employee, null);
    }

    public void updateKey(Employee employee, SecretKey key) {
        keyAssignments.put(employee, key);
    }

    public void storeKey(SecretKey key) {
        keys.add(key);
    }
}