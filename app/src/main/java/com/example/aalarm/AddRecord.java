package com.example.aalarm;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddRecord extends AppCompatActivity {

    public static ArrayList<UserActivity> userAActivity = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        readUserActivity();
    }

    private void readUserActivity() {

        // 1. Get SQLiteDatabase instance
        Context context = getApplicationContext().getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("alarms", Context.MODE_PRIVATE, null);

        // 2. Initialize the alarms class variable
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        userAActivity = dbHelper.readUserActivity();
        if (userAActivity.size() == 0) return;

        // 3. Create an ArrayList<String> object by iterating over alarms object
        ArrayList<String> displayUserActivity = new ArrayList<>();
        for (UserActivity ua : userAActivity) {
            displayUserActivity.add(String.format("%s", ua.getName()));
        }

        // 4. User ListView view to display alarms on screen
        if (displayUserActivity.size() != 0) {
            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, displayUserActivity);
            listView = (ListView) findViewById(R.id.userlist);
            listView.setAdapter(adapter);
        }

        // 5. Move to AlarmDetailActivity when clicked on an alarm
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                String name = (String) o; //As you are using Default String Adapter
                Toast.makeText(getBaseContext(),name,Toast.LENGTH_SHORT).show();

                Date currentTime = Calendar.getInstance().getTime();
                int year = currentTime.getYear();
                int month = currentTime.getMonth();
                int day = currentTime.getDay();
                int hour = currentTime.getHours();
                int minute = currentTime.getMinutes();
                dbHelper.saveActivityRecord(year, month, day, hour, minute, name);
                toMain();
            }
        });
    }

    public void clickCancel(View view) {
        toMain();
    }

    public void toMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("frgToLoad", R.id.personal);
        startActivity(intent);
    }
}
