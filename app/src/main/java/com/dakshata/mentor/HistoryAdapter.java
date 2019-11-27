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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Aditya.v on 09-01-2018.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyHolder> {
    View view;
    List<ListPojo> arrayList;
    String selectedSession;
    Cursor cursor;
    SQLiteDatabase sqLiteDatabase;
    JhpiegoDatabase jhpiegoDatabase;
    private int lastPosition = -1;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor1;
    public HistoryAdapter(Context context,List<ListPojo> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
        this.jhpiegoHistory  = (JhpiegoHistory)context;
    }

    Context context;
    JhpiegoHistory jhpiegoHistory;
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.list_item_sync,parent,false);
        MyHolder myHolder=new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        ListPojo listPojo=arrayList.get(position);
          // holder.textViewSession.setText(listPojo.getSession());
        holder.textViewFacility.setText(listPojo.getFacilityname());
        holder.getTextViewFacilityType.setText(listPojo.getFacilitytype());
        holder.textViewdov.setText(listPojo.getDov());

        String sessionID=listPojo.getFacilityname()+"-"+listPojo.getFacilitytype()+"-"+listPojo.getDov();
        holder.linearLayout_record_data.setTag(sessionID);
        holder.linearLayout_record_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jhpiegoHistory.alertHistory((String)v.getTag(), (String)v.getTag(R.string.facilityNameKey), (String)v.getTag(R.string.facilityTypeKey));
            }
        });

        holder.linearLayout_download.setTag(R.string.action_sign_in,listPojo.getFacilityname());
        holder.linearLayout_download.setTag(R.string.action_sign_in_short, listPojo.getFacilitytype());
        holder.linearLayout_download.setTag(sessionID);
        holder.linearLayout_download.setTag(R.string.facilityNameKey, listPojo.getFacilityname());
        holder.linearLayout_download.setTag(R.string.facilityTypeKey, listPojo.getFacilitytype());
        holder.linearLayout_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jhpiegoHistory.createPdf((String)v.getTag(), ((String)v.getTag(R.string.action_sign_in))+"_"+((String)v.getTag(R.string.action_sign_in_short)));
            }
        });


        setAnimation(holder.itemView, position);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView textViewSession,textViewFacility,getTextViewFacilityType,textViewdov;
        LinearLayout linearLayout_download, linearLayout_record_data;
        CardView cardView;
        public MyHolder(View itemView) {
            super(itemView);
           // textViewSession=itemView.findViewById(R.id.tv_listItem);
            textViewFacility=itemView.findViewById(R.id.tv_listItemFacility);
            getTextViewFacilityType=itemView.findViewById(R.id.tv_listItemFacilityType);
            textViewdov=itemView.findViewById(R.id.tv_listItemDov);
            cardView=itemView.findViewById(R.id.history_card);

            linearLayout_download=itemView.findViewById(R.id.linearLayout_download);
            linearLayout_record_data=itemView.findViewById(R.id.linearLayout_record_data);
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
}
