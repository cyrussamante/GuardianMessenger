package com.example.guardianmessenger.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
    Any useful utils for usability within activities
 */
public class SessionController {

    /**
     * Redirects to desired page using an ImageButton.
     * @param redirectButton: Button to be activated
     * @param currentActivity: Current page the user is on
     * @param newClass: Desired class to go to
     */
    public static void redirectButton(ImageButton redirectButton, Activity currentActivity, final Class<?> newClass) {
        redirectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentActivity.startActivity(new Intent(currentActivity, newClass));
            }
        });
    }

    /**
     * Redirects to desired page using a Button.
     * @param redirectButton: Button to be activated
     * @param currentActivity: Current page the user is on
     * @param newClass: Desired class to go to
     */
    public static void redirectButton(Button redirectButton, Activity currentActivity, final Class<?> newClass) {
        redirectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentActivity.startActivity(new Intent(currentActivity, newClass));
            }
        });
    }

}
