package com.example.aalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GameShakePhone extends AppCompatActivity implements SensorEventListener {

    private TextView x_txt, y_txt, z_txt, message;
    // define the sensor variables
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private boolean isAccelerometerSensorAvailable, isNotFirstTime = false;
    private float currentX, currentY, currentZ, lastX, lastY, lastZ;
    private float xDiff, yDiff, zDiff;
    private float shakeThreshold = 10f;

    private String answer;
    private int requestCode;
    private Alarm alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_shakephone);

        x_txt = findViewById(R.id.txt_x);
        y_txt = findViewById(R.id.txt_y);
        z_txt = findViewById(R.id.txt_z);
        message = findViewById(R.id.message);

        // initialize sensor objects
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        if(mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isAccelerometerSensorAvailable = true;
        }else{
            x_txt.setText("Accelerometer sensor is not available");
            isAccelerometerSensorAvailable = false;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(isAccelerometerSensorAvailable)
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();

        if(isAccelerometerSensorAvailable)
            mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //x_txt.setText(sensorEvent.values[0] + "m/s2");
        //y_txt.setText(sensorEvent.values[1] + "m/s2");
        //z_txt.setText(sensorEvent.values[2] + "m/s2");

        currentX = sensorEvent.values[0];
        currentY = sensorEvent.values[1];
        currentZ = sensorEvent.values[2];

        if(isNotFirstTime){
            xDiff = Math.abs(lastX - currentX);
            yDiff = Math.abs(lastY - currentY);
            zDiff = Math.abs(lastZ - currentZ);



            if((xDiff > shakeThreshold && yDiff > shakeThreshold) ||
                    (xDiff > shakeThreshold && zDiff > shakeThreshold) ||
                    (yDiff > shakeThreshold && zDiff > shakeThreshold)){
                // start activity
                expireAlarm();
                goToActivityMain();
            }
        }

        lastX = currentX;
        lastY = currentY;
        lastZ = currentZ;
        isNotFirstTime = true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

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
