package com.dakshata.mentor;

/**
 * Created by Aditya.v on 15-12-2017.
 */

public class FormDetails {
    public FormDetails(String formName, int formImage) {
        this.formName = formName;
        this.formImage = formImage;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public int getFormImage() {
        return formImage;
    }

    public void setFormImage(int formImage) {
        this.formImage = formImage;
    }

    String formName;
    int formImage;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public FormDetails(String formName, int formImage, String count) {
        this.formName = formName;
        this.formImage = formImage;
        this.count = count;
    }

    String count;
}
