package com.example.guardianmessenger.chatlog;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.guardianmessenger.messaging.Message;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

public class ChatLogDB {

    private ArrayList<Message> db = new ArrayList<>();

    public void updateChatLog(Message msg) {
        db.add(msg);
    }

    boolean entryExists(String senderID, HashSet<String> recipientIDs) {
        for (Message m : db) {
            if (m.getSenderID().equals(senderID) && m.getReceivers().equals(recipientIDs))
                return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    ArrayList<Message> fetchChatLog(String senderID, HashSet<String> recipientIDs, LocalDateTime start, LocalDateTime end) {
        if (!entryExists(senderID, recipientIDs)) return null;
        ArrayList<Message> fetchedChatLog = new ArrayList<>();
        for (Message m : db) {
//            if (m.getSenderID().equals(senderID) && m.getReceivers().equals(recipientIDs) && m.getDatetime().isAfter(start) && m.getDatetime().isBefore(end))
//                fetchedChatLog.add(m);
        }
        return fetchedChatLog;
    }

}
