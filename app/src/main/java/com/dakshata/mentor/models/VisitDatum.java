
package com.dakshata.mentor.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VisitDatum implements Serializable
{

    @SerializedName("form_code")
    @Expose
    private String formCode;
    @SerializedName("form_data")
    @Expose
    private List<FormDatum> formData = null;
    @SerializedName("form_data_sub_ques")
    @Expose
    private ArrayList<FormDataSubQuestion> formDataSubQuestions=null;

    public ArrayList<FormDataSubQuestion> getFormDataSubQuestions() {
        return formDataSubQuestions;
    }

    public void setFormDataSubQuestions(ArrayList<FormDataSubQuestion> formDataSubQuestions) {
        this.formDataSubQuestions = formDataSubQuestions;
    }

    private List<CompetencyTrackingParent> formData2 = new ArrayList<>();
    private List<ClientFeedbackDto> formData3 = new ArrayList<>();

    public List<ClientFeedbackDto> getFormData3() {
        return formData3;
    }

    public void setFormData3(List<ClientFeedbackDto> formData3) {
        this.formData3 = formData3;
    }

    public List<CompetencyTrackingParent> getFormData2() {
        return formData2;
    }

    public void setFormData2(List<CompetencyTrackingParent> formData2) {
        this.formData2 = formData2;
    }

    private final static long serialVersionUID = -2757198718635596290L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public VisitDatum() {
    }

    /**
     * 
     * @param formData
     * @param formCode
     */
    public VisitDatum(String formCode, List<FormDatum> formData) {
        super();
        this.formCode = formCode;
        this.formData = formData;
    }

    /**
     *
     * @param formData
     * @param formCode
     */
    public VisitDatum(String formCode, List<FormDatum> formData,ArrayList<FormDataSubQuestion> formDataSubQuestions) {
        super();
        this.formCode = formCode;
        this.formData = formData;
        this.formDataSubQuestions=formDataSubQuestions;
    }
    public String getFormCode() {
        return formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public VisitDatum withFormCode(String formCode) {
        this.formCode = formCode;
        return this;
    }

    public List<FormDatum> getFormData() {
        return formData;
    }

    public void setFormData(List<FormDatum> formData) {
        this.formData = formData;
    }

    public VisitDatum withFormData(List<FormDatum> formData) {
        this.formData = formData;
        return this;
    }

}
