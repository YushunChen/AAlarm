package com.example.aalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class GameMath extends AppCompatActivity {

    static int answer;
    private int requestCode;
    private Alarm alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_math);

        TextView content = (TextView) findViewById(R.id.hintField);
        //String contentStr = content.getText().toString();

        final int min = 90;
        final int max = 900;
        final int random1 = new Random().nextInt((max - min) + 1) + min;
        final int random2 = new Random().nextInt((max - min) + 1) + min;
        final int random3 = new Random().nextInt((max - min) + 1) + min;

        String number1 = String.valueOf(random1);
        String number2 = String.valueOf(random2);
        String number3 = String.valueOf(random3);

        answer = random1 + random2 + random3;
        content.setText(number1 + "+" + number2 + "+" + number3);

    }
    public void clickFunction(View view){
        //get rewrite content
        EditText rewriteContent = (EditText) findViewById(R.id.answerField);

        //compair content
        if(rewriteContent.getText().toString().length() <= 0 ||
                !rewriteContent.getText().toString().matches("^[0-9]+$") ||
                Integer.parseInt(rewriteContent.getText().toString()) != answer){
            Toast.makeText(GameMath.this, "Please check the text you entered", Toast.LENGTH_LONG).show();
        }else{
            // start activity
            expireAlarm();
            goToActivityMain();
        }
    }

    public void expireAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, alarmIntent, 0);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(getApplicationContext(), "Alarm is Canceled", Toast.LENGTH_SHORT).show();

        MediaPlayer mp = AlarmReceiver.getMediaPlayer();
        if (mp != null) {
            AlarmReceiver.getMediaPlayer().stop();
        }

        expireAlarmFromDb();
    }

    private void expireAlarmFromDb() {
        // 1. Initialize SQLiteDatabase instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("alarms", Context.MODE_PRIVATE, null);

        // 2. Initialize DBHelper class
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        // 3. Find the expired alarm and delete it from db
        ArrayList<Alarm> alarms = dbHelper.readAlarms();
        for (Alarm a : alarms) {
            if (a.getRequestCode() == requestCode) {
                alarm = a;
            }
        }
        if (alarm != null) {
            boolean removeStatus = dbHelper.removeAlarm(alarm.getYear(), alarm.getMonth(), alarm.getDay(), alarm.getHour(), alarm.getMinute());
            if (!removeStatus) {
                Toast.makeText(getApplicationContext(), "Delete failed. Try again later.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void goToActivityMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
