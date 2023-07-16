package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class LeaderBoard extends AppCompatActivity {

    private TimeScoreListFragment scoreList;

    private TextView myScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);


        myScore = findViewById(R.id.myScore_LBL);
        scoreList = new TimeScoreListFragment();



        FireBaseModul.getFireBaseModul().setCompletion_time_callback(new Completion_Time_Callback() {
            @Override
            public void setUserScore(String s) {
                myScore.setText(s);
            }
        });

        FireBaseModul.getFireBaseModul().readUserData();


        ScreenUtils.hideSystemUI(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_LAY_leaderBoard, scoreList)
                .commit();

    }

}