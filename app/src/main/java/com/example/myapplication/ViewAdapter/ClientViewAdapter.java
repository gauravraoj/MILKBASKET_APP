package com.example.myapplication.ViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import  com.example.myapplication.Interface.ItemClickListener;
import  com.example.myapplication.Model.HomeModel;
import  com.example.myapplication.R;
import  com.example.myapplication.SubActivity.ClientDetail;
import  com.example.myapplication.SubActivity.ClientPayHistoryActivity;
import  com.example.myapplication.SubActivity.ClientViewActivity;

import java.util.List;

public class ClientViewAdapter extends RecyclerView.Adapter<ClientViewAdapter.ClientViewHolder> {

    private List<HomeModel> cList;
    private Context context;
    private ItemClickListener itemClickListener;

    public ClientViewAdapter(List<HomeModel> cList, Context context) {
        this.cList = cList;
        this.context = context;
    }



    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.clientdetailitem, parent, false);

        return new ClientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, final int position) {
        final HomeModel clienthomeModel = cList.get(position);

        holder.cname.setText(clienthomeModel.getName());
        holder.caddress.setText(clienthomeModel.getAddress());
        holder.cdueamount.setText(String.valueOf("₹ " +clienthomeModel.getCurrentdue()));
        holder.cphone.setText(clienthomeModel.getPhone());

        holder.ccall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",clienthomeModel.getPhone() , null));
                context.startActivity(intent);
            }
        });


        holder.addedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent  =  new Intent(context,ClientDetail.class);
                //sending food id to FoodDetail
                intent.putExtra("clientPhone",cList.get(position).getPhone());

                context.startActivity(intent);
            }
        });
//                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();

        holder.addpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent  =  new Intent(context,ClientPayHistoryActivity.class);
                //sending food id to FoodDetail
                intent.putExtra("clientPhone",cList.get(position).getPhone());

                context.startActivity(intent);
            }
        });


        holder.addview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent  =  new Intent(context,ClientViewActivity.class);
                //sending food id to FoodDetail
                intent.putExtra("clientPhone",cList.get(position).getPhone());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cList.size();
    }


    public class ClientViewHolder extends RecyclerView.ViewHolder  {

        TextView cname,cphone,caddress,cdueamount;
        private ItemClickListener itemClickListener;
        Button addedit,addview,addpay;
        ImageButton ccall;

        public ClientViewHolder(View itemView) {
            super(itemView);
            cname =  (TextView) itemView.findViewById(R.id.cname);
            cphone =  (TextView) itemView.findViewById(R.id.cphone);
            caddress =  (TextView) itemView.findViewById(R.id.caddress);
            cdueamount =  (TextView) itemView.findViewById(R.id.cdueamount);
            addedit = (Button) itemView.findViewById(R.id.addedit);
            addview = (Button) itemView.findViewById(R.id.addview);
            addpay = (Button) itemView.findViewById(R.id.addpay);
            ccall =  (ImageButton) itemView.findViewById(R.id.ccall);

        }

    }
}
