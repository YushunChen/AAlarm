package com.example.aalarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private NavigationBarView bottomNavigationView;

    public static final String CHANNEL_1_ID = "CHANNEL1";
    public static final String CHANNEL_2_ID = "CHANNEL2";
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setOnItemSelectedListener(bottomnavFunction);

        notificationManager = NotificationManagerCompat.from(this);


        //intent from other activity, to load previous fragment
        if(getIntent().getExtras() != null) {
            int intentFragment = getIntent().getExtras().getInt("frgToLoad");
            BottomNavigationView bottomNavigationView;
            Fragment fragment = null;
            switch (intentFragment) {
                case R.id.alarm:
                    fragment = new AlarmFragment();
                    bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnav);
                    bottomNavigationView.setSelectedItemId(R.id.alarm);
                    break;
                case R.id.plan:
                    fragment = new PlanFragment();
                    bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnav);
                    bottomNavigationView.setSelectedItemId(R.id.plan);
                    break;
                case R.id.personal:
                    fragment = new PersonFragment();
                    bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnav);
                    bottomNavigationView.setSelectedItemId(R.id.personal);
                    break;
                default:
                    fragment = new PersonFragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new AlarmFragment()).commit();
        }

        Timer timer = new Timer ();
        TimerTask hourlyTask = new TimerTask () {
            @Override
            public void run () {
                // your code here...
                ArrayList<UserActivity> userAActivity = new ArrayList<>();
                Date currentTime = Calendar.getInstance().getTime();
                int year = currentTime.getYear();
                int month = currentTime.getMonth();
                int day = currentTime.getDay();
                int hour = currentTime.getHours();
                int minute = currentTime.getMinutes();

                // 1. Get SQLiteDatabase instance
                Context context = (MainActivity.this).getApplicationContext();
                SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("alarms", Context.MODE_PRIVATE, null);
                // 2. Initialize the alarms class variable
                DBHelper dbHelper = new DBHelper(sqLiteDatabase);
                userAActivity = dbHelper.readUserActivity();
                if (userAActivity.size() == 0) return;
                // push notification
                for (UserActivity ua : userAActivity) {
                    String name = ua.getName();
                    int frequency = ua.getFrequency();
                    int count = dbHelper.getRecordDailyCount(year, month, day, name);
                    if(count < frequency){
                        String message = String.format("Finish %s today! %s more times to go!", name, frequency - count);
                        SendOnChannel1(message);
                    }
                }
                Log.v("", "timertask running: " + currentTime);
            }
        };

        // schedule the task to run starting now and then every hour...
        timer.schedule (hourlyTask, 1000*1*10, 1000*1*10);


    }

    public void onAddAlarm(View view) {
        goToAddAlarmActivity();
    }

    public void goToAddAlarmActivity() {
        Intent intent = new Intent(this, AddAlarmActivity.class);
        startActivity(intent);
    }

    private NavigationBarView.OnItemSelectedListener bottomnavFunction = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()){
                case R.id.alarm:
                    fragment = new AlarmFragment();
                    break;
                case R.id.plan:
                    fragment = new PlanFragment();
                    break;
                case R.id.personal:
                    fragment = new PersonFragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            return true;
        }
    };

    public void clickAddRecord(View view){
        Intent intent = new Intent(this, AddRecord.class);
        startActivity(intent);
    }


    public void SendOnChannel1(String message){
        String title = "Activity Reminder";

        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                activityIntent, 0);

        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
        broadcastIntent.putExtra("toastMessage", message);
        PendingIntent actionIntent = PendingIntent.getBroadcast(this, 0,
                broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.apple)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
                .build();

        notificationManager.notify(1, notification);

    }
}