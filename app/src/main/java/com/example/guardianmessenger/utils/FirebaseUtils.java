package com.example.guardianmessenger.utils;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.guardianmessenger.LoginActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    // delete user from both user db and authentication db
    public static void deleteUser(Context context) {
        getUserDetails().delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                removeUserAuth();
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(context, "Account Deletion Successful", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Account Deletion Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void removeUserAuth() {
         FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
         if (user != null) {
             user.delete();
         }
    }
}
