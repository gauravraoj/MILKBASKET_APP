package com.example.myapplication.SubActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Common.CommonKey;
import com.example.myapplication.Common.FirebaseInitilization;
import com.example.myapplication.Model.VendorGlobalPriceModel;
import com.example.myapplication.Model.WorkLocationModel;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddNewClient extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText ancn,ancd,ancpn,ancplno,anccity,ancs,anclp,anchlp,anccd;
    RadioButton ancrcow,ancrbuffalo;
    Button ancsubmit,ancclear;
    TextView anclq,anchlq,ancanclq,ancanchlq;
    private RadioGroup ancradioGroup;
    String uid,localstring = "All Work Place";
    Spinner ancl;
    ImageButton anclitmin,anclitplus,anchlitmin,anchlitplus;
    List<String> locality;
     FirebaseInitilization firebaseInitilization;
     String buffaloLitPrice = "not Assigned",buffalohalfLitPrice= "not Assigned",cowLitPrice= "not Assigned",cowhalfLitPrice= "not Assigned";

    String cattle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_client);
        ancn = (EditText) findViewById(R.id.ancn);
        ancd = (EditText) findViewById(R.id.ancd);
        ancpn = (EditText) findViewById(R.id.ancpn);
        ancplno = (EditText) findViewById(R.id.ancplno);
        ancl = (Spinner) findViewById(R.id.ancl);
        anccity = (EditText) findViewById(R.id.anccity);
        ancs = (EditText) findViewById(R.id.ancs);
        anclp = (EditText) findViewById(R.id.anclp);
        anchlp = (EditText) findViewById(R.id.anchlp);
        anccd= (EditText) findViewById(R.id.anccd);
        ancsubmit = (Button) findViewById(R.id.ancsubmit);
        ancclear = (Button) findViewById(R.id.ancclear);
        ancanclq=(TextView)findViewById(R.id.ancanclq);
        ancanchlq=(TextView)findViewById(R.id.ancanchlq) ;


        anclq =  (TextView) findViewById(R.id.ancanclq);
        anchlq =  (TextView) findViewById(R.id.ancanchlq);


        anclitmin = (ImageButton) findViewById(R.id.ancdecrease);
        anclitplus = (ImageButton) findViewById(R.id.ancincrease);
        anchlitmin = (ImageButton) findViewById(R.id.ancdecreaseh);
        anchlitplus = (ImageButton) findViewById(R.id.ancincreaseh);
        ancclear = (Button) findViewById(R.id.ancclear);
        ancradioGroup = (RadioGroup) findViewById(R.id.ancradioGroup);
        ancradioGroup.clearCheck();
        firebaseInitilization = new FirebaseInitilization();

        locality = new ArrayList<>();
        locality.add("All Work Place");





        uid = FirebaseAuth.getInstance().getUid();

        CommonKey commonKey = new CommonKey();
        if (uid == null){
            uid = commonKey.uidnull(AddNewClient.this);
//            Toast.makeText(getActivity(),"null " + commonKey.uidnull(getActivity()),Toast.LENGTH_SHORT).show();
        }
        if (uid == null){
            uid = CommonKey.key;
        }


        ancl.setOnItemSelectedListener(this);


        ancradioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    cattle = rb.getText().toString();

                    if(cattle != null) {
                        if (cattle.equals("Buffalo")) {
                            anchlp.setText(buffalohalfLitPrice);
                            anclp.setText(buffaloLitPrice);

                        } else if (cattle.equals("Cow")) {
                            anchlp.setText(cowhalfLitPrice);
                            anclp.setText(cowLitPrice);
                        }
                    }

                }

            }
        });

        firebaseInitilization.owner.child(uid).child("GlobalPrice").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    VendorGlobalPriceModel vendorGlobalPriceModel = dataSnapshot.getValue(VendorGlobalPriceModel.class);
                    buffaloLitPrice = vendorGlobalPriceModel.getBuffaloLitPrice();
                    buffalohalfLitPrice = vendorGlobalPriceModel.getBuffalohalfLitPrice();
                    cowLitPrice = vendorGlobalPriceModel.getCowLitPrice();
                cowhalfLitPrice = vendorGlobalPriceModel.getCowhalfLitPrice();




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


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
        ancanclq.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            int one =Integer.parseInt( ancanclq.getText().toString());
            float half=Float.parseFloat(ancanchlq.getText().toString());
            int halfint=(int) Math.round(half);
            int sum=one*Integer.parseInt(anclp.getText().toString())+halfint*Integer.parseInt(anchlp.getText().toString());
                anccd.setText(Integer.toString(sum));
            }
        });

        ancclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ancn.setText("");
                ancd.setText("");
                ancpn.setText("");
                ancplno.setText("");
                anclp.setText("");
                anchlp.setText("");
                anccd.setText("");

            }
        });


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddNewClient.this, android.R.layout.simple_spinner_item,locality);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        ancl.setAdapter(dataAdapter);


        ancsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ancpn.getText().toString().isEmpty() || ancpn.getText().toString().length() < 10) {
                    Toast.makeText(AddNewClient.this, "Valid number is required", Toast.LENGTH_SHORT).show();
                    ancpn.requestFocus();
                } else {


                    DatabaseReference databaseReference = firebaseInitilization.users.child(uid).child(ancpn.getText().toString());
                    databaseReference.child("address").setValue(ancplno.getText().toString() + ", " + localstring + ", " + anccity.getText().toString());
                    databaseReference.child("cattle").setValue(cattle);
                    databaseReference.child("cattlehalflitprice").setValue(Integer.parseInt(anchlp.getText().toString()));
                    databaseReference.child("cattlelitprice").setValue(Integer.parseInt(anclp.getText().toString()));
                    databaseReference.child("city").setValue(anccity.getText().toString());

                    databaseReference.child("currentdue").setValue(Integer.parseInt(anccd.getText().toString()));
                    databaseReference.child("datepay").setValue(ancd.getText().toString());
                    databaseReference.child("halflitqty").setValue(anchlq.getText().toString());
                    databaseReference.child("latitude").setValue("3212321");
                    databaseReference.child("litqty").setValue(anclq.getText().toString());
                    databaseReference.child("locality").setValue(localstring);
                    databaseReference.child("longitude").setValue("12311313");



                    databaseReference.child("name").setValue(ancn.getText().toString());
                    databaseReference.child("phone").setValue(ancpn.getText().toString());
                    databaseReference.child("plotno").setValue(ancplno.getText().toString());
                    databaseReference.child("state").setValue(ancs.getText().toString());


                    Calendar c = Calendar.getInstance();
                    int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
                    final String formattedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    final String formattedmonth = new SimpleDateFormat("dd-yyyy", Locale.getDefault()).format(new Date());

                    String timeofday = "null";


                    if (timeOfDay >= 0 && timeOfDay < 16) {
                        timeofday = "morning";
                    } else if (timeOfDay >= 16 && timeOfDay < 24) {
                        timeofday = "evening";
                    }

//                    firebaseInitilization.home.child(uid).child(ancpn.getText().toString()).child(formattedDate).child("date").setValue(formattedDate);
//                    firebaseInitilization.home.child(uid).child(ancpn.getText().toString()).child(formattedDate).child("morning").setValue("00-00-000");
//                    firebaseInitilization.home.child(uid).child(ancpn.getText().toString()).child(formattedDate).child("evening").setValue("00-00-000");
//                    firebaseInitilization.home.child(uid).child(ancpn.getText().toString()).child(formattedDate).child("milktaken").setValue(true);

//                    Intent intent = new Intent(AddNewClient.this, MainActivity.class);
//                    intent.putExtra("clientPhone", ancpn.getText().toString());
//                    startActivity(intent);


                    finish();
                }

            }


        });




        final int[] minteger = {0};
        final double[] halfminteger = {0};



        anclitmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (minteger[0]> 0) {
                    minteger[0] = minteger[0] - 1;
                    anclq.setText(String.valueOf(minteger[0]));
                }
            }
        });


        anclitplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minteger[0] = minteger[0] + 1;
                anclq.setText(String.valueOf(minteger[0]));

            }
        });
        anchlitmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (halfminteger[0] > 0.0) {
                    halfminteger[0] = halfminteger[0] - 0.5;
                    anchlq.setText(String.valueOf(halfminteger[0]));
                }
            }
        });
        anchlitplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (halfminteger[0] < 0.5) {

                    halfminteger[0] = halfminteger[0] + 0.5;
                    anchlq.setText(String.valueOf(halfminteger[0]));

                }
            }
        });



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

