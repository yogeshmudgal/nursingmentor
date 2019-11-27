package com.dakshata.mentor;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dakshata.mentor.Utils.MentorConstant;
import com.dakshata.mentor.models.AnswersModel;
import com.dakshata.mentor.models.F6;
import com.dakshata.mentor.models.FormDataSubQuestion;
import com.dakshata.mentor.models.FormDatum;
import com.dakshata.mentor.models.QuetionsMaster;
import com.dakshata.mentor.models.VisitDatum;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
import static com.dakshata.mentor.JhpiegoDatabase.COL_UNIQUE_ID;
import static com.dakshata.mentor.JhpiegoDatabase.COL_USERNAME;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME1;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME6;

/**
 * Created by Aditya.v on 16-12-2017.
 */

public class MentoringPractices extends AppCompatActivity implements LocationListener {
    RadioGroup radioGroup1, radioGroup2, radioGroup3, radioGroup4, radioGroup5, radioGroup6, radioGroup7, radioGroup8, radioGroup9, radioGroup10, radioGroup11, radioGroup12, radioGroup13, radioGroup14, radioGroup15, radioGroup16, radioGroup17, radioGroup18, radioGroup19;
    RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5, radioButton6, radioButton7, radioButton8, radioButton9, radioButton10, radioButton11, radioButton12, radioButton13, radioButton14, radioButton15, radioButton16, radioButton17, radioButton18, radioButton19, radioButton20, radioButton21, radioButton22, radioButton23, radioButton24, radioButton25, radioButton26, radioButton27, radioButton28, radioButton29, radioButton30, radioButton31, radioButton32, radioButton33, radioButton34, radioButton35, radioButton36, radioButton37, radioButton38, radioButton39, radioButton40, radioButton41, radioButton42, radioButton43, radioButton44, radioButton45, radioButton46, radioButton47, radioButton48, radioButton49, radioButton50, radioButton51, radioButton52, radioButton53, radioButton54, radioButton55, radioButton56, radioButton57;
    Button button_save, button_back;
    JhpiegoDatabase jhpiegoDatabase;
    ProgressDialog progressDialog;
    String row_1, row_2, row_3, row_4, row_5, row_6, row_7, row_8, row_9, row_10, row_11, row_12, row_13, row_14, row_15, row_16, row_17, row_18, row_19;
    Toolbar toolbar;
    SharedPreferences sh_Pref, sharedPreferences, sharedPreferencescount;
    SharedPreferences.Editor editor, editor1, editorcount;
    int count = 0;
    String session;
    String username;
    ImageView back;
    TextView textViewHeaderName;
    String ansJson;
    String sessionid;
    public static QuetionsMaster mQuetionsMaster = new QuetionsMaster();

    // Location Access
    LocationManager locationManager;
    String provider, address;
    Location location;
    private int loop = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mentoring_practices);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.menu_image_opt3));
        setSupportActionBar(toolbar);
        back = (ImageView) findViewById(R.id.back);
        textViewHeaderName = (TextView) findViewById(R.id.header_name);
        textViewHeaderName.setText(getString(R.string.section_f_header_1));
        jhpiegoDatabase = new JhpiegoDatabase(this);
        progressDialog = new ProgressDialog(this);

        initViews();
        //getUserDisplay();
        sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
        editor = sh_Pref.edit();

        sessionid = getIntent().getStringExtra("sessionNew");

        username = sh_Pref.getString("Username", "Unknown");
        getSessionOfUser();
        setDefaultValues();
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
        getAllAccessToGPSLocation();
    }

    public void initViews() {
        radioGroup1 = (RadioGroup) findViewById(R.id.mp_rg1);
        radioGroup2 = (RadioGroup) findViewById(R.id.mp_rg2);
        radioGroup3 = (RadioGroup) findViewById(R.id.mp_rg3);
        radioGroup4 = (RadioGroup) findViewById(R.id.mp_rg4);
        radioGroup5 = (RadioGroup) findViewById(R.id.mp_rg5);
        radioGroup6 = (RadioGroup) findViewById(R.id.mp_rg6);
        radioGroup7 = (RadioGroup) findViewById(R.id.mp_rg7);
        radioGroup8 = (RadioGroup) findViewById(R.id.mp_rg8);
        radioGroup9 = (RadioGroup) findViewById(R.id.mp_rg9);
        radioGroup10 = (RadioGroup) findViewById(R.id.mp_rg10);
        radioGroup11 = (RadioGroup) findViewById(R.id.mp_rg11);
        radioGroup12 = (RadioGroup) findViewById(R.id.mp_rg12);
        radioGroup13 = (RadioGroup) findViewById(R.id.mp_rg13);
        radioGroup14 = (RadioGroup) findViewById(R.id.mp_rg14);
        radioGroup15 = (RadioGroup) findViewById(R.id.mp_rg15);
        radioGroup16 = (RadioGroup) findViewById(R.id.mp_rg16);
        radioGroup17 = (RadioGroup) findViewById(R.id.mp_rg17);
        radioGroup18 = (RadioGroup) findViewById(R.id.mp_rg18);
        radioGroup19 = (RadioGroup) findViewById(R.id.mp_rg19);
        radioButton20 = (RadioButton) findViewById(R.id.yes1);
        radioButton21 = (RadioButton) findViewById(R.id.yes2);
        radioButton22 = (RadioButton) findViewById(R.id.yes3);
        radioButton23 = (RadioButton) findViewById(R.id.yes4);
        radioButton24 = (RadioButton) findViewById(R.id.yes5);
        radioButton25 = (RadioButton) findViewById(R.id.yes6);
        radioButton26 = (RadioButton) findViewById(R.id.yes7);
        radioButton27 = (RadioButton) findViewById(R.id.yes8);
        radioButton28 = (RadioButton) findViewById(R.id.yes9);
        radioButton29 = (RadioButton) findViewById(R.id.yes10);
        radioButton30 = (RadioButton) findViewById(R.id.yes11);
        radioButton31 = (RadioButton) findViewById(R.id.yes12);
        radioButton32 = (RadioButton) findViewById(R.id.yes13);
        radioButton33 = (RadioButton) findViewById(R.id.yes14);
        radioButton34 = (RadioButton) findViewById(R.id.yes15);
        radioButton35 = (RadioButton) findViewById(R.id.yes16);
        radioButton36 = (RadioButton) findViewById(R.id.yes17);
        radioButton37 = (RadioButton) findViewById(R.id.yes18);
        radioButton38 = (RadioButton) findViewById(R.id.yes19);
        radioButton39 = (RadioButton) findViewById(R.id.no1);
        radioButton40 = (RadioButton) findViewById(R.id.no2);
        radioButton41 = (RadioButton) findViewById(R.id.no3);
        radioButton42 = (RadioButton) findViewById(R.id.no4);
        radioButton43 = (RadioButton) findViewById(R.id.no5);
        radioButton44 = (RadioButton) findViewById(R.id.no6);
        radioButton45 = (RadioButton) findViewById(R.id.no7);
        radioButton46 = (RadioButton) findViewById(R.id.no8);
        radioButton47 = (RadioButton) findViewById(R.id.no9);
        radioButton48 = (RadioButton) findViewById(R.id.no10);
        radioButton49 = (RadioButton) findViewById(R.id.no11);
        radioButton50 = (RadioButton) findViewById(R.id.no12);
        radioButton51 = (RadioButton) findViewById(R.id.no13);
        radioButton52 = (RadioButton) findViewById(R.id.no14);
        radioButton53 = (RadioButton) findViewById(R.id.no15);
        radioButton54 = (RadioButton) findViewById(R.id.no16);
        radioButton55 = (RadioButton) findViewById(R.id.no17);
        radioButton56 = (RadioButton) findViewById(R.id.no18);
        radioButton57 = (RadioButton) findViewById(R.id.no19);
        button_save = (Button) findViewById(R.id.mp_save);
        button_back = (Button) findViewById(R.id.mp_back);
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
    public void getUserEnteredData() {
        int selectedId = radioGroup1.getCheckedRadioButtonId();
        int selectedId2 = radioGroup2.getCheckedRadioButtonId();
        int selectedId3 = radioGroup3.getCheckedRadioButtonId();
        int selectedId4 = radioGroup4.getCheckedRadioButtonId();
        int selectedId5 = radioGroup5.getCheckedRadioButtonId();
        int selectedId6 = radioGroup6.getCheckedRadioButtonId();
        int selectedId7 = radioGroup7.getCheckedRadioButtonId();
        int selectedId8 = radioGroup8.getCheckedRadioButtonId();
        int selectedId9 = radioGroup9.getCheckedRadioButtonId();
        int selectedId10 = radioGroup10.getCheckedRadioButtonId();
        int selectedId11 = radioGroup11.getCheckedRadioButtonId();
        int selectedId12 = radioGroup12.getCheckedRadioButtonId();
        int selectedId13 = radioGroup13.getCheckedRadioButtonId();
        int selectedId14 = radioGroup14.getCheckedRadioButtonId();
        int selectedId15 = radioGroup15.getCheckedRadioButtonId();
        int selectedId16 = radioGroup16.getCheckedRadioButtonId();
        int selectedId17 = radioGroup17.getCheckedRadioButtonId();
        int selectedId18 = radioGroup18.getCheckedRadioButtonId();
        int selectedId19 = radioGroup19.getCheckedRadioButtonId();

        if (radioGroup1.getCheckedRadioButtonId() < 0 && radioGroup2.getCheckedRadioButtonId() < 0 && radioGroup3.getCheckedRadioButtonId() < 0 && radioGroup4.getCheckedRadioButtonId() < 0 && radioGroup5.getCheckedRadioButtonId() < 0 && radioGroup6.getCheckedRadioButtonId() < 0 && radioGroup7.getCheckedRadioButtonId() < 0 && radioGroup8.getCheckedRadioButtonId() < 0 && radioGroup9.getCheckedRadioButtonId() < 0 && radioGroup10.getCheckedRadioButtonId() < 0 && radioGroup11.getCheckedRadioButtonId() < 0 && radioGroup12.getCheckedRadioButtonId() < 0 && radioGroup13.getCheckedRadioButtonId() < 0 && radioGroup14.getCheckedRadioButtonId() < 0 && radioGroup15.getCheckedRadioButtonId() < 0 && radioGroup16.getCheckedRadioButtonId() < 0 && radioGroup17.getCheckedRadioButtonId() < 0 && radioGroup18.getCheckedRadioButtonId() < 0 && radioGroup19.getCheckedRadioButtonId() < 0) {
        } else {
                   /* progressDialog.setMessage("Please wait...");
                    progressDialog.show();*/
            try {
                radioButton1 = (RadioButton) findViewById(selectedId);
                row_1 = radioButton1.getText().toString();
                if (!row_1.matches("")) {
                    count++;
                    Log.v("mpcount1", "" + count);
                }
            } catch (Exception e) {
            }
            try {
                radioButton2 = (RadioButton) findViewById(selectedId2);
                row_2 = radioButton2.getText().toString();
                if (!row_2.matches("")) {
                    count++;
                    Log.v("mpcount2", "" + count);
                }
            } catch (Exception e) {
            }
            try {
                radioButton3 = (RadioButton) findViewById(selectedId3);
                row_3 = radioButton3.getText().toString();
                if (!row_3.matches("")) {
                    count++;
                    Log.v("mpcount3", "" + count);
                }
            } catch (Exception e) {
            }
            try {
                radioButton4 = (RadioButton) findViewById(selectedId4);
                row_4 = radioButton4.getText().toString();
                if (!row_4.matches("")) {
                    count++;
                    Log.v("mpcount4", "" + count);
                }
            } catch (Exception e) {
            }
            try {
                radioButton5 = (RadioButton) findViewById(selectedId5);
                row_5 = radioButton5.getText().toString();
                if (!row_5.matches("")) {
                    count++;
                    Log.v("mpcount5", "" + count);
                }

            } catch (Exception e) {
            }
            try {
                radioButton6 = (RadioButton) findViewById(selectedId6);
                row_6 = radioButton6.getText().toString();
                if (!row_6.matches("")) {
                    count++;
                    Log.v("mpcount6", "" + count);
                }
            } catch (Exception e) {
            }
            try {
                radioButton7 = (RadioButton) findViewById(selectedId7);
                row_7 = radioButton7.getText().toString();
                if (!row_7.matches("")) {
                    count++;
                    Log.v("mpcount7", "" + count);
                }
            } catch (Exception e) {
            }
            try {
                radioButton8 = (RadioButton) findViewById(selectedId8);
                row_8 = radioButton8.getText().toString();
                if (!row_8.matches("")) {
                    count++;
                    Log.v("mpcount8", "" + count);
                }
            } catch (Exception e) {
            }
            try {
                radioButton9 = (RadioButton) findViewById(selectedId9);
                row_9 = radioButton9.getText().toString();
                if (!row_9.matches("")) {
                    count++;
                    Log.v("mpcount9", "" + count);
                }
            } catch (Exception e) {
            }
            try {
                radioButton10 = (RadioButton) findViewById(selectedId10);
                row_10 = radioButton10.getText().toString();
                if (!row_10.matches("")) {
                    count++;
                    Log.v("mpcount10", "" + count);
                }
            } catch (Exception e) {
            }
            try {
                radioButton11 = (RadioButton) findViewById(selectedId11);
                row_11 = radioButton11.getText().toString();
                if (!row_11.matches("")) {
                    count++;
                    Log.v("mpcount11", "" + count);
                }
            } catch (Exception e) {
            }
            try {
                radioButton12 = (RadioButton) findViewById(selectedId12);
                row_12 = radioButton12.getText().toString();
                if (!row_12.matches("")) {
                    count++;
                    Log.v("mpcount12", "" + count);
                }
            } catch (Exception e) {
            }
            try {
                radioButton13 = (RadioButton) findViewById(selectedId13);
                row_13 = radioButton13.getText().toString();
                if (!row_13.matches("")) {
                    count++;
                    Log.v("mpcount13", "" + count);
                }
            } catch (Exception e) {
            }
            try {
                radioButton14 = (RadioButton) findViewById(selectedId14);
                row_14 = radioButton14.getText().toString();
                if (!row_14.matches("")) {
                    count++;
                    Log.v("mpcount14", "" + count);
                }
            } catch (Exception e) {
            }
            try {
                radioButton15 = (RadioButton) findViewById(selectedId15);
                row_15 = radioButton15.getText().toString();
                if (!row_15.matches("")) {
                    count++;
                    Log.v("mpcount15", "" + count);
                }
            } catch (Exception e) {
            }
            try {
                radioButton16 = (RadioButton) findViewById(selectedId16);
                row_16 = radioButton16.getText().toString();
                if (!row_16.matches("")) {
                    count++;
                    Log.v("mpcount16", "" + count);
                }
            } catch (Exception e) {
            }
            try {
                radioButton17 = (RadioButton) findViewById(selectedId17);
                row_17 = radioButton17.getText().toString();
                if (!row_17.matches("")) {
                    count++;
                    Log.v("mpcount17", "" + count);
                }
            } catch (Exception e) {
            }
            try {
                radioButton18 = (RadioButton) findViewById(selectedId18);
                row_18 = radioButton18.getText().toString();
                if (!row_18.matches("")) {
                    count++;
                    Log.v("mpcount18", "" + count);
                }
            } catch (Exception e) {
            }
            try {
                radioButton19 = (RadioButton) findViewById(selectedId19);
                row_19 = radioButton19.getText().toString();
                if (!row_19.matches("")) {
                    count = count + 1;
                    Log.v("mpcount19", "" + count);
                }
            } catch (Exception e) {
            }

            Log.v("mpcount", "" + count);
            sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
            editor1 = sharedPreferences.edit();
            try {
                SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                Cursor cursor = sqLiteDatabase.query(TABLENAME6, new String[]{COL_SESSION}, COL_SESSION + "=? AND " + COL_UNIQUE_ID + "=?", new String[]{sessionid, String.valueOf(MentorConstant.recordId)}, null, null, null);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    createJson();
//                    jhpiegoDatabase.deleteMentoringPractices(username, row_1, row_2, row_3, row_4, row_5, row_6, row_7, row_8, row_9, row_10, row_11, row_12, row_13, row_14, row_15, row_16, row_17, row_18, row_19, String.valueOf(count), sessionid, ansJson);
                    long row = jhpiegoDatabase.addMentoringPractices(username, row_1, row_2, row_3, row_4, row_5, row_6, row_7, row_8, row_9, row_10, row_11, row_12, row_13, row_14, row_15, row_16, row_17, row_18, row_19, String.valueOf(count), sessionid, ansJson, String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude), String.valueOf(MentorConstant.recordId), 1);
                    SharedPreferences sPref = getSharedPreferences("session", MODE_PRIVATE);
                    jhpiegoDatabase.updateLatAndLongWithDetailsOfVisit(String.valueOf(MentorConstant.recordId), String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude));
                    if (row != -1) {

                        Toast.makeText(getApplicationContext(), getString(R.string.alert_saved_successfully), Toast.LENGTH_SHORT).show();
                        if (!MentorConstant.whichBlockCalled) {
                            sharedPreferencescount = getSharedPreferences("mp" + username, MODE_PRIVATE);
                            editorcount = sharedPreferencescount.edit();
                            editorcount.putString("count" + username, String.valueOf(count));
                            editorcount.commit();
                        }
                        onBackPressed();
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.alert_not_saved_to_db), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    createJson();
                    long row = jhpiegoDatabase.addMentoringPractices(username, row_1, row_2, row_3, row_4, row_5, row_6, row_7, row_8, row_9, row_10, row_11, row_12, row_13, row_14, row_15, row_16, row_17, row_18, row_19, String.valueOf(count), sessionid, ansJson, String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude), String.valueOf(MentorConstant.recordId), 0);
                    SharedPreferences sPref = getSharedPreferences("session", MODE_PRIVATE);
                    jhpiegoDatabase.updateLatAndLongWithDetailsOfVisit(String.valueOf(MentorConstant.recordId), String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude));
                    if (row != -1) {

                        Toast.makeText(getApplicationContext(), getString(R.string.alert_saved_successfully), Toast.LENGTH_SHORT).show();
                        if (!MentorConstant.whichBlockCalled) {
                            sharedPreferencescount = getSharedPreferences("mp" + username, MODE_PRIVATE);
                            editorcount = sharedPreferencescount.edit();
                            editorcount.putString("count" + username, String.valueOf(count));
                            editorcount.commit();
                        }
                        onBackPressed();
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.alert_not_saved_to_db), Toast.LENGTH_SHORT).show();
                    }
                }
                sqLiteDatabase.close();
            } catch (Exception e) {
            }

               /* long row= jhpiegoDatabase.addMentoringPractices(row_1,row_2,row_3,row_4,row_5,row_6,row_7,row_8,row_9,row_10,row_11,row_12,row_13,row_14,row_15,row_16,row_17,row_18,row_19, String.valueOf(count),sessionid);
            if(row!=-1)
            {
                Toast.makeText(getApplicationContext(), getString(R.string.alert_saved_successfully), Toast.LENGTH_SHORT).show();
                sharedPreferencescount=getSharedPreferences("mp",MODE_PRIVATE);
                editorcount=sharedPreferencescount.edit();
                editorcount.putString("count", String.valueOf(count));
                editorcount.commit();
                Intent intent=new Intent(MentoringPractices.this,MainActivity.class);
                startActivity(intent);

            }else{
                Toast.makeText(getApplicationContext(), getString(R.string.alert_not_saved_to_db), Toast.LENGTH_SHORT).show();
            }*/
        }
    }

    public void getSessionOfUser() {
        try {
            SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query(TABLENAME1, new String[]{COL_USERNAME, COL_SESSION}, COL_USERNAME + "=?", new String[]{username}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                session = cursor.getString(cursor.getColumnIndex(COL_SESSION));
            }
            sqLiteDatabase.close();
        } catch (Exception e) {
        }
    }

    public void setDefaultValues() {
        try {
            if (!jhpiegoDatabase.isLastVisitSubmittedMentoringPractices(sessionid)) {
                //    Cursor cursor = jhpiegoDatabase.getLastVisitDataMentoringPractices();
                SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                Cursor cursor = sqLiteDatabase.query(TABLENAME6, new String[]{COL_USERNAME, COL_ROLR, COL_LP, COL_GA, COL_PV, COL_AUA, COL_ACP, COL_CND, COL_AMTSL, COL_ENBC, COL_NR, COL_SCN, COL_PPH, COL_IMPE, COL_IMPO, COL_PP1, COL_DND, COL_DPPH, COL_DPE, COL_DNR}, COL_SESSION + "=?", new String[]{sessionid}, null, null, null);

                //   cursor.moveToFirst();
                if (cursor.getCount() > 0) {
                    cursor.moveToLast();
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
                    if (col_1 != null)
                        if (col_1.toLowerCase().equalsIgnoreCase("yes")) {
                            radioButton20.setChecked(true);
                        } else {
                            radioButton39.setChecked(true);
                        }

                    if (col_2 != null)
                        if (col_2.toLowerCase().equalsIgnoreCase("yes")) {
                            radioButton21.setChecked(true);
                        } else {
                            radioButton40.setChecked(true);
                        }

                    if (col_3 != null)
                        if (col_3.toLowerCase().equalsIgnoreCase("yes")) {
                            radioButton22.setChecked(true);
                        } else {
                            radioButton41.setChecked(true);
                        }

                    if (col_4 != null)
                        if (col_4.toLowerCase().equalsIgnoreCase("yes")) {
                            radioButton23.setChecked(true);
                        } else {
                            radioButton42.setChecked(true);
                        }

                    if (col_5 != null)
                        if (col_5.toLowerCase().equalsIgnoreCase("yes")) {
                            radioButton24.setChecked(true);
                        } else {
                            radioButton43.setChecked(true);
                        }

                    if (col_6 != null)
                        if (col_6.toLowerCase().equalsIgnoreCase("yes")) {
                            radioButton25.setChecked(true);
                        } else {
                            radioButton44.setChecked(true);
                        }

                    if (col_7 != null)
                        if (col_7.toLowerCase().equalsIgnoreCase("yes")) {
                            radioButton26.setChecked(true);
                        } else {
                            radioButton45.setChecked(true);
                        }

                    if (col_8 != null)
                        if (col_8.toLowerCase().equalsIgnoreCase("yes")) {
                            radioButton27.setChecked(true);
                        } else {
                            radioButton46.setChecked(true);
                        }

                    if (col_9 != null)
                        if (col_9.toLowerCase().equalsIgnoreCase("yes")) {
                            radioButton28.setChecked(true);
                        } else {
                            radioButton47.setChecked(true);
                        }

                    if (col_10 != null)
                        if (col_10.toLowerCase().equalsIgnoreCase("yes")) {
                            radioButton29.setChecked(true);
                        } else {
                            radioButton48.setChecked(true);
                        }

                    if (col_11 != null)
                        if (col_11.toLowerCase().equalsIgnoreCase("yes")) {
                            radioButton30.setChecked(true);
                        } else {
                            radioButton49.setChecked(true);
                        }

                    if (col_12 != null)
                        if (col_12.toLowerCase().equalsIgnoreCase("yes")) {
                            radioButton31.setChecked(true);
                        } else {
                            radioButton50.setChecked(true);
                        }

                    if (col_13 != null)
                        if (col_13.toLowerCase().equalsIgnoreCase("yes")) {
                            radioButton32.setChecked(true);
                        } else {
                            radioButton51.setChecked(true);
                        }

                    if (col_14 != null)
                        if (col_14.toLowerCase().equalsIgnoreCase("yes")) {
                            radioButton33.setChecked(true);
                        } else {
                            radioButton52.setChecked(true);
                        }

                    if (col_15 != null)
                        if (col_15.toLowerCase().equalsIgnoreCase("yes")) {
                            radioButton34.setChecked(true);
                        } else {
                            radioButton53.setChecked(true);
                        }

                    if (col_16 != null)
                        if (col_16.toLowerCase().equalsIgnoreCase("yes")) {
                            radioButton35.setChecked(true);
                        } else {
                            radioButton54.setChecked(true);
                        }

                    if (col_17 != null)
                        if (col_17.toLowerCase().equalsIgnoreCase("yes")) {
                            radioButton36.setChecked(true);
                        } else {
                            radioButton55.setChecked(true);
                        }

                    if (col_18 != null)
                        if (col_18.toLowerCase().equalsIgnoreCase("yes")) {
                            radioButton37.setChecked(true);
                        } else {
                            radioButton56.setChecked(true);
                        }

                    if (col_19 != null)
                        if (col_19.toLowerCase().equalsIgnoreCase("yes")) {
                            radioButton38.setChecked(true);
                        } else {
                            radioButton57.setChecked(true);
                        }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getQuestionCode(String question) {

        for (F6 f6 : MentoringPractices.mQuetionsMaster.getF6()) {
            if (f6.getQuestionsName() != null) {
                if (f6.getQuestionsName().replaceAll("\\s+$", "").equalsIgnoreCase(question.replaceAll("\\s+$", ""))){
                    Log.v("District f6", "" + f6.getQCode());
                    return f6.getQCode().toString();
                }
            }
            //something here
        }
        return "";

    }

    private void createJson() {
        AnswersModel mAnswersModel = new AnswersModel();

        mAnswersModel.setUser(username);
        mAnswersModel.setVisitId(sessionid);

        List<VisitDatum> mVisitDatumAns = new ArrayList<VisitDatum>();
        List<FormDatum> formDatumList = new ArrayList<>();
        VisitDatum mVisitDatum = new VisitDatum();
        mVisitDatum.setFormCode("f6");

        FormDatum mFormDatum = new FormDatum();
        mFormDatum.setAns(row_1);
        mFormDatum.setQCode("f6_q1");
        //mVisitDatumAns.add(mVisitDatum);
        formDatumList.add(mFormDatum);

        FormDatum mFormDatumstate = new FormDatum();
        mFormDatumstate.setAns(row_2);
        mFormDatumstate.setQCode("f6_q2");
        formDatumList.add(mFormDatumstate);

        FormDatum mFormDatumdistrict = new FormDatum();
        mFormDatumdistrict.setAns(row_3);
        mFormDatumdistrict.setQCode("f6_q3");
        formDatumList.add(mFormDatumdistrict);

        FormDatum mFormDatumblock = new FormDatum();
        mFormDatumblock.setAns(row_4);
        mFormDatumblock.setQCode("f6_q4");
        formDatumList.add(mFormDatumblock);

        FormDatum mFormDatumfacilitytype = new FormDatum();
        mFormDatumfacilitytype.setAns(row_5);
        mFormDatumfacilitytype.setQCode("f6_q5");
        formDatumList.add(mFormDatumfacilitytype);

        FormDatum mFormDatumfacilityname = new FormDatum();
        mFormDatumfacilityname.setAns(row_6);
        mFormDatumfacilityname.setQCode("f6_q6");
        formDatumList.add(mFormDatumfacilityname);

        FormDatum mFormDatumfacilitylevel = new FormDatum();
        mFormDatumfacilitylevel.setAns(row_7);
        mFormDatumfacilitylevel.setQCode("f6_q7");
        formDatumList.add(mFormDatumfacilitylevel);

        FormDatum mFormDatumdov = new FormDatum();
        mFormDatumdov.setAns(row_8);
        mFormDatumdov.setQCode("f6_q8");
        formDatumList.add(mFormDatumdov);

        FormDatum mFormDatum9 = new FormDatum();
        mFormDatum9.setAns(row_9);
        mFormDatum9.setQCode("f6_q9");
        formDatumList.add(mFormDatum9);

        FormDatum mFormDatum10 = new FormDatum();
        mFormDatum10.setAns(row_10);
        mFormDatum10.setQCode("f6_q10");
        formDatumList.add(mFormDatum10);

        FormDatum mFormDatum11 = new FormDatum();
        mFormDatum11.setAns(row_11);
        mFormDatum11.setQCode("f6_q11");
        formDatumList.add(mFormDatum11);

        FormDatum mFormDatum12 = new FormDatum();
        mFormDatum12.setAns(row_12);
        mFormDatum12.setQCode("f6_q12");
        formDatumList.add(mFormDatum12);

        FormDatum mFormDatum13 = new FormDatum();
        mFormDatum13.setAns(row_13);
        mFormDatum13.setQCode("f6_q13");
        formDatumList.add(mFormDatum13);

        FormDatum mFormDatum14 = new FormDatum();
        mFormDatum14.setAns(row_14);
        mFormDatum14.setQCode("f6_q14");
        formDatumList.add(mFormDatum14);

        FormDatum mFormDatum15 = new FormDatum();
        mFormDatum15.setAns(row_15);
        mFormDatum15.setQCode("f6_q15");
        formDatumList.add(mFormDatum15);

        FormDatum mFormDatum16 = new FormDatum();
        mFormDatum16.setAns(row_16);
        mFormDatum16.setQCode("f6_q16");
        formDatumList.add(mFormDatum16);

        FormDatum mFormDatum17 = new FormDatum();
        mFormDatum17.setAns(row_17);
        mFormDatum17.setQCode("f6_q17");
        formDatumList.add(mFormDatum17);

        FormDatum mFormDatum18 = new FormDatum();
        mFormDatum18.setAns(row_18);
        mFormDatum18.setQCode("f6_q18");
        formDatumList.add(mFormDatum18);

        FormDatum mFormDatum19 = new FormDatum();
        mFormDatum19.setAns(row_19);
        mFormDatum19.setQCode("f6_q19");
        formDatumList.add(mFormDatum19);


        mVisitDatum.setFormData(formDatumList);
        Gson gsonmVisitDatum = new Gson();
        ansJson = gsonmVisitDatum.toJson(mVisitDatum);
        Log.v("ans ", "ans json v: " + ansJson);

        mVisitDatumAns.add(mVisitDatum);
        mAnswersModel.setVisitData(mVisitDatumAns);
        Gson gson = new Gson();
        String fullJson = gson.toJson(mAnswersModel);
        try {
            JSONObject jsonObject = new JSONObject(fullJson);
            //   serverPost(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.v("ans ", "ans json : " + fullJson);
    }

    public void serverPost(JSONObject jsonObject) {

        String url = "http://84538451.ngrok.io/services/bookmark_answers";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("mentoringpractices", "" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("error", "" + error);

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void getAllAccessToGPSLocation() {
        callProvide();
        if (provider != null && !provider.equals("")) {
            // Get the location from the given provider
            callLocationM();
        } else {
            checkLocationPermission();
        }
    }

    private void callProvide() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Creating an empty criteria object
        Criteria criteria = new Criteria();
        // Getting the name of the provider that meets the criteria
        provider = locationManager.getBestProvider(criteria, false);
    }

    private void callLocationM() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (checkLocationPermission()) {
                callProvide();
                location = locationManager.getLastKnownLocation(provider);
                locationManager.requestLocationUpdates(provider, 5000, 1, this);
                if (location != null) {
                    onLocationChanged(location);
                } else {
                    if (locationManager != null) {

                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            MentorConstant.latitude = location.getLatitude();
                            MentorConstant.longitude = location.getLongitude();
                            address = MentorConstant.getAddress(MentoringPractices.this, MentorConstant.latitude, MentorConstant.longitude);
                        }
                    }
                }
            } else {
                checkLocationPermission();
            }
            return;
        }
        callProvide();
        location = locationManager.getLastKnownLocation(provider);
        locationManager.requestLocationUpdates(provider, 5000, 1, this);
        if (location != null) {
            onLocationChanged(location);
        } else {
            if (locationManager != null) {

                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    MentorConstant.latitude = location.getLatitude();
                    MentorConstant.longitude = location.getLongitude();
                    address = MentorConstant.getAddress(MentoringPractices.this, MentorConstant.latitude, MentorConstant.longitude);
                }
            }
        }
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            loop = loop + 1;
            if (loop > 8) return false;

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(MentoringPractices.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        MentorConstant.MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        MentorConstant.MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MentorConstant.MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callLocationM();
                } else {
                    checkLocationPermission();
                }
                return;
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location.getLatitude() != 0 && location.getLongitude() != 0) {
            MentorConstant.latitude = location.getLatitude();
            MentorConstant.longitude = location.getLongitude();
            address = MentorConstant.getAddress(MentoringPractices.this, MentorConstant.latitude, MentorConstant.longitude);
        } else {
            address = null;
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

}
