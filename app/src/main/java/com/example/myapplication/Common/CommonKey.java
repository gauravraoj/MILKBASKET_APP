package com.example.myapplication.Common;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;

public class CommonKey {

    public String sharedKey = "managet";
    String uid;
    public static String key;

    public CommonKey() {
    }



    public String uidnull(Context context) {

        if (key == null) {
            uid = key;

        } else {
            if (context == null) {

                uid = FirebaseAuth.getInstance().getUid();

            } else {
                SharedPreferences prefs = context.getSharedPreferences(sharedKey, 0);


                String restoredText = prefs.getString("uid", null);
                if (restoredText != null) {
                    uid = prefs.getString("uid", "No name defined");//"No name defined" is the default value.
                }


            }
        }
return uid;
    }



    }





