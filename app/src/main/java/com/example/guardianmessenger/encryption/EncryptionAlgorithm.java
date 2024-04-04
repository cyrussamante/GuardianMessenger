package com.example.guardianmessenger.encryption;

import javax.crypto.SecretKey;

/**
 * Interface for Encryption Algorithms
 */

public interface EncryptionAlgorithm {
    
    /**
     * Encrypts a string given a secret key
     * @param key: The key to allow for encryption
     * @param string: The text to be encrypted
     * @return the encrypted bytes of the text
     */
    byte[] encrypt(SecretKey key, String string);

    /**
     * Decrypts an encrypted message of bytes given a secret key
     * @param key: The key to allow for decryption
     * @param encryptedMsg: The encrypted message
     * @return the decrypted text
     */
    String decrypt(SecretKey key, byte[] encryptedMsg);
}
