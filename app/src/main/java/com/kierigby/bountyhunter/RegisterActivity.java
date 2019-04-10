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

    private BountyHunterAPI api;
    private EditText mFirstNameInput, mLastNameInput, mUsernameInput, mEmailInput, mPasswordInput, mConfirmPasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        api = new BountyHunterAPI(this);
        addListenerToArrow();
        addListenerToCreateAccountButton();
    }

    public void addListenerToArrow() {
        ImageButton mbackButton = findViewById(R.id.backFromStats);

        mbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(RegisterActivity.this);
            }
        });
    }

    public void addListenerToCreateAccountButton() {
        Button mCreatAccountButton = findViewById(R.id.createAccountBtn);
        mFirstNameInput = findViewById(R.id.firstNameEditText);
        mLastNameInput = findViewById(R.id.lastNameEditText);
        mEmailInput = findViewById(R.id.registerEmailEditText);
        mUsernameInput = findViewById(R.id.registerUsernameEditText);
        mPasswordInput = findViewById(R.id.registerPasswordEditText);
        mConfirmPasswordInput = findViewById(R.id.confirmPasswordEditText);

        mCreatAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fistname = mFirstNameInput.getText().toString();
                String lastname = mLastNameInput.getText().toString();
                String email = mEmailInput.getText().toString();
                String username = mUsernameInput.getText().toString();
                String password = mPasswordInput.getText().toString();
                String confirmPassword = mConfirmPasswordInput.getText().toString();

                if (validInputs(fistname, lastname, email, username, password, confirmPassword)) {
                    api.registerUser(fistname, lastname, username, email, confirmPassword, new BountyHunterAPI.successCallBack() {
                        @Override
                        public void success() {
                            NavUtils.navigateUpFromSameTask(RegisterActivity.this);
                            Toast.makeText(getApplicationContext(), "Your account was successfully registered", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    public boolean validInputs(String fistname, String lastname, String email, String username, String password, String confirmPassword) {

        final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!|0-9])(?=\\S+$).{6,}$";
        if (fistname.isEmpty() || lastname.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter all the necessary information", Toast.LENGTH_LONG).show();
            return false;
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(getApplicationContext(), "The passwords you entered do not match", Toast.LENGTH_LONG).show();
            return false;
        } else if (!confirmPassword.matches(PASSWORD_REGEX)) {
            Toast.makeText(getApplicationContext(), "Your password must be 6 characters long and must contain one upper and lowercase letter and number or special character (@#$%!)", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (!api.isEmailValid(email)) {
            Toast.makeText(getApplicationContext(), "Please enter a valid email address", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
