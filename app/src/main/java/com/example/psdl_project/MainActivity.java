package com.example.psdl_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;  // Correct Toolbar import
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class  MainActivity extends AppCompatActivity {


    TextView userName;
    Button logout, sosCall;

    @Override
    // MainActivity.java


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the Pill Schedule button by its ID
        LinearLayout pillScheduleButton = findViewById(R.id.pill_schedule_button);
        LinearLayout PhotoAlbum = findViewById(R.id.photo);
        LinearLayout Task = findViewById(R.id.task);
        LinearLayout MemoryBooster= findViewById(R.id.brainbooster);
        LinearLayout Form= findViewById(R.id.form);
        LinearLayout current = (LinearLayout) findViewById(R.id.currentaddress);
        LinearLayout address = findViewById(R.id.address);
        LinearLayout games = findViewById(R.id.games);
        Toolbar toolbar=findViewById(R.id.toolbar);
        // Set a click listener on the button
        sosCall = (Button) findViewById(R.id.sos);

        setSupportActionBar(toolbar);
        pillScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start PillSchedule activity when the button is clicked
                Intent intent = new Intent(MainActivity.this, PillSchedule.class);
                startActivity(intent);
            }
        });

        PhotoAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start PillSchedule activity when the button is clicked
                Intent intent = new Intent(MainActivity.this, PhotoAlbum.class);
                startActivity(intent);
            }
        });
        Task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start PillSchedule activity when the button is clicked
                Intent intent = new Intent(MainActivity.this, DailyActivity.class);
                startActivity(intent);
            }
        });
        MemoryBooster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start PillSchedule activity when the button is clicked
                Intent intent = new Intent(MainActivity.this, MemoryBooster.class);
                startActivity(intent);
            }
        });
        Form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start PillSchedule activity when the button is clicked
                Intent intent = new Intent(MainActivity.this, UpdateForm.class);
                startActivity(intent);
            }
        });
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to open Google Maps location
                Intent intent = new Intent(MainActivity.this, AddressBookActivity.class);
                startActivity(intent);
            }
        });
        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to open Google Maps location
                Intent intent = new Intent(MainActivity.this, CurrentAddress.class);
                startActivity(intent);
            }
        });
        sosCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelperForm dbHelper = new DBHelperForm(MainActivity.this);
                String sosNumber = dbHelper.getFirstPhoneNumber(); // Fetch the first phone number from DB

                if (TextUtils.isEmpty(sosNumber)) {
                    Toast.makeText(MainActivity.this, "No SOS number found in the database!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check for CALL_PHONE permission
                if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, make the call
                    makeCall(sosNumber);
                } else {
                    // Request permission if not granted
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
                }
            }
        });
        games.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Games.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.options_menu, menu);

        // Set text color for each menu item
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            SpannableString spanString = new SpannableString(item.getTitle());
            spanString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spanString.length(), 0); // Set text color to black
            item.setTitle(spanString);
        }

        return true;
    }

    @Override

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menuProfile) {
            // Redirect to LoginActivity and clear the activity stack
            Intent intent = new Intent(MainActivity.this, ProfilePage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish(); // Finish the current activity
        }
        if (itemId == R.id.logout) {
            // Redirect to LoginActivity and clear the activity stack
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish(); // Finish the current activity
        }

        return super.onOptionsItemSelected(item);
    }

    private void makeCall(String sosNumber) {
        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
        phoneIntent.setData(Uri.parse("tel:" + sosNumber));
        startActivity(phoneIntent);
    }
}