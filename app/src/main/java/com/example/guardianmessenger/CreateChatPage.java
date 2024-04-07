package com.example.guardianmessenger;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guardianmessenger.adapter.SearchUserRecyclerAdapter;
import com.example.guardianmessenger.utils.FirebaseUtils;
import com.example.guardianmessenger.utils.SessionController;
import com.example.guardianmessenger.utils.EmployeeModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

/**
 * This is the activity for searching for users and creating chats
 */
public class CreateChatPage extends AppCompatActivity {

    EditText searchInput;
    ImageButton searchButton, backButton;
    RecyclerView recyclerView;

    SearchUserRecyclerAdapter adapter;

    /**
     * Method to create the activity
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chat);
        searchButton = findViewById(R.id.search_button);
        backButton = findViewById(R.id.back_button);
        searchInput = findViewById(R.id.search_input);
        recyclerView = findViewById(R.id.search_recycler_view);
        searchInput.requestFocus();

        // enter key listener on search input
        searchInput.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) { // looks for key
                    searchUser();
                    return true;
                }
                return false;
            }
        });

        // search button listener
        searchButton.setOnClickListener(v ->{searchUser();});

        // back button listener
        SessionController.redirectButton(backButton, CreateChatPage.this, MessagesPage.class);

    }

    /**
     * Gets search term and calls recylcer view
     */
    private void searchUser() {
        String searchTerm = searchInput.getText().toString();
        if (searchTerm.isEmpty() || searchTerm.length() <2){
            searchInput.setError("Invalid Term");
            return;
        }

        setupSearchRecyclerView(searchTerm);
    }

    /**
     * Setups recycler views after getting matching users from firebase
     * @param searchTerm search query for users
     */
    void setupSearchRecyclerView(String searchTerm){
        Query query = FirebaseUtils.allUsersCollectionReference().orderBy("name").startAt(searchTerm.toUpperCase()).endAt(searchTerm.toLowerCase() + "\uf8ff");
        FirestoreRecyclerOptions<EmployeeModel> options = new FirestoreRecyclerOptions.Builder<EmployeeModel>().setQuery(query, EmployeeModel.class).build();
        adapter = new SearchUserRecyclerAdapter(options,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    /**
     * onStart(), onStop(), onResume() are methods to control behaviour of the recycler view
     */
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