package com.example.testdesigntab;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;


import com.example.testdesigntab.databinding.ActivitySecondBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {
    private ActivitySecondBinding variableBinding;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        variableBinding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        // Initialize SharedPreferences
        prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);

        // Load the saved phone number, if any, and set it in the EditText
        String savedPhoneNumber = prefs.getString("PhoneNumber", "");
        variableBinding.editTextPhone.setText(savedPhoneNumber);

        // Get the email address from the intent and display a welcome message
        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");
        variableBinding.welcomeText.setText("Welcome back " + emailAddress);

        // Set a click listener for the button to initiate a phone call
        variableBinding.button2.setOnClickListener(v -> {
            String phoneNumber = variableBinding.editTextPhone.getText().toString().trim();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        });

        // Set a click listener for the button to capture an image using the camera
        variableBinding.button3.setOnClickListener(v -> cameraResult.launch(cameraIntent));

        // Check if the image file exists and display it if found
        String filename = "Picture.png";
        File file = new File(getFilesDir(), filename);
        if (file.exists()) {
            Bitmap theImage = BitmapFactory.decodeFile(file.getAbsolutePath());
            variableBinding.imageView.setImageBitmap(theImage);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Save the current phone number to SharedPreferences
        String phoneNumber = variableBinding.editTextPhone.getText().toString().trim();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("PhoneNumber", phoneNumber);
        editor.apply();
    }

    // Intent to capture an image using the camera
    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

    // Activity result launcher for camera intent
    ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.getExtras() != null) {
                        // Get the captured image bitmap
                        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                        variableBinding.imageView.setImageBitmap(thumbnail);

                        // Save the image to internal storage
                        try (FileOutputStream fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE)) {
                            thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );
}