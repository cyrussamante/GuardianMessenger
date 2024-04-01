package com.example.guardianmessenger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guardianmessenger.adapter.ChatRecyclerAdapter;
import com.example.guardianmessenger.adapter.SearchUserRecyclerAdapter;
import com.example.guardianmessenger.utils.AndroidUtils;
import com.example.guardianmessenger.utils.ChatMessageModel;
import com.example.guardianmessenger.utils.ChatModel;
import com.example.guardianmessenger.utils.FirebaseUtils;
import com.example.guardianmessenger.utils.UserModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Query;

import java.util.Arrays;
import java.util.Date;

public class RequestChatLogActivity extends AppCompatActivity {

    private ImageButton backButton;
    private Button requestButton;
    private EditText nameField, startDateField, endDateField;
    private String name, startDate, endDate;
    private Date start, end;
    private String chatID;
    private UserModel sender, recipient;
    private ChatModel chat;
    SearchUserRecyclerAdapter searchAdapter;
    ChatRecyclerAdapter chatAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_request_chat_log);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // button initialization
        backButton = findViewById(R.id.back_button);
        requestButton = findViewById(R.id.request_chat_logs_button);

        // fields initialization
        nameField = findViewById(R.id.nameInput);
        startDateField = findViewById(R.id.startDateInput);
        endDateField = findViewById(R.id.endDateInput);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RequestChatLogActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchChatLogs();
                Toast.makeText(RequestChatLogActivity.this, "Chat Log Request Success", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // WIP below

    private void fetchChatLogs() {

        // get values from fields
        name = String.valueOf(nameField.getText());
        startDate = String.valueOf(startDateField.getText());
        endDate = String.valueOf(endDateField.getText());
        String[] temp = startDate.split("/");
        start = new Date(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
        temp = endDate.split("/");
        end = new Date(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));

        Query query = FirebaseUtils.allUsersCollectionReference().whereGreaterThanOrEqualTo("name", name);
        FirestoreRecyclerOptions<UserModel> options = new FirestoreRecyclerOptions.Builder<UserModel>().setQuery(query,UserModel.class).build();
        searchAdapter = new SearchUserRecyclerAdapter(options,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(searchAdapter);
        searchAdapter.startListening();

        recipient = AndroidUtils.getUserModel(getIntent());
        chatID = FirebaseUtils.getChatId(FirebaseUtils.currentUserId(),recipient.getUserId());

        //nameField.setText(recipient.getName());

        FirebaseUtils.getChatReference(chatID).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                chat = task.getResult().toObject(ChatModel.class);
                if(chat==null){
                    chat = new ChatModel(chatID, Arrays.asList(FirebaseUtils.currentUserId(),recipient.getUserId()), Timestamp.now(),"");
                    FirebaseUtils.getChatReference(chatID).set(chat);
                }
            }

        });

        setupChatRecyclerView();
    }

    void setupChatRecyclerView(){
        Query query = FirebaseUtils.getChatMessageRef(chatID).orderBy("timestamp", Query.Direction.DESCENDING).startAt("timestamp", start).endAt("timestamp", end);
        FirestoreRecyclerOptions<ChatMessageModel> options = new FirestoreRecyclerOptions.Builder<ChatMessageModel>().setQuery(query,ChatMessageModel.class).build();
        chatAdapter = new ChatRecyclerAdapter(options,getApplicationContext());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(chatAdapter);
        chatAdapter.startListening();
        chatAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }

    void getChats(){
        FirebaseUtils.getChatReference(chatID).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                chat = task.getResult().toObject(ChatModel.class);
                if(chat==null){
                    chat = new ChatModel(chatID, Arrays.asList(FirebaseUtils.currentUserId(),recipient.getUserId()), Timestamp.now(),"");
                    FirebaseUtils.getChatReference(chatID).set(chat);
                }
            }

        });
    }
}