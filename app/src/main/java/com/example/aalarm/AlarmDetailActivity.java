package com.example.aalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AlarmDetailActivity extends AppCompatActivity {

    Alarm alarm;
    int alarmId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_detail);

        TextView alarmDetailTitle = (TextView) findViewById(R.id.alarmDetailTitle);
        Intent intent = getIntent();
        alarmId = intent.getIntExtra("alarmid", alarmId);

        if (alarmId != -1) {
            alarm = AlarmFragment.alarms.get(alarmId);
            String alarmContent = String.format("Alarm set for: %s:%s on %s-%s-%s", alarm.getHour(), alarm.getMinute(), alarm.getYear(), alarm.getMonth() + 1, alarm.getDay());
            alarmDetailTitle.setText(alarmContent);
        }

    }

    public void onRemoveAlarm(View v) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, alarm.getRequestCode(), alarmIntent, 0);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(getApplicationContext(), "Alarm is Canceled", Toast.LENGTH_SHORT).show();

        MediaPlayer mp = AlarmReceiver.getMediaPlayer();
        if (mp != null) {
            AlarmReceiver.getMediaPlayer().stop();
        }

        removeAlarmFromDb();
    }

    private void removeAlarmFromDb() {
        // 1. Initialize SQLiteDatabase instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("alarms", Context.MODE_PRIVATE, null);

        // 2. Initialize DBHelper class
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        // 3. Save information to database
        if (alarm != null) {
            boolean removeStatus = dbHelper.removeAlarm(alarm.getYear(), alarm.getMonth(), alarm.getDay(), alarm.getHour(), alarm.getMinute());
            if (!removeStatus) {
                Toast.makeText(getApplicationContext(), "Delete failed. Try again later.", Toast.LENGTH_SHORT).show();
            }
        }

        // 4. Go to main activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}