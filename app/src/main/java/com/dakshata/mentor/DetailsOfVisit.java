package com.dakshata.mentor;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.dakshata.mentor.models.F1;
import com.dakshata.mentor.models.FormDatum;
import com.dakshata.mentor.models.QuetionsMaster;
import com.dakshata.mentor.models.VisitDatum;
import com.google.gson.Gson;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import static com.dakshata.mentor.JhpiegoDatabase.COL_IS_ASSESSMENT;
import static com.dakshata.mentor.JhpiegoDatabase.COL_IS_SUBMITTED;
import static com.dakshata.mentor.JhpiegoDatabase.COL_LEVEL;
import static com.dakshata.mentor.JhpiegoDatabase.COL_SESSION;
import static com.dakshata.mentor.JhpiegoDatabase.COL_STATE;
import static com.dakshata.mentor.JhpiegoDatabase.COL_USERNAME;
import static com.dakshata.mentor.JhpiegoDatabase.CREATED_DATE;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME1;
import static com.dakshata.mentor.JhpiegoDatabase.TABLE_WorkPlan;

/**
 * Created by Aditya.v on 16-12-2017.
 */
public class DetailsOfVisit extends AppCompatActivity implements LocationListener {
    TextView spinner_state;
    MaterialSpinner spinner_selectDistrict;
    MaterialSpinner spinner_selectBlock;
    MaterialSpinner spinner_facilityType;
    MaterialSpinner spinner_facility_name;
    EditText spinner_facilityName_other;
    //MaterialSpinner spinner_level;
    ImageView imageView1;
    TextView editText_nom;
    TextView editText_dov;
    JhpiegoDatabase jhpiegoDatabase;
    Button button_save, button_back;
    Uri selectedImageUri;
    ImageView img1, img2, img3, img4, img5;
    private static final int MY_INTENT_CLICK1 = 302;
    private static final int MY_INTENT_CLICK2 = 1;
    private static final int MY_INTENT_CLICK3 = 2;
    private static final int MY_INTENT_CLICK4 = 3;
    private static final int MY_INTENT_CLICK5 = 4;
    private static final int MY_INTENT_CLICK6 = 5;
    String path1, path2, path3, path4, path5, path6;
    ProgressDialog progressDialog;
    public static int indexSize = 0;
    Intent i;
    Calendar myCalendar;
    SharedPreferences sharedPreferences, sharedPreferences2, sharedPreferencescount;

    Bitmap bitmap1 = null, bitmap2 = null, bitmap3 = null, bitmap4 = null;
    Toolbar toolbar;
    SharedPreferences sh_Pref;
    SharedPreferences.Editor editor, editor2, editorcount;
    int count = 0;
    String username;
    ImageView back;
    TextView textViewHeaderName;
    private String lastId;
    public static QuetionsMaster mQuetionsMaster = new QuetionsMaster();
    String ansJson;
    String state, district, blocklist, selectedblock, nom;
    boolean submitted = true;
    List<String> listBlock;
    List<String> listFacilityName = new ArrayList<>();
    String sessionId;
    String imagejson;
    MultipartEntityBuilder multipartEntity;
    URL url;
    ProgressBar progressBar;
    long totalSize = 0;
    File finalFile1, finalFile2, finalFile3, finalFile4;
    private static final int REQUEST_CODE = 1;
    private List<String> listFacilityType, listDistrict, listWorkplanDates, listWorkplanIds, listWorkplanCreationDate;
    String _selectedDistrict = "", _selectedBlock = "", _selectedFacilityType = "";
    private TextView dov_tv_district;
    private String role;

    // Location Access
    LocationManager locationManager;
    String provider, address;
    Location location;
    String FType;
    private int loop = 0;
    String[] facilityTypeArray;
    List list3;
    JhpiegoDatabase mydb;

    private int visitsCount = 1, colId = 0, workplanPostion = 0;
    private TextView txt_visit_status;
    private MaterialSpinner dov_visit_status;
    private RadioButton rbPlanned, rbUnplanned;
    private String strState = "", strDistrict = "", strBlock = "", strFacilitytype = "", strFacilityname = "", actualStatus = "", tempSessionId = "", tempRecordId = "0",strStateCode="",strDistrictCode="",strBlockCode="",strFacilityCode="";
    private RadioGroup radioGroup_visit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_of_visit);
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
            editor = sh_Pref.edit();

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
       // spinner_level.setItems(list3);

        myCalendar = Calendar.getInstance();

        progressBar = new ProgressBar(this);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        progressDialog = new ProgressDialog(this);
        jhpiegoDatabase = new JhpiegoDatabase(this);
        try {
            sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
            editor = sh_Pref.edit();

            username = sh_Pref.getString("Username", "Unknown");
            state = sh_Pref.getString("state", "Unknown");
            district = sh_Pref.getString("district", "Unknown");
            selectedblock = sh_Pref.getString("selectedblock", "Unknown");
            blocklist = sh_Pref.getString("block", "Unknown");
            nom = sh_Pref.getString("name", "Unknown");
            role = sh_Pref.getString("role", "Unknown");
            Log.v("block", "" + blocklist);


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
//                        spinner_selectDistrict.setText(listDistrict.indexOf(cursor.getString(cursor.getColumnIndex(COL_DIST))));
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
                //spinner_selectDistrict.setText(dbdist);
                submitted = jhpiegoDatabase.LastVisitSubmitted(dbdist);
                Log.v("submitted", "" + submitted);
            }
            sqLiteDatabase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        imageView1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select File"), MY_INTENT_CLICK1);

            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // new UploadFileToServer().execute();
                SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                String query = "select * from " + TABLENAME1 + " where " + COL_USERNAME + "=" + username;
                Cursor cursor = sqLiteDatabase.query(TABLENAME1, new String[]{COL_USERNAME, COL_SESSION}, COL_ID + "=?", new String[]{String.valueOf(MentorConstant.recordId)}, null, null, null);
                if (cursor.getCount() > 0) {
                    getUserEnteredData();
                } else {
                    if (!submitted) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailsOfVisit.this);
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
                   /* progressDialog.setMessage("Please wait...");
                    progressDialog.show();*/
                        getUserEnteredData();
                    }
                }

            }
        });
        // progressDialog.dismiss();
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                if (!isStoragePermissionGranted()) {

                    //File write logic here
                   /* try {
                        ActivityCompat.requestPermissions((Activity) getApplicationContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);

                    }catch (Exception e){}
*/
                } else {
                    if (indexSize == 0) {
                        i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                        startActivityForResult(i, 1);
                        indexSize = indexSize + 1;
                    } else if (indexSize == 1) {
                        i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                        startActivityForResult(i, 2);

                        indexSize = indexSize + 1;
                    } else if (indexSize == 2) {
                        i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                        startActivityForResult(i, 3);

                        indexSize = indexSize + 1;
                    } else if (indexSize == 3) {
                        i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                        startActivityForResult(i, 4);

                        indexSize = indexSize + 1;
                    } else if (indexSize == 4) {
                        Toast.makeText(getApplicationContext(), "Reached maxmum pics", Toast.LENGTH_LONG).show();

                    } else if (indexSize > 3) {
                        Toast.makeText(getApplicationContext(), "Reached maxmum pics", Toast.LENGTH_LONG).show();
                    }
                }


            }
        });
        editText_dov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(DetailsOfVisit.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                datePickerDialog.show();
            }
        });

        rbPlanned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strState = spinner_state.getText().toString();

                strDistrict = "";
                if (role.equalsIgnoreCase("mentor")) {
                    strDistrict = dov_tv_district.getText().toString();
                }
                if (role.equalsIgnoreCase("supervisor") || role.equalsIgnoreCase("administrator")) {
                    strDistrict = spinner_selectDistrict.getText().toString();
                }
                strBlock = spinner_selectBlock.getText().toString();
                strFacilitytype = spinner_facilityType.getText().toString();
                strFacilityname = "";
                if (spinner_facilityType.getText().toString().equalsIgnoreCase("Others")) {
                    strFacilityname = spinner_facilityName_other.getText().toString();
                } else {
                    strFacilityname = spinner_facility_name.getText().toString();
                }

                if (strState.equalsIgnoreCase("") || strDistrict.equalsIgnoreCase("") || strBlock.equalsIgnoreCase("") ||
                        strFacilitytype.equalsIgnoreCase("") || strFacilityname.equalsIgnoreCase("")) {
                    refreshPlannedRadioButton();
                    Toast.makeText(getApplicationContext(), "Complete all above fields", Toast.LENGTH_LONG).show();
                } else if (strFacilityname.matches("")) {
                    refreshPlannedRadioButton();
                    Toast.makeText(getApplicationContext(), "Complete all above fields", Toast.LENGTH_SHORT).show();
                    return;
                } else if (spinner_facilityType.getText().toString().equalsIgnoreCase(listFacilityType.get(0))) {
                    refreshPlannedRadioButton();
                    Toast.makeText(getApplicationContext(), "Complete all above fields", Toast.LENGTH_SHORT).show();
                    return;
                } else if (spinner_facilityType.getText().toString().equalsIgnoreCase(listFacilityType.get(listFacilityType.size() - 1)) &&
                        spinner_facilityName_other.getText().toString().equalsIgnoreCase("")) {
                    refreshPlannedRadioButton();
                    Toast.makeText(getApplicationContext(), "Complete all above fields", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Cursor cursorDetailOfVisit = calculateWorkPlanFromDb(strBlock, strState, strDistrict, strFacilitytype, strFacilityname);
                    listWorkplanDates = new ArrayList<>();
                    listWorkplanIds = new ArrayList<>();
                    listWorkplanCreationDate = new ArrayList<>();
                    listWorkplanDates.add("Select");
                    listWorkplanIds.add("0");
                    listWorkplanCreationDate.add("0");
                    if (cursorDetailOfVisit.getCount() > 0) {
                        while (cursorDetailOfVisit.moveToNext()) {
                            listWorkplanDates.add(cursorDetailOfVisit.getString(cursorDetailOfVisit.getColumnIndex("workplan_date")));
                            listWorkplanIds.add(cursorDetailOfVisit.getString(cursorDetailOfVisit.getColumnIndex(COL_ID)));
                            listWorkplanCreationDate.add(cursorDetailOfVisit.getString(cursorDetailOfVisit.getColumnIndex(CREATED_DATE)));
                        }
                        dov_visit_status.setItems(listWorkplanDates);
                        radioGroup_visit.clearCheck();
                        rbPlanned.setChecked(true);
                        dov_visit_status.setVisibility(View.VISIBLE);
                        txt_visit_status.setVisibility(View.GONE);
                    } else {
                        refreshPlannedRadioButton();
                        dov_visit_status.setVisibility(View.GONE);
                        txt_visit_status.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "No workplan schedule found", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        rbUnplanned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tempSessionId.equalsIgnoreCase("") && !tempRecordId.equalsIgnoreCase("0")) {
                    SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                    jhpiegoDatabase.updateWorkPlanSessionId(tempRecordId, "Pending");
                    tempSessionId = "";
                    tempRecordId = "0";
                    sqLiteDatabase.close();
                }
                txt_visit_status.setText("Unplanned");
                dov_visit_status.setItems("Select");
                dov_visit_status.setVisibility(View.GONE);
                txt_visit_status.setVisibility(View.VISIBLE);
            }
        });

        dov_visit_status.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                workplanPostion = position;
            }
        });

        setSavedValuesFromDb();
        getAllAccessToGPSLocation();

    }

    private void refreshPlannedRadioButton() {
        rbPlanned.setChecked(false);
        dov_visit_status.setItems("Select");
        dov_visit_status.setVisibility(View.GONE);
        txt_visit_status.setVisibility(View.VISIBLE);
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
                        refreshPlannedRadioButton();
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
                    refreshPlannedRadioButton();
                    FType = "";
                    if (_selectedFacilityType.equalsIgnoreCase("RH")) {
                        FType = "RH";
                    }if (_selectedFacilityType.equalsIgnoreCase("TH RIMS")) {
                        FType = "TH RIMS";
                    }if (_selectedFacilityType.equalsIgnoreCase("TH KGH")) {
                        FType = "TH KGH";
                    }if (_selectedFacilityType.equalsIgnoreCase("TH GVH")) {
                        FType = "TH GVH";
                    }if (_selectedFacilityType.equalsIgnoreCase("TH  GGH")) {
                        FType = "TH  GGH";
                    }if (_selectedFacilityType.equalsIgnoreCase("TH  GMH")) {
                        FType = "TH  GMH";
                    }if (_selectedFacilityType.equalsIgnoreCase("DISPENSARY")) {
                        FType = "DISPENSARY";
                    }if (_selectedFacilityType.equalsIgnoreCase("GGH")) {
                        FType = "GGH";
                    }if (_selectedFacilityType.equalsIgnoreCase("Medical College Hospitals")) {
                        FType = "MCH";
                    }if (_selectedFacilityType.equalsIgnoreCase("Referral Hospital")) {
                        FType = "RH";
                    }if (_selectedFacilityType.equalsIgnoreCase("Area Hospital")) {
                        FType = "AH";
                    }if (_selectedFacilityType.equalsIgnoreCase(getString(R.string.district_hospital))) {
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
                    if (FType.equalsIgnoreCase("RH") ||FType.equalsIgnoreCase("TH RIMS") ||FType.equalsIgnoreCase("TH KGH") ||FType.equalsIgnoreCase("TH GVH") ||FType.equalsIgnoreCase("TH  GGH") ||FType.equalsIgnoreCase("TH  GMH") ||FType.equalsIgnoreCase("GGH") ||FType.equalsIgnoreCase("MCH") ||FType.equalsIgnoreCase("RH") ||FType.equalsIgnoreCase("AH") || FType.equalsIgnoreCase("DH") || FType.equalsIgnoreCase("SDH") || FType.equalsIgnoreCase("CHC") || FType.equalsIgnoreCase("PHC") || FType.equalsIgnoreCase("SC")) {
                        if (FType.equalsIgnoreCase("RH") ||FType.equalsIgnoreCase("TH RIMS") ||FType.equalsIgnoreCase("TH KGH") ||FType.equalsIgnoreCase("TH GVH") ||FType.equalsIgnoreCase("TH  GGH") ||FType.equalsIgnoreCase("GGH") ||FType.equalsIgnoreCase("MCH") ||FType.equalsIgnoreCase("RH") ||FType.equalsIgnoreCase("AH") ||FType.equalsIgnoreCase("SDH") || FType.equalsIgnoreCase("CHC") || FType.equalsIgnoreCase("PHC")) {
                            boolean isFacilityThere = mydb.checkFacilityAvailable(FType, spinner_selectBlock.getText().toString());
                            spinner_facility_name.setVisibility(View.VISIBLE);
                            spinner_facilityName_other.setVisibility(View.GONE);
                            if (!isFacilityThere) {
                                spinner_facilityType.setSelectedIndex(0);
                                Toast.makeText(DetailsOfVisit.this, "No Facility is available, Please select other facility type.", Toast.LENGTH_LONG).show();
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
                    else refreshPlannedRadioButton();
                }
            });
        }
    }

    private void setSavedValuesFromDb() {
        setFacilityType(mydb);
        if (MentorConstant.recordId > 0)
            if (!jhpiegoDatabase.isLastVisitSubmitted(String.valueOf(MentorConstant.recordId))) {
                Log.v("lastvisit", "" + jhpiegoDatabase.isLastVisitSubmitted(username));
                SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                Cursor cursor = sqLiteDatabase.query(TABLENAME1, new String[]{COL_ID, COL_USERNAME, COL_LEVEL, COL_SESSION, COL_BLOCK, COL_FACILITYNAME, COL_FACILITYTYPE, COL_DOV, COL_IMAGE1, COL_IMAGE2, COL_IMAGE3, COL_IMAGE4, COL_DIST, "reference_key", "workplan_status", "planned_date"}, COL_ID + "=?", new String[]{String.valueOf(MentorConstant.recordId)}, null, null, null);
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
                    //spinner_level.setText(cursor.getString(cursor.getColumnIndex(COL_LEVEL)));
                   // spinner_level.setArrowColor(getResources().getColor(R.color.black));
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
                    editText_dov.setText(cursor.getString(cursor.getColumnIndex(COL_DOV)));

                    byte[] blob1 = cursor.getBlob(cursor.getColumnIndex(COL_IMAGE1));
                    byte[] blob2 = cursor.getBlob(cursor.getColumnIndex(COL_IMAGE2));
                    byte[] blob3 = cursor.getBlob(cursor.getColumnIndex(COL_IMAGE3));
                    byte[] blob4 = cursor.getBlob(cursor.getColumnIndex(COL_IMAGE4));

                    setImageAndGetFinalUrl(blob1, bitmap1, img1, 1);
                    setImageAndGetFinalUrl(blob2, bitmap2, img2, 2);
                    setImageAndGetFinalUrl(blob3, bitmap3, img3, 3);
                    setImageAndGetFinalUrl(blob4, bitmap4, img4, 4);


                    lastId = cursor.getString(cursor.getColumnIndex("id"));

                    // Set work-plan status from db
                    if (cursor.getString(cursor.getColumnIndex("reference_key")).equalsIgnoreCase("0")) {
                        txt_visit_status.setText(cursor.getString(cursor.getColumnIndex("workplan_status")));
                        dov_visit_status.setItems("Select");
                        radioGroup_visit.clearCheck();
                        rbUnplanned.callOnClick();
                        rbUnplanned.setChecked(true);
                    } else {
                        radioGroup_visit.clearCheck();
                        if (!cursor.getString(cursor.getColumnIndex(COL_SESSION)).equalsIgnoreCase("")) {
                            tempSessionId = cursor.getString(cursor.getColumnIndex(COL_SESSION));
                            tempRecordId = cursor.getString(cursor.getColumnIndex("reference_key"));
                        }
                        rbPlanned.callOnClick();
                        if (listWorkplanDates != null && listWorkplanDates.size() > 0) {
                            for (int i = 0; i < listWorkplanDates.size(); i++) {
                                if (listWorkplanDates.get(i).equalsIgnoreCase(cursor.getString(cursor.getColumnIndex("planned_date")))) {
                                    workplanPostion = i;
                                    dov_visit_status.setSelectedIndex(i);
                                }
                            }
                        }
                        txt_visit_status.setText("");
                    }
                }
                sqLiteDatabase.close();
            }
    }

    private String getFType(String sBlock) {
        if (sBlock.equalsIgnoreCase("RH")) {
            sBlock = "RH";
        }if (sBlock.equalsIgnoreCase("TH RIMS")) {
            sBlock = "TH RIMS";
        }if (sBlock.equalsIgnoreCase("TH KGH")) {
            sBlock = "TH KGH";
        }if (sBlock.equalsIgnoreCase("TH GVH")) {
            sBlock = "TH GVH";
        }if (sBlock.equalsIgnoreCase("TH  GGH")) {
            sBlock = "TH  GGH";
        }if (sBlock.equalsIgnoreCase("TH  GMH")) {
            sBlock = "TH  GMH";
        }if (sBlock.equalsIgnoreCase("DISPENSARY")) {
            sBlock = "DISPENSARY";
        }if (sBlock.equalsIgnoreCase("GGH")) {
            sBlock = "GGH";
        }if (sBlock.equalsIgnoreCase("Medical College Hospital")) {
            sBlock = "MCH";
        }if (sBlock.equalsIgnoreCase("Referral Hospital")) {
            sBlock = "RH";
        }if (sBlock.equalsIgnoreCase(getString(R.string.ah))) {
            sBlock = "AH";
        }if (sBlock.equalsIgnoreCase(getString(R.string.district_hospital))) {
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
        if (MentorConstant.TH_GVH) {
            listFacilityType.add("RH");
            FType = "RH";
        }if (MentorConstant.TH_GVH) {
            listFacilityType.add("TH RIMS");
            FType = "TH RIMS";
        }if (MentorConstant.TH_GVH) {
            listFacilityType.add("TH KGH");
            FType = "TH KGH";
        }if (MentorConstant.TH_GVH) {
            listFacilityType.add("TH GVH");
            FType = "TH GVH";
        }if (MentorConstant.TH_GGH) {
            listFacilityType.add("TH  GGH");
            FType = "TH  GGH";
        }if (MentorConstant.TH_GMH) {
            listFacilityType.add("TH  GMH");
            FType = "TH  GMH";
        }if (MentorConstant.DISPENSARY) {
            listFacilityType.add("DISPENSARY");
            FType = "DISPENSARY";
        }if (MentorConstant.ggh) {
            listFacilityType.add("GGH");
            FType = "GGH";
        }if (MentorConstant.rh) {
            listFacilityType.add("Referral Hospital");
            FType = "RH";
        }if (MentorConstant.ah) {
            listFacilityType.add(getString(R.string.ah));
            FType = "AH";
        }if (MentorConstant.dh) {
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
        //listFacilityType.add(getString(R.string.others));
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
//        spinner_facilityType.setItems(listFacilityType);
        spinner_facilityType.setSelectedIndex(0);
    }

    private void createJson(String username, String sessionId, String state,String stateCode, String district,String districtCode, String block,String blockCode, String facilitytype, String facilityname,String facilityCode, String level, String dov) {
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
        mFormDatumstate.setLoc_code(strStateCode);
        formDatumList.add(mFormDatumstate);

        FormDatum mFormDatumdistrict = new FormDatum();
        mFormDatumdistrict.setAns(district);
        mFormDatumdistrict.setQCode("f1_q3");
        mFormDatumdistrict.setLoc_code(strDistrictCode);
        formDatumList.add(mFormDatumdistrict);

        FormDatum mFormDatumblock = new FormDatum();
        mFormDatumblock.setAns(block);
        mFormDatumblock.setQCode("f1_q4");
        mFormDatumblock.setLoc_code(strBlockCode);
        formDatumList.add(mFormDatumblock);

        FormDatum mFormDatumfacilitytype = new FormDatum();
        mFormDatumfacilitytype.setAns(facilitytype);
        mFormDatumfacilitytype.setQCode("f1_q5");
        formDatumList.add(mFormDatumfacilitytype);

        FormDatum mFormDatumfacilityname = new FormDatum();
        mFormDatumfacilityname.setAns(facilityname);
        mFormDatumfacilityname.setQCode("f1_q6");
        mFormDatumfacilityname.setLoc_code(strFacilityCode);
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

    private String getQuestionCode(String question) {

        for (F1 f1 : DetailsOfVisit.mQuetionsMaster.getF1()) {
            if (f1.getQuestionsName() != null && f1.getQuestionsName().contains(question)) {
                Log.v("District", "" + f1.getQCode());
                return f1.getQCode().toString();
            }
            //something here
        }
        return "";
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editText_dov.setText(sdf.format(myCalendar.getTime()));
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
        //spinner_level = (MaterialSpinner) findViewById(R.id.dov_spinner6);
        imageView1 = (ImageView) findViewById(R.id.dov_iv1);
        editText_nom = (TextView) findViewById(R.id.dov_et1);
        editText_dov = (TextView) findViewById(R.id.dov_et2);
        button_back = (Button) findViewById(R.id.dov_back);
        button_save = (Button) findViewById(R.id.dov_save);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
        img5 = (ImageView) findViewById(R.id.img5);

        txt_visit_status = (TextView) findViewById(R.id.txt_visit_status);
        dov_visit_status = (MaterialSpinner) findViewById(R.id.dov_visit_status);
        rbPlanned = (RadioButton) findViewById(R.id.rbPlanned);
        rbUnplanned = (RadioButton) findViewById(R.id.rbUnplanned);
        radioGroup_visit = (RadioGroup) findViewById(R.id.radioGroup_visit);

        spinner_facility_name.setBackgroundColor(Color.WHITE);
        spinner_selectBlock.setBackgroundColor(Color.WHITE);
        dov_visit_status.setBackgroundColor(Color.WHITE);
        spinner_facilityName_other.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                refreshPlannedRadioButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK) {

            bitmap1 = (Bitmap) data.getExtras().get("data");

            img1.setImageBitmap(bitmap1);
            Uri tempUri = getImageUri(getApplicationContext(), bitmap1);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            finalFile1 = new File(getRealPathFromURI(tempUri));

            Log.v("finalFile", "" + finalFile1);
            OutputStream os = null;
            try {
                os = new BufferedOutputStream(new FileOutputStream(finalFile1));
                bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, os);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            multipartEntity = MultipartEntityBuilder.create();
            multipartEntity.addTextBody("eid", "1");
            multipartEntity.addTextBody("uid", "14633");
            multipartEntity.addBinaryBody("image", finalFile1, ContentType.create("image/jpeg"), finalFile1.getName());
            // multipartEntity.addPart("someName", new StringBody(imagejson, ContentType.TEXT_PLAIN));
        }

        if (requestCode == 2 && resultCode == RESULT_OK) {

            bitmap2 = (Bitmap) data.getExtras().get("data");

            img2.setImageBitmap(bitmap2);
            Uri tempUri = getImageUri(getApplicationContext(), bitmap2);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            finalFile2 = new File(getRealPathFromURI(tempUri));

            Log.v("finalFile", "" + finalFile2);
        }

        if (requestCode == 3 && resultCode == RESULT_OK) {

            bitmap3 = (Bitmap) data.getExtras().get("data");

            img3.setImageBitmap(bitmap3);
            Uri tempUri = getImageUri(getApplicationContext(), bitmap3);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            finalFile3 = new File(getRealPathFromURI(tempUri));

            Log.v("finalFile", "" + finalFile3);
        }

        if (requestCode == 4 && resultCode == RESULT_OK) {

            bitmap4 = (Bitmap) data.getExtras().get("data");

            img4.setImageBitmap(bitmap4);
            Uri tempUri = getImageUri(getApplicationContext(), bitmap4);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            finalFile4 = new File(getRealPathFromURI(tempUri));

            Log.v("finalFile", "" + finalFile4);
        }

      /* if (requestCode == 5 && resultCode == RESULT_OK) {

          bitmap = (Bitmap) data.getExtras().get("data");

           img5.setImageBitmap(bitmap);
       }*/
    }

    public void Pback(View view) {
        super.onBackPressed();

    }

    public void getUserEnteredData() {
        String planned_date = "0", reference_key = "0";
        strState = spinner_state.getText().toString();
        strDistrict = "Ajmer";
        if (role.equalsIgnoreCase("mentor")) {
            strDistrict = dov_tv_district.getText().toString();
        }

        if (role.equalsIgnoreCase("supervisor") || role.equalsIgnoreCase("administrator")) {
            strDistrict = spinner_selectDistrict.getText().toString();
        }

        strBlock = spinner_selectBlock.getText().toString();
        //    String strFacilityname=spinner_facilityName_other.getSelectedItem().toString();
        strFacilitytype = spinner_facilityType.getText().toString();
        strFacilityname = "";
        if (spinner_facilityType.getText().toString().equalsIgnoreCase("Others")) {
            strFacilityname = spinner_facilityName_other.getText().toString();
        } else {
            strFacilityname = spinner_facility_name.getText().toString();
        }

        strDistrictCode=mydb.getDistrictCodeFromName(strDistrict);
        strFacilityCode=mydb.getFacilityCodeFromName(strFacilityname);
        strBlockCode=mydb.getBlockCodeFromName(strBlock);
        strStateCode=mydb.getStateCodeFromName(strState);


        String level = "";
       // String level = spinner_level.getText().toString();
        String nom = editText_nom.getText().toString();
        String dov = editText_dov.getText().toString();


        sessionId = strFacilityname + "-" + strFacilitytype + "-" + dov;
        createJson(username, sessionId, strState,strStateCode, strDistrict,strDistrictCode, strBlock,strBlockCode, strFacilitytype, strFacilityname,strFacilityCode, level, dov);
        count = 0;
        try {
            if (!strState.matches("")) {
                count++;
            }
            if (!strDistrict.matches("")) {
                count++;
            }
            if (!strBlock.matches("")) {
                count++;
            }
            if (!strFacilityname.matches("")) {
                count++;
            }
            if (!strFacilitytype.matches("")) {
                count++;
            }
            /*if (level.matches("")) {
                count++;
            }*/
            if (!nom.matches("")) {
                count++;
            }
            if (!dov.matches("")) {
                count++;
            }
        } catch (Exception e) {
        }
        if (strFacilityname.matches("") || nom.matches("") || dov.matches("")) {
            Toast.makeText(getApplicationContext(), "Complete all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if (spinner_facilityType.getText().toString().equalsIgnoreCase(listFacilityType.get(0))) {
            Toast.makeText(getApplicationContext(), "Complete all fields", Toast.LENGTH_SHORT).show();
            return;
        } /*else if (spinner_facilityType.getText().toString().equalsIgnoreCase(listFacilityType.get(listFacilityType.size() - 1)) &&
                spinner_facilityName_other.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "Complete all fields", Toast.LENGTH_SHORT).show();
            return;
        }*/ else if (!rbPlanned.isChecked() && !rbUnplanned.isChecked()) {
            Toast.makeText(getApplicationContext(), "Please fill Visit Status", Toast.LENGTH_SHORT).show();
            return;
        } else if (rbPlanned.isChecked() && dov_visit_status.getText().toString().equalsIgnoreCase("Select")) {
            Toast.makeText(getApplicationContext(), "Please fill workplan date", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (rbPlanned.isChecked()) {
                Date compareDov = new Date(dov);
//                Date compareCreationDate = new Date(getCurrentDate());
//                if (compareCreationDate.getTime() > compareDov.getTime()) {
//                    Toast.makeText(getApplicationContext(), "This workplan date is expired. Please select other date.", Toast.LENGTH_LONG).show();
//                    return;
//                }
                Date compareWorkplanCreationDate = new Date(listWorkplanDates.get(workplanPostion));
                if (compareDov.getTime() == compareWorkplanCreationDate.getTime() || compareDov.getTime() < compareWorkplanCreationDate.getTime()) {
                    actualStatus = "Timely Completed";
                } else if (compareDov.getTime() > compareWorkplanCreationDate.getTime()) {
                    actualStatus = "Delayed Completed";
                } else {
                    actualStatus = "Pending";
                }

                planned_date = listWorkplanDates.get(workplanPostion);
                reference_key = listWorkplanIds.get(workplanPostion);

            } else {
                actualStatus = "Unplanned";
            }
            sharedPreferences2 = getSharedPreferences("session", MODE_PRIVATE);
            editor2 = sharedPreferences2.edit();
            long row = -1;
            if (!jhpiegoDatabase.isLastVisitSubmitted(String.valueOf(MentorConstant.recordId))) {

                if (!jhpiegoDatabase.isSessionExists(sessionId, String.valueOf(MentorConstant.recordId)) && MentorConstant.recordFromDraft == 0) {
                    Log.v("finalFiledb", "" + finalFile1);
                    indexSize = 0;
                    editor2.putString("gps_record_id", lastId);
                    row = jhpiegoDatabase.updateDetailsOfVisit(lastId, nom, strState, strDistrict, strBlock, strFacilityname, strFacilitytype, level, dov, bitmap1, bitmap2, bitmap3, bitmap4, String.valueOf(count), sessionId, username, ansJson, String.valueOf(finalFile1), String.valueOf(finalFile2), String.valueOf(finalFile3), String.valueOf(finalFile4), String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude), planned_date, reference_key, actualStatus);
                    jhpiegoDatabase.updateAllTablesSessionId(sessionId, String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude));
                    if (rbPlanned.isChecked()) setDovInWorkPlanTable(lastId, level, dov, 0);
                    if (rbUnplanned.isChecked()) setDovInWorkPlanTable(lastId, level, dov, 1);
                } else if (!jhpiegoDatabase.isSessionExists(sessionId, lastId) && MentorConstant.recordFromDraft == 1) {
                    Log.v("finalFiledb", "" + finalFile1);
                    indexSize = 0;
                    editor2.putString("gps_record_id", lastId);
                    row = jhpiegoDatabase.updateDetailsOfVisit(lastId, nom, strState, strDistrict, strBlock, strFacilityname, strFacilitytype, level, dov, bitmap1, bitmap2, bitmap3, bitmap4, String.valueOf(count), sessionId, username, ansJson, String.valueOf(finalFile1), String.valueOf(finalFile2), String.valueOf(finalFile3), String.valueOf(finalFile4), String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude), planned_date, reference_key, actualStatus);
                    jhpiegoDatabase.updateAllTablesSessionId(sessionId, String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude));
                    if (rbPlanned.isChecked()) setDovInWorkPlanTable(lastId, level, dov, 0);
                    if (rbUnplanned.isChecked()) setDovInWorkPlanTable(lastId, level, dov, 1);
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.session_already_exists), Toast.LENGTH_SHORT).show();
                }
            } else {
                if (!jhpiegoDatabase.isSessionExists(sessionId, String.valueOf(MentorConstant.recordId))) {
                    Log.v("finalFiledb", "" + finalFile1);
                    indexSize = 0;
                    row = jhpiegoDatabase.addDetailsOfVisit(nom, strState, strDistrict, strBlock, strFacilityname, strFacilitytype, level, dov, bitmap1, bitmap2, bitmap3, bitmap4, String.valueOf(count), sessionId, username, ansJson, String.valueOf(finalFile1), String.valueOf(finalFile2), String.valueOf(finalFile3), String.valueOf(finalFile4), String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude), getCurrentDate(), planned_date, reference_key, actualStatus);
                    if (rbPlanned.isChecked())
                        setDovInWorkPlanTable(jhpiegoDatabase.getLastInsertedDataId(), level, dov, 0);
                    if (rbUnplanned.isChecked())
                        setDovInWorkPlanTable(jhpiegoDatabase.getLastInsertedDataId(), level, dov, 1);
                    editor2.putString("gps_record_id", String.valueOf(row));
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.session_already_exists), Toast.LENGTH_SHORT).show();
                }
            }

            editor2.putString("session", sessionId);
            editor2.commit();
            if (row != -1) {
                Toast.makeText(getApplicationContext(), getString(R.string.alert_saved_successfully), Toast.LENGTH_SHORT).show();
                if (MentorConstant.recordFromDraft == 0) {
                    sharedPreferencescount = getSharedPreferences("dov" + username, MODE_PRIVATE);
                    editorcount = sharedPreferencescount.edit();
                    editorcount.putString("count" + username, String.valueOf(count));
                    editorcount.putString("isRecordExist", "yes");
                    editorcount.commit();
                }
                //   DetailsOfVisit.IS_RESEAT_UI=true;
                onBackPressed();

            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.alert_not_saved_to_db), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setDovInWorkPlanTable(String lastInsertedDataId, String level, String dov, int selectionType) {
        SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
        if (selectionType == 0) {
            jhpiegoDatabase.updateWorkPlanDetail(listWorkplanIds.get(workplanPostion), nom, strState, strDistrict, strBlock, strFacilityname, strFacilitytype, level, dov, sessionId, lastInsertedDataId, actualStatus);
        }
        if (selectionType == 1) {
            jhpiegoDatabase.updateWorkPlanTableInUnplannedCase(lastInsertedDataId, sessionId, "Pending");
        }
        sqLiteDatabase.close();
    }

    private Cursor calculateWorkPlanFromDb(String block, String state, String district, String facilitytype, String facilityname) {
        visitsCount = colId = 0;
        SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
        Cursor cursorDetailOfVisit = null;
        if (MentorConstant.recordId != 0 && !tempSessionId.equalsIgnoreCase("")) {
            cursorDetailOfVisit = sqLiteDatabase.query(TABLE_WorkPlan, new String[]{COL_ID, COL_BLOCK, COL_STATE, COL_DIST, COL_FACILITYNAME, COL_FACILITYTYPE, COL_SESSION, COL_IS_SUBMITTED, "workplan_date", "visits", CREATED_DATE},
                    "(" + COL_BLOCK + "=? and " + COL_STATE + "=? and " + COL_DIST + "=? and " + COL_FACILITYTYPE + "=? and " + COL_FACILITYNAME + "=? and " + COL_SESSION + "=? and " + COL_IS_SUBMITTED + "=0)",
                    new String[]{block, state, district, facilitytype, facilityname, tempSessionId}, null, null, "workplan_date ASC");
        } else {
            cursorDetailOfVisit = sqLiteDatabase.query(TABLE_WorkPlan, new String[]{COL_ID, COL_BLOCK, COL_STATE, COL_DIST, COL_FACILITYNAME, COL_FACILITYTYPE, COL_SESSION, COL_IS_SUBMITTED, "workplan_date", "visits", CREATED_DATE},
                    "(" + COL_BLOCK + "=? and " + COL_STATE + "=? and " + COL_DIST + "=? and " + COL_FACILITYTYPE + "=? and " + COL_FACILITYNAME + "=? and " + COL_IS_SUBMITTED + "=0)",
                    new String[]{block, state, district, facilitytype, facilityname}, null, null, "workplan_date ASC");
        }

        return cursorDetailOfVisit;
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        String strDate = calendar.getTime().toString();
        try {
            SimpleDateFormat mdformat = new SimpleDateFormat("MM/dd/yyyy");
            strDate = "" + mdformat.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void setImageAndGetFinalUrl(byte[] blob, Bitmap bitmap, ImageView img, int post) {
        if (blob != null) {
            bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length);
            if (bitmap != null) {
                img.setImageBitmap(bitmap);
                try {
                    Uri tempUri = getImageUri(getApplicationContext(), bitmap);
                    switch (post) {
                        case 1:
                            finalFile1 = new File(getRealPathFromURI(tempUri));
                            break;
                        case 2:
                            finalFile2 = new File(getRealPathFromURI(tempUri));
                            break;
                        case 3:
                            finalFile3 = new File(getRealPathFromURI(tempUri));
                            break;
                        case 4:
                            finalFile4 = new File(getRealPathFromURI(tempUri));
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {

                return true;
            } else {


                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation

            return true;
        }
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
                            address = MentorConstant.getAddress(DetailsOfVisit.this, MentorConstant.latitude, MentorConstant.longitude);
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
                    address = MentorConstant.getAddress(DetailsOfVisit.this, MentorConstant.latitude, MentorConstant.longitude);
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
                ActivityCompat.requestPermissions(DetailsOfVisit.this,
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
            address = MentorConstant.getAddress(DetailsOfVisit.this, MentorConstant.latitude, MentorConstant.longitude);
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


    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            progressBar.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            progressBar.setVisibility(View.VISIBLE);

            // updating progress bar value
            progressBar.setProgress(progress[0]);

            // updating percentage value
            // txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://10.10.11.210:8000/services/membership_event_upload_images/");

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                File sourceFile = new File(String.valueOf(finalFile1));
                Log.v("finalFile1", "" + finalFile1);
                Log.v("sourceFile", "" + sourceFile);

                // Adding file data to http body
                entity.addPart("eid",
                        new StringBody("1"));
                entity.addPart("uid", new StringBody("14633"));
                entity.addPart("image", new FileBody(sourceFile));

                // Extra parameters if you want to pass to server


                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("result", "Response from server: " + result);

            // showing the server response in an alert dialog
            // showAlert(result);

            super.onPostExecute(result);
        }

    }

    public void serverPost(JSONObject jsonObject) {

        String url = "http://84538451.ngrok.io/services/bookmark_answers";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("detailsofvisit", "" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("error", "" + error);

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void httpImagePost() {
        // HttpPostHC4 httpPostHC4=new HttpPostHC4("http://10.10.11.210:8000/services/membership_event_upload_images/");
        // httpPostHC4.setEntity(multipartEntity.build());
        HttpPost httpPost = new HttpPost("http://10.10.11.210:8000/services/membership_event_upload_images/");
        httpPost.setEntity(multipartEntity.build());
        HttpClient httpClient = new DefaultHttpClient();

        try {
            url = new URL("http://10.10.11.210:8000/services/membership_event_upload_images/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();


            Log.v("response", "" + response);
            Log.d("status", "" + resEntity);

            //  Log.d("data", EntityUtils.toString(response.getEntity()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
