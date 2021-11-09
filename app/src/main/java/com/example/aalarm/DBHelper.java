package com.example.aalarm;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBHelper {

    SQLiteDatabase sqLiteDatabase;

    public DBHelper(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    public void createTable() {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS alarms " +
                "(id INTEGER PRIMARY KEY, year INTEGER, month INTEGER, day INTEGER, hour INTEGER, minute INTEGER, game TEXT)");
    }

    public ArrayList<Alarm> readAlarms() {
        createTable();
        Cursor c = sqLiteDatabase.rawQuery(String.format("SELECT * FROM alarms"), null);

        int yearIndex = c.getColumnIndex("year");
        int monthIndex = c.getColumnIndex(("month"));
        int dayIndex = c.getColumnIndex("day");
        int hourIndex = c.getColumnIndex("hour");
        int minuteIndex = c.getColumnIndex("minute");

        c.moveToFirst();

        ArrayList<Alarm> alarmsList = new ArrayList<>();

        while (!c.isAfterLast()) {

            int year = c.getInt(yearIndex);
            int month = c.getInt(monthIndex);
            int day = c.getInt(dayIndex);
            int hour = c.getInt(hourIndex);
            int minute = c.getInt(minuteIndex);

            Alarm alarm = new Alarm(year, month, day, hour, minute);
            alarmsList.add(alarm);
            c.moveToNext();
        }
        c.close();
        sqLiteDatabase.close();

        return alarmsList;
    }

    public void saveAlarms(int year, int month, int day, int hour, int minute) {
        createTable();
        sqLiteDatabase.execSQL(String.format("INSERT INTO alarms (year, month, day, hour, minute) VALUES ('%s', '%s', '%s', '%s', '%s')",
                year, month, day, hour, minute));
    }

    public boolean removeAlarm(int year, int month, int day, int hour, int minute) {
        createTable();
        int deleteStatus = sqLiteDatabase.delete("alarms","year=? AND month=? AND day=? AND hour=? AND minute=?",
                new String[]{String.valueOf(year), String.valueOf(month), String.valueOf(day), String.valueOf(hour), String.valueOf(minute)});
        return deleteStatus > 0;
    }

//    public void updateAlarm(String title, String date, String content, String username) {
//        createTable();
//        sqLiteDatabase.execSQL(String.format("UPDATE notes SET content = '%s', date = '%s' WHERE title = '%s' AND username = '%s'",
//                content, date, title, username));
//    }
}
