package com.dakshata.mentor.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CompetencyTrackingParent {

    @SerializedName("form_data_qcodes")
    FormDataQCodes formDataQCodes;

    public FormDataQCodes getFormDataQCodes() {
        return formDataQCodes;
    }

    public void setFormDataQCodes(FormDataQCodes formDataQCodes) {
        this.formDataQCodes = formDataQCodes;
    }

    public ArrayList<CompetencyTrackingDto> getList() {
        return list;
    }

    public void setList(ArrayList<CompetencyTrackingDto> list) {
        this.list = list;
    }

    @SerializedName("formData2")
    ArrayList<CompetencyTrackingDto> list;

    public CompetencyTrackingParent(FormDataQCodes formDataQCodes, ArrayList<CompetencyTrackingDto> list) {
        this.formDataQCodes = formDataQCodes;
        this.list = list;
    }
}
