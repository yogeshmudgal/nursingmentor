package com.dakshata.mentor;

import android.Manifest;
import android.app.AlertDialog;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.dakshata.mentor.Utils.MentorConstant;
import com.dakshata.mentor.Utils.Utils;
import com.dakshata.mentor.models.AnswersModel;
import com.dakshata.mentor.models.ClientFeedbackDto;
import com.dakshata.mentor.models.CompetencyTrackingDto;
import com.dakshata.mentor.models.CompetencyTrackingParent;
import com.dakshata.mentor.models.FormDatum;
import com.dakshata.mentor.models.QuestListDto;
import com.dakshata.mentor.models.VisitDatum;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.dakshata.mentor.JhpiegoDatabase.*;

/**
 * Created by Umesh Kumar on 4/9/2018.
 */
public class DraftActivity extends AppCompatActivity implements LocationListener {


    ListView listView;
    JhpiegoDatabase jhpiegoDatabase;
    List<ListPojo> list;
    TextView textView;
    Cursor cursor;
    Toolbar toolbar;
    SQLiteDatabase sqLiteDatabase;
    String sessionID;
    ImageView back;
    String selectedSession;
    TextView textViewHeaderName;
    DraftAdapter draftAdapter;
    RecyclerView recyclerView;

    // Location Access
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    LocationManager locationManager;
    String provider, address;
    private double latitude;
    private double longitude;
    Location location;
    private int loop = 0;

    String sessionid, reference_key = "0";
    public static String username = "";
    String fullJson;
    Bitmap bitmap1, bitmap2, bitmap3, bitmap4;
    File finalFile1, finalFile2, finalFile3, finalFile4;
    String finalFile5, finalFile6, finalFile7, finalFile8;
    SharedPreferences sh_Pref, sharedPreferencescount;
    SharedPreferences.Editor editor, editorcount;
    ProgressDialog progressDialog;
    private String selectedSessionForPdf = "";
    private String fileNameForPdf = "";


    // For Pdf Creation
    public static String mentorName, userEmail, lastVisitDate;
    public static String table1_col1, table1_col2, table1_col3, table1_col4, table1_col5, table1_col6, table1_col7, table1_col8, table1_col_latitude, table1_col_longitude, table1_colVisit, table1_colWorkPlanStatus, table1_colPlannedDate;
    public static String table2_col1, table2_col2, table2_col3, table2_col4, table2_col5, table2_col6, table2_col7, table2_col8, table2_col9, table2_col10,table2_col11,table2_col12,table2_col13,table2_col14,table2_col15,table2_col16,table2_col17,table2_col18,table2_col19;
    public static String table3_col21, table3_col2, table3_col3, table3_col4, table3_col5, table3_col6, table3_col7, table3_col8, table3_col9, table3_col10, table3_col11, table3_col12, table3_col13, table3_col14, table3_col15, table3_col16, table3_col17, table3_col18, table3_col19, table3_col20;
    public static String table4_col1, table4_col2, table4_col3, table4_col4, table4_col5, table4_col6, table4_col7, table4_col8, table4_col9, table4_col10, table4_col11, table4_col12, table4_col13, table4_col14, table4_col15, table4_col16, table4_col17, table4_col18, table4_col19, table4_col20, table4_col21, table4_col22, table4_col23, table4_col24, table4_col25, table4_col26;
    public static String table5_col1, table5_col2, table5_col3, table5_col4, table5_col5, table5_col6, table5_col7, table5_col8, table5_col9, table5_col10, table5_col11, table5_col12, table5_col13, table5_col14, table5_col15, table5_col16, table5_col17, table5_col18, table5_col19;
    public static String table6_col1, table6_col2, table6_col3, table6_col4, table6_col5, table6_col6, table6_col7, table6_col8, table6_col9, table6_col10, table6_col11, table6_col12, table6_col13, table6_col14, table6_col15, table6_col16, table6_col17, table6_col18, table6_col19;
    public static String table7_col1, table7_col2, table7_col3, table7_col4;
    public static String table8_col1;
    public static String table9_col1;
    public static String createdDate;
    public static String fileName;

    //LayoutAnimationController layoutAnimationController;
    // int resId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assessment_activity);
        MentorConstant.recordFromDraft = 1;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.menu_image_opt3));
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(DraftActivity.this);
        progressDialog.setMessage("Please wait...");

        textViewHeaderName = (TextView) findViewById(R.id.header_name);
        textViewHeaderName.setText("Review/Edit Data");
        // resId=R.anim.slide_in_from_bottom;
        //  listView= (ListView) findViewById(R.id.history_view);
        // layoutAnimationController= AnimationUtils.loadLayoutAnimation(this,android.R.anim.slide_in_left);
        recyclerView = (RecyclerView) findViewById(R.id.history_recyler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // recyclerView.setLayoutAnimation(layoutAnimationController);

        jhpiegoDatabase = new JhpiegoDatabase(this);
        textView = (TextView) findViewById(R.id.records);
        textView.setVisibility(View.GONE);
        list = new ArrayList<>();
        draftAdapter = new DraftAdapter(this, list);
        recyclerView.setAdapter(draftAdapter);
        draftAdapter.notifyDataSetChanged();
        sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
        username = sh_Pref.getString("Username", "");

        populateListing();
    }

    private void populateListing() {
        list.clear();
        sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
        cursor = sqLiteDatabase.query(TABLENAME1, new String[]{COL_ID, COL_USERNAME, COL_SESSION, COL_FACILITYNAME, COL_FACILITYTYPE, COL_DOV}, COL_USERNAME + "= ? AND " + COL_IS_SUBMITTED + " = ? AND " + COL_IS_ASSESSMENT + " = ? ", new String[]{username, "0", "1"}, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                //sessionID=cursor.getString(cursor.getColumnIndex(COL_SESSION));
                int recordId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COL_ID)));
                String facilityname = cursor.getString(cursor.getColumnIndex(COL_FACILITYNAME));
                String facilitytype = cursor.getString(cursor.getColumnIndex(COL_FACILITYTYPE));
                String dov = cursor.getString(cursor.getColumnIndex(COL_DOV));
                ListPojo listPojo = new ListPojo(facilityname, facilitytype, dov, cursor.getString(cursor.getColumnIndex(COL_SESSION)), recordId);
                sessionID = facilityname + "-" + facilitytype + "-" + dov;
                list.add(listPojo);
            } while (cursor.moveToNext());

            if (draftAdapter != null) draftAdapter.notifyDataSetChanged();
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText("No records found");
            if (draftAdapter != null) draftAdapter.notifyDataSetChanged();
        }
    }

    public void Pback(View view) {
        super.onBackPressed();
    }

    public void alertHistory(final String selectedSession, String facilityName, String facilityType, int recordId, final String sessionNew) {
        View view1;
        view1 = LayoutInflater.from(DraftActivity.this).inflate(R.layout.alert_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(DraftActivity.this).create();
        alertDialog.setView(view1);
        Button button = view1.findViewById(R.id.ad_cancel);
        CardView cardView1 = view1.findViewById(R.id.ad_card1);
        CardView cardView2 = view1.findViewById(R.id.ad_card2);
        CardView cardView3 = view1.findViewById(R.id.ad_card3);
        CardView cardView4 = view1.findViewById(R.id.ad_card4);
        CardView cardView5 = view1.findViewById(R.id.ad_card5);
        CardView cardView6 = view1.findViewById(R.id.ad_card6);
        CardView cardView7 = view1.findViewById(R.id.ad_card7);
        CardView cardView8 = view1.findViewById(R.id.ad_card8);
        CardView cardView9 = view1.findViewById(R.id.ad_card9);
        CardView cardView10 = view1.findViewById(R.id.ad_card10);
        TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10;
        textView1 = view1.findViewById(R.id.ad_tv1);
        textView2 = view1.findViewById(R.id.ad_tv2);
        textView3 = view1.findViewById(R.id.ad_tv3);
        textView4 = view1.findViewById(R.id.ad_tv4);
        textView5 = view1.findViewById(R.id.ad_tv5);
        textView6 = view1.findViewById(R.id.ad_tv6);
        textView7 = view1.findViewById(R.id.ad_tv7);
        textView8 = view1.findViewById(R.id.ad_tv8);
        textView9 = view1.findViewById(R.id.ad_tv9);
        textView10 = view1.findViewById(R.id.ad_tv10);

        sessionID = selectedSession;

        final String selectedFacilityName = facilityName;
        final String selectedFacilityType = facilityType;
//        selectedSession = facility + "-" + facilitytype + "-" + dov;

        try {
            cursor = sqLiteDatabase.query(TABLENAME1, new String[]{COL_SESSION}, COL_SESSION + "=?", new String[]{sessionNew}, null, null, null);
            if (cursor.getCount() > 0) {
                textView1.setTextColor(Color.BLACK);
                cardView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MentorConstant.whichBlockCalled = true;
                        Intent intent = new Intent(getApplicationContext(), DetailsOfVisit.class);
                        intent.putExtra("session", selectedSession);
                        intent.putExtra("facilityName", selectedFacilityName);
                        intent.putExtra("facilityType", selectedFacilityType);
                        intent.putExtra("sessionNew", sessionNew);
                        startActivity(intent);
                        alertDialog.cancel();
                    }
                });
            } else {
                cardView1.setCardElevation(0);
                cardView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "No records found", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {
        }

        try {
            String query = "select * from " + TABLENAME7;
            cursor = sqLiteDatabase.query(TABLENAME7, new String[]{COL_SESSION}, COL_SESSION + "=?", new String[]{sessionNew}, null, null, null);
            if (cursor.getCount() >= 0) {
                textView2.setTextColor(Color.BLACK);
                cardView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MentorConstant.whichBlockCalled = true;
                        Intent intent = new Intent(getApplicationContext(), StaffMaternity.class);
                        intent.putExtra("session", selectedSession);
                        intent.putExtra("facilityName", selectedFacilityName);
                        intent.putExtra("facilityType", selectedFacilityType);
                        intent.putExtra("sessionNew", sessionNew);
                        startActivity(intent);
                        alertDialog.cancel();
                    }
                });
            } else {
                cardView2.setCardElevation(0);
                cardView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "No records found", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {
        }

        try {
            String query = "select * from " + TABLENAME5;
            cursor = sqLiteDatabase.query(TABLENAME5, new String[]{COL_SESSION}, COL_SESSION + "=?", new String[]{sessionNew}, null, null, null);
            if (cursor.getCount() >= 0) {
                textView3.setTextColor(Color.BLACK);
                cardView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MentorConstant.whichBlockCalled = true;
                        Intent intent = new Intent(getApplicationContext(), Infrastructure.class);
                        intent.putExtra("session", selectedSession);
                        intent.putExtra("facilityName", selectedFacilityName);
                        intent.putExtra("facilityType", selectedFacilityType);
                        intent.putExtra("sessionNew", sessionNew);
                        startActivity(intent);
                        alertDialog.cancel();
                    }
                });
            } else {
                cardView3.setCardElevation(0);
                cardView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "No records found", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {
        }

        try {
            String query = "select * from " + TABLENAME8;
            cursor = sqLiteDatabase.query(TABLENAME8, new String[]{COL_SESSION}, COL_SESSION + "=?", new String[]{sessionNew}, null, null, null);
            if (cursor.getCount() >= 0) {
                textView4.setTextColor(Color.BLACK);
                cardView4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MentorConstant.whichBlockCalled = true;
                        Intent intent = new Intent(getApplicationContext(), DrugsSupply.class);
                        intent.putExtra("session", selectedSession);
                        intent.putExtra("facilityName", selectedFacilityName);
                        intent.putExtra("facilityType", selectedFacilityType);
                        intent.putExtra("sessionNew", sessionNew);
                        startActivity(intent);
                        alertDialog.cancel();
                    }
                });
            } else {
                cardView4.setCardElevation(0);
                cardView4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "No records found", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {
        }

        try {
            cursor = sqLiteDatabase.query(TABLE_ClinicalStandards, new String[]{COL_SESSION}, COL_SESSION + "=?", new String[]{sessionNew}, null, null, null);
            if (cursor.getCount() >= 0) {
                textView5.setTextColor(Color.BLACK);
                cardView5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MentorConstant.whichBlockCalled = true;
                        Intent intent = new Intent(getApplicationContext(), ClinicalStandards.class);
                        intent.putExtra("session", selectedSession);
                        intent.putExtra("facilityName", selectedFacilityName);
                        intent.putExtra("facilityType", selectedFacilityType);
                        intent.putExtra("sessionNew", sessionNew);
                        startActivity(intent);
                        alertDialog.cancel();
                    }
                });
            } else {
                cardView5.setCardElevation(0);
                cardView5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "No records found", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {
        }

        try {
            String query = "select * from " + TABLENAME6;
            cursor = sqLiteDatabase.query(TABLENAME6, new String[]{COL_SESSION}, COL_SESSION + "=?", new String[]{sessionNew}, null, null, null);
            if (cursor.getCount() >= 0) {
                textView6.setTextColor(Color.BLACK);
                cardView6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MentorConstant.whichBlockCalled = true;
                        Intent intent = new Intent(getApplicationContext(), MentoringPractices.class);
                        intent.putExtra("session", selectedSession);
                        intent.putExtra("facilityName", selectedFacilityName);
                        intent.putExtra("facilityType", selectedFacilityType);
                        intent.putExtra("sessionNew", sessionNew);
                        startActivity(intent);
                        alertDialog.cancel();
                    }
                });
            } else {
                cardView6.setCardElevation(0);
                cardView6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "No records found", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {
        }

        try {
            String query = "select * from " + TABLENAME9;
            cursor = sqLiteDatabase.query(TABLENAME9, new String[]{COL_SESSION}, COL_SESSION + "=?", new String[]{sessionNew}, null, null, null);
            if (cursor.getCount() >= 0) {
                textView7.setTextColor(Color.BLACK);
                cardView7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MentorConstant.whichBlockCalled = true;
                        Intent intent = new Intent(getApplicationContext(), RecprdingRoporting.class);
                        intent.putExtra("session", selectedSession);
                        intent.putExtra("facilityName", selectedFacilityName);
                        intent.putExtra("facilityType", selectedFacilityType);
                        intent.putExtra("sessionNew", sessionNew);
                        startActivity(intent);
                        alertDialog.cancel();
                    }
                });
            } else {
                cardView7.setCardElevation(0);
                cardView7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "No records found", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {
        }

        try {
            String query = "select * from " + TABLENAME10;
            cursor = sqLiteDatabase.query(TABLENAME10, new String[]{COL_SESSION}, COL_SESSION + "=?", new String[]{sessionNew}, null, null, null);
            if (cursor.getCount() >= 0) {
                textView8.setTextColor(Color.BLACK);
                cardView8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MentorConstant.whichBlockCalled = true;
                        Intent intent = new Intent(getApplicationContext(), NextVisitDate.class);
                        intent.putExtra("session", selectedSession);
                        intent.putExtra("facilityName", selectedFacilityName);
                        intent.putExtra("facilityType", selectedFacilityType);
                        intent.putExtra("sessionNew", sessionNew);
                        startActivity(intent);
                        alertDialog.cancel();
                    }
                });
            } else {
                cardView8.setCardElevation(0);
                cardView8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), R.string.alert_no_records_found, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {
        }

        try {
            String query = "select * from " + TABLENAME11;
            cursor = sqLiteDatabase.query(TABLENAME11, new String[]{COL_SESSION}, COL_SESSION + "=?", new String[]{sessionNew}, null, null, null);
            if (cursor.getCount() >= 0) {
                textView9.setTextColor(Color.BLACK);
                cardView9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MentorConstant.whichBlockCalled = true;
                        Intent intent = new Intent(getApplicationContext(), CommentsRemarks.class);
                        intent.putExtra("session", sessionID);
                        intent.putExtra("sessionNew", sessionNew);
                        startActivity(intent);
                        alertDialog.cancel();
                    }
                });
            } else {
                cardView9.setCardElevation(0);
                cardView9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "No records found", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {
        }
        alertDialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
    }

    // New way to get address
    public String getLocationCityName(double lat, double lon) {
        JSONObject result = getLocationFormGoogle(lat + "," + lon);
        if (getCityAddress(result) != null) {
            return getCityAddress(result);
        } else {
            return "\n\n Latitude : " + lat + ",     \n Longitude : " + lon;
        }
    }

    JSONObject jsonObject = null;

    protected JSONObject getLocationFormGoogle(final String placesName) {

        new AsyncTask<Void, Void, Void>() {
            ProgressDialog dl;

            @Override
            protected void onPreExecute() {
                dl = new ProgressDialog(DraftActivity.this);
                dl.setTitle("Location Access");
                dl.setMessage("Locating address, please wait...");
                dl.setCancelable(false);
                dl.show();
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                String apiRequest = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + placesName; //+ "&ka&sensor=false"
                HttpGet httpGet = new HttpGet(apiRequest);
                HttpClient client = new DefaultHttpClient();
                HttpResponse response;
                StringBuilder stringBuilder = new StringBuilder();

                try {
                    response = client.execute(httpGet);
                    HttpEntity entity = response.getEntity();
                    InputStream stream = entity.getContent();
                    int b;
                    while ((b = stream.read()) != -1) {
                        stringBuilder.append((char) b);
                    }
                } catch (ClientProtocolException e) {
                } catch (IOException e) {
                }

                jsonObject = new JSONObject();
                try {
                    jsonObject = new JSONObject(stringBuilder.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (dl != null && dl.isShowing()) {
                    dl.dismiss();
                }
            }

        }.execute();
        return jsonObject;
    }

    protected static String getCityAddress(JSONObject result) {
        if (result.has("results")) {
            try {
                JSONArray array = result.getJSONArray("results");
                if (array.length() > 0) {
                    JSONObject place = array.getJSONObject(0);
                    JSONArray components = place.getJSONArray("address_components");
                    for (int i = 0; i < components.length(); i++) {
                        JSONObject component = components.getJSONObject(i);
                        JSONArray types = component.getJSONArray("types");
                        for (int j = 0; j < types.length(); j++) {
                            if (types.getString(j).equals("locality")) {
                                return component.getString("long_name");
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void alertSave(final String selectedSession, final String facilityName, final String facilityType, int position, final String sessionNew) {
        forPdfCreation(selectedSession, facilityName + "_" + facilityType);
        MentorConstant.recordId = position;
        View view1;
        view1 = LayoutInflater.from(DraftActivity.this).inflate(R.layout.alert_new_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(DraftActivity.this).create();
        alertDialog.setView(view1);
        ScrollView scrollView = view1.findViewById(R.id.scrollView);
        TextView textMessage = view1.findViewById(R.id.textMessage);
        Button btnSave = view1.findViewById(R.id.ad_save);
        Button btnCompare = view1.findViewById(R.id.ad_compare);
        Button btnPdf = view1.findViewById(R.id.ad_pdf);
        ImageView icon_back = view1.findViewById(R.id.icon_back);

        scrollView.setVisibility(View.GONE);
        textMessage.setVisibility(View.VISIBLE);
        btnSave.setVisibility(View.VISIBLE);
        Button button = view1.findViewById(R.id.ad_cancel);
        CardView cardView1 = view1.findViewById(R.id.ad_card1);
        CardView cardView2 = view1.findViewById(R.id.ad_card2);
        CardView cardView3 = view1.findViewById(R.id.ad_card3);
        CardView cardView4 = view1.findViewById(R.id.ad_card4);
        CardView cardView5 = view1.findViewById(R.id.ad_card5);
        CardView cardView6 = view1.findViewById(R.id.ad_card6);
        CardView cardView7 = view1.findViewById(R.id.ad_card7);
        CardView cardView8 = view1.findViewById(R.id.ad_card8);
        CardView cardView9 = view1.findViewById(R.id.ad_card9);
        TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9;
        textView1 = view1.findViewById(R.id.ad_tv1);
        textView2 = view1.findViewById(R.id.ad_tv2);
        textView3 = view1.findViewById(R.id.ad_tv3);
        textView4 = view1.findViewById(R.id.ad_tv4);
        textView5 = view1.findViewById(R.id.ad_tv5);
        textView6 = view1.findViewById(R.id.ad_tv6);
        textView7 = view1.findViewById(R.id.ad_tv7);
        textView8 = view1.findViewById(R.id.ad_tv8);
        textView9 = view1.findViewById(R.id.ad_tv9);

        cardView1.setVisibility(View.GONE);
        cardView2.setVisibility(View.GONE);
        cardView3.setVisibility(View.GONE);
        cardView4.setVisibility(View.GONE);
        cardView5.setVisibility(View.GONE);
        cardView6.setVisibility(View.GONE);
        cardView7.setVisibility(View.GONE);
        cardView8.setVisibility(View.GONE);
        cardView9.setVisibility(View.GONE);
        textView1.setVisibility(View.GONE);
        textView2.setVisibility(View.GONE);
        textView3.setVisibility(View.GONE);
        textView4.setVisibility(View.GONE);
        textView5.setVisibility(View.GONE);
        textView6.setVisibility(View.GONE);
        textView7.setVisibility(View.GONE);
        textView8.setVisibility(View.GONE);
        textView9.setVisibility(View.GONE);

        alertDialog.show();
        button.setText("EDIT");
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
        btnCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertHistoryComparison(selectedSession, facilityName, facilityType, MentorConstant.recordId, sessionNew);
            }
        });
        btnPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPdf(selectedSessionForPdf, fileNameForPdf);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertHistory(selectedSession, facilityName, facilityType, MentorConstant.recordId, sessionNew);
                alertDialog.cancel();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
                progressDialog.show();

                String label_1_total = "8", label_2_total = "10", label_3_total = "20", label_4_total = "26", label_5_total = "19", label_6_total = "19",
                        label_7_total = "4", label_8_total = "1", label_9_total = "1", label_10_total = "10";

                try {
                    jhpiegoDatabase = new JhpiegoDatabase(DraftActivity.this);
                    sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                    String query = "select * from " + TABLENAME1 + " where syncstatus is NULL";
                    Cursor cursor = sqLiteDatabase.rawQuery(query, null);
                    Log.e("Cursor", "" + cursor.getCount());
                } catch (Exception e) {

                }

                try {
                    jhpiegoDatabase = new JhpiegoDatabase(DraftActivity.this);
                    sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                    String query1, query2, query3, query4, query5, query6, query7, query8, query9, query10, query11;
                    Cursor cursor1, cursor2, cursor3, cursor4, cursor5, cursor6, cursor7, cursor8, cursor9, cursor10, cursor11;
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
                    final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(DraftActivity.this);
                    builder.setTitle("DAKSHATA MENTOR");
                    builder.setCancelable(false);
                    if (cursor1.getCount() > 0 || cursor2.getCount() > 0 || cursor3.getCount() > 0 || cursor4.getCount() > 0
                            || cursor5.getCount() > 0 || cursor6.getCount() > 0 || cursor7.getCount() > 0
                            || cursor8.getCount() > 0 || cursor9.getCount() > 0 || cursor10.getCount() > 0 || cursor11.getCount() > 0) {
                        if (cursor1.getCount() > 0 && cursor2.getCount() > 0 && cursor3.getCount() > 0 && cursor4.getCount() > 0
                                && cursor5.getCount() > 0 && cursor6.getCount() > 0
                                && cursor7.getCount() > 0 && cursor8.getCount() > 0
                                && cursor9.getCount() > 0 && cursor10.getCount() > 0 && cursor11.getCount() > 0) {
                            builder.setMessage("Do you wish to save your data?");
                        } else {
                            builder.setMessage("Few questions have not been answered. Do you wish to save your data?");
                        }
                    } else {

                        builder.setMessage("You have not answered some of the forms still do you want to submit session?");

                    }
                    builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            createFullJson(0);
                            dialogInterface.cancel();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (progressDialog.isShowing()) progressDialog.dismiss();
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void createFullJson(int position) {
        getAllAccessToGPSLocation();
        if (position == 0) {

            Gson gson = new Gson();
            AnswersModel mAnswersModel = new AnswersModel();

            mAnswersModel.setUser(username);
            List<VisitDatum> mVisitDatumListAns = new ArrayList<>();
            List<CompetencyTrackingParent> mList = new ArrayList<>();

            try {
                SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                Cursor cursor = sqLiteDatabase.query(TABLENAME1, new String[]{COL_SESSION, COL_ANSJSON, COL_IMAGE1, COL_IMAGE2, COL_IMAGE3, COL_IMAGE4, COL_FILE1, COL_FILE2, COL_FILE3, COL_FILE4, "workplan_status", "planned_date", "reference_key"}, COL_ID + "=?", new String[]{String.valueOf(MentorConstant.recordId)}, null, null, null);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    reference_key = cursor.getString(cursor.getColumnIndex("reference_key"));
                    sessionid = cursor.getString(cursor.getColumnIndex(COL_SESSION));
                    mAnswersModel.setVisitId(sessionid);

                    String json = cursor.getString(cursor.getColumnIndex(COL_ANSJSON));
                    finalFile5 = cursor.getString(cursor.getColumnIndex(COL_FILE1));
                    Log.v("finalFile5", "finalFile5" + finalFile5);
                    finalFile6 = cursor.getString(cursor.getColumnIndex(COL_FILE2));
                    finalFile7 = cursor.getString(cursor.getColumnIndex(COL_FILE3));
                    finalFile8 = cursor.getString(cursor.getColumnIndex(COL_FILE4));

                    byte[] blob1 = cursor.getBlob(cursor.getColumnIndex(COL_IMAGE1));
                    byte[] blob2 = cursor.getBlob(cursor.getColumnIndex(COL_IMAGE2));
                    byte[] blob3 = cursor.getBlob(cursor.getColumnIndex(COL_IMAGE3));
                    byte[] blob4 = cursor.getBlob(cursor.getColumnIndex(COL_IMAGE4));

                    if (blob1 != null)
                        bitmap1 = BitmapFactory.decodeByteArray(blob1, 0, blob1.length);

                    if (blob2 != null)
                        bitmap2 = BitmapFactory.decodeByteArray(blob2, 0, blob2.length);

                    if (blob3 != null)
                        bitmap3 = BitmapFactory.decodeByteArray(blob3, 0, blob3.length);

                    if (blob4 != null)
                        bitmap4 = BitmapFactory.decodeByteArray(blob4, 0, blob4.length);
                    // VisitDatum mVisitDatumAns=new VisitDatum();
                    VisitDatum mVisitDatumAns = gson.fromJson(json, VisitDatum.class);
                    mVisitDatumListAns.add(mVisitDatumAns);
                }
            } catch (Exception e) {
            }
            try {
                SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                String query = "select * from " + TABLENAME7;
                Cursor cursor = sqLiteDatabase.query(TABLENAME7, new String[]{COL_SESSION, COL_ANSJSON}, COL_UNIQUE_ID + "=?", new String[]{String.valueOf(MentorConstant.recordId)}, null, null, null);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    String json = cursor.getString(cursor.getColumnIndex(COL_ANSJSON));
                    Log.v("json", "" + json);
                    //VisitDatum mVisitDatumAns=new VisitDatum();
                    VisitDatum mVisitDatumAns = gson.fromJson(json, VisitDatum.class);
                    mVisitDatumListAns.add(mVisitDatumAns);
                }
            } catch (Exception e) {
            }
            try {
                SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                String query = "select * from " + TABLENAME5;
                Cursor cursor = sqLiteDatabase.query(TABLENAME5, new String[]{COL_SESSION, COL_ANSJSON}, COL_UNIQUE_ID + "=?", new String[]{String.valueOf(MentorConstant.recordId)}, null, null, null);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    String json = cursor.getString(cursor.getColumnIndex(COL_ANSJSON));
                    Log.v("json", "" + json);
                    //VisitDatum mVisitDatumAns=new VisitDatum();
                    VisitDatum mVisitDatumAns = gson.fromJson(json, VisitDatum.class);
                    mVisitDatumListAns.add(mVisitDatumAns);
                }
            } catch (Exception e) {
            }
            try {
                SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                String query = "select * from " + TABLENAME8;
                Cursor cursor = sqLiteDatabase.query(TABLENAME8, new String[]{COL_SESSION, COL_ANSJSON}, COL_UNIQUE_ID + "=?", new String[]{String.valueOf(MentorConstant.recordId)}, null, null, null);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    String json = cursor.getString(cursor.getColumnIndex(COL_ANSJSON));
                    Log.v("json", "" + json);
                    //VisitDatum mVisitDatumAns=new VisitDatum();
                    VisitDatum mVisitDatumAns = gson.fromJson(json, VisitDatum.class);
                    mVisitDatumListAns.add(mVisitDatumAns);
                }
            } catch (Exception e) {
            }
            try {
                SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                Cursor cursor = sqLiteDatabase.query(TABLE_ClinicalStandards, new String[]{COL_SESSION, COL_ANSJSON}, COL_UNIQUE_ID + "=?", new String[]{String.valueOf(MentorConstant.recordId)}, null, null, null);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    String json = cursor.getString(cursor.getColumnIndex(COL_ANSJSON));
                    Log.v("json", "" + json);
                    //VisitDatum mVisitDatumAns=new VisitDatum();
                    VisitDatum mVisitDatumAns = gson.fromJson(json, VisitDatum.class);
                    mVisitDatumListAns.add(mVisitDatumAns);
                }
            } catch (Exception e) {
            }
            try {
                SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                String query = "select * from " + TABLENAME6;
                Cursor cursor = sqLiteDatabase.query(TABLENAME6, new String[]{COL_SESSION, COL_ANSJSON}, COL_UNIQUE_ID + "=?", new String[]{String.valueOf(MentorConstant.recordId)}, null, null, null);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    String json = cursor.getString(cursor.getColumnIndex(COL_ANSJSON));
                    Log.v("json", "" + json);
                    //VisitDatum mVisitDatumAns=new VisitDatum();
                    VisitDatum mVisitDatumAns = gson.fromJson(json, VisitDatum.class);
                    mVisitDatumListAns.add(mVisitDatumAns);
                }
            } catch (Exception e) {
            }
            try {
                SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                String query = "select * from " + TABLENAME9;
                Cursor cursor = sqLiteDatabase.query(TABLENAME9, new String[]{COL_SESSION, COL_ANSJSON}, COL_UNIQUE_ID + "=?", new String[]{String.valueOf(MentorConstant.recordId)}, null, null, null);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    String json = cursor.getString(cursor.getColumnIndex(COL_ANSJSON));
                    Log.v("json", "" + json);
                    //VisitDatum mVisitDatumAns=new VisitDatum();
                    VisitDatum mVisitDatumAns = gson.fromJson(json, VisitDatum.class);
                    mVisitDatumListAns.add(mVisitDatumAns);
                }
            } catch (Exception e) {
            }
            try {
                SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                String query = "select * from " + TABLENAME10;
                Cursor cursor = sqLiteDatabase.query(TABLENAME10, new String[]{COL_SESSION, COL_ANSJSON}, COL_UNIQUE_ID + "=?", new String[]{String.valueOf(MentorConstant.recordId)}, null, null, null);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    String json = cursor.getString(cursor.getColumnIndex(COL_ANSJSON));
                    Log.v("json", "" + json);
                    //VisitDatum mVisitDatumAns=new VisitDatum();
                    VisitDatum mVisitDatumAns = gson.fromJson(json, VisitDatum.class);
                    mVisitDatumListAns.add(mVisitDatumAns);
                }
            } catch (Exception e) {
            }
            try {
                SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                String query = "select * from " + TABLENAME11;
                Cursor cursor = sqLiteDatabase.query(TABLENAME11, new String[]{COL_SESSION, COL_ANSJSON}, COL_UNIQUE_ID + "=?", new String[]{String.valueOf(MentorConstant.recordId)}, null, null, null);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    String json = cursor.getString(cursor.getColumnIndex(COL_ANSJSON));
                    Log.v("json", "" + json);
                    //VisitDatum mVisitDatumAns=new VisitDatum();
                    VisitDatum mVisitDatumAns = gson.fromJson(json, VisitDatum.class);
                    mVisitDatumListAns.add(mVisitDatumAns);
                }
            } catch (Exception e) {
            }
            try {
                SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                String query = "select * from " + TABLENAME17;
                Cursor cursor = sqLiteDatabase.query(TABLENAME17, new String[]{COL_SESSION, COL_ANSJSON}, COL_UNIQUE_ID + "=?", new String[]{String.valueOf(MentorConstant.recordId)}, null, null, null);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    String json = cursor.getString(cursor.getColumnIndex(COL_ANSJSON));

                    //VisitDatum mVisitDatumAns=new VisitDatum();
                    JSONArray jArray = (JSONArray) new JSONArray(json);
                    ArrayList<CompetencyTrackingParent> tempList = new ArrayList<>();
                    Gson gson1 = new Gson();
                    Type type = new TypeToken<List<CompetencyTrackingParent>>() {
                    }.getType();
                    tempList = gson1.fromJson(json, type);
                    VisitDatum mVisitDatumAns = new VisitDatum();
                    mVisitDatumAns.setFormCode("c_t");
                    mVisitDatumAns.setFormData2(tempList);
                    mVisitDatumAns.setFormData(new ArrayList<FormDatum>());
                    //VisitDatum mVisitDatumAns = gson.fromJson(mainObject.toString(), VisitDatum.class);

                    mVisitDatumListAns.add(mVisitDatumAns);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            mAnswersModel.setVisitData(mVisitDatumListAns);
            // Gson gson= new Gson();
            fullJson = gson.toJson(mAnswersModel);
            Log.v("fullJson ", "fullJson : " + fullJson);


            try {
                SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                String query = "select * from " + TABLENAME18;
                Cursor cursor = sqLiteDatabase.query(TABLENAME18, new String[]{COL_SESSION, COL_ANSJSON}, COL_UNIQUE_ID + "=?", new String[]{String.valueOf(MentorConstant.recordId)}, null, null, null);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    String json = cursor.getString(cursor.getColumnIndex(COL_ANSJSON));


                    JSONArray jArray = (JSONArray) new JSONArray(json);
                    ArrayList<ClientFeedbackDto> tempList = new ArrayList<>();
                    Gson gson1 = new Gson();
                    Type type = new TypeToken<List<ClientFeedbackDto>>() {
                    }.getType();
                    tempList = gson1.fromJson(json, type);
                    VisitDatum mVisitDatumAns = new VisitDatum();
                    mVisitDatumAns.setFormCode("c_f");
                    mVisitDatumAns.setFormData3(tempList);
                    mVisitDatumAns.setFormData(new ArrayList<FormDatum>());
                    mVisitDatumAns.setFormData2(new ArrayList<CompetencyTrackingParent>());
                    mVisitDatumListAns.add(mVisitDatumAns);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            mAnswersModel.setVisitData(mVisitDatumListAns);
            // Gson gson= new Gson();
            fullJson = gson.toJson(mAnswersModel);
            Log.v("fullJson ", "fullJson : " + fullJson);


            try {

                JSONObject jsonObject = new JSONObject(fullJson);

                String status = "pending";
                jhpiegoDatabase.updateIsSubmitted(String.valueOf(MentorConstant.recordId));

                final String url = Utils.URL_NEW + Utils.BOOKMARK_ANS_NEW;
                long row = jhpiegoDatabase.saveJsonToPost(username, url, fullJson, status, sessionid, finalFile5, finalFile6, finalFile7, finalFile8, reference_key);
                Toast.makeText(getApplicationContext(), "Successfully submitted", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if (row == -1) {
                    Toast.makeText(getApplicationContext(), "Not saved pending json", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
//                SharedPreferences.Editor sPref = getSharedPreferences("session", MODE_PRIVATE).edit();
//                sPref.putString("gps_record_id", "");
//                sPref.commit();

//                Intent intent = new Intent(DraftActivity.this, DraftActivity.class);
//                startActivity(intent);
//                finish();

                populateListing();

            } catch (JSONException e) {
                e.printStackTrace();
                Log.v("ans ", "ans json :execp " + e.toString());
            }

        } else {
            if (progressDialog.isShowing()) progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Not saved, please try again.", Toast.LENGTH_SHORT).show();
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
                            address = MentorConstant.getAddress(DraftActivity.this, MentorConstant.latitude, MentorConstant.longitude);
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
                    address = MentorConstant.getAddress(DraftActivity.this, MentorConstant.latitude, MentorConstant.longitude);
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
                ActivityCompat.requestPermissions(DraftActivity.this,
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
            address = MentorConstant.getAddress(DraftActivity.this, MentorConstant.latitude, MentorConstant.longitude);
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
    protected void onResume() {
        super.onResume();
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

        if (draftAdapter != null) {
            populateListing();
        }
    }

    // For record comparison
    public void alertHistoryComparison(final String selectedSession, final String selectedFacilityName, final String selectedFacilityType, int recordId, final String sessionNew) {
        View view1;
        view1 = LayoutInflater.from(DraftActivity.this).inflate(R.layout.alert_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(DraftActivity.this).create();
        alertDialog.setView(view1);
        Button button = view1.findViewById(R.id.ad_cancel);
        CardView cardView1 = view1.findViewById(R.id.ad_card1);
        CardView cardView2 = view1.findViewById(R.id.ad_card2);
        CardView cardView3 = view1.findViewById(R.id.ad_card3);
        CardView cardView4 = view1.findViewById(R.id.ad_card4);
        CardView cardView5 = view1.findViewById(R.id.ad_card5);
        CardView cardView6 = view1.findViewById(R.id.ad_card6);
        CardView cardView7 = view1.findViewById(R.id.ad_card7);
        CardView cardView8 = view1.findViewById(R.id.ad_card8);
        CardView cardView9 = view1.findViewById(R.id.ad_card9);
        TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9;
        textView1 = view1.findViewById(R.id.ad_tv1);
        textView2 = view1.findViewById(R.id.ad_tv2);
        textView3 = view1.findViewById(R.id.ad_tv3);
        textView4 = view1.findViewById(R.id.ad_tv4);
        textView5 = view1.findViewById(R.id.ad_tv5);
        textView6 = view1.findViewById(R.id.ad_tv6);
        textView7 = view1.findViewById(R.id.ad_tv7);
        textView8 = view1.findViewById(R.id.ad_tv8);
        textView9 = view1.findViewById(R.id.ad_tv9);

        try {
            cursor = sqLiteDatabase.query(TABLENAME1, new String[]{COL_SESSION}, COL_SESSION + "=?", new String[]{selectedSession}, null, null, null);
            if (cursor.getCount() > 0) {
                textView1.setTextColor(Color.BLACK);
                cardView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), DetailsOfVisitRetrieved.class);
                        intent.putExtra("session", selectedSession);
                        intent.putExtra("facilityName", selectedFacilityName);
                        intent.putExtra("facilityType", selectedFacilityType);
                        startActivity(intent);
                    }
                });
            } else {
                cardView1.setCardElevation(0);
                cardView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), getString(R.string.alert_no_records_found), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {
        }

        try {
            String query = "select * from " + TABLENAME7;
            cursor = sqLiteDatabase.query(TABLENAME7, new String[]{COL_SESSION}, COL_SESSION + "=?", new String[]{selectedSession}, null, null, null);
            if (cursor.getCount() > 0) {
                textView2.setTextColor(Color.BLACK);
                cardView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), StaffMaternityRetrComparision.class);
                        intent.putExtra("session", selectedSession);
                        intent.putExtra("facilityName", selectedFacilityName);
                        intent.putExtra("facilityType", selectedFacilityType);
                        startActivity(intent);
                    }
                });
            } else {
                cardView2.setCardElevation(0);
                cardView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), getString(R.string.alert_no_records_found), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {
        }

        try {
            String query = "select * from " + TABLENAME5;
            cursor = sqLiteDatabase.query(TABLENAME5, new String[]{COL_SESSION}, COL_SESSION + "=?", new String[]{selectedSession}, null, null, null);
            if (cursor.getCount() > 0) {
                textView3.setTextColor(Color.BLACK);
                cardView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), InfrastructureRetrComparision.class);
                        intent.putExtra("session", selectedSession);
                        intent.putExtra("facilityName", selectedFacilityName);
                        intent.putExtra("facilityType", selectedFacilityType);
                        startActivity(intent);
                    }
                });
            } else {
                cardView3.setCardElevation(0);
                cardView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), getString(R.string.alert_no_records_found), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {
        }

        try {
            String query = "select * from " + TABLENAME8;
            cursor = sqLiteDatabase.query(TABLENAME8, new String[]{COL_SESSION}, COL_SESSION + "=?", new String[]{selectedSession}, null, null, null);
            if (cursor.getCount() > 0) {
                textView4.setTextColor(Color.BLACK);
                cardView4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), DrugSupplyRetrComparision.class);
                        intent.putExtra("session", selectedSession);
                        intent.putExtra("facilityName", selectedFacilityName);
                        intent.putExtra("facilityType", selectedFacilityType);
                        startActivity(intent);
                    }
                });
            } else {
                cardView4.setCardElevation(0);
                cardView4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), getString(R.string.alert_no_records_found), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {
        }

        try {
            cursor = sqLiteDatabase.query(TABLE_ClinicalStandards, new String[]{COL_SESSION}, COL_SESSION + "=?", new String[]{selectedSession}, null, null, null);
            if (cursor.getCount() > 0) {
                textView5.setTextColor(Color.BLACK);
                cardView5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), ClinicalStandardsRetrComparision.class);
                        intent.putExtra("session", selectedSession);
                        intent.putExtra("facilityName", selectedFacilityName);
                        intent.putExtra("facilityType", selectedFacilityType);
                        startActivity(intent);
                    }
                });
            } else {
                cardView5.setCardElevation(0);
                cardView5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), getString(R.string.alert_no_records_found), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {
        }

        try {
            String query = "select * from " + TABLENAME6;
            cursor = sqLiteDatabase.query(TABLENAME6, new String[]{COL_SESSION}, COL_SESSION + "=?", new String[]{selectedSession}, null, null, null);
            if (cursor.getCount() > 0) {
                textView6.setTextColor(Color.BLACK);
                cardView6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), MentoringPracticesRetrComparision.class);
                        intent.putExtra("session", selectedSession);
                        intent.putExtra("facilityName", selectedFacilityName);
                        intent.putExtra("facilityType", selectedFacilityType);
                        startActivity(intent);
                    }
                });
            } else {
                cardView6.setCardElevation(0);
                cardView6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), getString(R.string.alert_no_records_found), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {
        }

        try {
            String query = "select * from " + TABLENAME9;
            cursor = sqLiteDatabase.query(TABLENAME9, new String[]{COL_SESSION}, COL_SESSION + "=?", new String[]{selectedSession}, null, null, null);
            if (cursor.getCount() > 0) {
                textView7.setTextColor(Color.BLACK);
                cardView7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), RecordingReportingRetrComparision.class);
                        intent.putExtra("session", selectedSession);
                        intent.putExtra("facilityName", selectedFacilityName);
                        intent.putExtra("facilityType", selectedFacilityType);
                        startActivity(intent);
                    }
                });
            } else {
                cardView7.setCardElevation(0);
                cardView7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), getString(R.string.alert_no_records_found), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {
        }

        try {
            String query = "select * from " + TABLENAME10;
            cursor = sqLiteDatabase.query(TABLENAME10, new String[]{COL_SESSION}, COL_SESSION + "=?", new String[]{selectedSession}, null, null, null);
            if (cursor.getCount() > 0) {
                textView8.setTextColor(Color.BLACK);
                cardView8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), NextVisitRetr.class);
                        intent.putExtra("session", selectedSession);
                        intent.putExtra("facilityName", selectedFacilityName);
                        intent.putExtra("facilityType", selectedFacilityType);
                        startActivity(intent);
                    }
                });
            } else {
                cardView8.setCardElevation(0);
                cardView8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), getString(R.string.alert_no_records_found), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {
        }

        try {
            String query = "select * from " + TABLENAME11;
            cursor = sqLiteDatabase.query(TABLENAME11, new String[]{COL_SESSION}, COL_SESSION + "=?", new String[]{selectedSession}, null, null, null);
            if (cursor.getCount() > 0) {
                textView9.setTextColor(Color.BLACK);
                cardView9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), CommentsRemarksRetr.class);
                        intent.putExtra("session", selectedSession);
                        intent.putExtra("facilityName", selectedFacilityName);
                        intent.putExtra("facilityType", selectedFacilityType);
                        startActivity(intent);
                    }
                });
            } else {
                cardView9.setCardElevation(0);
                cardView9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), getString(R.string.alert_no_records_found), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {
        }
        alertDialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
    }

    // For Pdf Creation
    public void forPdfCreation(String selectedSessionForPdf, String fileNameForPdf) {
        this.selectedSessionForPdf = selectedSessionForPdf;
        this.fileNameForPdf = fileNameForPdf;

        mentorName = userEmail = lastVisitDate = table1_col1 = table1_col2 = table1_col3 = table1_col4 = table1_col5 = table1_col6 = table1_col7 = table1_col8 = table1_colVisit =
                table1_col_latitude = table1_col_longitude = table2_col1 = table2_col2 = table2_col3 = table2_col4 = table2_col5 = table2_col6 = table2_col7 = table2_col8 =
                        table2_col9 = table2_col10 =table2_col11=table2_col12=table2_col13= table2_col14=table2_col15=table2_col16=table2_col17=table2_col18= table2_col19=table3_col21 = table3_col2 = table3_col3 = table3_col4 = table3_col5 = table3_col6 = table3_col7 = table3_col8 = table3_col9 =
                                table3_col10 = table3_col11 = table3_col12 = table3_col13 = table6_col7 = table6_col8 = table6_col9 = table6_col10 = table6_col11 = table6_col12 =
                                        table3_col14 = table3_col15 = table3_col16 = table3_col17 = table3_col18 = table3_col19 = table3_col20 = table4_col1 = table4_col2 = table4_col3 = table4_col4 =
                                                table4_col5 = table4_col6 = table4_col7 = table4_col8 = table4_col9 = table4_col10 = table4_col11 = table4_col12 = table4_col13 = table4_col14 = table4_col15 =
                                                        table4_col16 = table4_col17 = table4_col18 = table4_col19 = table4_col20 = table4_col21 = table4_col22 = table4_col23 = table4_col24 = table4_col25 =
                                                                table4_col26 = table5_col1 = table5_col2 = table5_col3 = table5_col4 = table5_col5 = table5_col6 = table5_col7 = table5_col8 = table5_col9 =
                                                                        table5_col10 = table5_col11 = table5_col12 = table5_col13 = table5_col14 = table5_col15 = table5_col16 = table5_col17 =
                                                                                table5_col18 = table5_col19 = table6_col1 = table6_col2 = table6_col3 = table6_col4 = table6_col5 = table6_col6 = table6_col13 =
                                                                                        table6_col14 = table6_col15 = table6_col16 = table6_col17 = table6_col18 = table6_col19 = table7_col1 =
                                                                                                table7_col2 = table7_col3 = table7_col4 = table8_col1 = table9_col1 = table1_colWorkPlanStatus = table1_colPlannedDate = "";
    }

    // Create Pdf
    public void createPdf(String selectedSession, String fileName) {

        try {
            jhpiegoDatabase = new JhpiegoDatabase(DraftActivity.this);
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

        Cursor cursor2 = sqLiteDatabase.query(TABLENAME7, new String[]{COL_SESSION, COL_OBG, COL_SBA, COL_MOLR, COL_MOD, COL_SNLR, COL_SND, COL_LHVSLR, COL_LHVSD, COL_LHVWORKING,
                COL_LHVTRAINED,COL_NSEL,COL_NSR_MNHToolKit,COL_VAR_Staff,COL_Labour_TableLR,COL_Labour_TableReq,COL_Variance_LbrTbl,COL_Caesarian_Act,COL_Person_InCharge,COL_Mobile_InCharge}, COL_SESSION + "=?", new String[]{selectedSession}, null, null, null);
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
            table2_col11 = cursor2.getString(cursor2.getColumnIndex(COL_NSEL));
            table2_col12 = cursor2.getString(cursor2.getColumnIndex(COL_NSR_MNHToolKit));
            table2_col13 = cursor2.getString(cursor2.getColumnIndex(COL_VAR_Staff));
            table2_col14 = cursor2.getString(cursor2.getColumnIndex(COL_Labour_TableLR));
            table2_col15 = cursor2.getString(cursor2.getColumnIndex(COL_Labour_TableReq));
            table2_col16 = cursor2.getString(cursor2.getColumnIndex(COL_Variance_LbrTbl));
            table2_col17 = cursor2.getString(cursor2.getColumnIndex(COL_Caesarian_Act));
            table2_col18 = cursor2.getString(cursor2.getColumnIndex(COL_Person_InCharge));
            table2_col19 = cursor2.getString(cursor2.getColumnIndex(COL_Mobile_InCharge));
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
            new FirstPdf(DraftActivity.this, fileName, MentorConstant.getAddress(DraftActivity.this, Double.parseDouble(table1_col_latitude), Double.parseDouble(table1_col_longitude)));
        } catch (Exception e) {
            new FirstPdf(DraftActivity.this, fileName, " Latitude : " + table1_col_latitude + ",   Longitude : " + table1_col_longitude);
        }

    }
}