package com.example.guardianmessenger.outreach;

import androidx.annotation.NonNull;

import com.example.guardianmessenger.utils.FirebaseUtils;
import com.example.guardianmessenger.utils.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class OutreachController {


    public boolean requestOutreach(String employee_id1, String employee_id2) {
        //TODO: request outreach approval from manager
        boolean approved = true; // for now, assume all requests are approved
        if (approved) {
            return approveOutreach(employee_id1, employee_id2);
        } else {
            return rejectOutreach(employee_id1, employee_id2);
        }
    }

    private boolean approveOutreach(String employee1, String employee2) {
        addOutreach(employee1, employee2);
        return true;
    }

    private boolean rejectOutreach(String employee1, String employee2) {
        return false;
    }

    private void addOutreach(String employee1, String employee2) {
        DocumentReference currentUser = FirebaseUtils.getOtherUser(employee1);
        currentUser.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        UserModel user = document.toObject(UserModel.class);
                        assert user != null;
                        user.addOutreach(employee2);
                    }
                }
            }
        });
        DocumentReference otherUser = FirebaseUtils.getOtherUser(employee2);
        otherUser.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        UserModel user = document.toObject(UserModel.class);
                        assert user != null;
                        user.addOutreach(employee1);
                    }
                }
            }
        });
    }

}
