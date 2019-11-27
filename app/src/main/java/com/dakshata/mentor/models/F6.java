
package com.dakshata.mentor.models;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class F6 implements Serializable
{

    @SerializedName("questions_name")
    @Expose
    private String questionsName;
    @SerializedName("q_code")
    @Expose
    private String qCode;
    @SerializedName("form_name")
    @Expose
    private String formName;
    private final static long serialVersionUID = -5838954664936526479L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public F6() {
    }

    /**
     * 
     * @param formName
     * @param qCode
     * @param questionsName
     */
    public F6(String questionsName, String qCode, String formName) {
        super();
        this.questionsName = questionsName;
        this.qCode = qCode;
        this.formName = formName;
    }

    public String getQuestionsName() {
        return questionsName;
    }

    public void setQuestionsName(String questionsName) {
        this.questionsName = questionsName;
    }

    public F6 withQuestionsName(String questionsName) {
        this.questionsName = questionsName;
        return this;
    }

    public String getQCode() {
        return qCode;
    }

    public void setQCode(String qCode) {
        this.qCode = qCode;
    }

    public F6 withQCode(String qCode) {
        this.qCode = qCode;
        return this;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public F6 withFormName(String formName) {
        this.formName = formName;
        return this;
    }

}
