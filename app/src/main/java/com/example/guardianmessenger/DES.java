package com.example.guardianmessenger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class DES implements EncryptionAlgorithm {

    Cipher cipher;
    DES() {
        try {
            // cipher initialization
            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        } catch (Exception e) {
            System.out.println("Cipher initialization error.");
        }

    }
    @Override
    public byte[] encrypt(SecretKey key, String message) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] byteMsg = message.getBytes();
            byte[] encryptedMsg = cipher.doFinal(byteMsg);
            System.out.println("Message: " + new String(byteMsg));
            System.out.println("Encrypted Message: " + new String(encryptedMsg));
            return encryptedMsg;
        } catch (Exception e) {
           System.out.println("Encryption error.");
        }
        return null;
    }

    @Override
    public byte[] decrypt(SecretKey key, byte[] encryptedMsg) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedMsg = cipher.doFinal(encryptedMsg);
            System.out.println("Encrypted Message: " + new String(encryptedMsg));
            System.out.println("Decrypted Message: " + new String(decryptedMsg));
            return decryptedMsg;
        } catch (Exception e) {
            System.out.println("Decryption error.");
        }
        return null;
    }
}
