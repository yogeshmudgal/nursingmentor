package com.dakshata.mentor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dakshata.mentor.Utils.MentorConstant;
import com.dakshata.mentor.Utils.Utils;
import com.dakshata.mentor.models.QuestListDto;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dakshata.mentor.JhpiegoDatabase.*;
import static com.dakshata.mentor.JhpiegoDatabase.COL_1;
import static com.dakshata.mentor.JhpiegoDatabase.COL_2;
import static com.dakshata.mentor.JhpiegoDatabase.COL_3;
import static com.dakshata.mentor.JhpiegoDatabase.COL_4;
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
import static com.dakshata.mentor.JhpiegoDatabase.COL_IMPE;
import static com.dakshata.mentor.JhpiegoDatabase.COL_IMPO;
import static com.dakshata.mentor.JhpiegoDatabase.COL_LP;
import static com.dakshata.mentor.JhpiegoDatabase.COL_NR;
import static com.dakshata.mentor.JhpiegoDatabase.COL_PP1;
import static com.dakshata.mentor.JhpiegoDatabase.COL_PPH;
import static com.dakshata.mentor.JhpiegoDatabase.COL_PV;
import static com.dakshata.mentor.JhpiegoDatabase.COL_ROLR;
import static com.dakshata.mentor.JhpiegoDatabase.COL_SCN;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME10;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME11;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME9;

/**
 * Created by Aditya on 1/5/2018.
 */

public class SyncActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SyncActivity";

    ListView listView;
    JhpiegoDatabase jhpiegoDatabase;
    SharedPreferences sh_Pref, sharedPreferences;
    SharedPreferences.Editor editor, editor1;
    List<SyncPojo> list;
    TextView textView;
    Cursor cursor, cursorForLoop, cursorClinicalPractices;
    Toolbar toolbar;
    SQLiteDatabase sqLiteDatabase;
    String sessionID;
    ImageView back;
    String selectedSession;
    TextView textViewHeaderName;
    //  HistoryAdapter historyAdapter;
    RecyclerView recyclerView;
    SyncAdapter syncAdapter;
    Button buttonSyncALL, buttonSyncData, buttonSyncPhoto;
    String username;
    ProgressDialog progressDialog;
    File sourceFile1, sourceFile2, sourceFile3, sourceFile4;
    String imageResponse;
    long totalSize = 0;
    String finalFile5, finalFile6, finalFile7, finalFile8;
    String sessionToPost, reference_key = "";
    private boolean isSubQuestionsAvailable = true, isCalled = false;
    private String col_session = "";

    // For Pdf Creation
    public static String mentorName, userEmail, lastVisitDate;
    public static String table1_col1, table1_col2, table1_col3, table1_col4, table1_col5, table1_col6, table1_col7, table1_col8, table1_col_latitude, table1_col_longitude, table1_colVisit, table1_colWorkPlanStatus, table1_colPlannedDate;
    public static String table2_col1, table2_col2, table2_col3, table2_col4, table2_col5, table2_col6, table2_col7, table2_col8, table2_col9, table2_col10;
    public static String table3_col21, table3_col2, table3_col3, table3_col4, table3_col5, table3_col6, table3_col7, table3_col8, table3_col9, table3_col10, table3_col11, table3_col12, table3_col13, table3_col14, table3_col15, table3_col16, table3_col17, table3_col18, table3_col19, table3_col20;
    public static String table4_col1, table4_col2, table4_col3, table4_col4, table4_col5, table4_col6, table4_col7, table4_col8, table4_col9, table4_col10, table4_col11, table4_col12, table4_col13, table4_col14, table4_col15, table4_col16, table4_col17, table4_col18, table4_col19, table4_col20, table4_col21, table4_col22, table4_col23, table4_col24, table4_col25, table4_col26;
    public static String table5_col1, table5_col2, table5_col3, table5_col4, table5_col5, table5_col6, table5_col7, table5_col8, table5_col9, table5_col10, table5_col11, table5_col12, table5_col13, table5_col14, table5_col15, table5_col16, table5_col17, table5_col18, table5_col19;
    public static String table6_col1, table6_col2, table6_col3, table6_col4, table6_col5, table6_col6, table6_col7, table6_col8, table6_col9, table6_col10, table6_col11, table6_col12, table6_col13, table6_col14, table6_col15, table6_col16, table6_col17, table6_col18, table6_col19;
    public static String table7_col1, table7_col2, table7_col3, table7_col4;
    public static String table8_col1;
    public static String table9_col1;
    public static String createdDate;
    public static String fileName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sync_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.menu_image_opt3));
        setSupportActionBar(toolbar);

        textViewHeaderName = (TextView) findViewById(R.id.header_name);
        textViewHeaderName.setText("Sync");
        buttonSyncALL = findViewById(R.id.button_sync_all);
        buttonSyncData = findViewById(R.id.button_sync_data);
        recyclerView = (RecyclerView) findViewById(R.id.history_recyler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        jhpiegoDatabase = new JhpiegoDatabase(this);
        textView = (TextView) findViewById(R.id.records);
        textView.setVisibility(View.GONE);
        list = new ArrayList<>();
        syncAdapter = new SyncAdapter(this, list);
        recyclerView.setAdapter(syncAdapter);
        syncAdapter.notifyDataSetChanged();
        sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
        username = sh_Pref.getString("Username", "");

        showProgressDialogue();
        isCalled = true;
        callDbToSetListItemsInArray();

        buttonSyncALL.setOnClickListener(this);
        buttonSyncData.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button_sync_all:
            case R.id.button_sync_data:
                if (isNetworkAvailable()) {
                    SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                    cursorForLoop = sqLiteDatabase.query(TABLENAME13, new String[]{COL_USERNAME, COL_URL, COL_JSON, COL_STATUS, COL_SESSION, COL_FILE1, COL_FILE2, COL_FILE3, COL_FILE4, "reference_key"}, COL_USERNAME + "=?", new String[]{username}, null, null, null);
                    if (cursorForLoop != null && cursorForLoop.getCount() > 0) {
                        showProgressDialogue();
                        isSubQuestionsAvailable = true;
                        callCursorForLoop();
                    } else {
                        isCalled = true;
                        progressDialog = new ProgressDialog(SyncActivity.this);
                        progressDialog.setMessage("Please wait...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        callDbToSetListItemsInArray();
                        Toast.makeText(getApplicationContext(), "No records pending to sync", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please check network connection", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void callCursorForLoop() {
        if (cursorForLoop.getCount() > 0) {
            if (!progressDialog.isShowing()) {
                showProgressDialogue();
            }
            cursorForLoop.moveToNext();
            if (cursorForLoop.isLast()) {
                isCalled = true;
                isSubQuestionsAvailable = false;
                Log.e("if", "yes");
                String status = cursorForLoop.getString(cursorForLoop.getColumnIndex(COL_STATUS));
                String json = cursorForLoop.getString(cursorForLoop.getColumnIndex(COL_JSON));
                sessionToPost = cursorForLoop.getString(cursorForLoop.getColumnIndex(COL_SESSION));
                finalFile5 = cursorForLoop.getString(cursorForLoop.getColumnIndex(COL_FILE1));
                finalFile6 = cursorForLoop.getString(cursorForLoop.getColumnIndex(COL_FILE2));
                finalFile7 = cursorForLoop.getString(cursorForLoop.getColumnIndex(COL_FILE3));
                finalFile8 = cursorForLoop.getString(cursorForLoop.getColumnIndex(COL_FILE4));
                reference_key = cursorForLoop.getString(cursorForLoop.getColumnIndex("reference_key"));
                try {
                    JSONObject jsonObject1 = new JSONObject(json);
                    new UploadFileToServer(jsonObject1, sessionToPost, json).execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                if (isSubQuestionsAvailable) {
                    Log.e("all-other", "yes");
                    String status = cursorForLoop.getString(cursorForLoop.getColumnIndex(COL_STATUS));
                    String json = cursorForLoop.getString(cursorForLoop.getColumnIndex(COL_JSON));
                    sessionToPost = cursorForLoop.getString(cursorForLoop.getColumnIndex(COL_SESSION));
                    finalFile5 = cursorForLoop.getString(cursorForLoop.getColumnIndex(COL_FILE1));
                    finalFile6 = cursorForLoop.getString(cursorForLoop.getColumnIndex(COL_FILE2));
                    finalFile7 = cursorForLoop.getString(cursorForLoop.getColumnIndex(COL_FILE3));
                    finalFile8 = cursorForLoop.getString(cursorForLoop.getColumnIndex(COL_FILE4));
                    reference_key = cursorForLoop.getString(cursorForLoop.getColumnIndex("reference_key"));
                    try {
                        JSONObject jsonObject1 = new JSONObject(json);
                        new UploadFileToServer(jsonObject1, sessionToPost, json).execute();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    callDbToSetListItemsInArray();
                    Toast.makeText(getApplicationContext(), "Records synced successfully", Toast.LENGTH_LONG).show();
//                    if (progressDialog.isShowing()) progressDialog.dismiss();
                    return;
                }
            }
        } else {
            callDbToSetListItemsInArray();
        }
    }

    private void callDbToSetListItemsInArray() {

        list.clear();
        sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
        cursor = sqLiteDatabase.query(TABLENAME1, new String[]{COL_USERNAME, COL_SESSION, COL_FACILITYNAME, COL_FACILITYTYPE, COL_DOV, COL_SYNC_STATUS}, COL_USERNAME + "=? AND " + COL_IS_SUBMITTED + "=? AND " + COL_SYNC_STATUS + "=?", new String[]{username, "1", "pending"}, null, null, null);
        Cursor cursorNew = sqLiteDatabase.query(TABLENAME1, new String[]{COL_USERNAME, COL_SESSION, COL_FACILITYNAME, COL_FACILITYTYPE, COL_DOV, COL_SYNC_STATUS}, COL_USERNAME + "=? AND " + COL_IS_SUBMITTED + "=? AND " + COL_SYNC_STATUS + "=?", new String[]{username, "1", "synced"}, null, null, null);

        cursor = new MergeCursor(new Cursor[]{cursor, cursorNew});

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                String syncstatus = cursor.getString(cursor.getColumnIndex(COL_SYNC_STATUS));
                String facilityname = cursor.getString(cursor.getColumnIndex(COL_FACILITYNAME));
                String facilitytype = cursor.getString(cursor.getColumnIndex(COL_FACILITYTYPE));
                String dov = cursor.getString(cursor.getColumnIndex(COL_DOV));
                if (syncstatus.matches("pending")) {
                    Cursor cursorPendingTable = sqLiteDatabase.query(TABLENAME13, new String[]{COL_SESSION}, COL_SESSION + "=? ", new String[]{cursor.getString(cursor.getColumnIndex(COL_SESSION))}, null, null, null);
                    SyncPojo syncPojo = null;
                    if (cursorPendingTable.getCount() > 0) {
                        syncPojo = new SyncPojo(facilityname, facilitytype, dov, R.drawable.notsynced, R.drawable.notsynced);
                    } else {
                        syncPojo = new SyncPojo(facilityname, facilitytype, dov, R.drawable.synced, R.drawable.synced);
                    }
                    sessionID = facilityname + "-" + facilitytype + "-" + dov;
                    list.add(syncPojo);
                } else {
                    SyncPojo syncPojo = new SyncPojo(facilityname, facilitytype, dov, R.drawable.synced, R.drawable.synced);
                    sessionID = facilityname + "-" + facilitytype + "-" + dov;
                    list.add(syncPojo);
                }

            } while (cursor.moveToNext());

        } else {

            textView.setVisibility(View.VISIBLE);
            textView.setText(getString(R.string.alert_no_records_found));
        }

        if (syncAdapter != null) syncAdapter.notifyDataSetChanged();

        if (progressDialog.isShowing() && isCalled) progressDialog.dismiss();

    }

    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        JSONObject jsonObject1;
        String json;
        ProgressBar progressBar = new ProgressBar(SyncActivity.this);

        UploadFileToServer(JSONObject jsonObject1, String s, String json) {
            this.jsonObject1 = jsonObject1;
            this.json = json;
        }

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
//                    Log.v("sourceFile1", "" + sourceFile1);
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

                // Adding file data to http body
                entity.addPart("username",
                        new StringBody(username));
                entity.addPart("visit_id", new StringBody(sessionToPost));
                for (int i = 0; i < list.size(); i++) {
                    if (!list.get(i).equals(null)) {
                        entity.addPart("image_data", new FileBody(list.get(i)));
                    }
                }

//                Log.d(TAG, "uploadFile: "+username+"--"+sessionToPost+"--"+list.get(0));

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
                    responseString = "Error occurred! Http Status Code: " + statusCode;
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
            super.onPostExecute(result);

            if (result.contains("{")) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    System.out.println("image_response: "+jsonObject.toString());
                    imageResponse = jsonObject.getString("msg");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            imageResponse = result;

            if (syncAdapter != null) syncAdapter.notifyDataSetChanged();
            postToServer(jsonObject1, sessionToPost, json);
        }

    }

    public void postToServer(JSONObject jsonObject, final String session, final String json) {
        final String url = Utils.URL_NEW + Utils.BOOKMARK_ANS_NEW;
        String tempQuestionCode = "";
        String[] strTemp = new String[0];
        int newValue = 0;
        try {
            for (int i = 0; i < jsonObject.getJSONArray("visit_data").length(); i++) {
                if (jsonObject.getJSONArray("visit_data").getJSONObject(i).getJSONArray("form_data").length() > 1) {
                    for (int j = 0; j < jsonObject.getJSONArray("visit_data").getJSONObject(i).getJSONArray("form_data").length(); j++) {
                        if (jsonObject.getJSONArray("visit_data").getJSONObject(i).getJSONArray("form_data").getJSONObject(j).get("q_code").equals("")) {
                            if (j == 0) {
                                tempQuestionCode = jsonObject.getJSONArray("visit_data").getJSONObject(i).getString("form_code") + "_q1";
                                jsonObject.getJSONArray("visit_data").getJSONObject(i).getJSONArray("form_data").getJSONObject(j).put("q_code", tempQuestionCode);
                            } else {
                                tempQuestionCode = jsonObject.getJSONArray("visit_data").getJSONObject(i).getJSONArray("form_data").getJSONObject(j - 1).getString("q_code");
                                strTemp = tempQuestionCode.split("_q");
                                newValue = Integer.parseInt(strTemp[1]) + 1;
                                jsonObject.getJSONArray("visit_data").getJSONObject(i).getJSONArray("form_data").getJSONObject(j).put("q_code", strTemp[0] + "_q" + newValue);
                            }
                        }
                    }
                } else {
                    String datePattern = "\\d{2}/\\d{2}/\\d{4}";
                    try {
                        if (jsonObject.getJSONArray("visit_data").getJSONObject(i).getJSONArray("form_data").length()<=0)
                            continue;
                        if (jsonObject.getJSONArray("visit_data").getJSONObject(i).getJSONArray("form_data").getJSONObject(0).getString("ans").matches(datePattern)) {
                            tempQuestionCode = jsonObject.getJSONArray("visit_data").getJSONObject(i).getString("form_code") + "_q5";
                        } else {
                            tempQuestionCode = jsonObject.getJSONArray("visit_data").getJSONObject(i).getString("form_code") + "_q6";
                        }
                        jsonObject.getJSONArray("visit_data").getJSONObject(i).getJSONArray("form_data").getJSONObject(0).put("q_code", tempQuestionCode);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                            }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//48218

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (progressDialog!=null && progressDialog.isShowing()) progressDialog.dismiss();
                String status = "";
                try {
                    status = response.getString("status");
                    Log.v("status sync", "" + status);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (status == null) {
                    status = String.valueOf(status);
                }
                if (imageResponse == null) {
                    imageResponse = String.valueOf(imageResponse);
                }
                if (status.matches("ok") /*|| imageResponse.matches("photos upload successfully")*/) {
                    jhpiegoDatabase.updateSyncStatus(session);
                    jhpiegoDatabase.updateWorkPlanStatus(reference_key, "1");
                    jhpiegoDatabase.deleteJsonToPost(session);
//                    if (progressDialog.isShowing()) progressDialog.dismiss();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                jhpiegoDatabase = new JhpiegoDatabase(SyncActivity.this);
                                sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                                if (sessionToPost != null) {
                                    cursorClinicalPractices = sqLiteDatabase.query(TABLE_ClinicalStandards, new String[]{COL_SESSION, JSON_DATA, COL_ANSJSON, COL_LATITUDE, COL_LONGITUDE}, COL_SESSION + "=? and " + COL_IS_SUBMITTED + " =? and " + COL_IS_ASSESSMENT + " =? and flag=?", new String[]{sessionToPost, "1", "1", "false"}, null, null, null);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
//                            if (cursorClinicalPractices != null && cursorClinicalPractices.getCount() > 0) {
//                                syncAllPendingRecordsToServer(cursorClinicalPractices);
//                            } else {
                                if (cursorForLoop != null) {
                                    callCursorForLoop();
                                } else {
                                    if (progressDialog!=null && progressDialog.isShowing()) progressDialog.dismiss();
                                }
//                            }
                        }
                    });

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (syncAdapter != null) syncAdapter.notifyDataSetChanged();
                if (!isNetworkAvailable()) {
                    Toast.makeText(getApplicationContext(), "Please check network connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Server error occurred", Toast.LENGTH_SHORT).show();
                }
                callCursorForLoop();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                180000,
                2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    private void syncAllPendingRecordsToServer(Cursor cursorClinicalPractices) {

        if (progressDialog!=null && !progressDialog.isShowing()) {
            showProgressDialogue();
        }

        if (cursorClinicalPractices != null && cursorClinicalPractices.getCount() > 0) {

            JSONObject jsonObject = new JSONObject();

            cursorClinicalPractices.moveToNext();
            Cursor cursor = sqLiteDatabase.query(TABLENAME1, new String[]{COL_SESSION, COL_USERNAME, COL_NOM, COL_ANSJSON, COL_DOV, CREATED_DATE, COL_LATITUDE, COL_LONGITUDE}, COL_SESSION + "=? ", new String[]{cursorClinicalPractices.getString(cursorClinicalPractices.getColumnIndex(COL_SESSION))}, null, null, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                try {
                    String mentor_name = cursor.getString(cursor.getColumnIndex(COL_NOM));
                    String visit_date = cursor.getString(cursor.getColumnIndex(COL_DOV));
                    col_session = cursor.getString(cursor.getColumnIndex(COL_SESSION));

                    String clinical_practices_json = cursorClinicalPractices.getString(cursorClinicalPractices.getColumnIndex(JSON_DATA));

                    // Create Json for vc data to post at server
                    JSONArray subQuesArrayJson = new JSONArray();
                    JSONObject cpMainJsonData = new JSONObject(clinical_practices_json);
                    JSONArray cpMainQuesArrayJson = cpMainJsonData.getJSONArray("ques_array");
                    // 1 - creating sub question json
                    for (int i = 0; i < cpMainQuesArrayJson.length(); i++) {
                        JSONArray tempJson = cpMainQuesArrayJson.getJSONObject(i).getJSONArray("sub_ques_array");
                        for (int j = 0; j < tempJson.length(); j++) {
                            JSONObject tempJsonObject = new JSONObject();
                            tempJsonObject.put("s_q_ans", tempJson.getJSONObject(j).getString("s_q_ans"));
                            tempJsonObject.put("s_q_code", tempJson.getJSONObject(j).getString("s_q_code"));
                            subQuesArrayJson.put(tempJsonObject);
                        }
                    }

                    JSONArray quesArrayJson = new JSONArray();
                    JSONObject temp = new JSONObject(cursor.getString(cursor.getColumnIndex(COL_ANSJSON)));
                    quesArrayJson.put(0, temp);
                    temp = new JSONObject(cursorClinicalPractices.getString(cursorClinicalPractices.getColumnIndex(COL_ANSJSON)));
                    quesArrayJson.put(1, temp);

                    JSONObject mainJson = new JSONObject();
                    JSONObject requestDataJson = new JSONObject();
                    JSONObject visitDataJson = new JSONObject();
                    visitDataJson.put("latitude", String.valueOf(cursor.getString(cursor.getColumnIndex(COL_LATITUDE))));
                    visitDataJson.put("longitude", String.valueOf(cursor.getString(cursor.getColumnIndex(COL_LONGITUDE))));
                    visitDataJson.put("address", "");
                    visitDataJson.put("ques_array", quesArrayJson);
                    visitDataJson.put("sub_ques_array", subQuesArrayJson);

                    requestDataJson.put("visit_data", visitDataJson);
                    requestDataJson.put("visit_id", col_session);
                    requestDataJson.put("created_date", MentorConstant.parseDate(cursor.getString(cursor.getColumnIndex(CREATED_DATE))) /*"2019-02-17 00:41:08"*/);

                    mainJson.put("request", "bookmark_answers");
                    mainJson.put("user", cursor.getString(cursor.getColumnIndex(COL_USERNAME)));
                    mainJson.put("requestData", requestDataJson);
                    jsonObject = new JSONObject(mainJson.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Utils.URL_New_For_Vc, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    String status = "";
                    try {
                        status = response.getString("success");
//                        Log.e("status vc", response+ " - " + status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status.equalsIgnoreCase("true")) {
                        jhpiegoDatabase.updateFlagOfClinicalPractices(col_session);
                    }
                    callCursorForLoop();

//                    if (progressDialog.isShowing()) progressDialog.dismiss();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (syncAdapter != null) syncAdapter.notifyDataSetChanged();
                    if (!isNetworkAvailable()) {
                        Toast.makeText(getApplicationContext(), "Please check network connection", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Server error occurred", Toast.LENGTH_SHORT).show();
                    }
                    callCursorForLoop();
                }
            }); /*{
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json; charset=utf-8");
                    return params;
                }
            };*/
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    180000,
                    2,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonObjectRequest);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void Pback(View view) {
        onBackPressed();
    }

    private void showProgressDialogue() {
        progressDialog = new ProgressDialog(SyncActivity.this);
        progressDialog.setMessage("Sending records to server, please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    // Create Pdf
    public void createPdf(String selectedSession, String fileName) {

        try {
            jhpiegoDatabase = new JhpiegoDatabase(SyncActivity.this);
            sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.fileName = fileName;
        SharedPreferences sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
        String username = sh_Pref.getString("Username", "Unknown");
        Cursor cursor = sqLiteDatabase.query(TABLENAME, new String[]{COL_USERNAME, COL_EMAIL, COL_MOBILE, COL_STATE, COL_NAME}, COL_USERNAME + "=?", new String[]{username}, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            mentorName = cursor.getString(cursor.getColumnIndex(COL_NAME));
            userEmail = cursor.getString(cursor.getColumnIndex(COL_EMAIL));
        }

        Cursor cursor1 = sqLiteDatabase.query(TABLENAME1, new String[]{COL_SESSION, COL_NOM, COL_STATE, COL_DIST, COL_BLOCK, COL_FACILITYNAME, COL_FACILITYTYPE, COL_LEVEL, COL_DOV, COL_IMAGE1, COL_IMAGE2, COL_IMAGE3, COL_IMAGE4, COL_LATITUDE, COL_LONGITUDE, CREATED_DATE, "workplan_status", "planned_date"}, COL_SESSION + "=?", new String[]{selectedSession}, null, null, null);
        if (cursor1 != null && cursor1.getCount() > 0) {
            cursor1.moveToFirst();
            table1_col1 = cursor1.getString(cursor1.getColumnIndex(COL_NOM));
            table1_col2 = cursor1.getString(cursor1.getColumnIndex(COL_STATE));
            table1_col3 = cursor1.getString(cursor1.getColumnIndex(COL_DIST));
            table1_col4 = cursor1.getString(cursor1.getColumnIndex(COL_BLOCK));
            table1_col5 = cursor1.getString(cursor1.getColumnIndex(COL_FACILITYNAME));
            table1_col6 = cursor1.getString(cursor1.getColumnIndex(COL_FACILITYTYPE));
            table1_col7 = cursor1.getString(cursor1.getColumnIndex(COL_LEVEL));
            table1_col8 = cursor1.getString(cursor1.getColumnIndex(COL_DOV));
            createdDate = cursor1.getString(cursor1.getColumnIndex(CREATED_DATE));
            table1_col_latitude = cursor1.getString(cursor1.getColumnIndex(COL_LATITUDE));
            table1_col_longitude = cursor1.getString(cursor1.getColumnIndex(COL_LONGITUDE));

            table1_colWorkPlanStatus = cursor1.getString(cursor1.getColumnIndex("workplan_status"));
            table1_colPlannedDate = cursor1.getString(cursor1.getColumnIndex("planned_date"));

            byte[] blob1 = cursor1.getBlob(cursor1.getColumnIndex(COL_IMAGE1));

            Bitmap bitmap1;
            if (blob1 != null)
                bitmap1 = BitmapFactory.decodeByteArray(blob1, 0, blob1.length);
        }

        try {
            Cursor cursorTotalCount = sqLiteDatabase.query(TABLENAME1, new String[]{COL_ID}, null, null, null, null, null);
            table1_colVisit = String.valueOf(cursorTotalCount.getCount());
        } catch (Exception e) {
        }

        Cursor cursor2 = sqLiteDatabase.query(TABLENAME7, new String[]{COL_SESSION, COL_OBG, COL_SBA, COL_MOLR, COL_MOD, COL_SNLR, COL_SND, COL_LHVSLR, COL_LHVSD, COL_LHVWORKING, COL_LHVTRAINED}, COL_SESSION + "=?", new String[]{selectedSession}, null, null, null);
        if (cursor2 != null && cursor2.getCount() > 0) {
            cursor2.moveToFirst();
            table2_col1 = cursor2.getString(cursor2.getColumnIndex(COL_OBG));
            table2_col2 = cursor2.getString(cursor2.getColumnIndex(COL_SBA));
            table2_col3 = cursor2.getString(cursor2.getColumnIndex(COL_MOLR));
            table2_col4 = cursor2.getString(cursor2.getColumnIndex(COL_MOD));
            table2_col5 = cursor2.getString(cursor2.getColumnIndex(COL_SNLR));
            table2_col6 = cursor2.getString(cursor2.getColumnIndex(COL_SND));
            table2_col7 = cursor2.getString(cursor2.getColumnIndex(COL_LHVSLR));
            table2_col8 = cursor2.getString(cursor2.getColumnIndex(COL_LHVSD));
            table2_col9 = cursor2.getString(cursor2.getColumnIndex(COL_LHVWORKING));
            table2_col10 = cursor2.getString(cursor2.getColumnIndex(COL_LHVTRAINED));
        }

        Cursor cursor3 = sqLiteDatabase.query(TABLENAME5, new String[]{COL_SESSION, COL_LRD, COL_PSC, COL_LCFW, COL_ASTNSEM, COL_WALLS, COL_AC, COL_AV, COL_FLSL, COL_PB, COL_LOF, COL_ASBT, COL_CNRNB, COL_CMDS, COL_RW, COL_SOB, COL_FWRW, COL_DUA, COL_FHWS, COL_EOT, COL_THREE_SIDE_SPACE}, COL_SESSION + "=?", new String[]{selectedSession}, null, null, null);
        if (cursor3 != null && cursor3.getCount() > 0) {
            cursor3.moveToFirst();
            table3_col2 = cursor3.getString(cursor3.getColumnIndex(COL_LRD));
            table3_col3 = cursor3.getString(cursor3.getColumnIndex(COL_PSC));
            table3_col4 = cursor3.getString(cursor3.getColumnIndex(COL_LCFW));
            table3_col5 = cursor3.getString(cursor3.getColumnIndex(COL_ASTNSEM));
            table3_col6 = cursor3.getString(cursor3.getColumnIndex(COL_WALLS));
            table3_col7 = cursor3.getString(cursor3.getColumnIndex(COL_AC));
            table3_col8 = cursor3.getString(cursor3.getColumnIndex(COL_AV));
            table3_col9 = cursor3.getString(cursor3.getColumnIndex(COL_FLSL));
            table3_col10 = cursor3.getString(cursor3.getColumnIndex(COL_PB));
            table3_col11 = cursor3.getString(cursor3.getColumnIndex(COL_LOF));
            table3_col12 = cursor3.getString(cursor3.getColumnIndex(COL_ASBT));
            table3_col13 = cursor3.getString(cursor3.getColumnIndex(COL_CNRNB));
            table3_col14 = cursor3.getString(cursor3.getColumnIndex(COL_CMDS));
            table3_col15 = cursor3.getString(cursor3.getColumnIndex(COL_RW));
            table3_col16 = cursor3.getString(cursor3.getColumnIndex(COL_SOB));
            table3_col17 = cursor3.getString(cursor3.getColumnIndex(COL_FWRW));
            table3_col18 = cursor3.getString(cursor3.getColumnIndex(COL_DUA));
            table3_col19 = cursor3.getString(cursor3.getColumnIndex(COL_FHWS));
            table3_col20 = cursor3.getString(cursor3.getColumnIndex(COL_EOT));
            table3_col21 = cursor3.getString(cursor3.getColumnIndex(COL_THREE_SIDE_SPACE));
        }

        Cursor cursor4 = sqLiteDatabase.query(TABLENAME8, new String[]{COL_SESSION, COL_1, COL_2, COL_3, COL_4, COL_5, COL_6, COL_7, COL_8, COL_9, COL_10, COL_11, COL_12, COL_13, COL_14, COL_15, COL_16, COL_17, COL_18, COL_19, COL_20, COL_21, COL_22, COL_23, COL_24, COL_25, COL_26}, COL_SESSION + "=?", new String[]{selectedSession}, null, null, null);
        if (cursor4 != null && cursor4.getCount() > 0) {
            cursor4.moveToFirst();
            table4_col1 = cursor4.getString(cursor4.getColumnIndex(COL_1));
            table4_col2 = cursor4.getString(cursor4.getColumnIndex(COL_2));
            table4_col3 = cursor4.getString(cursor4.getColumnIndex(COL_3));
            table4_col4 = cursor4.getString(cursor4.getColumnIndex(COL_4));
            table4_col5 = cursor4.getString(cursor4.getColumnIndex(COL_5));
            table4_col6 = cursor4.getString(cursor4.getColumnIndex(COL_6));
            table4_col7 = cursor4.getString(cursor4.getColumnIndex(COL_7));
            table4_col8 = cursor4.getString(cursor4.getColumnIndex(COL_8));
            table4_col9 = cursor4.getString(cursor4.getColumnIndex(COL_9));
            table4_col10 = cursor4.getString(cursor4.getColumnIndex(COL_10));
            table4_col11 = cursor4.getString(cursor4.getColumnIndex(COL_11));
            table4_col12 = cursor4.getString(cursor4.getColumnIndex(COL_12));
            table4_col13 = cursor4.getString(cursor4.getColumnIndex(COL_13));
            table4_col14 = cursor4.getString(cursor4.getColumnIndex(COL_14));
            table4_col15 = cursor4.getString(cursor4.getColumnIndex(COL_15));
            table4_col16 = cursor4.getString(cursor4.getColumnIndex(COL_16));
            table4_col17 = cursor4.getString(cursor4.getColumnIndex(COL_17));
            table4_col18 = cursor4.getString(cursor4.getColumnIndex(COL_18));
            table4_col19 = cursor4.getString(cursor4.getColumnIndex(COL_19));
            table4_col20 = cursor4.getString(cursor4.getColumnIndex(COL_20));
            table4_col21 = cursor4.getString(cursor4.getColumnIndex(COL_21));
            table4_col22 = cursor4.getString(cursor4.getColumnIndex(COL_22));
            table4_col23 = cursor4.getString(cursor4.getColumnIndex(COL_23));
            table4_col24 = cursor4.getString(cursor4.getColumnIndex(COL_24));
            table4_col25 = cursor4.getString(cursor4.getColumnIndex(COL_25));
            table4_col26 = cursor4.getString(cursor4.getColumnIndex(COL_26));
        }

        Cursor cursor5 = sqLiteDatabase.query(TABLE_ClinicalStandards, new String[]{COL_SESSION, JSON_DATA}, COL_SESSION + "=?", new String[]{selectedSession}, null, null, null);
        if (cursor5 != null && cursor5.getCount() > 0) {
            cursor5.moveToFirst();
            String json_data = cursor5.getString(cursor5.getColumnIndex(JSON_DATA));
            QuestListDto questListDto = new Gson().fromJson(json_data, QuestListDto.class);
            for (int i = 0; i < questListDto.getQues_array().size(); i++) {
                if (!questListDto.getQues_array().get(i).getQ_ans().equalsIgnoreCase("0")) {

                    switch (i + 1) {
                        case 1:
                            table5_col1 = questListDto.getQues_array().get(i).getQ_ans();
                            break;
                        case 2:
                            table5_col2 = questListDto.getQues_array().get(i).getQ_ans();
                            break;
                        case 3:
                            table5_col3 = questListDto.getQues_array().get(i).getQ_ans();
                            break;
                        case 4:
                            table5_col4 = questListDto.getQues_array().get(i).getQ_ans();
                            break;
                        case 5:
                            table5_col5 = questListDto.getQues_array().get(i).getQ_ans();
                            break;
                        case 6:
                            table5_col6 = questListDto.getQues_array().get(i).getQ_ans();
                            break;
                        case 7:
                            table5_col7 = questListDto.getQues_array().get(i).getQ_ans();
                            break;
                        case 8:
                            table5_col8 = questListDto.getQues_array().get(i).getQ_ans();
                            break;
                        case 9:
                            table5_col9 = questListDto.getQues_array().get(i).getQ_ans();
                            break;
                        case 10:
                            table5_col10 = questListDto.getQues_array().get(i).getQ_ans();
                            break;
                        case 11:
                            table5_col11 = questListDto.getQues_array().get(i).getQ_ans();
                            break;
                        case 12:
                            table5_col12 = questListDto.getQues_array().get(i).getQ_ans();
                            break;
                        case 13:
                            table5_col13 = questListDto.getQues_array().get(i).getQ_ans();
                            break;
                        case 14:
                            table5_col14 = questListDto.getQues_array().get(i).getQ_ans();
                            break;
                        case 15:
                            table5_col15 = questListDto.getQues_array().get(i).getQ_ans();
                            break;
                        case 16:
                            table5_col16 = questListDto.getQues_array().get(i).getQ_ans();
                            break;
                        case 17:
                            table5_col17 = questListDto.getQues_array().get(i).getQ_ans();
                            break;
                        case 18:
                            table5_col18 = questListDto.getQues_array().get(i).getQ_ans();
                            break;
                        case 19:
                            table5_col19 = questListDto.getQues_array().get(i).getQ_ans();
                            break;
                    }
                }
            }
        }

        Cursor cursor6 = sqLiteDatabase.query(TABLENAME6, new String[]{COL_SESSION, COL_ROLR, COL_LP, COL_GA, COL_PV, COL_AUA, COL_ACP, COL_CND, COL_AMTSL, COL_ENBC, COL_NR, COL_SCN, COL_PPH, COL_IMPE, COL_IMPO, COL_PP1, COL_DND, COL_DPPH, COL_DPE, COL_DNR}, COL_SESSION + "=?", new String[]{selectedSession}, null, null, null);
        if (cursor6 != null && cursor6.getCount() > 0) {
            cursor6.moveToFirst();
            table6_col1 = cursor6.getString(cursor6.getColumnIndex(COL_ROLR));
            table6_col2 = cursor6.getString(cursor6.getColumnIndex(COL_LP));
            table6_col3 = cursor6.getString(cursor6.getColumnIndex(COL_GA));
            table6_col4 = cursor6.getString(cursor6.getColumnIndex(COL_PV));
            table6_col5 = cursor6.getString(cursor6.getColumnIndex(COL_AUA));
            table6_col6 = cursor6.getString(cursor6.getColumnIndex(COL_ACP));
            table6_col7 = cursor6.getString(cursor6.getColumnIndex(COL_CND));
            table6_col8 = cursor6.getString(cursor6.getColumnIndex(COL_AMTSL));
            table6_col9 = cursor6.getString(cursor6.getColumnIndex(COL_ENBC));
            table6_col10 = cursor6.getString(cursor6.getColumnIndex(COL_NR));
            table6_col11 = cursor6.getString(cursor6.getColumnIndex(COL_SCN));
            table6_col12 = cursor6.getString(cursor6.getColumnIndex(COL_PPH));
            table6_col13 = cursor6.getString(cursor6.getColumnIndex(COL_IMPE));
            table6_col14 = cursor6.getString(cursor6.getColumnIndex(COL_IMPO));
            table6_col15 = cursor6.getString(cursor6.getColumnIndex(COL_PP1));
            table6_col16 = cursor6.getString(cursor6.getColumnIndex(COL_DND));
            table6_col17 = cursor6.getString(cursor6.getColumnIndex(COL_DPPH));
            table6_col18 = cursor6.getString(cursor6.getColumnIndex(COL_DPE));
            table6_col19 = cursor6.getString(cursor6.getColumnIndex(COL_DNR));
        }
        Cursor cursor7 = sqLiteDatabase.query(TABLENAME9, new String[]{COL_SESSION, COL_1, COL_2, COL_3, COL_4}, COL_SESSION + "=?", new String[]{selectedSession}, null, null, null);
        if (cursor7 != null && cursor7.getCount() > 0) {
            cursor7.moveToFirst();
            table7_col1 = cursor7.getString(cursor7.getColumnIndex(COL_1));
            table7_col2 = cursor7.getString(cursor7.getColumnIndex(COL_2));
            table7_col3 = cursor7.getString(cursor7.getColumnIndex(COL_3));
            table7_col4 = cursor7.getString(cursor7.getColumnIndex(COL_4));
        }

        Cursor cursor8 = sqLiteDatabase.query(TABLENAME10, new String[]{COL_SESSION, COL_1}, COL_SESSION + "=?", new String[]{selectedSession}, null, null, null);
        if (cursor8 != null && cursor8.getCount() > 0) {
            cursor8.moveToFirst();
            table8_col1 = cursor8.getString(cursor8.getColumnIndex(COL_1));
        }
        Cursor cursor9 = sqLiteDatabase.query(TABLENAME11, new String[]{COL_SESSION, COL_1}, COL_SESSION + "=?", new String[]{selectedSession}, null, null, null);
        if (cursor9 != null && cursor9.getCount() > 0) {
            cursor9.moveToFirst();
            table9_col1 = cursor9.getString(cursor9.getColumnIndex(COL_1));
        }

        try {
            new FirstPdf(SyncActivity.this, fileName, MentorConstant.getAddress(SyncActivity.this, Double.parseDouble(table1_col_latitude), Double.parseDouble(table1_col_longitude)));
        } catch (Exception e) {
            new FirstPdf(SyncActivity.this, fileName, " Latitude : " + table1_col_latitude + ",   Longitude : " + table1_col_longitude);
        }

    }


}
