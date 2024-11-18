package com.example.psdl_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Games extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        // Find all the game layouts
        LinearLayout numberGame = findViewById(R.id.number);
        LinearLayout tilesGame = findViewById(R.id.tiles);
        LinearLayout matchingGame = findViewById(R.id.matching);

        // Set click listener for number memory game
        numberGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Games", "Number Memory Game clicked");
                // Start NumberMemoryGame activity
                Intent intent = new Intent(Games.this, NumberMemoryGame.class);
                startActivity(intent);
            }
        });

        // Set click listener for memory tiles game
        tilesGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Games", "Memory Tiles Game clicked");
                // Start MemoryTiles activity
                Intent intent = new Intent(Games.this, MemoryTiles.class);
                startActivity(intent);
            }
        });

        // Set click listener for memory matching game
        matchingGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Games", "Memory Matching Game clicked");
                // Start MemoryMatching activity
                Intent intent = new Intent(Games.this, MemoryMatching.class);
                startActivity(intent);
            }
        });
    }
}
