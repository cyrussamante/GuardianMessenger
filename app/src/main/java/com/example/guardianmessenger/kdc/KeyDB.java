package com.example.guardianmessenger.kdc;

import com.example.guardianmessenger.utils.ChatModel;
import com.example.guardianmessenger.utils.EmployeeModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.crypto.SecretKey;

/**
 * Feature: Database for KDC
 */
public class KeyDB {
    private Map<EmployeeModel, SecretKey> keyAssignments;
    private Map<ChatModel, SecretKey> sessionKeys;

    /**
     * Constructor for keys database.
     */
    public KeyDB() {
        this.keyAssignments = new HashMap<>();
        this.sessionKeys = new HashMap<>();
    }

    /**
     * get user key
     * @param employee: The employee whose key will be retrieved.
     * @return key
     */
    public SecretKey getUserKey(EmployeeModel employee) {
        return keyAssignments.getOrDefault(employee, null);
    }

    /**
     * updates user key
     * @param employee: The employee whose key will be updated.
     * @param newKey: The new key to be assigned.
     */
    public void updateUserKey(EmployeeModel employee, SecretKey newKey) {
        keyAssignments.put(employee, newKey);
    }

    /**
     * updates session key
     * @param session: The employee whose key will be updated.
     * @param sessionKey: The new key to be assigned.
     * @return key
     */
    public void updateSessionKey(ChatModel session, SecretKey sessionKey) {
        sessionKeys.put(session, sessionKey);
    }

    /**
     * get session key
     * @param session: The chat session whose key will be retrieved from the database
     * @return key
     */
    public SecretKey getSessionKey(ChatModel session) {
        return sessionKeys.getOrDefault(session, null);
    }

    /**
     * gets a list of employees
     * @return set of employees
     */
    public Set<EmployeeModel> getEmployees() {
        return keyAssignments.keySet();
    }

    /**
     * gets a list of chat sessions
     * @return set of chat sessions
     */
    public Set<ChatModel> getSessions() { return sessionKeys.keySet(); }

}