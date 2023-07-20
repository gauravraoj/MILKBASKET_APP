package com.example.myapplication.Model;

public class CalendardaysModel {
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEvening() {
        return evening;
    }

    public void setEvening(String evening) {
        this.evening = evening;
    }

    public String getMorning() {
        return morning;
    }

    public void setMorning(String morning) {
        this.morning = morning;
    }

    public CalendardaysModel(String date, String evening, String morning, boolean milktaken) {
        this.date = date;
        this.evening = evening;
        this.morning = morning;
        this.milktaken = milktaken;
    }

    public CalendardaysModel() {
    }

    public boolean isMilktaken() {
        return milktaken;
    }

    public void setMilktaken(boolean milktaken) {
        this.milktaken = milktaken;
    }

    public String date;
    public String evening;
    public String morning;
    public boolean milktaken;

}
