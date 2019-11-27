package com.dakshata.mentor;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dakshata.mentor.models.QuestListDto;
import com.google.gson.Gson;

import static com.dakshata.mentor.JhpiegoDatabase.COL_SESSION;
import static com.dakshata.mentor.JhpiegoDatabase.JSON_DATA;
import static com.dakshata.mentor.JhpiegoDatabase.TABLE_ClinicalStandards;

/**
 * Created by Aditya on 12/17/2017.
 */

public class ClinicalStandardsRetrComparision extends AppCompatActivity {
    TextView radioGroup1, radioGroup2, radioGroup3, radioGroup4, radioGroup5, radioGroup6, radioGroup7, radioGroup8, radioGroup9, radioGroup10, radioGroup11, radioGroup12, radioGroup13, radioGroup14, radioGroup15, radioGroup16, radioGroup17, radioGroup18, radioGroup19;

    TextView radioGroup1_pre, radioGroup2_pre, radioGroup3_pre, radioGroup4_pre, radioGroup5_pre, radioGroup6_pre, radioGroup7_pre, radioGroup8_pre, radioGroup9_pre, radioGroup10_pre, radioGroup11_pre, radioGroup12_pre, radioGroup13_pre, radioGroup14_pre, radioGroup15_pre, radioGroup16_pre, radioGroup17_pre, radioGroup18_pre, radioGroup19_pre;

    Button button_save, button_back;
    JhpiegoDatabase jhpiegoDatabase;
    SharedPreferences sh_Pref, sharedPreferences;
    SharedPreferences.Editor editor, editor1;
    Toolbar toolbar;
    ImageView back;
    TextView textViewHeaderName;
    String sessionID, facilityName, facilityType;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clinical_standerd_view_comparision);

        sessionID = getIntent().getStringExtra("session");
        facilityName = getIntent().getStringExtra("facilityName");
        facilityType = getIntent().getStringExtra("facilityType");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.menu_image_opt3));
        setSupportActionBar(toolbar);
        back = (ImageView) findViewById(R.id.back);
        textViewHeaderName = (TextView) findViewById(R.id.header_name);
        textViewHeaderName.setText(getString(R.string.section_e_header_1));
        jhpiegoDatabase = new JhpiegoDatabase(this);
        setTitle("  Clinical Practices");
        initViews();
        setViews();
        //getUserDisplay();
        getFormDataFromDb();
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initViews() {
        radioGroup1 = (TextView) findViewById(R.id.cs_rg1);
        radioGroup2 = (TextView) findViewById(R.id.cs_rg2);
        radioGroup3 = (TextView) findViewById(R.id.cs_rg3);
        radioGroup4 = (TextView) findViewById(R.id.cs_rg4);
        radioGroup5 = (TextView) findViewById(R.id.cs_rg5);
        radioGroup6 = (TextView) findViewById(R.id.cs_rg6);
        radioGroup7 = (TextView) findViewById(R.id.cs_rg7);
        radioGroup8 = (TextView) findViewById(R.id.cs_rg8);
        radioGroup9 = (TextView) findViewById(R.id.cs_rg9);
        radioGroup10 = (TextView) findViewById(R.id.cs_rg10);
        radioGroup11 = (TextView) findViewById(R.id.cs_rg11);
        radioGroup12 = (TextView) findViewById(R.id.cs_rg12);
        radioGroup13 = (TextView) findViewById(R.id.cs_rg13);
        radioGroup14 = (TextView) findViewById(R.id.cs_rg14);
        radioGroup15 = (TextView) findViewById(R.id.cs_rg15);
        radioGroup16 = (TextView) findViewById(R.id.cs_rg16);
        radioGroup17 = (TextView) findViewById(R.id.cs_rg17);
        radioGroup18 = (TextView) findViewById(R.id.cs_rg18);
        radioGroup19 = (TextView) findViewById(R.id.cs_rg19);
        button_save = (Button) findViewById(R.id.cs_save);
        button_back = (Button) findViewById(R.id.cs_back);

        radioGroup1_pre = (TextView) findViewById(R.id.cs_rg1_pre);
        radioGroup2_pre = (TextView) findViewById(R.id.cs_rg2_pre);
        radioGroup3_pre = (TextView) findViewById(R.id.cs_rg3_pre);
        radioGroup4_pre = (TextView) findViewById(R.id.cs_rg4_pre);
        radioGroup5_pre = (TextView) findViewById(R.id.cs_rg5_pre);
        radioGroup6_pre = (TextView) findViewById(R.id.cs_rg6_pre);
        radioGroup7_pre = (TextView) findViewById(R.id.cs_rg7_pre);
        radioGroup8_pre = (TextView) findViewById(R.id.cs_rg8_pre);
        radioGroup9_pre = (TextView) findViewById(R.id.cs_rg9_pre);
        radioGroup10_pre = (TextView) findViewById(R.id.cs_rg10_pre);
        radioGroup11_pre = (TextView) findViewById(R.id.cs_rg11_pre);
        radioGroup12_pre = (TextView) findViewById(R.id.cs_rg12_pre);
        radioGroup13_pre = (TextView) findViewById(R.id.cs_rg13_pre);
        radioGroup14_pre = (TextView) findViewById(R.id.cs_rg14_pre);
        radioGroup15_pre = (TextView) findViewById(R.id.cs_rg15_pre);
        radioGroup16_pre = (TextView) findViewById(R.id.cs_rg16_pre);
        radioGroup17_pre = (TextView) findViewById(R.id.cs_rg17_pre);
        radioGroup18_pre = (TextView) findViewById(R.id.cs_rg18_pre);
        radioGroup19_pre = (TextView) findViewById(R.id.cs_rg19_pre);
    }

    public void setViews() {

        button_save.setVisibility(View.GONE);

    }

    public void Pback(View view) {
        super.onBackPressed();

    }

    /* public void getUserDisplay(){
         try {
             sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
             editor = sh_Pref.edit();

             String username=sh_Pref.getString("Username","Unknown");
             SQLiteDatabase sqLiteDatabase=jhpiegoDatabase.getReadableDatabase();
             Cursor cursor=sqLiteDatabase.query(TABLENAME,new String[]{COL_USERNAME,COL_ID},COL_USERNAME + "=?",new String[]{username},null,null,null);
             if (cursor !=null && cursor.getCount()>0){
                 cursor.moveToFirst();
                 String id=cursor.getString(cursor.getColumnIndex(COL_ID));
                 String dbusername=cursor.getString(cursor.getColumnIndex(COL_USERNAME));
                 TextView textView1,textView2;
                 textView1= (TextView) findViewById(R.id.header_name);
                 textView2= (TextView) findViewById(R.id.header_id);
                 textView1.setText(dbusername);
                 textView2.setText("Mentor100"+id);
             }

         }catch (Exception e){}
     }*/
    public void getFormDataFromDb() {
        try {
//            sharedPreferences = getSharedPreferences("session Credentials", MODE_PRIVATE);
//            editor1=sharedPreferences.edit();
//            String sessionID=sharedPreferences.getString("session","");
            Log.v("session", "" + sessionID);
            SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query(TABLE_ClinicalStandards, new String[]{COL_SESSION, JSON_DATA}, COL_SESSION + "=?", new String[]{sessionID}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                String json_data = cursor.getString(cursor.getColumnIndex(JSON_DATA));
                QuestListDto questListDto = new Gson().fromJson(json_data, QuestListDto.class);
                String col_1, col_2, col_3, col_4, col_5, col_6, col_7, col_8, col_9, col_10, col_11, col_12, col_13, col_14, col_15, col_16, col_17, col_18, col_19;
                col_1 = col_2 = col_3 = col_4 = col_5 = col_6 = col_7 = col_8 = col_9 = col_10 = col_11 = col_12 = col_13 = col_14 = col_15 = col_16 = col_17 = col_18 = col_19 = "";
                for (int i = 0; i < questListDto.getQues_array().size(); i++) {
                    if (!questListDto.getQues_array().get(i).getQ_ans().equalsIgnoreCase("0")) {

                        switch (i + 1) {
                            case 1:
                                col_1 = questListDto.getQues_array().get(i).getQ_ans();
                                break;
                            case 2:
                                col_2 = questListDto.getQues_array().get(i).getQ_ans();
                                break;
                            case 3:
                                col_3 = questListDto.getQues_array().get(i).getQ_ans();
                                break;
                            case 4:
                                col_4 = questListDto.getQues_array().get(i).getQ_ans();
                                break;
                            case 5:
                                col_5 = questListDto.getQues_array().get(i).getQ_ans();
                                break;
                            case 6:
                                col_6 = questListDto.getQues_array().get(i).getQ_ans();
                                break;
                            case 7:
                                col_7 = questListDto.getQues_array().get(i).getQ_ans();
                                break;
                            case 8:
                                col_8 = questListDto.getQues_array().get(i).getQ_ans();
                                break;
                            case 9:
                                col_9 = questListDto.getQues_array().get(i).getQ_ans();
                                break;
                            case 10:
                                col_10 = questListDto.getQues_array().get(i).getQ_ans();
                                break;
                            case 11:
                                col_11 = questListDto.getQues_array().get(i).getQ_ans();
                                break;
                            case 12:
                                col_12 = questListDto.getQues_array().get(i).getQ_ans();
                                break;
                            case 13:
                                col_13 = questListDto.getQues_array().get(i).getQ_ans();
                                break;
                            case 14:
                                col_14 = questListDto.getQues_array().get(i).getQ_ans();
                                break;
                            case 15:
                                col_15 = questListDto.getQues_array().get(i).getQ_ans();
                                break;
                            case 16:
                                col_16 = questListDto.getQues_array().get(i).getQ_ans();
                                break;
                            case 17:
                                col_17 = questListDto.getQues_array().get(i).getQ_ans();
                                break;
                            case 18:
                                col_18 = questListDto.getQues_array().get(i).getQ_ans();
                                break;
                            case 19:
                                col_19 = questListDto.getQues_array().get(i).getQ_ans();
                                break;
                        }
                    }
                }

                radioGroup1.setText(col_1);
                setColorForText(radioGroup1, col_1);
                radioGroup2.setText(col_2);
                setColorForText(radioGroup2, col_2);
                radioGroup3.setText(col_3);
                setColorForText(radioGroup3, col_3);
                radioGroup4.setText(col_4);
                setColorForText(radioGroup4, col_4);
                radioGroup5.setText(col_5);
                setColorForText(radioGroup5, col_5);
                radioGroup6.setText(col_6);
                setColorForText(radioGroup6, col_6);
                radioGroup7.setText(col_7);
                setColorForText(radioGroup7, col_7);
                radioGroup8.setText(col_8);
                setColorForText(radioGroup8, col_8);
                radioGroup9.setText(col_9);
                setColorForText(radioGroup9, col_9);
                radioGroup10.setText(col_10);
                setColorForText(radioGroup10, col_10);
                radioGroup11.setText(col_11);
                setColorForText(radioGroup11, col_11);
                radioGroup12.setText(col_12);
                setColorForText(radioGroup12, col_12);
                radioGroup13.setText(col_13);
                setColorForText(radioGroup13, col_13);
                radioGroup14.setText(col_14);
                setColorForText(radioGroup14, col_14);
                radioGroup15.setText(col_15);
                setColorForText(radioGroup15, col_15);
                radioGroup16.setText(col_16);
                setColorForText(radioGroup16, col_16);
                radioGroup17.setText(col_17);
                setColorForText(radioGroup17, col_17);
                radioGroup18.setText(col_18);
                setColorForText(radioGroup18, col_18);
                radioGroup19.setText(col_19);
                setColorForText(radioGroup19, col_19);
            }

            Cursor cursorSessionId = sqLiteDatabase.rawQuery("SELECT session from detailsofvisit WHERE facilityname = '" + facilityName + "' AND facilitytype = '" + facilityType + "' ORDER BY id DESC ", null);
            if (cursorSessionId != null && cursorSessionId.getCount() > 1) {
                cursorSessionId.moveToFirst();
                cursorSessionId.moveToNext();
                String lastRecordSessionId = cursorSessionId.getString(0);
                Cursor cursor_pre = sqLiteDatabase.query(TABLE_ClinicalStandards, new String[]{COL_SESSION, JSON_DATA}, COL_SESSION + "=?", new String[]{lastRecordSessionId}, null, null, null, null);
                if (cursor_pre != null && cursor_pre.getCount() > 0) {
                    cursor_pre.moveToFirst();
                    String json_data = cursor.getString(cursor.getColumnIndex(JSON_DATA));
                    QuestListDto questListDto = new Gson().fromJson(json_data, QuestListDto.class);
                    String col_1, col_2, col_3, col_4, col_5, col_6, col_7, col_8, col_9, col_10, col_11, col_12, col_13, col_14, col_15, col_16, col_17, col_18, col_19;
                    col_1 = col_2 = col_3 = col_4 = col_5 = col_6 = col_7 = col_8 = col_9 = col_10 = col_11 = col_12 = col_13 = col_14 = col_15 = col_16 = col_17 = col_18 = col_19 = "";
                    for (int i = 0; i < questListDto.getQues_array().size(); i++) {
                        if (!questListDto.getQues_array().get(i).getQ_ans().equalsIgnoreCase("0")) {

                            switch (i + 1) {
                                case 1:
                                    col_1 = questListDto.getQues_array().get(i).getQ_ans();
                                    break;
                                case 2:
                                    col_2 = questListDto.getQues_array().get(i).getQ_ans();
                                    break;
                                case 3:
                                    col_3 = questListDto.getQues_array().get(i).getQ_ans();
                                    break;
                                case 4:
                                    col_4 = questListDto.getQues_array().get(i).getQ_ans();
                                    break;
                                case 5:
                                    col_5 = questListDto.getQues_array().get(i).getQ_ans();
                                    break;
                                case 6:
                                    col_6 = questListDto.getQues_array().get(i).getQ_ans();
                                    break;
                                case 7:
                                    col_7 = questListDto.getQues_array().get(i).getQ_ans();
                                    break;
                                case 8:
                                    col_8 = questListDto.getQues_array().get(i).getQ_ans();
                                    break;
                                case 9:
                                    col_9 = questListDto.getQues_array().get(i).getQ_ans();
                                    break;
                                case 10:
                                    col_10 = questListDto.getQues_array().get(i).getQ_ans();
                                    break;
                                case 11:
                                    col_11 = questListDto.getQues_array().get(i).getQ_ans();
                                    break;
                                case 12:
                                    col_12 = questListDto.getQues_array().get(i).getQ_ans();
                                    break;
                                case 13:
                                    col_13 = questListDto.getQues_array().get(i).getQ_ans();
                                    break;
                                case 14:
                                    col_14 = questListDto.getQues_array().get(i).getQ_ans();
                                    break;
                                case 15:
                                    col_15 = questListDto.getQues_array().get(i).getQ_ans();
                                    break;
                                case 16:
                                    col_16 = questListDto.getQues_array().get(i).getQ_ans();
                                    break;
                                case 17:
                                    col_17 = questListDto.getQues_array().get(i).getQ_ans();
                                    break;
                                case 18:
                                    col_18 = questListDto.getQues_array().get(i).getQ_ans();
                                    break;
                                case 19:
                                    col_19 = questListDto.getQues_array().get(i).getQ_ans();
                                    break;
                            }
                        }
                    }

                    radioGroup1_pre.setText(col_1);
                    radioGroup2_pre.setText(col_2);
                    radioGroup3_pre.setText(col_3);
                    radioGroup4_pre.setText(col_4);
                    radioGroup5_pre.setText(col_5);
                    radioGroup6_pre.setText(col_6);
                    radioGroup7_pre.setText(col_7);
                    radioGroup8_pre.setText(col_8);
                    radioGroup9_pre.setText(col_9);
                    radioGroup10_pre.setText(col_10);
                    radioGroup11_pre.setText(col_11);
                    radioGroup12_pre.setText(col_12);
                    radioGroup13_pre.setText(col_13);
                    radioGroup14_pre.setText(col_14);
                    radioGroup15_pre.setText(col_15);
                    radioGroup16_pre.setText(col_16);
                    radioGroup17_pre.setText(col_17);
                    radioGroup18_pre.setText(col_18);
                    radioGroup19_pre.setText(col_19);
                } else {
                    radioGroup1_pre.setText(getString(R.string.na));
                    radioGroup2_pre.setText(getString(R.string.na));
                    radioGroup3_pre.setText(getString(R.string.na));
                    radioGroup4_pre.setText(getString(R.string.na));
                    radioGroup5_pre.setText(getString(R.string.na));
                    radioGroup6_pre.setText(getString(R.string.na));
                    radioGroup7_pre.setText(getString(R.string.na));
                    radioGroup8_pre.setText(getString(R.string.na));
                    radioGroup9_pre.setText(getString(R.string.na));
                    radioGroup10_pre.setText(getString(R.string.na));
                    radioGroup11_pre.setText(getString(R.string.na));
                    radioGroup12_pre.setText(getString(R.string.na));
                    radioGroup13_pre.setText(getString(R.string.na));
                    radioGroup14_pre.setText(getString(R.string.na));
                    radioGroup15_pre.setText(getString(R.string.na));
                    radioGroup16_pre.setText(getString(R.string.na));
                    radioGroup17_pre.setText(getString(R.string.na));
                    radioGroup18_pre.setText(getString(R.string.na));
                    radioGroup19_pre.setText(getString(R.string.na));
                }
            }
        } catch (Exception e) {
        }
    }

    private void setColorForText(TextView radioGroup, String textValue) {
        if (textValue == null) textValue = "";
        if (textValue.equalsIgnoreCase("null")) textValue = "";
        if (textValue.equalsIgnoreCase("Yes") || textValue.equalsIgnoreCase("Available")) {
            radioGroup.setTextAppearance(getApplicationContext(), R.style.TextViewForAstericBold);
            radioGroup.setTextColor(getResources().getColor(R.color.colorGreenDark));
        } else if (textValue.equalsIgnoreCase("No") || textValue.equalsIgnoreCase("Not Available")) {
            radioGroup.setTextAppearance(getApplicationContext(), R.style.TextViewForAstericBold);
            radioGroup.setTextColor(getResources().getColor(R.color.colorRedDark));
        } else if (textValue.equalsIgnoreCase("Available but not functional")) {
            radioGroup.setTextAppearance(getApplicationContext(), R.style.TextViewForAstericBold);
            radioGroup.setTextColor(getResources().getColor(R.color.colorOrangeDark));
        } else {
            radioGroup.setTextAppearance(getApplicationContext(), R.style.TextViewForAsteric);
            radioGroup.setTextColor(getResources().getColor(R.color.black));
        }
    }

}
