package com.example.guardianmessenger.messaging;



import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * Feature: Controller for Sending Mass Messages
 */
public class MassMessageController {

    // firebase document containing mass messsage
    public static final DocumentReference messageDocument = FirebaseFirestore.getInstance().collection("mass_message").
            document("current_message");

    // sets desired mass message
    public static void setMassMessage(String sentMsg) {
        editMassMessage(sentMsg);
    }

    // clears mass message
    public static void removeMassMessage() {
        editMassMessage("");
    }

    // uploads mass message to firebase
    private static void editMassMessage(String mass_msg) {
        Map<String, Object> msgMap = new HashMap<>();
        msgMap.put("msg", mass_msg);
        messageDocument.set(msgMap);
    }
}
