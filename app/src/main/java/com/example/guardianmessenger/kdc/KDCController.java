package com.example.guardianmessenger.kdc;

import com.example.guardianmessenger.accounts.Employee;
import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;
import java.util.Set;

public class KDCController {
    private KeyDB keyDB;

    public KDCController() {
        this.keyDB = new KeyDB();
    }
    public void registerEmployee(Employee employee) {
        if (getKey(employee) == null) {
            SecretKey key = createKey();
            keyDB.updateUserKey(employee, key);
        }
    }

    public SecretKey getKey(Employee employee) {
        return keyDB.getUserKey(employee);
    }

    public SecretKey getSessionKey(Employee employee, SecretKey userKey) {
        if (userKey != getKey(employee)) {
            return null;
        }
        return keyDB.getSessionKey(employee);
    }

    public void refreshAllKeys() {
        //TODO: refresh session keys
        Set<Employee> employees = keyDB.getEmployees();
        for (Employee employee : employees) {
            keyDB.updateUserKey(employee, createKey());
        }
    }

    public SecretKey createKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("DES");
            keyGen.init(56);
            SecretKey key = keyGen.generateKey();
            return key;
        } catch (Exception e) {return null;}
    }

    public void establishSessionKey(Set<Employee> participants) {
        for (Employee participant : participants) {
            //check if they're authenticated
        }
        SecretKey sessionKey = createKey();
        for (Employee participant : participants) {
            keyDB.updateSessionKey(participant, sessionKey);
        }
    }

}