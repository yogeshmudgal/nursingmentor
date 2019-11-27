package com.dakshata.mentor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import com.dakshata.mentor.Utils.MentorConstant;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aditya.v on 15-12-2017.
 */

public class JhpiegoDatabase extends SQLiteOpenHelper {
    public static final String DATABASENAME = "jhpiego";
    public static final String TABLENAME = "jhpiegousers";
    public static final String COL_ID = "id";
    public static final String COL_DISTRICT = "district";
    public static final String COL_USERNAME = "username";
    public static final String COL_ansJSON = "username";
    public static final String COL_DESIGNATION = "designation";
    public static final String COL_OFFICE = "office";
    public static final String COL_MOBILE = "mobile";
    public static final String COL_EMAIL = "email";
    public static final String COL_PASSWORD = "password";
    public static final String COL_NAME = "name";
    public static final String COL_ROLE = "role";


    //detais_of_visit
    public static final String TABLENAME1 = "detailsofvisit";
    public static final String COL_COUNT = "count";
    public static final String COL_NOM = "nom";
    public static final String COL_STATE = "state";
    public static final String COL_DIST = "district";
    public static final String COL_BLOCK = "block";
    public static final String COL_FACILITYNAME = "facilityname";
    public static final String COL_FACILITYTYPE = "facilitytype";
    public static final String COL_LEVEL = "level";
    public static final String COL_DOV = "dov";
    public static final String COL_IMAGE1 = "image1";
    public static final String COL_IMAGE2 = "image2";
    public static final String COL_IMAGE3 = "image3";
    public static final String COL_IMAGE4 = "image4";
    public static final String COL_SESSION = "session";
    public static final String COL_IS_SUBMITTED = "isSubmitted";
    public static final String COL_IS_ASSESSMENT = "isAssessment";
    public static final String COL_UNIQUE_ID = "uniqueId";
    public static final String COL_ANSJSON = "ansJson";
    public static final String COL_SYNC_STATUS = "syncstatus";
    public static final String COL_FILE1 = "file1";
    public static final String COL_FILE2 = "file2";
    public static final String COL_FILE3 = "file3";
    public static final String COL_FILE4 = "file4";
    public static final String CREATED_DATE = "created_date";

    // GPS Location parammeters
    public static final String COL_LATITUDE = "latitude";
    public static final String COL_LONGITUDE = "longitude";


    //drugs_supply
    public static final String TABLENAME2 = "drugsupply";
    public static final String COL_MS = "ms";
    public static final String COL_AM = "am";
    public static final String COL_AB = "ab";
    public static final String COL_OXY = "oxy";
    public static final String COL_ANTIRETROIDS = "antiretroids";
    public static final String COL_LABITOL = "labitol";
    public static final String COL_MISOPRISTOL = "misopristol";
    public static final String COL_GLOVES = "gloves";
    public static final String COL_URITICK = "uritick";
    public static final String COL_CWP = "cwp";
    public static final String COL_CCL = "ccl";
    public static final String COL_SS = "ss";
    public static final String COL_PP = "pp";
    public static final String COL_BSR = "bsr";
    public static final String COL_SIVS = "sivs";
    public static final String COL_ABBWBPP = "abbwbpp";
    public static final String COL_BP = "bp";
    public static final String COL_STETHOSCOPE = "stethoscope";
    public static final String COL_THERMAMETER = "thermameter";
    public static final String COL_MUCUS = "mucus";
    public static final String COL_HIV = "hiv";
    public static final String COL_LABOUR = "labour";


    //clinical_standards
    public static final String TABLE_WorkPlan = "work_plan";
    public static final String TABLE_ClinicalStandards = "clinicalstandards_new";
    public static final String JSON_DATA = "json_data";
    public static final String TABLENAME3 = "clinicalstandards";
    public static final String COL_PWF = "pwf";
    public static final String COL_PCIEA = "pciea";
    public static final String COL_HIVPW = "hivpw";
    public static final String COL_IPW = "ipw";
    public static final String COL_PD = "pd";
    public static final String COL_PE = "pe";
    public static final String COL_PMONITOR = "pmonitor";
    public static final String COL_PENSURES = "pensures";
    public static final String COL_PPREPARES = "pprepares";
    public static final String COL_PASSISTS = "passists";
    public static final String COL_PCONDUCTS = "pconducts";
    public static final String COL_PPERFORMS = "pperforms";
    public static final String COL_PPERFORMSAM = "pperformsam";
    public static final String COL_PIDENTIFIES = "pidentifies";
    public static final String COL_PASSESSES = "passesses";
    public static final String COL_PENSURESEX = "pensuresex";
    public static final String COL_PENSURESCARE = "pensurescare";
    public static final String COL_PCOUNSELS = "pcounsels";
    public static final String COL_FAUPP = "faupp";

    //equipments
    public static final String TABLENAME4 = "equipments";
    public static final String COL_FAM = "fam";
    public static final String COL_FB = "fb";
    public static final String COL_BLT = "blt";
    public static final String COL_WCLOCK = "wclock";
    public static final String COL_DBW = "dbw";
    public static final String COL_DLR = "dlr";
    public static final String COL_FS = "fs";
    public static final String COL_MLT = "mlt";
    public static final String COL_FOC = "foc";
    public static final String COL_PPD = "ppd";
    public static final String COL_ET = "et";
    public static final String COL_ETRAY = "etray";
    public static final String COL_FRW = "frw";
    public static final String COL_FBWS = "fbws";

    //infrastructure
    public static final String TABLENAME5 = "infrastructure";
    public static final String COL_LOI = "loi";
    public static final String COL_LRD = "lrd";
    public static final String COL_PSC = "psc";
    public static final String COL_LCFW = "lcfw";
    public static final String COL_ASTNSEM = "astnsem";
    public static final String COL_WALLS = "walls";
    public static final String COL_AC = "ac";
    public static final String COL_AV = "av";
    public static final String COL_FLSL = "flsl";
    public static final String COL_PB = "pb";
    public static final String COL_LOF = "lof";
    public static final String COL_ASBT = "asbt";
    public static final String COL_CNRNB = "cnrnb";
    public static final String COL_CMDS = "cmds";
    public static final String COL_RW = "rw";
    public static final String COL_SOB = "sob";
    public static final String COL_FWRW = "fwrw";
    public static final String COL_DUA = "dua";
    public static final String COL_FHWS = "fhws";
    public static final String COL_EOT = "eot";
    public static final String COL_THREE_SIDE_SPACE = "three_side_free_space";

    //mentoring_practices
    public static final String TABLENAME6 = "mentoringpractices";
    public static final String COL_ROLR = "rolr";
    public static final String COL_LP = "lp";
    public static final String COL_GA = "ga";
    public static final String COL_PV = "pv";
    public static final String COL_AUA = "aua";
    public static final String COL_ACP = "acp";
    public static final String COL_CND = "cnd";
    public static final String COL_AMTSL = "amtsl";
    public static final String COL_ENBC = "enbc";
    public static final String COL_NR = "nr";
    public static final String COL_SCN = "scn";
    public static final String COL_PPH = "pph";
    public static final String COL_IMPE = "impe";
    public static final String COL_IMPO = "impo";
    public static final String COL_PP1 = "pp";
    public static final String COL_DND = "dnd";
    public static final String COL_DPPH = "dpph";
    public static final String COL_DPE = "dpe";
    public static final String COL_DNR = "dnr";

    //staff_maternity
    public static final String TABLENAME7 = "staffmaternity";
    public static final String COL_AMDL="amdl";
    public static final String COL_OBG = "obg";
    public static final String COL_SBA = "sba";
    public static final String COL_MOLR = "molr";
    public static final String COL_MOD = "mod";
    public static final String COL_SNLR = "snlr";
    public static final String COL_SND = "snd";
    public static final String COL_LHVSLR = "lhvslr";
    public static final String COL_LHVSD = "lhvsd";
    public static final String COL_LHVWORKING = "lhvworking";
    public static final String COL_LHVTRAINED = "lhvtrained";

    public static final String COL_NSEL = "nsael";
    public static final String COL_NSR_MNHToolKit = "nsr_mnh_toolkit";
    public static final String COL_VAR_Staff = "var_staff";
    public static final String COL_Labour_TableLR = "labour_table_lr";
    public static final String COL_Labour_TableReq = "labour_table_req";
    public static final String COL_Variance_LbrTbl = "variance_labour_table";
    public static final String COL_Caesarian_Act = "caesarian_act";
    public static final String COL_Person_InCharge = "person_in_charge";
    public static final String COL_Mobile_InCharge = "mobile_in_charge";


    //labour_room
    public static final String TABLENAME8 = "labourroom";
    public static final String COL_1 = "col_1";
    public static final String COL_2 = "col_2";
    public static final String COL_3 = "col_3";
    public static final String COL_4 = "col_4";
    public static final String COL_5 = "col_5";
    public static final String COL_6 = "col_6";
    public static final String COL_7 = "col_7";
    public static final String COL_8 = "col_8";
    public static final String COL_9 = "col_9";
    public static final String COL_10 = "col_10";
    public static final String COL_11 = "col_11";
    public static final String COL_12 = "col_12";
    public static final String COL_13 = "col_13";
    public static final String COL_14 = "col_14";
    public static final String COL_15 = "col_15";
    public static final String COL_16 = "col_16";
    public static final String COL_17 = "col_17";
    public static final String COL_18 = "col_18";
    public static final String COL_19 = "col_19";
    public static final String COL_20 = "col_20";
    public static final String COL_21 = "col_21";
    public static final String COL_22 = "col_22";
    public static final String COL_23 = "col_23";
    public static final String COL_24 = "col_24";
    public static final String COL_25 = "col_25";
    public static final String COL_26 = "col_26";

    //recording_reporting
    public static final String TABLENAME9 = "recordingreporting";

    //next_visit_date
    public static final String TABLENAME10 = "nextvisitdate";

    //comments_reports
    public static final String TABLENAME11 = "comments";

    //questionMaster
    public static final String TABLENAME12 = "questionmaster";
    public static final String TABLENAME17 = "competencyTracking";
    public static final String TABLENAME18 = "clientFeedBackForm";

    public static final String TABLENAME13 = "pendingpost";
    public static final String COL_URL = "url";
    public static final String COL_JSON = "json";
    public static final String COL_STATUS = "status";

    public static final String TABLENAME_LocationMaster = "facilityMaster";


    public JhpiegoDatabase(Context context) {
        super(context, DATABASENAME, null, 8);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + TABLENAME + "(id integer primary key autoincrement,username text,name text,email text,mobile text,state text,district text,block text, role text)";
        db.execSQL(query);
        /*ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USERNAME, "a");
        contentValues.put(COL_PASSWORD, "a");
        contentValues.put(COL_DISTRICT, "Telangana");
        contentValues.put(COL_BLOCK, "Hyderabad");
        contentValues.put(COL_MOBILE, "9256824153");
        db.insert(TABLENAME, null, contentValues);*/

        String query1 = "create table " + TABLENAME1 + "(id integer primary key autoincrement,nom text,state text,district text,block text,facilityname text,facilitytype text,level text,dov text,image1 blob,image2 blob,image3 blob,image4 blob,count number,session text,username text,isSubmitted text,ansJson text,syncstatus text,file1 text,file2 text,file3 text,file4 text, latitude text, longitude text, " + COL_IS_ASSESSMENT + " text DEFAULT 0, " + COL_UNIQUE_ID + " text DEFAULT 0, " + CREATED_DATE + " text, reference_key text DEFAULT 0, workplan_status text, planned_date text)";
        db.execSQL(query1);

        String query2 = "create table " + TABLENAME2 + "(id integer primary key autoincrement,username text,ms text,am text,ab text,oxy text,antiretroids text,labitol text,misopristol text,gloves text,uritick text,cwp text,ccl text,ss text,pp text,bsr text,sivs text,abbwbpp text,bp text,stethoscope text,thermameter text,mucus text,hiv text,labour text,count number,session text,isSubmitted text,ansJson text, " + COL_IS_ASSESSMENT + " text DEFAULT 0, " + COL_UNIQUE_ID + " text DEFAULT 0)";
        db.execSQL(query2);

        String query3 = "create table " + TABLENAME3 + "(id integer primary key autoincrement,username text,pwf text,pciea text,hivpw text,ipw text,pd text,pe text,pmonitor text,pensures text,pprepares text,passists text,pconducts text,pperforms text,pperformsam text,pidentifies text,passesses text,pensuresex text,pensurescare text,pcounsels text,faupp text,count number,session text,isSubmitted text,ansJson text, latitude text, longitude text, " + COL_IS_ASSESSMENT + " text DEFAULT 0, " + COL_UNIQUE_ID + " text DEFAULT 0)";
        db.execSQL(query3);

        String query4 = "create table " + TABLENAME4 + "(id integer primary key autoincrement,username text,fam text,fb text,blt text,wclock text,dbw text,dlr text,fs text,mlt text,foc text,ppd text,et text,etray text,frw text,fbws text,count number,session text,isSubmitted text,ansJson text, latitude text, longitude text, " + COL_IS_ASSESSMENT + " text DEFAULT 0, " + COL_UNIQUE_ID + " text DEFAULT 0)";
        db.execSQL(query4);

        String query5 = "create table " + TABLENAME5 + "(id integer primary key autoincrement,username text,loi text,lrd text,psc text,lcfw text,astnsem text,walls text,ac text,av text,flsl text,pb text,lof text,asbt text,cnrnb text,cmds text,rw text,sob text,fwrw text,dua text,fhws text,eot text,three_side_free_space text,count number,session text,isSubmitted text,ansJson text, latitude text, longitude text, " + COL_IS_ASSESSMENT + " text DEFAULT 0, " + COL_UNIQUE_ID + " text DEFAULT 0)";
        db.execSQL(query5);

        String query6 = "create table " + TABLENAME6 + "(id integer primary key autoincrement,username text,rolr text,lp text,ga text,pv text,aua text,acp text,cnd text,amtsl text,enbc text,nr text,scn text,pph text,impe text,impo text,pp text,dnd text,dpph text,dpe text,dnr text,count number,session text,isSubmitted text,ansJson text, latitude text, longitude text, " + COL_IS_ASSESSMENT + " text DEFAULT 0, " + COL_UNIQUE_ID + " text DEFAULT 0)";
        db.execSQL(query6);

        String query7 = "create table " + TABLENAME7 + "(id integer primary key autoincrement,username text,obg text,sba text,molr text,mod text,snlr text,snd text,lhvslr text,lhvsd text,lhvworking text,lhvtrained text,count number,session text,isSubmitted text,ansJson text, latitude text, longitude text, " + COL_IS_ASSESSMENT + " text DEFAULT 0, " + COL_UNIQUE_ID + " text DEFAULT 0, "+
                COL_NSEL+" TEXT, "+
                COL_NSR_MNHToolKit+" TEXT, "+
               COL_VAR_Staff+" TEXT, "+
               COL_Labour_TableLR+" TEXT, "+
               COL_Labour_TableReq+" TEXT, "+
                COL_Variance_LbrTbl+" TEXT, "+
                COL_Caesarian_Act+" TEXT, "+
                COL_Person_InCharge+" TEXT, "+
                COL_AMDL+" TEXT, "+
                COL_Mobile_InCharge+" TEXT);";

                db.execSQL(query7);
        String query8 = "create table " + TABLENAME8 + "(id integer primary key autoincrement,username text,col_1 text,col_2 text,col_3 text,col_4 text,col_5 text,col_6 text,col_7 text,col_8 text,col_9 text,col_10 text,col_11 text,col_12 text,col_13 text,col_14 text,col_15 text,col_16 text,col_17 text,col_18 text,col_19 text,col_20 text,col_21 text,col_22 text,col_23 text,col_24 text,col_25 text,col_26 text,count number,session text,isSubmitted text,ansJson text, latitude text, longitude text, " + COL_IS_ASSESSMENT + " text DEFAULT 0, " + COL_UNIQUE_ID + " text DEFAULT 0)";
        db.execSQL(query8);

        String query9 = "create table " + TABLENAME9 + "(id integer primary key autoincrement,username text,col_1 text,col_2 text,col_3 text,col_4 text,count number,session text,isSubmitted text,ansJson text, latitude text, longitude text, " + COL_IS_ASSESSMENT + " text DEFAULT 0, " + COL_UNIQUE_ID + " text DEFAULT 0)";
        db.execSQL(query9);

        String query10 = "create table " + TABLENAME10 + "(id integer primary key autoincrement,username text,col_1 text,count number,session text,isSubmitted text,ansJson text, latitude text, longitude text, " + COL_IS_ASSESSMENT + " text DEFAULT 0, " + COL_UNIQUE_ID + " text DEFAULT 0)";
        db.execSQL(query10);

        String query11 = "create table " + TABLENAME11 + "(id integer primary key autoincrement,username text,col_1 text,count number,session text,isSubmitted text,ansJson text, latitude text, longitude text, " + COL_IS_ASSESSMENT + " text DEFAULT 0, " + COL_UNIQUE_ID + " text DEFAULT 0)";
        db.execSQL(query11);

        String query12 = "create table " + TABLENAME12 + "(id integer primary key autoincrement,col_1 text)";
        db.execSQL(query12);

        String query13 = "create table " + TABLENAME13 + "(id integer primary key autoincrement,username text,url text,json text,status text,session text,file1 text,file2 text,file3 text,file4 text, reference_key text)";
        db.execSQL(query13);

        String query14 = "CREATE TABLE " + TABLENAME_LocationMaster + " (_id integer primary key autoincrement,StateName  text,DistrictName  text,BlockName  text,FType  text,PHCName  text,SubCenter  text,Village  text,Taluka  text,SCTaluka  text,facility text,state_code text,district_code text,block_code text,facility_code text,subfacility_code text)";
        db.execSQL(query14);

        String query15 = "CREATE TABLE " + TABLE_ClinicalStandards + " (id integer primary key autoincrement,username text,count number,session text,isSubmitted text,json_data text,ansJson text, latitude text, longitude text, " + COL_IS_ASSESSMENT + " text DEFAULT 0, " + COL_UNIQUE_ID + " text DEFAULT 0, flag text DEFAULT 0)";
        db.execSQL(query15);

        String query16 = "create table " + TABLE_WorkPlan + "(id integer primary key autoincrement,nom text,state text,district text,block text,facilityname text,facilitytype text,level text, session text,username text,isSubmitted text,workplan_date text, actual_date text, " + COL_IS_ASSESSMENT + " text DEFAULT 0, " + "visits text DEFAULT 0, " + CREATED_DATE + " text, " + COL_SYNC_STATUS + " text, reference_key text DEFAULT 0)";
        db.execSQL(query16);

        String query17 = "create table " + TABLENAME17 + "(id integer primary key autoincrement,username text,count number,session text,isSubmitted text,ansJson text, latitude text, longitude text, " + COL_IS_ASSESSMENT + " text DEFAULT 0, " + COL_UNIQUE_ID + " text DEFAULT 0)";
        db.execSQL(query17);
        String query18 = "create table " + TABLENAME18 + "(id integer primary key autoincrement,username text,count number,session text,isSubmitted text,ansJson text, latitude text, longitude text, " + COL_IS_ASSESSMENT + " text DEFAULT 0, " + COL_UNIQUE_ID + " text DEFAULT 0)";
        db.execSQL(query18);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String queryStart = "ALTER TABLE ";
        String queryMid = " ADD COLUMN ";
        if ((oldVersion == 1 && newVersion == 2) || (oldVersion == 1 && newVersion == 3)) {
            String queryEnd1 = queryMid + COL_IS_ASSESSMENT + " text DEFAULT 0";
            String queryEnd2 = queryMid + COL_UNIQUE_ID + " text DEFAULT 0";
            try {
                db.execSQL(queryStart + TABLENAME1 + queryEnd1);
            } catch (Exception e) {
            }
            try {
                db.execSQL(queryStart + TABLENAME2 + queryEnd1);
            } catch (Exception e) {
            }
            try {
                db.execSQL(queryStart + TABLENAME3 + queryEnd1);
            } catch (Exception e) {
            }
            try {
                db.execSQL(queryStart + TABLENAME4 + queryEnd1);
            } catch (Exception e) {
            }
            try {
                db.execSQL(queryStart + TABLENAME5 + queryEnd1);
            } catch (Exception e) {
            }
            try {
                db.execSQL(queryStart + TABLENAME6 + queryEnd1);
            } catch (Exception e) {
            }
            try {
                db.execSQL(queryStart + TABLENAME7 + queryEnd1);
            } catch (Exception e) {
            }
            try {
                db.execSQL(queryStart + TABLENAME8 + queryEnd1);
            } catch (Exception e) {
            }
            try {
                db.execSQL(queryStart + TABLENAME9 + queryEnd1);
            } catch (Exception e) {
            }
            try {
                db.execSQL(queryStart + TABLENAME10 + queryEnd1);
            } catch (Exception e) {
            }
            try {
                db.execSQL(queryStart + TABLENAME11 + queryEnd1);
            } catch (Exception e) {
            }

            try {
                db.execSQL(queryStart + TABLENAME1 + queryEnd2);
                db.execSQL(queryStart + TABLENAME2 + queryEnd2);
                db.execSQL(queryStart + TABLENAME3 + queryEnd2);
                db.execSQL(queryStart + TABLENAME4 + queryEnd2);
                db.execSQL(queryStart + TABLENAME5 + queryEnd2);
                db.execSQL(queryStart + TABLENAME6 + queryEnd2);
                db.execSQL(queryStart + TABLENAME7 + queryEnd2);
                db.execSQL(queryStart + TABLENAME8 + queryEnd2);
                db.execSQL(queryStart + TABLENAME9 + queryEnd2);
                db.execSQL(queryStart + TABLENAME10 + queryEnd2);
                db.execSQL(queryStart + TABLENAME11 + queryEnd2);
            } catch (Exception e) {
            }

            // Adding new column for DB Version 3
            String queryEnd3 = queryMid + CREATED_DATE + " text";
            try {
                db.execSQL(queryStart + TABLENAME1 + queryEnd3);
            } catch (Exception e) {
            }
        }

        // Adding new column for DB Version 3
        if (oldVersion == 2 && newVersion == 3) {
            String queryEnd3 = queryMid + CREATED_DATE + " text";
            try {
                db.execSQL(queryStart + TABLENAME1 + queryEnd3);
            } catch (Exception e) {
            }
        }

        // Adding new table for DB - Version 4
        if ((oldVersion == 1 || oldVersion == 2 || oldVersion == 3) && newVersion == 4) {
            try {
                db.execSQL("CREATE TABLE " + TABLE_ClinicalStandards + " (id integer primary key autoincrement,username text,count number,session text,isSubmitted text,json_data text,ansJson text, latitude text, longitude text, " + COL_IS_ASSESSMENT + " text DEFAULT 0, " + COL_UNIQUE_ID + " text DEFAULT 0)");
            } catch (Exception e) {
            }
        }


        // Adding new table for DB - Version 7
        if ((oldVersion == 4 || oldVersion == 6) && newVersion == 7) {
            try {
                String queryEnd3 = queryMid + "reference_key text DEFAULT 0";
                String queryEnd4 = queryMid + "workplan_status text";
                String queryEnd5 = queryMid + "planned_date text";
                try {
                    db.execSQL(queryStart + TABLENAME1 + queryEnd3);
                } catch (Exception e) {
                }
                try {
                    db.execSQL(queryStart + TABLENAME1 + queryEnd4);
                } catch (Exception e) {
                }
                try {
                    db.execSQL(queryStart + TABLENAME1 + queryEnd5);
                } catch (Exception e) {
                }
                try {
                    db.execSQL(queryStart + TABLENAME13 + queryEnd3);
                } catch (Exception e) {
                }
                try {
                    db.execSQL("create table " + TABLE_WorkPlan + "(id integer primary key autoincrement,nom text,state text,district text,block text,facilityname text,facilitytype text,level text, session text,username text,isSubmitted text,workplan_date text, actual_date text, " + COL_IS_ASSESSMENT + " text DEFAULT 0, " + "visits text DEFAULT 0, " + CREATED_DATE + " text, " + COL_SYNC_STATUS + " text, reference_key text)");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
            }
            try {
                String queryEnd3 = queryMid + "flag text DEFAULT 0";
                try {
                    db.execSQL(queryStart + TABLE_ClinicalStandards + queryEnd3);
                } catch (Exception e) {
                }
            } catch (Exception e) {
            }

            if((oldVersion == 4 || oldVersion == 6|| oldVersion == 7) && newVersion == 8){
                String query17 = "create table " + TABLENAME17 + "(id integer primary key autoincrement,username text,count number,session text,isSubmitted text,ansJson text, latitude text, longitude text, " + COL_IS_ASSESSMENT + " text DEFAULT 0, " + COL_UNIQUE_ID + " text DEFAULT 0)";
                db.execSQL(query17);
            }
            if((oldVersion == 4 || oldVersion == 6|| oldVersion == 7) && newVersion == 9){
                String query18 = "create table " + TABLENAME18 + "(id integer primary key autoincrement,username text,count number,session text,isSubmitted text,ansJson text, latitude text, longitude text, " + COL_IS_ASSESSMENT + " text DEFAULT 0, " + COL_UNIQUE_ID + " text DEFAULT 0)";
                db.execSQL(query18);
            }
            if(oldVersion<9){
                db.execSQL("ALTER TABLE "+TABLENAME7+" ADD COLUMN "+COL_NSEL+" TEXT");
                db.execSQL("ALTER TABLE "+TABLENAME7+" ADD COLUMN "+COL_NSR_MNHToolKit+" TEXT");
                db.execSQL("ALTER TABLE "+TABLENAME7+" ADD COLUMN "+COL_VAR_Staff+" TEXT");
                db.execSQL("ALTER TABLE "+TABLENAME7+" ADD COLUMN "+COL_Labour_TableLR+" TEXT");
                db.execSQL("ALTER TABLE "+TABLENAME7+" ADD COLUMN "+COL_Labour_TableReq+" TEXT");
                db.execSQL("ALTER TABLE "+TABLENAME7+" ADD COLUMN "+COL_Variance_LbrTbl+" TEXT");
                db.execSQL("ALTER TABLE "+TABLENAME7+" ADD COLUMN "+COL_Caesarian_Act+" TEXT");
                db.execSQL("ALTER TABLE "+TABLENAME7+" ADD COLUMN "+COL_Person_InCharge+" TEXT");
                db.execSQL("ALTER TABLE "+TABLENAME7+" ADD COLUMN "+COL_Mobile_InCharge+" TEXT");
                db.execSQL("ALTER TABLE "+TABLENAME7+" ADD COLUMN "+COL_AMDL+" TEXT");
            }


        }
    }

    public long addUser(String username, String name, String emailid, String block_name, String mobile_number, String district_name, String state_name, String role) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_DISTRICT, district_name);
        contentValues.put(COL_BLOCK, block_name);
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_MOBILE, mobile_number);
        contentValues.put(COL_EMAIL, emailid);
        contentValues.put(COL_STATE, state_name);
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_ROLE, role);
        long row = sqLiteDatabase.insert(TABLENAME, null, contentValues);
        sqLiteDatabase.close();
        return row;

    }

    public long updateUser(String username, String name, String emailid, String block_name, String mobile_number, String district_name, String state_name, String role) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_DISTRICT, district_name);
        contentValues.put(COL_BLOCK, block_name);
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_MOBILE, mobile_number);
        contentValues.put(COL_EMAIL, emailid);
        contentValues.put(COL_STATE, state_name);
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_ROLE, role);
        long row = sqLiteDatabase.update(TABLENAME, contentValues, "username=?", new String[]{username});
        sqLiteDatabase.close();
        return row;

    }

    public boolean checkUserExists(String email) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLENAME, new String[]{COL_USERNAME}, COL_USERNAME + "=?", new String[]{email}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            return true;
        } else {
            return false;
        }
    }

    public String userLogin(String username) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLENAME, new String[]{COL_USERNAME, COL_PASSWORD}, COL_USERNAME + "=?", new String[]{username}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            String password = cursor.getString(cursor.getColumnIndex(COL_PASSWORD));
            return password;
        } else {
            return "does not exists";
        }
    }

    byte[] buffer1, buffer2, buffer3, buffer4;

    public long addDetailsOfVisit(String nom, String state, String district, String block, String facilityname, String facilitytype, String level, String dov,
                                  Bitmap image1, Bitmap image2, Bitmap image3, Bitmap image4, String count, String session, String username, String ansJson,
                                  String imageFile1, String imageFile2, String imageFile3, String imageFile4, String latitude, String longitude, String createdDate,
                                  String planned_date, String reference_key, String workplan_status) {

        if (image1 != null) {
            ByteArrayOutputStream out1 = new ByteArrayOutputStream();
            image1.compress(Bitmap.CompressFormat.PNG, 100, out1);
            buffer1 = out1.toByteArray();
        }
        if (image2 != null) {
            ByteArrayOutputStream out2 = new ByteArrayOutputStream();
            image2.compress(Bitmap.CompressFormat.PNG, 100, out2);
            buffer2 = out2.toByteArray();
        }
        if (image3 != null) {
            ByteArrayOutputStream out3 = new ByteArrayOutputStream();
            image3.compress(Bitmap.CompressFormat.PNG, 100, out3);
            buffer3 = out3.toByteArray();
        }
        if (image4 != null) {
            ByteArrayOutputStream out4 = new ByteArrayOutputStream();
            image4.compress(Bitmap.CompressFormat.PNG, 100, out4);
            buffer4 = out4.toByteArray();
        }
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_DIST, district);
        contentValues.put(COL_NOM, nom);
        contentValues.put(COL_BLOCK, block);
        contentValues.put(COL_FACILITYTYPE, facilitytype);
        contentValues.put(COL_FACILITYNAME, facilityname);
        contentValues.put(COL_STATE, state);
        contentValues.put(COL_LEVEL, level);
        contentValues.put(COL_DOV, dov);
        contentValues.put(COL_IMAGE1, buffer1);
        contentValues.put(COL_IMAGE2, buffer2);
        contentValues.put(COL_IMAGE3, buffer3);
        contentValues.put(COL_IMAGE4, buffer4);
        contentValues.put(COL_COUNT, count);
        contentValues.put(COL_SESSION, session);
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_IS_SUBMITTED, "0");
        contentValues.put(COL_ANSJSON, ansJson);
        contentValues.put(COL_SYNC_STATUS, "pending");
        contentValues.put(COL_FILE1, imageFile1);
        contentValues.put(COL_FILE2, imageFile2);
        contentValues.put(COL_FILE3, imageFile3);
        contentValues.put(COL_FILE4, imageFile4);
        contentValues.put(COL_LATITUDE, latitude);
        contentValues.put(COL_LONGITUDE, longitude);
        contentValues.put(CREATED_DATE, createdDate);
        contentValues.put("planned_date", planned_date);
        contentValues.put("reference_key", reference_key);
        contentValues.put("workplan_status", workplan_status);
        long row = sqLiteDatabase.insert(TABLENAME1, null, contentValues);
        sqLiteDatabase.close();
        return row;

    }

    public String[] getDataOfVisit(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.query(TABLENAME1,new String[]{COL_DIST,COL_FACILITYTYPE,COL_FACILITYNAME},COL_ID + "=?", new String[]{String.valueOf(MentorConstant.recordId)},null,null,null);
        String[] data=new String[3];
        try{
            if(cursor!=null && cursor.getCount()>0){
                cursor.moveToLast();
                data[0]=cursor.getString(0);
                data[1]=cursor.getString(1);
                data[2]=cursor.getString(2);
            }
        }finally {
            if(cursor!=null)
                cursor.close();
        }
        return data;
    }


    public long updateDetailsOfVisit(String id, String nom, String state, String district, String block, String facilityname, String facilitytype, String level, String dov, Bitmap image1, Bitmap image2, Bitmap image3, Bitmap image4, String count, String session, String username, String ansJson, String imageFile1, String imageFile2, String imageFile3, String imageFile4,
                                     String latitude, String longitude, String planned_date, String reference_key, String workplan_status) {

        if (image1 != null) {
            ByteArrayOutputStream out1 = new ByteArrayOutputStream();
            image1.compress(Bitmap.CompressFormat.PNG, 100, out1);
            buffer1 = out1.toByteArray();
        }
        if (image2 != null) {
            ByteArrayOutputStream out2 = new ByteArrayOutputStream();
            image2.compress(Bitmap.CompressFormat.PNG, 100, out2);
            buffer2 = out2.toByteArray();
        }
        if (image3 != null) {
            ByteArrayOutputStream out3 = new ByteArrayOutputStream();
            image3.compress(Bitmap.CompressFormat.PNG, 100, out3);
            buffer3 = out3.toByteArray();
        }
        if (image4 != null) {
            ByteArrayOutputStream out4 = new ByteArrayOutputStream();
            image4.compress(Bitmap.CompressFormat.PNG, 100, out4);
            buffer4 = out4.toByteArray();
        }
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_DIST, district);
        contentValues.put(COL_NOM, nom);
        contentValues.put(COL_BLOCK, block);
        contentValues.put(COL_FACILITYTYPE, facilitytype);
        contentValues.put(COL_FACILITYNAME, facilityname);
        contentValues.put(COL_STATE, state);
        contentValues.put(COL_LEVEL, level);
        contentValues.put(COL_DOV, dov);
        contentValues.put(COL_IMAGE1, buffer1);
        contentValues.put(COL_IMAGE2, buffer2);
        contentValues.put(COL_IMAGE3, buffer3);
        contentValues.put(COL_IMAGE4, buffer4);
        contentValues.put(COL_COUNT, count);
        contentValues.put(COL_SESSION, session);
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_IS_SUBMITTED, "0");
        contentValues.put(COL_ANSJSON, ansJson);
        contentValues.put(COL_SYNC_STATUS, "pending");
        contentValues.put(COL_FILE1, imageFile1);
        contentValues.put(COL_FILE2, imageFile2);
        contentValues.put(COL_FILE3, imageFile3);
        contentValues.put(COL_FILE4, imageFile4);
        contentValues.put(COL_LATITUDE, latitude);
        contentValues.put(COL_LONGITUDE, longitude);
        contentValues.put("planned_date", planned_date);
        contentValues.put("reference_key", reference_key);
        contentValues.put("workplan_status", workplan_status);
        long row = sqLiteDatabase.update(TABLENAME1, contentValues, "id=" + id, null);
        sqLiteDatabase.close();
        return row;

    }

    public String getLastInsertedDataId() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLENAME1 + " ORDER BY id DESC LIMIT 1", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursor.getString(cursor.getColumnIndex("id"));
        }
        int size = cursor.getCount();
        sqLiteDatabase.close();
        return String.valueOf(size);
    }

    public long updateIsSubmitted(String username, String recordId) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_IS_SUBMITTED, "1");
        long row = sqLiteDatabase.update(TABLENAME1, contentValues, COL_ID + "=?", new String[]{recordId});
        return row;
    }

    public boolean isLastVisitSubmitted(String recordId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLENAME1, new String[]{COL_USERNAME, COL_IS_SUBMITTED, COL_IS_ASSESSMENT}, COL_ID + "=?", new String[]{recordId}, null, null, null);
        boolean result = true;
        if (cursor.getCount() > 0) {
            return false;
        }
        return result;
    }

    public boolean LastVisitSubmitted(String sessionid) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLENAME1, new String[]{COL_SESSION, COL_IS_SUBMITTED}, COL_SESSION + "=?", new String[]{sessionid}, null, null, null);
        boolean result = true;
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            String isSubmitted = cursor.getString(cursor.getColumnIndex(COL_IS_SUBMITTED));
            if (isSubmitted.equals("0")) {
                result = false;
            } else {
                result = true;
            }
        }
        return result;
    }

    public Cursor getLastVisitData(String username) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLENAME1, new String[]{COL_USERNAME, COL_LEVEL, COL_SESSION, COL_BLOCK, COL_FACILITYNAME, COL_FACILITYTYPE, COL_DOV}, COL_USERNAME + "=?", new String[]{username}, null, null, null);
        //  Cursor cursor = sqLiteDatabase.rawQuery("select * from detailsofvisit ORDER BY id DESC LIMIT 1", null);
        return cursor;
    }

    public long deleteDetailsofVisit(String nom, String state, String district, String block, String facilityname, String facilitytype, String level, String dov, Bitmap image1, Bitmap image2, Bitmap image3, Bitmap image4, String count, String session, String username, String ansJson) {

        if (image1 != null) {
            ByteArrayOutputStream out1 = new ByteArrayOutputStream();
            image1.compress(Bitmap.CompressFormat.PNG, 100, out1);
            buffer1 = out1.toByteArray();
        }
        if (image2 != null) {
            ByteArrayOutputStream out2 = new ByteArrayOutputStream();
            image2.compress(Bitmap.CompressFormat.PNG, 100, out2);
            buffer2 = out2.toByteArray();
        }
        if (image3 != null) {
            ByteArrayOutputStream out3 = new ByteArrayOutputStream();
            image3.compress(Bitmap.CompressFormat.PNG, 100, out3);
            buffer3 = out3.toByteArray();
        }
        if (image4 != null) {
            ByteArrayOutputStream out4 = new ByteArrayOutputStream();
            image4.compress(Bitmap.CompressFormat.PNG, 100, out4);
            buffer4 = out4.toByteArray();
        }
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_DIST, district);
        contentValues.put(COL_NOM, nom);
        contentValues.put(COL_BLOCK, block);
        contentValues.put(COL_FACILITYTYPE, facilitytype);
        contentValues.put(COL_FACILITYNAME, facilityname);
        contentValues.put(COL_STATE, state);
        contentValues.put(COL_LEVEL, level);
        contentValues.put(COL_DOV, dov);
        contentValues.put(COL_IMAGE1, buffer1);
        contentValues.put(COL_IMAGE2, buffer2);
        contentValues.put(COL_IMAGE3, buffer3);
        contentValues.put(COL_IMAGE4, buffer4);
        contentValues.put(COL_COUNT, count);
        contentValues.put(COL_SESSION, session);
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_ANSJSON, ansJson);
        long row = sqLiteDatabase.delete(TABLENAME1, null, null);
        sqLiteDatabase.close();
        return row;

    }

    public long addDrugSupply(String ms, String am, String ab, String oxy, String antiretroids, String labitol, String misopristol, String gloves,
                              String uritick, String cwp, String ccl, String ss, String pp, String bsr, String sivs, String abbwbpp, String bp,
                              String stethoscope, String thermameter, String mucus, String hiv, String labour) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_MS, ms);
        contentValues.put(COL_AM, am);
        contentValues.put(COL_AB, ab);
        contentValues.put(COL_OXY, oxy);
        contentValues.put(COL_ANTIRETROIDS, antiretroids);
        contentValues.put(COL_LABITOL, labitol);
        contentValues.put(COL_MISOPRISTOL, misopristol);
        contentValues.put(COL_GLOVES, gloves);
        contentValues.put(COL_URITICK, uritick);
        contentValues.put(COL_CWP, cwp);
        contentValues.put(COL_CCL, ccl);
        contentValues.put(COL_SS, ss);
        contentValues.put(COL_PP, pp);
        contentValues.put(COL_BSR, bsr);
        contentValues.put(COL_SIVS, sivs);
        contentValues.put(COL_ABBWBPP, abbwbpp);
        contentValues.put(COL_BP, bp);
        contentValues.put(COL_STETHOSCOPE, stethoscope);
        contentValues.put(COL_THERMAMETER, thermameter);
        contentValues.put(COL_MUCUS, mucus);
        contentValues.put(COL_HIV, hiv);
        contentValues.put(COL_LABOUR, labour);
        long row = sqLiteDatabase.insert(TABLENAME2, null, contentValues);
        sqLiteDatabase.close();
        return row;


    }

    public long addClinicalStandards(String username, String pwf, String pciea, String hivpw, String ipw, String pd, String pe, String pmonitor, String pensures,
                                     String pprepares, String passists, String pconducts, String pperforms, String pperformsam, String pidentifies,
                                     String passesses, String pensuresex, String pensurescare, String pcounsels, String faupp, String count, String session, String ansJson,
                                     String latitude, String longitude, String uniqueId, int setAddOrUpdate) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PWF, pwf);
        contentValues.put(COL_PCIEA, pciea);
        contentValues.put(COL_HIVPW, hivpw);
        contentValues.put(COL_IPW, ipw);
        contentValues.put(COL_PD, pd);
        contentValues.put(COL_PE, pe);
        contentValues.put(COL_PMONITOR, pmonitor);
        contentValues.put(COL_PENSURES, pensures);
        contentValues.put(COL_PPREPARES, pprepares);
        contentValues.put(COL_PASSISTS, passists);
        contentValues.put(COL_PCONDUCTS, pconducts);
        contentValues.put(COL_PPERFORMS, pperforms);
        contentValues.put(COL_PPERFORMSAM, pperformsam);
        contentValues.put(COL_PIDENTIFIES, pidentifies);
        contentValues.put(COL_PASSESSES, passesses);
        contentValues.put(COL_PENSURESEX, pensuresex);
        contentValues.put(COL_PENSURESCARE, pensurescare);
        contentValues.put(COL_PCOUNSELS, pcounsels);
        contentValues.put(COL_FAUPP, faupp);
        contentValues.put(COL_COUNT, count);
        contentValues.put(COL_SESSION, session);
        contentValues.put(COL_IS_SUBMITTED, "0");
        contentValues.put(COL_ANSJSON, ansJson);
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_LATITUDE, latitude);
        contentValues.put(COL_LONGITUDE, longitude);
        contentValues.put(COL_UNIQUE_ID, uniqueId);
        long row;
        if (setAddOrUpdate == 0) {
            row = sqLiteDatabase.insert(TABLENAME3, null, contentValues);
        } else {
            row = sqLiteDatabase.update(TABLENAME3, contentValues, COL_SESSION + "=? AND " + COL_UNIQUE_ID + "=?", new String[]{session, String.valueOf(MentorConstant.recordId)});
        }
        sqLiteDatabase.close();
        return row;
    }


    public boolean isLastVisitSubmittedClinicalStandards(String recordId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLENAME3, new String[]{COL_USERNAME, COL_IS_SUBMITTED}, COL_SESSION + "=?", new String[]{recordId}, null, null, null);
        boolean result = true;
        if (cursor.getCount() > 0) {
            return false;
        }
        return result;
    }

    public Cursor getLastVisitDataClinicalStandards() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from clinicalstandards ORDER BY id DESC LIMIT 1", null);
        return cursor;
    }


    public long deleteClinicalStandards(String username, String pwf, String pciea, String hivpw, String ipw, String pd, String pe, String pmonitor, String pensures,
                                        String pprepares, String passists, String pconducts, String pperforms, String pperformsam, String pidentifies,
                                        String passesses, String pensuresex, String pensurescare, String pcounsels, String faupp, String count, String session, String ansJson) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PWF, pwf);
        contentValues.put(COL_PCIEA, pciea);
        contentValues.put(COL_HIVPW, hivpw);
        contentValues.put(COL_IPW, ipw);
        contentValues.put(COL_PD, pd);
        contentValues.put(COL_PE, pe);
        contentValues.put(COL_PMONITOR, pmonitor);
        contentValues.put(COL_PENSURES, pensures);
        contentValues.put(COL_PPREPARES, pprepares);
        contentValues.put(COL_PASSISTS, passists);
        contentValues.put(COL_PCONDUCTS, pconducts);
        contentValues.put(COL_PPERFORMS, pperforms);
        contentValues.put(COL_PPERFORMSAM, pperformsam);
        contentValues.put(COL_PIDENTIFIES, pidentifies);
        contentValues.put(COL_PASSESSES, passesses);
        contentValues.put(COL_PENSURESEX, pensuresex);
        contentValues.put(COL_PENSURESCARE, pensurescare);
        contentValues.put(COL_PCOUNSELS, pcounsels);
        contentValues.put(COL_FAUPP, faupp);
        contentValues.put(COL_COUNT, count);
        contentValues.put(COL_SESSION, session);
        contentValues.put(COL_ANSJSON, ansJson);
        contentValues.put(COL_USERNAME, username);
        long row = sqLiteDatabase.delete(TABLENAME3, null, null);
        sqLiteDatabase.close();
        return row;

    }

    public long addEquipments(String username, String fam, String fb, String blt, String wclock, String dbw, String dlr, String fs, String mlt, String foc, String ppd, String et,
                              String etray, String frw, String fbws, String count, String session, String ansJson) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_FAM, fam);
        contentValues.put(COL_FB, fb);
        contentValues.put(COL_BLT, blt);
        contentValues.put(COL_WCLOCK, wclock);
        contentValues.put(COL_DBW, dbw);
        contentValues.put(COL_DLR, dlr);
        contentValues.put(COL_FS, fs);
        contentValues.put(COL_MLT, mlt);
        contentValues.put(COL_FOC, foc);
        contentValues.put(COL_PPD, ppd);
        contentValues.put(COL_ET, et);
        contentValues.put(COL_ETRAY, etray);
        contentValues.put(COL_FRW, frw);
        contentValues.put(COL_FBWS, fbws);
        contentValues.put(COL_COUNT, count);
        contentValues.put(COL_SESSION, session);
        contentValues.put(COL_ANSJSON, ansJson);
        contentValues.put(COL_USERNAME, username);
        long row = sqLiteDatabase.insert(TABLENAME4, null, contentValues);
        sqLiteDatabase.close();
        return row;
    }

    public long deleteEquipments(String username, String fam, String fb, String blt, String wclock, String dbw, String dlr, String fs, String mlt, String foc, String ppd, String et,
                                 String etray, String frw, String fbws, String count, String session, String ansJson) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_FAM, fam);
        contentValues.put(COL_FB, fb);
        contentValues.put(COL_BLT, blt);
        contentValues.put(COL_WCLOCK, wclock);
        contentValues.put(COL_DBW, dbw);
        contentValues.put(COL_DLR, dlr);
        contentValues.put(COL_FS, fs);
        contentValues.put(COL_MLT, mlt);
        contentValues.put(COL_FOC, foc);
        contentValues.put(COL_PPD, ppd);
        contentValues.put(COL_ET, et);
        contentValues.put(COL_ETRAY, etray);
        contentValues.put(COL_FRW, frw);
        contentValues.put(COL_FBWS, fbws);
        contentValues.put(COL_COUNT, count);
        contentValues.put(COL_SESSION, session);
        contentValues.put(COL_ANSJSON, ansJson);
        contentValues.put(COL_USERNAME, username);
        long row = sqLiteDatabase.insert(TABLENAME4, null, contentValues);
        sqLiteDatabase.close();
        return row;
    }

    public long addInfrastructure(String username, String loi, String lrd, String psc, String lcfw, String astnsem, String walls, String ac, String av, String flsl, String pb, String lof, String asbt, String cnrnb, String cmds, String rw, String sob, String fwrw, String dua, String fhws, String eot, String count, String session, String ansJson, String three_side_space,
                                  String latitude, String longitude, String uniqueId, int setAddOrUpdate) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_LOI, loi);
        contentValues.put(COL_LRD, lrd);
        contentValues.put(COL_PSC, psc);
        contentValues.put(COL_LCFW, lcfw);
        contentValues.put(COL_ASTNSEM, astnsem);
        contentValues.put(COL_WALLS, walls);
        contentValues.put(COL_AC, ac);
        contentValues.put(COL_AV, av);
        contentValues.put(COL_FLSL, flsl);
        contentValues.put(COL_PB, pb);
        contentValues.put(COL_LOF, lof);
        contentValues.put(COL_ASBT, asbt);
        contentValues.put(COL_CNRNB, cnrnb);
        contentValues.put(COL_CMDS, cmds);
        contentValues.put(COL_RW, rw);
        contentValues.put(COL_SOB, sob);
        contentValues.put(COL_FWRW, fwrw);
        contentValues.put(COL_DUA, dua);
        contentValues.put(COL_FHWS, fhws);
        contentValues.put(COL_EOT, eot);
        contentValues.put(COL_THREE_SIDE_SPACE, three_side_space);
        contentValues.put(COL_COUNT, count);
        contentValues.put(COL_SESSION, session);
        contentValues.put(COL_IS_SUBMITTED, "0");
        contentValues.put(COL_ANSJSON, ansJson);
        contentValues.put(COL_LATITUDE, latitude);
        contentValues.put(COL_LONGITUDE, longitude);

        contentValues.put(COL_UNIQUE_ID, uniqueId);
        long row;
        if (setAddOrUpdate == 0) {
            row = sqLiteDatabase.insert(TABLENAME5, null, contentValues);
        } else {
            row = sqLiteDatabase.update(TABLENAME5, contentValues, COL_SESSION + "=? AND " + COL_UNIQUE_ID + "=?", new String[]{session, String.valueOf(MentorConstant.recordId)});
        }
        sqLiteDatabase.close();
        return row;

    }

    public boolean isLastVisitSubmittedInfrastructure(String recordId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLENAME5, new String[]{COL_USERNAME, COL_IS_SUBMITTED}, COL_SESSION + "=?", new String[]{recordId}, null, null, null);
        boolean result = true;
        if (cursor.getCount() > 0) {
            return false;
        }
        return result;
    }

    public Cursor getLastVisitDataInfrastructure() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from infrastructure ORDER BY id DESC LIMIT 1", null);
        return cursor;
    }


    public long deleteInfrastructure(String username, String loi, String lrd, String psc, String lcfw, String astnsem, String walls, String ac, String av, String flsl, String pb, String lof, String asbt,
                                     String cnrnb, String cmds, String rw, String sob, String fwrw, String dua, String fhws, String eot, String count, String session, String ansJson, String three_side_space) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_LOI, loi);
        contentValues.put(COL_LRD, lrd);
        contentValues.put(COL_PSC, psc);
        contentValues.put(COL_LCFW, lcfw);
        contentValues.put(COL_ASTNSEM, astnsem);
        contentValues.put(COL_WALLS, walls);
        contentValues.put(COL_AC, ac);
        contentValues.put(COL_AV, av);
        contentValues.put(COL_FLSL, flsl);
        contentValues.put(COL_PB, pb);
        contentValues.put(COL_LOF, lof);
        contentValues.put(COL_ASBT, asbt);
        contentValues.put(COL_CNRNB, cnrnb);
        contentValues.put(COL_CMDS, cmds);
        contentValues.put(COL_RW, rw);
        contentValues.put(COL_SOB, sob);
        contentValues.put(COL_FWRW, fwrw);
        contentValues.put(COL_DUA, dua);
        contentValues.put(COL_FHWS, fhws);
        contentValues.put(COL_EOT, eot);
        contentValues.put(COL_THREE_SIDE_SPACE, three_side_space);
        contentValues.put(COL_COUNT, count);
        contentValues.put(COL_SESSION, session);
        contentValues.put(COL_ANSJSON, ansJson);
        contentValues.put(COL_USERNAME, username);
        long row = sqLiteDatabase.delete(TABLENAME5, null, null);
        sqLiteDatabase.close();
        return row;

    }

    public long addMentoringPractices(String username, String rolr, String lp, String ga, String pv, String aua, String acp, String cnd, String amtsl, String enbc, String nr, String scn, String pph, String impe, String impo, String pp, String dnd, String dpph, String dpe, String dnr, String count, String session, String ansJson,
                                      String latitude, String longitude, String uniqueId, int setAddOrUpdate) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ROLR, rolr);
        contentValues.put(COL_LP, lp);
        contentValues.put(COL_GA, ga);
        contentValues.put(COL_PV, pv);
        contentValues.put(COL_AUA, aua);
        contentValues.put(COL_ACP, acp);
        contentValues.put(COL_CND, cnd);
        contentValues.put(COL_AMTSL, amtsl);
        contentValues.put(COL_ENBC, enbc);
        contentValues.put(COL_NR, nr);
        contentValues.put(COL_SCN, scn);
        contentValues.put(COL_PPH, pph);
        contentValues.put(COL_IMPE, impe);
        contentValues.put(COL_IMPO, impo);
        contentValues.put(COL_PP1, pp);
        contentValues.put(COL_DND, dnd);
        contentValues.put(COL_DPPH, dpph);
        contentValues.put(COL_DPE, dpe);
        contentValues.put(COL_DNR, dnr);
        contentValues.put(COL_COUNT, count);
        contentValues.put(COL_SESSION, session);
        contentValues.put(COL_IS_SUBMITTED, "0");
        contentValues.put(COL_ANSJSON, ansJson);
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_LATITUDE, latitude);
        contentValues.put(COL_LONGITUDE, longitude);
        contentValues.put(COL_UNIQUE_ID, uniqueId);
        long row;
        if (setAddOrUpdate == 0) {
            row = sqLiteDatabase.insert(TABLENAME6, null, contentValues);
        } else {
            row = sqLiteDatabase.update(TABLENAME6, contentValues, COL_SESSION + "=? AND " + COL_UNIQUE_ID + "=?", new String[]{session, String.valueOf(MentorConstant.recordId)});
        }
        sqLiteDatabase.close();
        return row;

    }

    public long deleteMentoringPractices(String username, String rolr, String lp, String ga, String pv, String aua, String acp, String cnd, String amtsl, String enbc, String nr, String scn, String pph, String impe, String impo, String pp, String dnd, String dpph, String dpe, String dnr, String count, String session, String ansJson) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ROLR, rolr);
        contentValues.put(COL_LP, lp);
        contentValues.put(COL_GA, ga);
        contentValues.put(COL_PV, pv);
        contentValues.put(COL_AUA, aua);
        contentValues.put(COL_ACP, acp);
        contentValues.put(COL_CND, cnd);
        contentValues.put(COL_AMTSL, amtsl);
        contentValues.put(COL_ENBC, enbc);
        contentValues.put(COL_NR, nr);
        contentValues.put(COL_SCN, scn);
        contentValues.put(COL_PPH, pph);
        contentValues.put(COL_IMPE, impe);
        contentValues.put(COL_IMPO, impo);
        contentValues.put(COL_PP1, pp);
        contentValues.put(COL_DND, dnd);
        contentValues.put(COL_DPPH, dpph);
        contentValues.put(COL_DPE, dpe);
        contentValues.put(COL_DNR, dnr);
        contentValues.put(COL_COUNT, count);
        contentValues.put(COL_SESSION, session);
        contentValues.put(COL_ANSJSON, ansJson);
        contentValues.put(COL_USERNAME, username);
        long row = sqLiteDatabase.delete(TABLENAME6, null, null);
        sqLiteDatabase.close();
        return row;

    }

    public boolean isLastVisitSubmittedMentoringPractices(String recordId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLENAME6, new String[]{COL_USERNAME, COL_IS_SUBMITTED}, COL_SESSION + "=?", new String[]{recordId}, null, null, null);
        boolean result = true;
        if (cursor.getCount() > 0) {
            return false;
        }
        return result;
    }

    public Cursor getLastVisitDataMentoringPractices() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from mentoringpractices ORDER BY id DESC LIMIT 1", null);
        return cursor;
    }


    public long addStaffMaternity(String username, String obg, String sba, String molr, String mod, String snlr, String snd, String lhvslr, String lhvsd, String count, String session, String ansJson, String lhvWorking, String lhvTrained,
                                  String latitude, String longitude, String uniqueId, int setAddOrUpdate,String[] data) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_OBG, obg);
        contentValues.put(COL_SBA, sba);
        contentValues.put(COL_MOLR, molr);
        contentValues.put(COL_MOD, mod);
        contentValues.put(COL_SNLR, snlr);
        contentValues.put(COL_SND, snd);
        contentValues.put(COL_LHVSLR, lhvslr);
        contentValues.put(COL_LHVSD, lhvsd);
        contentValues.put(COL_LHVWORKING, lhvWorking);
        contentValues.put(COL_LHVTRAINED, lhvTrained);
        contentValues.put(COL_COUNT, count);
        contentValues.put(COL_SESSION, session);
        contentValues.put(COL_IS_SUBMITTED, "0");
        contentValues.put(COL_ANSJSON, ansJson);
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_LATITUDE, latitude);
        contentValues.put(COL_LONGITUDE, longitude);
        contentValues.put(COL_UNIQUE_ID, uniqueId);

        contentValues.put(COL_NSEL, data[0]);
        contentValues.put(COL_NSR_MNHToolKit, data[1]);
        contentValues.put(COL_VAR_Staff, data[2]);
        contentValues.put(COL_Labour_TableLR, data[3]);
        contentValues.put(COL_Labour_TableReq, data[4]);
        contentValues.put(COL_Variance_LbrTbl, data[5]);
        contentValues.put(COL_Caesarian_Act, data[6]);
        contentValues.put(COL_Person_InCharge, data[7]);
        contentValues.put(COL_Mobile_InCharge, data[8]);
        contentValues.put(COL_AMDL,data[9]);

        long row;
        if (setAddOrUpdate == 0) {
            row = sqLiteDatabase.insert(TABLENAME7, null, contentValues);
        } else {
            row = sqLiteDatabase.update(TABLENAME7, contentValues, COL_SESSION + "=? AND " + COL_UNIQUE_ID + "=?", new String[]{session, String.valueOf(MentorConstant.recordId)});
        }
        sqLiteDatabase.close();
        return row;

    }

    public long deleteStaffMaternity(String username, String obg, String sba, String molr, String mod, String snlr, String snd, String lhvslr, String lhvsd, String count, String session, String ansJson, String lhvWorking, String lhvTrained) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_OBG, obg);
        contentValues.put(COL_SBA, sba);
        contentValues.put(COL_MOLR, molr);
        contentValues.put(COL_MOD, mod);
        contentValues.put(COL_SNLR, snlr);
        contentValues.put(COL_SND, snd);
        contentValues.put(COL_LHVSLR, lhvslr);
        contentValues.put(COL_LHVSD, lhvsd);
        contentValues.put(COL_LHVWORKING, lhvWorking);
        contentValues.put(COL_LHVTRAINED, lhvTrained);
        contentValues.put(COL_COUNT, count);
        contentValues.put(COL_SESSION, session);
        contentValues.put(COL_ANSJSON, ansJson);
        contentValues.put(COL_USERNAME, username);
        long row = sqLiteDatabase.delete(TABLENAME7, null, null);
        sqLiteDatabase.close();
        return row;

    }

    public boolean isLastVisitSubmittedStaffMaternity(String recordId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLENAME7, new String[]{COL_USERNAME, COL_IS_SUBMITTED}, COL_SESSION + "=?", new String[]{recordId}, null, null, null);
        boolean result = true;
        if (cursor.getCount() > 0) {
            return false;
        }
        return result;
    }

    public Cursor getLastVisitDataStaffMaternity() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from staffmaternity ORDER BY id DESC LIMIT 1", null);
        return cursor;
    }


    public long addLabourRoom(String username, String col_1, String col_2, String col_3, String col_4, String col_5, String col_6, String col_7, String col_8, String col_9, String col_10, String col_11, String col_12, String col_13, String col_14, String col_15, String col_16, String col_17, String col_18, String col_19, String col_20, String col_21, String col_22, String col_23, String col_24, String col_25, String col_26, String count, String session, String ansJson,
                              String latitude, String longitude, String uniqueId, int setAddOrUpdate) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, col_1);
        contentValues.put(COL_2, col_2);
        contentValues.put(COL_3, col_3);
        contentValues.put(COL_4, col_4);
        contentValues.put(COL_5, col_5);
        contentValues.put(COL_6, col_6);
        contentValues.put(COL_7, col_7);
        contentValues.put(COL_8, col_8);
        contentValues.put(COL_9, col_9);
        contentValues.put(COL_10, col_10);
        contentValues.put(COL_11, col_11);
        contentValues.put(COL_12, col_12);
        contentValues.put(COL_13, col_13);
        contentValues.put(COL_14, col_14);
        contentValues.put(COL_15, col_15);
        contentValues.put(COL_16, col_16);
        contentValues.put(COL_17, col_17);
        contentValues.put(COL_18, col_18);
        contentValues.put(COL_19, col_19);
        contentValues.put(COL_20, col_20);
        contentValues.put(COL_21, col_21);
        contentValues.put(COL_22, col_22);
        contentValues.put(COL_23, col_23);
        contentValues.put(COL_24, col_24);
        contentValues.put(COL_25, col_25);
        contentValues.put(COL_26, col_26);
        contentValues.put(COL_COUNT, count);
        contentValues.put(COL_SESSION, session);
        contentValues.put(COL_IS_SUBMITTED, "0");
        contentValues.put(COL_ANSJSON, ansJson);
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_LATITUDE, latitude);
        contentValues.put(COL_LONGITUDE, longitude);
        contentValues.put(COL_UNIQUE_ID, uniqueId);
        long row;
        if (setAddOrUpdate == 0) {
            row = sqLiteDatabase.insert(TABLENAME8, null, contentValues);
        } else {
            row = sqLiteDatabase.update(TABLENAME8, contentValues, COL_SESSION + "=? AND " + COL_UNIQUE_ID + "=?", new String[]{session, String.valueOf(MentorConstant.recordId)});
        }
        sqLiteDatabase.close();
        return row;

    }

    public long deleteLabourRoom(String username, String col_1, String col_2, String col_3, String col_4, String col_5, String col_6, String col_7, String col_8, String col_9, String col_10, String col_11, String col_12, String col_13, String col_14, String col_15, String col_16, String col_17, String col_18, String col_19, String col_20, String col_21, String col_22, String col_23, String col_24, String col_25, String col_26, String count, String session, String ansJson) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, col_1);
        contentValues.put(COL_2, col_2);
        contentValues.put(COL_3, col_3);
        contentValues.put(COL_4, col_4);
        contentValues.put(COL_5, col_5);
        contentValues.put(COL_6, col_6);
        contentValues.put(COL_7, col_7);
        contentValues.put(COL_8, col_8);
        contentValues.put(COL_9, col_9);
        contentValues.put(COL_10, col_10);
        contentValues.put(COL_11, col_11);
        contentValues.put(COL_12, col_12);
        contentValues.put(COL_13, col_13);
        contentValues.put(COL_14, col_14);
        contentValues.put(COL_15, col_15);
        contentValues.put(COL_16, col_16);
        contentValues.put(COL_17, col_17);
        contentValues.put(COL_18, col_18);
        contentValues.put(COL_19, col_19);
        contentValues.put(COL_20, col_20);
        contentValues.put(COL_21, col_21);
        contentValues.put(COL_22, col_22);
        contentValues.put(COL_23, col_23);
        contentValues.put(COL_24, col_24);
        contentValues.put(COL_25, col_25);
        contentValues.put(COL_26, col_26);
        contentValues.put(COL_COUNT, count);
        contentValues.put(COL_SESSION, session);
        contentValues.put(COL_ANSJSON, ansJson);
        contentValues.put(COL_USERNAME, username);
        long row = sqLiteDatabase.delete(TABLENAME8, null, null);
        sqLiteDatabase.close();
        return row;

    }

    public boolean isLastVisitSubmittedLabourRoom(String recordId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLENAME8, new String[]{COL_USERNAME, COL_IS_SUBMITTED}, COL_SESSION + "=?", new String[]{recordId}, null, null, null);
        boolean result = true;
        if (cursor.getCount() > 0) {
            return false;
        }
        return result;
    }

    public Cursor getLastVisitDataLabourRoom() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from labourroom ORDER BY id DESC LIMIT 1", null);
        return cursor;
    }

    public long addReporting(String username, String col_1, String col_2, String count, String session, String ansJson, String col_3, String col_4,
                             String latitude, String longitude, String uniqueId, int setAddOrUpdate) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, col_1);
        contentValues.put(COL_2, col_2);
        contentValues.put(COL_3, col_3);
        contentValues.put(COL_4, col_4);
        contentValues.put(COL_COUNT, count);
        contentValues.put(COL_SESSION, session);
        contentValues.put(COL_IS_SUBMITTED, "0");
        contentValues.put(COL_ANSJSON, ansJson);
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_LATITUDE, latitude);
        contentValues.put(COL_LONGITUDE, longitude);
        contentValues.put(COL_UNIQUE_ID, uniqueId);
        long row;
        if (setAddOrUpdate == 0) {
            row = sqLiteDatabase.insert(TABLENAME9, null, contentValues);
        } else {
            row = sqLiteDatabase.update(TABLENAME9, contentValues, COL_SESSION + "=? AND " + COL_UNIQUE_ID + "=?", new String[]{session, String.valueOf(MentorConstant.recordId)});
        }
        sqLiteDatabase.close();
        return row;
    }

    public long deleteReporting(String username, String col_1, String col_2, String count, String session, String ansJson, String col_3, String col_4) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, col_1);
        contentValues.put(COL_2, col_2);
        contentValues.put(COL_3, col_3);
        contentValues.put(COL_4, col_4);
        contentValues.put(COL_COUNT, count);
        contentValues.put(COL_SESSION, session);
        contentValues.put(COL_ANSJSON, ansJson);
        contentValues.put(COL_USERNAME, username);
        long row = sqLiteDatabase.delete(TABLENAME9, null, null);
        sqLiteDatabase.close();
        return row;
    }

    public boolean isLastVisitSubmittedReporting(String recordId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLENAME9, new String[]{COL_USERNAME, COL_IS_SUBMITTED}, COL_SESSION + "=?", new String[]{recordId}, null, null, null);
        boolean result = true;
        if (cursor.getCount() > 0) {
            return false;
        }
        return result;
    }

    public Cursor getLastVisitDataReporting() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from recordingreporting ORDER BY id DESC LIMIT 1", null);
        return cursor;
    }


    public long addNextVisitDate(String username, String col_1, String count, String session, String ansJson,
                                 String latitude, String longitude, String uniqueId, int setAddOrUpdate) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, col_1);
        contentValues.put(COL_COUNT, count);
        contentValues.put(COL_SESSION, session);
        contentValues.put(COL_IS_SUBMITTED, "0");
        contentValues.put(COL_ANSJSON, ansJson);
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_LATITUDE, latitude);
        contentValues.put(COL_LONGITUDE, longitude);
        contentValues.put(COL_UNIQUE_ID, uniqueId);
        long row;
        if (setAddOrUpdate == 0) {
            row = sqLiteDatabase.insert(TABLENAME10, null, contentValues);
        } else {
            row = sqLiteDatabase.update(TABLENAME10, contentValues, COL_SESSION + "=? AND " + COL_UNIQUE_ID + "=?", new String[]{session, String.valueOf(MentorConstant.recordId)});
        }
        sqLiteDatabase.close();
        return row;

    }

    public long deleteNextVisitDate(String username, String col_1, String count, String session, String ansJson) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, col_1);
        contentValues.put(COL_COUNT, count);
        contentValues.put(COL_SESSION, session);
        contentValues.put(COL_ANSJSON, ansJson);
        contentValues.put(COL_USERNAME, username);
        long row = sqLiteDatabase.delete(TABLENAME10, null, null);
        sqLiteDatabase.close();
        return row;

    }

    public boolean isLastVisitSubmittedNextVisitDate(String recordId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLENAME10, new String[]{COL_USERNAME, COL_IS_SUBMITTED}, COL_SESSION + "=?", new String[]{recordId}, null, null, null);
        boolean result = true;
        if (cursor.getCount() > 0) {
            return false;
        }
        return result;
    }

    public Cursor getLastVisitDataNextVisitDate() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from nextvisitdate ORDER BY id DESC LIMIT 1", null);
        return cursor;
    }


    public long addCommentsRemarks(String username, String col_1, String count, String session, String ansJson,
                                   String latitude, String longitude, String uniqueId, int setAddOrUpdate) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, col_1);
        contentValues.put(COL_COUNT, count);
        contentValues.put(COL_SESSION, session);
        contentValues.put(COL_IS_SUBMITTED, "0");
        contentValues.put(COL_ANSJSON, ansJson);
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_LATITUDE, latitude);
        contentValues.put(COL_LONGITUDE, longitude);
        contentValues.put(COL_UNIQUE_ID, uniqueId);
        long row;
        if (setAddOrUpdate == 0) {
            row = sqLiteDatabase.insert(TABLENAME11, null, contentValues);
        } else {
            row = sqLiteDatabase.update(TABLENAME11, contentValues, COL_SESSION + "=? AND " + COL_UNIQUE_ID + "=?", new String[]{session, String.valueOf(MentorConstant.recordId)});
        }
        sqLiteDatabase.close();
        return row;

    }
    public long addCompetencyTrackingData(String username, String count, String session, String ansJson,
                                   String latitude, String longitude, String uniqueId, int setAddOrUpdate) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_COUNT, count);
        contentValues.put(COL_SESSION, session);
        contentValues.put(COL_IS_SUBMITTED, "0");
        contentValues.put(COL_ANSJSON, ansJson);
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_LATITUDE, latitude);
        contentValues.put(COL_LONGITUDE, longitude);
        contentValues.put(COL_UNIQUE_ID, uniqueId);
        long row;
        if (setAddOrUpdate == 0) {
            row = sqLiteDatabase.insert(TABLENAME17, null, contentValues);
        } else {
            row = sqLiteDatabase.update(TABLENAME17, contentValues, COL_SESSION + "=? AND " + COL_UNIQUE_ID + "=?", new String[]{session, String.valueOf(MentorConstant.recordId)});
        }
        sqLiteDatabase.close();
        return row;

    }
    public long addClientfeedbackData(String username, String count, String session, String ansJson,
                                         String latitude, String longitude, String uniqueId, int setAddOrUpdate) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_COUNT, count);
        contentValues.put(COL_SESSION, session);
        contentValues.put(COL_IS_SUBMITTED, "0");
        contentValues.put(COL_ANSJSON, ansJson);
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_LATITUDE, latitude);
        contentValues.put(COL_LONGITUDE, longitude);
        contentValues.put(COL_UNIQUE_ID, uniqueId);
        long row;
        if (setAddOrUpdate == 0) {
            row = sqLiteDatabase.insert(TABLENAME18, null, contentValues);
        } else {
            row = sqLiteDatabase.update(TABLENAME18, contentValues, COL_SESSION + "=? AND " + COL_UNIQUE_ID + "=?", new String[]{session, String.valueOf(MentorConstant.recordId)});
        }
        sqLiteDatabase.close();
        return row;

    }

    public String getClientfeedbackData() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String ansJson="";
        Cursor cursor = sqLiteDatabase.query(TABLENAME18,new String[]{"ansJson"}, null,null,null,null,null);
        try {
            if(cursor!=null && cursor.moveToFirst()){
                ansJson=cursor.getString(0);
            }
        }catch (Exception e){

        }finally {
            if(cursor!=null)
                cursor.close();
        }

        sqLiteDatabase.close();
        return ansJson;

    }
    public String getClientfeedbackData(String sessionId) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String ansJson="";
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT ansJson FROM "+TABLENAME18+ " Where "+COL_SESSION+" = '"+sessionId+"'",null);
        //Cursor cursor = sqLiteDatabase.query(TABLENAME18,new String[]{"ansJson"}, null,null,null,null,null);
        try {
            if(cursor!=null && cursor.moveToFirst()){
                ansJson=cursor.getString(0);
            }
        }catch (Exception e){

        }finally {
            if(cursor!=null)
                cursor.close();
        }

        sqLiteDatabase.close();
        return ansJson;

    }

    public long deleteCommentsRemarks(String username, String col_1, String count, String session, String ansJson) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, col_1);
        contentValues.put(COL_COUNT, count);
        contentValues.put(COL_SESSION, session);
        contentValues.put(COL_ANSJSON, ansJson);
        contentValues.put(COL_USERNAME, username);
        long row = sqLiteDatabase.delete(TABLENAME11, null, null);
        sqLiteDatabase.close();
        return row;

    }

    public boolean isLastVisitSubmittedCommentsRemarks(String sessionId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLENAME11, new String[]{COL_USERNAME, COL_IS_SUBMITTED}, COL_SESSION + "=?", new String[]{sessionId}, null, null, null);
        boolean result = true;
        if (cursor.getCount() > 0) {
            return false;
        }
        return result;
    }
    public boolean isLastVisitdataSubmited(String sessionId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLENAME17, new String[]{COL_USERNAME, COL_IS_SUBMITTED}, COL_SESSION + "=?", new String[]{sessionId}, null, null, null);
        boolean result = true;
        if (cursor.getCount() > 0) {
            return false;
        }
        return result;
    }

    public Cursor getLastVisitDataCommentsRemarks() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from comments ORDER BY id DESC LIMIT 1", null);
        return cursor;
    }

    public long saveServerQuestions(String jsonQuestions) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, jsonQuestions);
        long row = sqLiteDatabase.insert(TABLENAME12, null, contentValues);
        sqLiteDatabase.close();
        return row;
    }

    public long saveJsonToPost(String username, String serverUrl, String json, String status, String sessionid, String imageFile1, String imageFile2,
                               String imageFile3, String imageFile4, String reference_key) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_URL, serverUrl);
        contentValues.put(COL_JSON, json);
        contentValues.put(COL_STATUS, status);
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_SESSION, sessionid);
        contentValues.put(COL_FILE1, imageFile1);
        contentValues.put(COL_FILE2, imageFile2);
        contentValues.put(COL_FILE3, imageFile3);
        contentValues.put(COL_FILE4, imageFile4);
        contentValues.put("reference_key", reference_key);
        long row = sqLiteDatabase.insert(TABLENAME13, null, contentValues);
        sqLiteDatabase.close();
        return row;
    }

    public long updatesaveJsonToPost(String status) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_STATUS, status);
        long row = sqLiteDatabase.update(TABLENAME13, contentValues, COL_STATUS + "=?", new String[]{status});
        sqLiteDatabase.close();
        return row;
    }

    public long updateSyncStatus(String sessionid) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        // Cursor cursor=sqLiteDatabase.query(TABLENAME1,new String[]{COL_USERNAME,COL_IS_SUBMITTED},COL_USERNAME + "=?",new String[]{username},null,null,null);
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_SYNC_STATUS, "synced");
        long row = sqLiteDatabase.update(TABLENAME1, contentValues, COL_SESSION + "=?", new String[]{sessionid});
        sqLiteDatabase.close();
        return row;
    }

    public boolean sessionExists(String session) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLENAME1, new String[]{COL_SESSION}, COL_SESSION + "=?", new String[]{session}, null, null, null);

        // Cursor cursor = sqLiteDatabase.rawQuery("select isSubmitted from comments ORDER BY id DESC LIMIT 1", null);
        boolean result = true;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            return true;
        } else {
            return false;
        }
    }

    public long deleteJsonToPost(String session) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        /*ContentValues contentValues = new ContentValues();
        contentValues.put(COL_URL, serverUrl);
        contentValues.put(COL_JSON, json);
        contentValues.put(COL_STATUS, status);
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_SESSION, sessionid);
        contentValues.put(COL_FILE1, imageFile1);
        contentValues.put(COL_FILE1, imageFile2);
        contentValues.put(COL_FILE1, imageFile3);
        contentValues.put(COL_FILE1, imageFile4);*/
        long row = sqLiteDatabase.delete(TABLENAME13, COL_SESSION + "='" + session + "'", null);
        sqLiteDatabase.close();
        return row;
    }

    public long deleteJsonToPostWitoutImage(String username, String serverUrl, String json, String status, String sessionid, String imageFile1, String imageFile2, String imageFile3, String imageFile4) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_URL, serverUrl);
        contentValues.put(COL_JSON, json);
        contentValues.put(COL_STATUS, status);
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_SESSION, sessionid);
        contentValues.put(COL_FILE1, imageFile1);
        contentValues.put(COL_FILE1, imageFile2);
        contentValues.put(COL_FILE1, imageFile3);
        contentValues.put(COL_FILE1, imageFile4);
        long row = sqLiteDatabase.delete(TABLENAME13, null, null);
        sqLiteDatabase.close();
        return row;
    }

    public long saveLocationMaster(SQLiteDatabase sqLiteDatabase, String StateName, String DistrictName, String BlockName, String PHCName, String FType, String SubCenter, String Village, String Taluka, String SCTaluka,String facility, String state_code,String district_code,String block_code,String facility_code,String subfacility_code) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("DistrictName", DistrictName);
        contentValues.put("BlockName", BlockName);
        contentValues.put("FType", FType);
        contentValues.put("PHCName", PHCName);
        contentValues.put("StateName", StateName);
        contentValues.put("SubCenter", SubCenter);
        contentValues.put("Village", Village);
        contentValues.put("Taluka", Taluka);
        contentValues.put("SCTaluka", SCTaluka);

        contentValues.put("facility", facility);
        contentValues.put("state_code", state_code);
        contentValues.put("district_code", district_code);
        contentValues.put("block_code", block_code);
        contentValues.put("facility_code", facility_code);
        contentValues.put("subfacility_code", subfacility_code);

        long row = sqLiteDatabase.insert(TABLENAME_LocationMaster, null, contentValues);
        return row;
    }


    public List<String> getBlockList(String _selectedDistrict) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<String> blockList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT BlockName from facilityMaster where DistrictName = '" + _selectedDistrict + "' AND DistrictName IS NOT NULL And BlockName IS NOT NULL AND FType IS NOT NULL AND PHCName IS NOT NULL GROUP BY BlockName", null);
        if (cursor.moveToFirst()) {
            do {
                blockList.add(cursor.getString(cursor.getColumnIndex("BlockName")));
            } while (cursor.moveToNext());
        }

        return blockList;
    }

    public List<String> getBlockList() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<String> blockList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT BlockName from facilityMaster where DistrictName IS NOT NULL And BlockName IS NOT NULL AND FType IS NOT NULL AND PHCName IS NOT NULL AND SubCenter IS NOT NULL GROUP BY BlockName", null);
        if (cursor.moveToFirst()) {
            do {
                blockList.add(cursor.getString(cursor.getColumnIndex("BlockName")));
            } while (cursor.moveToNext());
        }

        return blockList;
    }

    public boolean checkFacilityAvailable(String fType, String blockName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT BlockName from facilityMaster where BlockName = '" + blockName + "' AND FType='" + fType + "' AND DistrictName IS NOT NULL And BlockName IS NOT NULL AND FType IS NOT NULL  GROUP BY BlockName", null);
        if (cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    public List<String> getFacilityName(String fType, String blockName, String _selectedDistrict) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<String> facilityTypeList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT PHCName from facilityMaster where BlockName = '" + blockName + "' AND FType='" + fType + "' AND DistrictName='" + _selectedDistrict + "' AND DistrictName IS NOT NULL And BlockName IS NOT NULL AND FType IS NOT NULL  GROUP BY PHCName", null);
        if (cursor.moveToFirst()) {
            do {
                facilityTypeList.add(cursor.getString(cursor.getColumnIndex("PHCName")));
            } while (cursor.moveToNext());
        }
        return facilityTypeList;
    }

    public List<String> getFacilityNameForDistrictHospital(String fType) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<String> facilityTypeList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT PHCName from facilityMaster where FType='" + fType + "' AND DistrictName IS NOT NULL And BlockName IS NOT NULL AND FType IS NOT NULL AND PHCName IS NOT NULL AND SubCenter IS NOT NULL  GROUP BY PHCName", null);
        if (cursor.moveToFirst()) {
            do {
                facilityTypeList.add(cursor.getString(cursor.getColumnIndex("PHCName")));
            } while (cursor.moveToNext());
        }
        return facilityTypeList;
    }

    public List<String> getFacilityNameForSubCenter(String blockName) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<String> facilityTypeList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT SubCenter from facilityMaster where BlockName = '" + blockName + "' AND DistrictName IS NOT NULL And BlockName IS NOT NULL AND FType IS NOT NULL AND PHCName IS NOT NULL AND SubCenter IS NOT NULL  GROUP BY SubCenter", null);
        if (cursor.moveToFirst()) {
            do {
                facilityTypeList.add(cursor.getString(cursor.getColumnIndex("SubCenter")));
            } while (cursor.moveToNext());
        }
        return facilityTypeList;
    }

    public List<String> getDistrictList(List<String> listDistrict) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from facilityMaster where DistrictName IS NOT NULL And BlockName IS NOT NULL AND FType IS NOT NULL AND PHCName IS NOT NULL AND SubCenter IS NOT NULL GROUP BY DistrictName", null);
        if (cursor.moveToFirst()) {
            do {
                listDistrict.add(cursor.getString(cursor.getColumnIndex("DistrictName")));
            } while (cursor.moveToNext());
        }

        return listDistrict;
    }


    public long updateLatAndLongWithDetailsOfVisit(String id, String latitude, String longitude) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_LATITUDE, latitude);
        contentValues.put(COL_LONGITUDE, longitude);
        long row = sqLiteDatabase.update(TABLENAME1, contentValues, "id=" + id, null);
        sqLiteDatabase.close();
        return row;
    }


    public String getStateCodeFromName(String stateName) {
        String state_code = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT state_code from facilityMaster where StateName= '"+stateName+"'", null);
        if (cursor.moveToFirst()) {
            do {
                state_code = cursor.getString(cursor.getColumnIndex("state_code"));
            } while (cursor.moveToNext());
        }

        return state_code;
    }

    public String getBlockCodeFromName(String blockName) {
        String block_code = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT block_code from facilityMaster where BlockName= '"+blockName+"'", null);
        if (cursor.moveToFirst()) {
            do {
                block_code = cursor.getString(cursor.getColumnIndex("block_code"));
            } while (cursor.moveToNext());
        }

        return block_code;
    }

    public String getDistrictCodeFromName(String districtName) {
        String districtCode = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT district_code from facilityMaster where DistrictName='"+districtName+"'", null);
        if (cursor.moveToFirst()) {
            do {
                districtCode = cursor.getString(cursor.getColumnIndex("district_code"));
            } while (cursor.moveToNext());
        }

        return districtCode;
    }

    public String getFacilityCodeFromName(String facilityName) {
        String facilityCode = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT facility_code from facilityMaster where facility= '"+facilityName+"'", null);
        if (cursor.moveToFirst()) {
            do {
                facilityCode = cursor.getString(cursor.getColumnIndex("facility_code"));
            } while (cursor.moveToNext());
        }

        return facilityCode;
    }


    public String getDistrictNameForMentor() {
        String districtName = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT DistrictName from facilityMaster GROUP BY DistrictName", null);
        if (cursor.moveToFirst()) {
            do {
                districtName = cursor.getString(cursor.getColumnIndex("DistrictName"));
            } while (cursor.moveToNext());
        }

        return districtName;
    }

    public int getLocationMasterCount() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from facilityMaster", null);
        return cursor.getCount();
    }

    public void clearDbAtLogoutTime() {
        SQLiteDatabase db = this.getWritableDatabase();
        String prefixOfQuery = "delete from ";
        String query = prefixOfQuery + TABLENAME;
        db.execSQL(query);

        String query1 = prefixOfQuery + TABLENAME1;
        db.execSQL(query1);

        String query2 = prefixOfQuery + TABLENAME2;
        db.execSQL(query2);

        String query3 = prefixOfQuery + TABLENAME3;
        db.execSQL(query3);

        String query4 = prefixOfQuery + TABLENAME4;
        db.execSQL(query4);

        String query5 = prefixOfQuery + TABLENAME5;
        db.execSQL(query5);

        String query6 = prefixOfQuery + TABLENAME6;
        db.execSQL(query6);

        String query7 = prefixOfQuery + TABLENAME7;
        db.execSQL(query7);

        String query8 = prefixOfQuery + TABLENAME8;
        db.execSQL(query8);

        String query9 = prefixOfQuery + TABLENAME9;
        db.execSQL(query9);

        String query10 = prefixOfQuery + TABLENAME10;
        db.execSQL(query10);

        String query11 = prefixOfQuery + TABLENAME11;
        db.execSQL(query11);

        String query12 = prefixOfQuery + TABLENAME12;
        db.execSQL(query12);

        String query13 = prefixOfQuery + TABLENAME13;
        db.execSQL(query13);

        String query14 = prefixOfQuery + TABLENAME_LocationMaster;
        db.execSQL(query14);

        String query15 = prefixOfQuery + TABLE_ClinicalStandards;
        db.execSQL(query15);

        String query16 = prefixOfQuery + TABLE_WorkPlan;
        db.execSQL(query16);

    }

    public void getFacilityTypeList(String strDistrict, String strBlock) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from facilityMaster where BlockName = '" + strBlock + "' AND DistrictName = '" + strDistrict + "' AND FType IS NOT NULL AND PHCName IS NOT NULL AND SubCenter IS NOT NULL GROUP BY FType", null);
        if (cursor.moveToFirst()) {
            do {
                String fType = (cursor.getString(cursor.getColumnIndex("FType")));
                if (fType.equalsIgnoreCase("RH")) {
                    MentorConstant.RH = true;
                }if (fType.equalsIgnoreCase("TH RIMS")) {
                    MentorConstant.TH_RIMS = true;
                }if (fType.equalsIgnoreCase("TH KGH")) {
                    MentorConstant.TH_KGH = true;
                } if (fType.equalsIgnoreCase("TH GVH")) {
                    MentorConstant.TH_GVH = true;
                }if (fType.equalsIgnoreCase("TH  GGH")) {
                    MentorConstant.TH_GGH = true;
                }if (fType.equalsIgnoreCase("DISPENSARY")) {
                    MentorConstant.DISPENSARY = true;
                }if (fType.equalsIgnoreCase("GGH")) {
                    MentorConstant.ggh = true;
                }if (fType.equalsIgnoreCase("MCH")) {
                    MentorConstant.mch = true;
                }if (fType.equalsIgnoreCase("RH")) {
                    MentorConstant.rh = true;
                }if (fType.equalsIgnoreCase("AH")) {
                    MentorConstant.ah = true;
                }if (fType.equalsIgnoreCase("DH")) {
                    MentorConstant.dh = true;
                } else if (fType.equalsIgnoreCase("SDH")) {
                    MentorConstant.sdh = true;
                } else if (fType.equalsIgnoreCase("CHC")) {
                    MentorConstant.chc = true;
                } else if (fType.equalsIgnoreCase("PHC")) {
                    MentorConstant.phc = true;
                } else if (fType.equalsIgnoreCase("SC")) {
                    MentorConstant.sc = true;
                }
            } while (cursor.moveToNext());
        }

        return;
    }

    public long updateIsAssessment(String username) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_IS_ASSESSMENT, "1");
        long row = sqLiteDatabase.update(TABLENAME2, contentValues, COL_USERNAME + "=?", new String[]{username});
        row = sqLiteDatabase.update(TABLE_ClinicalStandards, contentValues, COL_USERNAME + "=?", new String[]{username});
        row = sqLiteDatabase.update(TABLENAME4, contentValues, COL_USERNAME + "=?", new String[]{username});
        row = sqLiteDatabase.update(TABLENAME5, contentValues, COL_USERNAME + "=?", new String[]{username});
        row = sqLiteDatabase.update(TABLENAME6, contentValues, COL_USERNAME + "=?", new String[]{username});
        row = sqLiteDatabase.update(TABLENAME7, contentValues, COL_USERNAME + "=?", new String[]{username});
        row = sqLiteDatabase.update(TABLENAME8, contentValues, COL_USERNAME + "=?", new String[]{username});
        row = sqLiteDatabase.update(TABLENAME9, contentValues, COL_USERNAME + "=?", new String[]{username});
        row = sqLiteDatabase.update(TABLENAME10, contentValues, COL_USERNAME + "=?", new String[]{username});
        row = sqLiteDatabase.update(TABLENAME1, contentValues, COL_USERNAME + "=?", new String[]{username});
        row = sqLiteDatabase.update(TABLENAME3, contentValues, COL_USERNAME + "=?", new String[]{username});
        row = sqLiteDatabase.update(TABLE_WorkPlan, contentValues, COL_USERNAME + "=?", new String[]{username});
        return row;
    }

    public long updateIsSubmitted(String recordId) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_IS_SUBMITTED, "1");
        long row = sqLiteDatabase.update(TABLE_ClinicalStandards, contentValues, COL_ID + "=?", new String[]{recordId});
        row = sqLiteDatabase.update(TABLENAME2, contentValues, COL_ID + "=?", new String[]{recordId});
        row = sqLiteDatabase.update(TABLENAME4, contentValues, COL_ID + "=?", new String[]{recordId});
        row = sqLiteDatabase.update(TABLENAME5, contentValues, COL_ID + "=?", new String[]{recordId});
        row = sqLiteDatabase.update(TABLENAME6, contentValues, COL_ID + "=?", new String[]{recordId});
        row = sqLiteDatabase.update(TABLENAME7, contentValues, COL_ID + "=?", new String[]{recordId});
        row = sqLiteDatabase.update(TABLENAME8, contentValues, COL_ID + "=?", new String[]{recordId});
        row = sqLiteDatabase.update(TABLENAME9, contentValues, COL_ID + "=?", new String[]{recordId});
        row = sqLiteDatabase.update(TABLENAME10, contentValues, COL_ID + "=?", new String[]{recordId});
        row = sqLiteDatabase.update(TABLENAME11, contentValues, COL_ID + "=?", new String[]{recordId});
        row = sqLiteDatabase.update(TABLENAME1, contentValues, COL_ID + "=?", new String[]{recordId});
        row = sqLiteDatabase.update(TABLENAME3, contentValues, COL_ID + "=?", new String[]{recordId});
        row = sqLiteDatabase.update(TABLE_WorkPlan, contentValues, COL_ID + "=?", new String[]{recordId});

        return row;
    }

    public void updateAllTablesSessionId(String session, String latitude, String longitude) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_SESSION, session);
        contentValues.put(COL_IS_SUBMITTED, "0");
        contentValues.put(COL_LATITUDE, latitude);
        contentValues.put(COL_LONGITUDE, longitude);

        sqLiteDatabase.update(TABLENAME7, contentValues, COL_SESSION + "=?", new String[]{session});
        sqLiteDatabase.update(TABLENAME5, contentValues, COL_SESSION + "=?", new String[]{session});
        sqLiteDatabase.update(TABLENAME8, contentValues, COL_SESSION + "=?", new String[]{session});
        sqLiteDatabase.update(TABLE_ClinicalStandards, contentValues, COL_SESSION + "=?", new String[]{session});
        sqLiteDatabase.update(TABLENAME6, contentValues, COL_SESSION + "=?", new String[]{session});
        sqLiteDatabase.update(TABLENAME9, contentValues, COL_SESSION + "=?", new String[]{session});
        sqLiteDatabase.update(TABLENAME10, contentValues, COL_SESSION + "=?", new String[]{session});
        sqLiteDatabase.update(TABLENAME11, contentValues, COL_SESSION + "=?", new String[]{session});
        sqLiteDatabase.update(TABLENAME3, contentValues, COL_SESSION + "=?", new String[]{session});
        sqLiteDatabase.close();
    }

    public boolean isSessionExists(String sessionId, String lastId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLENAME1, new String[]{COL_SESSION, COL_ID}, COL_SESSION + "=?", new String[]{sessionId}, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() == 0) {
            return false;
        } else {
            String _id = cursor.getString(cursor.getColumnIndex(COL_ID));
            if (_id.equalsIgnoreCase(lastId)) return false;
            else {
                if (cursor != null && cursor.getCount() > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    public long addClinicalStandardsNew(String username, String json_data, String count, String session, String ansJson,
                                        String latitude, String longitude, String uniqueId, int setAddOrUpdate) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(JSON_DATA, json_data);
        contentValues.put(COL_COUNT, count);
        contentValues.put(COL_SESSION, session);
        contentValues.put(COL_IS_SUBMITTED, "0");
        contentValues.put(COL_ANSJSON, ansJson);
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_LATITUDE, latitude);
        contentValues.put(COL_LONGITUDE, longitude);
        contentValues.put(COL_UNIQUE_ID, uniqueId);
        contentValues.put("flag", "false");
        long row;
        if (setAddOrUpdate == 0) {
            row = sqLiteDatabase.insert(TABLE_ClinicalStandards, null, contentValues);
        } else {
            row = sqLiteDatabase.update(TABLE_ClinicalStandards, contentValues, COL_SESSION + "=? AND " + COL_UNIQUE_ID + "=?", new String[]{session, String.valueOf(MentorConstant.recordId)});
        }
        sqLiteDatabase.close();
        return row;
    }

    public boolean isLastVisitSubmittedClinicalStandardsNew(String recordId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_ClinicalStandards, new String[]{JSON_DATA}, COL_SESSION + "=?", new String[]{recordId}, null, null, null);
        boolean result = true;
        if (cursor.getCount() > 0) {
            return false;
        }
        return result;
    }

    public void deleteTableClinicalOldData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "delete from " + TABLENAME3;
        db.execSQL(query);
    }

    public long addWorkPlanDetail(String nom, String state, String district, String block, String facilityname, String facilitytype, String level,
                                  String session, String username, String workplan_date, String visits, String createdDate) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_DIST, district);
        contentValues.put(COL_NOM, nom);
        contentValues.put(COL_BLOCK, block);
        contentValues.put(COL_FACILITYTYPE, facilitytype);
        contentValues.put(COL_FACILITYNAME, facilityname);
        contentValues.put(COL_STATE, state);
        contentValues.put(COL_LEVEL, level);
        contentValues.put(COL_SESSION, session);
        contentValues.put(COL_USERNAME, username);
        contentValues.put("workplan_date", workplan_date);
        contentValues.put("actual_date", "");
        contentValues.put(COL_IS_SUBMITTED, "0");
        contentValues.put("visits", visits);
        contentValues.put(COL_SYNC_STATUS, "Pending");
        contentValues.put(CREATED_DATE, createdDate);
        long row = sqLiteDatabase.insert(TABLE_WorkPlan, null, contentValues);
        sqLiteDatabase.close();
        return row;

    }

    public long updateWorkPlanDetail(String id, String nom, String state, String district, String block, String facilityname, String facilitytype, String level,
                                     String dov, String session, String reference_key, String workplan_status) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_DIST, district);
        contentValues.put(COL_NOM, nom);
        contentValues.put(COL_BLOCK, block);
        contentValues.put(COL_FACILITYTYPE, facilitytype);
        contentValues.put(COL_FACILITYNAME, facilityname);
        contentValues.put(COL_STATE, state);
        contentValues.put(COL_LEVEL, level);
        contentValues.put("actual_date", dov);
        contentValues.put(COL_SESSION, session);
        contentValues.put(COL_IS_SUBMITTED, "0");
        contentValues.put(COL_SYNC_STATUS, workplan_status);
        contentValues.put("reference_key", reference_key);
        long row = sqLiteDatabase.update(TABLE_WorkPlan, contentValues, "id=" + id, null);
        sqLiteDatabase.close();
        return row;

    }

    public long updateWorkPlanStatus(String id, String isSubmitted) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_IS_SUBMITTED, isSubmitted);
        long row = sqLiteDatabase.update(TABLE_WorkPlan, contentValues, "id=" + id, null);
        sqLiteDatabase.close();
        return row;

    }

    public void updateWorkPlanTableInUnplannedCase(String lastInsertedDataId, String sessionId, String actualStatus) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_IS_SUBMITTED, "0");
        contentValues.put(COL_SYNC_STATUS, actualStatus);
        contentValues.put("reference_key", "0");
        long row = sqLiteDatabase.update(TABLE_WorkPlan, contentValues, "reference_key=" + lastInsertedDataId + " and " + COL_SESSION + "='" + sessionId + "'", null);
        sqLiteDatabase.close();
    }

    public long updateWorkPlanSessionId(String id, String session) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_SESSION, "");
        contentValues.put(COL_SYNC_STATUS, session);
        contentValues.put("actual_date", "");
        contentValues.put("reference_key", "0");
        long row = sqLiteDatabase.update(TABLE_WorkPlan, contentValues, "id=" + id, null);
        sqLiteDatabase.close();
        return row;

    }

    public long updateFlagOfClinicalPractices(String sessionid) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("flag", "true");
        long row = sqLiteDatabase.update(TABLE_ClinicalStandards, contentValues, COL_SESSION + "=?", new String[]{sessionid});
        sqLiteDatabase.close();
        return row;
    }
}
