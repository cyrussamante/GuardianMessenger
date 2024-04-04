package com.example.guardianmessenger.encryption;

import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

/**
 * Feature: Relates to DES Encryption / Decryption Algorithm
 */
public class DES implements EncryptionAlgorithm {

    Cipher cipher;

    /**
     * Constructor for DES. Initializes the cipher.
     */
    public DES() {
        try {
            // cipher initialization
            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        } catch (Exception e) {
            Log.e("DES", "Cipher Initialization",e);
        }

    }

    /**
     * Encrypts a string given a secret key
     * @param key: The key to allow for encryption
     * @param string: The text to be encrypted
     * @return the encrypted bytes of the text
     */
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

    /**
     * Decrypts an encrypted message of bytes given a secret key
     * @param key: The key to allow for decryption
     * @param encryptedMsg: The encrypted message
     * @return the decrypted text
     */
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
