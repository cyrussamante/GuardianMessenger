package com.example.guardianmessenger.utils;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseUtils {
    public static String currentUserId(){
        return FirebaseAuth.getInstance().getUid();
    }

    public static Boolean isLoggedIn(){
        return (currentUserId() != null);
    }
}
