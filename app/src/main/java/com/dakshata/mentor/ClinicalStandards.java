package com.dakshata.mentor;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dakshata.mentor.Utils.MentorConstant;
import com.dakshata.mentor.adapter.QuestionListAdapter;
import com.dakshata.mentor.listener.RadioButtonSelection;
import com.dakshata.mentor.models.AnswersModel;
import com.dakshata.mentor.models.F5;
import com.dakshata.mentor.models.FormDataSubQuestion;
import com.dakshata.mentor.models.FormDatum;
import com.dakshata.mentor.models.QuestListDto;
import com.dakshata.mentor.models.QuetionsMaster;
import com.dakshata.mentor.models.VisitDatum;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.dakshata.mentor.JhpiegoDatabase.COL_1;
import static com.dakshata.mentor.JhpiegoDatabase.COL_ID;
import static com.dakshata.mentor.JhpiegoDatabase.COL_SESSION;
import static com.dakshata.mentor.JhpiegoDatabase.COL_UNIQUE_ID;
import static com.dakshata.mentor.JhpiegoDatabase.JSON_DATA;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME12;
import static com.dakshata.mentor.JhpiegoDatabase.TABLE_ClinicalStandards;

/**
 * Created by Aditya on 12/16/2017.
 */

public class ClinicalStandards extends AppCompatActivity implements LocationListener, RadioButtonSelection {

    Button button_save, button_back;
    JhpiegoDatabase jhpiegoDatabase;

    ProgressDialog progressDialog;
    SharedPreferences sh_Pref, sharedPreferences, sharedPreferencescount;
    SharedPreferences.Editor editor, editor1, editorcount;
    Toolbar toolbar;
    int count = 0;
    LinearLayout linearLayout;
    TextView textView;
    String session;
    String username;
    ImageView back;
    TextView textViewHeaderName;
    String sessionid;
    String ansJson;
    public static QuetionsMaster mQuetionsMaster = new QuetionsMaster();

    // Location Access
    LocationManager locationManager;
    String provider, address;
    Location location;
    private int loop = 0;

    private RecyclerView recyclerView;
    private QuestionListAdapter mAdapter;
    private QuestListDto questListDto;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clinical_standerd_new);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.menu_image_opt3));
        setSupportActionBar(toolbar);

        findIds();

    }

    @SuppressLint("NewApi")
    private void findIds() {

        back = (ImageView) findViewById(R.id.back);
        textViewHeaderName = (TextView) findViewById(R.id.header_name);
        textViewHeaderName.setText(getString(R.string.section_e_header_1));
        jhpiegoDatabase = new JhpiegoDatabase(this);
        progressDialog = new ProgressDialog(this);
        initViews();
        sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
        editor = sh_Pref.edit();

        sessionid = getIntent().getStringExtra("sessionNew");

        username = sh_Pref.getString("Username", "Unknown");
        try {
            SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();

            Cursor cursorQuestions = sqLiteDatabase.query(TABLENAME12, new String[]{COL_ID, COL_1}, "", new String[]{}, null, null, null);
            if (cursorQuestions.getCount() > 0) {
                cursorQuestions.moveToLast();
                JSONObject jsonObject = new JSONObject(cursorQuestions.getString(cursorQuestions.getColumnIndex(COL_1)));
                JSONArray jsonArray = jsonObject.getJSONArray("f5");
                Log.e("Array Length", "" + jsonArray.length());
                setDefaultValues(jsonArray);
            }

            /*Cursor cursor = sqLiteDatabase.query(TABLENAME1, new String[]{COL_USERNAME, COL_SESSION}, COL_USERNAME + "=?", new String[]{username}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                session = cursor.getString(cursor.getColumnIndex(COL_SESSION));
            }*/
            sqLiteDatabase.close();
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            mAdapter = new QuestionListAdapter(this, questListDto.getQues_array(), this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //setDefaultValues();
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

//    @SuppressLint("NewApi")
//    private void prepareJsonData(JSONArray jsonArray) {
//        JSONObject quesJobj = null;
//        JSONArray subQuesArray;
//        JSONArray quesArray = new JSONArray();
//        JSONObject s_q_jobj;
//        JSONObject mainJsonobj = new JSONObject();
//        JSONArray jsonArrayForSubQuestionJson = null;
//
//        try {
//            JSONObject jsonObjectForSubQuestionJson = new JSONObject(MentorConstant.sub_question_json);
//            jsonArrayForSubQuestionJson = jsonObjectForSubQuestionJson.getJSONArray("sub_ques_array");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            for (int i = 0; i < jsonArray.length(); i++) {
//                quesJobj = new JSONObject();
//                quesJobj.put("q_code", jsonArray.getJSONObject(i).getString("q_code"));
//                quesJobj.put("q_text", jsonArray.getJSONObject(i).getString("questions_name"));
//                quesJobj.put("q_ans", "");
//                quesJobj.put("q_selection", 0);
//                subQuesArray = new JSONArray();
//                for (int j = 0; j < jsonArrayForSubQuestionJson.length(); j++) {
//                    s_q_jobj = new JSONObject();
//                    if (jsonArrayForSubQuestionJson.getJSONObject(j).getString("s_q_id").equalsIgnoreCase(String.valueOf((i + 1)))) {
//                        s_q_jobj.put("s_q_id", jsonArrayForSubQuestionJson.getJSONObject(j).getString("s_q_id"));
//                        s_q_jobj.put("s_q_text", jsonArrayForSubQuestionJson.getJSONObject(j).getString("s_q_text"));
//                        s_q_jobj.put("s_q_code", jsonArrayForSubQuestionJson.getJSONObject(j).getString("s_q_code"));
//                        s_q_jobj.put("s_q_ans", jsonArrayForSubQuestionJson.getJSONObject(j).getString("s_q_ans"));
//                        s_q_jobj.put("isSelected", false);
//                        subQuesArray.put(s_q_jobj);
//                    }
//                }
//                quesJobj.put("sub_ques_array", subQuesArray);
//                quesArray.put(quesJobj);
//
//            }
//            mainJsonobj.put("ques_array", quesArray);
//            questListDto = new Gson().fromJson(mainJsonobj.toString(), QuestListDto.class);
//            System.out.println("final_outPut" + mainJsonobj);
//
//            if (mAdapter != null) mAdapter.notifyDataSetChanged();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }

    public void initViews() {

        button_save = (Button) findViewById(R.id.cs_save);
        button_back = (Button) findViewById(R.id.cs_back);
    }

    public void Pback(View view) {
        super.onBackPressed();
    }

    RadioButtonSelection listener;

    public void getUserEnteredData() {

        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        int tempCheckSelection = 0;
        boolean ss = false;
        count = 0;

        AnswersModel mAnswersModel = new AnswersModel();

        mAnswersModel.setUser(username);
        mAnswersModel.setVisitId(sessionid);

        List<FormDatum> formDatumList = new ArrayList<>();
        ArrayList<FormDataSubQuestion> form_data_sub_ques = new ArrayList<>();
        VisitDatum mVisitDatum = new VisitDatum();
        mVisitDatum.setFormCode("f5");

        for (int i = 0; i < questListDto.getQues_array().size(); i++) {

            if (questListDto.getQues_array().get(i).getQ_total_selected() != 0 && questListDto.getQues_array().get(i).getQ_total_selected() != questListDto.getQues_array().get(i).getSub_ques_array().size()) {
                Toast.makeText(this, "Q." + (i + 1) + " " + questListDto.getQues_array().
                        get(i).getQ_text() + " is not Filled Properly.", Toast.LENGTH_LONG).show();
                ss = true;
                progressDialog.dismiss();
                return;
            }

            if (questListDto.getQues_array().get(i).getQ_total_selected() > 0) {
                count = count + 1;
                ss = true;
            }

            for (int j = 0; j < questListDto.getQues_array().get(i).getSub_ques_array().size(); j++) {
                if (questListDto.getQues_array().get(i).getSub_ques_array().get(j).getS_q_ans().equalsIgnoreCase("Yes")) {
                    questListDto.getQues_array().get(i).setQ_ans("Yes");
                }
                if (questListDto.getQues_array().get(i).getSub_ques_array().get(j).getS_q_ans().equalsIgnoreCase("No")) {
                    questListDto.getQues_array().get(i).setQ_ans("No");
                    break;
                }

            }

            FormDatum mFormDatum = new FormDatum();
            formDatumList.add(createJson(mFormDatum, questListDto.getQues_array().get(i).getQ_ans(), i));

            for(int index=0;index<questListDto.getQues_array().get(i).getSub_ques_array().size();index++){
                FormDataSubQuestion formDataSubQuestion=new FormDataSubQuestion();
                QuestListDto subquestListDto=questListDto.getQues_array().get(i).getSub_ques_array().get(index);
                formDataSubQuestion.setS_q_text(subquestListDto.getS_q_text());
                formDataSubQuestion.setS_q_code(subquestListDto.getS_q_code());
                formDataSubQuestion.setS_q_id(subquestListDto.getS_q_id());
                formDataSubQuestion.setS_q_ans(subquestListDto.getS_q_ans());
                form_data_sub_ques.add(formDataSubQuestion);
            }

        }





        mVisitDatum.setFormData(formDatumList);
        mVisitDatum.setFormDataSubQuestions(form_data_sub_ques);
        Gson gsonmVisitDatum = new Gson();
        ansJson = gsonmVisitDatum.toJson(mVisitDatum);
        Log.v("ans ", "ans json v: " + ansJson);
//        mVisitDatumAns.add(mVisitDatum);
//        mAnswersModel.setVisitData(mVisitDatumAns);
//        Gson gson = new Gson();
//        String fullJson = gson.toJson(mAnswersModel);
//        try {
//            JSONObject jsonObject = new JSONObject(fullJson);
//            //serverPost(jsonObject);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        String jsonString = new Gson().toJson(questListDto);
        System.out.println("jsonString:-" + jsonString);

        // q_selection
        if (!ss) {
            progressDialog.dismiss();
            Toast.makeText(this, getString(R.string.please_select_atleast_one_value), Toast.LENGTH_LONG).show();
            return;
        }

        sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
        editor1 = sharedPreferences.edit();
        try {
            SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query(TABLE_ClinicalStandards, new String[]{COL_SESSION}, COL_SESSION + "=? AND " + COL_UNIQUE_ID + "=?", new String[]{sessionid, String.valueOf(MentorConstant.recordId)}, null, null, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                long row = jhpiegoDatabase.addClinicalStandardsNew(username, jsonString, String.valueOf(count), sessionid, ansJson, String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude), String.valueOf(MentorConstant.recordId), 1);
//                    SharedPreferences sPref = getSharedPreferences("session", MODE_PRIVATE);
                jhpiegoDatabase.updateLatAndLongWithDetailsOfVisit(String.valueOf(MentorConstant.recordId), String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude));
                Log.v("count1", "" + count);
                if (row != -1) {
                    Toast.makeText(getApplicationContext(), getString(R.string.alert_saved_successfully), Toast.LENGTH_SHORT).show();
                    if (!MentorConstant.whichBlockCalled) {
                        sharedPreferencescount = getSharedPreferences("cs" + username, MODE_PRIVATE);
                        editorcount = sharedPreferencescount.edit();
                        editorcount.putString("count" + username, String.valueOf(count));
                        editorcount.commit();
                    }
                    onBackPressed();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.alert_not_saved_to_db, Toast.LENGTH_SHORT).show();
                }
            } else {
//                createJson();
                long row = jhpiegoDatabase.addClinicalStandardsNew(username, jsonString, String.valueOf(count), sessionid, ansJson, String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude), String.valueOf(MentorConstant.recordId), 0);
//                    SharedPreferences sPref = getSharedPreferences("session", MODE_PRIVATE);
                jhpiegoDatabase.updateLatAndLongWithDetailsOfVisit(String.valueOf(MentorConstant.recordId), String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude));
                Log.v("count1", "" + count);
                if (row != -1) {
                    Toast.makeText(getApplicationContext(), getString(R.string.alert_saved_successfully), Toast.LENGTH_SHORT).show();
                    if (!MentorConstant.whichBlockCalled) {
                        sharedPreferencescount = getSharedPreferences("cs" + username, MODE_PRIVATE);
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

        progressDialog.dismiss();

    }

    public void setDefaultValues(JSONArray jsonArray) {
        Log.e("Error c", "" + jsonArray.toString());
        try {
//            if (jhpiegoDatabase.isLastVisitSubmittedClinicalStandardsNew(sessionid)) {
            SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query(TABLE_ClinicalStandards, new String[]{COL_SESSION, JSON_DATA}, COL_SESSION + "=?", new String[]{sessionid}, null, null, null);

            if (cursor.getCount() > 0) {
                cursor.moveToLast();
                JSONObject jsonObject = new JSONObject(cursor.getString(cursor.getColumnIndex(JSON_DATA)));
                questListDto = new Gson().fromJson(jsonObject.toString(), QuestListDto.class);
                if (mAdapter != null) mAdapter.notifyDataSetChanged();
            } else {
                questListDto = MentorConstant.prepareJsonData(jsonArray);
                if (mAdapter != null) mAdapter.notifyDataSetChanged();
            }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private FormDatum createJson(FormDatum mFormDatum, String q_ans, int i) {
        mFormDatum.setAns(q_ans);
        mFormDatum.setQCode("f5_q" + (i + 1));
        return mFormDatum;
    }



    public void serverPost(JSONObject jsonObject) {

        String url = "http://84538451.ngrok.io/services/bookmark_answers";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("clinicalstandars", "" + response);
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
                            address = MentorConstant.getAddress(ClinicalStandards.this, MentorConstant.latitude, MentorConstant.longitude);
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
                    address = MentorConstant.getAddress(ClinicalStandards.this, MentorConstant.latitude, MentorConstant.longitude);
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
                ActivityCompat.requestPermissions(ClinicalStandards.this,
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
            address = MentorConstant.getAddress(ClinicalStandards.this, MentorConstant.latitude, MentorConstant.longitude);
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

    @Override
    public void onSelected(int parentQuestionPosition, int subQuestionPosition, int isSelected) {
        if (isSelected == 0) {
            questListDto.getQues_array().get(parentQuestionPosition).getSub_ques_array().get(subQuestionPosition).setS_q_ans("");
        } else if (isSelected == 1) {
            questListDto.getQues_array().get(parentQuestionPosition).getSub_ques_array().get(subQuestionPosition).setS_q_ans("Yes");
        } else if (isSelected == 2) {
            questListDto.getQues_array().get(parentQuestionPosition).getSub_ques_array().get(subQuestionPosition).setS_q_ans("No");
        }
        questListDto.getQues_array().get(parentQuestionPosition).getSub_ques_array().get(subQuestionPosition).setSelected(true);
        if (mAdapter != null) mAdapter.notifyDataSetChanged();
        recyclerView.smoothScrollToPosition(parentQuestionPosition);
    }

    @Override
    public void parentQuestionListSelectionMark(int parentQuestionPosition, int isSelected, int tempTotalSelected) {
        if (isSelected == 0) {
            questListDto.getQues_array().get(parentQuestionPosition).setQ_ans("");
        } else if (isSelected == 1) {
            questListDto.getQues_array().get(parentQuestionPosition).setQ_ans("Yes");
        } else if (isSelected == 2) {
            questListDto.getQues_array().get(parentQuestionPosition).setQ_ans("No");
        }
        questListDto.getQues_array().get(parentQuestionPosition).setQ_selection(isSelected);
        questListDto.getQues_array().get(parentQuestionPosition).setQ_total_selected(tempTotalSelected);

//        if (tempTotalSelected == questListDto.getQues_array().get(parentQuestionPosition).getQues_array().size()) {
//            questListDto.getQues_array().get(parentQuestionPosition).setQ_total_selected();
//        }
    }
}
