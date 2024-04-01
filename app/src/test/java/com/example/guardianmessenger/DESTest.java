package com.example.guardianmessenger;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.guardianmessenger.encryption.DES;
import com.example.guardianmessenger.kdc.KDCController;

import javax.crypto.SecretKey;

public class DESTest {

    DES des;
    KDCController kdc;
    @Before
    public void testInitialization() {
        des = new DES();
        kdc = new KDCController();
    }
    @Test
    public void encryptDecryptMessageTest(){
        SecretKey key = kdc.createKey();
        System.out.println(key);
        String message = "hello";
        byte[] encrypted = des.encrypt(key, message);
        String decrypted = des.decrypt(key, encrypted);
        assertEquals(message, decrypted);
    }
}
