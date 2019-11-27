package com.dakshata.mentor;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 14-02-2018.
 */

public class FacilityDBAdapter extends SQLiteOpenHelper {

    private static String dbPath = "/data/data/com.dakshata.mentor/databases/";
    public static final String DATABASE_NAME = "nursing_mentor.db";
    private static final int DATABASE_VERSION = 1;
    public static final String hmstr[] = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
    private static FacilityDBAdapter mInstance = null;
    private final Context context;
    private SQLiteDatabase db;

    public FacilityDBAdapter(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    public static FacilityDBAdapter getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new FacilityDBAdapter(ctx.getApplicationContext());
            try {
                mInstance.createDataBase();
                mInstance.openDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mInstance;
    }

    private boolean checkDataBase() {
        File dbFile = new File(dbPath + DATABASE_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() throws IOException {
        try {

            InputStream input = context.getAssets().open(DATABASE_NAME);
            String outPutFileName = dbPath + DATABASE_NAME;
            OutputStream output = new FileOutputStream(outPutFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            input.close();
        } catch (IOException e) {
            Log.v("error", e.toString());
        }
    }

    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();
        Log.d("createDataBase", String.valueOf(dbExist));
        if (dbExist) {
            this.getWritableDatabase();
            //do nothing - database already exist
        } else {
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                Log.v("error copying database", e.toString());
                throw new Error("Error copying database");
            }
        }
    }

    public void openDataBase() throws SQLException, IOException {
        if (db == null) {
            String fullDbPath = dbPath + DATABASE_NAME;
            db = SQLiteDatabase.openDatabase(fullDbPath, null, SQLiteDatabase.OPEN_READWRITE);
        } else if (!db.isOpen()) {
            String fullDbPath = dbPath + DATABASE_NAME;
            db = SQLiteDatabase.openDatabase(fullDbPath, null, SQLiteDatabase.OPEN_READWRITE);
        }
        db.setVersion(DATABASE_VERSION);
    }

    public SQLiteDatabase getDB() {
        try {
            openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return db;
    }

    //---closes the database---
    @Override
    public synchronized void close() {
//	      if( db!=null) db.close();
//	       super.close();
    }

    public synchronized void myclose() {

        if (db != null) db.close();
        super.close();
        mInstance = null;
        //DBHelper.close();
    }

    public static String strtodate(String dstr) {
        String tmp = "-";
        if (dstr != null) {
            String dt_str[] = dstr.split("\\-");
            if (dt_str.length > 2)
                tmp = dt_str[2] + "-" + hmstr[Integer.parseInt(dt_str[1]) - 1] + "-" + dt_str[0];
            else tmp = dstr;
        }
        return tmp;
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<String> getBlockList(String _selectedDistrict) {
        List<String> blockList = new ArrayList<>();

        try {
            openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cursor cursor = db.rawQuery("SELECT BlockName from facilityMaster where DistrictName = '" + _selectedDistrict + "' AND DistrictName IS NOT NULL And BlockName IS NOT NULL AND FType IS NOT NULL AND PHCName IS NOT NULL AND SubCenter IS NOT NULL GROUP BY BlockName", null);
        if (cursor.moveToFirst()) {
            do {
                blockList.add(cursor.getString(cursor.getColumnIndex("BlockName")));
            } while (cursor.moveToNext());
        }

        return blockList;
    }

    public List<String> getBlockList() {
        List<String> blockList = new ArrayList<>();

        try {
            openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cursor cursor = db.rawQuery("SELECT BlockName from facilityMaster where DistrictName IS NOT NULL And BlockName IS NOT NULL AND FType IS NOT NULL AND PHCName IS NOT NULL AND SubCenter IS NOT NULL GROUP BY BlockName", null);
        if (cursor.moveToFirst()) {
            do {
                blockList.add(cursor.getString(cursor.getColumnIndex("BlockName")));
            } while (cursor.moveToNext());
        }

        return blockList;
    }

    public boolean checkFacilityAvailable(String fType, String blockName) {
        try {
            openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cursor cursor = db.rawQuery("SELECT BlockName from facilityMaster where BlockName = '"+blockName+"' AND FType='"+fType+"' AND DistrictName IS NOT NULL And BlockName IS NOT NULL AND FType IS NOT NULL AND PHCName IS NOT NULL AND SubCenter IS NOT NULL GROUP BY BlockName", null);
        if (cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    public List<String> getFacilityName(String fType, String blockName) {
        List<String> facilityTypeList = new ArrayList<>();
        try {
            openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cursor cursor = db.rawQuery("SELECT PHCName from facilityMaster where BlockName = '"+blockName+"' AND FType='"+fType+"' AND DistrictName IS NOT NULL And BlockName IS NOT NULL AND FType IS NOT NULL AND PHCName IS NOT NULL AND SubCenter IS NOT NULL GROUP BY PHCName", null);
        if (cursor.moveToFirst()) {
            do {
                facilityTypeList.add(cursor.getString(cursor.getColumnIndex("PHCName")));
            } while (cursor.moveToNext());
        }
        return facilityTypeList;
    }

    public List<String> getFacilityNameForDistrictHospital(String fType) {
        List<String> facilityTypeList = new ArrayList<>();
        try {
            openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cursor cursor = db.rawQuery("SELECT PHCName from facilityMaster where FType='"+fType+"' AND DistrictName IS NOT NULL And BlockName IS NOT NULL AND FType IS NOT NULL AND PHCName IS NOT NULL AND SubCenter IS NOT NULL  GROUP BY PHCName", null);
        if (cursor.moveToFirst()) {
            do {
                facilityTypeList.add(cursor.getString(cursor.getColumnIndex("PHCName")));
            } while (cursor.moveToNext());
        }
        return facilityTypeList;
    }

    public List<String> getFacilityNameForSubCenter(String blockName) {
        List<String> facilityTypeList = new ArrayList<>();
        try {
            openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cursor cursor = db.rawQuery("SELECT SubCenter from facilityMaster where BlockName = '"+blockName+"' AND DistrictName IS NOT NULL And BlockName IS NOT NULL AND FType IS NOT NULL AND PHCName IS NOT NULL AND SubCenter IS NOT NULL  GROUP BY SubCenter", null);
        if (cursor.moveToFirst()) {
            do {
                facilityTypeList.add(cursor.getString(cursor.getColumnIndex("SubCenter")));
            } while (cursor.moveToNext());
        }
        return facilityTypeList;
    }

    public List<String>     getDistrictList(List<String> listDistrict) {
        try {
            openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cursor cursor = db.rawQuery("SELECT * from facilityMaster where DistrictName IS NOT NULL And BlockName IS NOT NULL AND FType IS NOT NULL AND PHCName IS NOT NULL AND SubCenter IS NOT NULL GROUP BY DistrictName", null);
        if (cursor.moveToFirst()) {
            do {
                listDistrict.add(cursor.getString(cursor.getColumnIndex("DistrictName")));
            } while (cursor.moveToNext());
        }

        return listDistrict;
    }

}
