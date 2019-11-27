package com.dakshata.mentor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
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
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.dakshata.NewCompetencyTracking;
import com.dakshata.mentor.Utils.MentorConstant;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.dakshata.mentor.JhpiegoDatabase.COL_ID;
import static com.dakshata.mentor.JhpiegoDatabase.COL_IS_ASSESSMENT;
import static com.dakshata.mentor.JhpiegoDatabase.COL_SESSION;
import static com.dakshata.mentor.JhpiegoDatabase.COL_USERNAME;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME1;

/**
 * Created by Aditya.v on 15-12-2017.
 */

public class MainForm_Recycler_Adapter extends RecyclerView.Adapter<MainForm_Recycler_Adapter.MyViewHolder> {
    View view;
    private int lastPosition = -1;
    SharedPreferences sh_Pref;
    SharedPreferences.Editor editor;
    String username;
    JhpiegoDatabase jhpiegoDatabase;
    Cursor cursor;
    public String sessionNew= "";
    private String label_1_total = "7", label_2_total = "20", label_3_total = "20", label_4_total = "26", label_5_total = "19", label_6_total = "19",
            label_7_total = "4", label_8_total = "1", label_9_total = "1",label_10_total = "10",label_11_total = "3";

    public MainForm_Recycler_Adapter(Context context, List<FormDetails> formDetails, JhpiegoDatabase jhpiegoDatabase) {
        this.context = context;
        this.formDetails = formDetails;
        this.jhpiegoDatabase = jhpiegoDatabase;

    }

    public MainForm_Recycler_Adapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    Context context;
    List<FormDetails> formDetails;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.recycler_cardview_item,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        jhpiegoDatabase=new JhpiegoDatabase(context);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position)  {
        sh_Pref = context.getSharedPreferences("Login Credentials", MODE_PRIVATE);
        editor = sh_Pref.edit();

        username = sh_Pref.getString("Username", "Unknown");
        final SQLiteDatabase sqLiteDatabase=jhpiegoDatabase.getReadableDatabase();

        FormDetails detailForm=formDetails.get(position);
            holder.textViewFromName.setText(detailForm.getFormName());
            try {
                if (position==0){
//                    if (detailForm.getCount().equalsIgnoreCase(label_1_total)) MentorConstant.isFormMentorVisitFilled = true;
//                    else MentorConstant.isFormMentorVisitFilled = false;
                    holder.textView.setText(detailForm.getCount()+"/" + label_1_total);
                    setBlockItemColor(holder, detailForm, label_1_total, holder.textViewFromName, holder.textView, holder.imageView, holder.answered);
                }
                if (position==1){
//                    if (detailForm.getCount().equalsIgnoreCase(label_2_total)) MentorConstant.isFormLaborRoomStaffFilled = true;
//                    else MentorConstant.isFormLaborRoomStaffFilled = false;
                    holder.textView.setText(detailForm.getCount()+"/" + label_2_total);
                    setBlockItemColor(holder, detailForm, label_2_total, holder.textViewFromName, holder.textView, holder.imageView, holder.answered);
                }
                if (position==2){
//                    if (detailForm.getCount().equalsIgnoreCase(label_3_total)) MentorConstant.isFormInfrastructureFilled = true;
//                    else MentorConstant.isFormInfrastructureFilled = false;
                    holder.textView.setText(detailForm.getCount()+"/" + label_3_total);
                    setBlockItemColor(holder, detailForm, label_3_total, holder.textViewFromName, holder.textView, holder.imageView, holder.answered);
                }
                if (position==3){
//                    if (detailForm.getCount().equalsIgnoreCase(label_4_total)) MentorConstant.isFormEssentialRoomFilled = true;
//                    else MentorConstant.isFormEssentialRoomFilled = false;
                    holder.textView.setText(detailForm.getCount()+"/" + label_4_total);
                    setBlockItemColor(holder, detailForm, label_4_total, holder.textViewFromName, holder.textView, holder.imageView, holder.answered);
                }
                if (position==4){
//                    if (detailForm.getCount().equalsIgnoreCase(label_5_total)) MentorConstant.isFormAdherenceFilled = true;
//                    else MentorConstant.isFormAdherenceFilled = false;
                    holder.textView.setText(detailForm.getCount()+"/" + label_5_total);
                    setBlockItemColor(holder, detailForm, label_5_total, holder.textViewFromName, holder.textView, holder.imageView, holder.answered);
                }
                if (position==5){
//                    if (detailForm.getCount().equalsIgnoreCase(label_6_total)) MentorConstant.isFormTopicsCoveredFilled = true;
//                    else MentorConstant.isFormTopicsCoveredFilled = false;
                    holder.textView.setText(detailForm.getCount()+"/" + label_6_total);
                    setBlockItemColor(holder, detailForm, label_6_total, holder.textViewFromName, holder.textView, holder.imageView, holder.answered);
                }
                if (position==6){
//                    if (detailForm.getCount().equalsIgnoreCase(label_7_total)) MentorConstant.isFormRecordingAndReportingFilled = true;
//                    else MentorConstant.isFormRecordingAndReportingFilled = false;
                    holder.textView.setText(detailForm.getCount()+"/" + label_7_total);
                    setBlockItemColor(holder, detailForm, label_7_total, holder.textViewFromName, holder.textView, holder.imageView, holder.answered);
                }
                if (position==7){
//                    if (detailForm.getCount().equalsIgnoreCase(label_8_total)) MentorConstant.isFormNextVisitPlannedFilled = true;
//                    else MentorConstant.isFormNextVisitPlannedFilled = false;
                    holder.textView.setText(detailForm.getCount()+"/" + label_8_total);
                    setBlockItemColor(holder, detailForm, label_8_total, holder.textViewFromName, holder.textView, holder.imageView, holder.answered);
                }
                if (position==8){
//                    if (detailForm.getCount().equalsIgnoreCase(label_9_total)) MentorConstant.isFormRemarksFilled = true;
//                    else MentorConstant.isFormRemarksFilled = false;
                    holder.textView.setText(detailForm.getCount()+"/" + label_9_total);
                    setBlockItemColor(holder, detailForm, label_9_total, holder.textViewFromName, holder.textView, holder.imageView, holder.answered);
                }if (position==9){
//                    if (detailForm.getCount().equalsIgnoreCase(label_9_total)) MentorConstant.isFormRemarksFilled = true;
//                    else MentorConstant.isFormRemarksFilled = false;
                    holder.textView.setText(detailForm.getCount()+"/" + label_10_total);
                    setBlockItemColor(holder, detailForm, label_10_total, holder.textViewFromName, holder.textView, holder.imageView, holder.answered);
                }if (position==10){
//                    if (detailForm.getCount().equalsIgnoreCase(label_9_total)) MentorConstant.isFormRemarksFilled = true;
//                    else MentorConstant.isFormRemarksFilled = false;
                    holder.textView.setText(detailForm.getCount()+"/" + label_11_total);
                    setBlockItemColor(holder, detailForm, label_11_total, holder.textViewFromName, holder.textView, holder.imageView, holder.answered);
                }
            }catch (Exception e){}


        Glide.with(context).load(detailForm.getFormImage()).into(holder.imageViewFormImage);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
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

                SharedPreferences sharedPreferences = context.getSharedPreferences("dov" + username, MODE_PRIVATE);
                String isRecordExist = sharedPreferences.getString("isRecordExist", "no");

                MentorConstant.whichBlockCalled = false;
                switch (position){
                    case 0:
                        Intent intent0=new Intent(context,DetailsOfVisit.class);
                        intent0.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent0);
                        break;
                    case 1:
                        if (isRecordExist.equalsIgnoreCase("yes")) {
                            Intent intent1 = new Intent(context, StaffMaternity.class);
                            intent1.putExtra("sessionNew", sessionNew);
                            context.startActivity(intent1);
                        }else {
                            Toast.makeText(context, context.getString(R.string.alert_please_fill_visit_detail),Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        if (isRecordExist.equalsIgnoreCase("yes")) {
                            Intent intent2=new Intent(context,Infrastructure.class);
                            intent2.putExtra("sessionNew", sessionNew);
                            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent2.putExtra(Settings.EXTRA_AUTHORITIES, new String[]{"com.dakshata.mentor"});
                            context.startActivity(intent2);
                        }else {
                            Toast.makeText(context,context.getString(R.string.alert_please_fill_visit_detail),Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 3:
                        if (isRecordExist.equalsIgnoreCase("yes")) {
                            Intent intent3=new Intent(context,DrugsSupply.class);
                            intent3.putExtra("sessionNew", sessionNew);
                            intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent3);
                        }else {
                            Toast.makeText(context,context.getString(R.string.alert_please_fill_visit_detail),Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 4:
                        if (isRecordExist.equalsIgnoreCase("yes")) {
                            Intent intent4=new Intent(context,ClinicalStandards.class);
                            intent4.putExtra("sessionNew", sessionNew);
                            intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent4);
                        }else {
                            Toast.makeText(context,context.getString(R.string.alert_please_fill_visit_detail),Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 5:
                        if (isRecordExist.equalsIgnoreCase("yes")) {
                            Intent intent5=new Intent(context,MentoringPractices.class);
                            intent5.putExtra("sessionNew", sessionNew);
                            intent5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent5);
                        }else {
                            Toast.makeText(context, context.getString(R.string.alert_please_fill_visit_detail),Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 6:
                        if (isRecordExist.equalsIgnoreCase("yes")) {
                            Intent intent6=new Intent(context,RecprdingRoporting.class);
                            intent6.putExtra("sessionNew", sessionNew);
                            context.startActivity(intent6);
                        }else {
                            Toast.makeText(context,context.getString(R.string.alert_please_fill_visit_detail),Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 7:
                        if (isRecordExist.equalsIgnoreCase("yes")) {
                            Intent intent7=new Intent(context,NextVisitDate.class);
                            intent7.putExtra("sessionNew", sessionNew);
                            context.startActivity(intent7);
                        }else {
                            Toast.makeText(context,context.getString(R.string.alert_please_fill_visit_detail),Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 8:
                        /*cursor = sqLiteDatabase.query(TABLENAME1, new String[]{COL_USERNAME,COL_SESSION}, COL_USERNAME + "=?", new String[]{username}, null, null, null);
                        if (cursor.getCount()>0){
                            cursor.moveToLast();
                            String session=cursor.getString(cursor.getColumnIndex(COL_SESSION));
                            boolean submit=jhpiegoDatabase.LastVisitSubmitted(session);
                            if (isRecordExist.equalsIgnoreCase("yes")) {
                                Intent intent8=new Intent(context,CommentsRemarks.class);
                                intent8.putExtra("sessionNew", sessionNew);
                                context.startActivity(intent8);
                            }else {
                                Toast.makeText(context,context.getString(R.string.alert_please_fill_visit_detail),Toast.LENGTH_SHORT).show();
                            }

                        }else {
                            Toast.makeText(context,context.getString(R.string.alert_please_fill_visit_detail),Toast.LENGTH_SHORT).show();
                        }*/
                        if (isRecordExist.equalsIgnoreCase("yes")) {
                            Intent intent1 = new Intent(context, CommentsRemarks.class);
                            intent1.putExtra("sessionNew", sessionNew);
                            context.startActivity(intent1);
                        }else {
                            Toast.makeText(context, context.getString(R.string.alert_please_fill_visit_detail),Toast.LENGTH_SHORT).show();
                        }
                        break;
                        case 9:
                        if (isRecordExist.equalsIgnoreCase("yes")) {
                            Intent intent1 = new Intent(context, NewCompetencyTracking.class);
                            intent1.putExtra("sessionNew", sessionNew);
                            context.startActivity(intent1);
                        }else {
                            Toast.makeText(context, context.getString(R.string.alert_please_fill_visit_detail),Toast.LENGTH_SHORT).show();
                        }
                        break;
                        case 10:
                        if (isRecordExist.equalsIgnoreCase("yes")) {
                            Intent intent1 = new Intent(context, ClientFeedBackForm.class);
                            intent1.putExtra("sessionNew", sessionNew);
                            context.startActivity(intent1);
                        }else {
                            Toast.makeText(context, context.getString(R.string.alert_please_fill_visit_detail),Toast.LENGTH_SHORT).show();
                        }
                        break;
                }

            }
        });

        setAnimation(holder.itemView, position);
    }

    private void setBlockItemColor(MyViewHolder holder, FormDetails detailForm, String label_1_total, TextView textViewFromName,
                                   TextView textView, ImageView imageView, TextView answered) {
        if (detailForm.getCount().equals(label_1_total)) {
            holder.linearItemBlock.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
            textViewFromName.setTextColor(context.getResources().getColor(R.color.white));
            textView.setTextColor(context.getResources().getColor(R.color.white));
            answered.setTextColor(context.getResources().getColor(R.color.white));
            imageView.setColorFilter(context.getResources().getColor(R.color.white));
        } else if (!detailForm.getCount().equalsIgnoreCase("0") && !detailForm.getCount().equals(label_1_total)) {
            holder.linearItemBlock.setBackgroundColor(context.getResources().getColor(R.color.colorOrange));
            textViewFromName.setTextColor(context.getResources().getColor(R.color.white));
            textView.setTextColor(context.getResources().getColor(R.color.white));
            answered.setTextColor(context.getResources().getColor(R.color.white));
            imageView.setColorFilter(context.getResources().getColor(R.color.white));
        } else {
            holder.linearItemBlock.setBackgroundColor(context.getResources().getColor(R.color.white));
            textViewFromName.setTextColor(context.getResources().getColor(R.color.black));
            textView.setTextColor(context.getResources().getColor(R.color.black));
            answered.setTextColor(context.getResources().getColor(R.color.black));
            imageView.setColorFilter(context.getResources().getColor(R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return formDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewFromName,textView, answered;
        ImageView imageViewFormImage, imageView;
        CardView cardView;
        LinearLayout linearLayout, linearItemBlock;
        public MyViewHolder(View itemView) {
            super(itemView);
            context=itemView.getContext();
            textViewFromName=itemView.findViewById(R.id.texview_formName);
            textView=itemView.findViewById(R.id.tv_count);
            answered=itemView.findViewById(R.id.answered);
            imageViewFormImage=itemView.findViewById(R.id.image_form);
            imageView=itemView.findViewById(R.id.imageView);
            cardView=itemView.findViewById(R.id.jhpiego_card);
            linearLayout=itemView.findViewById(R.id.count_layout);
            linearItemBlock=itemView.findViewById(R.id.linearItemBlock);
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
