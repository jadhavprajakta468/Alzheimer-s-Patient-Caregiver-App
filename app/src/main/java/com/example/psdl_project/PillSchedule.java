package com.example.psdl_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PillSchedule extends AppCompatActivity {

    private DBHelper dbHelper;
    private LinearLayout pillScheduleContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pill_schedule);

        dbHelper = new DBHelper(this);
        pillScheduleContainer = findViewById(R.id.pill_schedule_container);

        // Button to open AddScheduleActivity
        Button addScheduleButton = findViewById(R.id.add_schedule_button);
        addScheduleButton.setOnClickListener(v -> openAddSchedule());

        loadSchedules(); // Initial load of schedules
    }

    // This method is called when AddScheduleActivity finishes and returns to PillSchedule.
    @Override
    protected void onResume() {
        super.onResume();
        loadSchedules(); // Reload schedules to display the new data when returning from AddScheduleActivity
    }

    private void loadSchedules() {
        // Query all pill schedules from the database
        Cursor cursor = dbHelper.getAllSchedules();

        if (cursor != null && cursor.moveToFirst()) {
            pillScheduleContainer.removeAllViews();  // Clear any previous data in the layout
            do {
                // Retrieve pill data from the cursor
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NAME));
                @SuppressLint("Range") String schedule = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_SCHEDULE));

                // Dynamically create a new layout for each pill
                LinearLayout itemLayout = new LinearLayout(this);
                itemLayout.setOrientation(LinearLayout.HORIZONTAL);
                itemLayout.setPadding(16, 16, 16, 16);

                // Text layout for pill name and schedule
                LinearLayout textLayout = new LinearLayout(this);
                textLayout.setOrientation(LinearLayout.VERTICAL);
                textLayout.setPadding(16, 0, 0, 0);

                // Text for pill name
                TextView pillNameText = new TextView(this);
                pillNameText.setText("Pill Name: " + name);
                pillNameText.setTextSize(18);
                pillNameText.setTextColor(getResources().getColor(R.color.black));

                // Text for pill schedule
                TextView pillScheduleText = new TextView(this);
                pillScheduleText.setText("Schedule: " + schedule);
                pillScheduleText.setTextSize(16);
                pillScheduleText.setTextColor(getResources().getColor(R.color.black));

                // Add the TextViews to the textLayout container
                textLayout.addView(pillNameText);
                textLayout.addView(pillScheduleText);

                // Add the textLayout to the itemLayout container
                itemLayout.addView(textLayout);

                // Add the itemLayout (pill item) to the pillScheduleContainer
                pillScheduleContainer.addView(itemLayout);

            } while (cursor.moveToNext());
            cursor.close();
        } else {
            // If no pill schedules are found, show a toast message
            Toast.makeText(this, "No pill schedules found.", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to open the AddScheduleActivity
    private void openAddSchedule() {
        Intent intent = new Intent(PillSchedule.this, AddScheduleActivity.class);
        startActivity(intent); // Start AddScheduleActivity
    }
}
