package com.example.myapplication.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Common.CommonKey;
import com.example.myapplication.Common.FirebaseInitilization;
import com.example.myapplication.Model.CalendardaysModel;
import com.example.myapplication.Model.HomeModel;
import com.example.myapplication.Model.WorkLocationModel;
import com.example.myapplication.R;
import com.example.myapplication.ViewAdapter.HomeViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
//import com.victor.loading.rotate.RotateLoading;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment   implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    List<String> locality;
    Context   context = getContext();
    String uid;
    String wp = "Select Work Place";
    int positiond = 0;
    private RecyclerView recyclerView;
    private HomeViewAdapter homeViewAdapter;
    private List<HomeModel> homeList =  new ArrayList<>();
    FirebaseInitilization firebaseInitilization;
    Button fftd;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param";
    private static final String ARG_PARAM2 = "param2";
//    private RotateLoading rotateLoading;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        spinner = (Spinner) view.findViewById(R.id.homespinner);
        firebaseInitilization  =  new FirebaseInitilization();

        CommonKey commonKey = new CommonKey();
        uid = commonKey.uidnull(getActivity());


        recyclerView = (RecyclerView) view.findViewById(R.id.recylist);
        homeViewAdapter = new HomeViewAdapter(homeList,getActivity(),uid);
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        fftd =  (Button) view.findViewById(R.id.fftd);
//        rotateLoading = (RotateLoading) view.findViewById(R.id.rotateloading);
//        rotateLoading.start();
        fftd.setVisibility(View.GONE);



        loadspinner();
        loaddata(locality.get(0));



        fftd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               
              builddialog();
                
                

            }
        });
        return view;
    }

    private void loadspinner() {

        spinner.setOnItemSelectedListener(this);

        locality = new ArrayList<>();
        locality.add("All Work Place");



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

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,locality);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    private void builddialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle("Finish For the day!...");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want update all your client!");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.righ_arrow);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // Write your code here to invoke YES event
//                Toast.makeText(getActivity(), "Done For The Day", Toast.LENGTH_SHORT).show();
                updateAll();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    private void updateAll() {


        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        final String formattedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        final  String formattedmonth = new SimpleDateFormat("dd-yyyy", Locale.getDefault()).format(new Date());

        String timeofday = "null";


        if(timeOfDay >= 0 && timeOfDay < 16){
            timeofday = "morning";
        }else if(timeOfDay >= 16 && timeOfDay < 24){
            timeofday = "evening";
        }


//        if (homeList.size() == 0){
//            Toast.makeText(context, "Done For The Day", Toast.LENGTH_SHORT).show();
//        }else {


            for (int i = 0;i< homeList.size();i++){
                final int finalI = i;
                final String finalTimeofday = timeofday;

//                Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();

                firebaseInitilization.dayRs.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.child(homeList.get(finalI).getPhone()).exists()){
//
////                            Log.e("exist",homeList.get(finalI).getPhone());
//
//                        }else {
//                            Log.e(" not exist",homeList.get(finalI).getPhone());

                            String lit = homeList.get(finalI).getLitqty() + " - " +homeList.get(finalI).getHalflitqty();


                            firebaseInitilization.dayRs.child(uid).child(homeList.get(finalI).getPhone()).child(formattedDate).child("date").setValue(formattedDate);

                            firebaseInitilization.dayRs.child(uid).child(homeList.get(finalI).getPhone()).child(formattedDate).child(finalTimeofday).setValue(lit);

                        }

//                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

//
//                firebaseInitilization.dayRs.child(uid).child(homeList.get(i).getPhone()).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//
//                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
//                            CalendardaysModel calendardaysModel = dataSnapshot1.getValue(CalendardaysModel.class);
//                            String datew = calendardaysModel.getDate();
//                            String morning  = calendardaysModel.getMorning();
//                            String evening  = calendardaysModel.getEvening();
//
//                            Toast.makeText(getActivity(), homeList.get(finalI).getPhone(), Toast.LENGTH_SHORT).show();
////                            int sPrice = homeList.get(i).getCurrentdue() + litPrice * homeList.get(i).getCattlelitprice() + halflit;
//
//
//                            String lit = homeList.get(finalI).getLitqty() + " - " +homeList.get(finalI).getHalflitqty();
//                            Log.e("date",homeList.get(finalI).getPhone()  + " " + formattedDate);
//
//
//                            firebaseInitilization.dayRs.child(uid).child(homeList.get(finalI).getPhone()).child(formattedDate).child("date").setValue(formattedDate);
//
//                            firebaseInitilization.dayRs.child(uid).child(homeList.get(finalI).getPhone()).child(formattedDate).child(finalTimeofday).setValue(lit);
//
//
////                            if (!datew.equals(formattedDate)){
////                                if (morning == null){
////                                    firebaseInitilization.dayRs.child(uid).child(homeList.get(finalI).getPhone()).child(formattedDate).child("date").setValue(formattedDate);
////
////                                    firebaseInitilization.dayRs.child(uid).child(homeList.get(finalI).getPhone()).child(formattedDate).child(finalTimeofday).setValue(lit);
////                                }
//////                                if (evening == null){
//////
//////                                    firebaseInitilization.dayRs.child(uid).child(homeList.get(finalI).getPhone()).child(formattedDate).child("date").setValue(formattedDate);
//////                                    firebaseInitilization.dayRs.child(uid).child(homeList.get(finalI).getPhone()).child(formattedDate).child(finalTimeofday).setValue(lit);
//////                                }
////                            }
//
//
//                        }
//                    }
//
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
            }



//        }


        final String finalTimeofday1 = timeofday;
        final String finalTimeofday2 = timeofday;

        firebaseInitilization.upaf.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(formattedDate).child(finalTimeofday1).exists()){
                    Toast.makeText(getActivity(), "Already updated", Toast.LENGTH_SHORT).show();

                }else{


                    for (int j = 0;j< homeList.size();j++){


                        final int finalJ = j;
                        firebaseInitilization.dayRs.child(uid).child(homeList.get(finalJ).getPhone()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                    CalendardaysModel calendardaysModel = dataSnapshot1.getValue(CalendardaysModel.class);
                                    String datew = calendardaysModel.getDate();
                                    final String morning  = calendardaysModel.getMorning();
                                    final String evening  = calendardaysModel.getEvening();


                                    if (morning ==  null) {
                                        String currentString = evening;

                                        String[] separated = currentString.split("-");

                                        int liteq = homeList.get(finalJ).cattlelitprice * Integer.parseInt(separated[0].trim());

                                        double hliteq = homeList.get(finalJ).cattlehalflitprice * Double.parseDouble(separated[1].trim());

                                        int sPrice = (int) (homeList.get(finalJ).getCurrentdue() + hliteq + liteq);

                                        firebaseInitilization.users.child(uid).child(homeList.get(finalJ).getPhone()).child("currentdue").setValue(sPrice);

//                                    Log.e("pricw ", String.valueOf(sPrice));

//                            if (!datew.equals(formattedDate)){
//                                if (morning == null){
////                                    firebaseInitilization.dayRs.child(uid).child(homeList.get(finalJ).getPhone()).child(formattedDate).child("date").setValue(formattedDate);
////
////                                    firebaseInitilization.dayRs.child(uid).child(homeList.get(finalJ).getPhone()).child(formattedDate).child(finalTimeofday).setValue(lit);
//
//                                    String currentString = formattedDate;
//                                    String[] separated = currentString.split("-");
//                                   Log.e("even",separated[0] + separated[1]);
////                                    separated[1];
//
////                                    int eq =
//
//
//                                }
//                                if (evening == null){
//
//                                    String currentString = formattedDate;
//                                    String[] separated = currentString.split("-");
//                                    Log.e("even4",separated[0] + separated[1]);
////                                    firebaseInitilization.dayRs.child(uid).child(homeList.get(finalJ).getPhone()).child(formattedDate).child("date").setValue(formattedDate);
////                                    firebaseInitilization.dayRs.child(uid).child(homeList.get(finalJ).getPhone()).child(formattedDate).child(finalTimeofday).setValue(lit);
//                                }
//                            }
                                    }


                                    if (evening ==  null) {
                                        String currentString = morning;

                                        String[] separated = currentString.split("-");
//                                                    Log.e("even4x", separated[0].trim() + separated[1].trim());

                                        int liteq = homeList.get(finalJ).cattlelitprice * Integer.parseInt(separated[0].trim());

                                        double hliteq = homeList.get(finalJ).cattlehalflitprice * Double.parseDouble(separated[1].trim());

                                        int sPrice = (int) (homeList.get(finalJ).getCurrentdue() + hliteq + liteq);

                                        firebaseInitilization.users.child(uid).child(homeList.get(finalJ).getPhone()).child("currentdue").setValue(sPrice);


                                    }

                                }


                            }


                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }
                    firebaseInitilization.upaf.child(uid).child(formattedDate).child(finalTimeofday2).setValue(true);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });










    }

    private void loaddata(String local) {


        Query query;

        if (local.equals("All Work Place")) {
            query = firebaseInitilization.users.child(uid);
        }else {
            query = firebaseInitilization.users.child(uid).orderByChild("locality").equalTo(local);
        }
        query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    homeList.clear();

                    for (DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                        HomeModel homeModel = dataSnapshot1.getValue(HomeModel.class);
                        homeModel.setName(homeModel.getName());
                        homeModel.setPlotno(homeModel.getPlotno());
                        homeModel.setCattle(homeModel.getCattle());
                        homeModel.setCattlehalflitprice(homeModel.getCattlehalflitprice());
                        homeModel.setHalflitqty(homeModel.getHalflitqty());
                        homeModel.setLitqty(homeModel.getLitqty());
                        homeModel.setCattlelitprice(homeModel.getCattlelitprice());

                        homeList.add(homeModel);
                    }
//                    rotateLoading.stop();
                    fftd.setVisibility(View.VISIBLE);

                    recyclerView.setAdapter(homeViewAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        positiond = position;
        wp = item;
        loaddata(item);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
