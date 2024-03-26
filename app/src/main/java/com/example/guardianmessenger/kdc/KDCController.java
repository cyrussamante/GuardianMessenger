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
        if (keyDB.getKey(employee.getID()) == null) {
            SecretKey key = createKey();
            int employeeID = employee.getID();
            updateKey(employeeID, key);
        }
    }

    public SecretKey getKey(int employeeID) {
        return keyDB.getKey(employeeID);
    }

    public void updateKey(int employeeID, SecretKey key) {
        keyDB.updateKey(employeeID, key);
    }

    public SecretKey createKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("DES");
            keyGen.init(56);
            return keyGen.generateKey();
        } catch (Exception e) {return null;}
    }
}