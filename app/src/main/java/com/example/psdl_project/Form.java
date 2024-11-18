package com.example.psdl_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Form extends AppCompatActivity {

    private EditText nameEditText, ageEditText, birthDateEditText, cityEditText, spouseEditText, favoriteActivityEditText, phoneNumberEditText;
    private Button submitButton;
    private DBHelperForm dbHelperForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        // Reset form completion flag in SharedPreferences when the form is opened
        SharedPreferences preferences = getSharedPreferences("FormPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isFormCompleted", false); // Reset to false when opening the form
        editor.apply();

        dbHelperForm = new DBHelperForm(this);

        nameEditText = findViewById(R.id.editTextName);
        ageEditText = findViewById(R.id.editTextAge);
        birthDateEditText = findViewById(R.id.editTextBirthDate);
        cityEditText = findViewById(R.id.editTextCity);
        spouseEditText = findViewById(R.id.editTextSpouse);
        favoriteActivityEditText = findViewById(R.id.editTextFavoriteActivity);
        phoneNumberEditText = findViewById(R.id.editTextPhoneNumber);
        submitButton = findViewById(R.id.buttonSubmitForm);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
    }

    private void submitForm() {
        String name = nameEditText.getText().toString().trim();
        String ageStr = ageEditText.getText().toString().trim();
        String birthDate = birthDateEditText.getText().toString().trim();
        String city = cityEditText.getText().toString().trim();
        String spouse = spouseEditText.getText().toString().trim();
        String favoriteActivity = favoriteActivityEditText.getText().toString().trim();
        String phoneNumber = phoneNumberEditText.getText().toString().trim();

        // Validate phone number
        if (phoneNumber.isEmpty() || phoneNumber.length() != 10) {
            Toast.makeText(this, "Please enter a valid 10-digit phone number.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate other fields
        if (name.isEmpty() || ageStr.isEmpty() || birthDate.isEmpty() || city.isEmpty() || spouse.isEmpty() || favoriteActivity.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate age
        int age = -1;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid numeric age.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insert form data into database
        boolean isInserted = dbHelperForm.insertUserDetails(name, age, birthDate, city, spouse, favoriteActivity, phoneNumber);

        if (isInserted) {
            Toast.makeText(this, "Form submitted successfully!", Toast.LENGTH_SHORT).show();
            clearForm();

            // Mark the form as completed by setting a SharedPreferences flag
            SharedPreferences preferences = getSharedPreferences("FormPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFormCompleted", true); // Set form as completed
            editor.apply();

            // Redirect to SignUpActivity
            Intent intent = new Intent(Form.this, SignUpActivity.class);
            startActivity(intent);
            finish(); // Close FormActivity
        } else {
            // If insertion failed, log the error and show a toast
            Toast.makeText(this, "Error submitting form, please try again.", Toast.LENGTH_SHORT).show();
            Log.e("Form", "Failed to insert user data");
        }
    }

    // Clear form fields
    private void clearForm() {
        nameEditText.setText("");
        ageEditText.setText("");
        birthDateEditText.setText("");
        cityEditText.setText("");
        spouseEditText.setText("");
        favoriteActivityEditText.setText("");
        phoneNumberEditText.setText("");
    }
}
