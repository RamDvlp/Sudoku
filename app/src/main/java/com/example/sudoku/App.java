package com.example.sudoku;

import android.app.Application;

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        mySP.init(this);
        FireBaseModul.init();
    }
}
