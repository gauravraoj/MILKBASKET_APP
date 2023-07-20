package com.example.myapplication.OTPvarification;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Common.CommonKey;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.CountryData;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

public class OTOvarificationActivity extends AppCompatActivity {

    SharedPreferences.Editor editor;
    private Spinner spinner;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otovarification);


        CommonKey commonKey = new CommonKey();

        editor = getSharedPreferences(commonKey.sharedKey, MODE_PRIVATE).edit();

        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        editText = findViewById(R.id.editTextPhone);

        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];

                String number = editText.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                    editText.setError("Valid number is required");
                    editText.requestFocus();
                    return;
                }

                String code = "+91";

                String phoneNumber =  code + number;

                Intent intent = new Intent(OTOvarificationActivity.this, VerifyPhoneActivity.class);
                intent.putExtra("phonenumber", phoneNumber);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            editor.putString("uid", FirebaseAuth.getInstance().getUid());
            editor.apply();
            CommonKey.key  = FirebaseAuth.getInstance().getUid();
            intent.putExtra("uid", FirebaseAuth.getInstance().getUid());

            startActivity(intent);
        }
    }
}
