package com.dakshata.mentor;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dakshata.mentor.Utils.Utils;
import com.dakshata.mentor.models.QuetionsMaster;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.gson.Gson;

import org.aviran.cookiebar2.CookieBar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME12;


/**
 * Created by Aditya.v on 15-12-2017.
 */

public class JhpiegoLogin extends AppCompatActivity {
    Button jhpiego_login;
    EditText jhpiego_username, jhpiego_password;
    TextView textView_forgotpassword, textView_register;
    JhpiegoDatabase jhpiegoDatabase;
    ProgressDialog progressDialog;
    SharedPreferences sh_Pref;
    SharedPreferences.Editor editor;
    private static final String IS_LOGIN = "IsLoggedIn";
    TextView textView_fp;

    ProgressDialog dl;
    SQLiteDatabase sqLiteDatabaseLoc;
    SQLiteDatabase sqLiteDatabase;


    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    // Remote Config keys
    private static final String VERSION_CODE_KEY = "Version_Code";
    private static final String VERSION_NAME_KEY = "Version_Name";
    private static final String VERSION_TYPE_KEY = "Version_Type";
    private static final String VERSION_MESSAGE_KEY = "Version_Message";
    private static final String LOWER_VERSION_KEY = "lower_version";
    private static final String STATE_CODE_KEY = "state_code";
    private static final String DISTRICT_CODE_KEY = "district_code";
    private static final String BLOCK_CODE_KEY = "block_code";
    private static final String NOTIFICATION_MESSAGE_KEY = "notification_message";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_activity);

        jhpiegoDatabase = new JhpiegoDatabase(this);

        progressDialog = new ProgressDialog(this);

        intiViews();

        jhpiego_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = jhpiego_username.getText().toString();
                final String password = jhpiego_password.getText().toString();
                if (!isNetworkAvailable()) {
                    Toast.makeText(getApplicationContext(), "Please check network connection", Toast.LENGTH_SHORT).show();
                } else if (username.matches("")) {
                    jhpiego_username.setError("Field should not be empty");
                } else if (password.matches("")) {
                    jhpiego_username.setError("Field should not be empty");
                } else {
                    progressDialog.setMessage("Validating credentials...");
                    progressDialog.show();
                    serverPostLogin(username, password);
                }

            }
        });

        textView_forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(JhpiegoLogin.this).inflate(R.layout.forgot_password_activity, null);
                AlertDialog alertDialog = new AlertDialog.Builder(JhpiegoLogin.this).create();
                alertDialog.setView(view);
                Button submit = view.findViewById(R.id.btn_fpa);
                textView_fp = view.findViewById(R.id.tv_fp);
                textView_fp.setVisibility(View.GONE);
                final EditText editText = view.findViewById(R.id.et_fpa);

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String fpaUsername = editText.getText().toString();
                        if (!isNetworkAvailable()) {
                            Toast.makeText(getApplicationContext(), "Please check network connection", Toast.LENGTH_SHORT).show();
                        } else if (fpaUsername.matches("")) {
                        } else {
                            progressDialog.show();
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("username", fpaUsername);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            serverPostFP(jsonObject);
                        }
                    }
                });
                alertDialog.show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
        boolean check = sh_Pref.getBoolean(IS_LOGIN, false);
        if (check) {
            if (jhpiegoDatabase.getLocationMasterCount() == 0) {
                Toast.makeText(getApplicationContext(), "Failed to load location master. Try login again.", Toast.LENGTH_SHORT).show();
            } else {
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Checking update...");
                progressDialog.show();
                jhpiego_username.setText(sh_Pref.getString("Username", ""));
                jhpiego_password.setText(sh_Pref.getString("Password", ""));

                mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
                FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                        .setDeveloperModeEnabled(true)
                        .build();
                mFirebaseRemoteConfig.setConfigSettings(configSettings);
                mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
                fetchFireBaseData(true);
            }
        }
    }

    public void intiViews() {
        jhpiego_login = (Button) findViewById(R.id.button_jhpiego_login);
        jhpiego_username = (EditText) findViewById(R.id.survey_username);
        jhpiego_password = (EditText) findViewById(R.id.survey_password);
        textView_forgotpassword = (TextView) findViewById(R.id.tv_forgotpassword);
        TextView version_name = (TextView) findViewById(R.id.version_name);

        String version = "";
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = "App Version: " + pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            version = "";
        }
        version_name.setText(version);

    }

    private void userLogin() {
        String username = jhpiego_username.getText().toString();
        String password = jhpiego_password.getText().toString();
        if (username.matches("")) {
            jhpiego_username.setError("Field should not be empty");
        } else if (password.matches("")) {
            jhpiego_username.setError("Field should not be empty");
        } else {
            progressDialog.setMessage("Validating Credentials");
            progressDialog.show();
            String savedPassword = jhpiegoDatabase.userLogin(username);
            if (password.equals(savedPassword)) {

                sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
                editor = sh_Pref.edit();
                editor.putBoolean(IS_LOGIN, true);
                editor.putString("Username", username);
                editor.putString("Password", password);
                editor.commit();

                Intent intent = new Intent(JhpiegoLogin.this, MainActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                progressDialog.cancel();
            } else {
                CookieBar.Build(JhpiegoLogin.this).setBackgroundColor(R.color.primaryColor).setMessage("Username or Password wrong").show();
                progressDialog.cancel();
            }
        }
    }

    public void Pback(View view) {
        super.onBackPressed();

    }

    public void serverPostLogin(final String username, final String password) {

        String url = Utils.URL_NEW + Utils.WORKER_LOGIN_NEW;

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            //  jsonObject.put("Content-Type", "application/json; charset=utf-8");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Log.v("jsonObject", "" + jsonObject);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ArrayList<String> blocks = new ArrayList<>();

                Log.v("response", "" + response);
                String msg = "", getUsername = "", getState = "", getDistrict = "", getBlockName = "", getEmail = "", getMobile = "", getName = "", getRole = "";
                try {
                    msg = response.getString("msg");
                    // getUsername=response.getString("worker");
                    JSONObject jsonObject1 = response.getJSONObject("worker");
                       /* for (int i=0;i<jsonObject1.length();i++){*/
                    getUsername = jsonObject1.getString("username").trim();
                    getState = jsonObject1.getString("state").trim();
                    getBlockName = jsonObject1.getString("block").trim();
                    getEmail = jsonObject1.getString("email").trim();
                    getMobile = jsonObject1.getString("mobile_number").trim();
                    getName = jsonObject1.getString("name").trim();
                    getRole = jsonObject1.getString("role").trim();
                    try {
                        getDistrict = jsonObject1.getString("district").trim();
                    } catch (Exception e) {
                    }
                    //String getblocks=jsonObject1.getString("name");
                    try {
                        JSONArray jsonObjectBlock = jsonObject1.getJSONArray("block_list");
                        Log.v("jsonObjectBlock", "" + jsonObjectBlock);
                        for (int j = 0; j < jsonObjectBlock.length(); j++) {
                            JSONObject jsonObjectBlocks = jsonObjectBlock.getJSONObject(j);
                            String getblocks = jsonObjectBlocks.getString("name").trim();
                            Log.v("getblocks", "" + getblocks);
                            blocks.add(getblocks);
                        }
                    } catch (Exception e) {
                    }
                    Log.v("username", "" + getUsername);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (msg.matches("username wrong") || msg.matches("password wrong")) {
                    Toast.makeText(getApplicationContext(), "Username or Password wrong", Toast.LENGTH_SHORT).show();

                } else {
                    sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
                    editor = sh_Pref.edit();
                    editor.putBoolean(IS_LOGIN, true);
                    editor.putString("Username", username);
                    editor.putString("Password", password);
                    editor.putString("state", getState);
                    editor.putString("district", getDistrict);
                    editor.putString("selectedblock", getBlockName);
                    editor.putString("mobile", getMobile);
                    editor.putString("email", getEmail);
                    editor.putString("name", getName);
                    editor.putString("role", getRole);
                    editor.putString("block", blocks.toString().replace("[", "").replace("]", ""));
                    editor.commit();
                    boolean check = jhpiegoDatabase.checkUserExists(getUsername);
                    long row;
                    if (!check) {
                        row = jhpiegoDatabase.addUser(username, getName, getEmail, getBlockName, getMobile, getDistrict, getState, getRole);
                        // Toast.makeText(getApplicationContext(),"User saved",Toast.LENGTH_SHORT).show();

                    } else {
                        row = jhpiegoDatabase.updateUser(username, getName, getEmail, getBlockName, getMobile, getDistrict, getState, getRole);
                        // Toast.makeText(getApplicationContext(),"User update saved",Toast.LENGTH_SHORT).show();

                    }
                    if (row != -1) {
                        //Toast.makeText(getApplicationContext(),"User saved",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "User not saved", Toast.LENGTH_SHORT).show();
                    }

                    if (jhpiegoDatabase.getLocationMasterCount() == 0) {
                        getLocationMaster(username);
                    }
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("error", "" + error);
                Toast.makeText(getApplicationContext(), "Server error occurred", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            //adding parameters to the request
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);

    }


    private void servergetQuestions() {
        String url = Utils.URL_NEW + Utils.GETALL_QUESTIONS_NEW;

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("response", "" + response);
                Gson gson = new Gson();
                DetailsOfVisit.mQuetionsMaster = gson.fromJson(response.toString(), QuetionsMaster.class);
                ClinicalStandards.mQuetionsMaster = gson.fromJson(response.toString(), QuetionsMaster.class);
                CommentsRemarks.mQuetionsMaster = gson.fromJson(response.toString(), QuetionsMaster.class);
                DrugsSupply.mQuetionsMaster = gson.fromJson(response.toString(), QuetionsMaster.class);
                Infrastructure.mQuetionsMaster = gson.fromJson(response.toString(), QuetionsMaster.class);
                MentoringPractices.mQuetionsMaster = gson.fromJson(response.toString(), QuetionsMaster.class);
                NextVisitDate.mQuetionsMaster = gson.fromJson(response.toString(), QuetionsMaster.class);
                RecprdingRoporting.mQuetionsMaster = gson.fromJson(response.toString(), QuetionsMaster.class);
                StaffMaternity.mQuetionsMaster = gson.fromJson(response.toString(), QuetionsMaster.class);

                long row = jhpiegoDatabase.saveServerQuestions(response.toString());
                if (row != -1) {

                } else {
                    Toast.makeText(getApplicationContext(), "Question Master not saved", Toast.LENGTH_SHORT).show();
                }

                Log.v("size", "" + DetailsOfVisit.mQuetionsMaster.getF1().size());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("error", "" + error);
                if (!isNetworkAvailable()) {
                    Toast.makeText(getApplicationContext(), "Please check network connection", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Server error occurred", Toast.LENGTH_SHORT).show();
                }


            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonObjectRequest);
    }

    public void serverPostFP(JSONObject forgotpassword) {

        String url = Utils.URL_NEW + Utils.FORGOT_PASSWORD_NEW;

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, forgotpassword, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("response", "" + response);
                String msg = "", fp_password = "";
                try {
                    msg = response.getString("msg");
                    fp_password = response.getString("password");

                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (msg.matches("user doesn't exist")) {
                    Toast.makeText(getApplicationContext(), "User doesn't exist", Toast.LENGTH_SHORT).show();

                } else {

                    textView_fp.setVisibility(View.VISIBLE);
                    textView_fp.setText("Your password: " + fp_password);
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("error", "" + error);
                Toast.makeText(getApplicationContext(), "Server error occurred", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();


            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonObjectRequest);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getLocationMaster(final String username) {
        sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
        new AsyncTask<Void, Void, Void>() {
            ProgressDialog dl;
            JSONArray jsonArray;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dl = new ProgressDialog(JhpiegoLogin.this);
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

                if (dl != null && dl.isShowing()) {
                    dl.dismiss();
                }
                jhpiegoDatabase = new JhpiegoDatabase(JhpiegoLogin.this);
                sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                if (jhpiegoDatabase.getLocationMasterCount() == 0) {
                    Toast.makeText(getApplicationContext(), "Failed to load location master. Try login again.", Toast.LENGTH_SHORT).show();
                } else {
                    callHomeIntent();
                }
            }

        }.execute();
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


    /**
     * Fetch a welcome message from the Remote Config service, and then activate it.
     */
    private void fetchFireBaseData(final boolean shouldShowPartialUpdateDialog) {

//        long cacheExpiration = 3600;
//        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
        int cacheExpiration = 0;
//        }

        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mFirebaseRemoteConfig.activateFetched();
                        } else {
//                            Toast.makeText(MainActivity.this, "Fetch Failed", Toast.LENGTH_SHORT).show();
                        }
                        displayNotificationMessage(shouldShowPartialUpdateDialog);
                    }
                });
    }

    /**
     * Display a welcome message in all caps if welcome_message_caps is set to true. Otherwise,
     * display a welcome message as fetched from welcome_message.
     */
    private void displayNotificationMessage(boolean shouldShowPartialUpdateDialog) {
        Log.d("fire Version_Code = ", mFirebaseRemoteConfig.getString(VERSION_CODE_KEY));
        Log.d("fire Version_Name = ", mFirebaseRemoteConfig.getString(VERSION_NAME_KEY));
        Log.d("fire Version_Type = ", mFirebaseRemoteConfig.getString(VERSION_TYPE_KEY));
        Log.d("fire Version_Message = ", mFirebaseRemoteConfig.getString(VERSION_MESSAGE_KEY));
        Log.d("fire LOWER_VERSION = ", mFirebaseRemoteConfig.getString(LOWER_VERSION_KEY));
        Log.d("fire STATE_CODE = ", mFirebaseRemoteConfig.getString(STATE_CODE_KEY));
        Log.d("fire DISTRICT_CODE = ", mFirebaseRemoteConfig.getString(DISTRICT_CODE_KEY));
        Log.d("fire BLOCK_CODE = ", mFirebaseRemoteConfig.getString(BLOCK_CODE_KEY));
        Log.d("fire NOTIFICATION = ", mFirebaseRemoteConfig.getString(NOTIFICATION_MESSAGE_KEY));

        try {
            int versionCode = 0;
            int lowerVersionCode = 0;
            int currentVersion = getApplicationVersionCode();
            if ((mFirebaseRemoteConfig.getString(VERSION_CODE_KEY)) != null && (mFirebaseRemoteConfig.getString(LOWER_VERSION_KEY)) != null) {
                versionCode = Integer.parseInt(mFirebaseRemoteConfig.getString(VERSION_CODE_KEY));
                lowerVersionCode = Integer.parseInt(mFirebaseRemoteConfig.getString(LOWER_VERSION_KEY));

                if ((mFirebaseRemoteConfig.getString(VERSION_MESSAGE_KEY)) != null || (mFirebaseRemoteConfig.getString(NOTIFICATION_MESSAGE_KEY)) != null) {
                    if (versionCode != currentVersion) {
                        Log.d("force update condi=", String.valueOf(lowerVersionCode >= currentVersion));
                        if (lowerVersionCode >= currentVersion) {
                            if ((mFirebaseRemoteConfig.getString(NOTIFICATION_MESSAGE_KEY)).length() > 0) {
                                showForceUpdateDialog(mFirebaseRemoteConfig.getString(NOTIFICATION_MESSAGE_KEY));
                            } else {
                                callHomeIntent();
                            }
                        } else {
                            if ((mFirebaseRemoteConfig.getString(VERSION_MESSAGE_KEY)).length() > 0 && shouldShowPartialUpdateDialog) {
                                showPartialUpdateDialog(mFirebaseRemoteConfig.getString(VERSION_MESSAGE_KEY));
                            } else {
                                callHomeIntent();
                            }
                        }
                    } else {
                        callHomeIntent();
                    }
                }
            } else {
                callHomeIntent();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void callHomeIntent() {
        servergetQuestions();
        Intent intent = new Intent(JhpiegoLogin.this, MainActivity.class);
        startActivity(intent);
        if (progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
        finish();
    }

    public void showForceUpdateDialog(String message) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.alert));
        builder.setMessage(message);
        builder.setPositiveButton(getResources().getString(R.string.update), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.dakshata.mentor&hl=en")));
            }
        });
        builder.setCancelable(false);
        builder.show();
        return;
    }

    public void showPartialUpdateDialog(String message) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.alert));
        builder.setMessage(message);
        builder.setPositiveButton(getResources().getString(R.string.update), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.dakshata.mentor&hl=en")));
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.show();
        return;
    }

    public int getApplicationVersionCode() {
        Context mContext = getApplicationContext();
        try {
            String pkg = mContext.getPackageName();
            return mContext.getPackageManager().getPackageInfo(pkg, 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 27;
    }

}
