package com.example.guardianmessenger.encryption;

import javax.crypto.SecretKey;

/**
 * Abstraction for Encryption
 */
public class EncryptionController {

    private final EncryptionAlgorithm ea;

    /**
     * Constructor for Encryption Controller. Default algorithm is DES.
     */
    public EncryptionController() {
        ea = new DES();
    }

    /**
     * Constructor for Encryption Controller.
     * @param ea: Desired encryption algorithm
     */
    public EncryptionController(EncryptionAlgorithm ea) {
        this.ea = ea;
    }

    /**
     * Encrypts a string given a secret key
     * @param key: The key to allow for encryption
     * @param data: The data to be encrypted
     * @return the encrypted bytes of the text
     */
    private byte[] encryptData(SecretKey key, String data) {
        return ea.encrypt(key, data);
    }

    /**
     * Decrypts an encrypted message of bytes given a secret key
     * @param key: The key to allow for decryption
     * @param data: The encrypted message
     * @return the decrypted text
     */
    private String decryptData(SecretKey key, byte[] data) {
        return ea.decrypt(key, data);
    }
}
