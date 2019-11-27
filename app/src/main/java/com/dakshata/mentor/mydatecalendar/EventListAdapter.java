package com.dakshata.mentor.mydatecalendar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dakshata.mentor.R;

import java.util.ArrayList;

/**
 * Created by by Umesh Kumar on 5-2-2019.
 */
public class EventListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity context;
    private String strViewFlag;
    private ArrayList<EventModel> eventModelList;

    public EventListAdapter(Context context, ArrayList<EventModel> eventModelList, String strViewFlag) {
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
                    tv_event_district.setText("" + "NA");
                } else {
                    tv_event_name.setText("" + "NA");
                    tv_event_district.setText("" + "NA");
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
        return new EventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        EventModel eventModel = eventModelList.get(position);

        EventViewHolder showEventsViewHolder = (EventViewHolder) holder;
        showEventsViewHolder.setEvent(eventModel);

        ((EventViewHolder) holder).ll_month_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "" + ((EventViewHolder) holder).tv_event_name.getText().toString(), Toast.LENGTH_LONG).show();

//                MyConstant.handleBackPressCall = 2;
//                Bundle bundle = new Bundle();
//                bundle.putString("name", ((EventViewHolder) holder).tv_event_name.getText().toString());
//                bundle.putString("date", ((EventViewHolder) holder).tv_event_date.getText().toString());
//                bundle.putString("pnc_period", ((EventViewHolder) holder).tv_event_time.getText().toString());
//                bundle.putString("mobile", ((EventViewHolder) holder).tv_event_mobile.getText().toString().replace(context.getString(R.string.mobile), "").trim());
//                bundle.putString("mother_or_child", ((EventViewHolder) holder).tv_mother_or_child.getText().toString());
//                VhndFormFrag fragment = new VhndFormFrag();
//                fragment.setArguments(bundle);
//                FragmentManager fragmentManager = context.getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.frameLand, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return eventModelList.size();
    }
}
