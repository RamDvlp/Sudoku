package com.example.sudoku;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TimeScoreListFragment extends Fragment {

    private ListView list_LST_names_scores;

    private static ArrayList<String> data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_time_score_list, container,false);

        list_LST_names_scores = view.findViewById(R.id.list_LST_scores);


//        update_allScores_list_callback.updateListView();
//        ArrayAdapter arrayAdapter = new ArrayAdapter(container.getContext(), R.layout.list_country, data);
        ArrayAdapter arrayAdapter = new ArrayAdapter(requireContext(), R.layout.listitem, data);
        list_LST_names_scores.setAdapter(arrayAdapter);
//        //ArrayList<String> a = new ArrayList();
//        a.add("1");
//        list_LST_names_scores.setAdapter(new ArrayAdapter(requireContext(), R.layout.listitem, a));


        //list_LST_names_scores.seton

        return view;
    }

    public void setNewAdapter(){

        //getListItems();
        ArrayAdapter arrayAdapter = new ArrayAdapter(requireContext(), R.layout.listitem, data);
        list_LST_names_scores.setAdapter(arrayAdapter);

    }

    public static void getListItems() {

        data = FireBaseModul.getFireBaseModul().readAllUsers();

    }
}