package com.example.guardianmessenger;

import javax.crypto.SecretKey;

public interface EncryptionAlgorithm {
    byte[] encrypt(SecretKey key, String message);
    byte[] decrypt(SecretKey key, byte[] encryptedMsg);
}
