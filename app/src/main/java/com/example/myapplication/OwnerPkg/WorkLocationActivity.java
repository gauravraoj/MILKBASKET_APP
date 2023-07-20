package com.example.myapplication.OwnerPkg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Common.CommonKey;
import com.example.myapplication.Common.FirebaseInitilization;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.WorkLocationModel;
import com.example.myapplication.R;
import com.example.myapplication.ViewAdapter.WorkLocationViewAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WorkLocationActivity extends AppCompatActivity  implements OnItemSelectedListener{
    Spinner spinner;
    List<String> city;
    String wp = "Select Work Place";
    String phno,uid,uidd,ina;
    int positiond = 0;
    Button wlsubmit,wldone;
    RecyclerView recyclerView;
    private List<WorkLocationModel> wlList =  new ArrayList<>();

    WorkLocationViewAdapter workLocationViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_location);
        uid = FirebaseAuth.getInstance().getUid();

         spinner = (Spinner) findViewById(R.id.wlspinner);
         wlsubmit = (Button) findViewById(R.id.wlsubmit);
        wldone = (Button) findViewById(R.id.wldone);

        
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            phno = bundle.getString("phno");
            uidd = bundle.getString("uid");
            ina = bundle.getString("ina");
        }

        if (uid == null){
            CommonKey commonKey = new CommonKey();
            uid =  commonKey.uidnull(WorkLocationActivity.this);


        }
        if (uid == null){
            uid = CommonKey.key;
        }


        recyclerView = (RecyclerView) findViewById(R.id.wlrecylist);
        workLocationViewAdapter = new WorkLocationViewAdapter(wlList,WorkLocationActivity.this,uid);
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(WorkLocationActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);

        spinner.setOnItemSelectedListener(this);

         city = new ArrayList<>();

         city.add("Select Work Place");
         city.add("Mangalore");
         city.add("Puttur");
        city.add("Udupi");
      //  city.add("Moodbidri");

        final FirebaseInitilization firebaseInitilization = new FirebaseInitilization();
        firebaseInitilization.city.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    WorkLocationModel workLocationModel = dataSnapshot1.getValue(WorkLocationModel.class);
                    city.add(workLocationModel.getTitle());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, city);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);



        wlsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(wp.equals("Select Work Place"))) {
                    firebaseInitilization.owner.child(uid).child("WorkPlace").child(String.valueOf(positiond)).child("title").setValue(wp);
                    firebaseInitilization.owner.child(uid).child("WorkPlace").child(String.valueOf(positiond)).child("phone").setValue(phno);
                    firebaseInitilization.owner.child(uid).child("WorkPlace").child(String.valueOf(positiond)).child("key").setValue(String.valueOf(positiond));

                }
            }
        });

        firebaseInitilization.owner.child(uid).child("WorkPlace").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                wlList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    WorkLocationModel workLocationModel = dataSnapshot1.getValue(WorkLocationModel.class);
                    workLocationModel.setTitle(workLocationModel.getTitle());
                    wlList.add(workLocationModel);

                }

                recyclerView.setAdapter(workLocationViewAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        wldone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wlList.size() > 0){
                    if (ina != null) {
                        finish();

                    }
                    else {
                        Intent intent = new Intent(WorkLocationActivity.this, MainActivity.class);
                        intent.putExtra("phno", phno);
                        intent.putExtra("uid", uid);
                        startActivity(intent);

                    }
                }else {
                    Toast.makeText(WorkLocationActivity.this, "Please Select Work Location", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        positiond = position;
        // Showing selected spinner item
        wp = item;

    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
