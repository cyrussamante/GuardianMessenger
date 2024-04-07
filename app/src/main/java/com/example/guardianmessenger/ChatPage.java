package com.example.guardianmessenger;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guardianmessenger.adapter.ChatRecyclerAdapter;
import com.example.guardianmessenger.utils.AndroidUtils;
import com.example.guardianmessenger.utils.MessageModel;
import com.example.guardianmessenger.utils.ChatModel;
import com.example.guardianmessenger.utils.FirebaseUtils;
import com.example.guardianmessenger.utils.EmployeeModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;

import java.util.Arrays;

/**
 * This screen represents an individual chat activity
 */
public class ChatPage extends AppCompatActivity {
    EmployeeModel recipientModel;
    ChatRecyclerAdapter adapter;
    EditText messageInput;
    ImageButton sendButton, backButton;
    TextView recipientName;
    RecyclerView recyclerView;
    String chatId;
    ChatModel chatModel;

    /**
     * Creates the Activity, creates a new chat if one doesn't exist
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);

        recipientModel = AndroidUtils.getUserModel(getIntent());
        chatId = FirebaseUtils.getChatId(FirebaseUtils.currentUserId(), recipientModel.getUserId());

        //Getting UI elements
        messageInput = findViewById(R.id.inputMessage);
        sendButton = findViewById(R.id.sendButton);
        recipientName = findViewById(R.id.contactName);
        backButton = findViewById(R.id.backButton);
        recyclerView = findViewById(R.id.chat_recycler_view);

        recipientName.setText(recipientModel.getName());
        // back button listener
        backButton.setOnClickListener(v -> startActivity(new Intent(ChatPage.this, MessagesPage.class)));

        //Gets Chat ref
        getChats();

        // enter key listener on message input
        messageInput.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) { // looks for key
                    deliver();
                    return true;
                }
                return false;
            }
        });

        sendButton.setOnClickListener((v -> {
            deliver();
        }));

        //Display Chats
        setupChatRecyclerView();

    }

    private void deliver() {
        String msg = messageInput.getText().toString().trim();
        if (msg.isEmpty()){
            return;
        }
        sendMessage(msg);
    }

    /**
     * Sets up the recycler view
     */
    void setupChatRecyclerView(){
        Query query = FirebaseUtils.getChatMessageRef(chatId).orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<MessageModel> options = new FirestoreRecyclerOptions.Builder<MessageModel>().setQuery(query, MessageModel.class).build();
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

    /**
     * Gets chat reference if it exists, creates one if it doesn't exist
     */
    void getChats(){
        FirebaseUtils.getChatReference(chatId).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                //Getting Existing Chat
                chatModel = task.getResult().toObject(ChatModel.class);
                if(chatModel==null){
                    //Creating New Chat
                    chatModel = new ChatModel(chatId, Arrays.asList(FirebaseUtils.currentUserId(), recipientModel.getUserId()), Timestamp.now(),"");
                    FirebaseUtils.getChatReference(chatId).set(chatModel);
                }
            }

        });
    }

    /**
     * Method to encrypt and store message in the chats collection in firebase
     * @param message the message string that is to be encrypted and stored
     */
    void sendMessage(String message){
        //Setting Chat metadata
        chatModel.setLastMsg(message);
        chatModel.setLastMsgTime(Timestamp.now());
        FirebaseUtils.getChatReference(chatId).set(chatModel);

        //Encrypt Message
        Blob encryptedMsgBlob = AndroidUtils.encryptMessage(message);
        MessageModel msgModel = new MessageModel(encryptedMsgBlob, FirebaseUtils.currentUserId(), Timestamp.now());
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