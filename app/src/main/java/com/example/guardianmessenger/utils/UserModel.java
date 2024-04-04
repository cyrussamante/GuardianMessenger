package com.example.guardianmessenger.utils;

import android.util.Log;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.crypto.SecretKey;

public class UserModel {
    private String phoneNumber, name, email, userId, position, department, fcmToken;

    private List<String> outreachApprovals = new ArrayList<>();
    private Timestamp createdTimestamp;
    private int age, salary;

    private SecretKey secretKey;

    // Default constructor required for calls to DataSnapshot.getValue(UserModel.class)
    public UserModel() {
    }

    public UserModel(Timestamp createdTimestamp, String userId, String email) {
        this.createdTimestamp = createdTimestamp;
        this.userId = userId;
        this.email = email;
    }

    /***
     * Get the list of employees that have been approved for outreach
     * @return List of employees that have been approved for outreach
     */
    public List<String> getOutreachApprovals() {
        return outreachApprovals;
    }

    /***
     * Update the list of employees that have been approved for outreach
     * @param employee to add to outreach
     */
    public void addOutreach(String employee) {
        if (this.outreachApprovals.contains(employee)) {
            Log.e("Outreach", "Outreach already exists");
            return;
        }
        Log.e("Outreach", "Adding outreach approval for " + employee + " to " + this.userId);
        this.outreachApprovals.add(employee);
    }

    /***
     * Remove an employee from the list of employees that have been approved for outreach
     * @param employee to remove from outreach
     */
    public void removeOutreach(String employee) {
        this.outreachApprovals.remove(employee);
    }
    /***
     * Get the phone number of the user
     * @return phone number of the user
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /***
     * Set the phone number of the user
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /***
     * Get the secret key of the user
     * @return secret key of the user
     */
    public SecretKey getSecretKey() {
        return secretKey;
    }

    /***
     * Set the secret key of the user
     * @param secretKey
     */
    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }
    /***
     * Get the name of the user
     * @return name of the user
     */
    public String getName() {
        return name;
    }
    /***
     * Set the name of the user
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /***
     * Get the email of the user
     * @return email of the user
     */
    public String getEmail() {
        return email;
    }
    /***
     * Set the email of the user
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /***
     * Get the created timestamp of the user
     * @return
     */
    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    /***
     * Set the created timestamp of the user
     * @param createdTimestamp
     */
    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    /***
     * Get the user id
     * @return user id
     */
    public String getUserId() {
        return userId;
    }

    /***
     * Set the user id
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /***
     * Get the FCM token
     * @return FCM token
     */
    public String getFcmToken() {
        return fcmToken;
    }

    /***
     * Set the FCM token
     * @param fcmToken
     */
    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    /***
     * Get the age of the user
     * @return age of the user
     */
    public int getAge() {
        return age;
    }

    /***
     * Set the age of the user
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /***
     * Get the position of the user
     * @return position of the user
     */
    public String getPosition() {
        return position;
    }

    /***
     * Set the position of the user
     * @param position
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /***
     * Get the department of the user
     * @return department of the user
     */
    public String getDepartment() {
        return department;
    }

    /***
     * Set the department of the user
     * @param department
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /***
     * Get the salary of the user
     * @return salary of the user
     */
    public int getSalary() {
        return salary;
    }

    /***
     * Set the salary of the user
     * @param salary
     */
    public void setSalary(int salary) {
        this.salary = salary;
    }
}
