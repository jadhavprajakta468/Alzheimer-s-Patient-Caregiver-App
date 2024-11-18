package com.example.psdl_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MemoryBooster extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_booster);

        // Initialize the "Start Quiz" button
        Button startQuizButton = findViewById(R.id.start_quiz_button);

        // Set an OnClickListener for the "Start Quiz" button
        startQuizButton.setOnClickListener(v -> {
            
            // Create an Intent to navigate to the Quiz activity
            Intent intent = new Intent(MemoryBooster.this, Quiz.class);
            startActivity(intent);
        });

        // Initialize the "Quit" button
        Button quitButton = findViewById(R.id.quit_button);

        // Set an OnClickListener for the "Quit" button
        quitButton.setOnClickListener(v -> {
            // Create an Intent to navigate to MainActivity
            Intent intent = new Intent(MemoryBooster.this, MainActivity.class);
            startActivity(intent);
            finish();  // Optionally finish MemoryBooster activity so it's removed from the back stack
        });
    }
}
