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
import android.widget.EditText;
import android.widget.ImageView;
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
import static com.dakshata.mentor.JhpiegoDatabase.COL_ID;
import static com.dakshata.mentor.JhpiegoDatabase.COL_SESSION;
import static com.dakshata.mentor.JhpiegoDatabase.COL_UNIQUE_ID;
import static com.dakshata.mentor.JhpiegoDatabase.COL_USERNAME;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME11;

/**
 * Created by Aditya.v on 18-12-2017.
 */

public class CommentsRemarks extends AppCompatActivity implements LocationListener{
    EditText editText;
    Button button_save, button_back;
    JhpiegoDatabase jhpiegoDatabase;
    Toolbar toolbar;
    SharedPreferences sh_Pref, sharedPreferences, sharedPreferencescount;
    SharedPreferences.Editor editor, editor1, editorcount;
    int count = 0;
    String comments;
    String session;
    String username;
    TextView textViewHeaderName;
    ImageView back;
    String ansJson;
    public static QuetionsMaster mQuetionsMaster=new QuetionsMaster();
    String sessionid;

    // Location Access
    LocationManager locationManager;
    String provider, address;
    Location location;
    private int loop = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_remarks);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.menu_image_opt3));
        setSupportActionBar(toolbar);
        back = (ImageView) findViewById(R.id.back);
        textViewHeaderName = (TextView) findViewById(R.id.header_name);
        textViewHeaderName.setText(getString(R.string.section_i_label_1));
        editText = (EditText) findViewById(R.id.cr_et1);
        button_back = (Button) findViewById(R.id.cr_back);
        button_save = (Button) findViewById(R.id.cr_save);
        jhpiegoDatabase = new JhpiegoDatabase(this);

        sessionid = getIntent().getStringExtra("sessionNew");

        //getUserDisplay();
        sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
        editor = sh_Pref.edit();

        username = sh_Pref.getString("Username", "Unknown");

        try {
            SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query(TABLENAME, new String[]{COL_USERNAME, COL_SESSION}, COL_USERNAME + "=?", new String[]{username}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                session = cursor.getString(cursor.getColumnIndex(COL_SESSION));
            }
            sqLiteDatabase.close();
        } catch (Exception e) {
        }

        setDefaultSelectedValues();
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 comments = editText.getText().toString();
                if (comments.matches("")) {
                } else {
                    count++;
                    Log.v("cr", "" + count);
                    sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
                    editor1 = sharedPreferences.edit();
                    try {
                        SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                        Cursor cursor = sqLiteDatabase.query(TABLENAME11, new String[]{COL_SESSION}, COL_SESSION + "=? AND " + COL_UNIQUE_ID + "=?", new String[]{sessionid, String.valueOf(MentorConstant.recordId)}, null, null, null);
                        if (cursor.getCount() > 0) {
                            cursor.moveToFirst();
                            createJson();
//                            jhpiegoDatabase.deleteCommentsRemarks(username,comments, String.valueOf(count), sessionid,ansJson);
                            long row = jhpiegoDatabase.addCommentsRemarks(username,comments, String.valueOf(count), sessionid,ansJson, String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude), String.valueOf(MentorConstant.recordId), 1);
                            jhpiegoDatabase.updateLatAndLongWithDetailsOfVisit(String.valueOf(MentorConstant.recordId), String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude));
                            if (row != -1) {

                                Toast.makeText(getApplicationContext(), getString(R.string.alert_saved_successfully), Toast.LENGTH_SHORT).show();
                                if (!MentorConstant.whichBlockCalled) {
                                    sharedPreferencescount = getSharedPreferences("cr" + username, MODE_PRIVATE);
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
                            long row = jhpiegoDatabase.addCommentsRemarks(username,comments, String.valueOf(count), sessionid,ansJson, String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude), String.valueOf(MentorConstant.recordId), 0);
                            SharedPreferences sPref = getSharedPreferences("session", MODE_PRIVATE);
                            jhpiegoDatabase.updateLatAndLongWithDetailsOfVisit(String.valueOf(MentorConstant.recordId), String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude));
                            if (row != -1) {

                                Toast.makeText(getApplicationContext(), getString(R.string.alert_saved_successfully), Toast.LENGTH_SHORT).show();
                                if (!MentorConstant.whichBlockCalled) {
                                    sharedPreferencescount = getSharedPreferences("cr" + username, MODE_PRIVATE);
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
                        e.printStackTrace();
                    }
                }
            }
        });
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getAllAccessToGPSLocation();
    }

    public void Pback(View view) {
        super.onBackPressed();

    }

    public void setDefaultSelectedValues() {
        try {
            if (!jhpiegoDatabase.isLastVisitSubmittedCommentsRemarks(sessionid)) {
             //   Cursor cursor = jhpiegoDatabase.getLastVisitDataCommentsRemarks();
                SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                Cursor cursor=sqLiteDatabase.query(TABLENAME11,new String[]{COL_USERNAME,COL_1},COL_SESSION + "=?", new String[]{sessionid},null,null,null);

             //   cursor.moveToFirst();
                if (cursor.getCount() > 0) {
                    cursor.moveToLast();
                    String col_1 = cursor.getString(cursor.getColumnIndex(COL_1));
                    editText.setText(col_1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String getQuestionCode(String question) {

        for(F7 f9 :CommentsRemarks.mQuetionsMaster.getF7() ){
            if (f9.getQuestionsName() != null) {
                if (f9.getQuestionsName().replaceAll("\\s+$", "").equalsIgnoreCase(question.replaceAll("\\s+$", ""))){
                    Log.v("District f9", "" + f9.getQCode());
                    return f9.getQCode().toString();
                }
            }
            //something here
        }
        return "";

    }
    private void createJson() {
        AnswersModel mAnswersModel= new AnswersModel();

        mAnswersModel.setUser(username);
        mAnswersModel.setVisitId(sessionid);

        List<VisitDatum> mVisitDatumAns = new ArrayList<VisitDatum>();
        List<FormDatum> formDatumList = new ArrayList<>();
        VisitDatum mVisitDatum=new VisitDatum();
        mVisitDatum.setFormCode("f7");

        FormDatum mFormDatum=new FormDatum();
        mFormDatum.setAns(comments);
        mFormDatum.setQCode("f7_q6");
        //mVisitDatumAns.add(mVisitDatum);
        formDatumList.add(mFormDatum);

        mVisitDatum.setFormData(formDatumList);
        Gson gsonmVisitDatum= new Gson();
        ansJson=gsonmVisitDatum.toJson(mVisitDatum);
        Log.v("ans ","ans json v: "+ansJson);

        mVisitDatumAns.add(mVisitDatum);
        mAnswersModel.setVisitData(mVisitDatumAns);
        Gson gson= new Gson();
        String fullJson=gson.toJson(mAnswersModel);
        try {
            JSONObject jsonObject=new JSONObject(fullJson);
          //  serverPost(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.v("ans ","ans json : "+fullJson);
    }

    public void serverPost(JSONObject jsonObject){

        String url= "http://84538451.ngrok.io/services/bookmark_answers";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("commentsremarks", "" + response);
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
                            address = MentorConstant.getAddress(CommentsRemarks.this, MentorConstant.latitude, MentorConstant.longitude);
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
                    address = MentorConstant.getAddress(CommentsRemarks.this, MentorConstant.latitude, MentorConstant.longitude);
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
                ActivityCompat.requestPermissions(CommentsRemarks.this,
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
            address = MentorConstant.getAddress(CommentsRemarks.this, MentorConstant.latitude, MentorConstant.longitude);
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
