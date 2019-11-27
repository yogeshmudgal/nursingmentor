package com.dakshata.mentor;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dakshata.mentor.Utils.MentorConstant;
import com.dakshata.mentor.mydatecalendar.AppConstants;
import com.dakshata.mentor.mydatecalendar.EventModel;
import com.dakshata.mentor.mydatecalendar.GetEventListListener;
import com.dakshata.mentor.mydatecalendar.MyDynamicCalendar;
import com.dakshata.mentor.mydatecalendar.OnDateClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.dakshata.mentor.JhpiegoDatabase.COL_BLOCK;
import static com.dakshata.mentor.JhpiegoDatabase.COL_DIST;
import static com.dakshata.mentor.JhpiegoDatabase.COL_DOV;
import static com.dakshata.mentor.JhpiegoDatabase.COL_FACILITYNAME;
import static com.dakshata.mentor.JhpiegoDatabase.COL_FACILITYTYPE;
import static com.dakshata.mentor.JhpiegoDatabase.COL_ID;
import static com.dakshata.mentor.JhpiegoDatabase.COL_IMAGE1;
import static com.dakshata.mentor.JhpiegoDatabase.COL_IMAGE2;
import static com.dakshata.mentor.JhpiegoDatabase.COL_IMAGE3;
import static com.dakshata.mentor.JhpiegoDatabase.COL_IMAGE4;
import static com.dakshata.mentor.JhpiegoDatabase.COL_LEVEL;
import static com.dakshata.mentor.JhpiegoDatabase.COL_SESSION;
import static com.dakshata.mentor.JhpiegoDatabase.COL_STATE;
import static com.dakshata.mentor.JhpiegoDatabase.COL_SYNC_STATUS;
import static com.dakshata.mentor.JhpiegoDatabase.COL_USERNAME;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME1;
import static com.dakshata.mentor.JhpiegoDatabase.TABLE_WorkPlan;


/**
 * Created by Umesh Kumar on 4-2-2019.
 */
public class CalenderSelectionActivity extends AppCompatActivity {

    JhpiegoDatabase jhpiegoDatabase;
    ProgressDialog progressDialog;
    public static int indexSize = 0;
    SharedPreferences sharedPreferences, sharedPreferences2, sharedPreferencescount;

    Toolbar toolbar;
    SharedPreferences sh_Pref;
    SharedPreferences.Editor editor, editor2, editorcount;
    ProgressBar progressBar;
    JhpiegoDatabase mydb;

    MyDynamicCalendar myCalendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.menu_image_opt3));
        setSupportActionBar(toolbar);
        TextView header_name = (TextView) findViewById(R.id.header_name);
        header_name.setText("Workplan");
//        ImageView back = (ImageView) findViewById(R.id.back);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Pback(view);
//            }
//        });

        try {
            sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
            editor = sh_Pref.edit();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        initViews();

        progressBar = new ProgressBar(this);
        progressDialog = new ProgressDialog(this);
        jhpiegoDatabase = new JhpiegoDatabase(this);
        try {
            sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
            editor = sh_Pref.edit();

            mydb = new JhpiegoDatabase(this);
            SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();

            sqLiteDatabase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Calender View
        myCalendar = (MyDynamicCalendar) findViewById(R.id.myCalendar);

        myCalendar.showMonthView();

        myCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onClick(Date date) {
                Log.e("date", String.valueOf(date));
            }

            @Override
            public void onLongClick(Date date) {
                Log.e("date", String.valueOf(date));
            }
        });

//        myCalendar.setCalendarBackgroundColor(R.color.gray);

//        myCalendar.setHeaderBackgroundColor(getResources().getColor(R.color.primaryDark_light));
//        myCalendar.setHeaderBackgroundColor(R.color.black);

//        myCalendar.setHeaderTextColor(getResources().getColor(R.color.white));
//        myCalendar.setHeaderTextColor(R.color.white);

//        myCalendar.setNextPreviousIndicatorColor(getResources().getColor(R.color.white));
//        myCalendar.setNextPreviousIndicatorColor(R.color.black);

//        myCalendar.setWeekDayLayoutBackgroundColor(getResources().getColor(R.color.newPrimary));
//        myCalendar.setWeekDayLayoutBackgroundColor(R.color.black);

//        myCalendar.setWeekDayLayoutTextColor(getResources().getColor(R.color.black));
//        myCalendar.setWeekDayLayoutTextColor(R.color.black);

//        myCalendar.isSaturdayOff(true, "#ffffff", "#ff0000");
//        myCalendar.isSaturdayOff(true, R.color.white, R.color.red);

//        myCalendar.isSundayOff(true, "#658745", "#254632");
//        myCalendar.isSundayOff(true, R.color.white, R.color.red);

//        myCalendar.setExtraDatesOfMonthBackgroundColor(R.color.black);
//        myCalendar.setExtraDatesOfMonthTextColor(R.color.black);

//        myCalendar.setDatesOfMonthBackgroundColor(R.drawable.event_view);
//        myCalendar.setDatesOfMonthBackgroundColor(R.color.black);

//        myCalendar.setHolidayCellBackgroundColor(getResources().getColor(R.color.white));
//        myCalendar.setHolidayCellBackgroundColor("#ffffff");

        myCalendar.setCalendarBackgroundColor(getResources().getColor(R.color.white));

        myCalendar.setEventCellBackgroundColor(getResources().getColor(R.color.white));
//        myCalendar.setEventCellBackgroundColor(R.color.black);

        myCalendar.setEventCellTextColor(getResources().getColor(R.color.black));
//        myCalendar.setEventCellTextColor(R.color.black);

        myCalendar.setBelowMonthEventTextColor(getResources().getColor(R.color.black));
//        myCalendar.setBelowMonthEventTextColor(R.color.black);

        myCalendar.setBelowMonthEventDividerColor(getResources().getColor(R.color.newPrimaryDark));
//        myCalendar.setBelowMonthEventDividerColor(R.color.black);

        myCalendar.setHolidayCellTextColor(getResources().getColor(R.color.white));

        myCalendar.setCurrentDateTextColor(getResources().getColor(R.color.white));
        myCalendar.setCurrentDateTextColor("#ffffff");
        myCalendar.setCurrentDateBackgroundColor(getResources().getColor(R.color.primaryColor));
        myCalendar.setCurrentDateBackgroundColor("#1a748d");

        myCalendar.setExtraDatesOfMonthBackgroundColor(getResources().getColor(R.color.grey_dark));
        myCalendar.setExtraDatesOfMonthBackgroundColor("#c1c1c1");
        myCalendar.setExtraDatesOfMonthTextColor(getResources().getColor(R.color.grey_dark));
        myCalendar.setExtraDatesOfMonthTextColor("#c1c1c1");

        myCalendar.setDatesOfMonthBackgroundColor(getResources().getColor(R.color.calendar_background_color));
        myCalendar.setDatesOfMonthTextColor(getResources().getColor(R.color.white));
        myCalendar.setDatesOfMonthTextColor("#ffffff");

        myCalendar.setWeekDayLayoutTextColor(getResources().getColor(R.color.white));


        setEventListInCalendar();

        /*myCalendar.getEventList(new GetEventListListener() {
            @Override
            public void eventList(ArrayList<EventModel> eventList) {

                Log.e("tag", "eventList.size():-" + eventList.size());
                for (int i = 0; i < eventList.size(); i++) {
                    Log.e("tag", "eventList.getStrName:-" + eventList.get(i).getStrName());
                }

            }
        });*/

//        myCalendar.updateEvent(0, "5-10-2019", "8:00", "8:15", "Today Event 111111");
//        myCalendar.deleteEvent(2);
//        myCalendar.deleteAllEvent();

        myCalendar.setHolidayCellClickable(false);
//        myCalendar.addHoliday("13-02-2019");

        MentorConstant.currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        MentorConstant.currentDate = Calendar.getInstance().get(Calendar.DATE);
        MentorConstant.currentYear = Calendar.getInstance().get(Calendar.YEAR);
        myCalendar.setCalendarDate(MentorConstant.currentDate, MentorConstant.currentMonth, MentorConstant.currentYear);

        showMonthViewWithBelowEvents();

    }

    private void setEventListInCalendar() {
        if (AppConstants.eventList != null) AppConstants.eventList.clear();
        // Dates - Villages Name - Anmd Name - VHND Site Name - Other

        SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_WorkPlan, new String[]{COL_ID, COL_USERNAME, COL_LEVEL, COL_SESSION, COL_BLOCK, COL_STATE, COL_DIST, COL_FACILITYTYPE, COL_FACILITYNAME, "workplan_date", COL_SYNC_STATUS}, null, new String[]{}, null, null, "workplan_date" + " ASC");
        // Cursor cursor = jhpiegoDatabase.getLastVisitData(username);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
//                String status = "Pending";
                String colBlock = cursor.getString(cursor.getColumnIndex(COL_BLOCK));
                String colState = cursor.getString(cursor.getColumnIndex(COL_STATE));
                String colDist = cursor.getString(cursor.getColumnIndex(COL_DIST));
                String colFacilityType = cursor.getString(cursor.getColumnIndex(COL_FACILITYTYPE));
                String colFacilityName = cursor.getString(cursor.getColumnIndex(COL_FACILITYNAME));
                String colSession = cursor.getString(cursor.getColumnIndex(COL_SESSION));
                String colDov = cursor.getString(cursor.getColumnIndex("workplan_date"));
                String status = cursor.getString(cursor.getColumnIndex(COL_SYNC_STATUS));

//                Cursor cursorDetailOfVisit = sqLiteDatabase.query(TABLENAME1, new String[]{COL_ID, COL_BLOCK, COL_STATE, COL_DIST, COL_FACILITYNAME, COL_FACILITYTYPE, COL_USERNAME, COL_SESSION, COL_DOV},
//                        "(" + COL_DOV + "=? and " + COL_SESSION + "=?)",
//                        new String[]{colDov, colSession}, null, null, null);
//
//                if (cursorDetailOfVisit.getCount() == 0) {
//                    cursorDetailOfVisit = null;
//                    cursorDetailOfVisit = sqLiteDatabase.query(TABLENAME1, new String[]{COL_ID, COL_BLOCK, COL_STATE, COL_DIST, COL_FACILITYNAME, COL_FACILITYTYPE, COL_USERNAME, COL_SESSION, COL_DOV},
//                            "(" + COL_BLOCK + "=? and " + COL_STATE + "=? and " + COL_DIST + "=? and " + COL_FACILITYTYPE + "=? and " + COL_FACILITYNAME + "=?) or " + COL_SESSION + "=?",
//                            new String[]{colBlock, colState, colDist, colFacilityType, colFacilityName, colSession}, null, null, null);
//                }
//                if (cursorDetailOfVisit.getCount() > 0) {
//                    cursorDetailOfVisit.moveToFirst();
//                    Date dateDov = new Date(cursorDetailOfVisit.getString(cursorDetailOfVisit.getColumnIndex(COL_DOV)));
//                    Date dateWorkPlanDate = new Date(cursor.getString(cursor.getColumnIndex("workplan_date")));
//                    if (cursorDetailOfVisit.getString(cursorDetailOfVisit.getColumnIndex(COL_BLOCK)).equalsIgnoreCase(colBlock) &&
//                            cursorDetailOfVisit.getString(cursorDetailOfVisit.getColumnIndex(COL_STATE)).equalsIgnoreCase(colState) &&
//                            cursorDetailOfVisit.getString(cursorDetailOfVisit.getColumnIndex(COL_DIST)).equalsIgnoreCase(colDist) &&
//                            cursorDetailOfVisit.getString(cursorDetailOfVisit.getColumnIndex(COL_FACILITYTYPE)).equalsIgnoreCase(colFacilityType) &&
//                            cursorDetailOfVisit.getString(cursorDetailOfVisit.getColumnIndex(COL_FACILITYNAME)).equalsIgnoreCase(colFacilityName)) {
//                        if ((dateDov.getTime() == dateWorkPlanDate.getTime()) /*|| dateDov.getTime() < dateWorkPlanDate.getTime()*/) {
//                            status = "Timely Completed";
////                            jhpiegoDatabase.updateWorkPlanStatus(cursorDetailOfVisit.getString(cursorDetailOfVisit.getColumnIndex(COL_ID)), status);
//                        } else if (dateDov.getTime() > dateWorkPlanDate.getTime()) {
//                            status = "Delayed Completed";
////                            jhpiegoDatabase.updateWorkPlanStatus(cursorDetailOfVisit.getString(cursorDetailOfVisit.getColumnIndex(COL_ID)), status);
//                        } else {
//                            status = "Timely Completed";
//                        }
//                    }
//                }
                String levelOfFacility = "NA";
                if (!cursor.getString(cursor.getColumnIndex(COL_LEVEL)).equalsIgnoreCase("Select")) {
                    levelOfFacility = cursor.getString(cursor.getColumnIndex(COL_LEVEL));
                }
                myCalendar.addEvent(String.valueOf(cursor.getString(cursor.getColumnIndex("workplan_date"))),
                        String.valueOf(cursor.getString(cursor.getColumnIndex(COL_FACILITYTYPE)) + "-" + cursor.getString(cursor.getColumnIndex(COL_FACILITYNAME))),
                        String.valueOf(levelOfFacility),
                        String.valueOf(cursor.getString(cursor.getColumnIndex(COL_BLOCK)) + "/" + cursor.getString(cursor.getColumnIndex(COL_DIST))),
                        status);
            }
        }

//        myCalendar.addEvent("08-2-2019", "Village 1, Village 2, Village 3",  "Himani","Anmol Villages", "mother");
//        myCalendar.addEvent("15-2-2019", "Village 1, Village 2, Village 3",  "Jamuna","Anmol Aanganwari", "mother");
    }

    private void showMonthViewWithBelowEvents() {

        myCalendar.showMonthViewWithBelowEvents();
        /*Calendar calendar = Calendar.getInstance();
        myCalendar.setCalendarDate(Calendar.getInstance().get(Calendar.DATE), Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.YEAR));*/

        myCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onClick(Date date) {
                Log.e("date", String.valueOf(date));
            }

            @Override
            public void onLongClick(Date date) {
                Log.e("date", String.valueOf(date));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (myCalendar != null && MentorConstant.isWorkPlanSaved == 1) {
            MentorConstant.isWorkPlanSaved = 0;
            setEventListInCalendar();
            myCalendar.refreshCalendar();
//            try {
//                myCalendar.notifyAll();
//            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    public void Pback(View view) {
        super.onBackPressed();

    }

}
