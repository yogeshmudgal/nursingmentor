package com.dakshata.mentor;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.KeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.dakshata.mentor.JhpiegoDatabase.COL_BLOCK;
import static com.dakshata.mentor.JhpiegoDatabase.COL_DIST;
import static com.dakshata.mentor.JhpiegoDatabase.COL_DOV;
import static com.dakshata.mentor.JhpiegoDatabase.COL_EMAIL;
import static com.dakshata.mentor.JhpiegoDatabase.COL_FACILITYNAME;
import static com.dakshata.mentor.JhpiegoDatabase.COL_FACILITYTYPE;
import static com.dakshata.mentor.JhpiegoDatabase.COL_IMAGE1;
import static com.dakshata.mentor.JhpiegoDatabase.COL_IMAGE2;
import static com.dakshata.mentor.JhpiegoDatabase.COL_IMAGE3;
import static com.dakshata.mentor.JhpiegoDatabase.COL_IMAGE4;
import static com.dakshata.mentor.JhpiegoDatabase.COL_LEVEL;
import static com.dakshata.mentor.JhpiegoDatabase.COL_MOBILE;
import static com.dakshata.mentor.JhpiegoDatabase.COL_NAME;
import static com.dakshata.mentor.JhpiegoDatabase.COL_NOM;
import static com.dakshata.mentor.JhpiegoDatabase.COL_ROLE;
import static com.dakshata.mentor.JhpiegoDatabase.COL_SESSION;
import static com.dakshata.mentor.JhpiegoDatabase.COL_STATE;
import static com.dakshata.mentor.JhpiegoDatabase.COL_USERNAME;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME1;

public class ProfileActivity extends AppCompatActivity {

    View profile_main;
    ImageView imageView_profile_img;
    ImageView back;
    TextView textViewHeaderName,textViewUserName,textViewMobile,textViewEmail,textViewState;
    Toolbar toolbar;
    private JhpiegoDatabase jhpiegoDatabase;
    SharedPreferences sh_Pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(),R.drawable.menu_image_opt3));
//        setSupportActionBar(toolbar);
//        textViewHeaderName= (TextView) findViewById(R.id.header_name);
//        textViewHeaderName.setText("User Profile");
        sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);

        TextView pro_name = (TextView) findViewById(R.id.pro_name);
        TextView pro_mobile = (TextView) findViewById(R.id.pro_mobile);
        TextView pro_location = (TextView) findViewById(R.id.pro_location);
        TextView pro_email = (TextView) findViewById(R.id.pro_email);

        pro_name.setText(sh_Pref.getString("name", ""));
        pro_mobile.setText(sh_Pref.getString("Username", ""));
        pro_location.setText(sh_Pref.getString("state", ""));
        pro_email.setText(sh_Pref.getString("email", ""));

        jhpiegoDatabase=new JhpiegoDatabase(this);

        imageView_profile_img = (ImageView) findViewById(R.id.imageView_profile_img);

        profile_main = findViewById(R.id.profile_main);
        TextView app_version = findViewById(R.id.app_version);

        final InputMethodManager imm = (InputMethodManager) ProfileActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);

//        getUserDetailsFromDB();
        imageView_profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfileActivity.this, "Select Image!", Toast.LENGTH_SHORT).show();
            }
        });

        String version = "";
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = "" + pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            version = "";
        }
        app_version.setText(version);
    }
public void getUserDetailsFromDB(){
    textViewUserName=findViewById(R.id.pro_name);
    textViewEmail=findViewById(R.id.pro_email);
    textViewMobile=findViewById(R.id.pro_mobile);
    textViewState=findViewById(R.id.pro_location);
    try {
        /*sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
        editor = sh_Pref.edit();
        String username=sh_Pref.getString("Username","Unknown");
        SQLiteDatabase sqLiteDatabase=jhpiegoDatabase.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.query(TABLENAME,new String[]{COL_USERNAME,COL_EMAIL,COL_MOBILE,COL_STATE,COL_NAME},COL_USERNAME + "=?",new String[]{username},null,null,null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            String username1=cursor.getString(cursor.getColumnIndex(COL_NAME));
            String state=cursor.getString(cursor.getColumnIndex(COL_STATE));
            String mobile=cursor.getString(cursor.getColumnIndex(COL_MOBILE));
            String email=cursor.getString(cursor.getColumnIndex(COL_EMAIL));
            String role=cursor.getString(cursor.getColumnIndex(COL_ROLE));
            textViewState.setText(state);
            textViewUserName.setText(username1);
            textViewEmail.setText(email);
            textViewMobile.setText(mobile);        }*/

        sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
        String username = sh_Pref.getString("Username", "");
        // ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        // listView.setAdapter(listAdapter);
        // listView.deferNotifyDataSetChanged();
        SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLENAME1, new String[]{COL_USERNAME, COL_SESSION, COL_FACILITYNAME, COL_FACILITYTYPE, COL_DOV}, COL_USERNAME + "=?", new String[]{username}, null, null, null);

        String sessionID = null;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                //sessionID=cursor.getString(cursor.getColumnIndex(COL_SESSION));
                String facilityname = cursor.getString(cursor.getColumnIndex(COL_FACILITYNAME));
                String facilitytype = cursor.getString(cursor.getColumnIndex(COL_FACILITYTYPE));
                String dov = cursor.getString(cursor.getColumnIndex(COL_DOV));
                ListPojo listPojo = new ListPojo(facilityname, facilitytype, dov);
                sessionID = facilityname + "-" + facilitytype + "-" + dov;
            } while (cursor.moveToNext());

        }

        sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
        String query = "select * from " + TABLENAME1;
        cursor=sqLiteDatabase.query(TABLENAME1,new String[]{COL_STATE},COL_SESSION + "=?",new String[]{sessionID},null,null,null);
        if (cursor != null && cursor.getCount() > 0) {
            String state=cursor.getString(cursor.getColumnIndex(COL_STATE));
            textViewState.setText(state);
        }
    }catch (Exception e){}
}
}
