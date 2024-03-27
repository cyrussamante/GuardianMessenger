package com.example.guardianmessenger;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guardianmessenger.adapter.SearchUserRecyclerAdapter;
import com.example.guardianmessenger.utils.FirebaseUtils;
import com.example.guardianmessenger.utils.UserModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class CreateChat extends AppCompatActivity {

    EditText searchInput;
    Button searchButton;
    RecyclerView recyclerView;

    SearchUserRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chat);
        searchButton = findViewById(R.id.search_button);
        searchInput = findViewById(R.id.search_input);
        recyclerView = findViewById(R.id.search_recyler_view);
        searchInput.requestFocus();

        searchButton.setOnClickListener(v ->{
            String searchTerm = searchInput.getText().toString();
            if (searchTerm.isEmpty() || searchTerm.length() <2){
                searchInput.setError("Invalid Term");
                return;
            }

            setupSearchRecylerView(searchTerm);
        });

    }

    void setupSearchRecylerView(String searchTerm){

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