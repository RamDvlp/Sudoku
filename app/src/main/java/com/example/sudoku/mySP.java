package com.example.sudoku;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;

public class mySP {

    private SharedPreferences pref = null;

    private static final String FILE_NAME = "USER_ID";

    private static final String KEY = "ID";

    private static final String NAME = "NAME";

    private static mySP sp;

    private static final String DEF_VALUE = "";

    private mySP(Context context) {
        this.pref = context.getApplicationContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

    }

    public static void init(Context context) {
        if (sp == null) {
            sp = new mySP(context);
        }

    }

    public static mySP getSP() {
        return sp;
    }

    public void writeID(String id){

        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY, id);
        editor.apply();
    }

    public String readID(){

        String id = pref.getString(KEY, DEF_VALUE);
        return id;
    }


    public void writeNickName(String name){

        SharedPreferences.Editor editor = pref.edit();
        editor.putString(NAME, name);
        editor.apply();
    }

    public String readNickName(){

        String name = pref.getString(NAME, DEF_VALUE);
        //Log.d("Sfff", name);
        return name;
    }

}
