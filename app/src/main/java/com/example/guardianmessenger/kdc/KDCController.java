package com.example.guardianmessenger.kdc;

import com.example.guardianmessenger.utils.UserModel;
import com.example.guardianmessenger.utils.ChatModel;

import java.util.Base64;
import java.util.Set;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class KDCController {
    private KeyDB keyDB;

    public KDCController() {
        this.keyDB = new KeyDB();
    }

    public void registerEmployee(UserModel employee) {
        if (getKey(employee) == null) {
            SecretKey key = createKey();
            keyDB.updateUserKey(employee, key);
        }
    }

    public SecretKey getKey(UserModel employee) {
        return keyDB.getUserKey(employee);
    }

    public SecretKey getSessionKey(ChatModel session, SecretKey userKey) {
        return keyDB.getSessionKey(session);
    }

    public void refreshAllKeys() {
        Set<UserModel> employees = keyDB.getEmployees();
        for (UserModel employee : employees) {
            keyDB.updateUserKey(employee, createKey());
        }
        Set<ChatModel> sessions = keyDB.getSessions();
        for (ChatModel session : sessions) {
            keyDB.updateSessionKey(session, createKey());
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

    public String keyToString(SecretKey key) {
        byte[] rawData = key.getEncoded();
        String encodedKey = null;
        encodedKey = Base64.getEncoder().encodeToString(rawData);
        return encodedKey;
    }

    public SecretKey stringToKey(String encodedKey) {
        byte[] decodedKey = new byte[0];
        decodedKey = Base64.getDecoder().decode(encodedKey);
        SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES");
        return key;
    }

}