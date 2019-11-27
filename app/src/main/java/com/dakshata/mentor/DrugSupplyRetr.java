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

import static com.dakshata.mentor.JhpiegoDatabase.COL_1;
import static com.dakshata.mentor.JhpiegoDatabase.COL_10;
import static com.dakshata.mentor.JhpiegoDatabase.COL_11;
import static com.dakshata.mentor.JhpiegoDatabase.COL_12;
import static com.dakshata.mentor.JhpiegoDatabase.COL_13;
import static com.dakshata.mentor.JhpiegoDatabase.COL_14;
import static com.dakshata.mentor.JhpiegoDatabase.COL_15;
import static com.dakshata.mentor.JhpiegoDatabase.COL_16;
import static com.dakshata.mentor.JhpiegoDatabase.COL_17;
import static com.dakshata.mentor.JhpiegoDatabase.COL_18;
import static com.dakshata.mentor.JhpiegoDatabase.COL_19;
import static com.dakshata.mentor.JhpiegoDatabase.COL_2;
import static com.dakshata.mentor.JhpiegoDatabase.COL_20;
import static com.dakshata.mentor.JhpiegoDatabase.COL_21;
import static com.dakshata.mentor.JhpiegoDatabase.COL_22;
import static com.dakshata.mentor.JhpiegoDatabase.COL_23;
import static com.dakshata.mentor.JhpiegoDatabase.COL_24;
import static com.dakshata.mentor.JhpiegoDatabase.COL_25;
import static com.dakshata.mentor.JhpiegoDatabase.COL_26;
import static com.dakshata.mentor.JhpiegoDatabase.COL_3;
import static com.dakshata.mentor.JhpiegoDatabase.COL_4;
import static com.dakshata.mentor.JhpiegoDatabase.COL_5;
import static com.dakshata.mentor.JhpiegoDatabase.COL_6;
import static com.dakshata.mentor.JhpiegoDatabase.COL_7;
import static com.dakshata.mentor.JhpiegoDatabase.COL_8;
import static com.dakshata.mentor.JhpiegoDatabase.COL_9;
import static com.dakshata.mentor.JhpiegoDatabase.COL_ID;
import static com.dakshata.mentor.JhpiegoDatabase.COL_SESSION;
import static com.dakshata.mentor.JhpiegoDatabase.COL_USERNAME;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME8;

/**
 * Created by Aditya on 12/17/2017.
 */

public class DrugSupplyRetr extends AppCompatActivity {
    TextView radioGroup1,radioGroup2,radioGroup3,radioGroup4,radioGroup5,radioGroup6,radioGroup7,radioGroup8,radioGroup9,radioGroup10,radioGroup11,radioGroup12,radioGroup13,radioGroup14,radioGroup15,radioGroup16,radioGroup17,radioGroup18,radioGroup19,radioGroup20,radioGroup21,radioGroup22,radioGroup23,radioGroup24,radioGroup25,radioGroup26;
    RadioButton radioButton1,radioButton2,radioButton3,radioButton4,radioButton5,radioButton6,radioButton7,radioButton8,radioButton9,radioButton10,radioButton11,radioButton12,radioButton13,radioButton14,radioButton15,radioButton16,radioButton17,radioButton18,radioButton19,radioButton20,radioButton21,radioButton22,radioButton23,radioButton24,radioButton25,radioButton26;
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
        setContentView(R.layout.labour_room_view);

        sessionID = getIntent().getStringExtra("session");
        facilityName = getIntent().getStringExtra("facilityName");
        facilityType = getIntent().getStringExtra("facilityType");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(),R.drawable.menu_image_opt3));
        setSupportActionBar(toolbar);
        back =(ImageView)findViewById(R.id.back);
        textViewHeaderName= (TextView) findViewById(R.id.header_name);
        textViewHeaderName.setText(getString(R.string.section_d_header_1));
        jhpiegoDatabase=new JhpiegoDatabase(this);
        setTitle("  Essential Resources in Labor Room ");
        initViews();
        setViews();
      //  getUserDisplay();

        getFormDataFromDb();
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void initViews(){
        radioGroup1= (TextView) findViewById(R.id.lr_rg1);
        radioGroup2= (TextView) findViewById(R.id.lr_rg2);
        radioGroup3= (TextView) findViewById(R.id.lr_rg3);
        radioGroup4= (TextView) findViewById(R.id.lr_rg4);
        radioGroup5= (TextView) findViewById(R.id.lr_rg5);
        radioGroup6= (TextView) findViewById(R.id.lr_rg6);
        radioGroup7= (TextView) findViewById(R.id.lr_rg7);
        radioGroup8= (TextView) findViewById(R.id.lr_rg8);
        radioGroup9= (TextView) findViewById(R.id.lr_rg9);
        radioGroup10= (TextView) findViewById(R.id.lr_rg10);
        radioGroup11= (TextView) findViewById(R.id.lr_rg11);
        radioGroup12= (TextView) findViewById(R.id.lr_rg12);
        radioGroup13= (TextView) findViewById(R.id.lr_rg13);
        radioGroup14= (TextView) findViewById(R.id.lr_rg14);
        radioGroup15= (TextView) findViewById(R.id.lr_rg15);
        radioGroup16= (TextView) findViewById(R.id.lr_rg16);
        radioGroup17= (TextView) findViewById(R.id.lr_rg17);
        radioGroup18= (TextView) findViewById(R.id.lr_rg18);
        radioGroup19= (TextView) findViewById(R.id.lr_rg19);
        radioGroup20= (TextView) findViewById(R.id.lr_rg20);
        radioGroup21= (TextView) findViewById(R.id.lr_rg21);
        radioGroup22= (TextView) findViewById(R.id.lr_rg22);
        radioGroup23= (TextView) findViewById(R.id.lr_rg23);
        radioGroup24= (TextView) findViewById(R.id.lr_rg24);
        radioGroup25= (TextView) findViewById(R.id.lr_rg25);
        radioGroup26= (TextView) findViewById(R.id.lr_rg26);
        button_save= (Button) findViewById(R.id.lr_save);
        button_back= (Button) findViewById(R.id.lr_back);
    }
    public void setViews(){

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
            String query = "select * from " + TABLENAME8;
            Cursor cursor=sqLiteDatabase.query(TABLENAME8,new String[]{COL_SESSION,COL_1,COL_2,COL_3,COL_4,COL_5,COL_6,COL_7,COL_8,COL_9,COL_10,COL_11,COL_12,COL_13,COL_14,COL_15,COL_16,COL_17,COL_18,COL_19,COL_20,COL_21,COL_22,COL_23,COL_24,COL_25,COL_26},COL_SESSION + "=?",new String[]{sessionID},null,null,null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                String col_1 = cursor.getString(cursor.getColumnIndex(COL_1));
                String col_2 = cursor.getString(cursor.getColumnIndex(COL_2));
                String col_3 = cursor.getString(cursor.getColumnIndex(COL_3));
                String col_4 = cursor.getString(cursor.getColumnIndex(COL_4));
                String col_5 = cursor.getString(cursor.getColumnIndex(COL_5));
                String col_6 = cursor.getString(cursor.getColumnIndex(COL_6));
                String col_7 = cursor.getString(cursor.getColumnIndex(COL_7));
                String col_8 = cursor.getString(cursor.getColumnIndex(COL_8));
                String col_9 = cursor.getString(cursor.getColumnIndex(COL_9));
                String col_10 = cursor.getString(cursor.getColumnIndex(COL_10));
                String col_11 = cursor.getString(cursor.getColumnIndex(COL_11));
                String col_12 = cursor.getString(cursor.getColumnIndex(COL_12));
                String col_13 = cursor.getString(cursor.getColumnIndex(COL_13));
                String col_14 = cursor.getString(cursor.getColumnIndex(COL_14));
                String col_15 = cursor.getString(cursor.getColumnIndex(COL_15));
                String col_16 = cursor.getString(cursor.getColumnIndex(COL_16));
                String col_17 = cursor.getString(cursor.getColumnIndex(COL_17));
                String col_18 = cursor.getString(cursor.getColumnIndex(COL_18));
                String col_19 = cursor.getString(cursor.getColumnIndex(COL_19));
                String col_20 = cursor.getString(cursor.getColumnIndex(COL_20));
                String col_21 = cursor.getString(cursor.getColumnIndex(COL_21));
                String col_22 = cursor.getString(cursor.getColumnIndex(COL_22));
                String col_23 = cursor.getString(cursor.getColumnIndex(COL_23));
                String col_24 = cursor.getString(cursor.getColumnIndex(COL_24));
                String col_25 = cursor.getString(cursor.getColumnIndex(COL_25));
                String col_26 = cursor.getString(cursor.getColumnIndex(COL_26));

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
                radioGroup20.setText(col_20);
                radioGroup21.setText(col_21);
                radioGroup22.setText(col_22);
                radioGroup23.setText(col_23);
                radioGroup24.setText(col_24);
                radioGroup25.setText(col_25);
                radioGroup26.setText(col_26);
            }
        }catch (Exception e){}
    }
}
