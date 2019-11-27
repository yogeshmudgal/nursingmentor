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
import android.widget.RadioButton;
import android.widget.TextView;

import static com.dakshata.mentor.JhpiegoDatabase.COL_1;
import static com.dakshata.mentor.JhpiegoDatabase.COL_2;
import static com.dakshata.mentor.JhpiegoDatabase.COL_3;
import static com.dakshata.mentor.JhpiegoDatabase.COL_4;
import static com.dakshata.mentor.JhpiegoDatabase.COL_SESSION;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME9;

/**
 * Created by Aditya.v on 18-12-2017.
 */

public class RecordingReportingRetrComparision extends AppCompatActivity {
    TextView radioGroup1, radioGroup2, radioGroup3, radioGroup4;

    TextView radioGroup1_pre, radioGroup2_pre, radioGroup3_pre, radioGroup4_pre;

    Button button_save, button_back;
    RadioButton radioButton1, radioButton2;
    JhpiegoDatabase jhpiegoDatabase;
    Toolbar toolbar;
    SharedPreferences sh_Pref, sharedPreferences;
    SharedPreferences.Editor editor, editor1;
    ImageView back;
    TextView textViewHeaderName;
    String sessionID, facilityName, facilityType;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recording_reporting_view_comparision);

        sessionID = getIntent().getStringExtra("session");
        facilityName = getIntent().getStringExtra("facilityName");
        facilityType = getIntent().getStringExtra("facilityType");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.menu_image_opt3));
        setSupportActionBar(toolbar);
        back = (ImageView) findViewById(R.id.back);
        textViewHeaderName = (TextView) findViewById(R.id.header_name);
        textViewHeaderName.setText(getString(R.string.section_g_header_1));
        radioGroup1 = (TextView) findViewById(R.id.rr_rg1);
        radioGroup2 = (TextView) findViewById(R.id.rr_rg2);
        radioGroup3 = (TextView) findViewById(R.id.rr_rg3);
        radioGroup4 = (TextView) findViewById(R.id.rr_rg4);
        radioGroup1_pre = (TextView) findViewById(R.id.rr_rg1_pre);
        radioGroup2_pre = (TextView) findViewById(R.id.rr_rg2_pre);
        radioGroup3_pre = (TextView) findViewById(R.id.rr_rg3_pre);
        radioGroup4_pre = (TextView) findViewById(R.id.rr_rg4_pre);
        button_back = (Button) findViewById(R.id.rr_back);
        button_save = (Button) findViewById(R.id.rr_save);
        button_save.setVisibility(View.GONE);


        jhpiegoDatabase = new JhpiegoDatabase(this);
        // getUserDisplay();
        getFormDataFromDb();
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
            String query = "select * from " + TABLENAME9;
            Cursor cursor = sqLiteDatabase.query(TABLENAME9, new String[]{COL_SESSION, COL_1, COL_2, COL_3, COL_4}, COL_SESSION + "=?", new String[]{sessionID}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                String col_1 = cursor.getString(cursor.getColumnIndex(COL_1));
                String col_2 = cursor.getString(cursor.getColumnIndex(COL_2));
                String col_3 = cursor.getString(cursor.getColumnIndex(COL_3));
                String col_4 = cursor.getString(cursor.getColumnIndex(COL_4));
                radioGroup1.setText(col_1);
                setColorForText(radioGroup1, col_1);
                radioGroup2.setText(col_2);
                setColorForText(radioGroup2, col_2);

                radioGroup3.setText(col_3);
                setColorForText(radioGroup3, col_3);
                radioGroup4.setText(col_4);
                setColorForText(radioGroup4, col_4);

            }

            Cursor cursorSessionId = sqLiteDatabase.rawQuery("SELECT session from detailsofvisit WHERE facilityname = '" + facilityName + "' AND facilitytype = '" + facilityType + "' ORDER BY id DESC ", null);
            if (cursorSessionId != null && cursorSessionId.getCount() > 1) {
                cursorSessionId.moveToFirst();
                cursorSessionId.moveToNext();
                String lastRecordSessionId = cursorSessionId.getString(0);
                Cursor cursor_pre = sqLiteDatabase.query(TABLENAME9, new String[]{COL_SESSION, COL_1, COL_2, COL_3, COL_4}, COL_SESSION + "=?", new String[]{lastRecordSessionId}, null, null, null, null);
                if (cursor_pre != null && cursor_pre.getCount() > 0) {
                    cursor_pre.moveToFirst();
                    String col_1 = cursor_pre.getString(cursor_pre.getColumnIndex(COL_1));
                    String col_2 = cursor_pre.getString(cursor_pre.getColumnIndex(COL_2));
                    String col_3 = cursor_pre.getString(cursor_pre.getColumnIndex(COL_3));
                    String col_4 = cursor_pre.getString(cursor_pre.getColumnIndex(COL_4));
                    radioGroup1_pre.setText(col_1);
                    radioGroup2_pre.setText(col_2);
                    radioGroup3_pre.setText(col_3);
                    radioGroup4_pre.setText(col_4);

                } else {
                    radioGroup1_pre.setText(getString(R.string.na));
                    radioGroup2_pre.setText(getString(R.string.na));
                    radioGroup3_pre.setText(getString(R.string.na));
                    radioGroup4_pre.setText(getString(R.string.na));
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
