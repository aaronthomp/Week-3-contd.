package com.example.testdesigntab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.testdesigntab.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    private ActivityMainBinding variableBinding;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w( TAG, "Any memory used by the application is freed.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w( TAG, "The application is now responding to user input");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w( TAG, "The application is no longer responding to user input");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w( TAG, "The application is no longer visible.");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w( TAG, "The application is now visible on screen.");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());
        Intent nextPage = new Intent( MainActivity.this, SecondActivity.class);

        Log.w( TAG, "the first function that gets created when an application is launched.");


        variableBinding.loginButton.setOnClickListener( clk-> {
            nextPage.putExtra( "EmailAddress", variableBinding.emailEditText.getText().toString() );
            startActivity(nextPage);
        } );

    }
}