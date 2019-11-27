package com.dakshata.mentor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Aditya.v on 15-12-2017.
 */

public class JhpiegoRegistration extends AppCompatActivity {
    EditText et_username,et_email,et_password,et_mobile,et_office,et_designation;
    Spinner spinner_district,spinner_block;
    Button button_register;
    JhpiegoDatabase jhpiegoDatabase;
    String[] statesName = {"---District-name---","Madhya pradesh","Uttar pradesh","Maharastra"};
    String [] Austtaliast={"------Madhya_pradesh---","Bhopal","Bhind","Indore","Riwa","Guna"};
    String [] Canadast={"---Uttar_pradesh---","Alberta","British","Manitoba","Brunswick"};
    String [] Indiast={"---Maharatra---","AndhraPradesh","ArunachalPradesh","Assam","Bihar","Chhattisgarh"};
    Spinner spinner1,spinner2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jhpiego_registration);
        jhpiegoDatabase=new JhpiegoDatabase(this);
        spinner1 = (Spinner) findViewById(R.id.jr_spinner1);
        spinner2 = (Spinner) findViewById(R.id.jr_spinner2);
        spinner2.setEnabled(false);
        ArrayAdapter<String> states = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, statesName);
        spinner1.setAdapter(states);
        final ArrayAdapter<String> indiaStates    = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, Indiast);

        final ArrayAdapter<String> australiaStates = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, Austtaliast);

        final ArrayAdapter<String> CStates = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, Canadast);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    spinner2.setEnabled(false);

                }
                if (position==1){
                    spinner2.setEnabled(true);

                    spinner2.setAdapter(australiaStates);
                }
                if (position==2){
                    spinner2.setEnabled(true);
                    spinner2.setAdapter(CStates);
                }
                if (position==3){
                    spinner2.setEnabled(true);
                    spinner2.setAdapter(indiaStates);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });

        et_username= (EditText) findViewById(R.id.survey_fullname);
        et_email= (EditText) findViewById(R.id.survey_email);
        et_password= (EditText) findViewById(R.id.survey_password);
        et_mobile= (EditText) findViewById(R.id.survey_mobile);
        et_designation= (EditText) findViewById(R.id.survey_designation);
        et_office= (EditText) findViewById(R.id.survey_office);
        button_register= (Button) findViewById(R.id.button_jhpiego_register);

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=et_username.getText().toString();
                String email=et_email.getText().toString();
                String password=et_password.getText().toString();
                String mobile=et_mobile.getText().toString();
                String office=et_office.getText().toString();
                String designation=et_designation.getText().toString();
                String district=spinner2.getSelectedItem().toString();
                String state=spinner1.getSelectedItem().toString();
                if (mobile.length()<10){
                    Toast.makeText(getApplicationContext(),"Wrong mobile number",Toast.LENGTH_SHORT).show();
                }else {
                   /* long row = jhpiegoDatabase.addUser(state, district, username, designation, office, mobile, email, password);
                    if (row != -1) {
                        Toast.makeText(getApplicationContext(), getString(R.string.alert_saved_successfully), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.alert_not_saved_to_db), Toast.LENGTH_SHORT).show();
                    }*/
                    Intent intent = new Intent(JhpiegoRegistration.this, JhpiegoLogin.class);
                    startActivity(intent);
                }
            }
        });
    }
}
