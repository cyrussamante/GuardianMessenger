package com.example.guardianmessenger.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseUtils {
    public static String currentUserId(){
        return FirebaseAuth.getInstance().getUid();
    }

    public static Boolean isLoggedIn(){
        return (currentUserId() != null);
    }

    public static DocumentReference getUserDetails(){
        return FirebaseFirestore.getInstance().collection("users").document(currentUserId());
    }

    public static CollectionReference allUsersCollectionReference(){
        return FirebaseFirestore.getInstance().collection("users");
    }
}
