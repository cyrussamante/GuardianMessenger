package com.example.guardianmessenger.utils;
import android.content.Intent;


public class AndroidUtils {
    public static void passUserModel(Intent intent, UserModel model){
        intent.putExtra("username", model.getName());
        intent.putExtra("phone",model.getPhoneNumber());
        intent.putExtra("userId",model.getUserId());
    }

    public static UserModel getUserModel(Intent intent){
        UserModel userModel = new UserModel();
        userModel.setName(intent.getStringExtra("username"));
        userModel.setPhoneNumber(intent.getStringExtra("phone"));
        userModel.setUserId(intent.getStringExtra("userId"));
        return userModel;

    }
}
