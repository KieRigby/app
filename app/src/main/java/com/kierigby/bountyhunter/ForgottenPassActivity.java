package com.kierigby.bountyhunter;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bountyhunterapi.BountyHunterAPI;

public class ForgottenPassActivity extends AppCompatActivity {

    private EditText mEmailInput;
    private BountyHunterAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_pass);
        api= new BountyHunterAPI(this);
        addListenerToNextButton();
        addListenerToBackButton();
    }

    public void addListenerToNextButton() {
        Button mNextButton = findViewById(R.id.nextBtn);
        mEmailInput = findViewById(R.id.enterEmail);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String email= mEmailInput.getText().toString();

               if (validInputs(email)){
                   api.resetPasswordRequest(mEmailInput.getText().toString());
                   NavUtils.navigateUpFromSameTask(ForgottenPassActivity.this);
               }
            }
        });
    }

    public void addListenerToBackButton() {
        ImageButton mBackButton = findViewById(R.id.backFromForgottenPass);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(ForgottenPassActivity.this);
            }
        });
    }

    public boolean validInputs(String email) {

        if (email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter the email of the account that you would like to reset the password", Toast.LENGTH_LONG).show();
            return false;
        } else if (!api.isEmailValid(email)) {
            Toast.makeText(getApplicationContext(),"Please enter a valid email address" ,Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
