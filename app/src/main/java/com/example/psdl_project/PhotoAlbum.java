package com.example.psdl_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class PhotoAlbum extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_album);

        ImageView addPictureButton = findViewById(R.id.image_add_picture);
        ImageView displayPicturesButton = findViewById(R.id.image_display_picture);

        addPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhotoAlbum.this, AddPhotoActivity.class);
                startActivity(intent);
            }
        });

        displayPicturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhotoAlbum.this, DisplayPhotoActivity.class);
                startActivity(intent);
            }
        });
    }
}
