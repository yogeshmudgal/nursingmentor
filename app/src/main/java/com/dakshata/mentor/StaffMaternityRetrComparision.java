package com.dakshata.mentor;

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

import static com.dakshata.mentor.JhpiegoDatabase.COL_LHVSD;
import static com.dakshata.mentor.JhpiegoDatabase.COL_LHVSLR;
import static com.dakshata.mentor.JhpiegoDatabase.COL_LHVTRAINED;
import static com.dakshata.mentor.JhpiegoDatabase.COL_LHVWORKING;
import static com.dakshata.mentor.JhpiegoDatabase.COL_MOD;
import static com.dakshata.mentor.JhpiegoDatabase.COL_MOLR;
import static com.dakshata.mentor.JhpiegoDatabase.COL_OBG;
import static com.dakshata.mentor.JhpiegoDatabase.COL_SBA;
import static com.dakshata.mentor.JhpiegoDatabase.COL_SESSION;
import static com.dakshata.mentor.JhpiegoDatabase.COL_SND;
import static com.dakshata.mentor.JhpiegoDatabase.COL_SNLR;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME7;

/**
 * Created by Aditya on 12/17/2017.
 */

public class StaffMaternityRetrComparision extends AppCompatActivity {
    TextView editText1, editText2, editText3, editText4, editText5, editText6, editText7, editText8, editText9, editText10;

    TextView editText1_pre, editText2_pre, editText3_pre, editText4_pre, editText5_pre, editText6_pre, editText7_pre, editText8_pre, editText9_pre, editText10_pre;

    Button button_save, button_clear;
    JhpiegoDatabase jhpiegoDatabase;
    Toolbar toolbar;
    SharedPreferences sh_Pref, sharedPreferences;
    SharedPreferences.Editor editor, editor1;
    ImageView back;
    TextView textViewHeaderName;
    String sessionID, facilityName, facilityType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_maternity_view_comparision);

        sessionID = getIntent().getStringExtra("session");
        facilityName = getIntent().getStringExtra("facilityName");
        facilityType = getIntent().getStringExtra("facilityType");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.menu_image_opt3));
        setSupportActionBar(toolbar);
        back = (ImageView) findViewById(R.id.back);
        textViewHeaderName = (TextView) findViewById(R.id.header_name);
        textViewHeaderName.setText(getString(R.string.section_b_header_1));
        jhpiegoDatabase = new JhpiegoDatabase(this);
        initViews();
        setViews();
        //getUserDisplay();
        getFormDataFromDb();
        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initViews() {
        editText1 = (TextView) findViewById(R.id.sm_et1);
        editText2 = (TextView) findViewById(R.id.sm_et2);
        editText3 = (TextView) findViewById(R.id.sm_et3);
        editText4 = (TextView) findViewById(R.id.sm_et4);
        editText5 = (TextView) findViewById(R.id.sm_et5);
        editText6 = (TextView) findViewById(R.id.sm_et6);
        editText7 = (TextView) findViewById(R.id.sm_et7);
        editText8 = (TextView) findViewById(R.id.sm_et8);
        editText9 = (TextView) findViewById(R.id.sm_et9);
        editText10 = (TextView) findViewById(R.id.sm_et10);

        editText1_pre = (TextView) findViewById(R.id.sm_et1_pre);
        editText2_pre = (TextView) findViewById(R.id.sm_et2_pre);
        editText3_pre = (TextView) findViewById(R.id.sm_et3_pre);
        editText4_pre = (TextView) findViewById(R.id.sm_et4_pre);
        editText5_pre = (TextView) findViewById(R.id.sm_et5_pre);
        editText6_pre = (TextView) findViewById(R.id.sm_et6_pre);
        editText7_pre = (TextView) findViewById(R.id.sm_et7_pre);
        editText8_pre = (TextView) findViewById(R.id.sm_et8_pre);
        editText9_pre = (TextView) findViewById(R.id.sm_et9_pre);
        editText10_pre = (TextView) findViewById(R.id.sm_et10_pre);

        button_clear = (Button) findViewById(R.id.sm_back);
        button_save = (Button) findViewById(R.id.sm_save);
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
            Log.v("session2", "" + sessionID);
            SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
            String query = "select * from " + TABLENAME7;
            Cursor cursor = sqLiteDatabase.query(TABLENAME7, new String[]{COL_SESSION, COL_OBG, COL_SBA, COL_MOLR, COL_MOD, COL_SNLR, COL_SND, COL_LHVSLR, COL_LHVSD, COL_LHVWORKING, COL_LHVTRAINED}, COL_SESSION + "=?", new String[]{sessionID}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                String col_1 = cursor.getString(cursor.getColumnIndex(COL_OBG));
                String col_2 = cursor.getString(cursor.getColumnIndex(COL_SBA));
                String col_3 = cursor.getString(cursor.getColumnIndex(COL_MOLR));
                String col_4 = cursor.getString(cursor.getColumnIndex(COL_MOD));
                String col_5 = cursor.getString(cursor.getColumnIndex(COL_SNLR));
                String col_6 = cursor.getString(cursor.getColumnIndex(COL_SND));
                String col_7 = cursor.getString(cursor.getColumnIndex(COL_LHVSLR));
                String col_8 = cursor.getString(cursor.getColumnIndex(COL_LHVSD));
                String col_9 = cursor.getString(cursor.getColumnIndex(COL_LHVWORKING));
                String col_10 = cursor.getString(cursor.getColumnIndex(COL_LHVTRAINED));

                editText1.setText(col_1);
                editText2.setText(col_2);
                editText3.setText(col_3);
                editText4.setText(col_4);
                editText5.setText(col_5);
                editText6.setText(col_6);
                editText7.setText(col_7);
                editText8.setText(col_8);
                editText9.setText(col_9);
                editText10.setText(col_10);
            }

            Cursor cursorSessionId = sqLiteDatabase.rawQuery("SELECT session from detailsofvisit WHERE facilityname = '" + facilityName + "' AND facilitytype = '" + facilityType + "' ORDER BY id DESC ", null);
            if (cursorSessionId != null && cursorSessionId.getCount() > 1) {
                cursorSessionId.moveToFirst();
                cursorSessionId.moveToNext();
                String lastRecordSessionId = cursorSessionId.getString(0);
                Cursor cursor_pre = sqLiteDatabase.query(TABLENAME7, new String[]{COL_SESSION, COL_OBG, COL_SBA, COL_MOLR, COL_MOD, COL_SNLR, COL_SND, COL_LHVSLR, COL_LHVSD, COL_LHVWORKING, COL_LHVTRAINED}, COL_SESSION + "=?", new String[]{lastRecordSessionId}, null, null, null, null);
                if (cursor_pre != null && cursor_pre.getCount() > 0) {
                    cursor_pre.moveToFirst();
                    String col_1 = cursor_pre.getString(cursor_pre.getColumnIndex(COL_OBG));
                    String col_2 = cursor_pre.getString(cursor_pre.getColumnIndex(COL_SBA));
                    String col_3 = cursor_pre.getString(cursor_pre.getColumnIndex(COL_MOLR));
                    String col_4 = cursor_pre.getString(cursor_pre.getColumnIndex(COL_MOD));
                    String col_5 = cursor_pre.getString(cursor_pre.getColumnIndex(COL_SNLR));
                    String col_6 = cursor_pre.getString(cursor_pre.getColumnIndex(COL_SND));
                    String col_7 = cursor_pre.getString(cursor_pre.getColumnIndex(COL_LHVSLR));
                    String col_8 = cursor_pre.getString(cursor_pre.getColumnIndex(COL_LHVSD));
                    String col_9 = cursor_pre.getString(cursor_pre.getColumnIndex(COL_LHVWORKING));
                    String col_10 = cursor_pre.getString(cursor_pre.getColumnIndex(COL_LHVTRAINED));

                    editText1_pre.setText(col_1);
                    editText2_pre.setText(col_2);
                    editText3_pre.setText(col_3);
                    editText4_pre.setText(col_4);
                    editText5_pre.setText(col_5);
                    editText6_pre.setText(col_6);
                    editText7_pre.setText(col_7);
                    editText8_pre.setText(col_8);
                    editText7_pre.setText(col_7);
                    editText8_pre.setText(col_8);
                } else {
                    editText1_pre.setText(getString(R.string.na));
                    editText2_pre.setText(getString(R.string.na));
                    editText3_pre.setText(getString(R.string.na));
                    editText4_pre.setText(getString(R.string.na));
                    editText5_pre.setText(getString(R.string.na));
                    editText6_pre.setText(getString(R.string.na));
                    editText7_pre.setText(getString(R.string.na));
                    editText8_pre.setText(getString(R.string.na));
                    editText9_pre.setText(getString(R.string.na));
                    editText10_pre.setText(getString(R.string.na));
                }
            }
        } catch (Exception e) {
        }
    }
}
