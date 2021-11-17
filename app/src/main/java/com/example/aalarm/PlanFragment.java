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
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public PlanFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment PlanFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static PlanFragment newInstance(String param1, String param2) {
//        PlanFragment fragment = new PlanFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

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
            displayUserActivity.add(String.format("%s   Importance: %s", ua.getName(), ua.getImportance()));
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