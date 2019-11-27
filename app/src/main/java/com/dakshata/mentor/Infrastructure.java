package com.dakshata.mentor;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dakshata.mentor.Utils.MentorConstant;
import com.dakshata.mentor.models.AnswersModel;
import com.dakshata.mentor.models.F3;
import com.dakshata.mentor.models.FormDatum;
import com.dakshata.mentor.models.QuetionsMaster;
import com.dakshata.mentor.models.VisitDatum;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.dakshata.mentor.JhpiegoDatabase.COL_AC;
import static com.dakshata.mentor.JhpiegoDatabase.COL_ASBT;
import static com.dakshata.mentor.JhpiegoDatabase.COL_ASTNSEM;
import static com.dakshata.mentor.JhpiegoDatabase.COL_AV;
import static com.dakshata.mentor.JhpiegoDatabase.COL_CMDS;
import static com.dakshata.mentor.JhpiegoDatabase.COL_CNRNB;
import static com.dakshata.mentor.JhpiegoDatabase.COL_DUA;
import static com.dakshata.mentor.JhpiegoDatabase.COL_EOT;
import static com.dakshata.mentor.JhpiegoDatabase.COL_FHWS;
import static com.dakshata.mentor.JhpiegoDatabase.COL_FLSL;
import static com.dakshata.mentor.JhpiegoDatabase.COL_FWRW;
import static com.dakshata.mentor.JhpiegoDatabase.COL_ID;
import static com.dakshata.mentor.JhpiegoDatabase.COL_LCFW;
import static com.dakshata.mentor.JhpiegoDatabase.COL_LOF;
import static com.dakshata.mentor.JhpiegoDatabase.COL_LOI;
import static com.dakshata.mentor.JhpiegoDatabase.COL_LRD;
import static com.dakshata.mentor.JhpiegoDatabase.COL_PB;
import static com.dakshata.mentor.JhpiegoDatabase.COL_PSC;
import static com.dakshata.mentor.JhpiegoDatabase.COL_RW;
import static com.dakshata.mentor.JhpiegoDatabase.COL_SESSION;
import static com.dakshata.mentor.JhpiegoDatabase.COL_SOB;
import static com.dakshata.mentor.JhpiegoDatabase.COL_THREE_SIDE_SPACE;
import static com.dakshata.mentor.JhpiegoDatabase.COL_UNIQUE_ID;
import static com.dakshata.mentor.JhpiegoDatabase.COL_USERNAME;
import static com.dakshata.mentor.JhpiegoDatabase.COL_WALLS;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME1;
import static com.dakshata.mentor.JhpiegoDatabase.TABLENAME5;

/**
 * Created by Aditya.v on 16-12-2017.
 */

public class Infrastructure extends AppCompatActivity implements LocationListener {
    RadioGroup radioGroup1, radioGroup2, radioGroup3, radioGroup4, radioGroup5, radioGroup6, radioGroup7, radioGroup8, radioGroup9, radioGroup10, radioGroup11, radioGroup12, radioGroup13, radioGroup14, radioGroup15, radioGroup16, radioGroup17, radioGroup18, radioGroup19, radioGroup20;
    RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5, radioButton6, radioButton7, radioButton8, radioButton9, radioButton10, radioButton11, radioButton12, radioButton13, radioButton14, radioButton15, radioButton16, radioButton17, radioButton18, radioButton19, radioButton20, radioButton21, radioButton22, radioButton23, radioButton24, radioButton25, radioButton26, radioButton27, radioButton28, radioButton29, radioButton30, radioButton31, radioButton32, radioButton33, radioButton34, radioButton35, radioButton36, radioButton37, radioButton38, radioButton39, radioButton40, radioButton41, radioButton42, radioButton43, radioButton44, radioButton45, radioButton46, radioButton47, radioButton48, radioButton49, radioButton50, radioButton51, radioButton52, radioButton53, radioButton54, radioButton55, radioButton56, radioButton57, radioButton58, radioButton59, radioButton60;
    Button button_save, button_back;
    Spinner spinner1;
    JhpiegoDatabase jhpiegoDatabase;
    ProgressDialog progressDialog;
    LinearLayout linearLayout;
    Toolbar toolbar;
    SharedPreferences sh_Pref, sharedPreferences, sharedPreferencescount;
    SharedPreferences.Editor editor, editor1, editorcount;
    int count = 0;
    String session;
    String username;
    ImageView back;
    TextView textViewHeaderName;
    String row_0, row_1, row_2, row_3, row_4, row_5, row_6, row_7, row_8, row_9, row_10, row_11, row_12, row_13, row_14, row_15, row_16, row_17, row_18, row_19, row_20;
    boolean rgChecked1, rgChecked2, rgChecked3, rgChecked4, rgChecked5, rgChecked6, rgChecked7, rgChecked8, rgChecked9, rgChecked10, rgChecked11, rgChecked12, rgChecked13, rgChecked14, rgChecked15, rgChecked16, rgChecked17, rgChecked18, rgChecked19, rgChecked20;
    boolean rgChecked21, rgChecked22, rgChecked23, rgChecked24, rgChecked25, rgChecked26, rgChecked27, rgChecked28, rgChecked29, rgChecked210, rgChecked211, rgChecked212, rgChecked213, rgChecked214, rgChecked215, rgChecked216, rgChecked217, rgChecked218, rgChecked219, rgChecked220;
    String ansJson;
    String sessionid;
    public static QuetionsMaster mQuetionsMaster = new QuetionsMaster();
    TextView textView;
    Button sm_previous_visit;
    boolean isCurrentRecordBackupExist = false;

    // Location Access
    LocationManager locationManager;
    String provider, address;
    Location location;
    private int loop = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infrastructure);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.menu_image_opt3));
        setSupportActionBar(toolbar);
        back = (ImageView) findViewById(R.id.back);
        textViewHeaderName = (TextView) findViewById(R.id.header_name);
        textViewHeaderName.setText(getString(R.string.section_c_header_1));
        jhpiegoDatabase = new JhpiegoDatabase(this);
        progressDialog = new ProgressDialog(this);

        row_0=row_1=row_2=row_3=row_4=row_5=row_6=row_7=row_8=row_9=row_10=row_11=row_12=row_13=row_14=row_15=row_16=row_17=row_18=row_19=row_20 = "";

        initViews();
        sh_Pref = getSharedPreferences("Login Credentials", MODE_PRIVATE);
        editor = sh_Pref.edit();

        sessionid = getIntent().getStringExtra("sessionNew");

        username = sh_Pref.getString("Username", "Unknown");

        getSessionOfUser();
        setDefaultValues();
        try {
            radioButton20.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rgChecked21) {
                        radioGroup1.clearCheck();
                        rgChecked21 = false;
                        rgChecked1 = false;
                    } else {
                        rgChecked21 = true;
                        rgChecked1 = false;
                        final Dialog dialog = new Dialog(Infrastructure.this);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.loi_dialog);
                        dialog.setTitle("Dakshata Mentor");
                        final Spinner spinnerloi = dialog.findViewById(R.id.infra_spinner1);
                        Button button = dialog.findViewById(R.id.btn_loi);
                        spinnerloi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position == 0) {

                                } else {
                                    row_1 = spinnerloi.getSelectedItem().toString();
                                    // dialog.dismiss();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (spinnerloi.getSelectedItemPosition() == 0) {

                                } else {
                                    dialog.cancel();
                                }
                            }
                        });

                        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                radioButton20.setChecked(false);
                                dialog.cancel();
                            }
                        });
                        dialog.show();
                    }
                }
            });

        } catch (Exception e) {
        }

        try {
            radioButton21.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rgChecked22) {
                        radioGroup2.clearCheck();
                        rgChecked22 = false;
                        rgChecked2 = false;
                    } else {
                        rgChecked22 = true;
                        rgChecked2 = false;
                        final Dialog dialog = new Dialog(Infrastructure.this);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.loi_dialog);
                        dialog.setTitle("Dakshata Mentor");
                        final Spinner spinnerloi = dialog.findViewById(R.id.infra_spinner1);
                        Button button = dialog.findViewById(R.id.btn_loi);
                        spinnerloi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position == 0) {

                                } else {
                                    row_2 = spinnerloi.getSelectedItem().toString();
                                    // dialog.cancel();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (spinnerloi.getSelectedItemPosition() == 0) {

                                } else {
                                    dialog.cancel();
                                }
                            }
                        });
                        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                radioButton21.setChecked(false);
                                dialog.cancel();
                            }
                        });
                        dialog.show();
                    }
                }
            });

        } catch (Exception e) {
        }
        try {
            radioButton22.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rgChecked23) {
                        radioGroup3.clearCheck();
                        rgChecked23 = false;
                        rgChecked3 = false;
                    } else {
                        rgChecked23 = true;
                        rgChecked3 = false;
                        final Dialog dialog = new Dialog(Infrastructure.this);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.loi_dialog);
                        dialog.setTitle("Dakshata Mentor");
                        final Spinner spinnerloi = dialog.findViewById(R.id.infra_spinner1);
                        Button button = dialog.findViewById(R.id.btn_loi);
                        spinnerloi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position == 0) {

                                } else {
                                    row_3 = spinnerloi.getSelectedItem().toString();
                                    // dialog.cancel();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (spinnerloi.getSelectedItemPosition() == 0) {

                                } else {
                                    dialog.cancel();
                                }
                            }
                        });
                        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                radioButton22.setChecked(false);
                                dialog.cancel();
                            }
                        });
                        dialog.show();
                    }
                }
            });

        } catch (Exception e) {
        }
        try {
            radioButton23.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rgChecked24) {
                        radioGroup4.clearCheck();
                        rgChecked24 = false;
                        rgChecked4 = false;
                    } else {
                        rgChecked24 = true;
                        rgChecked4 = false;
                        final Dialog dialog = new Dialog(Infrastructure.this);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.loi_dialog);
                        dialog.setTitle("Dakshata Mentor");
                        final Spinner spinnerloi = dialog.findViewById(R.id.infra_spinner1);
                        Button button = dialog.findViewById(R.id.btn_loi);
                        spinnerloi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position == 0) {

                                } else {
                                    row_4 = spinnerloi.getSelectedItem().toString();
                                    //  dialog.cancel();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (spinnerloi.getSelectedItemPosition() == 0) {

                                } else {
                                    dialog.cancel();
                                }
                            }
                        });
                        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                radioButton23.setChecked(false);
                                dialog.cancel();
                            }
                        });
                        dialog.show();
                    }
                }
            });

        } catch (Exception e) {
        }
        try {
            radioButton24.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rgChecked25) {
                        radioGroup5.clearCheck();
                        rgChecked25 = false;
                        rgChecked5 = false;
                    } else {
                        rgChecked25 = true;
                        rgChecked5 = false;
                        final Dialog dialog = new Dialog(Infrastructure.this);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.loi_dialog);
                        dialog.setTitle("Dakshata Mentor");
                        final Spinner spinnerloi = dialog.findViewById(R.id.infra_spinner1);
                        Button button = dialog.findViewById(R.id.btn_loi);
                        spinnerloi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position == 0) {

                                } else {
                                    row_5 = spinnerloi.getSelectedItem().toString();
                                    //  dialog.cancel();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (spinnerloi.getSelectedItemPosition() == 0) {

                                } else {
                                    dialog.cancel();
                                }
                            }
                        });
                        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                radioButton24.setChecked(false);
                                dialog.cancel();
                            }
                        });
                        dialog.show();
                    }
                }
            });

        } catch (Exception e) {
        }
        try {
            radioButton25.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rgChecked26) {
                        radioGroup6.clearCheck();
                        rgChecked26 = false;
                        rgChecked6 = false;
                    } else {
                        rgChecked26 = true;
                        rgChecked6 = false;
                        final Dialog dialog = new Dialog(Infrastructure.this);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.loi_dialog);
                        dialog.setTitle("Dakshata Mentor");
                        final Spinner spinnerloi = dialog.findViewById(R.id.infra_spinner1);
                        Button button = dialog.findViewById(R.id.btn_loi);
                        spinnerloi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position == 0) {

                                } else {
                                    row_6 = spinnerloi.getSelectedItem().toString();
                                    //  dialog.cancel();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (spinnerloi.getSelectedItemPosition() == 0) {

                                } else {
                                    dialog.cancel();
                                }
                            }
                        });
                        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                radioButton25.setChecked(false);
                                dialog.cancel();
                            }
                        });
                        dialog.show();
                    }
                }
            });

        } catch (Exception e) {
        }
        try {
            radioButton26.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rgChecked27) {
                        radioGroup7.clearCheck();
                        rgChecked27 = false;
                        rgChecked7 = false;
                    } else {
                        rgChecked27 = true;
                        rgChecked7 = false;
                        final Dialog dialog = new Dialog(Infrastructure.this);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.loi_dialog);
                        dialog.setTitle("Dakshata Mentor");
                        final Spinner spinnerloi = dialog.findViewById(R.id.infra_spinner1);
                        Button button = dialog.findViewById(R.id.btn_loi);
                        spinnerloi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position == 0) {

                                } else {
                                    row_7 = spinnerloi.getSelectedItem().toString();
                                    //  dialog.cancel();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (spinnerloi.getSelectedItemPosition() == 0) {

                                } else {
                                    dialog.cancel();
                                }
                            }
                        });
                        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                radioButton26.setChecked(false);
                                dialog.cancel();
                            }
                        });
                        dialog.show();
                    }
                }
            });

        } catch (Exception e) {
        }
        try {
            radioButton27.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rgChecked28) {
                        radioGroup8.clearCheck();
                        rgChecked28 = false;
                        rgChecked8 = false;
                    } else {
                        rgChecked28 = true;
                        rgChecked8 = false;
                        final Dialog dialog = new Dialog(Infrastructure.this);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.loi_dialog);
                        dialog.setTitle("Dakshata Mentor");
                        final Spinner spinnerloi = dialog.findViewById(R.id.infra_spinner1);
                        Button button = dialog.findViewById(R.id.btn_loi);
                        spinnerloi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position == 0) {

                                } else {
                                    row_8 = spinnerloi.getSelectedItem().toString();
                                    //  dialog.cancel();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (spinnerloi.getSelectedItemPosition() == 0) {

                                } else {
                                    dialog.cancel();
                                }
                            }
                        });
                        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                radioButton27.setChecked(false);
                                dialog.cancel();
                            }
                        });
                        dialog.show();
                    }
                }
            });

        } catch (Exception e) {
        }
        try {
            radioButton28.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rgChecked29) {
                        radioGroup9.clearCheck();
                        rgChecked29 = false;
                        rgChecked9 = false;
                    } else {
                        rgChecked29 = true;
                        rgChecked9 = false;
                        final Dialog dialog = new Dialog(Infrastructure.this);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.loi_dialog);
                        dialog.setTitle("Dakshata Mentor");
                        final Spinner spinnerloi = dialog.findViewById(R.id.infra_spinner1);
                        Button button = dialog.findViewById(R.id.btn_loi);
                        spinnerloi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position == 0) {

                                } else {
                                    row_9 = spinnerloi.getSelectedItem().toString();
                                    //  dialog.cancel();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (spinnerloi.getSelectedItemPosition() == 0) {

                                } else {
                                    dialog.cancel();
                                }
                            }
                        });
                        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                radioButton28.setChecked(false);
                                dialog.cancel();
                            }
                        });
                        dialog.show();
                    }
                }
            });

        } catch (Exception e) {
        }
        try {
            radioButton29.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rgChecked210) {
                        radioGroup10.clearCheck();
                        rgChecked210 = false;
                        rgChecked10 = false;
                    } else {
                        rgChecked210 = true;
                        rgChecked10 = false;
                        final Dialog dialog = new Dialog(Infrastructure.this);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.loi_dialog);
                        dialog.setTitle("Dakshata Mentor");
                        final Spinner spinnerloi = dialog.findViewById(R.id.infra_spinner1);
                        Button button = dialog.findViewById(R.id.btn_loi);
                        spinnerloi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position == 0) {

                                } else {
                                    row_10 = spinnerloi.getSelectedItem().toString();
                                    //  dialog.cancel();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (spinnerloi.getSelectedItemPosition() == 0) {

                                } else {
                                    dialog.cancel();
                                }
                            }
                        });
                        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                radioButton29.setChecked(false);
                                dialog.cancel();
                            }
                        });
                        dialog.show();
                    }
                }
            });

        } catch (Exception e) {
        }
        try {
            radioButton30.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rgChecked211) {
                        radioGroup11.clearCheck();
                        rgChecked211 = false;
                        rgChecked11 = false;
                    } else {
                        rgChecked211 = true;
                        rgChecked11 = false;
                        final Dialog dialog = new Dialog(Infrastructure.this);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.loi_dialog);
                        dialog.setTitle("Dakshata Mentor");
                        final Spinner spinnerloi = dialog.findViewById(R.id.infra_spinner1);
                        Button button = dialog.findViewById(R.id.btn_loi);
                        spinnerloi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position == 0) {

                                } else {
                                    row_11 = spinnerloi.getSelectedItem().toString();
                                    //  dialog.cancel();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (spinnerloi.getSelectedItemPosition() == 0) {

                                } else {
                                    dialog.cancel();
                                }
                            }
                        });
                        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                radioButton30.setChecked(false);
                                dialog.cancel();
                            }
                        });
                        dialog.show();
                    }
                }
            });

        } catch (Exception e) {
        }
        try {
            radioButton31.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rgChecked212) {
                        radioGroup12.clearCheck();
                        rgChecked212 = false;
                        rgChecked12 = false;
                    } else {
                        rgChecked212 = true;
                        rgChecked12 = false;
                        final Dialog dialog = new Dialog(Infrastructure.this);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.loi_dialog);
                        dialog.setTitle("Dakshata Mentor");
                        final Spinner spinnerloi = dialog.findViewById(R.id.infra_spinner1);
                        Button button = dialog.findViewById(R.id.btn_loi);
                        spinnerloi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position == 0) {

                                } else {
                                    row_12 = spinnerloi.getSelectedItem().toString();
                                    // dialog.cancel();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (spinnerloi.getSelectedItemPosition() == 0) {

                                } else {
                                    dialog.cancel();
                                }
                            }
                        });
                        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                radioButton31.setChecked(false);
                                dialog.cancel();
                            }
                        });
                        dialog.show();
                    }
                }
            });

        } catch (Exception e) {
        }
        try {
            radioButton32.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rgChecked213) {
                        radioGroup13.clearCheck();
                        rgChecked213 = false;
                        rgChecked13 = false;
                    } else {
                        rgChecked213 = true;
                        rgChecked13 = false;
                        final Dialog dialog = new Dialog(Infrastructure.this);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.loi_dialog);
                        dialog.setTitle("Dakshata Mentor");
                        final Spinner spinnerloi = dialog.findViewById(R.id.infra_spinner1);
                        Button button = dialog.findViewById(R.id.btn_loi);
                        spinnerloi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position == 0) {

                                } else {
                                    row_13 = spinnerloi.getSelectedItem().toString();
                                    // dialog.cancel();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (spinnerloi.getSelectedItemPosition() == 0) {

                                } else {
                                    dialog.cancel();
                                }
                            }
                        });

                        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                radioButton32.setChecked(false);
                                dialog.cancel();
                            }
                        });
                        dialog.show();
                    }
                }
            });

        } catch (Exception e) {
        }
        try {
            radioButton33.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rgChecked214) {
                        radioGroup14.clearCheck();
                        rgChecked214 = false;
                        rgChecked14 = false;
                    } else {
                        rgChecked214 = true;
                        rgChecked14 = false;
                        final Dialog dialog = new Dialog(Infrastructure.this);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.loi_dialog);
                        dialog.setTitle("Dakshata Mentor");
                        final Spinner spinnerloi = dialog.findViewById(R.id.infra_spinner1);
                        Button button = dialog.findViewById(R.id.btn_loi);
                        spinnerloi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position == 0) {

                                } else {
                                    row_14 = spinnerloi.getSelectedItem().toString();
                                    //  dialog.cancel();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (spinnerloi.getSelectedItemPosition() == 0) {

                                } else {
                                    dialog.cancel();
                                }
                            }
                        });

                        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                radioButton33.setChecked(false);
                                dialog.cancel();
                            }
                        });
                        dialog.show();
                    }
                }
            });

        } catch (Exception e) {
        }
        try {
            radioButton34.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rgChecked215) {
                        radioGroup15.clearCheck();
                        rgChecked215 = false;
                        rgChecked15 = false;
                    } else {
                        rgChecked215 = true;
                        rgChecked15 = false;
                        final Dialog dialog = new Dialog(Infrastructure.this);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.loi_dialog);
                        dialog.setTitle("Dakshata Mentor");
                        final Spinner spinnerloi = dialog.findViewById(R.id.infra_spinner1);
                        Button button = dialog.findViewById(R.id.btn_loi);
                        spinnerloi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position == 0) {

                                } else {
                                    row_15 = spinnerloi.getSelectedItem().toString();
                                    // dialog.cancel();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (spinnerloi.getSelectedItemPosition() == 0) {

                                } else {
                                    dialog.cancel();
                                }
                            }
                        });

                        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                radioButton34.setChecked(false);
                                dialog.cancel();
                            }
                        });
                        dialog.show();
                    }
                }
            });

        } catch (Exception e) {
        }
        try {
            radioButton35.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rgChecked216) {
                        radioGroup16.clearCheck();
                        rgChecked216 = false;
                        rgChecked16 = false;
                    } else {
                        rgChecked216 = true;
                        rgChecked16 = false;
                        final Dialog dialog = new Dialog(Infrastructure.this);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.loi_dialog);
                        dialog.setTitle("Dakshata Mentor");
                        final Spinner spinnerloi = dialog.findViewById(R.id.infra_spinner1);
                        Button button = dialog.findViewById(R.id.btn_loi);
                        spinnerloi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position == 0) {

                                } else {
                                    row_16 = spinnerloi.getSelectedItem().toString();
                                    //   dialog.cancel();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (spinnerloi.getSelectedItemPosition() == 0) {

                                } else {
                                    dialog.cancel();
                                }
                            }
                        });

                        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                radioButton35.setChecked(false);
                                dialog.cancel();
                            }
                        });
                        dialog.show();
                    }
                }
            });

        } catch (Exception e) {
        }
        try {
            radioButton36.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rgChecked217) {
                        radioGroup17.clearCheck();
                        rgChecked217 = false;
                        rgChecked17 = false;
                    } else {
                        rgChecked217 = true;
                        rgChecked17 = false;
                        final Dialog dialog = new Dialog(Infrastructure.this);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.loi_dialog);
                        dialog.setTitle("Dakshata Mentor");
                        final Spinner spinnerloi = dialog.findViewById(R.id.infra_spinner1);
                        Button button = dialog.findViewById(R.id.btn_loi);
                        spinnerloi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position == 0) {

                                } else {
                                    row_17 = spinnerloi.getSelectedItem().toString();
                                    //  dialog.cancel();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (spinnerloi.getSelectedItemPosition() == 0) {

                                } else {
                                    dialog.cancel();
                                }
                            }
                        });

                        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                radioButton36.setChecked(false);
                                dialog.cancel();
                            }
                        });
                        dialog.show();
                    }
                }
            });

        } catch (Exception e) {
        }
        try {
            radioButton37.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rgChecked218) {
                        radioGroup18.clearCheck();
                        rgChecked218 = false;
                        rgChecked18 = false;
                    } else {
                        rgChecked218 = true;
                        rgChecked18 = false;
                        final Dialog dialog = new Dialog(Infrastructure.this);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.loi_dialog);
                        dialog.setTitle("Dakshata Mentor");
                        final Spinner spinnerloi = dialog.findViewById(R.id.infra_spinner1);
                        Button button = dialog.findViewById(R.id.btn_loi);
                        spinnerloi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position == 0) {

                                } else {
                                    row_18 = spinnerloi.getSelectedItem().toString();
                                    //  dialog.cancel();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (spinnerloi.getSelectedItemPosition() == 0) {

                                } else {
                                    dialog.cancel();
                                }
                            }
                        });

                        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                radioButton37.setChecked(false);
                                dialog.cancel();
                            }
                        });
                        dialog.show();
                    }
                }
            });

        } catch (Exception e) {
        }
        try {
            radioButton38.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rgChecked219) {
                        radioGroup19.clearCheck();
                        rgChecked219 = false;
                        rgChecked19 = false;
                    } else {
                        rgChecked219 = true;
                        rgChecked19 = false;
                        final Dialog dialog = new Dialog(Infrastructure.this);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.loi_dialog);
                        dialog.setTitle("Dakshata Mentor");
                        final Spinner spinnerloi = dialog.findViewById(R.id.infra_spinner1);
                        Button button = dialog.findViewById(R.id.btn_loi);
                        spinnerloi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position == 0) {

                                } else {
                                    row_19 = spinnerloi.getSelectedItem().toString();
                                    //  dialog.cancel();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (spinnerloi.getSelectedItemPosition() == 0) {

                                } else {
                                    dialog.cancel();
                                }
                            }
                        });
                        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                radioButton38.setChecked(false);
                                dialog.cancel();
                            }
                        });
                        dialog.show();
                    }
                }
            });

        } catch (Exception e) {
        }

        try {
            radioButton59.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rgChecked220) {
                        radioGroup20.clearCheck();
                        rgChecked220 = false;
                        rgChecked20 = false;
                    } else {
                        rgChecked220 = true;
                        rgChecked20 = false;
                        final Dialog dialog = new Dialog(Infrastructure.this);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.loi_dialog);
                        dialog.setTitle("Dakshata Mentor");
                        final Spinner spinnerloi = dialog.findViewById(R.id.infra_spinner1);
                        Button button=dialog.findViewById(R.id.btn_loi);
                        spinnerloi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position == 0) {

                                } else {
                                    row_20 = spinnerloi.getSelectedItem().toString();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (spinnerloi.getSelectedItemPosition()==0){

                                }else {
                                    dialog.cancel();
                                }
                            }
                        });
                        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                radioButton59.setChecked(false);
                                dialog.cancel();
                            }
                        });
                        dialog.show();
                    }
                }
            });

        } catch (Exception e) {
        }
        radioButton58.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rgChecked20) {
                    radioGroup20.clearCheck();
                    rgChecked20 = false;
                    rgChecked220 = false;
                    row_20 = "";
                } else {
                    rgChecked20 = true;
                    rgChecked220 = false;
                    row_20 = radioButton58.getText().toString();
                }
            }
        });

        radioButton57.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rgChecked19) {
                    radioGroup19.clearCheck();
                    rgChecked19 = false;
                    rgChecked219 = false;
                    row_19 = "";
                } else {
                    rgChecked19 = true;
                    rgChecked219 = false;
                    row_19 = radioButton57.getText().toString();
                }
            }
        });
        radioButton39.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rgChecked1) {
                    radioGroup1.clearCheck();
                    rgChecked1 = false;
                    rgChecked21 = false;
                    row_1 = "";
                } else {
                    rgChecked1 = true;
                    rgChecked21 = false;
                    row_1 = radioButton39.getText().toString();
                }
            }
        });
        radioButton40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rgChecked2) {
                    radioGroup2.clearCheck();
                    rgChecked2 = false;
                    rgChecked22 = false;
                    row_2 = "";
                } else {
                    rgChecked2 = true;
                    rgChecked22 = false;
                    row_2 = radioButton40.getText().toString();
                }
            }
        });
        radioButton41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rgChecked3) {
                    radioGroup3.clearCheck();
                    rgChecked3 = false;
                    rgChecked213 = false;
                    row_3 = "";
                } else {
                    rgChecked3 = true;
                    rgChecked23 = false;
                    row_3 = radioButton41.getText().toString();
                }
            }
        });
        radioButton42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rgChecked4) {
                    radioGroup4.clearCheck();
                    rgChecked4 = false;
                    rgChecked24 = false;
                    row_4 = "";
                } else {
                    rgChecked4 = true;
                    rgChecked24 = false;
                    row_4 = radioButton42.getText().toString();
                }
            }
        });
        radioButton43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rgChecked5) {
                    radioGroup5.clearCheck();
                    rgChecked5 = false;
                    rgChecked25 = false;
                    row_5 = "";
                } else {
                    rgChecked5 = true;
                    rgChecked25 = false;
                    row_5 = radioButton43.getText().toString();
                }
            }
        });
        radioButton44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rgChecked6) {
                    radioGroup6.clearCheck();
                    rgChecked6 = false;
                    rgChecked26 = false;
                    row_6 = "";
                } else {
                    rgChecked6 = true;
                    rgChecked26 = false;
                    row_6 = radioButton44.getText().toString();
                }
            }
        });
        radioButton45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rgChecked7) {
                    radioGroup7.clearCheck();
                    rgChecked7 = false;
                    rgChecked27 = false;
                    row_7 = "";
                } else {
                    rgChecked7 = true;
                    rgChecked27 = false;
                    row_7 = radioButton45.getText().toString();
                }
            }
        });
        radioButton46.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rgChecked8) {
                    radioGroup8.clearCheck();
                    rgChecked8 = false;
                    rgChecked28 = false;
                    row_8 = "";
                } else {
                    rgChecked8 = true;
                    rgChecked28 = false;
                    row_8 = radioButton46.getText().toString();
                }
            }
        });
        radioButton47.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rgChecked9) {
                    radioGroup9.clearCheck();
                    rgChecked9 = false;
                    rgChecked29 = false;
                    row_9 = "";
                } else {
                    rgChecked9 = true;
                    rgChecked29 = false;
                    row_9 = radioButton47.getText().toString();
                }
            }
        });
        radioButton48.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rgChecked10) {
                    radioGroup10.clearCheck();
                    rgChecked10 = false;
                    rgChecked210 = false;
                    row_10 = "";
                } else {
                    rgChecked10 = true;
                    rgChecked210 = false;
                    row_10 = radioButton48.getText().toString();
                }
            }
        });
        radioButton49.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rgChecked11) {
                    radioGroup11.clearCheck();
                    rgChecked11 = false;
                    rgChecked211 = false;
                    row_11 = "";
                } else {
                    rgChecked11 = true;
                    rgChecked211 = false;
                    row_11 = radioButton49.getText().toString();
                }
            }
        });
        radioButton50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rgChecked12) {
                    radioGroup12.clearCheck();
                    rgChecked12 = false;
                    rgChecked212 = false;
                    row_12 = "";
                } else {
                    rgChecked12 = true;
                    rgChecked212 = false;
                    row_12 = radioButton50.getText().toString();
                }
            }
        });
        radioButton51.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rgChecked13) {
                    radioGroup13.clearCheck();
                    rgChecked13 = false;
                    rgChecked213 = false;
                    row_13 = "";
                } else {
                    rgChecked13 = true;
                    rgChecked213 = false;
                    row_13 = radioButton51.getText().toString();
                }
            }
        });
        radioButton52.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rgChecked14) {
                    radioGroup14.clearCheck();
                    rgChecked14 = false;
                    rgChecked214 = false;
                    row_14 = "";
                } else {
                    rgChecked14 = true;
                    rgChecked214 = false;
                    row_14 = radioButton52.getText().toString();
                }
            }
        });
        radioButton53.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rgChecked15) {
                    radioGroup15.clearCheck();
                    rgChecked15 = false;
                    rgChecked215 = false;
                    row_15 = "";
                } else {
                    rgChecked15 = true;
                    rgChecked215 = false;
                    row_15 = radioButton53.getText().toString();
                }
            }
        });
        radioButton54.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rgChecked16) {
                    radioGroup16.clearCheck();
                    rgChecked16 = false;
                    rgChecked216 = false;
                    row_16 = "";
                } else {
                    rgChecked16 = true;
                    rgChecked216 = false;
                    row_16 = radioButton54.getText().toString();
                }
            }
        });
        radioButton55.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rgChecked17) {
                    radioGroup17.clearCheck();
                    rgChecked17 = false;
                    rgChecked217 = false;
                    row_17 = "";
                } else {
                    rgChecked17 = true;
                    rgChecked217 = false;
                    row_17 = radioButton55.getText().toString();
                }
            }
        });
        radioButton56.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rgChecked18) {
                    radioGroup18.clearCheck();
                    rgChecked18 = false;
                    rgChecked218 = false;
                    row_18 = "";
                } else {
                    rgChecked18 = true;
                    rgChecked218 = false;
                    row_18 = radioButton56.getText().toString();
                }
            }
        });
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserEnteredData();

            }

        });

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sm_previous_visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                int levelPosition = 1;
                if (isCurrentRecordBackupExist) levelPosition = 2;
                else levelPosition = 1;
                Cursor cursor=sqLiteDatabase.rawQuery("select * from " + TABLENAME5 + " ORDER BY id DESC LIMIT " + levelPosition, null);
                if (cursor.getCount() >= levelPosition) {
                    if (isCurrentRecordBackupExist) cursor.moveToLast();
                    else cursor.moveToFirst();
                    setSavedValuesInFields(cursor);
                } else {
                    Toast.makeText(getApplicationContext(), "No previous data is available.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        getAllAccessToGPSLocation();
    }

    public void initViews() {
        radioGroup1 = (RadioGroup) findViewById(R.id.infra_rg1);
        radioGroup2 = (RadioGroup) findViewById(R.id.infra_rg2);
        radioGroup3 = (RadioGroup) findViewById(R.id.infra_rg3);
        radioGroup4 = (RadioGroup) findViewById(R.id.infra_rg4);
        radioGroup5 = (RadioGroup) findViewById(R.id.infra_rg5);
        radioGroup6 = (RadioGroup) findViewById(R.id.infra_rg6);
        radioGroup7 = (RadioGroup) findViewById(R.id.infra_rg7);
        radioGroup8 = (RadioGroup) findViewById(R.id.infra_rg8);
        radioGroup9 = (RadioGroup) findViewById(R.id.infra_rg9);
        radioGroup10 = (RadioGroup) findViewById(R.id.infra_rg10);
        radioGroup11 = (RadioGroup) findViewById(R.id.infra_rg11);
        radioGroup12 = (RadioGroup) findViewById(R.id.infra_rg12);
        radioGroup13 = (RadioGroup) findViewById(R.id.infra_rg13);
        radioGroup14 = (RadioGroup) findViewById(R.id.infra_rg14);
        radioGroup15 = (RadioGroup) findViewById(R.id.infra_rg15);
        radioGroup16 = (RadioGroup) findViewById(R.id.infra_rg16);
        radioGroup17 = (RadioGroup) findViewById(R.id.infra_rg17);
        radioGroup18 = (RadioGroup) findViewById(R.id.infra_rg18);
        radioGroup19 = (RadioGroup) findViewById(R.id.infra_rg19);

        radioGroup20 = (RadioGroup) findViewById(R.id.infra_rg20);

        radioButton20 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton21 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton22 = (RadioButton) findViewById(R.id.radioButton3);
        radioButton23 = (RadioButton) findViewById(R.id.radioButton4);
        radioButton24 = (RadioButton) findViewById(R.id.radioButton5);
        radioButton25 = (RadioButton) findViewById(R.id.radioButton6);
        radioButton26 = (RadioButton) findViewById(R.id.radioButton7);
        radioButton27 = (RadioButton) findViewById(R.id.radioButton8);
        radioButton28 = (RadioButton) findViewById(R.id.radioButton9);
        radioButton29 = (RadioButton) findViewById(R.id.radioButton10);
        radioButton30 = (RadioButton) findViewById(R.id.radioButton11);
        radioButton31 = (RadioButton) findViewById(R.id.radioButton12);
        radioButton32 = (RadioButton) findViewById(R.id.radioButton13);
        radioButton33 = (RadioButton) findViewById(R.id.radioButton14);
        radioButton34 = (RadioButton) findViewById(R.id.radioButton15);
        radioButton35 = (RadioButton) findViewById(R.id.radioButton16);
        radioButton36 = (RadioButton) findViewById(R.id.radioButton17);
        radioButton37 = (RadioButton) findViewById(R.id.radioButton18);
        radioButton38 = (RadioButton) findViewById(R.id.radioButton19);
        radioButton39 = (RadioButton) findViewById(R.id.radio1);
        radioButton40 = (RadioButton) findViewById(R.id.radio2);
        radioButton41 = (RadioButton) findViewById(R.id.radio3);
        radioButton42 = (RadioButton) findViewById(R.id.radio4);
        radioButton43 = (RadioButton) findViewById(R.id.radio5);
        radioButton44 = (RadioButton) findViewById(R.id.radio6);
        radioButton45 = (RadioButton) findViewById(R.id.radio7);
        radioButton46 = (RadioButton) findViewById(R.id.radio8);
        radioButton47 = (RadioButton) findViewById(R.id.radio9);
        radioButton48 = (RadioButton) findViewById(R.id.radio10);
        radioButton49 = (RadioButton) findViewById(R.id.radio11);
        radioButton50 = (RadioButton) findViewById(R.id.radio12);
        radioButton51 = (RadioButton) findViewById(R.id.radio13);
        radioButton52 = (RadioButton) findViewById(R.id.radio14);
        radioButton53 = (RadioButton) findViewById(R.id.radio15);
        radioButton54 = (RadioButton) findViewById(R.id.radio16);
        radioButton55 = (RadioButton) findViewById(R.id.radio17);
        radioButton56 = (RadioButton) findViewById(R.id.radio18);
        radioButton57 = (RadioButton) findViewById(R.id.radio19);

        radioButton58 = (RadioButton) findViewById(R.id.radio20);
        radioButton59 = (RadioButton) findViewById(R.id.radioButton20);

        button_save = (Button) findViewById(R.id.infra_save);
        button_back = (Button) findViewById(R.id.infra_back);
        spinner1 = (Spinner) findViewById(R.id.infra_spinner1);
        linearLayout = (LinearLayout) findViewById(R.id.loilo);
        linearLayout.setVisibility(View.GONE);
        sm_previous_visit = (Button) findViewById(R.id.sm_previous_visit);

    }

    public void Pback(View view) {
        super.onBackPressed();
    }

    public void getUserEnteredData() {
        int selectedId = radioGroup1.getCheckedRadioButtonId();
        int selectedId2 = radioGroup2.getCheckedRadioButtonId();
        int selectedId3 = radioGroup3.getCheckedRadioButtonId();
        int selectedId4 = radioGroup4.getCheckedRadioButtonId();
        int selectedId5 = radioGroup5.getCheckedRadioButtonId();
        int selectedId6 = radioGroup6.getCheckedRadioButtonId();
        int selectedId7 = radioGroup7.getCheckedRadioButtonId();
        int selectedId8 = radioGroup8.getCheckedRadioButtonId();
        int selectedId9 = radioGroup9.getCheckedRadioButtonId();
        int selectedId10 = radioGroup10.getCheckedRadioButtonId();
        int selectedId11 = radioGroup11.getCheckedRadioButtonId();
        int selectedId12 = radioGroup12.getCheckedRadioButtonId();
        int selectedId13 = radioGroup13.getCheckedRadioButtonId();
        int selectedId14 = radioGroup14.getCheckedRadioButtonId();
        int selectedId15 = radioGroup15.getCheckedRadioButtonId();
        int selectedId16 = radioGroup16.getCheckedRadioButtonId();
        int selectedId17 = radioGroup17.getCheckedRadioButtonId();
        int selectedId18 = radioGroup18.getCheckedRadioButtonId();
        int selectedId19 = radioGroup19.getCheckedRadioButtonId();
        int selectedId20 = radioGroup20.getCheckedRadioButtonId();
        if (radioGroup1.getCheckedRadioButtonId() < 0 && radioGroup2.getCheckedRadioButtonId() < 0 && radioGroup3.getCheckedRadioButtonId() < 0 && radioGroup4.getCheckedRadioButtonId() < 0 && radioGroup5.getCheckedRadioButtonId() < 0 && radioGroup6.getCheckedRadioButtonId() < 0 && radioGroup7.getCheckedRadioButtonId() < 0 && radioGroup8.getCheckedRadioButtonId() < 0 && radioGroup9.getCheckedRadioButtonId() < 0 && radioGroup10.getCheckedRadioButtonId() < 0 && radioGroup11.getCheckedRadioButtonId() < 0 && radioGroup12.getCheckedRadioButtonId() < 0 && radioGroup13.getCheckedRadioButtonId() < 0 && radioGroup14.getCheckedRadioButtonId() < 0 && radioGroup15.getCheckedRadioButtonId() < 0 && radioGroup16.getCheckedRadioButtonId() < 0 && radioGroup17.getCheckedRadioButtonId() < 0 && radioGroup18.getCheckedRadioButtonId() < 0 && radioGroup19.getCheckedRadioButtonId() < 0 && radioGroup20.getCheckedRadioButtonId() < 0) {
        } else {
                   /* progressDialog.setMessage("Please wait...");
                    progressDialog.show();*/
            try {
                radioButton1 = (RadioButton) findViewById(selectedId);
                //  row_1 = radioButton1.getText().toString();
                if (!row_1.matches("")) {
                    count++;
                }

            } catch (Exception e) {
            }
            try {
                radioButton2 = (RadioButton) findViewById(selectedId2);
                //   row_2 = radioButton2.getText().toString();
                if (!row_2.matches("")) {
                    count++;
                }
            } catch (Exception e) {
            }
            try {
                radioButton3 = (RadioButton) findViewById(selectedId3);
                //    row_3 = radioButton3.getText().toString();
                if (!row_3.matches("")) {
                    count++;
                }
            } catch (Exception e) {
            }
            try {
                radioButton4 = (RadioButton) findViewById(selectedId4);
                //   row_4 = radioButton4.getText().toString();
                if (!row_4.matches("")) {
                    count++;
                }
            } catch (Exception e) {
            }
            try {
                radioButton5 = (RadioButton) findViewById(selectedId5);
                //  row_5 = radioButton5.getText().toString();
                if (!row_5.matches("")) {
                    count++;
                }
            } catch (Exception e) {
            }
            try {
                radioButton6 = (RadioButton) findViewById(selectedId6);
                //   row_6 = radioButton6.getText().toString();
                if (!row_6.matches("")) {
                    count++;
                }
            } catch (Exception e) {
            }
            try {
                radioButton7 = (RadioButton) findViewById(selectedId7);
                //   row_7 = radioButton7.getText().toString();
                if (!row_7.matches("")) {
                    count++;
                }
            } catch (Exception e) {
            }
            try {
                radioButton8 = (RadioButton) findViewById(selectedId8);
                //   row_8 = radioButton8.getText().toString();
                if (!row_8.matches("")) {
                    count++;
                }
            } catch (Exception e) {
            }
            try {
                radioButton9 = (RadioButton) findViewById(selectedId9);
                //    row_9 = radioButton9.getText().toString();
                if (!row_9.matches("")) {
                    count++;
                }
            } catch (Exception e) {
            }
            try {
                radioButton10 = (RadioButton) findViewById(selectedId10);
                //    row_10 = radioButton10.getText().toString();
                if (!row_10.matches("")) {
                    count++;
                }
            } catch (Exception e) {
            }
            try {
                radioButton11 = (RadioButton) findViewById(selectedId11);
                //   row_11 = radioButton11.getText().toString();
                if (!row_11.matches("")) {
                    count++;
                }
            } catch (Exception e) {
            }
            try {
                radioButton12 = (RadioButton) findViewById(selectedId12);
                //   row_12 = radioButton12.getText().toString();
                if (!row_12.matches("")) {
                    count++;
                }
            } catch (Exception e) {
            }
            try {
                radioButton13 = (RadioButton) findViewById(selectedId13);
                //    row_13 = radioButton13.getText().toString();
                if (!row_13.matches("")) {
                    count++;
                }
            } catch (Exception e) {
            }
            try {
                radioButton14 = (RadioButton) findViewById(selectedId14);
                //    row_14 = radioButton14.getText().toString();
                if (!row_14.matches("")) {
                    count++;
                }
            } catch (Exception e) {
            }
            try {
                radioButton15 = (RadioButton) findViewById(selectedId15);
                //    row_15 = radioButton15.getText().toString();
                if (!row_15.matches("")) {
                    count++;
                }
            } catch (Exception e) {
            }
            try {
                radioButton16 = (RadioButton) findViewById(selectedId16);
                //    row_16 = radioButton16.getText().toString();
                if (!row_16.matches("")) {
                    count++;
                }
            } catch (Exception e) {
            }
            try {
                radioButton17 = (RadioButton) findViewById(selectedId17);
                //    row_17 = radioButton17.getText().toString();
                if (!row_17.matches("")) {
                    count++;
                }
            } catch (Exception e) {
            }
            try {
                radioButton18 = (RadioButton) findViewById(selectedId18);
                //    row_18 = radioButton18.getText().toString();
                if (!row_18.matches("")) {
                    count++;
                }
            } catch (Exception e) {
            }
            try {
                radioButton19 = (RadioButton) findViewById(selectedId19);
                if (!row_19.matches("")) {
                    count++;
                }
            } catch (Exception e) {
            }

            try {
                radioButton60 = (RadioButton) findViewById(selectedId20);
                if (!row_20.matches("")) {
                    count++;
                }
            } catch (Exception e) {
            }

            sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);
            editor1 = sharedPreferences.edit();
            try {
                SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                Cursor cursor = sqLiteDatabase.query(TABLENAME5, new String[]{COL_SESSION}, COL_SESSION + "=? AND " + COL_UNIQUE_ID + "=?", new String[]{sessionid, String.valueOf(MentorConstant.recordId)}, null, null, null);
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    createJson();
//                    jhpiegoDatabase.deleteInfrastructure(username, row_0, row_1, row_2, row_3, row_4, row_5, row_6, row_7, row_8, row_9, row_10, row_11, row_12, row_13, row_14, row_15, row_16, row_17, row_18, row_19, String.valueOf(count), sessionid, ansJson, row_20);
                    long row = jhpiegoDatabase.addInfrastructure(username, row_0, row_1, row_2, row_3, row_4, row_5, row_6, row_7, row_8, row_9, row_10, row_11, row_12, row_13, row_14, row_15, row_16, row_17, row_18, row_19, String.valueOf(count), sessionid, ansJson, row_20, String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude), String.valueOf(MentorConstant.recordId), 1);
                    SharedPreferences sPref = getSharedPreferences("session", MODE_PRIVATE);
                    jhpiegoDatabase.updateLatAndLongWithDetailsOfVisit(String.valueOf(MentorConstant.recordId), String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude));
                    if (row != -1) {

                        Toast.makeText(getApplicationContext(), getString(R.string.alert_saved_successfully), Toast.LENGTH_SHORT).show();
                        if (!MentorConstant.whichBlockCalled) {
                            sharedPreferencescount = getSharedPreferences("infra" + username, MODE_PRIVATE);
                            editorcount = sharedPreferencescount.edit();
                            editorcount.putString("count" + username, String.valueOf(count));
                            editorcount.commit();
                        }
                        onBackPressed();
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.alert_not_saved_to_db), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    createJson();
                    long row = jhpiegoDatabase.addInfrastructure(username, row_0, row_1, row_2, row_3, row_4, row_5, row_6, row_7, row_8, row_9, row_10, row_11, row_12, row_13, row_14, row_15, row_16, row_17, row_18, row_19, String.valueOf(count), sessionid, ansJson, row_20, String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude), String.valueOf(MentorConstant.recordId), 0);
                    SharedPreferences sPref = getSharedPreferences("session", MODE_PRIVATE);
                    jhpiegoDatabase.updateLatAndLongWithDetailsOfVisit(String.valueOf(MentorConstant.recordId), String.valueOf(MentorConstant.latitude), String.valueOf(MentorConstant.longitude));
                    if (row != -1) {

                        Toast.makeText(getApplicationContext(), getString(R.string.alert_saved_successfully), Toast.LENGTH_SHORT).show();
                        if (!MentorConstant.whichBlockCalled) {
                            sharedPreferencescount = getSharedPreferences("infra" + username, MODE_PRIVATE);
                            editorcount = sharedPreferencescount.edit();
                            editorcount.putString("count" + username, String.valueOf(count));
                            editorcount.commit();
                        }
                        onBackPressed();
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.alert_not_saved_to_db), Toast.LENGTH_SHORT).show();
                    }
                }
                sqLiteDatabase.close();
            } catch (Exception e) {
            }
            /*long row=jhpiegoDatabase.addInfrastructure(row_0,row_1,row_2,row_3,row_4,row_5,row_6,row_7,row_8,row_9,row_10,row_11,row_12,row_13,row_14,row_15,row_16,row_17,row_18,row_19, String.valueOf(count),sessionid);
            if(row!=-1)
            {
                Toast.makeText(getApplicationContext(), getString(R.string.alert_saved_successfully), Toast.LENGTH_SHORT).show();
                sharedPreferencescount=getSharedPreferences("infra",MODE_PRIVATE);
                editorcount=sharedPreferencescount.edit();
                editorcount.putString("count", String.valueOf(count));
                editorcount.commit();
                Intent intent=new Intent(Infrastructure.this,MainActivity.class);
                startActivity(intent);

            }else{
                Toast.makeText(getApplicationContext(), getString(R.string.alert_not_saved_to_db), Toast.LENGTH_SHORT).show();
            }*/

        }
    }

    public void getSessionOfUser() {
        try {
            SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query(TABLENAME1, new String[]{COL_USERNAME, COL_SESSION}, COL_USERNAME + "=?", new String[]{username}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                session = cursor.getString(cursor.getColumnIndex(COL_SESSION));
            }
            sqLiteDatabase.close();
        } catch (Exception e) {
        }
    }

    public void setDefaultValues() {
        try {
            if (!jhpiegoDatabase.isLastVisitSubmittedInfrastructure(sessionid)) {
                //  Cursor cursor = jhpiegoDatabase.getLastVisitDataInfrastructure();
                SQLiteDatabase sqLiteDatabase = jhpiegoDatabase.getReadableDatabase();
                Cursor cursor = sqLiteDatabase.query(TABLENAME5, new String[]{COL_USERNAME, COL_LOI, COL_LRD, COL_PSC, COL_LCFW, COL_ASTNSEM, COL_WALLS, COL_AC, COL_AV, COL_FLSL, COL_PB, COL_LOF, COL_ASBT, COL_CNRNB, COL_CMDS, COL_RW, COL_SOB, COL_FWRW, COL_DUA, COL_FHWS, COL_EOT, COL_THREE_SIDE_SPACE}, COL_SESSION + "=?", new String[]{sessionid}, null, null, null);

                //  cursor.moveToFirst();
                if (cursor.getCount() > 0) {
                    cursor.moveToLast();
                    isCurrentRecordBackupExist = true;
                    setSavedValuesInFields(cursor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            isCurrentRecordBackupExist = false;
        }
    }

    private void setSavedValuesInFields(Cursor cursor) {
        String col_1 = cursor.getString(cursor.getColumnIndex(COL_LOI));
        String col_2 = cursor.getString(cursor.getColumnIndex(COL_LRD));
        String col_3 = cursor.getString(cursor.getColumnIndex(COL_PSC));
        String col_4 = cursor.getString(cursor.getColumnIndex(COL_LCFW));
        String col_5 = cursor.getString(cursor.getColumnIndex(COL_ASTNSEM));
        String col_6 = cursor.getString(cursor.getColumnIndex(COL_WALLS));
        String col_7 = cursor.getString(cursor.getColumnIndex(COL_AC));
        String col_8 = cursor.getString(cursor.getColumnIndex(COL_AV));
        String col_9 = cursor.getString(cursor.getColumnIndex(COL_FLSL));
        String col_10 = cursor.getString(cursor.getColumnIndex(COL_PB));
        String col_11 = cursor.getString(cursor.getColumnIndex(COL_LOF));
        String col_12 = cursor.getString(cursor.getColumnIndex(COL_ASBT));
        String col_13 = cursor.getString(cursor.getColumnIndex(COL_CNRNB));
        String col_14 = cursor.getString(cursor.getColumnIndex(COL_CMDS));
        String col_15 = cursor.getString(cursor.getColumnIndex(COL_RW));
        String col_16 = cursor.getString(cursor.getColumnIndex(COL_SOB));
        String col_18 = cursor.getString(cursor.getColumnIndex(COL_DUA));
        String col_17 = cursor.getString(cursor.getColumnIndex(COL_FWRW));
        String col_19 = cursor.getString(cursor.getColumnIndex(COL_FHWS));
        String col_20 = cursor.getString(cursor.getColumnIndex(COL_EOT));
        String col_21 = cursor.getString(cursor.getColumnIndex(COL_THREE_SIDE_SPACE));

        if (!col_2.equalsIgnoreCase(""))
            if (!col_2.toLowerCase().equalsIgnoreCase("yes")) {
                radioButton20.setChecked(true);
                rgChecked21 = true;
                rgChecked1 = false;
                row_1 = col_2;
            } else {
                radioButton39.setChecked(true);
                rgChecked21 = false;
                rgChecked1 = true;
                row_1 = col_2;
            }

        if (!col_3.equalsIgnoreCase(""))
            if (!col_3.toLowerCase().equalsIgnoreCase("yes")) {
                radioButton21.setChecked(true);
                rgChecked22 = true;
                rgChecked2 = false;
                row_2 = col_3;
            } else {
                radioButton40.setChecked(true);
                rgChecked22 = false;
                rgChecked2 = true;
                row_2 = col_3;
            }

        if (!col_4.equalsIgnoreCase(""))
            if (!col_4.toLowerCase().equalsIgnoreCase("yes")) {
                radioButton22.setChecked(true);
                rgChecked23 = true;
                rgChecked3 = false;
                row_3 = col_4;
            } else {
                radioButton41.setChecked(true);
                rgChecked23 = false;
                rgChecked3 = true;
                row_3 = col_4;
            }

        if (!col_5.equalsIgnoreCase(""))
            if (!col_5.toLowerCase().equalsIgnoreCase("yes")) {
                radioButton23.setChecked(true);
                rgChecked24 = true;
                rgChecked4 = false;
                row_4 = col_5;
            } else {
                radioButton42.setChecked(true);
                rgChecked24 = false;
                rgChecked4 = true;
                row_4 = col_5;
            }

        if (!col_6.equalsIgnoreCase(""))
            if (!col_6.toLowerCase().equalsIgnoreCase("yes")) {
                radioButton24.setChecked(true);
                rgChecked25 = true;
                rgChecked5 = false;
                row_5 = col_6;
            } else {
                radioButton43.setChecked(true);
                rgChecked25 = false;
                rgChecked5 = true;
                row_5 = col_6;
            }

        if (!col_7.equalsIgnoreCase(""))
            if (!col_7.toLowerCase().equalsIgnoreCase("yes")) {
                radioButton25.setChecked(true);
                rgChecked26 = true;
                rgChecked6 = false;
                row_6 = col_7;
            } else {
                radioButton44.setChecked(true);
                rgChecked26 = false;
                rgChecked6 = true;
                row_6 = col_7;
            }

        if (!col_8.equalsIgnoreCase(""))
            if (!col_8.toLowerCase().equalsIgnoreCase("yes")) {
                radioButton26.setChecked(true);
                rgChecked27 = true;
                rgChecked7 = false;
                row_7 = col_8;
            } else {
                radioButton45.setChecked(true);
                rgChecked27 = false;
                rgChecked7 = true;
                row_7 = col_8;
            }

        if (!col_9.equalsIgnoreCase(""))
            if (!col_9.toLowerCase().equalsIgnoreCase("yes")) {
                radioButton27.setChecked(true);
                rgChecked28 = true;
                rgChecked8 = false;
                row_8 = col_9;
            } else {
                radioButton46.setChecked(true);
                rgChecked28 = false;
                rgChecked8 = true;
                row_8 = col_9;
            }

        if (!col_10.equalsIgnoreCase(""))
            if (!col_10.toLowerCase().equalsIgnoreCase("yes")) {
                radioButton28.setChecked(true);
                rgChecked29 = true;
                rgChecked9 = false;
                row_9 = col_10;
            } else {
                radioButton47.setChecked(true);
                rgChecked29 = false;
                rgChecked9 = true;
                row_9 = col_10;
            }

        if (!col_11.equalsIgnoreCase(""))
            if (!col_11.toLowerCase().equalsIgnoreCase("yes")) {
                radioButton29.setChecked(true);
                rgChecked210 = true;
                rgChecked10 = false;
                row_10 = col_11;
            } else {
                radioButton48.setChecked(true);
                rgChecked210 = false;
                rgChecked10 = true;
                row_10 = col_11;
            }

        if (!col_12.equalsIgnoreCase(""))
            if (!col_12.toLowerCase().equalsIgnoreCase("yes")) {
                radioButton30.setChecked(true);
                rgChecked211 = true;
                rgChecked11 = false;
                row_11 = col_12;
            } else {
                radioButton49.setChecked(true);
                rgChecked211 = false;
                rgChecked11 = true;
                row_11 = col_12;
            }

        if (!col_13.equalsIgnoreCase(""))
            if (!col_13.toLowerCase().equalsIgnoreCase("yes")) {
                radioButton31.setChecked(true);
                rgChecked212 = true;
                rgChecked12 = false;
                row_12 = col_13;
            } else {
                radioButton50.setChecked(true);
                rgChecked212 = false;
                rgChecked12 = true;
                row_12 = col_13;
            }

        if (!col_14.equalsIgnoreCase(""))
            if (!col_14.toLowerCase().equalsIgnoreCase("yes")) {
                radioButton32.setChecked(true);
                rgChecked213 = true;
                rgChecked13 = false;
                row_13 = col_14;
            } else {
                radioButton51.setChecked(true);
                rgChecked213 = false;
                rgChecked13 = true;
                row_13 = col_14;
            }

        if (!col_15.equalsIgnoreCase(""))
            if (!col_15.toLowerCase().equalsIgnoreCase("yes")) {
                radioButton33.setChecked(true);
                rgChecked214 = true;
                rgChecked14 = false;
                row_14 = col_15;
            } else {
                radioButton52.setChecked(true);
                rgChecked214 = false;
                rgChecked14 = true;
                row_14 = col_15;
            }

        if (!col_16.equalsIgnoreCase(""))
            if (!col_16.toLowerCase().equalsIgnoreCase("yes")) {
                radioButton34.setChecked(true);
                rgChecked215 = true;
                rgChecked15 = false;
                row_15 = col_16;
            } else {
                radioButton53.setChecked(true);
                rgChecked215 = false;
                rgChecked15 = true;
                row_15 = col_16;
            }

        if (!col_17.equalsIgnoreCase(""))
            if (!col_17.toLowerCase().equalsIgnoreCase("yes")) {
                radioButton35.setChecked(true);
                rgChecked216 = true;
                rgChecked16 = false;
                row_16 = col_17;
            } else {
                radioButton54.setChecked(true);
                rgChecked216 = false;
                rgChecked16 = true;
                row_16 = col_17;
            }

        if (!col_18.equalsIgnoreCase(""))
            if (!col_18.toLowerCase().equalsIgnoreCase("yes")) {
                radioButton36.setChecked(true);
                rgChecked217 = true;
                rgChecked17 = false;
                row_17 = col_18;
            } else {
                radioButton55.setChecked(true);
                rgChecked217 = false;
                rgChecked17 = true;
                row_17 = col_18;
            }

        if (!col_19.equalsIgnoreCase(""))
            if (!col_19.toLowerCase().equalsIgnoreCase("yes")) {
                radioButton37.setChecked(true);
                rgChecked218 = true;
                rgChecked18 = false;
                row_18 = col_19;
            } else {
                radioButton56.setChecked(true);
                rgChecked218 = false;
                rgChecked18 = true;
                row_18 = col_19;
            }

        if (!col_20.equalsIgnoreCase(""))
            if (!col_20.toLowerCase().equalsIgnoreCase("yes")) {
                radioButton38.setChecked(true);
                rgChecked219 = true;
                rgChecked19 = false;
                row_19 = col_20;
            } else {
                radioButton57.setChecked(true);
                rgChecked219 = false;
                rgChecked19 = true;
                row_19 = col_20;
            }

        if (!col_21.equalsIgnoreCase(""))
            if (!col_21.toLowerCase().equalsIgnoreCase("yes")) {
                radioButton59.setChecked(true);
                rgChecked220 = true;
                rgChecked20 = false;
                row_20 = col_21;
            } else {
                radioButton58.setChecked(true);
                rgChecked220 = false;
                rgChecked20 = true;
                row_20 = col_21;
            }
    }

    private String getQuestionCode(String question) {

        for (F3 f3 : Infrastructure.mQuetionsMaster.getF3()) {
            if (f3.getQuestionsName() != null) {
                if (f3.getQuestionsName().replaceAll("\\s+$", "").equalsIgnoreCase(question.replaceAll("\\s+$", ""))){
                    Log.v("District f3", "" + f3.getQCode());
                    return f3.getQCode().toString();
                }
            }
            //something here
        }
        return "";

    }

    private void createJson() {
        AnswersModel mAnswersModel = new AnswersModel();

        mAnswersModel.setUser(username);
        mAnswersModel.setVisitId(sessionid);

        List<VisitDatum> mVisitDatumAns = new ArrayList<VisitDatum>();
        List<FormDatum> formDatumList = new ArrayList<>();
        VisitDatum mVisitDatum = new VisitDatum();
        mVisitDatum.setFormCode("f3");

        FormDatum mFormDatum = new FormDatum();
        mFormDatum.setAns(row_20);
        mFormDatum.setQCode("f3_q1");
        //mVisitDatumAns.add(mVisitDatum);
        formDatumList.add(mFormDatum);

        FormDatum mFormDatumstate = new FormDatum();
        mFormDatumstate.setAns(row_1);
        mFormDatumstate.setQCode("f3_q2");
        formDatumList.add(mFormDatumstate);

        FormDatum mFormDatumdistrict = new FormDatum();
        mFormDatumdistrict.setAns(row_2);
        mFormDatumdistrict.setQCode("f3_q3");
        formDatumList.add(mFormDatumdistrict);

        FormDatum mFormDatumblock = new FormDatum();
        mFormDatumblock.setAns(row_3);
        mFormDatumblock.setQCode("f3_q4");
        formDatumList.add(mFormDatumblock);

        FormDatum mFormDatumfacilitytype = new FormDatum();
        mFormDatumfacilitytype.setAns(row_4);
        mFormDatumfacilitytype.setQCode("f3_q5");
        formDatumList.add(mFormDatumfacilitytype);

        FormDatum mFormDatumfacilityname = new FormDatum();
        mFormDatumfacilityname.setAns(row_5);
        mFormDatumfacilityname.setQCode("f3_q6");
        formDatumList.add(mFormDatumfacilityname);

        FormDatum mFormDatumfacilitylevel = new FormDatum();
        mFormDatumfacilitylevel.setAns(row_6);
        mFormDatumfacilitylevel.setQCode("f3_q7");
        formDatumList.add(mFormDatumfacilitylevel);

        FormDatum mFormDatumdov = new FormDatum();
        mFormDatumdov.setAns(row_7);
        mFormDatumdov.setQCode("f3_q8");
        formDatumList.add(mFormDatumdov);

        FormDatum mFormDatum9 = new FormDatum();
        mFormDatum9.setAns(row_8);
        mFormDatum9.setQCode("f3_q9");
        formDatumList.add(mFormDatum9);

        FormDatum mFormDatum10 = new FormDatum();
        mFormDatum10.setAns(row_9);
        mFormDatum10.setQCode("f3_q10");
        formDatumList.add(mFormDatum10);

        FormDatum mFormDatum11 = new FormDatum();
        mFormDatum11.setAns(row_10);
        mFormDatum11.setQCode("f3_q11");
        formDatumList.add(mFormDatum11);

        FormDatum mFormDatum12 = new FormDatum();
        mFormDatum12.setAns(row_11);
        mFormDatum12.setQCode("f3_q12");
        formDatumList.add(mFormDatum12);

        FormDatum mFormDatum13 = new FormDatum();
        mFormDatum13.setAns(row_12);
        mFormDatum13.setQCode("f3_q13");
        formDatumList.add(mFormDatum13);

        FormDatum mFormDatum14 = new FormDatum();
        mFormDatum14.setAns(row_13);
        mFormDatum14.setQCode("f3_q14");
        formDatumList.add(mFormDatum14);



        FormDatum mFormDatum15=new FormDatum();
        mFormDatum15.setAns(row_14);
        mFormDatum15.setQCode("f3_q15");
        formDatumList.add(mFormDatum15);

        FormDatum mFormDatum16 = new FormDatum();
        mFormDatum16.setAns(row_15);
        mFormDatum16.setQCode("f3_q16");
        formDatumList.add(mFormDatum16);

        FormDatum mFormDatum17 = new FormDatum();
        mFormDatum17.setAns(row_16);
        mFormDatum17.setQCode("f3_q17");
        formDatumList.add(mFormDatum17);

        FormDatum mFormDatum18 = new FormDatum();
        mFormDatum18.setAns(row_17);
        mFormDatum18.setQCode("f3_q18");
        formDatumList.add(mFormDatum18);

        FormDatum mFormDatum19 = new FormDatum();
        mFormDatum19.setAns(row_18);
        mFormDatum19.setQCode("f3_q19");
        formDatumList.add(mFormDatum19);

        FormDatum mFormDatum20=new FormDatum();
        mFormDatum20.setAns(row_19);
        mFormDatum20.setQCode("f3_q20");
        formDatumList.add(mFormDatum20);

        mVisitDatum.setFormData(formDatumList);
        Gson gsonmVisitDatum = new Gson();
        ansJson = gsonmVisitDatum.toJson(mVisitDatum);
        Log.v("ans ", "ans json v: " + ansJson);

        mVisitDatumAns.add(mVisitDatum);
        mAnswersModel.setVisitData(mVisitDatumAns);
        Gson gson = new Gson();
        String fullJson = gson.toJson(mAnswersModel);
        try {
            JSONObject jsonObject = new JSONObject(fullJson);
            //  serverPost(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.v("ans ", "ans json : " + fullJson);
    }

    public void serverPost(JSONObject jsonObject) {

        String url = "http://84538451.ngrok.io/services/bookmark_answers";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("infrastructure", "" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("error", "" + error);

            }
        });
        requestQueue.add(jsonObjectRequest);
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
                            address = MentorConstant.getAddress(Infrastructure.this, MentorConstant.latitude, MentorConstant.longitude);
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
                    address = MentorConstant.getAddress(Infrastructure.this, MentorConstant.latitude, MentorConstant.longitude);
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
                ActivityCompat.requestPermissions(Infrastructure.this,
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
            address = MentorConstant.getAddress(Infrastructure.this, MentorConstant.latitude, MentorConstant.longitude);
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

}
