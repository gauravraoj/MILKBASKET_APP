package com.example.myapplication.ViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Common.FirebaseInitilization;
import com.example.myapplication.Model.HomeModel;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeViewAdapter extends RecyclerView.Adapter<HomeViewAdapter.HomeViewHolder> {

    int minteger = 0;
    private List<HomeModel> hList;
    private Context context;

String uid;
    public HomeViewAdapter(List<HomeModel> hList, Context context1, String uid) {
        this.hList = hList;
        this.context = context1;
        this.uid = uid;
    }


    public class HomeViewHolder extends RecyclerView.ViewHolder{

        public TextView hname,hplotno;
        public TextView hlit,hlit2,hcattle;
        public Button hdone;
        public FrameLayout frameLayout;
        public ImageButton litmin,litplus,hlitmin,hlitplus,hremove,hcall,htodaydone;

        public HomeViewHolder(View itemView) {
            super(itemView);
            hname = (TextView) itemView.findViewById(R.id.hname);
            hplotno = (TextView) itemView.findViewById(R.id.hplotno);
            hdone = (Button) itemView.findViewById(R.id.hdone);
            hlit = (TextView) itemView.findViewById(R.id.hlit);
            hlit2 = (TextView) itemView.findViewById(R.id.hlit2);
            litmin = (ImageButton) itemView.findViewById(R.id.decrease);
            litplus = (ImageButton) itemView.findViewById(R.id.increase);
            hlitmin = (ImageButton) itemView.findViewById(R.id.decreaseh);
            hlitplus = (ImageButton) itemView.findViewById(R.id.increaseh);
            hcattle = (TextView) itemView.findViewById(R.id.hcattle);
            hremove = (ImageButton) itemView.findViewById(R.id.hremove);
            frameLayout =  (FrameLayout) itemView.findViewById(R.id.hframe);
            hcall = (ImageButton) itemView.findViewById(R.id.hcall);
            htodaydone = (ImageButton) itemView.findViewById(R.id.htodaydone);

            Typeface typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), "Fonts/LucidaGrande.ttf");
            hname.setTypeface(typeface);
            hdone.setTypeface(typeface);
            hlit2.setTypeface(typeface);
            hlit.setTypeface(typeface);

        }
    }



    @NonNull
    @Override
    public HomeViewAdapter.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.homeitemview, parent, false);

        return new HomeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeViewAdapter.HomeViewHolder holder, int position) {

        final FirebaseInitilization firebaseInitilization = new FirebaseInitilization();

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

        final HomeModel homeModel = hList.get(holder.getBindingAdapterPosition());
        holder.hname.setText(homeModel.getName());
        holder.hplotno.setText(homeModel.getPlotno());
        holder.hlit.setText(String.valueOf(homeModel.getLitqty()));
        holder.hlit2.setText(String.valueOf(homeModel.getHalflitqty()));
        holder.hcattle.setText(homeModel.getCattle());
        final String finalTimeofday = timeofday;

        firebaseInitilization.home.child(uid).child(homeModel.getPhone()).child(formattedDate).child(finalTimeofday).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    holder.htodaydone.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_done_all_black_24dp));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle onCancelled event
            }
        });

        holder.hdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int halflit = 0;

                if (holder.hlit2.getText().toString().equals("0.5")) {
                    halflit = homeModel.getCattlehalflitprice();
                }

                int litPrice = Integer.parseInt(holder.hlit.getText().toString());

                int sPrice = homeModel.getCurrentdue() + litPrice * homeModel.getCattlelitprice() + halflit;

                String lit = holder.hlit.getText().toString() + " - " + holder.hlit2.getText().toString();

                firebaseInitilization.home.child(uid).child(homeModel.getPhone()).child(formattedDate).child("date").setValue(formattedDate);
                firebaseInitilization.home.child(uid).child(homeModel.getPhone()).child(formattedDate).child(finalTimeofday).setValue(lit);

                holder.htodaydone.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_done_all_black_24dp));
            }
        });

        final int[] minteger = {0};
        final double[] halfminteger = {0};

        holder.litmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (minteger[0] > 0) {
                    minteger[0] = minteger[0] - 1;
                    holder.hlit.setText(String.valueOf(minteger[0]));
                }
            }
        });

        holder.litplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minteger[0] = minteger[0] + 1;
                holder.hlit.setText(String.valueOf(minteger[0]));
            }
        });

        holder.hlitmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (halfminteger[0] > 0.0) {
                    halfminteger[0] = halfminteger[0] - 0.5;
                    holder.hlit2.setText(String.valueOf(halfminteger[0]));
                }
            }
        });

        holder.hlitplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (halfminteger[0] < 0.5) {
                    halfminteger[0] = halfminteger[0] + 0.5;
                    holder.hlit2.setText(String.valueOf(halfminteger[0]));
                }
            }
        });

        holder.hremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int halflit = 0;

                if (holder.hlit2.getText().toString().equals("0.5")) {
                    halflit = homeModel.getCattlehalflitprice();
                }

                int litPrice = Integer.parseInt(holder.hlit.getText().toString());

                int sPrice = homeModel.getCurrentdue() + litPrice * homeModel.getCattlelitprice() + halflit;

                String lit = "0" + " - " + "0.0";

                firebaseInitilization.home.child(uid).child(homeModel.getPhone()).child(formattedDate).child("date").setValue(formattedDate);
                firebaseInitilization.home.child(uid).child(homeModel.getPhone()).child(formattedDate).child(finalTimeofday).setValue(lit);

                removeAt(holder.getAdapterPosition(), holder);
                Toast.makeText(context, "Client Removed", Toast.LENGTH_SHORT).show();
            }
        });

        holder.hcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", homeModel.getPhone(), null));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hList.size();
    }

    public void removeAt(int position, HomeViewHolder homeViewHolder) {
        if (hList.size() > 1) {
            hList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, hList.size());
        } else {
            homeViewHolder.itemView.setVisibility(View.GONE);
        }
    }

}
