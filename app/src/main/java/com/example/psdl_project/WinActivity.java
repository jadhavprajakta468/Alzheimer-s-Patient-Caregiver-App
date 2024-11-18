package com.example.psdl_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class WinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        // Initialize buttons
        Button playAgainButton = findViewById(R.id.playAgain_button);
        Button quitButton = findViewById(R.id.quit_button);

        // Set onClickListener for Play Again button
        playAgainButton.setOnClickListener(v -> {
            // Intent to navigate to Quiz activity
            Intent quizIntent = new Intent(WinActivity.this, Quiz.class);
            startActivity(quizIntent);
            finish(); // Close current activity
        });

        // Set onClickListener for Quit button
        quitButton.setOnClickListener(v -> {
            // Intent to navigate to MainActivity
            Intent mainIntent = new Intent(WinActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish(); // Close current activity
        });
    }
}
