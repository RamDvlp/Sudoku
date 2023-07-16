package com.example.sudoku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 difficulty_pageView;

    private Button startGame_btn;

    private Button leaderBoard_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ScreenUtils.hideSystemUI(this);

        difficulty_pageView = findViewById(R.id.Main_difficulty_selection);
        OptionPagerAdapter adapter = new OptionPagerAdapter(this);
        difficulty_pageView.setAdapter(adapter);
        //difficulty_pageView.setCurrentItem(adapter.getInitialItem(), false);

        difficulty_pageView.setCurrentItem(1);

        leaderBoard_btn = findViewById(R.id.main_BTN_leaderBoard);
        leaderBoard_btn.setOnClickListener(v -> openLeaderBoard());

        startGame_btn = findViewById(R.id.main_BTN_startGame);
        startGame_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startgame();
            }
        });


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                TimeScoreListFragment.getListItems();

            }
        });
        thread.start();



    }

    private void openLeaderBoard() {
        Intent intent = new Intent(MainActivity.this, LeaderBoard.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        choseName();
    }

    private void choseName(){
        if(!mySP.getSP().readNickName().equals("")){
            return;
        }

        Intent intent = new Intent(MainActivity.this, NickNamePick.class);
        //intent.putExtra("Difficulty", difficulty_pageView.getCurrentItem());
        startActivity(intent);
    }


    private void startgame() {
        Intent intent = new Intent(MainActivity.this, SudokuActivity.class);
        intent.putExtra("Difficulty", difficulty_pageView.getCurrentItem());
        startActivity(intent);

    }


    /*
    all of this is made so the choosing of difficulty will be made with swipe over the field.
    Some taken from StuckOverFlow some from chatGPT, with my customization of course.
     */
    private static class OptionPagerAdapter extends RecyclerView.Adapter<OptionPagerAdapter.OptionViewHolder> {

        private List<String> optionTexts = Arrays.asList(" < Easy > ", " < Medium > ", " < Hard > ");

        private Context context;

        public OptionPagerAdapter(Context context) {
            this.context = context;
        }


        @NonNull
        @Override
        public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.fragment_difficulty_option, parent, false);
            return new OptionViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull OptionViewHolder holder, int position) {
            String optionText = optionTexts.get(position);
            holder.optionTextView.setText(optionText);


        }

        @Override
        public int getItemCount() {
            return optionTexts.size();
        }

        class OptionViewHolder extends RecyclerView.ViewHolder {

            TextView optionTextView;

            public OptionViewHolder(@NonNull View itemView) {
                super(itemView);
                optionTextView = itemView.findViewById(R.id.presented_difficulty);
            }
        }
    }
}
