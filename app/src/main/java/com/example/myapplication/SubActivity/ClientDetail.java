package com.example.myapplication.SubActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Common.CommonKey;
import com.example.myapplication.Common.FirebaseInitilization;
import com.example.myapplication.Model.HomeModel;
import com.example.myapplication.Model.WorkLocationModel;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClientDetail extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText cdname,cdplot,cddateofpay,cdphone,cdaddress,cdcattleprice,cdcurrentdue,cdcity,cdstate,cdlitprice,cdhalfprice;
    String clientphone,cdcattle,localstring;
    Button cdcsubmit;
    Spinner cdlocality;
    HomeModel homeModel;
    TextView cdcanchlq,cdcanclq,cdcdue;
    ImageButton cdcdecrease,cdcincrease,cdcdecreaseh,cdcincreaseh;
    RadioGroup cdcradioGroup;
    RadioButton cdcrcow,cdcrbuffalo;
    List<String> locality;
    String buffaloLitPrice = "not Assigned",buffalohalfLitPrice= "not Assigned",cowLitPrice= "not Assigned",cowhalfLitPrice= "not Assigned";
    String uid;
    FirebaseInitilization firebaseInitilization;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_detail);
        cdname = (EditText) findViewById(R.id.cdname);
        cddateofpay = (EditText) findViewById(R.id.cddateofpay);
        cdphone = (EditText) findViewById(R.id.cdphone);
        cdplot = (EditText) findViewById(R.id.cdplotno);
        cdlocality = (Spinner) findViewById(R.id.cdlocality);
        cdcity = (EditText) findViewById(R.id.cdccity);
        cdstate= (EditText) findViewById(R.id.cdstate);
        cdlitprice = (EditText) findViewById(R.id.cdlp);
        cdhalfprice = (EditText) findViewById(R.id.cdhlp);

        localstring = "All Work Place";;

        cdphone.setEnabled(false);
        firebaseInitilization = new FirebaseInitilization();

        cdcurrentdue = (EditText) findViewById(R.id.cdcdue);

        cdcdecrease = (ImageButton) findViewById(R.id.cdcdecrease);
        cdcincrease = (ImageButton) findViewById(R.id.cdcincrease);
        cdcdecreaseh = (ImageButton) findViewById(R.id.cdcdecreaseh);
        cdcincreaseh = (ImageButton) findViewById(R.id.cdcincreaseh);

        cdcanchlq = (TextView) findViewById(R.id.cdcanchlq);
        cdcanclq = (TextView) findViewById(R.id.cdcanclq);
        cdcdue=(TextView) findViewById(R.id.cdcdue);

        cdcrcow =  (RadioButton) findViewById(R.id.cdcrcow);
        cdcrbuffalo =  (RadioButton) findViewById(R.id.cdcrbuffalo);

        cdcsubmit  = (Button) findViewById(R.id.cdcsubmit);
        cdcradioGroup = (RadioGroup) findViewById(R.id.cdcradioGroup);
        cdcradioGroup.clearCheck();


        locality = new ArrayList<>();
        locality.add("All Work Place");

        cdcradioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    cdcattle = rb.getText().toString();
                }

            }
        });

        cdlocality.setOnItemSelectedListener(this);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            clientphone = bundle.getString("clientPhone");

        }
        CommonKey commonKey = new CommonKey();
        uid = commonKey.uidnull(this);



        loadSpinner();


        final FirebaseInitilization firebaseInitilization  =  new FirebaseInitilization();
        firebaseInitilization.users.child(uid).child(clientphone).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                     homeModel = dataSnapshot.getValue(HomeModel.class);
                    cdname.setText(String.valueOf(homeModel.getName()));
                cdhalfprice.setText(String.valueOf(homeModel.cattlehalflitprice));
                cdlitprice.setText(String.valueOf(homeModel.cattlelitprice));
//                cdcity.setText(String.valueOf(homeModel.getCity()));
                cdcurrentdue.setText(String.valueOf(homeModel.getCurrentdue()));
                cddateofpay.setText(String.valueOf(homeModel.getDatepay()));
                cdcanchlq.setText(String.valueOf(homeModel.getHalflitqty()));
                cdcanclq.setText(String.valueOf(homeModel.getLitqty()));
                cdlitprice.setText(String.valueOf(homeModel.cattlelitprice));
                cdhalfprice.setText(String.valueOf(homeModel.cattlehalflitprice));
//                cdlocality.setText(String.valueOf(homeModel.getLocality()));
                cdphone.setText(String.valueOf(homeModel.getPhone()));
                cdplot.setText(String.valueOf(homeModel.getPlotno()));
//                cdstate.setText(String.valueOf(homeModel.getState()));


                if (homeModel.getCattle().equals("Cow")){
                        cdcrcow.setChecked(true);
                        cdcattle = "Cow";
                    }else
                        if (homeModel.getCattle().equals("Buffalo")){
                        cdcrbuffalo.setChecked(true);
                        cdcattle = "Buffalo";

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        cdcsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference = firebaseInitilization.users.child(uid).child(clientphone);
                databaseReference.child("address").setValue(cdplot.getText().toString() + ", " +localstring + ", " + cdcity.getText().toString());
                databaseReference.child("cattle").setValue(cdcattle);
                databaseReference.child("cattlehalflitprice").setValue(Integer.parseInt(cdhalfprice.getText().toString()));
                databaseReference.child("cattlelitprice").setValue(Integer.parseInt(cdlitprice.getText().toString()));
                databaseReference.child("city").setValue(cdcity.getText().toString());

                databaseReference.child("currentdue").setValue(Integer.parseInt(cdcurrentdue.getText().toString()));
                databaseReference.child("datepay").setValue(cddateofpay.getText().toString());
                databaseReference.child("halflitqty").setValue(cdcanchlq.getText().toString());
                databaseReference.child("latitude").setValue("3212321");
                databaseReference.child("litqty").setValue(cdcanclq.getText().toString());
                databaseReference.child("locality").setValue(localstring);
                databaseReference.child("longitude").setValue("12311313");

                databaseReference.child("name").setValue(cdname.getText().toString());
                databaseReference.child("phone").setValue(cdphone.getText().toString());
                databaseReference.child("plotno").setValue(cdplot.getText().toString());
                databaseReference.child("state").setValue(cdstate.getText().toString());

                finish();


            }
        });




        final int[] minteger = {0};
        final double[] halfminteger = {0};



        cdcdecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (minteger[0]> 0) {
                    minteger[0] = minteger[0] - 1;
                    cdcanclq.setText(String.valueOf(minteger[0]));
                }
            }
        });


        cdcincrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minteger[0] = minteger[0] + 1;
                cdcanclq.setText(String.valueOf(minteger[0]));

            }
        });
        cdcdecreaseh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (halfminteger[0] > 0.0) {
                    halfminteger[0] = halfminteger[0] - 0.5;
                    cdcanchlq.setText(String.valueOf(halfminteger[0]));
                }
            }
        });
        cdcincreaseh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (halfminteger[0] < 0.5) {

                    halfminteger[0] = halfminteger[0] + 0.5;
                    cdcanchlq.setText(String.valueOf(halfminteger[0]));

                }
            }
        });


    }

    private void loadSpinner() {


        firebaseInitilization.owner.child(uid).child("WorkPlace").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    WorkLocationModel workLocationModel = dataSnapshot1.getValue(WorkLocationModel.class);
                    locality.add(workLocationModel.getTitle());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ClientDetail.this, android.R.layout.simple_spinner_item,locality);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        cdlocality.setAdapter(dataAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        localstring = item;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
