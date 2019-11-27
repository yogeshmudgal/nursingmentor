package com.dakshata.mentor;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dakshata.mentor.Utils.MentorConstant;
import com.dakshata.mentor.models.AnswersModel;
import com.dakshata.mentor.models.FormDatum;
import com.dakshata.mentor.models.QuetionsMaster;
import com.dakshata.mentor.models.VisitDatum;
import com.google.gson.Gson;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

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
import static com.dakshata.mentor.JhpiegoDatabase.COL_SESSION;
import static com.dakshata.mentor.JhpiegoDatabase.COL_STATE;
import static com.dakshata.mentor.JhpiegoDatabase.COL_USERNAME;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME1;
import static com.dakshata.mentor.JhpiegoDatabase.TABLE_WorkPlan;

/**
 * Created by Umesh Kumar on 4-2-2019.
 */
public class WorkplanActivity extends AppCompatActivity {
    TextView spinner_state;
    MaterialSpinner spinner_selectDistrict;
    MaterialSpinner spinner_selectBlock;
    MaterialSpinner spinner_facilityType;
    MaterialSpinner spinner_facility_name;
    EditText spinner_facilityName_other;
    MaterialSpinner spinner_level;
    TextView editText_nom;
    TextView editText_dov;
    JhpiegoDatabase jhpiegoDatabase;
    Button button_save, button_back;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    SharedPreferences sh_Pref;
    int count = 0;
    String username;
    ImageView back;
    TextView textViewHeaderName;
    public static QuetionsMaster mQuetionsMaster = new QuetionsMaster();
    String ansJson;
    String state, district, blocklist, selectedblock, nom;
    boolean submitted = true;
    List<String> listBlock;
    List<String> listFacilityName = new ArrayList<>();
    String sessionId;
    URL url;
    ProgressBar progressBar;
    private List<String> listFacilityType, listDistrict;
    String _selectedDistrict = "", _selectedBlock = "", _selectedFacilityType = "";
    private TextView dov_tv_district;
    private String role;
    String FType;
    String[] facilityTypeArray;
    List list3;
    JhpiegoDatabase mydb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_plan);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.menu_image_opt3));
        setSupportActionBar(toolbar);
        back = (ImageView) findViewById(R.id.back);
        textViewHeaderName = (TextView) findViewById(R.id.header_name);
        textViewHeaderName.setText(getString(R.string.section_a_header_1));
        listBlock = new ArrayList<>();
        listDistrict = new ArrayList<>();
        // actionBar.setDisplayHomeAsUpEnabled(true);
        // setTitle("  Details of Mentoring Visit");
        try {
            sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);

            username = sh_Pref.getString("Username", "Unknown");
            state = sh_Pref.getString("state", "Unknown");
            district = sh_Pref.getString("district", "Unknown");
            selectedblock = sh_Pref.getString("selectedblock", "Unknown");
            blocklist = sh_Pref.getString("block", "Unknown");
            nom = sh_Pref.getString("name", "Unknown");
            Log.v("block", "" + blocklist);
            role = sh_Pref.getString("role", "Unknown");
//            listBlock = Arrays.asList(blocklist.split(","));
        } catch (Exception e) {
            e.printStackTrace();
        }
        initViews();

        facilityTypeArray = getResources().getStringArray(R.array.facility_level);
        list3 = Arrays.asList(facilityTypeArray);
        spinner_level.setItems(list3);


        progressBar = new ProgressBar(this);
        progressDialog = new ProgressDialog(this);
        jhpiegoDatabase = new JhpiegoDatabase(this);
        try {
            sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);

            username = sh_Pref.getString("Username", "Unknown");
            state = sh_Pref.getString("state", "Unknown");
            district = sh_Pref.getString("district", "Unknown");
            selectedblock = sh_Pref.getString("selectedblock", "Unknown");
            blocklist = sh_Pref.getString("block", "Unknown");
            nom = sh_Pref.getString("name", "Unknown");
            role = sh_Pref.getString("role", "Unknown");
            editText_nom.setText(nom);
            spinner_state.setText(state);

            mydb = new JhpiegoDatabase(this);
            listDistrict = new ArrayList<>();
            listBlock = new ArrayList<>();
            listDistrict = mydb.getDistrictList(listDistrict);

            try {
                SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                Cursor cursor = sqLiteDatabase.query(TABLENAME1, new String[]{COL_ID, COL_DIST, COL_FACILITYNAME}, COL_ID + "=?", new String[]{String.valueOf(MentorConstant.recordId)}, null, null, null);
                if (cursor.getCount() > 0) {
                    cursor.moveToLast();
                    if (role.equalsIgnoreCase("mentor")) {
                        dov_tv_district.setText(district);
                    } else if (role.equalsIgnoreCase("supervisor") || role.equalsIgnoreCase("administrator")) {
                        spinner_selectDistrict.setText(cursor.getString(cursor.getColumnIndex(COL_DIST)));
                        _selectedDistrict = cursor.getString(cursor.getColumnIndex(COL_DIST));
                    }
                    spinner_facilityName_other.setText(cursor.getString(cursor.getColumnIndex(COL_FACILITYNAME)));
                }
            } catch (Exception e) {

            }

            if (role.equalsIgnoreCase("mentor")) {
                if (district.equalsIgnoreCase("") || district.equalsIgnoreCase("Unknown")) {
                    district = mydb.getDistrictNameForMentor();
                }
                _selectedDistrict = district;
                dov_tv_district.setText(_selectedDistrict);
            } else if (role.equalsIgnoreCase("supervisor") || role.equalsIgnoreCase("administrator")) {
                spinner_selectDistrict.setItems(listDistrict);
                if (listDistrict.size() != 0) _selectedDistrict = listDistrict.get(0);
                spinner_selectDistrict.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                        _selectedDistrict = listDistrict.get(position);
                        setBlockWiseData(mydb, 1);
                        resetSubFacilityView();
                    }
                });
            }
            try {
                setBlockWiseData(mydb, 1);
            } catch (Exception e) {
            }

            SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query(TABLENAME1, new String[]{COL_USERNAME, COL_SESSION}, COL_ID + "=?", new String[]{String.valueOf(MentorConstant.recordId)}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToLast();
                String dbdist = cursor.getString(cursor.getColumnIndex(COL_SESSION));
                submitted = jhpiegoDatabase.LastVisitSubmitted(dbdist);
            }
            sqLiteDatabase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                Cursor cursor = sqLiteDatabase.query(TABLENAME1, new String[]{COL_USERNAME, COL_SESSION}, COL_ID + "=?", new String[]{String.valueOf(MentorConstant.recordId)}, null, null, null);
                if (cursor.getCount() > 0) {
                    getUserEnteredData();
                } else {
                    if (!submitted) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(WorkplanActivity.this);
                        builder.setTitle("Dakshata Mentor");
                        builder.setMessage("Please submit the session before creating another");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                    } else {
                        getUserEnteredData();
                    }
                }

            }
        });
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editText_dov.setText(getIntent().getStringExtra("selected_date"));

//        setSavedValuesFromDb();

    }

    private void setBlockWiseData(final JhpiegoDatabase mydb, int position) {
        if (position == 1) {
            listBlock.clear();
            listBlock = mydb.getBlockList(_selectedDistrict);
            spinner_selectBlock.setItems(listBlock);
            spinner_selectBlock.setSelectedIndex(0);
        }
        if (position == 1) {
            spinner_selectBlock.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                    if (_selectedDistrict.equalsIgnoreCase("")) {
                        Toast.makeText(getApplicationContext(), getString(R.string.alert_please_select_district), Toast.LENGTH_LONG).show();
                    } else {
                        setBlockWiseData(mydb, 2);
                        _selectedBlock = listBlock.get(position);
                        setFacilityType(mydb);
                        resetSubFacilityView();
                    }
                }
            });
        }
        if (position == 1 || position == 2) {
            setFacilityType(mydb);
            listFacilityName = mydb.getFacilityName(FType, spinner_selectBlock.getText().toString(), _selectedDistrict);
            spinner_facility_name.setItems(listFacilityName);
            spinner_facilityType.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                    _selectedFacilityType = listFacilityType.get(position);
                    FType = "";
                    if (_selectedFacilityType.equalsIgnoreCase(getString(R.string.district_hospital))) {
                        FType = "DH";
                    } else if (_selectedFacilityType.equalsIgnoreCase(getString(R.string.sub_district_hospital))) {
                        FType = "SDH";
                    } else if (_selectedFacilityType.equalsIgnoreCase(getString(R.string.community_health_center))) {
                        FType = "CHC";
                    } else if (_selectedFacilityType.equalsIgnoreCase(getString(R.string.primary_health_center))) {
                        FType = "PHC";
                    } else if (_selectedFacilityType.equalsIgnoreCase(getString(R.string.sub_center))) {
                        FType = "SC";
                    } else if (_selectedFacilityType.equalsIgnoreCase(getString(R.string.others))) {
                        FType = "Others";
                    }

                    spinner_facilityName_other.setText("");
                    if (listFacilityName.size() > 0)
                        spinner_facility_name.setItems(0);
                    if (FType.equalsIgnoreCase("DH") || FType.equalsIgnoreCase("SDH") || FType.equalsIgnoreCase("CHC") || FType.equalsIgnoreCase("PHC") || FType.equalsIgnoreCase("SC")) {
                        if (FType.equalsIgnoreCase("SDH") || FType.equalsIgnoreCase("CHC") || FType.equalsIgnoreCase("PHC")) {
                            boolean isFacilityThere = mydb.checkFacilityAvailable(FType, spinner_selectBlock.getText().toString());
                            spinner_facility_name.setVisibility(View.VISIBLE);
                            spinner_facilityName_other.setVisibility(View.GONE);
                            if (!isFacilityThere) {
                                spinner_facilityType.setSelectedIndex(0);
                                Toast.makeText(WorkplanActivity.this, "No Facility is available, Please select other facility type.", Toast.LENGTH_LONG).show();
                                spinner_facility_name.setVisibility(View.INVISIBLE);
                            } else {
                                listFacilityName = mydb.getFacilityName(FType, spinner_selectBlock.getText().toString(), _selectedDistrict);
                                spinner_facility_name.setItems(listFacilityName);
                            }
                        } else if (FType.equalsIgnoreCase("DH")) {
                            listFacilityName = mydb.getFacilityNameForDistrictHospital(FType);
                            spinner_facility_name.setItems(listFacilityName);
                        } else if (FType.equalsIgnoreCase("SC")) {
                            listFacilityName = mydb.getFacilityNameForSubCenter(spinner_selectBlock.getText().toString());
                            spinner_facility_name.setItems(listFacilityName);
                        }
                    } else if (FType.equalsIgnoreCase("Others")) {
                        spinner_facility_name.setVisibility(View.GONE);
                        spinner_facilityName_other.setVisibility(View.VISIBLE);
                    } else if (position == 0) {
                        resetSubFacilityView();
                    }
                    Log.e("Facility", "Yes");
//                    }
                }
            });

            spinner_facility_name.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                    if (listFacilityName.get(position).equalsIgnoreCase(""))
                        Toast.makeText(getApplicationContext(), "Please select type of facility", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private String getFType(String sBlock) {
        if (sBlock.equalsIgnoreCase(getString(R.string.district_hospital))) {
            sBlock = "DH";
        } else if (sBlock.equalsIgnoreCase(getString(R.string.sub_district_hospital))) {
            sBlock = "SDH";
        } else if (sBlock.equalsIgnoreCase(getString(R.string.community_health_center))) {
            sBlock = "CHC";
        } else if (sBlock.equalsIgnoreCase(getString(R.string.primary_health_center))) {
            sBlock = "PHC";
        } else if (sBlock.equalsIgnoreCase(getString(R.string.sub_center))) {
            sBlock = "SC";
        }
        return sBlock;
    }

    private void setFacilityType(JhpiegoDatabase mydb) {
        listFacilityType = new ArrayList<>();
        _selectedFacilityType = "";
        String strDistrict = "";
        if (role.equalsIgnoreCase("mentor")) {
            strDistrict = dov_tv_district.getText().toString();
        } else {
            strDistrict = spinner_selectDistrict.getText().toString();
        }
        mydb.getFacilityTypeList(strDistrict, spinner_selectBlock.getText().toString());
        listFacilityType.add("Select");
        if (MentorConstant.dh) {
            listFacilityType.add(getString(R.string.district_hospital));
            FType = "DH";
        }
        if (MentorConstant.sdh) {
            listFacilityType.add(getString(R.string.sub_district_hospital));
            FType = "SDH";
        }
        if (MentorConstant.chc) {
            listFacilityType.add(getString(R.string.community_health_center));
            FType = "CHC";
        }
        if (MentorConstant.phc) {
            listFacilityType.add(getString(R.string.primary_health_center));
            FType = "PHC";
        }
        if (MentorConstant.sc) {
            listFacilityType.add(getString(R.string.sub_center));
            FType = "SC";
        }
        listFacilityType.add(getString(R.string.others));
        spinner_facilityType.setItems(listFacilityType);
        if (_selectedFacilityType.equalsIgnoreCase(getString(R.string.others))) {
            FType = "Others";
        }
    }

    private void resetSubFacilityView() {
        spinner_facilityName_other.setVisibility(View.GONE);
        spinner_facility_name.setVisibility(View.VISIBLE);
        List<String> listFacilityName1 = new ArrayList<>();
        listFacilityName1.add("");
        spinner_facilityName_other.setText("");
        spinner_facility_name.setItems("");
        spinner_facilityType.setSelectedIndex(0);
    }

    private void createJson(String username, String sessionId, String state, String district, String block, String facilitytype, String facilityname, String level, String dov) {
        AnswersModel mAnswersModel = new AnswersModel();

        mAnswersModel.setUser(username);
        mAnswersModel.setVisitId(sessionId);

        List<VisitDatum> mVisitDatumAns = new ArrayList<VisitDatum>();
        List<FormDatum> formDatumList = new ArrayList<>();
        VisitDatum mVisitDatum = new VisitDatum();
        mVisitDatum.setFormCode("f1");

        FormDatum mFormDatum = new FormDatum();
        mFormDatum.setAns(username);
        mFormDatum.setQCode("f1_q1");
        //mVisitDatumAns.add(mVisitDatum);
        formDatumList.add(mFormDatum);

        FormDatum mFormDatumstate = new FormDatum();
        mFormDatumstate.setAns(state);
        mFormDatumstate.setQCode("f1_q2");
        formDatumList.add(mFormDatumstate);

        FormDatum mFormDatumdistrict = new FormDatum();
        mFormDatumdistrict.setAns(district);
        mFormDatumdistrict.setQCode("f1_q3");
        formDatumList.add(mFormDatumdistrict);

        FormDatum mFormDatumblock = new FormDatum();
        mFormDatumblock.setAns(block);
        mFormDatumblock.setQCode("f1_q4");
        formDatumList.add(mFormDatumblock);

        FormDatum mFormDatumfacilitytype = new FormDatum();
        mFormDatumfacilitytype.setAns(facilitytype);
        mFormDatumfacilitytype.setQCode("f1_q5");
        formDatumList.add(mFormDatumfacilitytype);

        FormDatum mFormDatumfacilityname = new FormDatum();
        mFormDatumfacilityname.setAns(facilityname);
        mFormDatumfacilityname.setQCode("f1_q6");
        formDatumList.add(mFormDatumfacilityname);

        FormDatum mFormDatumfacilitylevel = new FormDatum();
        mFormDatumfacilitylevel.setAns(level);
        mFormDatumfacilitylevel.setQCode("f1_q7");
        formDatumList.add(mFormDatumfacilitylevel);

        FormDatum mFormDatumdov = new FormDatum();
        mFormDatumdov.setAns(dov);
        mFormDatumdov.setQCode("f1_q8");
        formDatumList.add(mFormDatumdov);

        mVisitDatum.setFormData(formDatumList);
        Gson gsonmVisitDatum = new Gson();
        ansJson = gsonmVisitDatum.toJson(mVisitDatum);
        Log.v("ans ", "ans json v: " + ansJson);

        mVisitDatumAns.add(mVisitDatum);
        mAnswersModel.setVisitData(mVisitDatumAns);
        Gson gson = new Gson();
        String fullJson = gson.toJson(mAnswersModel);
        Log.v("ans ", "ans json : " + fullJson);
        try {

            JSONObject jsonObject = new JSONObject(fullJson);
            //serverPost(jsonObject);
            Log.v("ans ", "ans json : " + jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.v("ans ", "ans json :execp " + e.toString());
        }


    }

    public void initViews() {

        spinner_state = (TextView) findViewById(R.id.dov_spinner1);
        dov_tv_district = (TextView) findViewById(R.id.dov_tv_district);
        spinner_selectDistrict = (MaterialSpinner) findViewById(R.id.dov_spinner2);
        if (role.equalsIgnoreCase("mentor")) {
            dov_tv_district.setVisibility(View.VISIBLE);
            spinner_selectDistrict.setVisibility(View.GONE);
        } else if (role.equalsIgnoreCase("supervisor") || role.equalsIgnoreCase("administrator")) {
            dov_tv_district.setVisibility(View.GONE);
            spinner_selectDistrict.setVisibility(View.VISIBLE);
        }
        spinner_selectBlock = (MaterialSpinner) findViewById(R.id.dov_spinner3);
        spinner_facilityName_other = (EditText) findViewById(R.id.dov_spinner4);
        spinner_facility_name = (MaterialSpinner) findViewById(R.id.spinner_facility_name);
        spinner_facilityType = (MaterialSpinner) findViewById(R.id.dov_spinner5);
        spinner_level = (MaterialSpinner) findViewById(R.id.dov_spinner6);
        editText_nom = (TextView) findViewById(R.id.dov_et1);
        editText_dov = (TextView) findViewById(R.id.dov_et2);
        button_back = (Button) findViewById(R.id.dov_back);
        button_save = (Button) findViewById(R.id.dov_save);
    }

    public void Pback(View view) {
        super.onBackPressed();

    }

    public void getUserEnteredData() {
        String state = spinner_state.getText().toString();
        String district = "Ajmer";
        if (role.equalsIgnoreCase("mentor")) {
            district = dov_tv_district.getText().toString();
        }

        if (role.equalsIgnoreCase("supervisor") || role.equalsIgnoreCase("administrator")) {
            district = spinner_selectDistrict.getText().toString();
        }

        String block = spinner_selectBlock.getText().toString();
        //    String facilityname=spinner_facilityName_other.getSelectedItem().toString();
        String facilitytype = spinner_facilityType.getText().toString();
        String facilityname = "";
        if (spinner_facilityType.getText().toString().equalsIgnoreCase("Others")) {
            facilityname = spinner_facilityName_other.getText().toString();
        } else {
            facilityname = spinner_facility_name.getText().toString();
        }

        String level = spinner_level.getText().toString();
        String nom = editText_nom.getText().toString();
        String dov = editText_dov.getText().toString();

        String[] temp = dov.split("/");
        if (temp.length > 1) {
            String tempMonth = temp[0];
            if (temp[0].length() == 1) {
                tempMonth = "0" + temp[0];
            }
            String tempDate = temp[1];
            if (temp[1].length() == 1) {
                tempDate = "0" + temp[1];
            }
            dov = tempMonth + "/" + tempDate + "/" + temp[2];
        }

        sessionId = facilityname + "-" + facilitytype + "-" + dov;
        createJson(username, sessionId, state, district, block, facilitytype, facilityname, level, dov);
        count = 0;
        try {
            if (!state.matches("")) {
                count++;
            }
            if (!district.matches("")) {
                count++;
            }
            if (!block.matches("")) {
                count++;
            }
            if (!facilityname.matches("")) {
                count++;
            }
            if (!facilitytype.matches("")) {
                count++;
            }
            if (!level.matches("")) {
                count++;
            }
            if (!nom.matches("")) {
                count++;
            }
            if (!dov.matches("")) {
                count++;
            }
        } catch (Exception e) {
        }
        if (facilityname.matches("") || nom.matches("") || dov.matches("")) {
            Toast.makeText(getApplicationContext(), "Complete all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if (spinner_facilityType.getText().toString().equalsIgnoreCase(listFacilityType.get(0))) {
            Toast.makeText(getApplicationContext(), "Complete all fields", Toast.LENGTH_SHORT).show();
            return;
        } else if (spinner_facilityType.getText().toString().equalsIgnoreCase(listFacilityType.get(listFacilityType.size() - 1)) &&
                spinner_facilityName_other.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "Complete all fields", Toast.LENGTH_SHORT).show();
            return;
        } else {
            long row = -1;
            if (!jhpiegoDatabase.isSessionExists(sessionId, String.valueOf(MentorConstant.recordId))) {
                SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                Cursor cursorDetailOfVisit = sqLiteDatabase.query(TABLE_WorkPlan, new String[]{COL_ID, COL_BLOCK, COL_STATE, COL_DIST, COL_FACILITYNAME, COL_FACILITYTYPE, COL_SESSION, "workplan_date"},
                        "(" + COL_BLOCK + "=? and " + COL_STATE + "=? and " + COL_DIST + "=? and " + COL_FACILITYTYPE + "=? and " + COL_FACILITYNAME + "=?)",
                        new String[]{block, state, district, facilitytype, facilityname}, null, null, null);
                int visitsCount = 1;
                if (cursorDetailOfVisit.getCount() > 0) {
                    try {
                        visitsCount = Integer.parseInt(cursorDetailOfVisit.getString(cursorDetailOfVisit.getColumnIndex("visits"))) + 1;
                    } catch (Exception e) { e.printStackTrace(); }
                }
                row = jhpiegoDatabase.addWorkPlanDetail(nom, state, district, block, facilityname, facilitytype, level, "0", username, dov, String.valueOf(visitsCount), getCurrentDate());
                MentorConstant.isWorkPlanSaved = 1;
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.session_already_exists), Toast.LENGTH_SHORT).show();
            }

            if (row != -1) {
                Toast.makeText(getApplicationContext(), getString(R.string.alert_saved_successfully), Toast.LENGTH_SHORT).show();
                onBackPressed();
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.alert_not_saved_to_db), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        String strDate = calendar.getTime().toString();
        try {
            SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
            strDate = "" + mdformat.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    private void setSavedValuesFromDb() {
        setFacilityType(mydb);
        if (MentorConstant.recordId > 0)
            if (!jhpiegoDatabase.isLastVisitSubmitted(String.valueOf(MentorConstant.recordId))) {
                Log.v("lastvisit", "" + jhpiegoDatabase.isLastVisitSubmitted(username));
                SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                Cursor cursor = sqLiteDatabase.query(TABLENAME1, new String[]{COL_ID, COL_USERNAME, COL_LEVEL, COL_SESSION, COL_BLOCK, COL_FACILITYNAME, COL_FACILITYTYPE, COL_DOV, COL_IMAGE1, COL_IMAGE2, COL_IMAGE3, COL_IMAGE4, COL_DIST}, COL_ID + "=?", new String[]{String.valueOf(MentorConstant.recordId)}, null, null, null);
                // Cursor cursor = jhpiegoDatabase.getLastVisitData(username);

                if (cursor.getCount() > 0) {
                    Log.v("getCount", "" + cursor.getCount());
                    cursor.moveToLast();
                    if (role.equalsIgnoreCase("mentor")) {
                        dov_tv_district.setVisibility(View.VISIBLE);
                        spinner_selectDistrict.setVisibility(View.GONE);
                        _selectedDistrict = district;
                        dov_tv_district.setText(district);
                        listBlock = mydb.getBlockList(district);
                    } else if (role.equalsIgnoreCase("supervisor") || role.equalsIgnoreCase("administrator")) {
                        dov_tv_district.setVisibility(View.GONE);
                        spinner_selectDistrict.setVisibility(View.VISIBLE);
//                    spinner_selectDistrict.setSelection(listDistrict.indexOf(cursor.getString(cursor.getColumnIndex(COL_DIST))));
                        spinner_selectDistrict.setItems(listDistrict);
                        spinner_selectDistrict.setSelectedIndex(listDistrict.indexOf(cursor.getString(cursor.getColumnIndex(COL_DIST))));
                        _selectedDistrict = spinner_selectDistrict.getText().toString();
                        listBlock = mydb.getBlockList(cursor.getString(cursor.getColumnIndex(COL_DIST)));
                    }
                    spinner_selectBlock.setItems(listBlock);
                    spinner_selectBlock.setSelectedIndex(listBlock.indexOf(cursor.getString(cursor.getColumnIndex(COL_BLOCK))));
                    Log.v("spinner_selectBlock", "" + cursor.getString(cursor.getColumnIndex(COL_BLOCK)));
                    spinner_facilityType.setItems(listFacilityType);
                    spinner_facilityType.setSelectedIndex(listFacilityType.indexOf(cursor.getString(cursor.getColumnIndex(COL_FACILITYTYPE))));
                    Log.v("spinner_facilityType", "" + cursor.getString(cursor.getColumnIndex(COL_FACILITYTYPE)));
//                spinner_level.setSelection(list3.indexOf(cursor.getString(cursor.getColumnIndex(COL_LEVEL))));
                    spinner_level.setText(cursor.getString(cursor.getColumnIndex(COL_LEVEL)));
                    spinner_level.setArrowColor(getResources().getColor(R.color.black));
//                spinner_level.setBackgroundColor(getResources().getColor(R.color.spinner_back_2));
//                Log.v("spinner_level", "" + cursor.getString(cursor.getColumnIndex(COL_LEVEL)));
                    if (cursor.getString(cursor.getColumnIndex(COL_FACILITYTYPE)).equalsIgnoreCase("other") || cursor.getString(cursor.getColumnIndex(COL_FACILITYTYPE)).equalsIgnoreCase("Others")) {
                        spinner_facilityName_other.setVisibility(View.VISIBLE);
                        spinner_facility_name.setVisibility(View.GONE);
                        spinner_facilityName_other.setText(cursor.getString(cursor.getColumnIndex(COL_FACILITYNAME)));
                    } else {
                        listFacilityName = mydb.getFacilityName(getFType(spinner_facilityType.getText().toString()), spinner_selectBlock.getText().toString(), _selectedDistrict);
                        spinner_facility_name.setItems(listFacilityName);

                        spinner_facility_name.setSelectedIndex(listFacilityName.indexOf(cursor.getString(cursor.getColumnIndex(COL_FACILITYNAME))));
                        spinner_facilityName_other.setVisibility(View.GONE);
                        spinner_facility_name.setVisibility(View.VISIBLE);
                    }

                    Log.v("spinner_facilityName", "" + cursor.getString(cursor.getColumnIndex(COL_FACILITYNAME)));
                    editText_dov.setText(cursor.getString(cursor.getColumnIndex(COL_DOV)));
                    Log.v("editText_dov", "" + cursor.getString(cursor.getColumnIndex(COL_DOV)));

                }
                sqLiteDatabase.close();
            }
    }

}
