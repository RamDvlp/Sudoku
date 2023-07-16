package com.example.sudoku;

import android.util.Log;

import androidx.annotation.NonNull;

public class LocalUser {

    String userID;
    String userNickName;
    String userTime;


    public LocalUser(){

    }

    public LocalUser(String userID, String userNickName) {
        this.userID = userID;
        this.userNickName = userNickName;
    }

    public String getUserID() {
        return userID;
    }

    public LocalUser setUserID(String userID) {
        this.userID = userID;
        return this;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public LocalUser setUserNickName(String userNickName) {
        this.userNickName = userNickName;
        return this;
    }

    public String getUserTime() {
        return userTime;
    }

    public LocalUser setUserTime(@NonNull String userTime) {

        if(this.userTime == null){
            this.userTime = userTime;
            Log.d("ASD", this.userTime);

            return this;
        }

        //TODO -Optionally, make sure that the time achieved in this game better then previous. otherwise dont bother DB.
        //Really not necessary but nice.

        Log.d("ASD", "current time "+userTime);
        Log.d("ASD", "last time" + this.userTime);


        String val[] = userTime.split(":");
        String val2[] = this.userTime.split(":");

        int a = Integer.parseInt(val[0]+ "" + val[1]);
        int b = Integer.parseInt(val2[0]+ "" + val2[1]);

        if(a>b){
            this.userTime = userTime;
        }

        return this;
    }



}
