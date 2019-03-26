package com.kierigby.bountyhunter;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bountyhunterapi.BountyHunterAPI;

public class ForgottenPassActivity extends AppCompatActivity {

    private Button mNextButton;
    private EditText mEmailInput;
    private BountyHunterAPI api = new BountyHunterAPI(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_pass);
        addListenerToNextButton();

    }

    public void addListenerToNextButton() {
        mNextButton = findViewById(R.id.nextBtn);
        mEmailInput = findViewById(R.id.enterEmail);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmailInput.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter the email of the account that you would like to reset the password for", Toast.LENGTH_LONG).show();
                }else if(api.isEmailValid(mEmailInput.getText().toString())==false){
                    Toast.makeText(getApplicationContext(),"Please enter a valid email address" ,Toast.LENGTH_LONG).show();
                }
                else {
                    api.resetPasswordRequest(mEmailInput.getText().toString());
                    NavUtils.navigateUpFromSameTask(ForgottenPassActivity.this);
                }
            }
        });
    }

}
