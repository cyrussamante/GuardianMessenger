package com.example.guardianmessenger.chatlog;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.guardianmessenger.utils.AndroidUtils;
import com.example.guardianmessenger.utils.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

/**
 * Based on ChatLogController controller class from previous deliverables
 * Uses ChatLogDB entity class based on Firestore
 */
public class ChatLogController {

    private final Date start, end;
    private final String recipientName;
    private String senderName, senderID, recipientID, chatID;
    private final View view;
    private final ContentResolver contentResolver;

    /**
     * Constructor for ChatLogController stores required information
     * @param recipientName: Inputted name of participating employee
     * @param start: Inputted start date
     * @param end: Inputted end date
     * @param view: View that was clicked (request chat logs button); required for Toasts and file writing
     * @param contentResolver: Required for file writing
     */
    public ChatLogController(String recipientName, Date start, Date end, View view, ContentResolver contentResolver) {
        getSenderName(); // task must finish before string is built, otherwise userID is shown in chat log file
        this.recipientName = recipientName;
        this.start = start;
        this.end = end;
        this.view = view;
        this.contentResolver = contentResolver;
    }

    /**
     * Get current user's name and store in class attribute
     */
    private void getSenderName() {
        Task<DocumentSnapshot> taskFindName = FirebaseUtils.getUserDetails().get();
        taskFindName.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> taskFindName) {
                if (taskFindName.isSuccessful()) {
                    senderName = taskFindName.getResult().getString("name");
                } else {
                    Toast.makeText(view.getContext(), "A problem has occurred! Try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Gets chatID using userIDs of participating employees
     * userID of recipient employee is found using their name (taken from user input)
     */
    public void findChat() {
        // get userID of sender
        senderID = FirebaseUtils.currentUserId();
        // start task to find userID of recipient
        Task<QuerySnapshot> taskFindRecipient = FirebaseUtils.allUsersCollectionReference().whereEqualTo("name", recipientName).get();
        // wait for task to finish asynchronously
        taskFindRecipient.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> taskFindRecipient) {
                if (taskFindRecipient.isSuccessful()) {
                    // get recipient name on task success with appropriate error handling
                    List<DocumentSnapshot> queryResult = taskFindRecipient.getResult().getDocuments();
                    if (queryResult.isEmpty()) {
                        Toast.makeText(view.getContext(), "Invalid name entered", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    recipientID = queryResult.get(0).getString("userId");
                    if (recipientID == null) {
                        Toast.makeText(view.getContext(), "Invalid name entered", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // get chatID with both userIDs
                    chatID = FirebaseUtils.getChatId(senderID, recipientID);
                    // get chat log with chatID
                    getChatLogs();
                } else {
                    Toast.makeText(view.getContext(), "A problem has occurred! Try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * Gets encrypted chat log with the chatID
     */
    private void getChatLogs() {
        // start task to get chat log data
        Task<QuerySnapshot> taskFindChatLog = FirebaseUtils.getChatMessageRef(chatID).orderBy("timestamp", Query.Direction.ASCENDING).whereGreaterThanOrEqualTo("timestamp", new Timestamp(start)).whereLessThanOrEqualTo("timestamp", new Timestamp(end)).get();
        // wait for task to finish asynchronously
        taskFindChatLog.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> taskFindChatLog) {
                if (taskFindChatLog.isSuccessful()) {
                    List<DocumentSnapshot> chatLog = taskFindChatLog.getResult().getDocuments();
                    // get chat log as a string and write to file
                    writeFile(createChatLogString(chatLog));
                } else {
                    Toast.makeText(view.getContext(), "A problem has occurred! Try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Decrypts and transforms chat log into a string
     * @param chatLog: Encrypted chat log in the form of a DocumentSnapshot list
     * @return String: decrypted chat log
     */
    private String createChatLogString(List<DocumentSnapshot> chatLog) {
        // display current user's ID instead of name if name was not found in time
        if (senderName == null) senderName = senderID;

        StringBuilder log = new StringBuilder();
        if (!chatLog.isEmpty()) {
            String name = "";
            // append each message to chat log string
            for (DocumentSnapshot d : chatLog) {
                // get message sender name
                if (d.getString("senderId").equals(senderID))
                    name = senderName;
                else
                    name = recipientName;
                // get message contents
                String msg = AndroidUtils.decryptMessage((Blob) d.get("message"), chatID);
                // get date
                Date date = ((Timestamp) d.get("timestamp")).toDate();
                String timestamp = date.toString();
                // append string
                log.append(name).append(" (").append(timestamp).append("): ").append(msg).append("\n");
            }
        } else {
            log.append("No messages exist between the provided dates.");
        }
        return log.toString();
    }

    /**
     * Writes the given string to chatlog.txt in Downloads folder
     * @param data: String to write to file
     */
    private void writeFile(String data) {
        try {
            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, "chatlog"); // file name
            values.put(MediaStore.MediaColumns.MIME_TYPE, "text/plain"); // file extension
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/Guardian Messenger Chat Logs/"); // destination directory

            Uri uri = contentResolver.insert(MediaStore.Files.getContentUri("external"), values);
            OutputStream outputStream = contentResolver.openOutputStream(uri);
            outputStream.write(data.getBytes());
            outputStream.close();

            Toast.makeText(view.getContext(), "File created successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(view.getContext(), "Failed to create file", Toast.LENGTH_SHORT).show();
        }
    }
}
