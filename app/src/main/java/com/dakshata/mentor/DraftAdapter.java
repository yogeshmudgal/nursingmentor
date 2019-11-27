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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Umesh Kumar on 4/9/2018.
 */
public class DraftAdapter extends RecyclerView.Adapter<DraftAdapter.MyHolder> {


    View view;
    List<ListPojo> arrayList;
    String selectedSession;
    Cursor cursor;
    SQLiteDatabase sqLiteDatabase;
    JhpiegoDatabase jhpiegoDatabase;
    private int lastPosition = -1;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor1;
    public DraftAdapter(Context context, List<ListPojo> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
        this.draftActivity = (DraftActivity)context;
    }

    Context context;
    DraftActivity draftActivity;
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.list_item_sync,parent,false);
        MyHolder myHolder=new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        ListPojo listPojo=arrayList.get(position);
          // holder.textViewSession.setText(listPojo.getSession());
        holder.textViewFacility.setText(listPojo.getFacilityname());
        holder.getTextViewFacilityType.setText(listPojo.getFacilitytype());
        holder.textViewdov.setText(listPojo.getDov());

        String sessionID=listPojo.getFacilityname()+"-"+listPojo.getFacilitytype()+"-"+listPojo.getDov();
        holder.linearLayout_record_data.setTag(sessionID);
        holder.linearLayout_record_data.setTag(R.string.record_id, listPojo.getRecordId());
        holder.linearLayout_record_data.setTag(R.string.session_new, listPojo.getSession());
        holder.linearLayout_record_data.setTag(R.string.facilityNameKey, listPojo.getFacilityname());
        holder.linearLayout_record_data.setTag(R.string.facilityTypeKey, listPojo.getFacilitytype());

        holder.linearLayout_record_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPojo listPojoNew = arrayList.get(position);
                draftActivity.alertSave((String)v.getTag(), (String)v.getTag(R.string.facilityNameKey), (String)v.getTag(R.string.facilityTypeKey), (Integer) v.getTag(R.string.record_id), (String) v.getTag(R.string.session_new));
            }
        });

        holder.linearLayout_download.setTag(sessionID);
        holder.linearLayout_download.setTag(R.string.record_id, listPojo.getRecordId());
        holder.linearLayout_download.setTag(R.string.session_new, listPojo.getSession());
        holder.linearLayout_download.setTag(R.string.facilityNameKey, listPojo.getFacilityname());
        holder.linearLayout_download.setTag(R.string.facilityTypeKey, listPojo.getFacilitytype());

        holder.linearLayout_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListPojo listPojoNew = arrayList.get(position);
                draftActivity.alertSave((String)v.getTag(), (String)v.getTag(R.string.facilityNameKey), (String)v.getTag(R.string.facilityTypeKey), (Integer) v.getTag(R.string.record_id), (String) v.getTag(R.string.session_new));
            }
        });

        holder.iv_download_report.setImageResource(R.drawable.save_icon);
        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView textViewSession,textViewFacility,getTextViewFacilityType,textViewdov;
        LinearLayout linearLayout_download, linearLayout_record_data;
        ImageView iv_download_report;
        CardView cardView;
        public MyHolder(View itemView) {
            super(itemView);
            iv_download_report=itemView.findViewById(R.id.iv_download_report);
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
