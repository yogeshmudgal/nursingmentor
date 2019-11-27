
package com.dakshata.mentor.models;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class F8 implements Serializable
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
    private final static long serialVersionUID = 7361903837640303195L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public F8() {
    }

    /**
     * 
     * @param formName
     * @param qCode
     * @param questionsName
     */
    public F8(String questionsName, String qCode, String formName) {
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

//    public F8 withQuestionsName(String questionsName) {
//        this.questionsName = questionsName;
//        return this;
//    }

    public String getQCode() {
        return qCode;
    }

    public void setQCode(String qCode) {
        this.qCode = qCode;
    }

//    public F8 withQCode(String qCode) {
//        this.qCode = qCode;
//        return this;
//    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

//    public F8 withFormName(String formName) {
//        this.formName = formName;
//        return this;
//    }

}
