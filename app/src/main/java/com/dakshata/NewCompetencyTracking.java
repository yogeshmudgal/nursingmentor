package com.dakshata;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dakshata.mentor.CompetencyTracking;
import com.dakshata.mentor.CompetencyTrackingPdf;
import com.dakshata.mentor.JhpiegoDatabase;
import com.dakshata.mentor.R;
import com.dakshata.mentor.Utils.MentorConstant;
import com.dakshata.mentor.adapter.PACKAdapter;
import com.dakshata.mentor.adapter.TableViewAdapter;
import com.dakshata.mentor.listener.CustomOnItemClickListner;
import com.dakshata.mentor.models.CompetencyTrackingDto;
import com.dakshata.mentor.models.CompetencyTrackingParent;
import com.dakshata.mentor.models.F7;
import com.dakshata.mentor.models.FormDataQCodes;
import com.dakshata.mentor.models.QuetionsMaster;
import com.dakshata.mentor.mydatecalendar.GlobalMethods;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.GenericDeclaration;
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

public class NewCompetencyTracking extends AppCompatActivity implements LocationListener, View.OnClickListener {
    EditText editText;
    Button button_save, crAddMore,btnAddProviderData;
    JhpiegoDatabase jhpiegoDatabase;
    Toolbar toolbar;
    SharedPreferences sh_Pref, sharedPreferences, sharedPreferencescount;
    SharedPreferences.Editor editor, editor1, editorcount;
    int count = 0;
    String comments;
    String session;
    String username;
    TextView textViewHeaderName,tvHint;
    ImageView back;
    String ansJson;
    public static QuetionsMaster mQuetionsMaster=new QuetionsMaster();
    String sessionid;
    PACKAdapter adapter;
    // Location Access
    LocationManager locationManager;
    String provider, address;
    Location location;
    private int loop = 0;
    private EditText et_PName;
    private LinearLayout layout1,layout2;
    private ArrayList<CompetencyTrackingDto> list= new ArrayList<>();
    private RecyclerView recyclerView;
    private Spinner spr_cadre,spr_PAExam,spr_PVExam,spr_AMTSL,spr_NBR,spr_HandHygiene,spr_AntenatalComp,spr_Partograph,spr_PostnatalComp,
            spr_Manage_Preterm_Birth,spr_PNC_Counseling;

    private int editPosition =-1;
    ImageButton ibDownload;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.competency_tracking);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.menu_image_opt3));
        setSupportActionBar(toolbar);
        back = (ImageView) findViewById(R.id.back);
        textViewHeaderName = (TextView) findViewById(R.id.header_name);
        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        textViewHeaderName.setText(getString(R.string.provider_wise_tracking_text));
        editText = (EditText) findViewById(R.id.cr_et1);
        crAddMore = (Button) findViewById(R.id.crAddMore);
        btnAddProviderData = (Button) findViewById(R.id.btnAddProviderData);
        button_save = (Button) findViewById(R.id.cr_save);
        ibDownload=(ImageButton)findViewById(R.id.ibDownload);
        ibDownload.setOnClickListener(this);
        tvHint = findViewById(R.id.tvHint);
        jhpiegoDatabase = new JhpiegoDatabase(this);
        sessionid = getIntent().getStringExtra("sessionNew");
        editPosition = getIntent().getIntExtra("position",-1);
        list = (ArrayList<CompetencyTrackingDto>)getIntent().getSerializableExtra("list");
        ibDownload.setVisibility(View.GONE);
        if(list==null){
            list = new ArrayList<>();
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //getUserDisplay();
        sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
        editor = sh_Pref.edit();
        /*count = Integer.parseInt(sh_Pref.getString("count","0"));*/
        username = sh_Pref.getString("Username", "Unknown");
        count = Integer.parseInt(sh_Pref.getString("count","0"));

        et_PName = findViewById(R.id.et_PName);
        spr_cadre = findViewById(R.id.spr_cadre);
        spr_PAExam = findViewById(R.id.spr_PAExam);
        spr_PVExam = findViewById(R.id.spr_PVExam);
        spr_AMTSL = findViewById(R.id.spr_AMTSL);
        spr_NBR = findViewById(R.id.spr_NBR);
        spr_HandHygiene = findViewById(R.id.spr_HandHygiene);
        spr_AntenatalComp = findViewById(R.id.spr_AntenatalComp);
        spr_Partograph = findViewById(R.id.spr_Partograph);
        spr_PostnatalComp = findViewById(R.id.spr_PostnatalComp);
        spr_Manage_Preterm_Birth = findViewById(R.id.spr_Manage_Preterm_Birth);
        spr_PNC_Counseling = findViewById(R.id.spr_PNC_Counseling);

        try {
            SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query(TABLENAME1, new String[]{COL_USERNAME, COL_SESSION}, COL_USERNAME + "=?", new String[]{username}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                session = cursor.getString(cursor.getColumnIndex(COL_SESSION));
            }
            sqLiteDatabase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setRecyclerView();

        if(editPosition>-1){
            layout1.setVisibility(View.GONE);
            crAddMore.setVisibility(View.GONE);
            layout2.setVisibility(View.VISIBLE);
            setValue(list.get(editPosition));

        }
        else {
            layout1.setVisibility(View.VISIBLE);
            crAddMore.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.GONE);
            setDefaultSelectedValues();
        }

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Log.v("cT", "" + count);
                    if(list.size()>0){
                        count=10;
                    }
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
        crAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout1.setVisibility(View.GONE);
                layout2.setVisibility(View.VISIBLE);
            }
        });
        btnAddProviderData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formValidation();
                layout2.setVisibility(View.VISIBLE);
            }
        });
        getAllAccessToGPSLocation();
    }

    private void setValue(CompetencyTrackingDto competencyTrackingDto) {
        et_PName.setText(competencyTrackingDto.getPName());
        if(competencyTrackingDto.getCadre().equalsIgnoreCase("OBG")){
            spr_cadre.setSelection(1);
        }else if(competencyTrackingDto.getCadre().equalsIgnoreCase("MO")){
            spr_cadre.setSelection(2);
        }else if(competencyTrackingDto.getCadre().equalsIgnoreCase("SN")){
            spr_cadre.setSelection(3);
        }else if(competencyTrackingDto.getCadre().equalsIgnoreCase("LHV")){
            spr_cadre.setSelection(4);
        }else if(competencyTrackingDto.getCadre().equalsIgnoreCase("ANM")){
            spr_cadre.setSelection(5);
        }else{
            spr_cadre.setSelection(0);
        }

        if(competencyTrackingDto.getPaExam().equalsIgnoreCase("C")){
            spr_PAExam.setSelection(1);
        }else if(competencyTrackingDto.getPaExam().equalsIgnoreCase("NC")){
            spr_PAExam.setSelection(2);
        }else if(competencyTrackingDto.getPaExam().equalsIgnoreCase("A")){
            spr_PAExam.setSelection(3);
        }else{
            spr_PAExam.setSelection(0);
        }

        if(competencyTrackingDto.getPvExam().equalsIgnoreCase("C")){
            spr_PVExam.setSelection(1);
        }else if(competencyTrackingDto.getPvExam().equalsIgnoreCase("NC")){
            spr_PVExam.setSelection(2);
        }else if(competencyTrackingDto.getPvExam().equalsIgnoreCase("A")){
            spr_PVExam.setSelection(3);
        }else{
            spr_PVExam.setSelection(0);
        }

        if(competencyTrackingDto.getAMTSL().equalsIgnoreCase("C")){
            spr_AMTSL.setSelection(1);
        }else if(competencyTrackingDto.getAMTSL().equalsIgnoreCase("NC")){
            spr_AMTSL.setSelection(2);
        }else if(competencyTrackingDto.getAMTSL().equalsIgnoreCase("A")){
            spr_AMTSL.setSelection(3);
        }else{
            spr_AMTSL.setSelection(0);
        }

        if(competencyTrackingDto.getNBR().equalsIgnoreCase("C")){
            spr_NBR.setSelection(1);
        }else if(competencyTrackingDto.getNBR().equalsIgnoreCase("NC")){
            spr_NBR.setSelection(2);
        }else if(competencyTrackingDto.getNBR().equalsIgnoreCase("A")){
            spr_NBR.setSelection(3);
        }else{
            spr_NBR.setSelection(0);
        }

        if(competencyTrackingDto.getHandHygiene().equalsIgnoreCase("C")){
            spr_HandHygiene.setSelection(1);
        }else if(competencyTrackingDto.getHandHygiene().equalsIgnoreCase("NC")){
            spr_HandHygiene.setSelection(2);
        }else if(competencyTrackingDto.getHandHygiene().equalsIgnoreCase("A")){
            spr_HandHygiene.setSelection(3);
        }else{
            spr_HandHygiene.setSelection(0);
        }

        if(competencyTrackingDto.getAntenatalComp().equalsIgnoreCase("C")){
            spr_AntenatalComp.setSelection(1);
        }else if(competencyTrackingDto.getAntenatalComp().equalsIgnoreCase("NC")){
            spr_AntenatalComp.setSelection(2);
        }else if(competencyTrackingDto.getAntenatalComp().equalsIgnoreCase("A")){
            spr_AntenatalComp.setSelection(3);
        }else{
            spr_AntenatalComp.setSelection(0);
        }

        if(competencyTrackingDto.getPartograph().equalsIgnoreCase("C")){
            spr_Partograph.setSelection(1);
        }else if(competencyTrackingDto.getPartograph().equalsIgnoreCase("NC")){
            spr_Partograph.setSelection(2);
        }else if(competencyTrackingDto.getPartograph().equalsIgnoreCase("A")){
            spr_Partograph.setSelection(3);
        }else{
            spr_Partograph.setSelection(0);
        }

        if(competencyTrackingDto.getPostnatalComp().equalsIgnoreCase("C")){
            spr_PostnatalComp.setSelection(1);
        }else if(competencyTrackingDto.getPostnatalComp().equalsIgnoreCase("NC")){
            spr_PostnatalComp.setSelection(2);
        }else if(competencyTrackingDto.getPostnatalComp().equalsIgnoreCase("A")){
            spr_PostnatalComp.setSelection(3);
        }else{
            spr_PostnatalComp.setSelection(0);
        }

        if(competencyTrackingDto.getManagePretermBirth().equalsIgnoreCase("C")){
            spr_Manage_Preterm_Birth.setSelection(1);
        }else if(competencyTrackingDto.getManagePretermBirth().equalsIgnoreCase("NC")){
            spr_Manage_Preterm_Birth.setSelection(2);
        }else if(competencyTrackingDto.getManagePretermBirth().equalsIgnoreCase("A")){
            spr_Manage_Preterm_Birth.setSelection(3);
        }else{
            spr_Manage_Preterm_Birth.setSelection(0);
        }

        if(competencyTrackingDto.getPNCCounseling().equalsIgnoreCase("C")){
            spr_PNC_Counseling.setSelection(1);
        }else if(competencyTrackingDto.getPNCCounseling().equalsIgnoreCase("NC")){
            spr_PNC_Counseling.setSelection(2);
        }else if(competencyTrackingDto.getPNCCounseling().equalsIgnoreCase("A")){
            spr_PNC_Counseling.setSelection(3);
        }else{
            spr_PNC_Counseling.setSelection(0);
        }
    }

    private void formValidation() {
        if(et_PName.getText().toString().length()==0){
            Toast.makeText(this,"Please Enter Provider Name",Toast.LENGTH_LONG).show();

        }else if(spr_cadre.getSelectedItem().toString().equalsIgnoreCase("Select Value")){
            Toast.makeText(this,"Please select Cadre Value",Toast.LENGTH_LONG).show();

        }else if(spr_PAExam.getSelectedItem().toString().equalsIgnoreCase("Select Value")){
            Toast.makeText(this,"Please select PA Exam Value",Toast.LENGTH_LONG).show();

        }else if(spr_PVExam.getSelectedItem().toString().equalsIgnoreCase("Select Value")){
            Toast.makeText(this,"Please select PV Exam Value",Toast.LENGTH_LONG).show();

        }else if(spr_AMTSL.getSelectedItem().toString().equalsIgnoreCase("Select Value")){
            Toast.makeText(this,"Please select AMTSL Value",Toast.LENGTH_LONG).show();

        }else if(spr_NBR.getSelectedItem().toString().equalsIgnoreCase("Select Value")){
            Toast.makeText(this,"Please select NBR Value",Toast.LENGTH_LONG).show();

        }else if(spr_HandHygiene.getSelectedItem().toString().equalsIgnoreCase("Select Value")){
            Toast.makeText(this,"Please select Hand Hygiene Value",Toast.LENGTH_LONG).show();

        }else if(spr_AntenatalComp.getSelectedItem().toString().equalsIgnoreCase("Select Value")){
            Toast.makeText(this,"Please select PE/E Mx Value",Toast.LENGTH_LONG).show();

        }else if(spr_Partograph.getSelectedItem().toString().equalsIgnoreCase("Select Value")){
            Toast.makeText(this,"Please select Partograph Value",Toast.LENGTH_LONG).show();

        }else if(spr_PostnatalComp.getSelectedItem().toString().equalsIgnoreCase("Select Value")){
            Toast.makeText(this,"Please select PPH Mx Value",Toast.LENGTH_LONG).show();

        }else if(spr_Manage_Preterm_Birth.getSelectedItem().toString().equalsIgnoreCase("Select Value")){
            Toast.makeText(this,"Please select Preterm Birth Mx Value",Toast.LENGTH_LONG).show();

        }else if(spr_PNC_Counseling.getSelectedItem().toString().equalsIgnoreCase("Select Value")){
            Toast.makeText(this,"Please select PNC Counseling Value",Toast.LENGTH_LONG).show();

        }else {

            CompetencyTrackingDto cDto = new CompetencyTrackingDto();
            cDto.setDate(GlobalMethods.getCurrentDate(this));
            cDto.setPName(et_PName.getText().toString());
            cDto.setCadre(spr_cadre.getSelectedItem().toString());
            cDto.setPaExam(spr_PAExam.getSelectedItem().toString());
            cDto.setPvExam(spr_PVExam.getSelectedItem().toString());
            cDto.setAMTSL(spr_AMTSL.getSelectedItem().toString());
            cDto.setNBR(spr_NBR.getSelectedItem().toString());
            cDto.setHandHygiene(spr_HandHygiene.getSelectedItem().toString());
            cDto.setAntenatalComp(spr_AntenatalComp.getSelectedItem().toString());
            cDto.setPartograph(spr_Partograph.getSelectedItem().toString());
            cDto.setPostnatalComp(spr_PostnatalComp.getSelectedItem().toString());
            cDto.setManagePretermBirth(spr_Manage_Preterm_Birth.getSelectedItem().toString());
            cDto.setPNCCounseling(spr_PNC_Counseling.getSelectedItem().toString());
            cDto.setOverall(getCompetencyOverall(cDto));
            if(editPosition>-1){
                list.get(editPosition).setPName(et_PName.getText().toString());
                list.get(editPosition).setCadre(spr_cadre.getSelectedItem().toString());
                list.get(editPosition).setPaExam(spr_PAExam.getSelectedItem().toString());
                list.get(editPosition).setPvExam(spr_PVExam.getSelectedItem().toString());
                list.get(editPosition).setAMTSL(spr_AMTSL.getSelectedItem().toString());
                list.get(editPosition).setNBR(spr_NBR.getSelectedItem().toString());
                list.get(editPosition).setHandHygiene(spr_HandHygiene.getSelectedItem().toString());
                list.get(editPosition).setAntenatalComp(spr_AntenatalComp.getSelectedItem().toString());
                list.get(editPosition).setPartograph(spr_Partograph.getSelectedItem().toString());
                list.get(editPosition).setPostnatalComp(spr_PostnatalComp.getSelectedItem().toString());
                list.get(editPosition).setManagePretermBirth(spr_Manage_Preterm_Birth.getSelectedItem().toString());
                list.get(editPosition).setPNCCounseling(spr_PNC_Counseling.getSelectedItem().toString());
                list.get(editPosition).setOverall(getCompetencyOverall(cDto));

            }
            else {
                list.add(cDto);
            }

            tvHint.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(list.size()-1);
            layout2.setVisibility(View.GONE);
            layout1.setVisibility(View.VISIBLE);
        }
    }

    private String getCompetencyOverall(CompetencyTrackingDto cDto) {
        int count1 = 0;
        if(cDto.getPaExam().equalsIgnoreCase("C")){
            ++count1;
            ++count;
        }
        if(cDto.getPvExam().equalsIgnoreCase("C")){
            ++count1;
            ++count;
        }
        if(cDto.getAMTSL().equalsIgnoreCase("C")){
            ++count1;
            ++count;
        }
        if(cDto.getNBR().equalsIgnoreCase("C")){
            ++count1;
            ++count;
        }
        if(cDto.getHandHygiene().equalsIgnoreCase("C")){
            ++count1;
            ++count;
        }
        if(cDto.getAntenatalComp().equalsIgnoreCase("C")){
            ++count1;
            ++count;
        }
        if(cDto.getPartograph().equalsIgnoreCase("C")){
            ++count1;
            ++count;
        }
        if(cDto.getPostnatalComp().equalsIgnoreCase("C")){
            ++count1;
            ++count;
        }
        if(cDto.getManagePretermBirth().equalsIgnoreCase("C")){
            ++count1;
            ++count;
        }
        if(cDto.getPNCCounseling().equalsIgnoreCase("C")){
            ++count1;
            ++count;

        }
        return count1+"0%";
    }

    private void setRecyclerView() {
        recyclerView = findViewById(R.id.rvTableList);
        adapter = new PACKAdapter(this,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

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
                        Type type = new TypeToken<ArrayList<CompetencyTrackingParent>>(){}.getType();
                        ArrayList<CompetencyTrackingParent> competencyTrackingParent = gson.fromJson(json, type);
                        list=competencyTrackingParent.get(0).getList();
                    }
                }
                if(list.size()>0){
                    tvHint.setVisibility(View.GONE);
                    adapter = new PACKAdapter(this,list);
                    recyclerView.setAdapter(adapter);
                    recyclerView.scrollToPosition(list.size()-1);
                    layout2.setVisibility(View.GONE);
                    layout1.setVisibility(View.VISIBLE);
                    ibDownload.setVisibility(View.VISIBLE);
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void createJson() {

        CompetencyTrackingParent competencyTrackingParent=new CompetencyTrackingParent(new FormDataQCodes(),list);
        ArrayList<CompetencyTrackingParent> list=new ArrayList<>();
        list.add(competencyTrackingParent);
        ansJson =new Gson().toJson(list);


        System.out.println("ansJson:- "+ansJson);
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
                            address = MentorConstant.getAddress(NewCompetencyTracking.this, MentorConstant.latitude, MentorConstant.longitude);
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
                    address = MentorConstant.getAddress(NewCompetencyTracking.this, MentorConstant.latitude, MentorConstant.longitude);
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
                ActivityCompat.requestPermissions(NewCompetencyTracking.this,
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
            address = MentorConstant.getAddress(NewCompetencyTracking.this, MentorConstant.latitude, MentorConstant.longitude);
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ibDownload:
                createPdf("","ClientFeedFormPdf");
                break;
        }
    }

    private  SQLiteDatabase sqLiteDatabase;
    public static String mentorName, userEmail, lastVisitDate;
    private String fileName;
    // Create Pdf
    public void createPdf(String selectedSession, String fileName1) {

        try {
            jhpiegoDatabase = new JhpiegoDatabase(NewCompetencyTracking.this);
            sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SharedPreferences sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
        String username = sh_Pref.getString("Username", "Unknown");
        Cursor cursor = sqLiteDatabase.query(TABLENAME, new String[]{COL_USERNAME, COL_EMAIL, COL_MOBILE, COL_STATE, COL_NAME}, COL_USERNAME + "=?", new String[]{username}, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            mentorName = cursor.getString(cursor.getColumnIndex(COL_NAME));
            userEmail = cursor.getString(cursor.getColumnIndex(COL_EMAIL));
        }

        String facilityName="",facilityType="";
        Cursor cursor1 = sqLiteDatabase.query(TABLENAME1, new String[]{COL_ID,COL_SESSION, COL_NOM, COL_STATE, COL_DIST, COL_BLOCK, COL_FACILITYNAME, COL_FACILITYTYPE}, COL_ID + "="+MentorConstant.recordId,null, null, null, null);
        if (cursor1 != null && cursor1.getCount() > 0) {
            cursor1.moveToLast();
            facilityName = cursor1.getString(cursor1.getColumnIndex(COL_FACILITYNAME));
            facilityType = cursor1.getString(cursor1.getColumnIndex(COL_FACILITYTYPE));
        }
        this.fileName = facilityName+"_"+facilityType+"_CompetencyTracking";

        try {
            new CompetencyTrackingPdf(NewCompetencyTracking.this, fileName, "",list);
        } catch (Exception e) {
            new CompetencyTrackingPdf(NewCompetencyTracking.this, fileName, "",list);
        }

    }



}
