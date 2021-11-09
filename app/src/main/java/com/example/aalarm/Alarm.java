package com.example.aalarm;

public class Alarm {
    private int year = -1;
    private int month = -1;
    private int day = -1;
    private int hour = -1;
    private int minute = -1;
    private String game;

    public Alarm(int year, int month, int day, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public Alarm(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Alarm(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public void setYear(int year) {
        this.year = year;
    }
    public int getYear() {
        return year;
    }

    public void setMonth(int month) {
        this.month = month;
    }
    public int getMonth() {
        return month;
    }

    public void setDay(int day) {
        this.day = day;
    }
    public int getDay() {
        return day;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
    public int getHour() {
        return hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
    public int getMinute() {
        return minute;
    }

    public void setGame(String game) {
        this.game = game;
    }
    public String getGame() {
        return game;
    }
}
