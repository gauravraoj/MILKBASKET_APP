package com.example.myapplication.Model;

public class VendorGlobalPriceModel {


    public VendorGlobalPriceModel(String buffaloLitPrice, String buffalohalfLitPrice, String cowLitPrice, String cowhalfLitPrice) {
        BuffaloLitPrice = buffaloLitPrice;
        BuffalohalfLitPrice = buffalohalfLitPrice;
        CowLitPrice = cowLitPrice;
        CowhalfLitPrice = cowhalfLitPrice;
    }

    public String getBuffaloLitPrice() {

        return BuffaloLitPrice;
    }

    public void setBuffaloLitPrice(String buffaloLitPrice) {
        BuffaloLitPrice = buffaloLitPrice;
    }

    public String getBuffalohalfLitPrice() {
        return BuffalohalfLitPrice;
    }

    public void setBuffalohalfLitPrice(String buffalohalfLitPrice) {
        BuffalohalfLitPrice = buffalohalfLitPrice;
    }

    public String getCowLitPrice() {
        return CowLitPrice;
    }

    public void setCowLitPrice(String cowLitPrice) {
        CowLitPrice = cowLitPrice;
    }

    public String getCowhalfLitPrice() {
        return CowhalfLitPrice;
    }

    public void setCowhalfLitPrice(String cowhalfLitPrice) {
        CowhalfLitPrice = cowhalfLitPrice;
    }

    private String BuffaloLitPrice;
    private String BuffalohalfLitPrice;
    private String CowLitPrice;
    private String CowhalfLitPrice;

    public VendorGlobalPriceModel() {
    }


}
