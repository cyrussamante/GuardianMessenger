package com.example.guardianmessenger.encryption;

import javax.crypto.SecretKey;

public class EncryptionController {

    private final EncryptionAlgorithm ea;

    public EncryptionController() {
        ea = new DES();
    }

    public EncryptionController(EncryptionAlgorithm ea) {
        this.ea = ea;
    }

    private byte[] encryptData(SecretKey key, String data) {
        return ea.encrypt(key, data);
    }

    private String decryptData(SecretKey key, byte[] data) {
        return ea.decrypt(key, data);
    }
}
