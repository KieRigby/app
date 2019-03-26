package com.kierigby.bountyhunter;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bountyhunterapi.BountyHunterAPI;

public class RegisterActivity extends AppCompatActivity {

    private ImageButton mbackButton;
    private Button mCreatAccountButton;
    private BountyHunterAPI api = new BountyHunterAPI(this);
    private EditText mFirstNameInput, mLastNameInput, mUsernameInput, mEmailInput, mPasswordInput, mConfirmPasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        addListenerToArrow();
        addListenerToCreateAccountButton();
    }

    public void addListenerToArrow() {
        mbackButton = findViewById(R.id.image_arrow_button);

        mbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(RegisterActivity.this);
            }
        });
    }

    public void addListenerToCreateAccountButton() {
        mCreatAccountButton = findViewById(R.id.createAccountBtn);
        mFirstNameInput = findViewById(R.id.firstNameEditText);
        mLastNameInput = findViewById(R.id.lastNameEditText);
        mEmailInput = findViewById(R.id.registerEmailEditText);
        mUsernameInput = findViewById(R.id.registerUsernameEditText);
        mPasswordInput = findViewById(R.id.registerPasswordEditText);
        mConfirmPasswordInput = findViewById(R.id.confirmPasswordEditText);

        mCreatAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFirstNameInput.getText().toString().isEmpty() || mLastNameInput.getText().toString().isEmpty() || mEmailInput.getText().toString().isEmpty() || mUsernameInput.getText().toString().isEmpty() || mPasswordInput.getText().toString().isEmpty() || mConfirmPasswordInput.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter all the necessary information", Toast.LENGTH_LONG).show();
                } else if (!mPasswordInput.getText().toString().equals(mConfirmPasswordInput.getText().toString())) {
                    Toast.makeText(getApplicationContext(),"The passwords you entered do not match" ,Toast.LENGTH_LONG).show();
                }else if(api.isEmailValid(mEmailInput.getText().toString())==false){
                    Toast.makeText(getApplicationContext(),"Please enter a valid email address" ,Toast.LENGTH_LONG).show();
                }

                else {
                    api.registerUser(mFirstNameInput.getText().toString(), mLastNameInput.getText().toString(),mUsernameInput.getText().toString(), mEmailInput.getText().toString(),mConfirmPasswordInput.getText().toString());
                    NavUtils.navigateUpFromSameTask(RegisterActivity.this);
                }
            }
        });
    }
}
