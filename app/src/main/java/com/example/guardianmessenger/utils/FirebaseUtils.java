package com.example.guardianmessenger.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.List;
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

    public static DocumentReference getUserDetails(String userId){
        return FirebaseFirestore.getInstance().collection("users").document(userId);
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

    public static DocumentReference getChatReference(String chatId){
        return FirebaseFirestore.getInstance().collection("chats").document(chatId);
    }

    public static String getChatId(String user1Id,String user2Id){
        if (user1Id.hashCode() < user2Id.hashCode()){
            return user1Id+""+user2Id;
        }else {
            return user2Id+""+user1Id;
        }
    }

    public static CollectionReference getChatMessageRef(String chatId){
        return getChatReference(chatId).collection("chatlog");
    }

    public static CollectionReference allChatsRefs(){
        return FirebaseFirestore.getInstance().collection("chats");
    }

    public static DocumentReference getOtherUsers(List<String> userIds){
        if (userIds.get(0).equals(FirebaseUtils.currentUserId())){
            return allUsersCollectionReference().document(userIds.get(1));
        }else {
            return allUsersCollectionReference().document(userIds.get(0));
        }
    }

    public static String timestampToString(Timestamp timestamp){
        return new SimpleDateFormat("HH:MM").format(timestamp.toDate());
    }
}
