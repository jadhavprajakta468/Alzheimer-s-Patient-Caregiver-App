package com.example.psdl_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddDailyActivity extends AppCompatActivity {

    private DBHelper1 dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_daily);

        dbHelper = new DBHelper1(this);

        EditText etDescription = findViewById(R.id.et_activity_description);
        EditText etDate = findViewById(R.id.et_activity_date);
        Button btnSubmit = findViewById(R.id.btn_submit_activity);

        btnSubmit.setOnClickListener(v -> {
            String description = etDescription.getText().toString();
            String date = etDate.getText().toString();

            if (dbHelper.addActivity(description, date)) {
                Toast.makeText(this, "Activity added", Toast.LENGTH_SHORT).show();
                finish(); // Go back to DailyActivity
            } else {
                Toast.makeText(this, "Error adding activity", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
