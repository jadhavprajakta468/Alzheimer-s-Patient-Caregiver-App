package com.example.psdl_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfilePage extends AppCompatActivity {

    private DBHelperForm dbHelper;
    private TextView textViewPhoneNumber, textViewName, textViewAge, textViewBirthDate, textViewCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        // Initialize DBHelper
        dbHelper = new DBHelperForm(this);

        // Bind UI components
        textViewPhoneNumber = findViewById(R.id.textView_show_mobile);
        textViewName = findViewById(R.id.textview_show_full_name);
        textViewAge = findViewById(R.id.age);
        textViewBirthDate = findViewById(R.id.textView_show_dob);
        textViewCity = findViewById(R.id.location_city);
        ImageView arrowIcon = findViewById(R.id.arrow_icon);
        arrowIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to MainActivity (or any activity)
                Intent intent = new Intent(ProfilePage.this, MainActivity.class);
                startActivity(intent);  // Start the new activity
            }
        });


        // Fetch and display the first user's details from the database
        displayUserProfile();
    }

    private void displayUserProfile() {
        // Get the first phone number from the DB
        String phoneNumber = dbHelper.getFirstPhoneNumber();

        if (phoneNumber != null) {
            // Fetch user details based on phone number
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DBHelperForm.TABLE_USER,
                    null, // Get all columns
                    DBHelperForm.COLUMN_PHONE_NUMBER + " = ?",
                    new String[]{phoneNumber},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                // Extract values from the cursor
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DBHelperForm.COLUMN_NAME));
                @SuppressLint("Range") int age = cursor.getInt(cursor.getColumnIndex(DBHelperForm.COLUMN_AGE));
                @SuppressLint("Range") String birthDate = cursor.getString(cursor.getColumnIndex(DBHelperForm.COLUMN_BIRTH_DATE));
                @SuppressLint("Range") String city = cursor.getString(cursor.getColumnIndex(DBHelperForm.COLUMN_CITY));

                // Set the values to the TextViews
                textViewPhoneNumber.setText("Phone Number: " + phoneNumber);
                textViewName.setText("Name: " + name);
                textViewAge.setText("Age: " + age);
                textViewBirthDate.setText("Birth Date: " + birthDate);
                textViewCity.setText("City: " + city);

                cursor.close();
            }
            db.close();
        }
    }
}
