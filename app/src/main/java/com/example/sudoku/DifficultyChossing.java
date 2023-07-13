package com.example.sudoku;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DifficultyChossing extends Fragment {

    private static final String ARG_OPTION_TEXT = "option_text";

    public static DifficultyChossing newInstance(String optionText) {
        DifficultyChossing fragment = new DifficultyChossing();
            Bundle args = new Bundle();
            args.putString(ARG_OPTION_TEXT, optionText);
            fragment.setArguments(args);
            return fragment;
    }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_difficulty_option, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            // Set up the content for each option fragment
            TextView optionTextView = view.findViewById(R.id.presented_difficulty);
            optionTextView.setText(getArguments().getString(ARG_OPTION_TEXT));
        }
    }


