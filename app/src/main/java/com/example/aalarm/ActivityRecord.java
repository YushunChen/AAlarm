package com.example.aalarm;

public class ActivityRecord {
    private int year = -1;
    private int month = -1;
    private int day = -1;
    private int hour = -1;
    private int minute = -1;
    private String activityName;

    public ActivityRecord(int year, int month, int day, int hour, int minute, String activityName) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.activityName = activityName;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getActivityName() {
        return this.activityName;
    }
}
