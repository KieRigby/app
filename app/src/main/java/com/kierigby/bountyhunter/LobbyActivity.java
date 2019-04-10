package com.kierigby.bountyhunter;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LobbyActivity extends AppCompatActivity {

    private TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        tabs = findViewById(R.id.tabs);

        tabs.addTab(tabs.newTab().setText("Accepted"));
        tabs.addTab(tabs.newTab().setText("Pending"));
        tabs.addTab(tabs.newTab().setText("Declined"));
    }
}