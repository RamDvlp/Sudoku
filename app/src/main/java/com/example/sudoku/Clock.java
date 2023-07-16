package com.example.sudoku;

import android.content.Context;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class Clock {

    private int seconds;
    private int minutes;

    public Clock(int seconds, int minutes) {
        this.seconds = seconds;
        this.minutes = minutes;

    }

    public int getSeconds() {
        return seconds;
    }

    public Clock setSeconds(int seconds) {
        this.seconds = seconds;
        return this;
    }

    public int getMinutes() {
        return minutes;
    }

    public Clock setMinutes(int minutes) {
        this.minutes = minutes;
        return this;
    }

    public void tick(){
        if(seconds== 60){
            seconds = 0;
            minutes++;
        }

        seconds++;
    }

    public String getCurrentCount(){

        if(minutes<10){
            if(seconds<10){
                return "0"+ minutes + ":0" + seconds;
            }
            return "0"+ minutes + ":" + seconds;
        } else {
            return minutes + ":" + seconds;
        }
    }


}
