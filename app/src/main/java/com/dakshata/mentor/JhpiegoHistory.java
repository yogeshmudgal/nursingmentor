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
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
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
import android.widget.TextView;
import android.widget.Toast;

import com.dakshata.mentor.Utils.MentorConstant;
import com.dakshata.mentor.models.QuestListDto;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.dakshata.mentor.JhpiegoDatabase.*;

/**
 * Created by Aditya on 1/5/2018.
 */
public class JhpiegoHistory extends AppCompatActivity {
    ListView listView;
    JhpiegoDatabase jhpiegoDatabase;
    SharedPreferences sh_Pref, sharedPreferences;
    SharedPreferences.Editor editor, editor1;
    List<ListPojo> list;
    TextView textView;
    Cursor cursor;
    Toolbar toolbar;
    SQLiteDatabase sqLiteDatabase;
    String sessionID;
    ImageView back;
    String selectedSession;
    TextView textViewHeaderName;
    HistoryAdapter historyAdapter;
    RecyclerView recyclerView;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    LocationManager locationManager;
    String provider;
    private double latitude;
    private double longitude;
    Location location;
    private int loop = 0;
    public static String username = "";


    //LayoutAnimationController layoutAnimationController;
    // int resId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jhpiego_history);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.menu_image_opt3));
        setSupportActionBar(toolbar);


        textViewHeaderName = (TextView) findViewById(R.id.header_name);
        textViewHeaderName.setText("Final Records");
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
        historyAdapter = new HistoryAdapter(this, list);
        recyclerView.setAdapter(historyAdapter);
        historyAdapter.notifyDataSetChanged();
        sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
        username = sh_Pref.getString("Username", "");
        // ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        // listView.setAdapter(listAdapter);
        // listView.deferNotifyDataSetChanged();
        sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
        cursor = sqLiteDatabase.query(TABLENAME1, new String[]{COL_USERNAME, COL_SESSION, COL_FACILITYNAME, COL_FACILITYTYPE, COL_DOV}, COL_USERNAME + "=? AND " + COL_SYNC_STATUS + "=?", new String[]{username, "synced"}, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                //sessionID=cursor.getString(cursor.getColumnIndex(COL_SESSION));
                String facilityname = cursor.getString(cursor.getColumnIndex(COL_FACILITYNAME));
                String facilitytype = cursor.getString(cursor.getColumnIndex(COL_FACILITYTYPE));
                String dov = cursor.getString(cursor.getColumnIndex(COL_DOV));
                ListPojo listPojo = new ListPojo(facilityname, facilitytype, dov);
                sessionID = facilityname + "-" + facilitytype + "-" + dov;
                list.add(listPojo);
            } while (cursor.moveToNext());

        } else {

            textView.setVisibility(View.VISIBLE);
            textView.setText("No records found");
        }

        // getUserDisplay();
      /*  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedSession= (String) listView.getItemAtPosition(i);
                sharedPreferences = getSharedPreferences("session Credentials", MODE_PRIVATE);
                editor1 = sharedPreferences.edit();
                editor1.putString("session", selectedSession);
                editor1.commit();


                alertHistory();


            }

        });*/

//        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                TextView textView = view.findViewById(R.id.tv_listItemFacility);
//                TextView textView1 = view.findViewById(R.id.tv_listItemFacilityType);
//                TextView textView2 = view.findViewById(R.id.tv_listItemDov);
//                String facility = textView.getText().toString();
//                String facilitytype = textView1.getText().toString();
//                String dov = textView2.getText().toString();
//                selectedSession = facility + "-" + facilitytype + "-" + dov;
//                sharedPreferences = getSharedPreferences("session Credentials", MODE_PRIVATE);
//                editor1 = sharedPreferences.edit();
//                editor1.putString("session", selectedSession);
//                editor1.commit();
//
//
//                alertHistory(selectedSession);
//            }
//
//            @Override
//            public void onLongItemClick(View view, int position) {
//
//            }
//        }));
    }

    public void Pback(View view) {
        super.onBackPressed();
    }

    public void alertHistory(final String selectedSession, String facilityName, String facilityType) {
        View view1;
        view1 = LayoutInflater.from(JhpiegoHistory.this).inflate(R.layout.alert_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(JhpiegoHistory.this).create();
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

        sessionID = selectedSession;

        final String selectedFacilityName = facilityName;
        final String selectedFacilityType = facilityType;
//        selectedSession = facility + "-" + facilitytype + "-" + dov;

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
                        Toast.makeText(getApplicationContext(), "No records found", Toast.LENGTH_SHORT).show();
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
                        Intent intent = new Intent(getApplicationContext(), StaffMaternityRetr.class);
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
                        Toast.makeText(getApplicationContext(), "No records found", Toast.LENGTH_SHORT).show();
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
                        Intent intent = new Intent(getApplicationContext(), InfrastructureRetr.class);
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
                        Toast.makeText(getApplicationContext(), "No records found", Toast.LENGTH_SHORT).show();
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
                        Intent intent = new Intent(getApplicationContext(), DrugSupplyRetr.class);
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
                        Toast.makeText(getApplicationContext(), "No records found", Toast.LENGTH_SHORT).show();
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
                        Intent intent = new Intent(getApplicationContext(), ClinicalStandardsRetr.class);
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
                        Toast.makeText(getApplicationContext(), "No records found", Toast.LENGTH_SHORT).show();
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
                        Intent intent = new Intent(getApplicationContext(), MentoringPracticesRetr.class);
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
                        Toast.makeText(getApplicationContext(), "No records found", Toast.LENGTH_SHORT).show();
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
                        Intent intent = new Intent(getApplicationContext(), RecordingReportingRetr.class);
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
                        Toast.makeText(getApplicationContext(), "No records found", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getApplicationContext(), R.string.alert_no_records_found, Toast.LENGTH_SHORT).show();
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

    public void createPdf(String selectedSession, String fileName1) {

        try {
            jhpiegoDatabase = new JhpiegoDatabase(JhpiegoHistory.this);
            sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mentorName = userEmail = lastVisitDate = table1_col1 = table1_col2 = table1_col3 = table1_col4 = table1_col5 = table1_col6 = table1_col7 = table1_col8 = table1_colVisit =
                table1_col_latitude = table1_col_longitude = table2_col1 = table2_col2 = table2_col3 = table2_col4 = table2_col5 = table2_col6 = table2_col7 = table2_col8 =
                        table2_col9 = table2_col10 = table3_col21 = table3_col2 = table3_col3 = table3_col4 = table3_col5 = table3_col6 = table3_col7 = table3_col8 = table3_col9 =
                                table3_col10 = table3_col11 = table3_col12 = table3_col13 = table6_col7 = table6_col8 = table6_col9 = table6_col10 = table6_col11 = table6_col12 =
                                        table3_col14 = table3_col15 = table3_col16 = table3_col17 = table3_col18 = table3_col19 = table3_col20 = table4_col1 = table4_col2 = table4_col3 = table4_col4 =
                                                table4_col5 = table4_col6 = table4_col7 = table4_col8 = table4_col9 = table4_col10 = table4_col11 = table4_col12 = table4_col13 = table4_col14 = table4_col15 =
                                                        table4_col16 = table4_col17 = table4_col18 = table4_col19 = table4_col20 = table4_col21 = table4_col22 = table4_col23 = table4_col24 = table4_col25 =
                                                                table4_col26 = table5_col1 = table5_col2 = table5_col3 = table5_col4 = table5_col5 = table5_col6 = table5_col7 = table5_col8 = table5_col9 =
                                                                        table5_col10 = table5_col11 = table5_col12 = table5_col13 = table5_col14 = table5_col15 = table5_col16 = table5_col17 =
                                                                                table5_col18 = table5_col19 = table6_col1 = table6_col2 = table6_col3 = table6_col4 = table6_col5 = table6_col6 = table6_col13 =
                                                                                        table6_col14 = table6_col15 = table6_col16 = table6_col17 = table6_col18 = table6_col19 = table7_col1 =
                                                                                                table7_col2 = table7_col3 = table7_col4 = table8_col1 = table9_col1 = table1_colWorkPlanStatus = table1_colPlannedDate = "";

        this.fileName = fileName1 + "_SyncedRecords";
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
        } catch (Exception e) {}

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
        sqLiteDatabase.close();
        try {
            new FinalRecordsPdf(JhpiegoHistory.this, fileName, MentorConstant.getAddress(JhpiegoHistory.this, Double.parseDouble(table1_col_latitude), Double.parseDouble(table1_col_longitude)));
        } catch (Exception e) {
            new FinalRecordsPdf(JhpiegoHistory.this, fileName, " Latitude : " + table1_col_latitude + ",   Longitude : " + table1_col_longitude);
        }

    }

    // New way to get address
    public String getLocationCityName( double lat, double lon){
        JSONObject result = getLocationFormGoogle(lat + "," + lon );
        if (getCityAddress(result) != null) {
            return getCityAddress(result);
        } else{
            return "\n\n Latitude : " + lat + ",     \n Longitude : " + lon;
        }
    }

    JSONObject jsonObject = null;
    protected JSONObject getLocationFormGoogle(final String placesName) {

        new AsyncTask<Void, Void, Void>() {
            ProgressDialog dl;

            @Override
            protected void onPreExecute() {
                dl = new ProgressDialog(JhpiegoHistory.this);
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

    protected static String getCityAddress( JSONObject result ){
        if( result.has("results") ){
            try {
                JSONArray array = result.getJSONArray("results");
                if( array.length() > 0 ){
                    JSONObject place = array.getJSONObject(0);
                    JSONArray components = place.getJSONArray("address_components");
                    for( int i = 0 ; i < components.length() ; i++ ){
                        JSONObject component = components.getJSONObject(i);
                        JSONArray types = component.getJSONArray("types");
                        for( int j = 0 ; j < types.length() ; j ++ ){
                            if( types.getString(j).equals("locality") ){
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

}