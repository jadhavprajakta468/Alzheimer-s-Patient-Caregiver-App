package com.example.psdl_project;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayPhotoActivity extends AppCompatActivity {
    private DBHelperPhoto dbHelperPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_photo);

        dbHelperPhoto = new DBHelperPhoto(this);
        LinearLayout photoContainer = findViewById(R.id.photoContainer);

        Cursor cursor = dbHelperPhoto.getAllPhotos();
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("person_name"));
                @SuppressLint("Range") String imagePath = cursor.getString(cursor.getColumnIndex("image_path"));

                // Add the name
                TextView textView = new TextView(this);
                textView.setText(name);
                textView.setTextSize(18f);
                photoContainer.addView(textView);

                // Add the image
                ImageView imageView = new ImageView(this);
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                imageView.setImageBitmap(bitmap);
                imageView.setAdjustViewBounds(true);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                photoContainer.addView(imageView);

                // Add a black line (divider)
                View divider = new View(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 2); // 2dp height for the divider
                params.setMargins(0, 16, 0, 16); // Add some vertical spacing
                divider.setLayoutParams(params);
                divider.setBackgroundColor(getResources().getColor(android.R.color.black)); // Black color
                photoContainer.addView(divider);

            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}
