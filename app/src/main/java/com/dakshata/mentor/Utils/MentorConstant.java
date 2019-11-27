package com.dakshata.mentor.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import com.dakshata.mentor.MainActivity;
import com.dakshata.mentor.models.QuestListDto;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Umesh on 3/8/2018.
 */

public class MentorConstant {

    /*public static boolean isFormMentorVisitFilled;
    public static boolean isFormLaborRoomStaffFilled;
    public static boolean isFormInfrastructureFilled;
    public static boolean isFormEssentialRoomFilled;
    public static boolean isFormAdherenceFilled;
    public static boolean isFormTopicsCoveredFilled;
    public static boolean isFormRecordingAndReportingFilled;
    public static boolean isFormNextVisitPlannedFilled;
    public static boolean isFormRemarksFilled;*/

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static double latitude;
    public static double longitude;
    public static boolean dh;
    public static boolean sdh;
    public static boolean chc;
    public static boolean phc;
    public static boolean sc;
    public static boolean ah;
    public static boolean rh;
    public static boolean mch;
    public static boolean ggh;
    public static boolean DISPENSARY;
    public static boolean TH_GMH;
    public static boolean TH_GGH;
    public static boolean TH_GVH;
    public static boolean TH_KGH;
    public static boolean TH_RIMS;
    public static boolean RH;
    public static int recordId = 0;
    public static int recordFromDraft = 0;
    public static boolean whichBlockCalled;
    public static int currentMonth=0;
    public static int currentDate=0;
    public static int currentYear=0;
    public static int isWorkPlanSaved=0;

    public static String getAddress(Activity activity, double lat, double lng) {
        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
        String add = " Latitude : " + lat + ",    Longitude : " + lng;
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            add = obj.getAddressLine(0).toString();
//            add = add + "\n" + obj.getCountryName();
//            add = add + "\n" + obj.getCountryCode();
//            add = add + "\n" + obj.getSubAdminArea();
//            add = add + "\n" + obj.getAdminArea();
//            add = add + "\n" + obj.getPostalCode();

//            add = add + "\n" + obj.getLocality();
//            add = add + "\n" + obj.getSubThoroughfare();
        } catch (Exception e) {
            e.printStackTrace();
            add = " Latitude : " + lat + ",    Longitude : " + lng;
        }
        return add;
    }

    public static AlertDialog alert;
    public static void buildAlertMessageNoGps(final Activity act) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(act);
        builder.setMessage("Your GPS seems to be disabled, please enable it first from Settings.")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        act.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });
        alert = builder.create();
        alert.show();
    }

    public static void turnGPSOn(Activity act) {

        String provider = android.provider.Settings.Secure.getString(act.getContentResolver(),android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (!provider.contains("gps")) {
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings","com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            act.sendBroadcast(poke);
        }
    }

    @SuppressLint("NewApi")
    public static QuestListDto prepareJsonData(JSONArray jsonArray) {
        JSONObject quesJobj = null;
        JSONArray subQuesArray;
        JSONArray quesArray = new JSONArray();
        JSONObject s_q_jobj;
        JSONObject mainJsonobj = new JSONObject();
        JSONArray jsonArrayForSubQuestionJson = null;

        try {
          JSONObject jsonObjectForSubQuestionJson = new JSONObject(MentorConstant.sub_question_json);
            jsonArrayForSubQuestionJson = jsonObjectForSubQuestionJson.getJSONArray("sub_ques_array");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                quesJobj = new JSONObject();
                quesJobj.put("q_code", jsonArray.getJSONObject(i).getString("q_code"));
                quesJobj.put("q_text", jsonArray.getJSONObject(i).getString("questions_name"));
                quesJobj.put("q_ans", "");
                quesJobj.put("q_selection", 0);
                subQuesArray = new JSONArray();
                for (int j = 0; j < jsonArrayForSubQuestionJson.length(); j++) {
                    s_q_jobj = new JSONObject();
                    if (jsonArrayForSubQuestionJson.getJSONObject(j).getString("s_q_id").equalsIgnoreCase(String.valueOf((i + 1)))) {
                        s_q_jobj.put("s_q_id", jsonArrayForSubQuestionJson.getJSONObject(j).getString("s_q_id"));
                        s_q_jobj.put("s_q_text", jsonArrayForSubQuestionJson.getJSONObject(j).getString("s_q_text"));
                        s_q_jobj.put("s_q_code", jsonArrayForSubQuestionJson.getJSONObject(j).getString("s_q_code"));
                        s_q_jobj.put("s_q_ans", jsonArrayForSubQuestionJson.getJSONObject(j).getString("s_q_ans"));
                        s_q_jobj.put("isSelected", false);
                        subQuesArray.put(s_q_jobj);
                    }
                }
                quesJobj.put("sub_ques_array", subQuesArray);
                quesArray.put(quesJobj);

            }
            mainJsonobj.put("ques_array", quesArray);

//            QuestListDto questListDto = new Gson().fromJson(mainJsonobj.toString(), QuestListDto.class);
//            System.out.println("final_outPut" + mainJsonobj);

            return new Gson().fromJson(mainJsonobj.toString(), QuestListDto.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Gson().fromJson(mainJsonobj.toString(), QuestListDto.class);
    }

    public static String sub_question_json = "{\n" +
            "  \"sub_ques_array\": [\n" +
            "    {\n" +
            "      \"s_q_id\": \"1\",\n" +
            "      \"s_q_text\": \"Takes obstetric, medical and surgical history\",\n" +
            "      \"s_q_code\": \"f1_q1_s1\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"1\",\n" +
            "      \"s_q_text\": \"Assesses gestational age through either LMP or Fundal height or USG (if available)\",\n" +
            "      \"s_q_code\": \"f1_q1_s2\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"1\",\n" +
            "      \"s_q_text\": \"Functional Doppler/fetoscope/stethoscope at point of use is available\",\n" +
            "      \"s_q_code\": \"f1_q1_s3\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"1\",\n" +
            "      \"s_q_text\": \"Records fetal heart rate at admission\",\n" +
            "      \"s_q_code\": \"f1_q1_s4\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"1\",\n" +
            "      \"s_q_text\": \"Records mother`s BP at admission\",\n" +
            "      \"s_q_code\": \"f1_q1_s5\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"1\",\n" +
            "      \"s_q_text\": \"Records mother' s temperature at admission\",\n" +
            "      \"s_q_code\": \"f1_q1_s6\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    \n" +
            "    \n" +
            "    {\n" +
            "      \"s_q_id\": \"2\",\n" +
            "      \"s_q_text\": \"Conducts PV examination only as indicated (4 hourly or based) on clinical indication\",\n" +
            "      \"s_q_code\": \"f1_q2_s1\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"2\",\n" +
            "      \"s_q_text\": \"Performs hand hygiene\",\n" +
            "      \"s_q_code\": \"f1_q2_s2\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"2\",\n" +
            "      \"s_q_text\": \"Wears gloves on both the hands with correct technique\",\n" +
            "      \"s_q_code\": \"f1_q2_s3\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"2\",\n" +
            "      \"s_q_text\": \"Antiseptic solution is available\",\n" +
            "      \"s_q_code\": \"f1_q2_s4\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"2\",\n" +
            "      \"s_q_text\": \"Cleans the perineum appropriately before conducting PV examination\",\n" +
            "      \"s_q_code\": \"f1_q2_s5\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"2\",\n" +
            "      \"s_q_text\": \"Records the finding of PV examination (in Case sheet/Partograph during active phase of labour)\",\n" +
            "      \"s_q_code\": \"f1_q2_s6\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    \n" +
            "    \n" +
            "    {\n" +
            "      \"s_q_id\": \"3\",\n" +
            "      \"s_q_text\": \"Rapid HIV kit is available\",\n" +
            "      \"s_q_code\": \"f1_q3_s1\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"3\",\n" +
            "      \"s_q_text\": \"Checks for test results or recommends testing if not done\",\n" +
            "      \"s_q_code\": \"f1_q3_s2\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"3\",\n" +
            "      \"s_q_text\": \"Provides ART for seropositive mother\",\n" +
            "      \"s_q_code\": \"f1_q3_s3\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"3\",\n" +
            "      \"s_q_text\": \"Link seropositive mother to ART center\",\n" +
            "      \"s_q_code\": \"f1_q3_s4\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"3\",\n" +
            "      \"s_q_text\": \"Nevirapine syrup is available\",\n" +
            "      \"s_q_code\": \"f1_q3_s5\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"3\",\n" +
            "      \"s_q_text\": \"Provides syrup Nevirapine to newborns of HIV seropositive and refers her to ARTC after delivery\",\n" +
            "      \"s_q_code\": \"f1_q3_s6\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    \n" +
            "    \n" +
            "    {\n" +
            "      \"s_q_id\": \"4\",\n" +
            "      \"s_q_text\": \"Checks mother’s history related to maternal infection\",\n" +
            "      \"s_q_code\": \"f1_q4_s1\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"4\",\n" +
            "      \"s_q_text\": \"Records mother' s temperature at admission\",\n" +
            "      \"s_q_code\": \"f1_q4_s2\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"4\",\n" +
            "      \"s_q_text\": \"Assess the appropriateness of administration of antibiotics at admission.\",\n" +
            "      \"s_q_code\": \"f1_q4_s3\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"4\",\n" +
            "      \"s_q_text\": \"Assess the appropriateness of administration of antibiotics before discharge.\",\n" +
            "      \"s_q_code\": \"f1_q4_s4\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"4\",\n" +
            "      \"s_q_text\": \"Gives correct regimen of antibiotics\",\n" +
            "      \"s_q_code\": \"f1_q4_s5\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    \n" +
            "    \n" +
            "    {\n" +
            "      \"s_q_id\": \"5\",\n" +
            "      \"s_q_text\": \"Correctly estimates gestational age to confirm that labour is pre term through fundal height/LMP/USG (if available)\",\n" +
            "      \"s_q_code\": \"f1_q5_s1\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"5\",\n" +
            "      \"s_q_text\": \"Identifies  condition that may lead to pre term delivery (severe Pre-eclampsia, Eclampsia, APH, Pre term Pre labour Rupture Of Membrane) and records them\",\n" +
            "      \"s_q_code\": \"f1_q5_s2\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"5\",\n" +
            "      \"s_q_text\": \"administration of antenatal corticosteroids in preterm labor and condition leading to preterm delivery (24 to 34 week)\",\n" +
            "      \"s_q_code\": \"f1_q5_s3\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"5\",\n" +
            "      \"s_q_text\": \"Give correct regimen of corticosteroid\",\n" +
            "      \"s_q_code\": \"f1_q5_s4\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"5\",\n" +
            "      \"s_q_text\": \"Ensures specialist attention (care by Pediatrician)\",\n" +
            "      \"s_q_code\": \"f1_q5_s5\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    \n" +
            "    \n" +
            "    {\n" +
            "      \"s_q_id\": \"6\",\n" +
            "      \"s_q_text\": \"Records mother`s BP at admission\",\n" +
            "      \"s_q_code\": \"f1_q6_s1\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"6\",\n" +
            "      \"s_q_text\": \"Identifies danger signs or presence of convulsions\",\n" +
            "      \"s_q_code\": \"f1_q6_s2\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"6\",\n" +
            "      \"s_q_text\": \"Inj. MgSO4 is appropriately administered\",\n" +
            "      \"s_q_code\": \"f1_q6_s3\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"6\",\n" +
            "      \"s_q_text\": \"Antihypertensives are available\",\n" +
            "      \"s_q_code\": \"f1_q6_s4\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"6\",\n" +
            "      \"s_q_text\": \"Facilitates prescription of anti-hypertensive\",\n" +
            "      \"s_q_code\": \"f1_q6_s5\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"6\",\n" +
            "      \"s_q_text\": \"Ensures specialist attention for care of mother and newborn\",\n" +
            "      \"s_q_code\": \"f1_q6_s6\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"6\",\n" +
            "      \"s_q_text\": \"Performs nursing care\",\n" +
            "      \"s_q_code\": \"f1_q6_s7\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    \n" +
            "    \n" +
            "    {\n" +
            "      \"s_q_id\": \"7\",\n" +
            "      \"s_q_text\": \"Initiates Partograph plotting once the cervical dilatation is >=4 cm\",\n" +
            "      \"s_q_code\": \"f1_q7_s1\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"7\",\n" +
            "      \"s_q_text\": \"Plots all parameters\",\n" +
            "      \"s_q_code\": \"f1_q7_s2\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"7\",\n" +
            "      \"s_q_text\": \"Provider interprets partograph correctly and adjusts care according to findings\",\n" +
            "      \"s_q_code\": \"f1_q7_s3\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    \n" +
            "    \n" +
            "    {\n" +
            "      \"s_q_id\": \"8\",\n" +
            "      \"s_q_text\": \"Treats woman and her companion cordially and respectfully (RMC), ensures privacy and confidentiality to woman during her stay\",\n" +
            "      \"s_q_code\": \"f1_q8_s1\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"8\",\n" +
            "      \"s_q_text\": \"Encourages the presence of birth companion during labour\",\n" +
            "      \"s_q_code\": \"f1_q8_s2\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"8\",\n" +
            "      \"s_q_text\": \"Explains danger signs to mother and her companion during her stay (for mother and baby)\",\n" +
            "      \"s_q_code\": \"f1_q8_s3\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"8\",\n" +
            "      \"s_q_text\": \"Explains important care activities to mother and her companion during her stay (for mother and baby)\",\n" +
            "      \"s_q_code\": \"f1_q8_s4\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    \n" +
            "    \n" +
            "    {\n" +
            "      \"s_q_id\": \"9\",\n" +
            "      \"s_q_text\": \"Ensures required number of sterile/HLD delivery tray is available\",\n" +
            "      \"s_q_code\": \"f1_q9_s1\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"9\",\n" +
            "      \"s_q_text\": \"Designated new born corner is present\",\n" +
            "      \"s_q_code\": \"f1_q9_s2\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"9\",\n" +
            "      \"s_q_text\": \"Ensures functional items for newborn care and resuscitation\",\n" +
            "      \"s_q_code\": \"f1_q9_s3\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"9\",\n" +
            "      \"s_q_text\": \"Switches radiant warmer ‘on’ 30 min before childbirth\",\n" +
            "      \"s_q_code\": \"f1_q9_s4\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    \n" +
            "    \n" +
            "    {\n" +
            "      \"s_q_id\": \"10\",\n" +
            "      \"s_q_text\": \"Antiseptic solution (Betadine/Savlon) is available (refer above)\",\n" +
            "      \"s_q_code\": \"f1_q10_s1\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"10\",\n" +
            "      \"s_q_text\": \"Performs hand hygiene\",\n" +
            "      \"s_q_code\": \"f1_q10_s2\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"10\",\n" +
            "      \"s_q_text\": \"Wears gloves on both the hand with correct technique\",\n" +
            "      \"s_q_code\": \"f1_q10_s3\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"10\",\n" +
            "      \"s_q_text\": \"Ensures six cleans while conducting delivery\",\n" +
            "      \"s_q_code\": \"f1_q10_s4\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"10\",\n" +
            "      \"s_q_text\": \"Performs an episiotomy only if indicated\",\n" +
            "      \"s_q_code\": \"f1_q10_s5\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"10\",\n" +
            "      \"s_q_text\": \"Allows spontaneous delivery of head by flexing it and giving perineal support; manages cord round the neck; assists delivery of shoulders and body\",\n" +
            "      \"s_q_code\": \"f1_q10_s6\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    \n" +
            "    \n" +
            "    {\n" +
            "      \"s_q_id\": \"11\",\n" +
            "      \"s_q_text\": \"Delivers the baby on mother’s abdomen\",\n" +
            "      \"s_q_code\": \"f1_q11_s1\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"11\",\n" +
            "      \"s_q_text\": \"If breathing is normal, dries the baby immediately and wraps in second warm towel\",\n" +
            "      \"s_q_code\": \"f1_q11_s2\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"11\",\n" +
            "      \"s_q_text\": \"Performs delayed cord clamping and cutting\",\n" +
            "      \"s_q_code\": \"f1_q11_s3\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"11\",\n" +
            "      \"s_q_text\": \"Initiates breast feeding within one hour of birth\",\n" +
            "      \"s_q_code\": \"f1_q11_s4\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"11\",\n" +
            "      \"s_q_text\": \"Baby weighing scale is available\",\n" +
            "      \"s_q_code\": \"f1_q11_s5\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"11\",\n" +
            "      \"s_q_text\": \"Weighs the baby\",\n" +
            "      \"s_q_code\": \"f1_q_s6\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"11\",\n" +
            "      \"s_q_text\": \"Administers Vitamin K\",\n" +
            "      \"s_q_code\": \"f1_q11_s7\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    \n" +
            "    \n" +
            "    {\n" +
            "      \"s_q_id\": \"12\",\n" +
            "      \"s_q_text\": \"Shoulder roll is available\",\n" +
            "      \"s_q_code\": \"f1_q12_s1\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"12\",\n" +
            "      \"s_q_text\": \"Performs following steps within first 30 seconds on mothers abdomen:  Suction if indicated; dries the baby; immediate clamping and cutting the cord and shifting to radiant warmer if still not breathing\",\n" +
            "      \"s_q_code\": \"f1_q12_s2\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"12\",\n" +
            "      \"s_q_text\": \"Performs following steps within first 30 seconds under radiant warmer:  Positioning, Suctioning, Stimulation, Repositioning (PSSR)\",\n" +
            "      \"s_q_code\": \"f1_q12_s3\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"12\",\n" +
            "      \"s_q_text\": \"Initiates bag and mask ventilation for 30 seconds if baby still not breathing\",\n" +
            "      \"s_q_code\": \"f1_q12_s4\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"12\",\n" +
            "      \"s_q_text\": \"Functional oxygen cylinder (with wrench)  with new born mask is available\",\n" +
            "      \"s_q_code\": \"f1_q12_s5\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"12\",\n" +
            "      \"s_q_text\": \"Assesses breathing, if still not breathing continues bag and mask ventilation; starts oxygen\",\n" +
            "      \"s_q_code\": \"f1_q12_s6\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"12\",\n" +
            "      \"s_q_text\": \"Checks heart rate/cord pulsation\",\n" +
            "      \"s_q_code\": \"f1_q12_s7\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"12\",\n" +
            "      \"s_q_text\": \"Calls for advance help/arranges referral\",\n" +
            "      \"s_q_code\": \"f1_q12_s8\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    \n" +
            "    \n" +
            "    {\n" +
            "      \"s_q_id\": \"13\",\n" +
            "      \"s_q_text\": \"Palpates mothers abdomen to rule out second baby\",\n" +
            "      \"s_q_code\": \"f1_q13_s1\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"13\",\n" +
            "      \"s_q_text\": \"Administers Injection Oxytocin 10 I.U. IM/IV within one minute of delivery of baby\",\n" +
            "      \"s_q_code\": \"f1_q13_s2\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"13\",\n" +
            "      \"s_q_text\": \"Performs controlled cord traction (CCT) during contraction\",\n" +
            "      \"s_q_code\": \"f1_q13_s3\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"13\",\n" +
            "      \"s_q_text\": \"Performs uterine massage\",\n" +
            "      \"s_q_code\": \"f1_q13_s4\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"13\",\n" +
            "      \"s_q_text\": \"Checks placenta and membranes for completeness before discarding\",\n" +
            "      \"s_q_code\": \"f1_q13_s5\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    \n" +
            "    \n" +
            "    {\n" +
            "      \"s_q_id\": \"14\",\n" +
            "      \"s_q_text\": \"Assesses uterine tone and bleeding per vaginum regularly\",\n" +
            "      \"s_q_code\": \"f1_q14_s1\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"14\",\n" +
            "      \"s_q_text\": \"Calls for help /assistance, while continue uterine message\",\n" +
            "      \"s_q_code\": \"f1_q14_s2\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"14\",\n" +
            "      \"s_q_text\": \"Starts IV fluid\",\n" +
            "      \"s_q_code\": \"f1_q14_s3\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"14\",\n" +
            "      \"s_q_text\": \"Manages shock if present\",\n" +
            "      \"s_q_code\": \"f1_q14_s4\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"14\",\n" +
            "      \"s_q_text\": \"Administers uterotonics\",\n" +
            "      \"s_q_code\": \"f1_q14_s5\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"14\",\n" +
            "      \"s_q_text\": \"Identifies specific cause of PPH\",\n" +
            "      \"s_q_code\": \"f1_q14_s6\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"14\",\n" +
            "      \"s_q_text\": \"Treats specific cause of PPH\",\n" +
            "      \"s_q_code\": \"f1_q14_s7\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"14\",\n" +
            "      \"s_q_text\": \"Refers if required\",\n" +
            "      \"s_q_code\": \"f1_q14_s8\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    \n" +
            "    \n" +
            "    {\n" +
            "      \"s_q_id\": \"15\",\n" +
            "      \"s_q_text\": \"Looks for signs of infection in baby \",\n" +
            "      \"s_q_code\": \"f1_q15_s1\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"15\",\n" +
            "      \"s_q_text\": \"Measures baby's temperature\",\n" +
            "      \"s_q_code\": \"f1_q15_s2\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"15\",\n" +
            "      \"s_q_text\": \"Looks for signs of infection in mother\",\n" +
            "      \"s_q_code\": \"f1_q15_s3\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"15\",\n" +
            "      \"s_q_text\": \"Records mother's temperature\",\n" +
            "      \"s_q_code\": \"f1_q15_s4\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"15\",\n" +
            "      \"s_q_text\": \"Records mother's BP\",\n" +
            "      \"s_q_code\": \"f1_q15_s5\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    \n" +
            "    \n" +
            "    {\n" +
            "      \"s_q_id\": \"16\",\n" +
            "      \"s_q_text\": \"Counsels mother on importance of exclusive and on demand breast feeding\",\n" +
            "      \"s_q_code\": \"f1_q16_s1\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"16\",\n" +
            "      \"s_q_text\": \"Provides assistance on techniques of breast feeding and on minor problems encountered whenever needed\",\n" +
            "      \"s_q_code\": \"f1_q16_s2\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    \n" +
            "    \n" +
            "    {\n" +
            "      \"s_q_id\": \"17\",\n" +
            "      \"s_q_text\": \"Facilitates specialist care in newborn <1800 gm (refer to FBNC/seen by pediatrician)\",\n" +
            "      \"s_q_code\": \"f1_q17_s1\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"17\",\n" +
            "      \"s_q_text\": \"Facilitates assisted feeding whenever required\",\n" +
            "      \"s_q_code\": \"f1_q17_s2\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"17\",\n" +
            "      \"s_q_text\": \"Facilitates thermal management including kangaroo mother care\",\n" +
            "      \"s_q_code\": \"f1_q17_s3\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    \n" +
            "    \n" +
            "    {\n" +
            "      \"s_q_id\": \"18\",\n" +
            "      \"s_q_text\": \"Counsels on danger signs to mother at time of discharge\",\n" +
            "      \"s_q_code\": \"f1_q18_s1\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"18\",\n" +
            "      \"s_q_text\": \"Counsels on post partum family planning to mother at discharge\",\n" +
            "      \"s_q_code\": \"f1_q18_s2\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"18\",\n" +
            "      \"s_q_text\": \"Counsels on exclusive breast feeding to mother at discharge\",\n" +
            "      \"s_q_code\": \"f1_18_s3\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    \n" +
            "    \n" +
            "    {\n" +
            "      \"s_q_id\": \"19\",\n" +
            "      \"s_q_text\": \"Bleaching powder/ Sodium hypochloride solution is available (Gluteraldehyde solution for Instruments)\",\n" +
            "      \"s_q_code\": \"f1_q19_s1\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"19\",\n" +
            "      \"s_q_text\": \"Instruments and gloves is decontaminated after each use using chlorine solution\",\n" +
            "      \"s_q_code\": \"f1_q19_s2\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"19\",\n" +
            "      \"s_q_text\": \"0.5% chlorine solution is prepared daily\",\n" +
            "      \"s_q_code\": \"f1_q19_s3\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"19\",\n" +
            "      \"s_q_text\": \"Color coded bags for disposal of biomedical waste is available\",\n" +
            "      \"s_q_code\": \"f1_q19_s4\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"19\",\n" +
            "      \"s_q_text\": \"Biomedical waste is segregated and disposed of as per the guidelines\",\n" +
            "      \"s_q_code\": \"f1_q19_s5\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"19\",\n" +
            "      \"s_q_text\": \"Hand hygiene is performed before and after each procedure and sterile/HLD gloves are worn during delivery and internal examination\",\n" +
            "      \"s_q_code\": \"f1_q19_s6\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"19\",\n" +
            "      \"s_q_text\": \"Personal protective equipment (PPE) is available\",\n" +
            "      \"s_q_code\": \"f1_q19_s7\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"19\",\n" +
            "      \"s_q_text\": \"Puts on  PPE while conducting delivery\",\n" +
            "      \"s_q_code\": \"f1_q19_s8\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"s_q_id\": \"19\",\n" +
            "      \"s_q_text\": \"Delivery environment such as labour table, contaminated surfaces and floor are cleaned after each delivery\",\n" +
            "      \"s_q_code\": \"f1_q19_s9\",\n" +
            "      \"s_q_ans\": \"\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    public static String parseDate(String strDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return parseFormat.format(simpleDateFormat.parse(strDate)) + " 00:00:00";
        } catch (Exception e) {
            e.printStackTrace();
            return strDate;
        }
    }

}
