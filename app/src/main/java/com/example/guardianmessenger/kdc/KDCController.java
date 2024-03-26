package com.example.guardianmessenger.kdc;

import com.example.guardianmessenger.accounts.Employee;
import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;

public class KDCController {
    private KeyDB keyDB;

    public KDCController() {
        this.keyDB = new KeyDB();
    }
    public void registerEmployee(Employee employee) {
        //TODO
        if (keyDB.getKey(employee) == null) {
            SecretKey key = createKey();
            updateKey(employee, key);
        }
    }

    public SecretKey getKey(Employee employee) {
        return keyDB.getKey(employee);
    }

    public void updateKey(Employee employee, SecretKey key) {
        keyDB.updateKey(employee, key);
    }

    public SecretKey createKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("DES");
            keyGen.init(56);
            SecretKey key = keyGen.generateKey();
            return key;
        } catch (Exception e) {return null;}
    }
}