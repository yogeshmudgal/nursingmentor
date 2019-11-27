package com.dakshata.mentor;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.dakshata.mentor.JhpiegoDatabase.COL_DOV;
import static com.dakshata.mentor.JhpiegoDatabase.COL_FACILITYNAME;
import static com.dakshata.mentor.JhpiegoDatabase.COL_FACILITYTYPE;
import static com.dakshata.mentor.JhpiegoDatabase.COL_SESSION;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME1;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME10;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME11;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME5;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME6;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME7;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME8;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME9;
import static com.dakshata.mentor.JhpiegoDatabase.TABLE_ClinicalStandards;

/**
 * Created by Aditya on 1/5/2018.
 */

public class ComparisionRecord extends AppCompatActivity {
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
    String selectedSession, selectedFacilityName, selectedFacilityType;
    TextView textViewHeaderName;
    ComparisionRecordAdapter comparisionRecordAdapter;
    RecyclerView recyclerView;

    //LayoutAnimationController layoutAnimationController;
    // int resId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comparision_record);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.menu_image_opt3));
        setSupportActionBar(toolbar);


        textViewHeaderName = (TextView) findViewById(R.id.header_name);
        textViewHeaderName.setText("History");

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
        comparisionRecordAdapter = new ComparisionRecordAdapter(this, list);
        recyclerView.setAdapter(comparisionRecordAdapter);
        comparisionRecordAdapter.notifyDataSetChanged();
        sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
        String username = sh_Pref.getString("Username", "");
        // ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        // listView.setAdapter(listAdapter);
        // listView.deferNotifyDataSetChanged();
        sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
        cursor = sqLiteDatabase.rawQuery("SELECT username, session, facilityname, facilitytype, dov from detailsofvisit GROUP BY facilityname, facilitytype ORDER BY id DESC ", null);
//        cursor=sqLiteDatabase.query(TABLENAME1,new String[]{COL_USERNAME,COL_SESSION,COL_FACILITYNAME,COL_FACILITYTYPE,COL_DOV},COL_USERNAME + "=?",new String[]{username},COL_FACILITYNAME, COL_FACILITYTYPE, "id",null);

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
            textView.setText(getString(R.string.alert_no_records_found));
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
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TextView textView = view.findViewById(R.id.tv_listItemFacility);
                TextView textView1 = view.findViewById(R.id.tv_listItemFacilityType);
                TextView textView2 = view.findViewById(R.id.tv_listItemDov);
                String facility = textView.getText().toString();
                String facilitytype = textView1.getText().toString();
                String dov = textView2.getText().toString();
                selectedFacilityName = facility;
                selectedFacilityType = facilitytype;
                selectedSession = facility + "-" + facilitytype + "-" + dov;
                sharedPreferences = getSharedPreferences("session Credentials", MODE_PRIVATE);
                editor1 = sharedPreferences.edit();
                editor1.putString("session", selectedSession);
                editor1.commit();


                alertHistory();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    public void Pback(View view) {
        super.onBackPressed();
    }

    public void alertHistory() {
        View view1;
        view1 = LayoutInflater.from(ComparisionRecord.this).inflate(R.layout.alert_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(ComparisionRecord.this).create();
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
            String query = "select * from " + TABLENAME1;
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
    /*public void getUserDisplay(){
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
}
