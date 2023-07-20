package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Common.CommonKey;
import com.example.myapplication.Common.FirebaseInitilization;
import com.example.myapplication.OwnerPkg.WorkLocationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class OwnerActivity extends AppCompatActivity {

    EditText on,opn,oea,ocmp,obmp,ocmph,obmph;
    Button os;
    String uid,phno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);

        on = (EditText) findViewById(R.id.on);
        opn = (EditText) findViewById(R.id.oph);
        opn.setEnabled(false);
        oea = (EditText) findViewById(R.id.oea);
        ocmp = (EditText) findViewById(R.id.ocmp);
        obmp = (EditText) findViewById(R.id.obmp);
        ocmph = (EditText) findViewById(R.id.ocmph);
        obmph = (EditText) findViewById(R.id.obmph);
        os = (Button) findViewById(R.id.os);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            phno = bundle.getString("phno");
            uid  = bundle.getString("autid");
        }
        CommonKey commonKey = new CommonKey();
            uid = commonKey.uidnull(OwnerActivity.this);



        opn.setText(phno);


        FirebaseInitilization firebaseInitilization = new FirebaseInitilization();
        firebaseInitilization.owner.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("General").exists() && dataSnapshot.child("GlobalPrice").exists()  && dataSnapshot.child("WorkPlace").exists() ) {
                    //do ur stuff
                    Intent intent = new Intent(OwnerActivity.this,MainActivity.class);
                    intent.putExtra("phno",opn.getText().toString());
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        os.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseInitilization firebaseInitilization = new FirebaseInitilization();

                if(on.getText().toString().matches("") &&
                        opn.getText().toString().matches("") && oea.getText().toString().matches("") &&
                        ocmp.getText().toString().matches("") && obmp.getText().toString().matches("") && ocmph.getText().toString().matches("") && obmph.getText().toString().matches("") && uid.isEmpty()){
                    Toast.makeText(OwnerActivity.this, "Please Fill All Details", Toast.LENGTH_SHORT).show();
                }else {

                    uid = FirebaseAuth.getInstance().getUid();

                    firebaseInitilization.owner.child(uid).child("General").child("name").setValue(on.getText().toString());
                    firebaseInitilization.owner.child(uid).child("General").child("phone").setValue(opn.getText().toString());
                    firebaseInitilization.owner.child(uid).child("General").child("email").setValue(oea.getText().toString());
                    firebaseInitilization.owner.child(uid).child("General").child("uid").setValue(uid);

                    firebaseInitilization.owner.child(uid).child("GlobalPrice").child("CowLitPrice").setValue(ocmp.getText().toString());
                    firebaseInitilization.owner.child(uid).child("GlobalPrice").child("BuffaloLitPrice").setValue(obmp.getText().toString());
                    firebaseInitilization.owner.child(uid).child("GlobalPrice").child("CowhalfLitPrice").setValue(ocmph.getText().toString());
                    firebaseInitilization.owner.child(uid).child("GlobalPrice").child("BuffalohalfLitPrice").setValue(obmph.getText().toString());

                    Intent intent = new Intent(OwnerActivity.this,WorkLocationActivity.class);
                    intent.putExtra("phno",opn.getText().toString());
                    startActivity(intent);

                }
            }
        });
    }
}
