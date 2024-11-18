package com.example.psdl_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateAddressBook extends AppCompatActivity {

    private EditText addressInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_address_book);

        addressInput = findViewById(R.id.address_input);
        Button updateButton = findViewById(R.id.update_button);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedAddress = addressInput.getText().toString().trim();

                if (!updatedAddress.isEmpty()) {
                    // Update the static presetLocation in OfficeMap class
                    OfficeMap.presetLocation = updatedAddress;

                    // Return to OfficeMap
                    Intent resultIntent = new Intent();
                    setResult(RESULT_OK, resultIntent);
                    finish(); // Close UpdateAddressBook and return to OfficeMap
                } else {
                    addressInput.setError("Address cannot be empty");
                }
            }
        });
    }
}
