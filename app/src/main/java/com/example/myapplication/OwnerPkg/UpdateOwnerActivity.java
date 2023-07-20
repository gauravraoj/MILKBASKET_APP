package com.example.myapplication.OwnerPkg;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Common.CommonKey;
import com.example.myapplication.Common.FirebaseInitilization;
import com.example.myapplication.Model.VendorGeneralModel;
import com.example.myapplication.Model.VendorGlobalPriceModel;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class UpdateOwnerActivity extends AppCompatActivity {

    EditText uon,uopn,uoea,uocmp,uobmp,uocmph,uobmph;
    Button uos;
    String uid,phno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_owner);


        uon = (EditText) findViewById(R.id.uon);
        uopn = (EditText) findViewById(R.id.uoph);
        uopn.setEnabled(false);
        uoea = (EditText) findViewById(R.id.uoea);
        uocmp = (EditText) findViewById(R.id.uocmp);
        uobmp = (EditText) findViewById(R.id.uobmp);
        uocmph = (EditText) findViewById(R.id.uocmph);
        uobmph = (EditText) findViewById(R.id.uobmph);
        uos = (Button) findViewById(R.id.uos);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            phno = bundle.getString("phno");

        }
        uopn.setText(phno);


        CommonKey commonKey = new CommonKey();
            uid = commonKey.uidnull(this);




        FirebaseInitilization firebaseInitilization = new FirebaseInitilization();
        firebaseInitilization.owner.child(uid).child("General").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                VendorGeneralModel vendorGeneralModel = dataSnapshot.getValue(VendorGeneralModel.class);
                uon.setText(String.valueOf(vendorGeneralModel.getName()));
                uopn.setText(String.valueOf(vendorGeneralModel.getPhone()));
                uoea.setText(String.valueOf(vendorGeneralModel.getEmail()));


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        firebaseInitilization.owner.child(uid).child("GlobalPrice").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                VendorGlobalPriceModel vendorGlobalPriceModel = dataSnapshot.getValue(VendorGlobalPriceModel.class);
                uocmp.setText(String.valueOf(vendorGlobalPriceModel.getCowLitPrice()));
                uocmph.setText(String.valueOf(vendorGlobalPriceModel.getCowhalfLitPrice()));
                uobmp.setText(String.valueOf(vendorGlobalPriceModel.getBuffaloLitPrice()));
                uobmph.setText(String.valueOf(vendorGlobalPriceModel.getBuffalohalfLitPrice()));


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        uos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseInitilization firebaseInitilization = new FirebaseInitilization();

                if(uon.getText().toString().matches("") &&
                        uopn.getText().toString().matches("") && uoea.getText().toString().matches("") &&
                        uocmp.getText().toString().matches("") && uobmp.getText().toString().matches("") && uocmph.getText().toString().matches("") && uobmph.getText().toString().matches("") && uid.isEmpty()){
                    Toast.makeText(UpdateOwnerActivity.this, "Please Fill All Details", Toast.LENGTH_SHORT).show();
                }else {


//                    CommonKey commonKey = new CommonKey();
//
//                        uid = commonKey.uidnull(UpdateOwnerActivity.this);
////            Toast.makeText(getActivity(),"null " + commonKey.uidnull(getActivity()),Toast.LENGTH_SHORT).show();




                    if (uon.getText().toString().matches("") && uopn.getText().toString().matches("") && uoea.getText().toString().matches("") && uocmp.getText().toString().matches("")
                            && uobmp.getText().toString().matches("") && uocmph.getText().toString().matches("") && uobmph.getText().toString().matches("")) {

                        Toast.makeText(UpdateOwnerActivity.this, "Please Fill All The Field", Toast.LENGTH_SHORT).show();

                    } else {

                        firebaseInitilization.owner.child(uid).child("General").child("name").setValue(uon.getText().toString());
                        firebaseInitilization.owner.child(uid).child("General").child("phone").setValue(uopn.getText().toString());
                        firebaseInitilization.owner.child(uid).child("General").child("email").setValue(uoea.getText().toString());
                        firebaseInitilization.owner.child(uid).child("General").child("uid").setValue(uid);

                        firebaseInitilization.owner.child(uid).child("GlobalPrice").child("CowLitPrice").setValue(uocmp.getText().toString());
                        firebaseInitilization.owner.child(uid).child("GlobalPrice").child("BuffaloLitPrice").setValue(uobmp.getText().toString());
                        firebaseInitilization.owner.child(uid).child("GlobalPrice").child("CowhalfLitPrice").setValue(uocmph.getText().toString());
                        firebaseInitilization.owner.child(uid).child("GlobalPrice").child("BuffalohalfLitPrice").setValue(uobmph.getText().toString());

                        finish();
// set MyFragment Arguments



                    }
                }
            }
        });
    }
}
