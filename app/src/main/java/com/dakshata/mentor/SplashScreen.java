package com.dakshata.mentor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.dakshata.mentor.Utils.MentorConstant;
import com.dakshata.mentor.models.FormDatum;
import com.dakshata.mentor.models.QuestListDto;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.fabric.sdk.android.Fabric;

import static com.dakshata.mentor.JhpiegoDatabase.COL_1;
import static com.dakshata.mentor.JhpiegoDatabase.COL_ANSJSON;
import static com.dakshata.mentor.JhpiegoDatabase.COL_COUNT;
import static com.dakshata.mentor.JhpiegoDatabase.COL_DOV;
import static com.dakshata.mentor.JhpiegoDatabase.COL_FACILITYNAME;
import static com.dakshata.mentor.JhpiegoDatabase.COL_FACILITYTYPE;
import static com.dakshata.mentor.JhpiegoDatabase.COL_FAUPP;
import static com.dakshata.mentor.JhpiegoDatabase.COL_HIVPW;
import static com.dakshata.mentor.JhpiegoDatabase.COL_ID;
import static com.dakshata.mentor.JhpiegoDatabase.COL_IPW;
import static com.dakshata.mentor.JhpiegoDatabase.COL_IS_ASSESSMENT;
import static com.dakshata.mentor.JhpiegoDatabase.COL_IS_SUBMITTED;
import static com.dakshata.mentor.JhpiegoDatabase.COL_JSON;
import static com.dakshata.mentor.JhpiegoDatabase.COL_LATITUDE;
import static com.dakshata.mentor.JhpiegoDatabase.COL_LONGITUDE;
import static com.dakshata.mentor.JhpiegoDatabase.COL_PASSESSES;
import static com.dakshata.mentor.JhpiegoDatabase.COL_PASSISTS;
import static com.dakshata.mentor.JhpiegoDatabase.COL_PCIEA;
import static com.dakshata.mentor.JhpiegoDatabase.COL_PCONDUCTS;
import static com.dakshata.mentor.JhpiegoDatabase.COL_PCOUNSELS;
import static com.dakshata.mentor.JhpiegoDatabase.COL_PD;
import static com.dakshata.mentor.JhpiegoDatabase.COL_PE;
import static com.dakshata.mentor.JhpiegoDatabase.COL_PENSURES;
import static com.dakshata.mentor.JhpiegoDatabase.COL_PENSURESCARE;
import static com.dakshata.mentor.JhpiegoDatabase.COL_PENSURESEX;
import static com.dakshata.mentor.JhpiegoDatabase.COL_PIDENTIFIES;
import static com.dakshata.mentor.JhpiegoDatabase.COL_PMONITOR;
import static com.dakshata.mentor.JhpiegoDatabase.COL_PPERFORMS;
import static com.dakshata.mentor.JhpiegoDatabase.COL_PPERFORMSAM;
import static com.dakshata.mentor.JhpiegoDatabase.COL_PPREPARES;
import static com.dakshata.mentor.JhpiegoDatabase.COL_PWF;
import static com.dakshata.mentor.JhpiegoDatabase.COL_SESSION;
import static com.dakshata.mentor.JhpiegoDatabase.COL_UNIQUE_ID;
import static com.dakshata.mentor.JhpiegoDatabase.COL_USERNAME;
import static com.dakshata.mentor.JhpiegoDatabase.JSON_DATA;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME1;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME12;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME3;
import static com.dakshata.mentor.JhpiegoDatabase.TABLE_ClinicalStandards;

/**
 * Created by Aditya.v on 18-12-2017.
 */

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    private QuestListDto questListDto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity


                SharedPreferences sharedPreferences = getSharedPreferences("CheckOldTable", MODE_PRIVATE);
                SharedPreferences.Editor sharedPreferencesEdit = sharedPreferences.edit();

                if (!sharedPreferences.getString("CheckReplaced", "0").equalsIgnoreCase("1")) {
                    JhpiegoDatabase jhpiegoDatabase = new JhpiegoDatabase(SplashScreen.this);
                    SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getWritableDatabase();
                    try {
                        Cursor cursorQuestions = sqLiteDatabase.query(TABLENAME12, new String[]{COL_ID, COL_1}, "", new String[]{}, null, null, null);
                        if (cursorQuestions.getCount() > 0) {
                            cursorQuestions.moveToLast();
                            JSONObject jsonObject = null;
                            jsonObject = new JSONObject(cursorQuestions.getString(cursorQuestions.getColumnIndex(COL_1)));
                            JSONArray jsonArray1 = jsonObject.getJSONArray("f5");
                            Log.e("Array Length", "" + jsonArray1.length());
                            questListDto = MentorConstant.prepareJsonData(jsonArray1);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        Cursor cursor = sqLiteDatabase.query(TABLENAME3, new String[]{COL_SESSION, COL_PWF, COL_PCIEA, COL_HIVPW, COL_IPW, COL_PD, COL_PE, COL_PMONITOR, COL_PENSURES, COL_PPREPARES, COL_PASSISTS, COL_PCONDUCTS, COL_PPERFORMS, COL_PPERFORMSAM, COL_PIDENTIFIES, COL_PASSESSES, COL_PENSURESEX, COL_PENSURESCARE, COL_PCOUNSELS, COL_FAUPP, COL_UNIQUE_ID, COL_COUNT, COL_IS_ASSESSMENT, COL_IS_SUBMITTED, COL_LATITUDE, COL_LONGITUDE, COL_USERNAME}, null, new String[]{}, null, null, null);
                        if (cursor != null && cursor.getCount() > 0) {
                            cursor.moveToFirst();
                            String col_1 = cursor.getString(cursor.getColumnIndex(COL_PWF));
                            String col_2 = cursor.getString(cursor.getColumnIndex(COL_PCIEA));
                            String col_3 = cursor.getString(cursor.getColumnIndex(COL_HIVPW));
                            String col_4 = cursor.getString(cursor.getColumnIndex(COL_IPW));
                            String col_5 = cursor.getString(cursor.getColumnIndex(COL_PD));
                            String col_6 = cursor.getString(cursor.getColumnIndex(COL_PE));
                            String col_7 = cursor.getString(cursor.getColumnIndex(COL_PMONITOR));
                            String col_8 = cursor.getString(cursor.getColumnIndex(COL_PENSURES));
                            String col_9 = cursor.getString(cursor.getColumnIndex(COL_PPREPARES));
                            String col_10 = cursor.getString(cursor.getColumnIndex(COL_PASSISTS));
                            String col_11 = cursor.getString(cursor.getColumnIndex(COL_PCONDUCTS));
                            String col_12 = cursor.getString(cursor.getColumnIndex(COL_PPERFORMS));
                            String col_13 = cursor.getString(cursor.getColumnIndex(COL_PPERFORMSAM));
                            String col_14 = cursor.getString(cursor.getColumnIndex(COL_PIDENTIFIES));
                            String col_15 = cursor.getString(cursor.getColumnIndex(COL_PASSESSES));
                            String col_16 = cursor.getString(cursor.getColumnIndex(COL_PENSURESEX));
                            String col_17 = cursor.getString(cursor.getColumnIndex(COL_PENSURESCARE));
                            String col_18 = cursor.getString(cursor.getColumnIndex(COL_PCOUNSELS));
                            String col_19 = cursor.getString(cursor.getColumnIndex(COL_FAUPP));

                            for (int i = 0; i < questListDto.getQues_array().size(); i++) {

                                switch (i) {
                                    case 0:
                                        setValuesInJsonData(col_1, i);
                                        break;
                                    case 1:
                                        setValuesInJsonData(col_2, i);
                                        break;
                                    case 2:
                                        setValuesInJsonData(col_3, i);
                                        break;
                                    case 3:
                                        setValuesInJsonData(col_4, i);
                                        break;
                                    case 4:
                                        setValuesInJsonData(col_5, i);
                                        break;
                                    case 5:
                                        setValuesInJsonData(col_6, i);
                                        break;
                                    case 6:
                                        setValuesInJsonData(col_7, i);
                                        break;
                                    case 7:
                                        setValuesInJsonData(col_8, i);
                                        break;
                                    case 8:
                                        setValuesInJsonData(col_9, i);
                                        break;
                                    case 9:
                                        setValuesInJsonData(col_10, i);
                                        break;
                                    case 10:
                                        setValuesInJsonData(col_11, i);
                                        break;
                                    case 11:
                                        setValuesInJsonData(col_12, i);
                                        break;
                                    case 12:
                                        setValuesInJsonData(col_13, i);
                                        break;
                                    case 13:
                                        setValuesInJsonData(col_14, i);
                                        break;
                                    case 14:
                                        setValuesInJsonData(col_15, i);
                                        break;
                                    case 15:
                                        setValuesInJsonData(col_16, i);
                                        break;
                                    case 16:
                                        setValuesInJsonData(col_17, i);
                                        break;
                                    case 17:
                                        setValuesInJsonData(col_18, i);
                                        break;
                                    case 18:
                                        setValuesInJsonData(col_19, i);
                                        break;
                                }

                            }

                            try {
                                jhpiegoDatabase.addClinicalStandardsNew(cursor.getString(cursor.getColumnIndex(COL_USERNAME)), new Gson().toJson(questListDto),
                                        String.valueOf(cursor.getString(cursor.getColumnIndex(COL_COUNT))), cursor.getString(cursor.getColumnIndex(COL_SESSION)),
                                        cursor.getString(cursor.getColumnIndex(COL_ANSJSON)), String.valueOf(cursor.getString(cursor.getColumnIndex(COL_LATITUDE))),
                                        String.valueOf(cursor.getString(cursor.getColumnIndex(COL_LONGITUDE))), String.valueOf(cursor.getString(cursor.getColumnIndex(COL_UNIQUE_ID))), 0);
                                jhpiegoDatabase.deleteTableClinicalOldData();
                                sqLiteDatabase.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        sharedPreferencesEdit.putString("CheckReplaced", "1");
                        sharedPreferencesEdit.commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        Intent i = new Intent(SplashScreen.this, JhpiegoLogin.class);
                        startActivity(i);
                    }

                } else {
                    Intent i = new Intent(SplashScreen.this, JhpiegoLogin.class);
                    startActivity(i);
                }
                finish();
            }

            private void setValuesInJsonData(String col_value, int i) {
                if (col_value != null || !col_value.equalsIgnoreCase("") || !col_value.equalsIgnoreCase("null")) {
                    if (col_value.equalsIgnoreCase("Yes")) {
                        questListDto.getQues_array().get(i).setQ_ans("Yes");
                        questListDto.getQues_array().get(i).setQ_total_selected(questListDto.getQues_array().get(i).getSub_ques_array().size());
                        for (int j = 0; j < questListDto.getQues_array().get(i).getSub_ques_array().size(); j++) {
                            questListDto.getQues_array().get(i).getSub_ques_array().get(j).setS_q_ans("Yes");
                        }
                    } else if (col_value.equalsIgnoreCase("No")) {
                        questListDto.getQues_array().get(i).setQ_ans("No");
                        questListDto.getQues_array().get(i).setQ_total_selected(questListDto.getQues_array().get(i).getSub_ques_array().size());
                        for (int j = 0; j < questListDto.getQues_array().get(i).getSub_ques_array().size(); j++) {
                            questListDto.getQues_array().get(i).getSub_ques_array().get(j).setS_q_ans("Yes");
                        }
                    } else {
                        questListDto.getQues_array().get(i).setQ_ans("");
                        questListDto.getQues_array().get(i).setQ_total_selected(0);
                        for (int j = 0; j < questListDto.getQues_array().get(i).getSub_ques_array().size(); j++) {
                            questListDto.getQues_array().get(i).getSub_ques_array().get(j).setS_q_ans("");
                        }
                    }
                } else {
                    questListDto.getQues_array().get(i).setQ_total_selected(0);
                    questListDto.getQues_array().get(i).setQ_ans("");
                }
            }
        }, SPLASH_TIME_OUT);
    }
}
