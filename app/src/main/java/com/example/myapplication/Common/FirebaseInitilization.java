package com.example.myapplication.Common;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseInitilization {



    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference users = database.getReference("User");
    public DatabaseReference home = database.getReference("dayRs");
    public DatabaseReference amount = database.getReference("Amount");
    public DatabaseReference amountPaid = database.getReference("amountPaid");
    public DatabaseReference owner = database.getReference("MilkVendor");
    public DatabaseReference city = database.getReference("City").child("141");
    public DatabaseReference dayRs = database.getReference("dayRs");
    public DatabaseReference upaf = database.getReference("upaf"); /* update after finish **/


}
