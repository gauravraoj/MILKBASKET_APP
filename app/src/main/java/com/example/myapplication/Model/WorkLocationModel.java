package com.example.myapplication.Model;

public class WorkLocationModel {
    public String title;
    public String phone;
    public String key;

    public WorkLocationModel(String title, String phone, String key) {
        this.title = title;
        this.phone = phone;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public WorkLocationModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
