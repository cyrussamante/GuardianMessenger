package com.example.guardianmessenger.messaging;



import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * Feature: Controller for Sending Mass Messages
 */
public class MassMessageController {

    /**
     * Firebase document containing mass message.
     */
    public static final DocumentReference messageDocument = FirebaseFirestore.getInstance().collection("mass_message").
            document("current_message");

    /**
     * Sets the desired mass message.
     * @param sentMsg: Mass message to be sent.
     */
    public static void setMassMessage(String sentMsg) {
        editMassMessage(sentMsg);
    }

    /**
     * Removes the current mass message.
     */
    public static void removeMassMessage() {
        editMassMessage("");
    }

    /**
     * Uploads the mass message to the server.
     * @param mass_msg: Mass message to be sent.
     */
    private static void editMassMessage(String mass_msg) {
        Map<String, Object> msgMap = new HashMap<>();
        msgMap.put("msg", mass_msg);
        messageDocument.set(msgMap);
    }
}
