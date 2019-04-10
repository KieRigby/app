package com.kierigby.bountyhunter;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import com.example.bountyhunterapi.BountyHunterAPI;
import com.example.bountyhunterapi.User;

public class GlobalUser extends Application {
    User loggedInUser;
    BountyHunterAPI api;

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public User getLoggedInUser() {
        return this.loggedInUser;
    }

    public void logoutUser() {
        this.loggedInUser = null;
        api = new BountyHunterAPI(this);
        api.clearToken();
        Intent logoutI = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(logoutI);
    }

    public void tokenCheck() {
        api = new BountyHunterAPI(this);
        api.getUser(loggedInUser.getId(), new BountyHunterAPI.TokenCheckCallBack() {
            @Override
            public void tokenCheck(int code) {
                if (code != 200) {
                    logoutUser();
                    Toast.makeText(getApplicationContext(), "Your session has expired please login again", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


}
