package com.dakshata.mentor.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dakshata.NewCompetencyTracking;
import com.dakshata.mentor.JhpiegoDatabase;
import com.dakshata.mentor.R;
import com.dakshata.mentor.models.CompetencyTrackingDto;

import java.util.ArrayList;
import java.util.List;

import static com.dakshata.mentor.JhpiegoDatabase.COL_ID;
import static com.dakshata.mentor.JhpiegoDatabase.COL_IS_ASSESSMENT;
import static com.dakshata.mentor.JhpiegoDatabase.COL_SESSION;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME1;

/**
 * Created by Rakesh Prajapat on 2019-05-09
 * Copyright (c) 2019. All rights reserved by mobikode studio.
 * Last modified 12:01
 */
public class PACKAdapter extends RecyclerView.Adapter<PACKAdapter.MyViewHolder> {

    private ArrayList<CompetencyTrackingDto> dataList;
    private Context context;
    private JhpiegoDatabase jhpiegoDatabase;
    public String sessionNew= "";

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvPName,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,
                tv12,tv13,tv14;
        private LinearLayout mainLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            tvPName = itemView.findViewById(R.id.tvPName);
            tv3 = itemView.findViewById(R.id.tv3);
            tv4 = itemView.findViewById(R.id.tv4);
            tv5 = itemView.findViewById(R.id.tv5);
            tv6 = itemView.findViewById(R.id.tv6);
            tv7 = itemView.findViewById(R.id.tv7);
            tv8 = itemView.findViewById(R.id.tv8);
            tv9 = itemView.findViewById(R.id.tv9);
            tv10 = itemView.findViewById(R.id.tv10);
            tv11 = itemView.findViewById(R.id.tv11);
            tv12 = itemView.findViewById(R.id.tv12);
            tv13 = itemView.findViewById(R.id.tv13);
            tv14 = itemView.findViewById(R.id.tv14);
        }
    }


    public PACKAdapter(Context context, ArrayList<CompetencyTrackingDto> questionList) {
        this.context = context;
        this.dataList = questionList;
        jhpiegoDatabase=new JhpiegoDatabase(context);
    }


    @Override
    public PACKAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.new_form_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PACKAdapter.MyViewHolder holder, int position) {
        MyViewHolder rowViewHolder = (MyViewHolder) holder;

        holder.tvPName.setText(dataList.get(position).getPName());
        holder.tv3.setText(dataList.get(position).getCadre());
        holder.tv4.setText(dataList.get(position).getPaExam());
        holder.tv5.setText(dataList.get(position).getPvExam());
        holder.tv6.setText(dataList.get(position).getAMTSL());
        holder.tv7.setText(dataList.get(position).getNBR());
        holder.tv8.setText(dataList.get(position).getHandHygiene());
        holder.tv9.setText(dataList.get(position).getAntenatalComp());
        holder.tv10.setText(dataList.get(position).getPartograph());
        holder.tv11.setText(dataList.get(position).getPostnatalComp());
        holder.tv12.setText(dataList.get(position).getManagePretermBirth());
        holder.tv13.setText(dataList.get(position).getPNCCounseling());
        holder.tv14.setText(dataList.get(position).getOverall());
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase sqLiteDatabase1 = jhpiegoDatabase.getReadableDatabase();
                Cursor cursor1 = sqLiteDatabase1.query(TABLENAME1, new String[]{COL_ID, COL_SESSION}, COL_IS_ASSESSMENT + "=?", new String[]{"0"}, null, null, null);
                if (cursor1.getCount() > 0) {
                    cursor1.moveToFirst();
                    sessionNew = cursor1.getString(cursor1.getColumnIndex(COL_SESSION));
                } else {
                    sessionNew = "";
                }
                Intent intent1 = new Intent(context, NewCompetencyTracking.class);
                intent1.putExtra("sessionNew", sessionNew);
                intent1.putExtra("position", position);
                intent1.putExtra("list", dataList);
                context.startActivity(intent1);
                ((Activity)context).finish();
            }
        });



    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }
}