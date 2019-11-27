package com.dakshata.mentor.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Rakesh Prajapat on 2019-04-30
 * Copyright (c) 2019. All rights reserved by mobikode studio.
 * Last modified 13:00
 */
public class CompetencyTrackingDto implements Serializable {

    private String PName;
    private String cadre;
    private String PaExam;
    private String PvExam;
    private String AMTSL;
    private String NBR;
    private String HandHygiene;
    private String AntenatalComp;
    private String Partograph;
    private String PostnatalComp;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String ManagePretermBirth;
    private String PNCCounseling;
    private String Overall;
    @SerializedName("VisitDate")
    @Expose
    private String date;

    public String getPName() {
        return PName;
    }

    public void setPName(String PName) {
        this.PName = PName;
    }

    public String getCadre() {
        return cadre;
    }

    public void setCadre(String cadre) {
        this.cadre = cadre;
    }

    public String getPaExam() {
        return PaExam;
    }

    public void setPaExam(String paExam) {
        PaExam = paExam;
    }

    public String getPvExam() {
        return PvExam;
    }

    public void setPvExam(String pvExam) {
        PvExam = pvExam;
    }

    public String getAMTSL() {
        return AMTSL;
    }

    public void setAMTSL(String AMTSL) {
        this.AMTSL = AMTSL;
    }

    public String getNBR() {
        return NBR;
    }

    public void setNBR(String NBR) {
        this.NBR = NBR;
    }

    public String getHandHygiene() {
        return HandHygiene;
    }

    public void setHandHygiene(String handHygiene) {
        HandHygiene = handHygiene;
    }

    public String getAntenatalComp() {
        return AntenatalComp;
    }

    public void setAntenatalComp(String antenatalComp) {
        AntenatalComp = antenatalComp;
    }

    public String getPartograph() {
        return Partograph;
    }

    public void setPartograph(String partograph) {
        Partograph = partograph;
    }

    public String getPostnatalComp() {
        return PostnatalComp;
    }

    public void setPostnatalComp(String postnatalComp) {
        PostnatalComp = postnatalComp;
    }

    public String getManagePretermBirth() {
        return ManagePretermBirth;
    }

    public void setManagePretermBirth(String managePretermBirth) {
        ManagePretermBirth = managePretermBirth;
    }

    public String getPNCCounseling() {
        return PNCCounseling;
    }

    public void setPNCCounseling(String PNCCounseling) {
        this.PNCCounseling = PNCCounseling;
    }

    public String getOverall() {
        return Overall;
    }

    public void setOverall(String overall) {
        Overall = overall;
    }
}
