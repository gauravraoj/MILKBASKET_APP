package com.example.myapplication.Model;

public class HomeModel {
    public HomeModel() {
    }

    public HomeModel(String name, String address, String phone, String locality, String plotno, String cattle, int currentdue, int cattlehalflitprice, int cattlelitprice, String latitude, String longitude, String date, String city, String state, String halflitqty, String litqty) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.locality = locality;
        this.plotno = plotno;
        this.cattle = cattle;
        this.currentdue = currentdue;
        this.cattlehalflitprice = cattlehalflitprice;
        this.cattlelitprice = cattlelitprice;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.city = city;
        this.state = state;
        this.halflitqty = halflitqty;
        this.litqty = litqty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getPlotno() {
        return plotno;
    }

    public void setPlotno(String plotno) {
        this.plotno = plotno;
    }

    public String getCattle() {
        return cattle;
    }

    public void setCattle(String cattle) {
        this.cattle = cattle;
    }

    public int getCurrentdue() {
        return currentdue;
    }

    public void setCurrentdue(int currentdue) {
        this.currentdue = currentdue;
    }

    public int getCattlehalflitprice() {
        return cattlehalflitprice;
    }

    public void setCattlehalflitprice(int cattlehalflitprice) {
        this.cattlehalflitprice = cattlehalflitprice;
    }

    public int getCattlelitprice() {
        return cattlelitprice;
    }

    public void setCattlelitprice(int cattlelitprice) {
        this.cattlelitprice = cattlelitprice;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String name;
   public String address;
   public String phone;
   public String locality;
   public String plotno;
   public String cattle;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getHalflitqty() {
        return halflitqty;
    }

    public void setHalflitqty(String halflitqty) {
        this.halflitqty = halflitqty;
    }

    public String getLitqty() {
        return litqty;
    }

    public void setLitqty(String litqty) {
        this.litqty = litqty;
    }

    public String datepay;
    public int currentdue;
    public int cattlehalflitprice;
    public int cattlelitprice;
    public String latitude;
    public String longitude;
    public String date;
    public String city;
    public String state;
    public String halflitqty;
    public String litqty;


    public String getDatepay() {
        return datepay;
    }

    public void setDatepay(String datepay) {
        this.datepay = datepay;
    }
}
