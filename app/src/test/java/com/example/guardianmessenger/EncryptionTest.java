package com.example.guardianmessenger;

import static org.junit.Assert.assertEquals;

import com.example.guardianmessenger.encryption.DES;
import com.example.guardianmessenger.kdc.KDCController;
import com.example.guardianmessenger.utils.AndroidUtils;
import com.google.firebase.firestore.Blob;

import org.junit.Before;
import org.junit.Test;

import javax.crypto.SecretKey;

public class EncryptionTest {
    DES des;
    KDCController kdc;
    @Before
    public void testInitialization() {
        des = new DES();
        kdc = new KDCController();
    }
    @Test
    public void encryptDecryptMessageTest(){
        String msg = "hello";
        Blob encryptedBlob = AndroidUtils.encryptMessage(msg);
        String decryptedString = AndroidUtils.decryptMessage(encryptedBlob);
        assertEquals(msg, decryptedString);
    }
}
