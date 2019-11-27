package com.dakshata.mentor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TextView;

import static com.dakshata.mentor.JhpiegoDatabase.COL_AC;
import static com.dakshata.mentor.JhpiegoDatabase.COL_ASBT;
import static com.dakshata.mentor.JhpiegoDatabase.COL_ASTNSEM;
import static com.dakshata.mentor.JhpiegoDatabase.COL_AV;
import static com.dakshata.mentor.JhpiegoDatabase.COL_CMDS;
import static com.dakshata.mentor.JhpiegoDatabase.COL_CNRNB;
import static com.dakshata.mentor.JhpiegoDatabase.COL_DUA;
import static com.dakshata.mentor.JhpiegoDatabase.COL_EOT;
import static com.dakshata.mentor.JhpiegoDatabase.COL_THREE_SIDE_SPACE;
import static com.dakshata.mentor.JhpiegoDatabase.COL_FHWS;
import static com.dakshata.mentor.JhpiegoDatabase.COL_FLSL;
import static com.dakshata.mentor.JhpiegoDatabase.COL_FWRW;
import static com.dakshata.mentor.JhpiegoDatabase.COL_ID;
import static com.dakshata.mentor.JhpiegoDatabase.COL_LCFW;
import static com.dakshata.mentor.JhpiegoDatabase.COL_LOF;
import static com.dakshata.mentor.JhpiegoDatabase.COL_LOI;
import static com.dakshata.mentor.JhpiegoDatabase.COL_LRD;
import static com.dakshata.mentor.JhpiegoDatabase.COL_PB;
import static com.dakshata.mentor.JhpiegoDatabase.COL_PSC;
import static com.dakshata.mentor.JhpiegoDatabase.COL_RW;
import static com.dakshata.mentor.JhpiegoDatabase.COL_SESSION;
import static com.dakshata.mentor.JhpiegoDatabase.COL_SOB;
import static com.dakshata.mentor.JhpiegoDatabase.COL_USERNAME;
import static com.dakshata.mentor.JhpiegoDatabase.COL_WALLS;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME5;

/**
 * Created by Aditya on 12/17/2017.
 */

public class InfrastructureRetr extends AppCompatActivity {
    TextView radioGroup1,radioGroup2,radioGroup3,radioGroup4,radioGroup5,radioGroup6,radioGroup7,radioGroup8,radioGroup9,radioGroup10,radioGroup11,radioGroup12,radioGroup13,radioGroup14,radioGroup15,radioGroup16,radioGroup17,radioGroup18,radioGroup19, radioGroup20;
    RadioButton radioButton1,radioButton2,radioButton3,radioButton4,radioButton5,radioButton6,radioButton7,radioButton8,radioButton9,radioButton10,radioButton11,radioButton12,radioButton13,radioButton14,radioButton15,radioButton16,radioButton17,radioButton18,radioButton19;
    Button button_save,button_back;
    TextView spinner1;
    JhpiegoDatabase jhpiegoDatabase;
    Toolbar toolbar;
    SharedPreferences sh_Pref,sharedPreferences;
    SharedPreferences.Editor editor,editor1;
    ImageView back;
    TextView textViewHeaderName;
    String sessionID, facilityName, facilityType;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infrastructure_view);

        sessionID = getIntent().getStringExtra("session");
        facilityName = getIntent().getStringExtra("facilityName");
        facilityType = getIntent().getStringExtra("facilityType");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(),R.drawable.menu_image_opt3));
        setSupportActionBar(toolbar);
        back =(ImageView)findViewById(R.id.back);
        textViewHeaderName= (TextView) findViewById(R.id.header_name);
        textViewHeaderName.setText(getString(R.string.section_c_header_1));
        jhpiegoDatabase = new JhpiegoDatabase(this);
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
    public void initViews(){
        radioGroup1= (TextView) findViewById(R.id.infra_rg1);
        radioGroup2= (TextView) findViewById(R.id.infra_rg2);
        radioGroup3= (TextView) findViewById(R.id.infra_rg3);
        radioGroup4= (TextView) findViewById(R.id.infra_rg4);
        radioGroup5= (TextView) findViewById(R.id.infra_rg5);
        radioGroup6= (TextView) findViewById(R.id.infra_rg6);
        radioGroup7= (TextView) findViewById(R.id.infra_rg7);
        radioGroup8= (TextView) findViewById(R.id.infra_rg8);
        radioGroup9= (TextView) findViewById(R.id.infra_rg9);
        radioGroup10= (TextView) findViewById(R.id.infra_rg10);
        radioGroup11= (TextView) findViewById(R.id.infra_rg11);
        radioGroup12= (TextView) findViewById(R.id.infra_rg12);
        radioGroup13= (TextView) findViewById(R.id.infra_rg13);
        radioGroup14= (TextView) findViewById(R.id.infra_rg14);
        radioGroup15= (TextView) findViewById(R.id.infra_rg15);
        radioGroup16= (TextView) findViewById(R.id.infra_rg16);
        radioGroup17= (TextView) findViewById(R.id.infra_rg17);
        radioGroup18= (TextView) findViewById(R.id.infra_rg18);
        radioGroup19= (TextView) findViewById(R.id.infra_rg19);
        radioGroup20= (TextView) findViewById(R.id.infra_rg20);
        button_save= (Button) findViewById(R.id.infra_save);
        button_back= (Button) findViewById(R.id.infra_back);
        spinner1= (TextView) findViewById(R.id.infra_spinner1);
    }
    public void setViews(){

        button_save.setVisibility(View.GONE);

    }

    public void Pback(View view) {
        super.onBackPressed();

    }
    /*public void getUserDisplay(){
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
    public void getFormDataFromDb(){
        try {
//            sharedPreferences = getSharedPreferences("session Credentials", MODE_PRIVATE);
//            editor1=sharedPreferences.edit();
//            String sessionID=sharedPreferences.getString("session","");
            Log.v("session",""+sessionID);
            SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
            String query = "select * from " + TABLENAME5;
            Cursor cursor=sqLiteDatabase.query(TABLENAME5,new String[]{COL_SESSION,COL_LOI,COL_LRD,COL_PSC,COL_LCFW,COL_ASTNSEM,COL_WALLS,COL_AC,COL_AV,COL_FLSL,COL_PB,COL_LOF,COL_ASBT,COL_CNRNB,COL_CMDS,COL_RW,COL_SOB,COL_FWRW,COL_DUA,COL_FHWS,COL_EOT, COL_THREE_SIDE_SPACE},COL_SESSION + "=?",new String[]{sessionID},null,null,null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                String col_1 = cursor.getString(cursor.getColumnIndex(COL_LOI));
                String col_2 = cursor.getString(cursor.getColumnIndex(COL_LRD));
                String col_3 = cursor.getString(cursor.getColumnIndex(COL_PSC));
                String col_4 = cursor.getString(cursor.getColumnIndex(COL_LCFW));
                String col_5 = cursor.getString(cursor.getColumnIndex(COL_ASTNSEM));
                String col_6 = cursor.getString(cursor.getColumnIndex(COL_WALLS));
                String col_7 = cursor.getString(cursor.getColumnIndex(COL_AC));
                String col_8 = cursor.getString(cursor.getColumnIndex(COL_AV));
                String col_9 = cursor.getString(cursor.getColumnIndex(COL_FLSL));
                String col_10 = cursor.getString(cursor.getColumnIndex(COL_PB));
                String col_11 = cursor.getString(cursor.getColumnIndex(COL_LOF));
                String col_12 = cursor.getString(cursor.getColumnIndex(COL_ASBT));
                String col_13 = cursor.getString(cursor.getColumnIndex(COL_CNRNB));
                String col_14 = cursor.getString(cursor.getColumnIndex(COL_CMDS));
                String col_15 = cursor.getString(cursor.getColumnIndex(COL_RW));
                String col_16 = cursor.getString(cursor.getColumnIndex(COL_SOB));
                String col_17 = cursor.getString(cursor.getColumnIndex(COL_FWRW));
                String col_18 = cursor.getString(cursor.getColumnIndex(COL_DUA));
                String col_19 = cursor.getString(cursor.getColumnIndex(COL_FHWS));
                String col_20 = cursor.getString(cursor.getColumnIndex(COL_EOT));
                String col_21 = cursor.getString(cursor.getColumnIndex(COL_THREE_SIDE_SPACE));

                spinner1.setText(col_1);
                radioGroup1.setText(col_2);
                radioGroup2.setText(col_3);
                radioGroup3.setText(col_4);
                radioGroup4.setText(col_5);
                radioGroup5.setText(col_6);
                radioGroup6.setText(col_7);
                radioGroup7.setText(col_8);
                radioGroup8.setText(col_9);
                radioGroup9.setText(col_10);
                radioGroup10.setText(col_11);
                radioGroup11.setText(col_12);
                radioGroup12.setText(col_13);
                radioGroup13.setText(col_14);
                radioGroup14.setText(col_15);
                radioGroup15.setText(col_16);
                radioGroup16.setText(col_17);
                radioGroup17.setText(col_18);
                radioGroup18.setText(col_19);
                radioGroup19.setText(col_20);
                radioGroup20.setText(col_21);
            }
        }catch (Exception e){}
    }
}
