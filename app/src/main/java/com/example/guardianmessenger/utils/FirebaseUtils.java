package com.example.guardianmessenger.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.List;

public class FirebaseUtils {

    /***
     * Returns the current user id
     * @return user id
     */
    public static String currentUserId(){
        return FirebaseAuth.getInstance().getUid();
    }

    /***
     * Returns if the user is logged in
     * @return true if user is logged in
     */
    public static Boolean isLoggedIn(){
        return (currentUserId() != null);
    }

    /***
     * Returns the current user's details
     * @return user details
     */
    public static DocumentReference getUserDetails(){
        return FirebaseFirestore.getInstance().collection("users").document(currentUserId());
    }
    /***
     * Returns the user details of the user with the given id
     * @param userId
     * @return user details
     */
    public static DocumentReference getUserDetails(String userId){
        return FirebaseFirestore.getInstance().collection("users").document(userId);
    }

    /***
     * Returns the collection of all users
     * @return all users collection
     */
    public static CollectionReference allUsersCollectionReference(){
        return FirebaseFirestore.getInstance().collection("users");
    }

    /***
     * Converts email to user id
     * @param email
     * @return user id
     */
    public static String emailToUserId(String email) {
            Log.e("email", "emailToUserId: " + email);
            Task<QuerySnapshot> task = FirebaseFirestore.getInstance().collection("users").whereEqualTo("email", email).get();
            Log.e("email", "task created");
            while (!task.isComplete()); // wait for task to complete, ugly but works, Tasks.await(task) doesn't work
            Log.e("email", "task complete");
            QuerySnapshot querySnapshot = (QuerySnapshot) task.getResult();
            if (querySnapshot.isEmpty()) {
                Log.e("email", "querySnapshot empty");
                return null;
            }
            Log.e("email", "querySnapshot not empty");
        try {
            return querySnapshot.getDocuments().get(0).getId();
        } catch (IndexOutOfBoundsException e) { // just in case querySnapshot.getDocuments().get(0) is empty
            return null;
        }
    }


    /***
     * Deletes the current user
     * @param context
     */
    public static void deleteUser(Context context) {
         FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
         if (user != null) {
             user.delete();
         }
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(context, "Account Deletion Successful", Toast.LENGTH_SHORT).show();
    }

    /***
     * Returns a chat with the given chat log id
     * @param chatId
     * @return DocumentReference
     */
    public static DocumentReference getChatReference(String chatId){
        return FirebaseFirestore.getInstance().collection("chats").document(chatId);
    }
    /***
     * Returns the chat id of the chat between the two users
     * @param user1Id
     * @param user2Id
     * @return chat id
     */
    public static String getChatId(String user1Id,String user2Id){
        if (user1Id.hashCode() < user2Id.hashCode()){
            return user1Id+""+user2Id;
        }else {
            return user2Id+""+user1Id;
        }
    }

    /***
     * Returns the chat log of the chat with the given chat id
     * @param chatId
     * @return CollectionReference
     */
    public static CollectionReference getChatMessageRef(String chatId){
        return getChatReference(chatId).collection("chatlog");
    }

    /***
     * Returns the collection of all chats
     * @return all chats collection
     */
    public static CollectionReference allChatsRefs(){
        return FirebaseFirestore.getInstance().collection("chats");
    }

    /***
     * Returns a collection of user data for all specified user ids
     * @param userIds
     * @return Collection of DocumentReferences
     */
    public static DocumentReference getOtherUsers(List<String> userIds){
        if (userIds.get(0).equals(FirebaseUtils.currentUserId())){
            return allUsersCollectionReference().document(userIds.get(1));
        }else {
            return allUsersCollectionReference().document(userIds.get(0));
        }
    }
    /***
     * Converts a timestamp to a string
     * @param timestamp
     * @return string
     */
    public static String timestampToString(Timestamp timestamp){
        return new SimpleDateFormat("HH:MM").format(timestamp.toDate());
    }
}
