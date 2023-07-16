package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NickNamePick extends AppCompatActivity {

    private EditText userName;
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_name_pick);

        userName = findViewById(R.id.user_nickName);
        submit = findViewById(R.id.sumbit_Name_btn);

        ScreenUtils.hideSystemUI(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = userName.getText().toString();
                if(name.length()<=0 || name.length()>10){
                    Toast.makeText(NickNamePick.this, "Name must be between 1 and 10 characters", Toast.LENGTH_SHORT).show();
                    return;
                }
                mySP.getSP().writeNickName(name);
                finish();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mySP.getSP()
    }
}