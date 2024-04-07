package com.example.guardianmessenger.utils;
import android.content.Intent;

import com.example.guardianmessenger.encryption.DES;
import com.example.guardianmessenger.kdc.KDCController;
import com.google.firebase.firestore.Blob;

import javax.crypto.SecretKey;

/**
 * AndroidUtils Class. Includes helpers methods used throughtout the application
 */
public class AndroidUtils {
    /**
     * Pass a user Model as flags into intent body
     * @param intent pass the intent which includes flags for the user data
     * @param model pass the usermodel from which name, phonenumber and userId can be fetched
     */
    public static void passUserModel(Intent intent, UserModel model){
        intent.putExtra("username", model.getName());
        intent.putExtra("phone",model.getPhoneNumber());
        intent.putExtra("userId",model.getUserId());
    }

    /**
     *
     * @param intent pass intent which has flags for name, phoneNumber and UserId
     * @return UserModel with name, phoneNumber and UserID
     */
    public static UserModel getUserModel(Intent intent){
        UserModel userModel = new UserModel();
        userModel.setName(intent.getStringExtra("username"));
        userModel.setPhoneNumber(intent.getStringExtra("phone"));
        userModel.setUserId(intent.getStringExtra("userId"));
        return userModel;

    }


    /**
     * Method to get an encryption key
     * @return A SecretKey that can be used for encryption
     */
    public static SecretKey getEncryptionKey(){
        KDCController kdcController = new KDCController();
        String encryptionFormat = "aKiGuftdeoU="; //Format for creating keys
        return kdcController.stringToKey(encryptionFormat);
    }


    /**
     * Encrypts a String
     * @param message the String that needs to be encrypted
     * @return Blob that contains a bytestring with the encrypted message
     */
    public static Blob encryptMessage(String message){
        DES des = new DES();
        byte[] encryptedMsg = des.encrypt(getEncryptionKey(),message);
        return Blob.fromBytes(encryptedMsg);
    }

    /**
     * Decrypts a Blob with an encrypted bytestring to an unencrypted String
     * @param message Blob with bytestring that is the encrypted message
     * @return String which was the original message
     */
    public static String decryptMessage(Blob message){
        DES des = new DES();
        byte[] encryptedMsg = message.toBytes();
        return des.decrypt(getEncryptionKey(),encryptedMsg);
    }
}
