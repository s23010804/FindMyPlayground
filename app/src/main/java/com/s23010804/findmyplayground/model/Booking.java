package com.s23010804.findmyplayground.model;

public class Booking {
    private String pgName;
    private String date;
    private String time;

    public Booking(String pgName, String date, String time) {
        this.pgName = pgName;
        this.date = date;
        this.time = time;
    }

    public String getPgName() {
        return pgName;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
