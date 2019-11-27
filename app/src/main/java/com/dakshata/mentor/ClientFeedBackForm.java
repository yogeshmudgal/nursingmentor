package com.dakshata.mentor;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dakshata.mentor.Utils.MentorConstant;
import com.dakshata.mentor.models.ClientFeedbackDto;
import com.dakshata.mentor.models.QuestListDto;
import com.dakshata.mentor.mydatecalendar.GlobalMethods;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dakshata.mentor.JhpiegoDatabase.*;
import static com.dakshata.mentor.JhpiegoDatabase.COL_1;
import static com.dakshata.mentor.JhpiegoDatabase.COL_2;
import static com.dakshata.mentor.JhpiegoDatabase.COL_3;
import static com.dakshata.mentor.JhpiegoDatabase.COL_4;
import static com.dakshata.mentor.JhpiegoDatabase.COL_ACP;
import static com.dakshata.mentor.JhpiegoDatabase.COL_AMTSL;
import static com.dakshata.mentor.JhpiegoDatabase.COL_AUA;
import static com.dakshata.mentor.JhpiegoDatabase.COL_CND;
import static com.dakshata.mentor.JhpiegoDatabase.COL_DND;
import static com.dakshata.mentor.JhpiegoDatabase.COL_DNR;
import static com.dakshata.mentor.JhpiegoDatabase.COL_DPE;
import static com.dakshata.mentor.JhpiegoDatabase.COL_DPPH;
import static com.dakshata.mentor.JhpiegoDatabase.COL_ENBC;
import static com.dakshata.mentor.JhpiegoDatabase.COL_GA;
import static com.dakshata.mentor.JhpiegoDatabase.COL_IMPE;
import static com.dakshata.mentor.JhpiegoDatabase.COL_IMPO;
import static com.dakshata.mentor.JhpiegoDatabase.COL_LP;
import static com.dakshata.mentor.JhpiegoDatabase.COL_NR;
import static com.dakshata.mentor.JhpiegoDatabase.COL_PP1;
import static com.dakshata.mentor.JhpiegoDatabase.COL_PPH;
import static com.dakshata.mentor.JhpiegoDatabase.COL_PV;
import static com.dakshata.mentor.JhpiegoDatabase.COL_ROLR;
import static com.dakshata.mentor.JhpiegoDatabase.COL_SCN;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME10;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME11;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME9;

/**
 * Created by Rakesh Prajapat on 2019-05-30
 * Copyright (c) 2019. All rights reserved by mobikode studio.
 * Last modified 11:31
 */
public class ClientFeedBackForm extends AppCompatActivity implements View.OnClickListener {

    private int FilledForm =0;
    String username;
    int count =0;
    String sessionid;
    SharedPreferences sh_Pref, sharedPreferences, sharedPreferencescount;
    SharedPreferences.Editor editor, editor1, editorcount;
    JhpiegoDatabase jhpiegoDatabase;
    private ArrayList<ClientFeedbackDto> data = new ArrayList<>();
    private RelativeLayout hintLayout,formLayout;
    private TextView textViewHeaderName,tvHint;
    private ImageView back;
    private Toolbar toolbar;
    private Button btn_save;
    private EditText et_IPDNo,et_PCTSId;
    private TextView tvQuestion1,tvQuestion2,tvQuestion3,tvQuestion4,tvQuestion5,tvQuestion6,tvQuestion7;
    private RadioGroup rGroup1,rGroup2,rGroup3,rGroup4,rGroup5,rGroup6,rGroup7;
    private RadioButton rBtnOV,rBtnHA,rBtn2Good,rBtn2HBad,rBtn3Yes,rBtn3No,rBtn4Yes,rBtn4No,rBtn5Yes,rBtn5No,rBtn6Yes,rBtn6No;
    private String ans1,ans2,ans3,ans4,ans5,ans6,ans7;
    private ClientFeedbackDto clientFeedbackDto;
    private int clientSelected;
    private String ansJson;
    private int selectedPosition;
    private Button btn_submit;
    private RelativeLayout rlC1,rlC2,rlC3;
    private TextView tvCBclient1,tvCBclient2,tvCBclient3,tv1underline,tv2underline,tv3underline;
    private String fileName;
    private ImageButton ibDownload;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_feedback);
        initViews();
    }

    private void initViews() {
        jhpiegoDatabase = new JhpiegoDatabase(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.menu_image_opt3));
        setSupportActionBar(toolbar);
        back = (ImageView) findViewById(R.id.back);
        ibDownload=(ImageButton)findViewById(R.id.ibDownload);

        tvCBclient1 = findViewById(R.id.tvCBclient1);
        tvCBclient2 = findViewById(R.id.tvCBclient2);
        tvCBclient3 = findViewById(R.id.tvCBclient3);
        tv1underline = findViewById(R.id.tv1underline);
        tv2underline = findViewById(R.id.tv2underline);
        tv3underline = findViewById(R.id.tv3underline);
        rlC1 =findViewById(R.id.rlC1);
        rlC2 =findViewById(R.id.rlC2);
        rlC3 =findViewById(R.id.rlC3);

        btn_save = findViewById(R.id.btn_save);
        et_IPDNo = findViewById(R.id.et_IPDNo);
        et_PCTSId = findViewById(R.id.et_PCTSId);
        tvQuestion1 = findViewById(R.id.tvQuestion1);
        tvQuestion2 = findViewById(R.id.tvQuestion2);
        tvQuestion3 = findViewById(R.id.tvQuestion3);
        tvQuestion4 = findViewById(R.id.tvQuestion4);
        tvQuestion5 = findViewById(R.id.tvQuestion5);
        tvQuestion6 = findViewById(R.id.tvQuestion6);
        tvQuestion7 = findViewById(R.id.tvQuestion7);

        rGroup1 = findViewById(R.id.rGroup1);
        rGroup2 = findViewById(R.id.rGroup2);
        rGroup3 = findViewById(R.id.rGroup3);
        rGroup4 = findViewById(R.id.rGroup4);
        rGroup5 = findViewById(R.id.rGroup5);
        rGroup6 = findViewById(R.id.rGroup6);
        rGroup7 = findViewById(R.id.rGroup7);

        rBtnOV = findViewById(R.id.rBtnOV);
        rBtnHA = findViewById(R.id.rBtnHA);
        rBtn2Good = findViewById(R.id.rBtn2Good);
        rBtn2HBad = findViewById(R.id.rBtn2HBad);
        rBtn3Yes = findViewById(R.id.rBtn3Yes);
        rBtn3No = findViewById(R.id.rBtn3No);
        rBtn4Yes = findViewById(R.id.rBtn4Yes);
        rBtn4No = findViewById(R.id.rBtn4No);
        rBtn5Yes = findViewById(R.id.rBtn5Yes);
        rBtn5No = findViewById(R.id.rBtn5No);
        rBtn6Yes = findViewById(R.id.rBtn6Yes);
        rBtn6No = findViewById(R.id.rBtn6No);

        btn_submit = findViewById(R.id.btn_submit);
        tvHint = findViewById(R.id.tvHint);
        btn_submit.setOnClickListener(this);
        if(data!=null&&data.size()>0){
            btn_submit.setVisibility(View.VISIBLE);
            tvHint.setText("");
        }else {
            btn_submit.setVisibility(View.GONE);
        }

        back.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        textViewHeaderName = (TextView) findViewById(R.id.header_name);
        textViewHeaderName.setText("Client Feedback Form (To be filled in PNC Ward)");
        hintLayout = findViewById(R.id.hintLayout);
        formLayout = findViewById(R.id.formLayout);

        sessionid = getIntent().getStringExtra("sessionNew");
        sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
        editor = sh_Pref.edit();
        username = sh_Pref.getString("Username", "Unknown");
        count = Integer.parseInt(sh_Pref.getString("count","0"));
        formLayout.setVisibility(View.GONE);
        rlC1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                clientSelected =1;
                hintLayout.setVisibility(View.GONE);
                formLayout.setVisibility(View.VISIBLE);
                tv1underline.setBackgroundColor(Color.parseColor("#FF4081"));
                tv2underline.setBackgroundColor(0);
                tv3underline.setBackgroundColor(0);

                if(data!=null&&data.size()>=1&&data.get(0).getSelected_client()==1){
                    selectedPosition=1;
                    openBlankFormDataLayout(false,data.get(0));
                }
                else {
                    openBlankFormDataLayout(true,null);
                }
            }
        });

        rlC2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((data!=null&&FilledForm>=1)||(data!=null&&data.size()==2)){
                    clientSelected =2;
                    hintLayout.setVisibility(View.GONE);
                    formLayout.setVisibility(View.VISIBLE);
                    tv2underline.setBackgroundColor(Color.parseColor("#FF4081"));
                    tv1underline.setBackgroundColor(0);
                    tv3underline.setBackgroundColor(0);
                    if(data!=null&&data.size()>=2&&data.get(1).getSelected_client()==2){
                        selectedPosition=2;
                        openBlankFormDataLayout(false,data.get(1));
                    }
                    else {
                        openBlankFormDataLayout(true,null);
                    }

                }
                else {

                    Toast.makeText(ClientFeedBackForm.this,"Please fill first client 1 feedback Form"
                        ,Toast.LENGTH_LONG).show();
                }


            }
        });
        rlC3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((data!=null&&FilledForm>=2)||(data!=null&&data.size()==3)){
                    clientSelected =3;
                    hintLayout.setVisibility(View.GONE);
                    formLayout.setVisibility(View.VISIBLE);
                    tv3underline.setBackgroundColor(Color.parseColor("#FF4081"));
                    tv2underline.setBackgroundColor(0);
                    tv1underline.setBackgroundColor(0);
                    if(data!=null&&data.size()>=3&&data.get(2).getSelected_client()==3){
                        selectedPosition=3;
                        openBlankFormDataLayout(false,data.get(2));
                    }
                    else {
                        openBlankFormDataLayout(true,null);
                    }
                }else {Toast.makeText(ClientFeedBackForm.this,"Please fill first client 1 feedback Form"
                        ,Toast.LENGTH_LONG).show();}



            }
        });


        ibDownload.setVisibility(View.GONE);

        ansJson=jhpiegoDatabase.getClientfeedbackData(sessionid);
        data= new Gson().fromJson(ansJson, new TypeToken<List<ClientFeedbackDto>>(){}.getType());
        if(data==null){
            data = new ArrayList<>();
        }

       if(data!=null && data.size()>0){
           if(data.size()==3) {
               tvCBclient1.setBackgroundResource(R.drawable.filled_bg);
               tvCBclient2.setBackgroundResource(R.drawable.filled_bg);
               tvCBclient3.setBackgroundResource(R.drawable.filled_bg);
           }
           if(data.size()==2) {
               tvCBclient1.setBackgroundResource(R.drawable.filled_bg);
               tvCBclient2.setBackgroundResource(R.drawable.filled_bg);
           }

           if(data.size()==1) {
               tvCBclient1.setBackgroundResource(R.drawable.filled_bg);
           }
           FilledForm=data.size();
           clientSelected =1;
           hintLayout.setVisibility(View.GONE);
           formLayout.setVisibility(View.VISIBLE);
           tv1underline.setBackgroundColor(Color.parseColor("#FF4081"));
           tv2underline.setBackgroundColor(0);
           tv3underline.setBackgroundColor(0);
           if(data.size()>=1&&data.get(0).getSelected_client()==1){
               selectedPosition=1;
               openBlankFormDataLayout(false,data.get(0));
           }
           else {
               openBlankFormDataLayout(true,null);
           }

           ibDownload.setVisibility(View.VISIBLE);
       }
       else {
           openBlankFormDataLayout(true,null);
       }


        ibDownload.setOnClickListener(this);

    }

    private void openBlankFormDataLayout(boolean isSelected,ClientFeedbackDto dto) {
        if(isSelected){
            et_IPDNo.setText("");
            et_PCTSId.setText("");
            rGroup1.clearCheck();
            rGroup2.clearCheck();
            rGroup3.clearCheck();
            rGroup4.clearCheck();
            rGroup5.clearCheck();
            rGroup6.clearCheck();
            rGroup7.clearCheck();
        }
        else {
            et_IPDNo.setText(dto.getClient_ipd_no());
            et_PCTSId.setText(dto.getClient_pcts_id());
            if(dto!=null&&dto.getQuestionList().get(0).getqAnswer().equalsIgnoreCase("Own Vehicle")){
                ((RadioButton)rGroup1.findViewById(R.id.rBtnOV)).setChecked(true);
            }
            else {
                ((RadioButton)rGroup1.findViewById(R.id.rBtnHA)).setChecked(true);
            }
            if(dto!=null&&dto.getQuestionList().get(1).getqAnswer().equalsIgnoreCase("Good")){
                rGroup2.check(R.id.rBtn2Good);
            }
            else {
                rGroup2.check(R.id.rBtn2HBad);
            }if(dto!=null&&dto.getQuestionList().get(2).getqAnswer().equalsIgnoreCase("Yes")){
                rGroup3.check(R.id.rBtn3Yes);
            }
            else {
                rGroup3.check(R.id.rBtn3No);
            }
            if(dto!=null&&dto.getQuestionList().get(3).getqAnswer().equalsIgnoreCase("Yes")){
                rGroup4.check(R.id.rBtn4Yes);
            }
            else {
                rGroup4.check(R.id.rBtn4No);
            }
            if(dto!=null&&dto.getQuestionList().get(4).getqAnswer().equalsIgnoreCase("Yes")){
                rGroup5.check(R.id.rBtn5Yes);
            }
            else {
                rGroup5.check(R.id.rBtn5No);
            }
            if(dto!=null&&dto.getQuestionList().get(5).getqAnswer().equalsIgnoreCase("Yes")){
                rGroup6.check(R.id.rBtn6Yes);
            }
            else {
                rGroup6.check(R.id.rBtn6No);
            }
            if(dto!=null&&dto.getQuestionList().get(6).getqAnswer().equalsIgnoreCase("Yes")){
                rGroup7.check(R.id.rBtn7Yes);
            }
            else {
                rGroup7.check(R.id.rBtn7No);
            }

        }

    }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.back:
              onBackPressed();
              break;
              case R.id.btn_save:
                  checkValidation();
              break;
              case R.id.btn_submit:
                  saveData();
                  break;
          case R.id.ibDownload:
              createPdf("","ClientFeedFormPdf");
              break;
      }
    }

    @SuppressLint("NewApi")
    private void checkValidation() {
        clientFeedbackDto = new ClientFeedbackDto();
        ans1= getSelectedRadioValue(ans1,rGroup1);
        ans2= getSelectedRadioValue(ans2,rGroup2);
        ans3= getSelectedRadioValue(ans3,rGroup3);
        ans4= getSelectedRadioValue(ans4,rGroup4);
        ans5= getSelectedRadioValue(ans5,rGroup5);
        ans6= getSelectedRadioValue(ans6,rGroup6);
        ans7= getSelectedRadioValue(ans7,rGroup7);
        if(clientSelected==0){
            ShowToastMessage("Please select client");
        }else if(et_IPDNo.getText().toString().length()==0){
            ShowToastMessage("Please Enter IPD No.");
        }else if(et_PCTSId.getText().toString().length()==0){
            ShowToastMessage("Please Enter PCTS Id.");
        }
        else if(ans1.length()==0){
            ShowToastMessage("Please select answer for "+tvQuestion1.getText());
        }else if(ans2.length()==0){
            ShowToastMessage("Please select answer for "+tvQuestion2.getText());
        }else if(ans3.length()==0){
            ShowToastMessage("Please select answer for "+tvQuestion3.getText());
        }else if(ans4.length()==0){
            ShowToastMessage("Please select answer for "+tvQuestion4.getText());
        }else if(ans5.length()==0){
            ShowToastMessage("Please select answer for "+tvQuestion5.getText());
        }else if(ans6.length()==0){
            ShowToastMessage("Please select answer for "+tvQuestion6.getText());
        }else if(ans7.length()==0){
            ShowToastMessage("Please select answer for "+tvQuestion7.getText());
        }
        else {
            clientFeedbackDto.setSelected_client(clientSelected);
            clientFeedbackDto.setClient_ipd_no(et_IPDNo.getText().toString());
            clientFeedbackDto.setClient_pcts_id(et_PCTSId.getText().toString());
            clientFeedbackDto.setQuestionList(new ArrayList<>());
            if(data.size()<clientSelected){
                clientFeedbackDto.getQuestionList().add(new ClientFeedbackDto(1,tvQuestion1.getText().toString(),ans1));
                clientFeedbackDto.getQuestionList().add(new ClientFeedbackDto(2,tvQuestion2.getText().toString(),ans2));
                clientFeedbackDto.getQuestionList().add(new ClientFeedbackDto(3,tvQuestion3.getText().toString(),ans3));
                clientFeedbackDto.getQuestionList().add(new ClientFeedbackDto(4,tvQuestion4.getText().toString(),ans4));
                clientFeedbackDto.getQuestionList().add(new ClientFeedbackDto(5,tvQuestion5.getText().toString(),ans5));
                clientFeedbackDto.getQuestionList().add(new ClientFeedbackDto(6,tvQuestion6.getText().toString(),ans6));
                clientFeedbackDto.getQuestionList().add(new ClientFeedbackDto(7,tvQuestion7.getText().toString(),ans7));
                clientFeedbackDto.setDateOfFeedback(GlobalMethods.getCurrentDate(this));
                data.add(clientFeedbackDto);
                FilledForm = data.size();
            }
            else {
                FilledForm = selectedPosition;
                clientFeedbackDto.getQuestionList().add(new ClientFeedbackDto(1,tvQuestion1.getText().toString(),ans1));
                clientFeedbackDto.getQuestionList().add(new ClientFeedbackDto(2,tvQuestion2.getText().toString(),ans2));
                clientFeedbackDto.getQuestionList().add(new ClientFeedbackDto(3,tvQuestion3.getText().toString(),ans3));
                clientFeedbackDto.getQuestionList().add(new ClientFeedbackDto(4,tvQuestion4.getText().toString(),ans4));
                clientFeedbackDto.getQuestionList().add(new ClientFeedbackDto(5,tvQuestion5.getText().toString(),ans5));
                clientFeedbackDto.getQuestionList().add(new ClientFeedbackDto(6,tvQuestion6.getText().toString(),ans6));
                clientFeedbackDto.getQuestionList().add(new ClientFeedbackDto(7,tvQuestion7.getText().toString(),ans7));
                clientFeedbackDto.setDateOfFeedback(GlobalMethods.getCurrentDate(this));
                data.get(selectedPosition-1).setQuestionList(clientFeedbackDto.getQuestionList());
                data.get(selectedPosition-1).setClient_ipd_no(et_IPDNo.getText().toString());
                data.get(selectedPosition-1).setClient_pcts_id(et_PCTSId.getText().toString());
                data.get(selectedPosition-1).setSelected_client(clientSelected);
            }


            Toast.makeText(this,"Added successfully",Toast.LENGTH_LONG).show();
            if(data.size()==1){
                if(count<3)
                count++;
                tvCBclient1.setBackground(getResources().getDrawable(R.drawable.filled_bg));
                formLayout.setVisibility(View.GONE);
                hintLayout.setVisibility(View.VISIBLE);
                btn_submit.setVisibility(View.VISIBLE);
                tvHint.setText("");
                //openAlertDialog("Do you want to add Feedback for Client 2 Also.");
            }else if(data.size()==2){
                if(count<3)
                count++;
                tvCBclient1.setBackground(getResources().getDrawable(R.drawable.filled_bg));
                tvCBclient2.setBackground(getResources().getDrawable(R.drawable.filled_bg));
                formLayout.setVisibility(View.GONE);
                hintLayout.setVisibility(View.VISIBLE);
                btn_submit.setVisibility(View.VISIBLE);
                tvHint.setText("");
                //openAlertDialog("Do you want to add Feedback for Client 3 Also.");
            }else if(data.size()==3){
                if(count<3)
                  count++;
                tvCBclient1.setBackground(getResources().getDrawable(R.drawable.filled_bg));
                tvCBclient2.setBackground(getResources().getDrawable(R.drawable.filled_bg));
                tvCBclient3.setBackground(getResources().getDrawable(R.drawable.filled_bg));
                formLayout.setVisibility(View.GONE);
                hintLayout.setVisibility(View.VISIBLE);
                btn_submit.setVisibility(View.VISIBLE);
                tvHint.setText("");
                //openAlertDialog("Do you want to save this record.");
            }

        }
    }



    private void saveData() {
        sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
        editor1 = sharedPreferences.edit();
        try {
            SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query(TABLENAME18, new String[]{COL_SESSION}, COL_SESSION + "=? AND " + COL_UNIQUE_ID + "=?", new String[]{sessionid, String.valueOf(MentorConstant.recordId)}, null, null, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                createJson();
//                            jhpiegoDatabase.deleteCommentsRemarks(username,comments, String.valueOf(count), sessionid,ansJson);
                long row = jhpiegoDatabase.addClientfeedbackData(username, String.valueOf(count), sessionid,ansJson, String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude), String.valueOf(MentorConstant.recordId), 1);
                jhpiegoDatabase.updateLatAndLongWithDetailsOfVisit(String.valueOf(MentorConstant.recordId), String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude));
                if (row != -1) {

                    Toast.makeText(getApplicationContext(), getString(R.string.alert_saved_successfully), Toast.LENGTH_SHORT).show();
                    if (!MentorConstant.whichBlockCalled) {
                        sharedPreferencescount = getSharedPreferences("cf" + username, MODE_PRIVATE);
                        editorcount = sharedPreferencescount.edit();
                        editorcount.putString("count" + username, String.valueOf(count));
                        editorcount.commit();
                    }
                    onBackPressed();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.alert_not_saved_to_db), Toast.LENGTH_SHORT).show();
                }
            } else {
                createJson();
                long row = jhpiegoDatabase.addClientfeedbackData(username, String.valueOf(count), sessionid,ansJson, String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude), String.valueOf(MentorConstant.recordId), 0);
                SharedPreferences sPref = getSharedPreferences("session", MODE_PRIVATE);
                jhpiegoDatabase.updateLatAndLongWithDetailsOfVisit(String.valueOf(MentorConstant.recordId), String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude));
                if (row != -1) {

                    Toast.makeText(getApplicationContext(), getString(R.string.alert_saved_successfully), Toast.LENGTH_SHORT).show();
                    if (!MentorConstant.whichBlockCalled) {
                        sharedPreferencescount = getSharedPreferences("cf" + username, MODE_PRIVATE);
                        editorcount = sharedPreferencescount.edit();
                        editorcount.putString("count" + username, String.valueOf(count));
                        editorcount.commit();
                    }
                    onBackPressed();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.alert_not_saved_to_db), Toast.LENGTH_SHORT).show();
                }
            }
            sqLiteDatabase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createJson() {
        ansJson =new Gson().toJson(data);
        System.out.println("ansJson:- "+ansJson);
    }


    private String getSelectedRadioValue(String ans,RadioGroup radioGroup) {

    int selectedId = radioGroup.getCheckedRadioButtonId();
    RadioButton radioButton = (RadioButton) findViewById(selectedId);
    if (radioButton==null) {
        ans = "";
    } else {
        ans = radioButton.getText().toString();
    }
return ans;
}

private void ShowToastMessage(String message){
    Toast.makeText(this,message,Toast.LENGTH_LONG).show();
}


private  SQLiteDatabase sqLiteDatabase;
    public static String mentorName, userEmail, lastVisitDate;
    // Create Pdf
    public void createPdf(String selectedSession, String fileName1) {

        try {
            jhpiegoDatabase = new JhpiegoDatabase(ClientFeedBackForm.this);
            sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SharedPreferences sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
        String username = sh_Pref.getString("Username", "Unknown");
        Cursor cursor = sqLiteDatabase.query(TABLENAME, new String[]{COL_USERNAME, COL_EMAIL, COL_MOBILE, COL_STATE, COL_NAME}, COL_USERNAME + "=?", new String[]{username}, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            mentorName = cursor.getString(cursor.getColumnIndex(COL_NAME));
            userEmail = cursor.getString(cursor.getColumnIndex(COL_EMAIL));
        }

        String facilityName="",facilityType="";
        Cursor cursor1 = sqLiteDatabase.query(TABLENAME1, new String[]{COL_ID,COL_SESSION, COL_NOM, COL_STATE, COL_DIST, COL_BLOCK, COL_FACILITYNAME, COL_FACILITYTYPE}, COL_ID + "="+MentorConstant.recordId,null, null, null, null);
        if (cursor1 != null && cursor1.getCount() > 0) {
            cursor1.moveToLast();
            facilityName = cursor1.getString(cursor1.getColumnIndex(COL_FACILITYNAME));
            facilityType = cursor1.getString(cursor1.getColumnIndex(COL_FACILITYTYPE));
        }
        this.fileName = facilityName+"_"+facilityType+"_feedback";

        try {
            new ClientFeedbackPdf(ClientFeedBackForm.this, fileName, "",data);
        } catch (Exception e) {
            new ClientFeedbackPdf(ClientFeedBackForm.this, fileName, "",data);
        }

    }

}
