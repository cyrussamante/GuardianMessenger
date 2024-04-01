package com.example.guardianmessenger.outreach;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.guardianmessenger.accounts.Employee;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class OutreachController {

    public boolean requestOutreach(Employee employee1, Employee employee2) {
        //TODO: request outreach approval from manager
        boolean approved = true; // for now, assume all requests are approved
        if (approved) {
            return approveOutreach(employee1, employee2);
        } else {
            return rejectOutreach(employee1, employee2);
        }
    }

    private boolean approveOutreach(Employee employee1, Employee employee2) {
        employee1.addOutreach(employee2);
        employee2.addOutreach(employee1);
        return true;
    }

    private boolean rejectOutreach(Employee employee1, Employee employee2) {
        return false;
    }

}
