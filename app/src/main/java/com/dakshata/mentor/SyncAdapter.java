package com.dakshata.mentor;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Aditya.v on 06-02-2018.
 */

public class SyncAdapter extends RecyclerView.Adapter<SyncAdapter.MyViewHolder> {
    View view;
    List<SyncPojo> arrayList;
    String selectedSession;
    Cursor cursor;
    SQLiteDatabase sqLiteDatabase;
    JhpiegoDatabase jhpiegoDatabase;
    private int lastPosition = -1;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor1;
    public SyncAdapter(Context context, List<SyncPojo> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
        notifyDataSetChanged();
    }
    public SyncAdapter(Context context) {
        this.context = context;
        notifyDataSetChanged();
    }

    Context context;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.sync_item,parent,false);
        SyncAdapter.MyViewHolder myHolder=new SyncAdapter.MyViewHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SyncPojo syncPojo=arrayList.get(position);
        // holder.textViewSession.setText(listPojo.getSession());
        holder.textViewFacility.setText(syncPojo.getFacilityname());
        holder.getTextViewFacilityType.setText(syncPojo.getFacilitytype());
        holder.textViewdov.setText(syncPojo.getDov());
        Glide.with(context).load(syncPojo.getImage()).into(holder.syncimage);
        Glide.with(context).load(syncPojo.getImage1()).into(holder.syncPhoto);

        holder.createPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //createPdf(position,syncPojo);
            }
        });


        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSession,textViewFacility,getTextViewFacilityType,textViewdov;
        CardView cardView;
        ImageView syncimage,syncPhoto,createPdf;
        public MyViewHolder(View itemView) {
            super(itemView);
            textViewFacility=itemView.findViewById(R.id.tv_listItemFacility);
            getTextViewFacilityType=itemView.findViewById(R.id.tv_listItemFacilityType);
            textViewdov=itemView.findViewById(R.id.tv_listItemDov);
            cardView=itemView.findViewById(R.id.history_card);
            syncimage=itemView.findViewById(R.id.iv_sync_data);
            syncPhoto=itemView.findViewById(R.id.iv_sync_photo);
            createPdf=itemView.findViewById(R.id.iv_pdf_photo);
        }
    }
    private void setAnimation(View viewToAnimate, int position)
    {
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_from_right);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    private void createPdf(int position){
       // ((SyncActivity)context).createPdf();
    }
}
