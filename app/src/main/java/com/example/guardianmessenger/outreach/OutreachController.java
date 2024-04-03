package com.example.guardianmessenger.outreach;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.guardianmessenger.OutreachActivity;
import com.example.guardianmessenger.utils.FirebaseUtils;
import com.example.guardianmessenger.utils.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class OutreachController {


    public static boolean requestOutreach(String employee_id1, String employee_id2) {
        //TODO: request outreach approval from manager
        boolean approved = true; // for now, assume all requests are approved
        if (approved) {
            return approveOutreach(employee_id1, employee_id2);
        } else {
            return rejectOutreach(employee_id1, employee_id2);
        }
    }

    private static boolean approveOutreach(String employee1, String employee2) {
        try {
            addOutreach(employee1, employee2);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private static boolean rejectOutreach(String employee1, String employee2) {
        return false;
    }

    private static void addOutreach(String employee1, String employee2) {
        DocumentReference currentUser = FirebaseFirestore.getInstance().collection("users").document(employee1);
        currentUser.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    UserModel user = document.toObject(UserModel.class);
                    user.addOutreach(employee2);
                    FirebaseUtils.getUserDetails(employee1).set(user);
                } else {
                    throw new IllegalArgumentException("User does not exist");
                }
            } else {
                throw new IllegalArgumentException("Task failed");
            }
        });

        DocumentReference otherUser = FirebaseFirestore.getInstance().collection("users").document(employee2);
        otherUser.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    UserModel user = document.toObject(UserModel.class);
                    user.addOutreach(employee1);
                    FirebaseUtils.getUserDetails(employee2).set(user);
                } else {
                    throw new IllegalArgumentException("User does not exist");
                }
            } else {
                throw new IllegalArgumentException("Task failed");
            }
        });
    }

}
