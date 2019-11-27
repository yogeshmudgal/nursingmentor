package com.dakshata.mentor.mydatecalendar;

/**
 * Created by by Umesh Kumar on 5-2-2019.
 */
public class EventModel {

    private String motherOrChild;
    private String strDate;
    private String strStartTime;
    private String strEndTime;
    private String strName;
    private int image = -1;


    public EventModel(String strDate, String strStartTime, String strEndTime, String strName, String motherOrChild) {
        this.strDate = strDate;
        this.strStartTime = strStartTime;
        this.strEndTime = strEndTime;
        this.strName = strName;
        this.motherOrChild = motherOrChild;
    }

    public EventModel(String strDate, String strStartTime, String strEndTime, String strName, int image) {
        this.strDate = strDate;
        this.strStartTime = strStartTime;
        this.strEndTime = strEndTime;
        this.strName = strName;
        this.image = image;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public String getStrStartTime() {
        return strStartTime;
    }

    public void setStrStartTime(String strStartTime) {
        this.strStartTime = strStartTime;
    }

    public String getStrEndTime() {
        return strEndTime;
    }

    public void setStrEndTime(String strEndTime) {
        this.strEndTime = strEndTime;
    }

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getMotherOrChild() {
        return motherOrChild;
    }

    public void setMotherOrChild(String motherOrChild) {
        this.motherOrChild = motherOrChild;
    }
}
