package com.kierigby.bountyhunter;

import android.app.Application;

import com.example.bountyhunterapi.BountyHunterAPI;
import com.example.bountyhunterapi.User;

public class GlobalUser extends Application {
    User loggedInUser;

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public User getLoggedInUser(){
        return this.loggedInUser;
    }

    public void logoutUser(){
        this.loggedInUser=null;
    }
}
