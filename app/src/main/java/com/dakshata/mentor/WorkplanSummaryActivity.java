package com.dakshata.mentor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dakshata.mentor.Utils.MentorConstant;
import com.dakshata.mentor.mydatecalendar.AppConstants;
import com.dakshata.mentor.mydatecalendar.EventListAdapter;
import com.dakshata.mentor.mydatecalendar.EventModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Umesh Kumar on 11-2-2019.
 */
public class WorkplanSummaryActivity extends AppCompatActivity {

    Button button_save, button_back;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    JhpiegoDatabase mydb;
    RecyclerView recycler_view_summary;
    WorkPlanSummaryAdapter workPlanSummaryAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_plan_summary);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.menu_image_opt3));
        setSupportActionBar(toolbar);
        TextView textViewHeaderName = (TextView) findViewById(R.id.header_name);
        textViewHeaderName.setText("Workplan Summary");
        initViews();

    }
    public void initViews() {
        button_back = (Button) findViewById(R.id.dov_back);
        button_save = (Button) findViewById(R.id.dov_save);
        recycler_view_summary = (RecyclerView) findViewById(R.id.recycler_view_summary);
        recycler_view_summary.setHasFixedSize(true);
        recycler_view_summary.setLayoutManager(new LinearLayoutManager(this));

        WorkPlanSummaryAdapter comparisionRecordAdapter = new WorkPlanSummaryAdapter(this, AppConstants.eventList, "month");
        recycler_view_summary.setAdapter(comparisionRecordAdapter);
    }

    public void Pback(View view) {
        super.onBackPressed();

    }

    private class WorkPlanSummaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Activity context;
        private String strViewFlag;
        private ArrayList<EventModel> eventModelList;

        public WorkPlanSummaryAdapter(Context context, ArrayList<EventModel> eventModelList, String strViewFlag) {
            this.context = (Activity) context;
            this.strViewFlag = strViewFlag;
            this.eventModelList = eventModelList;
        }

        class EventViewHolder extends RecyclerView.ViewHolder {

            LinearLayout ll_month_events;
            TextView tv_event_name, tv_event_district, tv_event_date, tv_event_time, tv_event_mobile, tv_mother_or_child;
            View v_divider;

            public EventViewHolder(View itemView) {
                super(itemView);
                ll_month_events = (LinearLayout) itemView.findViewById(R.id.ll_month_events);
                tv_event_name = (TextView) itemView.findViewById(R.id.tv_event_name);
                tv_event_district = (TextView) itemView.findViewById(R.id.tv_event_district);
                tv_event_date = (TextView) itemView.findViewById(R.id.tv_event_date);
                tv_event_time = (TextView) itemView.findViewById(R.id.tv_event_time);
                tv_event_mobile = (TextView) itemView.findViewById(R.id.tv_event_mobile);
                tv_mother_or_child = (TextView) itemView.findViewById(R.id.tv_mother_or_child);
                v_divider = (View) itemView.findViewById(R.id.v_divider);

            }

            public void setEvent(EventModel model) {

                if (strViewFlag.equals("month")) {
                    ll_month_events.setVisibility(View.VISIBLE);

                    String[] split = model.getStrStartTime().split("-");

                    if (split.length == 2) {
                        tv_event_name.setText("" + split[0]);
                        tv_event_district.setText("" + split[1]);
                    } else if (split.length == 1) {
                        tv_event_name.setText("" + split[0]);
                        tv_event_district.setText("NA");
                    } else {
                        tv_event_name.setText("NA");
                        tv_event_district.setText("NA");
                    }
                    tv_event_date.setText("" + model.getStrDate());
                    tv_event_time.setText("" + model.getStrName());
                    tv_event_mobile.setText("" + model.getStrEndTime());
                    tv_mother_or_child.setText(model.getMotherOrChild());

                    if (AppConstants.belowMonthEventTextColor != -1) {
                        tv_event_name.setTextColor(AppConstants.belowMonthEventTextColor);
                        tv_event_district.setTextColor(AppConstants.belowMonthEventTextColor);
                        tv_event_date.setTextColor(AppConstants.belowMonthEventTextColor);
                        tv_event_time.setTextColor(AppConstants.belowMonthEventTextColor);
                        tv_event_mobile.setTextColor(AppConstants.belowMonthEventTextColor);
                        tv_mother_or_child.setTextColor(AppConstants.belowMonthEventTextColor);
                    }

                    if (!AppConstants.strBelowMonthEventTextColor.equals("null")) {
                        tv_event_name.setTextColor(Color.parseColor(AppConstants.strBelowMonthEventTextColor));
                        tv_event_district.setTextColor(Color.parseColor(AppConstants.strBelowMonthEventTextColor));
                        tv_event_date.setTextColor(Color.parseColor(AppConstants.strBelowMonthEventTextColor));
                        tv_event_time.setTextColor(Color.parseColor(AppConstants.strBelowMonthEventTextColor));
                        tv_event_mobile.setTextColor(Color.parseColor(AppConstants.strBelowMonthEventTextColor));
                        tv_mother_or_child.setTextColor(Color.parseColor(AppConstants.strBelowMonthEventTextColor));
                    }

                    if (AppConstants.belowMonthEventDividerColor != -1) {
                        v_divider.setBackgroundColor(AppConstants.belowMonthEventDividerColor);
                    }

                    if (!AppConstants.strBelowMonthEventDividerColor.equals("null")) {
                        v_divider.setBackgroundColor(Color.parseColor(AppConstants.strBelowMonthEventDividerColor));
                    }

                    switch (model.getMotherOrChild()) {
                        case "Timely Completed":
                            tv_mother_or_child.setTextColor(context.getResources().getColor(R.color.colorGreenDark));
                            break;
                        case "Delayed Completed":
                            tv_mother_or_child.setTextColor(context.getResources().getColor(R.color.colorRedDark));
                            break;
                        case "Pending":
                        default:
                            tv_mother_or_child.setTextColor(context.getResources().getColor(R.color.colorOrangeDark));
                            break;
                    }

                }


            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(context).inflate(R.layout.row_event, parent, false);
            return new WorkPlanSummaryAdapter.EventViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

            EventModel eventModel = eventModelList.get(position);

            WorkPlanSummaryAdapter.EventViewHolder showEventsViewHolder = (WorkPlanSummaryAdapter.EventViewHolder) holder;
            showEventsViewHolder.setEvent(eventModel);

            ((WorkPlanSummaryAdapter.EventViewHolder) holder).ll_month_events.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }

        @Override
        public int getItemCount() {
            return eventModelList.size();
        }
    }


}
