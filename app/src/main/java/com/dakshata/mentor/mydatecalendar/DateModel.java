package com.dakshata.mentor.mydatecalendar;

import java.util.Date;

/**
 * Created by Umesh Kumar on 5-2-2019.
 */
public class DateModel {

    private Date dates;
    private String flag;

    public String getFlagTextView() {
        return flagTextView;
    }

    public void setFlagTextView(String flagTextView) {
        this.flagTextView = flagTextView;
    }

    private String flagTextView;

//    private boolean isCurrentDate;
//    private boolean isEventDate;

//    private ArrayList<Boolean> eventDate;


    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Date getDates() {
        return dates;
    }

    public void setDates(Date dates) {
        this.dates = dates;
    }
}
