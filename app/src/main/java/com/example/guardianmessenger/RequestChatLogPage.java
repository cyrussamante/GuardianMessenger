package com.example.guardianmessenger;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.guardianmessenger.chatlog.ChatLogController;
import com.example.guardianmessenger.utils.SessionController;

import java.util.Calendar;
import java.util.Date;

/**
 * Based on ChatLogPage boundary class from previous deliverables
 */
public class RequestChatLogPage extends AppCompatActivity {

    private EditText nameField, startDateField, endDateField;
    private Date  start, end;
    private String recipientName;

    /**
     * Creates the request chat log activity
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_request_chat_log);

        // button initialization
        ImageButton backButton = findViewById(R.id.back_button);
        Button requestButton = findViewById(R.id.request_chat_logs_button);

        // fields initialization
        nameField = findViewById(R.id.nameInput);
        startDateField = findViewById(R.id.startDateInput);
        endDateField = findViewById(R.id.endDateInput);

        // back button listener
        SessionController.redirectButton(backButton, RequestChatLogPage.this, HomePage.class);

        // request chat logs button listener
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (readInput()) {
                    ChatLogController c = new ChatLogController(recipientName, start, end, v, getContentResolver());
                    c.findChat();
                } else {
                    Toast.makeText(RequestChatLogPage.this, "Invalid information entered", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * Reads input from UI fields and stores information in class attributes
     * @return boolean: True if user provides input in correct format
     */
    private boolean readInput() {
        // get values from fields
        recipientName = String.valueOf(nameField.getText());
        String startDate = String.valueOf(startDateField.getText());
        String endDate = String.valueOf(endDateField.getText());

        // check for empty input information
        if (recipientName.isEmpty() || startDate.isEmpty() || endDate.isEmpty())
            return false;

        // try converting string input date (YYYY/MM/DD) to Date objects
        String[] temp = startDate.split("/");
        if (invalidDateInputFormat(temp)) return false;
        start = new Date(Integer.parseInt(temp[0]) - 1900, Integer.parseInt(temp[1]) - 1, Integer.parseInt(temp[2]));
        temp = endDate.split("/");
        if (invalidDateInputFormat(temp)) return false;
        end = new Date(Integer.parseInt(temp[0]) - 1900, Integer.parseInt(temp[1]) - 1, Integer.parseInt(temp[2]));

        // include end date in date range
        Calendar c = Calendar.getInstance();
        c.setTime(end);
        c.add(Calendar.DATE, 1);
        end = c.getTime();

        return true;
    }

    /**
     * Checks if user inputs dates in an incorrect format
     * Correct format: X/X/X where X is an integer
     * @param elements: String array after calling split("/") on user-inputted dates
     * @return boolean: True if user provides date information in an incorrect format
     */
    private boolean invalidDateInputFormat(String[] elements) {
        if (elements.length != 3) return true;
        for (String s : elements) {
            try {
                Integer.parseInt(s);
            } catch (NumberFormatException e) {
                return true;
            }
        }
        return false;
    }

}