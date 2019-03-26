package com.kierigby.bountyhunter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class LoggedInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        Toast.makeText(getApplicationContext(), ((GlobalUser) getApplication()).getLoggedInUser().getUsername(), Toast.LENGTH_LONG).show();

    }
}
