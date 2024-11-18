package com.example.psdl_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateForm extends AppCompatActivity {

    private EditText editTextPhoneNumber ,editTextName, editTextAge, editTextBirthDate, editTextCity, editTextSpouse, editTextFavoriteActivity;
    private Button buttonUpdateForm;
    private DBHelperForm dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_form);

        dbHelper = new DBHelperForm(this);

        // Initialize views
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextBirthDate = findViewById(R.id.editTextBirthDate);
        editTextCity = findViewById(R.id.editTextCity);
        editTextSpouse = findViewById(R.id.editTextSpouse);
        editTextFavoriteActivity = findViewById(R.id.editTextFavoriteActivity);
        buttonUpdateForm = findViewById(R.id.buttonUpdateForm);
        editTextPhoneNumber=findViewById(R.id.editTextPhoneNumber);
        // Set click listener
        buttonUpdateForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserDetails();
            }
        });
    }

    private void updateUserDetails() {
        // Retrieve user input values
        String phoneNumber = editTextPhoneNumber.getText().toString().trim(); // Assuming this field is used for phone number input
        String name = editTextName.getText().toString().trim();
        String ageStr = editTextAge.getText().toString().trim();
        String birthDate = editTextBirthDate.getText().toString().trim();
        String city = editTextCity.getText().toString().trim();
        String spouse = editTextSpouse.getText().toString().trim();
        String favoriteActivity = editTextFavoriteActivity.getText().toString().trim();

        // Validation for empty fields
        if (phoneNumber.isEmpty() || name.isEmpty() || ageStr.isEmpty() || birthDate.isEmpty() || city.isEmpty() || spouse.isEmpty() || favoriteActivity.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert age to integer
        int age = Integer.parseInt(ageStr);

        // Call the update method and pass the phone number along with other details
        boolean isUpdated = dbHelper.updateUserDetails(phoneNumber, name, age, birthDate, city, spouse, favoriteActivity);

        if (isUpdated) {
            Toast.makeText(this, "User details updated successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdateForm.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
        }
    }

}
