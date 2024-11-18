package com.example.psdl_project;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class AddPhotoActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView imageViewPreview;
    private EditText editTextName;
    private DBHelperPhoto dbHelperPhoto;
    private Uri imageUri;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        imageViewPreview = findViewById(R.id.imageViewPreview);
        editTextName = findViewById(R.id.editTextName);
        Button btnChooseFile = findViewById(R.id.btnChooseFile);
        Button btnSaveFile = findViewById(R.id.btnSaveFile);

        dbHelperPhoto = new DBHelperPhoto(this);

        btnChooseFile.setOnClickListener(v -> chooseImage());
        btnSaveFile.setOnClickListener(v -> saveImage());
    }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imageViewPreview.setImageURI(imageUri);
            imageViewPreview.setVisibility(View.VISIBLE);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imagePath = saveToInternalStorage(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String saveToInternalStorage(Bitmap bitmap) throws IOException {
        File directory = getFilesDir();
        File file = new File(directory, UUID.randomUUID().toString() + ".jpg");
        FileOutputStream fos = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        fos.close();
        return file.getAbsolutePath();
    }

    private void saveImage() {
        String personName = editTextName.getText().toString();
        if (personName.isEmpty() || imagePath == null) {
            Toast.makeText(this, "Please enter a name and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isInserted = dbHelperPhoto.insertPhoto(personName, imagePath);
        if (isInserted) {
            Toast.makeText(this, "Image Saved Successfully", Toast.LENGTH_SHORT).show();
            editTextName.setText("");
            imageViewPreview.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, "Failed to Save Image", Toast.LENGTH_SHORT).show();
        }
    }
}
