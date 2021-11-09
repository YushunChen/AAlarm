package com.example.aalarm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class AlarmFragment extends Fragment {

    private static ArrayList<Alarm> alarms = new ArrayList<>();

    public AlarmFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_alarm, container, false);
        readUserAlarms(view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        inflater.inflate(R.menu.game_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.rewrite:
                Intent intent = new Intent(getActivity(), GameRewrite.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void readUserAlarms(ViewGroup view) {

        // 1. Get SQLiteDatabase instance
        Context context = getActivity().getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("alarms", Context.MODE_PRIVATE, null);

        // 2. Initialize the alarms class variable
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        alarms = dbHelper.readAlarms();

        // 3. Create an ArrayList<String> object by iterating over alarms object
        ArrayList<String> displayAlarms = new ArrayList<>();
        for (Alarm alarm : alarms) {
            displayAlarms.add(String.format("Alarm set for: %s:%s on %s-%s-%s", alarm.getHour(), alarm.getMinute(), alarm.getYear(), alarm.getMonth() + 1, alarm.getDay()));
        }

        // 4. User ListView view to display alarms on screen
        if (displayAlarms.size() != 0) {
            ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, displayAlarms);
            ListView listView = (ListView) view.findViewById(R.id.alarmListView);
            listView.setAdapter(adapter);
        }


    }
}