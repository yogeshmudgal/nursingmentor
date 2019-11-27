package com.dakshata.mentor;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import static com.dakshata.mentor.JhpiegoDatabase.COL_BLT;
import static com.dakshata.mentor.JhpiegoDatabase.COL_DBW;
import static com.dakshata.mentor.JhpiegoDatabase.COL_DLR;
import static com.dakshata.mentor.JhpiegoDatabase.COL_ET;
import static com.dakshata.mentor.JhpiegoDatabase.COL_ETRAY;
import static com.dakshata.mentor.JhpiegoDatabase.COL_FAM;
import static com.dakshata.mentor.JhpiegoDatabase.COL_FB;
import static com.dakshata.mentor.JhpiegoDatabase.COL_FBWS;
import static com.dakshata.mentor.JhpiegoDatabase.COL_FOC;
import static com.dakshata.mentor.JhpiegoDatabase.COL_FRW;
import static com.dakshata.mentor.JhpiegoDatabase.COL_FS;
import static com.dakshata.mentor.JhpiegoDatabase.COL_ID;
import static com.dakshata.mentor.JhpiegoDatabase.COL_MLT;
import static com.dakshata.mentor.JhpiegoDatabase.COL_PPD;
import static com.dakshata.mentor.JhpiegoDatabase.COL_USERNAME;
import static com.dakshata.mentor.JhpiegoDatabase.COL_WCLOCK;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME4;

/**
 * Created by Aditya on 12/17/2017.
 */

public class EquipmentsRetr extends AppCompatActivity {
    RadioGroup radioGroup1,radioGroup2,radioGroup3,radioGroup4,radioGroup5,radioGroup6,radioGroup7,radioGroup8,radioGroup9,radioGroup10,radioGroup11,radioGroup12,radioGroup13,radioGroup14;
    RadioButton radioButton1,radioButton2,radioButton3,radioButton4,radioButton5,radioButton6,radioButton7,radioButton8,radioButton9,radioButton10,radioButton11,radioButton12,radioButton13,radioButton14;
    Button button_save,button_back;
    JhpiegoDatabase jhpiegoDatabase;
    Toolbar toolbar;
    SharedPreferences sh_Pref;
    SharedPreferences.Editor editor;
    ImageView back;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equipments);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(),R.drawable.menu_image_opt3));
        setSupportActionBar(toolbar);
        back =(ImageView)findViewById(R.id.back);
        jhpiegoDatabase=new JhpiegoDatabase(this);
        setTitle("  Equipments");
        initViews();
        setViews();
        getUserDisplay();

        getFormDataFromDb();
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void initViews(){
        radioGroup1= (RadioGroup) findViewById(R.id.equip_rg1);
        radioGroup2= (RadioGroup) findViewById(R.id.equip_rg2);
        radioGroup3= (RadioGroup) findViewById(R.id.equip_rg3);
        radioGroup4= (RadioGroup) findViewById(R.id.equip_rg4);
        radioGroup5= (RadioGroup) findViewById(R.id.equip_rg5);
        radioGroup6= (RadioGroup) findViewById(R.id.equip_rg6);
        radioGroup7= (RadioGroup) findViewById(R.id.equip_rg7);
        radioGroup8= (RadioGroup) findViewById(R.id.equip_rg8);
        radioGroup9= (RadioGroup) findViewById(R.id.equip_rg9);
        radioGroup10= (RadioGroup) findViewById(R.id.equip_rg10);
        radioGroup11= (RadioGroup) findViewById(R.id.equip_rg11);
        radioGroup12= (RadioGroup) findViewById(R.id.equip_rg12);
        radioGroup13= (RadioGroup) findViewById(R.id.equip_rg13);
        radioGroup14= (RadioGroup) findViewById(R.id.equip_rg14);
        button_save= (Button) findViewById(R.id.equip_save);
        button_back= (Button) findViewById(R.id.equip_back);
    }
    public void setViews(){
        radioGroup1.setEnabled(false);
        radioGroup2.setEnabled(false);
        radioGroup3.setEnabled(false);
        radioGroup4.setEnabled(false);
        radioGroup5.setEnabled(false);
        radioGroup6.setEnabled(false);
        radioGroup7.setEnabled(false);
        radioGroup8.setEnabled(false);
        radioGroup9.setEnabled(false);
        radioGroup10.setEnabled(false);
        radioGroup11.setEnabled(false);
        radioGroup12.setEnabled(false);
        radioGroup13.setEnabled(false);
        radioGroup14.setEnabled(false);
        button_save.setVisibility(View.GONE);

    }

    public void Pback(View view) {
        super.onBackPressed();

    }
    public void getUserDisplay(){
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
    }
    @SuppressLint("ResourceType")
    public void getFormDataFromDb(){
        try {
            SQLiteDatabase sqLiteDatabase=jhpiegoDatabase.getReadableDatabase();

            String query="select * from " +TABLENAME4;
            Cursor cursor=sqLiteDatabase.rawQuery(query,null);
            if (cursor!=null && cursor.getCount()>0) {
                cursor.moveToLast();
                String col_1 = cursor.getString(cursor.getColumnIndex(COL_FAM));
                String col_2 = cursor.getString(cursor.getColumnIndex(COL_FB));
                String col_3 = cursor.getString(cursor.getColumnIndex(COL_BLT));
                String col_4 = cursor.getString(cursor.getColumnIndex(COL_WCLOCK));
                String col_5 = cursor.getString(cursor.getColumnIndex(COL_DBW));
                String col_6 = cursor.getString(cursor.getColumnIndex(COL_DLR));
                String col_7 = cursor.getString(cursor.getColumnIndex(COL_FS));
                String col_8 = cursor.getString(cursor.getColumnIndex(COL_MLT));
                String col_9 = cursor.getString(cursor.getColumnIndex(COL_FOC));
                String col_10 = cursor.getString(cursor.getColumnIndex(COL_PPD));
                String col_11 = cursor.getString(cursor.getColumnIndex(COL_ET));
                String col_12 = cursor.getString(cursor.getColumnIndex(COL_ETRAY));
                String col_13 = cursor.getString(cursor.getColumnIndex(COL_FRW));
                String col_14 = cursor.getString(cursor.getColumnIndex(COL_FBWS));

                if (col_1.equals("yes")) {
                    radioGroup1.check(0);
                } else {
                    radioGroup1.check(1);
                }
                if (col_2.equals("yes")) {
                    radioGroup2.check(0);
                } else {
                    radioGroup2.check(1);
                }
                if (col_3.equals("yes")) {
                    radioGroup3.check(0);
                } else {
                    radioGroup3.check(1);
                }
                if (col_4.equals("yes")) {
                    radioGroup4.check(0);
                } else {
                    radioGroup4.check(1);
                }
                if (col_5.equals("yes")) {
                    radioGroup5.check(0);
                } else {
                    radioGroup5.check(1);
                }
                if (col_6.equals("yes")) {
                    radioGroup6.check(0);
                } else {
                    radioGroup6.check(1);
                }
                if (col_7.equals("yes")) {
                    radioGroup7.check(0);
                } else {
                    radioGroup7.check(1);
                }
                if (col_8.equals("yes")) {
                    radioGroup8.check(0);
                } else {
                    radioGroup8.check(1);
                }
                if (col_9.equals("yes")) {
                    radioGroup9.check(0);
                } else {
                    radioGroup9.check(1);
                }
                if (col_10.equals("yes")) {
                    radioGroup10.check(0);
                } else {
                    radioGroup10.check(1);
                }
                if (col_11.equals("yes")) {
                    radioGroup11.check(0);
                } else {
                    radioGroup11.check(1);
                }
                if (col_12.equals("yes")) {
                    radioGroup12.check(0);
                } else {
                    radioGroup12.check(1);
                }
                if (col_13.equals("yes")) {
                    radioGroup13.check(0);
                } else {
                    radioGroup13.check(1);
                }
                if (col_14.equals("yes")) {
                    radioGroup14.check(0);
                } else {
                    radioGroup14.check(1);
                }
            }
        }catch (Exception e){

        }
    }
}
