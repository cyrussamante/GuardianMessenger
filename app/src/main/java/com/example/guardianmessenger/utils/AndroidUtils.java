package com.example.guardianmessenger.utils;
import android.content.Intent;

import com.example.guardianmessenger.encryption.DES;
import com.example.guardianmessenger.kdc.KDCController;
import com.google.firebase.firestore.Blob;

import javax.crypto.SecretKey;


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

    //Gets secret key for chat from KDCController
    public static SecretKey getEncryptionKey(){
        KDCController kdcController = new KDCController();
        String encryptionFormat = "aKiGuftdeoU="; //Format for creating keys
        return kdcController.stringToKey(encryptionFormat);
    }

    public static SecretKey getEncryptionKey(String chatId){
        //TODO: Add firebase stuff
        KDCController kdcController = new KDCController();
        String encryptionFormat = "aKiGuftdeoU="; //Format for creating keys
        return kdcController.stringToKey(encryptionFormat);
    }

    //Returns a blob with a bytestring
    public static Blob encryptMessage(String message){
        DES des = new DES();
        byte[] encryptedMsg = des.encrypt(getEncryptionKey(),message);
        return Blob.fromBytes(encryptedMsg);
    }

    //Returns Original Message
    public static String decryptMessage(Blob message){
        DES des = new DES();
        byte[] encryptedMsg = message.toBytes();
        return des.decrypt(getEncryptionKey(),encryptedMsg);
    }

    //TODO: Fix methods here
    //IMPORTANT: Use these two methods, even though chatID isn't needed, just pass it, I'll add shit later
    public static Blob encryptMessage(String message, String chatId){
        //TODO: getEncryptionKEY() needs an arg
        DES des = new DES();
        byte[] encryptedMsg = des.encrypt(getEncryptionKey(),message);
        return Blob.fromBytes(encryptedMsg);
    }

    //Returns Original Message
    public static String decryptMessage(Blob message, String chatId){
        //TODO: getEncryptionKEY() needs an arg
        DES des = new DES();
        byte[] encryptedMsg = message.toBytes();
        return des.decrypt(getEncryptionKey(),encryptedMsg);
    }
}
