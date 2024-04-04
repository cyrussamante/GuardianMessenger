package com.example.guardianmessenger;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.guardianmessenger.utils.AndroidUtils;
import com.example.guardianmessenger.utils.FirebaseUtils;
import com.example.guardianmessenger.utils.SessionController;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RequestChatLogActivity extends AppCompatActivity {

    private ImageButton backButton;
    private Button requestButton;
    private EditText nameField, startDateField, endDateField;
    private Date start, end;
    private String senderName, recipientName, senderID, recipientID, chatID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_request_chat_log);

        // button initialization
        backButton = findViewById(R.id.back_button);
        requestButton = findViewById(R.id.request_chat_logs_button);

        // fields initialization
        nameField = findViewById(R.id.nameInput);
        startDateField = findViewById(R.id.startDateInput);
        endDateField = findViewById(R.id.endDateInput);

        // back button listener
        SessionController.redirectButton(backButton, RequestChatLogActivity.this, MainActivity.class);

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readInput(); // if false fail
                getSenderName(); // task must finish before string is built
                fetchChatLogs();
                Toast.makeText(RequestChatLogActivity.this, "Chat Logs Downloaded", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean readInput() {
        // get values from fields
        recipientName = String.valueOf(nameField.getText());
        String startDate = String.valueOf(startDateField.getText());
        String endDate = String.valueOf(endDateField.getText());
        if (recipientName.isEmpty() || startDate.isEmpty() || endDate.isEmpty())
            return false;
        // convert string input date (YYYY/MM/DD) to Date objects
        String[] temp = startDate.split("/");
        if (!checkDateInputFormat(temp)) return false;
        start = new Date(Integer.parseInt(temp[0]) - 1900, Integer.parseInt(temp[1]) - 1, Integer.parseInt(temp[2]));
        temp = endDate.split("/");
        if (!checkDateInputFormat(temp)) return false;
        end = new Date(Integer.parseInt(temp[0]) - 1900, Integer.parseInt(temp[1]) - 1, Integer.parseInt(temp[2]));
        // include end date in date range
        Calendar c = Calendar.getInstance();
        c.setTime(end);
        c.add(Calendar.DATE, 1);
        end = c.getTime();

        return true;
    }

    private boolean checkDateInputFormat(String[] elements) {
        for (String s : elements) {
            try {
                Integer.parseInt(s);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    private void getSenderName() {
        Task<DocumentSnapshot> taskFindName = FirebaseUtils.getUserDetails().get();
        taskFindName.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> taskFindName) {
                if (taskFindName.isSuccessful()) {
                    senderName = taskFindName.getResult().getString("name");
                } else {
                    // fail
                }
            }
        });
    }

    private void fetchChatLogs() {
        senderID = FirebaseUtils.currentUserId();
        // find userID of recipient
        Task<QuerySnapshot> taskFindRecipient = FirebaseUtils.allUsersCollectionReference().whereEqualTo("name", recipientName).get();
        taskFindRecipient.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> taskFindRecipient) {
                if (taskFindRecipient.isSuccessful()) {
                    List<DocumentSnapshot> queryResult = taskFindRecipient.getResult().getDocuments();
                    if (queryResult.isEmpty()) return; // no name found
                    recipientID = queryResult.get(0).getString("userId");
                    if (recipientID == null) return; // no name found
                    // get chatID with both userIDs
                    chatID = FirebaseUtils.getChatId(senderID, recipientID);
                    // get chat log data
                    Task<QuerySnapshot> taskFindChatLog = FirebaseUtils.getChatMessageRef(chatID).orderBy("timestamp", Query.Direction.ASCENDING).whereGreaterThanOrEqualTo("timestamp", new Timestamp(start)).whereLessThanOrEqualTo("timestamp", new Timestamp(end)).get();
                    taskFindChatLog.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> taskFindChatLog) {
                            if (taskFindChatLog.isSuccessful()) {
                                List<DocumentSnapshot> chatlog = taskFindChatLog.getResult().getDocuments();
                                writeFile(createChatLogString(chatlog));
                            } else {
                                // fail
                            }
                        }
                    });
                } else {
                    // fail
                }
            }
        });

    }

    private String createChatLogString(List<DocumentSnapshot> chatlog) {
        //while (senderID == null);
        StringBuilder log = new StringBuilder();
        if (!chatlog.isEmpty()) {
            for (DocumentSnapshot d : chatlog) {
                String name = "";
                if (d.getString("senderId").equals(senderID))
                    name = senderName;
                else
                    name = recipientName;
                String msg = AndroidUtils.decryptMessage((Blob) d.get("message"), chatID);
                Date date = ((Timestamp) d.get("timestamp")).toDate();
                String timestamp = date.toString();
                // String timestamp = FirebaseUtils.timestampToString((Timestamp) d.get("timestamp")); // alternative date display HH:MM
                log.append(name).append(" (").append(timestamp).append("): ").append(msg).append("\n");
            }
        } else {
            log.append("No messages exist between ").append(startDateField.getText()).append(" and ").append(endDateField.getText());
        }
        return log.toString();
    }

    private void writeFile(String data) {
        try {
            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, "chatlog"); // file name
            values.put(MediaStore.MediaColumns.MIME_TYPE, "text/plain"); // file extension
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/Guardian Messenger Chat Logs/");

            Uri uri = getContentResolver().insert(MediaStore.Files.getContentUri("external"), values);
            OutputStream outputStream = getContentResolver().openOutputStream(uri);
            outputStream.write(data.getBytes());
            outputStream.close();
            //Toast.makeText(view.getContext(), "File created successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            //Toast.makeText(view.getContext(), "Fail to create file", Toast.LENGTH_SHORT).show();
        }
    }

}