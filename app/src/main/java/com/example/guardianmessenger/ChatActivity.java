package com.example.guardianmessenger;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;

import java.sql.Time;
import java.util.Arrays;

public class ChatActivity extends AppCompatActivity {
    UserModel reciepientModel;
    ChatRecyclerAdapter adapter;
    EditText messageInput;
    ImageButton sendButton, backButton;
    TextView reciepientName;
    RecyclerView recyclerView;
    String chatId;
    ChatModel chatModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);

        reciepientModel = AndroidUtils.getUserModel(getIntent());
        chatId = FirebaseUtils.getChatId(FirebaseUtils.currentUserId(),reciepientModel.getUserId());

        messageInput = findViewById(R.id.inputMessage);
        sendButton = findViewById(R.id.sendButton);
        reciepientName = findViewById(R.id.contactName);
        backButton = findViewById(R.id.backButton);
        recyclerView = findViewById(R.id.chat_recycler_view);

        reciepientName.setText(reciepientModel.getName());
        backButton.setOnClickListener((v -> {
            startActivity(new Intent(ChatActivity.this,MessageActivity.class));

        }));
        getChats();

        sendButton.setOnClickListener((v -> {
            String msg = messageInput.getText().toString().trim();
            if (msg.isEmpty()){
                return;
            }
            sendMessage(msg);
        }));

        setupChatRecyclerView();

    }

    void setupChatRecyclerView(){
        Query query = FirebaseUtils.getChatMessageRef(chatId).orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<ChatMessageModel> options = new FirestoreRecyclerOptions.Builder<ChatMessageModel>().setQuery(query,ChatMessageModel.class).build();
        adapter = new ChatRecyclerAdapter(options,getApplicationContext());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }

    void getChats(){
        FirebaseUtils.getChatReference(chatId).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                chatModel = task.getResult().toObject(ChatModel.class);
                if(chatModel==null){
                    chatModel = new ChatModel(chatId, Arrays.asList(FirebaseUtils.currentUserId(),reciepientModel.getUserId()), Timestamp.now(),"");
                    FirebaseUtils.getChatReference(chatId).set(chatModel);
                }
            }

        });
    }
    void sendMessage(String message){
        chatModel.setLastMsg(message);
        chatModel.setLastMsgTime(Timestamp.now());
        FirebaseUtils.getChatReference(chatId).set(chatModel);
        ChatMessageModel msgModel = new ChatMessageModel(message, FirebaseUtils.currentUserId(), Timestamp.now());
        FirebaseUtils.getChatMessageRef(chatId).add(msgModel).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()){
                    messageInput.setText("");
                }
            }
        });
    }

}