package com.example.myapplication.ViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Common.FirebaseInitilization;
import com.example.myapplication.Model.WorkLocationModel;
import com.example.myapplication.R;

import java.util.List;

public class WorkLocationViewAdapter extends RecyclerView.Adapter<WorkLocationViewAdapter.WorkLocationViewHolder> {

    private List<WorkLocationModel> wlList;
    private Context context;
String uid;

    public WorkLocationViewAdapter(List<WorkLocationModel> wlList, Context context, String uid) {
        this.wlList = wlList;
        this.context = context;
        this.uid = uid;
    }


    @NonNull
    @Override
    public WorkLocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.worklocationrowitem, parent, false);

        return new WorkLocationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkLocationViewHolder holder, final int position) {

        final WorkLocationModel workLocationModel = wlList.get(position);
        holder.wltitle.setText(workLocationModel.getTitle());
        holder.wlbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                CommonKey commonKey = new CommonKey();
//                if (uid == null){
//                    uid = commonKey.uidnull(context);
////            Toast.makeText(getActivity(),"null " + commonKey.uidnull(getActivity()),Toast.LENGTH_SHORT).show();
//                }
//                if (uid == null){
//                    uid = CommonKey.key;
//                }




                FirebaseInitilization  firebaseInitilization = new FirebaseInitilization();
                firebaseInitilization.owner.child(uid).child("WorkPlace").child(workLocationModel.getKey()).removeValue();
            }
        });
    }

    @Override
    public int getItemCount() {
        return wlList.size();
    }

    public class WorkLocationViewHolder extends RecyclerView.ViewHolder {
        TextView wltitle;
        Button wlbutton;
        public WorkLocationViewHolder(View itemView) {
            super(itemView);
            wltitle = (TextView) itemView.findViewById(R.id.worklocationtitle);
            wlbutton = (Button) itemView.findViewById(R.id.worklocationdelete);
        }
    }
}
