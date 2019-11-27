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
import static com.dakshata.mentor.JhpiegoDatabase.COL_SESSION;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME8;

/**
 * Created by Aditya on 12/17/2017.
 */

public class DrugSupplyRetrComparision extends AppCompatActivity {
    TextView radioGroup1, radioGroup2, radioGroup3, radioGroup4, radioGroup5, radioGroup6, radioGroup7, radioGroup8, radioGroup9, radioGroup10, radioGroup11, radioGroup12, radioGroup13, radioGroup14, radioGroup15, radioGroup16, radioGroup17, radioGroup18, radioGroup19, radioGroup20, radioGroup21, radioGroup22, radioGroup23, radioGroup24, radioGroup25, radioGroup26;

    TextView radioGroup1_pre, radioGroup2_pre, radioGroup3_pre, radioGroup4_pre, radioGroup5_pre, radioGroup6_pre, radioGroup7_pre, radioGroup8_pre, radioGroup9_pre, radioGroup10_pre, radioGroup11_pre, radioGroup12_pre, radioGroup13_pre, radioGroup14_pre, radioGroup15_pre, radioGroup16_pre, radioGroup17_pre, radioGroup18_pre, radioGroup19_pre, radioGroup20_pre, radioGroup21_pre, radioGroup22_pre, radioGroup23_pre, radioGroup24_pre, radioGroup25_pre, radioGroup26_pre;

    RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5, radioButton6, radioButton7, radioButton8, radioButton9, radioButton10, radioButton11, radioButton12, radioButton13, radioButton14, radioButton15, radioButton16, radioButton17, radioButton18, radioButton19, radioButton20, radioButton21, radioButton22, radioButton23, radioButton24, radioButton25, radioButton26;
    Button button_save, button_back;
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
        setContentView(R.layout.labour_room_view_comparision);

        sessionID = getIntent().getStringExtra("session");
        facilityName = getIntent().getStringExtra("facilityName");
        facilityType = getIntent().getStringExtra("facilityType");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.menu_image_opt3));
        setSupportActionBar(toolbar);
        back = (ImageView) findViewById(R.id.back);
        textViewHeaderName = (TextView) findViewById(R.id.header_name);
        textViewHeaderName.setText(getString(R.string.section_d_header_1));
        jhpiegoDatabase = new JhpiegoDatabase(this);
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

    public void initViews() {
        radioGroup1 = (TextView) findViewById(R.id.lr_rg1);
        radioGroup2 = (TextView) findViewById(R.id.lr_rg2);
        radioGroup3 = (TextView) findViewById(R.id.lr_rg3);
        radioGroup4 = (TextView) findViewById(R.id.lr_rg4);
        radioGroup5 = (TextView) findViewById(R.id.lr_rg5);
        radioGroup6 = (TextView) findViewById(R.id.lr_rg6);
        radioGroup7 = (TextView) findViewById(R.id.lr_rg7);
        radioGroup8 = (TextView) findViewById(R.id.lr_rg8);
        radioGroup9 = (TextView) findViewById(R.id.lr_rg9);
        radioGroup10 = (TextView) findViewById(R.id.lr_rg10);
        radioGroup11 = (TextView) findViewById(R.id.lr_rg11);
        radioGroup12 = (TextView) findViewById(R.id.lr_rg12);
        radioGroup13 = (TextView) findViewById(R.id.lr_rg13);
        radioGroup14 = (TextView) findViewById(R.id.lr_rg14);
        radioGroup15 = (TextView) findViewById(R.id.lr_rg15);
        radioGroup16 = (TextView) findViewById(R.id.lr_rg16);
        radioGroup17 = (TextView) findViewById(R.id.lr_rg17);
        radioGroup18 = (TextView) findViewById(R.id.lr_rg18);
        radioGroup19 = (TextView) findViewById(R.id.lr_rg19);
        radioGroup20 = (TextView) findViewById(R.id.lr_rg20);
        radioGroup21 = (TextView) findViewById(R.id.lr_rg21);
        radioGroup22 = (TextView) findViewById(R.id.lr_rg22);
        radioGroup23 = (TextView) findViewById(R.id.lr_rg23);
        radioGroup24 = (TextView) findViewById(R.id.lr_rg24);
        radioGroup25 = (TextView) findViewById(R.id.lr_rg25);
        radioGroup26 = (TextView) findViewById(R.id.lr_rg26);
        button_save = (Button) findViewById(R.id.lr_save);
        button_back = (Button) findViewById(R.id.lr_back);

        radioGroup1_pre = (TextView) findViewById(R.id.lr_rg1_pre);
        radioGroup2_pre = (TextView) findViewById(R.id.lr_rg2_pre);
        radioGroup3_pre = (TextView) findViewById(R.id.lr_rg3_pre);
        radioGroup4_pre = (TextView) findViewById(R.id.lr_rg4_pre);
        radioGroup5_pre = (TextView) findViewById(R.id.lr_rg5_pre);
        radioGroup6_pre = (TextView) findViewById(R.id.lr_rg6_pre);
        radioGroup7_pre = (TextView) findViewById(R.id.lr_rg7_pre);
        radioGroup8_pre = (TextView) findViewById(R.id.lr_rg8_pre);
        radioGroup9_pre = (TextView) findViewById(R.id.lr_rg9_pre);
        radioGroup10_pre = (TextView) findViewById(R.id.lr_rg10_pre);
        radioGroup11_pre = (TextView) findViewById(R.id.lr_rg11_pre);
        radioGroup12_pre = (TextView) findViewById(R.id.lr_rg12_pre);
        radioGroup13_pre = (TextView) findViewById(R.id.lr_rg13_pre);
        radioGroup14_pre = (TextView) findViewById(R.id.lr_rg14_pre);
        radioGroup15_pre = (TextView) findViewById(R.id.lr_rg15_pre);
        radioGroup16_pre = (TextView) findViewById(R.id.lr_rg16_pre);
        radioGroup17_pre = (TextView) findViewById(R.id.lr_rg17_pre);
        radioGroup18_pre = (TextView) findViewById(R.id.lr_rg18_pre);
        radioGroup19_pre = (TextView) findViewById(R.id.lr_rg19_pre);
        radioGroup20_pre = (TextView) findViewById(R.id.lr_rg20_pre);
        radioGroup21_pre = (TextView) findViewById(R.id.lr_rg21_pre);
        radioGroup22_pre = (TextView) findViewById(R.id.lr_rg22_pre);
        radioGroup23_pre = (TextView) findViewById(R.id.lr_rg23_pre);
        radioGroup24_pre = (TextView) findViewById(R.id.lr_rg24_pre);
        radioGroup25_pre = (TextView) findViewById(R.id.lr_rg25_pre);
        radioGroup26_pre = (TextView) findViewById(R.id.lr_rg26_pre);
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
            String query = "select * from " + TABLENAME8;
            Cursor cursor = sqLiteDatabase.query(TABLENAME8, new String[]{COL_SESSION, COL_1, COL_2, COL_3, COL_4, COL_5, COL_6, COL_7, COL_8, COL_9, COL_10, COL_11, COL_12, COL_13, COL_14, COL_15, COL_16, COL_17, COL_18, COL_19, COL_20, COL_21, COL_22, COL_23, COL_24, COL_25, COL_26}, COL_SESSION + "=?", new String[]{sessionID}, null, null, null);
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
                radioGroup20.setText(col_20);
                setColorForText(radioGroup20, col_20);
                radioGroup21.setText(col_21);
                setColorForText(radioGroup21, col_21);
                radioGroup22.setText(col_22);
                setColorForText(radioGroup22, col_22);
                radioGroup23.setText(col_23);
                setColorForText(radioGroup23, col_23);
                radioGroup24.setText(col_24);
                setColorForText(radioGroup24, col_24);
                radioGroup25.setText(col_25);
                setColorForText(radioGroup25, col_25);
                radioGroup26.setText(col_26);
                setColorForText(radioGroup26, col_26);
            }

            Cursor cursorSessionId = sqLiteDatabase.rawQuery("SELECT session from detailsofvisit WHERE facilityname = '" + facilityName + "' AND facilitytype = '" + facilityType + "' ORDER BY id DESC ", null);
            if (cursorSessionId != null && cursorSessionId.getCount() > 1) {
                cursorSessionId.moveToFirst();
                cursorSessionId.moveToNext();
                String lastRecordSessionId = cursorSessionId.getString(0);

                Cursor cursor_pre = sqLiteDatabase.query(TABLENAME8, new String[]{COL_SESSION, COL_1, COL_2, COL_3, COL_4, COL_5, COL_6, COL_7, COL_8, COL_9, COL_10, COL_11, COL_12, COL_13, COL_14, COL_15, COL_16, COL_17, COL_18, COL_19, COL_20, COL_21, COL_22, COL_23, COL_24, COL_25, COL_26}, COL_SESSION + "=?", new String[]{lastRecordSessionId}, null, null, null, null);
                if (cursor_pre != null && cursor_pre.getCount() > 0) {
                    cursor_pre.moveToFirst();
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
                    radioGroup11.setText(col_11);
                    radioGroup12_pre.setText(col_12);
                    radioGroup13_pre.setText(col_13);
                    radioGroup14_pre.setText(col_14);
                    radioGroup15_pre.setText(col_15);
                    radioGroup16_pre.setText(col_16);
                    radioGroup17_pre.setText(col_17);
                    radioGroup18_pre.setText(col_18);
                    radioGroup19_pre.setText(col_19);
                    radioGroup20_pre.setText(col_20);
                    radioGroup21_pre.setText(col_21);
                    radioGroup22_pre.setText(col_22);
                    radioGroup23_pre.setText(col_23);
                    radioGroup24_pre.setText(col_24);
                    radioGroup25_pre.setText(col_25);
                    radioGroup26_pre.setText(col_26);
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
                    radioGroup20_pre.setText(getString(R.string.na));
                    radioGroup21_pre.setText(getString(R.string.na));
                    radioGroup22_pre.setText(getString(R.string.na));
                    radioGroup23_pre.setText(getString(R.string.na));
                    radioGroup24_pre.setText(getString(R.string.na));
                    radioGroup25_pre.setText(getString(R.string.na));
                    radioGroup26_pre.setText(getString(R.string.na));
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
