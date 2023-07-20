package com.example.myapplication.SubActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Common.FirebaseInitilization;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ClientPayActivity extends AppCompatActivity {

    EditText cpetpay;
    Button cpbtn;
    String clientphone;
    int clientdue;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_pay);

        if (getIntent() != null) {
            clientphone = getIntent().getStringExtra("clientPhone");
            clientdue = getIntent().getIntExtra("clientdue",0);
        }
        cpetpay = (EditText) findViewById(R.id.cprecpay);
        cpbtn = (Button) findViewById(R.id.cpbutton);


        uid = FirebaseAuth.getInstance().getUid();

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        final String formattedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

      final  FirebaseInitilization firebaseInitilization = new FirebaseInitilization();


        cpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(cpetpay.getText().toString()) <= clientdue) {
                    firebaseInitilization.amountPaid.child(uid).child(clientphone).child(formattedDate).child("amount").setValue(Integer.parseInt(cpetpay.getText().toString()));
                    firebaseInitilization.amountPaid.child(uid).child(clientphone).child(formattedDate).child("date").setValue(formattedDate);
                    firebaseInitilization.users.child(uid).child(clientphone).child("currentdue").setValue(clientdue - Integer.parseInt(cpetpay.getText().toString()));
//                    Intent intent = new Intent(ClientPayActivity.this,ClientPayHistoryActivity.class);
//                    intent.putExtra("clientPhone",clientphone);
//                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(ClientPayActivity.this, "Please check your input", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}
