package com.kierigby.bountyhunter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bountyhunterapi.BountyHunterAPI;
import com.example.bountyhunterapi.Friend;
import com.example.bountyhunterapi.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button mLoginButton, mRegisterButton;
    private EditText mPasswordInput;
    private EditText mUsernameInput;
    private TextView mForgotPasswordTextView;
    private BountyHunterAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        api= new BountyHunterAPI(this);
        addListenerToLoginButton();
        addListenerToRegisterButton();
        addListenerToForgotPasswordTextView();
    }

    public void addListenerToLoginButton() {
        mLoginButton = findViewById(R.id.loginBtn);
        mUsernameInput = findViewById(R.id.loginUsernameEditText);
        mPasswordInput = findViewById(R.id.loginPasswordEditText);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUsernameInput.getText().toString().isEmpty() || mPasswordInput.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter both your username and password", Toast.LENGTH_LONG).show();
                } else {
                    String username = mUsernameInput.getText().toString();
                    String password = mPasswordInput.getText().toString();
                    api.loginUser(username, password, new BountyHunterAPI.FoundUserCallBack() {
                        @Override
                        public void onUserReturned(User user) {
                            ((GlobalUser) getApplication()).setLoggedInUser(user);
                            Intent loggedInI = new Intent(MainActivity.this, LoggedInActivity.class);
                            startActivity(loggedInI);
                        }
                    });
                }

            }
        });
    }

    public void addListenerToRegisterButton() {
        mRegisterButton = findViewById(R.id.registerBtn);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerI = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerI);
            }
        });
    }

    public void addListenerToForgotPasswordTextView() {
        mForgotPasswordTextView = findViewById(R.id.forgotPasswordtextView);

        mForgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotPasswordI = new Intent(MainActivity.this, ForgottenPassActivity.class);
                startActivity(forgotPasswordI);
            }
        });
    }

    private void friendsTest(){
        api.getFriendsFollowing(((GlobalUser) getApplication()).getLoggedInUser().getId(), new BountyHunterAPI.FoundFriendsCallBack() {
            @Override
            public void onFriendsFound(List<Friend> friends) {
                Toast.makeText(getApplicationContext(), friends.get(0).getFriend().getUsername(), Toast.LENGTH_LONG).show();

            }
        });
    }
}
