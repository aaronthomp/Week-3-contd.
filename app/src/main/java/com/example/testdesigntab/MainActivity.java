package com.example.testdesigntab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Switch;


/**
 * this class runs the code since its the main
 * @author Aaron Thompson
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    /**
     * this holds the text at the center of the screen
     */
    private TextView tv = null;
    /**
     * this holds the edit text for the password
     */
    private EditText et = null;
    /**
     * this holds the button
     */
    private Button btn = null;
    /**
     * This method checks to see if the password input has enough complexity
     * @param pw takes in a password
     * @return returns true if it meets the complexity requirements
     */
    boolean checkPasswordComplexity(String pw) {
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;

        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        for (char c : pw.toCharArray()) {
            if (Character.isUpperCase(c)) {
                foundUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                foundLowerCase = true;
            } else if (Character.isDigit(c)) {
                foundNumber = true;
            } else if (isSpecialCharacter(c)) {
                foundSpecial = true;
            }
        }

        if (!foundUpperCase) {
            Toast.makeText(this, "Your password does not have an upper case letter", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundLowerCase) {
            Toast.makeText(this, "Your password does not have a lower case letter", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundNumber) {
            Toast.makeText(this, "Your password does not have a number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundSpecial) {
            Toast.makeText(this, "Your password does not have a special symbol", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    // Function to check if the character is a special character
    private boolean isSpecialCharacter(char c) {
        switch (c) {
            case '#':
            case '$':
            case '%':
            case '^':
            case '&':
            case '*':
            case '!':
            case '@':
            case '?':
                return true;
            default:
                return false;
        }
    }



    ImageView imgView;
    Switch sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView tv = findViewById(R.id.textView);
        EditText et = findViewById(R.id.editTextTextPassword);
        Button btn = findViewById(R.id.buttonLogin);

        btn.setOnClickListener((clk ->{
            String password = et.getText().toString();

            boolean isPasswordValid = checkPasswordComplexity(password);
            if (isPasswordValid) {
                tv.setText("Your password meets the requirements");
            } else {
                tv.setText("You shall not pass!");
            }
        }));

        imgView = findViewById(R.id.flagview);
        sw = findViewById(R.id.spin_switch);

        sw.setOnCheckedChangeListener((btn, isChecked) -> {
            if (isChecked)
            {
                RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(5000);
                rotate.setRepeatCount(Animation.INFINITE);
                rotate.setInterpolator(new LinearInterpolator());

                imgView.startAnimation(rotate);
            }
            else {
                imgView.clearAnimation();
            }
        });
    }
}