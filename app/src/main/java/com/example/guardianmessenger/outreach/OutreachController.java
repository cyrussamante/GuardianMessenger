package com.example.guardianmessenger.outreach;

import android.util.Log;

import com.example.guardianmessenger.utils.FirebaseUtils;
import com.example.guardianmessenger.utils.EmployeeModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class OutreachController {


    public static boolean requestOutreach(String employee_id1, String employee_email2) {
        Log.e("Outreach", "Attempting to convert email to user id");
        String employee_id2 = FirebaseUtils.emailToUserId(employee_email2);
        Log.e("Outreach", "Outreach request from " + employee_id1 + " to " + employee_id2 + " initiated");
        if (employee_id1.equals(employee_id2)) {
            Log.e("Outreach", "Outreach request to self");
            return false;
        }
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
            addOutreach(employee2, employee1);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private static boolean rejectOutreach(String employee1, String employee2) {
        return false;
    }

    private static void addOutreach(String employee1, String employee2) throws IllegalArgumentException {
        DocumentReference employee = FirebaseFirestore.getInstance().collection("users").document(employee1); // get user doc
        employee.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    EmployeeModel user = document.toObject(EmployeeModel.class); // convert doc to user object
                    user.addOutreach(employee2);
                    FirebaseUtils.getUserDetails(employee1).set(user); // update user doc
                } else {
                    throw new IllegalArgumentException("User does not exist");
                }
            } else {
                throw new IllegalArgumentException("Task failed");
            }
        });
    }
}
