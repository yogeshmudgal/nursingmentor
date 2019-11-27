package com.dakshata.mentor.mydatecalendar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dakshata.mentor.R;
import com.dakshata.mentor.Utils.MentorConstant;
import com.dakshata.mentor.WorkplanActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Umesh Kumar on 5-2-2019.
 */
public class DateListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<DateModel> dateModelList;

    private Calendar calendar1 = Calendar.getInstance();
    private Date date_current_date = calendar1.getTime();

    private OnMonthBellowEventsDateClickListener onMonthBellowEventsDateClickListener;
    private OnDateClickListener onDateClickListener;

    DateModel dateModel;
    DateViewHolder dateViewHolder;

    public DateListAdapter(Context context, ArrayList<DateModel> dateModelList) {
        this.context = context;
        this.dateModelList = dateModelList;
    }

    public void setOnDateClickListener(OnDateClickListener onDateClickListener) {
        this.onDateClickListener = onDateClickListener;
    }

    public void setOnMonthBellowEventsClick(OnMonthBellowEventsDateClickListener onMonthBellowEventsDateClickListener) {
        this.onMonthBellowEventsDateClickListener = onMonthBellowEventsDateClickListener;
    }

    ArrayList<LinearLayout> eventSymbolList = new ArrayList<>();
    ArrayList<TextView> eventSymbolTVList = new ArrayList<>();

    class DateViewHolder extends RecyclerView.ViewHolder {

        TextView tv_month_date, tv_week_date, tv_event_simbol;
        LinearLayout ll_sub_parrent;

        public DateViewHolder(View itemView) {
            super(itemView);
            tv_month_date = (TextView) itemView.findViewById(R.id.tv_month_date);
            tv_week_date = (TextView) itemView.findViewById(R.id.tv_week_date);
            ll_sub_parrent = (LinearLayout) itemView.findViewById(R.id.ll_sub_parrent);
            tv_event_simbol = (TextView) itemView.findViewById(R.id.tv_event_simbol);

        }

        public void setDates(final DateModel model, final DateViewHolder dateViewHolder, final int positionMain) {

            Date date_current_month_date = AppConstants.main_calendar.getTime();

            // set month view dates
            if (model.getFlag().equals("month")) {
                tv_week_date.setVisibility(View.GONE);
                tv_month_date.setVisibility(View.VISIBLE);

                tv_month_date.setText(String.valueOf(model.getDates().getDate()));

                // set extra dates of month color & current dates of month color
                if (model.getDates().getMonth() == date_current_month_date.getMonth() && model.getDates().getYear() == date_current_month_date.getYear()) {

                    if (AppConstants.datesBackgroundColor != -1) {
                        ll_sub_parrent.setBackgroundColor(AppConstants.datesBackgroundColor);
                    }

                    if (!AppConstants.strDatesBackgroundColor.equals("null")) {
                        ll_sub_parrent.setBackgroundColor(Color.parseColor(AppConstants.strDatesBackgroundColor));
                    }

                    if (AppConstants.datesTextColor != -1) {
                        tv_month_date.setTextColor(AppConstants.datesTextColor);
                    }

                    if (!AppConstants.strDatesTextColor.equals("null")) {
                        tv_month_date.setTextColor(Color.parseColor(AppConstants.strDatesTextColor));
                    } else {
                        tv_month_date.setTextColor(context.getResources().getColor(R.color.black));
                    }

                } else {

                    if (AppConstants.extraDatesBackgroundColor != -1) {
                        ll_sub_parrent.setBackgroundColor(AppConstants.extraDatesBackgroundColor);
                    }

                    if (!AppConstants.strExtraDatesBackgroundColor.equals("null")) {
                        ll_sub_parrent.setBackgroundColor(Color.parseColor(AppConstants.strExtraDatesBackgroundColor));
                    }

                    if (AppConstants.extraDatesTextColor != -1) {
                        tv_month_date.setTextColor(AppConstants.extraDatesTextColor);
                    }

                    if (!AppConstants.strExtraDatesTextColor.equals("null")) {
                        tv_month_date.setTextColor(Color.parseColor(AppConstants.strExtraDatesTextColor));
                    } else {
                        tv_month_date.setTextColor(context.getResources().getColor(R.color.grey));
                    }

                }

                // set all saturday color
                if (AppConstants.isSaturdayOff) {

                    if (new SimpleDateFormat("EEEE").format(model.getDates()).equals("Saturday")) {
                        if (AppConstants.saturdayOffBackgroundColor != -1) {
                            ll_sub_parrent.setBackgroundColor(AppConstants.saturdayOffBackgroundColor);
                        }

                        if (!AppConstants.strSaturdayOffBackgroundColor.equals("null")) {
                            ll_sub_parrent.setBackgroundColor(Color.parseColor(AppConstants.strSaturdayOffBackgroundColor));
                        }

                        if (AppConstants.saturdayOffTextColor != -1) {
                            tv_month_date.setTextColor(AppConstants.saturdayOffTextColor);
                        }

                        if (!AppConstants.strSaturdayOffTextColor.equals("null")) {
                            tv_month_date.setTextColor(Color.parseColor(AppConstants.strSaturdayOffTextColor));
                        }

                    }
                }

                // set all sunday color
                if (AppConstants.isSundayOff) {

                    if (new SimpleDateFormat("EEEE").format(model.getDates()).equals("Sunday")) {

                        if (AppConstants.sundayOffBackgroundColor != -1) {
                            ll_sub_parrent.setBackgroundColor(AppConstants.sundayOffBackgroundColor);
                        }

                        if (!AppConstants.strSundayOffBackgroundColor.equals("null")) {
                            ll_sub_parrent.setBackgroundColor(Color.parseColor(AppConstants.strSundayOffBackgroundColor));
                        }

                        if (AppConstants.sundayOffTextColor != -1) {
                            tv_month_date.setTextColor(AppConstants.sundayOffTextColor);
                        }

                        if (!AppConstants.strSundayOffTextColor.equals("null")) {
                            tv_month_date.setTextColor(Color.parseColor(AppConstants.strSundayOffTextColor));
                        }

                    }
                }

                // set holiday color
                for (int i = 0; i < AppConstants.holidayList.size(); i++) {
                    if (AppConstants.holidayList.get(i).equals(AppConstants.sdfDate.format(model.getDates()))) {
                        if (AppConstants.holidayCellBackgroundColor != -1) {
                            ll_sub_parrent.setBackgroundColor(AppConstants.holidayCellBackgroundColor);
                        }

                        if (!AppConstants.strHolidayCellBackgroundColor.equals("null")) {
                            ll_sub_parrent.setBackgroundColor(Color.parseColor(AppConstants.strHolidayCellBackgroundColor));
                        }

                        if (AppConstants.holidayCellTextColor != -1) {
                            tv_month_date.setTextColor(AppConstants.holidayCellTextColor);
                        }

                        if (!AppConstants.strHolidayCellTextColor.equals("null")) {
                            tv_month_date.setTextColor(Color.parseColor(AppConstants.strHolidayCellTextColor));
                        }

                        if (AppConstants.isHolidayCellClickable) {
                            ll_sub_parrent.setClickable(true);
                            ll_sub_parrent.setEnabled(true);
                        } else {
                            //ll_sub_parrent.setClickable(false);
                            //ll_sub_parrent.setEnabled(false);
                        }

                    }
                }

                // set event color
                for (int i = 0; i < AppConstants.eventList.size(); i++) {
                    if (getDateFromString(AppConstants.eventList.get(i).getStrDate()) == getDateFromString((AppConstants.sdfDate.format(model.getDates())))) {
                        tv_event_simbol.setVisibility(View.INVISIBLE);

                        if (AppConstants.eventList.get(i).getImage() != -1) {
                            tv_event_simbol.setBackgroundResource(AppConstants.eventList.get(i).getImage());
                        } else {
                            tv_event_simbol.setBackgroundResource(R.drawable.event_view);
                        }

                        if (AppConstants.eventCellBackgroundColor != -1) {
                            ll_sub_parrent.setBackgroundColor(AppConstants.eventCellBackgroundColor);
                        }

                        if (!AppConstants.strEventCellBackgroundColor.equals("null")) {
                            ll_sub_parrent.setBackgroundColor(Color.parseColor(AppConstants.strEventCellBackgroundColor));
                        }

                        if (AppConstants.eventCellTextColor != -1) {
                            tv_month_date.setTextColor(AppConstants.eventCellTextColor);
                        }

                        if (!AppConstants.strEventCellTextColor.equals("null")) {
                            tv_month_date.setTextColor(Color.parseColor(AppConstants.strEventCellTextColor));
                        }

                    } else {
                        tv_event_simbol.setVisibility(View.INVISIBLE);
                    }
                }

                // set current date color
                if (model.getDates().getDate() == date_current_date.getDate() && model.getDates().getMonth() == date_current_date.getMonth() && model.getDates().getYear() == date_current_date.getYear()) {

                    if (AppConstants.currentDateBackgroundColor != -1) {
                        ll_sub_parrent.setBackgroundColor(AppConstants.currentDateBackgroundColor);
                    }

                    if (!AppConstants.strCurrentDateBackgroundColor.equals("null")) {
                        ll_sub_parrent.setBackgroundColor(Color.parseColor(AppConstants.strCurrentDateBackgroundColor));
                    }

                    if (AppConstants.currentDateTextColor != -1) {
                        tv_month_date.setTextColor(AppConstants.currentDateTextColor);
                    }

                    if (!AppConstants.strCurrentDateTextColor.equals("null")) {
                        tv_month_date.setTextColor(Color.parseColor(AppConstants.strCurrentDateTextColor));
                    } else {
                        tv_month_date.setTextColor(Color.BLUE);
                    }

                }

            }
            // set week view dates
            else if (model.getFlag().equals("week")) {
                tv_month_date.setVisibility(View.GONE);
                tv_week_date.setVisibility(View.VISIBLE);

                tv_week_date.setText(String.valueOf(model.getDates().getDate()));

                // set extra dates of month color & current dates of month color
                if (model.getDates().getMonth() == date_current_month_date.getMonth() && model.getDates().getYear() == date_current_month_date.getYear()) {

                    if (AppConstants.datesBackgroundColor != -1) {
                        ll_sub_parrent.setBackgroundColor(AppConstants.datesBackgroundColor);
                    }

                    if (!AppConstants.strDatesBackgroundColor.equals("null")) {
                        ll_sub_parrent.setBackgroundColor(Color.parseColor(AppConstants.strDatesBackgroundColor));
                    }

                    if (AppConstants.datesTextColor != -1) {
                        tv_week_date.setTextColor(AppConstants.datesTextColor);
                    }

                    if (!AppConstants.strDatesTextColor.equals("null")) {
                        tv_week_date.setTextColor(Color.parseColor(AppConstants.strDatesTextColor));
                    } else {
                        tv_week_date.setTextColor(context.getResources().getColor(R.color.black));
                    }

                } else {

                    if (AppConstants.extraDatesBackgroundColor != -1) {
                        ll_sub_parrent.setBackgroundColor(AppConstants.extraDatesBackgroundColor);
                    }

                    if (!AppConstants.strExtraDatesBackgroundColor.equals("null")) {
                        ll_sub_parrent.setBackgroundColor(Color.parseColor(AppConstants.strExtraDatesBackgroundColor));
                    }

                    if (AppConstants.extraDatesTextColor != -1) {
                        tv_week_date.setTextColor(AppConstants.extraDatesTextColor);
                    }

                    if (!AppConstants.strExtraDatesTextColor.equals("null")) {
                        tv_week_date.setTextColor(Color.parseColor(AppConstants.strExtraDatesTextColor));
                    } else {
                        tv_week_date.setTextColor(context.getResources().getColor(R.color.grey));
                    }

                }

                // set all saturday color
                if (AppConstants.isSaturdayOff) {

                    if (new SimpleDateFormat("EEEE").format(model.getDates()).equals("Saturday")) {
                        if (AppConstants.saturdayOffBackgroundColor != -1) {
                            ll_sub_parrent.setBackgroundColor(AppConstants.saturdayOffBackgroundColor);
                        }

                        if (!AppConstants.strSaturdayOffBackgroundColor.equals("null")) {
                            ll_sub_parrent.setBackgroundColor(Color.parseColor(AppConstants.strSaturdayOffBackgroundColor));
                        }

                        if (AppConstants.saturdayOffTextColor != -1) {
                            tv_week_date.setTextColor(AppConstants.saturdayOffTextColor);
                        }

                        if (!AppConstants.strSaturdayOffTextColor.equals("null")) {
                            tv_week_date.setTextColor(Color.parseColor(AppConstants.strSaturdayOffTextColor));
                        }

                    }
                }

                // set all sunday color
                if (AppConstants.isSundayOff) {

                    if (new SimpleDateFormat("EEEE").format(model.getDates()).equals("Sunday")) {

                        if (AppConstants.sundayOffBackgroundColor != -1) {
                            ll_sub_parrent.setBackgroundColor(AppConstants.sundayOffBackgroundColor);
                        }

                        if (!AppConstants.strSundayOffBackgroundColor.equals("null")) {
                            ll_sub_parrent.setBackgroundColor(Color.parseColor(AppConstants.strSundayOffBackgroundColor));
                        }

                        if (AppConstants.sundayOffTextColor != -1) {
                            tv_week_date.setTextColor(AppConstants.sundayOffTextColor);
                        }

                        if (!AppConstants.strSundayOffTextColor.equals("null")) {
                            tv_week_date.setTextColor(Color.parseColor(AppConstants.strSundayOffTextColor));
                        }

                    }
                }

                // set holiday color
                for (int i = 0; i < AppConstants.holidayList.size(); i++) {
                    if (AppConstants.holidayList.get(i).equals(AppConstants.sdfDate.format(model.getDates()))) {

                        if (AppConstants.holidayCellBackgroundColor != -1) {
                            ll_sub_parrent.setBackgroundColor(AppConstants.holidayCellBackgroundColor);
                        }

                        if (!AppConstants.strHolidayCellBackgroundColor.equals("null")) {
                            ll_sub_parrent.setBackgroundColor(Color.parseColor(AppConstants.strHolidayCellBackgroundColor));
                        }

                        if (AppConstants.holidayCellTextColor != -1) {
                            tv_month_date.setTextColor(AppConstants.holidayCellTextColor);
                        }

                        if (!AppConstants.strHolidayCellTextColor.equals("null")) {
                            tv_month_date.setTextColor(Color.parseColor(AppConstants.strHolidayCellTextColor));
                        }

                        if (AppConstants.isHolidayCellClickable) {
                            ll_sub_parrent.setClickable(true);
                            ll_sub_parrent.setEnabled(true);
                        } else {
                            //ll_sub_parrent.setClickable(false);
                            //ll_sub_parrent.setEnabled(false);
                        }

                    }
                }

                // set event color
                for (int i = 0; i < AppConstants.eventList.size(); i++) {
                    if (getDateFromString(AppConstants.eventList.get(i).getStrDate()) == getDateFromString((AppConstants.sdfDate.format(model.getDates())))) {
                        tv_event_simbol.setVisibility(View.INVISIBLE);

                        if (AppConstants.eventList.get(i).getImage() != -1) {
                            tv_event_simbol.setBackgroundResource(AppConstants.eventList.get(i).getImage());
                        } else {
                            tv_event_simbol.setBackgroundResource(R.drawable.event_view);
                        }

                        if (AppConstants.eventCellBackgroundColor != -1) {
                            ll_sub_parrent.setBackgroundColor(AppConstants.eventCellBackgroundColor);
                        }

                        if (!AppConstants.strEventCellBackgroundColor.equals("null")) {
                            ll_sub_parrent.setBackgroundColor(Color.parseColor(AppConstants.strEventCellBackgroundColor));
                        }

                        if (AppConstants.eventCellTextColor != -1) {
                            tv_week_date.setTextColor(AppConstants.eventCellTextColor);
                        }

                        if (!AppConstants.strEventCellTextColor.equals("null")) {
                            tv_week_date.setTextColor(Color.parseColor(AppConstants.strEventCellTextColor));
                        }

                    } else {
                        tv_event_simbol.setVisibility(View.INVISIBLE);
                    }
                }

                // set current date color
                if (model.getDates().getDate() == date_current_date.getDate() && model.getDates().getMonth() == date_current_date.getMonth() && model.getDates().getYear() == date_current_date.getYear()) {

                    if (AppConstants.currentDateBackgroundColor != -1) {
                        ll_sub_parrent.setBackgroundColor(AppConstants.currentDateBackgroundColor);
                    }

                    if (!AppConstants.strCurrentDateBackgroundColor.equals("null")) {
                        ll_sub_parrent.setBackgroundColor(Color.parseColor(AppConstants.strCurrentDateBackgroundColor));
                    }

                    if (AppConstants.currentDateTextColor != -1) {
                        tv_week_date.setTextColor(AppConstants.currentDateTextColor);
                    }

                    if (!AppConstants.strCurrentDateTextColor.equals("null")) {
                        tv_week_date.setTextColor(Color.parseColor(AppConstants.strCurrentDateTextColor));
                    } else {
                        tv_week_date.setTextColor(Color.BLUE);
                    }

                }

            }

//            dateViewHolder.tv_event_simbol.setHint("F");
            dateViewHolder.tv_event_simbol.setBackgroundResource(R.drawable.event_view);

//            if (dateModel.getFlagTextView().equals("True")) {
//                dateViewHolder.tv_event_simbol.setVisibility(View.VISIBLE);
//            } else {
//                dateModel.setFlagTextView("False");
//                dateViewHolder.tv_event_simbol.setVisibility(View.INVISIBLE);
//            }

            if (((String) eventSymbolList.get(positionMain).getTag()).equals("True")) {
                dateViewHolder.tv_event_simbol.setVisibility(View.VISIBLE);
            } else {
                dateViewHolder.tv_event_simbol.setVisibility(View.INVISIBLE);
            }

            ll_sub_parrent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    Toast.makeText(context, positionMain + " - " , Toast.LENGTH_LONG).show();

                    onDateClickListener.onClick(model.getDates());

                    if (AppConstants.isShowMonthWithBellowEvents || AppConstants.isAgenda) {
                        onMonthBellowEventsDateClickListener.onClick(model.getDates());
                    }
                    for (int i = 0; i < eventSymbolList.size(); i++) {
                        eventSymbolList.get(i).setTag("False");
                        eventSymbolTVList.get(i).setVisibility(View.INVISIBLE);
                    }
                    ((LinearLayout) eventSymbolList.get(eventSymbolList.indexOf((LinearLayout) v))).setTag("True");
                    eventSymbolTVList.get(eventSymbolList.indexOf((LinearLayout) v)).setVisibility(View.VISIBLE);
                    DateModel dateModel = dateModelList.get(positionMain);
                    dateModel.setFlagTextView("True");
                    dateViewHolder.tv_event_simbol.setVisibility(View.VISIBLE);

                    if (model.getDates().getDate() == date_current_date.getDate() && model.getDates().getMonth() == date_current_date.getMonth() &&
                            model.getDates().getYear() == date_current_date.getYear()) {
                        Toast.makeText(context, "You can't set Workplan for today. Make workplan for tomorrow & so on.", Toast.LENGTH_LONG).show();
                    } else {
                        DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
                        Date date = null;
                        try {
                            date = (Date) formatter.parse(dateModel.getDates().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        System.out.println(date);

                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);

                        boolean isEventSet = false;
                        if ((cal.get(Calendar.MONTH)+1) >= MentorConstant.currentMonth &&
                                cal.get(Calendar.YEAR) == MentorConstant.currentYear) {
//                        Toast.makeText(context, MentorConstant.currentMonth + "" , Toast.LENGTH_LONG).show();

                            for (int i = 0; i < AppConstants.eventList.size(); i++) {

                                DateFormat formatterTemp = new SimpleDateFormat("dd-MM-yyyy");
                                Date dateTemp = null;
                                try {
                                    dateTemp = (Date) formatterTemp.parse(AppConstants.eventList.get(i).getStrDate());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                int dat = dateTemp.getDate();
                                int month = dateTemp.getMonth();
                                int year = dateTemp.getYear();
                                if (dat == cal.get(Calendar.DATE)) {
                                    isEventSet = true;
                                    break;
                                } else {
                                    isEventSet = false;
                                }
                            }

                            if (!isEventSet) {
                                Intent intent = new Intent(context, WorkplanActivity.class);
                                intent.putExtra("selected_date", (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DATE) + "/" + cal.get(Calendar.YEAR));
                                context.startActivity(intent);
                            } else {
                                Toast.makeText(context, "Workplan is already assigned", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            });

            ll_sub_parrent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onDateClickListener.onLongClick(model.getDates());
                    return true;
                }
            });


        }
    }

    private long getDateFromString(String strDate) {
        strDate = strDate.replace("-", "/");
//        Log.e("strDate","" + strDate);
        return new Date(strDate).getTime();
    }

    public void setSelectedBullet(DateModel model, DateViewHolder dateViewHolder, TextView tv_event_simbol) {
        for (int i = 0; i < AppConstants.eventList.size(); i++) {
            if (getDateFromString(AppConstants.eventList.get(i).getStrDate()) == getDateFromString((AppConstants.sdfDate.format(model.getDates())))) {
                dateViewHolder.tv_event_simbol.setBackgroundResource(R.drawable.event_view);
                dateViewHolder.tv_event_simbol.setVisibility(View.VISIBLE);
                break;
            } else if (getDateFromString(AppConstants.eventList.get(i).getStrDate()) != getDateFromString(AppConstants.sdfDate.format(model.getDates()))) {
                dateViewHolder.tv_event_simbol.setBackgroundResource(R.drawable.event_view_unselected);
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.row_date, parent, false);
        return new DateViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        dateModel = dateModelList.get(position);

        dateViewHolder = (DateViewHolder) holder;

        dateViewHolder.ll_sub_parrent.setTag("False");
        eventSymbolTVList.add(dateViewHolder.tv_event_simbol);
        eventSymbolList.add(dateViewHolder.ll_sub_parrent);

        dateViewHolder.setDates(dateModel, dateViewHolder, position);

    }

    @Override
    public int getItemCount() {
        return dateModelList.size();
    }

}
