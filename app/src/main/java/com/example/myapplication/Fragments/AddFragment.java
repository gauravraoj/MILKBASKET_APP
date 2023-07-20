package com.example.myapplication.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;



import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.example.myapplication.Common.CommonKey;
import com.example.myapplication.Common.FirebaseInitilization;
import com.example.myapplication.Model.HomeModel;
import com.example.myapplication.Model.WorkLocationModel;
import com.example.myapplication.R;
import com.example.myapplication.SubActivity.AddNewClient;
import com.example.myapplication.ViewAdapter.ClientViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment  implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Context   context = getActivity();
    Spinner spinner;
    List<String> locality;
    String uid;
    String wp = "Select Work Place";
    int positiond = 0;
    FirebaseInitilization firebaseInitilization;
    private RecyclerView recyclerView;
    private ClientViewAdapter clientViewAdapter;
    private List<HomeModel> clientList =  new ArrayList<>();
    private Button faclient;

    private OnFragmentInteractionListener mListener;

    public AddFragment() {
        // Required empty public constructor
    }

    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
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
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        faclient =  (Button) view.findViewById(R.id.faclient);
        faclient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddNewClient.class));
            }
        });


        spinner = (Spinner) view.findViewById(R.id.addspinner);

        recyclerView = (RecyclerView) view.findViewById(R.id.farecycler);
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);


        CommonKey commonKey = new CommonKey();
            uid = commonKey.uidnull(getActivity());



        firebaseInitilization  =  new FirebaseInitilization();


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

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,locality);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        loaddata(locality.get(0));



        return view;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
                clientList.clear();

                for (DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                    HomeModel clientModel = dataSnapshot1.getValue(HomeModel.class);
                    clientModel.setName(clientModel.getName());
                    clientModel.setAddress(clientModel.getAddress());
                    clientModel.setPhone(clientModel.getPhone());
                    clientModel.setCurrentdue(clientModel.getCurrentdue());
                    clientList.add(clientModel);
                }
                clientViewAdapter = new ClientViewAdapter(clientList,getActivity());
                recyclerView.setAdapter(clientViewAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }

}
