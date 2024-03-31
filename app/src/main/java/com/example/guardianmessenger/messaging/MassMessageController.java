package com.example.guardianmessenger.messaging;



import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class MassMessageController {

    public static final DocumentReference messageDocument = FirebaseFirestore.getInstance().collection("mass_message").
            document("current_message");

    public static void setMassMessage(String sentMsg) {
        editMassMessage(sentMsg);
    }

    public static void removeMassMessage() {
        editMassMessage("");
    }

    private static void editMassMessage(String mass_msg) {
        Map<String, Object> msgMap = new HashMap<>();
        msgMap.put("msg", mass_msg);
        messageDocument.set(msgMap);
    }
}
