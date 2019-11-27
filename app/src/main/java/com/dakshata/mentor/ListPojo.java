package com.dakshata.mentor;

import android.graphics.Bitmap;

/**
 * Created by Aditya.v on 09-01-2018.
 */

public class ListPojo {

    String session;
    String facilityname;
    String facilitytype;
    int recordId;

    public ListPojo(String facilityname, String facilitytype, String dov, String session, int recordId) {
        this.session = session;
        this.dov = dov;
        this.recordId = recordId;
        this.facilityname = facilityname;
        this.facilitytype = facilitytype;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getFacilityname() {
        return facilityname;
    }

    public void setFacilityname(String facilityname) {
        this.facilityname = facilityname;
    }

    public String getFacilitytype() {
        return facilitytype;
    }

    public void setFacilitytype(String facilitytype) {
        this.facilitytype = facilitytype;
    }

    public String getDov() {
        return dov;
    }

    public void setDov(String dov) {
        this.dov = dov;
    }

    public ListPojo(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    Bitmap image;
    String dov;

    public ListPojo(String facilityname, String facilitytype, String dov) {
        this.facilityname = facilityname;
        this.facilitytype = facilitytype;
        this.dov = dov;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }
}
