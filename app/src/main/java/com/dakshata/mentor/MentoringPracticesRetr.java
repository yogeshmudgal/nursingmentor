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
import static com.dakshata.mentor.JhpiegoDatabase.COL_ID;
import static com.dakshata.mentor.JhpiegoDatabase.COL_IMPE;
import static com.dakshata.mentor.JhpiegoDatabase.COL_IMPO;
import static com.dakshata.mentor.JhpiegoDatabase.COL_LP;
import static com.dakshata.mentor.JhpiegoDatabase.COL_NR;
import static com.dakshata.mentor.JhpiegoDatabase.COL_PP1;
import static com.dakshata.mentor.JhpiegoDatabase.COL_PPH;
import static com.dakshata.mentor.JhpiegoDatabase.COL_PV;
import static com.dakshata.mentor.JhpiegoDatabase.COL_ROLR;
import static com.dakshata.mentor.JhpiegoDatabase.COL_SCN;
import static com.dakshata.mentor.JhpiegoDatabase.COL_SESSION;
import static com.dakshata.mentor.JhpiegoDatabase.COL_USERNAME;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME6;
/**
 * Created by Aditya on 12/17/2017.
 */

public class MentoringPracticesRetr extends AppCompatActivity {
    TextView radioGroup1,radioGroup2,radioGroup3,radioGroup4,radioGroup5,radioGroup6,radioGroup7,radioGroup8,radioGroup9,radioGroup10,radioGroup11,radioGroup12,radioGroup13,radioGroup14,radioGroup15,radioGroup16,radioGroup17,radioGroup18,radioGroup19;
    RadioButton radioButton1,radioButton2,radioButton3,radioButton4,radioButton5,radioButton6,radioButton7,radioButton8,radioButton9,radioButton10,radioButton11,radioButton12,radioButton13,radioButton14,radioButton15,radioButton16,radioButton17,radioButton18,radioButton19;
    Button button_save,button_back;
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
        setContentView(R.layout.mentoring_practices_view);

        sessionID = getIntent().getStringExtra("session");
        facilityName = getIntent().getStringExtra("facilityName");
        facilityType = getIntent().getStringExtra("facilityType");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(),R.drawable.menu_image_opt3));
        setSupportActionBar(toolbar);
        back =(ImageView)findViewById(R.id.back);
        textViewHeaderName= (TextView) findViewById(R.id.header_name);
        textViewHeaderName.setText(getString(R.string.section_f_header_1));
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
        radioGroup1= (TextView) findViewById(R.id.mp_rg1);
        radioGroup2= (TextView) findViewById(R.id.mp_rg2);
        radioGroup3= (TextView) findViewById(R.id.mp_rg3);
        radioGroup4= (TextView) findViewById(R.id.mp_rg4);
        radioGroup5= (TextView) findViewById(R.id.mp_rg5);
        radioGroup6= (TextView) findViewById(R.id.mp_rg6);
        radioGroup7= (TextView) findViewById(R.id.mp_rg7);
        radioGroup8= (TextView) findViewById(R.id.mp_rg8);
        radioGroup9= (TextView) findViewById(R.id.mp_rg9);
        radioGroup10= (TextView) findViewById(R.id.mp_rg10);
        radioGroup11= (TextView) findViewById(R.id.mp_rg11);
        radioGroup12= (TextView) findViewById(R.id.mp_rg12);
        radioGroup13= (TextView) findViewById(R.id.mp_rg13);
        radioGroup14= (TextView) findViewById(R.id.mp_rg14);
        radioGroup15= (TextView) findViewById(R.id.mp_rg15);
        radioGroup16= (TextView) findViewById(R.id.mp_rg16);
        radioGroup17= (TextView) findViewById(R.id.mp_rg17);
        radioGroup18= (TextView) findViewById(R.id.mp_rg18);
        radioGroup19= (TextView) findViewById(R.id.mp_rg19);
        button_save= (Button) findViewById(R.id.mp_save);
        button_back= (Button) findViewById(R.id.mp_back);
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
    public void getFormDataFromDb(){
        try {
//            sharedPreferences = getSharedPreferences("session Credentials", MODE_PRIVATE);
//            editor1=sharedPreferences.edit();
//            String sessionID=sharedPreferences.getString("session","");
            Log.v("session",""+sessionID);
            SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
            String query = "select * from " + TABLENAME6;
            Cursor cursor=sqLiteDatabase.query(TABLENAME6,new String[]{COL_SESSION,COL_ROLR,COL_LP,COL_GA,COL_PV,COL_AUA,COL_ACP,COL_CND,COL_AMTSL,COL_ENBC,COL_NR,COL_SCN,COL_PPH,COL_IMPE,COL_IMPO,COL_PP1,COL_DND,COL_DPPH,COL_DPE,COL_DNR},COL_SESSION + "=?",new String[]{sessionID},null,null,null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                String col_1 = cursor.getString(cursor.getColumnIndex(COL_ROLR));
                String col_2 = cursor.getString(cursor.getColumnIndex(COL_LP));
                String col_3 = cursor.getString(cursor.getColumnIndex(COL_GA));
                String col_4 = cursor.getString(cursor.getColumnIndex(COL_PV));
                String col_5 = cursor.getString(cursor.getColumnIndex(COL_AUA));
                String col_6 = cursor.getString(cursor.getColumnIndex(COL_ACP));
                String col_7 = cursor.getString(cursor.getColumnIndex(COL_CND));
                String col_8 = cursor.getString(cursor.getColumnIndex(COL_AMTSL));
                String col_9 = cursor.getString(cursor.getColumnIndex(COL_ENBC));
                String col_10 = cursor.getString(cursor.getColumnIndex(COL_NR));
                String col_11 = cursor.getString(cursor.getColumnIndex(COL_SCN));
                String col_12 = cursor.getString(cursor.getColumnIndex(COL_PPH));
                String col_13 = cursor.getString(cursor.getColumnIndex(COL_IMPE));
                String col_14 = cursor.getString(cursor.getColumnIndex(COL_IMPO));
                String col_15 = cursor.getString(cursor.getColumnIndex(COL_PP1));
                String col_16 = cursor.getString(cursor.getColumnIndex(COL_DND));
                String col_17 = cursor.getString(cursor.getColumnIndex(COL_DPPH));
                String col_18 = cursor.getString(cursor.getColumnIndex(COL_DPE));
                String col_19 = cursor.getString(cursor.getColumnIndex(COL_DNR));

                radioGroup1.setText(col_1);
                radioGroup2.setText(col_2);
                radioGroup3.setText(col_3);
                radioGroup4.setText(col_4);
                radioGroup5.setText(col_5);
                radioGroup6.setText(col_6);
                radioGroup7.setText(col_7);
                radioGroup8.setText(col_8);
                radioGroup9.setText(col_9);
                radioGroup10.setText(col_10);
                radioGroup11.setText(col_11);
                radioGroup12.setText(col_12);
                radioGroup13.setText(col_13);
                radioGroup14.setText(col_14);
                radioGroup15.setText(col_15);
                radioGroup16.setText(col_16);
                radioGroup17.setText(col_17);
                radioGroup18.setText(col_18);
                radioGroup19.setText(col_19);
            }
        }catch (Exception e){}
    }
}
