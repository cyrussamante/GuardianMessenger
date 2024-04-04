package com.example.guardianmessenger.encryption;

import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

/**
 * Feature: Relates to DES Encryption / Decryption Algorithm
 */
public class DES implements EncryptionAlgorithm {

    Cipher cipher;
    public DES() {
        try {
            // cipher initialization
            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        } catch (Exception e) {
            Log.e("DES", "Cipher Initialization",e);
        }

    }
    @Override
    public byte[] encrypt(SecretKey key, String string) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] byteMsg = string.getBytes();
            return cipher.doFinal(byteMsg); // string encrypted
        } catch (Exception e) {
            Log.e("DES", "Encryption",e);
        }
        return null;
    }

    @Override
    public String decrypt(SecretKey key, byte[] encryptedMsg) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(encryptedMsg)); // string decrypted
        } catch (Exception e) {
            Log.e("DES", "Decryption",e);
        }
        return null;
    }
}
