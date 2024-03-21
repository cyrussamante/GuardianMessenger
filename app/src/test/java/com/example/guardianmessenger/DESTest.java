package com.example.guardianmessenger;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class DESTest {

    DES des;
    KeyGenerator kg;
    @Before
    public void testInitialization() {
        des = new DES();
        try {
            kg = KeyGenerator.getInstance("DES");
        } catch (Exception e) {
            System.out.println("Key generator error.");
        }
    }
    @Test
    public void encryptDecryptMessageTest(){
        SecretKey key = kg.generateKey();
        System.out.println(key);
        String message = "hello";
        byte[] encrypted = des.encrypt(key, message);
        byte[] decrypted = des.decrypt(key, encrypted);
        assertEquals(message, new String(decrypted));

    }
}
