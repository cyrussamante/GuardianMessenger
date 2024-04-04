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

    public UserModel() {
    }

    public UserModel(Timestamp createdTimestamp, String userId, String email) {
        this.createdTimestamp = createdTimestamp;
        this.userId = userId;
        this.email = email;
    }

    public List<String> getOutreachApprovals() {
        return outreachApprovals;
    }

    public void addOutreach(String employee) {
        if (this.outreachApprovals.contains(employee)) {
            Log.e("Outreach", "Outreach already exists");
            return;
        }
        Log.e("Outreach", "Adding outreach approval for " + employee + " to " + this.userId);
        this.outreachApprovals.add(employee);
    }

    public void removeOutreach(String employee) {
        this.outreachApprovals.remove(employee);
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
