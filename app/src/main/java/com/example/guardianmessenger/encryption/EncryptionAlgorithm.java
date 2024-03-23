package com.example.guardianmessenger.encryption;

import javax.crypto.SecretKey;

public interface EncryptionAlgorithm {
    byte[] encrypt(SecretKey key, String string);
    String decrypt(SecretKey key, byte[] encryptedMsg);
}
