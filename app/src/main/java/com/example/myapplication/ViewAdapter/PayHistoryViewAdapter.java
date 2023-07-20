package com.example.myapplication.ViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.PayHistoryModel;
import com.example.myapplication.R;

import java.util.List;

public class PayHistoryViewAdapter extends RecyclerView.Adapter<PayHistoryViewAdapter.PayHistoryViewHolder> {

    private List<PayHistoryModel> phList;
    private Context context;

    public PayHistoryViewAdapter(List<PayHistoryModel> phList, Context context) {
        this.phList = phList;
        this.context =

                context;
    }



    @NonNull
    @Override
    public PayHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payhistoryitem, parent, false);

        return new PayHistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PayHistoryViewHolder holder, int position) {

        final PayHistoryModel payHistoryModel = phList.get(position);
        holder.phdate.setText(payHistoryModel.getDate());
        holder.phamt.setText(String.valueOf("₹ " +payHistoryModel.getAmount()));

    }

    @Override
    public int getItemCount() {
        return  phList.size();
    }

    public class PayHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView phdate,phamt;
        public PayHistoryViewHolder(View itemView) {
            super(itemView);
            phdate =  (TextView) itemView.findViewById(R.id.phdate);
            phamt =  (TextView) itemView.findViewById(R.id.phap);

        }
    }
}
