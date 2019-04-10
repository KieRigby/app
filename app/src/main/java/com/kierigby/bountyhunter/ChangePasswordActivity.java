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

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText mCurrentPassword, mPasswordInput, mConfirmPasswordInput;
    private BountyHunterAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((GlobalUser) this.getApplication()).tokenCheck();
        setContentView(R.layout.activity_change_password);
        api = new BountyHunterAPI(this);
        addListenerToArrow();
        addListenerToSubmitButton();
    }

    public void addListenerToSubmitButton() {
        mCurrentPassword = findViewById(R.id.currPass);
        mPasswordInput = findViewById(R.id.etChangePassword);
        mConfirmPasswordInput = findViewById(R.id.etConfirmChangePassword);
        Button mNewPasswordBtn = findViewById(R.id.btSubmitPassword);

        mNewPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPassword = mCurrentPassword.getText().toString();
                String password = mPasswordInput.getText().toString();
                String confirmPassword = mConfirmPasswordInput.getText().toString();
                if(validInputs(currentPassword,password,confirmPassword)){
                    api.changePassword(((GlobalUser) getApplication()).getLoggedInUser().getId(), currentPassword, confirmPassword, new BountyHunterAPI.successCallBack() {
                        @Override
                        public void success() {
                            NavUtils.navigateUpFromSameTask(ChangePasswordActivity.this);
                        }
                    });


                }
            }
        });


    }


    public void addListenerToArrow() {
        ImageButton mbackButton = findViewById(R.id.backFromChangePass);

        mbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(ChangePasswordActivity.this);
            }
        });
    }

    public boolean validInputs(String currentPassword, String password, String confirmPassword) {

        final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!|0-9])(?=\\S+$).{6,}$";
        if (currentPassword.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter all the necessary information", Toast.LENGTH_LONG).show();
            return false;
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(getApplicationContext(), "The passwords you entered do not match", Toast.LENGTH_LONG).show();
            return false;
        } else if (!confirmPassword.matches(PASSWORD_REGEX)) {
            Toast.makeText(getApplicationContext(), "Your password must be 6 characters long and must contain one upper and lowercase letter and number or special character (@#$%!)", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
