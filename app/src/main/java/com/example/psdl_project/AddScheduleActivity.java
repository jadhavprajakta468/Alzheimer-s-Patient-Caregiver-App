package com.example.psdl_project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddScheduleActivity extends AppCompatActivity {

    private EditText pillName, scheduleInput;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        // Corrected references for EditText fields
        pillName = findViewById(R.id.pill_name);
        scheduleInput = findViewById(R.id.schedule_input);  // Fixed: This should reference the EditText field for schedule

        // Button to save the schedule
        Button saveButton = findViewById(R.id.save_button);

        dbHelper = new DBHelper(this);

        // Save button click listener
        saveButton.setOnClickListener(v -> saveSchedule());
    }

    private void saveSchedule() {
        String name = pillName.getText().toString();
        String schedule = scheduleInput.getText().toString();

        if (name.isEmpty() || schedule.isEmpty()) {
            Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isInserted = dbHelper.insertPillSchedule(name, schedule);

        if (isInserted) {
            Toast.makeText(this, "Schedule Saved", Toast.LENGTH_SHORT).show();
            finish(); // Return to previous screen
        } else {
            Toast.makeText(this, "Error Saving Schedule", Toast.LENGTH_SHORT).show();
        }
    }
}
