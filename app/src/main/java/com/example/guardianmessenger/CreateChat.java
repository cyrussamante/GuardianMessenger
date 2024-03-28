package com.example.guardianmessenger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guardianmessenger.adapter.SearchUserRecyclerAdapter;
import com.example.guardianmessenger.utils.FirebaseUtils;
import com.example.guardianmessenger.utils.UserModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class CreateChat extends AppCompatActivity {

    EditText searchInput;
    ImageButton searchButton, backButton;
    RecyclerView recyclerView;

    SearchUserRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chat);
        searchButton = findViewById(R.id.search_button);
        backButton = findViewById(R.id.back_button);
        searchInput = findViewById(R.id.search_input);
        recyclerView = findViewById(R.id.search_recycler_view);
        searchInput.requestFocus();

        searchButton.setOnClickListener(v ->{
            String searchTerm = searchInput.getText().toString();
            if (searchTerm.isEmpty() || searchTerm.length() <2){
                searchInput.setError("Invalid Term");
                return;
            }

            setupSearchRecyclerView(searchTerm);
        });

        // back button listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateChat.this, MessageActivity.class);
                startActivity(i);
            }
        });

    }

    void setupSearchRecyclerView(String searchTerm){

        Query query = FirebaseUtils.allUsersCollectionReference().whereGreaterThanOrEqualTo("name",searchTerm);
        FirestoreRecyclerOptions<UserModel> options = new FirestoreRecyclerOptions.Builder<UserModel>().setQuery(query,UserModel.class).build();
        adapter = new SearchUserRecyclerAdapter(options,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
            adapter.startListening();
        }
    }
}