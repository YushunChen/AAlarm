package com.example.aalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class AlarmFragment extends Fragment {

    public static ArrayList<Alarm> alarms = new ArrayList<>();
    ListView listView;

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

    private void readUserAlarms(ViewGroup view) {

        // 1. Get SQLiteDatabase instance
        Context context = getActivity().getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("alarms", Context.MODE_PRIVATE, null);

        // 2. Initialize the alarms class variable
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        alarms = dbHelper.readAlarms();
        if (alarms.size() == 0) return;

        // 3. Create an ArrayList<String> object by iterating over alarms object
        ArrayList<String> displayAlarms = new ArrayList<>();
        for (Alarm alarm : alarms) {
            displayAlarms.add(String.format("Alarm set for: %s:%s on %s-%s-%s", alarm.getHour(), alarm.getMinute(), alarm.getYear(), alarm.getMonth() + 1, alarm.getDay()));
        }

        // 4. User ListView view to display alarms on screen
        if (displayAlarms.size() != 0) {
            ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, displayAlarms);
            listView = (ListView) view.findViewById(R.id.alarmListView);
            listView.setAdapter(adapter);
        }

        // 5. Move to AlarmDetailActivity when clicked on an alarm
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AlarmDetailActivity.class);
                intent.putExtra("alarmid", position);
                startActivity(intent);
            }
        });


    }


}