package com.example.aalarm;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

    public void createTableUserActivity() {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS user_activity " +
                "(name TEXT PRIMARY KEY, frequency INTEGER, monday BOOLEAN DEFAULT(FALSE), tuesday BOOLEAN DEFAULT(FALSE), wednesday BOOLEAN DEFAULT(FALSE), " +
                "thursday BOOLEAN DEFAULT(FALSE), friday, BOOLEAN DEFAULT(FALSE), saturday BOOLEAN DEFAULT(FALSE), sunday BOOLEAN DEFAULT(FALSE))");
    }

    public void createTableActivityRecord() {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS activity_record " +
                "(activity_record_id INTEGER PRIMARY KEY, year INTEGER, month INTEGER, day INTEGER, hour INTEGER, minute INTEGER, name TEXT)");
    }

    public ArrayList<Alarm> readAlarms() {
        createTable();
        Cursor c = sqLiteDatabase.rawQuery(String.format("SELECT * FROM alarms"), null);

        int yearIndex = c.getColumnIndex("year");
        int monthIndex = c.getColumnIndex(("month"));
        int dayIndex = c.getColumnIndex("day");
        int hourIndex = c.getColumnIndex("hour");
        int minuteIndex = c.getColumnIndex("minute");
        int gameIndex = c.getColumnIndex("game");

        c.moveToFirst();

        ArrayList<Alarm> alarmsList = new ArrayList<>();

        while (!c.isAfterLast()) {

            int year = c.getInt(yearIndex);
            int month = c.getInt(monthIndex);
            int day = c.getInt(dayIndex);
            int hour = c.getInt(hourIndex);
            int minute = c.getInt(minuteIndex);
            String game = c.getString(gameIndex);

            Alarm alarm = new Alarm(year, month, day, hour, minute, game);
            alarmsList.add(alarm);
            c.moveToNext();
        }
        c.close();
//        sqLiteDatabase.close();

        return alarmsList;
    }

    public void saveAlarms(int year, int month, int day, int hour, int minute, String game) {
        createTable();
        sqLiteDatabase.execSQL(String.format("INSERT INTO alarms (year, month, day, hour, minute, game) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
                year, month, day, hour, minute, game));
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

    public ArrayList<UserActivity> readUserActivity() {
        createTableUserActivity();
        Cursor c = sqLiteDatabase.rawQuery(String.format("SELECT * FROM user_activity"), null);

        //        name TEXT, importance TEXT, monday BOOLEAN DEFAULT(FALSE), tuesday BOOLEAN DEFAULT(FALSE), wednesday BOOLEAN DEFAULT(FALSE), " +
//        "thursday BOOLEAN DEFAULT(FALSE), friday, BOOLEAN DEFAULT(FALSE), saturday BOOLEAN DEFAULT(FALSE), sunday BOOLEAN DEFAULT(FALSE)

        int nameIndex = c.getColumnIndex("name");
        int frequencyIndex = c.getColumnIndex("frequency");
        int mondayIndex = c.getColumnIndex("monday");
        int tuesdayIndex = c.getColumnIndex("tuesday");
        int wednesdayIndex = c.getColumnIndex("wednesday");
        int thursdayIndex = c.getColumnIndex("thursday");
        int fridayIndex = c.getColumnIndex("friday");
        int saturdayIndex = c.getColumnIndex("saturday");
        int sundayIndex = c.getColumnIndex("sunday");

        c.moveToFirst();

        ArrayList<UserActivity> activityList = new ArrayList<>();

        while (!c.isAfterLast()) {
            String name = c.getString(nameIndex);
            int frequency = c.getInt(frequencyIndex);
            boolean monday = c.getInt(mondayIndex) > 0;
            boolean tuesday = c.getInt(tuesdayIndex) > 0;
            boolean wednesday = c.getInt(wednesdayIndex) > 0;
            boolean thursday = c.getInt(thursdayIndex) > 0;
            boolean friday = c.getInt(fridayIndex) > 0;
            boolean saturday = c.getInt(saturdayIndex) > 0;
            boolean sunday = c.getInt(sundayIndex) > 0;

            UserActivity ua = new UserActivity(name, frequency, monday, tuesday,
                    wednesday, thursday, friday, saturday, sunday);

            activityList.add(ua);
            c.moveToNext();
        }
        c.close();
//        sqLiteDatabase.close();

        return activityList;
    }

    public ArrayList<ActivityRecord> readActivityRecord() {
        createTableActivityRecord();
        Cursor c = sqLiteDatabase.rawQuery(String.format("SELECT * FROM activity_record"), null);

        int yearIndex = c.getColumnIndex("year");
        int monthIndex = c.getColumnIndex(("month"));
        int dayIndex = c.getColumnIndex("day");
        int hourIndex = c.getColumnIndex("hour");
        int minuteIndex = c.getColumnIndex("minute");
        int activityIndex = c.getColumnIndex("name");

        c.moveToFirst();

        ArrayList<ActivityRecord> recordList = new ArrayList<>();

        while (!c.isAfterLast()) {

            int year = c.getInt(yearIndex);
            int month = c.getInt(monthIndex);
            int day = c.getInt(dayIndex);
            int hour = c.getInt(hourIndex);
            int minute = c.getInt(minuteIndex);
            String name = c.getString(activityIndex);

            ActivityRecord ar = new ActivityRecord(year, month, day, hour, minute, name);
            recordList.add(ar);
            c.moveToNext();
        }
        c.close();
//        sqLiteDatabase.close();


        return recordList;
    }

    public boolean saveUserActivity(String name, int frequency, boolean monday, boolean tuesday,
                                    boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday) {
        createTableUserActivity();
        Cursor c = sqLiteDatabase.rawQuery(String.format("SELECT * FROM user_activity WHERE name = '%s'", name), null);
        if(c.moveToFirst()) {
            //record exists
            return false;
        } else {
            // Inserting record
            sqLiteDatabase.execSQL(String.format("INSERT INTO user_activity (name, frequency, monday, tuesday, " +
                            "wednesday, thursday, friday, saturday, sunday) VALUES ('%s', '%s', %b, " +
                            "%b, %b, %b, %b, %b, %b)",name, frequency, monday, tuesday, wednesday, thursday, friday, saturday, sunday));
        }

        return true;
    }

    public void saveActivityRecord(int year, int month, int day, int hour, int minute, String name) {
        createTableActivityRecord();
        sqLiteDatabase.execSQL(String.format("INSERT INTO activity_record (year, month, day, hour, minute, name) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
                year, month, day, hour, minute, name));
    }


    public void updateUserActivity(String name, int frequency, boolean monday, boolean tuesday,
                                   boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday) {
        createTableUserActivity();
        sqLiteDatabase.execSQL(String.format("UPDATE user_activity SET frequency = '%s', monday = %b, " +
                        "tuesday = %b, wednesday = %b, thursday = %b, friday = %b, saturday = %b, sunday = %b WHERE name = '%s'",
                frequency, monday, tuesday, wednesday, thursday, friday, saturday, sunday, name));
    }

    public boolean removeUserActivity(String name) {
        createTableUserActivity();
        int deleteStatus = sqLiteDatabase.delete("user_activity","name=?", new String[]{name});
        return deleteStatus > 0;
    }

    public int getRecordDailyCount(int year, int month, int day, String name){
        createTableActivityRecord();
        Cursor c = sqLiteDatabase.rawQuery(String.format("SELECT * FROM activity_record WHERE year = '%s' AND month = '%s' AND day = '%s' AND name = '%s'", year, month, day, name), null);

        int count = 0;
        if(c.moveToFirst()) {
            while (!c.isAfterLast()) {
               count++;
               c.moveToNext();
            }
            c.close();
        } else {
            return 0;
        }

        return count;

    }

}
