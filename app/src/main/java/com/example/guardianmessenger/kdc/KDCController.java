package com.example.guardianmessenger.kdc;

import com.example.guardianmessenger.utils.ChatModel;
import com.example.guardianmessenger.utils.UserModel;

import java.util.Base64;
import java.util.Set;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Feature: Controller for KDC
 */
public class KDCController {
    private KeyDB keyDB;

    /**
     * Constructor for KDC Controller.
     */
    public KDCController() {
        this.keyDB = new KeyDB();
    }

    /**
     * register a new employee into the database and generate their key
     * @param employee: The employee to be registered
     */
    public void registerEmployee(UserModel employee) {
        if (getKey(employee) == null) {
            SecretKey key = createKey();
            keyDB.updateUserKey(employee, key);
        }
    }

    /**
     * get user key
     * @param employee: The employee whose key will be retrieved from the database
     * @return key
     */
    public SecretKey getKey(UserModel employee) {
        return keyDB.getUserKey(employee);
    }

    /**
     * get session key
     * @param session: The chat session whose key will be retrieved from the database
     * @return key
     */
    public SecretKey getSessionKey(ChatModel session) {
        return keyDB.getSessionKey(session);
    }

    /**
     * update all keys in the database
     */
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

    /**
     * generate new key
     * @return key
     */
    public SecretKey createKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("DES");
            keyGen.init(56);
            SecretKey key = keyGen.generateKey();
            return key;
        } catch (Exception e) {return null;}
    }

    /**
     * convert key to string
     * @param key: The key to be converted to a string
     * @return the encoded key string
     */
    public String keyToString(SecretKey key) {
        byte[] rawData = key.getEncoded();
        String encodedKey = null;
        encodedKey = Base64.getEncoder().encodeToString(rawData);
        return encodedKey;
    }

    /**
     * convert string to key
     * @param encodedKey: The key to be converted back to the original key
     * @return the original key
     */
    public SecretKey stringToKey(String encodedKey) {
        byte[] decodedKey = new byte[0];
        decodedKey = Base64.getDecoder().decode(encodedKey);
        SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES");
        return key;
    }

}