package com.dakshata.mentor;

import android.app.ProgressDialog;
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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static com.dakshata.mentor.JhpiegoDatabase.COL_ID;
import static com.dakshata.mentor.JhpiegoDatabase.COL_SESSION;
import static com.dakshata.mentor.JhpiegoDatabase.COL_USERNAME;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME1;

/**
 * Created by Aditya.v on 16-12-2017.
 */

public class Equipments extends AppCompatActivity {
    RadioGroup radioGroup1,radioGroup2,radioGroup3,radioGroup4,radioGroup5,radioGroup6,radioGroup7,radioGroup8,radioGroup9,radioGroup10,radioGroup11,radioGroup12,radioGroup13,radioGroup14;
    RadioButton radioButton1,radioButton2,radioButton3,radioButton4,radioButton5,radioButton6,radioButton7,radioButton8,radioButton9,radioButton10,radioButton11,radioButton12,radioButton13,radioButton14;
    Button button_save,button_back;
    JhpiegoDatabase jhpiegoDatabase;
    ProgressDialog progressDialog;
    String row_1,row_2,row_3,row_4,row_5,row_6,row_7,row_8,row_9,row_10,row_11,row_12,row_13,row_14,row_15,row_16,row_17,row_18,row_19;
    Toolbar toolbar;
    SharedPreferences sh_Pref,sharedPreferences;
    SharedPreferences.Editor editor,editor1;
    int count=0;
    String session;
    String username;
    ImageView back;
    String ansJson;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equipments);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(),R.drawable.menu_image_opt3));
        setSupportActionBar(toolbar);
        back =(ImageView)findViewById(R.id.back);
        progressDialog=new ProgressDialog(this);
        jhpiegoDatabase=new JhpiegoDatabase(this);

        initViews();
        getUserDisplay();

        getSessionOfUser();
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserEnteredData();
            }
        });
      //  progressDialog.dismiss();
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
    public void getUserEnteredData(){
        int selectedId=radioGroup1.getCheckedRadioButtonId();
        int selectedId2=radioGroup2.getCheckedRadioButtonId();
        int selectedId3=radioGroup3.getCheckedRadioButtonId();
        int selectedId4=radioGroup4.getCheckedRadioButtonId();
        int selectedId5=radioGroup5.getCheckedRadioButtonId();
        int selectedId6=radioGroup6.getCheckedRadioButtonId();
        int selectedId7=radioGroup7.getCheckedRadioButtonId();
        int selectedId8=radioGroup8.getCheckedRadioButtonId();
        int selectedId9=radioGroup9.getCheckedRadioButtonId();
        int selectedId10=radioGroup10.getCheckedRadioButtonId();
        int selectedId11=radioGroup11.getCheckedRadioButtonId();
        int selectedId12=radioGroup12.getCheckedRadioButtonId();
        int selectedId13=radioGroup13.getCheckedRadioButtonId();
        int selectedId14=radioGroup14.getCheckedRadioButtonId();
        if (radioGroup1.getCheckedRadioButtonId()<0 && radioGroup2.getCheckedRadioButtonId()<0 && radioGroup3.getCheckedRadioButtonId()<0 && radioGroup4.getCheckedRadioButtonId()<0 && radioGroup5.getCheckedRadioButtonId()<0 && radioGroup6.getCheckedRadioButtonId()<0 && radioGroup7.getCheckedRadioButtonId()<0 && radioGroup8.getCheckedRadioButtonId()<0 && radioGroup9.getCheckedRadioButtonId()<0 && radioGroup10.getCheckedRadioButtonId()<0 && radioGroup11.getCheckedRadioButtonId()<0 && radioGroup12.getCheckedRadioButtonId()<0 && radioGroup13.getCheckedRadioButtonId()<0 && radioGroup14.getCheckedRadioButtonId()<0 ){
        }else {
                   /* progressDialog.setMessage("Please wait...");
                    progressDialog.show();*/
            try {
                radioButton1 = (RadioButton) findViewById(selectedId);
                row_1 = radioButton1.getText().toString();
                if (!row_1.matches("")){
                    count++;
                }
            }catch (Exception e){}
            try {
                radioButton2 = (RadioButton) findViewById(selectedId2);
                row_2 = radioButton2.getText().toString();
                if (!row_2.matches("")){
                    count++;
                }
            }catch (Exception e){}
            try {
                radioButton3 = (RadioButton) findViewById(selectedId3);
                row_3 = radioButton3.getText().toString();
                if (!row_3.matches("")){
                    count++;
                }
            }catch (Exception e){}
            try {
                radioButton4 = (RadioButton) findViewById(selectedId4);
                row_4 = radioButton4.getText().toString();
            }catch (Exception e){}
            try {
                radioButton5 = (RadioButton) findViewById(selectedId5);
                row_5 = radioButton5.getText().toString();
                if (!row_5.matches("")){
                    count++;
                }

            }catch (Exception e){}
            try {
                radioButton6 = (RadioButton) findViewById(selectedId6);
                row_6 = radioButton6.getText().toString();
                if (!row_6.matches("")){
                    count++;
                }
            }catch (Exception e){}
            try {
                radioButton7 = (RadioButton) findViewById(selectedId7);
                row_7 = radioButton7.getText().toString();
                if (!row_7.matches("")){
                    count++;
                }
            }catch (Exception e){}
            try {
                radioButton8 = (RadioButton) findViewById(selectedId8);
                row_8 = radioButton8.getText().toString();
                if (!row_8.matches("")){
                    count++;
                }
            }catch (Exception e){}
            try {
                radioButton9 = (RadioButton) findViewById(selectedId9);
                row_9 = radioButton9.getText().toString();
                if (!row_9.matches("")){
                    count++;
                }
            }catch (Exception e){}
            try {
                radioButton10 = (RadioButton) findViewById(selectedId10);
                row_10 = radioButton10.getText().toString();
                if (!row_10.matches("")){
                    count++;
                }
            }catch (Exception e){}
            try {
                radioButton11 = (RadioButton) findViewById(selectedId11);
                row_11 = radioButton11.getText().toString();
                if (!row_11.matches("")){
                    count++;
                }
            }catch (Exception e){}
            try {
                radioButton12 = (RadioButton) findViewById(selectedId12);
                row_12 = radioButton12.getText().toString();
                if (!row_12.matches("")){
                    count++;
                }
            }catch (Exception e){}
            try {
                radioButton13 = (RadioButton) findViewById(selectedId13);
                row_13 = radioButton13.getText().toString();
                if (!row_13.matches("")){
                    count++;
                }
            }catch (Exception e){}
            try {
                radioButton14 = (RadioButton) findViewById(selectedId14);
                row_14 = radioButton14.getText().toString();
                if (!row_14.matches("")){
                    count++;
                }
            }catch (Exception e){}
            sharedPreferences=getSharedPreferences("session",MODE_PRIVATE);
            editor1=sharedPreferences.edit();
            String sessionid=sharedPreferences.getString("session","");
            long row=jhpiegoDatabase.addEquipments(username,row_1,row_2,row_3,row_4,row_5,row_6,row_7,row_8,row_9,row_10,row_11,row_12,row_13,row_14, String.valueOf(count),sessionid,ansJson);
            if(row!=-1)
            {
                Toast.makeText(getApplicationContext(), getString(R.string.alert_saved_successfully), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Equipments.this,MainActivity.class);
                startActivity(intent);

            }else{
                Toast.makeText(getApplicationContext(), getString(R.string.alert_not_saved_to_db), Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void getSessionOfUser(){
        try {
            SQLiteDatabase sqLiteDatabase=jhpiegoDatabase.getReadableDatabase();
            Cursor cursor=sqLiteDatabase.query(TABLENAME1,new String[]{COL_USERNAME,COL_SESSION},COL_USERNAME + "=?",new String[]{username},null,null,null);
            if (cursor !=null && cursor.getCount()>0) {
                cursor.moveToFirst();
                session = cursor.getString(cursor.getColumnIndex(COL_SESSION));
            }
        }catch (Exception e){}
    }
}
