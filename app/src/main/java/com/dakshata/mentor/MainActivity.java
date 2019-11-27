package com.dakshata.mentor;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dakshata.mentor.Utils.MentorConstant;
import com.dakshata.mentor.Utils.Utils;
import com.dakshata.mentor.models.QuetionsMaster;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import static com.dakshata.mentor.JhpiegoDatabase.COL_1;
import static com.dakshata.mentor.JhpiegoDatabase.COL_ID;
import static com.dakshata.mentor.JhpiegoDatabase.COL_IS_ASSESSMENT;
import static com.dakshata.mentor.JhpiegoDatabase.COL_MOBILE;
import static com.dakshata.mentor.JhpiegoDatabase.COL_SESSION;
import static com.dakshata.mentor.JhpiegoDatabase.COL_USERNAME;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME1;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME10;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME11;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME12;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME17;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME18;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME5;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME6;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME7;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME8;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME9;
import static com.dakshata.mentor.JhpiegoDatabase.TABLE_ClinicalStandards;

public class MainActivity extends AppCompatActivity implements LocationListener {

    RecyclerView recyclerView;
    List<FormDetails> formDetails;
    MainForm_Recycler_Adapter mainForm_recycler_adapter;
    Button button_history, button_submit;
    JhpiegoDatabase jhpiegoDatabase;
    Cursor cursor;
    SQLiteDatabase sqLiteDatabase;
    Toolbar toolbar;
    ImageView back;
    TextView header_name;
    SharedPreferences sh_Pref, sharedPreferences, sharedPreferencescount;
    SharedPreferences.Editor editor, editor1, editorcount;
    String mycount = String.valueOf(0);
    String username;
    ProgressDialog progressDialog;
    String fullJson;
    String session;
    String sessionid;
    Bitmap bitmap1, bitmap2, bitmap3, bitmap4;
    File finalFile1, finalFile2, finalFile3, finalFile4;
    String finalFile5, finalFile6, finalFile7, finalFile8;
    MultipartEntityBuilder multipartEntity;
    ProgressBar progressBar;
    long totalSize = 0;
    File sourceFile1, sourceFile2, sourceFile3, sourceFile4;
    String imageResponse;

    String sessionID;

    // Location Access
    LocationManager locationManager;
    String provider, address;
    Location location;
    private int loop = 0;
    private boolean isButtonEnabled;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.menu_image_opt3));
        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);
        back = (ImageView) findViewById(R.id.back);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please wait...");
        progressBar = new ProgressBar(this);
        back.setVisibility(View.GONE);
        jhpiegoDatabase = new JhpiegoDatabase(this);
        sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();

        //   button_history= (Button) findViewById(R.id.am_history);
        button_submit = (Button) findViewById(R.id.am_submit);
        button_submit.setEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.formList);
        formDetails = new ArrayList<>();
        mainForm_recycler_adapter = new MainForm_Recycler_Adapter(this, formDetails, jhpiegoDatabase);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mainForm_recycler_adapter);
        mainForm_recycler_adapter.notifyDataSetChanged();

        intiViews();
        addForms();

        getUserDisplay();


        try {
            // String query1 = "select * from " + TABLENAME1;
            Cursor cursor = sqLiteDatabase.query(TABLENAME1, new String[]{COL_USERNAME, COL_SESSION}, COL_USERNAME + "=?", new String[]{username}, null, null, null);
            if (cursor.getCount() > 0) {
                cursor.moveToLast();
                try {
                    session = cursor.getString(cursor.getColumnIndex(COL_SESSION));
                    boolean submit = jhpiegoDatabase.LastVisitSubmitted(session);
                } catch (Exception e) {
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isButtonEnabled) {
                    try {
                        jhpiegoDatabase = new JhpiegoDatabase(MainActivity.this);
                        sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();

                        Cursor cursorForSessionId = sqLiteDatabase.query(TABLENAME1, new String[]{COL_ID, COL_SESSION}, COL_IS_ASSESSMENT + "=?", new String[]{"0"}, null, null, null);
                        if (cursorForSessionId.getCount() > 0) {
                            cursorForSessionId.moveToFirst();
                            sessionID = cursorForSessionId.getString(cursorForSessionId.getColumnIndex(COL_SESSION));
                        }

                        String query1, query2, query3, query4, query5, query6, query7, query8, query9,query10,query11;
                        Cursor cursor1, cursor2, cursor3, cursor4, cursor5, cursor6, cursor7, cursor8, cursor9, cursor10,cursor11;
                        query1 = "select * from " + TABLENAME1 + " where session = '" + sessionID + "'";
                        cursor1 = sqLiteDatabase.rawQuery(query1, null);
                        query2 = "select * from " + TABLENAME7 + " where session = '" + sessionID + "'";
                        cursor2 = sqLiteDatabase.rawQuery(query2, null);
                        query3 = "select * from " + TABLENAME5 + " where session = '" + sessionID + "'";
                        cursor3 = sqLiteDatabase.rawQuery(query3, null);
                        query4 = "select * from " + TABLENAME8 + " where session = '" + sessionID + "'";
                        cursor4 = sqLiteDatabase.rawQuery(query4, null);
                        query5 = "select * from " + TABLE_ClinicalStandards + " where session = '" + sessionID + "'";
                        cursor5 = sqLiteDatabase.rawQuery(query5, null);
                        query6 = "select * from " + TABLENAME6 + " where session = '" + sessionID + "'";
                        cursor6 = sqLiteDatabase.rawQuery(query6, null);
                        query7 = "select * from " + TABLENAME9 + " where session = '" + sessionID + "'";
                        cursor7 = sqLiteDatabase.rawQuery(query7, null);
                        query8 = "select * from " + TABLENAME10 + " where session = '" + sessionID + "'";
                        cursor8 = sqLiteDatabase.rawQuery(query8, null);
                        query9 = "select * from " + TABLENAME11 + " where session = '" + sessionID + "'";
                        cursor9 = sqLiteDatabase.rawQuery(query9, null);
                        query10 = "select * from " + TABLENAME17 + " where session = '" + sessionID + "'";
                        cursor10 = sqLiteDatabase.rawQuery(query10, null);
                        query11 = "select * from " + TABLENAME18 + " where session = '" + sessionID + "'";
                        cursor11 = sqLiteDatabase.rawQuery(query11, null);

                        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("DAKSHATA MENTOR");
                        builder.setCancelable(false);
                        if (cursor1.getCount() > 0 || cursor2.getCount() > 0
                                || cursor3.getCount() > 0 || cursor4.getCount() > 0
                                || cursor5.getCount() > 0 || cursor6.getCount() > 0
                                || cursor7.getCount() > 0 || cursor8.getCount() > 0
                                || cursor9.getCount() > 0 || cursor10.getCount() > 0
                                || cursor11.getCount() > 0) {
                            if (cursor1.getCount() > 0 && cursor2.getCount() > 0 && cursor3.getCount() > 0 && cursor4.getCount() > 0 && cursor5.getCount() > 0 && cursor6.getCount() > 0 && cursor7.getCount() > 0 && cursor8.getCount() > 0 && cursor9.getCount() > 0&& cursor10.getCount() > 0) {
                                builder.setMessage("Do you wish to save your data in draft?");
                            } else {
                                builder.setMessage("Few questions have not been answered. Do you wish to save your data in draft?");
                            }
                        } else {
                            builder.setMessage("You have not answered some of the forms still do you want to submit session in draft?");
                        }
                        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                saveForAssessment();
                                dialogInterface.cancel();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        builder.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No record found for assessment.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        getAllAccessToGPSLocation();


    }

    public void addForms() {
        sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
        editor1 = sharedPreferences.edit();
        String session = sharedPreferences.getString("session", "");
        String count1 = String.valueOf(0), count2 = String.valueOf(0), count3 = String.valueOf(0), count4 = String.valueOf(0), count5 = String.valueOf(0), count6 = String.valueOf(0), count7 = String.valueOf(0), count8 = String.valueOf(0), count9 = String.valueOf(0),count10 = String.valueOf(0),count11 = String.valueOf(0);

        sharedPreferencescount = getSharedPreferences("cr" + username, MODE_PRIVATE);
        editorcount = sharedPreferencescount.edit();
        count8 = sharedPreferencescount.getString("count" + username, "0");

        sharedPreferencescount = getSharedPreferences("dov" + username, MODE_PRIVATE);
        editorcount = sharedPreferencescount.edit();
        count9 = sharedPreferencescount.getString("count" + username, "0");

        sharedPreferencescount = getSharedPreferences("ds" + username, MODE_PRIVATE);
        editorcount = sharedPreferencescount.edit();
        count3 = sharedPreferencescount.getString("count" + username, "0");

        sharedPreferencescount = getSharedPreferences("infra" + username, MODE_PRIVATE);
        editorcount = sharedPreferencescount.edit();
        count2 = sharedPreferencescount.getString("count" + username, "0");

        sharedPreferencescount = getSharedPreferences("mp" + username, MODE_PRIVATE);
        editorcount = sharedPreferencescount.edit();
        count5 = sharedPreferencescount.getString("count" + username, "0");

        sharedPreferencescount = getSharedPreferences("nvd" + username, MODE_PRIVATE);
        editorcount = sharedPreferencescount.edit();
        count7 = sharedPreferencescount.getString("count" + username, "0");

        sharedPreferencescount = getSharedPreferences("rr" + username, MODE_PRIVATE);
        editorcount = sharedPreferencescount.edit();
        count6 = sharedPreferencescount.getString("count" + username, "0");

        sharedPreferencescount = getSharedPreferences("sm" + username, MODE_PRIVATE);
        editorcount = sharedPreferencescount.edit();
        count1 = sharedPreferencescount.getString("count" + username, "0");

        sharedPreferencescount = getSharedPreferences("cs" + username, MODE_PRIVATE);
        editorcount = sharedPreferencescount.edit();
        count4 = sharedPreferencescount.getString("count" + username, "0");

        sharedPreferencescount = getSharedPreferences("ct" + username, MODE_PRIVATE);
        editorcount = sharedPreferencescount.edit();
        count10 = sharedPreferencescount.getString("count" + username, "0");

        sharedPreferencescount = getSharedPreferences("cf" + username, MODE_PRIVATE);
        editorcount = sharedPreferencescount.edit();
        count11 = sharedPreferencescount.getString("count" + username, "0");

        updateList(count1, count2, count3, count4, count5, count6, count7, count8, count9,count10,count11);
    }

    private void updateList(String count1, String count2, String count3, String count4, String count5, String count6,
                            String count7, String count8, String count9,String count10,String count11) {

        try {
            formDetails.clear();
        } catch (Exception e) {
        }
        FormDetails formDetailed = new FormDetails(getString(R.string.section_a_header_1), R.drawable.re_regist_r, count9);
        formDetails.add(formDetailed);
        formDetailed = new FormDetails(getString(R.string.section_b_header_1), R.drawable.re_regist_r, count1);
        formDetails.add(formDetailed);
        formDetailed = new FormDetails(getString(R.string.section_c_header_1), R.drawable.re_regist_r, count2);
        formDetails.add(formDetailed);
        formDetailed = new FormDetails(getString(R.string.section_d_header_1), R.drawable.re_regist_r, count3);
        formDetails.add(formDetailed);
        formDetailed = new FormDetails(getString(R.string.section_e_header_1), R.drawable.re_regist_r, count4);
        formDetails.add(formDetailed);
        formDetailed = new FormDetails(getString(R.string.section_f_header_1), R.drawable.re_regist_r, count5);
        formDetails.add(formDetailed);
        formDetailed = new FormDetails(getString(R.string.section_g_header_1), R.drawable.re_regist_r, count6);
        formDetails.add(formDetailed);
        formDetailed = new FormDetails(getString(R.string.section_h_label_1), R.drawable.re_regist_r, count7);
        formDetails.add(formDetailed);
        formDetailed = new FormDetails(getString(R.string.section_i_label_1), R.drawable.re_regist_r, count8);
        formDetails.add(formDetailed);
        formDetailed = new FormDetails(getString(R.string.provider_wise_tracking), R.drawable.re_regist_r, count10);
        formDetails.add(formDetailed);
        formDetailed = new FormDetails(getString(R.string.client_feedback_form), R.drawable.re_regist_r, count11);
        formDetails.add(formDetailed);

        if (!count9.equalsIgnoreCase("0")) {
            isButtonEnabled = true;
        } else {
            isButtonEnabled = false;
        }

        SQLiteDatabase sqLiteDatabase1 = jhpiegoDatabase.getReadableDatabase();
        Cursor cursor1 = sqLiteDatabase1.query(TABLENAME1, new String[]{COL_ID}, COL_IS_ASSESSMENT + "=?", new String[]{"0"}, null, null, null);
        if (cursor1.getCount() > 0) {
            cursor1.moveToFirst();
            MentorConstant.recordId = Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(COL_ID)));
        } else {
            MentorConstant.recordId = 0;
        }

        mainForm_recycler_adapter.notifyDataSetChanged();
    }

    private void intiViews() {
       /* textViewblock= (TextView) findViewById(R.id.block_main);
        textViewdist= (TextView) findViewById(R.id.district_main);
        textViewfacility= (TextView) findViewById(R.id.facility_main);
        textViewnom= (TextView) findViewById(R.id.nom_main);
        textViewstate= (TextView) findViewById(R.id.state_main);
        textViewtof= (TextView) findViewById(R.id.tof_main);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.check_for_draft:
                Intent assessment = new Intent(MainActivity.this, DraftActivity.class);
                startActivity(assessment);
                break;
            case R.id.history:
                Intent intent = new Intent(MainActivity.this, JhpiegoHistory.class);
                startActivity(intent);
                break;
            case R.id.comparision_record:
                /*Intent intent12 = new Intent(MainActivity.this, ComparisionRecord.class);
                startActivity(intent12);*/
                break;
            case R.id.sync:
                Intent intent2 = new Intent(MainActivity.this, SyncActivity.class);
                startActivity(intent2);
                break;
            case R.id.workplan:
                Intent intent_workplan = new Intent(MainActivity.this, CalenderSelectionActivity.class);
                startActivity(intent_workplan);
                break;
            case R.id.profile:
                Intent intent1 = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent1);
                break;
            case R.id.logout:
                alertDialogue("Logout From Dakshata Mentor", "Are you sure you want to logout from the app?", "LOGOUT", 1);
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onResume() {
        super.onResume();
        MentorConstant.recordFromDraft = 0;
        MentorConstant.turnGPSOn(this);
        String provider = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !provider.contains("gps")) {
            MentorConstant.buildAlertMessageNoGps(this);
        } else {
            try {
                if (MentorConstant.alert != null)
                    if (MentorConstant.alert.isShowing())
                        MentorConstant.alert.cancel();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        getQuestionMasterFromDB();
        getUserDisplay();
        addForms();
        try {
            // String query1 = "select * from " + TABLENAME1;
            Cursor cursor = sqLiteDatabase.query(TABLENAME1, new String[]{COL_USERNAME, COL_SESSION}, COL_USERNAME + "=?", new String[]{username}, null, null, null);
            if (cursor.getCount() > 0) {
                cursor.moveToLast();
                try {
                    String session = cursor.getString(cursor.getColumnIndex(COL_SESSION));
                    boolean submit = jhpiegoDatabase.LastVisitSubmitted(session);
                } catch (Exception e) {
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            String query1 = "select * from " + TABLENAME1;
            Cursor cursor = sqLiteDatabase.query(TABLENAME1, new String[]{COL_USERNAME, COL_SESSION}, COL_USERNAME + "=?", new String[]{username}, null, null, null);
            if (cursor.getCount() > 0) {
                cursor.moveToLast();
                try {
                    String session = cursor.getString(cursor.getColumnIndex(COL_SESSION));
                    boolean submit = jhpiegoDatabase.LastVisitSubmitted(session);
                } catch (Exception e) {
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        getQuestionMasterFromDB();
        getUserDisplay();
    }

    @Override
    public void onBackPressed() {
        alertDialogue("Exit From Dakshata Mentor", "Are you sure you want to exit from the app?", "EXIT", 2);
    }

    private void backUpDB() {
        try {
            String pnm = getApplicationContext().getPackageName();
            File sd = Environment.getExternalStorageDirectory();

            if (sd.canWrite()) {
                File locDB = new File(getFilesDir(), "../databases/");
                String backupDBPath = "mentorDB.db";
                File currentDB = new File(locDB, JhpiegoDatabase.DATABASENAME);
                File backupDB = new File(sd, backupDBPath);

                File file = new File(sd + backupDBPath);
                if (file.exists() == true) {
                    boolean deleted = file.delete();
                    Log.e("Delete", "Delete");
                }

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }

                if (true) {
                    File locPF = new File(getFilesDir(), "../shared_prefs/");
                    File currentPF = new File(locPF, pnm + "_preferences.xml");
                    File backupPF = new File(sd, "prefs.xml");
                    FileChannel src = new FileInputStream(currentPF).getChannel();
                    FileChannel dst = new FileOutputStream(backupPF).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();


        }
    }

    public void getUserDisplay() {
        try {
            sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
            editor = sh_Pref.edit();

            username = sh_Pref.getString("Username", "Unknown");
            SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query(TABLENAME, new String[]{COL_USERNAME, COL_MOBILE}, COL_USERNAME + "=?", new String[]{username}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                String mobile = cursor.getString(cursor.getColumnIndex(COL_MOBILE));
                String dbusername = cursor.getString(cursor.getColumnIndex(COL_USERNAME));
                TextView textView1, textView2;
                textView1 = (TextView) findViewById(R.id.header_name);
                textView2 = (TextView) findViewById(R.id.header_id);
//                textView1.setText("Name: "+dbusername);
//                textView2.setText("Mobile: "+mobile);
            }

        } catch (Exception e) {
        }
    }

    public void saveForAssessment() {
        try {
            String status = "pending";

            jhpiegoDatabase.updateIsAssessment(username);
            clearSharedPreferences();
            updateList("0", "0", "0", "0", "0", "0", "0", "0", "0","0","0");
            //   Toast.makeText(getApplicationContext(), "Successfully submitted", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getQuestionMasterFromDB() {
        try {
            SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
            String query = "select * from " + TABLENAME12;
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor.getCount() > 0) {
                cursor.moveToLast();
                String questions = cursor.getString(cursor.getColumnIndex(COL_1));
                Log.v("questions", "" + questions);
                Gson gson = new Gson();
                DetailsOfVisit.mQuetionsMaster = gson.fromJson(questions, QuetionsMaster.class);
                ClinicalStandards.mQuetionsMaster = gson.fromJson(questions, QuetionsMaster.class);
                CommentsRemarks.mQuetionsMaster = gson.fromJson(questions, QuetionsMaster.class);
                DrugsSupply.mQuetionsMaster = gson.fromJson(questions, QuetionsMaster.class);
                Infrastructure.mQuetionsMaster = gson.fromJson(questions, QuetionsMaster.class);
                MentoringPractices.mQuetionsMaster = gson.fromJson(questions, QuetionsMaster.class);
                NextVisitDate.mQuetionsMaster = gson.fromJson(questions, QuetionsMaster.class);
                RecprdingRoporting.mQuetionsMaster = gson.fromJson(questions, QuetionsMaster.class);
                StaffMaternity.mQuetionsMaster = gson.fromJson(questions, QuetionsMaster.class);
            }
        } catch (Exception e) {
        }
    }

    private boolean isNetworkAvailable() {
//        ConnectivityManager connectivityManager
//                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        return false;
    }

    private boolean isNetworkAvailableOther() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//        return false;
    }

    public void imageMultipart() {
        Uri tempUri1 = getImageUri(getApplicationContext(), bitmap1);
        Uri tempUri2 = getImageUri(getApplicationContext(), bitmap2);
        Uri tempUri3 = getImageUri(getApplicationContext(), bitmap3);
        Uri tempUri4 = getImageUri(getApplicationContext(), bitmap4);

        // CALL THIS METHOD TO GET THE ACTUAL PATH
        finalFile1 = new File(getRealPathFromURI(tempUri1));
        finalFile2 = new File(getRealPathFromURI(tempUri2));
        finalFile3 = new File(getRealPathFromURI(tempUri3));
        finalFile4 = new File(getRealPathFromURI(tempUri4));

        Log.v("finalFile", "" + finalFile1 + finalFile2 + finalFile3 + finalFile4);
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(finalFile1));
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            os = new BufferedOutputStream(new FileOutputStream(finalFile2));
            bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            os = new BufferedOutputStream(new FileOutputStream(finalFile3));
            bitmap3.compress(Bitmap.CompressFormat.JPEG, 100, os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            os = new BufferedOutputStream(new FileOutputStream(finalFile4));
            bitmap4.compress(Bitmap.CompressFormat.JPEG, 100, os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
       /* multipartEntity = MultipartEntityBuilder.create();
        multipartEntity.addTextBody("eid", "1");
        multipartEntity.addTextBody("uid", "14633");
        multipartEntity.addBinaryBody("image", finalFile1, ContentType.create("image/jpeg"), finalFile1.getName());
        multipartEntity.addBinaryBody("image", finalFile2, ContentType.create("image/jpeg"), finalFile2.getName());
        multipartEntity.addBinaryBody("image", finalFile3, ContentType.create("image/jpeg"), finalFile3.getName());
        multipartEntity.addBinaryBody("image", finalFile4, ContentType.create("image/jpeg"), finalFile4.getName());*/
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
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
                            address = MentorConstant.getAddress(MainActivity.this, MentorConstant.latitude, MentorConstant.longitude);
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
                    address = MentorConstant.getAddress(MainActivity.this, MentorConstant.latitude, MentorConstant.longitude);
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
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        MentorConstant.MY_PERMISSIONS_REQUEST_LOCATION);
//                new AlertDialog.Builder(this)
//                        .setTitle(R.string.title_location_permission)
//                        .setMessage(R.string.text_location_permission)
//                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                //Prompt the user once explanation has been shown
//                                ActivityCompat.requestPermissions(JhpiegoHistory.this,
//                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                                        MY_PERMISSIONS_REQUEST_LOCATION);
//                            }
//                        })
//                        .create()
//                        .show();
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
            address = MentorConstant.getAddress(MainActivity.this, MentorConstant.latitude, MentorConstant.longitude);
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
            HttpPost httppost = new HttpPost(Utils.URL_NEW + Utils.IMAGE_UPLOAD_NEW);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });
                List<File> list = new ArrayList<>();
                if (finalFile5 == null || finalFile5.matches("null")) {

                } else {
                    sourceFile1 = new File(finalFile5);
                    list.add(sourceFile1);
                    Log.v("sourceFile1", "" + sourceFile1);
                }
                if (finalFile6 == null || finalFile6.matches("null")) {

                } else {
                    sourceFile2 = new File(finalFile6);
                    list.add(sourceFile2);
                }
                if (finalFile7 == null || finalFile7.matches("null")) {

                } else {
                    sourceFile3 = new File(finalFile7);
                    list.add(sourceFile3);
                }
                if (finalFile8 == null || finalFile8.matches("null")) {

                } else {
                    sourceFile4 = new File(finalFile8);
                    list.add(sourceFile4);
                }
                Log.v("list", "" + list);

                // Adding file data to http body
                entity.addPart("username",
                        new StringBody(username));
                entity.addPart("visit_id", new StringBody(sessionid));
                for (int i = 0; i < list.size(); i++) {
                    if (!list.get(i).equals(null)) {
                        entity.addPart("image", new FileBody(list.get(i)));
                    }
                }


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
            try {
                JSONObject jsonObject = new JSONObject(result);
                imageResponse = jsonObject.getString("msg");
                Log.v("imageResponse", "" + imageResponse);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            imageResponse = result;

            super.onPostExecute(result);
        }

    }

    ProgressDialog dl;
    SQLiteDatabase sqLiteDatabaseLoc;

    private void getLocationMaster() {
        if (!isNetworkAvailableOther()) {
            Toast.makeText(getApplicationContext(), "Please check network connection", Toast.LENGTH_SHORT).show();
        } else {
            new AsyncTask<Void, Void, Void>() {
                ProgressDialog dl;
                JSONArray jsonArray;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    dl = new ProgressDialog(MainActivity.this);
                    dl.setTitle("Loading Location Master");
                    dl.setMessage("Please wait...");
                    dl.setCancelable(false);
                    dl.show();
                    sqLiteDatabaseLoc = jhpiegoDatabase.getWritableDatabase();
                }

                @Override
                protected Void doInBackground(Void... voids) {

                    try {
                        URL url = new URL(Utils.LOCATION_MASTER_NEW + username); // here is your URL path

                        try {
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("GET");
                            // read the response
                            InputStream in = new BufferedInputStream(conn.getInputStream());
                            String response1 = convertStreamToString(in);
                            Log.d("MainActivity", "doInBackground: "+response1);
                            jsonArray = new JSONArray(response1);
                            Gson gson = new Gson();
                            Log.v("size", "" + jsonArray.length());
                            sqLiteDatabaseLoc.beginTransaction();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    LocationMaster mLocationMaster = gson.fromJson(jsonObject.toString(), LocationMaster.class);
                                    long row = jhpiegoDatabase.saveLocationMaster(sqLiteDatabaseLoc, mLocationMaster.getState(), mLocationMaster.getDistrict(), mLocationMaster.getBlock(), mLocationMaster.getFacility(),
                                            mLocationMaster.getFacilityType(), mLocationMaster.getSubfacility(),
                                            "Village Name", "Taluka Name", "SCTaluka Name",mLocationMaster.getFacility(),mLocationMaster.getStateCode(),mLocationMaster.getDistrictCode(),mLocationMaster.getBlockCode(),mLocationMaster.getFacilityCode(),mLocationMaster.getSubfacilityCode());
                                    if (row != -1) {

                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getApplicationContext(), "Location Master not saved", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            sqLiteDatabaseLoc.setTransactionSuccessful();
                            sqLiteDatabaseLoc.endTransaction();
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (sqLiteDatabaseLoc.isOpen()) {
                                sqLiteDatabaseLoc.setTransactionSuccessful();
                                sqLiteDatabaseLoc.endTransaction();
                            }
                        }
                    } catch (Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Server error occurred", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    if (sqLiteDatabaseLoc.isOpen()) sqLiteDatabase.close();
                    if (dl != null && dl.isShowing()) {
                        dl.dismiss();
                    }
                }

            }.execute();
        }

    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private void cancelDialogue(ProgressDialog dl) {
        if (dl != null && dl.isShowing()) {
            dl.dismiss();
        }
    }

    private void clearSharedPreferences() {
        sharedPreferencescount = getSharedPreferences("cs" + username, MODE_PRIVATE);
        editorcount = sharedPreferencescount.edit();
        editorcount.clear();
        editorcount.commit();
        sharedPreferencescount = getSharedPreferences("cr" + username, MODE_PRIVATE);
        editorcount = sharedPreferencescount.edit();
        editorcount.clear();
        editorcount.commit();
        sharedPreferencescount = getSharedPreferences("ds" + username, MODE_PRIVATE);
        editorcount = sharedPreferencescount.edit();
        editorcount.clear();
        editorcount.commit();
        sharedPreferencescount = getSharedPreferences("sm" + username, MODE_PRIVATE);
        editorcount = sharedPreferencescount.edit();
        editorcount.clear();
        editorcount.commit();
        sharedPreferencescount = getSharedPreferences("nvd" + username, MODE_PRIVATE);
        editorcount = sharedPreferencescount.edit();
        editorcount.clear();
        editorcount.commit();
        sharedPreferencescount = getSharedPreferences("rr" + username, MODE_PRIVATE);
        editorcount = sharedPreferencescount.edit();
        editorcount.clear();
        editorcount.commit();
        sharedPreferencescount = getSharedPreferences("infra" + username, MODE_PRIVATE);
        editorcount = sharedPreferencescount.edit();
        editorcount.clear();
        editorcount.commit();
        sharedPreferencescount = getSharedPreferences("dov" + username, MODE_PRIVATE);
        editorcount = sharedPreferencescount.edit();
        editorcount.clear();
        editorcount.commit();
        sharedPreferencescount = getSharedPreferences("mp" + username, MODE_PRIVATE);
        editorcount = sharedPreferencescount.edit();
        editorcount.clear();
        editorcount.commit();
        sharedPreferencescount = getSharedPreferences("ct" + username, MODE_PRIVATE);
        editorcount = sharedPreferencescount.edit();
        editorcount.clear();
        editorcount.commit();
        sharedPreferencescount = getSharedPreferences("cf" + username, MODE_PRIVATE);
        editorcount = sharedPreferencescount.edit();
        editorcount.clear();
        editorcount.commit();

        SharedPreferences.Editor sPref = getSharedPreferences("session", MODE_PRIVATE).edit();
        sPref.putString("gps_record_id", "");
        sPref.commit();
        SharedPreferences.Editor editorNew = getSharedPreferences("dov" + username, MODE_PRIVATE).edit();
        editorNew.putString("isRecordExist", "no");
        editorNew.commit();
    }

    public void alertDialogue(String title, String message, String btnName, final int position) {
        View view1;
        view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.alert_exit_dialog, null);
        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setView(view1);
        TextView alertTitle = view1.findViewById(R.id.alertTitle);
        TextView textMessage = view1.findViewById(R.id.ad_tv1);
        Button btnSave = view1.findViewById(R.id.ad_save);
        Button btnCancel = view1.findViewById(R.id.ad_cancel);

        textMessage.setText(message);
        alertTitle.setText(title);
        btnSave.setText(btnName);
        alertDialog.show();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position) {
                    case 1:
                        clearSharedPreferences();
                        jhpiegoDatabase.clearDbAtLogoutTime();
                        MentorConstant.recordId = 0;
                        SharedPreferences sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sh_Pref.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent3 = new Intent(MainActivity.this, JhpiegoLogin.class);
                        intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent3);
                        finish();
                        break;

                    case 2:
                        MainActivity.super.onBackPressed();
                        backUpDB();
                        finish();
                        break;
                }
                alertDialog.cancel();
            }
        });
    }
}
