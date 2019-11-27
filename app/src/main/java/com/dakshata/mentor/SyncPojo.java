package com.dakshata.mentor;

/**
 * Created by Aditya.v on 06-02-2018.
 */

public class SyncPojo {
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    String facilityname;

    public SyncPojo(String facilityname, String facilitytype, String dov, int image,int image1) {
        this.facilityname = facilityname;
        this.facilitytype = facilitytype;
        this.dov = dov;
        this.image = image;
        this.image1 = image1;
    }

    String facilitytype;
    String dov;
    int image;

    public int getImage1() {
        return image1;
    }

    public void setImage1(int image1) {
        this.image1 = image1;
    }

    int image1;
}
