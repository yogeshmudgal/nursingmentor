package com.dakshata.mentor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import static com.dakshata.mentor.JhpiegoDatabase.COL_BLOCK;
import static com.dakshata.mentor.JhpiegoDatabase.COL_DIST;
import static com.dakshata.mentor.JhpiegoDatabase.COL_DOV;
import static com.dakshata.mentor.JhpiegoDatabase.COL_FACILITYNAME;
import static com.dakshata.mentor.JhpiegoDatabase.COL_FACILITYTYPE;
import static com.dakshata.mentor.JhpiegoDatabase.COL_ID;
import static com.dakshata.mentor.JhpiegoDatabase.COL_IMAGE1;
import static com.dakshata.mentor.JhpiegoDatabase.COL_IMAGE2;
import static com.dakshata.mentor.JhpiegoDatabase.COL_IMAGE3;
import static com.dakshata.mentor.JhpiegoDatabase.COL_IMAGE4;
import static com.dakshata.mentor.JhpiegoDatabase.COL_LEVEL;
import static com.dakshata.mentor.JhpiegoDatabase.COL_NOM;
import static com.dakshata.mentor.JhpiegoDatabase.COL_SESSION;
import static com.dakshata.mentor.JhpiegoDatabase.COL_STATE;
import static com.dakshata.mentor.JhpiegoDatabase.COL_USERNAME;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME1;

/**
 * Created by Aditya on 12/17/2017.
 */

public class DetailsOfVisitRetrieved extends AppCompatActivity {
    TextView spinner_state,spinner_selectDistrict,spinner_selectBlock,spinner_facilityType,spinner_facilityName,spinner_level;
    ImageView imageView1,imageView2,imageView3,imageView4;
    TextView editText_nom,editText_dov;
    JhpiegoDatabase jhpiegoDatabase;
    Button button_save,button_back;
    Bitmap bitmap1,bitmap2,bitmap3,bitmap4;
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
        setContentView(R.layout.details_of_visit_view);

        sessionID = getIntent().getStringExtra("session");
        facilityName = getIntent().getStringExtra("facilityName");
        facilityType = getIntent().getStringExtra("facilityType");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(),R.drawable.menu_image_opt3));
        setSupportActionBar(toolbar);
        back =(ImageView)findViewById(R.id.back);
        textViewHeaderName= (TextView) findViewById(R.id.header_name);
        textViewHeaderName.setText(getString(R.string.section_a_header_1));
        jhpiegoDatabase=new JhpiegoDatabase(this);
        initViews();
        setViews();
       // getUserDisplay();
        getFormDataFromDb();
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void initViews(){

        spinner_state= (TextView) findViewById(R.id.dov_spinner1);
        spinner_selectDistrict= (TextView) findViewById(R.id.dov_spinner2);
        spinner_selectBlock= (TextView) findViewById(R.id.dov_spinner3);
        spinner_facilityName= (TextView) findViewById(R.id.dov_spinner4);
        spinner_facilityType= (TextView) findViewById(R.id.dov_spinner5);
        spinner_level= (TextView) findViewById(R.id.dov_spinner6);
        imageView1= (ImageView) findViewById(R.id.dov_iv1);
        imageView2= (ImageView) findViewById(R.id.dov_iv2);
        imageView3= (ImageView) findViewById(R.id.dov_iv3);
        imageView4= (ImageView) findViewById(R.id.dov_iv4);
        editText_nom= (TextView) findViewById(R.id.dov_et1);
        editText_dov= (TextView) findViewById(R.id.dov_et2);
        button_back= (Button) findViewById(R.id.dov_back);
        button_save= (Button) findViewById(R.id.dov_save);
    }
    public void setViews(){
        button_save.setVisibility(View.GONE);
//        imageView1.setEnabled(false);

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
            String query = "select * from " + TABLENAME1;
            Cursor cursor=sqLiteDatabase.query(TABLENAME1,new String[]{COL_SESSION,COL_NOM,COL_STATE,COL_DIST,COL_BLOCK,COL_FACILITYNAME,COL_FACILITYTYPE,COL_LEVEL,COL_DOV,COL_IMAGE1,COL_IMAGE2,COL_IMAGE3,COL_IMAGE4},COL_SESSION + "=?",new String[]{sessionID},null,null,null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                String col_1 = cursor.getString(cursor.getColumnIndex(COL_NOM));
                String col_2 = cursor.getString(cursor.getColumnIndex(COL_STATE));
                String col_3 = cursor.getString(cursor.getColumnIndex(COL_DIST));
                String col_4 = cursor.getString(cursor.getColumnIndex(COL_BLOCK));
                String col_5 = cursor.getString(cursor.getColumnIndex(COL_FACILITYNAME));
                String col_6 = cursor.getString(cursor.getColumnIndex(COL_FACILITYTYPE));
                String col_7 = cursor.getString(cursor.getColumnIndex(COL_LEVEL));
                String col_8 = cursor.getString(cursor.getColumnIndex(COL_DOV));
                byte[] blob1 = cursor.getBlob(cursor.getColumnIndex(COL_IMAGE1));
                byte[] blob2 = cursor.getBlob(cursor.getColumnIndex(COL_IMAGE2));
                byte[] blob3 = cursor.getBlob(cursor.getColumnIndex(COL_IMAGE3));
                byte[] blob4 = cursor.getBlob(cursor.getColumnIndex(COL_IMAGE4));

                if (blob1!=null)
                    bitmap1= BitmapFactory.decodeByteArray(blob1, 0, blob1.length);

                if (blob2!=null)
                    bitmap2= BitmapFactory.decodeByteArray(blob2, 0, blob2.length);

                if (blob3!=null)
                    bitmap3= BitmapFactory.decodeByteArray(blob3, 0, blob3.length);

                if (blob4!=null)
                    bitmap4= BitmapFactory.decodeByteArray(blob4, 0, blob4.length);

                editText_nom.setText(col_1);
                editText_dov.setText(col_8);
                spinner_selectDistrict.setText(col_3);
                spinner_selectBlock.setText(col_4);
                spinner_facilityType.setText(col_6);
                spinner_facilityName.setText(col_5);
                spinner_state.setText(col_2);
                spinner_level.setText(col_7);

                if (bitmap1!=null ) {
                    imageView1.setImageBitmap(bitmap1);
                    imageView2.setImageBitmap(bitmap2);
                    imageView3.setImageBitmap(bitmap3);
                    imageView4.setImageBitmap(bitmap4);
                }
            }
        }catch (Exception e){

            Log.v("",""+e.toString());
        }
    }
}
