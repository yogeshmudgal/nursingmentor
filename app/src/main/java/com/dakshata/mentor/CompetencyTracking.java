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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.dakshata.mentor.Utils.MentorConstant;
import com.dakshata.mentor.adapter.TableViewAdapter;
import com.dakshata.mentor.listener.CustomOnItemClickListner;
import com.dakshata.mentor.models.CompetencyTrackingDto;
import com.dakshata.mentor.models.F7;
import com.dakshata.mentor.models.QuetionsMaster;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.dakshata.mentor.JhpiegoDatabase.COL_ANSJSON;
import static com.dakshata.mentor.JhpiegoDatabase.COL_BLOCK;
import static com.dakshata.mentor.JhpiegoDatabase.COL_DIST;
import static com.dakshata.mentor.JhpiegoDatabase.COL_EMAIL;
import static com.dakshata.mentor.JhpiegoDatabase.COL_FACILITYNAME;
import static com.dakshata.mentor.JhpiegoDatabase.COL_FACILITYTYPE;
import static com.dakshata.mentor.JhpiegoDatabase.COL_ID;
import static com.dakshata.mentor.JhpiegoDatabase.COL_MOBILE;
import static com.dakshata.mentor.JhpiegoDatabase.COL_NAME;
import static com.dakshata.mentor.JhpiegoDatabase.COL_NOM;
import static com.dakshata.mentor.JhpiegoDatabase.COL_SESSION;
import static com.dakshata.mentor.JhpiegoDatabase.COL_STATE;
import static com.dakshata.mentor.JhpiegoDatabase.COL_UNIQUE_ID;
import static com.dakshata.mentor.JhpiegoDatabase.COL_USERNAME;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME1;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME17;

/**
 * Created by Aditya.v on 18-12-2017.
 */

public class CompetencyTracking extends AppCompatActivity implements LocationListener, CustomOnItemClickListner {
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
    TableViewAdapter adapter;
    // Location Access
    LocationManager locationManager;
    String provider, address;
    Location location;
    private int loop = 0;
    private ArrayList<CompetencyTrackingDto> list;
    private ImageButton ibDownload;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.competency_tracking);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.menu_image_opt3));
        setSupportActionBar(toolbar);
        back = (ImageView) findViewById(R.id.back);
        textViewHeaderName = (TextView) findViewById(R.id.header_name);
        textViewHeaderName.setText(getString(R.string.section_i_label_1));
        editText = (EditText) findViewById(R.id.cr_et1);
        //button_back = (Button) findViewById(R.id.cr_back);
        button_save = (Button) findViewById(R.id.cr_save);
        jhpiegoDatabase = new JhpiegoDatabase(this);
        sessionid = getIntent().getStringExtra("sessionNew");
        ibDownload=(ImageButton)findViewById(R.id.ibDownload);


        //getUserDisplay();
        sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
        editor = sh_Pref.edit();
        /*count = Integer.parseInt(sh_Pref.getString("count","0"));*/
        username = sh_Pref.getString("Username", "Unknown");
        count = Integer.parseInt(sh_Pref.getString("count","0"));
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

        setRecyclerView();
        setDefaultSelectedValues();
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Log.v("cT", "" + count);
                    sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
                    editor1 = sharedPreferences.edit();
                    try {
                        SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                        Cursor cursor = sqLiteDatabase.query(TABLENAME17, new String[]{COL_SESSION}, COL_SESSION + "=? AND " + COL_UNIQUE_ID + "=?", new String[]{sessionid, String.valueOf(MentorConstant.recordId)}, null, null, null);
                        if (cursor.getCount() > 0) {
                            cursor.moveToFirst();
                            createJson();
//                            jhpiegoDatabase.deleteCommentsRemarks(username,comments, String.valueOf(count), sessionid,ansJson);
                            long row = jhpiegoDatabase.addCompetencyTrackingData(username, String.valueOf(count), sessionid,ansJson, String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude), String.valueOf(MentorConstant.recordId), 1);
                            jhpiegoDatabase.updateLatAndLongWithDetailsOfVisit(String.valueOf(MentorConstant.recordId), String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude));
                            if (row != -1) {

                                Toast.makeText(getApplicationContext(), getString(R.string.alert_saved_successfully), Toast.LENGTH_SHORT).show();
                                if (!MentorConstant.whichBlockCalled) {
                                    sharedPreferencescount = getSharedPreferences("ct" + username, MODE_PRIVATE);
                                    editorcount = sharedPreferencescount.edit();
                                    editorcount.putString("count" + username, String.valueOf(count));
                                    editorcount.commit();
                                }
                                ibDownload.setVisibility(View.VISIBLE);
                                onBackPressed();
                            } else {
                                Toast.makeText(getApplicationContext(), getString(R.string.alert_not_saved_to_db), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            createJson();
                            long row = jhpiegoDatabase.addCompetencyTrackingData(username, String.valueOf(count), sessionid,ansJson, String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude), String.valueOf(MentorConstant.recordId), 0);
                            SharedPreferences sPref = getSharedPreferences("session", MODE_PRIVATE);
                            jhpiegoDatabase.updateLatAndLongWithDetailsOfVisit(String.valueOf(MentorConstant.recordId), String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude));
                            if (row != -1) {

                                Toast.makeText(getApplicationContext(), getString(R.string.alert_saved_successfully), Toast.LENGTH_SHORT).show();
                                if (!MentorConstant.whichBlockCalled) {
                                    sharedPreferencescount = getSharedPreferences("ct" + username, MODE_PRIVATE);
                                    editorcount = sharedPreferencescount.edit();
                                    editorcount.putString("count" + username, String.valueOf(count));
                                    editorcount.commit();
                                }
                                ibDownload.setVisibility(View.VISIBLE);
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

        });
        /*button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });*/
        getAllAccessToGPSLocation();

    }

    private void setRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.rvTableList);

        adapter = new TableViewAdapter(list,this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(adapter);
        if(list!=null&& list.size()>0){
            ibDownload.setVisibility(View.VISIBLE);
        }
    }


    public void Pback(View view) {
        super.onBackPressed();

    }

    public void setDefaultSelectedValues() {
        try {
            if (!jhpiegoDatabase.isLastVisitdataSubmited(sessionid)) {
             //   Cursor cursor = jhpiegoDatabase.getLastVisitDataCommentsRemarks();
                SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                Cursor cursor=sqLiteDatabase.query(TABLENAME17,new String[]{COL_USERNAME,COL_ANSJSON},COL_SESSION + "=?", new String[]{sessionid},null,null,null);

             //   cursor.moveToFirst();
                if (cursor.getCount() > 0) {
                    cursor.moveToLast();
                    String json = cursor.getString(cursor.getColumnIndex(COL_ANSJSON));
                    System.out.println("data"+json);
                    if (json != null) {
                        list.clear();
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<CompetencyTrackingDto>>(){}.getType();
                        list = gson.fromJson(json, type);

                        adapter.notifyDataSetChanged();
                        }
                    }

                }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String getQuestionCode(String question) {

        for(F7 f9 : CompetencyTracking.mQuetionsMaster.getF7() ){
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


/*
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;
        for(int i=0;i<11;i++) {
           if(i<10){

           }
            jsonObject = new JSONObject();
            for (int j = 0; j < 14; j++) {
                switch (j) {
                    case 0:
                        try {
                            jsonObject.put("Sr.No", list.get(i).getTv1());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 1:
                        try {
                            jsonObject.put("Name of Provider", list.get(i).getTv2());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        try {
                            jsonObject.put("Cadre(OBG/Doctor)", list.get(i).getTv3());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        try {
                            jsonObject.put("GA Estimation", list.get(i).getTv4());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 4:
                        try {
                            jsonObject.put("PV Examination", list.get(i).getTv5());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 5:
                        try {
                            jsonObject.put("AMTSL", list.get(i).getTv6());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 6:
                        try {
                            jsonObject.put("Parograph", list.get(i).getTv7());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 7:
                        try {
                            jsonObject.put("Discharge Counselling", list.get(i).getTv8());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 8:
                        try {
                            jsonObject.put("PE/E Management", list.get(i).getTv9());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 9:
                        try {
                            jsonObject.put("PPH Management", list.get(i).getTv10());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 10:
                        try {
                            jsonObject.put("NBR", list.get(i).getTv11());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 11:
                        try {
                            jsonObject.put("Small Size baby", list.get(i).getTv12());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 12:
                        try {
                            jsonObject.put("Competency of Skills(out of 9)", list.get(i).getTv13());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 13:
                        try {
                            jsonObject.put("provider ranking", list.get(i).getTv14());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                }

            }
            jsonArray.put(jsonObject);

        }
        ansJson =jsonArray.toString();*/
    }

    /*public void serverPost(JSONObject jsonObject){

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
    }*/

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
                            address = MentorConstant.getAddress(CompetencyTracking.this, MentorConstant.latitude, MentorConstant.longitude);
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
                    address = MentorConstant.getAddress(CompetencyTracking.this, MentorConstant.latitude, MentorConstant.longitude);
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
                ActivityCompat.requestPermissions(CompetencyTracking.this,
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
            address = MentorConstant.getAddress(CompetencyTracking.this, MentorConstant.latitude, MentorConstant.longitude);
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



    private void openDialogBox(ArrayList<CompetencyTrackingDto> list,int verticalPostion,String value){

        ArrayList<CompetencyTrackingDto> mList =list;
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialogbox_edittext, null);

        final EditText editText = (EditText) dialogView.findViewById(R.id.edt_comment);
        Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
        Button button2 = (Button) dialogView.findViewById(R.id.buttonCancel);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DO SOMETHINGS
                dialogBuilder.dismiss();
                if(count<10){
                    count++;
                }

                setPositionValue( list,verticalPostion, value,editText.getText().toString());

            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    private void setPositionValue(ArrayList<CompetencyTrackingDto> list,
                                  int verticalPostion, String value,String selectedValue) {
       /* for(int i=0;i<11;i++){
            if(i==verticalPostion){
               for(int j=0;j<14;j++){
                   if(value.equalsIgnoreCase("tv"+(j+1))){
                       switch (j){
                           case 0:
                               list.get(verticalPostion).setTv1(selectedValue);
                               break;
                               case 1:
                                   list.get(verticalPostion).setTv2(selectedValue);
                               break;
                               case 2:
                                   list.get(verticalPostion).setTv3(selectedValue);
                               break;
                               case 3:
                                   list.get(verticalPostion).setTv4(selectedValue);
                               break;
                               case 4:
                                   list.get(verticalPostion).setTv5(selectedValue);
                               break;
                               case 5:
                                   list.get(verticalPostion).setTv6(selectedValue);
                               break;
                               case 6:
                                   list.get(verticalPostion).setTv7(selectedValue);
                               break;
                               case 7:
                                   list.get(verticalPostion).setTv8(selectedValue);
                               break;
                               case 8:
                                   list.get(verticalPostion).setTv9(selectedValue);
                               break;
                               case 9:
                                   list.get(verticalPostion).setTv10(selectedValue);
                               break;
                               case 10:
                                   list.get(verticalPostion).setTv11(selectedValue);
                               break;
                               case 11:
                                   list.get(verticalPostion).setTv12(selectedValue);
                               break;
                               case 12:
                                   list.get(verticalPostion).setTv13(selectedValue);
                               break;
                               case 13:
                                   list.get(verticalPostion).setTv14(selectedValue);
                               break;

                       }
                       list.size();
                       adapter.notifyDataSetChanged();
                       break;
                   }
               }
            }
            else {

            }
        }*/
    }

    @Override
    public void OnClick(int verticalPosition, String tvValue) {

        openDialogBox(list,verticalPosition,tvValue);
    }





}
