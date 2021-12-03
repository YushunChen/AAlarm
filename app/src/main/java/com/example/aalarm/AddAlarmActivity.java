package com.example.aalarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddAlarmActivity extends AppCompatActivity {

    private static Alarm alarm;
    int requestCode = 0;

    private TextView gameChosenText;
    String game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        gameChosenText = (TextView) findViewById(R.id.gameChosenText);
        Intent intent = getIntent();
        game = intent.getStringExtra("game");
        if (game != null) {
            gameChosenText.setText("Your chosen game: " + game);
        } else {
            gameChosenText.setText("Please select a game below.");
        }

    }

    public static class  TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Log.i("info", String.valueOf(hourOfDay));
            Log.i("info", String.valueOf(minute));
            if (alarm != null) {
                alarm.setHour(hourOfDay);
                alarm.setMinute(minute);
            } else {
                alarm = new Alarm(hourOfDay, minute);
            }
        }
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            Log.i("info", String.valueOf(year));
            Log.i("info", String.valueOf(month));
            Log.i("info", String.valueOf(day));
            if (alarm != null) {
                alarm.setYear(year);
                alarm.setMonth(month);
                alarm.setDay(day);
            } else {
                alarm = new Alarm(year, month, day);
            }
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void onChooseGame(View v) {
        Intent intent = new Intent(this, ChooseGameActivity.class);
        startActivity(intent);
    }

    public void onUserAddAlarm(View v) {

        Calendar calendar = Calendar.getInstance();
        int year, month, day, hour, minute;

        // check for empty user input and initialize alarm variables
        if (alarm != null) {
            year = alarm.getYear();
            month = alarm.getMonth();
            day = alarm.getDay();
            hour = alarm.getHour();
            minute = alarm.getMinute();
        } else {
            Toast.makeText(getApplicationContext(), "Please pick your date and time!", Toast.LENGTH_SHORT).show();
            return;
        }

        // check for full user input with both date and time
        if (year != -1 && month != -1 && day != -1 && hour != -1 && minute != -1) {
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);

            // check for expired dates
            Calendar currCalendar = Calendar.getInstance();
            if (currCalendar.getTime().after(calendar.getTime())) {
                Toast.makeText(getApplicationContext(), "You cannot live in the past!", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please pick your date and time!", Toast.LENGTH_SHORT).show();
            return;
        }

        addUserAlarmToDb();
        setUserAlarm(calendar, requestCode);
        alarm.setRequestCode(requestCode++);
    }


    private void setUserAlarm(Calendar calendar, int rCode) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        // pass game to the alarm when it rings
        alarmIntent.putExtra("game", alarm.getGame());
        alarmIntent.putExtra("requestCode", rCode);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, rCode, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Toast.makeText(getApplicationContext(), "Alarm is Set", Toast.LENGTH_SHORT).show();
    }


    private void addUserAlarmToDb() {

        // 1. Initialize SQLiteDatabase instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("alarms", Context.MODE_PRIVATE, null);

        // 2. Initialize DBHelper class
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        // 3. Save information to database
        if (game != null) {
            alarm.setGame(game);
        } else {
            alarm.setGame("no game");
        }
        if (alarm != null) {
            dbHelper.saveAlarms(alarm.getYear(), alarm.getMonth(), alarm.getDay(), alarm.getHour(), alarm.getMinute(), alarm.getGame());
        }

        // 4. Go to main activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // TODO: temporal method to stop alarm
    public void onCancelAlarm(View v) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(getApplicationContext(), "Alarm is Canceled", Toast.LENGTH_SHORT).show();

        MediaPlayer mp = AlarmReceiver.getMediaPlayer();
        if (mp != null) {
            AlarmReceiver.getMediaPlayer().stop();
        }
    }

}