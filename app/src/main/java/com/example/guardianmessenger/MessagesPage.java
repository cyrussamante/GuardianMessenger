package com.example.guardianmessenger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guardianmessenger.adapter.RecentChatsRecyclerAdapter;
import com.example.guardianmessenger.utils.ChatModel;
import com.example.guardianmessenger.utils.FirebaseUtils;
import com.example.guardianmessenger.utils.SessionController;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

/**
 * Activity to show all existing messages of a logged in user
 */
public class MessagesPage extends AppCompatActivity {

    RecyclerView recyclerView;
    RecentChatsRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_message);

        // initializing views
        ImageButton backButton = findViewById(R.id.back_button);
        ImageButton addButton = findViewById(R.id.add_button);

        recyclerView = findViewById(R.id.recyler_view);
        setupRecyclerView();


        // back button listener
        SessionController.redirectButton(backButton, MessagesPage.this, HomePage.class);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessagesPage.this, CreateChatPage.class));
            }
        });

    }
    void setupRecyclerView(){

        Query query = FirebaseUtils.allChatsRefs().whereArrayContains("userIds",FirebaseUtils.currentUserId()).orderBy("lastMsgTime", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ChatModel> options = new FirestoreRecyclerOptions.Builder<ChatModel>().setQuery(query,ChatModel.class).build();

        adapter = new RecentChatsRecyclerAdapter(options,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter!=null){
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter!=null){
            adapter.stopListening();
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter!=null){
            adapter.notifyDataSetChanged();
        }
    }
}