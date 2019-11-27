package com.dakshata.mentor;

import android.Manifest;
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
import com.dakshata.mentor.models.F7;
import com.dakshata.mentor.models.FormDatum;
import com.dakshata.mentor.models.QuetionsMaster;
import com.dakshata.mentor.models.VisitDatum;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.dakshata.mentor.JhpiegoDatabase.COL_1;
import static com.dakshata.mentor.JhpiegoDatabase.COL_2;
import static com.dakshata.mentor.JhpiegoDatabase.COL_3;
import static com.dakshata.mentor.JhpiegoDatabase.COL_4;
import static com.dakshata.mentor.JhpiegoDatabase.COL_ID;
import static com.dakshata.mentor.JhpiegoDatabase.COL_SESSION;
import static com.dakshata.mentor.JhpiegoDatabase.COL_UNIQUE_ID;
import static com.dakshata.mentor.JhpiegoDatabase.COL_USERNAME;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME1;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME9;

/**
 * Created by Aditya.v on 18-12-2017.
 */

public class RecprdingRoporting extends AppCompatActivity implements LocationListener {
    RadioGroup radioGroup1, radioGroup2, radioGroup3, radioGroup4;
    Button button_save, button_back;
    RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5, radioButton6, radioButton7, radioButton8, radioButton9, radioButton10, radioButton11, radioButton12;
    JhpiegoDatabase jhpiegoDatabase;
    String row_1, row_2, row_3, row_4;
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
        setContentView(R.layout.recording_reporting);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.menu_image_opt3));
        setSupportActionBar(toolbar);
        back = (ImageView) findViewById(R.id.back);
        textViewHeaderName = (TextView) findViewById(R.id.header_name);
        textViewHeaderName.setText(getString(R.string.section_g_header_1));
        radioGroup1 = (RadioGroup) findViewById(R.id.rr_rg1);
        radioGroup2 = (RadioGroup) findViewById(R.id.rr_rg2);
        radioGroup3 = (RadioGroup) findViewById(R.id.rr_rg3);
        radioGroup4 = (RadioGroup) findViewById(R.id.rr_rg4);
        radioButton3 = (RadioButton) findViewById(R.id.yes1);
        radioButton4 = (RadioButton) findViewById(R.id.yes2);
        radioButton5 = (RadioButton) findViewById(R.id.no1);
        radioButton6 = (RadioButton) findViewById(R.id.no2);

        radioButton9 = (RadioButton) findViewById(R.id.yes3);
        radioButton10 = (RadioButton) findViewById(R.id.yes4);
        radioButton11 = (RadioButton) findViewById(R.id.no3);
        radioButton12 = (RadioButton) findViewById(R.id.no4);

        button_back = (Button) findViewById(R.id.rr_back);
        button_save = (Button) findViewById(R.id.rr_save);

        jhpiegoDatabase = new JhpiegoDatabase(this);
        // getUserDisplay();
        sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
        editor = sh_Pref.edit();

        sessionid = getIntent().getStringExtra("sessionNew");

        username = sh_Pref.getString("Username", "Unknown");
        getSessionOfUser();
        setDefaultValues();
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getUserEnteredData();
            }
        });
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getAllAccessToGPSLocation();
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
    public void getUserEnteredData() {
        int selectedId = radioGroup1.getCheckedRadioButtonId();
        int selectedId2 = radioGroup2.getCheckedRadioButtonId();
        int selectedId3 = radioGroup3.getCheckedRadioButtonId();
        int selectedId4 = radioGroup4.getCheckedRadioButtonId();
        if (radioGroup1.getCheckedRadioButtonId() < 0 && radioGroup2.getCheckedRadioButtonId() < 0 &&
                radioGroup3.getCheckedRadioButtonId() < 0 && radioGroup4.getCheckedRadioButtonId() < 0) {

        } else {

            try {
                radioButton1 = (RadioButton) findViewById(selectedId);
                row_1 = radioButton1.getText().toString();
                if (!row_1.matches("")) {
                    count++;
                }
            } catch (Exception e) {
            }

            try {
                radioButton2 = (RadioButton) findViewById(selectedId2);
                row_2 = radioButton2.getText().toString();
                if (!row_2.matches("")) {
                    count++;
                }
            } catch (Exception e) {
            }

            try {
                radioButton7 = (RadioButton) findViewById(selectedId3);
                row_3 = radioButton7.getText().toString();
                if (!row_3.matches("")) {
                    count++;
                }
            } catch (Exception e) {
            }

            try {
                radioButton8 = (RadioButton) findViewById(selectedId4);
                row_4 = radioButton8.getText().toString();
                if (!row_4.matches("")) {
                    count++;
                }
            } catch (Exception e) {
            }


            sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
            editor1 = sharedPreferences.edit();
            try {
                SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                Cursor cursor = sqLiteDatabase.query(TABLENAME9, new String[]{COL_SESSION}, COL_SESSION + "=? AND " + COL_UNIQUE_ID + "=?", new String[]{sessionid, String.valueOf(MentorConstant.recordId)}, null, null, null);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    createJson();
//                    jhpiegoDatabase.deleteReporting(username, row_1, row_2, String.valueOf(count), sessionid, ansJson, row_3, row_4);
                    long row = jhpiegoDatabase.addReporting(username, row_1, row_2, String.valueOf(count), sessionid, ansJson, row_3, row_4, String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude), String.valueOf(MentorConstant.recordId), 1);
                    SharedPreferences sPref = getSharedPreferences("session", MODE_PRIVATE);
                    jhpiegoDatabase.updateLatAndLongWithDetailsOfVisit(String.valueOf(MentorConstant.recordId), String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude));
                    if (row != -1) {

                        Toast.makeText(getApplicationContext(), getString(R.string.alert_saved_successfully), Toast.LENGTH_SHORT).show();
                        if (!MentorConstant.whichBlockCalled) {
                            sharedPreferencescount = getSharedPreferences("rr" + username, MODE_PRIVATE);
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
                    long row = jhpiegoDatabase.addReporting(username, row_1, row_2, String.valueOf(count), sessionid, ansJson, row_3, row_4, String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude), String.valueOf(MentorConstant.recordId), 0);
                    SharedPreferences sPref = getSharedPreferences("session", MODE_PRIVATE);
                    jhpiegoDatabase.updateLatAndLongWithDetailsOfVisit(String.valueOf(MentorConstant.recordId), String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude));
                    if (row != -1) {

                        Toast.makeText(getApplicationContext(), getString(R.string.alert_saved_successfully), Toast.LENGTH_SHORT).show();
                        if (!MentorConstant.whichBlockCalled) {
                            sharedPreferencescount = getSharedPreferences("rr" + username, MODE_PRIVATE);
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

               /* long row = jhpiegoDatabase.addReporting(row_1, row_2, String.valueOf(count),sessionid);
            if (row != -1) {
                Toast.makeText(getApplicationContext(), getString(R.string.alert_saved_successfully), Toast.LENGTH_SHORT).show();
                sharedPreferencescount=getSharedPreferences("rr",MODE_PRIVATE);
                editorcount=sharedPreferencescount.edit();
                editorcount.putString("count", String.valueOf(count));
                editorcount.commit();
                Intent intent = new Intent(RecprdingRoporting.this, MainActivity.class);
                startActivity(intent);

            } else {
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
            if (!jhpiegoDatabase.isLastVisitSubmittedReporting(sessionid)) {
                //   Cursor cursor = jhpiegoDatabase.getLastVisitDataReporting();
                SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                Cursor cursor = sqLiteDatabase.query(TABLENAME9, new String[]{COL_USERNAME, COL_1, COL_2, COL_3, COL_4}, COL_SESSION + "=?", new String[]{sessionid}, null, null, null);

                //  cursor.moveToFirst();
                if (cursor.getCount() > 0) {
                    cursor.moveToLast();
                    String col_1 = cursor.getString(cursor.getColumnIndex(COL_1));
                    String col_2 = cursor.getString(cursor.getColumnIndex(COL_2));
                    String col_3 = cursor.getString(cursor.getColumnIndex(COL_3));
                    String col_4 = cursor.getString(cursor.getColumnIndex(COL_4));

                    if (col_1 != null)
                        if (col_1.toLowerCase().equalsIgnoreCase("yes")) {
                            radioButton3.setChecked(true);
                        } else {
                            radioButton5.setChecked(true);
                        }

                    if (col_2 != null)
                        if (col_2.toLowerCase().equalsIgnoreCase("yes")) {
                            radioButton4.setChecked(true);
                        } else {
                            radioButton6.setChecked(true);
                        }

                    if (col_3 != null)
                        if (col_3.toLowerCase().equalsIgnoreCase("yes")) {
                            radioButton9.setChecked(true);
                        } else {
                            radioButton11.setChecked(true);
                        }

                    if (col_4 != null)
                        if (col_4.toLowerCase().equalsIgnoreCase("yes")) {
                            radioButton10.setChecked(true);
                        } else {
                            radioButton12.setChecked(true);
                        }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getQuestionCode(String question) {

        for (F7 f7 : RecprdingRoporting.mQuetionsMaster.getF7()) {
            if (f7.getQuestionsName() != null) {
                if (f7.getQuestionsName().replaceAll("\\s+$", "").equalsIgnoreCase(question.replaceAll("\\s+$", ""))){
                    Log.v("District f7", "" + f7.getQCode());
                    return f7.getQCode().toString();
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
        mVisitDatum.setFormCode("f7");

        FormDatum mFormDatum = new FormDatum();
        mFormDatum.setAns(row_1);
        mFormDatum.setQCode("f7_q1");
        //mVisitDatumAns.add(mVisitDatum);
        formDatumList.add(mFormDatum);

        FormDatum mFormDatum2 = new FormDatum();
        mFormDatum2.setAns(row_3);
        mFormDatum2.setQCode("f7_q2");
        formDatumList.add(mFormDatum2);

        FormDatum mFormDatumstate = new FormDatum();
        mFormDatumstate.setAns(row_2);
        mFormDatumstate.setQCode("f7_q3");
        formDatumList.add(mFormDatumstate);

        FormDatum mFormDatumstate4 = new FormDatum();
        mFormDatumstate4.setAns(row_4);
        mFormDatumstate4.setQCode("f7_q4");
        formDatumList.add(mFormDatumstate4);

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
            // serverPost(jsonObject);
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
                Log.v("recordingreporting", "" + response);
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
                            address = MentorConstant.getAddress(RecprdingRoporting.this, MentorConstant.latitude, MentorConstant.longitude);
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
                    address = MentorConstant.getAddress(RecprdingRoporting.this, MentorConstant.latitude, MentorConstant.longitude);
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
                ActivityCompat.requestPermissions(RecprdingRoporting.this,
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
            address = MentorConstant.getAddress(RecprdingRoporting.this, MentorConstant.latitude, MentorConstant.longitude);
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
