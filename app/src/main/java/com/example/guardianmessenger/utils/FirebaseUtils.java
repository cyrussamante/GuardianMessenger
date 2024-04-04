package com.example.guardianmessenger.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

    public static String emailToUserId(String email) {
        Log.e("email", FirebaseFirestore.getInstance().collection("users").get().toString());
        try {
            Log.e("email", "emailToUserId: " + email);
            Task<QuerySnapshot> task = FirebaseFirestore.getInstance().collection("users").whereEqualTo("email", email).get();
            Log.e("email", "task created");
            while (!task.isComplete()); // wait for task to complete
            Log.e("email", "task awaited");
            QuerySnapshot querySnapshot = (QuerySnapshot) task.getResult();
            Log.e("email", "querySnapshot created");
            if (querySnapshot.isEmpty()) {
                Log.e("email", "querySnapshot empty");
                return null;
            }
            Log.e("email", "querySnapshot not empty");
            return querySnapshot.getDocuments().get(0).getId();
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }


    // delete user from both authentication db
    public static void deleteUser(Context context) {
         FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
         if (user != null) {
             user.delete();
         }
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(context, "Account Deletion Successful", Toast.LENGTH_SHORT).show();
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
