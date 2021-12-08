package com.example.aalarm;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class PlanFragment extends Fragment {

    public static ArrayList<UserActivity> userAActivity = new ArrayList<>();
    ListView listView;

    public PlanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_plan, container, false);
        readUserActivity(view);
        Button button = (Button) view.findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity().getApplicationContext(), AddUserActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void readUserActivity(ViewGroup view) {

        // 1. Get SQLiteDatabase instance
        Context context = getActivity().getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("alarms", Context.MODE_PRIVATE, null);

        // 2. Initialize the alarms class variable
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        userAActivity = dbHelper.readUserActivity();
        if (userAActivity.size() == 0) return;

        // 3. Create an ArrayList<String> object by iterating over alarms object
        ArrayList<String> displayUserActivity = new ArrayList<>();
        for (UserActivity ua : userAActivity) {
            displayUserActivity.add(String.format("%s   Frequency: %s", ua.getName(), ua.getFrequency()));
        }

        // 4. User ListView view to display alarms on screen
        if (displayUserActivity.size() != 0) {
            ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, displayUserActivity);
            listView = (ListView) view.findViewById(R.id.userlist);
            listView.setAdapter(adapter);
        }

        // 5. Move to AlarmDetailActivity when clicked on an alarm
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getActivity().getApplicationContext(), AlarmDetailActivity.class);
//                intent.putExtra("activity_id", position);
//                startActivity(intent);
            }
        });
    }


}