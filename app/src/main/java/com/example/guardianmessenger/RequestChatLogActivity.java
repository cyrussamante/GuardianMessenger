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
import java.util.Date;
import java.util.List;

public class RequestChatLogActivity extends AppCompatActivity {

    private ImageButton backButton;
    private Button requestButton;
    private EditText nameField, startDateField, endDateField;
    private Date start, end;
    private String name, senderID, recipientID, chatID;

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
                fetchChatLogs();
                Toast.makeText(RequestChatLogActivity.this, "Chat Log Request Success", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void readInput() {
        // get values from fields
        name = String.valueOf(nameField.getText());
        String startDate = String.valueOf(startDateField.getText());
        String endDate = String.valueOf(endDateField.getText());
        if (name.isEmpty() || startDate.isEmpty() || endDate.isEmpty())
            return;
        // convert string input date (YYYY/MM/DD) to Date objects
        String[] temp = startDate.split("/");
        start = new Date(Integer.parseInt(temp[0]) - 1900, Integer.parseInt(temp[1]) - 1, Integer.parseInt(temp[2]));
        temp = endDate.split("/");
        end = new Date(Integer.parseInt(temp[0]) - 1900, Integer.parseInt(temp[1]) - 1, Integer.parseInt(temp[2]));
    }

    private void fetchChatLogs() {
        readInput();
        senderID = FirebaseUtils.currentUserId();
        // find userID of recipient
        Task<QuerySnapshot> taskFindRecipient = FirebaseUtils.allUsersCollectionReference().whereEqualTo("name", name).get();
        taskFindRecipient.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> taskFindRecipient) {
                if (taskFindRecipient.isSuccessful()) {
                    List<DocumentSnapshot> queryResult = taskFindRecipient.getResult().getDocuments();
                    if (queryResult.isEmpty()) return;
                    recipientID = queryResult.get(0).getString("userId");
                    if (recipientID == null) return;
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
                            }
                        }
                    });
                }
            }
        });

    }

    private String createChatLogString(List<DocumentSnapshot> chatlog) {
        StringBuilder log = new StringBuilder();
        for (DocumentSnapshot d : chatlog) {
            // TODO? show employee name instead of ID
//            String name = "";
//            Task<DocumentSnapshot> taskFindName = FirebaseUtils.getUserDetails(d.getString("senderId")).get();
//            taskFindName.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<DocumentSnapshot> taskFindName) {
//                            if (taskFindName.isSuccessful()) {
//                                name = taskFindName.getResult().getString("name");
//                            } else {
//                                Exception e = taskFindName.getException();
//                            }
//                        }
//                    });
//
            String msg = AndroidUtils.decryptMessage((Blob) d.get("message"));
            Date date = ((Timestamp) d.get("timestamp")).toDate();
            String timestamp = date.toString();
            // String timestamp = FirebaseUtils.timestampToString((Timestamp) d.get("timestamp")); // alternative date display HH:MM
            log.append(d.getString("senderId")).append(" (").append(timestamp).append("): ").append(msg).append("\n");
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