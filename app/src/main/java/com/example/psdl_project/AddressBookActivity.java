package com.example.psdl_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddressBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book);

        // Finding the ImageViews by their IDs
        ImageView officeImageView = findViewById(R.id.office);
        ImageView bankImageView = findViewById(R.id.bank);
        ImageView marketImageView = findViewById(R.id.market);
        ImageView homeImageView = findViewById(R.id.home);

        // Finding the TextViews by their IDs
        TextView officeEditTextView = findViewById(R.id.office_edit);
        // Setting click listeners for each ImageView to open the corresponding activity or map
        officeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AddressBookActivity", "Office ImageView clicked");
                Intent officeIntent = new Intent(AddressBookActivity.this, OfficeMap.class);
                startActivity(officeIntent);
            }
        });

        bankImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bankIntent = new Intent(AddressBookActivity.this, BankMap.class);
                startActivity(bankIntent);
            }
        });

        marketImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent marketIntent = new Intent(AddressBookActivity.this, MarketMap.class);
                startActivity(marketIntent);
            }
        });

        homeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(AddressBookActivity.this, HomeMap.class);
                startActivity(homeIntent);
            }
        });

        // Setting click listeners for each "edit" TextView to navigate to UpdateAddressBook
        officeEditTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateOfficeIntent = new Intent(AddressBookActivity.this, UpdateAddressBook.class);
                updateOfficeIntent.putExtra("type", "Office");
                startActivity(updateOfficeIntent);
            }
        });


    }
}
