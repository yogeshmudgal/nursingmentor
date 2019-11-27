
package com.dakshata.mentor.models;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnswersModel implements Serializable
{

    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("visit_id")
    @Expose
    private String visitId;
    @SerializedName("visit_data")
    @Expose
    private List<VisitDatum> visitData = null;
    private List<CompetencyTrackingDto> visitData_new = null;

    public List<CompetencyTrackingDto> getVisitData_new() {
        return visitData_new;
    }

    public void setVisitData_new(List<CompetencyTrackingDto> visitData_new) {
        this.visitData_new = visitData_new;
    }

    private final static long serialVersionUID = -9139171995669741136L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AnswersModel() {
    }

    /**
     * 
     * @param visitData
     * @param visitId
     * @param user
     */
    public AnswersModel(String user, String visitId, List<VisitDatum> visitData) {
        super();
        this.user = user;
        this.visitId = visitId;
        this.visitData = visitData;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public AnswersModel withUser(String user) {
        this.user = user;
        return this;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public AnswersModel withVisitId(String visitId) {
        this.visitId = visitId;
        return this;
    }

    public List<VisitDatum> getVisitData() {
        return visitData;
    }

    public void setVisitData(List<VisitDatum> visitData) {
        this.visitData = visitData;
    }

    public AnswersModel withVisitData(List<VisitDatum> visitData) {
        this.visitData = visitData;
        return this;
    }

}
