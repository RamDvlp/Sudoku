package com.example.sudoku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 difficulty_pageView;

    private String chosenDifficulty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ScreenUtils.hideSystemUI(this);

        difficulty_pageView = findViewById(R.id.Main_difficulty_selection);
        OptionPagerAdapter adapter = new OptionPagerAdapter(this);
        difficulty_pageView.setAdapter(adapter);
        //difficulty_pageView.setCurrentItem(adapter.getInitialItem(), false);

    }




    /*
    all of this is made so the choosing of difficulty will be made with swipe over the field.
    Some taken from StuckOverFlow some from chatGPT, with my customization of course.
     */
    private static class OptionPagerAdapter extends RecyclerView.Adapter<OptionPagerAdapter.OptionViewHolder> {

        private List<String> optionTexts = Arrays.asList("Easy", "Medium", "Hard");

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
