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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.dakshata.mentor.models.F2;
import com.dakshata.mentor.models.FormDatum;
import com.dakshata.mentor.models.QuetionsMaster;
import com.dakshata.mentor.models.VisitDatum;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.dakshata.mentor.JhpiegoDatabase.COL_ID;
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
import static com.dakshata.mentor.JhpiegoDatabase.COL_UNIQUE_ID;
import static com.dakshata.mentor.JhpiegoDatabase.COL_USERNAME;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME7;
import static com.dakshata.mentor.JhpiegoDatabase.*;

/**
 * Created by Aditya on 12/17/2017.
 */
public class StaffMaternity extends AppCompatActivity implements LocationListener {
    EditText editText1, editText2, editText3, editText4, editText5, editText6, editText7, editText8, editText9, editText10;
    Button button_save, button_clear;
    JhpiegoDatabase jhpiegoDatabase;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    String row_0,row_1, row_2, row_3, row_4, row_5, row_6, row_7, row_8, row_9, row_10,row_11,row_12,row_13,row_14,row_15,row_16,row_17,row_18,row_19;
    SharedPreferences sh_Pref, sharedPreferences, sharedPreferencescount;
    SharedPreferences.Editor editor, editor1, editorcount;
    int count = 0;
    String session;
    String username;
    ImageView back;
    String sessionid;
    TextView textViewHeaderName;
    String ansJson;
    EditText sm_etdelLoad,sm_etNofMember,sm_etNHMToolkit,sm_etvarInStaff,sm_etNumberOfLR,sm_etTableRequired,sm_etVarianceInTables
            ,sm_et_nameOFNodal,sm_et_mobileOfNodal;
    public static QuetionsMaster mQuetionsMaster = new QuetionsMaster();
    Button sm_previous_visit;
    boolean isCurrentRecordBackupExist = false;

    // Location Access
    LocationManager locationManager;
    String provider, address;
    Location location;
    private int loop = 0;
    private int labourTable=0;

    private int staffRequired=0;

    private int totalStaffAvailable=0;
    private int lt_required=0;
    private int lt_variance=0;

    private RadioGroup rgCaesarian;
    private String facility_performing_caserian="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_maternity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.menu_image_opt3));
        setSupportActionBar(toolbar);
        back = (ImageView) findViewById(R.id.back);

        sm_etdelLoad = findViewById(R.id.sm_etdelLoad);
        sm_etNofMember = findViewById(R.id.sm_etNofMember);
        sm_etNHMToolkit = findViewById(R.id.sm_etNHMToolkit);
        sm_etvarInStaff = findViewById(R.id.sm_etvarInStaff);
        sm_etNumberOfLR = findViewById(R.id.sm_etNumberOfLR);
        sm_etTableRequired = findViewById(R.id.sm_etTableRequired);
        sm_etVarianceInTables = findViewById(R.id.sm_etVarianceInTables);
        sm_et_nameOFNodal = findViewById(R.id.sm_et_nameOFNodal);
        sm_et_mobileOfNodal = findViewById(R.id.sm_et_mobileOfNodal);
        rgCaesarian=(RadioGroup)findViewById(R.id.rgCaesarian);

        rgCaesarian.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rbYes_Caesarian:
                        facility_performing_caserian = "Yes";
                        break;
                    case R.id.rbNo_Caesarian:
                        facility_performing_caserian = "No";
                        break;
                    case R.id.rbNA_Caesarian:
                        facility_performing_caserian = "NA";
                        break;
                }
            }
        });


        sm_etvarInStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sm_etvarInStaff.setText((Integer.parseInt(sm_etNHMToolkit.getText().toString())-Integer.parseInt(sm_etNofMember.getText().toString()))+"");
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        textViewHeaderName = (TextView) findViewById(R.id.header_name);
        textViewHeaderName.setText(getString(R.string.section_b_header_1));
        jhpiegoDatabase = new JhpiegoDatabase(this);
        progressDialog = new ProgressDialog(this);
        initViews();
        //getUserDisplay();
        sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
        editor = sh_Pref.edit();

        sessionid = getIntent().getStringExtra("sessionNew");

        username = sh_Pref.getString("Username", "Unknown");
        Log.v("username", "" + username);
        //getSessionOfUser();
        setDefaultValues();

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getUserEnteredData();
            }
        });



        sm_etdelLoad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    /*if(Integer.parseInt(sm_etdelLoad.getText().toString())<20){

                        labourTable=1;
                    }*/ if(/*Integer.parseInt(sm_etdelLoad.getText().toString())>=20 &&*/
                            Integer.parseInt(sm_etdelLoad.getText().toString())<=99){
                        labourTable=2;
                        staffRequired=4;
                    }
                     if(Integer.parseInt(sm_etdelLoad.getText().toString())>=100 &&
                            Integer.parseInt(sm_etdelLoad.getText().toString())<=199){
                        labourTable=4;
                        staffRequired=14;
                    } if(Integer.parseInt(sm_etdelLoad.getText().toString())>=200 &&
                            Integer.parseInt(sm_etdelLoad.getText().toString())<=499){
                        labourTable=6;
                        staffRequired=20;
                    }  if(Integer.parseInt(sm_etdelLoad.getText().toString())>=500){
                        labourTable=8;
                        staffRequired=24;
                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                sm_etNHMToolkit.setText(staffRequired+"");
                sm_etTableRequired.setText(labourTable+"");

                try {
                    sm_etvarInStaff.setText((Integer.parseInt(sm_etNHMToolkit.getText().toString())-Integer.parseInt(sm_etNofMember.getText().toString()))+"");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        sm_etNumberOfLR.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int x=0;
                if(s.length()>0){
                    x = Integer.parseInt(s.toString());
                    lt_required =x;
                }
                if(sm_etNumberOfLR.getText().toString()!=null&&sm_etNumberOfLR.getText().length()>0){
                    lt_variance = labourTable-lt_required;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                sm_etVarianceInTables.setText(lt_variance+"");
                try {
                    sm_etvarInStaff.setText((Integer.parseInt(sm_etNHMToolkit.getText().toString())-Integer.parseInt(sm_etNofMember.getText().toString()))+"");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        editText5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int x=0;
                if(s.length()>0){
                    x = Integer.parseInt(s.toString());
                    totalStaffAvailable =x;

                }
                if(editText7.getText().toString()!=null&&editText7.getText().length()>0){
                    totalStaffAvailable = totalStaffAvailable+Integer.parseInt(editText7.getText().toString());
                }
                if(editText9.getText().toString()!=null&&editText9.getText().length()>0){
                    totalStaffAvailable = totalStaffAvailable+Integer.parseInt(editText9.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                sm_etNofMember.setText(totalStaffAvailable+"");

                try {
                    sm_etvarInStaff.setText((Integer.parseInt(sm_etNHMToolkit.getText().toString())-Integer.parseInt(sm_etNofMember.getText().toString()))+"");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        editText7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int x=0;
                if(s.length()>0){
                    x = Integer.parseInt(s.toString());
                    totalStaffAvailable=x;

                }
                if(editText5.getText().toString()!=null&&editText5.getText().length()>0){
                    totalStaffAvailable = totalStaffAvailable+Integer.parseInt(editText5.getText().toString());
                }
                if(editText9.getText().toString()!=null&&editText9.getText().length()>0){
                    totalStaffAvailable = totalStaffAvailable+Integer.parseInt(editText9.getText().toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                sm_etNofMember.setText(totalStaffAvailable+"");

                try {
                    sm_etvarInStaff.setText((Integer.parseInt(sm_etNHMToolkit.getText().toString())-Integer.parseInt(sm_etNofMember.getText().toString()))+"");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        editText9.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int x=0;
                if(s.length()>0){
                    x = Integer.parseInt(s.toString());
                    totalStaffAvailable=x;

                }
                if(editText7.getText().toString()!=null&&editText7.getText().length()>0){
                    totalStaffAvailable = totalStaffAvailable+Integer.parseInt(editText7.getText().toString());
                }
                if(editText5.getText().toString()!=null&&editText5.getText().length()>0){
                    totalStaffAvailable = totalStaffAvailable+Integer.parseInt(editText5.getText().toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                sm_etNofMember.setText(totalStaffAvailable+"");
                try {
                    sm_etvarInStaff.setText((Integer.parseInt(sm_etNHMToolkit.getText().toString())-Integer.parseInt(sm_etNofMember.getText().toString()))+"");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        sm_previous_visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                int levelPosition = 1;
                if (isCurrentRecordBackupExist) levelPosition = 2;
                else levelPosition = 1;
                Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLENAME7 + " ORDER BY id DESC LIMIT " + levelPosition, null);
                if (cursor.getCount() >= levelPosition) {
                    if (isCurrentRecordBackupExist) cursor.moveToLast();
                    else cursor.moveToFirst();

                    sm_etdelLoad.setText(cursor.getString(cursor.getColumnIndex(COL_NSEL)));
                    editText1.setText(cursor.getString(cursor.getColumnIndex(COL_OBG)));
                    editText2.setText(cursor.getString(cursor.getColumnIndex(COL_SBA)));
                    editText3.setText(cursor.getString(cursor.getColumnIndex(COL_MOLR)));
                    editText4.setText(cursor.getString(cursor.getColumnIndex(COL_MOD)));
                    editText5.setText(cursor.getString(cursor.getColumnIndex(COL_SNLR)));
                    editText6.setText(cursor.getString(cursor.getColumnIndex(COL_SND)));
                    editText7.setText(cursor.getString(cursor.getColumnIndex(COL_LHVSLR)));
                    editText8.setText(cursor.getString(cursor.getColumnIndex(COL_LHVSD)));
                    editText9.setText(cursor.getString(cursor.getColumnIndex(COL_LHVWORKING)));
                    editText10.setText(cursor.getString(cursor.getColumnIndex(COL_LHVTRAINED)));

                    sm_etNHMToolkit.setText(cursor.getString(cursor.getColumnIndex(COL_NSR_MNHToolKit)));
                    sm_etvarInStaff.setText(cursor.getString(cursor.getColumnIndex(COL_VAR_Staff)));
                    sm_etNumberOfLR.setText(cursor.getString(cursor.getColumnIndex(COL_Labour_TableLR)));
                    sm_etTableRequired.setText(cursor.getString(cursor.getColumnIndex(COL_Labour_TableReq)));
                    sm_etVarianceInTables.setText(cursor.getString(cursor.getColumnIndex(COL_Variance_LbrTbl)));
                    if(cursor.getString(cursor.getColumnIndex(COL_Caesarian_Act)).equalsIgnoreCase("Yes"))
                    rgCaesarian.check(R.id.rbYes_Caesarian);
                    else if(cursor.getString(cursor.getColumnIndex(COL_Caesarian_Act)).equalsIgnoreCase("No"))
                        rgCaesarian.check(R.id.rbNo_Caesarian);
                    else if(cursor.getString(cursor.getColumnIndex(COL_Caesarian_Act)).equalsIgnoreCase("No"))
                        rgCaesarian.check(R.id.rbNA_Caesarian);
                    sm_et_nameOFNodal.setText(cursor.getString(cursor.getColumnIndex(COL_Person_InCharge)));
                    sm_et_mobileOfNodal.setText(cursor.getString(cursor.getColumnIndex(COL_Mobile_InCharge)));
                    sm_etdelLoad.setText(cursor.getString(cursor.getColumnIndex(COL_AMDL)));




                } else {
                    Toast.makeText(getApplicationContext(), "No previous data is available.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //  progressDialog.dismiss();
        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getAllAccessToGPSLocation();

    }

    public void initViews() {
        editText1 = (EditText) findViewById(R.id.sm_et1);
        editText2 = (EditText) findViewById(R.id.sm_et2);
        editText3 = (EditText) findViewById(R.id.sm_et3);
        editText4 = (EditText) findViewById(R.id.sm_et4);
        editText5 = (EditText) findViewById(R.id.sm_et5);
        editText6 = (EditText) findViewById(R.id.sm_et6);
        editText7 = (EditText) findViewById(R.id.sm_et7);
        editText8 = (EditText) findViewById(R.id.sm_et8);
        editText9 = (EditText) findViewById(R.id.sm_et9);
        editText10 = (EditText) findViewById(R.id.sm_et10);
        button_clear = (Button) findViewById(R.id.sm_back);
        button_save = (Button) findViewById(R.id.sm_save);
        sm_previous_visit = (Button) findViewById(R.id.sm_previous_visit);

        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editText1.getText().toString().equalsIgnoreCase("")) {
//                    Toast.makeText(getApplicationContext(), getString(R.string.required) + " " + getString(R.string.section_b_label_1), Toast.LENGTH_LONG).show();
                } else {
                    if (!editText2.getText().toString().equalsIgnoreCase("")) {
                        if (Integer.parseInt(editText1.getText().toString()) >= Integer.parseInt(editText2.getText().toString())) {

                        } else {
                            editText2.setText(editText2.getText().toString().substring(0, editText2.getText().toString().length() - 1));
                            Toast.makeText(getApplicationContext(), R.string.alert_staff_1, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editText4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editText3.getText().toString().equalsIgnoreCase("")) {
//                    Toast.makeText(getApplicationContext(), getString(R.string.required) + " " + getString(R.string.section_b_label_3), Toast.LENGTH_LONG).show();
                } else {
                    if (!editText4.getText().toString().equalsIgnoreCase("")) {
                        if (Integer.parseInt(editText3.getText().toString()) >= Integer.parseInt(editText4.getText().toString())) {

                        } else {
                            editText4.setText(editText4.getText().toString().substring(0, editText4.getText().toString().length() - 1));
                            Toast.makeText(getApplicationContext(), R.string.alert_staff_2, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editText6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editText5.getText().toString().equalsIgnoreCase("")) {
//                    Toast.makeText(getApplicationContext(), getString(R.string.required) + " " + getString(R.string.section_b_label_5), Toast.LENGTH_LONG).show();
                } else {
                    if (!editText6.getText().toString().equalsIgnoreCase("")) {
                        if (Integer.parseInt(editText5.getText().toString()) >= Integer.parseInt(editText6.getText().toString())) {

                        } else {
                            editText6.setText(editText6.getText().toString().substring(0, editText6.getText().toString().length() - 1));
                            Toast.makeText(getApplicationContext(), R.string.alert_staff_3, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editText8.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editText7.getText().toString().equalsIgnoreCase("")) {
//                    Toast.makeText(getApplicationContext(), getString(R.string.required) + " " + getString(R.string.section_b_label_7), Toast.LENGTH_LONG).show();
                } else {
                    if (!editText8.getText().toString().equalsIgnoreCase("")) {
                        if (Integer.parseInt(editText7.getText().toString()) >= Integer.parseInt(editText8.getText().toString())) {

                        } else {
                            editText8.setText(editText8.getText().toString().substring(0, editText8.getText().toString().length() - 1));
                            Toast.makeText(getApplicationContext(), R.string.alert_staff_4, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editText10.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editText9.getText().toString().equalsIgnoreCase("")) {
//                    Toast.makeText(getApplicationContext(), getString(R.string.required) + " " + getString(R.string.section_b_label_9), Toast.LENGTH_LONG).show();
                } else {
                    if (!editText10.getText().toString().equalsIgnoreCase("")) {
                        if (Integer.parseInt(editText9.getText().toString()) >= Integer.parseInt(editText10.getText().toString())) {

                        } else {
                            editText10.setText(editText10.getText().toString().substring(0, editText10.getText().toString().length() - 1));
                            Toast.makeText(getApplicationContext(), R.string.alert_staff_5, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void Pback(View view) {
        super.onBackPressed();

    }

    public void getUserEnteredData() {
        row_0=sm_etdelLoad.getText().toString();
        row_1 = editText1.getText().toString();
        row_2 = editText2.getText().toString();
        row_3 = editText3.getText().toString();
        row_4 = editText4.getText().toString();
        row_5 = editText5.getText().toString();
        row_6 = editText6.getText().toString();
        row_7 = editText7.getText().toString();
        row_8 = editText8.getText().toString();
        row_9 = editText9.getText().toString();
        row_10 = editText10.getText().toString();
        row_11= sm_etNofMember.getText().toString();
        row_12=sm_etNHMToolkit.getText().toString();
        row_13=sm_etvarInStaff.getText().toString();
        row_14=sm_etNumberOfLR.getText().toString();
        row_15=sm_etTableRequired.getText().toString();
        row_16=sm_etVarianceInTables.getText().toString();
        row_17=facility_performing_caserian;
        row_18=sm_et_nameOFNodal.getText().toString();
        row_19=sm_et_mobileOfNodal.getText().toString();

        String[] data=new String[]{row_11,row_12,row_13,row_14,row_15,row_16,row_17,row_18,row_19,row_0};


        if (!isValidationCorrectOrNot(row_1, row_2, getString(R.string.required) + " " + getString(R.string.section_b_label_1), getString(R.string.required) + " " + getString(R.string.section_b_label_2), getString(R.string.alert_staff_1))) {
            return;
        }
        if (!isValidationCorrectOrNot(row_3, row_4, getString(R.string.required) + " " + getString(R.string.section_b_label_3), getString(R.string.required) + " " + getString(R.string.section_b_label_4), getString(R.string.alert_staff_2))) {
            return;
        }
        if (!isValidationCorrectOrNot(row_5, row_6, getString(R.string.required) + " " + getString(R.string.section_b_label_5), getString(R.string.required) + " " + getString(R.string.section_b_label_6), getString(R.string.alert_staff_3))) {
            return;
        }
        if (!isValidationCorrectOrNot(row_7, row_8, getString(R.string.required) + " " + getString(R.string.section_b_label_7), getString(R.string.required) + " " + getString(R.string.section_b_label_8), getString(R.string.alert_staff_4))) {
            return;
        }
        if (!isValidationCorrectOrNot(row_9, row_10, getString(R.string.required) + " " + getString(R.string.section_b_label_9), getString(R.string.required) + " " + getString(R.string.section_b_label_10), getString(R.string.alert_staff_5))) {
            return;
        }

        try {
            if (!row_0.matches("")) {
                count++;
            }
            if (!row_1.matches("")) {
                count++;
            }
            if (!row_2.matches("")) {
                count++;
            }
            if (!row_3.matches("")) {
                count++;
            }
            if (!row_4.matches("")) {
                count++;
            }
            if (!row_5.matches("")) {
                count++;
            }
            if (!row_6.matches("")) {
                count++;
            }
            if (!row_7.matches("")) {
                count++;
            }
            if (!row_8.matches("")) {
                count++;
            }
            if (!row_9.matches("")) {
                count++;
            }
            if (!row_10.matches("")) {
                count++;
            }
            if (!row_11.matches("")) {
                count++;
            }
            if (!row_12.matches("")) {
                count++;
            }
            if (!row_13.matches("")) {
                count++;
            }
            if (!row_14.matches("")) {
                count++;
            }
            if (!row_15.matches("")) {
                count++;
            }
            if (!row_16.matches("")) {
                count++;
            }
            if (!row_17.matches("")) {
                count++;
            }
            if (!row_18.matches("")) {
                count++;
            }
            if (!row_19.matches("")) {
                count++;
            }

        } catch (Exception e) {
        }
                /*if (row_1.length()>3 || row_2.length()>3|| row_3.length()>3|| row_4.length()>3|| row_5.length()>3|| row_6.length()>3|| row_7.length()>3|| row_8.length()>3){
                    Toast.makeText(getApplicationContext(),"Should be a 3 digit number",Toast.LENGTH_SHORT).show();
                    editText1.setError("Should be a 3 digit number");
                }*/

        if (row_1.matches("") && row_2.matches("") && row_3.matches("") && row_4.matches("") && row_5.matches("") && row_6.matches("") && row_7.matches("") && row_8.matches("") && row_9.matches("") && row_10.matches("")) {

        } else {
                   /* progressDialog.setMessage("Please wait...");
                    progressDialog.show();*/
            sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
            editor1 = sharedPreferences.edit();
            try {
                SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                Cursor cursor = sqLiteDatabase.query(TABLENAME7, new String[]{COL_SESSION}, COL_SESSION + "=? AND " + COL_UNIQUE_ID + "=?", new String[]{sessionid, String.valueOf(MentorConstant.recordId)}, null, null, null);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    createJson();
//                    jhpiegoDatabase.deleteStaffMaternity(username, row_1, row_2, row_3, row_4, row_5, row_6, row_7, row_8, String.valueOf(count), sessionid, ansJson, row_9, row_10);
                    long row = jhpiegoDatabase.addStaffMaternity(username, row_1, row_2, row_3, row_4, row_5, row_6, row_7, row_8, String.valueOf(count), sessionid, ansJson, row_9, row_10, String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude), String.valueOf(MentorConstant.recordId), 1,data);
                    SharedPreferences sPref = getSharedPreferences("session", MODE_PRIVATE);
                    jhpiegoDatabase.updateLatAndLongWithDetailsOfVisit(String.valueOf(MentorConstant.recordId), String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude));
                    if (row != -1) {
                        Toast.makeText(getApplicationContext(), getString(R.string.alert_saved_successfully), Toast.LENGTH_SHORT).show();
                        if (!MentorConstant.whichBlockCalled) {
                            sharedPreferencescount = getSharedPreferences("sm" + username, MODE_PRIVATE);
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
                    long row = jhpiegoDatabase.addStaffMaternity(username, row_1, row_2, row_3, row_4, row_5, row_6, row_7, row_8, String.valueOf(count), sessionid, ansJson, row_9, row_10, String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude), String.valueOf(MentorConstant.recordId), 0,data);
                    SharedPreferences sPref = getSharedPreferences("session", MODE_PRIVATE);
                    jhpiegoDatabase.updateLatAndLongWithDetailsOfVisit(String.valueOf(MentorConstant.recordId), String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude));
                    if (row != -1) {

                        Toast.makeText(getApplicationContext(), getString(R.string.alert_saved_successfully), Toast.LENGTH_SHORT).show();
                        if (!MentorConstant.whichBlockCalled) {
                            sharedPreferencescount = getSharedPreferences("sm" + username, MODE_PRIVATE);
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

              /*  long row=jhpiegoDatabase.addStaffMaternity(row_1,row_2,row_3,row_4,row_5,row_6,row_7,row_8, String.valueOf(count),sessionid);
            if(row!=-1)
            {
                Toast.makeText(getApplicationContext(), getString(R.string.alert_saved_successfully), Toast.LENGTH_SHORT).show();
                sharedPreferencescount=getSharedPreferences("sm",MODE_PRIVATE);
                editorcount=sharedPreferencescount.edit();
                editorcount.putString("count", String.valueOf(count));
                editorcount.commit();
                Intent intent=new Intent(StaffMaternity.this,MainActivity.class);
                startActivity(intent);

            }else{
                Toast.makeText(getApplicationContext(), getString(R.string.alert_not_saved_to_db), Toast.LENGTH_SHORT).show();
            }*/
        }
    }

    private boolean isValidationCorrectOrNot(String row_1, String row_2, String message1, String message2, String alert_staff) {
        if (!row_1.equalsIgnoreCase("") || !row_2.equalsIgnoreCase("")) {
            if (row_1.equalsIgnoreCase("") && !row_2.equalsIgnoreCase("")) {
                Toast.makeText(this, message1, Toast.LENGTH_LONG).show();
                return false;
            }
            if (!row_1.equalsIgnoreCase("") && row_2.equalsIgnoreCase("")) {
//                Toast.makeText(this, message2, Toast.LENGTH_LONG).show();
                return true;
            }

            if (Integer.parseInt(row_1) >= Integer.parseInt(row_2)) {
                return true;
            } else {
                Toast.makeText(getApplicationContext(), alert_staff, Toast.LENGTH_LONG).show();
                return false;
            }
        } else {
            return true;
        }
    }

    public void setDefaultValues() {
        try {

            if (!jhpiegoDatabase.isLastVisitSubmittedStaffMaternity(sessionid)) {
                // Cursor cursor = jhpiegoDatabase.getLastVisitDataStaffMaternity();
                SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                Cursor cursor = sqLiteDatabase.query(TABLENAME7, new String[]{COL_USERNAME, COL_OBG, COL_SBA, COL_MOLR, COL_MOD, COL_SNLR, COL_SND, COL_LHVSLR, COL_LHVSD, COL_LHVWORKING, COL_LHVTRAINED,COL_NSEL,COL_NSR_MNHToolKit,
                        COL_VAR_Staff,COL_Labour_TableLR,COL_Labour_TableReq,COL_Variance_LbrTbl,COL_Caesarian_Act,COL_Person_InCharge,
                        COL_Mobile_InCharge,COL_AMDL}, COL_SESSION + "=?", new String[]{sessionid}, null, null, null);

                //  cursor.moveToFirst();
                if (cursor.getCount() > 0) {
                    cursor.moveToLast();
                    isCurrentRecordBackupExist = true;
                    sm_etdelLoad.setText(cursor.getString(cursor.getColumnIndex(COL_AMDL)));
                    editText1.setText(cursor.getString(cursor.getColumnIndex(COL_OBG)));
                    editText2.setText(cursor.getString(cursor.getColumnIndex(COL_SBA)));
                    editText3.setText(cursor.getString(cursor.getColumnIndex(COL_MOLR)));
                    editText4.setText(cursor.getString(cursor.getColumnIndex(COL_MOD)));
                    editText5.setText(cursor.getString(cursor.getColumnIndex(COL_SNLR)));
                    editText6.setText(cursor.getString(cursor.getColumnIndex(COL_SND)));
                    editText7.setText(cursor.getString(cursor.getColumnIndex(COL_LHVSLR)));
                    editText8.setText(cursor.getString(cursor.getColumnIndex(COL_LHVSD)));
                    editText9.setText(cursor.getString(cursor.getColumnIndex(COL_LHVWORKING)));
                    editText10.setText(cursor.getString(cursor.getColumnIndex(COL_LHVTRAINED)));
                    sm_etNofMember.setText(cursor.getString(cursor.getColumnIndex(COL_NSEL)));
                    sm_etNHMToolkit.setText(cursor.getString(cursor.getColumnIndex(COL_NSEL)));
                    sm_etNofMember.setText(cursor.getString(cursor.getColumnIndex(COL_NSEL)));
                    sm_etvarInStaff.setText(cursor.getString(cursor.getColumnIndex(COL_VAR_Staff)));
                    sm_etNumberOfLR.setText(cursor.getString(cursor.getColumnIndex(COL_Labour_TableLR)));
                    sm_etTableRequired.setText(cursor.getString(cursor.getColumnIndex(COL_Labour_TableReq)));
                    sm_etVarianceInTables.setText(cursor.getString(cursor.getColumnIndex(COL_Variance_LbrTbl)));
                    facility_performing_caserian=cursor.getString(cursor.getColumnIndex(COL_Caesarian_Act));
                    sm_et_nameOFNodal.setText(cursor.getString(cursor.getColumnIndex(COL_Person_InCharge)));
                    sm_et_mobileOfNodal.setText(cursor.getString(cursor.getColumnIndex(COL_Mobile_InCharge)));
                    if(facility_performing_caserian!=null && facility_performing_caserian.length()>0){
                        if(facility_performing_caserian.equalsIgnoreCase("Yes"))
                            rgCaesarian.check(R.id.rbYes_Caesarian);
                        else if(facility_performing_caserian.equalsIgnoreCase("No"))
                            rgCaesarian.check(R.id.rbNo_Caesarian);
                        else if(facility_performing_caserian.equalsIgnoreCase("NA"))
                            rgCaesarian.check(R.id.rbNA_Caesarian);
                    }


                }
            }

        } catch (Exception e) {
            isCurrentRecordBackupExist = false;
        }
    }

    private String getQuestionCode(String question) {

        for (F2 f2 : StaffMaternity.mQuetionsMaster.getF2()) {
            if (f2.getQuestionsName() != null) {
                if (f2.getQuestionsName().replaceAll("\\s+$", "").equalsIgnoreCase(question.replaceAll("\\s+$", ""))){
                    Log.v("District f2", "" + f2.getQCode());
                    return f2.getQCode().toString();
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
        mVisitDatum.setFormCode("f2");

        FormDatum mFormDatum = new FormDatum();
        mFormDatum.setAns(row_0);
        mFormDatum.setQCode("f2_q0");
        //mVisitDatumAns.add(mVisitDatum);
        formDatumList.add(mFormDatum);

        FormDatum mFormDatum1 = new FormDatum();
        mFormDatum1.setAns(row_1);
        mFormDatum1.setQCode("f2_q1");
        //mVisitDatumAns.add(mVisitDatum);
        formDatumList.add(mFormDatum1);

        FormDatum mFormDatumstate = new FormDatum();
        mFormDatumstate.setAns(row_2);
        mFormDatumstate.setQCode("f2_q2");
        formDatumList.add(mFormDatumstate);

        FormDatum mFormDatumdistrict = new FormDatum();
        mFormDatumdistrict.setAns(row_3);
        mFormDatumdistrict.setQCode("f2_q3");
        formDatumList.add(mFormDatumdistrict);

        FormDatum mFormDatumblock = new FormDatum();
        mFormDatumblock.setAns(row_4);
        mFormDatumblock.setQCode("f2_q4");
        formDatumList.add(mFormDatumblock);

        FormDatum mFormDatumfacilitytype = new FormDatum();
        mFormDatumfacilitytype.setAns(row_5);
        mFormDatumfacilitytype.setQCode("f2_q5");
        formDatumList.add(mFormDatumfacilitytype);

        FormDatum mFormDatumfacilityname = new FormDatum();
        mFormDatumfacilityname.setAns(row_6);
        mFormDatumfacilityname.setQCode("f2_q6");
        formDatumList.add(mFormDatumfacilityname);

        FormDatum mFormDatumfacilitylevel = new FormDatum();
        mFormDatumfacilitylevel.setAns(row_7);
        mFormDatumfacilitylevel.setQCode("f2_q7");
        formDatumList.add(mFormDatumfacilitylevel);

        FormDatum mFormDatumdov1 = new FormDatum();
        mFormDatumdov1.setAns(row_9);
        mFormDatumdov1.setQCode("f2_q8");
        formDatumList.add(mFormDatumdov1);

        FormDatum mFormDatumdov = new FormDatum();
        mFormDatumdov.setAns(row_8);
        mFormDatumdov.setQCode("f2_q9");
        formDatumList.add(mFormDatumdov);

        FormDatum mFormDatumdov2 = new FormDatum();
        mFormDatumdov2.setAns(row_10);
        mFormDatumdov2.setQCode("f2_q10");
        formDatumList.add(mFormDatumdov2);

        FormDatum mFormDatumdov11 = new FormDatum();
        mFormDatumdov11.setAns(row_11);
        mFormDatumdov11.setQCode("f2_q11");
        formDatumList.add(mFormDatumdov11);

        FormDatum mFormDatumdov12 = new FormDatum();
        mFormDatumdov12.setAns(row_12);
        mFormDatumdov12.setQCode("f2_q12");
        formDatumList.add(mFormDatumdov12);

        FormDatum mFormDatumdov13 = new FormDatum();
        mFormDatumdov13.setAns(row_13);
        mFormDatumdov13.setQCode("f2_q13");
        formDatumList.add(mFormDatumdov13);

        FormDatum mFormDatumdov14 = new FormDatum();
        mFormDatumdov14.setAns(row_14);
        mFormDatumdov14.setQCode("f2_q14");
        formDatumList.add(mFormDatumdov14);

        FormDatum mFormDatumdov15 = new FormDatum();
        mFormDatumdov15.setAns(row_15);
        mFormDatumdov15.setQCode("f2_q15");
        formDatumList.add(mFormDatumdov15);

        FormDatum mFormDatumdov16 = new FormDatum();
        mFormDatumdov16.setAns(row_16);
        mFormDatumdov16.setQCode("f2_q16");
        formDatumList.add(mFormDatumdov16);

        FormDatum mFormDatumdov17 = new FormDatum();
        mFormDatumdov17.setAns(row_17);
        mFormDatumdov17.setQCode("f2_q17");
        formDatumList.add(mFormDatumdov17);

        FormDatum mFormDatumdov18 = new FormDatum();
        mFormDatumdov18.setAns(row_18);
        mFormDatumdov18.setQCode("f2_q18");
        formDatumList.add(mFormDatumdov18);

        FormDatum mFormDatumdov19 = new FormDatum();
        mFormDatumdov19.setAns(row_19);
        mFormDatumdov19.setQCode("f2_q19");
        formDatumList.add(mFormDatumdov19);

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
            //  serverPost(jsonObject);
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
                Log.v("staffmaternity", "" + response);
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
                            address = MentorConstant.getAddress(StaffMaternity.this, MentorConstant.latitude, MentorConstant.longitude);
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
                    address = MentorConstant.getAddress(StaffMaternity.this, MentorConstant.latitude, MentorConstant.longitude);
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
                ActivityCompat.requestPermissions(StaffMaternity.this,
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
            address = MentorConstant.getAddress(StaffMaternity.this, MentorConstant.latitude, MentorConstant.longitude);
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
