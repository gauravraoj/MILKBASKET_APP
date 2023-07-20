package com.example.myapplication.Model;

public class PayHistoryModel {

    public PayHistoryModel(int amount, String date) {
        this.amount = amount;
        this.date = date;
    }

    public PayHistoryModel() {
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int amount;
    public String date;
}
